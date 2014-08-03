package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public abstract class FileFormat
{
  static final String FORMAT_PACKAGE = "org.eclipse.swt.internal.image";
  static final String FORMAT_SUFFIX = "FileFormat";
  static final String[] FORMATS = { "WinBMP", "WinBMP", "GIF", "WinICO", "JPEG", "PNG", "TIFF", "OS2BMP" };
  LEDataInputStream inputStream;
  LEDataOutputStream outputStream;
  ImageLoader loader;
  int compression;

  abstract boolean isFileFormat(LEDataInputStream paramLEDataInputStream);

  abstract ImageData[] loadFromByteStream();

  public ImageData[] loadFromStream(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      this.inputStream = paramLEDataInputStream;
      return loadFromByteStream();
    }
    catch (Exception localException)
    {
      if ((localException instanceof IOException))
        SWT.error(39, localException);
      else
        SWT.error(40, localException);
    }
    return null;
  }

  public static ImageData[] load(InputStream paramInputStream, ImageLoader paramImageLoader)
  {
    FileFormat localFileFormat = null;
    LEDataInputStream localLEDataInputStream = new LEDataInputStream(paramInputStream);
    int i = 0;
    for (int j = 1; j < FORMATS.length; j++)
      if (FORMATS[j] != null)
        try
        {
          Class localClass = Class.forName("org.eclipse.swt.internal.image." + FORMATS[j] + "FileFormat");
          localFileFormat = (FileFormat)localClass.newInstance();
          if (localFileFormat.isFileFormat(localLEDataInputStream))
            i = 1;
        }
        catch (ClassNotFoundException localClassNotFoundException)
        {
          FORMATS[j] = null;
        }
        catch (Exception localException)
        {
        }
    if (i == 0)
      SWT.error(42);
    localFileFormat.loader = paramImageLoader;
    return localFileFormat.loadFromStream(localLEDataInputStream);
  }

  public static void save(OutputStream paramOutputStream, int paramInt, ImageLoader paramImageLoader)
  {
    if ((paramInt < 0) || (paramInt >= FORMATS.length))
      SWT.error(42);
    if (FORMATS[paramInt] == null)
      SWT.error(42);
    if ((paramImageLoader.data == null) || (paramImageLoader.data.length < 1))
      SWT.error(5);
    LEDataOutputStream localLEDataOutputStream = new LEDataOutputStream(paramOutputStream);
    FileFormat localFileFormat = null;
    try
    {
      Class localClass = Class.forName("org.eclipse.swt.internal.image." + FORMATS[paramInt] + "FileFormat");
      localFileFormat = (FileFormat)localClass.newInstance();
    }
    catch (Exception localException)
    {
      SWT.error(42);
    }
    if (paramInt == 1)
      switch (paramImageLoader.data[0].depth)
      {
      case 8:
        localFileFormat.compression = 1;
        break;
      case 4:
        localFileFormat.compression = 2;
      case 5:
      case 6:
      case 7:
      }
    localFileFormat.unloadIntoStream(paramImageLoader, localLEDataOutputStream);
  }

  abstract void unloadIntoByteStream(ImageLoader paramImageLoader);

  public void unloadIntoStream(ImageLoader paramImageLoader, LEDataOutputStream paramLEDataOutputStream)
  {
    try
    {
      this.outputStream = paramLEDataOutputStream;
      unloadIntoByteStream(paramImageLoader);
      this.outputStream.flush();
    }
    catch (Exception localException1)
    {
      try
      {
        this.outputStream.flush();
      }
      catch (Exception localException2)
      {
      }
      SWT.error(39, localException1);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.FileFormat
 * JD-Core Version:    0.6.2
 */