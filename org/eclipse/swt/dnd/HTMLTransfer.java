package org.eclipse.swt.dnd;

import org.eclipse.swt.internal.ole.win32.FORMATETC;
import org.eclipse.swt.internal.ole.win32.IDataObject;
import org.eclipse.swt.internal.ole.win32.STGMEDIUM;
import org.eclipse.swt.internal.win32.OS;

public class HTMLTransfer extends ByteArrayTransfer
{
  static HTMLTransfer _instance = new HTMLTransfer();
  static final String HTML_FORMAT = "HTML Format";
  static final int HTML_FORMATID = registerType("HTML Format");
  static final String NUMBER = "00000000";
  static final String HEADER = "Version:0.9\r\nStartHTML:00000000\r\nEndHTML:00000000\r\nStartFragment:00000000\r\nEndFragment:00000000\r\n";
  static final String PREFIX = "<html><body><!--StartFragment-->";
  static final String SUFFIX = "<!--EndFragment--></body></html>";
  static final String StartFragment = "StartFragment:";
  static final String EndFragment = "EndFragment:";

  public static HTMLTransfer getInstance()
  {
    return _instance;
  }

  public void javaToNative(Object paramObject, TransferData paramTransferData)
  {
    if ((!checkHTML(paramObject)) || (!isSupportedType(paramTransferData)))
      DND.error(2003);
    String str1 = (String)paramObject;
    int i = str1.length();
    char[] arrayOfChar = new char[i + 1];
    str1.getChars(0, i, arrayOfChar, 0);
    int j = OS.WideCharToMultiByte(65001, 0, arrayOfChar, -1, null, 0, null, null);
    if (j == 0)
    {
      paramTransferData.stgmedium = new STGMEDIUM();
      paramTransferData.result = -2147221402;
      return;
    }
    int k = "Version:0.9\r\nStartHTML:00000000\r\nEndHTML:00000000\r\nStartFragment:00000000\r\nEndFragment:00000000\r\n".length();
    int m = k + "<html><body><!--StartFragment-->".length();
    int n = m + j - 1;
    int i1 = n + "<!--EndFragment--></body></html>".length();
    StringBuffer localStringBuffer = new StringBuffer("Version:0.9\r\nStartHTML:00000000\r\nEndHTML:00000000\r\nStartFragment:00000000\r\nEndFragment:00000000\r\n");
    int i2 = "00000000".length();
    int i3 = localStringBuffer.toString().indexOf("00000000");
    String str2 = Integer.toString(k);
    localStringBuffer.replace(i3 + i2 - str2.length(), i3 + i2, str2);
    i3 = localStringBuffer.toString().indexOf("00000000", i3);
    str2 = Integer.toString(i1);
    localStringBuffer.replace(i3 + i2 - str2.length(), i3 + i2, str2);
    i3 = localStringBuffer.toString().indexOf("00000000", i3);
    str2 = Integer.toString(m);
    localStringBuffer.replace(i3 + i2 - str2.length(), i3 + i2, str2);
    i3 = localStringBuffer.toString().indexOf("00000000", i3);
    str2 = Integer.toString(n);
    localStringBuffer.replace(i3 + i2 - str2.length(), i3 + i2, str2);
    localStringBuffer.append("<html><body><!--StartFragment-->");
    localStringBuffer.append(str1);
    localStringBuffer.append("<!--EndFragment--></body></html>");
    i = localStringBuffer.length();
    arrayOfChar = new char[i + 1];
    localStringBuffer.getChars(0, i, arrayOfChar, 0);
    j = OS.WideCharToMultiByte(65001, 0, arrayOfChar, -1, null, 0, null, null);
    int i4 = OS.GlobalAlloc(64, j);
    OS.WideCharToMultiByte(65001, 0, arrayOfChar, -1, i4, j, null, null);
    paramTransferData.stgmedium = new STGMEDIUM();
    paramTransferData.stgmedium.tymed = 1;
    paramTransferData.stgmedium.unionField = i4;
    paramTransferData.stgmedium.pUnkForRelease = 0;
    paramTransferData.result = 0;
  }

  public Object nativeToJava(TransferData paramTransferData)
  {
    if ((!isSupportedType(paramTransferData)) || (paramTransferData.pIDataObject == 0))
      return null;
    IDataObject localIDataObject = new IDataObject(paramTransferData.pIDataObject);
    localIDataObject.AddRef();
    STGMEDIUM localSTGMEDIUM = new STGMEDIUM();
    FORMATETC localFORMATETC = paramTransferData.formatetc;
    localSTGMEDIUM.tymed = 1;
    paramTransferData.result = getData(localIDataObject, localFORMATETC, localSTGMEDIUM);
    localIDataObject.Release();
    if (paramTransferData.result != 0)
      return null;
    int i = localSTGMEDIUM.unionField;
    try
    {
      int j = OS.GlobalLock(i);
      if (j == 0);
      while (true)
      {
        return null;
        try
        {
          int k = OS.MultiByteToWideChar(65001, 0, j, -1, null, 0);
          if (k == 0)
            continue;
          char[] arrayOfChar;
          do
          {
            int m;
            int n;
            do
            {
              arrayOfChar = new char[k - 1];
              OS.MultiByteToWideChar(65001, 0, j, -1, arrayOfChar, arrayOfChar.length);
              String str1 = new String(arrayOfChar);
              m = 0;
              n = 0;
              int i1 = str1.indexOf("StartFragment:") + "StartFragment:".length();
              int i2 = i1 + 1;
              while (i2 < str1.length())
              {
                str2 = str1.substring(i1, i2);
                try
                {
                  m = Integer.parseInt(str2);
                  i2++;
                }
                catch (NumberFormatException localNumberFormatException1)
                {
                  break;
                }
              }
              i1 = str1.indexOf("EndFragment:") + "EndFragment:".length();
              i2 = i1 + 1;
              while (i2 < str1.length())
              {
                str2 = str1.substring(i1, i2);
                try
                {
                  n = Integer.parseInt(str2);
                  i2++;
                }
                catch (NumberFormatException localNumberFormatException2)
                {
                  break;
                }
              }
            }
            while ((n <= m) || (n > OS.strlen(j)));
            k = OS.MultiByteToWideChar(65001, 0, j + m, n - m, arrayOfChar, arrayOfChar.length);
          }
          while (k == 0);
          String str2 = new String(arrayOfChar, 0, k);
          String str3 = "<!--StartFragment -->\r\n";
          int i3 = str2.indexOf(str3);
          if (i3 != -1)
          {
            i3 += str3.length();
            str2 = str2.substring(i3);
          }
          String str4 = str2;
          return str4;
        }
        finally
        {
          OS.GlobalUnlock(i);
        }
      }
    }
    finally
    {
      OS.GlobalFree(i);
    }
  }

  protected int[] getTypeIds()
  {
    return new int[] { HTML_FORMATID };
  }

  protected String[] getTypeNames()
  {
    return new String[] { "HTML Format" };
  }

  boolean checkHTML(Object paramObject)
  {
    return (paramObject != null) && ((paramObject instanceof String)) && (((String)paramObject).length() > 0);
  }

  protected boolean validate(Object paramObject)
  {
    return checkHTML(paramObject);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.HTMLTransfer
 * JD-Core Version:    0.6.2
 */