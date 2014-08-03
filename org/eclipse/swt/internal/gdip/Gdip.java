package org.eclipse.swt.internal.gdip;

import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.Platform;

public class Gdip extends Platform
{
  public static final float FlatnessDefault = 0.25F;
  public static final int BrushTypeSolidColor = 0;
  public static final int BrushTypeHatchFill = 1;
  public static final int BrushTypeTextureFill = 2;
  public static final int BrushTypePathGradient = 3;
  public static final int BrushTypeLinearGradient = 4;
  public static final int ColorAdjustTypeBitmap = 1;
  public static final int ColorMatrixFlagsDefault = 0;
  public static final int CombineModeReplace = 0;
  public static final int CombineModeIntersect = 1;
  public static final int CombineModeUnion = 2;
  public static final int CombineModeXor = 3;
  public static final int CombineModeExclude = 4;
  public static final int CombineModeComplement = 5;
  public static final int FillModeAlternate = 0;
  public static final int FillModeWinding = 1;
  public static final int DashCapFlat = 0;
  public static final int DashCapRound = 2;
  public static final int DashCapTriangle = 3;
  public static final int DashStyleSolid = 0;
  public static final int DashStyleDash = 1;
  public static final int DashStyleDot = 2;
  public static final int DashStyleDashDot = 3;
  public static final int DashStyleDashDotDot = 4;
  public static final int DashStyleCustom = 5;
  public static final int DriverStringOptionsRealizedAdvance = 4;
  public static final int FontStyleRegular = 0;
  public static final int FontStyleBold = 1;
  public static final int FontStyleItalic = 2;
  public static final int FontStyleBoldItalic = 3;
  public static final int FontStyleUnderline = 4;
  public static final int FontStyleStrikeout = 8;
  public static final int PaletteFlagsHasAlpha = 1;
  public static final int FlushIntentionFlush = 0;
  public static final int FlushIntentionSync = 1;
  public static final int HotkeyPrefixNone = 0;
  public static final int HotkeyPrefixShow = 1;
  public static final int HotkeyPrefixHide = 2;
  public static final int LineJoinMiter = 0;
  public static final int LineJoinBevel = 1;
  public static final int LineJoinRound = 2;
  public static final int LineCapFlat = 0;
  public static final int LineCapSquare = 1;
  public static final int LineCapRound = 2;
  public static final int MatrixOrderPrepend = 0;
  public static final int MatrixOrderAppend = 1;
  public static final int QualityModeDefault = 0;
  public static final int QualityModeLow = 1;
  public static final int QualityModeHigh = 2;
  public static final int InterpolationModeDefault = 0;
  public static final int InterpolationModeLowQuality = 1;
  public static final int InterpolationModeHighQuality = 2;
  public static final int InterpolationModeBilinear = 3;
  public static final int InterpolationModeBicubic = 4;
  public static final int InterpolationModeNearestNeighbor = 5;
  public static final int InterpolationModeHighQualityBilinear = 6;
  public static final int InterpolationModeHighQualityBicubic = 7;
  public static final int PathPointTypeStart = 0;
  public static final int PathPointTypeLine = 1;
  public static final int PathPointTypeBezier = 3;
  public static final int PathPointTypePathTypeMask = 7;
  public static final int PathPointTypePathDashMode = 16;
  public static final int PathPointTypePathMarker = 32;
  public static final int PathPointTypeCloseSubpath = 128;
  public static final int PathPointTypeBezier3 = 3;
  public static final int PixelFormatIndexed = 65536;
  public static final int PixelFormatGDI = 131072;
  public static final int PixelFormatAlpha = 262144;
  public static final int PixelFormatPAlpha = 524288;
  public static final int PixelFormatExtended = 1048576;
  public static final int PixelFormatCanonical = 2097152;
  public static final int PixelFormat1bppIndexed = 196865;
  public static final int PixelFormat4bppIndexed = 197634;
  public static final int PixelFormat8bppIndexed = 198659;
  public static final int PixelFormat16bppGrayScale = 1052676;
  public static final int PixelFormat16bppRGB555 = 135173;
  public static final int PixelFormat16bppRGB565 = 135174;
  public static final int PixelFormat16bppARGB1555 = 397319;
  public static final int PixelFormat24bppRGB = 137224;
  public static final int PixelFormat32bppRGB = 139273;
  public static final int PixelFormat32bppARGB = 2498570;
  public static final int PixelFormat32bppPARGB = 925707;
  public static final int PixelFormat48bppRGB = 1060876;
  public static final int PixelFormat64bppARGB = 3424269;
  public static final int PixelFormat64bppPARGB = 1851406;
  public static final int PixelFormatMax = 15;
  public static final int PixelOffsetModeNone = 3;
  public static final int PixelOffsetModeHalf = 4;
  public static final int SmoothingModeDefault = 0;
  public static final int SmoothingModeHighSpeed = 1;
  public static final int SmoothingModeHighQuality = 2;
  public static final int SmoothingModeNone = 3;
  public static final int SmoothingModeAntiAlias8x4 = 4;
  public static final int SmoothingModeAntiAlias = 4;
  public static final int SmoothingModeAntiAlias8x8 = 5;
  public static final int StringFormatFlagsDirectionRightToLeft = 1;
  public static final int StringFormatFlagsDirectionVertical = 2;
  public static final int StringFormatFlagsNoFitBlackBox = 4;
  public static final int StringFormatFlagsDisplayFormatControl = 32;
  public static final int StringFormatFlagsNoFontFallback = 1024;
  public static final int StringFormatFlagsMeasureTrailingSpaces = 2048;
  public static final int StringFormatFlagsNoWrap = 4096;
  public static final int StringFormatFlagsLineLimit = 8192;
  public static final int StringFormatFlagsNoClip = 16384;
  public static final int TextRenderingHintSystemDefault = 0;
  public static final int TextRenderingHintSingleBitPerPixelGridFit = 1;
  public static final int TextRenderingHintSingleBitPerPixel = 2;
  public static final int TextRenderingHintAntiAliasGridFit = 3;
  public static final int TextRenderingHintAntiAlias = 4;
  public static final int TextRenderingHintClearTypeGridFit = 5;
  public static final int UnitPixel = 2;
  public static final int WrapModeTile = 0;
  public static final int WrapModeTileFlipX = 1;
  public static final int WrapModeTileFlipY = 2;
  public static final int WrapModeTileFlipXY = 3;
  public static final int WrapModeClamp = 4;

