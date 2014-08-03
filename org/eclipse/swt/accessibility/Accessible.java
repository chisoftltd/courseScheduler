package org.eclipse.swt.accessibility;

import java.util.Locale;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.ole.win32.IAccessible;
import org.eclipse.swt.internal.ole.win32.IEnumVARIANT;
import org.eclipse.swt.internal.ole.win32.IServiceProvider;
import org.eclipse.swt.internal.ole.win32.VARIANT;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.ole.win32.OLE;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class Accessible
{
  static final int MAX_RELATION_TYPES = 15;
  static final int TABLE_MODEL_CHANGE_SIZE = 5;
  static final int TEXT_CHANGE_SIZE = 4;
  static final boolean DEBUG = false;
  static final String PROPERTY_USEIA2 = "org.eclipse.swt.accessibility.UseIA2";
  static boolean UseIA2 = true;
  static int UniqueID = -16;
  int refCount = 0;
  int enumIndex = 0;
  COMObject objIAccessible;
  COMObject objIEnumVARIANT;
  COMObject objIServiceProvider;
  COMObject objIAccessible2;
  COMObject objIAccessibleAction;
  COMObject objIAccessibleApplication;
  COMObject objIAccessibleHyperlink;
  COMObject objIAccessibleHypertext;
  COMObject objIAccessibleTable2;
  COMObject objIAccessibleTableCell;
  COMObject objIAccessibleText;
  COMObject objIAccessibleValue;
  IAccessible iaccessible;
  Vector accessibleListeners = new Vector();
  Vector accessibleControlListeners = new Vector();
  Vector accessibleTextListeners = new Vector();
  Vector accessibleActionListeners = new Vector();
  Vector accessibleHyperlinkListeners = new Vector();
  Vector accessibleTableListeners = new Vector();
  Vector accessibleTableCellListeners = new Vector();
  Vector accessibleTextExtendedListeners = new Vector();
  Vector accessibleValueListeners = new Vector();
  Vector accessibleAttributeListeners = new Vector();
  Relation[] relations = new Relation[15];
  Object[] variants;
  Accessible parent;
  Vector children = new Vector();
  Control control;
  int uniqueID = -1;
  int[] tableChange;
  Object[] textDeleted;
  Object[] textInserted;
  ToolItem item;
  static final String zeros = "00000000";

  static
  {
    String str = System.getProperty("org.eclipse.swt.accessibility.UseIA2");
    if ((str != null) && (str.equalsIgnoreCase("false")))
      UseIA2 = false;
  }

  public Accessible(Accessible paramAccessible)
  {
    this.parent = checkNull(paramAccessible);
    this.control = paramAccessible.control;
    paramAccessible.children.addElement(this);
    AddRef();
  }

  /** @deprecated */
  protected Accessible()
  {
  }

  Accessible(Control paramControl)
  {
    this.control = paramControl;
    int[] arrayOfInt = new int[1];
    int i = COM.CreateStdAccessibleObject(paramControl.handle, -4, COM.IIDIAccessible, arrayOfInt);
    if (arrayOfInt[0] == 0)
      return;
    if (i != 0)
      OLE.error(1001, i);
    this.iaccessible = new IAccessible(arrayOfInt[0]);
    createIAccessible();
    AddRef();
  }

  Accessible(Accessible paramAccessible, int paramInt)
  {
    this(paramAccessible);
    this.iaccessible = new IAccessible(paramInt);
  }

  static Accessible checkNull(Accessible paramAccessible)
  {
    if (paramAccessible == null)
      SWT.error(4);
    return paramAccessible;
  }

  void createIAccessible()
  {
    this.objIAccessible = new COMObject(new int[] { 2, 0, 0, 1, 3, 5, 8, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 2, 1, 1, 2, 2, 5, 3, 3, 1, 2, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accParent(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accChildCount(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accChild(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accValue(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accDescription(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accRole(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accState(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accHelp(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accHelpTopic(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accKeyboardShortcut(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accFocus(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accSelection(paramAnonymousArrayOfInt[0]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accDefaultAction(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accSelect(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method22(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accLocation(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method23(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accNavigate(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method24(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accHitTest(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method25(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accDoDefaultAction(paramAnonymousArrayOfInt[0]);
      }

      public int method26(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.put_accName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method27(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.put_accValue(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
    int i = this.objIAccessible.ppVtable;
    int[] arrayOfInt1 = new int[1];
    COM.MoveMemory(arrayOfInt1, i, OS.PTR_SIZEOF);
    int[] arrayOfInt2 = new int[28];
    COM.MoveMemory(arrayOfInt2, arrayOfInt1[0], OS.PTR_SIZEOF * arrayOfInt2.length);
    arrayOfInt2[9] = COM.get_accChild_CALLBACK(arrayOfInt2[9]);
    arrayOfInt2[10] = COM.get_accName_CALLBACK(arrayOfInt2[10]);
    arrayOfInt2[11] = COM.get_accValue_CALLBACK(arrayOfInt2[11]);
    arrayOfInt2[12] = COM.get_accDescription_CALLBACK(arrayOfInt2[12]);
    arrayOfInt2[13] = COM.get_accRole_CALLBACK(arrayOfInt2[13]);
    arrayOfInt2[14] = COM.get_accState_CALLBACK(arrayOfInt2[14]);
    arrayOfInt2[15] = COM.get_accHelp_CALLBACK(arrayOfInt2[15]);
    arrayOfInt2[16] = COM.get_accHelpTopic_CALLBACK(arrayOfInt2[16]);
    arrayOfInt2[17] = COM.get_accKeyboardShortcut_CALLBACK(arrayOfInt2[17]);
    arrayOfInt2[20] = COM.get_accDefaultAction_CALLBACK(arrayOfInt2[20]);
    arrayOfInt2[21] = COM.accSelect_CALLBACK(arrayOfInt2[21]);
    arrayOfInt2[22] = COM.accLocation_CALLBACK(arrayOfInt2[22]);
    arrayOfInt2[23] = COM.accNavigate_CALLBACK(arrayOfInt2[23]);
    arrayOfInt2[25] = COM.accDoDefaultAction_CALLBACK(arrayOfInt2[25]);
    arrayOfInt2[26] = COM.put_accName_CALLBACK(arrayOfInt2[26]);
    arrayOfInt2[27] = COM.put_accValue_CALLBACK(arrayOfInt2[27]);
    COM.MoveMemory(arrayOfInt1[0], arrayOfInt2, OS.PTR_SIZEOF * arrayOfInt2.length);
  }

  void createIAccessible2()
  {
    this.objIAccessible2 = new COMObject(new int[] { 2, 0, 0, 1, 3, 5, 8, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 2, 1, 1, 2, 2, 5, 3, 3, 1, 2, 2, 1, 2, 3, 1, 1, 3, 3, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accParent(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accChildCount(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accChild(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accValue(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accDescription(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accRole(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accState(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accHelp(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accHelpTopic(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accKeyboardShortcut(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accFocus(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accSelection(paramAnonymousArrayOfInt[0]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_accDefaultAction(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accSelect(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method22(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accLocation(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method23(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accNavigate(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method24(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accHitTest(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method25(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.accDoDefaultAction(paramAnonymousArrayOfInt[0]);
      }

      public int method26(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.put_accName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method27(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.put_accValue(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method28(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nRelations(paramAnonymousArrayOfInt[0]);
      }

      public int method29(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_relation(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method30(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_relations(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method31(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_role(paramAnonymousArrayOfInt[0]);
      }

      public int method32(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollTo(paramAnonymousArrayOfInt[0]);
      }

      public int method33(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollToPoint(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method34(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_groupPosition(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method35(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_states(paramAnonymousArrayOfInt[0]);
      }

      public int method36(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_extendedRole(paramAnonymousArrayOfInt[0]);
      }

      public int method37(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_localizedExtendedRole(paramAnonymousArrayOfInt[0]);
      }

      public int method38(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nExtendedStates(paramAnonymousArrayOfInt[0]);
      }

      public int method39(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_extendedStates(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method40(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_localizedExtendedStates(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method41(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_uniqueID(paramAnonymousArrayOfInt[0]);
      }

      public int method42(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_windowHandle(paramAnonymousArrayOfInt[0]);
      }

      public int method43(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_indexInParent(paramAnonymousArrayOfInt[0]);
      }

      public int method44(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_locale(paramAnonymousArrayOfInt[0]);
      }

      public int method45(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_attributes(paramAnonymousArrayOfInt[0]);
      }
    };
    int i = this.objIAccessible2.ppVtable;
    int[] arrayOfInt1 = new int[1];
    COM.MoveMemory(arrayOfInt1, i, OS.PTR_SIZEOF);
    int[] arrayOfInt2 = new int[28];
    COM.MoveMemory(arrayOfInt2, arrayOfInt1[0], OS.PTR_SIZEOF * arrayOfInt2.length);
    arrayOfInt2[9] = COM.get_accChild_CALLBACK(arrayOfInt2[9]);
    arrayOfInt2[10] = COM.get_accName_CALLBACK(arrayOfInt2[10]);
    arrayOfInt2[11] = COM.get_accValue_CALLBACK(arrayOfInt2[11]);
    arrayOfInt2[12] = COM.get_accDescription_CALLBACK(arrayOfInt2[12]);
    arrayOfInt2[13] = COM.get_accRole_CALLBACK(arrayOfInt2[13]);
    arrayOfInt2[14] = COM.get_accState_CALLBACK(arrayOfInt2[14]);
    arrayOfInt2[15] = COM.get_accHelp_CALLBACK(arrayOfInt2[15]);
    arrayOfInt2[16] = COM.get_accHelpTopic_CALLBACK(arrayOfInt2[16]);
    arrayOfInt2[17] = COM.get_accKeyboardShortcut_CALLBACK(arrayOfInt2[17]);
    arrayOfInt2[20] = COM.get_accDefaultAction_CALLBACK(arrayOfInt2[20]);
    arrayOfInt2[21] = COM.accSelect_CALLBACK(arrayOfInt2[21]);
    arrayOfInt2[22] = COM.accLocation_CALLBACK(arrayOfInt2[22]);
    arrayOfInt2[23] = COM.accNavigate_CALLBACK(arrayOfInt2[23]);
    arrayOfInt2[25] = COM.accDoDefaultAction_CALLBACK(arrayOfInt2[25]);
    arrayOfInt2[26] = COM.put_accName_CALLBACK(arrayOfInt2[26]);
    arrayOfInt2[27] = COM.put_accValue_CALLBACK(arrayOfInt2[27]);
    COM.MoveMemory(arrayOfInt1[0], arrayOfInt2, OS.PTR_SIZEOF * arrayOfInt2.length);
  }

  void createIAccessibleAction()
  {
    this.objIAccessibleAction = new COMObject(new int[] { 2, 0, 0, 1, 1, 2, 4, 2, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nActions(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.doAction(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_description(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_keyBinding(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_name(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_localizedName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
  }

  void createIAccessibleApplication()
  {
    this.objIAccessibleApplication = new COMObject(new int[] { 2, 0, 0, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_appName(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_appVersion(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_toolkitName(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_toolkitVersion(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIAccessibleHyperlink()
  {
    this.objIAccessibleHyperlink = new COMObject(new int[] { 2, 0, 0, 1, 1, 2, 4, 2, 2, 2, 2, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nActions(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.doAction(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_description(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_keyBinding(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_name(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_localizedName(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_anchor(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_anchorTarget(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_startIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_endIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_valid(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIAccessibleHypertext()
  {
    this.objIAccessibleHypertext = new COMObject(new int[] { 2, 0, 0, 2, 4, 1, 6, 1, 4, 3, 3, 5, 5, 5, 1, 1, 3, 1, 3, 5, 1, 1, 1, 2, 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.addSelection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_attributes(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_caretOffset(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_characterExtents(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nSelections(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_offsetAtPoint(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_selection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_text(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textBeforeOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textAfterOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textAtOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.removeSelection(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.setCaretOffset(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.setSelection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nCharacters(paramAnonymousArrayOfInt[0]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollSubstringTo(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollSubstringToPoint(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_newText(paramAnonymousArrayOfInt[0]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_oldText(paramAnonymousArrayOfInt[0]);
      }

      public int method22(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nHyperlinks(paramAnonymousArrayOfInt[0]);
      }

      public int method23(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_hyperlink(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method24(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_hyperlinkIndex(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }
    };
  }

  void createIAccessibleTable2()
  {
    this.objIAccessibleTable2 = new COMObject(new int[] { 2, 0, 0, 3, 1, 2, 1, 1, 1, 1, 1, 2, 2, 2, 2, 1, 2, 2, 1, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_cellAt(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_caption(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_columnDescription(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nColumns(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nRows(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nSelectedCells(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nSelectedColumns(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nSelectedRows(paramAnonymousArrayOfInt[0]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_rowDescription(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_selectedCells(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_selectedColumns(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_selectedRows(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_summary(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_isColumnSelected(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_isRowSelected(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.selectRow(paramAnonymousArrayOfInt[0]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.selectColumn(paramAnonymousArrayOfInt[0]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.unselectRow(paramAnonymousArrayOfInt[0]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.unselectColumn(paramAnonymousArrayOfInt[0]);
      }

      public int method22(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_modelChange(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIAccessibleTableCell()
  {
    this.objIAccessibleTableCell = new COMObject(new int[] { 2, 0, 0, 1, 2, 1, 1, 2, 1, 1, 5, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_columnExtent(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_columnHeaderCells(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_columnIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_rowExtent(paramAnonymousArrayOfInt[0]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_rowHeaderCells(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_rowIndex(paramAnonymousArrayOfInt[0]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_isSelected(paramAnonymousArrayOfInt[0]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_rowColumnExtents(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_table(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIAccessibleText()
  {
    this.objIAccessibleText = new COMObject(new int[] { 2, 0, 0, 2, 4, 1, 6, 1, 4, 3, 3, 5, 5, 5, 1, 1, 3, 1, 3, 5, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.addSelection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_attributes(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_caretOffset(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_characterExtents(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nSelections(paramAnonymousArrayOfInt[0]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_offsetAtPoint(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_selection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_text(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textBeforeOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textAfterOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_textAtOffset(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method14(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.removeSelection(paramAnonymousArrayOfInt[0]);
      }

      public int method15(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.setCaretOffset(paramAnonymousArrayOfInt[0]);
      }

      public int method16(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.setSelection(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method17(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_nCharacters(paramAnonymousArrayOfInt[0]);
      }

      public int method18(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollSubstringTo(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method19(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.scrollSubstringToPoint(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method20(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_newText(paramAnonymousArrayOfInt[0]);
      }

      public int method21(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_oldText(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIAccessibleValue()
  {
    this.objIAccessibleValue = new COMObject(new int[] { 2, 0, 0, 1, 1, 1, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_currentValue(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.setCurrentValue(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_maximumValue(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.get_minimumValue(paramAnonymousArrayOfInt[0]);
      }
    };
    int i = this.objIAccessibleValue.ppVtable;
    int[] arrayOfInt1 = new int[1];
    COM.MoveMemory(arrayOfInt1, i, OS.PTR_SIZEOF);
    int[] arrayOfInt2 = new int[7];
    COM.MoveMemory(arrayOfInt2, arrayOfInt1[0], OS.PTR_SIZEOF * arrayOfInt2.length);
    arrayOfInt2[4] = COM.CALLBACK_setCurrentValue(arrayOfInt2[4]);
    COM.MoveMemory(arrayOfInt1[0], arrayOfInt2, OS.PTR_SIZEOF * arrayOfInt2.length);
  }

  void createIEnumVARIANT()
  {
    this.objIEnumVARIANT = new COMObject(new int[] { 2, 0, 0, 3, 1, 0, 1 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Next(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Skip(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Reset();
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Clone(paramAnonymousArrayOfInt[0]);
      }
    };
  }

  void createIServiceProvider()
  {
    this.objIServiceProvider = new COMObject(new int[] { 2, 0, 0, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Accessible.this.QueryService(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
  }

  public static Accessible internal_new_Accessible(Control paramControl)
  {
    return new Accessible(paramControl);
  }

  public void addAccessibleListener(AccessibleListener paramAccessibleListener)
  {
    checkWidget();
    if (paramAccessibleListener == null)
      SWT.error(4);
    this.accessibleListeners.addElement(paramAccessibleListener);
  }

  public void addAccessibleControlListener(AccessibleControlListener paramAccessibleControlListener)
  {
    checkWidget();
    if (paramAccessibleControlListener == null)
      SWT.error(4);
    this.accessibleControlListeners.addElement(paramAccessibleControlListener);
  }

  public void addAccessibleTextListener(AccessibleTextListener paramAccessibleTextListener)
  {
    checkWidget();
    if (paramAccessibleTextListener == null)
      SWT.error(4);
    if ((paramAccessibleTextListener instanceof AccessibleTextExtendedListener))
      this.accessibleTextExtendedListeners.addElement(paramAccessibleTextListener);
    else
      this.accessibleTextListeners.addElement(paramAccessibleTextListener);
  }

  public void addAccessibleActionListener(AccessibleActionListener paramAccessibleActionListener)
  {
    checkWidget();
    if (paramAccessibleActionListener == null)
      SWT.error(4);
    this.accessibleActionListeners.addElement(paramAccessibleActionListener);
  }

  public void addAccessibleHyperlinkListener(AccessibleHyperlinkListener paramAccessibleHyperlinkListener)
  {
    checkWidget();
    if (paramAccessibleHyperlinkListener == null)
      SWT.error(4);
    this.accessibleHyperlinkListeners.addElement(paramAccessibleHyperlinkListener);
  }

  public void addAccessibleTableListener(AccessibleTableListener paramAccessibleTableListener)
  {
    checkWidget();
    if (paramAccessibleTableListener == null)
      SWT.error(4);
    this.accessibleTableListeners.addElement(paramAccessibleTableListener);
  }

  public void addAccessibleTableCellListener(AccessibleTableCellListener paramAccessibleTableCellListener)
  {
    checkWidget();
    if (paramAccessibleTableCellListener == null)
      SWT.error(4);
    this.accessibleTableCellListeners.addElement(paramAccessibleTableCellListener);
  }

  public void addAccessibleValueListener(AccessibleValueListener paramAccessibleValueListener)
  {
    checkWidget();
    if (paramAccessibleValueListener == null)
      SWT.error(4);
    this.accessibleValueListeners.addElement(paramAccessibleValueListener);
  }

  public void addAccessibleAttributeListener(AccessibleAttributeListener paramAccessibleAttributeListener)
  {
    checkWidget();
    if (paramAccessibleAttributeListener == null)
      SWT.error(4);
    this.accessibleAttributeListeners.addElement(paramAccessibleAttributeListener);
  }

  public void addRelation(int paramInt, Accessible paramAccessible)
  {
    checkWidget();
    if (this.relations[paramInt] == null)
      this.relations[paramInt] = new Relation(this, paramInt);
    this.relations[paramInt].addTarget(paramAccessible);
  }

  public void dispose()
  {
    if (this.parent == null)
      return;
    Release();
    this.parent.children.removeElement(this);
    this.parent = null;
  }

  int getAddress()
  {
    if (this.objIAccessible == null)
      createIAccessible();
    return this.objIAccessible.getAddress();
  }

  public Control getControl()
  {
    return this.control;
  }

  public void internal_dispose_Accessible()
  {
    if (this.iaccessible != null)
      this.iaccessible.Release();
    this.iaccessible = null;
    Release();
    for (int i = 0; i < this.children.size(); i++)
    {
      Accessible localAccessible = (Accessible)this.children.elementAt(i);
      localAccessible.dispose();
    }
  }

  public int internal_WM_GETOBJECT(int paramInt1, int paramInt2)
  {
    if (this.objIAccessible == null)
      return 0;
    if (paramInt2 == -4)
      return COM.LresultFromObject(COM.IIDIAccessible, paramInt1, this.objIAccessible.getAddress());
    return 0;
  }

  public void removeAccessibleListener(AccessibleListener paramAccessibleListener)
  {
    checkWidget();
    if (paramAccessibleListener == null)
      SWT.error(4);
    this.accessibleListeners.removeElement(paramAccessibleListener);
  }

  public void removeAccessibleControlListener(AccessibleControlListener paramAccessibleControlListener)
  {
    checkWidget();
    if (paramAccessibleControlListener == null)
      SWT.error(4);
    this.accessibleControlListeners.removeElement(paramAccessibleControlListener);
  }

  public void removeAccessibleTextListener(AccessibleTextListener paramAccessibleTextListener)
  {
    checkWidget();
    if (paramAccessibleTextListener == null)
      SWT.error(4);
    if ((paramAccessibleTextListener instanceof AccessibleTextExtendedListener))
      this.accessibleTextExtendedListeners.removeElement(paramAccessibleTextListener);
    else
      this.accessibleTextListeners.removeElement(paramAccessibleTextListener);
  }

  public void removeAccessibleActionListener(AccessibleActionListener paramAccessibleActionListener)
  {
    checkWidget();
    if (paramAccessibleActionListener == null)
      SWT.error(4);
    this.accessibleActionListeners.removeElement(paramAccessibleActionListener);
  }

  public void removeAccessibleHyperlinkListener(AccessibleHyperlinkListener paramAccessibleHyperlinkListener)
  {
    checkWidget();
    if (paramAccessibleHyperlinkListener == null)
      SWT.error(4);
    this.accessibleHyperlinkListeners.removeElement(paramAccessibleHyperlinkListener);
  }

  public void removeAccessibleTableListener(AccessibleTableListener paramAccessibleTableListener)
  {
    checkWidget();
    if (paramAccessibleTableListener == null)
      SWT.error(4);
    this.accessibleTableListeners.removeElement(paramAccessibleTableListener);
  }

  public void removeAccessibleTableCellListener(AccessibleTableCellListener paramAccessibleTableCellListener)
  {
    checkWidget();
    if (paramAccessibleTableCellListener == null)
      SWT.error(4);
    this.accessibleTableCellListeners.removeElement(paramAccessibleTableCellListener);
  }

  public void removeAccessibleValueListener(AccessibleValueListener paramAccessibleValueListener)
  {
    checkWidget();
    if (paramAccessibleValueListener == null)
      SWT.error(4);
    this.accessibleValueListeners.removeElement(paramAccessibleValueListener);
  }

  public void removeAccessibleAttributeListener(AccessibleAttributeListener paramAccessibleAttributeListener)
  {
    checkWidget();
    if (paramAccessibleAttributeListener == null)
      SWT.error(4);
    this.accessibleAttributeListeners.removeElement(paramAccessibleAttributeListener);
  }

  public void removeRelation(int paramInt, Accessible paramAccessible)
  {
    checkWidget();
    Relation localRelation = this.relations[paramInt];
    if (localRelation != null)
    {
      localRelation.removeTarget(paramAccessible);
      if (!localRelation.hasTargets())
      {
        this.relations[paramInt].Release();
        this.relations[paramInt] = null;
      }
    }
  }

  public void sendEvent(int paramInt, Object paramObject)
  {
    checkWidget();
    if (!UseIA2)
      return;
    switch (paramInt)
    {
    case 518:
      if (((paramObject instanceof int[])) && (((int[])paramObject).length == 5))
      {
        this.tableChange = ((int[])paramObject);
        COM.NotifyWinEvent(518, this.control.handle, -4, eventChildID());
      }
      break;
    case 524:
      if (((paramObject instanceof Object[])) && (((Object[])paramObject).length == 4))
      {
        Object[] arrayOfObject = (Object[])paramObject;
        int i = ((Integer)arrayOfObject[0]).intValue();
        switch (i)
        {
        case 1:
          this.textDeleted = ((Object[])paramObject);
          COM.NotifyWinEvent(527, this.control.handle, -4, eventChildID());
          break;
        case 0:
          this.textInserted = ((Object[])paramObject);
          COM.NotifyWinEvent(526, this.control.handle, -4, eventChildID());
        }
      }
      break;
    case 268:
      if ((paramObject instanceof Integer))
        COM.NotifyWinEvent(268, this.control.handle, -4, eventChildID());
      break;
    case 32782:
      COM.NotifyWinEvent(32782, this.control.handle, -4, eventChildID());
      break;
    case 32778:
      COM.NotifyWinEvent(32778, this.control.handle, -4, eventChildID());
      break;
    case 32777:
      COM.NotifyWinEvent(32777, this.control.handle, -4, eventChildID());
      break;
    case 32788:
      COM.NotifyWinEvent(32788, this.control.handle, -4, eventChildID());
      break;
    case 32779:
      COM.NotifyWinEvent(32779, this.control.handle, -4, eventChildID());
      break;
    case 32780:
      COM.NotifyWinEvent(32780, this.control.handle, -4, eventChildID());
      break;
    case 32781:
      COM.NotifyWinEvent(32781, this.control.handle, -4, eventChildID());
      break;
    case 261:
      COM.NotifyWinEvent(261, this.control.handle, -4, eventChildID());
      break;
    case 262:
      COM.NotifyWinEvent(262, this.control.handle, -4, eventChildID());
      break;
    case 263:
      COM.NotifyWinEvent(263, this.control.handle, -4, eventChildID());
      break;
    case 273:
      COM.NotifyWinEvent(273, this.control.handle, -4, eventChildID());
      break;
    case 274:
      COM.NotifyWinEvent(274, this.control.handle, -4, eventChildID());
      break;
    case 256:
      COM.NotifyWinEvent(256, this.control.handle, -4, eventChildID());
      break;
    case 269:
      COM.NotifyWinEvent(269, this.control.handle, -4, eventChildID());
      break;
    case 264:
      COM.NotifyWinEvent(264, this.control.handle, -4, eventChildID());
      break;
    case 265:
      COM.NotifyWinEvent(265, this.control.handle, -4, eventChildID());
      break;
    case 266:
      COM.NotifyWinEvent(266, this.control.handle, -4, eventChildID());
      break;
    case 267:
      COM.NotifyWinEvent(267, this.control.handle, -4, eventChildID());
      break;
    case 271:
      COM.NotifyWinEvent(271, this.control.handle, -4, eventChildID());
      break;
    case 512:
      COM.NotifyWinEvent(512, this.control.handle, -4, eventChildID());
      break;
    case 515:
      COM.NotifyWinEvent(515, this.control.handle, -4, eventChildID());
      break;
    case 516:
      COM.NotifyWinEvent(516, this.control.handle, -4, eventChildID());
      break;
    case 517:
      COM.NotifyWinEvent(517, this.control.handle, -4, eventChildID());
      break;
    case 519:
      COM.NotifyWinEvent(519, this.control.handle, -4, eventChildID());
      break;
    case 520:
      COM.NotifyWinEvent(520, this.control.handle, -4, eventChildID());
      break;
    case 521:
      COM.NotifyWinEvent(521, this.control.handle, -4, eventChildID());
      break;
    case 522:
      COM.NotifyWinEvent(522, this.control.handle, -4, eventChildID());
      break;
    case 283:
      COM.NotifyWinEvent(283, this.control.handle, -4, eventChildID());
      break;
    case 285:
      COM.NotifyWinEvent(285, this.control.handle, -4, eventChildID());
    }
  }

  public void selectionChanged()
  {
    checkWidget();
    COM.NotifyWinEvent(32777, this.control.handle, -4, eventChildID());
  }

  public void setFocus(int paramInt)
  {
    checkWidget();
    int i = paramInt == -1 ? eventChildID() : childIDToOs(paramInt);
    COM.NotifyWinEvent(32773, this.control.handle, -4, i);
  }

  public void textCaretMoved(int paramInt)
  {
    checkWidget();
    COM.NotifyWinEvent(32779, this.control.handle, -8, eventChildID());
    if (!UseIA2)
      return;
    COM.NotifyWinEvent(283, this.control.handle, -4, eventChildID());
  }

  public void textChanged(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = paramInt2;
    localAccessibleTextEvent.end = (paramInt2 + paramInt3);
    localAccessibleTextEvent.count = 0;
    localAccessibleTextEvent.type = 5;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getText(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.result != null)
    {
      Object[] arrayOfObject = { new Integer(paramInt1), new Integer(paramInt2), new Integer(paramInt2 + paramInt3), localAccessibleTextEvent.result };
      sendEvent(524, arrayOfObject);
      return;
    }
    COM.NotifyWinEvent(32782, this.control.handle, -4, eventChildID());
  }

  public void textSelectionChanged()
  {
    checkWidget();
    COM.NotifyWinEvent(32782, this.control.handle, -4, eventChildID());
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    COM.MoveMemory(paramInt2, new int[1], OS.PTR_SIZEOF);
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if (COM.IsEqualGUID(localGUID, COM.IIDIUnknown))
    {
      COM.MoveMemory(paramInt2, new int[] { getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if ((COM.IsEqualGUID(localGUID, COM.IIDIDispatch)) || (COM.IsEqualGUID(localGUID, COM.IIDIAccessible)))
    {
      if (this.objIAccessible == null)
        createIAccessible();
      COM.MoveMemory(paramInt2, new int[] { this.objIAccessible.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIEnumVARIANT))
    {
      if (this.objIEnumVARIANT == null)
        createIEnumVARIANT();
      COM.MoveMemory(paramInt2, new int[] { this.objIEnumVARIANT.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      this.enumIndex = 0;
      return 0;
    }
    if (COM.IsEqualGUID(localGUID, COM.IIDIServiceProvider))
    {
      if (!UseIA2)
        return -2147467262;
      if ((this.accessibleActionListeners.size() > 0) || (this.accessibleAttributeListeners.size() > 0) || (this.accessibleHyperlinkListeners.size() > 0) || (this.accessibleTableListeners.size() > 0) || (this.accessibleTableCellListeners.size() > 0) || (this.accessibleTextExtendedListeners.size() > 0) || (this.accessibleValueListeners.size() > 0) || (getRelationCount() > 0))
      {
        if (this.objIServiceProvider == null)
          createIServiceProvider();
        COM.MoveMemory(paramInt2, new int[] { this.objIServiceProvider.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    int i = queryAccessible2Interfaces(localGUID, paramInt2);
    if (i != 1)
      return i;
    if (this.iaccessible != null)
    {
      int[] arrayOfInt = new int[1];
      i = this.iaccessible.QueryInterface(localGUID, arrayOfInt);
      COM.MoveMemory(paramInt2, arrayOfInt, OS.PTR_SIZEOF);
      return i;
    }
    return -2147467262;
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
    {
      if (this.objIAccessible != null)
        this.objIAccessible.dispose();
      this.objIAccessible = null;
      if (this.objIEnumVARIANT != null)
        this.objIEnumVARIANT.dispose();
      this.objIEnumVARIANT = null;
      if (this.objIServiceProvider != null)
        this.objIServiceProvider.dispose();
      this.objIServiceProvider = null;
      if (this.objIAccessible2 != null)
        this.objIAccessible2.dispose();
      this.objIAccessible2 = null;
      if (this.objIAccessibleAction != null)
        this.objIAccessibleAction.dispose();
      this.objIAccessibleAction = null;
      if (this.objIAccessibleApplication != null)
        this.objIAccessibleApplication.dispose();
      this.objIAccessibleApplication = null;
      if (this.objIAccessibleHyperlink != null)
        this.objIAccessibleHyperlink.dispose();
      this.objIAccessibleHyperlink = null;
      if (this.objIAccessibleHypertext != null)
        this.objIAccessibleHypertext.dispose();
      this.objIAccessibleHypertext = null;
      if (this.objIAccessibleTable2 != null)
        this.objIAccessibleTable2.dispose();
      this.objIAccessibleTable2 = null;
      if (this.objIAccessibleTableCell != null)
        this.objIAccessibleTableCell.dispose();
      this.objIAccessibleTableCell = null;
      if (this.objIAccessibleText != null)
        this.objIAccessibleText.dispose();
      this.objIAccessibleText = null;
      if (this.objIAccessibleValue != null)
        this.objIAccessibleValue.dispose();
      this.objIAccessibleValue = null;
      for (int i = 0; i < this.relations.length; i++)
        if (this.relations[i] != null)
          this.relations[i].Release();
    }
    return this.refCount;
  }

  int QueryService(int paramInt1, int paramInt2, int paramInt3)
  {
    COM.MoveMemory(paramInt3, new int[1], OS.PTR_SIZEOF);
    GUID localGUID1 = new GUID();
    COM.MoveMemory(localGUID1, paramInt1, GUID.sizeof);
    GUID localGUID2 = new GUID();
    COM.MoveMemory(localGUID2, paramInt2, GUID.sizeof);
    int i;
    if (COM.IsEqualGUID(localGUID1, COM.IIDIAccessible))
    {
      if ((COM.IsEqualGUID(localGUID2, COM.IIDIUnknown)) || ((COM.IsEqualGUID(localGUID2, COM.IIDIDispatch) | COM.IsEqualGUID(localGUID2, COM.IIDIAccessible))))
      {
        if (this.objIAccessible == null)
          createIAccessible();
        COM.MoveMemory(paramInt3, new int[] { this.objIAccessible.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      i = queryAccessible2Interfaces(localGUID2, paramInt3);
      if (i != 1)
        return i;
    }
    if (COM.IsEqualGUID(localGUID1, COM.IIDIAccessible2))
    {
      i = queryAccessible2Interfaces(localGUID2, paramInt3);
      if (i != 1)
        return i;
    }
    if (this.iaccessible != null)
    {
      int[] arrayOfInt1 = new int[1];
      int j = this.iaccessible.QueryInterface(COM.IIDIServiceProvider, arrayOfInt1);
      if (j == 0)
      {
        IServiceProvider localIServiceProvider = new IServiceProvider(arrayOfInt1[0]);
        int[] arrayOfInt2 = new int[1];
        j = localIServiceProvider.QueryService(localGUID1, localGUID2, arrayOfInt2);
        COM.MoveMemory(paramInt3, arrayOfInt2, OS.PTR_SIZEOF);
        return j;
      }
    }
    return -2147467262;
  }

  int queryAccessible2Interfaces(GUID paramGUID, int paramInt)
  {
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessible2))
    {
      if ((this.accessibleActionListeners.size() > 0) || (this.accessibleAttributeListeners.size() > 0) || (this.accessibleHyperlinkListeners.size() > 0) || (this.accessibleTableListeners.size() > 0) || (this.accessibleTableCellListeners.size() > 0) || (this.accessibleTextExtendedListeners.size() > 0) || (this.accessibleValueListeners.size() > 0) || (getRelationCount() > 0))
      {
        if (this.objIAccessible2 == null)
          createIAccessible2();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessible2.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleAction))
    {
      if (this.accessibleActionListeners.size() > 0)
      {
        if (this.objIAccessibleAction == null)
          createIAccessibleAction();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleAction.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleApplication))
    {
      if (this.objIAccessibleApplication == null)
        createIAccessibleApplication();
      COM.MoveMemory(paramInt, new int[] { this.objIAccessibleApplication.getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleComponent))
      return -2147467262;
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleEditableText))
      return -2147467262;
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleHyperlink))
    {
      if (this.accessibleHyperlinkListeners.size() > 0)
      {
        if (this.objIAccessibleHyperlink == null)
          createIAccessibleHyperlink();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleHyperlink.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleHypertext))
    {
      if (this.accessibleTextExtendedListeners.size() > 0)
      {
        if (this.objIAccessibleHypertext == null)
          createIAccessibleHypertext();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleHypertext.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleImage))
      return -2147467262;
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleTable))
      return -2147467262;
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleTable2))
    {
      if (this.accessibleTableListeners.size() > 0)
      {
        if (this.objIAccessibleTable2 == null)
          createIAccessibleTable2();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleTable2.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleTableCell))
    {
      if (this.accessibleTableCellListeners.size() > 0)
      {
        if (this.objIAccessibleTableCell == null)
          createIAccessibleTableCell();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleTableCell.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleText))
    {
      if (this.accessibleTextExtendedListeners.size() > 0)
      {
        if (this.objIAccessibleText == null)
          createIAccessibleText();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleText.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    if (COM.IsEqualGUID(paramGUID, COM.IIDIAccessibleValue))
    {
      if (this.accessibleValueListeners.size() > 0)
      {
        if (this.objIAccessibleValue == null)
          createIAccessibleValue();
        COM.MoveMemory(paramInt, new int[] { this.objIAccessibleValue.getAddress() }, OS.PTR_SIZEOF);
        AddRef();
        return 0;
      }
      return -2147467262;
    }
    return 1;
  }

  int accDoDefaultAction(int paramInt)
  {
    if (this.accessibleActionListeners.size() > 0)
    {
      VARIANT localVARIANT = getVARIANT(paramInt);
      if (localVARIANT.vt != 3)
        return -2147024809;
      if (localVARIANT.lVal == 0)
        return doAction(0);
    }
    int i = -2147352573;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.accDoDefaultAction(paramInt);
      if (i == -2147024809)
        i = -2147352573;
    }
    return i;
  }

  int accHitTest(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -2;
    int j = 0;
    if (this.iaccessible != null)
    {
      int k = this.iaccessible.accHitTest(paramInt1, paramInt2, paramInt3);
      if (k == 0)
      {
        VARIANT localVARIANT = getVARIANT(paramInt3);
        if (localVARIANT.vt == 3)
          i = localVARIANT.lVal;
        else if (localVARIANT.vt == 9)
          j = localVARIANT.lVal;
      }
      if (this.accessibleControlListeners.size() == 0)
        return k;
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = (i == -2 ? -2 : osToChildID(i));
    localAccessibleControlEvent.x = paramInt1;
    localAccessibleControlEvent.y = paramInt2;
    for (int m = 0; m < this.accessibleControlListeners.size(); m++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(m);
      localAccessibleControlListener.getChildAtPoint(localAccessibleControlEvent);
    }
    Accessible localAccessible = localAccessibleControlEvent.accessible;
    if (localAccessible != null)
    {
      localAccessible.AddRef();
      setPtrVARIANT(paramInt3, (short)9, localAccessible.getAddress());
      return 0;
    }
    int n = localAccessibleControlEvent.childID;
    if (n == -2)
    {
      if (j != 0)
        return 0;
      setIntVARIANT(paramInt3, (short)0, 0);
      return 1;
    }
    setIntVARIANT(paramInt3, (short)3, childIDToOs(n));
    return 0;
  }

  int accLocation(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    VARIANT localVARIANT = getVARIANT(paramInt5);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    Object localObject;
    if (this.iaccessible != null)
    {
      int n = this.iaccessible.accLocation(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      if (n == -2147024809)
        n = -2147352573;
      if (this.accessibleControlListeners.size() == 0)
        return n;
      if (n == 0)
      {
        int[] arrayOfInt1 = new int[1];
        localObject = new int[1];
        int[] arrayOfInt2 = new int[1];
        int[] arrayOfInt3 = new int[1];
        COM.MoveMemory(arrayOfInt1, paramInt1, 4);
        COM.MoveMemory((int[])localObject, paramInt2, 4);
        COM.MoveMemory(arrayOfInt2, paramInt3, 4);
        COM.MoveMemory(arrayOfInt3, paramInt4, 4);
        i = arrayOfInt1[0];
        j = localObject[0];
        k = arrayOfInt2[0];
        m = arrayOfInt3[0];
      }
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = osToChildID(localVARIANT.lVal);
    localAccessibleControlEvent.x = i;
    localAccessibleControlEvent.y = j;
    localAccessibleControlEvent.width = k;
    localAccessibleControlEvent.height = m;
    for (int i1 = 0; i1 < this.accessibleControlListeners.size(); i1++)
    {
      localObject = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i1);
      ((AccessibleControlListener)localObject).getLocation(localAccessibleControlEvent);
    }
    OS.MoveMemory(paramInt1, new int[] { localAccessibleControlEvent.x }, 4);
    OS.MoveMemory(paramInt2, new int[] { localAccessibleControlEvent.y }, 4);
    OS.MoveMemory(paramInt3, new int[] { localAccessibleControlEvent.width }, 4);
    OS.MoveMemory(paramInt4, new int[] { localAccessibleControlEvent.height }, 4);
    return 0;
  }

  int accNavigate(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -2147352573;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.accNavigate(paramInt1, paramInt2, paramInt3);
      if (i == -2147024809)
        i = -2147352573;
    }
    return i;
  }

  int accSelect(int paramInt1, int paramInt2)
  {
    int i = -2147352573;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.accSelect(paramInt1, paramInt2);
      if (i == -2147024809)
        i = -2147352573;
    }
    return i;
  }

  int get_accChild(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    if (localVARIANT.lVal == 0)
    {
      AddRef();
      COM.MoveMemory(paramInt2, new int[] { getAddress() }, OS.PTR_SIZEOF);
      return 0;
    }
    int i = osToChildID(localVARIANT.lVal);
    int j = 1;
    Accessible localAccessible1 = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      j = this.iaccessible.get_accChild(paramInt1, paramInt2);
      if (j == -2147024809)
        j = 1;
      if ((j == 0) && ((this.control instanceof ToolBar)))
      {
        localObject1 = (ToolBar)this.control;
        ToolItem localToolItem = ((ToolBar)localObject1).getItem(i);
        if ((localToolItem != null) && ((localToolItem.getStyle() & 0x4) != 0))
        {
          localObject2 = new int[1];
          COM.MoveMemory((int[])localObject2, paramInt2, OS.PTR_SIZEOF);
          int m = 0;
          for (int n = 0; n < this.children.size(); n++)
          {
            Accessible localAccessible3 = (Accessible)this.children.elementAt(n);
            if (localAccessible3.item == localToolItem)
            {
              localAccessible3.dispose();
              localAccessible3.item = null;
              m = 1;
              break;
            }
          }
          localAccessible1 = new Accessible(this, localObject2[0]);
          localAccessible1.item = localToolItem;
          if (m == 0)
            localToolItem.addListener(12, new Listener()
            {
              private final ToolItem val$item;

              public void handleEvent(Event paramAnonymousEvent)
              {
                for (int i = 0; i < Accessible.this.children.size(); i++)
                {
                  Accessible localAccessible = (Accessible)Accessible.this.children.elementAt(i);
                  if (localAccessible.item == this.val$item)
                    localAccessible.dispose();
                }
              }
            });
          localAccessible1.addAccessibleListener(new AccessibleAdapter()
          {
            private final int val$childID;

            public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
            {
              if (paramAnonymousAccessibleEvent.childID == -1)
              {
                AccessibleEvent localAccessibleEvent = new AccessibleEvent(Accessible.this);
                localAccessibleEvent.childID = this.val$childID;
                for (int i = 0; i < Accessible.this.accessibleListeners.size(); i++)
                {
                  AccessibleListener localAccessibleListener = (AccessibleListener)Accessible.this.accessibleListeners.elementAt(i);
                  localAccessibleListener.getName(localAccessibleEvent);
                }
                paramAnonymousAccessibleEvent.result = localAccessibleEvent.result;
              }
            }
          });
        }
      }
    }
    Object localObject1 = new AccessibleControlEvent(this);
    ((AccessibleControlEvent)localObject1).childID = i;
    for (int k = 0; k < this.accessibleControlListeners.size(); k++)
    {
      localObject2 = (AccessibleControlListener)this.accessibleControlListeners.elementAt(k);
      ((AccessibleControlListener)localObject2).getChild((AccessibleControlEvent)localObject1);
    }
    Accessible localAccessible2 = ((AccessibleControlEvent)localObject1).accessible;
    if (localAccessible2 == null)
      localAccessible2 = localAccessible1;
    if (localAccessible2 != null)
    {
      localAccessible2.AddRef();
      COM.MoveMemory(paramInt2, new int[] { localAccessible2.getAddress() }, OS.PTR_SIZEOF);
      return 0;
    }
    return j;
  }

  int get_accChildCount(int paramInt)
  {
    int i = 0;
    if (this.iaccessible != null)
    {
      int j = this.iaccessible.get_accChildCount(paramInt);
      if (j == 0)
      {
        int[] arrayOfInt = new int[1];
        COM.MoveMemory(arrayOfInt, paramInt, 4);
        i = arrayOfInt[0];
      }
      if (this.accessibleControlListeners.size() == 0)
        return j;
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = -1;
    localAccessibleControlEvent.detail = i;
    for (int k = 0; k < this.accessibleControlListeners.size(); k++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(k);
      localAccessibleControlListener.getChildCount(localAccessibleControlEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleControlEvent.detail }, 4);
    return 0;
  }

  int get_accDefaultAction(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = -2147352573;
    String str = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accDefaultAction(paramInt1, paramInt2);
      if (i == -2147024809)
        i = 1;
      if (this.accessibleControlListeners.size() == 0)
        return i;
      if (i == 0)
      {
        localObject1 = new int[1];
        COM.MoveMemory((int[])localObject1, paramInt2, OS.PTR_SIZEOF);
        j = COM.SysStringByteLen(localObject1[0]);
        if (j > 0)
        {
          localObject2 = new char[(j + 1) / 2];
          COM.MoveMemory((char[])localObject2, localObject1[0], j);
          str = new String((char[])localObject2);
        }
      }
    }
    Object localObject1 = new AccessibleControlEvent(this);
    ((AccessibleControlEvent)localObject1).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleControlEvent)localObject1).result = str;
    for (int j = 0; j < this.accessibleControlListeners.size(); j++)
    {
      localObject2 = (AccessibleControlListener)this.accessibleControlListeners.elementAt(j);
      ((AccessibleControlListener)localObject2).getDefaultAction((AccessibleControlEvent)localObject1);
    }
    if (((((AccessibleControlEvent)localObject1).result == null) || (((AccessibleControlEvent)localObject1).result.length() == 0)) && (localVARIANT.lVal == 0))
      i = get_name(0, paramInt2);
    if (((AccessibleControlEvent)localObject1).result == null)
      return i;
    if (((AccessibleControlEvent)localObject1).result.length() == 0)
      return 1;
    setString(paramInt2, ((AccessibleControlEvent)localObject1).result);
    return 0;
  }

  int get_accDescription(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = -2147352573;
    String str = null;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accDescription(paramInt1, paramInt2);
      if (i == -2147024809)
        i = 1;
      if ((this.accessibleListeners.size() == 0) && (!(this.control instanceof Tree)))
        return i;
      if (i == 0)
      {
        localObject = new int[1];
        COM.MoveMemory((int[])localObject, paramInt2, OS.PTR_SIZEOF);
        int j = COM.SysStringByteLen(localObject[0]);
        if (j > 0)
        {
          char[] arrayOfChar = new char[(j + 1) / 2];
          COM.MoveMemory(arrayOfChar, localObject[0], j);
          str = new String(arrayOfChar);
        }
      }
    }
    Object localObject = new AccessibleEvent(this);
    ((AccessibleEvent)localObject).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleEvent)localObject).result = str;
    if ((localVARIANT.lVal != 0) && ((this.control instanceof Tree)))
    {
      Tree localTree = (Tree)this.control;
      int m = localTree.getColumnCount();
      if (m > 1)
      {
        int n = this.control.handle;
        int i1 = 0;
        if (OS.COMCTL32_MAJOR >= 6)
          i1 = OS.SendMessage(n, 4394, localVARIANT.lVal, 0);
        else
          i1 = localVARIANT.lVal;
        Widget localWidget = localTree.getDisplay().findWidget(n, i1);
        ((AccessibleEvent)localObject).result = "";
        if ((localWidget != null) && ((localWidget instanceof TreeItem)))
        {
          TreeItem localTreeItem = (TreeItem)localWidget;
          for (int i2 = 1; i2 < m; i2++)
          {
            Object tmp306_304 = localObject;
            tmp306_304.result = (tmp306_304.result + localTree.getColumn(i2).getText() + ": " + localTreeItem.getText(i2));
            if (i2 + 1 < m)
              localObject.result += ", ";
          }
        }
      }
    }
    for (int k = 0; k < this.accessibleListeners.size(); k++)
    {
      AccessibleListener localAccessibleListener = (AccessibleListener)this.accessibleListeners.elementAt(k);
      localAccessibleListener.getDescription((AccessibleEvent)localObject);
    }
    if (((AccessibleEvent)localObject).result == null)
      return i;
    if (((AccessibleEvent)localObject).result.length() == 0)
      return 1;
    setString(paramInt2, ((AccessibleEvent)localObject).result);
    return 0;
  }

  int get_accFocus(int paramInt)
  {
    int i = -2;
    if (this.iaccessible != null)
    {
      int j = this.iaccessible.get_accFocus(paramInt);
      if (j == 0)
      {
        VARIANT localVARIANT = getVARIANT(paramInt);
        if (localVARIANT.vt == 3)
          i = localVARIANT.lVal;
      }
      if (this.accessibleControlListeners.size() == 0)
        return j;
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = (i == -2 ? -2 : osToChildID(i));
    for (int k = 0; k < this.accessibleControlListeners.size(); k++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(k);
      localAccessibleControlListener.getFocus(localAccessibleControlEvent);
    }
    Accessible localAccessible = localAccessibleControlEvent.accessible;
    if (localAccessible != null)
    {
      localAccessible.AddRef();
      setPtrVARIANT(paramInt, (short)9, localAccessible.getAddress());
      return 0;
    }
    int m = localAccessibleControlEvent.childID;
    if (m == -2)
    {
      setIntVARIANT(paramInt, (short)0, 0);
      return 1;
    }
    if (m == -1)
    {
      AddRef();
      setIntVARIANT(paramInt, (short)3, 0);
      return 0;
    }
    setIntVARIANT(paramInt, (short)3, childIDToOs(m));
    return 0;
  }

  int get_accHelp(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = -2147352573;
    String str = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accHelp(paramInt1, paramInt2);
      if (i == -2147024809)
        i = 1;
      if (this.accessibleListeners.size() == 0)
        return i;
      if (i == 0)
      {
        localObject1 = new int[1];
        COM.MoveMemory((int[])localObject1, paramInt2, OS.PTR_SIZEOF);
        j = COM.SysStringByteLen(localObject1[0]);
        if (j > 0)
        {
          localObject2 = new char[(j + 1) / 2];
          COM.MoveMemory((char[])localObject2, localObject1[0], j);
          str = new String((char[])localObject2);
        }
      }
    }
    Object localObject1 = new AccessibleEvent(this);
    ((AccessibleEvent)localObject1).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleEvent)localObject1).result = str;
    for (int j = 0; j < this.accessibleListeners.size(); j++)
    {
      localObject2 = (AccessibleListener)this.accessibleListeners.elementAt(j);
      ((AccessibleListener)localObject2).getHelp((AccessibleEvent)localObject1);
    }
    if (((AccessibleEvent)localObject1).result == null)
      return i;
    if (((AccessibleEvent)localObject1).result.length() == 0)
      return 1;
    setString(paramInt2, ((AccessibleEvent)localObject1).result);
    return 0;
  }

  int get_accHelpTopic(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = -2147352573;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accHelpTopic(paramInt1, paramInt2, paramInt3);
      if (i == -2147024809)
        i = -2147352573;
    }
    return i;
  }

  int get_accKeyboardShortcut(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = -2147352573;
    String str = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accKeyboardShortcut(paramInt1, paramInt2);
      if (i == -2147024809)
        i = 1;
      if (this.accessibleListeners.size() == 0)
        return i;
      if (i == 0)
      {
        localObject1 = new int[1];
        COM.MoveMemory((int[])localObject1, paramInt2, OS.PTR_SIZEOF);
        j = COM.SysStringByteLen(localObject1[0]);
        if (j > 0)
        {
          localObject2 = new char[(j + 1) / 2];
          COM.MoveMemory((char[])localObject2, localObject1[0], j);
          str = new String((char[])localObject2);
        }
      }
    }
    Object localObject1 = new AccessibleEvent(this);
    ((AccessibleEvent)localObject1).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleEvent)localObject1).result = str;
    for (int j = 0; j < this.accessibleListeners.size(); j++)
    {
      localObject2 = (AccessibleListener)this.accessibleListeners.elementAt(j);
      ((AccessibleListener)localObject2).getKeyboardShortcut((AccessibleEvent)localObject1);
    }
    if (((AccessibleEvent)localObject1).result == null)
      return i;
    if (((AccessibleEvent)localObject1).result.length() == 0)
      return 1;
    setString(paramInt2, ((AccessibleEvent)localObject1).result);
    return 0;
  }

  int get_accName(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = 1;
    String str = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accName(paramInt1, paramInt2);
      if (i == 0)
      {
        localObject1 = new int[1];
        COM.MoveMemory((int[])localObject1, paramInt2, OS.PTR_SIZEOF);
        j = COM.SysStringByteLen(localObject1[0]);
        if (j > 0)
        {
          localObject2 = new char[(j + 1) / 2];
          COM.MoveMemory((char[])localObject2, localObject1[0], j);
          str = new String((char[])localObject2);
        }
      }
      if (i == -2147024809)
        i = 1;
      if (this.accessibleListeners.size() == 0)
        return i;
    }
    Object localObject1 = new AccessibleEvent(this);
    ((AccessibleEvent)localObject1).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleEvent)localObject1).result = str;
    for (int j = 0; j < this.accessibleListeners.size(); j++)
    {
      localObject2 = (AccessibleListener)this.accessibleListeners.elementAt(j);
      ((AccessibleListener)localObject2).getName((AccessibleEvent)localObject1);
    }
    if (((AccessibleEvent)localObject1).result == null)
      return i;
    if (((AccessibleEvent)localObject1).result.length() == 0)
      return 1;
    setString(paramInt2, ((AccessibleEvent)localObject1).result);
    return 0;
  }

  int get_accParent(int paramInt)
  {
    int i = -2147352573;
    if (this.iaccessible != null)
      i = this.iaccessible.get_accParent(paramInt);
    if (this.parent != null)
    {
      this.parent.AddRef();
      COM.MoveMemory(paramInt, new int[] { this.parent.getAddress() }, OS.PTR_SIZEOF);
      i = 0;
    }
    return i;
  }

  int get_accRole(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT1 = getVARIANT(paramInt1);
    if (localVARIANT1.vt != 3)
      return -2147024809;
    int i = 10;
    if (this.iaccessible != null)
    {
      int j = this.iaccessible.get_accRole(paramInt1, paramInt2);
      if (j == 0)
      {
        VARIANT localVARIANT2 = getVARIANT(paramInt2);
        if (localVARIANT2.vt == 3)
          i = localVARIANT2.lVal;
      }
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = osToChildID(localVARIANT1.lVal);
    localAccessibleControlEvent.detail = osToRole(i);
    if ((((this.control instanceof Tree)) || ((this.control instanceof Table))) && (localVARIANT1.lVal != 0) && ((this.control.getStyle() & 0x20) != 0))
      localAccessibleControlEvent.detail = 44;
    for (int k = 0; k < this.accessibleControlListeners.size(); k++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(k);
      localAccessibleControlListener.getRole(localAccessibleControlEvent);
    }
    setIntVARIANT(paramInt2, (short)3, roleToOs(localAccessibleControlEvent.detail));
    return 0;
  }

  int get_accSelection(int paramInt)
  {
    int i = -2;
    int j = 0;
    if (this.iaccessible != null)
    {
      int k = this.iaccessible.get_accSelection(paramInt);
      if (this.accessibleControlListeners.size() == 0)
        return k;
      if (k == 0)
      {
        VARIANT localVARIANT = getVARIANT(paramInt);
        if (localVARIANT.vt == 3)
          i = osToChildID(localVARIANT.lVal);
        else if (localVARIANT.vt == 9)
          j = localVARIANT.lVal;
        else if (localVARIANT.vt == 13)
          i = -3;
      }
    }
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = i;
    for (int m = 0; m < this.accessibleControlListeners.size(); m++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(m);
      localAccessibleControlListener.getSelection(localAccessibleControlEvent);
    }
    Accessible localAccessible = localAccessibleControlEvent.accessible;
    if (localAccessible != null)
    {
      localAccessible.AddRef();
      setPtrVARIANT(paramInt, (short)9, localAccessible.getAddress());
      return 0;
    }
    int n = localAccessibleControlEvent.childID;
    if (n == -2)
    {
      if (j != 0)
        return 0;
      setIntVARIANT(paramInt, (short)0, 0);
      return 1;
    }
    if (n == -3)
      return 0;
    if (n == -1)
    {
      AddRef();
      setPtrVARIANT(paramInt, (short)9, getAddress());
      return 0;
    }
    setIntVARIANT(paramInt, (short)3, childIDToOs(n));
    return 0;
  }

  int get_accState(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = 0;
    if (this.iaccessible != null)
    {
      j = this.iaccessible.get_accState(paramInt1, paramInt2);
      if (j == 0)
      {
        localObject = getVARIANT(paramInt2);
        if (((VARIANT)localObject).vt == 3)
          i = ((VARIANT)localObject).lVal;
      }
    }
    int j = 0;
    Object localObject = new AccessibleControlEvent(this);
    ((AccessibleControlEvent)localObject).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleControlEvent)localObject).detail = osToState(i);
    if (localVARIANT.lVal != 0)
      if (((this.control instanceof Tree)) && ((this.control.getStyle() & 0x20) != 0))
      {
        int k = this.control.handle;
        TVITEM localTVITEM = new TVITEM();
        localTVITEM.mask = 24;
        localTVITEM.stateMask = 61440;
        if (OS.COMCTL32_MAJOR >= 6)
          localTVITEM.hItem = OS.SendMessage(k, 4394, localVARIANT.lVal, 0);
        else
          localTVITEM.hItem = localVARIANT.lVal;
        int i1 = OS.SendMessage(k, OS.TVM_GETITEM, 0, localTVITEM);
        int i2 = (i1 != 0) && ((localTVITEM.state >> 12 & 0x1) == 0) ? 1 : 0;
        if (i2 != 0)
          localObject.detail |= 16;
        j = localTVITEM.state >> 12 > 2 ? 1 : 0;
      }
      else if (((this.control instanceof Table)) && ((this.control.getStyle() & 0x20) != 0))
      {
        Table localTable = (Table)this.control;
        int n = ((AccessibleControlEvent)localObject).childID;
        if ((n >= 0) && (n < localTable.getItemCount()))
        {
          TableItem localTableItem = localTable.getItem(n);
          if (localTableItem.getChecked())
            localObject.detail |= 16;
          if (localTableItem.getGrayed())
            j = 1;
        }
      }
    for (int m = 0; m < this.accessibleControlListeners.size(); m++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(m);
      localAccessibleControlListener.getState((AccessibleControlEvent)localObject);
    }
    m = stateToOs(((AccessibleControlEvent)localObject).detail);
    if (((m & 0x10) != 0) && (j != 0))
    {
      m &= -17;
      m |= 32;
    }
    setIntVARIANT(paramInt2, (short)3, m);
    return 0;
  }

  int get_accValue(int paramInt1, int paramInt2)
  {
    VARIANT localVARIANT = getVARIANT(paramInt1);
    if (localVARIANT.vt != 3)
      return -2147024809;
    int i = -2147352573;
    String str = null;
    Object localObject2;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.get_accValue(paramInt1, paramInt2);
      if (i == 0)
      {
        localObject1 = new int[1];
        COM.MoveMemory((int[])localObject1, paramInt2, OS.PTR_SIZEOF);
        j = COM.SysStringByteLen(localObject1[0]);
        if (j > 0)
        {
          localObject2 = new char[(j + 1) / 2];
          COM.MoveMemory((char[])localObject2, localObject1[0], j);
          str = new String((char[])localObject2);
        }
      }
      if (i == -2147024809)
        i = -2147352573;
      if (this.accessibleControlListeners.size() == 0)
        return i;
    }
    Object localObject1 = new AccessibleControlEvent(this);
    ((AccessibleControlEvent)localObject1).childID = osToChildID(localVARIANT.lVal);
    ((AccessibleControlEvent)localObject1).result = str;
    for (int j = 0; j < this.accessibleControlListeners.size(); j++)
    {
      localObject2 = (AccessibleControlListener)this.accessibleControlListeners.elementAt(j);
      ((AccessibleControlListener)localObject2).getValue((AccessibleControlEvent)localObject1);
    }
    if (((AccessibleControlEvent)localObject1).result == null)
      return i;
    setString(paramInt2, ((AccessibleControlEvent)localObject1).result);
    return 0;
  }

  int put_accName(int paramInt1, int paramInt2)
  {
    return -2147467263;
  }

  int put_accValue(int paramInt1, int paramInt2)
  {
    int i = -2147352573;
    if (this.iaccessible != null)
    {
      i = this.iaccessible.put_accValue(paramInt1, paramInt2);
      if (i == -2147024809)
        i = -2147352573;
    }
    return i;
  }

  int Next(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    Object localObject2;
    Object localObject4;
    if ((this.iaccessible != null) && (this.accessibleControlListeners.size() == 0))
    {
      localObject1 = new int[1];
      i = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, (int[])localObject1);
      if (i != 0)
        return i;
      localObject2 = new IEnumVARIANT(localObject1[0]);
      localObject4 = new int[1];
      i = ((IEnumVARIANT)localObject2).Next(paramInt1, paramInt2, (int[])localObject4);
      ((IEnumVARIANT)localObject2).Release();
      COM.MoveMemory(paramInt3, (int[])localObject4, 4);
      return i;
    }
    if (paramInt2 == 0)
      return -2147024809;
    if ((paramInt3 == 0) && (paramInt1 != 1))
      return -2147024809;
    if (this.enumIndex == 0)
    {
      localObject1 = new AccessibleControlEvent(this);
      ((AccessibleControlEvent)localObject1).childID = -1;
      for (i = 0; i < this.accessibleControlListeners.size(); i++)
      {
        localObject2 = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i);
        ((AccessibleControlListener)localObject2).getChildren((AccessibleControlEvent)localObject1);
      }
      this.variants = ((AccessibleControlEvent)localObject1).children;
    }
    Object localObject1 = (Object[])null;
    if ((this.variants != null) && (paramInt1 >= 1))
    {
      i = this.enumIndex + paramInt1 - 1;
      if (i > this.variants.length - 1)
        i = this.variants.length - 1;
      if (this.enumIndex <= i)
      {
        localObject1 = new Object[i - this.enumIndex + 1];
        for (int j = 0; j < localObject1.length; j++)
        {
          localObject4 = this.variants[this.enumIndex];
          if ((localObject4 instanceof Integer))
            localObject1[j] = new Integer(childIDToOs(((Integer)localObject4).intValue()));
          else
            localObject1[j] = localObject4;
          this.enumIndex += 1;
        }
      }
    }
    if (localObject1 != null)
    {
      for (i = 0; i < localObject1.length; i++)
      {
        Object localObject3 = localObject1[i];
        if ((localObject3 instanceof Integer))
        {
          int k = ((Integer)localObject3).intValue();
          setIntVARIANT(paramInt2 + i * VARIANT.sizeof, (short)3, k);
        }
        else
        {
          Accessible localAccessible = (Accessible)localObject3;
          localAccessible.AddRef();
          setPtrVARIANT(paramInt2 + i * VARIANT.sizeof, (short)9, localAccessible.getAddress());
        }
      }
      if (paramInt3 != 0)
        COM.MoveMemory(paramInt3, new int[] { localObject1.length }, 4);
      if (localObject1.length == paramInt1)
        return 0;
    }
    else if (paramInt3 != 0)
    {
      COM.MoveMemory(paramInt3, new int[1], 4);
    }
    return 1;
  }

  int Skip(int paramInt)
  {
    if ((this.iaccessible != null) && (this.accessibleControlListeners.size() == 0))
    {
      int[] arrayOfInt = new int[1];
      int i = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, arrayOfInt);
      if (i != 0)
        return i;
      IEnumVARIANT localIEnumVARIANT = new IEnumVARIANT(arrayOfInt[0]);
      i = localIEnumVARIANT.Skip(paramInt);
      localIEnumVARIANT.Release();
      return i;
    }
    if (paramInt < 1)
      return -2147024809;
    this.enumIndex += paramInt;
    if (this.enumIndex > this.variants.length - 1)
    {
      this.enumIndex = (this.variants.length - 1);
      return 1;
    }
    return 0;
  }

  int Reset()
  {
    if ((this.iaccessible != null) && (this.accessibleControlListeners.size() == 0))
    {
      int[] arrayOfInt = new int[1];
      int i = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, arrayOfInt);
      if (i != 0)
        return i;
      IEnumVARIANT localIEnumVARIANT = new IEnumVARIANT(arrayOfInt[0]);
      i = localIEnumVARIANT.Reset();
      localIEnumVARIANT.Release();
      return i;
    }
    this.enumIndex = 0;
    return 0;
  }

  int Clone(int paramInt)
  {
    if ((this.iaccessible != null) && (this.accessibleControlListeners.size() == 0))
    {
      int[] arrayOfInt1 = new int[1];
      int i = this.iaccessible.QueryInterface(COM.IIDIEnumVARIANT, arrayOfInt1);
      if (i != 0)
        return i;
      IEnumVARIANT localIEnumVARIANT = new IEnumVARIANT(arrayOfInt1[0]);
      int[] arrayOfInt2 = new int[1];
      i = localIEnumVARIANT.Clone(arrayOfInt2);
      localIEnumVARIANT.Release();
      COM.MoveMemory(paramInt, arrayOfInt2, OS.PTR_SIZEOF);
      return i;
    }
    if (paramInt == 0)
      return -2147024809;
    COM.MoveMemory(paramInt, new int[] { this.objIEnumVARIANT.getAddress() }, OS.PTR_SIZEOF);
    AddRef();
    return 0;
  }

  int get_nRelations(int paramInt)
  {
    int i = getRelationCount();
    COM.MoveMemory(paramInt, new int[] { i }, 4);
    return 0;
  }

  int get_relation(int paramInt1, int paramInt2)
  {
    int i = -1;
    for (int j = 0; j < 15; j++)
    {
      Relation localRelation = this.relations[j];
      if (localRelation != null)
        i++;
      if (i == paramInt1)
      {
        localRelation.AddRef();
        COM.MoveMemory(paramInt2, new int[] { localRelation.getAddress() }, OS.PTR_SIZEOF);
        return 0;
      }
    }
    return -2147024809;
  }

  int get_relations(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    for (int j = 0; j < 15; j++)
    {
      if (i == paramInt1)
        break;
      Relation localRelation = this.relations[j];
      if (localRelation != null)
      {
        localRelation.AddRef();
        COM.MoveMemory(paramInt2 + i * OS.PTR_SIZEOF, new int[] { localRelation.getAddress() }, OS.PTR_SIZEOF);
        i++;
      }
    }
    COM.MoveMemory(paramInt3, new int[] { i }, 4);
    return 0;
  }

  int get_role(int paramInt)
  {
    int i = getRole();
    if (i == 0)
      i = getDefaultRole();
    COM.MoveMemory(paramInt, new int[] { i }, 4);
    return 0;
  }

  int scrollTo(int paramInt)
  {
    if ((paramInt < 4) || (paramInt > 6))
      return -2147024809;
    return -2147467263;
  }

  int scrollToPoint(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 != 0)
      return -2147024809;
    return -2147467263;
  }

  int get_groupPosition(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 0;
    COM.MoveMemory(paramInt1, new int[] { i }, 4);
    int j = 0;
    COM.MoveMemory(paramInt2, new int[] { j }, 4);
    int k = 0;
    COM.MoveMemory(paramInt3, new int[] { k }, 4);
    if ((i == 0) && (j == 0) && (k == 0))
      return 1;
    return 0;
  }

  int get_states(int paramInt)
  {
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = -1;
    for (int i = 0; i < this.accessibleControlListeners.size(); i++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i);
      localAccessibleControlListener.getState(localAccessibleControlEvent);
    }
    i = localAccessibleControlEvent.detail;
    int j = 0;
    if ((i & 0x4000000) != 0)
      j |= 1;
    if ((i & 0x8000000) != 0)
      j |= 8192;
    if ((i & 0x10000000) != 0)
      j |= 512;
    if ((i & 0x2000000) != 0)
      j |= 2048;
    if ((i & 0x20000000) != 0)
      j |= 64;
    if ((i & 0x40000000) != 0)
      j |= 32768;
    if ((getRole() == 42) && (this.accessibleTextExtendedListeners.size() > 0))
      j |= 8;
    COM.MoveMemory(paramInt, new int[] { j }, 4);
    return 0;
  }

  int get_extendedRole(int paramInt)
  {
    setString(paramInt, null);
    return 1;
  }

  int get_localizedExtendedRole(int paramInt)
  {
    setString(paramInt, null);
    return 1;
  }

  int get_nExtendedStates(int paramInt)
  {
    COM.MoveMemory(paramInt, new int[1], 4);
    return 0;
  }

  int get_extendedStates(int paramInt1, int paramInt2, int paramInt3)
  {
    setString(paramInt2, null);
    COM.MoveMemory(paramInt3, new int[1], 4);
    return 1;
  }

  int get_localizedExtendedStates(int paramInt1, int paramInt2, int paramInt3)
  {
    setString(paramInt2, null);
    COM.MoveMemory(paramInt3, new int[1], 4);
    return 1;
  }

  int get_uniqueID(int paramInt)
  {
    if (this.uniqueID == -1)
      this.uniqueID = (UniqueID--);
    COM.MoveMemory(paramInt, new int[] { this.uniqueID }, 4);
    return 0;
  }

  int get_windowHandle(int paramInt)
  {
    COM.MoveMemory(paramInt, new int[] { this.control.handle }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_indexInParent(int paramInt)
  {
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = -5;
    localAccessibleControlEvent.detail = -1;
    for (int i = 0; i < this.accessibleControlListeners.size(); i++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i);
      localAccessibleControlListener.getChild(localAccessibleControlEvent);
    }
    i = localAccessibleControlEvent.detail;
    COM.MoveMemory(paramInt, new int[] { i }, 4);
    return i == -1 ? 1 : 0;
  }

  int get_locale(int paramInt)
  {
    Locale localLocale = Locale.getDefault();
    char[] arrayOfChar = (localLocale.getLanguage() + "").toCharArray();
    int i = COM.SysAllocString(arrayOfChar);
    COM.MoveMemory(paramInt, new int[] { i }, OS.PTR_SIZEOF);
    arrayOfChar = (localLocale.getCountry() + "").toCharArray();
    i = COM.SysAllocString(arrayOfChar);
    COM.MoveMemory(paramInt + OS.PTR_SIZEOF, new int[] { i }, OS.PTR_SIZEOF);
    arrayOfChar = (localLocale.getVariant() + "").toCharArray();
    i = COM.SysAllocString(arrayOfChar);
    COM.MoveMemory(paramInt + 2 * OS.PTR_SIZEOF, new int[] { i }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_attributes(int paramInt)
  {
    AccessibleAttributeEvent localAccessibleAttributeEvent = new AccessibleAttributeEvent(this);
    for (int i = 0; i < this.accessibleAttributeListeners.size(); i++)
    {
      AccessibleAttributeListener localAccessibleAttributeListener = (AccessibleAttributeListener)this.accessibleAttributeListeners.elementAt(i);
      localAccessibleAttributeListener.getAttributes(localAccessibleAttributeEvent);
    }
    String str = "";
    str = str + "margin-left:" + localAccessibleAttributeEvent.leftMargin + ";";
    str = str + "margin-top:" + localAccessibleAttributeEvent.topMargin + ";";
    str = str + "margin-right:" + localAccessibleAttributeEvent.rightMargin + ";";
    str = str + "margin-bottom:" + localAccessibleAttributeEvent.bottomMargin + ";";
    int j;
    if (localAccessibleAttributeEvent.tabStops != null)
      for (j = 0; j < localAccessibleAttributeEvent.tabStops.length; j++)
        str = str + "tab-stop:position=" + localAccessibleAttributeEvent.tabStops[j] + ";";
    if (localAccessibleAttributeEvent.justify)
      str = str + "text-align:justify;";
    str = str + "text-align:" + (localAccessibleAttributeEvent.alignment == 131072 ? "right" : localAccessibleAttributeEvent.alignment == 16384 ? "left" : "center") + ";";
    str = str + "text-indent:" + localAccessibleAttributeEvent.indent + ";";
    if (localAccessibleAttributeEvent.attributes != null)
      for (j = 0; j + 1 < localAccessibleAttributeEvent.attributes.length; j += 2)
        str = str + localAccessibleAttributeEvent.attributes[j] + ":" + localAccessibleAttributeEvent.attributes[(j + 1)] + ";";
    if (getRole() == 42)
      str = str + "text-model:a1;";
    setString(paramInt, str);
    if (str.length() == 0)
      return 1;
    return 0;
  }

  int get_nActions(int paramInt)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.getActionCount(localAccessibleActionEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleActionEvent.count }, 4);
    return 0;
  }

  int doAction(int paramInt)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    localAccessibleActionEvent.index = paramInt;
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.doAction(localAccessibleActionEvent);
    }
    if ((localAccessibleActionEvent.result == null) || (!localAccessibleActionEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int get_description(int paramInt1, int paramInt2)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    localAccessibleActionEvent.index = paramInt1;
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.getDescription(localAccessibleActionEvent);
    }
    setString(paramInt2, localAccessibleActionEvent.result);
    if ((localAccessibleActionEvent.result == null) || (localAccessibleActionEvent.result.length() == 0))
      return 1;
    return 0;
  }

  int get_keyBinding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    localAccessibleActionEvent.index = paramInt1;
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.getKeyBinding(localAccessibleActionEvent);
    }
    String str1 = localAccessibleActionEvent.result;
    int j = 0;
    if (str1 != null)
      j = str1.length();
    int k = 0;
    int m = 0;
    while (k < j)
    {
      if (m == paramInt2)
        break;
      int n = str1.indexOf(';', k);
      if (n == -1)
        n = j;
      String str2 = str1.substring(k, n);
      if (str2.length() > 0)
      {
        setString(paramInt3 + m * OS.PTR_SIZEOF, str2);
        m++;
      }
      k = n + 1;
    }
    COM.MoveMemory(paramInt4, new int[] { m }, 4);
    if (m == 0)
    {
      setString(paramInt3, null);
      return 1;
    }
    return 0;
  }

  int get_name(int paramInt1, int paramInt2)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    localAccessibleActionEvent.index = paramInt1;
    localAccessibleActionEvent.localized = false;
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.getName(localAccessibleActionEvent);
    }
    if ((localAccessibleActionEvent.result == null) || (localAccessibleActionEvent.result.length() == 0))
    {
      setString(paramInt2, null);
      return 1;
    }
    setString(paramInt2, localAccessibleActionEvent.result);
    return 0;
  }

  int get_localizedName(int paramInt1, int paramInt2)
  {
    AccessibleActionEvent localAccessibleActionEvent = new AccessibleActionEvent(this);
    localAccessibleActionEvent.index = paramInt1;
    localAccessibleActionEvent.localized = true;
    for (int i = 0; i < this.accessibleActionListeners.size(); i++)
    {
      AccessibleActionListener localAccessibleActionListener = (AccessibleActionListener)this.accessibleActionListeners.elementAt(i);
      localAccessibleActionListener.getName(localAccessibleActionEvent);
    }
    if ((localAccessibleActionEvent.result == null) || (localAccessibleActionEvent.result.length() == 0))
    {
      setString(paramInt2, null);
      return 1;
    }
    setString(paramInt2, localAccessibleActionEvent.result);
    return 0;
  }

  int get_appName(int paramInt)
  {
    String str = Display.getAppName();
    if ((str == null) || (str.length() == 0))
    {
      setString(paramInt, null);
      return 1;
    }
    setString(paramInt, str);
    return 0;
  }

  int get_appVersion(int paramInt)
  {
    String str = Display.getAppVersion();
    if ((str == null) || (str.length() == 0))
    {
      setString(paramInt, null);
      return 1;
    }
    setString(paramInt, str);
    return 0;
  }

  int get_toolkitName(int paramInt)
  {
    String str = "SWT";
    setString(paramInt, str);
    return 0;
  }

  int get_toolkitVersion(int paramInt)
  {
    String str = SWT.getVersion();
    setString(paramInt, str);
    return 0;
  }

  int get_anchor(int paramInt1, int paramInt2)
  {
    AccessibleHyperlinkEvent localAccessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
    localAccessibleHyperlinkEvent.index = paramInt1;
    for (int i = 0; i < this.accessibleHyperlinkListeners.size(); i++)
    {
      AccessibleHyperlinkListener localAccessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.elementAt(i);
      localAccessibleHyperlinkListener.getAnchor(localAccessibleHyperlinkEvent);
    }
    Accessible localAccessible = localAccessibleHyperlinkEvent.accessible;
    if (localAccessible != null)
    {
      localAccessible.AddRef();
      setPtrVARIANT(paramInt2, (short)9, localAccessible.getAddress());
      return 0;
    }
    setStringVARIANT(paramInt2, localAccessibleHyperlinkEvent.result);
    if (localAccessibleHyperlinkEvent.result == null)
      return 1;
    return 0;
  }

  int get_anchorTarget(int paramInt1, int paramInt2)
  {
    AccessibleHyperlinkEvent localAccessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
    localAccessibleHyperlinkEvent.index = paramInt1;
    for (int i = 0; i < this.accessibleHyperlinkListeners.size(); i++)
    {
      AccessibleHyperlinkListener localAccessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.elementAt(i);
      localAccessibleHyperlinkListener.getAnchorTarget(localAccessibleHyperlinkEvent);
    }
    Accessible localAccessible = localAccessibleHyperlinkEvent.accessible;
    if (localAccessible != null)
    {
      localAccessible.AddRef();
      setPtrVARIANT(paramInt2, (short)9, localAccessible.getAddress());
      return 0;
    }
    setStringVARIANT(paramInt2, localAccessibleHyperlinkEvent.result);
    if (localAccessibleHyperlinkEvent.result == null)
      return 1;
    return 0;
  }

  int get_startIndex(int paramInt)
  {
    AccessibleHyperlinkEvent localAccessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
    for (int i = 0; i < this.accessibleHyperlinkListeners.size(); i++)
    {
      AccessibleHyperlinkListener localAccessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.elementAt(i);
      localAccessibleHyperlinkListener.getStartIndex(localAccessibleHyperlinkEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleHyperlinkEvent.index }, 4);
    return 0;
  }

  int get_endIndex(int paramInt)
  {
    AccessibleHyperlinkEvent localAccessibleHyperlinkEvent = new AccessibleHyperlinkEvent(this);
    for (int i = 0; i < this.accessibleHyperlinkListeners.size(); i++)
    {
      AccessibleHyperlinkListener localAccessibleHyperlinkListener = (AccessibleHyperlinkListener)this.accessibleHyperlinkListeners.elementAt(i);
      localAccessibleHyperlinkListener.getEndIndex(localAccessibleHyperlinkEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleHyperlinkEvent.index }, 4);
    return 0;
  }

  int get_valid(int paramInt)
  {
    return -2147467263;
  }

  int get_nHyperlinks(int paramInt)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getHyperlinkCount(localAccessibleTextEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTextEvent.count }, 4);
    return 0;
  }

  int get_hyperlink(int paramInt1, int paramInt2)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.index = paramInt1;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getHyperlink(localAccessibleTextEvent);
    }
    Accessible localAccessible = localAccessibleTextEvent.accessible;
    if (localAccessible == null)
    {
      setIntVARIANT(paramInt2, (short)0, 0);
      return -2147024809;
    }
    localAccessible.AddRef();
    COM.MoveMemory(paramInt2, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_hyperlinkIndex(int paramInt1, int paramInt2)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.offset = paramInt1;
    localAccessibleTextEvent.index = -1;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getHyperlinkIndex(localAccessibleTextEvent);
    }
    COM.MoveMemory(paramInt2, new int[] { localAccessibleTextEvent.index }, 4);
    if (localAccessibleTextEvent.index == -1)
      return 1;
    return 0;
  }

  int get_cellAt(int paramInt1, int paramInt2, int paramInt3)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.row = paramInt1;
    localAccessibleTableEvent.column = paramInt2;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getCell(localAccessibleTableEvent);
    }
    Accessible localAccessible = localAccessibleTableEvent.accessible;
    if (localAccessible == null)
      return -2147024809;
    localAccessible.AddRef();
    COM.MoveMemory(paramInt3, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_caption(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getCaption(localAccessibleTableEvent);
    }
    Accessible localAccessible = localAccessibleTableEvent.accessible;
    if (localAccessible == null)
    {
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
      return 1;
    }
    localAccessible.AddRef();
    COM.MoveMemory(paramInt, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_columnDescription(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.column = paramInt1;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getColumnDescription(localAccessibleTableEvent);
    }
    setString(paramInt2, localAccessibleTableEvent.result);
    if (localAccessibleTableEvent.result == null)
      return 1;
    return 0;
  }

  int get_nColumns(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getColumnCount(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableEvent.count }, 4);
    return 0;
  }

  int get_nRows(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getRowCount(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableEvent.count }, 4);
    return 0;
  }

  int get_nSelectedCells(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedCellCount(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableEvent.count }, 4);
    return 0;
  }

  int get_nSelectedColumns(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedColumnCount(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableEvent.count }, 4);
    return 0;
  }

  int get_nSelectedRows(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedRowCount(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableEvent.count }, 4);
    return 0;
  }

  int get_rowDescription(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.row = paramInt1;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getRowDescription(localAccessibleTableEvent);
    }
    setString(paramInt2, localAccessibleTableEvent.result);
    if (localAccessibleTableEvent.result == null)
      return 1;
    return 0;
  }

  int get_selectedCells(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedCells(localAccessibleTableEvent);
    }
    if ((localAccessibleTableEvent.accessibles == null) || (localAccessibleTableEvent.accessibles.length == 0))
    {
      COM.MoveMemory(paramInt1, new int[1], OS.PTR_SIZEOF);
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 1;
    }
    i = localAccessibleTableEvent.accessibles.length;
    int j = COM.CoTaskMemAlloc(i * OS.PTR_SIZEOF);
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Accessible localAccessible = localAccessibleTableEvent.accessibles[m];
      if (localAccessible != null)
      {
        localAccessible.AddRef();
        COM.MoveMemory(j + m * OS.PTR_SIZEOF, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
        k++;
      }
    }
    COM.MoveMemory(paramInt1, new int[] { j }, OS.PTR_SIZEOF);
    COM.MoveMemory(paramInt2, new int[] { k }, 4);
    return 0;
  }

  int get_selectedColumns(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedColumns(localAccessibleTableEvent);
    }
    i = localAccessibleTableEvent.selected == null ? 0 : localAccessibleTableEvent.selected.length;
    if (i == 0)
    {
      COM.MoveMemory(paramInt1, new int[1], OS.PTR_SIZEOF);
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 1;
    }
    int j = COM.CoTaskMemAlloc(i * 4);
    COM.MoveMemory(j, localAccessibleTableEvent.selected, i * 4);
    COM.MoveMemory(paramInt1, new int[] { j }, OS.PTR_SIZEOF);
    COM.MoveMemory(paramInt2, new int[] { i }, 4);
    return 0;
  }

  int get_selectedRows(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSelectedRows(localAccessibleTableEvent);
    }
    i = localAccessibleTableEvent.selected == null ? 0 : localAccessibleTableEvent.selected.length;
    if (i == 0)
    {
      COM.MoveMemory(paramInt1, new int[1], OS.PTR_SIZEOF);
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 1;
    }
    int j = COM.CoTaskMemAlloc(i * 4);
    COM.MoveMemory(j, localAccessibleTableEvent.selected, i * 4);
    COM.MoveMemory(paramInt1, new int[] { j }, OS.PTR_SIZEOF);
    COM.MoveMemory(paramInt2, new int[] { i }, 4);
    return 0;
  }

  int get_summary(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.getSummary(localAccessibleTableEvent);
    }
    Accessible localAccessible = localAccessibleTableEvent.accessible;
    if (localAccessible == null)
    {
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
      return 1;
    }
    localAccessible.AddRef();
    COM.MoveMemory(paramInt, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_isColumnSelected(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.column = paramInt1;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.isColumnSelected(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt2, new int[] { localAccessibleTableEvent.isSelected ? 1 : 0 }, 4);
    return 0;
  }

  int get_isRowSelected(int paramInt1, int paramInt2)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.row = paramInt1;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.isRowSelected(localAccessibleTableEvent);
    }
    COM.MoveMemory(paramInt2, new int[] { localAccessibleTableEvent.isSelected ? 1 : 0 }, 4);
    return 0;
  }

  int selectRow(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.row = paramInt;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.setSelectedRow(localAccessibleTableEvent);
    }
    if ((localAccessibleTableEvent.result == null) || (!localAccessibleTableEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int selectColumn(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.column = paramInt;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.setSelectedColumn(localAccessibleTableEvent);
    }
    if ((localAccessibleTableEvent.result == null) || (!localAccessibleTableEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int unselectRow(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.row = paramInt;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.deselectRow(localAccessibleTableEvent);
    }
    if ((localAccessibleTableEvent.result == null) || (!localAccessibleTableEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int unselectColumn(int paramInt)
  {
    AccessibleTableEvent localAccessibleTableEvent = new AccessibleTableEvent(this);
    localAccessibleTableEvent.column = paramInt;
    for (int i = 0; i < this.accessibleTableListeners.size(); i++)
    {
      AccessibleTableListener localAccessibleTableListener = (AccessibleTableListener)this.accessibleTableListeners.elementAt(i);
      localAccessibleTableListener.deselectColumn(localAccessibleTableEvent);
    }
    if ((localAccessibleTableEvent.result == null) || (!localAccessibleTableEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int get_modelChange(int paramInt)
  {
    if (this.tableChange == null)
    {
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
      return 1;
    }
    COM.MoveMemory(paramInt, this.tableChange, this.tableChange.length * 4);
    return 0;
  }

  int get_columnExtent(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getColumnSpan(localAccessibleTableCellEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableCellEvent.count }, 4);
    return 0;
  }

  int get_columnHeaderCells(int paramInt1, int paramInt2)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getColumnHeaders(localAccessibleTableCellEvent);
    }
    if ((localAccessibleTableCellEvent.accessibles == null) || (localAccessibleTableCellEvent.accessibles.length == 0))
    {
      COM.MoveMemory(paramInt1, new int[1], OS.PTR_SIZEOF);
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 1;
    }
    i = localAccessibleTableCellEvent.accessibles.length;
    int j = COM.CoTaskMemAlloc(i * OS.PTR_SIZEOF);
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Accessible localAccessible = localAccessibleTableCellEvent.accessibles[m];
      if (localAccessible != null)
      {
        localAccessible.AddRef();
        COM.MoveMemory(j + m * OS.PTR_SIZEOF, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
        k++;
      }
    }
    COM.MoveMemory(paramInt1, new int[] { j }, OS.PTR_SIZEOF);
    COM.MoveMemory(paramInt2, new int[] { k }, 4);
    return 0;
  }

  int get_columnIndex(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getColumnIndex(localAccessibleTableCellEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableCellEvent.index }, 4);
    return 0;
  }

  int get_rowExtent(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getRowSpan(localAccessibleTableCellEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableCellEvent.count }, 4);
    return 0;
  }

  int get_rowHeaderCells(int paramInt1, int paramInt2)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getRowHeaders(localAccessibleTableCellEvent);
    }
    if ((localAccessibleTableCellEvent.accessibles == null) || (localAccessibleTableCellEvent.accessibles.length == 0))
    {
      COM.MoveMemory(paramInt1, new int[1], OS.PTR_SIZEOF);
      COM.MoveMemory(paramInt2, new int[1], 4);
      return 1;
    }
    i = localAccessibleTableCellEvent.accessibles.length;
    int j = COM.CoTaskMemAlloc(i * OS.PTR_SIZEOF);
    int k = 0;
    for (int m = 0; m < i; m++)
    {
      Accessible localAccessible = localAccessibleTableCellEvent.accessibles[m];
      if (localAccessible != null)
      {
        localAccessible.AddRef();
        COM.MoveMemory(j + m * OS.PTR_SIZEOF, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
        k++;
      }
    }
    COM.MoveMemory(paramInt1, new int[] { j }, OS.PTR_SIZEOF);
    COM.MoveMemory(paramInt2, new int[] { k }, 4);
    return 0;
  }

  int get_rowIndex(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getRowIndex(localAccessibleTableCellEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableCellEvent.index }, 4);
    return 0;
  }

  int get_isSelected(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.isSelected(localAccessibleTableCellEvent);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTableCellEvent.isSelected ? 1 : 0 }, 4);
    return 0;
  }

  int get_rowColumnExtents(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return -2147352573;
  }

  int get_table(int paramInt)
  {
    AccessibleTableCellEvent localAccessibleTableCellEvent = new AccessibleTableCellEvent(this);
    for (int i = 0; i < this.accessibleTableCellListeners.size(); i++)
    {
      AccessibleTableCellListener localAccessibleTableCellListener = (AccessibleTableCellListener)this.accessibleTableCellListeners.elementAt(i);
      localAccessibleTableCellListener.getTable(localAccessibleTableCellEvent);
    }
    Accessible localAccessible = localAccessibleTableCellEvent.accessible;
    if (localAccessible == null)
    {
      COM.MoveMemory(paramInt, new int[1], OS.PTR_SIZEOF);
      return 1;
    }
    localAccessible.AddRef();
    COM.MoveMemory(paramInt, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int addSelection(int paramInt1, int paramInt2)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = (paramInt1 == -1 ? getCharacterCount() : paramInt1);
    localAccessibleTextEvent.end = (paramInt2 == -1 ? getCharacterCount() : paramInt2);
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.addSelection(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int get_attributes(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    AccessibleTextAttributeEvent localAccessibleTextAttributeEvent = new AccessibleTextAttributeEvent(this);
    localAccessibleTextAttributeEvent.offset = (paramInt1 == -1 ? getCharacterCount() : paramInt1);
    for (int i = 0; i < this.accessibleAttributeListeners.size(); i++)
    {
      localObject1 = (AccessibleAttributeListener)this.accessibleAttributeListeners.elementAt(i);
      ((AccessibleAttributeListener)localObject1).getTextAttributes(localAccessibleTextAttributeEvent);
    }
    String str = "";
    Object localObject1 = localAccessibleTextAttributeEvent.textStyle;
    if (localObject1 != null)
    {
      if (((TextStyle)localObject1).rise != 0)
      {
        str = str + "text-position:";
        if (((TextStyle)localObject1).rise > 0)
          str = str + "super";
        else
          str = str + "sub";
      }
      if (((TextStyle)localObject1).underline)
      {
        str = str + "text-underline-type:";
        switch (((TextStyle)localObject1).underlineStyle)
        {
        case 0:
          str = str + "single;";
          break;
        case 1:
          str = str + "double;";
          break;
        case 3:
          str = str + "single;text-underline-style:wave;";
          break;
        case 2:
          str = str + "single;text-underline-style:wave;invalid:true;";
          break;
        default:
          str = str + "none;";
        }
      }
      if (((TextStyle)localObject1).strikeout)
        str = str + "text-line-through-type:single";
      Font localFont = ((TextStyle)localObject1).font;
      if ((localFont != null) && (!localFont.isDisposed()))
      {
        localObject2 = localFont.getFontData()[0];
        str = str + "font-family:" + ((FontData)localObject2).getName() + ";";
        str = str + "font-size:" + ((FontData)localObject2).getHeight() + "pt;";
        str = str + "font-style:" + (((FontData)localObject2).data.lfItalic != 0 ? "italic" : "normal") + ";";
        str = str + "font-weight:" + ((FontData)localObject2).data.lfWeight + ";";
      }
      Object localObject2 = ((TextStyle)localObject1).foreground;
      if ((localObject2 != null) && (!((Color)localObject2).isDisposed()))
        str = str + "color:rgb(" + ((Color)localObject2).getRed() + "," + ((Color)localObject2).getGreen() + "," + ((Color)localObject2).getBlue() + ");";
      localObject2 = ((TextStyle)localObject1).background;
      if ((localObject2 != null) && (!((Color)localObject2).isDisposed()))
        str = str + "background-color:rgb(" + ((Color)localObject2).getRed() + "," + ((Color)localObject2).getGreen() + "," + ((Color)localObject2).getBlue() + ");";
    }
    if (localAccessibleTextAttributeEvent.attributes != null)
      for (int j = 0; j + 1 < localAccessibleTextAttributeEvent.attributes.length; j += 2)
        str = str + localAccessibleTextAttributeEvent.attributes[j] + ":" + localAccessibleTextAttributeEvent.attributes[(j + 1)] + ";";
    COM.MoveMemory(paramInt2, new int[] { localAccessibleTextAttributeEvent.start }, 4);
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextAttributeEvent.end }, 4);
    setString(paramInt4, str);
    if (str.length() == 0)
      return 1;
    return 0;
  }

  int get_caretOffset(int paramInt)
  {
    int i = getCaretOffset();
    COM.MoveMemory(paramInt, new int[] { i }, 4);
    if (i == -1)
      return 1;
    return 0;
  }

  int get_characterExtents(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = getCharacterCount();
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = (paramInt1 < 0 ? 0 : paramInt1 == -1 ? i : paramInt1);
    localAccessibleTextEvent.end = ((paramInt1 == -1) || (paramInt1 >= i) ? i : paramInt1 + 1);
    for (int j = 0; j < this.accessibleTextExtendedListeners.size(); j++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(j);
      localAccessibleTextExtendedListener.getTextBounds(localAccessibleTextEvent);
    }
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextEvent.x }, 4);
    COM.MoveMemory(paramInt4, new int[] { localAccessibleTextEvent.y }, 4);
    COM.MoveMemory(paramInt5, new int[] { localAccessibleTextEvent.width }, 4);
    COM.MoveMemory(paramInt6, new int[] { localAccessibleTextEvent.height }, 4);
    if ((localAccessibleTextEvent.width == 0) && (localAccessibleTextEvent.height == 0))
      return -2147024809;
    return 0;
  }

  int get_nSelections(int paramInt)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.count = -1;
    Object localObject;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      localObject = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      ((AccessibleTextExtendedListener)localObject).getSelectionCount(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.count == -1)
    {
      localAccessibleTextEvent.childID = -1;
      localAccessibleTextEvent.offset = -1;
      localAccessibleTextEvent.length = 0;
      for (i = 0; i < this.accessibleTextListeners.size(); i++)
      {
        localObject = (AccessibleTextListener)this.accessibleTextListeners.elementAt(i);
        ((AccessibleTextListener)localObject).getSelectionRange(localAccessibleTextEvent);
      }
      localAccessibleTextEvent.count = ((localAccessibleTextEvent.offset != -1) && (localAccessibleTextEvent.length > 0) ? 1 : 0);
    }
    COM.MoveMemory(paramInt, new int[] { localAccessibleTextEvent.count }, 4);
    return 0;
  }

  int get_offsetAtPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.x = paramInt1;
    localAccessibleTextEvent.y = paramInt2;
    localAccessibleTextEvent.offset = -1;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getOffsetAtPoint(localAccessibleTextEvent);
    }
    COM.MoveMemory(paramInt4, new int[] { localAccessibleTextEvent.offset }, 4);
    if (localAccessibleTextEvent.offset == -1)
      return 1;
    return 0;
  }

  int get_selection(int paramInt1, int paramInt2, int paramInt3)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.index = paramInt1;
    localAccessibleTextEvent.start = -1;
    localAccessibleTextEvent.end = -1;
    Object localObject;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      localObject = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      ((AccessibleTextExtendedListener)localObject).getSelection(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.start == -1) && (paramInt1 == 0))
    {
      localAccessibleTextEvent.childID = -1;
      localAccessibleTextEvent.offset = -1;
      localAccessibleTextEvent.length = 0;
      for (i = 0; i < this.accessibleTextListeners.size(); i++)
      {
        localObject = (AccessibleTextListener)this.accessibleTextListeners.elementAt(i);
        ((AccessibleTextListener)localObject).getSelectionRange(localAccessibleTextEvent);
      }
      localAccessibleTextEvent.start = localAccessibleTextEvent.offset;
      localAccessibleTextEvent.end = (localAccessibleTextEvent.offset + localAccessibleTextEvent.length);
    }
    COM.MoveMemory(paramInt2, new int[] { localAccessibleTextEvent.start }, 4);
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextEvent.end }, 4);
    if (localAccessibleTextEvent.start == -1)
      return 1;
    return 0;
  }

  int get_text(int paramInt1, int paramInt2, int paramInt3)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = (paramInt1 == -1 ? getCharacterCount() : paramInt1);
    localAccessibleTextEvent.end = (paramInt2 == -1 ? getCharacterCount() : paramInt2);
    if (localAccessibleTextEvent.start > localAccessibleTextEvent.end)
    {
      i = localAccessibleTextEvent.start;
      localAccessibleTextEvent.start = localAccessibleTextEvent.end;
      localAccessibleTextEvent.end = i;
    }
    localAccessibleTextEvent.count = 0;
    localAccessibleTextEvent.type = 5;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getText(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.result == null)
    {
      AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
      localAccessibleControlEvent.childID = -1;
      for (int j = 0; j < this.accessibleControlListeners.size(); j++)
      {
        AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(j);
        localAccessibleControlListener.getRole(localAccessibleControlEvent);
        localAccessibleControlListener.getValue(localAccessibleControlEvent);
      }
      if (localAccessibleControlEvent.detail == 42)
        localAccessibleTextEvent.result = localAccessibleControlEvent.result;
    }
    setString(paramInt3, localAccessibleTextEvent.result);
    if (localAccessibleTextEvent.result == null)
      return -2147024809;
    return 0;
  }

  int get_textBeforeOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    int i = getCharacterCount();
    localAccessibleTextEvent.start = (paramInt1 == -2 ? getCaretOffset() : paramInt1 == -1 ? i : paramInt1);
    localAccessibleTextEvent.end = localAccessibleTextEvent.start;
    localAccessibleTextEvent.count = -1;
    switch (paramInt2)
    {
    case 0:
      localAccessibleTextEvent.type = 0;
      break;
    case 1:
      localAccessibleTextEvent.type = 1;
      break;
    case 2:
      localAccessibleTextEvent.type = 2;
      break;
    case 3:
      localAccessibleTextEvent.type = 3;
      break;
    case 4:
      localAccessibleTextEvent.type = 4;
      break;
    default:
      return -2147024809;
    }
    int j = localAccessibleTextEvent.start;
    int k = localAccessibleTextEvent.end;
    for (int m = 0; m < this.accessibleTextExtendedListeners.size(); m++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener1 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(m);
      localAccessibleTextExtendedListener1.getText(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.end < i)
      switch (paramInt2)
      {
      case 1:
      case 2:
      case 3:
      case 4:
        m = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = j;
        localAccessibleTextEvent.end = k;
        localAccessibleTextEvent.count = 0;
        AccessibleTextExtendedListener localAccessibleTextExtendedListener2;
        for (int n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
        localAccessibleTextEvent.end = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = m;
        localAccessibleTextEvent.type = 5;
        localAccessibleTextEvent.count = 0;
        for (n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
      }
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextEvent.start }, 4);
    COM.MoveMemory(paramInt4, new int[] { localAccessibleTextEvent.end }, 4);
    setString(paramInt5, localAccessibleTextEvent.result);
    if (localAccessibleTextEvent.result == null)
      return 1;
    return 0;
  }

  int get_textAfterOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    int i = getCharacterCount();
    localAccessibleTextEvent.start = (paramInt1 == -2 ? getCaretOffset() : paramInt1 == -1 ? i : paramInt1);
    localAccessibleTextEvent.end = localAccessibleTextEvent.start;
    localAccessibleTextEvent.count = 1;
    switch (paramInt2)
    {
    case 0:
      localAccessibleTextEvent.type = 0;
      break;
    case 1:
      localAccessibleTextEvent.type = 1;
      break;
    case 2:
      localAccessibleTextEvent.type = 2;
      break;
    case 3:
      localAccessibleTextEvent.type = 3;
      break;
    case 4:
      localAccessibleTextEvent.type = 4;
      break;
    default:
      return -2147024809;
    }
    int j = localAccessibleTextEvent.start;
    int k = localAccessibleTextEvent.end;
    for (int m = 0; m < this.accessibleTextExtendedListeners.size(); m++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener1 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(m);
      localAccessibleTextExtendedListener1.getText(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.end < i)
      switch (paramInt2)
      {
      case 1:
      case 2:
      case 3:
      case 4:
        m = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = j;
        localAccessibleTextEvent.end = k;
        localAccessibleTextEvent.count = 2;
        AccessibleTextExtendedListener localAccessibleTextExtendedListener2;
        for (int n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
        localAccessibleTextEvent.end = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = m;
        localAccessibleTextEvent.type = 5;
        localAccessibleTextEvent.count = 0;
        for (n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
      }
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextEvent.start }, 4);
    COM.MoveMemory(paramInt4, new int[] { localAccessibleTextEvent.end }, 4);
    setString(paramInt5, localAccessibleTextEvent.result);
    if (localAccessibleTextEvent.result == null)
      return 1;
    return 0;
  }

  int get_textAtOffset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    int i = getCharacterCount();
    localAccessibleTextEvent.start = (paramInt1 == -2 ? getCaretOffset() : paramInt1 == -1 ? i : paramInt1);
    localAccessibleTextEvent.end = localAccessibleTextEvent.start;
    localAccessibleTextEvent.count = 0;
    switch (paramInt2)
    {
    case 0:
      localAccessibleTextEvent.type = 0;
      break;
    case 1:
      localAccessibleTextEvent.type = 1;
      break;
    case 2:
      localAccessibleTextEvent.type = 2;
      break;
    case 3:
      localAccessibleTextEvent.type = 3;
      break;
    case 4:
      localAccessibleTextEvent.type = 4;
      break;
    case 5:
      localAccessibleTextEvent.type = 5;
      localAccessibleTextEvent.start = 0;
      localAccessibleTextEvent.end = i;
      localAccessibleTextEvent.count = 0;
      break;
    default:
      return -2147024809;
    }
    int j = localAccessibleTextEvent.start;
    int k = localAccessibleTextEvent.end;
    for (int m = 0; m < this.accessibleTextExtendedListeners.size(); m++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener1 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(m);
      localAccessibleTextExtendedListener1.getText(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.end < i)
      switch (paramInt2)
      {
      case 1:
      case 2:
      case 3:
      case 4:
        m = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = j;
        localAccessibleTextEvent.end = k;
        localAccessibleTextEvent.count = 1;
        AccessibleTextExtendedListener localAccessibleTextExtendedListener2;
        for (int n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
        localAccessibleTextEvent.end = localAccessibleTextEvent.start;
        localAccessibleTextEvent.start = m;
        localAccessibleTextEvent.type = 5;
        localAccessibleTextEvent.count = 0;
        for (n = 0; n < this.accessibleTextExtendedListeners.size(); n++)
        {
          localAccessibleTextExtendedListener2 = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(n);
          localAccessibleTextExtendedListener2.getText(localAccessibleTextEvent);
        }
      }
    COM.MoveMemory(paramInt3, new int[] { localAccessibleTextEvent.start }, 4);
    COM.MoveMemory(paramInt4, new int[] { localAccessibleTextEvent.end }, 4);
    setString(paramInt5, localAccessibleTextEvent.result);
    if (localAccessibleTextEvent.result == null)
      return 1;
    return 0;
  }

  int removeSelection(int paramInt)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.index = paramInt;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.removeSelection(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int setCaretOffset(int paramInt)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.offset = (paramInt == -1 ? getCharacterCount() : paramInt);
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.setCaretOffset(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int setSelection(int paramInt1, int paramInt2, int paramInt3)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.index = paramInt1;
    localAccessibleTextEvent.start = (paramInt2 == -1 ? getCharacterCount() : paramInt2);
    localAccessibleTextEvent.end = (paramInt3 == -1 ? getCharacterCount() : paramInt3);
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.setSelection(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int get_nCharacters(int paramInt)
  {
    int i = getCharacterCount();
    COM.MoveMemory(paramInt, new int[] { i }, 4);
    return 0;
  }

  int scrollSubstringTo(int paramInt1, int paramInt2, int paramInt3)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = paramInt1;
    localAccessibleTextEvent.end = paramInt2;
    switch (paramInt3)
    {
    case 0:
      localAccessibleTextEvent.type = 0;
      break;
    case 1:
      localAccessibleTextEvent.type = 1;
      break;
    case 2:
      localAccessibleTextEvent.type = 2;
      break;
    case 3:
      localAccessibleTextEvent.type = 3;
      break;
    case 4:
      localAccessibleTextEvent.type = 4;
      break;
    case 5:
      localAccessibleTextEvent.type = 5;
      break;
    case 6:
      localAccessibleTextEvent.type = 6;
    }
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.scrollText(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int scrollSubstringToPoint(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.start = paramInt1;
    localAccessibleTextEvent.end = paramInt2;
    localAccessibleTextEvent.type = 7;
    localAccessibleTextEvent.x = paramInt4;
    localAccessibleTextEvent.y = paramInt5;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.scrollText(localAccessibleTextEvent);
    }
    if ((localAccessibleTextEvent.result == null) || (!localAccessibleTextEvent.result.equals("OK")))
      return -2147024809;
    return 0;
  }

  int get_newText(int paramInt)
  {
    String str = null;
    int i = 0;
    int j = 0;
    if (this.textInserted != null)
    {
      str = (String)this.textInserted[3];
      i = ((Integer)this.textInserted[1]).intValue();
      j = ((Integer)this.textInserted[2]).intValue();
    }
    setString(paramInt, str);
    COM.MoveMemory(paramInt + OS.PTR_SIZEOF, new int[] { i }, 4);
    COM.MoveMemory(paramInt + OS.PTR_SIZEOF + 4, new int[] { j }, 4);
    if (this.textInserted == null)
      return 1;
    return 0;
  }

  int get_oldText(int paramInt)
  {
    String str = null;
    int i = 0;
    int j = 0;
    if (this.textDeleted != null)
    {
      str = (String)this.textDeleted[3];
      i = ((Integer)this.textDeleted[1]).intValue();
      j = ((Integer)this.textDeleted[2]).intValue();
    }
    setString(paramInt, str);
    COM.MoveMemory(paramInt + OS.PTR_SIZEOF, new int[] { i }, 4);
    COM.MoveMemory(paramInt + OS.PTR_SIZEOF + 4, new int[] { j }, 4);
    if (this.textDeleted == null)
      return 1;
    return 0;
  }

  int get_currentValue(int paramInt)
  {
    AccessibleValueEvent localAccessibleValueEvent = new AccessibleValueEvent(this);
    for (int i = 0; i < this.accessibleValueListeners.size(); i++)
    {
      AccessibleValueListener localAccessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.elementAt(i);
      localAccessibleValueListener.getCurrentValue(localAccessibleValueEvent);
    }
    setNumberVARIANT(paramInt, localAccessibleValueEvent.value);
    return 0;
  }

  int setCurrentValue(int paramInt)
  {
    AccessibleValueEvent localAccessibleValueEvent = new AccessibleValueEvent(this);
    localAccessibleValueEvent.value = getNumberVARIANT(paramInt);
    for (int i = 0; i < this.accessibleValueListeners.size(); i++)
    {
      AccessibleValueListener localAccessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.elementAt(i);
      localAccessibleValueListener.setCurrentValue(localAccessibleValueEvent);
    }
    return 0;
  }

  int get_maximumValue(int paramInt)
  {
    AccessibleValueEvent localAccessibleValueEvent = new AccessibleValueEvent(this);
    for (int i = 0; i < this.accessibleValueListeners.size(); i++)
    {
      AccessibleValueListener localAccessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.elementAt(i);
      localAccessibleValueListener.getMaximumValue(localAccessibleValueEvent);
    }
    setNumberVARIANT(paramInt, localAccessibleValueEvent.value);
    return 0;
  }

  int get_minimumValue(int paramInt)
  {
    AccessibleValueEvent localAccessibleValueEvent = new AccessibleValueEvent(this);
    for (int i = 0; i < this.accessibleValueListeners.size(); i++)
    {
      AccessibleValueListener localAccessibleValueListener = (AccessibleValueListener)this.accessibleValueListeners.elementAt(i);
      localAccessibleValueListener.getMinimumValue(localAccessibleValueEvent);
    }
    setNumberVARIANT(paramInt, localAccessibleValueEvent.value);
    return 0;
  }

  int eventChildID()
  {
    if (this.parent == null)
      return 0;
    if (this.uniqueID == -1)
      this.uniqueID = (UniqueID--);
    return this.uniqueID;
  }

  void checkUniqueID(int paramInt)
  {
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = paramInt;
    for (int i = 0; i < this.accessibleControlListeners.size(); i++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i);
      localAccessibleControlListener.getChild(localAccessibleControlEvent);
    }
    Accessible localAccessible = localAccessibleControlEvent.accessible;
    if ((localAccessible != null) && (localAccessible.uniqueID == -1))
      localAccessible.uniqueID = paramInt;
  }

  int childIDToOs(int paramInt)
  {
    if (paramInt == -1)
      return 0;
    int i = paramInt + 1;
    if ((this.control instanceof Tree))
      if (OS.COMCTL32_MAJOR < 6)
        i = paramInt;
      else
        i = OS.SendMessage(this.control.handle, 4395, paramInt, 0);
    checkUniqueID(i);
    return i;
  }

  int osToChildID(int paramInt)
  {
    if (paramInt == 0)
      return -1;
    if (!(this.control instanceof Tree))
      return paramInt - 1;
    if (OS.COMCTL32_MAJOR < 6)
      return paramInt;
    return OS.SendMessage(this.control.handle, 4394, paramInt, 0);
  }

  int stateToOs(int paramInt)
  {
    int i = 0;
    if ((paramInt & 0x2) != 0)
      i |= 2;
    if ((paramInt & 0x200000) != 0)
      i |= 2097152;
    if ((paramInt & 0x1000000) != 0)
      i |= 16777216;
    if ((paramInt & 0x4) != 0)
      i |= 4;
    if ((paramInt & 0x100000) != 0)
      i |= 1048576;
    if ((paramInt & 0x8) != 0)
      i |= 8;
    if ((paramInt & 0x10) != 0)
      i |= 16;
    if ((paramInt & 0x200) != 0)
      i |= 512;
    if ((paramInt & 0x400) != 0)
      i |= 1024;
    if ((paramInt & 0x80) != 0)
      i |= 128;
    if ((paramInt & 0x800) != 0)
      i |= 2048;
    if ((paramInt & 0x40) != 0)
      i |= 64;
    if ((paramInt & 0x8000) != 0)
      i |= 32768;
    if ((paramInt & 0x10000) != 0)
      i |= 65536;
    if ((paramInt & 0x20000) != 0)
      i |= 131072;
    if ((paramInt & 0x400000) != 0)
      i |= 4194304;
    if ((paramInt & 0x1) != 0)
      i |= 1;
    return i;
  }

  int osToState(int paramInt)
  {
    int i = 0;
    if ((paramInt & 0x2) != 0)
      i |= 2;
    if ((paramInt & 0x200000) != 0)
      i |= 2097152;
    if ((paramInt & 0x1000000) != 0)
      i |= 16777216;
    if ((paramInt & 0x4) != 0)
      i |= 4;
    if ((paramInt & 0x100000) != 0)
      i |= 1048576;
    if ((paramInt & 0x8) != 0)
      i |= 8;
    if ((paramInt & 0x10) != 0)
      i |= 16;
    if ((paramInt & 0x200) != 0)
      i |= 512;
    if ((paramInt & 0x400) != 0)
      i |= 1024;
    if ((paramInt & 0x80) != 0)
      i |= 128;
    if ((paramInt & 0x800) != 0)
      i |= 2048;
    if ((paramInt & 0x40) != 0)
      i |= 64;
    if ((paramInt & 0x8000) != 0)
      i |= 32768;
    if ((paramInt & 0x10000) != 0)
      i |= 65536;
    if ((paramInt & 0x20000) != 0)
      i |= 131072;
    if ((paramInt & 0x400000) != 0)
      i |= 4194304;
    if ((paramInt & 0x1) != 0)
      i |= 1;
    return i;
  }

  int roleToOs(int paramInt)
  {
    switch (paramInt)
    {
    case 10:
      return 10;
    case 9:
      return 9;
    case 2:
      return 2;
    case 11:
      return 11;
    case 12:
      return 12;
    case 21:
      return 21;
    case 13:
      return 13;
    case 3:
      return 3;
    case 18:
      return 18;
    case 41:
      return 41;
    case 43:
      return 43;
    case 44:
      return 44;
    case 45:
      return 45;
    case 62:
      return 62;
    case 46:
      return 46;
    case 42:
      return 42;
    case 22:
      return 22;
    case 33:
      return 33;
    case 34:
      return 34;
    case 24:
      return 24;
    case 29:
      return 29;
    case 25:
      return 25;
    case 26:
      return 26;
    case 35:
      return 35;
    case 36:
      return 36;
    case 60:
      return 60;
    case 37:
      return 37;
    case 48:
      return 48;
    case 51:
      return 51;
    case 30:
      return 30;
    case 8:
      return 8;
    case 54:
      return 54;
    case 27:
      return 27;
    case 15:
      return 15;
    case 40:
      return 40;
    case 20:
      return 20;
    case 28:
      return 28;
    case 52:
      return 52;
    case 23:
      return 23;
    case 61:
      return 61;
    case 47:
      return 47;
    case 1025:
      return 10;
    case 1027:
      return 12;
    case 1073:
      return 12;
    case 1029:
      return 47;
    case 1038:
      return 10;
    case 1040:
      return 10;
    case 1043:
      return 10;
    case 1044:
      return 10;
    case 1053:
      return 10;
    case 1054:
      return 10;
    case 1060:
      return 10;
    }
    return 10;
  }

  int osToRole(int paramInt)
  {
    switch (paramInt)
    {
    case 10:
      return 10;
    case 9:
      return 9;
    case 2:
      return 2;
    case 11:
      return 11;
    case 12:
      return 12;
    case 21:
      return 21;
    case 13:
      return 13;
    case 3:
      return 3;
    case 18:
      return 18;
    case 41:
      return 41;
    case 43:
      return 43;
    case 44:
      return 44;
    case 45:
      return 45;
    case 62:
      return 62;
    case 46:
      return 46;
    case 42:
      return 42;
    case 22:
      return 22;
    case 33:
      return 33;
    case 34:
      return 34;
    case 24:
      return 24;
    case 29:
      return 29;
    case 25:
      return 25;
    case 26:
      return 26;
    case 35:
      return 35;
    case 36:
      return 36;
    case 60:
      return 60;
    case 37:
      return 37;
    case 48:
      return 48;
    case 51:
      return 51;
    case 30:
      return 30;
    case 8:
      return 8;
    case 54:
      return 54;
    case 27:
      return 27;
    case 15:
      return 15;
    case 40:
      return 40;
    case 20:
      return 20;
    case 28:
      return 28;
    case 52:
      return 52;
    case 23:
      return 23;
    case 61:
      return 61;
    case 47:
      return 47;
    case 4:
    case 5:
    case 6:
    case 7:
    case 14:
    case 16:
    case 17:
    case 19:
    case 31:
    case 32:
    case 38:
    case 39:
    case 49:
    case 50:
    case 53:
    case 55:
    case 56:
    case 57:
    case 58:
    case 59:
    }
    return 10;
  }

  int getCaretOffset()
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.offset = -1;
    AccessibleTextListener localAccessibleTextListener;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      localAccessibleTextListener = (AccessibleTextListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextListener.getCaretOffset(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.offset == -1)
      for (i = 0; i < this.accessibleTextListeners.size(); i++)
      {
        localAccessibleTextEvent.childID = -1;
        localAccessibleTextListener = (AccessibleTextListener)this.accessibleTextListeners.elementAt(i);
        localAccessibleTextListener.getCaretOffset(localAccessibleTextEvent);
      }
    return localAccessibleTextEvent.offset;
  }

  int getCharacterCount()
  {
    AccessibleTextEvent localAccessibleTextEvent = new AccessibleTextEvent(this);
    localAccessibleTextEvent.count = -1;
    for (int i = 0; i < this.accessibleTextExtendedListeners.size(); i++)
    {
      AccessibleTextExtendedListener localAccessibleTextExtendedListener = (AccessibleTextExtendedListener)this.accessibleTextExtendedListeners.elementAt(i);
      localAccessibleTextExtendedListener.getCharacterCount(localAccessibleTextEvent);
    }
    if (localAccessibleTextEvent.count == -1)
    {
      AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
      localAccessibleControlEvent.childID = -1;
      for (int j = 0; j < this.accessibleControlListeners.size(); j++)
      {
        AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(j);
        localAccessibleControlListener.getRole(localAccessibleControlEvent);
        localAccessibleControlListener.getValue(localAccessibleControlEvent);
      }
      localAccessibleTextEvent.count = ((localAccessibleControlEvent.detail == 42) && (localAccessibleControlEvent.result != null) ? localAccessibleControlEvent.result.length() : 0);
    }
    return localAccessibleTextEvent.count;
  }

  int getRelationCount()
  {
    int i = 0;
    for (int j = 0; j < 15; j++)
      if (this.relations[j] != null)
        i++;
    return i;
  }

  int getRole()
  {
    AccessibleControlEvent localAccessibleControlEvent = new AccessibleControlEvent(this);
    localAccessibleControlEvent.childID = -1;
    for (int i = 0; i < this.accessibleControlListeners.size(); i++)
    {
      AccessibleControlListener localAccessibleControlListener = (AccessibleControlListener)this.accessibleControlListeners.elementAt(i);
      localAccessibleControlListener.getRole(localAccessibleControlEvent);
    }
    return localAccessibleControlEvent.detail;
  }

  int getDefaultRole()
  {
    int i = 10;
    if (this.iaccessible != null)
    {
      int j = OS.GlobalAlloc(64, VARIANT.sizeof);
      setIntVARIANT(j, (short)3, 0);
      int k = OS.GlobalAlloc(64, VARIANT.sizeof);
      int m = this.iaccessible.get_accRole(j, k);
      if (m == 0)
      {
        VARIANT localVARIANT = getVARIANT(k);
        if (localVARIANT.vt == 3)
          i = localVARIANT.lVal;
      }
      OS.GlobalFree(j);
      OS.GlobalFree(k);
    }
    return i;
  }

  VARIANT getVARIANT(int paramInt)
  {
    VARIANT localVARIANT = new VARIANT();
    COM.MoveMemory(localVARIANT, paramInt, VARIANT.sizeof);
    return localVARIANT;
  }

  Number getNumberVARIANT(int paramInt)
  {
    VARIANT localVARIANT = new VARIANT();
    COM.MoveMemory(localVARIANT, paramInt, VARIANT.sizeof);
    if (localVARIANT.vt == 20)
      return new Long(localVARIANT.lVal);
    return new Integer(localVARIANT.lVal);
  }

  void setIntVARIANT(int paramInt1, short paramShort, int paramInt2)
  {
    if ((paramShort == 3) || (paramShort == 0))
    {
      COM.MoveMemory(paramInt1, new short[] { paramShort }, 2);
      COM.MoveMemory(paramInt1 + 8, new int[] { paramInt2 }, 4);
    }
  }

  void setPtrVARIANT(int paramInt1, short paramShort, int paramInt2)
  {
    if ((paramShort == 9) || (paramShort == 13))
    {
      COM.MoveMemory(paramInt1, new short[] { paramShort }, 2);
      COM.MoveMemory(paramInt1 + 8, new int[] { paramInt2 }, OS.PTR_SIZEOF);
    }
  }

  void setNumberVARIANT(int paramInt, Number paramNumber)
  {
    if (paramNumber == null)
    {
      COM.MoveMemory(paramInt, new short[1], 2);
      COM.MoveMemory(paramInt + 8, new int[1], 4);
    }
    else if ((paramNumber instanceof Double))
    {
      COM.MoveMemory(paramInt, new short[] { 5 }, 2);
      COM.MoveMemory(paramInt + 8, new double[] { paramNumber.doubleValue() }, 8);
    }
    else if ((paramNumber instanceof Float))
    {
      COM.MoveMemory(paramInt, new short[] { 4 }, 2);
      COM.MoveMemory(paramInt + 8, new float[] { paramNumber.floatValue() }, 4);
    }
    else if ((paramNumber instanceof Long))
    {
      COM.MoveMemory(paramInt, new short[] { 20 }, 2);
      COM.MoveMemory(paramInt + 8, new long[] { paramNumber.longValue() }, 8);
    }
    else
    {
      COM.MoveMemory(paramInt, new short[] { 3 }, 2);
      COM.MoveMemory(paramInt + 8, new int[] { paramNumber.intValue() }, 4);
    }
  }

  void setString(int paramInt, String paramString)
  {
    int i = 0;
    if (paramString != null)
    {
      char[] arrayOfChar = (paramString + "").toCharArray();
      i = COM.SysAllocString(arrayOfChar);
    }
    COM.MoveMemory(paramInt, new int[] { i }, OS.PTR_SIZEOF);
  }

  void setStringVARIANT(int paramInt, String paramString)
  {
    int i = 0;
    if (paramString != null)
    {
      char[] arrayOfChar = (paramString + "").toCharArray();
      i = COM.SysAllocString(arrayOfChar);
    }
    COM.MoveMemory(paramInt, new short[] { i == 0 ? 0 : 8 }, 2);
    COM.MoveMemory(paramInt + 8, new int[] { i }, OS.PTR_SIZEOF);
  }

  void checkWidget()
  {
    if (!isValidThread())
      SWT.error(22);
    if (this.control.isDisposed())
      SWT.error(24);
  }

  boolean isValidThread()
  {
    return this.control.getDisplay().getThread() == Thread.currentThread();
  }

  static void print(String paramString)
  {
  }

  String getRoleString(int paramInt)
  {
    return "Unknown role (" + paramInt + ")";
  }

  String getStateString(int paramInt)
  {
    if (paramInt == 0)
      return " no state bits set";
    StringBuffer localStringBuffer = new StringBuffer();
    return localStringBuffer.toString();
  }

  String getIA2StatesString(int paramInt)
  {
    if (paramInt == 0)
      return " no state bits set";
    StringBuffer localStringBuffer = new StringBuffer();
    return localStringBuffer.toString();
  }

  String getEventString(int paramInt)
  {
    return "Unknown event (" + paramInt + ")";
  }

  private String hresult(int paramInt)
  {
    return " HRESULT=" + paramInt;
  }

  boolean interesting(GUID paramGUID)
  {
    return false;
  }

  String guidString(GUID paramGUID)
  {
    return StringFromIID(paramGUID);
  }

  static GUID IIDFromString(String paramString)
  {
    return null;
  }

  static String StringFromIID(GUID paramGUID)
  {
    return '{' + toHex(paramGUID.Data1, 8) + "-" + toHex(paramGUID.Data2, 4) + "-" + toHex(paramGUID.Data3, 4) + "-" + toHex(paramGUID.Data4[0], 2) + toHex(paramGUID.Data4[1], 2) + "-" + toHex(paramGUID.Data4[2], 2) + toHex(paramGUID.Data4[3], 2) + toHex(paramGUID.Data4[4], 2) + toHex(paramGUID.Data4[5], 2) + toHex(paramGUID.Data4[6], 2) + toHex(paramGUID.Data4[7], 2) + '}';
  }

  static String toHex(int paramInt1, int paramInt2)
  {
    String str = Integer.toHexString(paramInt1).toUpperCase();
    int i = str.length();
    if (i > paramInt2)
      str = str.substring(i - paramInt2);
    return "00000000".substring(0, Math.max(0, paramInt2 - i)) + str;
  }

  public String toString()
  {
    String str = super.toString();
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.Accessible
 * JD-Core Version:    0.6.2
 */