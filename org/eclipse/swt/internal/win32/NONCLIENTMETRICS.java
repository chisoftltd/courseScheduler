package org.eclipse.swt.internal.win32;

public abstract class NONCLIENTMETRICS
{
  public int cbSize;
  public int iBorderWidth;
  public int iScrollWidth;
  public int iScrollHeight;
  public int iCaptionWidth;
  public int iCaptionHeight;
  public int iSmCaptionWidth;
  public int iSmCaptionHeight;
  public int iMenuWidth;
  public int iMenuHeight;
  public static final int sizeof = OS.IsUnicode ? OS.NONCLIENTMETRICSW_sizeof() : OS.NONCLIENTMETRICSA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NONCLIENTMETRICS
 * JD-Core Version:    0.6.2
 */