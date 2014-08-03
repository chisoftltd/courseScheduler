package org.eclipse.swt.program;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.internal.win32.FILETIME;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PROCESS_INFORMATION;
import org.eclipse.swt.internal.win32.SHELLEXECUTEINFO;
import org.eclipse.swt.internal.win32.SHFILEINFO;
import org.eclipse.swt.internal.win32.SHFILEINFOA;
import org.eclipse.swt.internal.win32.SHFILEINFOW;
import org.eclipse.swt.internal.win32.STARTUPINFO;
import org.eclipse.swt.internal.win32.TCHAR;

public final class Program
{
  String name;
  String command;
  String iconName;
  String extension;
  static final String[] ARGUMENTS = { "%1", "%l", "%L" };

  static String assocQueryString(int paramInt, TCHAR paramTCHAR, boolean paramBoolean)
  {
    TCHAR localTCHAR1 = new TCHAR(0, 1024);
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = localTCHAR1.length();
    int i = 1056;
    int j = OS.AssocQueryString(i, paramInt, paramTCHAR, null, localTCHAR1, arrayOfInt);
    if (j == -2147467261)
    {
      localTCHAR1 = new TCHAR(0, arrayOfInt[0]);
      j = OS.AssocQueryString(i, paramInt, paramTCHAR, null, localTCHAR1, arrayOfInt);
    }
    if (j == 0)
    {
      if ((!OS.IsWinCE) && (paramBoolean))
      {
        int k = OS.ExpandEnvironmentStrings(localTCHAR1, null, 0);
        if (k != 0)
        {
          TCHAR localTCHAR2 = new TCHAR(0, k);
          OS.ExpandEnvironmentStrings(localTCHAR1, localTCHAR2, k);
          return localTCHAR2.toString(0, Math.max(0, k - 1));
        }
        return "";
      }
      return localTCHAR1.toString(0, Math.max(0, arrayOfInt[0] - 1));
    }
    return null;
  }

  public static Program findProgram(String paramString)
  {
    if (paramString == null)
      SWT.error(4);
    if (paramString.length() == 0)
      return null;
    if (paramString.charAt(0) != '.')
      paramString = "." + paramString;
    TCHAR localTCHAR1 = new TCHAR(0, paramString, true);
    Program localProgram = null;
    Object localObject1;
    Object localObject2;
    if (OS.IsWinCE)
    {
      localObject1 = new int[1];
      if (OS.RegOpenKeyEx(-2147483648, localTCHAR1, 0, 131097, (int[])localObject1) != 0)
        return null;
      localObject2 = new int[1];
      int i = OS.RegQueryValueEx(localObject1[0], null, 0, null, null, (int[])localObject2);
      if (i == 0)
      {
        TCHAR localTCHAR2 = new TCHAR(0, localObject2[0] / TCHAR.sizeof);
        i = OS.RegQueryValueEx(localObject1[0], null, 0, null, localTCHAR2, (int[])localObject2);
        if (i == 0)
          localProgram = getProgram(localTCHAR2.toString(0, localTCHAR2.strlen()), paramString);
      }
      OS.RegCloseKey(localObject1[0]);
    }
    else
    {
      localObject1 = assocQueryString(1, localTCHAR1, true);
      if (localObject1 != null)
      {
        localObject2 = null;
        localObject2 = assocQueryString(3, localTCHAR1, false);
        if (localObject2 == null)
          localObject2 = assocQueryString(4, localTCHAR1, false);
        if (localObject2 == null)
          localObject2 = "";
        String str = assocQueryString(15, localTCHAR1, true);
        if (str == null)
          str = "";
        localProgram = new Program();
        localProgram.name = ((String)localObject2);
        localProgram.command = ((String)localObject1);
        localProgram.iconName = str;
        localProgram.extension = paramString;
      }
    }
    return localProgram;
  }

  public static String[] getExtensions()
  {
    Object localObject1 = new String[1024];
    TCHAR localTCHAR = new TCHAR(0, 1024);
    int[] arrayOfInt = { localTCHAR.length() };
    FILETIME localFILETIME = new FILETIME();
    int i = 0;
    int j = 0;
    Object localObject2;
    while (OS.RegEnumKeyEx(-2147483648, i, localTCHAR, arrayOfInt, null, null, null, localFILETIME) != 259)
    {
      localObject2 = localTCHAR.toString(0, arrayOfInt[0]);
      arrayOfInt[0] = localTCHAR.length();
      if ((((String)localObject2).length() > 0) && (((String)localObject2).charAt(0) == '.'))
      {
        if (j == localObject1.length)
        {
          String[] arrayOfString = new String[localObject1.length + 1024];
          System.arraycopy(localObject1, 0, arrayOfString, 0, localObject1.length);
          localObject1 = arrayOfString;
        }
        localObject1[(j++)] = localObject2;
      }
      i++;
    }
    if (j != localObject1.length)
    {
      localObject2 = new String[j];
      System.arraycopy(localObject1, 0, localObject2, 0, j);
      localObject1 = localObject2;
    }
    return localObject1;
  }

