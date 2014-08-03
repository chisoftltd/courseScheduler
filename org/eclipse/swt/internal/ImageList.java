package org.eclipse.swt.internal;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.OS;

public class ImageList
{
  int handle;
  int style;
  int refCount;
  Image[] images;

  public ImageList(int paramInt)
  {
    this.style = paramInt;
    int i = 1;
    if (OS.IsWinCE)
    {
      i |= 0;
    }
    else if (OS.COMCTL32_MAJOR >= 6)
    {
      i |= 32;
    }
    else
    {
      int j = OS.GetDC(0);
      int k = OS.GetDeviceCaps(j, 12);
      int m = OS.GetDeviceCaps(j, 14);
      OS.ReleaseDC(0, j);
      int n = k * m;
      switch (n)
      {
      case 4:
        i |= 4;
        break;
      case 8:
        i |= 8;
        break;
      case 16:
        i |= 16;
        break;
      case 24:
        i |= 24;
        break;
      case 32:
        i |= 32;
        break;
      default:
        i |= 0;
      }
    }
    if ((paramInt & 0x4000000) != 0)
      i |= 8192;
    this.handle = OS.ImageList_Create(32, 32, i, 16, 16);
    this.images = new Image[4];
  }

  public int add(Image paramImage)
  {
    int i = OS.ImageList_GetImageCount(this.handle);
    for (int j = 0; j < i; j++)
    {
      if ((this.images[j] != null) && (this.images[j].isDisposed()))
        this.images[j] = null;
      if (this.images[j] == null)
        break;
    }
    Object localObject;
    if (i == 0)
    {
      localObject = paramImage.getBounds();
      OS.ImageList_SetIconSize(this.handle, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
    }
    set(j, paramImage, i);
    if (j == this.images.length)
    {
      localObject = new Image[this.images.length + 4];
      System.arraycopy(this.images, 0, localObject, 0, this.images.length);
      this.images = ((Image[])localObject);
    }
    this.images[j] = paramImage;
    return j;
  }

  public int addRef()
  {
    return ++this.refCount;
  }

  int copyBitmap(int paramInt1, int paramInt2, int paramInt3)
  {
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(paramInt1, BITMAP.sizeof, localBITMAP);
    int i = OS.GetDC(0);
    int j = OS.CreateCompatibleDC(i);
    OS.SelectObject(j, paramInt1);
    int k = OS.CreateCompatibleDC(i);
    int m;
    if ((localBITMAP.bmBitsPixel == 32) && (OS.COMCTL32_MAJOR >= 6))
    {
      BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
      localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
      localBITMAPINFOHEADER.biWidth = paramInt2;
      localBITMAPINFOHEADER.biHeight = (-paramInt3);
      localBITMAPINFOHEADER.biPlanes = 1;
      localBITMAPINFOHEADER.biBitCount = 24;
      if (OS.IsWinCE)
        localBITMAPINFOHEADER.biCompression = 3;
      else
        localBITMAPINFOHEADER.biCompression = 0;
      byte[] arrayOfByte = new byte[BITMAPINFOHEADER.sizeof + (OS.IsWinCE ? 12 : 0)];
      OS.MoveMemory(arrayOfByte, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
      if (OS.IsWinCE)
      {
        int n = 65280;
        int i1 = 16711680;
        int i2 = -16777216;
        int i3 = BITMAPINFOHEADER.sizeof;
        arrayOfByte[i3] = ((byte)((n & 0xFF000000) >> 24));
        arrayOfByte[(i3 + 1)] = ((byte)((n & 0xFF0000) >> 16));
        arrayOfByte[(i3 + 2)] = ((byte)((n & 0xFF00) >> 8));
        arrayOfByte[(i3 + 3)] = ((byte)((n & 0xFF) >> 0));
        arrayOfByte[(i3 + 4)] = ((byte)((i1 & 0xFF000000) >> 24));
        arrayOfByte[(i3 + 5)] = ((byte)((i1 & 0xFF0000) >> 16));
        arrayOfByte[(i3 + 6)] = ((byte)((i1 & 0xFF00) >> 8));
        arrayOfByte[(i3 + 7)] = ((byte)((i1 & 0xFF) >> 0));
        arrayOfByte[(i3 + 8)] = ((byte)((i2 & 0xFF000000) >> 24));
        arrayOfByte[(i3 + 9)] = ((byte)((i2 & 0xFF0000) >> 16));
        arrayOfByte[(i3 + 10)] = ((byte)((i2 & 0xFF00) >> 8));
        arrayOfByte[(i3 + 11)] = ((byte)((i2 & 0xFF) >> 0));
      }
      int[] arrayOfInt = new int[1];
      m = OS.CreateDIBSection(0, arrayOfByte, 0, arrayOfInt, 0, 0);
    }
    else
    {
      m = OS.CreateCompatibleBitmap(i, paramInt2, paramInt3);
    }
    OS.SelectObject(k, m);
    if ((paramInt2 != localBITMAP.bmWidth) || (paramInt3 != localBITMAP.bmHeight))
    {
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(k, 3);
      OS.StretchBlt(k, 0, 0, paramInt2, paramInt3, j, 0, 0, localBITMAP.bmWidth, localBITMAP.bmHeight, 13369376);
    }
    else
    {
      OS.BitBlt(k, 0, 0, paramInt2, paramInt3, j, 0, 0, 13369376);
    }
    OS.DeleteDC(j);
    OS.DeleteDC(k);
    OS.ReleaseDC(0, i);
    return m;
  }

  int copyIcon(int paramInt1, int paramInt2, int paramInt3)
  {
    if (OS.IsWinCE)
      SWT.error(20);
    int i = OS.CopyImage(paramInt1, 1, paramInt2, paramInt3, 0);
    return i != 0 ? i : paramInt1;
  }

  int copyWithAlpha(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, int paramInt4)
  {
    BITMAP localBITMAP1 = new BITMAP();
    OS.GetObject(paramInt1, BITMAP.sizeof, localBITMAP1);
    int i = localBITMAP1.bmWidth;
    int j = localBITMAP1.bmHeight;
    int k = OS.GetDC(0);
    int m = OS.CreateCompatibleDC(k);
    int n = OS.SelectObject(m, paramInt1);
    int i1 = OS.CreateCompatibleDC(k);
    BITMAPINFOHEADER localBITMAPINFOHEADER1 = new BITMAPINFOHEADER();
    localBITMAPINFOHEADER1.biSize = BITMAPINFOHEADER.sizeof;
    localBITMAPINFOHEADER1.biWidth = i;
    localBITMAPINFOHEADER1.biHeight = (-j);
    localBITMAPINFOHEADER1.biPlanes = 1;
    localBITMAPINFOHEADER1.biBitCount = 32;
    localBITMAPINFOHEADER1.biCompression = 0;
    byte[] arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof];
    OS.MoveMemory(arrayOfByte1, localBITMAPINFOHEADER1, BITMAPINFOHEADER.sizeof);
    int[] arrayOfInt1 = new int[1];
    int i2 = OS.CreateDIBSection(0, arrayOfByte1, 0, arrayOfInt1, 0, 0);
    if (i2 == 0)
      SWT.error(2);
    int i3 = OS.SelectObject(i1, i2);
    BITMAP localBITMAP2 = new BITMAP();
    OS.GetObject(i2, BITMAP.sizeof, localBITMAP2);
    int i4 = localBITMAP2.bmWidthBytes * localBITMAP2.bmHeight;
    OS.BitBlt(i1, 0, 0, i, j, m, 0, 0, 13369376);
    byte[] arrayOfByte2 = new byte[i4];
    OS.MoveMemory(arrayOfByte2, localBITMAP2.bmBits, i4);
    int i5;
    int i6;
    int i7;
    int i8;
    int i9;
    int i10;
    if (paramArrayOfByte != null)
    {
      i5 = localBITMAP2.bmWidthBytes - i * 4;
      i6 = 0;
      i7 = 3;
      for (i8 = 0; i8 < j; i8++)
      {
        for (i9 = 0; i9 < i; i9++)
        {
          arrayOfByte2[i7] = paramArrayOfByte[(i6++)];
          i7 += 4;
        }
        i7 += i5;
      }
    }
    else
    {
      i5 = (byte)(paramInt2 & 0xFF);
      i6 = (byte)(paramInt2 >> 8 & 0xFF);
      i7 = (byte)(paramInt2 >> 16 & 0xFF);
      i8 = localBITMAP2.bmWidthBytes - i * 4;
      i9 = 3;
      for (i10 = 0; i10 < j; i10++)
      {
        for (int i11 = 0; i11 < i; i11++)
        {
          arrayOfByte2[i9] = ((arrayOfByte2[(i9 - 1)] == i5) && (arrayOfByte2[(i9 - 2)] == i6) && (arrayOfByte2[(i9 - 3)] == i7) ? 0 : -1);
          i9 += 4;
        }
        i9 += i8;
      }
    }
    OS.MoveMemory(localBITMAP2.bmBits, arrayOfByte2, i4);
    if ((i != paramInt3) || (j != paramInt4))
    {
      BITMAPINFOHEADER localBITMAPINFOHEADER2 = new BITMAPINFOHEADER();
      localBITMAPINFOHEADER2.biSize = BITMAPINFOHEADER.sizeof;
      localBITMAPINFOHEADER2.biWidth = paramInt3;
      localBITMAPINFOHEADER2.biHeight = (-paramInt4);
      localBITMAPINFOHEADER2.biPlanes = 1;
      localBITMAPINFOHEADER2.biBitCount = 32;
      localBITMAPINFOHEADER2.biCompression = 0;
      byte[] arrayOfByte3 = new byte[BITMAPINFOHEADER.sizeof];
      OS.MoveMemory(arrayOfByte3, localBITMAPINFOHEADER2, BITMAPINFOHEADER.sizeof);
      int[] arrayOfInt2 = new int[1];
      i8 = OS.CreateDIBSection(0, arrayOfByte3, 0, arrayOfInt2, 0, 0);
      i9 = OS.CreateCompatibleDC(k);
      i10 = OS.SelectObject(i9, i8);
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(i9, 3);
      OS.StretchBlt(i9, 0, 0, paramInt3, paramInt4, i1, 0, 0, i, j, 13369376);
      OS.SelectObject(i9, i10);
      OS.DeleteDC(i9);
      OS.SelectObject(i1, i3);
      OS.DeleteDC(i1);
      OS.DeleteObject(i2);
      i2 = i8;
    }
    else
    {
      OS.SelectObject(i1, i3);
      OS.DeleteDC(i1);
    }
    OS.SelectObject(m, n);
    OS.DeleteDC(m);
    OS.ReleaseDC(0, k);
    return i2;
  }

  int createMaskFromAlpha(ImageData paramImageData, int paramInt1, int paramInt2)
  {
    int i = paramImageData.width;
    int j = paramImageData.height;
    ImageData localImageData = ImageData.internal_new(i, j, 1, new PaletteData(new RGB[] { new RGB(0, 0, 0), new RGB(255, 255, 255) }), 2, null, 1, null, null, -1, -1, -1, 0, 0, 0, 0);
    int k = 0;
    int n;
    for (int m = 0; m < localImageData.height; m++)
      for (n = 0; n < localImageData.width; n++)
        localImageData.setPixel(n, m, (paramImageData.alphaData[(k++)] & 0xFF) <= 127 ? 1 : 0);
    m = OS.CreateBitmap(i, j, 1, 1, localImageData.data);
    if ((i != paramInt1) || (j != paramInt2))
    {
      n = OS.GetDC(0);
      int i1 = OS.CreateCompatibleDC(n);
      OS.SelectObject(i1, m);
      int i2 = OS.CreateCompatibleDC(n);
      int i3 = OS.CreateBitmap(paramInt1, paramInt2, 1, 1, null);
      OS.SelectObject(i2, i3);
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(i2, 3);
      OS.StretchBlt(i2, 0, 0, paramInt1, paramInt2, i1, 0, 0, i, j, 13369376);
      OS.DeleteDC(i1);
      OS.DeleteDC(i2);
      OS.ReleaseDC(0, n);
      OS.DeleteObject(m);
      m = i3;
    }
    return m;
  }

  int createMask(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(paramInt1, BITMAP.sizeof, localBITMAP);
    int i = localBITMAP.bmWidth;
    int j = localBITMAP.bmHeight;
    int k = OS.CreateBitmap(paramInt2, paramInt3, 1, 1, null);
    int m = OS.GetDC(0);
    int n = OS.CreateCompatibleDC(m);
    int i1;
    if (paramInt4 != -1)
    {
      OS.SelectObject(n, paramInt1);
      i1 = localBITMAP.bmBits != 0 ? 1 : 0;
      Object localObject = (byte[])null;
      if ((!OS.IsWinCE) && (paramInt5 != -1) && (i1 != 0) && (localBITMAP.bmBitsPixel <= 8))
      {
        i2 = 1 << localBITMAP.bmBitsPixel;
        byte[] arrayOfByte1 = new byte[i2 * 4];
        OS.GetDIBColorTable(n, 0, i2, arrayOfByte1);
        int i3 = paramInt5 * 4;
        byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
        arrayOfByte2[i3] = -1;
        arrayOfByte2[(i3 + 1)] = -1;
        arrayOfByte2[(i3 + 2)] = -1;
        OS.SetDIBColorTable(n, 0, i2, arrayOfByte2);
        localObject = arrayOfByte1;
        OS.SetBkColor(n, 16777215);
      }
      else
      {
        OS.SetBkColor(n, paramInt4);
      }
      int i2 = OS.CreateCompatibleDC(m);
      OS.SelectObject(i2, k);
      if ((paramInt2 != i) || (paramInt3 != j))
      {
        if (!OS.IsWinCE)
          OS.SetStretchBltMode(i2, 3);
        OS.StretchBlt(i2, 0, 0, paramInt2, paramInt3, n, 0, 0, i, j, 13369376);
      }
      else
      {
        OS.BitBlt(i2, 0, 0, paramInt2, paramInt3, n, 0, 0, 13369376);
      }
      OS.DeleteDC(i2);
      if (localObject != null)
        OS.SetDIBColorTable(n, 0, 1 << localBITMAP.bmBitsPixel, (byte[])localObject);
    }
    else
    {
      i1 = OS.SelectObject(n, k);
      OS.PatBlt(n, 0, 0, paramInt2, paramInt3, 66);
      OS.SelectObject(n, i1);
    }
    OS.ReleaseDC(0, m);
    OS.DeleteDC(n);
    return k;
  }

  public void dispose()
  {
    if (this.handle != 0)
      OS.ImageList_Destroy(this.handle);
    this.handle = 0;
    this.images = null;
  }

  public Image get(int paramInt)
  {
    return this.images[paramInt];
  }

  public int getStyle()
  {
    return this.style;
  }

  public int getHandle()
  {
    return this.handle;
  }

  public Point getImageSize()
  {
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.ImageList_GetIconSize(this.handle, arrayOfInt1, arrayOfInt2);
    return new Point(arrayOfInt1[0], arrayOfInt2[0]);
  }

  public int indexOf(Image paramImage)
  {
    int i = OS.ImageList_GetImageCount(this.handle);
    for (int j = 0; j < i; j++)
      if (this.images[j] != null)
      {
        if (this.images[j].isDisposed())
          this.images[j] = null;
        if ((this.images[j] != null) && (this.images[j].equals(paramImage)))
          return j;
      }
    return -1;
  }

  public void put(int paramInt, Image paramImage)
  {
    int i = OS.ImageList_GetImageCount(this.handle);
    if ((paramInt < 0) || (paramInt >= i))
      return;
    if (paramImage != null)
      set(paramInt, paramImage, i);
    this.images[paramInt] = paramImage;
  }

  public void remove(int paramInt)
  {
    int i = OS.ImageList_GetImageCount(this.handle);
    if ((paramInt < 0) || (paramInt >= i))
      return;
    OS.ImageList_Remove(this.handle, paramInt);
    System.arraycopy(this.images, paramInt + 1, this.images, paramInt, --i - paramInt);
    this.images[paramInt] = null;
  }

  public int removeRef()
  {
    return --this.refCount;
  }

  void set(int paramInt1, Image paramImage, int paramInt2)
  {
    int i = paramImage.handle;
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    OS.ImageList_GetIconSize(this.handle, arrayOfInt1, arrayOfInt2);
    int j;
    switch (paramImage.type)
    {
    case 0:
      j = 0;
      int k = 0;
      ImageData localImageData = paramImage.getImageData();
      switch (localImageData.getTransparencyType())
      {
      case 1:
        if (OS.COMCTL32_MAJOR >= 6)
        {
          j = copyWithAlpha(i, -1, localImageData.alphaData, arrayOfInt1[0], arrayOfInt2[0]);
        }
        else
        {
          j = copyBitmap(i, arrayOfInt1[0], arrayOfInt2[0]);
          k = createMaskFromAlpha(localImageData, arrayOfInt1[0], arrayOfInt2[0]);
        }
        break;
      case 4:
        int m = -1;
        Color localColor = paramImage.getBackground();
        if (localColor != null)
          m = localColor.handle;
        j = copyBitmap(i, arrayOfInt1[0], arrayOfInt2[0]);
        k = createMask(i, arrayOfInt1[0], arrayOfInt2[0], m, localImageData.transparentPixel);
        break;
      case 0:
      case 2:
      case 3:
      default:
        j = copyBitmap(i, arrayOfInt1[0], arrayOfInt2[0]);
        if (paramInt1 != paramInt2)
          k = createMask(i, arrayOfInt1[0], arrayOfInt2[0], -1, -1);
        break;
      }
      if (paramInt1 == paramInt2)
        OS.ImageList_Add(this.handle, j, k);
      else
        OS.ImageList_Replace(this.handle, paramInt1, j, k);
      if (k != 0)
        OS.DeleteObject(k);
      if (j != i)
        OS.DeleteObject(j);
      break;
    case 1:
      if (OS.IsWinCE)
      {
        OS.ImageList_ReplaceIcon(this.handle, paramInt1 == paramInt2 ? -1 : paramInt1, i);
      }
      else
      {
        j = copyIcon(i, arrayOfInt1[0], arrayOfInt2[0]);
        OS.ImageList_ReplaceIcon(this.handle, paramInt1 == paramInt2 ? -1 : paramInt1, j);
        OS.DestroyIcon(j);
      }
      break;
    }
  }

  public int size()
  {
    int i = 0;
    int j = OS.ImageList_GetImageCount(this.handle);
    for (int k = 0; k < j; k++)
      if (this.images[k] != null)
      {
        if (this.images[k].isDisposed())
          this.images[k] = null;
        if (this.images[k] != null)
          i++;
      }
    return i;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ImageList
 * JD-Core Version:    0.6.2
 */