package org.eclipse.swt.internal;

import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.internal.win32.GCP_RESULTS;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Control;

public class BidiUtil
{
  public static final int KEYBOARD_NON_BIDI = 0;
  public static final int KEYBOARD_BIDI = 1;
  static int isBidiPlatform = -1;
  public static final int CLASSIN = 1;
  public static final int LINKBEFORE = 2;
  public static final int LINKAFTER = 4;
  static Hashtable languageMap = new Hashtable();
  static Hashtable keyMap = new Hashtable();
  static Hashtable oldProcMap = new Hashtable();
  static final String CLASS_NAME = "org.eclipse.swt.internal.BidiUtil";
  static Callback callback;
  static final int GCP_REORDER = 2;
  static final int GCP_GLYPHSHAPE = 16;
  static final int GCP_LIGATE = 32;
  static final int GCP_CLASSIN = 524288;
  static final byte GCPCLASS_ARABIC = 2;
  static final byte GCPCLASS_HEBREW = 2;
  static final byte GCPCLASS_LOCALNUMBER = 4;
  static final byte GCPCLASS_LATINNUMBER = 5;
  static final int GCPGLYPH_LINKBEFORE = 32768;
  static final int GCPGLYPH_LINKAFTER = 16384;
  static final int ETO_CLIPPED = 4;
  static final int ETO_GLYPH_INDEX = 16;
  static final int LANG_ARABIC = 1;
  static final int LANG_HEBREW = 13;
  static final int LANG_FARSI = 41;
  static final String CD_PG_HEBREW = "1255";
  static final String CD_PG_ARABIC = "1256";
  static final int HKL_NEXT = 1;
  static final int HKL_PREV = 0;
  public static final int CLASS_HEBREW = 2;
  public static final int CLASS_ARABIC = 2;
  public static final int CLASS_LOCALNUMBER = 4;
  public static final int CLASS_LATINNUMBER = 5;
  public static final int REORDER = 2;
  public static final int LIGATE = 32;
  public static final int GLYPHSHAPE = 16;

  static
  {
    try
    {
      callback = new Callback(Class.forName("org.eclipse.swt.internal.BidiUtil"), "windowProc", 4);
      if (callback.getAddress() == 0)
        SWT.error(3);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
  }

  public static void addLanguageListener(int paramInt, Runnable paramRunnable)
  {
    languageMap.put(new LONG(paramInt), paramRunnable);
    subclass(paramInt);
  }

  public static void addLanguageListener(Control paramControl, Runnable paramRunnable)
  {
    addLanguageListener(paramControl.handle, paramRunnable);
  }

  static int EnumSystemLanguageGroupsProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (paramInt1 == 12)
    {
      isBidiPlatform = 1;
      return 0;
    }
    if (paramInt1 == 13)
    {
      isBidiPlatform = 1;
      return 0;
    }
    return 1;
  }

