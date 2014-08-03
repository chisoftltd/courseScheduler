package org.eclipse.swt.internal.mozilla;

public class nsEmbedString
{
  int handle;

  public nsEmbedString()
  {
    this.handle = XPCOM.nsEmbedString_new();
  }

  public nsEmbedString(String paramString)
  {
    if (paramString != null)
    {
      char[] arrayOfChar = new char[paramString.length() + 1];
      paramString.getChars(0, paramString.length(), arrayOfChar, 0);
      this.handle = XPCOM.nsEmbedString_new(arrayOfChar);
    }
  }

  public int getAddress()
  {
    return this.handle;
  }

  public String toString()
  {
    if (this.handle == 0)
      return null;
    int i = XPCOM.nsEmbedString_Length(this.handle);
    int j = XPCOM.nsEmbedString_get(this.handle);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, j, i * 2);
    return new String(arrayOfChar);
  }

  public void dispose()
  {
    if (this.handle == 0)
      return;
    XPCOM.nsEmbedString_delete(this.handle);
    this.handle = 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.nsEmbedString
 * JD-Core Version:    0.6.2
 */