  static
  {
    Library.loadLibrary("swt-gdip");
  }

  public static final native int ColorPalette_sizeof();

  public static final native int GdiplusStartupInput_sizeof();

  public static final native int GdiplusStartup(int[] paramArrayOfInt, GdiplusStartupInput paramGdiplusStartupInput, int paramInt);

  public static final native void GdiplusShutdown(int paramInt);

  public static final native int Bitmap_new(int paramInt1, int paramInt2);

  public static final native int Bitmap_new(int paramInt);

  public static final native int Bitmap_new(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int Bitmap_new(char[] paramArrayOfChar, boolean paramBoolean);

  public static final native void Bitmap_delete(int paramInt);

  public static final native int Bitmap_GetHBITMAP(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native int Bitmap_GetHICON(int paramInt, int[] paramArrayOfInt);

  public static final native int BitmapData_new();

  public static final native void BitmapData_delete(int paramInt);

  public static final native int Bitmap_LockBits(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int Bitmap_UnlockBits(int paramInt1, int paramInt2);

  public static final native int Brush_Clone(int paramInt);

  public static final native int Brush_GetType(int paramInt);

  public static final native int Color_new(int paramInt);

  public static final native void Color_delete(int paramInt);

  public static final native int PrivateFontCollection_new();

  public static final native void PrivateFontCollection_delete(int paramInt);

  public static final native int PrivateFontCollection_AddFontFile(int paramInt, char[] paramArrayOfChar);

  public static final native int Font_new(int paramInt1, int paramInt2);

  public static final native int Font_new(int paramInt1, float paramFloat, int paramInt2, int paramInt3);

  public static final native int Font_new(char[] paramArrayOfChar, float paramFloat, int paramInt1, int paramInt2, int paramInt3);

  public static final native void Font_delete(int paramInt);

  public static final native int Font_GetFamily(int paramInt1, int paramInt2);

  public static final native float Font_GetSize(int paramInt);

  public static final native int Font_GetStyle(int paramInt);

  public static final native int Font_GetLogFontW(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean Font_IsAvailable(int paramInt);

  public static final native int FontFamily_new();

  public static final native int FontFamily_new(char[] paramArrayOfChar, int paramInt);

  public static final native void FontFamily_delete(int paramInt);

  public static final native int FontFamily_GetFamilyName(int paramInt, char[] paramArrayOfChar, char paramChar);

  public static final native boolean FontFamily_IsAvailable(int paramInt);

  public static final native int Graphics_new(int paramInt);

  public static final native void Graphics_delete(int paramInt);

  public static final native int Graphics_DrawArc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2);

  public static final native int Graphics_DrawDriverString(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, PointF paramPointF, int paramInt6, int paramInt7);

  public static final native int Graphics_DrawDriverString(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, float[] paramArrayOfFloat, int paramInt6, int paramInt7);

  public static final native int Graphics_DrawEllipse(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int Graphics_DrawImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int Graphics_DrawImage(int paramInt1, int paramInt2, Rect paramRect, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  public static final native int Graphics_DrawLine(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int Graphics_DrawLines(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int Graphics_DrawPath(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Graphics_DrawPolygon(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int Graphics_DrawRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int Graphics_DrawString(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, PointF paramPointF, int paramInt4);

  public static final native int Graphics_DrawString(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, PointF paramPointF, int paramInt4, int paramInt5);

  public static final native int Graphics_FillEllipse(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int Graphics_FillPath(int paramInt1, int paramInt2, int paramInt3);

  public static final native void Graphics_Flush(int paramInt1, int paramInt2);

  public static final native int Graphics_FillPie(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat1, float paramFloat2);

  public static final native int Graphics_FillPolygon(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4);

  public static final native int Graphics_FillRectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int Graphics_GetClipBounds(int paramInt, RectF paramRectF);

  public static final native int Graphics_GetClipBounds(int paramInt, Rect paramRect);

  public static final native int Graphics_GetClip(int paramInt1, int paramInt2);

  public static final native int Graphics_GetHDC(int paramInt);

  public static final native void Graphics_ReleaseHDC(int paramInt1, int paramInt2);

  public static final native int Graphics_GetInterpolationMode(int paramInt);

  public static final native int Graphics_GetSmoothingMode(int paramInt);

  public static final native int Graphics_GetTextRenderingHint(int paramInt);

  public static final native int Graphics_GetTransform(int paramInt1, int paramInt2);

  public static final native int Graphics_GetVisibleClipBounds(int paramInt, Rect paramRect);

  public static final native int Graphics_MeasureDriverString(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float[] paramArrayOfFloat, int paramInt5, int paramInt6, RectF paramRectF);

  public static final native int Graphics_MeasureString(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, PointF paramPointF, RectF paramRectF);

  public static final native int Graphics_MeasureString(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, PointF paramPointF, int paramInt4, RectF paramRectF);

  public static final native int Graphics_ResetClip(int paramInt);

  public static final native int Graphics_Restore(int paramInt1, int paramInt2);

  public static final native int Graphics_Save(int paramInt);

  public static final native int Graphics_ScaleTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int Graphics_SetClip(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Graphics_SetClip(int paramInt1, Rect paramRect, int paramInt2);

  public static final native int Graphics_SetClipPath(int paramInt1, int paramInt2);

  public static final native int Graphics_SetClipPath(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Graphics_SetCompositingQuality(int paramInt1, int paramInt2);

  public static final native int Graphics_SetPageUnit(int paramInt1, int paramInt2);

  public static final native int Graphics_SetPixelOffsetMode(int paramInt1, int paramInt2);

  public static final native int Graphics_SetSmoothingMode(int paramInt1, int paramInt2);

  public static final native int Graphics_SetTransform(int paramInt1, int paramInt2);

  public static final native int Graphics_SetInterpolationMode(int paramInt1, int paramInt2);

  public static final native int Graphics_SetTextRenderingHint(int paramInt1, int paramInt2);

  public static final native int Graphics_TranslateTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int GraphicsPath_new(int paramInt);

  public static final native int GraphicsPath_new(int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static final native void GraphicsPath_delete(int paramInt);

  public static final native int GraphicsPath_AddArc(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public static final native int GraphicsPath_AddBezier(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8);

  public static final native int GraphicsPath_AddLine(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native int GraphicsPath_AddPath(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native int GraphicsPath_AddRectangle(int paramInt, RectF paramRectF);

  public static final native int GraphicsPath_AddString(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, int paramInt4, float paramFloat, PointF paramPointF, int paramInt5);

  public static final native int GraphicsPath_CloseFigure(int paramInt);

  public static final native int GraphicsPath_Clone(int paramInt);

  public static final native int GraphicsPath_Flatten(int paramInt1, int paramInt2, float paramFloat);

  public static final native int GraphicsPath_GetBounds(int paramInt1, RectF paramRectF, int paramInt2, int paramInt3);

  public static final native int GraphicsPath_GetLastPoint(int paramInt, PointF paramPointF);

  public static final native int GraphicsPath_GetPathPoints(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native int GraphicsPath_GetPathTypes(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GraphicsPath_GetPointCount(int paramInt);

  public static final native boolean GraphicsPath_IsOutlineVisible(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, int paramInt3);

  public static final native boolean GraphicsPath_IsVisible(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int GraphicsPath_SetFillMode(int paramInt1, int paramInt2);

  public static final native int GraphicsPath_StartFigure(int paramInt);

  public static final native int GraphicsPath_Transform(int paramInt1, int paramInt2);

  public static final native int HatchBrush_new(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Image_GetLastStatus(int paramInt);

  public static final native int Image_GetPixelFormat(int paramInt);

  public static final native int Image_GetWidth(int paramInt);

  public static final native int Image_GetHeight(int paramInt);

  public static final native int Image_GetPalette(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Image_GetPaletteSize(int paramInt);

  public static final native int ImageAttributes_new();

  public static final native void ImageAttributes_delete(int paramInt);

  public static final native int ImageAttributes_SetWrapMode(int paramInt1, int paramInt2);

  public static final native int ImageAttributes_SetColorMatrix(int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3);

  public static final native void HatchBrush_delete(int paramInt);

  public static final native int LinearGradientBrush_new(PointF paramPointF1, PointF paramPointF2, int paramInt1, int paramInt2);

  public static final native void LinearGradientBrush_delete(int paramInt);

  public static final native int LinearGradientBrush_SetInterpolationColors(int paramInt1, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt2);

  public static final native int LinearGradientBrush_SetWrapMode(int paramInt1, int paramInt2);

  public static final native int LinearGradientBrush_ResetTransform(int paramInt);

  public static final native int LinearGradientBrush_ScaleTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int LinearGradientBrush_TranslateTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int Matrix_new(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public static final native void Matrix_delete(int paramInt);

  public static final native int Matrix_GetElements(int paramInt, float[] paramArrayOfFloat);

  public static final native int Matrix_Invert(int paramInt);

  public static final native boolean Matrix_IsIdentity(int paramInt);

  public static final native int Matrix_Multiply(int paramInt1, int paramInt2, int paramInt3);

  public static final native int Matrix_Rotate(int paramInt1, float paramFloat, int paramInt2);

  public static final native int Matrix_Scale(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int Matrix_Shear(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int Matrix_TransformPoints(int paramInt1, PointF paramPointF, int paramInt2);

  public static final native int Matrix_TransformPoints(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native int Matrix_TransformVectors(int paramInt1, PointF paramPointF, int paramInt2);

  public static final native int Matrix_Translate(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int Matrix_SetElements(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);

  public static final native void MoveMemory(ColorPalette paramColorPalette, int paramInt1, int paramInt2);

  public static final native void MoveMemory(BitmapData paramBitmapData, int paramInt);

  public static final native int PathGradientBrush_new(int paramInt);

  public static final native void PathGradientBrush_delete(int paramInt);

  public static final native int PathGradientBrush_SetCenterColor(int paramInt1, int paramInt2);

  public static final native int PathGradientBrush_SetCenterPoint(int paramInt, PointF paramPointF);

  public static final native int PathGradientBrush_SetInterpolationColors(int paramInt1, int[] paramArrayOfInt, float[] paramArrayOfFloat, int paramInt2);

  public static final native int PathGradientBrush_SetSurroundColors(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int PathGradientBrush_SetGraphicsPath(int paramInt1, int paramInt2);

  public static final native int PathGradientBrush_SetWrapMode(int paramInt1, int paramInt2);

  public static final native int Pen_new(int paramInt, float paramFloat);

  public static final native void Pen_delete(int paramInt);

  public static final native int Pen_GetBrush(int paramInt);

  public static final native int Pen_SetBrush(int paramInt1, int paramInt2);

  public static final native int Pen_SetDashOffset(int paramInt, float paramFloat);

  public static final native int Pen_SetDashPattern(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native int Pen_SetDashStyle(int paramInt1, int paramInt2);

  public static final native int Pen_SetLineCap(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int Pen_SetLineJoin(int paramInt1, int paramInt2);

  public static final native int Pen_SetMiterLimit(int paramInt, float paramFloat);

  public static final native int Pen_SetWidth(int paramInt, float paramFloat);

  public static final native int Point_new(int paramInt1, int paramInt2);

  public static final native void Point_delete(int paramInt);

  public static final native int Region_new(int paramInt);

  public static final native int Region_newGraphicsPath(int paramInt);

  public static final native int Region_new();

  public static final native void Region_delete(int paramInt);

  public static final native int Region_GetHRGN(int paramInt1, int paramInt2);

  public static final native boolean Region_IsInfinite(int paramInt1, int paramInt2);

  public static final native int SolidBrush_new(int paramInt);

  public static final native void SolidBrush_delete(int paramInt);

  public static final native void StringFormat_delete(int paramInt);

  public static final native int StringFormat_Clone(int paramInt);

  public static final native int StringFormat_GenericDefault();

  public static final native int StringFormat_GenericTypographic();

  public static final native int StringFormat_GetFormatFlags(int paramInt);

  public static final native int StringFormat_SetHotkeyPrefix(int paramInt1, int paramInt2);

  public static final native int StringFormat_SetFormatFlags(int paramInt1, int paramInt2);

  public static final native int StringFormat_SetTabStops(int paramInt1, float paramFloat, int paramInt2, float[] paramArrayOfFloat);

  public static final native int TextureBrush_new(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public static final native void TextureBrush_delete(int paramInt);

  public static final native int TextureBrush_SetTransform(int paramInt1, int paramInt2);

  public static final native int TextureBrush_ResetTransform(int paramInt);

  public static final native int TextureBrush_ScaleTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);

  public static final native int TextureBrush_TranslateTransform(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.gdip.Gdip
 * JD-Core Version:    0.6.2
 */