  static String getKeyValue(String paramString, boolean paramBoolean)
  {
    TCHAR localTCHAR1 = new TCHAR(0, paramString, true);
    int[] arrayOfInt1 = new int[1];
    if (OS.RegOpenKeyEx(-2147483648, localTCHAR1, 0, 131097, arrayOfInt1) != 0)
      return null;
    String str = null;
    int[] arrayOfInt2 = new int[1];
    if (OS.RegQueryValueEx(arrayOfInt1[0], null, 0, null, null, arrayOfInt2) == 0)
    {
      str = "";
      int i = arrayOfInt2[0] / TCHAR.sizeof;
      if (i != 0)
      {
        TCHAR localTCHAR2 = new TCHAR(0, i);
        if (OS.RegQueryValueEx(arrayOfInt1[0], null, 0, null, localTCHAR2, arrayOfInt2) == 0)
          if ((!OS.IsWinCE) && (paramBoolean))
          {
            i = OS.ExpandEnvironmentStrings(localTCHAR2, null, 0);
            if (i != 0)
            {
              TCHAR localTCHAR3 = new TCHAR(0, i);
              OS.ExpandEnvironmentStrings(localTCHAR2, localTCHAR3, i);
              str = localTCHAR3.toString(0, Math.max(0, i - 1));
            }
          }
          else
          {
            i = Math.max(0, localTCHAR2.length() - 1);
            str = localTCHAR2.toString(0, i);
          }
      }
    }
    if (arrayOfInt1[0] != 0)
      OS.RegCloseKey(arrayOfInt1[0]);
    return str;
  }

  static Program getProgram(String paramString1, String paramString2)
  {
    String str1 = getKeyValue(paramString1, false);
    if ((str1 == null) || (str1.length() == 0))
      str1 = paramString1;
    String str2 = "\\shell";
    String str3 = getKeyValue(paramString1 + str2, true);
    if ((str3 == null) || (str3.length() == 0))
      str3 = "open";
    String str4 = "\\shell\\" + str3 + "\\command";
    String str5 = getKeyValue(paramString1 + str4, true);
    if ((str5 == null) || (str5.length() == 0))
      return null;
    String str6 = "\\DefaultIcon";
    String str7 = getKeyValue(paramString1 + str6, true);
    if (str7 == null)
      str7 = "";
    Program localProgram = new Program();
    localProgram.name = str1;
    localProgram.command = str5;
    localProgram.iconName = str7;
    localProgram.extension = paramString2;
    return localProgram;
  }

  public static Program[] getPrograms()
  {
    Object localObject1 = new Program[1024];
    TCHAR localTCHAR = new TCHAR(0, 1024);
    int[] arrayOfInt = { localTCHAR.length() };
    FILETIME localFILETIME = new FILETIME();
    int i = 0;
    int j = 0;
    Object localObject2;
    while (OS.RegEnumKeyEx(-2147483648, i, localTCHAR, arrayOfInt, null, null, null, localFILETIME) != 259)
    {
      localObject2 = localTCHAR.toString(0, arrayOfInt[0]);
      arrayOfInt[0] = localTCHAR.length();
      Program localProgram = getProgram((String)localObject2, null);
      if (localProgram != null)
      {
        if (j == localObject1.length)
        {
          Program[] arrayOfProgram = new Program[localObject1.length + 1024];
          System.arraycopy(localObject1, 0, arrayOfProgram, 0, localObject1.length);
          localObject1 = arrayOfProgram;
        }
        localObject1[(j++)] = localProgram;
      }
      i++;
    }
    if (j != localObject1.length)
    {
      localObject2 = new Program[j];
      System.arraycopy(localObject1, 0, localObject2, 0, j);
      localObject1 = localObject2;
    }
    return localObject1;
  }

  public static boolean launch(String paramString)
  {
    return launch(paramString, null);
  }

