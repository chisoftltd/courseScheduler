package org.eclipse.swt.internal.mozilla;

public class nsIChannel extends nsIRequest
{
  static final int LAST_METHOD_ID = nsIRequest.LAST_METHOD_ID + 16;
  public static final String NS_ICHANNEL_IID_STR = "c63a055a-a676-4e71-bf3c-6cfa11082018";
  public static final nsID NS_ICHANNEL_IID = new nsID("c63a055a-a676-4e71-bf3c-6cfa11082018");
  public static final int LOAD_DOCUMENT_URI = 65536;
  public static final int LOAD_RETARGETED_DOCUMENT_URI = 131072;
  public static final int LOAD_REPLACE = 262144;
  public static final int LOAD_INITIAL_DOCUMENT_URI = 524288;
  public static final int LOAD_TARGETED = 1048576;
  public static final int LOAD_CALL_CONTENT_SNIFFERS = 2097152;

  public nsIChannel(int paramInt)
  {
    super(paramInt);
  }

  public int GetOriginalURI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 1, getAddress(), paramArrayOfInt);
  }

  public int SetOriginalURI(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }

  public int GetURI(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 3, getAddress(), paramArrayOfInt);
  }

  public int GetOwner(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 4, getAddress(), paramArrayOfInt);
  }

  public int SetOwner(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 5, getAddress(), paramInt);
  }

  public int GetNotificationCallbacks(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 6, getAddress(), paramArrayOfInt);
  }

  public int SetNotificationCallbacks(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 7, getAddress(), paramInt);
  }

  public int GetSecurityInfo(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 8, getAddress(), paramArrayOfInt);
  }

  public int GetContentType(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 9, getAddress(), paramInt);
  }

  public int SetContentType(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 10, getAddress(), paramInt);
  }

  public int GetContentCharset(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 11, getAddress(), paramInt);
  }

  public int SetContentCharset(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 12, getAddress(), paramInt);
  }

  public int GetContentLength(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 13, getAddress(), paramArrayOfInt);
  }

  public int SetContentLength(int paramInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 14, getAddress(), paramInt);
  }

  public int Open(int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 15, getAddress(), paramArrayOfInt);
  }

  public int AsyncOpen(int paramInt1, int paramInt2)
  {
    return XPCOM.VtblCall(nsIRequest.LAST_METHOD_ID + 16, getAddress(), paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsIChannel
 * JD-Core Version:    0.6.2
 */