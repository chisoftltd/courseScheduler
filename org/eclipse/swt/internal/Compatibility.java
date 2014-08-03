package org.eclipse.swt.internal;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import org.eclipse.swt.SWT;

public final class Compatibility
{
  public static double PI = Math.PI;
  static double toRadians = PI / 180.0D;
  private static ResourceBundle msgs = null;

  public static int cos(int paramInt1, int paramInt2)
  {
    return (int)(Math.cos(paramInt1 * toRadians) * paramInt2);
  }

  public static int sin(int paramInt1, int paramInt2)
  {
    return (int)(Math.sin(paramInt1 * toRadians) * paramInt2);
  }

  public static int ceil(int paramInt1, int paramInt2)
  {
    return (int)Math.ceil(paramInt1 / paramInt2);
  }

  public static boolean fileExists(String paramString1, String paramString2)
  {
    return new File(paramString1, paramString2).exists();
  }

  public static int floor(int paramInt1, int paramInt2)
  {
    return (int)Math.floor(paramInt1 / paramInt2);
  }

  public static int round(int paramInt1, int paramInt2)
  {
    return Math.round(paramInt1 / paramInt2);
  }

  public static int pow2(int paramInt)
  {
    if ((paramInt >= 1) && (paramInt <= 30))
      return 2 << paramInt - 1;
    if (paramInt != 0)
      SWT.error(6);
    return 1;
  }

  public static OutputStream newDeflaterOutputStream(OutputStream paramOutputStream)
    throws IOException
  {
    return new DeflaterOutputStream(paramOutputStream);
  }

  public static InputStream newFileInputStream(String paramString)
    throws IOException
  {
    return new FileInputStream(paramString);
  }

  public static OutputStream newFileOutputStream(String paramString)
    throws IOException
  {
    return new FileOutputStream(paramString);
  }

  public static InputStream newInflaterInputStream(InputStream paramInputStream)
    throws IOException
  {
    return new BufferedInputStream(new InflaterInputStream(paramInputStream));
  }

  public static boolean isLetter(char paramChar)
  {
    return Character.isLetter(paramChar);
  }

  public static boolean isLetterOrDigit(char paramChar)
  {
    return Character.isLetterOrDigit(paramChar);
  }

  public static boolean isSpaceChar(char paramChar)
  {
    return Character.isSpaceChar(paramChar);
  }

  public static boolean isWhitespace(char paramChar)
  {
    return Character.isWhitespace(paramChar);
  }

  public static void exec(String paramString)
    throws IOException
  {
    Runtime.getRuntime().exec(paramString);
  }

  public static void exec(String[] paramArrayOfString)
    throws IOException
  {
    Runtime.getRuntime().exec(paramArrayOfString);
  }

  public static void exec(String[] paramArrayOfString1, String[] paramArrayOfString2, String paramString)
    throws IOException
  {
    Runtime.getRuntime().exec(paramArrayOfString1, null, paramString != null ? new File(paramString) : null);
  }

  public static String getMessage(String paramString)
  {
    String str = paramString;
    if (paramString == null)
      SWT.error(4);
    if (msgs == null)
      try
      {
        msgs = ResourceBundle.getBundle("org.eclipse.swt.internal.SWTMessages");
      }
      catch (MissingResourceException localMissingResourceException1)
      {
        str = paramString + " (no resource bundle)";
      }
    if (msgs != null)
      try
      {
        str = msgs.getString(paramString);
      }
      catch (MissingResourceException localMissingResourceException2)
      {
      }
    return str;
  }

  public static String getMessage(String paramString, Object[] paramArrayOfObject)
  {
    String str = paramString;
    if ((paramString == null) || (paramArrayOfObject == null))
      SWT.error(4);
    if (msgs == null)
      try
      {
        msgs = ResourceBundle.getBundle("org.eclipse.swt.internal.SWTMessages");
      }
      catch (MissingResourceException localMissingResourceException1)
      {
        str = paramString + " (no resource bundle)";
      }
    if (msgs != null)
      try
      {
        MessageFormat localMessageFormat = new MessageFormat("");
        localMessageFormat.applyPattern(msgs.getString(paramString));
        str = localMessageFormat.format(paramArrayOfObject);
      }
      catch (MissingResourceException localMissingResourceException2)
      {
      }
    return str;
  }

  public static void interrupt()
  {
    Thread.currentThread().interrupt();
  }

  public static boolean equalsIgnoreCase(String paramString1, String paramString2)
  {
    return paramString1.equalsIgnoreCase(paramString2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.Compatibility
 * JD-Core Version:    0.6.2
 */