package org.eclipse.swt.internal.mozilla;

import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.LONG;

public class XPCOMObject
{
  static boolean IsSolaris = (str.startsWith("sunos")) || (str.startsWith("solaris"));
  private int ppVtable;
  private static final int MAX_ARG_COUNT = 12;
  private static final int MAX_VTABLE_LENGTH = 80;
  static final int OS_OFFSET = IsSolaris ? 2 : 0;
  private static Callback[][] Callbacks = new Callback[80 + OS_OFFSET][12];
  private static Hashtable ObjectMap = new Hashtable();

  static
  {
    String str = System.getProperty("os.name").toLowerCase();
  }

  public XPCOMObject(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[paramArrayOfInt.length + OS_OFFSET];
    synchronized (Callbacks)
    {
      int j = 0;
      int k = paramArrayOfInt.length;
      while (j < k)
      {
        if (Callbacks[(j + OS_OFFSET)][paramArrayOfInt[j]] == null)
          Callbacks[(j + OS_OFFSET)][paramArrayOfInt[j]] = new Callback(getClass(), "callback" + j, paramArrayOfInt[j] + 1, true, -2147467259);
        arrayOfInt[(j + OS_OFFSET)] = Callbacks[(j + OS_OFFSET)][paramArrayOfInt[j]].getAddress();
        if (arrayOfInt[(j + OS_OFFSET)] == 0)
          SWT.error(3);
        j++;
      }
    }
    int i = C.malloc(C.PTR_SIZEOF * (paramArrayOfInt.length + OS_OFFSET));
    XPCOM.memmove(i, arrayOfInt, C.PTR_SIZEOF * (paramArrayOfInt.length + OS_OFFSET));
    this.ppVtable = C.malloc(C.PTR_SIZEOF);
    XPCOM.memmove(this.ppVtable, new int[] { i }, C.PTR_SIZEOF);
    ObjectMap.put(new LONG(this.ppVtable), this);
  }

  static int callback0(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method0(arrayOfInt);
  }

  static int callback1(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method1(arrayOfInt);
  }

  static int callback10(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method10(arrayOfInt);
  }

  static int callback11(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method11(arrayOfInt);
  }

  static int callback12(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method12(arrayOfInt);
  }

  static int callback13(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method13(arrayOfInt);
  }

  static int callback14(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method14(arrayOfInt);
  }

  static int callback15(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method15(arrayOfInt);
  }

  static int callback16(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method16(arrayOfInt);
  }

  static int callback17(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method17(arrayOfInt);
  }

  static int callback18(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method18(arrayOfInt);
  }

  static int callback19(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method19(arrayOfInt);
  }

  static int callback2(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method2(arrayOfInt);
  }

  static int callback20(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method20(arrayOfInt);
  }

  static int callback21(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method21(arrayOfInt);
  }

  static int callback22(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method22(arrayOfInt);
  }

  static int callback23(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method23(arrayOfInt);
  }

  static int callback24(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method24(arrayOfInt);
  }

  static int callback25(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method25(arrayOfInt);
  }

  static int callback26(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method26(arrayOfInt);
  }

  static int callback27(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method27(arrayOfInt);
  }

  static int callback28(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method28(arrayOfInt);
  }

  static int callback29(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method29(arrayOfInt);
  }

  static int callback3(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method3(arrayOfInt);
  }

  static int callback30(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method30(arrayOfInt);
  }

  static int callback31(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method31(arrayOfInt);
  }

  static int callback32(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method32(arrayOfInt);
  }

  static int callback33(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method33(arrayOfInt);
  }

  static int callback34(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method34(arrayOfInt);
  }

  static int callback35(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method35(arrayOfInt);
  }

  static int callback36(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method36(arrayOfInt);
  }

  static int callback37(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method37(arrayOfInt);
  }

  static int callback38(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method38(arrayOfInt);
  }

  static int callback39(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method39(arrayOfInt);
  }

  static int callback4(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method4(arrayOfInt);
  }

  static int callback40(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method40(arrayOfInt);
  }

  static int callback41(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method41(arrayOfInt);
  }

  static int callback42(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method42(arrayOfInt);
  }

  static int callback43(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method43(arrayOfInt);
  }

