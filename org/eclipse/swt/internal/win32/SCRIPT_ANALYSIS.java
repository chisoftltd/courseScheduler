package org.eclipse.swt.internal.win32;

public class SCRIPT_ANALYSIS
{
  public short eScript;
  public boolean fRTL;
  public boolean fLayoutRTL;
  public boolean fLinkBefore;
  public boolean fLinkAfter;
  public boolean fLogicalOrder;
  public boolean fNoGlyphIndex;
  public SCRIPT_STATE s = new SCRIPT_STATE();
  public static final int sizeof = OS.SCRIPT_ANALYSIS_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SCRIPT_ANALYSIS
 * JD-Core Version:    0.6.2
 */