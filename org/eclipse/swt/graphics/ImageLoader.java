package org.eclipse.swt.graphics;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.image.FileFormat;

public class ImageLoader
{
  public ImageData[] data;
  public int logicalScreenWidth;
  public int logicalScreenHeight;
  public int backgroundPixel;
  public int repeatCount;
  Vector imageLoaderListeners;

  public ImageLoader()
  {
    reset();
  }

  void reset()
  {
    this.data = null;
    this.logicalScreenWidth = 0;
    this.logicalScreenHeight = 0;
    this.backgroundPixel = -1;
    this.repeatCount = 1;
  }

  public ImageData[] load(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      SWT.error(4);
    reset();
    this.data = FileFormat.load(paramInputStream, this);
    return this.data;
  }

  // ERROR //
  public ImageData[] load(String paramString)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +7 -> 8
    //   4: iconst_4
    //   5: invokestatic 34	org/eclipse/swt/SWT:error	(I)V
    //   8: aconst_null
    //   9: astore_2
    //   10: aload_1
    //   11: invokestatic 46	org/eclipse/swt/internal/Compatibility:newFileInputStream	(Ljava/lang/String;)Ljava/io/InputStream;
    //   14: astore_2
    //   15: aload_0
    //   16: aload_2
    //   17: invokevirtual 52	org/eclipse/swt/graphics/ImageLoader:load	(Ljava/io/InputStream;)[Lorg/eclipse/swt/graphics/ImageData;
    //   20: astore 6
    //   22: jsr +24 -> 46
    //   25: aload 6
    //   27: areturn
    //   28: astore_3
    //   29: bipush 39
    //   31: aload_3
    //   32: invokestatic 54	org/eclipse/swt/SWT:error	(ILjava/lang/Throwable;)V
    //   35: goto +27 -> 62
    //   38: astore 5
    //   40: jsr +6 -> 46
    //   43: aload 5
    //   45: athrow
    //   46: astore 4
    //   48: aload_2
    //   49: ifnull +11 -> 60
    //   52: aload_2
    //   53: invokevirtual 57	java/io/InputStream:close	()V
    //   56: goto +4 -> 60
    //   59: pop
    //   60: ret 4
    //   62: jsr -16 -> 46
    //   65: aconst_null
    //   66: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   10	25	28	java/io/IOException
    //   10	25	38	finally
    //   28	35	38	finally
    //   62	65	38	finally
    //   48	56	59	java/io/IOException
  }

  public void save(OutputStream paramOutputStream, int paramInt)
  {
    if (paramOutputStream == null)
      SWT.error(4);
    FileFormat.save(paramOutputStream, paramInt, this);
  }

  public void save(String paramString, int paramInt)
  {
    if (paramString == null)
      SWT.error(4);
    OutputStream localOutputStream = null;
    try
    {
      localOutputStream = Compatibility.newFileOutputStream(paramString);
    }
    catch (IOException localIOException1)
    {
      SWT.error(39, localIOException1);
    }
    save(localOutputStream, paramInt);
    try
    {
      localOutputStream.close();
    }
    catch (IOException localIOException2)
    {
    }
  }

  public void addImageLoaderListener(ImageLoaderListener paramImageLoaderListener)
  {
    if (paramImageLoaderListener == null)
      SWT.error(4);
    if (this.imageLoaderListeners == null)
      this.imageLoaderListeners = new Vector();
    this.imageLoaderListeners.addElement(paramImageLoaderListener);
  }

  public void removeImageLoaderListener(ImageLoaderListener paramImageLoaderListener)
  {
    if (paramImageLoaderListener == null)
      SWT.error(4);
    if (this.imageLoaderListeners == null)
      return;
    this.imageLoaderListeners.removeElement(paramImageLoaderListener);
  }

  public boolean hasListeners()
  {
    return (this.imageLoaderListeners != null) && (this.imageLoaderListeners.size() > 0);
  }

  public void notifyListeners(ImageLoaderEvent paramImageLoaderEvent)
  {
    if (!hasListeners())
      return;
    int i = this.imageLoaderListeners.size();
    for (int j = 0; j < i; j++)
    {
      ImageLoaderListener localImageLoaderListener = (ImageLoaderListener)this.imageLoaderListeners.elementAt(j);
      localImageLoaderListener.imageDataLoaded(paramImageLoaderEvent);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.ImageLoader
 * JD-Core Version:    0.6.2
 */