package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.DIBSECTION;
import org.eclipse.swt.internal.win32.OS;

public class ImageTransfer extends ByteArrayTransfer
{
  private static ImageTransfer _instance = new ImageTransfer();
  private static final String CF_DIB = "CF_DIB";
  private static final int CF_DIBID = 8;

  public static ImageTransfer getInstance()
  {
    return _instance;
  }

  public void javaToNative(Object paramObject, TransferData paramTransferData)
  {
    if ((!checkImage(paramObject)) || (!isSupportedType(paramTransferData)))
      DND.error(2003);
    ImageData localImageData = (ImageData)paramObject;
    if (localImageData == null)
      SWT.error(4);
    int i = localImageData.data.length;
    int j = localImageData.height;
    int k = localImageData.bytesPerLine;
    BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
    localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
    localBITMAPINFOHEADER.biSizeImage = i;
    localBITMAPINFOHEADER.biWidth = localImageData.width;
    localBITMAPINFOHEADER.biHeight = j;
    localBITMAPINFOHEADER.biPlanes = 1;
    localBITMAPINFOHEADER.biBitCount = ((short)localImageData.depth);
    localBITMAPINFOHEADER.biCompression = 0;
    int m = 0;
    if (localBITMAPINFOHEADER.biBitCount <= 8)
      m += (1 << localBITMAPINFOHEADER.biBitCount) * 4;
    byte[] arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof + m];
    OS.MoveMemory(arrayOfByte1, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
    RGB[] arrayOfRGB = localImageData.palette.getRGBs();
    if ((arrayOfRGB != null) && (m > 0))
    {
      n = BITMAPINFOHEADER.sizeof;
      for (i1 = 0; i1 < arrayOfRGB.length; i1++)
      {
        arrayOfByte1[n] = ((byte)arrayOfRGB[i1].blue);
        arrayOfByte1[(n + 1)] = ((byte)arrayOfRGB[i1].green);
        arrayOfByte1[(n + 2)] = ((byte)arrayOfRGB[i1].red);
        arrayOfByte1[(n + 3)] = 0;
        n += 4;
      }
    }
    int n = OS.GlobalAlloc(64, BITMAPINFOHEADER.sizeof + m + i);
    OS.MoveMemory(n, arrayOfByte1, arrayOfByte1.length);
    int i1 = n + BITMAPINFOHEADER.sizeof + m;
    if (j <= 0)
    {
      OS.MoveMemory(i1, localImageData.data, i);
    }
    else
    {
      int i2 = 0;
      i1 += k * (j - 1);
      byte[] arrayOfByte2 = new byte[k];
      for (int i3 = 0; i3 < j; i3++)
      {
        System.arraycopy(localImageData.data, i2, arrayOfByte2, 0, k);
        OS.MoveMemory(i1, arrayOfByte2, k);
        i2 += k;
        i1 -= k;
      }
    }
    paramTransferData.stgmedium = new STGMEDIUM();
    paramTransferData.stgmedium.tymed = 1;
    paramTransferData.stgmedium.unionField = n;
    paramTransferData.stgmedium.pUnkForRelease = 0;
    paramTransferData.result = 0;
  }

  public Object nativeToJava(TransferData paramTransferData)
  {
    if ((!isSupportedType(paramTransferData)) || (paramTransferData.pIDataObject == 0))
      return null;
    IDataObject localIDataObject = new IDataObject(paramTransferData.pIDataObject);
    localIDataObject.AddRef();
    FORMATETC localFORMATETC = new FORMATETC();
    localFORMATETC.cfFormat = 8;
    localFORMATETC.ptd = 0;
    localFORMATETC.dwAspect = 1;
    localFORMATETC.lindex = -1;
    localFORMATETC.tymed = 1;
    STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
    localSTGMEDIUM.tymed = 1;
    paramTransferData.result = getData(localIDataObject, localFORMATETC, localSTGMEDIUM);
    if (paramTransferData.result != 0)
      return null;
    int i = localSTGMEDIUM.unionField;
    localIDataObject.Release();
    try
    {
      int j = OS.GlobalLock(i);
      if (j == 0)
        return null;
      try
      {
        BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
        OS.MoveMemory(localBITMAPINFOHEADER, j, BITMAPINFOHEADER.sizeof);
        int[] arrayOfInt = new int[1];
        int k = OS.CreateDIBSection(0, j, 0, arrayOfInt, 0, 0);
        if (k == 0)
          SWT.error(2);
        int m = j + localBITMAPINFOHEADER.biSize;
        if (localBITMAPINFOHEADER.biBitCount <= 8)
          m += (localBITMAPINFOHEADER.biClrUsed == 0 ? 1 << localBITMAPINFOHEADER.biBitCount : localBITMAPINFOHEADER.biClrUsed) * 4;
        else if (localBITMAPINFOHEADER.biCompression == 3)
          m += 12;
        if (localBITMAPINFOHEADER.biHeight < 0)
        {
          OS.MoveMemory(arrayOfInt[0], m, localBITMAPINFOHEADER.biSizeImage);
        }
        else
        {
          localObject1 = new DIBSECTION();
          OS.GetObject(k, DIBSECTION.sizeof, (DIBSECTION)localObject1);
          int n = ((DIBSECTION)localObject1).biHeight;
          int i1 = ((DIBSECTION)localObject1).biSizeImage / n;
          int i2 = arrayOfInt[0];
          int i3 = m + i1 * (n - 1);
          for (int i4 = 0; i4 < n; i4++)
          {
            OS.MoveMemory(i2, i3, i1);
            i2 += i1;
            i3 -= i1;
          }
        }
        Object localObject1 = Image.win32_new(null, 0, k);
        ImageData localImageData1 = ((Image)localObject1).getImageData();
        OS.DeleteObject(k);
        ((Image)localObject1).dispose();
        ImageData localImageData2 = localImageData1;
        return localImageData2;
      }
      finally
      {
        OS.GlobalUnlock(i);
      }
    }
    finally
    {
      OS.GlobalFree(i);
    }
  }

  protected int[] getTypeIds()
  {
    return new int[] { 8 };
  }

  protected String[] getTypeNames()
  {
    return new String[] { "CF_DIB" };
  }

  boolean checkImage(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof ImageData));
  }

  protected boolean validate(Object paramObject)
  {
    return checkImage(paramObject);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.ImageTransfer
 * JD-Core Version:    0.6.2
 */