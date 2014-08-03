package org.eclipse.swt.printing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GCData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.DEVMODE;
import org.eclipse.swt.internal.win32.DEVMODEA;
import org.eclipse.swt.internal.win32.DEVMODEW;
import org.eclipse.swt.internal.win32.DOCINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PRINTDLG;
import org.eclipse.swt.internal.win32.TCHAR;

public final class Printer extends Device
{
  public int handle;
  PrinterData data;
  boolean isGCCreated = false;
  static TCHAR profile = new TCHAR(0, "PrinterPorts", true);
  static TCHAR appName = new TCHAR(0, "windows", true);
  static TCHAR keyName = new TCHAR(0, "device", true);

  public static PrinterData[] getPrinterList()
  {
    int i = 1024;
    TCHAR localTCHAR1 = new TCHAR(0, i);
    TCHAR localTCHAR2 = new TCHAR(0, 1);
    int j = OS.GetProfileString(profile, null, localTCHAR2, localTCHAR1, i);
    if (j == 0)
      return new PrinterData[0];
    Object localObject = new String[5];
    int k = 0;
    int m = 0;
    for (int n = 0; n < j; n++)
      if (localTCHAR1.tcharAt(n) == 0)
      {
        if (k == localObject.length)
        {
          String[] arrayOfString = new String[localObject.length + 5];
          System.arraycopy(localObject, 0, arrayOfString, 0, localObject.length);
          localObject = arrayOfString;
        }
        localObject[k] = localTCHAR1.toString(m, n - m);
        k++;
        m = n + 1;
      }
    PrinterData[] arrayOfPrinterData = new PrinterData[k];
    for (int i1 = 0; i1 < k; i1++)
    {
      String str1 = localObject[i1];
      String str2 = "";
      if (OS.GetProfileString(profile, new TCHAR(0, str1, true), localTCHAR2, localTCHAR1, i) > 0)
      {
        for (int i2 = 0; (localTCHAR1.tcharAt(i2) != 44) && (i2 < i); i2++);
        if (i2 < i)
          str2 = localTCHAR1.toString(0, i2);
      }
      arrayOfPrinterData[i1] = new PrinterData(str2, str1);
    }
    return arrayOfPrinterData;
  }

  public static PrinterData getDefaultPrinterData()
  {
    String str1 = null;
    int i = 1024;
    TCHAR localTCHAR1 = new TCHAR(0, i);
    TCHAR localTCHAR2 = new TCHAR(0, 1);
    int j = OS.GetProfileString(appName, keyName, localTCHAR2, localTCHAR1, i);
    if (j == 0)
      return null;
    for (int k = 0; (localTCHAR1.tcharAt(k) != 44) && (k < i); k++);
    if (k < i)
      str1 = localTCHAR1.toString(0, k);
    String str2 = "";
    if (OS.GetProfileString(profile, new TCHAR(0, str1, true), localTCHAR2, localTCHAR1, i) > 0)
    {
      for (k = 0; (localTCHAR1.tcharAt(k) != 44) && (k < i); k++);
      if (k < i)
        str2 = localTCHAR1.toString(0, k);
    }
    return new PrinterData(str2, str1);
  }

  static DeviceData checkNull(PrinterData paramPrinterData)
  {
    if (paramPrinterData == null)
      paramPrinterData = new PrinterData();
    if ((paramPrinterData.driver == null) || (paramPrinterData.name == null))
    {
      PrinterData localPrinterData = getDefaultPrinterData();
      if (localPrinterData == null)
        SWT.error(2);
      paramPrinterData.driver = localPrinterData.driver;
      paramPrinterData.name = localPrinterData.name;
    }
    return paramPrinterData;
  }

  public Printer()
  {
    this(null);
  }

  public Printer(PrinterData paramPrinterData)
  {
    super(checkNull(paramPrinterData));
  }

  protected void create(DeviceData paramDeviceData)
  {
    this.data = ((PrinterData)paramDeviceData);
    TCHAR localTCHAR1 = new TCHAR(0, this.data.driver, true);
    TCHAR localTCHAR2 = new TCHAR(0, this.data.name, true);
    int i = 0;
    byte[] arrayOfByte = this.data.otherData;
    int j = OS.GetProcessHeap();
    if ((arrayOfByte != null) && (arrayOfByte.length != 0))
    {
      i = OS.HeapAlloc(j, 8, arrayOfByte.length);
      OS.MoveMemory(i, arrayOfByte, arrayOfByte.length);
    }
    else
    {
      localObject = new PRINTDLG();
      ((PRINTDLG)localObject).lStructSize = PRINTDLG.sizeof;
      ((PRINTDLG)localObject).Flags = 1024;
      OS.PrintDlg((PRINTDLG)localObject);
      if (((PRINTDLG)localObject).hDevMode != 0)
      {
        int k = ((PRINTDLG)localObject).hDevMode;
        int m = OS.GlobalLock(k);
        int n = OS.GlobalSize(k);
        i = OS.HeapAlloc(j, 8, n);
        OS.MoveMemory(i, m, n);
        OS.GlobalUnlock(k);
        OS.GlobalFree(((PRINTDLG)localObject).hDevMode);
      }
      if (((PRINTDLG)localObject).hDevNames != 0)
        OS.GlobalFree(((PRINTDLG)localObject).hDevNames);
    }
    Object localObject = OS.IsUnicode ? new DEVMODEW() : new DEVMODEA();
    OS.MoveMemory((DEVMODE)localObject, i, DEVMODE.sizeof);
    localObject.dmFields |= 1;
    ((DEVMODE)localObject).dmOrientation = (this.data.orientation == 2 ? 2 : 1);
    if (this.data.copyCount != 1)
    {
      localObject.dmFields |= 256;
      ((DEVMODE)localObject).dmCopies = ((short)this.data.copyCount);
    }
    if (this.data.collate)
    {
      localObject.dmFields |= 32768;
      ((DEVMODE)localObject).dmCollate = 1;
    }
    OS.MoveMemory(i, (DEVMODE)localObject, DEVMODE.sizeof);
    this.handle = OS.CreateDC(localTCHAR1, localTCHAR2, 0, i);
    if (i != 0)
      OS.HeapFree(j, 0, i);
    if (this.handle == 0)
      SWT.error(2);
  }

