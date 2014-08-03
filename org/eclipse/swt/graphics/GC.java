package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.PointF;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.gdip.RectF;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.BLENDFUNCTION;
import org.eclipse.swt.internal.win32.GCP_RESULTS;
import org.eclipse.swt.internal.win32.GRADIENT_RECT;
import org.eclipse.swt.internal.win32.ICONINFO;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.LOGPEN;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;
import org.eclipse.swt.internal.win32.TRIVERTEX;

public final class GC extends Resource
{
  public int handle;
  Drawable drawable;
  GCData data;
  static final int FOREGROUND = 1;
  static final int BACKGROUND = 2;
  static final int FONT = 4;
  static final int LINE_STYLE = 8;
  static final int LINE_WIDTH = 16;
  static final int LINE_CAP = 32;
  static final int LINE_JOIN = 64;
  static final int LINE_MITERLIMIT = 128;
  static final int FOREGROUND_TEXT = 256;
  static final int BACKGROUND_TEXT = 512;
  static final int BRUSH = 1024;
  static final int PEN = 2048;
  static final int NULL_BRUSH = 4096;
  static final int NULL_PEN = 8192;
  static final int DRAW_OFFSET = 16384;
  static final int DRAW = 22777;
  static final int FILL = 9218;
  static final float[] LINE_DOT_ZERO = { 3.0F, 3.0F };
  static final float[] LINE_DASH_ZERO = { 18.0F, 6.0F };
  static final float[] LINE_DASHDOT_ZERO = { 9.0F, 6.0F, 3.0F, 6.0F };
  static final float[] LINE_DASHDOTDOT_ZERO = { 9.0F, 3.0F, 3.0F, 3.0F, 3.0F, 3.0F };

  GC()
  {
  }

  public GC(Drawable paramDrawable)
  {
    this(paramDrawable, 0);
  }

