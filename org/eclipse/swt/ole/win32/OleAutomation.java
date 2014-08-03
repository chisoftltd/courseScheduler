package org.eclipse.swt.ole.win32;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.DISPPARAMS;
import org.eclipse.swt.internal.ole.win32.EXCEPINFO;
import org.eclipse.swt.internal.ole.win32.FUNCDESC;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.ITypeInfo;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.ole.win32.VARDESC;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;

public final class OleAutomation
{
  private IUnknown objIUnknown;
  private IDispatch objIDispatch;
  private String exceptionDescription;
  private ITypeInfo objITypeInfo;

  OleAutomation(IDispatch paramIDispatch)
  {
    if (paramIDispatch == null)
      OLE.error(1011);
    this.objIDispatch = paramIDispatch;
    this.objIDispatch.AddRef();
    int[] arrayOfInt = new int[1];
    int i = this.objIDispatch.GetTypeInfo(0, 2048, arrayOfInt);
    if (i == 0)
      this.objITypeInfo = new ITypeInfo(arrayOfInt[0]);
  }

  public OleAutomation(OleClientSite paramOleClientSite)
  {
    if (paramOleClientSite == null)
      OLE.error(1011);
    this.objIDispatch = paramOleClientSite.getAutomationObject();
    int[] arrayOfInt = new int[1];
    int i = this.objIDispatch.GetTypeInfo(0, 2048, arrayOfInt);
    if (i == 0)
      this.objITypeInfo = new ITypeInfo(arrayOfInt[0]);
  }

  public OleAutomation(String paramString)
  {
    try
    {
      OS.OleInitialize(0);
      GUID localGUID = getClassID(paramString);
      if (localGUID == null)
      {
        OS.OleUninitialize();
        OLE.error(1004);
      }
      int[] arrayOfInt = new int[1];
      int i = COM.CoCreateInstance(localGUID, 0, 1, COM.IIDIUnknown, arrayOfInt);
      if (i != 0)
      {
        OS.OleUninitialize();
        OLE.error(1001, i);
      }
      this.objIUnknown = new IUnknown(arrayOfInt[0]);
      arrayOfInt[0] = 0;
      i = this.objIUnknown.QueryInterface(COM.IIDIDispatch, arrayOfInt);
      if (i != 0)
        OLE.error(1003);
      this.objIDispatch = new IDispatch(arrayOfInt[0]);
      arrayOfInt[0] = 0;
      i = this.objIDispatch.GetTypeInfo(0, 2048, arrayOfInt);
      if (i == 0)
        this.objITypeInfo = new ITypeInfo(arrayOfInt[0]);
    }
    catch (SWTException localSWTException)
    {
      dispose();
      throw localSWTException;
    }
  }

  public void dispose()
  {
    if (this.objIDispatch != null)
      this.objIDispatch.Release();
    this.objIDispatch = null;
    if (this.objITypeInfo != null)
      this.objITypeInfo.Release();
    this.objITypeInfo = null;
    if (this.objIUnknown != null)
    {
      this.objIUnknown.Release();
      OS.OleUninitialize();
    }
    this.objIUnknown = null;
  }

  int getAddress()
  {
    return this.objIDispatch.getAddress();
  }

  GUID getClassID(String paramString)
  {
    GUID localGUID = new GUID();
    char[] arrayOfChar = (char[])null;
    int i;
    if (paramString != null)
    {
      i = paramString.length();
      arrayOfChar = new char[i + 1];
      paramString.getChars(0, i, arrayOfChar, 0);
    }
    if (COM.CLSIDFromProgID(arrayOfChar, localGUID) != 0)
    {
      i = COM.CLSIDFromString(arrayOfChar, localGUID);
      if (i != 0)
        return null;
    }
    return localGUID;
  }

  public String getHelpFile(int paramInt)
  {
    if (this.objITypeInfo == null)
      return null;
    String[] arrayOfString = new String[1];
    int i = this.objITypeInfo.GetDocumentation(paramInt, null, null, null, arrayOfString);
    if (i == 0)
      return arrayOfString[0];
    return null;
  }

  public String getDocumentation(int paramInt)
  {
    if (this.objITypeInfo == null)
      return null;
    String[] arrayOfString = new String[1];
    int i = this.objITypeInfo.GetDocumentation(paramInt, null, arrayOfString, null, null);
    if (i == 0)
      return arrayOfString[0];
    return null;
  }

