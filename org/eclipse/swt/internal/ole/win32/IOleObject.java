package org.eclipse.swt.internal.ole.win32;

import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;

public class IOleObject extends IUnknown
{
  public IOleObject(int paramInt)
  {
    super(paramInt);
  }

  public int Advise(int paramInt, int[] paramArrayOfInt)
  {
    return COM.VtblCall(19, this.address, paramInt, paramArrayOfInt);
  }

  public int Close(int paramInt)
  {
    return COM.VtblCall(6, this.address, paramInt);
  }

  public int DoVerb(int paramInt1, MSG paramMSG, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT)
  {
    return COM.VtblCall(11, this.address, paramInt1, paramMSG, paramInt2, paramInt3, paramInt4, paramRECT);
  }

  public int GetClientSite(int[] paramArrayOfInt)
  {
    return COM.VtblCall(4, this.address, paramArrayOfInt);
  }

  public int GetExtent(int paramInt, SIZE paramSIZE)
  {
    return COM.VtblCall(18, this.address, paramInt, paramSIZE);
  }

  public int SetClientSite(int paramInt)
  {
    return COM.VtblCall(3, this.address, paramInt);
  }

  public int SetExtent(int paramInt, SIZE paramSIZE)
  {
    return COM.VtblCall(17, this.address, paramInt, paramSIZE);
  }

  public int SetHostNames(String paramString1, String paramString2)
  {
    char[] arrayOfChar1 = (char[])null;
    if (paramString1 != null)
    {
      int i = paramString1.length();
      arrayOfChar1 = new char[i + 1];
      paramString1.getChars(0, i, arrayOfChar1, 0);
    }
    char[] arrayOfChar2 = (char[])null;
    if (paramString2 != null)
    {
      int j = paramString2.length();
      arrayOfChar2 = new char[j + 1];
      paramString2.getChars(0, j, arrayOfChar2, 0);
    }
    return COM.VtblCall(5, this.address, arrayOfChar1, arrayOfChar2);
  }

  public int Unadvise(int paramInt)
  {
    return COM.VtblCall(20, this.address, paramInt);
  }

  public int Update()
  {
    return COM.VtblCall(13, this.address);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IOleObject
 * JD-Core Version:    0.6.2
 */