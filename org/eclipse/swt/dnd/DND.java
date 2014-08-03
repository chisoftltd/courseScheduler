package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;

public class DND
{
  public static final int CLIPBOARD = 1;
  public static final int SELECTION_CLIPBOARD = 2;
  public static final int DROP_NONE = 0;
  public static final int DROP_COPY = 1;
  public static final int DROP_MOVE = 2;
  public static final int DROP_LINK = 4;
  public static final int DROP_TARGET_MOVE = 8;
  public static final int DROP_DEFAULT = 16;
  public static final int DragEnd = 2000;
  public static final int DragSetData = 2001;
  public static final int DragEnter = 2002;
  public static final int DragLeave = 2003;
  public static final int DragOver = 2004;
  public static final int DragOperationChanged = 2005;
  public static final int Drop = 2006;
  public static final int DropAccept = 2007;
  public static final int DragStart = 2008;
  public static final int FEEDBACK_NONE = 0;
  public static final int FEEDBACK_SELECT = 1;
  public static final int FEEDBACK_INSERT_BEFORE = 2;
  public static final int FEEDBACK_INSERT_AFTER = 4;
  public static final int FEEDBACK_SCROLL = 8;
  public static final int FEEDBACK_EXPAND = 16;
  public static final int ERROR_CANNOT_INIT_DRAG = 2000;
  public static final int ERROR_CANNOT_INIT_DROP = 2001;
  public static final int ERROR_CANNOT_SET_CLIPBOARD = 2002;
  public static final int ERROR_INVALID_DATA = 2003;
  public static final String DROP_TARGET_KEY = "DropTarget";
  public static final String DRAG_SOURCE_KEY = "DragSource";
  static final String INIT_DRAG_MESSAGE = "Cannot initialize Drag";
  static final String INIT_DROP_MESSAGE = "Cannot initialize Drop";
  static final String CANNOT_SET_CLIPBOARD_MESSAGE = "Cannot set data in clipboard";
  static final String INVALID_DATA_MESSAGE = "Data does not have correct format for type";

  public static void error(int paramInt)
  {
    error(paramInt, 0);
  }

  public static void error(int paramInt1, int paramInt2)
  {
    String str;
    switch (paramInt1)
    {
    case 2000:
      str = "Cannot initialize Drag";
      if (paramInt2 != 0)
        str = str + " result = " + paramInt2;
      throw new SWTError(paramInt1, str);
    case 2001:
      str = "Cannot initialize Drop";
      if (paramInt2 != 0)
        str = str + " result = " + paramInt2;
      throw new SWTError(paramInt1, str);
    case 2002:
      str = "Cannot set data in clipboard";
      if (paramInt2 != 0)
        str = str + " result = " + paramInt2;
      throw new SWTError(paramInt1, str);
    case 2003:
      str = "Data does not have correct format for type";
      if (paramInt2 != 0)
        str = str + " result = " + paramInt2;
      throw new SWTException(paramInt1, str);
    }
    SWT.error(paramInt1);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.DND
 * JD-Core Version:    0.6.2
 */