  public OlePropertyDescription getPropertyDescription(int paramInt)
  {
    if (this.objITypeInfo == null)
      return null;
    int[] arrayOfInt = new int[1];
    int i = this.objITypeInfo.GetVarDesc(paramInt, arrayOfInt);
    if (i != 0)
      return null;
    VARDESC localVARDESC = new VARDESC();
    COM.MoveMemory(localVARDESC, arrayOfInt[0], VARDESC.sizeof);
    OlePropertyDescription localOlePropertyDescription = new OlePropertyDescription();
    localOlePropertyDescription.id = localVARDESC.memid;
    localOlePropertyDescription.name = getName(localVARDESC.memid);
    localOlePropertyDescription.type = localVARDESC.elemdescVar_tdesc_vt;
    if (localOlePropertyDescription.type == 26)
    {
      short[] arrayOfShort = new short[1];
      COM.MoveMemory(arrayOfShort, localVARDESC.elemdescVar_tdesc_union + OS.PTR_SIZEOF, 2);
      localOlePropertyDescription.type = arrayOfShort[0];
    }
    localOlePropertyDescription.flags = localVARDESC.wVarFlags;
    localOlePropertyDescription.kind = localVARDESC.varkind;
    localOlePropertyDescription.description = getDocumentation(localVARDESC.memid);
    localOlePropertyDescription.helpFile = getHelpFile(localVARDESC.memid);
    this.objITypeInfo.ReleaseVarDesc(arrayOfInt[0]);
    return localOlePropertyDescription;
  }

  public OleFunctionDescription getFunctionDescription(int paramInt)
  {
    if (this.objITypeInfo == null)
      return null;
    int[] arrayOfInt = new int[1];
    int i = this.objITypeInfo.GetFuncDesc(paramInt, arrayOfInt);
    if (i != 0)
      return null;
    FUNCDESC localFUNCDESC = new FUNCDESC();
    COM.MoveMemory(localFUNCDESC, arrayOfInt[0], FUNCDESC.sizeof);
    OleFunctionDescription localOleFunctionDescription = new OleFunctionDescription();
    localOleFunctionDescription.id = localFUNCDESC.memid;
    localOleFunctionDescription.optionalArgCount = localFUNCDESC.cParamsOpt;
    localOleFunctionDescription.invokeKind = localFUNCDESC.invkind;
    localOleFunctionDescription.funcKind = localFUNCDESC.funckind;
    localOleFunctionDescription.flags = localFUNCDESC.wFuncFlags;
    localOleFunctionDescription.callingConvention = localFUNCDESC.callconv;
    localOleFunctionDescription.documentation = getDocumentation(localFUNCDESC.memid);
    localOleFunctionDescription.helpFile = getHelpFile(localFUNCDESC.memid);
    String[] arrayOfString = getNames(localFUNCDESC.memid, localFUNCDESC.cParams + 1);
    if (arrayOfString.length > 0)
      localOleFunctionDescription.name = arrayOfString[0];
    localOleFunctionDescription.args = new OleParameterDescription[localFUNCDESC.cParams];
    for (int j = 0; j < localOleFunctionDescription.args.length; j++)
    {
      localOleFunctionDescription.args[j] = new OleParameterDescription();
      if (arrayOfString.length > j + 1)
        localOleFunctionDescription.args[j].name = arrayOfString[(j + 1)];
      short[] arrayOfShort2 = new short[1];
      COM.MoveMemory(arrayOfShort2, localFUNCDESC.lprgelemdescParam + j * COM.ELEMDESC_sizeof() + OS.PTR_SIZEOF, 2);
      if (arrayOfShort2[0] == 26)
      {
        localObject = new int[1];
        COM.MoveMemory((int[])localObject, localFUNCDESC.lprgelemdescParam + j * COM.ELEMDESC_sizeof(), OS.PTR_SIZEOF);
        short[] arrayOfShort3 = new short[1];
        COM.MoveMemory(arrayOfShort3, localObject[0] + OS.PTR_SIZEOF, 2);
        arrayOfShort2[0] = ((short)(arrayOfShort3[0] | 0x4000));
      }
      localOleFunctionDescription.args[j].type = arrayOfShort2[0];
      Object localObject = new short[1];
      COM.MoveMemory((short[])localObject, localFUNCDESC.lprgelemdescParam + j * COM.ELEMDESC_sizeof() + COM.TYPEDESC_sizeof() + OS.PTR_SIZEOF, 2);
      localOleFunctionDescription.args[j].flags = localObject[0];
    }
    localOleFunctionDescription.returnType = localFUNCDESC.elemdescFunc_tdesc_vt;
    if (localOleFunctionDescription.returnType == 26)
    {
      short[] arrayOfShort1 = new short[1];
      COM.MoveMemory(arrayOfShort1, localFUNCDESC.elemdescFunc_tdesc_union + OS.PTR_SIZEOF, 2);
      localOleFunctionDescription.returnType = arrayOfShort1[0];
    }
    this.objITypeInfo.ReleaseFuncDesc(arrayOfInt[0]);
    return localOleFunctionDescription;
  }

