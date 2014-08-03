package org.eclipse.swt.internal;

public class Callback
{
  Object object;
  String method;
  String signature;
  int argCount;
  int address;
  int errorResult;
  boolean isStatic;
  boolean isArrayBased;
  static final String PTR_SIGNATURE = C.PTR_SIZEOF == 4 ? "I" : "J";
  static final String SIGNATURE_0 = getSignature(0);
  static final String SIGNATURE_1 = getSignature(1);
  static final String SIGNATURE_2 = getSignature(2);
  static final String SIGNATURE_3 = getSignature(3);
  static final String SIGNATURE_4 = getSignature(4);
  static final String SIGNATURE_N = "([" + PTR_SIGNATURE + ")" + PTR_SIGNATURE;

  public Callback(Object paramObject, String paramString, int paramInt)
  {
    this(paramObject, paramString, paramInt, false);
  }

  public Callback(Object paramObject, String paramString, int paramInt, boolean paramBoolean)
  {
    this(paramObject, paramString, paramInt, paramBoolean, 0);
  }

  public Callback(Object paramObject, String paramString, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    this.object = paramObject;
    this.method = paramString;
    this.argCount = paramInt1;
    this.isStatic = (paramObject instanceof Class);
    this.isArrayBased = paramBoolean;
    this.errorResult = paramInt2;
    if (paramBoolean)
      this.signature = SIGNATURE_N;
    else
      switch (paramInt1)
      {
      case 0:
        this.signature = SIGNATURE_0;
        break;
      case 1:
        this.signature = SIGNATURE_1;
        break;
      case 2:
        this.signature = SIGNATURE_2;
        break;
      case 3:
        this.signature = SIGNATURE_3;
        break;
      case 4:
        this.signature = SIGNATURE_4;
        break;
      default:
        this.signature = getSignature(paramInt1);
      }
    this.address = bind(this, paramObject, paramString, this.signature, paramInt1, this.isStatic, paramBoolean, paramInt2);
  }

  static synchronized native int bind(Callback paramCallback, Object paramObject, String paramString1, String paramString2, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, int paramInt2);

  public void dispose()
  {
    if (this.object == null)
      return;
    unbind(this);
    this.object = (this.method = this.signature = null);
    this.address = 0;
  }

  public int getAddress()
  {
    return this.address;
  }

  public static native String getPlatform();

  public static native int getEntryCount();

  static String getSignature(int paramInt)
  {
    String str = "(";
    for (int i = 0; i < paramInt; i++)
      str = str + PTR_SIGNATURE;
    str = str + ")" + PTR_SIGNATURE;
    return str;
  }

  public static final synchronized native void setEnabled(boolean paramBoolean);

  public static final synchronized native boolean getEnabled();

  /** @deprecated */
  static final void ignoreCallbacks(boolean paramBoolean)
  {
    setEnabled(!paramBoolean);
  }

  public static final synchronized native void reset();

  static final synchronized native void unbind(Callback paramCallback);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.Callback
 * JD-Core Version:    0.6.2
 */