  public static void drawGlyphs(GC paramGC, char[] paramArrayOfChar, int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfInt.length;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)) && (OS.GetLayout(paramGC.handle) != 0))
    {
      reverse(paramArrayOfInt);
      paramArrayOfInt[(i - 1)] -= 1;
      reverse(paramArrayOfChar);
    }
    int j = OS.SetBkMode(paramGC.handle, 1);
    OS.ExtTextOutW(paramGC.handle, paramInt1, paramInt2, 16, null, paramArrayOfChar, paramArrayOfChar.length, paramArrayOfInt);
    OS.SetBkMode(paramGC.handle, j);
  }

  public static char[] getRenderInfo(GC paramGC, String paramString, int[] paramArrayOfInt1, byte[] paramArrayOfByte, int[] paramArrayOfInt2, int paramInt, int[] paramArrayOfInt3)
  {
    int i = OS.GetFontLanguageInfo(paramGC.handle);
    int j = OS.GetProcessHeap();
    int[] arrayOfInt = new int[8];
    int k = OS.GetTextCharset(paramGC.handle);
    boolean bool = false;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
      bool = OS.GetLayout(paramGC.handle) != 0;
    OS.TranslateCharsetInfo(k, arrayOfInt, 1);
    TCHAR localTCHAR1 = new TCHAR(arrayOfInt[1], paramString, false);
    int m = localTCHAR1.length();
    int n = (paramInt & 0x2) == 2 ? 1 : 0;
    int i1 = (paramInt & 0x4) == 4 ? 1 : 0;
    GCP_RESULTS localGCP_RESULTS = new GCP_RESULTS();
    localGCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
    localGCP_RESULTS.nGlyphs = m;
    int i2 = localGCP_RESULTS.lpOrder = OS.HeapAlloc(j, 8, m * 4);
    int i3 = localGCP_RESULTS.lpDx = OS.HeapAlloc(j, 8, m * 4);
    int i4 = localGCP_RESULTS.lpClass = OS.HeapAlloc(j, 8, m);
    int i5 = localGCP_RESULTS.lpGlyphs = OS.HeapAlloc(j, 8, m * 2);
    int i6 = 0;
    int i7 = 0;
    i6 |= 2;
    if ((i & 0x20) == 32)
    {
      i6 |= 32;
      i7 |= 0;
    }
    if ((i & 0x10) == 16)
    {
      i6 |= 16;
      if (n != 0)
        i7 |= 32768;
      if (i1 != 0)
        i7 |= 16384;
    }
    byte[] arrayOfByte;
    if ((n != 0) || (i1 != 0))
    {
      arrayOfByte = new byte[2];
      arrayOfByte[0] = ((byte)i7);
      arrayOfByte[1] = ((byte)(i7 >> 8));
    }
    else
    {
      arrayOfByte = new byte[] { (byte)i7 };
    }
    OS.MoveMemory(localGCP_RESULTS.lpGlyphs, arrayOfByte, arrayOfByte.length);
    if ((paramInt & 0x1) == 1)
    {
      i6 |= 524288;
      OS.MoveMemory(localGCP_RESULTS.lpClass, paramArrayOfByte, paramArrayOfByte.length);
    }
    char[] arrayOfChar = new char[localGCP_RESULTS.nGlyphs];
    int i8 = 0;
    for (int i9 = 0; i9 < paramArrayOfInt3.length - 1; i9++)
    {
      int i10 = paramArrayOfInt3[i9];
      int i11 = paramArrayOfInt3[(i9 + 1)] - paramArrayOfInt3[i9];
      localGCP_RESULTS.nGlyphs = i11;
      TCHAR localTCHAR2 = new TCHAR(arrayOfInt[1], paramString.substring(i10, i10 + i11), false);
      OS.GetCharacterPlacement(paramGC.handle, localTCHAR2, localTCHAR2.length(), 0, localGCP_RESULTS, i6);
      if (paramArrayOfInt2 != null)
      {
        localObject = new int[localGCP_RESULTS.nGlyphs];
        OS.MoveMemory((int[])localObject, localGCP_RESULTS.lpDx, localObject.length * 4);
        if (bool)
          reverse((int[])localObject);
        System.arraycopy(localObject, 0, paramArrayOfInt2, i8, localObject.length);
      }
      if (paramArrayOfInt1 != null)
      {
        localObject = new int[i11];
        OS.MoveMemory((int[])localObject, localGCP_RESULTS.lpOrder, localObject.length * 4);
        translateOrder((int[])localObject, i8, bool);
        System.arraycopy(localObject, 0, paramArrayOfInt1, i10, i11);
      }
      if (paramArrayOfByte != null)
      {
        localObject = new byte[i11];
        OS.MoveMemory((byte[])localObject, localGCP_RESULTS.lpClass, localObject.length);
        System.arraycopy(localObject, 0, paramArrayOfByte, i10, i11);
      }
      Object localObject = new char[localGCP_RESULTS.nGlyphs];
      OS.MoveMemory((char[])localObject, localGCP_RESULTS.lpGlyphs, localObject.length * 2);
      if (bool)
        reverse((char[])localObject);
      System.arraycopy(localObject, 0, arrayOfChar, i8, localObject.length);
      i8 += localObject.length;
      localGCP_RESULTS.lpOrder += i11 * 4;
      localGCP_RESULTS.lpDx += i11 * 4;
      localGCP_RESULTS.lpClass += i11;
      localGCP_RESULTS.lpGlyphs += localObject.length * 2;
    }
    OS.HeapFree(j, 0, i5);
    OS.HeapFree(j, 0, i4);
    OS.HeapFree(j, 0, i3);
    OS.HeapFree(j, 0, i2);
    return arrayOfChar;
  }

  public static void getOrderInfo(GC paramGC, String paramString, int[] paramArrayOfInt1, byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt2)
  {
    int i = OS.GetFontLanguageInfo(paramGC.handle);
    int j = OS.GetProcessHeap();
    int[] arrayOfInt = new int[8];
    int k = OS.GetTextCharset(paramGC.handle);
    OS.TranslateCharsetInfo(k, arrayOfInt, 1);
    TCHAR localTCHAR1 = new TCHAR(arrayOfInt[1], paramString, false);
    int m = localTCHAR1.length();
    boolean bool = false;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
      bool = OS.GetLayout(paramGC.handle) != 0;
    GCP_RESULTS localGCP_RESULTS = new GCP_RESULTS();
    localGCP_RESULTS.lStructSize = GCP_RESULTS.sizeof;
    localGCP_RESULTS.nGlyphs = m;
    int n = localGCP_RESULTS.lpOrder = OS.HeapAlloc(j, 8, m * 4);
    int i1 = localGCP_RESULTS.lpClass = OS.HeapAlloc(j, 8, m);
    int i2 = 0;
    i2 |= 2;
    if ((i & 0x20) == 32)
      i2 |= 32;
    if ((i & 0x10) == 16)
      i2 |= 16;
    if ((paramInt & 0x1) == 1)
    {
      i2 |= 524288;
      OS.MoveMemory(localGCP_RESULTS.lpClass, paramArrayOfByte, paramArrayOfByte.length);
    }
    int i3 = 0;
    for (int i4 = 0; i4 < paramArrayOfInt2.length - 1; i4++)
    {
      int i5 = paramArrayOfInt2[i4];
      int i6 = paramArrayOfInt2[(i4 + 1)] - paramArrayOfInt2[i4];
      localGCP_RESULTS.nGlyphs = i6;
      TCHAR localTCHAR2 = new TCHAR(arrayOfInt[1], paramString.substring(i5, i5 + i6), false);
      OS.GetCharacterPlacement(paramGC.handle, localTCHAR2, localTCHAR2.length(), 0, localGCP_RESULTS, i2);
      Object localObject;
      if (paramArrayOfInt1 != null)
      {
        localObject = new int[i6];
        OS.MoveMemory((int[])localObject, localGCP_RESULTS.lpOrder, localObject.length * 4);
        translateOrder((int[])localObject, i3, bool);
        System.arraycopy(localObject, 0, paramArrayOfInt1, i5, i6);
      }
      if (paramArrayOfByte != null)
      {
        localObject = new byte[i6];
        OS.MoveMemory((byte[])localObject, localGCP_RESULTS.lpClass, localObject.length);
        System.arraycopy(localObject, 0, paramArrayOfByte, i5, i6);
      }
      i3 += localGCP_RESULTS.nGlyphs;
      localGCP_RESULTS.lpOrder += i6 * 4;
      localGCP_RESULTS.lpClass += i6;
    }
    OS.HeapFree(j, 0, i1);
    OS.HeapFree(j, 0, n);
  }

  public static int getFontBidiAttributes(GC paramGC)
  {
    int i = 0;
    int j = OS.GetFontLanguageInfo(paramGC.handle);
    if ((j & 0x2) != 0)
      i |= 2;
    if ((j & 0x20) != 0)
      i |= 32;
    if ((j & 0x10) != 0)
      i |= 16;
    return i;
  }

  public static int getKeyboardLanguage()
  {
    int i = OS.GetKeyboardLayout(0);
    return isBidiLang(i) ? 1 : 0;
  }

  static int[] getKeyboardLanguageList()
  {
    int i = 10;
    int[] arrayOfInt1 = new int[i];
    int j = OS.GetKeyboardLayoutList(i, arrayOfInt1);
    int[] arrayOfInt2 = new int[j];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, j);
    return arrayOfInt2;
  }

  static boolean isBidiLang(int paramInt)
  {
    int i = OS.PRIMARYLANGID(OS.LOWORD(paramInt));
    return (i == 1) || (i == 13) || (i == 41);
  }

  public static boolean isBidiPlatform()
  {
    if (OS.IsWinCE)
      return false;
    if (isBidiPlatform != -1)
      return isBidiPlatform == 1;
    isBidiPlatform = 0;
    if (!isKeyboardBidi())
      return false;
    Callback localCallback = null;
    try
    {
      localCallback = new Callback(Class.forName("org.eclipse.swt.internal.BidiUtil"), "EnumSystemLanguageGroupsProc", 5);
      int i = localCallback.getAddress();
      if (i == 0)
        SWT.error(3);
      OS.EnumSystemLanguageGroups(i, 1, 0);
      localCallback.dispose();
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      if (localCallback != null)
        localCallback.dispose();
    }
    if (isBidiPlatform == 1)
      return true;
    String str = String.valueOf(OS.GetACP());
    if (("1256".equals(str)) || ("1255".equals(str)))
      isBidiPlatform = 1;
    return isBidiPlatform == 1;
  }

  public static boolean isKeyboardBidi()
  {
    int[] arrayOfInt = getKeyboardLanguageList();
    for (int i = 0; i < arrayOfInt.length; i++)
      if (isBidiLang(arrayOfInt[i]))
        return true;
    return false;
  }

  public static void removeLanguageListener(int paramInt)
  {
    languageMap.remove(new LONG(paramInt));
    unsubclass(paramInt);
  }

  public static void removeLanguageListener(Control paramControl)
  {
    removeLanguageListener(paramControl.handle);
  }

  public static void setKeyboardLanguage(int paramInt)
  {
    if (paramInt == getKeyboardLanguage())
      return;
    int i = paramInt == 1 ? 1 : 0;
    int[] arrayOfInt = getKeyboardLanguageList();
    for (int j = 0; j < arrayOfInt.length; j++)
      if (i == isBidiLang(arrayOfInt[j]))
      {
        OS.ActivateKeyboardLayout(arrayOfInt[j], 0);
        return;
      }
  }

  public static boolean setOrientation(int paramInt1, int paramInt2)
  {
    if (OS.IsWinCE)
      return false;
    if (OS.WIN32_VERSION < OS.VERSION(4, 10))
      return false;
    int i = OS.GetWindowLong(paramInt1, -20);
    if ((paramInt2 & 0x4000000) != 0)
      i |= 4194304;
    else
      i &= -4194305;
    OS.SetWindowLong(paramInt1, -20, i);
    return true;
  }

  public static boolean setOrientation(Control paramControl, int paramInt)
  {
    return setOrientation(paramControl.handle, paramInt);
  }

  static void subclass(int paramInt)
  {
    LONG localLONG = new LONG(paramInt);
    if (oldProcMap.get(localLONG) == null)
    {
      int i = OS.GetWindowLongPtr(paramInt, -4);
      oldProcMap.put(localLONG, new LONG(i));
      OS.SetWindowLongPtr(paramInt, -4, callback.getAddress());
    }
  }

  static void reverse(char[] paramArrayOfChar)
  {
    int i = paramArrayOfChar.length;
    for (int j = 0; j <= (i - 1) / 2; j++)
    {
      int k = paramArrayOfChar[j];
      paramArrayOfChar[j] = paramArrayOfChar[(i - 1 - j)];
      paramArrayOfChar[(i - 1 - j)] = k;
    }
  }

  static void reverse(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j <= (i - 1) / 2; j++)
    {
      int k = paramArrayOfInt[j];
      paramArrayOfInt[j] = paramArrayOfInt[(i - 1 - j)];
      paramArrayOfInt[(i - 1 - j)] = k;
    }
  }

  static void translateOrder(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    int i = 0;
    int j = paramArrayOfInt.length;
    if (paramBoolean)
      for (k = 0; k < j; k++)
        i = Math.max(i, paramArrayOfInt[k]);
    for (int k = 0; k < j; k++)
    {
      if (paramBoolean)
        paramArrayOfInt[k] = (i - paramArrayOfInt[k]);
      paramArrayOfInt[k] += paramInt;
    }
  }

  static void unsubclass(int paramInt)
  {
    LONG localLONG1 = new LONG(paramInt);
    if ((languageMap.get(localLONG1) == null) && (keyMap.get(localLONG1) == null))
    {
      LONG localLONG2 = (LONG)oldProcMap.remove(localLONG1);
      if (localLONG2 == null)
        return;
      OS.SetWindowLongPtr(paramInt, -4, localLONG2.value);
    }
  }

  static int windowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    LONG localLONG = new LONG(paramInt1);
    switch (paramInt2)
    {
    case 81:
      localObject = (Runnable)languageMap.get(localLONG);
      if (localObject != null)
        ((Runnable)localObject).run();
      break;
    }
    Object localObject = (LONG)oldProcMap.get(localLONG);
    return OS.CallWindowProc(((LONG)localObject).value, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.BidiUtil
 * JD-Core Version:    0.6.2
 */