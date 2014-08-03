package org.eclipse.swt.custom;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface CTabFolder2Listener extends SWTEventListener
{
  public abstract void close(CTabFolderEvent paramCTabFolderEvent);

  public abstract void minimize(CTabFolderEvent paramCTabFolderEvent);

  public abstract void maximize(CTabFolderEvent paramCTabFolderEvent);

  public abstract void restore(CTabFolderEvent paramCTabFolderEvent);

  public abstract void showList(CTabFolderEvent paramCTabFolderEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabFolder2Listener
 * JD-Core Version:    0.6.2
 */