package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;

public final class FontData
{
  public LOGFONT data;
  public float height;
  String lang;
  String country;
  String variant;

  public FontData()
  {
    this.data = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
    this.data.lfCharSet = 1;
    this.height = 12.0F;
  }

  FontData(LOGFONT paramLOGFONT, float paramFloat)
  {
    this.data = paramLOGFONT;
    this.height = paramFloat;
  }

  public FontData(String paramString)
  {
    if (paramString == null)
      SWT.error(4);
    int i = 0;
    int j = paramString.indexOf('|');
    if (j == -1)
      SWT.error(5);
    String str1 = paramString.substring(i, j);
    try
    {
      if (Integer.parseInt(str1) != 1)
        SWT.error(5);
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      SWT.error(5);
    }
    i = j + 1;
    j = paramString.indexOf('|', i);
    if (j == -1)
      SWT.error(5);
    String str2 = paramString.substring(i, j);
    i = j + 1;
    j = paramString.indexOf('|', i);
    if (j == -1)
      SWT.error(5);
    float f = 0.0F;
    try
    {
      f = Float.parseFloat(paramString.substring(i, j));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      SWT.error(5);
    }
    i = j + 1;
    j = paramString.indexOf('|', i);
    if (j == -1)
      SWT.error(5);
    int k = 0;
    try
    {
      k = Integer.parseInt(paramString.substring(i, j));
    }
    catch (NumberFormatException localNumberFormatException3)
    {
      SWT.error(5);
    }
    i = j + 1;
    j = paramString.indexOf('|', i);
    this.data = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
    this.data.lfCharSet = 1;
    setName(str2);
    setHeight(f);
    setStyle(k);
    if (j == -1)
      return;
    String str3 = paramString.substring(i, j);
    i = j + 1;
    j = paramString.indexOf('|', i);
    if (j == -1)
      return;
    String str4 = paramString.substring(i, j);
    if ((str3.equals("WINDOWS")) && (str4.equals("1")))
    {
      LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
      try
      {
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfHeight = Integer.parseInt(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfWidth = Integer.parseInt(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfEscapement = Integer.parseInt(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfOrientation = Integer.parseInt(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfWeight = Integer.parseInt(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfItalic = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfUnderline = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfStrikeOut = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfCharSet = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfOutPrecision = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfClipPrecision = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfQuality = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
        j = paramString.indexOf('|', i);
        if (j == -1)
          return;
        localLOGFONTA.lfPitchAndFamily = Byte.parseByte(paramString.substring(i, j));
        i = j + 1;
      }
      catch (NumberFormatException localNumberFormatException4)
      {
        setName(str2);
        setHeight(f);
        setStyle(k);
        return;
      }
      TCHAR localTCHAR = new TCHAR(0, paramString.substring(i), false);
      int m = Math.min(31, localTCHAR.length());
      Object localObject;
      if (OS.IsUnicode)
      {
        localObject = ((LOGFONTW)localLOGFONTA).lfFaceName;
        System.arraycopy(localTCHAR.chars, 0, localObject, 0, m);
      }
      else
      {
        localObject = ((LOGFONTA)localLOGFONTA).lfFaceName;
        System.arraycopy(localTCHAR.bytes, 0, localObject, 0, m);
      }
      this.data = localLOGFONTA;
    }
  }

  public FontData(String paramString, int paramInt1, int paramInt2)
  {
    if (paramString == null)
      SWT.error(4);
    this.data = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
    setName(paramString);
    setHeight(paramInt1);
    setStyle(paramInt2);
    this.data.lfCharSet = 1;
  }

  FontData(String paramString, float paramFloat, int paramInt)
  {
    if (paramString == null)
      SWT.error(4);
    this.data = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
    setName(paramString);
    setHeight(paramFloat);
    setStyle(paramInt);
    this.data.lfCharSet = 1;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof FontData))
      return false;
    FontData localFontData = (FontData)paramObject;
    LOGFONT localLOGFONT = localFontData.data;
    return (this.data.lfCharSet == localLOGFONT.lfCharSet) && (this.height == localFontData.height) && (this.data.lfWidth == localLOGFONT.lfWidth) && (this.data.lfEscapement == localLOGFONT.lfEscapement) && (this.data.lfOrientation == localLOGFONT.lfOrientation) && (this.data.lfWeight == localLOGFONT.lfWeight) && (this.data.lfItalic == localLOGFONT.lfItalic) && (this.data.lfUnderline == localLOGFONT.lfUnderline) && (this.data.lfStrikeOut == localLOGFONT.lfStrikeOut) && (this.data.lfCharSet == localLOGFONT.lfCharSet) && (this.data.lfOutPrecision == localLOGFONT.lfOutPrecision) && (this.data.lfClipPrecision == localLOGFONT.lfClipPrecision) && (this.data.lfQuality == localLOGFONT.lfQuality) && (this.data.lfPitchAndFamily == localLOGFONT.lfPitchAndFamily) && (getName().equals(localFontData.getName()));
  }

  int EnumLocalesProc(int paramInt)
  {
    int i = 8;
    TCHAR localTCHAR = new TCHAR(0, i);
    int j = i * TCHAR.sizeof;
    OS.MoveMemory(localTCHAR, paramInt, j);
    int k = Integer.parseInt(localTCHAR.toString(0, localTCHAR.strlen()), 16);
    int m = OS.GetLocaleInfo(k, 89, localTCHAR, i);
    if ((m <= 0) || (!this.lang.equals(localTCHAR.toString(0, m - 1))))
      return 1;
    if (this.country != null)
    {
      m = OS.GetLocaleInfo(k, 90, localTCHAR, i);
      if ((m <= 0) || (!this.country.equals(localTCHAR.toString(0, m - 1))))
        return 1;
    }
    m = OS.GetLocaleInfo(k, 4100, localTCHAR, i);
    if (m <= 0)
      return 1;
    int n = Integer.parseInt(localTCHAR.toString(0, m - 1));
    int[] arrayOfInt = new int[8];
    OS.TranslateCharsetInfo(n, arrayOfInt, 2);
    this.data.lfCharSet = ((byte)arrayOfInt[0]);
    return 0;
  }

  public int getHeight()
  {
    return (int)(0.5F + this.height);
  }

  float getHeightF()
  {
    return this.height;
  }

  public String getLocale()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    char c = '_';
    if (this.lang != null)
    {
      localStringBuffer.append(this.lang);
      localStringBuffer.append(c);
    }
    if (this.country != null)
    {
      localStringBuffer.append(this.country);
      localStringBuffer.append(c);
    }
    if (this.variant != null)
      localStringBuffer.append(this.variant);
    String str = localStringBuffer.toString();
    int i = str.length();
    if ((i > 0) && (str.charAt(i - 1) == c))
      str = str.substring(0, i - 1);
    return str;
  }

  public String getName()
  {
    char[] arrayOfChar;
    if (OS.IsUnicode)
    {
      arrayOfChar = ((LOGFONTW)this.data).lfFaceName;
    }
    else
    {
      arrayOfChar = new char[32];
      byte[] arrayOfByte = ((LOGFONTA)this.data).lfFaceName;
      OS.MultiByteToWideChar(0, 1, arrayOfByte, arrayOfByte.length, arrayOfChar, arrayOfChar.length);
    }
    for (int i = 0; i < arrayOfChar.length; i++)
      if (arrayOfChar[i] == 0)
        break;
    return new String(arrayOfChar, 0, i);
  }

  public int getStyle()
  {
    int i = 0;
    if (this.data.lfWeight == 700)
      i |= 1;
    if (this.data.lfItalic != 0)
      i |= 2;
    return i;
  }

  public int hashCode()
  {
    return this.data.lfCharSet ^ getHeight() ^ this.data.lfWidth ^ this.data.lfEscapement ^ this.data.lfOrientation ^ this.data.lfWeight ^ this.data.lfItalic ^ this.data.lfUnderline ^ this.data.lfStrikeOut ^ this.data.lfCharSet ^ this.data.lfOutPrecision ^ this.data.lfClipPrecision ^ this.data.lfQuality ^ this.data.lfPitchAndFamily ^ getName().hashCode();
  }

  public void setHeight(int paramInt)
  {
    if (paramInt < 0)
      SWT.error(5);
    this.height = paramInt;
    this.data.lfWidth = 0;
  }

  void setHeight(float paramFloat)
  {
    if (paramFloat < 0.0F)
      SWT.error(5);
    this.height = paramFloat;
  }

  public void setLocale(String paramString)
  {
    this.lang = (this.country = this.variant = null);
    int j;
    if (paramString != null)
    {
      int i = 95;
      j = paramString.length();
      int k = paramString.indexOf(i);
      int m;
      if (k == -1)
      {
        k = m = j;
      }
      else
      {
        m = paramString.indexOf(i, k + 1);
        if (m == -1)
          m = j;
      }
      if (k > 0)
        this.lang = paramString.substring(0, k);
      if (m > k + 1)
        this.country = paramString.substring(k + 1, m);
      if (j > m + 1)
        this.variant = paramString.substring(m + 1);
    }
    if (this.lang == null)
    {
      this.data.lfCharSet = 1;
    }
    else
    {
      Callback localCallback = new Callback(this, "EnumLocalesProc", 1);
      j = localCallback.getAddress();
      if (j == 0)
        SWT.error(3);
      OS.EnumSystemLocales(j, 2);
      localCallback.dispose();
    }
  }

  public void setName(String paramString)
  {
    if (paramString == null)
      SWT.error(4);
    TCHAR localTCHAR = new TCHAR(0, paramString, true);
    int i = Math.min(31, localTCHAR.length());
    Object localObject;
    int j;
    if (OS.IsUnicode)
    {
      localObject = ((LOGFONTW)this.data).lfFaceName;
      for (j = 0; j < localObject.length; j++)
        localObject[j] = 0;
      System.arraycopy(localTCHAR.chars, 0, localObject, 0, i);
    }
    else
    {
      localObject = ((LOGFONTA)this.data).lfFaceName;
      for (j = 0; j < localObject.length; j++)
        localObject[j] = 0;
      System.arraycopy(localTCHAR.bytes, 0, localObject, 0, i);
    }
  }

  public void setStyle(int paramInt)
  {
    if ((paramInt & 0x1) == 1)
      this.data.lfWeight = 700;
    else
      this.data.lfWeight = 0;
    if ((paramInt & 0x2) == 2)
      this.data.lfItalic = 1;
    else
      this.data.lfItalic = 0;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(128);
    localStringBuffer.append("1|");
    String str = getName();
    localStringBuffer.append(str);
    localStringBuffer.append("|");
    localStringBuffer.append(getHeightF());
    localStringBuffer.append("|");
    localStringBuffer.append(getStyle());
    localStringBuffer.append("|");
    localStringBuffer.append("WINDOWS|1|");
    localStringBuffer.append(this.data.lfHeight);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfWidth);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfEscapement);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfOrientation);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfWeight);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfItalic);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfUnderline);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfStrikeOut);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfCharSet);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfOutPrecision);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfClipPrecision);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfQuality);
    localStringBuffer.append("|");
    localStringBuffer.append(this.data.lfPitchAndFamily);
    localStringBuffer.append("|");
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }

  public static FontData win32_new(LOGFONT paramLOGFONT, float paramFloat)
  {
    return new FontData(paramLOGFONT, paramFloat);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.FontData
 * JD-Core Version:    0.6.2
 */