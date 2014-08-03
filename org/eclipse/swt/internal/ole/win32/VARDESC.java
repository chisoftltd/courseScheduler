package org.eclipse.swt.internal.ole.win32;

public class VARDESC
{
  public int memid;
  public int lpstrSchema;
  public int oInst;
  public int elemdescVar_tdesc_union;
  public short elemdescVar_tdesc_vt;
  public int elemdescVar_paramdesc_pparamdescex;
  public short elemdescVar_paramdesc_wParamFlags;
  public short wVarFlags;
  public int varkind;
  public static final int sizeof = COM.VARDESC_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.VARDESC
 * JD-Core Version:    0.6.2
 */