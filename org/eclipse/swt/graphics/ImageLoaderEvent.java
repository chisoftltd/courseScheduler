package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.SWTEventObject;

public class ImageLoaderEvent extends SWTEventObject
{
  public ImageData imageData;
  public int incrementCount;
  public boolean endOfImage;
  static final long serialVersionUID = 3257284738325558065L;

  public ImageLoaderEvent(ImageLoader paramImageLoader, ImageData paramImageData, int paramInt, boolean paramBoolean)
  {
    super(paramImageLoader);
    this.imageData = paramImageData;
    this.incrementCount = paramInt;
    this.endOfImage = paramBoolean;
  }

  public String toString()
  {
    return "ImageLoaderEvent {source=" + this.source + " imageData=" + this.imageData + " incrementCount=" + this.incrementCount + " endOfImage=" + this.endOfImage + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.ImageLoaderEvent
 * JD-Core Version:    0.6.2
 */