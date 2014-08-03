package org.eclipse.swt.internal.mozilla.init;

import org.eclipse.swt.internal.Lock;
import org.eclipse.swt.internal.Platform;

public class XPCOMInit extends Platform
{
  public static final int PATH_MAX = 4096;

  public static final native int GREVersionRange_sizeof();

  public static final native int _GRE_GetGREPathWithProperties(GREVersionRange paramGREVersionRange, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final int GRE_GetGREPathWithProperties(GREVersionRange paramGREVersionRange, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    lock.lock();
    try
    {
      int i = _GRE_GetGREPathWithProperties(paramGREVersionRange, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _XPCOMGlueStartup(byte[] paramArrayOfByte);

  public static final int XPCOMGlueStartup(byte[] paramArrayOfByte)
  {
    lock.lock();
    try
    {
      int i = _XPCOMGlueStartup(paramArrayOfByte);
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }

  public static final native int _XPCOMGlueShutdown();

  public static final int XPCOMGlueShutdown()
  {
    lock.lock();
    try
    {
      int i = _XPCOMGlueShutdown();
      return i;
    }
    finally
    {
      lock.unlock();
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.init.XPCOMInit
 * JD-Core Version:    0.6.2
 */