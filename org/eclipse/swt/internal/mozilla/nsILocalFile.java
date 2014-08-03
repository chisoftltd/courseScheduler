package org.eclipse.swt.internal.mozilla;

public class nsILocalFile extends nsIFile
{
  static final int LAST_METHOD_ID = nsIFile.LAST_METHOD_ID + 17;
  public static final String NS_ILOCALFILE_IID_STR = "aa610f20-a889-11d3-8c81-000064657374";
  public static final nsID NS_ILOCALFILE_IID = new nsID("aa610f20-a889-11d3-8c81-000064657374");

  public nsILocalFile(int paramInt)
  {
    super(paramInt);
  }

  public int InitWithPath(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int InitWithNativePath(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int InitWithFile(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 3, getAddress(), paramInt);
  }

  public int GetFollowLinks(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetFollowLinks(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int OpenNSPRFileDesc(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 6, getAddress(), paramInt1, paramInt2, paramArrayOfInt);
  }

  public int OpenANSIFileDesc(byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 7, getAddress(), paramArrayOfByte, paramArrayOfInt);
  }

  public int Load(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetDiskSpaceAvailable(long[] paramArrayOfLong)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 9, getAddress(), paramArrayOfLong);
  }

  public int AppendRelativePath(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }

  public int AppendRelativeNativePath(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int GetPersistentDescriptor(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 12, getAddress(), paramInt);
  }

  public int SetPersistentDescriptor(int paramInt)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 13, getAddress(), paramInt);
  }

  public int Reveal()
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 14, getAddress());
  }

  public int Launch()
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 15, getAddress());
  }

  public int GetRelativeDescriptor(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 16, getAddress(), paramInt1, paramInt2);
  }

  public int SetRelativeDescriptor(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsIFile.LAST_METHOD_ID + 17, getAddress(), paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsILocalFile
 * JD-Core Version:    0.6.2
 */