package org.eclipse.swt.graphics;

import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.gdip.BitmapData;
import org.eclipse.swt.internal.gdip.ColorPalette;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.DIBSECTION;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.OS;

public final class Image extends Resource
  implements Drawable
{
  public int type;
  public int handle;
  int transparentPixel = -1;
  int transparentColor = -1;
  GC memGC;
  byte[] alphaData;
  int alpha = -1;
  ImageData data;
  int width = -1;
  int height = -1;
  static final int DEFAULT_SCANLINE_PAD = 4;

  Image(Device paramDevice)
  {
    super(paramDevice);
  }

  public Image(Device paramDevice, int paramInt1, int paramInt2)
  {
    super(paramDevice);
    init(paramInt1, paramInt2);
    init();
  }

  public Image(Device paramDevice, Image paramImage, int paramInt)
  {
    super(paramDevice);
    paramDevice = this.device;
    if (paramImage == null)
      SWT.error(4);
    if (paramImage.isDisposed())
      SWT.error(5);
    Rectangle localRectangle = paramImage.getBounds();
    this.type = paramImage.type;
    Object localObject3;
    ImageData localImageData1;
    PaletteData localPaletteData;
    Object localObject1;
    Object localObject2;
    int[] arrayOfInt2;
    int i4;
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    int i11;
    int i12;
    int i13;
    int i14;
    int i15;
    switch (paramInt)
    {
    case 0:
      switch (this.type)
      {
      case 0:
        int i = paramDevice.internal_new_GC(null);
        int j = OS.CreateCompatibleDC(i);
        int k = OS.CreateCompatibleDC(i);
        int m = OS.SelectObject(j, paramImage.handle);
        localObject3 = new BITMAP();
        OS.GetObject(paramImage.handle, BITMAP.sizeof, (BITMAP)localObject3);
        this.handle = OS.CreateCompatibleBitmap(j, localRectangle.width, ((BITMAP)localObject3).bmBits != 0 ? -localRectangle.height : localRectangle.height);
        if (this.handle == 0)
          SWT.error(2);
        int i1 = OS.SelectObject(k, this.handle);
        OS.BitBlt(k, 0, 0, localRectangle.width, localRectangle.height, j, 0, 0, 13369376);
        OS.SelectObject(j, m);
        OS.SelectObject(k, i1);
        OS.DeleteDC(j);
        OS.DeleteDC(k);
        paramDevice.internal_dispose_GC(i, null);
        this.transparentPixel = paramImage.transparentPixel;
        this.alpha = paramImage.alpha;
        if (paramImage.alphaData != null)
        {
          this.alphaData = new byte[paramImage.alphaData.length];
          System.arraycopy(paramImage.alphaData, 0, this.alphaData, 0, this.alphaData.length);
        }
        break;
      case 1:
        if (OS.IsWinCE)
        {
          init(paramImage.data);
        }
        else
        {
          this.handle = OS.CopyImage(paramImage.handle, 1, localRectangle.width, localRectangle.height, 0);
          if (this.handle == 0)
            SWT.error(2);
        }
        break;
      default:
        SWT.error(40);
      }
      break;
    case 1:
      localImageData1 = paramImage.getImageData();
      localPaletteData = localImageData1.palette;
      localObject1 = new RGB[3];
      localObject1[0] = paramDevice.getSystemColor(2).getRGB();
      localObject1[1] = paramDevice.getSystemColor(18).getRGB();
      localObject1[2] = paramDevice.getSystemColor(22).getRGB();
      localObject2 = new ImageData(localRectangle.width, localRectangle.height, 8, new PaletteData((RGB[])localObject1));
      ((ImageData)localObject2).alpha = localImageData1.alpha;
      ((ImageData)localObject2).alphaData = localImageData1.alphaData;
      ((ImageData)localObject2).maskData = localImageData1.maskData;
      ((ImageData)localObject2).maskPad = localImageData1.maskPad;
      if (localImageData1.transparentPixel != -1)
        ((ImageData)localObject2).transparentPixel = 0;
      localObject3 = new int[localRectangle.width];
      arrayOfInt2 = (int[])null;
      ImageData localImageData2 = null;
      if (localImageData1.maskData != null)
        localImageData2 = localImageData1.getTransparencyMask();
      if (localImageData2 != null)
        arrayOfInt2 = new int[localRectangle.width];
      i4 = localPaletteData.redMask;
      i5 = localPaletteData.greenMask;
      i6 = localPaletteData.blueMask;
      i7 = localPaletteData.redShift;
      i8 = localPaletteData.greenShift;
      i9 = localPaletteData.blueShift;
      for (i10 = 0; i10 < localRectangle.height; i10++)
      {
        i11 = i10 * ((ImageData)localObject2).bytesPerLine;
        localImageData1.getPixels(0, i10, localRectangle.width, (int[])localObject3, 0);
        if (localImageData2 != null)
          localImageData2.getPixels(0, i10, localRectangle.width, arrayOfInt2, 0);
        for (i12 = 0; i12 < localRectangle.width; i12++)
        {
          i13 = localObject3[i12];
          if (((localImageData1.transparentPixel == -1) || (i13 != localImageData1.transparentPixel)) && ((localImageData2 == null) || (arrayOfInt2[i12] != 0)))
          {
            int i16;
            if (localPaletteData.isDirect)
            {
              i14 = i13 & i4;
              i14 = i7 < 0 ? i14 >>> -i7 : i14 << i7;
              i15 = i13 & i5;
              i15 = i8 < 0 ? i15 >>> -i8 : i15 << i8;
              i16 = i13 & i6;
              i16 = i9 < 0 ? i16 >>> -i9 : i16 << i9;
            }
            else
            {
              i14 = localPaletteData.colors[i13].red;
              i15 = localPaletteData.colors[i13].green;
              i16 = localPaletteData.colors[i13].blue;
            }
            int i17 = i14 * i14 + i15 * i15 + i16 * i16;
            if (i17 < 98304)
              ((ImageData)localObject2).data[i11] = 1;
            else
              ((ImageData)localObject2).data[i11] = 2;
          }
          i11++;
        }
      }
      init((ImageData)localObject2);
      break;
    case 2:
      localImageData1 = paramImage.getImageData();
      localPaletteData = localImageData1.palette;
      localObject1 = localImageData1;
      int n;
      int i3;
      if (!localPaletteData.isDirect)
      {
        localObject2 = localPaletteData.getRGBs();
        for (n = 0; n < localObject2.length; n++)
          if (localImageData1.transparentPixel != n)
          {
            arrayOfInt2 = localObject2[n];
            i3 = arrayOfInt2.red;
            i4 = arrayOfInt2.green;
            i5 = arrayOfInt2.blue;
            i6 = i3 + i3 + i4 + i4 + i4 + i4 + i4 + i5 >> 3;
            arrayOfInt2.red = (arrayOfInt2.green = arrayOfInt2.blue = i6);
          }
        ((ImageData)localObject1).palette = new PaletteData((RGB[])localObject2);
      }
      else
      {
        localObject2 = new RGB[256];
        for (n = 0; n < localObject2.length; n++)
          localObject2[n] = new RGB(n, n, n);
        localObject1 = new ImageData(localRectangle.width, localRectangle.height, 8, new PaletteData((RGB[])localObject2));
        ((ImageData)localObject1).alpha = localImageData1.alpha;
        ((ImageData)localObject1).alphaData = localImageData1.alphaData;
        ((ImageData)localObject1).maskData = localImageData1.maskData;
        ((ImageData)localObject1).maskPad = localImageData1.maskPad;
        if (localImageData1.transparentPixel != -1)
          ((ImageData)localObject1).transparentPixel = 254;
        int[] arrayOfInt1 = new int[localRectangle.width];
        int i2 = localPaletteData.redMask;
        i3 = localPaletteData.greenMask;
        i4 = localPaletteData.blueMask;
        i5 = localPaletteData.redShift;
        i6 = localPaletteData.greenShift;
        i7 = localPaletteData.blueShift;
        for (i8 = 0; i8 < localRectangle.height; i8++)
        {
          i9 = i8 * ((ImageData)localObject1).bytesPerLine;
          localImageData1.getPixels(0, i8, localRectangle.width, arrayOfInt1, 0);
          for (i10 = 0; i10 < localRectangle.width; i10++)
          {
            i11 = arrayOfInt1[i10];
            if (i11 != localImageData1.transparentPixel)
            {
              i12 = i11 & i2;
              i12 = i5 < 0 ? i12 >>> -i5 : i12 << i5;
              i13 = i11 & i3;
              i13 = i6 < 0 ? i13 >>> -i6 : i13 << i6;
              i14 = i11 & i4;
              i14 = i7 < 0 ? i14 >>> -i7 : i14 << i7;
              i15 = i12 + i12 + i13 + i13 + i13 + i13 + i13 + i14 >> 3;
              if (((ImageData)localObject1).transparentPixel == i15)
                i15 = 255;
              ((ImageData)localObject1).data[i9] = ((byte)i15);
            }
            else
            {
              ((ImageData)localObject1).data[i9] = -2;
            }
            i9++;
          }
        }
      }
      init((ImageData)localObject1);
      break;
    default:
      SWT.error(5);
    }
    init();
  }

  public Image(Device paramDevice, Rectangle paramRectangle)
  {
    super(paramDevice);
    if (paramRectangle == null)
      SWT.error(4);
    init(paramRectangle.width, paramRectangle.height);
    init();
  }

  public Image(Device paramDevice, ImageData paramImageData)
  {
    super(paramDevice);
    init(paramImageData);
    init();
  }

  public Image(Device paramDevice, ImageData paramImageData1, ImageData paramImageData2)
  {
    super(paramDevice);
    if (paramImageData1 == null)
      SWT.error(4);
    if (paramImageData2 == null)
      SWT.error(4);
    if ((paramImageData1.width != paramImageData2.width) || (paramImageData1.height != paramImageData2.height))
      SWT.error(5);
    paramImageData2 = ImageData.convertMask(paramImageData2);
    init(this.device, this, paramImageData1, paramImageData2);
    init();
  }

  public Image(Device paramDevice, InputStream paramInputStream)
  {
    super(paramDevice);
    init(new ImageData(paramInputStream));
    init();
  }

  public Image(Device paramDevice, String paramString)
  {
    super(paramDevice);
    if (paramString == null)
      SWT.error(4);
    initNative(paramString);
    if (this.handle == 0)
      init(new ImageData(paramString));
    init();
  }

  void initNative(String paramString)
  {
    int i = 1;
    try
    {
      this.device.checkGDIP();
    }
    catch (SWTException localSWTException)
    {
      i = 0;
    }
    if ((i != 0) && (OS.PTR_SIZEOF == 8) && (paramString.toLowerCase().endsWith(".gif")))
      i = 0;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 1)) && (paramString.toLowerCase().endsWith(".gif")))
      i = 0;
    if (i != 0)
    {
      int j = paramString.length();
      char[] arrayOfChar = new char[j + 1];
      paramString.getChars(0, j, arrayOfChar, 0);
      int k = Gdip.Bitmap_new(arrayOfChar, false);
      if (k != 0)
      {
        int m = 2;
        int n = Gdip.Image_GetLastStatus(k);
        if (n == 0)
          if (paramString.toLowerCase().endsWith(".ico"))
          {
            this.type = 1;
            int[] arrayOfInt1 = new int[1];
            n = Gdip.Bitmap_GetHICON(k, arrayOfInt1);
            this.handle = arrayOfInt1[0];
          }
          else
          {
            this.type = 0;
            int i1 = Gdip.Image_GetWidth(k);
            int i2 = Gdip.Image_GetHeight(k);
            int i3 = Gdip.Image_GetPixelFormat(k);
            switch (i3)
            {
            case 135173:
            case 135174:
              this.handle = createDIB(i1, i2, 16);
              break;
            case 137224:
              this.handle = createDIB(i1, i2, 24);
              break;
            case 139273:
            case 925707:
            case 1052676:
            case 1060876:
            case 1851406:
            case 3424269:
              this.handle = createDIB(i1, i2, 32);
            }
            int i4;
            int i6;
            int i7;
            if (this.handle != 0)
            {
              i4 = this.device.internal_new_GC(null);
              int i5 = OS.CreateCompatibleDC(i4);
              i6 = OS.SelectObject(i5, this.handle);
              i7 = Gdip.Graphics_new(i5);
              if (i7 != 0)
              {
                Rect localRect = new Rect();
                localRect.Width = i1;
                localRect.Height = i2;
                n = Gdip.Graphics_DrawImage(i7, k, localRect, 0, 0, i1, i2, 2, 0, 0, 0);
                if (n != 0)
                {
                  m = 40;
                  OS.DeleteObject(this.handle);
                  this.handle = 0;
                }
                Gdip.Graphics_delete(i7);
              }
              OS.SelectObject(i5, i6);
              OS.DeleteDC(i5);
              this.device.internal_dispose_GC(i4, null);
            }
            else
            {
              i4 = Gdip.BitmapData_new();
              if (i4 != 0)
              {
                n = Gdip.Bitmap_LockBits(k, 0, 0, i3, i4);
                if (n == 0)
                {
                  BitmapData localBitmapData = new BitmapData();
                  Gdip.MoveMemory(localBitmapData, i4);
                  i6 = localBitmapData.Stride;
                  i7 = localBitmapData.Scan0;
                  int i8 = 0;
                  int i9 = 4;
                  int i10 = -1;
                  switch (localBitmapData.PixelFormat)
                  {
                  case 196865:
                    i8 = 1;
                    break;
                  case 197634:
                    i8 = 4;
                    break;
                  case 198659:
                    i8 = 8;
                    break;
                  case 135173:
                  case 135174:
                  case 397319:
                    i8 = 16;
                    break;
                  case 137224:
                    i8 = 24;
                    break;
                  case 139273:
                  case 2498570:
                    i8 = 32;
                  }
                  if (i8 != 0)
                  {
                    PaletteData localPaletteData = null;
                    int i13;
                    switch (localBitmapData.PixelFormat)
                    {
                    case 196865:
                    case 197634:
                    case 198659:
                      int i11 = Gdip.Image_GetPaletteSize(k);
                      int i12 = OS.GetProcessHeap();
                      i13 = OS.HeapAlloc(i12, 8, i11);
                      if (i13 == 0)
                        SWT.error(2);
                      Gdip.Image_GetPalette(k, i13, i11);
                      ColorPalette localColorPalette = new ColorPalette();
                      Gdip.MoveMemory(localColorPalette, i13, ColorPalette.sizeof);
                      int[] arrayOfInt2 = new int[localColorPalette.Count];
                      OS.MoveMemory(arrayOfInt2, i13 + 8, arrayOfInt2.length * 4);
                      OS.HeapFree(i12, 0, i13);
                      RGB[] arrayOfRGB = new RGB[localColorPalette.Count];
                      localPaletteData = new PaletteData(arrayOfRGB);
                      for (int i15 = 0; i15 < arrayOfInt2.length; i15++)
                      {
                        if (((arrayOfInt2[i15] >> 24 & 0xFF) == 0) && ((localColorPalette.Flags & 0x1) != 0))
                          i10 = i15;
                        arrayOfRGB[i15] = new RGB((arrayOfInt2[i15] & 0xFF0000) >> 16, (arrayOfInt2[i15] & 0xFF00) >> 8, (arrayOfInt2[i15] & 0xFF) >> 0);
                      }
                      break;
                    case 135173:
                    case 397319:
                      localPaletteData = new PaletteData(31744, 992, 31);
                      break;
                    case 135174:
                      localPaletteData = new PaletteData(63488, 2016, 31);
                      break;
                    case 137224:
                      localPaletteData = new PaletteData(255, 65280, 16711680);
                      break;
                    case 139273:
                    case 2498570:
                      localPaletteData = new PaletteData(65280, 16711680, -16777216);
                    }
                    byte[] arrayOfByte1 = new byte[i6 * i2];
                    byte[] arrayOfByte2 = (byte[])null;
                    OS.MoveMemory(arrayOfByte1, i7, arrayOfByte1.length);
                    int i14;
                    switch (localBitmapData.PixelFormat)
                    {
                    case 397319:
                      arrayOfByte2 = new byte[i1 * i2];
                      i13 = 1;
                      for (i14 = 0; i13 < arrayOfByte1.length; i14++)
                      {
                        arrayOfByte2[i14] = ((byte)((arrayOfByte1[i13] & 0x80) != 0 ? 'ÿ' : 0));
                        i13 += 2;
                      }
                      break;
                    case 2498570:
                      arrayOfByte2 = new byte[i1 * i2];
                      i13 = 3;
                      for (i14 = 0; i13 < arrayOfByte1.length; i14++)
                      {
                        arrayOfByte2[i14] = arrayOfByte1[i13];
                        i13 += 4;
                      }
                    }
                    ImageData localImageData = new ImageData(i1, i2, i8, localPaletteData, i9, arrayOfByte1);
                    localImageData.transparentPixel = i10;
                    localImageData.alphaData = arrayOfByte2;
                    init(localImageData);
                  }
                  Gdip.Bitmap_UnlockBits(k, i4);
                }
                else
                {
                  m = 40;
                }
                Gdip.BitmapData_delete(i4);
              }
            }
          }
        Gdip.Bitmap_delete(k);
        if ((n == 0) && (this.handle == 0))
          SWT.error(m);
      }
    }
  }

  int createDIBFromDDB(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = OS.GetDeviceCaps(paramInt1, 12);
    int j = OS.GetDeviceCaps(paramInt1, 14);
    int k = i * j;
    int m = k > 8 ? 1 : 0;
    RGB[] arrayOfRGB = (RGB[])null;
    if (m == 0)
    {
      n = 1 << k;
      localObject = new byte[4 * n];
      OS.GetPaletteEntries(this.device.hPalette, 0, n, (byte[])localObject);
      arrayOfRGB = new RGB[n];
      for (int i1 = 0; i1 < n; i1++)
        arrayOfRGB[i1] = new RGB(localObject[i1] & 0xFF, localObject[(i1 + 1)] & 0xFF, localObject[(i1 + 2)] & 0xFF);
    }
    int n = (OS.IsWinCE) && ((k == 16) || (k == 32)) ? 1 : 0;
    Object localObject = new BITMAPINFOHEADER();
    ((BITMAPINFOHEADER)localObject).biSize = BITMAPINFOHEADER.sizeof;
    ((BITMAPINFOHEADER)localObject).biWidth = paramInt3;
    ((BITMAPINFOHEADER)localObject).biHeight = (-paramInt4);
    ((BITMAPINFOHEADER)localObject).biPlanes = 1;
    ((BITMAPINFOHEADER)localObject).biBitCount = ((short)k);
    if (n != 0)
      ((BITMAPINFOHEADER)localObject).biCompression = 3;
    else
      ((BITMAPINFOHEADER)localObject).biCompression = 0;
    byte[] arrayOfByte;
    if (m != 0)
      arrayOfByte = new byte[BITMAPINFOHEADER.sizeof + (n != 0 ? 12 : 0)];
    else
      arrayOfByte = new byte[BITMAPINFOHEADER.sizeof + arrayOfRGB.length * 4];
    OS.MoveMemory(arrayOfByte, (BITMAPINFOHEADER)localObject, BITMAPINFOHEADER.sizeof);
    int i2 = BITMAPINFOHEADER.sizeof;
    int i3;
    if (m != 0)
    {
      if (n != 0)
      {
        i3 = 0;
        i4 = 0;
        i5 = 0;
        switch (k)
        {
        case 16:
          i3 = 31744;
          i4 = 992;
          i5 = 31;
          arrayOfByte[i2] = ((byte)((i3 & 0xFF) >> 0));
          arrayOfByte[(i2 + 1)] = ((byte)((i3 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 2)] = ((byte)((i3 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 3)] = ((byte)((i3 & 0xFF000000) >> 24));
          arrayOfByte[(i2 + 4)] = ((byte)((i4 & 0xFF) >> 0));
          arrayOfByte[(i2 + 5)] = ((byte)((i4 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 6)] = ((byte)((i4 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 7)] = ((byte)((i4 & 0xFF000000) >> 24));
          arrayOfByte[(i2 + 8)] = ((byte)((i5 & 0xFF) >> 0));
          arrayOfByte[(i2 + 9)] = ((byte)((i5 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 10)] = ((byte)((i5 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 11)] = ((byte)((i5 & 0xFF000000) >> 24));
          break;
        case 32:
          i3 = 65280;
          i4 = 16711680;
          i5 = -16777216;
          arrayOfByte[i2] = ((byte)((i3 & 0xFF000000) >> 24));
          arrayOfByte[(i2 + 1)] = ((byte)((i3 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 2)] = ((byte)((i3 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 3)] = ((byte)((i3 & 0xFF) >> 0));
          arrayOfByte[(i2 + 4)] = ((byte)((i4 & 0xFF000000) >> 24));
          arrayOfByte[(i2 + 5)] = ((byte)((i4 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 6)] = ((byte)((i4 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 7)] = ((byte)((i4 & 0xFF) >> 0));
          arrayOfByte[(i2 + 8)] = ((byte)((i5 & 0xFF000000) >> 24));
          arrayOfByte[(i2 + 9)] = ((byte)((i5 & 0xFF0000) >> 16));
          arrayOfByte[(i2 + 10)] = ((byte)((i5 & 0xFF00) >> 8));
          arrayOfByte[(i2 + 11)] = ((byte)((i5 & 0xFF) >> 0));
          break;
        default:
          SWT.error(38);
          break;
        }
      }
    }
    else
      for (i3 = 0; i3 < arrayOfRGB.length; i3++)
      {
        arrayOfByte[i2] = ((byte)arrayOfRGB[i3].blue);
        arrayOfByte[(i2 + 1)] = ((byte)arrayOfRGB[i3].green);
        arrayOfByte[(i2 + 2)] = ((byte)arrayOfRGB[i3].red);
        arrayOfByte[(i2 + 3)] = 0;
        i2 += 4;
      }
    int[] arrayOfInt = new int[1];
    int i4 = OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
    if (i4 == 0)
      SWT.error(2);
    int i5 = OS.CreateCompatibleDC(paramInt1);
    int i6 = OS.CreateCompatibleDC(paramInt1);
    int i7 = OS.SelectObject(i5, paramInt2);
    int i8 = OS.SelectObject(i6, i4);
    OS.BitBlt(i6, 0, 0, paramInt3, paramInt4, i5, 0, 0, 13369376);
    OS.SelectObject(i5, i7);
    OS.SelectObject(i6, i8);
    OS.DeleteDC(i5);
    OS.DeleteDC(i6);
    return i4;
  }

  int[] createGdipImage()
  {
    Object localObject;
    int i;
    int k;
    int m;
    int n;
    int i1;
    int i2;
    int i3;
    int i5;
    int i6;
    int i7;
    int i10;
    int i12;
    byte[] arrayOfByte2;
    int i11;
    switch (this.type)
    {
    case 0:
      if ((this.alpha != -1) || (this.alphaData != null) || (this.transparentPixel != -1))
      {
        localObject = new BITMAP();
        OS.GetObject(this.handle, BITMAP.sizeof, (BITMAP)localObject);
        i = ((BITMAP)localObject).bmWidth;
        int j = ((BITMAP)localObject).bmHeight;
        k = this.device.internal_new_GC(null);
        m = OS.CreateCompatibleDC(k);
        n = OS.SelectObject(m, this.handle);
        i1 = OS.CreateCompatibleDC(k);
        i2 = createDIB(i, j, 32);
        if (i2 == 0)
          SWT.error(2);
        i3 = OS.SelectObject(i1, i2);
        BITMAP localBITMAP2 = new BITMAP();
        OS.GetObject(i2, BITMAP.sizeof, localBITMAP2);
        i5 = localBITMAP2.bmWidthBytes * localBITMAP2.bmHeight;
        OS.BitBlt(i1, 0, 0, i, j, m, 0, 0, 13369376);
        i6 = 0;
        i7 = 0;
        int i8 = 0;
        int i13;
        if (this.transparentPixel != -1)
          if (((BITMAP)localObject).bmBitsPixel <= 8)
          {
            byte[] arrayOfByte1 = new byte[4];
            OS.GetDIBColorTable(m, this.transparentPixel, 1, arrayOfByte1);
            i8 = arrayOfByte1[0];
            i7 = arrayOfByte1[1];
            i6 = arrayOfByte1[2];
          }
          else
          {
            switch (((BITMAP)localObject).bmBitsPixel)
            {
            case 16:
              int i9 = 31;
              i10 = ImageData.getChannelShift(i9);
              byte[] arrayOfByte3 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(i9, i10)];
              i8 = arrayOfByte3[((this.transparentPixel & i9) >> i10)];
              i12 = 992;
              i13 = ImageData.getChannelShift(i12);
              byte[] arrayOfByte4 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(i12, i13)];
              i7 = arrayOfByte4[((this.transparentPixel & i12) >> i13)];
              int i14 = 31744;
              int i15 = ImageData.getChannelShift(i14);
              byte[] arrayOfByte5 = ImageData.ANY_TO_EIGHT[ImageData.getChannelWidth(i14, i15)];
              i6 = arrayOfByte5[((this.transparentPixel & i14) >> i15)];
              break;
            case 24:
              i8 = (byte)((this.transparentPixel & 0xFF0000) >> 16);
              i7 = (byte)((this.transparentPixel & 0xFF00) >> 8);
              i6 = (byte)(this.transparentPixel & 0xFF);
              break;
            case 32:
              i8 = (byte)((this.transparentPixel & 0xFF000000) >>> 24);
              i7 = (byte)((this.transparentPixel & 0xFF0000) >> 16);
              i6 = (byte)((this.transparentPixel & 0xFF00) >> 8);
            }
          }
        OS.SelectObject(m, n);
        OS.SelectObject(i1, i3);
        OS.DeleteObject(m);
        OS.DeleteObject(i1);
        arrayOfByte2 = new byte[i5];
        OS.MoveMemory(arrayOfByte2, localBITMAP2.bmBits, i5);
        OS.DeleteObject(i2);
        this.device.internal_dispose_GC(k, null);
        if (this.alpha != -1)
        {
          i10 = 0;
          i11 = 0;
          while (i10 < j)
          {
            for (i12 = 0; i12 < i; i12++)
            {
              arrayOfByte2[(i11 + 3)] = ((byte)this.alpha);
              i11 += 4;
            }
            i10++;
          }
        }
        else if (this.alphaData != null)
        {
          i10 = 0;
          i11 = 0;
          i12 = 0;
          while (i10 < j)
          {
            for (i13 = 0; i13 < i; i13++)
            {
              arrayOfByte2[(i11 + 3)] = this.alphaData[(i12++)];
              i11 += 4;
            }
            i10++;
          }
        }
        else if (this.transparentPixel != -1)
        {
          i10 = 0;
          i11 = 0;
          while (i10 < j)
          {
            for (i12 = 0; i12 < i; i12++)
            {
              if ((arrayOfByte2[i11] == i8) && (arrayOfByte2[(i11 + 1)] == i7) && (arrayOfByte2[(i11 + 2)] == i6))
                arrayOfByte2[(i11 + 3)] = 0;
              else
                arrayOfByte2[(i11 + 3)] = -1;
              i11 += 4;
            }
            i10++;
          }
        }
        i10 = OS.GetProcessHeap();
        i11 = OS.HeapAlloc(i10, 8, arrayOfByte2.length);
        if (i11 == 0)
          SWT.error(2);
        OS.MoveMemory(i11, arrayOfByte2, i5);
        return new int[] { Gdip.Bitmap_new(i, j, localBITMAP2.bmWidthBytes, 2498570, i11), i11 };
      }
      return new int[] { Gdip.Bitmap_new(this.handle, 0) };
    case 1:
      localObject = new ICONINFO();
      if (OS.IsWinCE)
        GetIconInfo(this, (ICONINFO)localObject);
      else
        OS.GetIconInfo(this.handle, (ICONINFO)localObject);
      i = ((ICONINFO)localObject).hbmColor;
      if (i == 0)
        i = ((ICONINFO)localObject).hbmMask;
      BITMAP localBITMAP1 = new BITMAP();
      OS.GetObject(i, BITMAP.sizeof, localBITMAP1);
      k = localBITMAP1.bmWidth;
      m = i == ((ICONINFO)localObject).hbmMask ? localBITMAP1.bmHeight / 2 : localBITMAP1.bmHeight;
      n = 0;
      i1 = 0;
      if ((k > m) || (localBITMAP1.bmBitsPixel == 32))
      {
        i2 = this.device.internal_new_GC(null);
        i3 = OS.CreateCompatibleDC(i2);
        int i4 = OS.SelectObject(i3, i);
        i5 = OS.CreateCompatibleDC(i2);
        i6 = createDIB(k, m, 32);
        if (i6 == 0)
          SWT.error(2);
        i7 = OS.SelectObject(i5, i6);
        BITMAP localBITMAP3 = new BITMAP();
        OS.GetObject(i6, BITMAP.sizeof, localBITMAP3);
        OS.BitBlt(i5, 0, 0, k, m, i3, 0, i == ((ICONINFO)localObject).hbmMask ? m : 0, 13369376);
        OS.SelectObject(i5, i7);
        OS.DeleteObject(i5);
        arrayOfByte2 = new byte[localBITMAP3.bmWidthBytes * localBITMAP3.bmHeight];
        OS.MoveMemory(arrayOfByte2, localBITMAP3.bmBits, arrayOfByte2.length);
        OS.DeleteObject(i6);
        OS.SelectObject(i3, ((ICONINFO)localObject).hbmMask);
        i10 = 0;
        i11 = 3;
        while (i10 < m)
        {
          for (i12 = 0; i12 < k; i12++)
          {
            if (arrayOfByte2[i11] == 0)
              if (OS.GetPixel(i3, i12, i10) != 0)
                arrayOfByte2[i11] = 0;
              else
                arrayOfByte2[i11] = -1;
            i11 += 4;
          }
          i10++;
        }
        OS.SelectObject(i3, i4);
        OS.DeleteObject(i3);
        this.device.internal_dispose_GC(i2, null);
        i10 = OS.GetProcessHeap();
        i1 = OS.HeapAlloc(i10, 8, arrayOfByte2.length);
        if (i1 == 0)
          SWT.error(2);
        OS.MoveMemory(i1, arrayOfByte2, arrayOfByte2.length);
        n = Gdip.Bitmap_new(k, m, localBITMAP3.bmWidthBytes, 2498570, i1);
      }
      else
      {
        n = Gdip.Bitmap_new(this.handle);
      }
      if (((ICONINFO)localObject).hbmColor != 0)
        OS.DeleteObject(((ICONINFO)localObject).hbmColor);
      if (((ICONINFO)localObject).hbmMask != 0)
        OS.DeleteObject(((ICONINFO)localObject).hbmMask);
      return new int[] { n, i1 };
    }
    SWT.error(40);
    return null;
  }

  void destroy()
  {
    if (this.memGC != null)
      this.memGC.dispose();
    if (this.type == 1)
    {
      if (OS.IsWinCE)
        this.data = null;
      OS.DestroyIcon(this.handle);
    }
    else
    {
      OS.DeleteObject(this.handle);
    }
    this.handle = 0;
    this.memGC = null;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Image))
      return false;
    Image localImage = (Image)paramObject;
    return (this.device == localImage.device) && (this.handle == localImage.handle);
  }

  public Color getBackground()
  {
    if (isDisposed())
      SWT.error(44);
    if (this.transparentPixel == -1)
      return null;
    int i = this.device.internal_new_GC(null);
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(this.handle, BITMAP.sizeof, localBITMAP);
    int j = OS.CreateCompatibleDC(i);
    int k = OS.SelectObject(j, this.handle);
    int m = 0;
    int n = 0;
    int i1 = 0;
    if (localBITMAP.bmBitsPixel <= 8)
    {
      byte[] arrayOfByte;
      if (OS.IsWinCE)
      {
        arrayOfByte = new byte[1];
        OS.MoveMemory(arrayOfByte, localBITMAP.bmBits, 1);
        int i2 = arrayOfByte[0];
        int i3 = 255 << 8 - localBITMAP.bmBitsPixel & 0xFF;
        arrayOfByte[0] = ((byte)(this.transparentPixel << 8 - localBITMAP.bmBitsPixel | arrayOfByte[0] & (i3 ^ 0xFFFFFFFF)));
        OS.MoveMemory(localBITMAP.bmBits, arrayOfByte, 1);
        int i4 = OS.GetPixel(j, 0, 0);
        arrayOfByte[0] = i2;
        OS.MoveMemory(localBITMAP.bmBits, arrayOfByte, 1);
        i1 = (i4 & 0xFF0000) >> 16;
        n = (i4 & 0xFF00) >> 8;
        m = i4 & 0xFF;
      }
      else
      {
        arrayOfByte = new byte[4];
        OS.GetDIBColorTable(j, this.transparentPixel, 1, arrayOfByte);
        i1 = arrayOfByte[0] & 0xFF;
        n = arrayOfByte[1] & 0xFF;
        m = arrayOfByte[2] & 0xFF;
      }
    }
    else
    {
      switch (localBITMAP.bmBitsPixel)
      {
      case 16:
        i1 = (this.transparentPixel & 0x1F) << 3;
        n = (this.transparentPixel & 0x3E0) >> 2;
        m = (this.transparentPixel & 0x7C00) >> 7;
        break;
      case 24:
        i1 = (this.transparentPixel & 0xFF0000) >> 16;
        n = (this.transparentPixel & 0xFF00) >> 8;
        m = this.transparentPixel & 0xFF;
        break;
      case 32:
        i1 = (this.transparentPixel & 0xFF000000) >>> 24;
        n = (this.transparentPixel & 0xFF0000) >> 16;
        m = (this.transparentPixel & 0xFF00) >> 8;
        break;
      default:
        return null;
      }
    }
    OS.SelectObject(j, k);
    OS.DeleteDC(j);
    this.device.internal_dispose_GC(i, null);
    return Color.win32_new(this.device, i1 << 16 | n << 8 | m);
  }

  public Rectangle getBounds()
  {
    if (isDisposed())
      SWT.error(44);
    if ((this.width != -1) && (this.height != -1))
      return new Rectangle(0, 0, this.width, this.height);
    BITMAP localBITMAP;
    switch (this.type)
    {
    case 0:
      localBITMAP = new BITMAP();
      OS.GetObject(this.handle, BITMAP.sizeof, localBITMAP);
      return new Rectangle(0, 0, this.width = localBITMAP.bmWidth, this.height = localBITMAP.bmHeight);
    case 1:
      if (OS.IsWinCE)
        return new Rectangle(0, 0, this.width = this.data.width, this.height = this.data.height);
      ICONINFO localICONINFO = new ICONINFO();
      OS.GetIconInfo(this.handle, localICONINFO);
      int i = localICONINFO.hbmColor;
      if (i == 0)
        i = localICONINFO.hbmMask;
      localBITMAP = new BITMAP();
      OS.GetObject(i, BITMAP.sizeof, localBITMAP);
      if (i == localICONINFO.hbmMask)
        localBITMAP.bmHeight /= 2;
      if (localICONINFO.hbmColor != 0)
        OS.DeleteObject(localICONINFO.hbmColor);
      if (localICONINFO.hbmMask != 0)
        OS.DeleteObject(localICONINFO.hbmMask);
      return new Rectangle(0, 0, this.width = localBITMAP.bmWidth, this.height = localBITMAP.bmHeight);
    }
    SWT.error(40);
    return null;
  }

  public ImageData getImageData()
  {
    if (isDisposed())
      SWT.error(44);
    int n;
    BITMAP localBITMAP;
    int i;
    int j;
    int k;
    int i1;
    Object localObject1;
    int i6;
    int i7;
    int i8;
    int i11;
    int i17;
    int i19;
    int i20;
    switch (this.type)
    {
    case 1:
      if (OS.IsWinCE)
        return this.data;
      ICONINFO localICONINFO = new ICONINFO();
      if (OS.IsWinCE)
        SWT.error(20);
      OS.GetIconInfo(this.handle, localICONINFO);
      n = localICONINFO.hbmColor;
      if (n == 0)
        n = localICONINFO.hbmMask;
      localBITMAP = new BITMAP();
      OS.GetObject(n, BITMAP.sizeof, localBITMAP);
      i = localBITMAP.bmPlanes * localBITMAP.bmBitsPixel;
      j = localBITMAP.bmWidth;
      if (n == localICONINFO.hbmMask)
        localBITMAP.bmHeight /= 2;
      k = localBITMAP.bmHeight;
      i1 = 0;
      if (i <= 8)
        i1 = 1 << i;
      BITMAPINFOHEADER localBITMAPINFOHEADER1 = new BITMAPINFOHEADER();
      localBITMAPINFOHEADER1.biSize = BITMAPINFOHEADER.sizeof;
      localBITMAPINFOHEADER1.biWidth = j;
      localBITMAPINFOHEADER1.biHeight = (-k);
      localBITMAPINFOHEADER1.biPlanes = 1;
      localBITMAPINFOHEADER1.biBitCount = ((short)i);
      localBITMAPINFOHEADER1.biCompression = 0;
      localObject1 = new byte[BITMAPINFOHEADER.sizeof + i1 * 4];
      OS.MoveMemory((byte[])localObject1, localBITMAPINFOHEADER1, BITMAPINFOHEADER.sizeof);
      int i4 = this.device.internal_new_GC(null);
      int i5 = OS.CreateCompatibleDC(i4);
      i6 = OS.SelectObject(i5, n);
      i7 = 0;
      if (i <= 8)
      {
        i8 = this.device.hPalette;
        if (i8 != 0)
        {
          i7 = OS.SelectPalette(i5, i8, false);
          OS.RealizePalette(i5);
        }
      }
      if (OS.IsWinCE)
        SWT.error(20);
      OS.GetDIBits(i5, n, 0, k, 0, (byte[])localObject1, 0);
      OS.MoveMemory(localBITMAPINFOHEADER1, (byte[])localObject1, BITMAPINFOHEADER.sizeof);
      i8 = localBITMAPINFOHEADER1.biSizeImage;
      byte[] arrayOfByte2 = new byte[i8];
      int i10 = OS.GetProcessHeap();
      i11 = OS.HeapAlloc(i10, 8, i8);
      if (i11 == 0)
        SWT.error(2);
      if (OS.IsWinCE)
        SWT.error(20);
      OS.GetDIBits(i5, n, 0, k, i11, (byte[])localObject1, 0);
      OS.MoveMemory(arrayOfByte2, i11, i8);
      PaletteData localPaletteData2 = null;
      int i15;
      if (i <= 8)
      {
        localObject3 = new RGB[i1];
        i15 = 40;
        for (i17 = 0; i17 < i1; i17++)
        {
          localObject3[i17] = new RGB(localObject1[(i15 + 2)] & 0xFF, localObject1[(i15 + 1)] & 0xFF, localObject1[i15] & 0xFF);
          i15 += 4;
        }
        localPaletteData2 = new PaletteData((RGB[])localObject3);
      }
      else if (i == 16)
      {
        localPaletteData2 = new PaletteData(31744, 992, 31);
      }
      else if (i == 24)
      {
        localPaletteData2 = new PaletteData(255, 65280, 16711680);
      }
      else if (i == 32)
      {
        localPaletteData2 = new PaletteData(65280, 16711680, -16777216);
      }
      else
      {
        SWT.error(38);
      }
      Object localObject3 = (byte[])null;
      if (localICONINFO.hbmColor == 0)
      {
        localObject3 = new byte[i8];
        if (OS.IsWinCE)
          SWT.error(20);
        OS.GetDIBits(i5, n, k, k, i11, (byte[])localObject1, 0);
        OS.MoveMemory((byte[])localObject3, i11, i8);
      }
      else
      {
        localBITMAPINFOHEADER1 = new BITMAPINFOHEADER();
        localBITMAPINFOHEADER1.biSize = BITMAPINFOHEADER.sizeof;
        localBITMAPINFOHEADER1.biWidth = j;
        localBITMAPINFOHEADER1.biHeight = (-k);
        localBITMAPINFOHEADER1.biPlanes = 1;
        localBITMAPINFOHEADER1.biBitCount = 1;
        localBITMAPINFOHEADER1.biCompression = 0;
        localObject1 = new byte[BITMAPINFOHEADER.sizeof + 8];
        OS.MoveMemory((byte[])localObject1, localBITMAPINFOHEADER1, BITMAPINFOHEADER.sizeof);
        i15 = BITMAPINFOHEADER.sizeof;
        byte tmp757_756 = (localObject1[(i15 + 6)] = -1);
        localObject1[(i15 + 5)] = tmp757_756;
        localObject1[(i15 + 4)] = tmp757_756;
        localObject1[(i15 + 7)] = 0;
        OS.SelectObject(i5, localICONINFO.hbmMask);
        if (OS.IsWinCE)
          SWT.error(20);
        OS.GetDIBits(i5, localICONINFO.hbmMask, 0, k, 0, (byte[])localObject1, 0);
        OS.MoveMemory(localBITMAPINFOHEADER1, (byte[])localObject1, BITMAPINFOHEADER.sizeof);
        i8 = localBITMAPINFOHEADER1.biSizeImage;
        localObject3 = new byte[i8];
        i17 = OS.HeapAlloc(i10, 8, i8);
        if (i17 == 0)
          SWT.error(2);
        if (OS.IsWinCE)
          SWT.error(20);
        OS.GetDIBits(i5, localICONINFO.hbmMask, 0, k, i17, (byte[])localObject1, 0);
        OS.MoveMemory((byte[])localObject3, i17, i8);
        OS.HeapFree(i10, 0, i17);
        for (int i18 = 0; i18 < localObject3.length; i18++)
        {
          int tmp910_908 = i18;
          Object tmp910_906 = localObject3;
          tmp910_906[tmp910_908] = ((byte)(tmp910_906[tmp910_908] ^ 0xFFFFFFFF));
        }
        i19 = i8 / k;
        for (i18 = 1; i18 < 128; i18++)
        {
          i20 = ((j + 7) / 8 + (i18 - 1)) / i18 * i18;
          if (i20 == i19)
            break;
        }
        localObject3 = ImageData.convertPad((byte[])localObject3, j, k, 1, i18, 2);
      }
      OS.HeapFree(i10, 0, i11);
      OS.SelectObject(i5, i6);
      if (i7 != 0)
      {
        OS.SelectPalette(i5, i7, false);
        OS.RealizePalette(i5);
      }
      OS.DeleteDC(i5);
      this.device.internal_dispose_GC(i4, null);
      if (localICONINFO.hbmColor != 0)
        OS.DeleteObject(localICONINFO.hbmColor);
      if (localICONINFO.hbmMask != 0)
        OS.DeleteObject(localICONINFO.hbmMask);
      ImageData localImageData = new ImageData(j, k, i, localPaletteData2, 4, arrayOfByte2);
      localImageData.maskData = ((byte[])localObject3);
      localImageData.maskPad = 2;
      return localImageData;
    case 0:
      localBITMAP = new BITMAP();
      OS.GetObject(this.handle, BITMAP.sizeof, localBITMAP);
      i = localBITMAP.bmPlanes * localBITMAP.bmBitsPixel;
      j = localBITMAP.bmWidth;
      k = localBITMAP.bmHeight;
      int m = localBITMAP.bmBits != 0 ? 1 : 0;
      n = this.device.internal_new_GC(null);
      i1 = this.handle;
      if ((OS.IsWinCE) && (m == 0))
      {
        int i2 = 0;
        if ((this.memGC != null) && (!this.memGC.isDisposed()))
        {
          this.memGC.flush();
          i2 = 1;
          localObject1 = this.memGC.data;
          if (((GCData)localObject1).hNullBitmap != 0)
          {
            OS.SelectObject(this.memGC.handle, ((GCData)localObject1).hNullBitmap);
            ((GCData)localObject1).hNullBitmap = 0;
          }
        }
        i1 = createDIBFromDDB(n, this.handle, j, k);
        if (i2 != 0)
        {
          i3 = OS.SelectObject(this.memGC.handle, this.handle);
          this.memGC.data.hNullBitmap = i3;
        }
        m = 1;
      }
      DIBSECTION localDIBSECTION = null;
      if (m != 0)
      {
        localDIBSECTION = new DIBSECTION();
        OS.GetObject(i1, DIBSECTION.sizeof, localDIBSECTION);
      }
      int i3 = 0;
      if (i <= 8)
        if (m != 0)
          i3 = localDIBSECTION.biClrUsed;
        else
          i3 = 1 << i;
      byte[] arrayOfByte1 = (byte[])null;
      BITMAPINFOHEADER localBITMAPINFOHEADER2 = null;
      if (m == 0)
      {
        localBITMAPINFOHEADER2 = new BITMAPINFOHEADER();
        localBITMAPINFOHEADER2.biSize = BITMAPINFOHEADER.sizeof;
        localBITMAPINFOHEADER2.biWidth = j;
        localBITMAPINFOHEADER2.biHeight = (-k);
        localBITMAPINFOHEADER2.biPlanes = 1;
        localBITMAPINFOHEADER2.biBitCount = ((short)i);
        localBITMAPINFOHEADER2.biCompression = 0;
        arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof + i3 * 4];
        OS.MoveMemory(arrayOfByte1, localBITMAPINFOHEADER2, BITMAPINFOHEADER.sizeof);
      }
      i6 = OS.CreateCompatibleDC(n);
      i7 = OS.SelectObject(i6, i1);
      i8 = 0;
      int i9;
      if ((m == 0) && (i <= 8))
      {
        i9 = this.device.hPalette;
        if (i9 != 0)
        {
          i8 = OS.SelectPalette(i6, i9, false);
          OS.RealizePalette(i6);
        }
      }
      if (m != 0)
      {
        i9 = localDIBSECTION.biSizeImage;
      }
      else
      {
        if (OS.IsWinCE)
          SWT.error(20);
        OS.GetDIBits(i6, i1, 0, k, 0, arrayOfByte1, 0);
        OS.MoveMemory(localBITMAPINFOHEADER2, arrayOfByte1, BITMAPINFOHEADER.sizeof);
        i9 = localBITMAPINFOHEADER2.biSizeImage;
      }
      byte[] arrayOfByte3 = new byte[i9];
      if (m != 0)
      {
        if ((OS.IsWinCE) && (this.handle != i1))
          OS.MoveMemory(arrayOfByte3, localDIBSECTION.bmBits, i9);
        else
          OS.MoveMemory(arrayOfByte3, localBITMAP.bmBits, i9);
      }
      else
      {
        i11 = OS.GetProcessHeap();
        int i12 = OS.HeapAlloc(i11, 8, i9);
        if (i12 == 0)
          SWT.error(2);
        if (OS.IsWinCE)
          SWT.error(20);
        OS.GetDIBits(i6, i1, 0, k, i12, arrayOfByte1, 0);
        OS.MoveMemory(arrayOfByte3, i12, i9);
        OS.HeapFree(i11, 0, i12);
      }
      PaletteData localPaletteData1 = null;
      if (i <= 8)
      {
        localObject2 = new RGB[i3];
        int i16;
        if (m != 0)
        {
          if (OS.IsWinCE)
          {
            int i13 = 0;
            i16 = 0;
            i17 = 0;
            byte[] arrayOfByte5 = new byte[1];
            OS.MoveMemory(arrayOfByte5, localBITMAP.bmBits, 1);
            i19 = arrayOfByte5[0];
            i20 = 255 << 8 - localBITMAP.bmBitsPixel & 0xFF;
            for (int i21 = 0; i21 < i3; i21++)
            {
              arrayOfByte5[0] = ((byte)(i21 << 8 - localBITMAP.bmBitsPixel | arrayOfByte5[0] & (i20 ^ 0xFFFFFFFF)));
              OS.MoveMemory(localBITMAP.bmBits, arrayOfByte5, 1);
              int i22 = OS.GetPixel(i6, 0, 0);
              i17 = (i22 & 0xFF0000) >> 16;
              i16 = (i22 & 0xFF00) >> 8;
              i13 = i22 & 0xFF;
              localObject2[i21] = new RGB(i13, i16, i17);
            }
            arrayOfByte5[0] = i19;
            OS.MoveMemory(localBITMAP.bmBits, arrayOfByte5, 1);
          }
          else
          {
            byte[] arrayOfByte4 = new byte[i3 * 4];
            OS.GetDIBColorTable(i6, 0, i3, arrayOfByte4);
            i16 = 0;
            for (i17 = 0; i17 < localObject2.length; i17++)
            {
              localObject2[i17] = new RGB(arrayOfByte4[(i16 + 2)] & 0xFF, arrayOfByte4[(i16 + 1)] & 0xFF, arrayOfByte4[i16] & 0xFF);
              i16 += 4;
            }
          }
        }
        else
        {
          int i14 = BITMAPINFOHEADER.sizeof;
          for (i16 = 0; i16 < i3; i16++)
          {
            localObject2[i16] = new RGB(arrayOfByte1[(i14 + 2)] & 0xFF, arrayOfByte1[(i14 + 1)] & 0xFF, arrayOfByte1[i14] & 0xFF);
            i14 += 4;
          }
        }
        localPaletteData1 = new PaletteData((RGB[])localObject2);
      }
      else if (i == 16)
      {
        localPaletteData1 = new PaletteData(31744, 992, 31);
      }
      else if (i == 24)
      {
        localPaletteData1 = new PaletteData(255, 65280, 16711680);
      }
      else if (i == 32)
      {
        localPaletteData1 = new PaletteData(65280, 16711680, -16777216);
      }
      else
      {
        SWT.error(38);
      }
      OS.SelectObject(i6, i7);
      if (i8 != 0)
      {
        OS.SelectPalette(i6, i8, false);
        OS.RealizePalette(i6);
      }
      if ((OS.IsWinCE) && (i1 != this.handle))
        OS.DeleteObject(i1);
      OS.DeleteDC(i6);
      this.device.internal_dispose_GC(n, null);
      Object localObject2 = new ImageData(j, k, i, localPaletteData1, 4, arrayOfByte3);
      ((ImageData)localObject2).transparentPixel = this.transparentPixel;
      ((ImageData)localObject2).alpha = this.alpha;
      if ((this.alpha == -1) && (this.alphaData != null))
      {
        ((ImageData)localObject2).alphaData = new byte[this.alphaData.length];
        System.arraycopy(this.alphaData, 0, ((ImageData)localObject2).alphaData, 0, this.alphaData.length);
      }
      return localObject2;
    }
    SWT.error(40);
    return null;
  }

  public int hashCode()
  {
    return this.handle;
  }

  void init(int paramInt1, int paramInt2)
  {
    if ((paramInt1 <= 0) || (paramInt2 <= 0))
      SWT.error(5);
    this.type = 0;
    int i = this.device.internal_new_GC(null);
    this.handle = OS.CreateCompatibleBitmap(i, paramInt1, paramInt2);
    int j;
    int k;
    if (this.handle == 0)
    {
      j = OS.GetDeviceCaps(i, 12);
      k = OS.GetDeviceCaps(i, 14);
      int m = j * k;
      if (m < 16)
        m = 16;
      this.handle = createDIB(paramInt1, paramInt2, m);
    }
    if (this.handle != 0)
    {
      j = OS.CreateCompatibleDC(i);
      k = OS.SelectObject(j, this.handle);
      OS.PatBlt(j, 0, 0, paramInt1, paramInt2, 15728673);
      OS.SelectObject(j, k);
      OS.DeleteDC(j);
    }
    this.device.internal_dispose_GC(i, null);
    if (this.handle == 0)
      SWT.error(2, null, this.device.getLastError());
  }

  static int createDIB(int paramInt1, int paramInt2, int paramInt3)
  {
    BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
    localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
    localBITMAPINFOHEADER.biWidth = paramInt1;
    localBITMAPINFOHEADER.biHeight = (-paramInt2);
    localBITMAPINFOHEADER.biPlanes = 1;
    localBITMAPINFOHEADER.biBitCount = ((short)paramInt3);
    if (OS.IsWinCE)
      localBITMAPINFOHEADER.biCompression = 3;
    else
      localBITMAPINFOHEADER.biCompression = 0;
    byte[] arrayOfByte = new byte[BITMAPINFOHEADER.sizeof + (OS.IsWinCE ? 12 : 0)];
    OS.MoveMemory(arrayOfByte, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
    if (OS.IsWinCE)
    {
      int i = 65280;
      int j = 16711680;
      int k = -16777216;
      int m = BITMAPINFOHEADER.sizeof;
      arrayOfByte[m] = ((byte)((i & 0xFF000000) >> 24));
      arrayOfByte[(m + 1)] = ((byte)((i & 0xFF0000) >> 16));
      arrayOfByte[(m + 2)] = ((byte)((i & 0xFF00) >> 8));
      arrayOfByte[(m + 3)] = ((byte)((i & 0xFF) >> 0));
      arrayOfByte[(m + 4)] = ((byte)((j & 0xFF000000) >> 24));
      arrayOfByte[(m + 5)] = ((byte)((j & 0xFF0000) >> 16));
      arrayOfByte[(m + 6)] = ((byte)((j & 0xFF00) >> 8));
      arrayOfByte[(m + 7)] = ((byte)((j & 0xFF) >> 0));
      arrayOfByte[(m + 8)] = ((byte)((k & 0xFF000000) >> 24));
      arrayOfByte[(m + 9)] = ((byte)((k & 0xFF0000) >> 16));
      arrayOfByte[(m + 10)] = ((byte)((k & 0xFF00) >> 8));
      arrayOfByte[(m + 11)] = ((byte)((k & 0xFF) >> 0));
    }
    int[] arrayOfInt = new int[1];
    return OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
  }

  static void GetIconInfo(Image paramImage, ICONINFO paramICONINFO)
  {
    int[] arrayOfInt = init(paramImage.device, null, paramImage.data);
    paramICONINFO.hbmColor = arrayOfInt[0];
    paramICONINFO.hbmMask = arrayOfInt[1];
  }

  static int[] init(Device paramDevice, Image paramImage, ImageData paramImageData)
  {
    if (((OS.IsWin95) && (paramImageData.depth == 1) && (paramImageData.getTransparencyType() != 2)) || (paramImageData.depth == 2))
    {
      localObject = new ImageData(paramImageData.width, paramImageData.height, 4, paramImageData.palette);
      ImageData.blit(1, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, paramImageData.getByteOrder(), 0, 0, paramImageData.width, paramImageData.height, null, null, null, 255, null, 0, 0, 0, ((ImageData)localObject).data, ((ImageData)localObject).depth, ((ImageData)localObject).bytesPerLine, paramImageData.getByteOrder(), 0, 0, ((ImageData)localObject).width, ((ImageData)localObject).height, null, null, null, false, false);
      ((ImageData)localObject).transparentPixel = paramImageData.transparentPixel;
      ((ImageData)localObject).maskPad = paramImageData.maskPad;
      ((ImageData)localObject).maskData = paramImageData.maskData;
      ((ImageData)localObject).alpha = paramImageData.alpha;
      ((ImageData)localObject).alphaData = paramImageData.alphaData;
      paramImageData = (ImageData)localObject;
    }
    if (paramImageData.palette.isDirect)
    {
      localObject = paramImageData.palette;
      i = ((PaletteData)localObject).redMask;
      int j = ((PaletteData)localObject).greenMask;
      int k = ((PaletteData)localObject).blueMask;
      m = paramImageData.depth;
      int n = 1;
      PaletteData localPaletteData2 = null;
      switch (paramImageData.depth)
      {
      case 8:
        m = 16;
        n = 0;
        localPaletteData2 = new PaletteData(31744, 992, 31);
        break;
      case 16:
        n = 0;
        if ((i != 31744) || (j != 992) || (k != 31))
          localPaletteData2 = new PaletteData(31744, 992, 31);
        break;
      case 24:
        if ((i != 255) || (j != 65280) || (k != 16711680))
          localPaletteData2 = new PaletteData(255, 65280, 16711680);
        break;
      case 32:
        if ((i != 65280) || (j != 16711680) || (k != -16777216))
          localPaletteData2 = new PaletteData(65280, 16711680, -16777216);
        break;
      default:
        SWT.error(38);
      }
      if (localPaletteData2 != null)
      {
        ImageData localImageData = new ImageData(paramImageData.width, paramImageData.height, m, localPaletteData2);
        ImageData.blit(1, paramImageData.data, paramImageData.depth, paramImageData.bytesPerLine, paramImageData.getByteOrder(), 0, 0, paramImageData.width, paramImageData.height, i, j, k, 255, null, 0, 0, 0, localImageData.data, localImageData.depth, localImageData.bytesPerLine, n, 0, 0, localImageData.width, localImageData.height, localPaletteData2.redMask, localPaletteData2.greenMask, localPaletteData2.blueMask, false, false);
        if (paramImageData.transparentPixel != -1)
          localImageData.transparentPixel = localPaletteData2.getPixel(((PaletteData)localObject).getRGB(paramImageData.transparentPixel));
        localImageData.maskPad = paramImageData.maskPad;
        localImageData.maskData = paramImageData.maskData;
        localImageData.alpha = paramImageData.alpha;
        localImageData.alphaData = paramImageData.alphaData;
        paramImageData = localImageData;
      }
    }
    Object localObject = paramImageData.palette.getRGBs();
    int i = (OS.IsWinCE) && ((paramImageData.depth == 16) || (paramImageData.depth == 32)) ? 1 : 0;
    BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
    localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
    localBITMAPINFOHEADER.biWidth = paramImageData.width;
    localBITMAPINFOHEADER.biHeight = (-paramImageData.height);
    localBITMAPINFOHEADER.biPlanes = 1;
    localBITMAPINFOHEADER.biBitCount = ((short)paramImageData.depth);
    if (i != 0)
      localBITMAPINFOHEADER.biCompression = 3;
    else
      localBITMAPINFOHEADER.biCompression = 0;
    localBITMAPINFOHEADER.biClrUsed = (localObject == null ? 0 : localObject.length);
    byte[] arrayOfByte1;
    if (paramImageData.palette.isDirect)
      arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof + (i != 0 ? 12 : 0)];
    else
      arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof + localObject.length * 4];
    OS.MoveMemory(arrayOfByte1, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
    int m = BITMAPINFOHEADER.sizeof;
    if (paramImageData.palette.isDirect)
    {
      if (i != 0)
      {
        PaletteData localPaletteData1 = paramImageData.palette;
        i2 = localPaletteData1.redMask;
        int i3 = localPaletteData1.greenMask;
        int i4 = localPaletteData1.blueMask;
        if (paramImageData.getByteOrder() == 0)
        {
          arrayOfByte1[m] = ((byte)((i2 & 0xFF) >> 0));
          arrayOfByte1[(m + 1)] = ((byte)((i2 & 0xFF00) >> 8));
          arrayOfByte1[(m + 2)] = ((byte)((i2 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 3)] = ((byte)((i2 & 0xFF000000) >> 24));
          arrayOfByte1[(m + 4)] = ((byte)((i3 & 0xFF) >> 0));
          arrayOfByte1[(m + 5)] = ((byte)((i3 & 0xFF00) >> 8));
          arrayOfByte1[(m + 6)] = ((byte)((i3 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 7)] = ((byte)((i3 & 0xFF000000) >> 24));
          arrayOfByte1[(m + 8)] = ((byte)((i4 & 0xFF) >> 0));
          arrayOfByte1[(m + 9)] = ((byte)((i4 & 0xFF00) >> 8));
          arrayOfByte1[(m + 10)] = ((byte)((i4 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 11)] = ((byte)((i4 & 0xFF000000) >> 24));
        }
        else
        {
          arrayOfByte1[m] = ((byte)((i2 & 0xFF000000) >> 24));
          arrayOfByte1[(m + 1)] = ((byte)((i2 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 2)] = ((byte)((i2 & 0xFF00) >> 8));
          arrayOfByte1[(m + 3)] = ((byte)((i2 & 0xFF) >> 0));
          arrayOfByte1[(m + 4)] = ((byte)((i3 & 0xFF000000) >> 24));
          arrayOfByte1[(m + 5)] = ((byte)((i3 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 6)] = ((byte)((i3 & 0xFF00) >> 8));
          arrayOfByte1[(m + 7)] = ((byte)((i3 & 0xFF) >> 0));
          arrayOfByte1[(m + 8)] = ((byte)((i4 & 0xFF000000) >> 24));
          arrayOfByte1[(m + 9)] = ((byte)((i4 & 0xFF0000) >> 16));
          arrayOfByte1[(m + 10)] = ((byte)((i4 & 0xFF00) >> 8));
          arrayOfByte1[(m + 11)] = ((byte)((i4 & 0xFF) >> 0));
        }
      }
    }
    else
      for (int i1 = 0; i1 < localObject.length; i1++)
      {
        arrayOfByte1[m] = ((byte)localObject[i1].blue);
        arrayOfByte1[(m + 1)] = ((byte)localObject[i1].green);
        arrayOfByte1[(m + 2)] = ((byte)localObject[i1].red);
        arrayOfByte1[(m + 3)] = 0;
        m += 4;
      }
    int[] arrayOfInt1 = new int[1];
    int i2 = OS.CreateDIBSection(0, arrayOfByte1, 0, arrayOfInt1, 0, 0);
    if (i2 == 0)
      SWT.error(2);
    byte[] arrayOfByte2 = paramImageData.data;
    if ((paramImageData.scanlinePad != 4) && (paramImageData.bytesPerLine % 4 != 0))
      arrayOfByte2 = ImageData.convertPad(arrayOfByte2, paramImageData.width, paramImageData.height, paramImageData.depth, paramImageData.scanlinePad, 4);
    OS.MoveMemory(arrayOfInt1[0], arrayOfByte2, arrayOfByte2.length);
    int[] arrayOfInt2 = (int[])null;
    int i5;
    if (paramImageData.getTransparencyType() == 2)
    {
      i5 = paramDevice.internal_new_GC(null);
      int i6 = OS.CreateCompatibleDC(i5);
      OS.SelectObject(i6, i2);
      int i7 = OS.CreateCompatibleBitmap(i5, paramImageData.width, paramImageData.height);
      if (i7 == 0)
        SWT.error(2);
      int i8 = OS.CreateCompatibleDC(i5);
      OS.SelectObject(i8, i7);
      OS.BitBlt(i8, 0, 0, paramImageData.width, paramImageData.height, i6, 0, 0, 13369376);
      paramDevice.internal_dispose_GC(i5, null);
      byte[] arrayOfByte3 = ImageData.convertPad(paramImageData.maskData, paramImageData.width, paramImageData.height, 1, paramImageData.maskPad, 2);
      int i9 = OS.CreateBitmap(paramImageData.width, paramImageData.height, 1, 1, arrayOfByte3);
      if (i9 == 0)
        SWT.error(2);
      OS.SelectObject(i6, i9);
      OS.PatBlt(i6, 0, 0, paramImageData.width, paramImageData.height, 5570569);
      OS.DeleteDC(i6);
      OS.DeleteDC(i8);
      OS.DeleteObject(i2);
      if (paramImage == null)
      {
        arrayOfInt2 = new int[] { i7, i9 };
      }
      else
      {
        ICONINFO localICONINFO = new ICONINFO();
        localICONINFO.fIcon = true;
        localICONINFO.hbmColor = i7;
        localICONINFO.hbmMask = i9;
        int i10 = OS.CreateIconIndirect(localICONINFO);
        if (i10 == 0)
          SWT.error(2);
        OS.DeleteObject(i7);
        OS.DeleteObject(i9);
        paramImage.handle = i10;
        paramImage.type = 1;
        if (OS.IsWinCE)
          paramImage.data = paramImageData;
      }
    }
    else if (paramImage == null)
    {
      arrayOfInt2 = new int[] { i2 };
    }
    else
    {
      paramImage.handle = i2;
      paramImage.type = 0;
      paramImage.transparentPixel = paramImageData.transparentPixel;
      if (paramImage.transparentPixel == -1)
      {
        paramImage.alpha = paramImageData.alpha;
        if ((paramImageData.alpha == -1) && (paramImageData.alphaData != null))
        {
          i5 = paramImageData.alphaData.length;
          paramImage.alphaData = new byte[i5];
          System.arraycopy(paramImageData.alphaData, 0, paramImage.alphaData, 0, i5);
        }
      }
    }
    return arrayOfInt2;
  }

  static int[] init(Device paramDevice, Image paramImage, ImageData paramImageData1, ImageData paramImageData2)
  {
    int i = 0;
    ImageData localImageData;
    Object localObject1;
    Object localObject2;
    int k;
    if (paramImageData1.palette.isDirect)
    {
      localImageData = new ImageData(paramImageData1.width, paramImageData1.height, paramImageData1.depth, paramImageData1.palette);
    }
    else
    {
      localObject1 = new RGB(0, 0, 0);
      localObject2 = paramImageData1.getRGBs();
      RGB[] arrayOfRGB;
      if (paramImageData1.transparentPixel != -1)
      {
        arrayOfRGB = new RGB[localObject2.length];
        System.arraycopy(localObject2, 0, arrayOfRGB, 0, localObject2.length);
        if (paramImageData1.transparentPixel >= arrayOfRGB.length)
        {
          localObject2 = new RGB[paramImageData1.transparentPixel + 1];
          System.arraycopy(arrayOfRGB, 0, localObject2, 0, arrayOfRGB.length);
          for (k = arrayOfRGB.length; k <= paramImageData1.transparentPixel; k++)
            localObject2[k] = new RGB(0, 0, 0);
        }
        else
        {
          arrayOfRGB[paramImageData1.transparentPixel] = localObject1;
          localObject2 = arrayOfRGB;
        }
        i = paramImageData1.transparentPixel;
        localImageData = new ImageData(paramImageData1.width, paramImageData1.height, paramImageData1.depth, new PaletteData((RGB[])localObject2));
      }
      else
      {
        while (i < localObject2.length)
        {
          if (localObject2[i].equals(localObject1))
            break;
          i++;
        }
        if (i == localObject2.length)
          if (1 << paramImageData1.depth > localObject2.length)
          {
            arrayOfRGB = new RGB[localObject2.length + 1];
            System.arraycopy(localObject2, 0, arrayOfRGB, 0, localObject2.length);
            arrayOfRGB[localObject2.length] = localObject1;
            localObject2 = arrayOfRGB;
          }
          else
          {
            i = -1;
          }
        localImageData = new ImageData(paramImageData1.width, paramImageData1.height, paramImageData1.depth, new PaletteData((RGB[])localObject2));
      }
    }
    if (i == -1)
    {
      System.arraycopy(paramImageData1.data, 0, localImageData.data, 0, localImageData.data.length);
    }
    else
    {
      localObject1 = new int[localImageData.width];
      localObject2 = new int[paramImageData2.width];
      for (int j = 0; j < localImageData.height; j++)
      {
        paramImageData1.getPixels(0, j, localImageData.width, (int[])localObject1, 0);
        paramImageData2.getPixels(0, j, paramImageData2.width, (int[])localObject2, 0);
        for (k = 0; k < localObject1.length; k++)
          if (localObject2[k] == 0)
            localObject1[k] = i;
        localImageData.setPixels(0, j, paramImageData1.width, (int[])localObject1, 0);
      }
    }
    localImageData.maskPad = paramImageData2.scanlinePad;
    localImageData.maskData = paramImageData2.data;
    return init(paramDevice, paramImage, localImageData);
  }

  void init(ImageData paramImageData)
  {
    if (paramImageData == null)
      SWT.error(4);
    init(this.device, this, paramImageData);
  }

  public int internal_new_GC(GCData paramGCData)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.type != 0) || (this.memGC != null))
      SWT.error(5);
    int i = this.device.internal_new_GC(null);
    int j = OS.CreateCompatibleDC(i);
    this.device.internal_dispose_GC(i, null);
    if (j == 0)
      SWT.error(2);
    if (paramGCData != null)
    {
      int k = 100663296;
      if ((paramGCData.style & k) != 0)
        paramGCData.layout = ((paramGCData.style & 0x4000000) != 0 ? 1 : 0);
      else
        paramGCData.style |= 33554432;
      paramGCData.device = this.device;
      paramGCData.image = this;
      paramGCData.font = this.device.systemFont;
    }
    return j;
  }

  public void internal_dispose_GC(int paramInt, GCData paramGCData)
  {
    OS.DeleteDC(paramInt);
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public void setBackground(Color paramColor)
  {
    if (OS.IsWinCE)
      return;
    if (isDisposed())
      SWT.error(44);
    if (paramColor == null)
      SWT.error(4);
    if (paramColor.isDisposed())
      SWT.error(5);
    if (this.transparentPixel == -1)
      return;
    this.transparentColor = -1;
    int i = this.device.internal_new_GC(null);
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(this.handle, BITMAP.sizeof, localBITMAP);
    int j = OS.CreateCompatibleDC(i);
    OS.SelectObject(j, this.handle);
    int k = 1 << localBITMAP.bmBitsPixel;
    byte[] arrayOfByte = new byte[k * 4];
    if (OS.IsWinCE)
      SWT.error(20);
    int m = OS.GetDIBColorTable(j, 0, k, arrayOfByte);
    int n = this.transparentPixel * 4;
    arrayOfByte[n] = ((byte)paramColor.getBlue());
    arrayOfByte[(n + 1)] = ((byte)paramColor.getGreen());
    arrayOfByte[(n + 2)] = ((byte)paramColor.getRed());
    if (OS.IsWinCE)
      SWT.error(20);
    OS.SetDIBColorTable(j, 0, m, arrayOfByte);
    OS.DeleteDC(j);
    this.device.internal_dispose_GC(i, null);
  }

  public String toString()
  {
    if (isDisposed())
      return "Image {*DISPOSED*}";
    return "Image {" + this.handle + "}";
  }

  public static Image win32_new(Device paramDevice, int paramInt1, int paramInt2)
  {
    Image localImage = new Image(paramDevice);
    localImage.type = paramInt1;
    localImage.handle = paramInt2;
    return localImage;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Image
 * JD-Core Version:    0.6.2
 */