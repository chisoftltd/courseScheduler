package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsISimpleEnumerator;
import org.eclipse.swt.internal.mozilla.nsISupports;

class SimpleEnumerator
{
  XPCOMObject supports;
  XPCOMObject simpleEnumerator;
  int refCount = 0;
  nsISupports[] values;
  int index = 0;

  SimpleEnumerator(nsISupports[] paramArrayOfnsISupports)
  {
    this.values = paramArrayOfnsISupports;
    for (int i = 0; i < paramArrayOfnsISupports.length; i++)
      paramArrayOfnsISupports[i].AddRef();
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.Release();
      }
    };
    this.simpleEnumerator = new XPCOMObject(new int[] { 2, 0, 0, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.HasMoreElements(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return SimpleEnumerator.this.GetNext(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.supports != null)
    {
      this.supports.dispose();
      this.supports = null;
    }
    if (this.simpleEnumerator != null)
    {
      this.simpleEnumerator.dispose();
      this.simpleEnumerator = null;
    }
    if (this.values != null)
    {
      for (int i = 0; i < this.values.length; i++)
        this.values[i].Release();
      this.values = null;
    }
  }

  int getAddress()
  {
    return this.simpleEnumerator.getAddress();
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    nsID localnsID = new nsID();
    XPCOM.memmove(localnsID, paramInt1, 16);
    if (localnsID.Equals(nsISupports.NS_ISUPPORTS_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.supports.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsISimpleEnumerator.NS_ISIMPLEENUMERATOR_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.simpleEnumerator.getAddress() }, C.PTR_SIZEOF);
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

  int HasMoreElements(int paramInt)
  {
    int i = (this.values != null) && (this.index < this.values.length) ? 1 : 0;
    XPCOM.memmove(paramInt, new int[] { i != 0 ? 1 : 0 }, 4);
    return 0;
  }

  int GetNext(int paramInt)
  {
    if ((this.values == null) || (this.index == this.values.length))
      return -2147418113;
    nsISupports localnsISupports = this.values[(this.index++)];
    localnsISupports.AddRef();
    XPCOM.memmove(paramInt, new int[] { localnsISupports.getAddress() }, C.PTR_SIZEOF);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.SimpleEnumerator
 * JD-Core Version:    0.6.2
 */