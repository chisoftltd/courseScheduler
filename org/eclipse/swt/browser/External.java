package org.eclipse.swt.browser;

import java.util.Hashtable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsIClassInfo;
import org.eclipse.swt.internal.mozilla.nsIComponentManager;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIMemory;
import org.eclipse.swt.internal.mozilla.nsISecurityCheckedComponent;
import org.eclipse.swt.internal.mozilla.nsIServiceManager;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.internal.mozilla.nsIVariant;
import org.eclipse.swt.internal.mozilla.nsIWritableVariant;

class External
{
  public static final String EXTERNAL_IID_STR = "ded01d20-ba6f-11dd-ad8b-0800200c9a66";
  public static final nsID EXTERNAL_IID = new nsID("ded01d20-ba6f-11dd-ad8b-0800200c9a66");
  XPCOMObject supports;
  XPCOMObject external;
  XPCOMObject classInfo;
  XPCOMObject securityCheckedComponent;
  int refCount = 0;

  External()
  {
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return External.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return External.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return External.this.Release();
      }
    };
    this.classInfo = new XPCOMObject(new int[] { 2, 0, 0, 2, 2, 1, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return External.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return External.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return External.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getInterfaces(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getHelperForLanguage(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getContractID(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getClassDescription(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getClassID(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getImplementationLanguage(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getFlags(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return External.this.getClassIDNoAlloc(paramAnonymousArrayOfInt[0]);
      }
    };
    this.securityCheckedComponent = new XPCOMObject(new int[] { 2, 0, 0, 2, 3, 3, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return External.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return External.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return External.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return External.this.canCreateWrapper(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return External.this.canCallMethod(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return External.this.canGetProperty(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return External.this.canSetProperty(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
    this.external = new XPCOMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return External.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return External.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return External.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return External.this.callJava(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.supports != null)
    {
      this.supports.dispose();
      this.supports = null;
    }
    if (this.external != null)
    {
      this.external.dispose();
      this.external = null;
    }
  }

  int getAddress()
  {
    return this.external.getAddress();
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    nsID localnsID = new nsID();
    XPCOM.memmove(localnsID, paramInt1, 16);
    if (localnsID.Equals(nsISupports.NS_ISUPPORTS_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.supports.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIClassInfo.NS_ICLASSINFO_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.classInfo.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsISecurityCheckedComponent.NS_ISECURITYCHECKEDCOMPONENT_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.securityCheckedComponent.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(EXTERNAL_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.external.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    XPCOM.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  int getClassDescription(int paramInt)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte1 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte1, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "external", true);
    int j = localnsIMemory.Alloc(arrayOfByte2.length);
    C.memmove(j, arrayOfByte2, arrayOfByte2.length);
    C.memmove(paramInt, new int[] { j }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    return 0;
  }

  int getClassID(int paramInt)
  {
    return 0;
  }

  int getClassIDNoAlloc(int paramInt)
  {
    return 0;
  }

  int getContractID(int paramInt)
  {
    return 0;
  }

  int getFlags(int paramInt)
  {
    C.memmove(paramInt, new int[] { 4 }, 4);
    return 0;
  }

  int getHelperForLanguage(int paramInt1, int paramInt2)
  {
    C.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    return 0;
  }

  int getImplementationLanguage(int paramInt)
  {
    C.memmove(paramInt, new int[] { 5 }, 4);
    return 0;
  }

  int getInterfaces(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    int j = localnsIMemory.Alloc(16);
    XPCOM.memmove(j, nsISecurityCheckedComponent.NS_ISECURITYCHECKEDCOMPONENT_IID, 16);
    int k = localnsIMemory.Alloc(16);
    XPCOM.memmove(k, EXTERNAL_IID, 16);
    int m = localnsIMemory.Alloc(2 * C.PTR_SIZEOF);
    C.memmove(m, new int[] { j }, C.PTR_SIZEOF);
    C.memmove(m + C.PTR_SIZEOF, new int[] { k }, C.PTR_SIZEOF);
    C.memmove(paramInt2, new int[] { m }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    C.memmove(paramInt1, new int[] { 2 }, 4);
    return 0;
  }

  int canCreateWrapper(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte1 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte1, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "allAccess", true);
    int j = localnsIMemory.Alloc(arrayOfByte2.length);
    C.memmove(j, arrayOfByte2, arrayOfByte2.length);
    C.memmove(paramInt2, new int[] { j }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    return 0;
  }

  int canCallMethod(int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte1 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte1, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    int j = XPCOM.strlen_PRUnichar(paramInt2);
    char[] arrayOfChar = new char[j];
    XPCOM.memmove(arrayOfChar, paramInt2, j * 2);
    String str = new String(arrayOfChar);
    byte[] arrayOfByte2;
    if (str.equals("callJava"))
      arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "allAccess", true);
    else
      arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "noAccess", true);
    int k = localnsIMemory.Alloc(arrayOfByte2.length);
    C.memmove(k, arrayOfByte2, arrayOfByte2.length);
    C.memmove(paramInt3, new int[] { k }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    return 0;
  }

  int canGetProperty(int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte1 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte1, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "noAccess", true);
    int j = localnsIMemory.Alloc(arrayOfByte2.length);
    C.memmove(j, arrayOfByte2, arrayOfByte2.length);
    C.memmove(paramInt3, new int[] { j }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    return 0;
  }

  int canSetProperty(int paramInt1, int paramInt2, int paramInt3)
  {
    int[] arrayOfInt = new int[1];
    int i = XPCOM.NS_GetServiceManager(arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte1 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
    i = localnsIServiceManager.GetServiceByContractID(arrayOfByte1, nsIMemory.NS_IMEMORY_IID, arrayOfInt);
    if (i != 0)
      Mozilla.error(i);
    if (arrayOfInt[0] == 0)
      Mozilla.error(-2147467262);
    localnsIServiceManager.Release();
    nsIMemory localnsIMemory = new nsIMemory(arrayOfInt[0]);
    arrayOfInt[0] = 0;
    byte[] arrayOfByte2 = MozillaDelegate.wcsToMbcs(null, "noAccess", true);
    int j = localnsIMemory.Alloc(arrayOfByte2.length);
    C.memmove(j, arrayOfByte2, arrayOfByte2.length);
    C.memmove(paramInt3, new int[] { j }, C.PTR_SIZEOF);
    localnsIMemory.Release();
    return 0;
  }

  Object convertToJava(nsIVariant paramnsIVariant, short paramShort)
  {
    int i;
    switch (paramShort)
    {
    case 13:
    case 255:
      return null;
    case 254:
      return new Object[0];
    case 10:
      int[] arrayOfInt1 = new int[1];
      i = paramnsIVariant.GetAsBool(arrayOfInt1);
      if (i != 0)
        Mozilla.error(i);
      return new Boolean(arrayOfInt1[0] != 0);
    case 2:
      int[] arrayOfInt2 = new int[1];
      i = paramnsIVariant.GetAsInt32(arrayOfInt2);
      if (i != 0)
        Mozilla.error(i);
      return new Double(arrayOfInt2[0]);
    case 9:
      int j = C.malloc(8);
      i = paramnsIVariant.GetAsDouble(j);
      if (i != 0)
        Mozilla.error(i);
      double[] arrayOfDouble = new double[1];
      C.memmove(arrayOfDouble, j, 8);
      C.free(j);
      return new Double(arrayOfDouble[0]);
    case 22:
      int[] arrayOfInt3 = new int[1];
      int[] arrayOfInt4 = new int[1];
      i = paramnsIVariant.GetAsWStringWithSize(arrayOfInt3, arrayOfInt4);
      if (i != 0)
        Mozilla.error(i);
      char[] arrayOfChar1 = new char[arrayOfInt3[0]];
      C.memmove(arrayOfChar1, arrayOfInt4[0], arrayOfInt3[0] * 2);
      return new String(arrayOfChar1);
    case 20:
      Object[] arrayOfObject = new Object[0];
      int k = C.malloc(16);
      C.memset(k, 0, 16);
      int[] arrayOfInt5 = new int[1];
      short[] arrayOfShort = new short[1];
      int[] arrayOfInt6 = new int[1];
      i = paramnsIVariant.GetAsArray(arrayOfShort, k, arrayOfInt5, arrayOfInt6);
      if (i != 0)
        Mozilla.error(i);
      if (arrayOfInt6[0] == 0)
        Mozilla.error(-2147467261);
      nsID localnsID = new nsID();
      XPCOM.memmove(localnsID, k, 16);
      C.free(k);
      int[] arrayOfInt7 = new int[1];
      i = XPCOM.NS_GetServiceManager(arrayOfInt7);
      if (i != 0)
        Mozilla.error(i);
      if (arrayOfInt7[0] == 0)
        Mozilla.error(-2147467262);
      nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt7[0]);
      arrayOfInt7[0] = 0;
      byte[] arrayOfByte = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
      i = localnsIServiceManager.GetServiceByContractID(arrayOfByte, nsIMemory.NS_IMEMORY_IID, arrayOfInt7);
      if (i != 0)
        Mozilla.error(i);
      if (arrayOfInt7[0] == 0)
        Mozilla.error(-2147467262);
      localnsIServiceManager.Release();
      nsIMemory localnsIMemory = new nsIMemory(arrayOfInt7[0]);
      arrayOfInt7[0] = 0;
      int m;
      Object localObject1;
      Object localObject2;
      if (localnsID.Equals(nsIVariant.NS_IVARIANT_IID))
      {
        arrayOfObject = new Object[arrayOfInt5[0]];
        for (m = 0; m < arrayOfInt5[0]; m++)
        {
          localObject1 = new int[1];
          C.memmove((int[])localObject1, arrayOfInt6[0] + m * C.PTR_SIZEOF, C.PTR_SIZEOF);
          localObject2 = new nsISupports(localObject1[0]);
          i = ((nsISupports)localObject2).QueryInterface(nsIVariant.NS_IVARIANT_IID, arrayOfInt7);
          if (i != 0)
            Mozilla.error(i);
          if (arrayOfInt7[0] == 0)
            Mozilla.error(-2147467262);
          nsIVariant localnsIVariant = new nsIVariant(arrayOfInt7[0]);
          arrayOfInt7[0] = 0;
          arrayOfShort[0] = 0;
          i = localnsIVariant.GetDataType(arrayOfShort);
          if (i != 0)
            Mozilla.error(i);
          try
          {
            arrayOfObject[m] = convertToJava(localnsIVariant, arrayOfShort[0]);
            localnsIVariant.Release();
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            localnsIVariant.Release();
            localnsIMemory.Free(arrayOfInt6[0]);
            localnsIMemory.Release();
            throw localIllegalArgumentException;
          }
        }
      }
      else
      {
        switch (arrayOfShort[0])
        {
        case 9:
          arrayOfObject = new Object[arrayOfInt5[0]];
          for (m = 0; m < arrayOfInt5[0]; m++)
          {
            localObject1 = new double[1];
            C.memmove((double[])localObject1, arrayOfInt6[0] + m * 8, 8);
            arrayOfObject[m] = new Double(localObject1[0]);
          }
          break;
        case 10:
          arrayOfObject = new Object[arrayOfInt5[0]];
          for (m = 0; m < arrayOfInt5[0]; m++)
          {
            localObject1 = new int[1];
            C.memmove((int[])localObject1, arrayOfInt6[0] + m * 4, 4);
            arrayOfObject[m] = new Boolean(localObject1[0] != 0);
          }
          break;
        case 2:
          arrayOfObject = new Object[arrayOfInt5[0]];
          for (m = 0; m < arrayOfInt5[0]; m++)
          {
            localObject1 = new int[1];
            C.memmove((int[])localObject1, arrayOfInt6[0] + m * 4, 4);
            arrayOfObject[m] = new Double(localObject1[0]);
          }
          break;
        case 17:
          arrayOfObject = new Object[arrayOfInt5[0]];
          for (m = 0; m < arrayOfInt5[0]; m++)
          {
            int n = arrayOfInt6[0] + m * C.PTR_SIZEOF;
            localObject2 = new int[1];
            C.memmove((int[])localObject2, n, C.PTR_SIZEOF);
            int i1 = XPCOM.strlen_PRUnichar(localObject2[0]);
            char[] arrayOfChar2 = new char[i1];
            XPCOM.memmove(arrayOfChar2, localObject2[0], i1 * 2);
            arrayOfObject[m] = new String(arrayOfChar2);
          }
          break;
        default:
          localnsIMemory.Free(arrayOfInt6[0]);
          localnsIMemory.Release();
          SWT.error(5);
        }
      }
      localnsIMemory.Free(arrayOfInt6[0]);
      localnsIMemory.Release();
      return arrayOfObject;
    }
    SWT.error(5);
    return null;
  }

  nsIVariant convertToJS(Object paramObject, nsIComponentManager paramnsIComponentManager)
  {
    int[] arrayOfInt1 = new int[1];
    byte[] arrayOfByte = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/variant;1", true);
    int i = paramnsIComponentManager.CreateInstanceByContractID(arrayOfByte, 0, nsIWritableVariant.NS_IWRITABLEVARIANT_IID, arrayOfInt1);
    nsIWritableVariant localnsIWritableVariant = new nsIWritableVariant(arrayOfInt1[0]);
    arrayOfInt1[0] = 0;
    if (paramObject == null)
    {
      i = localnsIWritableVariant.SetAsVoid();
      if (i != 0)
        Mozilla.error(i);
      return localnsIWritableVariant;
    }
    Object localObject1;
    int j;
    if ((paramObject instanceof String))
    {
      localObject1 = (String)paramObject;
      j = ((String)localObject1).length();
      char[] arrayOfChar = new char[j];
      ((String)localObject1).getChars(0, j, arrayOfChar, 0);
      i = localnsIWritableVariant.SetAsWStringWithSize(j, arrayOfChar);
      if (i != 0)
        Mozilla.error(i);
      return localnsIWritableVariant;
    }
    if ((paramObject instanceof Boolean))
    {
      localObject1 = (Boolean)paramObject;
      i = localnsIWritableVariant.SetAsBool(((Boolean)localObject1).booleanValue() ? 1 : 0);
      if (i != 0)
        Mozilla.error(i);
      return localnsIWritableVariant;
    }
    if ((paramObject instanceof Number))
    {
      localObject1 = (Number)paramObject;
      i = localnsIWritableVariant.SetAsDouble(((Number)localObject1).doubleValue());
      if (i != 0)
        Mozilla.error(i);
      return localnsIWritableVariant;
    }
    if ((paramObject instanceof Object[]))
    {
      localObject1 = (Object[])paramObject;
      j = localObject1.length;
      if (j == 0)
      {
        i = localnsIWritableVariant.SetAsEmptyArray();
        if (i != 0)
          Mozilla.error(i);
      }
      else
      {
        int k = C.malloc(C.PTR_SIZEOF * j);
        for (int m = 0; m < j; m++)
        {
          Object localObject2 = localObject1[m];
          try
          {
            nsIVariant localnsIVariant = convertToJS(localObject2, paramnsIComponentManager);
            C.memmove(k + C.PTR_SIZEOF * m, new int[] { localnsIVariant.getAddress() }, C.PTR_SIZEOF);
          }
          catch (SWTException localSWTException)
          {
            C.free(k);
            localnsIWritableVariant.Release();
            for (int n = 0; n < m; n++)
            {
              int[] arrayOfInt2 = new int[1];
              C.memmove(arrayOfInt2, k + C.PTR_SIZEOF * n, C.PTR_SIZEOF);
              new nsISupports(arrayOfInt2[0]).Release();
            }
            throw localSWTException;
          }
        }
        m = C.malloc(16);
        XPCOM.memmove(m, nsIVariant.NS_IVARIANT_IID, 16);
        i = localnsIWritableVariant.SetAsArray((short)19, m, j, k);
        C.free(m);
        C.free(k);
        if (i != 0)
          Mozilla.error(i);
      }
      return localnsIWritableVariant;
    }
    localnsIWritableVariant.Release();
    SWT.error(51);
    return null;
  }

  int callJava(int paramInt1, int paramInt2, int paramInt3)
  {
    Integer localInteger = new Integer(paramInt1);
    BrowserFunction localBrowserFunction = (BrowserFunction)Mozilla.AllFunctions.get(localInteger);
    Object localObject1 = null;
    if (localBrowserFunction != null)
    {
      localObject2 = new short[1];
      nsIVariant localnsIVariant1 = new nsIVariant(paramInt2);
      int j = localnsIVariant1.GetDataType((short[])localObject2);
      if (j != 0)
        Mozilla.error(j);
      try
      {
        Object[] arrayOfObject1 = (Object[])convertToJava(localnsIVariant1, localObject2[0]);
        if ((arrayOfObject1 instanceof Object[]))
        {
          Object[] arrayOfObject2 = (Object[])arrayOfObject1;
          try
          {
            localObject1 = localBrowserFunction.function(arrayOfObject2);
          }
          catch (Exception localException)
          {
            localObject1 = WebBrowser.CreateErrorString(localException.getLocalizedMessage());
          }
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        if (localBrowserFunction.isEvaluate)
          localBrowserFunction.function(new String[] { WebBrowser.CreateErrorString(new SWTException(51).getLocalizedMessage()) });
        localObject1 = WebBrowser.CreateErrorString(localIllegalArgumentException.getLocalizedMessage());
      }
    }
    Object localObject2 = new int[1];
    int i = XPCOM.NS_GetComponentManager((int[])localObject2);
    if (i != 0)
      Mozilla.error(i);
    if (localObject2[0] == 0)
      Mozilla.error(-2147467262);
    nsIComponentManager localnsIComponentManager = new nsIComponentManager(localObject2[0]);
    localObject2[0] = 0;
    nsIVariant localnsIVariant2;
    try
    {
      localnsIVariant2 = convertToJS(localObject1, localnsIComponentManager);
    }
    catch (SWTException localSWTException)
    {
      localnsIVariant2 = convertToJS(WebBrowser.CreateErrorString(localSWTException.getLocalizedMessage()), localnsIComponentManager);
    }
    localnsIComponentManager.Release();
    C.memmove(paramInt3, new int[] { localnsIVariant2.getAddress() }, C.PTR_SIZEOF);
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.External
 * JD-Core Version:    0.6.2
 */