package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIInputStream;
import org.eclipse.swt.internal.mozilla.nsISupports;

class InputStream
{
  XPCOMObject inputStream;
  int refCount = 0;
  byte[] buffer;
  int index = 0;

  InputStream(byte[] paramArrayOfByte)
  {
    this.buffer = paramArrayOfByte;
    this.index = 0;
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void createCOMInterfaces()
  {
    this.inputStream = new XPCOMObject(new int[] { 2, 0, 0, 0, 1, 3, 4, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.Close();
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.Available(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.Read(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.ReadSegments(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return InputStream.this.IsNonBlocking(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.inputStream != null)
    {
      this.inputStream.dispose();
      this.inputStream = null;
    }
  }

  int getAddress()
  {
    return this.inputStream.getAddress();
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    nsID localnsID = new nsID();
    XPCOM.memmove(localnsID, paramInt1, 16);
    if (localnsID.Equals(nsISupports.NS_ISUPPORTS_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.inputStream.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIInputStream.NS_IINPUTSTREAM_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.inputStream.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    XPCOM.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  int Close()
  {
    this.buffer = null;
    this.index = 0;
    return 0;
  }

  int Available(int paramInt)
  {
    int i = this.buffer == null ? 0 : this.buffer.length - this.index;
    XPCOM.memmove(paramInt, new int[] { i }, 4);
    return 0;
  }

  int Read(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.min(paramInt2, this.buffer == null ? 0 : this.buffer.length - this.index);
    if (i > 0)
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.buffer, this.index, arrayOfByte, 0, i);
      XPCOM.memmove(paramInt1, arrayOfByte, i);
      this.index += i;
    }
    XPCOM.memmove(paramInt3, new int[] { i }, 4);
    return 0;
  }

  int ReadSegments(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.buffer == null ? 0 : this.buffer.length - this.index;
    if (paramInt3 != -1)
      i = Math.min(i, paramInt3);
    int j = i;
    while (j > 0)
    {
      int[] arrayOfInt = new int[1];
      int k = XPCOM.Call(paramInt1, getAddress(), paramInt2, this.buffer, this.index, j, arrayOfInt);
      if (k != 0)
        break;
      this.index += arrayOfInt[0];
      j -= arrayOfInt[0];
    }
    XPCOM.memmove(paramInt4, new int[] { i - j }, 4);
    return 0;
  }

  int IsNonBlocking(int paramInt)
  {
    XPCOM.memmove(paramInt, new int[1], 4);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.InputStream
 * JD-Core Version:    0.6.2
 */