  public TYPEATTR getTypeInfoAttributes()
  {
    if (this.objITypeInfo == null)
      return null;
    int[] arrayOfInt = new int[1];
    int i = this.objITypeInfo.GetTypeAttr(arrayOfInt);
    if (i != 0)
      return null;
    TYPEATTR localTYPEATTR = new TYPEATTR();
    COM.MoveMemory(localTYPEATTR, arrayOfInt[0], TYPEATTR.sizeof);
    this.objITypeInfo.ReleaseTypeAttr(arrayOfInt[0]);
    return localTYPEATTR;
  }

  public String getName(int paramInt)
  {
    if (this.objITypeInfo == null)
      return null;
    String[] arrayOfString = new String[1];
    int i = this.objITypeInfo.GetDocumentation(paramInt, arrayOfString, null, null, null);
    if (i == 0)
      return arrayOfString[0];
    return null;
  }

  public String[] getNames(int paramInt1, int paramInt2)
  {
    if (this.objITypeInfo == null)
      return new String[0];
    String[] arrayOfString1 = new String[paramInt2];
    int[] arrayOfInt = new int[1];
    int i = this.objITypeInfo.GetNames(paramInt1, arrayOfString1, paramInt2, arrayOfInt);
    if (i == 0)
    {
      String[] arrayOfString2 = new String[arrayOfInt[0]];
      System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfInt[0]);
      return arrayOfString2;
    }
    return new String[0];
  }

  public int[] getIDsOfNames(String[] paramArrayOfString)
  {
    int[] arrayOfInt = new int[paramArrayOfString.length];
    int i = this.objIDispatch.GetIDsOfNames(new GUID(), paramArrayOfString, paramArrayOfString.length, 2048, arrayOfInt);
    if (i != 0)
      return null;
    return arrayOfInt;
  }

  public String getLastError()
  {
    return this.exceptionDescription;
  }

  public Variant getProperty(int paramInt)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 2, null, null, localVariant);
    return i == 0 ? localVariant : null;
  }

  public Variant getProperty(int paramInt, Variant[] paramArrayOfVariant)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 2, paramArrayOfVariant, null, localVariant);
    return i == 0 ? localVariant : null;
  }

  public Variant getProperty(int paramInt, Variant[] paramArrayOfVariant, int[] paramArrayOfInt)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 2, paramArrayOfVariant, paramArrayOfInt, localVariant);
    return i == 0 ? localVariant : null;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if ((paramObject instanceof OleAutomation))
    {
      if (this.objIDispatch == null)
        return false;
      OleAutomation localOleAutomation = (OleAutomation)paramObject;
      if (localOleAutomation.objIDispatch == null)
        return false;
      int i = this.objIDispatch.getAddress();
      int j = localOleAutomation.objIDispatch.getAddress();
      return i == j;
    }
    return false;
  }

  public Variant invoke(int paramInt)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 1, null, null, localVariant);
    return i == 0 ? localVariant : null;
  }

  public Variant invoke(int paramInt, Variant[] paramArrayOfVariant)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 1, paramArrayOfVariant, null, localVariant);
    return i == 0 ? localVariant : null;
  }

  public Variant invoke(int paramInt, Variant[] paramArrayOfVariant, int[] paramArrayOfInt)
  {
    Variant localVariant = new Variant();
    int i = invoke(paramInt, 1, paramArrayOfVariant, paramArrayOfInt, localVariant);
    return i == 0 ? localVariant : null;
  }

  private int invoke(int paramInt1, int paramInt2, Variant[] paramArrayOfVariant, int[] paramArrayOfInt, Variant paramVariant)
  {
    if (this.objIDispatch == null)
      return -2147467259;
    DISPPARAMS localDISPPARAMS = new DISPPARAMS();
    int i;
    int j;
    if ((paramArrayOfVariant != null) && (paramArrayOfVariant.length > 0))
    {
      localDISPPARAMS.cArgs = paramArrayOfVariant.length;
      localDISPPARAMS.rgvarg = OS.GlobalAlloc(64, VARIANT.sizeof * paramArrayOfVariant.length);
      i = 0;
      for (j = paramArrayOfVariant.length - 1; j >= 0; j--)
      {
        paramArrayOfVariant[j].getData(localDISPPARAMS.rgvarg + i);
        i += VARIANT.sizeof;
      }
    }
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length > 0))
    {
      localDISPPARAMS.cNamedArgs = paramArrayOfInt.length;
      localDISPPARAMS.rgdispidNamedArgs = OS.GlobalAlloc(64, 4 * paramArrayOfInt.length);
      i = 0;
      for (j = paramArrayOfInt.length; j > 0; j--)
      {
        COM.MoveMemory(localDISPPARAMS.rgdispidNamedArgs + i, new int[] { paramArrayOfInt[(j - 1)] }, 4);
        i += 4;
      }
    }
    EXCEPINFO localEXCEPINFO = new EXCEPINFO();
    int[] arrayOfInt = new int[1];
    int k = 0;
    if (paramVariant != null)
      k = OS.GlobalAlloc(64, VARIANT.sizeof);
    int m = this.objIDispatch.Invoke(paramInt1, new GUID(), 2048, paramInt2, localDISPPARAMS, k, localEXCEPINFO, arrayOfInt);
    if (k != 0)
    {
      paramVariant.setData(k);
      COM.VariantClear(k);
      OS.GlobalFree(k);
    }
    if (localDISPPARAMS.rgdispidNamedArgs != 0)
      OS.GlobalFree(localDISPPARAMS.rgdispidNamedArgs);
    if (localDISPPARAMS.rgvarg != 0)
    {
      int n = 0;
      int i1 = 0;
      int i2 = paramArrayOfVariant.length;
      while (i1 < i2)
      {
        COM.VariantClear(localDISPPARAMS.rgvarg + n);
        n += VARIANT.sizeof;
        i1++;
      }
      OS.GlobalFree(localDISPPARAMS.rgvarg);
    }
    manageExcepinfo(m, localEXCEPINFO);
    return m;
  }

  public void invokeNoReply(int paramInt)
  {
    int i = invoke(paramInt, 1, null, null, null);
    if (i != 0)
      OLE.error(1014, i);
  }

  public void invokeNoReply(int paramInt, Variant[] paramArrayOfVariant)
  {
    int i = invoke(paramInt, 1, paramArrayOfVariant, null, null);
    if (i != 0)
      OLE.error(1014, i);
  }

  public void invokeNoReply(int paramInt, Variant[] paramArrayOfVariant, int[] paramArrayOfInt)
  {
    int i = invoke(paramInt, 1, paramArrayOfVariant, paramArrayOfInt, null);
    if (i != 0)
      OLE.error(1014, i);
  }

  private void manageExcepinfo(int paramInt, EXCEPINFO paramEXCEPINFO)
  {
    if (paramInt == 0)
    {
      this.exceptionDescription = "No Error";
      return;
    }
    if (paramInt == -2147352567)
    {
      if (paramEXCEPINFO.bstrDescription != 0)
      {
        int i = COM.SysStringByteLen(paramEXCEPINFO.bstrDescription);
        char[] arrayOfChar = new char[(i + 1) / 2];
        COM.MoveMemory(arrayOfChar, paramEXCEPINFO.bstrDescription, i);
        this.exceptionDescription = new String(arrayOfChar);
      }
      else
      {
        this.exceptionDescription = "OLE Automation Error Exception ";
        if (paramEXCEPINFO.wCode != 0)
          this.exceptionDescription = (this.exceptionDescription + "code = " + paramEXCEPINFO.wCode);
        else if (paramEXCEPINFO.scode != 0)
          this.exceptionDescription = (this.exceptionDescription + "code = " + paramEXCEPINFO.scode);
      }
    }
    else
      this.exceptionDescription = ("OLE Automation Error HResult : " + paramInt);
    if (paramEXCEPINFO.bstrDescription != 0)
      COM.SysFreeString(paramEXCEPINFO.bstrDescription);
    if (paramEXCEPINFO.bstrHelpFile != 0)
      COM.SysFreeString(paramEXCEPINFO.bstrHelpFile);
    if (paramEXCEPINFO.bstrSource != 0)
      COM.SysFreeString(paramEXCEPINFO.bstrSource);
  }

  public boolean setProperty(int paramInt, Variant paramVariant)
  {
    Variant[] arrayOfVariant = { paramVariant };
    int[] arrayOfInt = { -3 };
    int i = 4;
    if ((paramVariant.getType() & 0x4000) == 16384)
      i = 8;
    Variant localVariant = new Variant();
    int j = invoke(paramInt, i, arrayOfVariant, arrayOfInt, localVariant);
    return j == 0;
  }

  public boolean setProperty(int paramInt, Variant[] paramArrayOfVariant)
  {
    int[] arrayOfInt = { -3 };
    int i = 4;
    for (int j = 0; j < paramArrayOfVariant.length; j++)
      if ((paramArrayOfVariant[j].getType() & 0x4000) == 16384)
        i = 8;
    Variant localVariant = new Variant();
    int k = invoke(paramInt, i, paramArrayOfVariant, arrayOfInt, localVariant);
    return k == 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleAutomation
 * JD-Core Version:    0.6.2
 */