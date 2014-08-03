package org.eclipse.swt.internal.win32;

public class NONCLIENTMETRICSW extends NONCLIENTMETRICS
{
  public LOGFONTW lfCaptionFont = new LOGFONTW();
  public LOGFONTW lfSmCaptionFont = new LOGFONTW();
  public LOGFONTW lfMenuFont = new LOGFONTW();
  public LOGFONTW lfStatusFont = new LOGFONTW();
  public LOGFONTW lfMessageFont = new LOGFONTW();
  public static final int sizeof = OS.NONCLIENTMETRICSW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NONCLIENTMETRICSW
 * JD-Core Version:    0.6.2
 */