  static int callback44(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method44(arrayOfInt);
  }

  static int callback45(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method45(arrayOfInt);
  }

  static int callback46(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method46(arrayOfInt);
  }

  static int callback47(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method47(arrayOfInt);
  }

  static int callback48(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method48(arrayOfInt);
  }

  static int callback49(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method49(arrayOfInt);
  }

  static int callback5(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method5(arrayOfInt);
  }

  static int callback50(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method50(arrayOfInt);
  }

  static int callback51(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method51(arrayOfInt);
  }

  static int callback52(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method52(arrayOfInt);
  }

  static int callback53(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method53(arrayOfInt);
  }

  static int callback54(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method54(arrayOfInt);
  }

  static int callback55(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method55(arrayOfInt);
  }

  static int callback56(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method56(arrayOfInt);
  }

  static int callback57(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method57(arrayOfInt);
  }

  static int callback58(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method58(arrayOfInt);
  }

  static int callback59(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method59(arrayOfInt);
  }

  static int callback6(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method6(arrayOfInt);
  }

  static int callback60(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method60(arrayOfInt);
  }

  static int callback61(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method61(arrayOfInt);
  }

  static int callback62(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method62(arrayOfInt);
  }

  static int callback63(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method63(arrayOfInt);
  }

  static int callback64(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method64(arrayOfInt);
  }

  static int callback65(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method65(arrayOfInt);
  }

  static int callback66(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method66(arrayOfInt);
  }

  static int callback67(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method67(arrayOfInt);
  }

  static int callback68(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method68(arrayOfInt);
  }

  static int callback69(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method69(arrayOfInt);
  }

  static int callback7(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method7(arrayOfInt);
  }

  static int callback70(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method70(arrayOfInt);
  }

  static int callback71(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method71(arrayOfInt);
  }

  static int callback72(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method72(arrayOfInt);
  }

  static int callback73(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method73(arrayOfInt);
  }

  static int callback74(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method74(arrayOfInt);
  }

  static int callback75(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method75(arrayOfInt);
  }

  static int callback76(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method76(arrayOfInt);
  }

  static int callback77(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method77(arrayOfInt);
  }

  static int callback78(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method78(arrayOfInt);
  }

  static int callback79(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method79(arrayOfInt);
  }

  static int callback8(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method8(arrayOfInt);
  }

  static int callback9(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt[0];
    Object localObject = ObjectMap.get(new LONG(i));
    if (localObject == null)
      return -2147467259;
    int[] arrayOfInt = new int[paramArrayOfInt.length - 1];
    System.arraycopy(paramArrayOfInt, 1, arrayOfInt, 0, arrayOfInt.length);
    return ((XPCOMObject)localObject).method9(arrayOfInt);
  }

  public void dispose()
  {
    int[] arrayOfInt = new int[1];
    XPCOM.memmove(arrayOfInt, this.ppVtable, C.PTR_SIZEOF);
    C.free(arrayOfInt[0]);
    C.free(this.ppVtable);
    ObjectMap.remove(new LONG(this.ppVtable));
    this.ppVtable = 0;
  }

  public int getAddress()
  {
    return this.ppVtable;
  }

  public int method0(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method1(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method10(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method11(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method12(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method13(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method14(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method15(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method16(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method17(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method18(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method19(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method2(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method20(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method21(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method22(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method23(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method24(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method25(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method26(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method27(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method28(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method29(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method3(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method30(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method31(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method32(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method33(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method34(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method35(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method36(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method37(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method38(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method39(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method4(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method40(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method41(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method42(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method43(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method44(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method45(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method46(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method47(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method48(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method49(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method5(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method50(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method51(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method52(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method53(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method54(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method55(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method56(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method57(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method58(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method59(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method6(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method60(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method61(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method62(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method63(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method64(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method65(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method66(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method67(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method68(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method69(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method7(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method70(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method71(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method72(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method73(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method74(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method75(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method76(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method77(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method78(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method79(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method8(int[] paramArrayOfInt)
  {
    return -2147467263;
  }

  public int method9(int[] paramArrayOfInt)
  {
    return -2147467263;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.mozilla.XPCOMObject
 * JD-Core Version:    0.6.2
 */