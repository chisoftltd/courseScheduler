package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.win32.PAINTSTRUCT;

public final class GCData
{
  public Device device;
  public int style;
  public int state = -1;
  public int foreground = -1;
  public int background = -1;
  public Font font;
  public Pattern foregroundPattern;
  public Pattern backgroundPattern;
  public int lineStyle = 1;
  public float lineWidth;
  public int lineCap = 1;
  public int lineJoin = 1;
  public float lineDashesOffset;
  public float[] lineDashes;
  public float lineMiterLimit = 10.0F;
  public int alpha = 255;
  public Image image;
  public int hPen;
  public int hOldPen;
  public int hBrush;
  public int hOldBrush;
  public int hNullBitmap;
  public int hwnd;
  public PAINTSTRUCT ps;
  public int layout = -1;
  public int gdipGraphics;
  public int gdipPen;
  public int gdipBrush;
  public int gdipFgBrush;
  public int gdipBgBrush;
  public int gdipFont;
  public int hGDIFont;
  public float gdipXOffset;
  public float gdipYOffset;
  public int uiState = 0;
  public boolean focusDrawn;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.GCData
 * JD-Core Version:    0.6.2
 */