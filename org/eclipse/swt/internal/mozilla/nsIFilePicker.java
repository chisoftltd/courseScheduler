package org.eclipse.swt.internal.mozilla;

public class nsIFilePicker extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 15;
  public static final String NS_IFILEPICKER_IID_STR = "c47de916-1dd1-11b2-8141-82507fa02b21";
  public static final nsID NS_IFILEPICKER_IID = new nsID("c47de916-1dd1-11b2-8141-82507fa02b21");
  public static final int modeOpen = 0;
  public static final int modeSave = 1;
  public static final int modeGetFolder = 2;
  public static final int modeOpenMultiple = 3;
  public static final int returnOK = 0;
  public static final int returnCancel = 1;
  public static final int returnReplace = 2;
  public static final int filterAll = 1;
  public static final int filterHTML = 2;
  public static final int filterText = 4;
  public static final int filterImages = 8;
  public static final int filterXML = 16;
  public static final int filterXUL = 32;
  public static final int filterApps = 64;

  public nsIFilePicker(int paramInt)
  {
    super(paramInt);
  }

  public int Init(int paramInt1, char[] paramArrayOfChar, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt1, paramArrayOfChar, paramInt2);
  }

  public int AppendFilters(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int AppendFilter(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress(), paramArrayOfChar1, paramArrayOfChar2);
  }

  public int GetDefaultString(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetDefaultString(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramArrayOfChar);
  }

  public int GetDefaultExtension(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int SetDefaultExtension(char[] paramArrayOfChar)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramArrayOfChar);
  }

  public int GetFilterIndex(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int SetFilterIndex(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int GetDisplayDirectory(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramArrayOfInt);
  }

  public int SetDisplayDirectory(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetFile(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramArrayOfInt);
  }

  public int GetFileURL(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramArrayOfInt);
  }

  public int GetFiles(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramArrayOfInt);
  }

  public int Show(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIFilePicker
 * JD-Core Version:    0.6.2
 */