  public int internal_new_GC(GCData paramGCData)
  {
    if (this.handle == 0)
      SWT.error(2);
    if (paramGCData != null)
    {
      if (this.isGCCreated)
        SWT.error(5);
      int i = 100663296;
      if ((paramGCData.style & i) != 0)
        paramGCData.layout = ((paramGCData.style & 0x4000000) != 0 ? 1 : 0);
      else
        paramGCData.style |= 33554432;
      paramGCData.device = this;
      paramGCData.font = Font.win32_new(this, OS.GetCurrentObject(this.handle, 6));
      this.isGCCreated = true;
    }
    return this.handle;
  }

  public void internal_dispose_GC(int paramInt, GCData paramGCData)
  {
    if (paramGCData != null)
      this.isGCCreated = false;
  }

  public boolean startJob(String paramString)
  {
    checkDevice();
    DOCINFO localDOCINFO = new DOCINFO();
    localDOCINFO.cbSize = DOCINFO.sizeof;
    int i = OS.GetProcessHeap();
    int j = 0;
    if ((paramString != null) && (paramString.length() != 0))
    {
      TCHAR localTCHAR1 = new TCHAR(0, paramString, true);
      int m = localTCHAR1.length() * TCHAR.sizeof;
      j = OS.HeapAlloc(i, 8, m);
      OS.MoveMemory(j, localTCHAR1, m);
      localDOCINFO.lpszDocName = j;
    }
    int k = 0;
    if ((this.data.printToFile) && (this.data.fileName != null))
    {
      TCHAR localTCHAR2 = new TCHAR(0, this.data.fileName, true);
      int i1 = localTCHAR2.length() * TCHAR.sizeof;
      k = OS.HeapAlloc(i, 8, i1);
      OS.MoveMemory(k, localTCHAR2, i1);
      localDOCINFO.lpszOutput = k;
    }
    int n = OS.StartDoc(this.handle, localDOCINFO);
    if (j != 0)
      OS.HeapFree(i, 0, j);
    if (k != 0)
      OS.HeapFree(i, 0, k);
    return n > 0;
  }

  public void endJob()
  {
    checkDevice();
    OS.EndDoc(this.handle);
  }

  public void cancelJob()
  {
    checkDevice();
    OS.AbortDoc(this.handle);
  }

  public boolean startPage()
  {
    checkDevice();
    int i = OS.StartPage(this.handle);
    if (i <= 0)
      OS.AbortDoc(this.handle);
    return i > 0;
  }

  public void endPage()
  {
    checkDevice();
    OS.EndPage(this.handle);
  }

  public Point getDPI()
  {
    checkDevice();
    int i = OS.GetDeviceCaps(this.handle, 88);
    int j = OS.GetDeviceCaps(this.handle, 90);
    return new Point(i, j);
  }

  public Rectangle getBounds()
  {
    checkDevice();
    int i = OS.GetDeviceCaps(this.handle, 110);
    int j = OS.GetDeviceCaps(this.handle, 111);
    return new Rectangle(0, 0, i, j);
  }

  public Rectangle getClientArea()
  {
    checkDevice();
    int i = OS.GetDeviceCaps(this.handle, 8);
    int j = OS.GetDeviceCaps(this.handle, 10);
    return new Rectangle(0, 0, i, j);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkDevice();
    int i = -OS.GetDeviceCaps(this.handle, 112);
    int j = -OS.GetDeviceCaps(this.handle, 113);
    int k = OS.GetDeviceCaps(this.handle, 8);
    int m = OS.GetDeviceCaps(this.handle, 10);
    int n = OS.GetDeviceCaps(this.handle, 110);
    int i1 = OS.GetDeviceCaps(this.handle, 111);
    int i2 = n - k;
    int i3 = i1 - m;
    return new Rectangle(paramInt1 + i, paramInt2 + j, paramInt3 + i2, paramInt4 + i3);
  }

  public PrinterData getPrinterData()
  {
    return this.data;
  }

  protected void checkDevice()
  {
    if (this.handle == 0)
      SWT.error(45);
  }

  protected void release()
  {
    super.release();
    this.data = null;
  }

  protected void destroy()
  {
    if (this.handle != 0)
      OS.DeleteDC(this.handle);
    this.handle = 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.printing.Printer
 * JD-Core Version:    0.6.2
 */