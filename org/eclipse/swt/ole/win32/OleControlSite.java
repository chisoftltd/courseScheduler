package org.eclipse.swt.ole.win32;

import java.io.File;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.CONTROLINFO;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IClassFactory2;
import org.eclipse.swt.internal.ole.win32.IOleControl;
import org.eclipse.swt.internal.ole.win32.IOleInPlaceObject;
import org.eclipse.swt.internal.ole.win32.IPersistStorage;
import org.eclipse.swt.internal.ole.win32.IProvideClassInfo;
import org.eclipse.swt.internal.ole.win32.IProvideClassInfo2;
import org.eclipse.swt.internal.ole.win32.IStorage;
import org.eclipse.swt.internal.ole.win32.ITypeInfo;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.LICINFO;
import org.eclipse.swt.internal.ole.win32.TYPEATTR;
import org.eclipse.swt.internal.win32.GUITHREADINFO;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class OleControlSite extends OleClientSite
{
  private COMObject iOleControlSite;
  private COMObject iDispatch;
  private OlePropertyChangeSink olePropertyChangeSink;
  private OleEventSink[] oleEventSink = new OleEventSink[0];
  private GUID[] oleEventSinkGUID = new GUID[0];
  private int[] oleEventSinkIUnknown = new int[0];
  private CONTROLINFO currentControlInfo;
  private int[] sitePropertyIds = new int[0];
  private Variant[] sitePropertyValues = new Variant[0];
  private Font font;
  static int SWT_RESTORECARET;
  static final String SHELL_PROG_ID = "Shell.Explorer";

  public OleControlSite(Composite paramComposite, int paramInt, File paramFile)
  {
    super(paramComposite, paramInt, paramFile);
    setSiteProperty(-709, new Variant(true));
    setSiteProperty(-710, new Variant(false));
  }

  public OleControlSite(Composite paramComposite, int paramInt, String paramString)
  {
    super(paramComposite, paramInt);
    try
    {
      this.appClsid = getClassID(paramString);
      if (this.appClsid == null)
        OLE.error(1004);
      int i = getLicenseInfo(this.appClsid);
      int[] arrayOfInt;
      int j;
      if (i == 0)
      {
        this.tempStorage = createTempStorage();
        arrayOfInt = new int[1];
        j = COM.OleCreate(this.appClsid, COM.IIDIUnknown, 1, null, this.iOleClientSite.getAddress(), this.tempStorage.getAddress(), arrayOfInt);
        if (j != 0)
          OLE.error(1001, j);
        this.objIUnknown = new IUnknown(arrayOfInt[0]);
      }
      else
      {
        arrayOfInt = new int[1];
        try
        {
          j = COM.CoGetClassObject(this.appClsid, 3, 0, COM.IIDIClassFactory2, arrayOfInt);
          if (j != 0)
            OLE.error(1005, j);
          IClassFactory2 localIClassFactory2 = new IClassFactory2(arrayOfInt[0]);
          arrayOfInt = new int[1];
          j = localIClassFactory2.CreateInstanceLic(0, 0, COM.IIDIUnknown, i, arrayOfInt);
          localIClassFactory2.Release();
          if (j != 0)
            OLE.error(1006, j);
        }
        finally
        {
          COM.SysFreeString(i);
          ret;
        }
        arrayOfInt = new int[1];
        if (this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, arrayOfInt) == 0)
        {
          IPersistStorage localIPersistStorage = new IPersistStorage(arrayOfInt[0]);
          this.tempStorage = createTempStorage();
          localIPersistStorage.InitNew(this.tempStorage.getAddress());
          localIPersistStorage.Release();
        }
      }
      addObjectReferences();
      setSiteProperty(-709, new Variant(true));
      setSiteProperty(-710, new Variant(false));
      if (COM.OleRun(this.objIUnknown.getAddress()) == 0)
        this.state = 1;
    }
    catch (SWTError localSWTError)
    {
      dispose();
      disposeCOMInterfaces();
      throw localSWTError;
    }
  }

  public OleControlSite(Composite paramComposite, int paramInt, String paramString, File paramFile)
  {
    super(paramComposite, paramInt, paramString, paramFile);
    setSiteProperty(-709, new Variant(true));
    setSiteProperty(-710, new Variant(false));
  }

  public void addEventListener(int paramInt, OleListener paramOleListener)
  {
    if (paramOleListener == null)
      OLE.error(4);
    GUID localGUID = getDefaultEventSinkGUID(this.objIUnknown);
    if (localGUID != null)
      addEventListener(this.objIUnknown.getAddress(), localGUID, paramInt, paramOleListener);
  }

  static GUID getDefaultEventSinkGUID(IUnknown paramIUnknown)
  {
    int[] arrayOfInt1 = new int[1];
    Object localObject1;
    Object localObject2;
    if (paramIUnknown.QueryInterface(COM.IIDIProvideClassInfo2, arrayOfInt1) == 0)
    {
      localObject1 = new IProvideClassInfo2(arrayOfInt1[0]);
      localObject2 = new GUID();
      int i = ((IProvideClassInfo2)localObject1).GetGUID(1, (GUID)localObject2);
      ((IProvideClassInfo2)localObject1).Release();
      if (i == 0)
        return localObject2;
    }
    if (paramIUnknown.QueryInterface(COM.IIDIProvideClassInfo, arrayOfInt1) == 0)
    {
      localObject1 = new IProvideClassInfo(arrayOfInt1[0]);
      localObject2 = new int[1];
      int[] arrayOfInt2 = new int[1];
      int j = ((IProvideClassInfo)localObject1).GetClassInfo((int[])localObject2);
      ((IProvideClassInfo)localObject1).Release();
      if ((j == 0) && (localObject2[0] != 0))
      {
        ITypeInfo localITypeInfo = new ITypeInfo(localObject2[0]);
        int[] arrayOfInt3 = new int[1];
        j = localITypeInfo.GetTypeAttr(arrayOfInt3);
        Object localObject3;
        if ((j == 0) && (arrayOfInt3[0] != 0))
        {
          localObject3 = new TYPEATTR();
          COM.MoveMemory((TYPEATTR)localObject3, arrayOfInt3[0], TYPEATTR.sizeof);
          localITypeInfo.ReleaseTypeAttr(arrayOfInt3[0]);
          int k = 7;
          int m = 3;
          for (int n = 0; n < ((TYPEATTR)localObject3).cImplTypes; n++)
          {
            int[] arrayOfInt4 = new int[1];
            if ((localITypeInfo.GetImplTypeFlags(n, arrayOfInt4) == 0) && ((arrayOfInt4[0] & k) == m))
            {
              int[] arrayOfInt5 = new int[1];
              if (localITypeInfo.GetRefTypeOfImplType(n, arrayOfInt5) == 0)
                localITypeInfo.GetRefTypeInfo(arrayOfInt5[0], arrayOfInt2);
            }
          }
        }
        localITypeInfo.Release();
        if (arrayOfInt2[0] != 0)
        {
          localObject3 = new ITypeInfo(arrayOfInt2[0]);
          arrayOfInt3 = new int[1];
          j = ((ITypeInfo)localObject3).GetTypeAttr(arrayOfInt3);
          GUID localGUID = null;
          if ((j == 0) && (arrayOfInt3[0] != 0))
          {
            localGUID = new GUID();
            COM.MoveMemory(localGUID, arrayOfInt3[0], GUID.sizeof);
            ((ITypeInfo)localObject3).ReleaseTypeAttr(arrayOfInt3[0]);
          }
          ((ITypeInfo)localObject3).Release();
          return localGUID;
        }
      }
    }
    return null;
  }

  public void addEventListener(OleAutomation paramOleAutomation, int paramInt, OleListener paramOleListener)
  {
    if ((paramOleListener == null) || (paramOleAutomation == null))
      OLE.error(4);
    int i = paramOleAutomation.getAddress();
    IUnknown localIUnknown = new IUnknown(i);
    GUID localGUID = getDefaultEventSinkGUID(localIUnknown);
    if (localGUID != null)
      addEventListener(i, localGUID, paramInt, paramOleListener);
  }

  public void addEventListener(OleAutomation paramOleAutomation, String paramString, int paramInt, OleListener paramOleListener)
  {
    if ((paramOleListener == null) || (paramOleAutomation == null) || (paramString == null))
      OLE.error(4);
    int i = paramOleAutomation.getAddress();
    if (i == 0)
      return;
    char[] arrayOfChar = (paramString + "").toCharArray();
    GUID localGUID = new GUID();
    if (COM.IIDFromString(arrayOfChar, localGUID) != 0)
      return;
    addEventListener(i, localGUID, paramInt, paramOleListener);
  }

  void addEventListener(int paramInt1, GUID paramGUID, int paramInt2, OleListener paramOleListener)
  {
    if ((paramOleListener == null) || (paramInt1 == 0) || (paramGUID == null))
      OLE.error(4);
    int i = -1;
    for (int j = 0; j < this.oleEventSinkGUID.length; j++)
      if ((COM.IsEqualGUID(this.oleEventSinkGUID[j], paramGUID)) && (paramInt1 == this.oleEventSinkIUnknown[j]))
      {
        i = j;
        break;
      }
    if (i != -1)
    {
      this.oleEventSink[i].addListener(paramInt2, paramOleListener);
    }
    else
    {
      j = this.oleEventSink.length;
      OleEventSink[] arrayOfOleEventSink = new OleEventSink[j + 1];
      GUID[] arrayOfGUID = new GUID[j + 1];
      int[] arrayOfInt = new int[j + 1];
      System.arraycopy(this.oleEventSink, 0, arrayOfOleEventSink, 0, j);
      System.arraycopy(this.oleEventSinkGUID, 0, arrayOfGUID, 0, j);
      System.arraycopy(this.oleEventSinkIUnknown, 0, arrayOfInt, 0, j);
      this.oleEventSink = arrayOfOleEventSink;
      this.oleEventSinkGUID = arrayOfGUID;
      this.oleEventSinkIUnknown = arrayOfInt;
      this.oleEventSink[j] = new OleEventSink(this, paramInt1, paramGUID);
      this.oleEventSinkGUID[j] = paramGUID;
      this.oleEventSinkIUnknown[j] = paramInt1;
      this.oleEventSink[j].AddRef();
      this.oleEventSink[j].connect();
      this.oleEventSink[j].addListener(paramInt2, paramOleListener);
    }
  }

  protected void addObjectReferences()
  {
    super.addObjectReferences();
    connectPropertyChangeSink();
    int[] arrayOfInt = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIOleControl, arrayOfInt) == 0)
    {
      IOleControl localIOleControl = new IOleControl(arrayOfInt[0]);
      this.currentControlInfo = new CONTROLINFO();
      localIOleControl.GetControlInfo(this.currentControlInfo);
      localIOleControl.Release();
    }
  }

  public void addPropertyListener(int paramInt, OleListener paramOleListener)
  {
    if (paramOleListener == null)
      SWT.error(4);
    this.olePropertyChangeSink.addListener(paramInt, paramOleListener);
  }

  private void connectPropertyChangeSink()
  {
    this.olePropertyChangeSink = new OlePropertyChangeSink(this);
    this.olePropertyChangeSink.AddRef();
    this.olePropertyChangeSink.connect(this.objIUnknown);
  }

  protected void createCOMInterfaces()
  {
    super.createCOMInterfaces();
    this.iOleControlSite = new COMObject(new int[] { 2, 0, 0, 0, 1, 1, 3, 2, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.OnControlInfoChanged();
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.OnFocus(paramAnonymousArrayOfInt[0]);
      }
    };
    this.iDispatch = new COMObject(new int[] { 2, 0, 0, 1, 3, 5, 8 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.Release();
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return OleControlSite.this.Invoke(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }
    };
  }

  private void disconnectEventSinks()
  {
    for (int i = 0; i < this.oleEventSink.length; i++)
    {
      OleEventSink localOleEventSink = this.oleEventSink[i];
      localOleEventSink.disconnect();
      localOleEventSink.Release();
    }
    this.oleEventSink = new OleEventSink[0];
    this.oleEventSinkGUID = new GUID[0];
    this.oleEventSinkIUnknown = new int[0];
  }

  private void disconnectPropertyChangeSink()
  {
    if (this.olePropertyChangeSink != null)
    {
      this.olePropertyChangeSink.disconnect(this.objIUnknown);
      this.olePropertyChangeSink.Release();
    }
    this.olePropertyChangeSink = null;
  }

  protected void disposeCOMInterfaces()
  {
    super.disposeCOMInterfaces();
    if (this.iOleControlSite != null)
      this.iOleControlSite.dispose();
    this.iOleControlSite = null;
    if (this.iDispatch != null)
      this.iDispatch.dispose();
    this.iDispatch = null;
  }

  public Color getBackground()
  {
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation = new OleAutomation(this);
      Variant localVariant = localOleAutomation.getProperty(-501);
      localOleAutomation.dispose();
      if (localVariant != null)
      {
        int[] arrayOfInt = new int[1];
        if (COM.OleTranslateColor(localVariant.getInt(), getDisplay().hPalette, arrayOfInt) == 0)
          return Color.win32_new(getDisplay(), arrayOfInt[0]);
      }
    }
    return super.getBackground();
  }

  public Font getFont()
  {
    if ((this.font != null) && (!this.font.isDisposed()))
      return this.font;
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation1 = new OleAutomation(this);
      Variant localVariant1 = localOleAutomation1.getProperty(-512);
      localOleAutomation1.dispose();
      if (localVariant1 != null)
      {
        OleAutomation localOleAutomation2 = localVariant1.getAutomation();
        Variant localVariant2 = localOleAutomation2.getProperty(0);
        Variant localVariant3 = localOleAutomation2.getProperty(2);
        Variant localVariant4 = localOleAutomation2.getProperty(4);
        Variant localVariant5 = localOleAutomation2.getProperty(3);
        localOleAutomation2.dispose();
        if ((localVariant2 != null) && (localVariant3 != null) && (localVariant4 != null) && (localVariant5 != null))
        {
          int i = 3 * localVariant5.getInt() + 2 * localVariant4.getInt();
          this.font = new Font(getShell().getDisplay(), localVariant2.getString(), localVariant3.getInt(), i);
          return this.font;
        }
      }
    }
    return super.getFont();
  }

  public Color getForeground()
  {
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation = new OleAutomation(this);
      Variant localVariant = localOleAutomation.getProperty(-513);
      localOleAutomation.dispose();
      if (localVariant != null)
      {
        int[] arrayOfInt = new int[1];
        if (COM.OleTranslateColor(localVariant.getInt(), getDisplay().hPalette, arrayOfInt) == 0)
          return Color.win32_new(getDisplay(), arrayOfInt[0]);
      }
    }
    return super.getForeground();
  }

  protected int getLicenseInfo(GUID paramGUID)
  {
    int[] arrayOfInt1 = new int[1];
    if (COM.CoGetClassObject(paramGUID, 3, 0, COM.IIDIClassFactory, arrayOfInt1) != 0)
      return 0;
    int i = 0;
    IUnknown localIUnknown = new IUnknown(arrayOfInt1[0]);
    if (localIUnknown.QueryInterface(COM.IIDIClassFactory2, arrayOfInt1) == 0)
    {
      IClassFactory2 localIClassFactory2 = new IClassFactory2(arrayOfInt1[0]);
      LICINFO localLICINFO = new LICINFO();
      if (localIClassFactory2.GetLicInfo(localLICINFO) == 0)
      {
        int[] arrayOfInt2 = new int[1];
        if ((localLICINFO != null) && (localLICINFO.fRuntimeKeyAvail) && (localIClassFactory2.RequestLicKey(0, arrayOfInt2) == 0))
          i = arrayOfInt2[0];
      }
      localIClassFactory2.Release();
    }
    localIUnknown.Release();
    return i;
  }

  public Variant getSiteProperty(int paramInt)
  {
    for (int i = 0; i < this.sitePropertyIds.length; i++)
      if (this.sitePropertyIds[i] == paramInt)
        return this.sitePropertyValues[i];
    return null;
  }

  protected int GetWindow(int paramInt)
  {
    if (paramInt == 0)
      return -2147024809;
    if (this.frame == null)
    {
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
      return -2147467263;
    }
    COM.MoveMemory(paramInt, new int[] { this.handle }, OS.PTR_SIZEOF);
    return 0;
  }

  private int Invoke(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    if ((paramInt6 == 0) || (paramInt4 != 2))
    {
      if (paramInt7 != 0)
        COM.MoveMemory(paramInt7, new int[1], OS.PTR_SIZEOF);
      if (paramInt8 != 0)
        COM.MoveMemory(paramInt8, new int[1], 4);
      return -2147352573;
    }
    Variant localVariant = getSiteProperty(paramInt1);
    if (localVariant != null)
    {
      if (paramInt6 != 0)
        localVariant.getData(paramInt6);
      return 0;
    }
    switch (paramInt1)
    {
    case -714:
    case -712:
    case -711:
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], OS.PTR_SIZEOF);
      if (paramInt7 != 0)
        COM.MoveMemory(paramInt7, new int[1], OS.PTR_SIZEOF);
      if (paramInt8 != 0)
        COM.MoveMemory(paramInt8, new int[1], 4);
      return 1;
    case -5502:
    case -5501:
    case -706:
    case -705:
    case -704:
    case -703:
    case -701:
      if (paramInt6 != 0)
        COM.MoveMemory(paramInt6, new int[1], OS.PTR_SIZEOF);
      if (paramInt7 != 0)
        COM.MoveMemory(paramInt7, new int[1], OS.PTR_SIZEOF);
      if (paramInt8 != 0)
        COM.MoveMemory(paramInt8, new int[1], 4);
      return -2147467263;
    }
    if (paramInt6 != 0)
      COM.MoveMemory(paramInt6, new int[1], OS.PTR_SIZEOF);
    if (paramInt7 != 0)
      COM.MoveMemory(paramInt7, new int[1], OS.PTR_SIZEOF);
    if (paramInt8 != 0)
      COM.MoveMemory(paramInt8, new int[1], 4);
    return -2147352573;
  }

  private int OnControlInfoChanged()
  {
    int[] arrayOfInt = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIOleControl, arrayOfInt) == 0)
    {
      IOleControl localIOleControl = new IOleControl(arrayOfInt[0]);
      this.currentControlInfo = new CONTROLINFO();
      localIOleControl.GetControlInfo(this.currentControlInfo);
      localIOleControl.Release();
    }
    return 0;
  }

  protected int OnUIDeactivate(int paramInt)
  {
    return super.OnUIDeactivate(paramInt);
  }

  void onFocusIn(Event paramEvent)
  {
    String str = getProgramID();
    if (str == null)
      return;
    if (!str.startsWith("Shell.Explorer"))
    {
      super.onFocusIn(paramEvent);
      return;
    }
    if (this.objIOleInPlaceObject == null)
      return;
    if (!this.isActivated)
      doVerb(-4);
    if (isFocusControl())
      return;
    int[] arrayOfInt = new int[1];
    this.objIOleInPlaceObject.GetWindow(arrayOfInt);
    if (arrayOfInt[0] == 0)
      return;
    OS.SetFocus(arrayOfInt[0]);
  }

  void onFocusOut(Event paramEvent)
  {
    if (this.objIOleInPlaceObject == null)
      return;
    String str = getProgramID();
    if (str == null)
      return;
    if (!str.startsWith("Shell.Explorer"))
    {
      super.onFocusOut(paramEvent);
      return;
    }
    int i = OS.GetCurrentThreadId();
    GUITHREADINFO localGUITHREADINFO1 = new GUITHREADINFO();
    localGUITHREADINFO1.cbSize = GUITHREADINFO.sizeof;
    OS.GetGUIThreadInfo(i, localGUITHREADINFO1);
    this.objIOleInPlaceObject.UIDeactivate();
    if (localGUITHREADINFO1.hwndCaret != 0)
    {
      GUITHREADINFO localGUITHREADINFO2 = new GUITHREADINFO();
      localGUITHREADINFO2.cbSize = GUITHREADINFO.sizeof;
      OS.GetGUIThreadInfo(i, localGUITHREADINFO2);
      if ((localGUITHREADINFO2.hwndCaret == 0) && (localGUITHREADINFO1.hwndCaret == OS.GetFocus()))
      {
        if (SWT_RESTORECARET == 0)
          SWT_RESTORECARET = OS.RegisterWindowMessage(new TCHAR(0, "SWT_RESTORECARET", true));
        if (OS.SendMessage(localGUITHREADINFO1.hwndCaret, SWT_RESTORECARET, 0, 0) == 0)
        {
          int j = localGUITHREADINFO1.right - localGUITHREADINFO1.left;
          int k = localGUITHREADINFO1.bottom - localGUITHREADINFO1.top;
          OS.CreateCaret(localGUITHREADINFO1.hwndCaret, 0, j, k);
          OS.SetCaretPos(localGUITHREADINFO1.left, localGUITHREADINFO1.top);
          OS.ShowCaret(localGUITHREADINFO1.hwndCaret);
        }
      }
    }
  }

  private int OnFocus(int paramInt)
  {
    return 0;
  }

  protected int QueryInterface(int paramInt1, int paramInt2)
  {
    int i = super.QueryInterface(paramInt1, paramInt2);
    if (i == 0)
      return i;
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147024809;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIOleControlSite))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iOleControlSite.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIDispatch))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iDispatch.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  protected int Release()
  {
    int i = super.Release();
    if (i == 0)
    {
      for (int j = 0; j < this.sitePropertyIds.length; j++)
        this.sitePropertyValues[j].dispose();
      this.sitePropertyIds = new int[0];
      this.sitePropertyValues = new Variant[0];
    }
    return i;
  }

  protected void releaseObjectInterfaces()
  {
    disconnectEventSinks();
    disconnectPropertyChangeSink();
    super.releaseObjectInterfaces();
  }

  public void removeEventListener(int paramInt, OleListener paramOleListener)
  {
    checkWidget();
    if (paramOleListener == null)
      SWT.error(4);
    GUID localGUID = getDefaultEventSinkGUID(this.objIUnknown);
    if (localGUID != null)
      removeEventListener(this.objIUnknown.getAddress(), localGUID, paramInt, paramOleListener);
  }

  /** @deprecated */
  public void removeEventListener(OleAutomation paramOleAutomation, GUID paramGUID, int paramInt, OleListener paramOleListener)
  {
    checkWidget();
    if ((paramOleAutomation == null) || (paramOleListener == null) || (paramGUID == null))
      SWT.error(4);
    removeEventListener(paramOleAutomation.getAddress(), paramGUID, paramInt, paramOleListener);
  }

  public void removeEventListener(OleAutomation paramOleAutomation, int paramInt, OleListener paramOleListener)
  {
    checkWidget();
    if ((paramOleAutomation == null) || (paramOleListener == null))
      SWT.error(4);
    int i = paramOleAutomation.getAddress();
    IUnknown localIUnknown = new IUnknown(i);
    GUID localGUID = getDefaultEventSinkGUID(localIUnknown);
    if (localGUID != null)
      removeEventListener(i, localGUID, paramInt, paramOleListener);
  }

  void removeEventListener(int paramInt1, GUID paramGUID, int paramInt2, OleListener paramOleListener)
  {
    if ((paramOleListener == null) || (paramGUID == null))
      SWT.error(4);
    for (int i = 0; i < this.oleEventSink.length; i++)
      if ((COM.IsEqualGUID(this.oleEventSinkGUID[i], paramGUID)) && (paramInt1 == this.oleEventSinkIUnknown[i]))
      {
        this.oleEventSink[i].removeListener(paramInt2, paramOleListener);
        if (!this.oleEventSink[i].hasListeners())
        {
          this.oleEventSink[i].disconnect();
          this.oleEventSink[i].Release();
          int j = this.oleEventSink.length;
          if (j == 1)
          {
            this.oleEventSink = new OleEventSink[0];
            this.oleEventSinkGUID = new GUID[0];
            this.oleEventSinkIUnknown = new int[0];
          }
          else
          {
            OleEventSink[] arrayOfOleEventSink = new OleEventSink[j - 1];
            System.arraycopy(this.oleEventSink, 0, arrayOfOleEventSink, 0, i);
            System.arraycopy(this.oleEventSink, i + 1, arrayOfOleEventSink, i, j - i - 1);
            this.oleEventSink = arrayOfOleEventSink;
            GUID[] arrayOfGUID = new GUID[j - 1];
            System.arraycopy(this.oleEventSinkGUID, 0, arrayOfGUID, 0, i);
            System.arraycopy(this.oleEventSinkGUID, i + 1, arrayOfGUID, i, j - i - 1);
            this.oleEventSinkGUID = arrayOfGUID;
            int[] arrayOfInt = new int[j - 1];
            System.arraycopy(this.oleEventSinkIUnknown, 0, arrayOfInt, 0, i);
            System.arraycopy(this.oleEventSinkIUnknown, i + 1, arrayOfInt, i, j - i - 1);
            this.oleEventSinkIUnknown = arrayOfInt;
          }
        }
        return;
      }
  }

  public void removePropertyListener(int paramInt, OleListener paramOleListener)
  {
    if (paramOleListener == null)
      SWT.error(4);
    this.olePropertyChangeSink.removeListener(paramInt, paramOleListener);
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation = new OleAutomation(this);
      localOleAutomation.setProperty(-501, new Variant(paramColor.handle));
      localOleAutomation.dispose();
    }
  }

  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation1 = new OleAutomation(this);
      Variant localVariant = localOleAutomation1.getProperty(-512);
      localOleAutomation1.dispose();
      if (localVariant != null)
      {
        OleAutomation localOleAutomation2 = localVariant.getAutomation();
        FontData[] arrayOfFontData = paramFont.getFontData();
        localOleAutomation2.setProperty(0, new Variant(arrayOfFontData[0].getName()));
        localOleAutomation2.setProperty(2, new Variant(arrayOfFontData[0].getHeight()));
        localOleAutomation2.setProperty(4, new Variant(arrayOfFontData[0].getStyle() & 0x2));
        localOleAutomation2.setProperty(3, new Variant(arrayOfFontData[0].getStyle() & 0x1));
        localOleAutomation2.dispose();
      }
    }
    this.font = paramFont;
  }

  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    if (this.objIUnknown != null)
    {
      OleAutomation localOleAutomation = new OleAutomation(this);
      localOleAutomation.setProperty(-513, new Variant(paramColor.handle));
      localOleAutomation.dispose();
    }
  }

  public void setSiteProperty(int paramInt, Variant paramVariant)
  {
    for (int i = 0; i < this.sitePropertyIds.length; i++)
      if (this.sitePropertyIds[i] == paramInt)
      {
        if (this.sitePropertyValues[i] != null)
          this.sitePropertyValues[i].dispose();
        if (paramVariant != null)
        {
          this.sitePropertyValues[i] = paramVariant;
        }
        else
        {
          int j = this.sitePropertyIds.length;
          localObject = new int[j - 1];
          Variant[] arrayOfVariant = new Variant[j - 1];
          System.arraycopy(this.sitePropertyIds, 0, localObject, 0, i);
          System.arraycopy(this.sitePropertyIds, i + 1, localObject, i, j - i - 1);
          System.arraycopy(this.sitePropertyValues, 0, arrayOfVariant, 0, i);
          System.arraycopy(this.sitePropertyValues, i + 1, arrayOfVariant, i, j - i - 1);
          this.sitePropertyIds = ((int[])localObject);
          this.sitePropertyValues = arrayOfVariant;
        }
        return;
      }
    i = this.sitePropertyIds.length;
    int[] arrayOfInt = new int[i + 1];
    Object localObject = new Variant[i + 1];
    System.arraycopy(this.sitePropertyIds, 0, arrayOfInt, 0, i);
    System.arraycopy(this.sitePropertyValues, 0, localObject, 0, i);
    arrayOfInt[i] = paramInt;
    localObject[i] = paramVariant;
    this.sitePropertyIds = arrayOfInt;
    this.sitePropertyValues = ((Variant[])localObject);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleControlSite
 * JD-Core Version:    0.6.2
 */