package org.eclipse.swt.ole.win32;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.ole.win32.CAUUID;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IDispatch;
import org.eclipse.swt.internal.ole.win32.IMoniker;
import org.eclipse.swt.internal.ole.win32.IOleCommandTarget;
import org.eclipse.swt.internal.ole.win32.IOleDocument;
import org.eclipse.swt.internal.ole.win32.IOleDocumentView;
import org.eclipse.swt.internal.ole.win32.IOleInPlaceObject;
import org.eclipse.swt.internal.ole.win32.IOleLink;
import org.eclipse.swt.internal.ole.win32.IOleObject;
import org.eclipse.swt.internal.ole.win32.IPersist;
import org.eclipse.swt.internal.ole.win32.IPersistFile;
import org.eclipse.swt.internal.ole.win32.IPersistStorage;
import org.eclipse.swt.internal.ole.win32.ISpecifyPropertyPages;
import org.eclipse.swt.internal.ole.win32.IStorage;
import org.eclipse.swt.internal.ole.win32.IStream;
import org.eclipse.swt.internal.ole.win32.IUnknown;
import org.eclipse.swt.internal.ole.win32.IViewObject2;
import org.eclipse.swt.internal.ole.win32.OLECMD;
import org.eclipse.swt.internal.ole.win32.OLEINPLACEFRAMEINFO;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;

public class OleClientSite extends Composite
{
  private COMObject iUnknown;
  COMObject iOleClientSite;
  private COMObject iAdviseSink;
  private COMObject iOleInPlaceSite;
  private COMObject iOleDocumentSite;
  protected GUID appClsid;
  private GUID objClsid;
  private int refCount;
  protected OleFrame frame;
  protected IUnknown objIUnknown;
  protected IOleObject objIOleObject;
  protected IViewObject2 objIViewObject2;
  protected IOleInPlaceObject objIOleInPlaceObject;
  protected IOleCommandTarget objIOleCommandTarget;
  protected IOleDocumentView objDocumentView;
  protected IStorage tempStorage;
  private int aspect;
  private int type;
  private boolean isStatic;
  boolean isActivated;
  private RECT borderWidths = new RECT();
  private RECT indent = new RECT();
  private boolean inUpdate = false;
  private boolean inInit = true;
  private boolean inDispose = false;
  private static final String WORDPROGID = "Word.Document";
  private Listener listener;
  static final int STATE_NONE = 0;
  static final int STATE_RUNNING = 1;
  static final int STATE_INPLACEACTIVE = 2;
  static final int STATE_UIACTIVE = 3;
  static final int STATE_ACTIVE = 4;
  int state = 0;

