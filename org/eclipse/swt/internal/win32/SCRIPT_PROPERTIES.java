package org.eclipse.swt.internal.win32;

public class SCRIPT_PROPERTIES
{
  public short langid;
  public boolean fNumeric;
  public boolean fComplex;
  public boolean fNeedsWordBreaking;
  public boolean fNeedsCaretInfo;
  public byte bCharSet;
  public boolean fControl;
  public boolean fPrivateUseArea;
  public boolean fNeedsCharacterJustify;
  public boolean fInvalidGlyph;
  public boolean fInvalidLogAttr;
  public boolean fCDM;
  public boolean fAmbiguousCharSet;
  public boolean fClusterSizeVaries;
  public boolean fRejectInvalid;
  public static final int sizeof = OS.SCRIPT_PROPERTIES_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.SCRIPT_PROPERTIES
 * JD-Core Version:    0.6.2
 */