  public GC(Drawable paramDrawable, int paramInt)
  {
    if (paramDrawable == null)
      SWT.error(4);
    GCData localGCData = new GCData();
    localGCData.style = checkStyle(paramInt);
    int i = paramDrawable.internal_new_GC(localGCData);
    Device localDevice = localGCData.device;
    if (localDevice == null)
      localDevice = Device.getDevice();
    if (localDevice == null)
      SWT.error(4);
    this.device = (localGCData.device = localDevice);
    init(paramDrawable, localGCData, i);
    init();
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x2000000) != 0)
      paramInt &= -67108865;
    return paramInt & 0x6000000;
  }

  void checkGC(int paramInt)
  {
    int i = this.data.state;
    if ((i & paramInt) == paramInt)
      return;
    i = (i ^ paramInt) & paramInt;
    this.data.state |= paramInt;
    int j = this.data.gdipGraphics;
    int k;
    int i10;
    if (j != 0)
    {
      k = this.data.gdipPen;
      float f1 = this.data.lineWidth;
      int i5;
      int i7;
      if (((i & 0x1) != 0) || ((k == 0) && ((i & 0xF8) != 0)))
      {
        if (this.data.gdipFgBrush != 0)
          Gdip.SolidBrush_delete(this.data.gdipFgBrush);
        this.data.gdipFgBrush = 0;
        Pattern localPattern = this.data.foregroundPattern;
        int n;
        if (localPattern != null)
        {
          n = localPattern.handle;
          if ((this.data.style & 0x8000000) != 0)
            switch (Gdip.Brush_GetType(n))
            {
            case 2:
              n = Gdip.Brush_Clone(n);
              if (n == 0)
                SWT.error(2);
              Gdip.TextureBrush_ScaleTransform(n, -1.0F, 1.0F, 0);
              this.data.gdipFgBrush = n;
            }
        }
        else
        {
          i5 = this.data.foreground;
          i7 = i5 >> 16 & 0xFF | i5 & 0xFF00 | (i5 & 0xFF) << 16;
          int i9 = Gdip.Color_new(this.data.alpha << 24 | i7);
          if (i9 == 0)
            SWT.error(2);
          n = Gdip.SolidBrush_new(i9);
          if (n == 0)
            SWT.error(2);
          Gdip.Color_delete(i9);
          this.data.gdipFgBrush = n;
        }
        if (k != 0)
          Gdip.Pen_SetBrush(k, n);
        else
          k = this.data.gdipPen = Gdip.Pen_new(n, f1);
      }
      if ((i & 0x10) != 0)
      {
        Gdip.Pen_SetWidth(k, f1);
        switch (this.data.lineStyle)
        {
        case 6:
          i |= 8;
        }
      }
      if ((i & 0x8) != 0)
      {
        float[] arrayOfFloat = (float[])null;
        float f2 = 0.0F;
        i5 = 0;
        switch (this.data.lineStyle)
        {
        case 1:
          break;
        case 3:
          i5 = 2;
          if (f1 == 0.0F)
            arrayOfFloat = LINE_DOT_ZERO;
          break;
        case 2:
          i5 = 1;
          if (f1 == 0.0F)
            arrayOfFloat = LINE_DASH_ZERO;
          break;
        case 4:
          i5 = 3;
          if (f1 == 0.0F)
            arrayOfFloat = LINE_DASHDOT_ZERO;
          break;
        case 5:
          i5 = 4;
          if (f1 == 0.0F)
            arrayOfFloat = LINE_DASHDOTDOT_ZERO;
          break;
        case 6:
          if (this.data.lineDashes != null)
          {
            f2 = this.data.lineDashesOffset / Math.max(1.0F, f1);
            arrayOfFloat = new float[this.data.lineDashes.length * 2];
            for (i7 = 0; i7 < this.data.lineDashes.length; i7++)
            {
              float f5 = this.data.lineDashes[i7] / Math.max(1.0F, f1);
              arrayOfFloat[i7] = f5;
              arrayOfFloat[(i7 + this.data.lineDashes.length)] = f5;
            }
          }
          break;
        }
        if (arrayOfFloat != null)
        {
          Gdip.Pen_SetDashPattern(k, arrayOfFloat, arrayOfFloat.length);
          Gdip.Pen_SetDashStyle(k, 5);
          Gdip.Pen_SetDashOffset(k, f2);
        }
        else
        {
          Gdip.Pen_SetDashStyle(k, i5);
        }
      }
      if ((i & 0x80) != 0)
        Gdip.Pen_SetMiterLimit(k, this.data.lineMiterLimit);
      int i1;
      if ((i & 0x40) != 0)
      {
        i1 = 0;
        switch (this.data.lineJoin)
        {
        case 1:
          i1 = 0;
          break;
        case 3:
          i1 = 1;
          break;
        case 2:
          i1 = 2;
        }
        Gdip.Pen_SetLineJoin(k, i1);
      }
      int i3;
      if ((i & 0x20) != 0)
      {
        i1 = 0;
        i3 = 0;
        switch (this.data.lineCap)
        {
        case 1:
          i3 = 0;
          break;
        case 2:
          i3 = 2;
          i1 = 2;
          break;
        case 3:
          i3 = 1;
        }
        Gdip.Pen_SetLineCap(k, i3, i3, i1);
      }
      Object localObject1;
      if ((i & 0x2) != 0)
      {
        if (this.data.gdipBgBrush != 0)
          Gdip.SolidBrush_delete(this.data.gdipBgBrush);
        this.data.gdipBgBrush = 0;
        localObject1 = this.data.backgroundPattern;
        if (localObject1 != null)
        {
          this.data.gdipBrush = ((Pattern)localObject1).handle;
          if ((this.data.style & 0x8000000) != 0)
            switch (Gdip.Brush_GetType(this.data.gdipBrush))
            {
            case 2:
              i3 = Gdip.Brush_Clone(this.data.gdipBrush);
              if (i3 == 0)
                SWT.error(2);
              Gdip.TextureBrush_ScaleTransform(i3, -1.0F, 1.0F, 0);
              this.data.gdipBrush = (this.data.gdipBgBrush = i3);
            }
        }
        else
        {
          i3 = this.data.background;
          i5 = i3 >> 16 & 0xFF | i3 & 0xFF00 | (i3 & 0xFF) << 16;
          i7 = Gdip.Color_new(this.data.alpha << 24 | i5);
          if (i7 == 0)
            SWT.error(2);
          i10 = Gdip.SolidBrush_new(i7);
          if (i10 == 0)
            SWT.error(2);
          Gdip.Color_delete(i7);
          this.data.gdipBrush = (this.data.gdipBgBrush = i10);
        }
      }
      Object localObject2;
      if ((i & 0x4) != 0)
      {
        localObject1 = this.data.font;
        OS.SelectObject(this.handle, ((Font)localObject1).handle);
        localObject2 = new int[1];
        i5 = createGdipFont(this.handle, ((Font)localObject1).handle, j, this.device.fontCollection, null, (int[])localObject2);
        if (localObject2[0] != 0)
          OS.SelectObject(this.handle, localObject2[0]);
        if (this.data.hGDIFont != 0)
          OS.DeleteObject(this.data.hGDIFont);
        this.data.hGDIFont = localObject2[0];
        if (this.data.gdipFont != 0)
          Gdip.Font_delete(this.data.gdipFont);
        this.data.gdipFont = i5;
      }
      if ((i & 0x4000) != 0)
      {
        this.data.gdipXOffset = (this.data.gdipYOffset = 0.0F);
        int i2 = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
        localObject2 = new PointF();
        ((PointF)localObject2).X = (((PointF)localObject2).Y = 1.0F);
        Gdip.Graphics_GetTransform(j, i2);
        Gdip.Matrix_TransformVectors(i2, (PointF)localObject2, 1);
        Gdip.Matrix_delete(i2);
        float f3 = ((PointF)localObject2).X;
        if (f3 < 0.0F)
          f3 = -f3;
        float f4 = this.data.lineWidth * f3;
        if ((f4 == 0.0F) || ((int)f4 % 2 == 1))
          this.data.gdipXOffset = (0.5F / f3);
        f3 = ((PointF)localObject2).Y;
        if (f3 < 0.0F)
          f3 = -f3;
        f4 = this.data.lineWidth * f3;
        if ((f4 == 0.0F) || ((int)f4 % 2 == 1))
          this.data.gdipYOffset = (0.5F / f3);
      }
      return;
    }
    if ((i & 0x79) != 0)
    {
      k = this.data.foreground;
      int m = (int)this.data.lineWidth;
      int[] arrayOfInt = (int[])null;
      int i4 = 0;
      switch (this.data.lineStyle)
      {
      case 1:
        break;
      case 2:
        i4 = 1;
        break;
      case 3:
        i4 = 2;
        break;
      case 4:
        i4 = 3;
        break;
      case 5:
        i4 = 4;
        break;
      case 6:
        if (this.data.lineDashes != null)
        {
          i4 = 7;
          arrayOfInt = new int[this.data.lineDashes.length];
          for (i6 = 0; i6 < arrayOfInt.length; i6++)
            arrayOfInt[i6] = ((int)this.data.lineDashes[i6]);
        }
        break;
      }
      if ((i & 0x8) != 0)
        OS.SetBkMode(this.handle, this.data.lineStyle == 1 ? 2 : 1);
      int i6 = 0;
      switch (this.data.lineJoin)
      {
      case 1:
        i6 = 8192;
        break;
      case 2:
        i6 = 0;
        break;
      case 3:
        i6 = 4096;
      }
      int i8 = 0;
      switch (this.data.lineCap)
      {
      case 2:
        i8 = 0;
        break;
      case 1:
        i8 = 512;
        break;
      case 3:
        i8 = 256;
      }
      i10 = i4 | i6 | i8;
      int i11;
      if ((OS.IsWinCE) || ((m == 0) && (i4 != 7)) || (i10 == 0))
      {
        i11 = OS.CreatePen(i10 & 0xF, m, k);
      }
      else
      {
        LOGBRUSH localLOGBRUSH = new LOGBRUSH();
        localLOGBRUSH.lbStyle = 0;
        localLOGBRUSH.lbColor = k;
        i11 = OS.ExtCreatePen(i10 | 0x10000, Math.max(1, m), localLOGBRUSH, arrayOfInt != null ? arrayOfInt.length : 0, arrayOfInt);
      }
      OS.SelectObject(this.handle, i11);
      this.data.state |= 2048;
      this.data.state &= -8193;
      if (this.data.hPen != 0)
        OS.DeleteObject(this.data.hPen);
      this.data.hPen = (this.data.hOldPen = i11);
    }
    else if ((i & 0x800) != 0)
    {
      OS.SelectObject(this.handle, this.data.hOldPen);
      this.data.state &= -8193;
    }
    else if ((i & 0x2000) != 0)
    {
      this.data.hOldPen = OS.SelectObject(this.handle, OS.GetStockObject(8));
      this.data.state &= -2049;
    }
    if ((i & 0x2) != 0)
    {
      k = OS.CreateSolidBrush(this.data.background);
      OS.SelectObject(this.handle, k);
      this.data.state |= 1024;
      this.data.state &= -4097;
      if (this.data.hBrush != 0)
        OS.DeleteObject(this.data.hBrush);
      this.data.hOldBrush = (this.data.hBrush = k);
    }
    else if ((i & 0x400) != 0)
    {
      OS.SelectObject(this.handle, this.data.hOldBrush);
      this.data.state &= -4097;
    }
    else if ((i & 0x1000) != 0)
    {
      this.data.hOldBrush = OS.SelectObject(this.handle, OS.GetStockObject(5));
      this.data.state &= -1025;
    }
    if ((i & 0x200) != 0)
      OS.SetBkColor(this.handle, this.data.background);
    if ((i & 0x100) != 0)
      OS.SetTextColor(this.handle, this.data.foreground);
    if ((i & 0x4) != 0)
    {
      Font localFont = this.data.font;
      OS.SelectObject(this.handle, localFont.handle);
    }
  }

  public void copyArea(Image paramImage, int paramInt1, int paramInt2)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramImage == null)
      SWT.error(4);
    if ((paramImage.type != 0) || (paramImage.isDisposed()))
      SWT.error(5);
    Rectangle localRectangle = paramImage.getBounds();
    int i = OS.CreateCompatibleDC(this.handle);
    int j = OS.SelectObject(i, paramImage.handle);
    OS.BitBlt(i, 0, 0, localRectangle.width, localRectangle.height, this.handle, paramInt1, paramInt2, 13369376);
    OS.SelectObject(i, j);
    OS.DeleteDC(i);
  }

  public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    copyArea(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, true);
  }

  public void copyArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
  {
    if (this.handle == 0)
      SWT.error(44);
    int i = this.data.hwnd;
    if (i == 0)
    {
      OS.BitBlt(this.handle, paramInt5, paramInt6, paramInt3, paramInt4, this.handle, paramInt1, paramInt2, 13369376);
    }
    else
    {
      RECT localRECT1 = null;
      int j = OS.CreateRectRgn(0, 0, 0, 0);
      if (OS.GetClipRgn(this.handle, j) == 1)
      {
        localRECT1 = new RECT();
        OS.GetRgnBox(j, localRECT1);
      }
      OS.DeleteObject(j);
      RECT localRECT2 = new RECT();
      OS.SetRect(localRECT2, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
      int k = paramBoolean ? 6 : 0;
      int m = OS.ScrollWindowEx(i, paramInt5 - paramInt1, paramInt6 - paramInt2, localRECT2, localRECT1, 0, null, k);
      if ((m == 0) && (OS.IsWinCE))
      {
        OS.BitBlt(this.handle, paramInt5, paramInt6, paramInt3, paramInt4, this.handle, paramInt1, paramInt2, 13369376);
        if (paramBoolean)
        {
          int n = paramInt5 - paramInt1;
          int i1 = paramInt6 - paramInt2;
          int i2 = (paramInt5 + paramInt3 >= paramInt1) && (paramInt1 + paramInt3 >= paramInt5) && (paramInt6 + paramInt4 >= paramInt2) && (paramInt2 + paramInt4 >= paramInt6) ? 0 : 1;
          if (i2 != 0)
          {
            OS.InvalidateRect(i, localRECT2, true);
          }
          else
          {
            int i3;
            if (n != 0)
            {
              i3 = paramInt5 - n;
              if (n < 0)
                i3 = paramInt5 + paramInt3;
              OS.SetRect(localRECT2, i3, paramInt2, i3 + Math.abs(n), paramInt2 + paramInt4);
              OS.InvalidateRect(i, localRECT2, true);
            }
            if (i1 != 0)
            {
              i3 = paramInt6 - i1;
              if (i1 < 0)
                i3 = paramInt6 + paramInt4;
              OS.SetRect(localRECT2, paramInt1, i3, paramInt1 + paramInt3, i3 + Math.abs(i1));
              OS.InvalidateRect(i, localRECT2, true);
            }
          }
        }
      }
    }
  }

  static int createGdipFont(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i = Gdip.Font_new(paramInt1, paramInt2);
    if (i == 0)
      SWT.error(2);
    int j = 0;
    if (!Gdip.Font_IsAvailable(i))
    {
      Gdip.Font_delete(i);
      LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
      OS.GetObject(paramInt2, LOGFONT.sizeof, localLOGFONTA);
      int k = Math.abs(localLOGFONTA.lfHeight);
      int m = 0;
      if (localLOGFONTA.lfWeight == 700)
        m |= 1;
      if (localLOGFONTA.lfItalic != 0)
        m |= 2;
      char[] arrayOfChar1;
      if (OS.IsUnicode)
      {
        arrayOfChar1 = ((LOGFONTW)localLOGFONTA).lfFaceName;
      }
      else
      {
        arrayOfChar1 = new char[32];
        byte[] arrayOfByte = ((LOGFONTA)localLOGFONTA).lfFaceName;
        OS.MultiByteToWideChar(0, 1, arrayOfByte, arrayOfByte.length, arrayOfChar1, arrayOfChar1.length);
      }
      for (int n = 0; n < arrayOfChar1.length; n++)
        if (arrayOfChar1[n] == 0)
          break;
      String str = new String(arrayOfChar1, 0, n);
      if (Compatibility.equalsIgnoreCase(str, "Courier"))
        str = "Courier New";
      char[] arrayOfChar2 = new char[str.length() + 1];
      str.getChars(0, str.length(), arrayOfChar2, 0);
      if (paramInt4 != 0)
      {
        j = Gdip.FontFamily_new(arrayOfChar2, paramInt4);
        if (!Gdip.FontFamily_IsAvailable(j))
        {
          Gdip.FontFamily_delete(j);
          j = Gdip.FontFamily_new(arrayOfChar2, 0);
          if (!Gdip.FontFamily_IsAvailable(j))
          {
            Gdip.FontFamily_delete(j);
            j = 0;
          }
        }
      }
      if (j != 0)
        i = Gdip.Font_new(j, k, m, 2);
      else
        i = Gdip.Font_new(arrayOfChar2, k, m, 2, 0);
      if ((paramArrayOfInt2 != null) && (i != 0))
      {
        int i1 = OS.GetProcessHeap();
        int i2 = OS.HeapAlloc(i1, 8, LOGFONTW.sizeof);
        Gdip.Font_GetLogFontW(i, paramInt3, i2);
        paramArrayOfInt2[0] = OS.CreateFontIndirectW(i2);
        OS.HeapFree(i1, 0, i2);
      }
    }
    if ((paramArrayOfInt1 != null) && (i != 0))
    {
      if (j == 0)
      {
        j = Gdip.FontFamily_new();
        Gdip.Font_GetFamily(i, j);
      }
      paramArrayOfInt1[0] = j;
    }
    else if (j != 0)
    {
      Gdip.FontFamily_delete(j);
    }
    if (i == 0)
      SWT.error(2);
    return i;
  }

  static void destroyGdipBrush(int paramInt)
  {
    int i = Gdip.Brush_GetType(paramInt);
    switch (i)
    {
    case 0:
      Gdip.SolidBrush_delete(paramInt);
      break;
    case 1:
      Gdip.HatchBrush_delete(paramInt);
      break;
    case 4:
      Gdip.LinearGradientBrush_delete(paramInt);
      break;
    case 2:
      Gdip.TextureBrush_delete(paramInt);
    case 3:
    }
  }

  void destroy()
  {
    int i = this.data.gdipGraphics != 0 ? 1 : 0;
    disposeGdip();
    if ((i != 0) && ((this.data.style & 0x8000000) != 0))
      OS.SetLayout(this.handle, OS.GetLayout(this.handle) | 0x1);
    if (this.data.hPen != 0)
    {
      OS.SelectObject(this.handle, OS.GetStockObject(8));
      OS.DeleteObject(this.data.hPen);
      this.data.hPen = 0;
    }
    if (this.data.hBrush != 0)
    {
      OS.SelectObject(this.handle, OS.GetStockObject(5));
      OS.DeleteObject(this.data.hBrush);
      this.data.hBrush = 0;
    }
    int j = this.data.hNullBitmap;
    if (j != 0)
    {
      OS.SelectObject(this.handle, j);
      this.data.hNullBitmap = 0;
    }
    Image localImage = this.data.image;
    if (localImage != null)
      localImage.memGC = null;
    if (this.drawable != null)
      this.drawable.internal_dispose_GC(this.handle, this.data);
    this.drawable = null;
    this.handle = 0;
    this.data.image = null;
    this.data.ps = null;
    this.data = null;
  }

  void disposeGdip()
  {
    if (this.data.gdipPen != 0)
      Gdip.Pen_delete(this.data.gdipPen);
    if (this.data.gdipBgBrush != 0)
      destroyGdipBrush(this.data.gdipBgBrush);
    if (this.data.gdipFgBrush != 0)
      destroyGdipBrush(this.data.gdipFgBrush);
    if (this.data.gdipFont != 0)
      Gdip.Font_delete(this.data.gdipFont);
    if (this.data.hGDIFont != 0)
      OS.DeleteObject(this.data.hGDIFont);
    if (this.data.gdipGraphics != 0)
      Gdip.Graphics_delete(this.data.gdipGraphics);
    this.data.gdipGraphics = (this.data.gdipBrush = this.data.gdipBgBrush = this.data.gdipFgBrush = this.data.gdipFont = this.data.gdipPen = this.data.hGDIFont = 0);
  }

  public void drawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(22777);
    if (paramInt3 < 0)
    {
      paramInt1 += paramInt3;
      paramInt3 = -paramInt3;
    }
    if (paramInt4 < 0)
    {
      paramInt2 += paramInt4;
      paramInt4 = -paramInt4;
    }
    if ((paramInt3 == 0) || (paramInt4 == 0) || (paramInt6 == 0))
      return;
    int i = this.data.gdipGraphics;
    int m;
    if (i != 0)
    {
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      if (paramInt3 == paramInt4)
      {
        Gdip.Graphics_DrawArc(i, this.data.gdipPen, paramInt1, paramInt2, paramInt3, paramInt4, -paramInt5, -paramInt6);
      }
      else
      {
        int j = Gdip.GraphicsPath_new(0);
        if (j == 0)
          SWT.error(2);
        m = Gdip.Matrix_new(paramInt3, 0.0F, 0.0F, paramInt4, paramInt1, paramInt2);
        if (m == 0)
          SWT.error(2);
        Gdip.GraphicsPath_AddArc(j, 0.0F, 0.0F, 1.0F, 1.0F, -paramInt5, -paramInt6);
        Gdip.GraphicsPath_Transform(j, m);
        Gdip.Graphics_DrawPath(i, this.data.gdipPen, j);
        Gdip.Matrix_delete(m);
        Gdip.GraphicsPath_delete(j);
      }
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      paramInt1--;
    int n;
    int i1;
    int i2;
    if (OS.IsWinCE)
    {
      if (paramInt6 < 0)
      {
        paramInt5 += paramInt6;
        paramInt6 = -paramInt6;
      }
      if (paramInt6 > 360)
        paramInt6 = 360;
      int[] arrayOfInt = new int[(paramInt6 + 1) * 2];
      m = 2 * paramInt1 + paramInt3;
      n = 2 * paramInt2 + paramInt4;
      i1 = 0;
      for (i2 = 0; i2 <= paramInt6; i2++)
      {
        arrayOfInt[(i1++)] = (Compatibility.cos(paramInt5 + i2, paramInt3) + m >> 1);
        arrayOfInt[(i1++)] = (n - Compatibility.sin(paramInt5 + i2, paramInt4) >> 1);
      }
      OS.Polyline(this.handle, arrayOfInt, arrayOfInt.length / 2);
    }
    else
    {
      int k;
      if ((paramInt6 >= 360) || (paramInt6 <= -360))
      {
        k = n = paramInt1 + paramInt3;
        m = i1 = paramInt2 + paramInt4 / 2;
      }
      else
      {
        int i3 = paramInt6 < 0 ? 1 : 0;
        paramInt6 += paramInt5;
        if (i3 != 0)
        {
          i2 = paramInt5;
          paramInt5 = paramInt6;
          paramInt6 = i2;
        }
        k = Compatibility.cos(paramInt5, paramInt3) + paramInt1 + paramInt3 / 2;
        m = -1 * Compatibility.sin(paramInt5, paramInt4) + paramInt2 + paramInt4 / 2;
        n = Compatibility.cos(paramInt6, paramInt3) + paramInt1 + paramInt3 / 2;
        i1 = -1 * Compatibility.sin(paramInt6, paramInt4) + paramInt2 + paramInt4 / 2;
      }
      OS.Arc(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1, k, m, n, i1);
    }
  }

  public void drawFocus(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.data.uiState & 0x1) != 0)
      return;
    this.data.focusDrawn = true;
    int i = this.handle;
    int j = 0;
    int k = this.data.gdipGraphics;
    if (k != 0)
    {
      int m = 0;
      Gdip.Graphics_SetPixelOffsetMode(k, 3);
      int n = Gdip.Region_new();
      if (n == 0)
        SWT.error(2);
      Gdip.Graphics_GetClip(k, n);
      if (!Gdip.Region_IsInfinite(n, k))
        m = Gdip.Region_GetHRGN(n, k);
      Gdip.Region_delete(n);
      Gdip.Graphics_SetPixelOffsetMode(k, 4);
      float[] arrayOfFloat = (float[])null;
      int i1 = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
      if (i1 == 0)
        SWT.error(2);
      Gdip.Graphics_GetTransform(k, i1);
      if (!Gdip.Matrix_IsIdentity(i1))
      {
        arrayOfFloat = new float[6];
        Gdip.Matrix_GetElements(i1, arrayOfFloat);
      }
      Gdip.Matrix_delete(i1);
      i = Gdip.Graphics_GetHDC(k);
      j = OS.SaveDC(i);
      if (arrayOfFloat != null)
      {
        OS.SetGraphicsMode(i, 2);
        OS.SetWorldTransform(i, arrayOfFloat);
      }
      if (m != 0)
      {
        OS.SelectClipRgn(i, m);
        OS.DeleteObject(m);
      }
    }
    OS.SetBkColor(i, 16777215);
    OS.SetTextColor(i, 0);
    RECT localRECT = new RECT();
    OS.SetRect(localRECT, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    OS.DrawFocusRect(i, localRECT);
    if (k != 0)
    {
      OS.RestoreDC(i, j);
      Gdip.Graphics_ReleaseHDC(k, i);
    }
    else
    {
      this.data.state &= -769;
    }
  }

  public void drawImage(Image paramImage, int paramInt1, int paramInt2)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramImage == null)
      SWT.error(4);
    if (paramImage.isDisposed())
      SWT.error(5);
    drawImage(paramImage, 0, 0, -1, -1, paramInt1, paramInt2, -1, -1, true);
  }

  public void drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramInt3 == 0) || (paramInt4 == 0) || (paramInt7 == 0) || (paramInt8 == 0))
      return;
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt3 < 0) || (paramInt4 < 0) || (paramInt7 < 0) || (paramInt8 < 0))
      SWT.error(5);
    if (paramImage == null)
      SWT.error(4);
    if (paramImage.isDisposed())
      SWT.error(5);
    drawImage(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, false);
  }

  void drawImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    if (this.data.gdipGraphics != 0)
    {
      int[] arrayOfInt = paramImage.createGdipImage();
      int i = arrayOfInt[0];
      int j = Gdip.Image_GetWidth(i);
      int k = Gdip.Image_GetHeight(i);
      if (paramBoolean)
      {
        paramInt3 = paramInt7 = j;
        paramInt4 = paramInt8 = k;
      }
      else
      {
        if ((paramInt1 + paramInt3 > j) || (paramInt2 + paramInt4 > k))
          SWT.error(5);
        paramBoolean = (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == paramInt7) && (paramInt7 == j) && (paramInt4 == paramInt8) && (paramInt8 == k);
      }
      Rect localRect = new Rect();
      localRect.X = paramInt5;
      localRect.Y = paramInt6;
      localRect.Width = paramInt7;
      localRect.Height = paramInt8;
      int m = Gdip.ImageAttributes_new();
      Gdip.ImageAttributes_SetWrapMode(m, 3);
      if (this.data.alpha != 255)
      {
        float[] arrayOfFloat = { 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, this.data.alpha / 255.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F };
        Gdip.ImageAttributes_SetColorMatrix(m, arrayOfFloat, 0, 1);
      }
      int n = 0;
      if ((this.data.style & 0x8000000) != 0)
      {
        n = Gdip.Graphics_Save(this.data.gdipGraphics);
        Gdip.Graphics_ScaleTransform(this.data.gdipGraphics, -1.0F, 1.0F, 0);
        Gdip.Graphics_TranslateTransform(this.data.gdipGraphics, -2 * paramInt5 - paramInt7, 0.0F, 0);
      }
      Gdip.Graphics_DrawImage(this.data.gdipGraphics, i, localRect, paramInt1, paramInt2, paramInt3, paramInt4, 2, m, 0, 0);
      if ((this.data.style & 0x8000000) != 0)
        Gdip.Graphics_Restore(this.data.gdipGraphics, n);
      Gdip.ImageAttributes_delete(m);
      Gdip.Bitmap_delete(i);
      if (arrayOfInt[1] != 0)
      {
        int i1 = OS.GetProcessHeap();
        OS.HeapFree(i1, 0, arrayOfInt[1]);
      }
      return;
    }
    switch (paramImage.type)
    {
    case 0:
      drawBitmap(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
      break;
    case 1:
      drawIcon(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
  }

  void drawIcon(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    int i = OS.GetDeviceCaps(this.handle, 2);
    int j = 1;
    int k = 3;
    int m = 0;
    int n = 0;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
    {
      if ((OS.GetLayout(this.handle) & 0x1) != 0)
      {
        k |= 16;
        localObject = new POINT();
        OS.GetWindowOrgEx(this.handle, (POINT)localObject);
        m = ((POINT)localObject).x;
        n = ((POINT)localObject).y;
      }
    }
    else if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
      j = (OS.GetLayout(this.handle) & 0x1) == 0 ? 1 : 0;
    if ((paramBoolean) && (i != 2) && (j != 0))
    {
      if ((m != 0) || (n != 0))
        OS.SetWindowOrgEx(this.handle, 0, 0, null);
      OS.DrawIconEx(this.handle, paramInt5 - m, paramInt6 - n, paramImage.handle, 0, 0, 0, 0, k);
      if ((m != 0) || (n != 0))
        OS.SetWindowOrgEx(this.handle, m, n, null);
      return;
    }
    Object localObject = new ICONINFO();
    if (OS.IsWinCE)
      Image.GetIconInfo(paramImage, (ICONINFO)localObject);
    else
      OS.GetIconInfo(paramImage.handle, (ICONINFO)localObject);
    int i1 = ((ICONINFO)localObject).hbmColor;
    if (i1 == 0)
      i1 = ((ICONINFO)localObject).hbmMask;
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(i1, BITMAP.sizeof, localBITMAP);
    int i2 = localBITMAP.bmWidth;
    int i3 = localBITMAP.bmHeight;
    if (i1 == ((ICONINFO)localObject).hbmMask)
      i3 /= 2;
    if (paramBoolean)
    {
      paramInt3 = paramInt7 = i2;
      paramInt4 = paramInt8 = i3;
    }
    int i4 = (paramInt1 + paramInt3 <= i2) && (paramInt2 + paramInt4 <= i3) ? 0 : 1;
    if (i4 == 0)
    {
      paramBoolean = (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == paramInt7) && (paramInt4 == paramInt8) && (paramInt3 == i2) && (paramInt4 == i3);
      if (j == 0)
      {
        drawBitmapMask(paramImage, ((ICONINFO)localObject).hbmColor, ((ICONINFO)localObject).hbmMask, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, i2, i3, false);
      }
      else if ((paramBoolean) && (i != 2))
      {
        if ((m != 0) || (n != 0))
          OS.SetWindowOrgEx(this.handle, 0, 0, null);
        OS.DrawIconEx(this.handle, paramInt5 - m, paramInt6 - n, paramImage.handle, 0, 0, 0, 0, k);
        if ((m != 0) || (n != 0))
          OS.SetWindowOrgEx(this.handle, m, n, null);
      }
      else
      {
        ICONINFO localICONINFO = new ICONINFO();
        localICONINFO.fIcon = true;
        int i5 = OS.CreateCompatibleDC(this.handle);
        int i6 = OS.CreateCompatibleDC(this.handle);
        int i7 = paramInt2;
        int i8 = ((ICONINFO)localObject).hbmColor;
        if (i8 == 0)
        {
          i8 = ((ICONINFO)localObject).hbmMask;
          i7 += i3;
        }
        int i9 = OS.SelectObject(i5, i8);
        localICONINFO.hbmColor = OS.CreateCompatibleBitmap(i5, paramInt7, paramInt8);
        if (localICONINFO.hbmColor == 0)
          SWT.error(2);
        int i10 = OS.SelectObject(i6, localICONINFO.hbmColor);
        int i11 = (!paramBoolean) && ((paramInt3 != paramInt7) || (paramInt4 != paramInt8)) ? 1 : 0;
        if (i11 != 0)
        {
          if (!OS.IsWinCE)
            OS.SetStretchBltMode(i6, 3);
          OS.StretchBlt(i6, 0, 0, paramInt7, paramInt8, i5, paramInt1, i7, paramInt3, paramInt4, 13369376);
        }
        else
        {
          OS.BitBlt(i6, 0, 0, paramInt7, paramInt8, i5, paramInt1, i7, 13369376);
        }
        OS.SelectObject(i5, ((ICONINFO)localObject).hbmMask);
        localICONINFO.hbmMask = OS.CreateBitmap(paramInt7, paramInt8, 1, 1, null);
        if (localICONINFO.hbmMask == 0)
          SWT.error(2);
        OS.SelectObject(i6, localICONINFO.hbmMask);
        if (i11 != 0)
          OS.StretchBlt(i6, 0, 0, paramInt7, paramInt8, i5, paramInt1, paramInt2, paramInt3, paramInt4, 13369376);
        else
          OS.BitBlt(i6, 0, 0, paramInt7, paramInt8, i5, paramInt1, paramInt2, 13369376);
        if (i == 2)
        {
          OS.SelectObject(i5, localICONINFO.hbmColor);
          OS.SelectObject(i6, localICONINFO.hbmMask);
          drawBitmapTransparentByClipping(i5, i6, 0, 0, paramInt7, paramInt8, paramInt5, paramInt6, paramInt7, paramInt8, true, paramInt7, paramInt8);
          OS.SelectObject(i5, i9);
          OS.SelectObject(i6, i10);
        }
        else
        {
          OS.SelectObject(i5, i9);
          OS.SelectObject(i6, i10);
          int i12 = OS.CreateIconIndirect(localICONINFO);
          if (i12 == 0)
            SWT.error(2);
          if ((m != 0) || (n != 0))
            OS.SetWindowOrgEx(this.handle, 0, 0, null);
          OS.DrawIconEx(this.handle, paramInt5 - m, paramInt6 - n, i12, paramInt7, paramInt8, 0, 0, k);
          if ((m != 0) || (n != 0))
            OS.SetWindowOrgEx(this.handle, m, n, null);
          OS.DestroyIcon(i12);
        }
        OS.DeleteObject(localICONINFO.hbmMask);
        OS.DeleteObject(localICONINFO.hbmColor);
        OS.DeleteDC(i6);
        OS.DeleteDC(i5);
      }
    }
    OS.DeleteObject(((ICONINFO)localObject).hbmMask);
    if (((ICONINFO)localObject).hbmColor != 0)
      OS.DeleteObject(((ICONINFO)localObject).hbmColor);
    if (i4 != 0)
      SWT.error(5);
  }

  void drawBitmap(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
  {
    BITMAP localBITMAP = new BITMAP();
    OS.GetObject(paramImage.handle, BITMAP.sizeof, localBITMAP);
    int i = localBITMAP.bmWidth;
    int j = localBITMAP.bmHeight;
    if (paramBoolean)
    {
      paramInt3 = paramInt7 = i;
      paramInt4 = paramInt8 = j;
    }
    else
    {
      if ((paramInt1 + paramInt3 > i) || (paramInt2 + paramInt4 > j))
        SWT.error(5);
      paramBoolean = (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == paramInt7) && (paramInt7 == i) && (paramInt4 == paramInt8) && (paramInt8 == j);
    }
    int k = 0;
    GC localGC = paramImage.memGC;
    if ((localGC != null) && (!localGC.isDisposed()))
    {
      localGC.flush();
      k = 1;
      GCData localGCData = localGC.data;
      if (localGCData.hNullBitmap != 0)
      {
        OS.SelectObject(localGC.handle, localGCData.hNullBitmap);
        localGCData.hNullBitmap = 0;
      }
    }
    if ((paramImage.alpha != -1) || (paramImage.alphaData != null))
      drawBitmapAlpha(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, localBITMAP, i, j);
    else if (paramImage.transparentPixel != -1)
      drawBitmapTransparent(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, localBITMAP, i, j);
    else
      drawBitmap(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, localBITMAP, i, j);
    if (k != 0)
    {
      int m = OS.SelectObject(localGC.handle, paramImage.handle);
      localGC.data.hNullBitmap = m;
    }
  }

  void drawBitmapAlpha(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean, BITMAP paramBITMAP, int paramInt9, int paramInt10)
  {
    if (paramImage.alpha == 0)
      return;
    if (paramImage.alpha == 255)
    {
      drawBitmap(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, paramBITMAP, paramInt9, paramInt10);
      return;
    }
    int i = (OS.IsWinNT) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)) ? 1 : 0;
    int j = OS.GetDeviceCaps(this.handle, 2) == 2 ? 1 : 0;
    if ((i != 0) && (j != 0))
    {
      int k = OS.GetDeviceCaps(this.handle, 120);
      if (k != 0)
        if (paramImage.alpha != -1)
          i = (k & 0x1) != 0 ? 1 : 0;
        else
          i = (k & 0x2) != 0 ? 1 : 0;
    }
    if (i != 0)
    {
      localObject = new BLENDFUNCTION();
      ((BLENDFUNCTION)localObject).BlendOp = 0;
      m = OS.CreateCompatibleDC(this.handle);
      n = OS.SelectObject(m, paramImage.handle);
      if (paramImage.alpha != -1)
      {
        ((BLENDFUNCTION)localObject).SourceConstantAlpha = ((byte)paramImage.alpha);
        OS.AlphaBlend(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, m, paramInt1, paramInt2, paramInt3, paramInt4, (BLENDFUNCTION)localObject);
      }
      else
      {
        i1 = Image.createDIB(paramInt3, paramInt4, 32);
        if (i1 == 0)
          SWT.error(2);
        i2 = OS.CreateCompatibleDC(this.handle);
        i3 = OS.SelectObject(i2, i1);
        BITMAP localBITMAP1 = new BITMAP();
        OS.GetObject(i1, BITMAP.sizeof, localBITMAP1);
        OS.BitBlt(i2, 0, 0, paramInt3, paramInt4, m, paramInt1, paramInt2, 13369376);
        byte[] arrayOfByte1 = new byte[localBITMAP1.bmWidthBytes * localBITMAP1.bmHeight];
        OS.MoveMemory(arrayOfByte1, localBITMAP1.bmBits, arrayOfByte1.length);
        i6 = paramInt9 - paramInt3;
        i7 = paramInt2 * paramInt9 + paramInt1;
        int i8 = 0;
        byte[] arrayOfByte2 = paramImage.alphaData;
        for (int i10 = 0; i10 < paramInt4; i10++)
        {
          for (int i11 = 0; i11 < paramInt3; i11++)
          {
            i12 = arrayOfByte2[(i7++)] & 0xFF;
            i13 = (arrayOfByte1[(i8 + 0)] & 0xFF) * i12 + 128;
            i13 = i13 + (i13 >> 8) >> 8;
            i14 = (arrayOfByte1[(i8 + 1)] & 0xFF) * i12 + 128;
            i14 = i14 + (i14 >> 8) >> 8;
            i15 = (arrayOfByte1[(i8 + 2)] & 0xFF) * i12 + 128;
            i15 = i15 + (i15 >> 8) >> 8;
            arrayOfByte1[(i8 + 0)] = ((byte)i13);
            arrayOfByte1[(i8 + 1)] = ((byte)i14);
            arrayOfByte1[(i8 + 2)] = ((byte)i15);
            arrayOfByte1[(i8 + 3)] = ((byte)i12);
            i8 += 4;
          }
          i7 += i6;
        }
        OS.MoveMemory(localBITMAP1.bmBits, arrayOfByte1, arrayOfByte1.length);
        ((BLENDFUNCTION)localObject).SourceConstantAlpha = -1;
        ((BLENDFUNCTION)localObject).AlphaFormat = 1;
        OS.AlphaBlend(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, i2, 0, 0, paramInt3, paramInt4, (BLENDFUNCTION)localObject);
        OS.SelectObject(i2, i3);
        OS.DeleteDC(i2);
        OS.DeleteObject(i1);
      }
      OS.SelectObject(m, n);
      OS.DeleteDC(m);
      return;
    }
    Object localObject = getClipping();
    localObject = ((Rectangle)localObject).intersection(new Rectangle(paramInt5, paramInt6, paramInt7, paramInt8));
    if (((Rectangle)localObject).isEmpty())
      return;
    int m = paramInt1 + (((Rectangle)localObject).x - paramInt5) * paramInt3 / paramInt7;
    int n = paramInt1 + (((Rectangle)localObject).x + ((Rectangle)localObject).width - paramInt5) * paramInt3 / paramInt7;
    int i1 = paramInt2 + (((Rectangle)localObject).y - paramInt6) * paramInt4 / paramInt8;
    int i2 = paramInt2 + (((Rectangle)localObject).y + ((Rectangle)localObject).height - paramInt6) * paramInt4 / paramInt8;
    paramInt5 = ((Rectangle)localObject).x;
    paramInt6 = ((Rectangle)localObject).y;
    paramInt7 = ((Rectangle)localObject).width;
    paramInt8 = ((Rectangle)localObject).height;
    paramInt1 = m;
    paramInt2 = i1;
    paramInt3 = Math.max(1, n - m);
    paramInt4 = Math.max(1, i2 - i1);
    int i3 = OS.CreateCompatibleDC(this.handle);
    int i4 = OS.SelectObject(i3, paramImage.handle);
    int i5 = OS.CreateCompatibleDC(this.handle);
    int i6 = Image.createDIB(Math.max(paramInt3, paramInt7), Math.max(paramInt4, paramInt8), 32);
    if (i6 == 0)
      SWT.error(2);
    int i7 = OS.SelectObject(i5, i6);
    BITMAP localBITMAP2 = new BITMAP();
    OS.GetObject(i6, BITMAP.sizeof, localBITMAP2);
    int i9 = localBITMAP2.bmWidthBytes * localBITMAP2.bmHeight;
    OS.BitBlt(i5, 0, 0, paramInt7, paramInt8, this.handle, paramInt5, paramInt6, 13369376);
    byte[] arrayOfByte3 = new byte[i9];
    OS.MoveMemory(arrayOfByte3, localBITMAP2.bmBits, i9);
    OS.BitBlt(i5, 0, 0, paramInt3, paramInt4, i3, paramInt1, paramInt2, 13369376);
    byte[] arrayOfByte4 = new byte[i9];
    OS.MoveMemory(arrayOfByte4, localBITMAP2.bmBits, i9);
    int i12 = paramImage.alpha;
    int i13 = paramImage.alpha == -1 ? 1 : 0;
    int i17;
    if (i13 != 0)
    {
      i14 = paramInt9 - paramInt3;
      i15 = localBITMAP2.bmWidthBytes - paramInt3 * 4;
      i16 = paramInt2 * paramInt9 + paramInt1;
      i17 = 3;
      byte[] arrayOfByte5 = paramImage.alphaData;
      for (int i18 = 0; i18 < paramInt4; i18++)
      {
        for (int i19 = 0; i19 < paramInt3; i19++)
        {
          arrayOfByte4[i17] = arrayOfByte5[(i16++)];
          i17 += 4;
        }
        i16 += i14;
        i17 += i15;
      }
    }
    OS.MoveMemory(localBITMAP2.bmBits, arrayOfByte4, i9);
    if (((OS.IsWinCE) && ((paramInt7 > paramInt3) || (paramInt8 > paramInt4))) || ((!OS.IsWinNT) && (!OS.IsWinCE)) || (j != 0))
    {
      i14 = OS.CreateCompatibleDC(this.handle);
      i15 = Image.createDIB(paramInt7, paramInt8, 32);
      if (i15 == 0)
        SWT.error(2);
      i16 = OS.SelectObject(i14, i15);
      if ((!paramBoolean) && ((paramInt3 != paramInt7) || (paramInt4 != paramInt8)))
      {
        if (!OS.IsWinCE)
          OS.SetStretchBltMode(i5, 3);
        OS.StretchBlt(i14, 0, 0, paramInt7, paramInt8, i5, 0, 0, paramInt3, paramInt4, 13369376);
      }
      else
      {
        OS.BitBlt(i14, 0, 0, paramInt7, paramInt8, i5, 0, 0, 13369376);
      }
      OS.BitBlt(i5, 0, 0, paramInt7, paramInt8, i14, 0, 0, 13369376);
      OS.SelectObject(i14, i16);
      OS.DeleteObject(i15);
      OS.DeleteDC(i14);
    }
    else if ((!paramBoolean) && ((paramInt3 != paramInt7) || (paramInt4 != paramInt8)))
    {
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(i5, 3);
      OS.StretchBlt(i5, 0, 0, paramInt7, paramInt8, i5, 0, 0, paramInt3, paramInt4, 13369376);
    }
    else
    {
      OS.BitBlt(i5, 0, 0, paramInt7, paramInt8, i5, 0, 0, 13369376);
    }
    OS.MoveMemory(arrayOfByte4, localBITMAP2.bmBits, i9);
    int i14 = localBITMAP2.bmWidthBytes - paramInt7 * 4;
    int i15 = 0;
    for (int i16 = 0; i16 < paramInt8; i16++)
    {
      for (i17 = 0; i17 < paramInt7; i17++)
      {
        if (i13 != 0)
          i12 = arrayOfByte4[(i15 + 3)] & 0xFF;
        int tmp1476_1474 = i15;
        byte[] tmp1476_1472 = arrayOfByte3;
        tmp1476_1472[tmp1476_1474] = ((byte)(tmp1476_1472[tmp1476_1474] + ((arrayOfByte4[i15] & 0xFF) - (arrayOfByte3[i15] & 0xFF)) * i12 / 255));
        int tmp1513_1512 = (i15 + 1);
        byte[] tmp1513_1507 = arrayOfByte3;
        tmp1513_1507[tmp1513_1512] = ((byte)(tmp1513_1507[tmp1513_1512] + ((arrayOfByte4[(i15 + 1)] & 0xFF) - (arrayOfByte3[(i15 + 1)] & 0xFF)) * i12 / 255));
        int tmp1554_1553 = (i15 + 2);
        byte[] tmp1554_1548 = arrayOfByte3;
        tmp1554_1548[tmp1554_1553] = ((byte)(tmp1554_1548[tmp1554_1553] + ((arrayOfByte4[(i15 + 2)] & 0xFF) - (arrayOfByte3[(i15 + 2)] & 0xFF)) * i12 / 255));
        i15 += 4;
      }
      i15 += i14;
    }
    OS.MoveMemory(localBITMAP2.bmBits, arrayOfByte3, i9);
    OS.BitBlt(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, i5, 0, 0, 13369376);
    OS.SelectObject(i5, i7);
    OS.DeleteDC(i5);
    OS.DeleteObject(i6);
    OS.SelectObject(i3, i4);
    OS.DeleteDC(i3);
  }

  void drawBitmapTransparentByClipping(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean, int paramInt11, int paramInt12)
  {
    int i = OS.CreateRectRgn(0, 0, 0, 0);
    for (int j = 0; j < paramInt12; j++)
      for (int k = 0; k < paramInt11; k++)
        if (OS.GetPixel(paramInt2, k, j) == 0)
        {
          int n = OS.CreateRectRgn(k, j, k + 1, j + 1);
          OS.CombineRgn(i, i, n, 2);
          OS.DeleteObject(n);
        }
    if ((paramInt9 != paramInt5) || (paramInt10 != paramInt6))
    {
      j = OS.GetRegionData(i, 0, null);
      int[] arrayOfInt = new int[j / 4];
      OS.GetRegionData(i, j, arrayOfInt);
      float[] arrayOfFloat = { paramInt9 / paramInt5, 0.0F, 0.0F, paramInt10 / paramInt6, 0.0F, 0.0F };
      i2 = OS.ExtCreateRegion(arrayOfFloat, j, arrayOfInt);
      OS.DeleteObject(i);
      i = i2;
    }
    OS.OffsetRgn(i, paramInt7, paramInt8);
    j = OS.CreateRectRgn(0, 0, 0, 0);
    int m = OS.GetClipRgn(this.handle, j);
    if (m == 1)
      OS.CombineRgn(i, i, j, 1);
    OS.SelectClipRgn(this.handle, i);
    int i1 = 0;
    if (!OS.IsWinCE)
    {
      i1 = OS.GetROP2(this.handle);
    }
    else
    {
      i1 = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, i1);
    }
    int i2 = i1 == 7 ? 6684742 : 13369376;
    if ((!paramBoolean) && ((paramInt5 != paramInt9) || (paramInt6 != paramInt10)))
    {
      int i3 = 0;
      if (!OS.IsWinCE)
        i3 = OS.SetStretchBltMode(this.handle, 3);
      OS.StretchBlt(this.handle, paramInt7, paramInt8, paramInt9, paramInt10, paramInt1, paramInt3, paramInt4, paramInt5, paramInt6, i2);
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(this.handle, i3);
    }
    else
    {
      OS.BitBlt(this.handle, paramInt7, paramInt8, paramInt9, paramInt10, paramInt1, paramInt3, paramInt4, i2);
    }
    OS.SelectClipRgn(this.handle, m == 1 ? j : 0);
    OS.DeleteObject(j);
    OS.DeleteObject(i);
  }

  void drawBitmapMask(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean1, int paramInt11, int paramInt12, boolean paramBoolean2)
  {
    int i = paramInt4;
    if (paramInt1 == 0)
    {
      paramInt1 = paramInt2;
      i += paramInt12;
    }
    int j = OS.CreateCompatibleDC(this.handle);
    int k = OS.SelectObject(j, paramInt1);
    int m = this.handle;
    int n = paramInt7;
    int i1 = paramInt8;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    if (paramBoolean2)
    {
      i2 = OS.CreateCompatibleDC(this.handle);
      i3 = OS.CreateCompatibleBitmap(this.handle, paramInt9, paramInt10);
      i4 = OS.SelectObject(i2, i3);
      OS.BitBlt(i2, 0, 0, paramInt9, paramInt10, this.handle, paramInt7, paramInt8, 13369376);
      m = i2;
      n = i1 = 0;
    }
    else
    {
      i5 = OS.SetBkColor(this.handle, 16777215);
      i6 = OS.SetTextColor(this.handle, 0);
    }
    if ((!paramBoolean1) && ((paramInt5 != paramInt9) || (paramInt6 != paramInt10)))
    {
      int i7 = 0;
      if (!OS.IsWinCE)
        i7 = OS.SetStretchBltMode(this.handle, 3);
      OS.StretchBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, i, paramInt5, paramInt6, 6684742);
      OS.SelectObject(j, paramInt2);
      OS.StretchBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, paramInt4, paramInt5, paramInt6, 8913094);
      OS.SelectObject(j, paramInt1);
      OS.StretchBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, i, paramInt5, paramInt6, 6684742);
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(this.handle, i7);
    }
    else
    {
      OS.BitBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, i, 6684742);
      OS.SetTextColor(m, 0);
      OS.SelectObject(j, paramInt2);
      OS.BitBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, paramInt4, 8913094);
      OS.SelectObject(j, paramInt1);
      OS.BitBlt(m, n, i1, paramInt9, paramInt10, j, paramInt3, i, 6684742);
    }
    if (paramBoolean2)
    {
      OS.BitBlt(this.handle, paramInt7, paramInt8, paramInt9, paramInt10, i2, 0, 0, 13369376);
      OS.SelectObject(i2, i4);
      OS.DeleteDC(i2);
      OS.DeleteObject(i3);
    }
    else
    {
      OS.SetBkColor(this.handle, i5);
      OS.SetTextColor(this.handle, i6);
    }
    OS.SelectObject(j, k);
    OS.DeleteDC(j);
  }

  void drawBitmapTransparent(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean, BITMAP paramBITMAP, int paramInt9, int paramInt10)
  {
    int i = paramBITMAP.bmBits != 0 ? 1 : 0;
    int j = paramImage.handle;
    int k = OS.CreateCompatibleDC(this.handle);
    int m = OS.SelectObject(k, j);
    Object localObject1 = (byte[])null;
    int n = paramImage.transparentColor;
    int i1;
    int i2;
    int i3;
    int i4;
    int i5;
    if (n == -1)
    {
      i1 = 0;
      i2 = 0;
      i3 = 0;
      i4 = 0;
      if (paramBITMAP.bmBitsPixel <= 8)
      {
        Object localObject2;
        if (i != 0)
        {
          int i8;
          int i9;
          if (OS.IsWinCE)
          {
            byte[] arrayOfByte1 = new byte[1];
            OS.MoveMemory(arrayOfByte1, paramBITMAP.bmBits, 1);
            int i6 = arrayOfByte1[0];
            i8 = 255 << 8 - paramBITMAP.bmBitsPixel & 0xFF;
            arrayOfByte1[0] = ((byte)(paramImage.transparentPixel << 8 - paramBITMAP.bmBitsPixel | arrayOfByte1[0] & (i8 ^ 0xFFFFFFFF)));
            OS.MoveMemory(paramBITMAP.bmBits, arrayOfByte1, 1);
            i9 = OS.GetPixel(k, 0, 0);
            arrayOfByte1[0] = i6;
            OS.MoveMemory(paramBITMAP.bmBits, arrayOfByte1, 1);
            i1 = (i9 & 0xFF0000) >> 16;
            i2 = (i9 & 0xFF00) >> 8;
            i3 = i9 & 0xFF;
          }
          else
          {
            i5 = 1 << paramBITMAP.bmBitsPixel;
            localObject2 = new byte[i5 * 4];
            OS.GetDIBColorTable(k, 0, i5, (byte[])localObject2);
            i8 = paramImage.transparentPixel * 4;
            for (i9 = 0; i9 < localObject2.length; i9 += 4)
              if ((i9 != i8) && (localObject2[i8] == localObject2[i9]) && (localObject2[(i8 + 1)] == localObject2[(i9 + 1)]) && (localObject2[(i8 + 2)] == localObject2[(i9 + 2)]))
              {
                i4 = 1;
                break;
              }
            if (i4 != 0)
            {
              byte[] arrayOfByte3 = new byte[localObject2.length];
              i3 = i2 = i1 = 'ÿ';
              arrayOfByte3[i8] = ((byte)i1);
              arrayOfByte3[(i8 + 1)] = ((byte)i2);
              arrayOfByte3[(i8 + 2)] = ((byte)i3);
              OS.SetDIBColorTable(k, 0, i5, arrayOfByte3);
              localObject1 = localObject2;
            }
            else
            {
              i1 = localObject2[i8] & 0xFF;
              i2 = localObject2[(i8 + 1)] & 0xFF;
              i3 = localObject2[(i8 + 2)] & 0xFF;
            }
          }
        }
        else
        {
          i5 = 1 << paramBITMAP.bmBitsPixel;
          localObject2 = new BITMAPINFOHEADER();
          ((BITMAPINFOHEADER)localObject2).biSize = BITMAPINFOHEADER.sizeof;
          ((BITMAPINFOHEADER)localObject2).biPlanes = paramBITMAP.bmPlanes;
          ((BITMAPINFOHEADER)localObject2).biBitCount = paramBITMAP.bmBitsPixel;
          byte[] arrayOfByte2 = new byte[BITMAPINFOHEADER.sizeof + i5 * 4];
          OS.MoveMemory(arrayOfByte2, (BITMAPINFOHEADER)localObject2, BITMAPINFOHEADER.sizeof);
          if (OS.IsWinCE)
            SWT.error(20);
          OS.GetDIBits(k, paramImage.handle, 0, 0, 0, arrayOfByte2, 0);
          int i10 = BITMAPINFOHEADER.sizeof + 4 * paramImage.transparentPixel;
          i3 = arrayOfByte2[(i10 + 2)] & 0xFF;
          i2 = arrayOfByte2[(i10 + 1)] & 0xFF;
          i1 = arrayOfByte2[i10] & 0xFF;
        }
      }
      else
      {
        i5 = paramImage.transparentPixel;
        switch (paramBITMAP.bmBitsPixel)
        {
        case 16:
          i1 = (i5 & 0x1F) << 3;
          i2 = (i5 & 0x3E0) >> 2;
          i3 = (i5 & 0x7C00) >> 7;
          break;
        case 24:
          i1 = (i5 & 0xFF0000) >> 16;
          i2 = (i5 & 0xFF00) >> 8;
          i3 = i5 & 0xFF;
          break;
        case 32:
          i1 = (i5 & 0xFF000000) >>> 24;
          i2 = (i5 & 0xFF0000) >> 16;
          i3 = (i5 & 0xFF00) >> 8;
        }
      }
      n = i1 << 16 | i2 << 8 | i3;
      if (i4 == 0)
        paramImage.transparentColor = n;
    }
    if (OS.IsWinCE)
    {
      OS.TransparentImage(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, k, paramInt1, paramInt2, paramInt3, paramInt4, n);
    }
    else if ((localObject1 == null) && (OS.IsWinNT) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      i1 = OS.SetStretchBltMode(this.handle, 3);
      OS.TransparentBlt(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, k, paramInt1, paramInt2, paramInt3, paramInt4, n);
      OS.SetStretchBltMode(this.handle, i1);
    }
    else
    {
      i1 = OS.CreateCompatibleDC(this.handle);
      i2 = OS.CreateBitmap(paramInt9, paramInt10, 1, 1, null);
      i3 = OS.SelectObject(i1, i2);
      OS.SetBkColor(k, n);
      OS.BitBlt(i1, 0, 0, paramInt9, paramInt10, k, 0, 0, 13369376);
      if (localObject1 != null)
        OS.SetDIBColorTable(k, 0, 1 << paramBITMAP.bmBitsPixel, (byte[])localObject1);
      if (OS.GetDeviceCaps(this.handle, 2) == 2)
      {
        drawBitmapTransparentByClipping(k, i1, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean, paramInt9, paramInt10);
      }
      else
      {
        i4 = OS.CreateCompatibleDC(this.handle);
        i5 = OS.CreateCompatibleBitmap(this.handle, paramInt7, paramInt8);
        int i7 = OS.SelectObject(i4, i5);
        OS.BitBlt(i4, 0, 0, paramInt7, paramInt8, this.handle, paramInt5, paramInt6, 13369376);
        if ((!paramBoolean) && ((paramInt3 != paramInt7) || (paramInt4 != paramInt8)))
        {
          if (!OS.IsWinCE)
            OS.SetStretchBltMode(i4, 3);
          OS.StretchBlt(i4, 0, 0, paramInt7, paramInt8, k, paramInt1, paramInt2, paramInt3, paramInt4, 6684742);
          OS.StretchBlt(i4, 0, 0, paramInt7, paramInt8, i1, paramInt1, paramInt2, paramInt3, paramInt4, 8913094);
          OS.StretchBlt(i4, 0, 0, paramInt7, paramInt8, k, paramInt1, paramInt2, paramInt3, paramInt4, 6684742);
        }
        else
        {
          OS.BitBlt(i4, 0, 0, paramInt7, paramInt8, k, paramInt1, paramInt2, 6684742);
          OS.BitBlt(i4, 0, 0, paramInt7, paramInt8, i1, paramInt1, paramInt2, 8913094);
          OS.BitBlt(i4, 0, 0, paramInt7, paramInt8, k, paramInt1, paramInt2, 6684742);
        }
        OS.BitBlt(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, i4, 0, 0, 13369376);
        OS.SelectObject(i4, i7);
        OS.DeleteDC(i4);
        OS.DeleteObject(i5);
      }
      OS.SelectObject(i1, i3);
      OS.DeleteDC(i1);
      OS.DeleteObject(i2);
    }
    OS.SelectObject(k, m);
    if (j != paramImage.handle)
      OS.DeleteObject(j);
    OS.DeleteDC(k);
  }

  void drawBitmap(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean, BITMAP paramBITMAP, int paramInt9, int paramInt10)
  {
    int i = OS.CreateCompatibleDC(this.handle);
    int j = OS.SelectObject(i, paramImage.handle);
    int k = 0;
    if (!OS.IsWinCE)
    {
      k = OS.GetROP2(this.handle);
    }
    else
    {
      k = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, k);
    }
    int m = k == 7 ? 6684742 : 13369376;
    if ((!paramBoolean) && ((paramInt3 != paramInt7) || (paramInt4 != paramInt8)))
    {
      int n = 0;
      if (!OS.IsWinCE)
        n = OS.SetStretchBltMode(this.handle, 3);
      OS.StretchBlt(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, i, paramInt1, paramInt2, paramInt3, paramInt4, m);
      if (!OS.IsWinCE)
        OS.SetStretchBltMode(this.handle, n);
    }
    else
    {
      OS.BitBlt(this.handle, paramInt5, paramInt6, paramInt7, paramInt8, i, paramInt1, paramInt2, m);
    }
    OS.SelectObject(i, j);
    OS.DeleteDC(i);
  }

  public void drawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(22777);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      Gdip.Graphics_DrawLine(i, this.data.gdipPen, paramInt1, paramInt2, paramInt3, paramInt4);
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
    {
      paramInt1--;
      paramInt3--;
    }
    if (OS.IsWinCE)
    {
      int[] arrayOfInt = { paramInt1, paramInt2, paramInt3, paramInt4 };
      OS.Polyline(this.handle, arrayOfInt, arrayOfInt.length / 2);
    }
    else
    {
      OS.MoveToEx(this.handle, paramInt1, paramInt2, 0);
      OS.LineTo(this.handle, paramInt3, paramInt4);
    }
    if (this.data.lineWidth <= 1.0F)
      OS.SetPixel(this.handle, paramInt3, paramInt4, this.data.foreground);
  }

  public void drawOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(22777);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      Gdip.Graphics_DrawEllipse(i, this.data.gdipPen, paramInt1, paramInt2, paramInt3, paramInt4);
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      paramInt1--;
    OS.Ellipse(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
  }

  public void drawPath(Path paramPath)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramPath == null)
      SWT.error(4);
    if (paramPath.handle == 0)
      SWT.error(5);
    initGdip();
    checkGC(22777);
    int i = this.data.gdipGraphics;
    Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
    Gdip.Graphics_DrawPath(i, this.data.gdipPen, paramPath.handle);
    Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
  }

  public void drawPoint(int paramInt1, int paramInt2)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.gdipGraphics != 0)
    {
      checkGC(22777);
      Gdip.Graphics_FillRectangle(this.data.gdipGraphics, getFgBrush(), paramInt1, paramInt2, 1, 1);
      return;
    }
    OS.SetPixel(this.handle, paramInt1, paramInt2, this.data.foreground);
  }

  public void drawPolygon(int[] paramArrayOfInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramArrayOfInt == null)
      SWT.error(4);
    checkGC(22777);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      Gdip.Graphics_DrawPolygon(i, this.data.gdipPen, paramArrayOfInt, paramArrayOfInt.length / 2);
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    int j;
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      for (j = 0; j < paramArrayOfInt.length; j += 2)
        paramArrayOfInt[j] -= 1;
    OS.Polygon(this.handle, paramArrayOfInt, paramArrayOfInt.length / 2);
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      for (j = 0; j < paramArrayOfInt.length; j += 2)
        paramArrayOfInt[j] += 1;
  }

  public void drawPolyline(int[] paramArrayOfInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramArrayOfInt == null)
      SWT.error(4);
    checkGC(22777);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      Gdip.Graphics_DrawLines(i, this.data.gdipPen, paramArrayOfInt, paramArrayOfInt.length / 2);
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      for (j = 0; j < paramArrayOfInt.length; j += 2)
        paramArrayOfInt[j] -= 1;
    OS.Polyline(this.handle, paramArrayOfInt, paramArrayOfInt.length / 2);
    int j = paramArrayOfInt.length;
    if ((j >= 2) && (this.data.lineWidth <= 1.0F))
      OS.SetPixel(this.handle, paramArrayOfInt[(j - 2)], paramArrayOfInt[(j - 1)], this.data.foreground);
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      for (int k = 0; k < paramArrayOfInt.length; k += 2)
        paramArrayOfInt[k] += 1;
  }

  public void drawRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(22777);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      if (paramInt3 < 0)
      {
        paramInt1 += paramInt3;
        paramInt3 = -paramInt3;
      }
      if (paramInt4 < 0)
      {
        paramInt2 += paramInt4;
        paramInt4 = -paramInt4;
      }
      Gdip.Graphics_TranslateTransform(i, this.data.gdipXOffset, this.data.gdipYOffset, 0);
      Gdip.Graphics_DrawRectangle(i, this.data.gdipPen, paramInt1, paramInt2, paramInt3, paramInt4);
      Gdip.Graphics_TranslateTransform(i, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
      return;
    }
    if ((this.data.style & 0x8000000) != 0)
      if (this.data.lineWidth > 1.0F)
      {
        if (this.data.lineWidth % 2.0F == 1.0F)
          paramInt1++;
      }
      else if ((this.data.hPen != 0) && (OS.GetObject(this.data.hPen, 0, 0) != LOGPEN.sizeof))
        paramInt1++;
    OS.Rectangle(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
  }

  public void drawRectangle(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    drawRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void drawRoundRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(22777);
    if (this.data.gdipGraphics != 0)
    {
      drawRoundRectangleGdip(this.data.gdipGraphics, this.data.gdipPen, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      return;
    }
    if (((this.data.style & 0x8000000) != 0) && (this.data.lineWidth != 0.0F) && (this.data.lineWidth % 2.0F == 0.0F))
      paramInt1--;
    if (OS.IsWinCE)
    {
      if ((paramInt3 == 0) || (paramInt4 == 0))
        return;
      if ((paramInt5 == 0) || (paramInt6 == 0))
      {
        drawRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
        return;
      }
      if (paramInt3 < 0)
      {
        paramInt1 += paramInt3;
        paramInt3 = -paramInt3;
      }
      if (paramInt4 < 0)
      {
        paramInt2 += paramInt4;
        paramInt4 = -paramInt4;
      }
      if (paramInt5 < 0)
        paramInt5 = -paramInt5;
      if (paramInt6 < 0)
        paramInt6 = -paramInt6;
      if (paramInt5 > paramInt3)
        paramInt5 = paramInt3;
      if (paramInt6 > paramInt4)
        paramInt6 = paramInt4;
      if (paramInt5 < paramInt3)
      {
        drawLine(paramInt1 + paramInt5 / 2, paramInt2, paramInt1 + paramInt3 - paramInt5 / 2, paramInt2);
        drawLine(paramInt1 + paramInt5 / 2, paramInt2 + paramInt4, paramInt1 + paramInt3 - paramInt5 / 2, paramInt2 + paramInt4);
      }
      if (paramInt6 < paramInt4)
      {
        drawLine(paramInt1, paramInt2 + paramInt6 / 2, paramInt1, paramInt2 + paramInt4 - paramInt6 / 2);
        drawLine(paramInt1 + paramInt3, paramInt2 + paramInt6 / 2, paramInt1 + paramInt3, paramInt2 + paramInt4 - paramInt6 / 2);
      }
      if ((paramInt5 != 0) && (paramInt6 != 0))
      {
        drawArc(paramInt1, paramInt2, paramInt5, paramInt6, 90, 90);
        drawArc(paramInt1 + paramInt3 - paramInt5, paramInt2, paramInt5, paramInt6, 0, 90);
        drawArc(paramInt1 + paramInt3 - paramInt5, paramInt2 + paramInt4 - paramInt6, paramInt5, paramInt6, 0, -90);
        drawArc(paramInt1, paramInt2 + paramInt4 - paramInt6, paramInt5, paramInt6, 180, 90);
      }
    }
    else
    {
      OS.RoundRect(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1, paramInt5, paramInt6);
    }
  }

  void drawRoundRectangleGdip(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    int i = paramInt3;
    int j = paramInt4;
    int k = paramInt5;
    int m = paramInt6;
    int n = paramInt7;
    int i1 = paramInt8;
    if (k < 0)
    {
      k = 0 - k;
      i -= k;
    }
    if (m < 0)
    {
      m = 0 - m;
      j -= m;
    }
    if (n < 0)
      n = 0 - n;
    if (i1 < 0)
      i1 = 0 - i1;
    Gdip.Graphics_TranslateTransform(paramInt1, this.data.gdipXOffset, this.data.gdipYOffset, 0);
    if ((n == 0) || (i1 == 0))
    {
      Gdip.Graphics_DrawRectangle(paramInt1, this.data.gdipPen, paramInt3, paramInt4, paramInt5, paramInt6);
    }
    else
    {
      int i2 = Gdip.GraphicsPath_new(0);
      if (i2 == 0)
        SWT.error(2);
      if (k > n)
      {
        if (m > i1)
        {
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j, n, i1, 0.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j, n, i1, -90.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j + m - i1, n, i1, -180.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j + m - i1, n, i1, -270.0F, -90.0F);
        }
        else
        {
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j, n, m, -270.0F, -180.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j, n, m, -90.0F, -180.0F);
        }
      }
      else if (m > i1)
      {
        Gdip.GraphicsPath_AddArc(i2, i, j, k, i1, 0.0F, -180.0F);
        Gdip.GraphicsPath_AddArc(i2, i, j + m - i1, k, i1, -180.0F, -180.0F);
      }
      else
      {
        Gdip.GraphicsPath_AddArc(i2, i, j, k, m, 0.0F, 360.0F);
      }
      Gdip.GraphicsPath_CloseFigure(i2);
      Gdip.Graphics_DrawPath(paramInt1, paramInt2, i2);
      Gdip.GraphicsPath_delete(i2);
    }
    Gdip.Graphics_TranslateTransform(paramInt1, -this.data.gdipXOffset, -this.data.gdipYOffset, 0);
  }

  public void drawString(String paramString, int paramInt1, int paramInt2)
  {
    drawString(paramString, paramInt1, paramInt2, false);
  }

  public void drawString(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramString == null)
      SWT.error(4);
    int i = paramString.length();
    if (i == 0)
      return;
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    int j = this.data.gdipGraphics;
    if (j != 0)
    {
      checkGC(0x5 | (paramBoolean ? 0 : 2));
      drawText(j, paramString, paramInt1, paramInt2, paramBoolean ? 1 : 0, null);
      return;
    }
    int k = 0;
    if (OS.IsWinCE)
    {
      k = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, k);
    }
    else
    {
      k = OS.GetROP2(this.handle);
    }
    checkGC(772);
    int m = OS.SetBkMode(this.handle, paramBoolean ? 1 : 2);
    RECT localRECT = null;
    SIZE localSIZE = null;
    int n = 0;
    if ((this.data.style & 0x8000000) != 0)
    {
      if (!paramBoolean)
      {
        localSIZE = new SIZE();
        OS.GetTextExtentPoint32W(this.handle, arrayOfChar, i, localSIZE);
        localRECT = new RECT();
        localRECT.left = paramInt1;
        localRECT.right = (paramInt1 + localSIZE.cx);
        localRECT.top = paramInt2;
        localRECT.bottom = (paramInt2 + localSIZE.cy);
        n = 4;
      }
      paramInt1--;
    }
    if (k != 7)
    {
      OS.ExtTextOutW(this.handle, paramInt1, paramInt2, n, localRECT, arrayOfChar, i, null);
    }
    else
    {
      int i1 = OS.GetTextColor(this.handle);
      int i2;
      if (paramBoolean)
      {
        if (localSIZE == null)
        {
          localSIZE = new SIZE();
          OS.GetTextExtentPoint32W(this.handle, arrayOfChar, i, localSIZE);
        }
        i2 = localSIZE.cx;
        int i3 = localSIZE.cy;
        int i4 = OS.CreateCompatibleBitmap(this.handle, i2, i3);
        if (i4 == 0)
          SWT.error(2);
        int i5 = OS.CreateCompatibleDC(this.handle);
        int i6 = OS.SelectObject(i5, i4);
        OS.PatBlt(i5, 0, 0, i2, i3, 66);
        OS.SetBkMode(i5, 1);
        OS.SetTextColor(i5, i1);
        OS.SelectObject(i5, OS.GetCurrentObject(this.handle, 6));
        OS.ExtTextOutW(i5, 0, 0, 0, null, arrayOfChar, i, null);
        OS.BitBlt(this.handle, paramInt1, paramInt2, i2, i3, i5, 0, 0, 6684742);
        OS.SelectObject(i5, i6);
        OS.DeleteDC(i5);
        OS.DeleteObject(i4);
      }
      else
      {
        i2 = OS.GetBkColor(this.handle);
        OS.SetTextColor(this.handle, i1 ^ i2);
        OS.ExtTextOutW(this.handle, paramInt1, paramInt2, n, localRECT, arrayOfChar, i, null);
        OS.SetTextColor(this.handle, i1);
      }
    }
    OS.SetBkMode(this.handle, m);
  }

  public void drawText(String paramString, int paramInt1, int paramInt2)
  {
    drawText(paramString, paramInt1, paramInt2, 6);
  }

  public void drawText(String paramString, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = 6;
    if (paramBoolean)
      i |= 1;
    drawText(paramString, paramInt1, paramInt2, i);
  }

  public void drawText(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramString == null)
      SWT.error(4);
    if (paramString.length() == 0)
      return;
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      checkGC(0x5 | ((paramInt3 & 0x1) != 0 ? 0 : 2));
      drawText(i, paramString, paramInt1, paramInt2, paramInt3, null);
      return;
    }
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, false);
    int j = localTCHAR.length();
    if (j == 0)
      return;
    RECT localRECT = new RECT();
    int k = OS.IsWin95 ? 32767 : 117440511;
    OS.SetRect(localRECT, paramInt1, paramInt2, k, k);
    int m = 0;
    if ((paramInt3 & 0x2) == 0)
      m |= 32;
    if ((paramInt3 & 0x4) != 0)
      m |= 64;
    if ((paramInt3 & 0x8) == 0)
      m |= 2048;
    if (((paramInt3 & 0x8) != 0) && ((this.data.uiState & 0x2) != 0))
      m |= 1048576;
    int n = 0;
    if (OS.IsWinCE)
    {
      n = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, n);
    }
    else
    {
      n = OS.GetROP2(this.handle);
    }
    checkGC(772);
    int i1 = OS.SetBkMode(this.handle, (paramInt3 & 0x1) != 0 ? 1 : 2);
    if (n != 7)
    {
      OS.DrawText(this.handle, localTCHAR, j, localRECT, m);
    }
    else
    {
      int i2 = OS.GetTextColor(this.handle);
      int i3;
      if ((paramInt3 & 0x1) != 0)
      {
        OS.DrawText(this.handle, localTCHAR, localTCHAR.length(), localRECT, m | 0x400);
        i3 = localRECT.right - localRECT.left;
        int i4 = localRECT.bottom - localRECT.top;
        int i5 = OS.CreateCompatibleBitmap(this.handle, i3, i4);
        if (i5 == 0)
          SWT.error(2);
        int i6 = OS.CreateCompatibleDC(this.handle);
        int i7 = OS.SelectObject(i6, i5);
        OS.PatBlt(i6, 0, 0, i3, i4, 66);
        OS.SetBkMode(i6, 1);
        OS.SetTextColor(i6, i2);
        OS.SelectObject(i6, OS.GetCurrentObject(this.handle, 6));
        OS.SetRect(localRECT, 0, 0, 32767, 32767);
        OS.DrawText(i6, localTCHAR, j, localRECT, m);
        OS.BitBlt(this.handle, paramInt1, paramInt2, i3, i4, i6, 0, 0, 6684742);
        OS.SelectObject(i6, i7);
        OS.DeleteDC(i6);
        OS.DeleteObject(i5);
      }
      else
      {
        i3 = OS.GetBkColor(this.handle);
        OS.SetTextColor(this.handle, i2 ^ i3);
        OS.DrawText(this.handle, localTCHAR, j, localRECT, m);
        OS.SetTextColor(this.handle, i2);
      }
    }
    OS.SetBkMode(this.handle, i1);
  }

  boolean useGDIP(int paramInt, char[] paramArrayOfChar)
  {
    if ((OS.IsWinCE) || (!OS.IsUnicode))
      return false;
    short[] arrayOfShort = new short[paramArrayOfChar.length];
    OS.GetGlyphIndicesW(paramInt, paramArrayOfChar, paramArrayOfChar.length, arrayOfShort, 1);
    for (int i = 0; i < arrayOfShort.length; i++)
      if (arrayOfShort[i] == -1)
        switch (paramArrayOfChar[i])
        {
        case '\t':
        case '\n':
        case '\r':
          break;
        case '\013':
        case '\f':
        default:
          return true;
        }
    return false;
  }

  void drawText(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, Point paramPoint)
  {
    int i = paramString.length();
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    int j = Gdip.Graphics_GetHDC(paramInt1);
    int k = this.data.hGDIFont;
    if ((k == 0) && (this.data.font != null))
      k = this.data.font.handle;
    int m = 0;
    if (k != 0)
      m = OS.SelectObject(j, k);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(j, localTEXTMETRICA);
    boolean bool = useGDIP(j, arrayOfChar);
    if (k != 0)
      OS.SelectObject(j, m);
    Gdip.Graphics_ReleaseHDC(paramInt1, j);
    if (bool)
    {
      drawTextGDIP(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramPoint == null, paramPoint);
      return;
    }
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = paramInt2;
    int i4 = paramInt3;
    int i5 = 0;
    int i6 = -1;
    if ((paramInt4 & 0xE) != 0)
    {
      int i7 = localTEXTMETRICA.tmAveCharWidth * 8;
      while (n < i)
      {
        int i8 = arrayOfChar[(i2++)] = arrayOfChar[(n++)];
        int i9;
        RectF localRectF2;
        switch (i8)
        {
        case 9:
          if ((paramInt4 & 0x4) != 0)
          {
            i9 = i2 - i1 - 1;
            localRectF2 = drawText(paramInt1, arrayOfChar, i1, i9, i3, i4, paramInt4, i6, localTEXTMETRICA, paramPoint == null);
            i3 = (int)(i3 + Math.ceil(localRectF2.Width));
            i3 = paramInt2 + ((i3 - paramInt2) / i7 + 1) * i7;
            i6 = -1;
            i1 = i2;
          }
          break;
        case 38:
          if ((paramInt4 & 0x8) != 0)
            if (n == i)
            {
              i2--;
            }
            else if (arrayOfChar[n] == '&')
            {
              n++;
            }
            else
            {
              i2--;
              i6 = i2 - i1;
            }
          break;
        case 10:
        case 13:
          if ((paramInt4 & 0x2) != 0)
          {
            i9 = i2 - i1 - 1;
            if ((i8 == 13) && (i2 != i) && (arrayOfChar[i2] == '\n'))
            {
              i2++;
              n++;
            }
            localRectF2 = drawText(paramInt1, arrayOfChar, i1, i9, i3, i4, paramInt4, i6, localTEXTMETRICA, paramPoint == null);
            i4 = (int)(i4 + Math.ceil(localRectF2.Height));
            i5 = Math.max(i5, i3 + (int)Math.ceil(localRectF2.Width));
            i3 = paramInt2;
            i6 = -1;
            i1 = i2;
          }
          break;
        }
      }
      i = i2;
    }
    RectF localRectF1 = drawText(paramInt1, arrayOfChar, i1, i - i1, i3, i4, paramInt4, i6, localTEXTMETRICA, paramPoint == null);
    if (paramPoint != null)
    {
      i4 = (int)(i4 + Math.ceil(localRectF1.Height));
      i5 = Math.max(i5, i3 + (int)Math.ceil(localRectF1.Width));
      paramPoint.x = i5;
      paramPoint.y = i4;
    }
  }

  RectF drawText(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, TEXTMETRIC paramTEXTMETRIC, boolean paramBoolean)
  {
    int i = (paramBoolean) && (paramInt7 != -1) && ((this.data.uiState & 0x2) == 0) ? 1 : 0;
    int j = (paramBoolean) && (i == 0) && ((paramInt6 & 0x1) != 0) && ((this.data.style & 0x8000000) == 0) && ((paramInt6 & 0x2) == 0) ? 0 : 1;
    if (paramInt3 <= 0)
    {
      RectF localRectF1 = null;
      if (j != 0)
      {
        localRectF1 = new RectF();
        localRectF1.Height = paramTEXTMETRIC.tmHeight;
      }
      return localRectF1;
    }
    int k = paramInt3 * 3 / 2 + 16;
    GCP_RESULTS localGCP_RESULTS = new GCP_RESULTS();
    localGCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
    localGCP_RESULTS.nGlyphs = k;
    int m = OS.GetProcessHeap();
    int n = localGCP_RESULTS.lpDx = OS.HeapAlloc(m, 8, k * 4);
    int i1 = localGCP_RESULTS.lpGlyphs = OS.HeapAlloc(m, 8, k * 2);
    int i2 = 0;
    int i3 = 50;
    if (i != 0)
      i2 = localGCP_RESULTS.lpOrder = OS.HeapAlloc(m, 8, k * 4);
    int i4 = Gdip.Graphics_GetHDC(paramInt1);
    int i5 = this.data.hGDIFont;
    if ((i5 == 0) && (this.data.font != null))
      i5 = this.data.font.handle;
    int i6 = 0;
    if (i5 != 0)
      i6 = OS.SelectObject(i4, i5);
    if (paramInt2 != 0)
    {
      char[] arrayOfChar = new char[paramInt3];
      System.arraycopy(paramArrayOfChar, paramInt2, arrayOfChar, 0, paramInt3);
      paramArrayOfChar = arrayOfChar;
    }
    if ((this.data.style & 0x8000000) != 0)
      OS.SetLayout(i4, OS.GetLayout(i4) | 0x1);
    OS.GetCharacterPlacementW(i4, paramArrayOfChar, paramInt3, 0, localGCP_RESULTS, i3);
    if ((this.data.style & 0x8000000) != 0)
      OS.SetLayout(i4, OS.GetLayout(i4) & 0xFFFFFFFE);
    if (i5 != 0)
      OS.SelectObject(i4, i6);
    Gdip.Graphics_ReleaseHDC(paramInt1, i4);
    k = localGCP_RESULTS.nGlyphs;
    int i7 = paramInt4;
    int i8 = paramInt5 + paramTEXTMETRIC.tmAscent;
    int[] arrayOfInt1 = new int[k];
    OS.MoveMemory(arrayOfInt1, localGCP_RESULTS.lpDx, k * 4);
    float[] arrayOfFloat = new float[arrayOfInt1.length * 2];
    int i9 = 0;
    int i10 = 0;
    while (i9 < arrayOfInt1.length)
    {
      arrayOfFloat[(i10++)] = i7;
      arrayOfFloat[(i10++)] = i8;
      i7 += arrayOfInt1[i9];
      i9++;
    }
    RectF localRectF2 = null;
    if (j != 0)
    {
      localRectF2 = new RectF();
      Gdip.Graphics_MeasureDriverString(paramInt1, i1, k, this.data.gdipFont, arrayOfFloat, 0, 0, localRectF2);
    }
    if (paramBoolean)
    {
      if ((paramInt6 & 0x1) == 0)
        Gdip.Graphics_FillRectangle(paramInt1, this.data.gdipBrush, paramInt4, paramInt5, (int)Math.ceil(localRectF2.Width), (int)Math.ceil(localRectF2.Height));
      i10 = 0;
      int i11 = getFgBrush();
      if ((this.data.style & 0x8000000) != 0)
      {
        switch (Gdip.Brush_GetType(i11))
        {
        case 4:
          Gdip.LinearGradientBrush_ScaleTransform(i11, -1.0F, 1.0F, 0);
          Gdip.LinearGradientBrush_TranslateTransform(i11, -2 * paramInt4 - localRectF2.Width, 0.0F, 0);
          break;
        case 2:
          Gdip.TextureBrush_ScaleTransform(i11, -1.0F, 1.0F, 0);
          Gdip.TextureBrush_TranslateTransform(i11, -2 * paramInt4 - localRectF2.Width, 0.0F, 0);
        case 3:
        }
        i10 = Gdip.Graphics_Save(paramInt1);
        Gdip.Graphics_ScaleTransform(paramInt1, -1.0F, 1.0F, 0);
        Gdip.Graphics_TranslateTransform(paramInt1, -2 * paramInt4 - localRectF2.Width, 0.0F, 0);
      }
      Gdip.Graphics_DrawDriverString(paramInt1, i1, localGCP_RESULTS.nGlyphs, this.data.gdipFont, i11, arrayOfFloat, 0, 0);
      if ((this.data.style & 0x8000000) != 0)
      {
        switch (Gdip.Brush_GetType(i11))
        {
        case 4:
          Gdip.LinearGradientBrush_ResetTransform(i11);
          break;
        case 2:
          Gdip.TextureBrush_ResetTransform(i11);
        case 3:
        }
        Gdip.Graphics_Restore(paramInt1, i10);
      }
      if (i != 0)
      {
        int i12 = Gdip.Pen_new(i11, 1.0F);
        if (i12 != 0)
        {
          int[] arrayOfInt2 = new int[1];
          OS.MoveMemory(arrayOfInt2, localGCP_RESULTS.lpOrder + paramInt7 * 4, 4);
          int i13;
          int i14;
          if ((this.data.style & 0x8000000) != 0)
          {
            i13 = (int)Math.ceil(localRectF2.Width) - (int)arrayOfFloat[(arrayOfInt2[0] * 2)] + 2 * paramInt4;
            i14 = i13 - arrayOfInt1[arrayOfInt2[0]];
          }
          else
          {
            i13 = (int)arrayOfFloat[(arrayOfInt2[0] * 2)];
            i14 = i13 + arrayOfInt1[arrayOfInt2[0]];
          }
          int i15 = paramInt5 + paramTEXTMETRIC.tmAscent + 2;
          int i16 = Gdip.Graphics_GetSmoothingMode(paramInt1);
          Gdip.Graphics_SetSmoothingMode(paramInt1, 3);
          Gdip.Graphics_DrawLine(paramInt1, i12, i13, i15, i14, i15);
          Gdip.Graphics_SetSmoothingMode(paramInt1, i16);
          Gdip.Pen_delete(i12);
        }
      }
    }
    if (i2 != 0)
      OS.HeapFree(m, 0, i2);
    OS.HeapFree(m, 0, i1);
    OS.HeapFree(m, 0, n);
    return localRectF2;
  }

  void drawTextGDIP(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, Point paramPoint)
  {
    int i = (paramBoolean) && ((paramInt4 & 0x1) != 0) ? 0 : 1;
    int j = paramString.length();
    char[] arrayOfChar;
    if (j != 0)
    {
      arrayOfChar = new char[j];
      paramString.getChars(0, j, arrayOfChar, 0);
    }
    else
    {
      if (paramBoolean)
        return;
      arrayOfChar = new char[] { ' ' };
    }
    PointF localPointF = new PointF();
    int k = Gdip.StringFormat_Clone(Gdip.StringFormat_GenericTypographic());
    int m = Gdip.StringFormat_GetFormatFlags(k) | 0x800;
    if ((this.data.style & 0x8000000) != 0)
      m |= 1;
    Gdip.StringFormat_SetFormatFlags(k, m);
    float[] arrayOfFloat = (paramInt4 & 0x4) != 0 ? new float[] { measureSpace(this.data.gdipFont, k) * 8.0F } : new float[1];
    Gdip.StringFormat_SetTabStops(k, 0.0F, arrayOfFloat.length, arrayOfFloat);
    int n = (paramInt4 & 0x8) != 0 ? 1 : 0;
    if (((paramInt4 & 0x8) != 0) && ((this.data.uiState & 0x2) != 0))
      n = 2;
    Gdip.StringFormat_SetHotkeyPrefix(k, n);
    RectF localRectF = null;
    if (i != 0)
    {
      localRectF = new RectF();
      Gdip.Graphics_MeasureString(paramInt1, arrayOfChar, arrayOfChar.length, this.data.gdipFont, localPointF, k, localRectF);
    }
    if (paramBoolean)
    {
      if ((paramInt4 & 0x1) == 0)
        Gdip.Graphics_FillRectangle(paramInt1, this.data.gdipBrush, paramInt2, paramInt3, (int)Math.ceil(localRectF.Width), (int)Math.ceil(localRectF.Height));
      int i1 = 0;
      int i2 = getFgBrush();
      if ((this.data.style & 0x8000000) != 0)
      {
        switch (Gdip.Brush_GetType(i2))
        {
        case 4:
          Gdip.LinearGradientBrush_ScaleTransform(i2, -1.0F, 1.0F, 0);
          Gdip.LinearGradientBrush_TranslateTransform(i2, -2 * paramInt2, 0.0F, 0);
          break;
        case 2:
          Gdip.TextureBrush_ScaleTransform(i2, -1.0F, 1.0F, 0);
          Gdip.TextureBrush_TranslateTransform(i2, -2 * paramInt2, 0.0F, 0);
        case 3:
        }
        i1 = Gdip.Graphics_Save(paramInt1);
        Gdip.Graphics_ScaleTransform(paramInt1, -1.0F, 1.0F, 0);
        Gdip.Graphics_TranslateTransform(paramInt1, -2 * paramInt2, 0.0F, 0);
      }
      localPointF.X = paramInt2;
      localPointF.Y = paramInt3;
      Gdip.Graphics_DrawString(paramInt1, arrayOfChar, arrayOfChar.length, this.data.gdipFont, localPointF, k, i2);
      if ((this.data.style & 0x8000000) != 0)
      {
        switch (Gdip.Brush_GetType(i2))
        {
        case 4:
          Gdip.LinearGradientBrush_ResetTransform(i2);
          break;
        case 2:
          Gdip.TextureBrush_ResetTransform(i2);
        case 3:
        }
        Gdip.Graphics_Restore(paramInt1, i1);
      }
    }
    Gdip.StringFormat_delete(k);
    if (j == 0)
      localRectF.Width = 0.0F;
    if (paramPoint != null)
    {
      paramPoint.x = ((int)Math.ceil(localRectF.Width));
      paramPoint.y = ((int)Math.ceil(localRectF.Height));
    }
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject == this) || (((paramObject instanceof GC)) && (this.handle == ((GC)paramObject).handle));
  }

  public void fillArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(9218);
    if (paramInt3 < 0)
    {
      paramInt1 += paramInt3;
      paramInt3 = -paramInt3;
    }
    if (paramInt4 < 0)
    {
      paramInt2 += paramInt4;
      paramInt4 = -paramInt4;
    }
    if ((paramInt3 == 0) || (paramInt4 == 0) || (paramInt6 == 0))
      return;
    int i = this.data.gdipGraphics;
    int j;
    if (i != 0)
    {
      if (paramInt3 == paramInt4)
      {
        Gdip.Graphics_FillPie(i, this.data.gdipBrush, paramInt1, paramInt2, paramInt3, paramInt4, -paramInt5, -paramInt6);
      }
      else
      {
        j = Gdip.Graphics_Save(i);
        Gdip.Graphics_TranslateTransform(i, paramInt1, paramInt2, 0);
        Gdip.Graphics_ScaleTransform(i, paramInt3, paramInt4, 0);
        Gdip.Graphics_FillPie(i, this.data.gdipBrush, 0, 0, 1, 1, -paramInt5, -paramInt6);
        Gdip.Graphics_Restore(i, j);
      }
      return;
    }
    if ((this.data.style & 0x8000000) != 0)
      paramInt1--;
    int m;
    int n;
    int i1;
    int i2;
    if (OS.IsWinCE)
    {
      if (paramInt6 < 0)
      {
        paramInt5 += paramInt6;
        paramInt6 = -paramInt6;
      }
      j = 1;
      if (paramInt6 >= 360)
      {
        paramInt6 = 360;
        j = 0;
      }
      int[] arrayOfInt = new int[(paramInt6 + 1) * 2 + (j != 0 ? 4 : 0)];
      m = 2 * paramInt1 + paramInt3;
      n = 2 * paramInt2 + paramInt4;
      i1 = j != 0 ? 2 : 0;
      for (i2 = 0; i2 <= paramInt6; i2++)
      {
        arrayOfInt[(i1++)] = (Compatibility.cos(paramInt5 + i2, paramInt3) + m >> 1);
        arrayOfInt[(i1++)] = (n - Compatibility.sin(paramInt5 + i2, paramInt4) >> 1);
      }
      if (j != 0)
      {
        int tmp359_358 = (m >> 1);
        arrayOfInt[(arrayOfInt.length - 2)] = tmp359_358;
        arrayOfInt[0] = tmp359_358;
        int tmp376_375 = (n >> 1);
        arrayOfInt[(arrayOfInt.length - 1)] = tmp376_375;
        arrayOfInt[1] = tmp376_375;
      }
      OS.Polygon(this.handle, arrayOfInt, arrayOfInt.length / 2);
    }
    else
    {
      int k;
      if ((paramInt6 >= 360) || (paramInt6 <= -360))
      {
        j = m = paramInt1 + paramInt3;
        k = n = paramInt2 + paramInt4 / 2;
      }
      else
      {
        i2 = paramInt6 < 0 ? 1 : 0;
        paramInt6 += paramInt5;
        if (i2 != 0)
        {
          i1 = paramInt5;
          paramInt5 = paramInt6;
          paramInt6 = i1;
        }
        j = Compatibility.cos(paramInt5, paramInt3) + paramInt1 + paramInt3 / 2;
        k = -1 * Compatibility.sin(paramInt5, paramInt4) + paramInt2 + paramInt4 / 2;
        m = Compatibility.cos(paramInt6, paramInt3) + paramInt1 + paramInt3 / 2;
        n = -1 * Compatibility.sin(paramInt6, paramInt4) + paramInt2 + paramInt4 / 2;
      }
      OS.Pie(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1, j, k, m, n);
    }
  }

  public void fillGradientRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramInt3 == 0) || (paramInt4 == 0))
      return;
    RGB localRGB1 = getBackground().getRGB();
    RGB localRGB2 = getForeground().getRGB();
    RGB localRGB3 = localRGB2;
    RGB localRGB4 = localRGB1;
    int i = 0;
    if (paramInt3 < 0)
    {
      paramInt1 += paramInt3;
      paramInt3 = -paramInt3;
      if (!paramBoolean)
        i = 1;
    }
    if (paramInt4 < 0)
    {
      paramInt2 += paramInt4;
      paramInt4 = -paramInt4;
      if (paramBoolean)
        i = 1;
    }
    if (i != 0)
    {
      localRGB3 = localRGB1;
      localRGB4 = localRGB2;
    }
    if (localRGB3.equals(localRGB4))
    {
      fillRectangle(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    int n;
    if (this.data.gdipGraphics != 0)
    {
      initGdip();
      PointF localPointF1 = new PointF();
      PointF localPointF2 = new PointF();
      localPointF1.X = paramInt1;
      localPointF1.Y = paramInt2;
      if (paramBoolean)
      {
        localPointF2.X = localPointF1.X;
        localPointF1.Y += paramInt4;
      }
      else
      {
        localPointF1.X += paramInt3;
        localPointF2.Y = localPointF1.Y;
      }
      m = (localRGB3.red & 0xFF) << 16 | (localRGB3.green & 0xFF) << 8 | localRGB3.blue & 0xFF;
      n = Gdip.Color_new(this.data.alpha << 24 | m);
      if (n == 0)
        SWT.error(2);
      m = (localRGB4.red & 0xFF) << 16 | (localRGB4.green & 0xFF) << 8 | localRGB4.blue & 0xFF;
      int i1 = Gdip.Color_new(this.data.alpha << 24 | m);
      if (i1 == 0)
        SWT.error(2);
      int i2 = Gdip.LinearGradientBrush_new(localPointF1, localPointF2, n, i1);
      Gdip.Graphics_FillRectangle(this.data.gdipGraphics, i2, paramInt1, paramInt2, paramInt3, paramInt4);
      Gdip.LinearGradientBrush_delete(i2);
      Gdip.Color_delete(n);
      Gdip.Color_delete(i1);
      return;
    }
    int j = 0;
    if (OS.IsWinCE)
    {
      j = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, j);
    }
    else
    {
      j = OS.GetROP2(this.handle);
    }
    if ((OS.IsWinNT) && (j != 7) && (OS.GetDeviceCaps(this.handle, 2) != 2))
    {
      k = OS.GetProcessHeap();
      m = OS.HeapAlloc(k, 8, GRADIENT_RECT.sizeof + TRIVERTEX.sizeof * 2);
      if (m == 0)
        SWT.error(2);
      n = m + GRADIENT_RECT.sizeof;
      GRADIENT_RECT localGRADIENT_RECT = new GRADIENT_RECT();
      localGRADIENT_RECT.UpperLeft = 0;
      localGRADIENT_RECT.LowerRight = 1;
      OS.MoveMemory(m, localGRADIENT_RECT, GRADIENT_RECT.sizeof);
      TRIVERTEX localTRIVERTEX = new TRIVERTEX();
      localTRIVERTEX.x = paramInt1;
      localTRIVERTEX.y = paramInt2;
      localTRIVERTEX.Red = ((short)(localRGB3.red << 8 | localRGB3.red));
      localTRIVERTEX.Green = ((short)(localRGB3.green << 8 | localRGB3.green));
      localTRIVERTEX.Blue = ((short)(localRGB3.blue << 8 | localRGB3.blue));
      localTRIVERTEX.Alpha = -1;
      OS.MoveMemory(n, localTRIVERTEX, TRIVERTEX.sizeof);
      localTRIVERTEX.x = (paramInt1 + paramInt3);
      localTRIVERTEX.y = (paramInt2 + paramInt4);
      localTRIVERTEX.Red = ((short)(localRGB4.red << 8 | localRGB4.red));
      localTRIVERTEX.Green = ((short)(localRGB4.green << 8 | localRGB4.green));
      localTRIVERTEX.Blue = ((short)(localRGB4.blue << 8 | localRGB4.blue));
      localTRIVERTEX.Alpha = -1;
      OS.MoveMemory(n + TRIVERTEX.sizeof, localTRIVERTEX, TRIVERTEX.sizeof);
      boolean bool = OS.GradientFill(this.handle, n, 2, m, 1, paramBoolean ? 1 : 0);
      OS.HeapFree(k, 0, m);
      if (bool)
        return;
    }
    int k = OS.GetDeviceCaps(this.handle, 12);
    int m = k >= 15 ? 5 : k >= 24 ? 8 : 0;
    ImageData.fillGradientRectangle(this, this.data.device, paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, localRGB3, localRGB4, m, m, m);
  }

  public void fillOval(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(9218);
    if (this.data.gdipGraphics != 0)
    {
      Gdip.Graphics_FillEllipse(this.data.gdipGraphics, this.data.gdipBrush, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    if ((this.data.style & 0x8000000) != 0)
      paramInt1--;
    OS.Ellipse(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1);
  }

  public void fillPath(Path paramPath)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramPath == null)
      SWT.error(4);
    if (paramPath.handle == 0)
      SWT.error(5);
    initGdip();
    checkGC(9218);
    int i = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
    Gdip.GraphicsPath_SetFillMode(paramPath.handle, i);
    Gdip.Graphics_FillPath(this.data.gdipGraphics, this.data.gdipBrush, paramPath.handle);
  }

  public void fillPolygon(int[] paramArrayOfInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramArrayOfInt == null)
      SWT.error(4);
    checkGC(9218);
    int i;
    if (this.data.gdipGraphics != 0)
    {
      i = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
      Gdip.Graphics_FillPolygon(this.data.gdipGraphics, this.data.gdipBrush, paramArrayOfInt, paramArrayOfInt.length / 2, i);
      return;
    }
    if ((this.data.style & 0x8000000) != 0)
      for (i = 0; i < paramArrayOfInt.length; i += 2)
        paramArrayOfInt[i] -= 1;
    OS.Polygon(this.handle, paramArrayOfInt, paramArrayOfInt.length / 2);
    if ((this.data.style & 0x8000000) != 0)
      for (i = 0; i < paramArrayOfInt.length; i += 2)
        paramArrayOfInt[i] += 1;
  }

  public void fillRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(9218);
    if (this.data.gdipGraphics != 0)
    {
      if (paramInt3 < 0)
      {
        paramInt1 += paramInt3;
        paramInt3 = -paramInt3;
      }
      if (paramInt4 < 0)
      {
        paramInt2 += paramInt4;
        paramInt4 = -paramInt4;
      }
      Gdip.Graphics_FillRectangle(this.data.gdipGraphics, this.data.gdipBrush, paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    int i = 0;
    if (OS.IsWinCE)
    {
      i = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, i);
    }
    else
    {
      i = OS.GetROP2(this.handle);
    }
    int j = i == 7 ? 5898313 : 15728673;
    OS.PatBlt(this.handle, paramInt1, paramInt2, paramInt3, paramInt4, j);
  }

  public void fillRectangle(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    fillRectangle(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void fillRoundRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(9218);
    if (this.data.gdipGraphics != 0)
    {
      fillRoundRectangleGdip(this.data.gdipGraphics, this.data.gdipBrush, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
      return;
    }
    if ((this.data.style & 0x8000000) != 0)
      paramInt1--;
    OS.RoundRect(this.handle, paramInt1, paramInt2, paramInt1 + paramInt3 + 1, paramInt2 + paramInt4 + 1, paramInt5, paramInt6);
  }

  void fillRoundRectangleGdip(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    int i = paramInt3;
    int j = paramInt4;
    int k = paramInt5;
    int m = paramInt6;
    int n = paramInt7;
    int i1 = paramInt8;
    if (k < 0)
    {
      k = 0 - k;
      i -= k;
    }
    if (m < 0)
    {
      m = 0 - m;
      j -= m;
    }
    if (n < 0)
      n = 0 - n;
    if (i1 < 0)
      i1 = 0 - i1;
    if ((n == 0) || (i1 == 0))
    {
      Gdip.Graphics_FillRectangle(this.data.gdipGraphics, this.data.gdipBrush, paramInt3, paramInt4, paramInt5, paramInt6);
    }
    else
    {
      int i2 = Gdip.GraphicsPath_new(0);
      if (i2 == 0)
        SWT.error(2);
      if (k > n)
      {
        if (m > i1)
        {
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j, n, i1, 0.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j, n, i1, -90.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j + m - i1, n, i1, -180.0F, -90.0F);
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j + m - i1, n, i1, -270.0F, -90.0F);
        }
        else
        {
          Gdip.GraphicsPath_AddArc(i2, i + k - n, j, n, m, -270.0F, -180.0F);
          Gdip.GraphicsPath_AddArc(i2, i, j, n, m, -90.0F, -180.0F);
        }
      }
      else if (m > i1)
      {
        Gdip.GraphicsPath_AddArc(i2, i, j, k, i1, 0.0F, -180.0F);
        Gdip.GraphicsPath_AddArc(i2, i, j + m - i1, k, i1, -180.0F, -180.0F);
      }
      else
      {
        Gdip.GraphicsPath_AddArc(i2, i, j, k, m, 0.0F, 360.0F);
      }
      Gdip.GraphicsPath_CloseFigure(i2);
      Gdip.Graphics_FillPath(paramInt1, paramInt2, i2);
      Gdip.GraphicsPath_delete(i2);
    }
  }

  void flush()
  {
    if (this.data.gdipGraphics != 0)
    {
      Gdip.Graphics_Flush(this.data.gdipGraphics, 0);
      int i = Gdip.Graphics_GetHDC(this.data.gdipGraphics);
      Gdip.Graphics_ReleaseHDC(this.data.gdipGraphics, i);
    }
  }

  public int getAdvanceWidth(char paramChar)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(4);
    if (OS.IsWinCE)
    {
      SIZE localSIZE = new SIZE();
      OS.GetTextExtentPoint32W(this.handle, new char[] { paramChar }, 1, localSIZE);
      return localSIZE.cx;
    }
    int i = paramChar;
    if (paramChar > '')
    {
      localObject = new TCHAR(getCodePage(), paramChar, false);
      i = ((TCHAR)localObject).tcharAt(0);
    }
    Object localObject = new int[1];
    OS.GetCharWidth(this.handle, i, i, (int[])localObject);
    return localObject[0];
  }

  public boolean getAdvanced()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.gdipGraphics != 0;
  }

  public int getAlpha()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.alpha;
  }

  public int getAntialias()
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.gdipGraphics == 0)
      return -1;
    int i = Gdip.Graphics_GetSmoothingMode(this.data.gdipGraphics);
    switch (i)
    {
    case 0:
      return -1;
    case 1:
    case 3:
      return 0;
    case 2:
    case 4:
    case 5:
      return 1;
    }
    return -1;
  }

  public Color getBackground()
  {
    if (this.handle == 0)
      SWT.error(44);
    return Color.win32_new(this.data.device, this.data.background);
  }

  public Pattern getBackgroundPattern()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.backgroundPattern;
  }

  public int getCharWidth(char paramChar)
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(4);
    if (!OS.IsWinCE)
    {
      int i = paramChar;
      if (paramChar > '')
      {
        localObject = new TCHAR(getCodePage(), paramChar, false);
        i = ((TCHAR)localObject).tcharAt(0);
      }
      localObject = new int[3];
      if (OS.GetCharABCWidths(this.handle, i, i, (int[])localObject))
        return localObject[1];
    }
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(this.handle, localTEXTMETRICA);
    Object localObject = new SIZE();
    OS.GetTextExtentPoint32W(this.handle, new char[] { paramChar }, 1, (SIZE)localObject);
    return ((SIZE)localObject).cx - localTEXTMETRICA.tmOverhang;
  }

  public Rectangle getClipping()
  {
    if (this.handle == 0)
      SWT.error(44);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      localObject = new Rect();
      Gdip.Graphics_SetPixelOffsetMode(i, 3);
      Gdip.Graphics_GetVisibleClipBounds(i, (Rect)localObject);
      Gdip.Graphics_SetPixelOffsetMode(i, 4);
      return new Rectangle(((Rect)localObject).X, ((Rect)localObject).Y, ((Rect)localObject).Width, ((Rect)localObject).Height);
    }
    Object localObject = new RECT();
    OS.GetClipBox(this.handle, (RECT)localObject);
    return new Rectangle(((RECT)localObject).left, ((RECT)localObject).top, ((RECT)localObject).right - ((RECT)localObject).left, ((RECT)localObject).bottom - ((RECT)localObject).top);
  }

  public void getClipping(Region paramRegion)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramRegion == null)
      SWT.error(4);
    if (paramRegion.isDisposed())
      SWT.error(5);
    int i = this.data.gdipGraphics;
    int i1;
    if (i != 0)
    {
      int j = Gdip.Region_new();
      Gdip.Graphics_GetClip(this.data.gdipGraphics, j);
      if (Gdip.Region_IsInfinite(j, i))
      {
        Rect localRect = new Rect();
        Gdip.Graphics_SetPixelOffsetMode(i, 3);
        Gdip.Graphics_GetVisibleClipBounds(i, localRect);
        Gdip.Graphics_SetPixelOffsetMode(i, 4);
        OS.SetRectRgn(paramRegion.handle, localRect.X, localRect.Y, localRect.X + localRect.Width, localRect.Y + localRect.Height);
      }
      else
      {
        k = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
        int m = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
        Gdip.Graphics_GetTransform(i, k);
        Gdip.Graphics_SetTransform(i, m);
        i1 = Gdip.Region_GetHRGN(j, this.data.gdipGraphics);
        Gdip.Graphics_SetTransform(i, k);
        Gdip.Matrix_delete(m);
        Gdip.Matrix_delete(k);
        if (!OS.IsWinCE)
        {
          POINT localPOINT2 = new POINT();
          OS.GetWindowOrgEx(this.handle, localPOINT2);
          OS.OffsetRgn(i1, localPOINT2.x, localPOINT2.y);
        }
        OS.CombineRgn(paramRegion.handle, i1, 0, 5);
        OS.DeleteObject(i1);
      }
      Gdip.Region_delete(j);
      return;
    }
    POINT localPOINT1 = new POINT();
    if (!OS.IsWinCE)
      OS.GetWindowOrgEx(this.handle, localPOINT1);
    int k = OS.GetClipRgn(this.handle, paramRegion.handle);
    if (k != 1)
    {
      RECT localRECT = new RECT();
      OS.GetClipBox(this.handle, localRECT);
      OS.SetRectRgn(paramRegion.handle, localRECT.left, localRECT.top, localRECT.right, localRECT.bottom);
    }
    else
    {
      OS.OffsetRgn(paramRegion.handle, localPOINT1.x, localPOINT1.y);
    }
    if (!OS.IsWinCE)
    {
      int n = OS.CreateRectRgn(0, 0, 0, 0);
      if (OS.GetMetaRgn(this.handle, n) != 0)
      {
        OS.OffsetRgn(n, localPOINT1.x, localPOINT1.y);
        OS.CombineRgn(paramRegion.handle, n, paramRegion.handle, 1);
      }
      OS.DeleteObject(n);
      i1 = this.data.hwnd;
      if ((i1 != 0) && (this.data.ps != null))
      {
        int i2 = OS.CreateRectRgn(0, 0, 0, 0);
        if (OS.GetRandomRgn(this.handle, i2, 4) == 1)
        {
          if ((OS.WIN32_VERSION >= OS.VERSION(4, 10)) && ((OS.GetLayout(this.handle) & 0x1) != 0))
          {
            int i3 = OS.GetRegionData(i2, 0, null);
            int[] arrayOfInt = new int[i3 / 4];
            OS.GetRegionData(i2, i3, arrayOfInt);
            int i4 = OS.ExtCreateRegion(new float[] { -1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F }, i3, arrayOfInt);
            OS.DeleteObject(i2);
            i2 = i4;
          }
          if (OS.IsWinNT)
          {
            OS.MapWindowPoints(0, i1, localPOINT1, 1);
            OS.OffsetRgn(i2, localPOINT1.x, localPOINT1.y);
          }
          OS.CombineRgn(paramRegion.handle, i2, paramRegion.handle, 1);
        }
        OS.DeleteObject(i2);
      }
    }
  }

  int getCodePage()
  {
    if (OS.IsUnicode)
      return 0;
    int[] arrayOfInt = new int[8];
    int i = OS.GetTextCharset(this.handle);
    OS.TranslateCharsetInfo(i, arrayOfInt, 1);
    return arrayOfInt[1];
  }

  int getFgBrush()
  {
    return this.data.foregroundPattern != null ? this.data.foregroundPattern.handle : this.data.gdipFgBrush;
  }

  public int getFillRule()
  {
    if (this.handle == 0)
      SWT.error(44);
    if (OS.IsWinCE)
      return 1;
    return OS.GetPolyFillMode(this.handle) == 2 ? 2 : 1;
  }

  public Font getFont()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.font;
  }

  public FontMetrics getFontMetrics()
  {
    if (this.handle == 0)
      SWT.error(44);
    checkGC(4);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.GetTextMetrics(this.handle, localTEXTMETRICA);
    return FontMetrics.win32_new(localTEXTMETRICA);
  }

  public Color getForeground()
  {
    if (this.handle == 0)
      SWT.error(44);
    return Color.win32_new(this.data.device, this.data.foreground);
  }

  public Pattern getForegroundPattern()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.foregroundPattern;
  }

  public GCData getGCData()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data;
  }

  public int getInterpolation()
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.gdipGraphics == 0)
      return -1;
    int i = Gdip.Graphics_GetInterpolationMode(this.data.gdipGraphics);
    switch (i)
    {
    case 0:
      return -1;
    case 5:
      return 0;
    case 1:
    case 3:
      return 1;
    case 2:
    case 4:
    case 6:
    case 7:
      return 2;
    }
    return -1;
  }

  public LineAttributes getLineAttributes()
  {
    if (this.handle == 0)
      SWT.error(44);
    float[] arrayOfFloat = (float[])null;
    if (this.data.lineDashes != null)
    {
      arrayOfFloat = new float[this.data.lineDashes.length];
      System.arraycopy(this.data.lineDashes, 0, arrayOfFloat, 0, arrayOfFloat.length);
    }
    return new LineAttributes(this.data.lineWidth, this.data.lineCap, this.data.lineJoin, this.data.lineStyle, arrayOfFloat, this.data.lineDashesOffset, this.data.lineMiterLimit);
  }

  public int getLineCap()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.lineCap;
  }

  public int[] getLineDash()
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.lineDashes == null)
      return null;
    int[] arrayOfInt = new int[this.data.lineDashes.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = ((int)this.data.lineDashes[i]);
    return arrayOfInt;
  }

  public int getLineJoin()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.lineJoin;
  }

  public int getLineStyle()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.lineStyle;
  }

  public int getLineWidth()
  {
    if (this.handle == 0)
      SWT.error(44);
    return (int)this.data.lineWidth;
  }

  public int getStyle()
  {
    if (this.handle == 0)
      SWT.error(44);
    return this.data.style;
  }

  public int getTextAntialias()
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.gdipGraphics == 0)
      return -1;
    int i = Gdip.Graphics_GetTextRenderingHint(this.data.gdipGraphics);
    switch (i)
    {
    case 0:
      return -1;
    case 1:
    case 2:
      return 0;
    case 3:
    case 4:
    case 5:
      return 1;
    }
    return -1;
  }

  public void getTransform(Transform paramTransform)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramTransform == null)
      SWT.error(4);
    if (paramTransform.isDisposed())
      SWT.error(5);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      Gdip.Graphics_GetTransform(i, paramTransform.handle);
      int j = identity();
      Gdip.Matrix_Invert(j);
      Gdip.Matrix_Multiply(paramTransform.handle, j, 1);
      Gdip.Matrix_delete(j);
    }
    else
    {
      paramTransform.setElements(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
    }
  }

  public boolean getXORMode()
  {
    if (this.handle == 0)
      SWT.error(44);
    int i = 0;
    if (OS.IsWinCE)
    {
      i = OS.SetROP2(this.handle, 13);
      OS.SetROP2(this.handle, i);
    }
    else
    {
      i = OS.GetROP2(this.handle);
    }
    return i == 7;
  }

  void initGdip()
  {
    this.data.device.checkGDIP();
    int i = this.data.gdipGraphics;
    if (i != 0)
      return;
    int j = OS.CreateRectRgn(0, 0, 0, 0);
    int k = OS.GetClipRgn(this.handle, j);
    if (!OS.IsWinCE)
    {
      POINT localPOINT = new POINT();
      OS.GetWindowOrgEx(this.handle, localPOINT);
      OS.OffsetRgn(j, localPOINT.x, localPOINT.y);
    }
    OS.SelectClipRgn(this.handle, 0);
    if ((this.data.style & 0x8000000) != 0)
      OS.SetLayout(this.handle, OS.GetLayout(this.handle) & 0xFFFFFFFE);
    i = this.data.gdipGraphics = Gdip.Graphics_new(this.handle);
    if (i == 0)
      SWT.error(2);
    Gdip.Graphics_SetPageUnit(i, 2);
    Gdip.Graphics_SetPixelOffsetMode(i, 4);
    if ((this.data.style & 0x8000000) != 0)
    {
      int m = identity();
      Gdip.Graphics_SetTransform(i, m);
      Gdip.Matrix_delete(m);
    }
    if (k == 1)
      setClipping(j);
    OS.DeleteObject(j);
    this.data.state = 0;
    if (this.data.hPen != 0)
    {
      OS.SelectObject(this.handle, OS.GetStockObject(8));
      OS.DeleteObject(this.data.hPen);
      this.data.hPen = 0;
    }
    if (this.data.hBrush != 0)
    {
      OS.SelectObject(this.handle, OS.GetStockObject(5));
      OS.DeleteObject(this.data.hBrush);
      this.data.hBrush = 0;
    }
  }

  int identity()
  {
    if ((this.data.style & 0x8000000) != 0)
    {
      int i = 0;
      int j = OS.GetDeviceCaps(this.handle, 2);
      if (j == 2)
      {
        i = OS.GetDeviceCaps(this.handle, 110);
      }
      else
      {
        localObject = this.data.image;
        if (localObject != null)
        {
          BITMAP localBITMAP1 = new BITMAP();
          OS.GetObject(((Image)localObject).handle, BITMAP.sizeof, localBITMAP1);
          i = localBITMAP1.bmWidth;
        }
        else
        {
          int k = OS.IsWinCE ? this.data.hwnd : OS.WindowFromDC(this.handle);
          if (k != 0)
          {
            RECT localRECT = new RECT();
            OS.GetClientRect(k, localRECT);
            i = localRECT.right - localRECT.left;
          }
          else
          {
            int m = OS.GetCurrentObject(this.handle, 7);
            BITMAP localBITMAP2 = new BITMAP();
            OS.GetObject(m, BITMAP.sizeof, localBITMAP2);
            i = localBITMAP2.bmWidth;
          }
        }
      }
      Object localObject = new POINT();
      if (!OS.IsWinCE)
        OS.GetWindowOrgEx(this.handle, (POINT)localObject);
      return Gdip.Matrix_new(-1.0F, 0.0F, 0.0F, 1.0F, i + 2 * ((POINT)localObject).x, 0.0F);
    }
    return Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
  }

  void init(Drawable paramDrawable, GCData paramGCData, int paramInt)
  {
    int i = paramGCData.foreground;
    if (i != -1)
      paramGCData.state &= -2306;
    else
      paramGCData.foreground = OS.GetTextColor(paramInt);
    int j = paramGCData.background;
    if (j != -1)
      paramGCData.state &= -1539;
    else
      paramGCData.background = OS.GetBkColor(paramInt);
    paramGCData.state &= -12289;
    Font localFont = paramGCData.font;
    if (localFont != null)
      paramGCData.state &= -5;
    else
      paramGCData.font = Font.win32_new(this.device, OS.GetCurrentObject(paramInt, 6));
    int k = paramGCData.device.hPalette;
    if (k != 0)
    {
      OS.SelectPalette(paramInt, k, true);
      OS.RealizePalette(paramInt);
    }
    Image localImage = paramGCData.image;
    if (localImage != null)
    {
      paramGCData.hNullBitmap = OS.SelectObject(paramInt, localImage.handle);
      localImage.memGC = this;
    }
    int m = paramGCData.layout;
    if ((m != -1) && (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      int n = OS.GetLayout(paramInt);
      if ((n & 0x1) != (m & 0x1))
      {
        n &= -2;
        OS.SetLayout(paramInt, n | m);
      }
      if ((paramGCData.style & 0x4000000) != 0)
        paramGCData.style |= 134217728;
    }
    this.drawable = paramDrawable;
    this.data = paramGCData;
    this.handle = paramInt;
  }

  public int hashCode()
  {
    return this.handle;
  }

  public boolean isClipped()
  {
    if (this.handle == 0)
      SWT.error(44);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      j = Gdip.Region_new();
      Gdip.Graphics_GetClip(this.data.gdipGraphics, j);
      boolean bool = Gdip.Region_IsInfinite(j, i);
      Gdip.Region_delete(j);
      return !bool;
    }
    int j = OS.CreateRectRgn(0, 0, 0, 0);
    int k = OS.GetClipRgn(this.handle, j);
    OS.DeleteObject(j);
    return k > 0;
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  float measureSpace(int paramInt1, int paramInt2)
  {
    PointF localPointF = new PointF();
    RectF localRectF = new RectF();
    Gdip.Graphics_MeasureString(this.data.gdipGraphics, new char[] { ' ' }, 1, paramInt1, localPointF, paramInt2, localRectF);
    return localRectF.Width;
  }

  public void setAdvanced(boolean paramBoolean)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramBoolean) && (this.data.gdipGraphics != 0))
      return;
    if (paramBoolean)
    {
      try
      {
        initGdip();
      }
      catch (SWTException localSWTException)
      {
      }
    }
    else
    {
      disposeGdip();
      this.data.alpha = 255;
      this.data.backgroundPattern = (this.data.foregroundPattern = null);
      this.data.state = 0;
      setClipping(0);
      if ((this.data.style & 0x8000000) != 0)
        OS.SetLayout(this.handle, OS.GetLayout(this.handle) | 0x1);
    }
  }

  public void setAntialias(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.data.gdipGraphics == 0) && (paramInt == -1))
      return;
    int i = 0;
    switch (paramInt)
    {
    case -1:
      i = 0;
      break;
    case 0:
      i = 3;
      break;
    case 1:
      i = 4;
      break;
    default:
      SWT.error(5);
    }
    initGdip();
    Gdip.Graphics_SetSmoothingMode(this.data.gdipGraphics, i);
  }

  public void setAlpha(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.data.gdipGraphics == 0) && ((paramInt & 0xFF) == 255))
      return;
    initGdip();
    this.data.alpha = (paramInt & 0xFF);
    this.data.state &= -4;
  }

  public void setBackground(Color paramColor)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramColor == null)
      SWT.error(4);
    if (paramColor.isDisposed())
      SWT.error(5);
    if ((this.data.backgroundPattern == null) && (this.data.background == paramColor.handle))
      return;
    this.data.backgroundPattern = null;
    this.data.background = paramColor.handle;
    this.data.state &= -515;
  }

  public void setBackgroundPattern(Pattern paramPattern)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramPattern != null) && (paramPattern.isDisposed()))
      SWT.error(5);
    if ((this.data.gdipGraphics == 0) && (paramPattern == null))
      return;
    initGdip();
    if (this.data.backgroundPattern == paramPattern)
      return;
    this.data.backgroundPattern = paramPattern;
    this.data.state &= -3;
  }

  void setClipping(int paramInt)
  {
    int i = paramInt;
    int j = this.data.gdipGraphics;
    if (j != 0)
    {
      if (i != 0)
      {
        int k = Gdip.Region_new(i);
        Gdip.Graphics_SetClip(j, k, 0);
        Gdip.Region_delete(k);
      }
      else
      {
        Gdip.Graphics_ResetClip(j);
      }
    }
    else
    {
      POINT localPOINT = null;
      if ((i != 0) && (!OS.IsWinCE))
      {
        localPOINT = new POINT();
        OS.GetWindowOrgEx(this.handle, localPOINT);
        OS.OffsetRgn(i, -localPOINT.x, -localPOINT.y);
      }
      OS.SelectClipRgn(this.handle, i);
      if ((i != 0) && (!OS.IsWinCE))
        OS.OffsetRgn(i, localPOINT.x, localPOINT.y);
    }
  }

  public void setClipping(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (this.handle == 0)
      SWT.error(44);
    int i = OS.CreateRectRgn(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    setClipping(i);
    OS.DeleteObject(i);
  }

  public void setClipping(Path paramPath)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramPath != null) && (paramPath.isDisposed()))
      SWT.error(5);
    setClipping(0);
    if (paramPath != null)
    {
      initGdip();
      int i = OS.GetPolyFillMode(this.handle) == 2 ? 1 : 0;
      Gdip.GraphicsPath_SetFillMode(paramPath.handle, i);
      Gdip.Graphics_SetClipPath(this.data.gdipGraphics, paramPath.handle);
    }
  }

  public void setClipping(Rectangle paramRectangle)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramRectangle == null)
      setClipping(0);
    else
      setClipping(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void setClipping(Region paramRegion)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramRegion != null) && (paramRegion.isDisposed()))
      SWT.error(5);
    setClipping(paramRegion != null ? paramRegion.handle : 0);
  }

  public void setFillRule(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (OS.IsWinCE)
      return;
    int i = 1;
    switch (paramInt)
    {
    case 2:
      i = 2;
      break;
    case 1:
      i = 1;
      break;
    default:
      SWT.error(5);
    }
    OS.SetPolyFillMode(this.handle, i);
  }

  public void setFont(Font paramFont)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    this.data.font = (paramFont != null ? paramFont : this.data.device.systemFont);
    this.data.state &= -5;
  }

  public void setForeground(Color paramColor)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramColor == null)
      SWT.error(4);
    if (paramColor.isDisposed())
      SWT.error(5);
    if ((this.data.foregroundPattern == null) && (paramColor.handle == this.data.foreground))
      return;
    this.data.foregroundPattern = null;
    this.data.foreground = paramColor.handle;
    this.data.state &= -258;
  }

  public void setForegroundPattern(Pattern paramPattern)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramPattern != null) && (paramPattern.isDisposed()))
      SWT.error(5);
    if ((this.data.gdipGraphics == 0) && (paramPattern == null))
      return;
    initGdip();
    if (this.data.foregroundPattern == paramPattern)
      return;
    this.data.foregroundPattern = paramPattern;
    this.data.state &= -2;
  }

  public void setInterpolation(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.data.gdipGraphics == 0) && (paramInt == -1))
      return;
    int i = 0;
    switch (paramInt)
    {
    case -1:
      i = 0;
      break;
    case 0:
      i = 5;
      break;
    case 1:
      i = 1;
      break;
    case 2:
      i = 2;
      break;
    default:
      SWT.error(5);
    }
    initGdip();
    Gdip.Graphics_SetInterpolationMode(this.data.gdipGraphics, i);
  }

  public void setLineAttributes(LineAttributes paramLineAttributes)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramLineAttributes == null)
      SWT.error(4);
    int i = 0;
    float f1 = paramLineAttributes.width;
    if (f1 != this.data.lineWidth)
      i |= 16400;
    int j = paramLineAttributes.style;
    if (j != this.data.lineStyle)
    {
      i |= 8;
      switch (j)
      {
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
        break;
      case 6:
        if (paramLineAttributes.dash == null)
          j = 1;
        break;
      default:
        SWT.error(5);
      }
    }
    int k = paramLineAttributes.join;
    if (k != this.data.lineJoin)
    {
      i |= 64;
      switch (k)
      {
      case 1:
      case 2:
      case 3:
        break;
      default:
        SWT.error(5);
      }
    }
    int m = paramLineAttributes.cap;
    if (m != this.data.lineCap)
    {
      i |= 32;
      switch (m)
      {
      case 1:
      case 2:
      case 3:
        break;
      default:
        SWT.error(5);
      }
    }
    Object localObject = paramLineAttributes.dash;
    float[] arrayOfFloat1 = this.data.lineDashes;
    if ((localObject != null) && (localObject.length > 0))
    {
      int n = (arrayOfFloat1 != null) && (arrayOfFloat1.length == localObject.length) ? 0 : 1;
      for (int i1 = 0; i1 < localObject.length; i1++)
      {
        float f4 = localObject[i1];
        if (f4 <= 0.0F)
          SWT.error(5);
        if ((n == 0) && (arrayOfFloat1[i1] != f4))
          n = 1;
      }
      if (n != 0)
      {
        float[] arrayOfFloat2 = new float[localObject.length];
        System.arraycopy(localObject, 0, arrayOfFloat2, 0, localObject.length);
        localObject = arrayOfFloat2;
        i |= 8;
      }
      else
      {
        localObject = arrayOfFloat1;
      }
    }
    else if ((arrayOfFloat1 != null) && (arrayOfFloat1.length > 0))
    {
      i |= 8;
    }
    else
    {
      localObject = arrayOfFloat1;
    }
    float f2 = paramLineAttributes.dashOffset;
    if (f2 != this.data.lineDashesOffset)
      i |= 8;
    float f3 = paramLineAttributes.miterLimit;
    if (f3 != this.data.lineMiterLimit)
      i |= 128;
    initGdip();
    if (i == 0)
      return;
    this.data.lineWidth = f1;
    this.data.lineStyle = j;
    this.data.lineCap = m;
    this.data.lineJoin = k;
    this.data.lineDashes = ((float[])localObject);
    this.data.lineDashesOffset = f2;
    this.data.lineMiterLimit = f3;
    this.data.state &= (i ^ 0xFFFFFFFF);
  }

  public void setLineCap(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.lineCap == paramInt)
      return;
    switch (paramInt)
    {
    case 1:
    case 2:
    case 3:
      break;
    default:
      SWT.error(5);
    }
    this.data.lineCap = paramInt;
    this.data.state &= -33;
  }

  public void setLineDash(int[] paramArrayOfInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    float[] arrayOfFloat = this.data.lineDashes;
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length > 0))
    {
      int i = (this.data.lineStyle == 6) && (arrayOfFloat != null) && (arrayOfFloat.length == paramArrayOfInt.length) ? 0 : 1;
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        int k = paramArrayOfInt[j];
        if (k <= 0)
          SWT.error(5);
        if ((i == 0) && (arrayOfFloat[j] != k))
          i = 1;
      }
      if (i == 0)
        return;
      this.data.lineDashes = new float[paramArrayOfInt.length];
      for (j = 0; j < paramArrayOfInt.length; j++)
        this.data.lineDashes[j] = paramArrayOfInt[j];
      this.data.lineStyle = 6;
    }
    else
    {
      if ((this.data.lineStyle == 1) && ((arrayOfFloat == null) || (arrayOfFloat.length == 0)))
        return;
      this.data.lineDashes = null;
      this.data.lineStyle = 1;
    }
    this.data.state &= -9;
  }

  public void setLineJoin(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.lineJoin == paramInt)
      return;
    switch (paramInt)
    {
    case 1:
    case 2:
    case 3:
      break;
    default:
      SWT.error(5);
    }
    this.data.lineJoin = paramInt;
    this.data.state &= -65;
  }

  public void setLineStyle(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.lineStyle == paramInt)
      return;
    switch (paramInt)
    {
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
      break;
    case 6:
      if (this.data.lineDashes == null)
        paramInt = 1;
      break;
    default:
      SWT.error(5);
    }
    this.data.lineStyle = paramInt;
    this.data.state &= -9;
  }

  public void setLineWidth(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (this.data.lineWidth == paramInt)
      return;
    this.data.lineWidth = paramInt;
    this.data.state &= -16401;
  }

  /** @deprecated */
  public void setXORMode(boolean paramBoolean)
  {
    if (this.handle == 0)
      SWT.error(44);
    OS.SetROP2(this.handle, paramBoolean ? 7 : 13);
  }

  public void setTextAntialias(int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((this.data.gdipGraphics == 0) && (paramInt == -1))
      return;
    int i = 0;
    switch (paramInt)
    {
    case -1:
      i = 0;
      break;
    case 0:
      i = 1;
      break;
    case 1:
      int[] arrayOfInt = new int[1];
      OS.SystemParametersInfo(8202, 0, arrayOfInt, 0);
      if (arrayOfInt[0] == 2)
        i = 5;
      else
        i = 3;
      break;
    default:
      SWT.error(5);
    }
    initGdip();
    Gdip.Graphics_SetTextRenderingHint(this.data.gdipGraphics, i);
  }

  public void setTransform(Transform paramTransform)
  {
    if (this.handle == 0)
      SWT.error(44);
    if ((paramTransform != null) && (paramTransform.isDisposed()))
      SWT.error(5);
    if ((this.data.gdipGraphics == 0) && (paramTransform == null))
      return;
    initGdip();
    int i = identity();
    if (paramTransform != null)
      Gdip.Matrix_Multiply(i, paramTransform.handle, 0);
    Gdip.Graphics_SetTransform(this.data.gdipGraphics, i);
    Gdip.Matrix_delete(i);
    this.data.state &= -16385;
  }

  public Point stringExtent(String paramString)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramString == null)
      SWT.error(4);
    checkGC(4);
    int i = paramString.length();
    int j = this.data.gdipGraphics;
    if (j != 0)
    {
      localObject = new Point(0, 0);
      drawText(j, paramString, 0, 0, 0, (Point)localObject);
      return localObject;
    }
    Object localObject = new SIZE();
    if (i == 0)
    {
      OS.GetTextExtentPoint32W(this.handle, new char[] { ' ' }, 1, (SIZE)localObject);
      return new Point(0, ((SIZE)localObject).cy);
    }
    char[] arrayOfChar = new char[i];
    paramString.getChars(0, i, arrayOfChar, 0);
    OS.GetTextExtentPoint32W(this.handle, arrayOfChar, i, (SIZE)localObject);
    return new Point(((SIZE)localObject).cx, ((SIZE)localObject).cy);
  }

  public Point textExtent(String paramString)
  {
    return textExtent(paramString, 6);
  }

  public Point textExtent(String paramString, int paramInt)
  {
    if (this.handle == 0)
      SWT.error(44);
    if (paramString == null)
      SWT.error(4);
    checkGC(4);
    int i = this.data.gdipGraphics;
    if (i != 0)
    {
      localObject = new Point(0, 0);
      drawText(i, paramString, 0, 0, paramInt, (Point)localObject);
      return localObject;
    }
    if (paramString.length() == 0)
    {
      localObject = new SIZE();
      OS.GetTextExtentPoint32W(this.handle, new char[] { ' ' }, 1, (SIZE)localObject);
      return new Point(0, ((SIZE)localObject).cy);
    }
    Object localObject = new RECT();
    TCHAR localTCHAR = new TCHAR(getCodePage(), paramString, false);
    int j = 1024;
    if ((paramInt & 0x2) == 0)
      j |= 32;
    if ((paramInt & 0x4) != 0)
      j |= 64;
    if ((paramInt & 0x8) == 0)
      j |= 2048;
    OS.DrawText(this.handle, localTCHAR, localTCHAR.length(), (RECT)localObject, j);
    return new Point(((RECT)localObject).right, ((RECT)localObject).bottom);
  }

  public String toString()
  {
    if (isDisposed())
      return "GC {*DISPOSED*}";
    return "GC {" + this.handle + "}";
  }

  public static GC win32_new(Drawable paramDrawable, GCData paramGCData)
  {
    GC localGC = new GC();
    int i = paramDrawable.internal_new_GC(paramGCData);
    localGC.device = paramGCData.device;
    localGC.init(paramDrawable, paramGCData, i);
    return localGC;
  }

  public static GC win32_new(int paramInt, GCData paramGCData)
  {
    GC localGC = new GC();
    localGC.device = paramGCData.device;
    paramGCData.style |= 33554432;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
    {
      int i = OS.GetLayout(paramInt);
      if ((i & 0x1) != 0)
        paramGCData.style |= 201326592;
    }
    localGC.init(null, paramGCData, paramInt);
    return localGC;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.GC
 * JD-Core Version:    0.6.2
 */