  protected OleClientSite(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt);
    createCOMInterfaces();
    while (paramComposite != null)
    {
      if ((paramComposite instanceof OleFrame))
      {
        this.frame = ((OleFrame)paramComposite);
        break;
      }
      paramComposite = paramComposite.getParent();
    }
    if (this.frame == null)
      OLE.error(5);
    this.frame.AddRef();
    this.aspect = 1;
    this.type = 1;
    this.isStatic = false;
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 10:
        case 11:
          OleClientSite.this.onResize(paramAnonymousEvent);
          break;
        case 12:
          OleClientSite.this.onDispose(paramAnonymousEvent);
          break;
        case 15:
          OleClientSite.this.onFocusIn(paramAnonymousEvent);
          break;
        case 16:
          OleClientSite.this.onFocusOut(paramAnonymousEvent);
          break;
        case 9:
          OleClientSite.this.onPaint(paramAnonymousEvent);
          break;
        case 31:
          OleClientSite.this.onTraverse(paramAnonymousEvent);
          break;
        case 1:
          break;
        case 26:
          OleClientSite.this.isActivated = true;
          break;
        case 27:
          OleClientSite.this.isActivated = false;
          break;
        default:
          OLE.error(20);
        }
      }
    };
    this.frame.addListener(11, this.listener);
    this.frame.addListener(10, this.listener);
    addListener(12, this.listener);
    addListener(15, this.listener);
    addListener(16, this.listener);
    addListener(9, this.listener);
    addListener(31, this.listener);
    addListener(1, this.listener);
    addListener(26, this.listener);
    addListener(27, this.listener);
  }

  public OleClientSite(Composite paramComposite, int paramInt, File paramFile)
  {
    this(paramComposite, paramInt);
    try
    {
      if ((paramFile == null) || (paramFile.isDirectory()) || (!paramFile.exists()))
        OLE.error(5);
      GUID localGUID = new GUID();
      char[] arrayOfChar = (paramFile.getAbsolutePath() + "").toCharArray();
      int i = COM.GetClassFile(arrayOfChar, localGUID);
      if (i != 0)
        OLE.error(1004, i);
      String str = getProgID(localGUID);
      if (str == null)
        OLE.error(1004, i);
      this.appClsid = localGUID;
      OleCreate(this.appClsid, localGUID, arrayOfChar, paramFile);
    }
    catch (SWTException localSWTException)
    {
      dispose();
      disposeCOMInterfaces();
      throw localSWTException;
    }
  }

  public OleClientSite(Composite paramComposite, int paramInt, String paramString)
  {
    this(paramComposite, paramInt);
    try
    {
      this.appClsid = getClassID(paramString);
      if (this.appClsid == null)
        OLE.error(1004);
      this.tempStorage = createTempStorage();
      int[] arrayOfInt = new int[1];
      int i = COM.OleCreate(this.appClsid, COM.IIDIUnknown, 1, null, this.iOleClientSite.getAddress(), this.tempStorage.getAddress(), arrayOfInt);
      if (i != 0)
        OLE.error(1001, i);
      this.objIUnknown = new IUnknown(arrayOfInt[0]);
      addObjectReferences();
      if (COM.OleRun(this.objIUnknown.getAddress()) == 0)
        this.state = 1;
    }
    catch (SWTException localSWTException)
    {
      dispose();
      disposeCOMInterfaces();
      throw localSWTException;
    }
  }

  public OleClientSite(Composite paramComposite, int paramInt, String paramString, File paramFile)
  {
    this(paramComposite, paramInt);
    try
    {
      if ((paramFile == null) || (paramFile.isDirectory()) || (!paramFile.exists()))
        OLE.error(5);
      this.appClsid = getClassID(paramString);
      if (this.appClsid == null)
        OLE.error(1004);
      char[] arrayOfChar = (paramFile.getAbsolutePath() + "").toCharArray();
      GUID localGUID = new GUID();
      COM.GetClassFile(arrayOfChar, localGUID);
      OleCreate(this.appClsid, localGUID, arrayOfChar, paramFile);
    }
    catch (SWTException localSWTException)
    {
      dispose();
      disposeCOMInterfaces();
      throw localSWTException;
    }
  }

  void OleCreate(GUID paramGUID1, GUID paramGUID2, char[] paramArrayOfChar, File paramFile)
  {
    boolean bool = isOffice2007(true);
    Object localObject;
    if ((!bool) && (COM.IsEqualGUID(paramGUID1, paramGUID2)))
    {
      this.tempStorage = createTempStorage();
      localObject = new int[1];
      int i = COM.OleCreateFromFile(paramGUID1, paramArrayOfChar, COM.IIDIUnknown, 1, null, this.iOleClientSite.getAddress(), this.tempStorage.getAddress(), (int[])localObject);
      if (i != 0)
        OLE.error(1001, i);
      this.objIUnknown = new IUnknown(localObject[0]);
    }
    else
    {
      localObject = null;
      int[] arrayOfInt1;
      int k;
      int m;
      if (COM.StgIsStorageFile(paramArrayOfChar) == 0)
      {
        arrayOfInt1 = new int[1];
        k = 65552;
        m = COM.StgOpenStorage(paramArrayOfChar, 0, k, 0, 0, arrayOfInt1);
        if (m != 0)
          OLE.error(1002, m);
        localObject = new IStorage(arrayOfInt1[0]);
      }
      else
      {
        arrayOfInt1 = new int[1];
        k = 4114;
        m = COM.StgCreateDocfile(null, k | 0x4000000, 0, arrayOfInt1);
        if (m != 0)
          OLE.error(1002, m);
        localObject = new IStorage(arrayOfInt1[0]);
        String str = "CONTENTS";
        GUID localGUID = getClassID("Word.Document");
        if ((localGUID != null) && (COM.IsEqualGUID(paramGUID1, localGUID)))
          str = "WordDocument";
        if (bool)
          str = "Package";
        arrayOfInt1 = new int[1];
        m = ((IStorage)localObject).CreateStream(str, k, 0, 0, arrayOfInt1);
        if (m != 0)
        {
          ((IStorage)localObject).Release();
          OLE.error(1002, m);
        }
        IStream localIStream = new IStream(arrayOfInt1[0]);
        try
        {
          FileInputStream localFileInputStream = new FileInputStream(paramFile);
          int n = 4096;
          byte[] arrayOfByte = new byte[n];
          int i1 = 0;
          while ((i1 = localFileInputStream.read(arrayOfByte)) > 0)
          {
            int i2 = COM.CoTaskMemAlloc(i1);
            OS.MoveMemory(i2, arrayOfByte, i1);
            m = localIStream.Write(i2, i1, null);
            COM.CoTaskMemFree(i2);
            if (m != 0)
            {
              localFileInputStream.close();
              localIStream.Release();
              ((IStorage)localObject).Release();
              OLE.error(1002, m);
            }
          }
          localFileInputStream.close();
          localIStream.Commit(0);
          localIStream.Release();
        }
        catch (IOException localIOException)
        {
          localIStream.Release();
          ((IStorage)localObject).Release();
          OLE.error(1002);
        }
      }
      this.tempStorage = createTempStorage();
      int j = ((IStorage)localObject).CopyTo(0, null, null, this.tempStorage.getAddress());
      ((IStorage)localObject).Release();
      if (j != 0)
        OLE.error(1002, j);
      int[] arrayOfInt2 = new int[1];
      j = COM.CoCreateInstance(paramGUID1, 0, 3, COM.IIDIUnknown, arrayOfInt2);
      if (j != 0)
        OLE.error(1001, j);
      this.objIUnknown = new IUnknown(arrayOfInt2[0]);
      arrayOfInt2 = new int[1];
      j = this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, arrayOfInt2);
      if (j != 0)
        OLE.error(1001, j);
      IPersistStorage localIPersistStorage = new IPersistStorage(arrayOfInt2[0]);
      j = localIPersistStorage.Load(this.tempStorage.getAddress());
      localIPersistStorage.Release();
      if (j != 0)
        OLE.error(1001, j);
    }
    addObjectReferences();
    if (COM.OleRun(this.objIUnknown.getAddress()) == 0)
      this.state = 1;
  }

  protected void addObjectReferences()
  {
    int[] arrayOfInt1 = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIPersist, arrayOfInt1) == 0)
    {
      IPersist localIPersist = new IPersist(arrayOfInt1[0]);
      localObject = new GUID();
      if (localIPersist.GetClassID((GUID)localObject) == 0)
        this.objClsid = ((GUID)localObject);
      localIPersist.Release();
    }
    arrayOfInt1 = new int[1];
    int i = this.objIUnknown.QueryInterface(COM.IIDIViewObject2, arrayOfInt1);
    if (i != 0)
      OLE.error(1003, i);
    this.objIViewObject2 = new IViewObject2(arrayOfInt1[0]);
    this.objIViewObject2.SetAdvise(this.aspect, 0, this.iAdviseSink.getAddress());
    arrayOfInt1 = new int[1];
    i = this.objIUnknown.QueryInterface(COM.IIDIOleObject, arrayOfInt1);
    if (i != 0)
      OLE.error(1003, i);
    this.objIOleObject = new IOleObject(arrayOfInt1[0]);
    Object localObject = new int[1];
    i = this.objIOleObject.GetClientSite((int[])localObject);
    if (localObject[0] == 0)
      this.objIOleObject.SetClientSite(this.iOleClientSite.getAddress());
    else
      Release();
    int[] arrayOfInt2 = new int[1];
    this.objIOleObject.Advise(this.iAdviseSink.getAddress(), arrayOfInt2);
    this.objIOleObject.SetHostNames("main", "main");
    COM.OleSetContainedObject(this.objIUnknown.getAddress(), true);
    arrayOfInt1 = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIOleLink, arrayOfInt1) == 0)
    {
      IOleLink localIOleLink = new IOleLink(arrayOfInt1[0]);
      int[] arrayOfInt3 = new int[1];
      if (localIOleLink.GetSourceMoniker(arrayOfInt3) == 0)
      {
        IMoniker localIMoniker = new IMoniker(arrayOfInt3[0]);
        localIMoniker.Release();
        this.type = 0;
        localIOleLink.BindIfRunning();
      }
      else
      {
        this.isStatic = true;
      }
      localIOleLink.Release();
    }
  }

  protected int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  private int CanInPlaceActivate()
  {
    if ((this.aspect == 1) && (this.type == 1))
      return 0;
    return 1;
  }

  private int ContextSensitiveHelp(int paramInt)
  {
    return 0;
  }

  protected void createCOMInterfaces()
  {
    this.iUnknown = new COMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.Release();
      }
    };
    this.iOleClientSite = new COMObject(new int[] { 2, 0, 0, 0, 3, 1, 0, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.SaveObject();
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.GetContainer(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.ShowObject();
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnShowWindow(paramAnonymousArrayOfInt[0]);
      }
    };
    this.iAdviseSink = new COMObject(new int[] { 2, 0, 0, 2, 2, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnDataChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnViewChange(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        OleClientSite.this.OnSave();
        return 0;
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnClose();
      }
    };
    this.iOleInPlaceSite = new COMObject(new int[] { 2, 0, 0, 1, 1, 0, 0, 0, 5, C.PTR_SIZEOF == 4 ? 2 : 1, 1, 0, 0, 0, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.GetWindow(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.ContextSensitiveHelp(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.CanInPlaceActivate();
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnInPlaceActivate();
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnUIActivate();
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.GetWindowContext(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        if (paramAnonymousArrayOfInt.length == 2)
          return OleClientSite.this.Scroll(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
        return OleClientSite.this.Scroll_64(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnUIDeactivate(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnInPlaceDeactivate();
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.OnPosRectChange(paramAnonymousArrayOfInt[0]);
      }
    };
    this.iOleDocumentSite = new COMObject(new int[] { 2, 0, 0, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return OleClientSite.this.ActivateMe(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  protected IStorage createTempStorage()
  {
    int[] arrayOfInt = new int[1];
    int i = 67108882;
    int j = COM.StgCreateDocfile(null, i, 0, arrayOfInt);
    if (j != 0)
      OLE.error(1000, j);
    return new IStorage(arrayOfInt[0]);
  }

  public void deactivateInPlaceClient()
  {
    if (this.objIOleInPlaceObject != null)
      this.objIOleInPlaceObject.InPlaceDeactivate();
  }

  private void deleteTempStorage()
  {
    if (this.tempStorage != null)
      this.tempStorage.Release();
    this.tempStorage = null;
  }

  protected void disposeCOMInterfaces()
  {
    if (this.iUnknown != null)
      this.iUnknown.dispose();
    this.iUnknown = null;
    if (this.iOleClientSite != null)
      this.iOleClientSite.dispose();
    this.iOleClientSite = null;
    if (this.iAdviseSink != null)
      this.iAdviseSink.dispose();
    this.iAdviseSink = null;
    if (this.iOleInPlaceSite != null)
      this.iOleInPlaceSite.dispose();
    this.iOleInPlaceSite = null;
    if (this.iOleDocumentSite != null)
      this.iOleDocumentSite.dispose();
    this.iOleDocumentSite = null;
  }

  public int doVerb(int paramInt)
  {
    if ((this.state == 0) && (COM.OleRun(this.objIUnknown.getAddress()) == 0))
      this.state = 1;
    if ((this.state == 0) || (this.isStatic))
      return -2147467259;
    RECT localRECT = new RECT();
    OS.GetClientRect(this.handle, localRECT);
    int i = this.objIOleObject.DoVerb(paramInt, null, this.iOleClientSite.getAddress(), 0, this.handle, localRECT);
    if ((this.state != 1) && (this.inInit))
    {
      updateStorage();
      this.inInit = false;
    }
    return i;
  }

  public int exec(int paramInt1, int paramInt2, Variant paramVariant1, Variant paramVariant2)
  {
    if (this.objIOleCommandTarget == null)
    {
      int[] arrayOfInt = new int[1];
      if (this.objIUnknown.QueryInterface(COM.IIDIOleCommandTarget, arrayOfInt) != 0)
        return 1003;
      this.objIOleCommandTarget = new IOleCommandTarget(arrayOfInt[0]);
    }
    int i = 0;
    if (paramVariant1 != null)
    {
      i = OS.GlobalAlloc(64, VARIANT.sizeof);
      paramVariant1.getData(i);
    }
    int j = 0;
    if (paramVariant2 != null)
    {
      j = OS.GlobalAlloc(64, VARIANT.sizeof);
      paramVariant2.getData(j);
    }
    int k = this.objIOleCommandTarget.Exec(null, paramInt1, paramInt2, i, j);
    if (i != 0)
    {
      COM.VariantClear(i);
      OS.GlobalFree(i);
    }
    if (j != 0)
    {
      paramVariant2.setData(j);
      COM.VariantClear(j);
      OS.GlobalFree(j);
    }
    return k;
  }

  IDispatch getAutomationObject()
  {
    int[] arrayOfInt = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIDispatch, arrayOfInt) != 0)
      return null;
    return new IDispatch(arrayOfInt[0]);
  }

  protected GUID getClassID(String paramString)
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

  private int GetContainer(int paramInt)
  {
    if (paramInt != 0)
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  private SIZE getExtent()
  {
    SIZE localSIZE = new SIZE();
    if (this.objIOleObject != null)
      if ((this.objIViewObject2 != null) && (!COM.OleIsRunning(this.objIOleObject.getAddress())))
        this.objIViewObject2.GetExtent(this.aspect, -1, null, localSIZE);
      else
        this.objIOleObject.GetExtent(this.aspect, localSIZE);
    return xFormHimetricToPixels(localSIZE);
  }

  public Rectangle getIndent()
  {
    return new Rectangle(this.indent.left, this.indent.right, this.indent.top, this.indent.bottom);
  }

  public String getProgramID()
  {
    return getProgID(this.appClsid);
  }

  String getProgID(GUID paramGUID)
  {
    if (paramGUID != null)
    {
      int[] arrayOfInt = new int[1];
      if (COM.ProgIDFromCLSID(paramGUID, arrayOfInt) == 0)
      {
        int i = arrayOfInt[0];
        int j = OS.GlobalSize(i);
        int k = OS.GlobalLock(i);
        char[] arrayOfChar = new char[j];
        COM.MoveMemory(arrayOfChar, k, j);
        OS.GlobalUnlock(i);
        OS.GlobalFree(i);
        String str = new String(arrayOfChar);
        int m = str.indexOf("");
        return str.substring(0, m);
      }
    }
    return null;
  }

  int ActivateMe(int paramInt)
  {
    if (paramInt == 0)
    {
      localObject = new int[1];
      if (this.objIUnknown.QueryInterface(COM.IIDIOleDocument, (int[])localObject) != 0)
        return -2147467259;
      IOleDocument localIOleDocument = new IOleDocument(localObject[0]);
      if (localIOleDocument.CreateView(this.iOleInPlaceSite.getAddress(), 0, 0, (int[])localObject) != 0)
        return -2147467259;
      localIOleDocument.Release();
      this.objDocumentView = new IOleDocumentView(localObject[0]);
    }
    else
    {
      this.objDocumentView = new IOleDocumentView(paramInt);
      this.objDocumentView.AddRef();
      this.objDocumentView.SetInPlaceSite(this.iOleInPlaceSite.getAddress());
    }
    this.objDocumentView.UIActivate(1);
    Object localObject = getRect();
    this.objDocumentView.SetRect((RECT)localObject);
    this.objDocumentView.Show(1);
    return 0;
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

  RECT getRect()
  {
    Rectangle localRectangle = getClientArea();
    RECT localRECT = new RECT();
    localRECT.left = localRectangle.x;
    localRECT.top = localRectangle.y;
    localRECT.right = (localRectangle.x + localRectangle.width);
    localRECT.bottom = (localRectangle.y + localRectangle.height);
    return localRECT;
  }

  private int GetWindowContext(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if ((this.frame == null) || (paramInt1 == 0))
      return -2147467263;
    int i = this.frame.getIOleInPlaceFrame();
    COM.MoveMemory(paramInt1, new int[] { i }, OS.PTR_SIZEOF);
    this.frame.AddRef();
    if (paramInt2 != 0)
      COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    RECT localRECT = getRect();
    if (paramInt3 != 0)
      OS.MoveMemory(paramInt3, localRECT, RECT.sizeof);
    if (paramInt4 != 0)
      OS.MoveMemory(paramInt4, localRECT, RECT.sizeof);
    OLEINPLACEFRAMEINFO localOLEINPLACEFRAMEINFO = new OLEINPLACEFRAMEINFO();
    localOLEINPLACEFRAMEINFO.cb = OLEINPLACEFRAMEINFO.sizeof;
    localOLEINPLACEFRAMEINFO.fMDIApp = 0;
    localOLEINPLACEFRAMEINFO.hwndFrame = this.frame.handle;
    Shell localShell = getShell();
    Menu localMenu = localShell.getMenuBar();
    if ((localMenu != null) && (!localMenu.isDisposed()))
    {
      int j = localShell.handle;
      int k = OS.SendMessage(j, 32768, 0, 0);
      if (k != 0)
      {
        int m = OS.SendMessage(j, 32769, 0, 0);
        if (m != 0)
        {
          localOLEINPLACEFRAMEINFO.cAccelEntries = k;
          localOLEINPLACEFRAMEINFO.haccel = m;
        }
      }
    }
    COM.MoveMemory(paramInt5, localOLEINPLACEFRAMEINFO, OLEINPLACEFRAMEINFO.sizeof);
    return 0;
  }

  public boolean isDirty()
  {
    int[] arrayOfInt = new int[1];
    if (this.objIOleObject.QueryInterface(COM.IIDIPersistFile, arrayOfInt) != 0)
      return true;
    IPersistFile localIPersistFile = new IPersistFile(arrayOfInt[0]);
    int i = localIPersistFile.IsDirty();
    localIPersistFile.Release();
    return i != 1;
  }

  public boolean isFocusControl()
  {
    checkWidget();
    int i = OS.GetFocus();
    if (this.objIOleInPlaceObject == null)
      return this.handle == i;
    int[] arrayOfInt = new int[1];
    this.objIOleInPlaceObject.GetWindow(arrayOfInt);
    while (i != 0)
    {
      if (arrayOfInt[0] == i)
        return true;
      i = OS.GetParent(i);
    }
    return false;
  }

  private boolean isOffice2007(boolean paramBoolean)
  {
    String str = getProgramID();
    if (str == null)
      return false;
    if (paramBoolean)
    {
      int i = str.lastIndexOf('.');
      if (i != -1)
      {
        str = str.substring(0, i);
        GUID localGUID = getClassID(str);
        str = getProgID(localGUID);
        if (str == null)
          return false;
      }
    }
    if (str.equals("Word.Document.12"))
      return true;
    if (str.equals("Excel.Sheet.12"))
      return true;
    return str.equals("PowerPoint.Show.12");
  }

  private int OnClose()
  {
    return 0;
  }

  private int OnDataChange(int paramInt1, int paramInt2)
  {
    return 0;
  }

  private void onDispose(Event paramEvent)
  {
    this.inDispose = true;
    removeListener(12, this.listener);
    removeListener(15, this.listener);
    removeListener(16, this.listener);
    removeListener(9, this.listener);
    removeListener(31, this.listener);
    removeListener(1, this.listener);
    if (this.state != 0)
      doVerb(-6);
    deactivateInPlaceClient();
    releaseObjectInterfaces();
    deleteTempStorage();
    this.frame.removeListener(11, this.listener);
    this.frame.removeListener(10, this.listener);
    this.frame.Release();
    this.frame = null;
  }

  void onFocusIn(Event paramEvent)
  {
    if (this.inDispose)
      return;
    if (this.state != 3)
    {
      arrayOfInt = new int[1];
      if (this.objIUnknown.QueryInterface(COM.IIDIOleInPlaceObject, arrayOfInt) == 0)
      {
        IOleInPlaceObject localIOleInPlaceObject = new IOleInPlaceObject(arrayOfInt[0]);
        localIOleInPlaceObject.Release();
        doVerb(-1);
      }
    }
    if (this.objIOleInPlaceObject == null)
      return;
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
  }

  private int OnInPlaceActivate()
  {
    this.state = 2;
    this.frame.setCurrentDocument(this);
    if (this.objIOleObject == null)
      return 0;
    int[] arrayOfInt = new int[1];
    if (this.objIOleObject.QueryInterface(COM.IIDIOleInPlaceObject, arrayOfInt) == 0)
      this.objIOleInPlaceObject = new IOleInPlaceObject(arrayOfInt[0]);
    return 0;
  }

  private int OnInPlaceDeactivate()
  {
    if (this.objIOleInPlaceObject != null)
      this.objIOleInPlaceObject.Release();
    this.objIOleInPlaceObject = null;
    this.state = 1;
    redraw();
    Shell localShell = getShell();
    if ((isFocusControl()) || (this.frame.isFocusControl()))
      localShell.traverse(16);
    return 0;
  }

  private int OnPosRectChange(int paramInt)
  {
    Point localPoint = getSize();
    setExtent(localPoint.x, localPoint.y);
    return 0;
  }

  private void onPaint(Event paramEvent)
  {
    if ((this.state == 1) || (this.state == 2))
    {
      SIZE localSIZE = getExtent();
      Rectangle localRectangle = getClientArea();
      RECT localRECT = new RECT();
      if (getProgramID().startsWith("Excel.Sheet"))
      {
        localRECT.left = localRectangle.x;
        localRECT.right = (localRectangle.x + localRectangle.height * localSIZE.cx / localSIZE.cy);
        localRECT.top = localRectangle.y;
        localRECT.bottom = (localRectangle.y + localRectangle.height);
      }
      else
      {
        localRECT.left = localRectangle.x;
        localRECT.right = (localRectangle.x + localSIZE.cx);
        localRECT.top = localRectangle.y;
        localRECT.bottom = (localRectangle.y + localSIZE.cy);
      }
      int i = OS.GlobalAlloc(64, RECT.sizeof);
      OS.MoveMemory(i, localRECT, RECT.sizeof);
      COM.OleDraw(this.objIUnknown.getAddress(), this.aspect, paramEvent.gc.handle, i);
      OS.GlobalFree(i);
    }
  }

  private void onResize(Event paramEvent)
  {
    setBounds();
  }

  private void OnSave()
  {
  }

  private int OnShowWindow(int paramInt)
  {
    return 0;
  }

  private int OnUIActivate()
  {
    if (this.objIOleInPlaceObject == null)
      return -2147467259;
    this.state = 3;
    int[] arrayOfInt = new int[1];
    if (this.objIOleInPlaceObject.GetWindow(arrayOfInt) == 0)
      OS.SetWindowPos(arrayOfInt[0], 0, 0, 0, 0, 0, 3);
    return 0;
  }

  int OnUIDeactivate(int paramInt)
  {
    if ((this.frame == null) || (this.frame.isDisposed()))
      return 0;
    this.state = 2;
    this.frame.SetActiveObject(0, 0);
    redraw();
    Shell localShell = getShell();
    if ((isFocusControl()) || (this.frame.isFocusControl()))
      localShell.traverse(16);
    Menu localMenu = localShell.getMenuBar();
    if ((localMenu == null) || (localMenu.isDisposed()))
      return 0;
    int i = localShell.handle;
    OS.SetMenu(i, localMenu.handle);
    return COM.OleSetMenuDescriptor(0, i, 0, 0, 0);
  }

  private void onTraverse(Event paramEvent)
  {
    switch (paramEvent.detail)
    {
    case 2:
    case 4:
    case 8:
    case 16:
    case 128:
    case 256:
    case 512:
      paramEvent.doit = true;
    }
  }

  private int OnViewChange(int paramInt1, int paramInt2)
  {
    return 0;
  }

  protected int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIUnknown))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iUnknown.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIAdviseSink))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iAdviseSink.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIOleClientSite))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iOleClientSite.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIOleInPlaceSite))
    {
      COM.MoveMemory(paramInt2, new int[] { this.iOleInPlaceSite.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIOleDocumentSite))
    {
      String str = getProgramID();
      if (!str.startsWith("PowerPoint"))
      {
        COM.MoveMemory(paramInt2, new int[] { this.iOleDocumentSite.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
    }
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    return -2147467262;
  }

  public int queryStatus(int paramInt)
  {
    if (this.objIOleCommandTarget == null)
    {
      localObject = new int[1];
      if (this.objIUnknown.QueryInterface(COM.IIDIOleCommandTarget, (int[])localObject) != 0)
        return 0;
      this.objIOleCommandTarget = new IOleCommandTarget(localObject[0]);
    }
    Object localObject = new OLECMD();
    ((OLECMD)localObject).cmdID = paramInt;
    int i = this.objIOleCommandTarget.QueryStatus(null, 1, (OLECMD)localObject, null);
    if (i != 0)
      return 0;
    return ((OLECMD)localObject).cmdf;
  }

  protected int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  protected void releaseObjectInterfaces()
  {
    if (this.objIOleInPlaceObject != null)
      this.objIOleInPlaceObject.Release();
    this.objIOleInPlaceObject = null;
    if (this.objIOleObject != null)
    {
      this.objIOleObject.Close(1);
      this.objIOleObject.Release();
    }
    this.objIOleObject = null;
    if (this.objDocumentView != null)
      this.objDocumentView.Release();
    this.objDocumentView = null;
    if (this.objIViewObject2 != null)
    {
      this.objIViewObject2.SetAdvise(this.aspect, 0, 0);
      this.objIViewObject2.Release();
    }
    this.objIViewObject2 = null;
    if (this.objIOleCommandTarget != null)
      this.objIOleCommandTarget.Release();
    this.objIOleCommandTarget = null;
    if (this.objIUnknown != null)
      this.objIUnknown.Release();
    this.objIUnknown = null;
    if (COM.FreeUnusedLibraries)
      COM.CoFreeUnusedLibraries();
  }

  public boolean save(File paramFile, boolean paramBoolean)
  {
    if (isOffice2007(false))
      return saveOffice2007(paramFile);
    if (paramBoolean)
      return saveToStorageFile(paramFile);
    return saveToTraditionalFile(paramFile);
  }

  private boolean saveFromContents(int paramInt, File paramFile)
  {
    boolean bool = false;
    IStream localIStream = new IStream(paramInt);
    localIStream.AddRef();
    try
    {
      FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
      int i = 4096;
      int j = COM.CoTaskMemAlloc(i);
      int[] arrayOfInt = new int[1];
      while ((localIStream.Read(j, i, arrayOfInt) == 0) && (arrayOfInt[0] > 0))
      {
        byte[] arrayOfByte = new byte[arrayOfInt[0]];
        OS.MoveMemory(arrayOfByte, j, arrayOfInt[0]);
        localFileOutputStream.write(arrayOfByte);
        bool = true;
      }
      COM.CoTaskMemFree(j);
      localFileOutputStream.close();
    }
    catch (IOException localIOException)
    {
    }
    localIStream.Release();
    return bool;
  }

  private boolean saveFromOle10Native(int paramInt, File paramFile)
  {
    boolean bool = false;
    IStream localIStream = new IStream(paramInt);
    localIStream.AddRef();
    int i = COM.CoTaskMemAlloc(4);
    int[] arrayOfInt = new int[1];
    int j = localIStream.Read(i, 4, null);
    OS.MoveMemory(arrayOfInt, i, 4);
    COM.CoTaskMemFree(i);
    if ((j == 0) && (arrayOfInt[0] > 0))
    {
      byte[] arrayOfByte = new byte[arrayOfInt[0]];
      i = COM.CoTaskMemAlloc(arrayOfInt[0]);
      j = localIStream.Read(i, arrayOfInt[0], null);
      OS.MoveMemory(arrayOfByte, i, arrayOfInt[0]);
      COM.CoTaskMemFree(i);
      try
      {
        FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
        localFileOutputStream.write(arrayOfByte);
        localFileOutputStream.close();
        bool = true;
      }
      catch (IOException localIOException)
      {
      }
    }
    localIStream.Release();
    return bool;
  }

  private int SaveObject()
  {
    updateStorage();
    return 0;
  }

  private boolean saveOffice2007(File paramFile)
  {
    if ((paramFile == null) || (paramFile.isDirectory()))
      return false;
    if (!updateStorage())
      return false;
    boolean bool = false;
    int[] arrayOfInt1 = new int[1];
    IPersistStorage localIPersistStorage = null;
    if (this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, arrayOfInt1) == 0)
    {
      localIPersistStorage = new IPersistStorage(arrayOfInt1[0]);
      this.tempStorage.AddRef();
      localIPersistStorage.HandsOffStorage();
    }
    int[] arrayOfInt2 = new int[1];
    int i = 16;
    if (this.tempStorage.OpenStream("Package", 0, i, 0, arrayOfInt2) == 0)
      bool = saveFromContents(arrayOfInt2[0], paramFile);
    if (localIPersistStorage != null)
    {
      localIPersistStorage.SaveCompleted(this.tempStorage.getAddress());
      this.tempStorage.Release();
      localIPersistStorage.Release();
    }
    return bool;
  }

  private boolean saveToStorageFile(File paramFile)
  {
    if ((paramFile == null) || (paramFile.isDirectory()))
      return false;
    if (!updateStorage())
      return false;
    int[] arrayOfInt = new int[1];
    if (this.objIOleObject.QueryInterface(COM.IIDIPersistStorage, arrayOfInt) != 0)
      return false;
    IPersistStorage localIPersistStorage = new IPersistStorage(arrayOfInt[0]);
    try
    {
      arrayOfInt = new int[1];
      char[] arrayOfChar = (paramFile.getAbsolutePath() + "").toCharArray();
      int i = 69650;
      int j = COM.StgCreateDocfile(arrayOfChar, i, 0, arrayOfInt);
      if (j != 0)
        return false;
      IStorage localIStorage = new IStorage(arrayOfInt[0]);
      try
      {
        if ((COM.OleSave(localIPersistStorage.getAddress(), localIStorage.getAddress(), false) == 0) && (localIStorage.Commit(0) == 0))
          return true;
      }
      finally
      {
        localIStorage.Release();
      }
    }
    finally
    {
      localIPersistStorage.Release();
    }
    jsr -9;
    return false;
  }

  private boolean saveToTraditionalFile(File paramFile)
  {
    if ((paramFile == null) || (paramFile.isDirectory()))
      return false;
    if (!updateStorage())
      return false;
    int[] arrayOfInt = new int[1];
    if (this.tempStorage.OpenStream("CONTENTS", 0, 16, 0, arrayOfInt) == 0)
      return saveFromContents(arrayOfInt[0], paramFile);
    if (this.tempStorage.OpenStream("\001Ole10Native", 0, 16, 0, arrayOfInt) == 0)
      return saveFromOle10Native(arrayOfInt[0], paramFile);
    return false;
  }

  private int Scroll_64(int paramInt)
  {
    return 0;
  }

  private int Scroll(int paramInt1, int paramInt2)
  {
    return 0;
  }

  void setBorderSpace(RECT paramRECT)
  {
    this.borderWidths = paramRECT;
    setBounds();
  }

  void setBounds()
  {
    Rectangle localRectangle = this.frame.getClientArea();
    setBounds(this.borderWidths.left, this.borderWidths.top, localRectangle.width - this.borderWidths.left - this.borderWidths.right, localRectangle.height - this.borderWidths.top - this.borderWidths.bottom);
    setObjectRects();
  }

  private void setExtent(int paramInt1, int paramInt2)
  {
    if ((this.objIOleObject == null) || (this.isStatic) || (this.inUpdate))
      return;
    SIZE localSIZE1 = getExtent();
    if ((paramInt1 == localSIZE1.cx) && (paramInt2 == localSIZE1.cy))
      return;
    SIZE localSIZE2 = new SIZE();
    localSIZE2.cx = paramInt1;
    localSIZE2.cy = paramInt2;
    localSIZE2 = xFormPixelsToHimetric(localSIZE2);
    boolean bool = COM.OleIsRunning(this.objIOleObject.getAddress());
    if (!bool)
      COM.OleRun(this.objIOleObject.getAddress());
    if (this.objIOleObject.SetExtent(this.aspect, localSIZE2) == 0)
    {
      this.inUpdate = true;
      this.objIOleObject.Update();
      this.inUpdate = false;
      if (!bool)
        this.objIOleObject.Close(0);
    }
  }

  public void setIndent(Rectangle paramRectangle)
  {
    this.indent = new RECT();
    this.indent.left = paramRectangle.x;
    this.indent.right = paramRectangle.width;
    this.indent.top = paramRectangle.y;
    this.indent.bottom = paramRectangle.height;
  }

  private void setObjectRects()
  {
    if (this.objIOleInPlaceObject == null)
      return;
    RECT localRECT = getRect();
    this.objIOleInPlaceObject.SetObjectRects(localRECT, localRECT);
  }

  private int ShowObject()
  {
    setBounds();
    return 0;
  }

  public void showProperties(String paramString)
  {
    int[] arrayOfInt = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDISpecifyPropertyPages, arrayOfInt) != 0)
      return;
    ISpecifyPropertyPages localISpecifyPropertyPages = new ISpecifyPropertyPages(arrayOfInt[0]);
    CAUUID localCAUUID = new CAUUID();
    int i = localISpecifyPropertyPages.GetPages(localCAUUID);
    localISpecifyPropertyPages.Release();
    if (i != 0)
      return;
    char[] arrayOfChar = (char[])null;
    if (paramString != null)
    {
      arrayOfChar = new char[paramString.length()];
      paramString.getChars(0, paramString.length(), arrayOfChar, 0);
    }
    i = COM.OleCreatePropertyFrame(this.frame.handle, 10, 10, arrayOfChar, 1, new int[] { this.objIUnknown.getAddress() }, localCAUUID.cElems, localCAUUID.pElems, 2048, 0, 0);
    COM.CoTaskMemFree(localCAUUID.pElems);
  }

  private boolean updateStorage()
  {
    if (this.tempStorage == null)
      return false;
    int[] arrayOfInt = new int[1];
    if (this.objIUnknown.QueryInterface(COM.IIDIPersistStorage, arrayOfInt) != 0)
      return false;
    IPersistStorage localIPersistStorage = new IPersistStorage(arrayOfInt[0]);
    int i = COM.OleSave(localIPersistStorage.getAddress(), this.tempStorage.getAddress(), true);
    if (i != 0)
    {
      COM.WriteClassStg(this.tempStorage.getAddress(), this.objClsid);
      i = localIPersistStorage.Save(this.tempStorage.getAddress(), true);
    }
    this.tempStorage.Commit(0);
    i = localIPersistStorage.SaveCompleted(0);
    localIPersistStorage.Release();
    return true;
  }

  private SIZE xFormHimetricToPixels(SIZE paramSIZE)
  {
    int i = OS.GetDC(0);
    int j = OS.GetDeviceCaps(i, 88);
    int k = OS.GetDeviceCaps(i, 90);
    OS.ReleaseDC(0, i);
    int m = Compatibility.round(paramSIZE.cx * j, 2540);
    int n = Compatibility.round(paramSIZE.cy * k, 2540);
    SIZE localSIZE = new SIZE();
    localSIZE.cx = m;
    localSIZE.cy = n;
    return localSIZE;
  }

  private SIZE xFormPixelsToHimetric(SIZE paramSIZE)
  {
    int i = OS.GetDC(0);
    int j = OS.GetDeviceCaps(i, 88);
    int k = OS.GetDeviceCaps(i, 90);
    OS.ReleaseDC(0, i);
    int m = Compatibility.round(paramSIZE.cx * 2540, j);
    int n = Compatibility.round(paramSIZE.cy * 2540, k);
    SIZE localSIZE = new SIZE();
    localSIZE.cx = m;
    localSIZE.cy = n;
    return localSIZE;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.ole.win32.OleClientSite
 * JD-Core Version:    0.6.2
 */