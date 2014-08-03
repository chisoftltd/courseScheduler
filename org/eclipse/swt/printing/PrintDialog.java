package org.eclipse.swt.printing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.DEVMODE;
import org.eclipse.swt.internal.win32.DEVMODEA;
import org.eclipse.swt.internal.win32.DEVMODEW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PRINTDLG;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class PrintDialog extends Dialog
{
  static final TCHAR DialogClass = new TCHAR(0, OS.IsWinCE ? "Dialog" : "#32770", true);
  PrinterData printerData = new PrinterData();

  public PrintDialog(Shell paramShell)
  {
    this(paramShell, 32768);
  }

  public PrintDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, checkStyle(paramShell, paramInt));
    checkSubclass();
  }

  static int checkBits(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    int i = paramInt2 | paramInt3 | paramInt4 | paramInt5 | paramInt6 | paramInt7;
    if ((paramInt1 & i) == 0)
      paramInt1 |= paramInt2;
    if ((paramInt1 & paramInt2) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt2;
    if ((paramInt1 & paramInt3) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt3;
    if ((paramInt1 & paramInt4) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt4;
    if ((paramInt1 & paramInt5) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt5;
    if ((paramInt1 & paramInt6) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt6;
    if ((paramInt1 & paramInt7) != 0)
      paramInt1 = paramInt1 & (i ^ 0xFFFFFFFF) | paramInt7;
    return paramInt1;
  }

  static int checkStyle(Shell paramShell, int paramInt)
  {
    int i = 229376;
    if ((paramInt & 0x10000000) != 0)
    {
      paramInt &= -268435457;
      if ((paramInt & i) == 0)
        paramInt |= (paramShell == null ? 65536 : 32768);
    }
    if ((paramInt & i) == 0)
      paramInt |= 65536;
    paramInt &= -134217729;
    if (((paramInt & 0x6000000) == 0) && (paramShell != null))
    {
      if ((paramShell.getStyle() & 0x2000000) != 0)
        paramInt |= 33554432;
      if ((paramShell.getStyle() & 0x4000000) != 0)
        paramInt |= 67108864;
    }
    return checkBits(paramInt, 33554432, 67108864, 0, 0, 0, 0);
  }

  public void setPrinterData(PrinterData paramPrinterData)
  {
    if (paramPrinterData == null)
      paramPrinterData = new PrinterData();
    this.printerData = paramPrinterData;
  }

  public PrinterData getPrinterData()
  {
    return this.printerData;
  }

  public int getScope()
  {
    return this.printerData.scope;
  }

  public void setScope(int paramInt)
  {
    this.printerData.scope = paramInt;
  }

  public int getStartPage()
  {
    return this.printerData.startPage;
  }

  public void setStartPage(int paramInt)
  {
    this.printerData.startPage = paramInt;
  }

  public int getEndPage()
  {
    return this.printerData.endPage;
  }

  public void setEndPage(int paramInt)
  {
    this.printerData.endPage = paramInt;
  }

  public boolean getPrintToFile()
  {
    return this.printerData.printToFile;
  }

  public void setPrintToFile(boolean paramBoolean)
  {
    this.printerData.printToFile = paramBoolean;
  }

  protected void checkSubclass()
  {
    String str1 = getClass().getName();
    String str2 = PrintDialog.class.getName();
    if (!str2.equals(str1))
      SWT.error(43);
  }

  public PrinterData open()
  {
    Shell localShell = getParent();
    int i = getStyle();
    int j = localShell.handle;
    int k = localShell.handle;
    boolean bool1 = false;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      int m = i & 0x6000000;
      int n = localShell.getStyle() & 0x6000000;
      if (m != n)
      {
        int i1 = 1048576;
        if (m == 67108864)
          i1 |= 4194304;
        j = OS.CreateWindowEx(i1, DialogClass, null, 0, -2147483648, 0, -2147483648, 0, k, 0, OS.GetModuleHandle(null), null);
        bool1 = OS.IsWindowEnabled(k);
        if (bool1)
          OS.EnableWindow(k, false);
      }
    }
    PrinterData localPrinterData = null;
    PRINTDLG localPRINTDLG = new PRINTDLG();
    localPRINTDLG.lStructSize = PRINTDLG.sizeof;
    localPRINTDLG.hwndOwner = j;
    localPRINTDLG.Flags = 1024;
    if (OS.PrintDlg(localPRINTDLG))
    {
      if (localPRINTDLG.hDevNames != 0)
      {
        OS.GlobalFree(localPRINTDLG.hDevNames);
        localPRINTDLG.hDevNames = 0;
      }
      byte[] arrayOfByte = this.printerData.otherData;
      if ((arrayOfByte != null) && (arrayOfByte.length != 0))
      {
        i2 = OS.GlobalAlloc(64, arrayOfByte.length);
        OS.MoveMemory(i2, arrayOfByte, arrayOfByte.length);
        if (localPRINTDLG.hDevMode != 0)
          OS.GlobalFree(localPRINTDLG.hDevMode);
        localPRINTDLG.hDevMode = i2;
      }
      int i2 = localPRINTDLG.hDevMode;
      int i3 = OS.GlobalLock(i2);
      DEVMODEA localDEVMODEA = OS.IsUnicode ? new DEVMODEW() : new DEVMODEA();
      OS.MoveMemory(localDEVMODEA, i3, OS.IsUnicode ? OS.DEVMODEW_sizeof() : OS.DEVMODEA_sizeof());
      localDEVMODEA.dmFields |= 1;
      localDEVMODEA.dmOrientation = (this.printerData.orientation == 1 ? 1 : 2);
      if (this.printerData.copyCount != 1)
      {
        localDEVMODEA.dmFields |= 256;
        localDEVMODEA.dmCopies = ((short)this.printerData.copyCount);
      }
      if (this.printerData.collate)
      {
        localDEVMODEA.dmFields |= 32768;
        localDEVMODEA.dmCollate = 1;
      }
      OS.MoveMemory(i3, localDEVMODEA, OS.IsUnicode ? OS.DEVMODEW_sizeof() : OS.DEVMODEA_sizeof());
      OS.GlobalUnlock(i2);
      localPRINTDLG.Flags = 262144;
      if (this.printerData.printToFile)
        localPRINTDLG.Flags |= 32;
      switch (this.printerData.scope)
      {
      case 1:
        localPRINTDLG.Flags |= 2;
        break;
      case 2:
        localPRINTDLG.Flags |= 1;
        break;
      default:
        localPRINTDLG.Flags |= 0;
      }
      localPRINTDLG.nMinPage = 1;
      localPRINTDLG.nMaxPage = -1;
      localPRINTDLG.nFromPage = ((short)Math.min(65535, Math.max(1, this.printerData.startPage)));
      localPRINTDLG.nToPage = ((short)Math.min(65535, Math.max(1, this.printerData.endPage)));
      Display localDisplay = localShell.getDisplay();
      Shell[] arrayOfShell = localDisplay.getShells();
      if ((getStyle() & 0x30000) != 0)
        for (int i4 = 0; i4 < arrayOfShell.length; i4++)
          if ((arrayOfShell[i4].isEnabled()) && (arrayOfShell[i4] != localShell))
            arrayOfShell[i4].setEnabled(false);
          else
            arrayOfShell[i4] = null;
      String str1 = "org.eclipse.swt.internal.win32.runMessagesInIdle";
      Object localObject = localDisplay.getData(str1);
      localDisplay.setData(str1, new Boolean(true));
      boolean bool2 = OS.PrintDlg(localPRINTDLG);
      localDisplay.setData(str1, localObject);
      int i5;
      if ((getStyle() & 0x30000) != 0)
        for (i5 = 0; i5 < arrayOfShell.length; i5++)
          if ((arrayOfShell[i5] != null) && (!arrayOfShell[i5].isDisposed()))
            arrayOfShell[i5].setEnabled(true);
      if (bool2)
      {
        i2 = localPRINTDLG.hDevNames;
        i5 = OS.GlobalSize(i2) / TCHAR.sizeof * TCHAR.sizeof;
        i3 = OS.GlobalLock(i2);
        short[] arrayOfShort = new short[4];
        OS.MoveMemory(arrayOfShort, i3, 2 * arrayOfShort.length);
        TCHAR localTCHAR = new TCHAR(0, i5);
        OS.MoveMemory(localTCHAR, i3, i5);
        OS.GlobalUnlock(i2);
        int i6 = arrayOfShort[0];
        for (int i7 = 0; i6 + i7 < i5; i7++)
          if (localTCHAR.tcharAt(i6 + i7) == 0)
            break;
        String str2 = localTCHAR.toString(i6, i7);
        int i8 = arrayOfShort[1];
        for (i7 = 0; i8 + i7 < i5; i7++)
          if (localTCHAR.tcharAt(i8 + i7) == 0)
            break;
        String str3 = localTCHAR.toString(i8, i7);
        int i9 = arrayOfShort[2];
        for (i7 = 0; i9 + i7 < i5; i7++)
          if (localTCHAR.tcharAt(i9 + i7) == 0)
            break;
        String str4 = localTCHAR.toString(i9, i7);
        localPrinterData = new PrinterData(str2, str3);
        if ((localPRINTDLG.Flags & 0x2) != 0)
        {
          localPrinterData.scope = 1;
          localPrinterData.startPage = (localPRINTDLG.nFromPage & 0xFFFF);
          localPrinterData.endPage = (localPRINTDLG.nToPage & 0xFFFF);
        }
        else if ((localPRINTDLG.Flags & 0x1) != 0)
        {
          localPrinterData.scope = 2;
        }
        localPrinterData.printToFile = ((localPRINTDLG.Flags & 0x20) != 0);
        if (localPrinterData.printToFile)
          localPrinterData.fileName = str4;
        localPrinterData.copyCount = localPRINTDLG.nCopies;
        localPrinterData.collate = ((localPRINTDLG.Flags & 0x10) != 0);
        i2 = localPRINTDLG.hDevMode;
        i5 = OS.GlobalSize(i2);
        i3 = OS.GlobalLock(i2);
        localPrinterData.otherData = new byte[i5];
        OS.MoveMemory(localPrinterData.otherData, i3, i5);
        localDEVMODEA = OS.IsUnicode ? new DEVMODEW() : new DEVMODEA();
        OS.MoveMemory(localDEVMODEA, i3, OS.IsUnicode ? OS.DEVMODEW_sizeof() : OS.DEVMODEA_sizeof());
        if ((localDEVMODEA.dmFields & 0x1) != 0)
        {
          int i10 = localDEVMODEA.dmOrientation;
          localPrinterData.orientation = (i10 == 2 ? 2 : 1);
        }
        OS.GlobalUnlock(i2);
        this.printerData = localPrinterData;
      }
    }
    if (localPRINTDLG.hDevNames != 0)
    {
      OS.GlobalFree(localPRINTDLG.hDevNames);
      localPRINTDLG.hDevNames = 0;
    }
    if (localPRINTDLG.hDevMode != 0)
    {
      OS.GlobalFree(localPRINTDLG.hDevMode);
      localPRINTDLG.hDevMode = 0;
    }
    if (k != j)
    {
      if (bool1)
        OS.EnableWindow(k, true);
      OS.SetActiveWindow(k);
      OS.DestroyWindow(j);
    }
    return localPrinterData;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.printing.PrintDialog
 * JD-Core Version:    0.6.2
 */