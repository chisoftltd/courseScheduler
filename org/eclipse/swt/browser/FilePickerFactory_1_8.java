package org.eclipse.swt.browser;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;

class FilePickerFactory_1_8 extends FilePickerFactory
{
  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.Release();
      }
    };
    this.factory = new XPCOMObject(new int[] { 2, 0, 0, 3, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.CreateInstance(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return FilePickerFactory_1_8.this.LockFactory(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  int CreateInstance(int paramInt1, int paramInt2, int paramInt3)
  {
    FilePicker_1_8 localFilePicker_1_8 = new FilePicker_1_8();
    localFilePicker_1_8.AddRef();
    XPCOM.memmove(paramInt3, new int[] { localFilePicker_1_8.getAddress() }, C.PTR_SIZEOF);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.FilePickerFactory_1_8
 * JD-Core Version:    0.6.2
 */