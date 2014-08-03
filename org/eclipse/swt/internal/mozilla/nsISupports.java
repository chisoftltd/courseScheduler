package org.eclipse.swt.internal.mozilla;

public class nsISupports
{
  static final boolean IsSolaris = (str.startsWith("sunos")) || (str.startsWith("solaris"));
  static final int FIRST_METHOD_ID = IsSolaris ? 2 : 0;
  static final int LAST_METHOD_ID = FIRST_METHOD_ID + 2;
  public static final String NS_ISUPPORTS_IID_STR = "00000000-0000-0000-c000-000000000046";
  public static final nsID NS_ISUPPORTS_IID = new nsID("00000000-0000-0000-c000-000000000046");
  int address;

  static
  {
    String str = System.getProperty("os.name").toLowerCase();
  }

  public nsISupports(int paramInt)
  {
    this.address = paramInt;
  }

  public int getAddress()
  {
    return this.address;
  }

  public int QueryInterface(nsID paramnsID, int[] paramArrayOfInt)
  {
    return XPCOM.VtblCall(FIRST_METHOD_ID, getAddress(), paramnsID, paramArrayOfInt);
  }

  public int AddRef()
  {
    return XPCOM.VtblCall(FIRST_METHOD_ID + 1, getAddress());
  }

  public int Release()
  {
    return XPCOM.VtblCall(FIRST_METHOD_ID + 2, getAddress());
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsISupports
 * JD-Core Version:    0.6.2
 */