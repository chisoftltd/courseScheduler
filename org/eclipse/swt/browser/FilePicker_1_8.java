package org.eclipse.swt.browser;

import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;

class FilePicker_1_8 extends FilePicker
{
  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.Release();
      }
    };
    this.filePicker = new XPCOMObject(new int[] { 2, 0, 0, 3, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.Init(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], (short)paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.AppendFilters(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.AppendFilter(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetDefaultString(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.SetDefaultString(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetDefaultExtension(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.SetDefaultExtension(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetFilterIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.SetFilterIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetDisplayDirectory(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.SetDisplayDirectory(paramAnonymousArrayOfInt[0]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetFile(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetFileURL(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.GetFiles(paramAnonymousArrayOfInt[0]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return FilePicker_1_8.this.Show(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  String parseAString(int paramInt)
  {
    if (paramInt == 0)
      return null;
    int i = XPCOM.nsEmbedString_Length(paramInt);
    int j = XPCOM.nsEmbedString_get(paramInt);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, j, i * 2);
    return new String(arrayOfChar);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.FilePicker_1_8
 * JD-Core Version:    0.6.2
 */