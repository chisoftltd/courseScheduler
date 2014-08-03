package org.eclipse.swt.internal.image;

import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

public final class TIFFFileFormat extends FileFormat
{
  boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[4];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      if (arrayOfByte[0] != arrayOfByte[1])
        return false;
      return ((arrayOfByte[0] == 73) && (arrayOfByte[2] == 42) && (arrayOfByte[3] == 0)) || ((arrayOfByte[0] == 77) && (arrayOfByte[2] == 0) && (arrayOfByte[3] == 42));
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  ImageData[] loadFromByteStream()
  {
    byte[] arrayOfByte = new byte[8];
    ImageData[] arrayOfImageData1 = new ImageData[0];
    TIFFRandomFileAccess localTIFFRandomFileAccess = new TIFFRandomFileAccess(this.inputStream);
    try
    {
      localTIFFRandomFileAccess.read(arrayOfByte);
      if (arrayOfByte[0] != arrayOfByte[1])
        SWT.error(40);
      if (((arrayOfByte[0] != 73) || (arrayOfByte[2] != 42) || (arrayOfByte[3] != 0)) && ((arrayOfByte[0] != 77) || (arrayOfByte[2] != 0) || (arrayOfByte[3] != 42)))
        SWT.error(40);
      boolean bool = arrayOfByte[0] == 73;
      int i = bool ? arrayOfByte[4] & 0xFF | (arrayOfByte[5] & 0xFF) << 8 | (arrayOfByte[6] & 0xFF) << 16 | (arrayOfByte[7] & 0xFF) << 24 : arrayOfByte[7] & 0xFF | (arrayOfByte[6] & 0xFF) << 8 | (arrayOfByte[5] & 0xFF) << 16 | (arrayOfByte[4] & 0xFF) << 24;
      while (i != 0)
      {
        localTIFFRandomFileAccess.seek(i);
        TIFFDirectory localTIFFDirectory = new TIFFDirectory(localTIFFRandomFileAccess, bool, this.loader);
        int[] arrayOfInt = new int[1];
        ImageData localImageData = localTIFFDirectory.read(arrayOfInt);
        i = arrayOfInt[0];
        ImageData[] arrayOfImageData2 = arrayOfImageData1;
        arrayOfImageData1 = new ImageData[arrayOfImageData2.length + 1];
        System.arraycopy(arrayOfImageData2, 0, arrayOfImageData1, 0, arrayOfImageData2.length);
        arrayOfImageData1[(arrayOfImageData1.length - 1)] = localImageData;
      }
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
    return arrayOfImageData1;
  }

  void unloadIntoByteStream(ImageLoader paramImageLoader)
  {
    ImageData localImageData = paramImageLoader.data[0];
    TIFFDirectory localTIFFDirectory = new TIFFDirectory(localImageData);
    try
    {
      localTIFFDirectory.writeToStream(this.outputStream);
    }
    catch (IOException localIOException)
    {
      SWT.error(39, localIOException);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.TIFFFileFormat
 * JD-Core Version:    0.6.2
 */