  public static boolean launch(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      SWT.error(4);
    int i = OS.GetProcessHeap();
    TCHAR localTCHAR = new TCHAR(0, paramString1, true);
    int j = localTCHAR.length() * TCHAR.sizeof;
    int k = OS.HeapAlloc(i, 8, j);
    OS.MoveMemory(k, localTCHAR, j);
    int m = 0;
    if ((paramString2 != null) && (OS.PathIsExe(k)))
    {
      localObject = new TCHAR(0, paramString2, true);
      j = ((TCHAR)localObject).length() * TCHAR.sizeof;
      m = OS.HeapAlloc(i, 8, j);
      OS.MoveMemory(m, (TCHAR)localObject, j);
    }
    Object localObject = new SHELLEXECUTEINFO();
    ((SHELLEXECUTEINFO)localObject).cbSize = SHELLEXECUTEINFO.sizeof;
    ((SHELLEXECUTEINFO)localObject).lpFile = k;
    ((SHELLEXECUTEINFO)localObject).lpDirectory = m;
    ((SHELLEXECUTEINFO)localObject).nShow = 5;
    boolean bool = OS.ShellExecuteEx((SHELLEXECUTEINFO)localObject);
    if (k != 0)
      OS.HeapFree(i, 0, k);
    if (m != 0)
      OS.HeapFree(i, 0, m);
    return bool;
  }

  public boolean execute(String paramString)
  {
    if (paramString == null)
      SWT.error(4);
    int i = 0;
    int j = 1;
    String str1 = this.command;
    String str2 = "";
    while (i < ARGUMENTS.length)
    {
      int k = this.command.indexOf(ARGUMENTS[i]);
      if (k != -1)
      {
        j = 0;
        str1 = this.command.substring(0, k);
        str2 = this.command.substring(k + ARGUMENTS[i].length(), this.command.length());
        break;
      }
      i++;
    }
    if (j != 0)
      paramString = " \"" + paramString + "\"";
    String str3 = str1 + paramString + str2;
    int m = OS.GetProcessHeap();
    TCHAR localTCHAR = new TCHAR(0, str3, true);
    int n = localTCHAR.length() * TCHAR.sizeof;
    int i1 = OS.HeapAlloc(m, 8, n);
    OS.MoveMemory(i1, localTCHAR, n);
    STARTUPINFO localSTARTUPINFO = new STARTUPINFO();
    localSTARTUPINFO.cb = STARTUPINFO.sizeof;
    PROCESS_INFORMATION localPROCESS_INFORMATION = new PROCESS_INFORMATION();
    boolean bool = OS.CreateProcess(0, i1, 0, 0, false, 0, 0, 0, localSTARTUPINFO, localPROCESS_INFORMATION);
    if (i1 != 0)
      OS.HeapFree(m, 0, i1);
    if (localPROCESS_INFORMATION.hProcess != 0)
      OS.CloseHandle(localPROCESS_INFORMATION.hProcess);
    if (localPROCESS_INFORMATION.hThread != 0)
      OS.CloseHandle(localPROCESS_INFORMATION.hThread);
    return bool;
  }

  public ImageData getImageData()
  {
    Object localObject1;
    if (this.extension != null)
    {
      SHFILEINFOA localSHFILEINFOA = OS.IsUnicode ? new SHFILEINFOW() : new SHFILEINFOA();
      int j = 273;
      TCHAR localTCHAR = new TCHAR(0, this.extension, true);
      OS.SHGetFileInfo(localTCHAR, 128, localSHFILEINFOA, SHFILEINFO.sizeof, j);
      if (localSHFILEINFOA.hIcon != 0)
      {
        localObject1 = Image.win32_new(null, 1, localSHFILEINFOA.hIcon);
        localObject2 = ((Image)localObject1).getImageData();
        ((Image)localObject1).dispose();
        return localObject2;
      }
    }
    int i = 0;
    String str = this.iconName;
    int k = this.iconName.indexOf(',');
    if (k != -1)
    {
      str = this.iconName.substring(0, k);
      localObject1 = this.iconName.substring(k + 1, this.iconName.length()).trim();
      try
      {
        i = Integer.parseInt((String)localObject1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    int m = str.length();
    if ((m > 1) && (str.charAt(0) == '"') && (str.charAt(m - 1) == '"'))
      str = str.substring(1, m - 1);
    Object localObject2 = new TCHAR(0, str, true);
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = (int[])null;
    OS.ExtractIconEx((TCHAR)localObject2, i, arrayOfInt2, arrayOfInt1, 1);
    if (arrayOfInt1[0] == 0)
      return null;
    Image localImage = Image.win32_new(null, 1, arrayOfInt1[0]);
    ImageData localImageData = localImage.getImageData();
    localImage.dispose();
    return localImageData;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if ((paramObject instanceof Program))
    {
      Program localProgram = (Program)paramObject;
      return (this.name.equals(localProgram.name)) && (this.command.equals(localProgram.command)) && (this.iconName.equals(localProgram.iconName));
    }
    return false;
  }

  public int hashCode()
  {
    return this.name.hashCode() ^ this.command.hashCode() ^ this.iconName.hashCode();
  }

  public String toString()
  {
    return "Program {" + this.name + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.program.Program
 * JD-Core Version:    0.6.2
 */