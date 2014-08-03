package org.eclipse.swt.internal.win32;

public class NONCLIENTMETRICSA extends NONCLIENTMETRICS
{
  public LOGFONTA lfCaptionFont = new LOGFONTA();
  public LOGFONTA lfSmCaptionFont = new LOGFONTA();
  public LOGFONTA lfMenuFont = new LOGFONTA();
  public LOGFONTA lfStatusFont = new LOGFONTA();
  public LOGFONTA lfMessageFont = new LOGFONTA();
  public static final int sizeof = OS.NONCLIENTMETRICSA_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NONCLIENTMETRICSA
 * JD-Core Version:    0.6.2
 */