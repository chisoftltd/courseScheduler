package org.eclipse.swt.internal.mozilla;

public class nsID
{
  public int m0;
  public short m1;
  public short m2;
  public byte[] m3 = new byte[8];
  public static final int sizeof = 16;

  public nsID()
  {
  }

  public nsID(String paramString)
  {
    Parse(paramString);
  }

  public boolean Equals(nsID paramnsID)
  {
    int i = XPCOM.nsID_new();
    XPCOM.memmove(i, this, 16);
    int j = XPCOM.nsID_new();
    XPCOM.memmove(j, paramnsID, 16);
    boolean bool = XPCOM.nsID_Equals(i, j) != 0;
    XPCOM.nsID_delete(i);
    XPCOM.nsID_delete(j);
    return bool;
  }

  public void Parse(String paramString)
  {
    if (paramString == null)
      throw new Error();
    int j;
    for (int i = 0; i < 8; i++)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m0 = ((this.m0 << 4) + j);
    }
    if (paramString.charAt(i++) != '-')
      throw new Error();
    while (i < 13)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m1 = ((short)((this.m1 << 4) + j));
      i++;
    }
    if (paramString.charAt(i++) != '-')
      throw new Error();
    while (i < 18)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m2 = ((short)((this.m2 << 4) + j));
      i++;
    }
    if (paramString.charAt(i++) != '-')
      throw new Error();
    while (i < 21)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[0] = ((byte)((this.m3[0] << 4) + j));
      i++;
    }
    while (i < 23)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[1] = ((byte)((this.m3[1] << 4) + j));
      i++;
    }
    if (paramString.charAt(i++) != '-')
      throw new Error();
    while (i < 26)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[2] = ((byte)((this.m3[2] << 4) + j));
      i++;
    }
    while (i < 28)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[3] = ((byte)((this.m3[3] << 4) + j));
      i++;
    }
    while (i < 30)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[4] = ((byte)((this.m3[4] << 4) + j));
      i++;
    }
    while (i < 32)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[5] = ((byte)((this.m3[5] << 4) + j));
      i++;
    }
    while (i < 34)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[6] = ((byte)((this.m3[6] << 4) + j));
      i++;
    }
    while (i < 36)
    {
      j = Character.digit(paramString.charAt(i), 16);
      if (j == -1)
        throw new Error();
      this.m3[7] = ((byte)((this.m3[7] << 4) + j));
      i++;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsID
 * JD-Core Version:    0.6.2
 */