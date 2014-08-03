package org.eclipse.swt.internal.ole.win32;

public class FUNCDESC
{
  public int memid;
  public int lprgscode;
  public int lprgelemdescParam;
  public int funckind;
  public int invkind;
  public int callconv;
  public short cParams;
  public short cParamsOpt;
  public short oVft;
  public short cScodes;
  public int elemdescFunc_tdesc_union;
  public short elemdescFunc_tdesc_vt;
  public int elemdescFunc_paramdesc_pparamdescex;
  public short elemdescFunc_paramdesc_wParamFlags;
  public short wFuncFlags;
  public static final int sizeof = COM.FUNCDESC_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.FUNCDESC
 * JD-Core Version:    0.6.2
 */