package org.eclipse.swt.internal.ole.win32;

public class IStorage extends IUnknown
{
  public IStorage(int paramInt)
  {
    super(paramInt);
  }

  public int Commit(int paramInt)
  {
    return COM.VtblCall(9, this.address, paramInt);
  }

  public int CopyTo(int paramInt1, GUID paramGUID, String[] paramArrayOfString, int paramInt2)
  {
    if (paramArrayOfString != null)
      return -2147024809;
    return COM.VtblCall(7, this.address, paramInt1, paramGUID, 0, paramInt2);
  }

  public int CreateStorage(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
      arrayOfChar = (paramString + "").toCharArray();
    return COM.VtblCall(5, this.address, arrayOfChar, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int CreateStream(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
      arrayOfChar = (paramString + "").toCharArray();
    return COM.VtblCall(3, this.address, arrayOfChar, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int DestroyElement(String paramString)
  {
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
      arrayOfChar = (paramString + "").toCharArray();
    return COM.VtblCall(12, this.address, arrayOfChar);
  }

  public int EnumElements(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    return COM.VtblCall(11, this.address, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int OpenStorage(String paramString, int paramInt1, int paramInt2, String[] paramArrayOfString, int paramInt3, int[] paramArrayOfInt)
  {
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
      arrayOfChar = (paramString + "").toCharArray();
    if (paramArrayOfString != null)
      return -2147024809;
    return COM.VtblCall(6, this.address, arrayOfChar, paramInt1, paramInt2, 0, paramInt3, paramArrayOfInt);
  }

  public int OpenStream(String paramString, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
      arrayOfChar = (paramString + "").toCharArray();
    return COM.VtblCall(4, this.address, arrayOfChar, paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public int RenameElement(String paramString1, String paramString2)
  {
    char[] arrayOfChar1 = (char[])null;
    if (paramString1 != null)
      arrayOfChar1 = (paramString1 + "").toCharArray();
    char[] arrayOfChar2 = (char[])null;
    if (paramString2 != null)
      arrayOfChar2 = (paramString2 + "").toCharArray();
    return COM.VtblCall(13, this.address, arrayOfChar1, arrayOfChar2);
  }

  public int Revert()
  {
    return COM.VtblCall(10, this.address);
  }

  public int SetClass(GUID paramGUID)
  {
    return COM.VtblCall(15, this.address, paramGUID);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IStorage
 * JD-Core Version:    0.6.2
 */