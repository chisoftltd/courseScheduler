package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIFactory;
import org.eclipse.swt.internal.mozilla.nsISupports;

class FilePickerFactory
{
  XPCOMObject supports;
  XPCOMObject factory;
  int refCount = 0;

  FilePickerFactory()
  {
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
        return FilePickerFactory.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.Release();
      }
    };
    this.factory = new XPCOMObject(new int[] { 2, 0, 0, 3, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.CreateInstance(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory.this.LockFactory(paramAnonymousArrayOfInt[0]);
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
    if (this.factory != null)
    {
      this.factory.dispose();
      this.factory = null;
    }
  }

  int getAddress()
  {
    return this.factory.getAddress();
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
    if (localnsID.Equals(nsIFactory.NS_IFACTORY_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.factory.getAddress() }, C.PTR_SIZEOF);
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

  int CreateInstance(int paramInt1, int paramInt2, int paramInt3)
  {
    FilePicker localFilePicker = new FilePicker();
    localFilePicker.AddRef();
    XPCOM.memmove(paramInt3, new int[] { localFilePicker.getAddress() }, C.PTR_SIZEOF);
    return 0;
  }

  int LockFactory(int paramInt)
  {
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.FilePickerFactory
 * JD-Core Version:    0.6.2
 */