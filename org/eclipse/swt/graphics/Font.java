package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.OS;

public final class Font extends Resource
{
  public int handle;

  Font(Device paramDevice)
  {
    super(paramDevice);
  }

  public Font(Device paramDevice, FontData paramFontData)
  {
    super(paramDevice);
    init(paramFontData);
    init();
  }

  public Font(Device paramDevice, FontData[] paramArrayOfFontData)
  {
    super(paramDevice);
    if (paramArrayOfFontData == null)
      SWT.error(4);
    if (paramArrayOfFontData.length == 0)
      SWT.error(5);
    for (int i = 0; i < paramArrayOfFontData.length; i++)
      if (paramArrayOfFontData[i] == null)
        SWT.error(5);
    init(paramArrayOfFontData[0]);
    init();
  }

  public Font(Device paramDevice, String paramString, int paramInt1, int paramInt2)
  {
    super(paramDevice);
    if (paramString == null)
      SWT.error(4);
    init(new FontData(paramString, paramInt1, paramInt2));
    init();
  }

  Font(Device paramDevice, String paramString, float paramFloat, int paramInt)
  {
    super(paramDevice);
    if (paramString == null)
      SWT.error(4);
    init(new FontData(paramString, paramFloat, paramInt));
    init();
  }

  void destroy()
  {
    OS.DeleteObject(this.handle);
    this.handle = 0;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Font))
      return false;
    Font localFont = (Font)paramObject;
    return (this.device == localFont.device) && (this.handle == localFont.handle);
  }

  public FontData[] getFontData()
  {
    if (isDisposed())
      SWT.error(44);
    LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
    OS.GetObject(this.handle, LOGFONT.sizeof, localLOGFONTA);
    return new FontData[] { FontData.win32_new(localLOGFONTA, this.device.computePoints(localLOGFONTA, this.handle)) };
  }

  public int hashCode()
  {
    return this.handle;
  }

  void init(FontData paramFontData)
  {
    if (paramFontData == null)
      SWT.error(4);
    LOGFONT localLOGFONT = paramFontData.data;
    int i = localLOGFONT.lfHeight;
    localLOGFONT.lfHeight = this.device.computePixels(paramFontData.height);
    this.handle = OS.CreateFontIndirect(localLOGFONT);
    localLOGFONT.lfHeight = i;
    if (this.handle == 0)
      SWT.error(2);
  }

  public boolean isDisposed()
  {
    return this.handle == 0;
  }

  public String toString()
  {
    if (isDisposed())
      return "Font {*DISPOSED*}";
    return "Font {" + this.handle + "}";
  }

  public static Font win32_new(Device paramDevice, int paramInt)
  {
    Font localFont = new Font(paramDevice);
    localFont.handle = paramInt;
    return localFont;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Font
 * JD-Core Version:    0.6.2
 */