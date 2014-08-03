package org.eclipse.swt.internal.win32;

public class NMTREEVIEW
{
  public NMHDR hdr = new NMHDR();
  public int action;
  public TVITEM itemOld = new TVITEM();
  public TVITEM itemNew = new TVITEM();
  public POINT ptDrag = new POINT();
  public static final int sizeof = OS.NMTREEVIEW_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.NMTREEVIEW
 * JD-Core Version:    0.6.2
 */