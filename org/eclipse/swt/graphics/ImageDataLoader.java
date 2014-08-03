package org.eclipse.swt.graphics;

import java.io.InputStream;

class ImageDataLoader
{
  public static ImageData[] load(InputStream paramInputStream)
  {
    return new ImageLoader().load(paramInputStream);
  }

  public static ImageData[] load(String paramString)
  {
    return new ImageLoader().load(paramString);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.ImageDataLoader
 * JD-Core Version:    0.6.2
 */