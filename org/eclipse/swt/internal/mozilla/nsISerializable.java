package org.eclipse.swt.internal.mozilla;

public class nsISerializable extends nsISupports
{
  static final int LAST_METHOD_ID = nsISupports.LAST_METHOD_ID + 2;
  public static final String NS_ISERIALIZABLE_IID_STR = "91cca981-c26d-44a8-bebe-d9ed4891503a";
  public static final nsID NS_ISERIALIZABLE_IID = new nsID("91cca981-c26d-44a8-bebe-d9ed4891503a");

  public nsISerializable(int paramInt)
  {
    super(paramInt);
  }

  public int Read(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 1, getAddress(), paramInt);
  }

  public int Write(int paramInt)
  {
    return XPCOM.VtblCall(nsISupports.LAST_METHOD_ID + 2, getAddress(), paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsISerializable
 * JD-Core Version:    0.6.2
 */