package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.OS;

public final class Cursor extends Resource
{
  public int handle;
  boolean isIcon;
  static final byte[] HAND_SOURCE = { -7, -1, -1, -1, -16, -1, -1, -1, -16, -1, -1, -1, -16, -1, -1, -1, -16, 63, -1, -1, -16, 7, -1, -1, -16, 3, -1, -1, -16, 0, -1, -1, 16, 0, 127, -1, 0, 0, 127, -1, -128, 0, 127, -1, -64, 0, 127, -1, -32, 0, 127, -1, -16, 0, 127, -1, -8, 0, -1, -1, -4, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
  static final byte[] HAND_MASK = { 0, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 6, 0, 0, 0, 6, -64, 0, 0, 6, -40, 0, 0, 6, -40, 0, 0, 7, -37, 0, 0, 103, -5, 0, 0, 63, -1, 0, 0, 31, -1, 0, 0, 15, -1, 0, 0, 7, -1, 0, 0, 3, -2 };

  Cursor(Device paramDevice)
  {
    super(paramDevice);
  }

  public Cursor(Device paramDevice, int paramInt)
  {
    super(paramDevice);
    int i = 0;
    switch (paramInt)
    {
    case 21:
      i = 32649;
      break;
    case 0:
      i = 32512;
      break;
    case 1:
      i = 32514;
      break;
    case 2:
      i = 32515;
      break;
    case 3:
      i = 32650;
      break;
    case 4:
      i = 32651;
      break;
    case 5:
      i = 32646;
      break;
    case 6:
      i = 32643;
      break;
    case 7:
      i = 32645;
      break;
    case 8:
      i = 32642;
      break;
    case 9:
      i = 32644;
      break;
    case 10:
      i = 32645;
      break;
    case 11:
      i = 32645;
      break;
    case 12:
      i = 32644;
      break;
    case 13:
      i = 32644;
      break;
    case 14:
      i = 32643;
      break;
    case 15:
      i = 32642;
      break;
    case 16:
      i = 32643;
      break;
    case 17:
      i = 32642;
      break;
    case 18:
      i = 32516;
      break;
    case 19:
      i = 32513;
      break;
    case 20:
      i = 32648;
      break;
    default:
      SWT.error(5);
    }
    this.handle = OS.LoadCursor(0, i);
    if ((this.handle == 0) && (paramInt == 21))
    {
      int j = OS.GetSystemMetrics(13);
      int k = OS.GetSystemMetrics(14);
      if ((j == 32) && (k == 32))
      {
        int m = OS.GetModuleHandle(null);
        if (OS.IsWinCE)
          SWT.error(20);
        this.handle = OS.CreateCursor(m, 5, 0, 32, 32, HAND_SOURCE, HAND_MASK);
      }
    }
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  public Cursor(Device paramDevice, ImageData paramImageData1, ImageData paramImageData2, int paramInt1, int paramInt2)
  {
    super(paramDevice);
    if (paramImageData1 == null)
      SWT.error(4);
    if (paramImageData2 == null)
    {
      if (paramImageData1.getTransparencyType() != 2)
        SWT.error(4);
      paramImageData2 = paramImageData1.getTransparencyMask();
    }
    if ((paramImageData2.width != paramImageData1.width) || (paramImageData2.height != paramImageData1.height))
      SWT.error(5);
    if ((paramInt1 >= paramImageData1.width) || (paramInt1 < 0) || (paramInt2 >= paramImageData1.height) || (paramInt2 < 0))
      SWT.error(5);
    paramImageData2 = ImageData.convertMask(paramImageData2);
    paramImageData1 = ImageData.convertMask(paramImageData1);
    byte[] arrayOfByte1 = ImageData.convertPad(paramImageData1.data, paramImageData1.width, paramImageData1.height, paramImageData1.depth, paramImageData1.scanlinePad, 2);
    byte[] arrayOfByte2 = ImageData.convertPad(paramImageData2.data, paramImageData2.width, paramImageData2.height, paramImageData2.depth, paramImageData2.scanlinePad, 2);
    int i = OS.GetModuleHandle(null);
    if (OS.IsWinCE)
      SWT.error(20);
    this.handle = OS.CreateCursor(i, paramInt1, paramInt2, paramImageData1.width, paramImageData1.height, arrayOfByte1, arrayOfByte2);
    if (this.handle == 0)
      SWT.error(2);
    init();
  }

  public Cursor(Device paramDevice, ImageData paramImageData, int paramInt1, int paramInt2)
  {
    super(paramDevice);
    if (paramImageData == null)
      SWT.error(4);
    if ((paramInt1 >= paramImageData.width) || (paramInt1 < 0) || (paramInt2 >= paramImageData.height) || (paramInt2 < 0))
      SWT.error(5);
    int i = 0;
    int j = 0;
    Object localObject2;
    if ((paramImageData.maskData == null) && (paramImageData.transparentPixel == -1) && ((paramImageData.alpha != -1) || (paramImageData.alphaData != null)))
    {
      localObject1 = paramImageData.palette;
      localObject2 = new PaletteData(65280, 16711680, -16777216);
      ImageData localImageData = new ImageData(paramImageData.width, paramImageData.height, 32, (PaletteData)localObject2);
      if (((PaletteData)localObject1).isDirect)
      {
        ImageData.blit(1, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, paramImageData.getByteOrder(), 0, 0, paramImageData.width, paramImageData.height, ((PaletteData)localObject1).redMask, ((PaletteData)localObject1).greenMask, ((PaletteData)localObject1).blueMask, 255, null, 0, 0, 0, localImageData.data, localImageData.depth, localImageData.bytesPerLine, localImageData.getByteOrder(), 0, 0, localImageData.width, localImageData.height, ((PaletteData)localObject2).redMask, ((PaletteData)localObject2).greenMask, ((PaletteData)localObject2).blueMask, false, false);
      }
      else
      {
        localObject3 = ((PaletteData)localObject1).getRGBs();
        int k = localObject3.length;
        byte[] arrayOfByte2 = new byte[k];
        byte[] arrayOfByte3 = new byte[k];
        byte[] arrayOfByte4 = new byte[k];
        for (int i1 = 0; i1 < localObject3.length; i1++)
        {
          Object localObject4 = localObject3[i1];
          if (localObject4 != null)
          {
            arrayOfByte2[i1] = ((byte)localObject4.red);
            arrayOfByte3[i1] = ((byte)localObject4.green);
            arrayOfByte4[i1] = ((byte)localObject4.blue);
          }
        }
        ImageData.blit(1, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, paramImageData.getByteOrder(), 0, 0, paramImageData.width, paramImageData.height, arrayOfByte2, arrayOfByte3, arrayOfByte4, 255, null, 0, 0, 0, localImageData.data, localImageData.depth, localImageData.bytesPerLine, localImageData.getByteOrder(), 0, 0, localImageData.width, localImageData.height, ((PaletteData)localObject2).redMask, ((PaletteData)localObject2).greenMask, ((PaletteData)localObject2).blueMask, false, false);
      }
      i = Image.createDIB(paramImageData.width, paramImageData.height, 32);
      if (i == 0)
        SWT.error(2);
      Object localObject3 = new BITMAP();
      OS.GetObject(i, BITMAP.sizeof, (BITMAP)localObject3);
      byte[] arrayOfByte1 = localImageData.data;
      int m;
      int n;
      if (paramImageData.alpha != -1)
      {
        m = 3;
        for (n = 0; m < arrayOfByte1.length; n++)
        {
          arrayOfByte1[m] = ((byte)paramImageData.alpha);
          m += 4;
        }
      }
      else if (paramImageData.alphaData != null)
      {
        m = 3;
        for (n = 0; m < arrayOfByte1.length; n++)
        {
          arrayOfByte1[m] = paramImageData.alphaData[n];
          m += 4;
        }
      }
      OS.MoveMemory(((BITMAP)localObject3).bmBits, arrayOfByte1, arrayOfByte1.length);
      j = OS.CreateBitmap(paramImageData.width, paramImageData.height, 1, 1, new byte[((paramImageData.width + 7) / 8 + 3) / 4 * 4 * paramImageData.height]);
      if (j == 0)
        SWT.error(2);
    }
    else
    {
      localObject1 = paramImageData.getTransparencyMask();
      localObject2 = Image.init(this.device, null, paramImageData, (ImageData)localObject1);
      i = localObject2[0];
      j = localObject2[1];
    }
    Object localObject1 = new ICONINFO();
    ((ICONINFO)localObject1).fIcon = false;
    ((ICONINFO)localObject1).hbmColor = i;
    ((ICONINFO)localObject1).hbmMask = j;
    ((ICONINFO)localObject1).xHotspot = paramInt1;
    ((ICONINFO)localObject1).yHotspot = paramInt2;
    this.handle = OS.CreateIconIndirect((ICONINFO)localObject1);
    OS.DeleteObject(i);
    OS.DeleteObject(j);
    if (this.handle == 0)
      SWT.error(2);
    this.isIcon = true;
    init();
  }

  void destroy()
  {
    if (this.isIcon)
      OS.DestroyIcon(this.handle);
    else if (!OS.IsWinCE)
      OS.DestroyCursor(this.handle);
    this.handle = 0;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Cursor))
      return false;
    Cursor localCursor = (Cursor)paramObject;
    return (this.device == localCursor.device) && (this.handle == localCursor.handle);
  }

  public int hashCode()
  {
    return this.handle;
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public String toString()
  {
    if (isDisposed())
      return "Cursor {*DISPOSED*}";
    return "Cursor {" + this.handle + "}";
  }

  public static Cursor win32_new(Device paramDevice, int paramInt)
  {
    Cursor localCursor = new Cursor(paramDevice);
    localCursor.handle = paramInt;
    return localCursor;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Cursor
 * JD-Core Version:    0.6.2
 */