package org.eclipse.swt.internal.mozilla;

public class nsIFile extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 45;
  public static final String NS_IFILE_IID_STR = "c8c0a080-0868-11d3-915f-d9d889d48e3c";
  public static final nsID NS_IFILE_IID = new nsID("c8c0a080-0868-11d3-915f-d9d889d48e3c");
  public static final int NORMAL_FILE_TYPE = 0;
  public static final int DIRECTORY_TYPE = 1;

  public nsIFile(int paramInt)
  {
    super(paramInt);
  }

  public int Append(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int AppendNative(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int Normalize()
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 3, getAddress());
  }

  public int Create(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 4, getAddress(), paramInt1, paramInt2);
  }

  public int GetLeafName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int SetLeafName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 6, getAddress(), paramInt);
  }

  public int GetNativeLeafName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int SetNativeLeafName(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 8, getAddress(), paramInt);
  }

  public int CopyTo(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 9, getAddress(), paramInt1, paramInt2);
  }

  public int CopyToNative(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 10, getAddress(), paramInt1, paramInt2);
  }

  public int CopyToFollowingLinks(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 11, getAddress(), paramInt1, paramInt2);
  }

  public int CopyToFollowingLinksNative(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 12, getAddress(), paramInt1, paramInt2);
  }

  public int MoveTo(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 13, getAddress(), paramInt1, paramInt2);
  }

  public int MoveToNative(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 14, getAddress(), paramInt1, paramInt2);
  }

  public int Remove(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 15, getAddress(), paramInt);
  }

  public int GetPermissions(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 16, getAddress(), paramArrayOfInt);
  }

  public int SetPermissions(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 17, getAddress(), paramInt);
  }

  public int GetPermissionsOfLink(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 18, getAddress(), paramArrayOfInt);
  }

  public int SetPermissionsOfLink(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 19, getAddress(), paramInt);
  }

  public int GetLastModifiedTime(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 20, getAddress(), paramArrayOfLong);
  }

  public int SetLastModifiedTime(long paramLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 21, getAddress(), paramLong);
  }

  public int GetLastModifiedTimeOfLink(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 22, getAddress(), paramArrayOfLong);
  }

  public int SetLastModifiedTimeOfLink(long paramLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 23, getAddress(), paramLong);
  }

  public int GetFileSize(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 24, getAddress(), paramArrayOfLong);
  }

  public int SetFileSize(long paramLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 25, getAddress(), paramLong);
  }

  public int GetFileSizeOfLink(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 26, getAddress(), paramArrayOfLong);
  }

  public int GetTarget(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 27, getAddress(), paramInt);
  }

  public int GetNativeTarget(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 28, getAddress(), paramInt);
  }

  public int GetPath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 29, getAddress(), paramInt);
  }

  public int GetNativePath(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 30, getAddress(), paramInt);
  }

  public int Exists(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 31, getAddress(), paramArrayOfInt);
  }

  public int IsWritable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 32, getAddress(), paramArrayOfInt);
  }

  public int IsReadable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 33, getAddress(), paramArrayOfInt);
  }

  public int IsExecutable(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 34, getAddress(), paramArrayOfInt);
  }

  public int IsHidden(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 35, getAddress(), paramArrayOfInt);
  }

  public int IsDirectory(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 36, getAddress(), paramArrayOfInt);
  }

  public int IsFile(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 37, getAddress(), paramArrayOfInt);
  }

  public int IsSymlink(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 38, getAddress(), paramArrayOfInt);
  }

  public int IsSpecial(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 39, getAddress(), paramArrayOfInt);
  }

  public int CreateUnique(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 40, getAddress(), paramInt1, paramInt2);
  }

  public int Clone(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 41, getAddress(), paramArrayOfInt);
  }

  public int Equals(int paramInt, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 42, getAddress(), paramInt, paramArrayOfInt);
  }

  public int Contains(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 43, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int GetParent(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 44, getAddress(), paramArrayOfInt);
  }

  public int GetDirectoryEntries(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 45, getAddress(), paramArrayOfInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIFile
 * JD-Core Version:    0.6.2
 */