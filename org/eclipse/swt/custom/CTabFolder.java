package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TypedListener;

public class CTabFolder extends Composite
{
  public int marginWidth = 0;
  public int marginHeight = 0;

  /** @deprecated */
  public int MIN_TAB_WIDTH = 4;

  /** @deprecated */
  public static RGB borderInsideRGB = new RGB(132, 130, 132);

  /** @deprecated */
  public static RGB borderMiddleRGB = new RGB(143, 141, 138);

  /** @deprecated */
  public static RGB borderOutsideRGB = new RGB(171, 168, 165);
  boolean onBottom = false;
  boolean single = false;
  boolean simple = true;
  int fixedTabHeight = -1;
  int tabHeight;
  int minChars = 20;
  boolean borderVisible = false;
  CTabFolderRenderer renderer;
  CTabItem[] items = new CTabItem[0];
  int firstIndex = -1;
  int selectedIndex = -1;
  int[] priority = new int[0];
  boolean mru = false;
  Listener listener;
  boolean ignoreTraverse;
  CTabFolder2Listener[] folderListeners = new CTabFolder2Listener[0];
  CTabFolderListener[] tabListeners = new CTabFolderListener[0];
  Image selectionBgImage;
  Color[] selectionGradientColors;
  int[] selectionGradientPercents;
  boolean selectionGradientVertical;
  Color selectionForeground;
  Color selectionBackground;
  Color[] gradientColors;
  int[] gradientPercents;
  boolean gradientVertical;
  boolean showUnselectedImage = true;
  boolean showClose = false;
  boolean showUnselectedClose = true;
  Rectangle chevronRect = new Rectangle(0, 0, 0, 0);
  int chevronImageState = 0;
  boolean showChevron = false;
  Menu showMenu;
  boolean showMin = false;
  Rectangle minRect = new Rectangle(0, 0, 0, 0);
  boolean minimized = false;
  int minImageState = 0;
  boolean showMax = false;
  Rectangle maxRect = new Rectangle(0, 0, 0, 0);
  boolean maximized = false;
  int maxImageState = 0;
  Control topRight;
  Rectangle topRightRect = new Rectangle(0, 0, 0, 0);
  int topRightAlignment = 131072;
  boolean inDispose = false;
  Point oldSize;
  Font oldFont;
  static final int DEFAULT_WIDTH = 64;
  static final int DEFAULT_HEIGHT = 64;
  static final int SELECTION_FOREGROUND = 24;
  static final int SELECTION_BACKGROUND = 25;
  static final int FOREGROUND = 21;
  static final int BACKGROUND = 22;
  static final int CHEVRON_CHILD_ID = 0;
  static final int MINIMIZE_CHILD_ID = 1;
  static final int MAXIMIZE_CHILD_ID = 2;
  static final int EXTRA_CHILD_ID_COUNT = 3;

  public CTabFolder(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramComposite, paramInt));
    init(paramInt);
  }

  void init(int paramInt)
  {
    super.setLayout(new CTabFolderLayout());
    int i = super.getStyle();
    this.oldFont = getFont();
    this.onBottom = ((i & 0x400) != 0);
    this.showClose = ((i & 0x40) != 0);
    this.single = ((i & 0x4) != 0);
    this.borderVisible = ((paramInt & 0x800) != 0);
    Display localDisplay = getDisplay();
    this.selectionForeground = localDisplay.getSystemColor(24);
    this.selectionBackground = localDisplay.getSystemColor(25);
    this.renderer = new CTabFolderRenderer(this);
    updateTabHeight(false);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          CTabFolder.this.onDispose(paramAnonymousEvent);
          break;
        case 29:
          CTabFolder.this.onDragDetect(paramAnonymousEvent);
          break;
        case 15:
          CTabFolder.this.onFocus(paramAnonymousEvent);
          break;
        case 16:
          CTabFolder.this.onFocus(paramAnonymousEvent);
          break;
        case 1:
          CTabFolder.this.onKeyDown(paramAnonymousEvent);
          break;
        case 8:
          CTabFolder.this.onMouseDoubleClick(paramAnonymousEvent);
          break;
        case 3:
          CTabFolder.this.onMouse(paramAnonymousEvent);
          break;
        case 6:
          CTabFolder.this.onMouse(paramAnonymousEvent);
          break;
        case 7:
          CTabFolder.this.onMouse(paramAnonymousEvent);
          break;
        case 5:
          CTabFolder.this.onMouse(paramAnonymousEvent);
          break;
        case 4:
          CTabFolder.this.onMouse(paramAnonymousEvent);
          break;
        case 9:
          CTabFolder.this.onPaint(paramAnonymousEvent);
          break;
        case 11:
          CTabFolder.this.onResize();
          break;
        case 31:
          CTabFolder.this.onTraverse(paramAnonymousEvent);
        case 2:
        case 10:
        case 13:
        case 14:
        case 17:
        case 18:
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 24:
        case 25:
        case 26:
        case 27:
        case 28:
        case 30:
        }
      }
    };
    int[] arrayOfInt = { 12, 29, 15, 16, 1, 8, 3, 6, 7, 5, 4, 9, 11, 31 };
    for (int j = 0; j < arrayOfInt.length; j++)
      addListener(arrayOfInt[j], this.listener);
    initAccessible();
  }

  static int checkStyle(Composite paramComposite, int paramInt)
  {
    int i = 109053126;
    paramInt &= i;
    if ((paramInt & 0x80) != 0)
      paramInt &= -1025;
    if ((paramInt & 0x2) != 0)
      paramInt &= -5;
    paramInt |= 1048576;
    if ((paramInt & 0x4000000) != 0)
      return paramInt;
    if (((paramComposite.getStyle() & 0x8000000) != 0) && ((paramInt & 0x2000000) == 0))
      return paramInt;
    return paramInt | 0x20000000;
  }

  public void addCTabFolder2Listener(CTabFolder2Listener paramCTabFolder2Listener)
  {
    checkWidget();
    if (paramCTabFolder2Listener == null)
      SWT.error(4);
    CTabFolder2Listener[] arrayOfCTabFolder2Listener = new CTabFolder2Listener[this.folderListeners.length + 1];
    System.arraycopy(this.folderListeners, 0, arrayOfCTabFolder2Listener, 0, this.folderListeners.length);
    this.folderListeners = arrayOfCTabFolder2Listener;
    this.folderListeners[(this.folderListeners.length - 1)] = paramCTabFolder2Listener;
  }

  /** @deprecated */
  public void addCTabFolderListener(CTabFolderListener paramCTabFolderListener)
  {
    checkWidget();
    if (paramCTabFolderListener == null)
      SWT.error(4);
    CTabFolderListener[] arrayOfCTabFolderListener = new CTabFolderListener[this.tabListeners.length + 1];
    System.arraycopy(this.tabListeners, 0, arrayOfCTabFolderListener, 0, this.tabListeners.length);
    this.tabListeners = arrayOfCTabFolderListener;
    this.tabListeners[(this.tabListeners.length - 1)] = paramCTabFolderListener;
    if (!this.showClose)
    {
      this.showClose = true;
      updateItems();
      redraw();
    }
  }

  public void addSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    TypedListener localTypedListener = new TypedListener(paramSelectionListener);
    addListener(13, localTypedListener);
    addListener(14, localTypedListener);
  }

  public Rectangle computeTrim(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    return this.renderer.computeTrim(-1, 0, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  void createItem(CTabItem paramCTabItem, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > getItemCount()))
      SWT.error(6);
    paramCTabItem.parent = this;
    CTabItem[] arrayOfCTabItem = new CTabItem[this.items.length + 1];
    System.arraycopy(this.items, 0, arrayOfCTabItem, 0, paramInt);
    arrayOfCTabItem[paramInt] = paramCTabItem;
    System.arraycopy(this.items, paramInt, arrayOfCTabItem, paramInt + 1, this.items.length - paramInt);
    this.items = arrayOfCTabItem;
    if (this.selectedIndex >= paramInt)
      this.selectedIndex += 1;
    int[] arrayOfInt = new int[this.priority.length + 1];
    int i = 0;
    int j = this.priority.length;
    for (int k = 0; k < this.priority.length; k++)
    {
      if ((!this.mru) && (this.priority[k] == paramInt))
        j = i++;
      arrayOfInt[(i++)] = (this.priority[k] >= paramInt ? this.priority[k] + 1 : this.priority[k]);
    }
    arrayOfInt[j] = paramInt;
    this.priority = arrayOfInt;
    if (this.items.length == 1)
    {
      if (!updateTabHeight(false))
        updateItems();
      redraw();
    }
    else
    {
      updateItems();
      redrawTabs();
    }
  }

  void destroyItem(CTabItem paramCTabItem)
  {
    if (this.inDispose)
      return;
    int i = indexOf(paramCTabItem);
    if (i == -1)
      return;
    if (this.items.length == 1)
    {
      this.items = new CTabItem[0];
      this.priority = new int[0];
      this.firstIndex = -1;
      this.selectedIndex = -1;
      localObject1 = paramCTabItem.getControl();
      if ((localObject1 != null) && (!((Control)localObject1).isDisposed()))
        ((Control)localObject1).setVisible(false);
      setToolTipText(null);
      localObject2 = new GC(this);
      setButtonBounds((GC)localObject2);
      ((GC)localObject2).dispose();
      redraw();
      return;
    }
    Object localObject1 = new CTabItem[this.items.length - 1];
    System.arraycopy(this.items, 0, localObject1, 0, i);
    System.arraycopy(this.items, i + 1, localObject1, i, this.items.length - i - 1);
    this.items = ((CTabItem[])localObject1);
    Object localObject2 = new int[this.priority.length - 1];
    int j = 0;
    for (int k = 0; k < this.priority.length; k++)
      if (this.priority[k] != i)
        localObject2[(j++)] = (this.priority[k] > i ? this.priority[k] - 1 : this.priority[k]);
    this.priority = ((int[])localObject2);
    if (this.selectedIndex == i)
    {
      Control localControl = paramCTabItem.getControl();
      this.selectedIndex = -1;
      int m = this.mru ? this.priority[0] : Math.max(0, i - 1);
      setSelection(m, true);
      if ((localControl != null) && (!localControl.isDisposed()))
        localControl.setVisible(false);
    }
    else if (this.selectedIndex > i)
    {
      this.selectedIndex -= 1;
    }
    updateItems();
    redrawTabs();
  }

  public boolean getBorderVisible()
  {
    checkWidget();
    return this.borderVisible;
  }

  public Rectangle getClientArea()
  {
    checkWidget();
    Rectangle localRectangle = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
    if (this.minimized)
      return new Rectangle(-localRectangle.x, -localRectangle.y, 0, 0);
    Point localPoint = getSize();
    int i = localPoint.x - localRectangle.width;
    int j = localPoint.y - localRectangle.height;
    return new Rectangle(-localRectangle.x, -localRectangle.y, i, j);
  }

  public CTabItem getItem(int paramInt)
  {
    if ((paramInt < 0) || (paramInt >= this.items.length))
      SWT.error(6);
    return this.items[paramInt];
  }

  public CTabItem getItem(Point paramPoint)
  {
    if (this.items.length == 0)
      return null;
    Point localPoint = getSize();
    Rectangle localRectangle1 = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
    if (localPoint.x <= localRectangle1.width)
      return null;
    if ((this.showChevron) && (this.chevronRect.contains(paramPoint)))
      return null;
    for (int i = 0; i < this.priority.length; i++)
    {
      CTabItem localCTabItem = this.items[this.priority[i]];
      Rectangle localRectangle2 = localCTabItem.getBounds();
      if (localRectangle2.contains(paramPoint))
        return localCTabItem;
    }
    return null;
  }

  public int getItemCount()
  {
    return this.items.length;
  }

  public CTabItem[] getItems()
  {
    CTabItem[] arrayOfCTabItem = new CTabItem[this.items.length];
    System.arraycopy(this.items, 0, arrayOfCTabItem, 0, this.items.length);
    return arrayOfCTabItem;
  }

  char _findMnemonic(String paramString)
  {
    if (paramString == null)
      return '\000';
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return '\000';
      if (paramString.charAt(i) != '&')
        return Character.toLowerCase(paramString.charAt(i));
      i++;
    }
    while (i < j);
    return '\000';
  }

  String stripMnemonic(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return paramString;
      if (paramString.charAt(i) != '&')
        return paramString.substring(0, i - 1) + paramString.substring(i, j);
      i++;
    }
    while (i < j);
    return paramString;
  }

  public boolean getMinimized()
  {
    checkWidget();
    return this.minimized;
  }

  public boolean getMinimizeVisible()
  {
    checkWidget();
    return this.showMin;
  }

  public int getMinimumCharacters()
  {
    checkWidget();
    return this.minChars;
  }

  public boolean getMaximized()
  {
    checkWidget();
    return this.maximized;
  }

  public boolean getMaximizeVisible()
  {
    checkWidget();
    return this.showMax;
  }

  public boolean getMRUVisible()
  {
    checkWidget();
    return this.mru;
  }

  public CTabFolderRenderer getRenderer()
  {
    checkWidget();
    return this.renderer;
  }

  int getRightItemEdge(GC paramGC)
  {
    Rectangle localRectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
    int i = getSize().x - (localRectangle.width + localRectangle.x) - 3;
    if (this.showMin)
      i -= this.renderer.computeSize(-6, 0, paramGC, -1, -1).x;
    if (this.showMax)
      i -= this.renderer.computeSize(-5, 0, paramGC, -1, -1).x;
    if (this.showChevron)
      i -= this.renderer.computeSize(-7, 0, paramGC, -1, -1).x;
    if ((this.topRight != null) && (this.topRightAlignment != 4))
    {
      Point localPoint = this.topRight.computeSize(-1, -1);
      i -= localPoint.x + 3;
    }
    return Math.max(0, i);
  }

  public CTabItem getSelection()
  {
    if (this.selectedIndex == -1)
      return null;
    return this.items[this.selectedIndex];
  }

  public Color getSelectionBackground()
  {
    checkWidget();
    return this.selectionBackground;
  }

  public Color getSelectionForeground()
  {
    checkWidget();
    return this.selectionForeground;
  }

  public int getSelectionIndex()
  {
    return this.selectedIndex;
  }

  public boolean getSimple()
  {
    checkWidget();
    return this.simple;
  }

  public boolean getSingle()
  {
    checkWidget();
    return this.single;
  }

  public int getStyle()
  {
    int i = super.getStyle();
    i &= -1153;
    i |= (this.onBottom ? 1024 : 128);
    i &= -7;
    i |= (this.single ? 4 : 2);
    if (this.borderVisible)
      i |= 2048;
    i &= -65;
    if (this.showClose)
      i |= 64;
    return i;
  }

  public int getTabHeight()
  {
    checkWidget();
    if (this.fixedTabHeight != -1)
      return this.fixedTabHeight;
    return this.tabHeight - 1;
  }

  public int getTabPosition()
  {
    checkWidget();
    return this.onBottom ? 1024 : 128;
  }

  public Control getTopRight()
  {
    checkWidget();
    return this.topRight;
  }

  public int getTopRightAlignment()
  {
    checkWidget();
    return this.topRightAlignment;
  }

  public boolean getUnselectedCloseVisible()
  {
    checkWidget();
    return this.showUnselectedClose;
  }

  public boolean getUnselectedImageVisible()
  {
    checkWidget();
    return this.showUnselectedImage;
  }

  public int indexOf(CTabItem paramCTabItem)
  {
    checkWidget();
    if (paramCTabItem == null)
      SWT.error(4);
    for (int i = 0; i < this.items.length; i++)
      if (this.items[i] == paramCTabItem)
        return i;
    return -1;
  }

  void initAccessible()
  {
    Accessible localAccessible = getAccessible();
    localAccessible.addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str = null;
        int i = paramAnonymousAccessibleEvent.childID;
        if ((i >= 0) && (i < CTabFolder.this.items.length))
          str = CTabFolder.this.stripMnemonic(CTabFolder.this.items[i].getText());
        else if (i == CTabFolder.this.items.length + 0)
          str = SWT.getMessage("SWT_ShowList");
        else if (i == CTabFolder.this.items.length + 1)
          str = CTabFolder.this.minimized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Minimize");
        else if (i == CTabFolder.this.items.length + 2)
          str = CTabFolder.this.maximized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Maximize");
        paramAnonymousAccessibleEvent.result = str;
      }

      public void getHelp(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str = null;
        int i = paramAnonymousAccessibleEvent.childID;
        if (i == -1)
          str = CTabFolder.this.getToolTipText();
        else if ((i >= 0) && (i < CTabFolder.this.items.length))
          str = CTabFolder.this.items[i].getToolTipText();
        paramAnonymousAccessibleEvent.result = str;
      }

      public void getKeyboardShortcut(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str1 = null;
        int i = paramAnonymousAccessibleEvent.childID;
        if ((i >= 0) && (i < CTabFolder.this.items.length))
        {
          String str2 = CTabFolder.this.items[i].getText();
          if (str2 != null)
          {
            char c = CTabFolder.this._findMnemonic(str2);
            if (c != 0)
              str1 = "Alt+" + c;
          }
        }
        paramAnonymousAccessibleEvent.result = str1;
      }
    });
    localAccessible.addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getChildAtPoint(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Point localPoint = CTabFolder.this.toControl(paramAnonymousAccessibleControlEvent.x, paramAnonymousAccessibleControlEvent.y);
        int i = -2;
        for (int j = 0; j < CTabFolder.this.items.length; j++)
          if (CTabFolder.this.items[j].getBounds().contains(localPoint))
          {
            i = j;
            break;
          }
        if (i == -2)
          if ((CTabFolder.this.showChevron) && (CTabFolder.this.chevronRect.contains(localPoint)))
          {
            i = CTabFolder.this.items.length + 0;
          }
          else if ((CTabFolder.this.showMin) && (CTabFolder.this.minRect.contains(localPoint)))
          {
            i = CTabFolder.this.items.length + 1;
          }
          else if ((CTabFolder.this.showMax) && (CTabFolder.this.maxRect.contains(localPoint)))
          {
            i = CTabFolder.this.items.length + 2;
          }
          else
          {
            Rectangle localRectangle = CTabFolder.this.getBounds();
            localRectangle.height -= CTabFolder.this.getClientArea().height;
            if (localRectangle.contains(localPoint))
              i = -1;
          }
        paramAnonymousAccessibleControlEvent.childID = i;
      }

      public void getLocation(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Rectangle localRectangle = null;
        Point localPoint = null;
        int i = paramAnonymousAccessibleControlEvent.childID;
        if (i == -1)
        {
          localRectangle = CTabFolder.this.getBounds();
          localPoint = CTabFolder.this.getParent().toDisplay(localRectangle.x, localRectangle.y);
        }
        else
        {
          if ((i >= 0) && (i < CTabFolder.this.items.length) && (CTabFolder.this.items[i].isShowing()))
            localRectangle = CTabFolder.this.items[i].getBounds();
          else if ((CTabFolder.this.showChevron) && (i == CTabFolder.this.items.length + 0))
            localRectangle = CTabFolder.this.chevronRect;
          else if ((CTabFolder.this.showMin) && (i == CTabFolder.this.items.length + 1))
            localRectangle = CTabFolder.this.minRect;
          else if ((CTabFolder.this.showMax) && (i == CTabFolder.this.items.length + 2))
            localRectangle = CTabFolder.this.maxRect;
          if (localRectangle != null)
            localPoint = CTabFolder.this.toDisplay(localRectangle.x, localRectangle.y);
        }
        if ((localRectangle != null) && (localPoint != null))
        {
          paramAnonymousAccessibleControlEvent.x = localPoint.x;
          paramAnonymousAccessibleControlEvent.y = localPoint.y;
          paramAnonymousAccessibleControlEvent.width = localRectangle.width;
          paramAnonymousAccessibleControlEvent.height = localRectangle.height;
        }
      }

      public void getChildCount(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = (CTabFolder.this.items.length + 3);
      }

      public void getDefaultAction(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        String str = null;
        int i = paramAnonymousAccessibleControlEvent.childID;
        if ((i >= 0) && (i < CTabFolder.this.items.length))
          str = SWT.getMessage("SWT_Switch");
        if ((i >= CTabFolder.this.items.length) && (i < CTabFolder.this.items.length + 3))
          str = SWT.getMessage("SWT_Press");
        paramAnonymousAccessibleControlEvent.result = str;
      }

      public void getFocus(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        int i = -2;
        if (CTabFolder.this.isFocusControl())
          if (CTabFolder.this.selectedIndex == -1)
            i = -1;
          else
            i = CTabFolder.this.selectedIndex;
        paramAnonymousAccessibleControlEvent.childID = i;
      }

      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        int i = 0;
        int j = paramAnonymousAccessibleControlEvent.childID;
        if (j == -1)
          i = 60;
        else if ((j >= 0) && (j < CTabFolder.this.items.length))
          i = 37;
        else if ((j >= CTabFolder.this.items.length) && (j < CTabFolder.this.items.length + 3))
          i = 43;
        paramAnonymousAccessibleControlEvent.detail = i;
      }

      public void getSelection(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.childID = (CTabFolder.this.selectedIndex == -1 ? -2 : CTabFolder.this.selectedIndex);
      }

      public void getState(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        int i = 0;
        int j = paramAnonymousAccessibleControlEvent.childID;
        if (j == -1)
        {
          i = 0;
        }
        else if ((j >= 0) && (j < CTabFolder.this.items.length))
        {
          i = 2097152;
          if (CTabFolder.this.isFocusControl())
            i |= 1048576;
          if (CTabFolder.this.selectedIndex == j)
          {
            i |= 2;
            if (CTabFolder.this.isFocusControl())
              i |= 4;
          }
        }
        else if (j == CTabFolder.this.items.length + 0)
        {
          i = CTabFolder.this.showChevron ? 0 : 32768;
        }
        else if (j == CTabFolder.this.items.length + 1)
        {
          i = CTabFolder.this.showMin ? 0 : 32768;
        }
        else if (j == CTabFolder.this.items.length + 2)
        {
          i = CTabFolder.this.showMax ? 0 : 32768;
        }
        paramAnonymousAccessibleControlEvent.detail = i;
      }

      public void getChildren(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        int i = CTabFolder.this.items.length + 3;
        Object[] arrayOfObject = new Object[i];
        for (int j = 0; j < i; j++)
          arrayOfObject[j] = new Integer(j);
        paramAnonymousAccessibleControlEvent.children = arrayOfObject;
      }
    });
    addListener(13, new Listener()
    {
      private final Accessible val$accessible;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (CTabFolder.this.isFocusControl())
          if (CTabFolder.this.selectedIndex == -1)
            this.val$accessible.setFocus(-1);
          else
            this.val$accessible.setFocus(CTabFolder.this.selectedIndex);
      }
    });
    addListener(15, new Listener()
    {
      private final Accessible val$accessible;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (CTabFolder.this.selectedIndex == -1)
          this.val$accessible.setFocus(-1);
        else
          this.val$accessible.setFocus(CTabFolder.this.selectedIndex);
      }
    });
  }

  void onKeyDown(Event paramEvent)
  {
    switch (paramEvent.keyCode)
    {
    case 16777219:
    case 16777220:
      int i = this.items.length;
      if (i == 0)
        return;
      if (this.selectedIndex == -1)
        return;
      int j = (getStyle() & 0x4000000) != 0 ? 16777220 : 16777219;
      int k = paramEvent.keyCode == j ? -1 : 1;
      int m;
      if (!this.mru)
      {
        m = this.selectedIndex + k;
      }
      else
      {
        int[] arrayOfInt = new int[this.items.length];
        int n = 0;
        int i1 = -1;
        for (int i2 = 0; i2 < this.items.length; i2++)
          if (this.items[i2].showing)
          {
            if (i2 == this.selectedIndex)
              i1 = n;
            arrayOfInt[(n++)] = i2;
          }
        if ((i1 + k >= 0) && (i1 + k < n))
        {
          m = arrayOfInt[(i1 + k)];
        }
        else
        {
          if (this.showChevron)
          {
            CTabFolderEvent localCTabFolderEvent = new CTabFolderEvent(this);
            localCTabFolderEvent.widget = this;
            localCTabFolderEvent.time = paramEvent.time;
            localCTabFolderEvent.x = this.chevronRect.x;
            localCTabFolderEvent.y = this.chevronRect.y;
            localCTabFolderEvent.width = this.chevronRect.width;
            localCTabFolderEvent.height = this.chevronRect.height;
            localCTabFolderEvent.doit = true;
            for (int i3 = 0; i3 < this.folderListeners.length; i3++)
              this.folderListeners[i3].showList(localCTabFolderEvent);
            if ((localCTabFolderEvent.doit) && (!isDisposed()))
              showList(this.chevronRect);
          }
          return;
        }
      }
      if ((m < 0) || (m >= i))
        return;
      setSelection(m, true);
      forceFocus();
    }
  }

  void onDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    this.inDispose = true;
    if ((this.showMenu != null) && (!this.showMenu.isDisposed()))
    {
      this.showMenu.dispose();
      this.showMenu = null;
    }
    int i = this.items.length;
    for (int j = 0; j < i; j++)
      if (this.items[j] != null)
        this.items[j].dispose();
    this.selectionGradientColors = null;
    this.selectionGradientPercents = null;
    this.selectionBgImage = null;
    this.selectionBackground = null;
    this.selectionForeground = null;
    if (this.renderer != null)
      this.renderer.dispose();
    this.renderer = null;
  }

  void onDragDetect(Event paramEvent)
  {
    int i = 0;
    if ((this.chevronRect.contains(paramEvent.x, paramEvent.y)) || (this.minRect.contains(paramEvent.x, paramEvent.y)) || (this.maxRect.contains(paramEvent.x, paramEvent.y)))
      i = 1;
    else
      for (int j = 0; j < this.items.length; j++)
        if (this.items[j].closeRect.contains(paramEvent.x, paramEvent.y))
        {
          i = 1;
          break;
        }
    if (i != 0)
      paramEvent.type = 0;
  }

  void onFocus(Event paramEvent)
  {
    checkWidget();
    if (this.selectedIndex >= 0)
      redraw();
    else
      setSelection(0, true);
  }

  boolean onMnemonic(Event paramEvent, boolean paramBoolean)
  {
    char c = paramEvent.character;
    for (int i = 0; i < this.items.length; i++)
      if (this.items[i] != null)
      {
        int j = _findMnemonic(this.items[i].getText());
        if ((j != 0) && (Character.toLowerCase(c) == j))
        {
          if (paramBoolean)
          {
            setSelection(i, true);
            forceFocus();
          }
          return true;
        }
      }
    return false;
  }

  void onMouseDoubleClick(Event paramEvent)
  {
    if ((paramEvent.button != 1) || ((paramEvent.stateMask & 0x100000) != 0) || ((paramEvent.stateMask & 0x200000) != 0))
      return;
    Event localEvent = new Event();
    localEvent.item = getItem(new Point(paramEvent.x, paramEvent.y));
    if (localEvent.item != null)
      notifyListeners(14, localEvent);
  }

  void onMouse(Event paramEvent)
  {
    int i = paramEvent.x;
    int j = paramEvent.y;
    Object localObject1;
    int n;
    int i2;
    int m;
    int i3;
    CTabItem localCTabItem3;
    switch (paramEvent.type)
    {
    case 6:
      setToolTipText(null);
      break;
    case 7:
      if (this.minImageState != 0)
      {
        this.minImageState = 0;
        redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
      }
      if (this.maxImageState != 0)
      {
        this.maxImageState = 0;
        redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
      }
      if (this.chevronImageState != 0)
      {
        this.chevronImageState = 0;
        redraw(this.chevronRect.x, this.chevronRect.y, this.chevronRect.width, this.chevronRect.height, false);
      }
      for (int k = 0; k < this.items.length; k++)
      {
        localObject1 = this.items[k];
        if ((k != this.selectedIndex) && (((CTabItem)localObject1).closeImageState != 8))
        {
          ((CTabItem)localObject1).closeImageState = 8;
          redraw(((CTabItem)localObject1).closeRect.x, ((CTabItem)localObject1).closeRect.y, ((CTabItem)localObject1).closeRect.width, ((CTabItem)localObject1).closeRect.height, false);
        }
        if ((((CTabItem)localObject1).state & 0x20) != 0)
        {
          localObject1.state &= -33;
          redraw(((CTabItem)localObject1).x, ((CTabItem)localObject1).y, ((CTabItem)localObject1).width, ((CTabItem)localObject1).height, false);
        }
        if ((k == this.selectedIndex) && (((CTabItem)localObject1).closeImageState != 0))
        {
          ((CTabItem)localObject1).closeImageState = 0;
          redraw(((CTabItem)localObject1).closeRect.x, ((CTabItem)localObject1).closeRect.y, ((CTabItem)localObject1).closeRect.width, ((CTabItem)localObject1).closeRect.height, false);
        }
      }
      break;
    case 3:
      if (paramEvent.button != 1)
        return;
      if (this.minRect.contains(i, j))
      {
        this.minImageState = 2;
        redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
        update();
        return;
      }
      if (this.maxRect.contains(i, j))
      {
        this.maxImageState = 2;
        redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
        update();
        return;
      }
      if (this.chevronRect.contains(i, j))
      {
        if (this.chevronImageState != 32)
          this.chevronImageState = 32;
        else
          this.chevronImageState = 2;
        redraw(this.chevronRect.x, this.chevronRect.y, this.chevronRect.width, this.chevronRect.height, false);
        update();
        return;
      }
      CTabItem localCTabItem1 = null;
      if (this.single)
      {
        if (this.selectedIndex != -1)
        {
          localObject1 = this.items[this.selectedIndex].getBounds();
          if (((Rectangle)localObject1).contains(i, j))
            localCTabItem1 = this.items[this.selectedIndex];
        }
      }
      else
        for (n = 0; n < this.items.length; n++)
        {
          Rectangle localRectangle = this.items[n].getBounds();
          if (localRectangle.contains(i, j))
            localCTabItem1 = this.items[n];
        }
      if (localCTabItem1 != null)
      {
        if (localCTabItem1.closeRect.contains(i, j))
        {
          localCTabItem1.closeImageState = 2;
          redraw(localCTabItem1.closeRect.x, localCTabItem1.closeRect.y, localCTabItem1.closeRect.width, localCTabItem1.closeRect.height, false);
          update();
          return;
        }
        n = indexOf(localCTabItem1);
        if (localCTabItem1.showing)
        {
          i2 = this.selectedIndex;
          setSelection(n, true);
          if (i2 == this.selectedIndex)
            forceFocus();
        }
        return;
      }
      break;
    case 5:
      _setToolTipText(paramEvent.x, paramEvent.y);
      m = 0;
      n = 0;
      i2 = 0;
      i3 = 0;
      if (this.minRect.contains(i, j))
      {
        n = 1;
        if ((this.minImageState != 2) && (this.minImageState != 32))
        {
          this.minImageState = 32;
          redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
        }
      }
      if (this.maxRect.contains(i, j))
      {
        i2 = 1;
        if ((this.maxImageState != 2) && (this.maxImageState != 32))
        {
          this.maxImageState = 32;
          redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
        }
      }
      if (this.chevronRect.contains(i, j))
      {
        i3 = 1;
        if ((this.chevronImageState != 2) && (this.chevronImageState != 32))
        {
          this.chevronImageState = 32;
          redraw(this.chevronRect.x, this.chevronRect.y, this.chevronRect.width, this.chevronRect.height, false);
        }
      }
      if ((this.minImageState != 0) && (n == 0))
      {
        this.minImageState = 0;
        redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
      }
      if ((this.maxImageState != 0) && (i2 == 0))
      {
        this.maxImageState = 0;
        redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
      }
      if ((this.chevronImageState != 0) && (i3 == 0))
      {
        this.chevronImageState = 0;
        redraw(this.chevronRect.x, this.chevronRect.y, this.chevronRect.width, this.chevronRect.height, false);
      }
      for (int i4 = 0; i4 < this.items.length; i4++)
      {
        localCTabItem3 = this.items[i4];
        m = 0;
        if (localCTabItem3.getBounds().contains(i, j))
        {
          m = 1;
          if (localCTabItem3.closeRect.contains(i, j))
          {
            if ((localCTabItem3.closeImageState != 2) && (localCTabItem3.closeImageState != 32))
            {
              localCTabItem3.closeImageState = 32;
              redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
            }
          }
          else if (localCTabItem3.closeImageState != 0)
          {
            localCTabItem3.closeImageState = 0;
            redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
          }
          if ((localCTabItem3.state & 0x20) == 0)
          {
            localCTabItem3.state |= 32;
            redraw(localCTabItem3.x, localCTabItem3.y, localCTabItem3.width, localCTabItem3.height, false);
          }
        }
        if ((i4 != this.selectedIndex) && (localCTabItem3.closeImageState != 8) && (m == 0))
        {
          localCTabItem3.closeImageState = 8;
          redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
        }
        if (((localCTabItem3.state & 0x20) != 0) && (m == 0))
        {
          localCTabItem3.state &= -33;
          redraw(localCTabItem3.x, localCTabItem3.y, localCTabItem3.width, localCTabItem3.height, false);
        }
        if ((i4 == this.selectedIndex) && (localCTabItem3.closeImageState != 0) && (m == 0))
        {
          localCTabItem3.closeImageState = 0;
          redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
        }
      }
      break;
    case 4:
      if (paramEvent.button != 1)
        return;
      Object localObject2;
      if (this.chevronRect.contains(i, j))
      {
        m = this.chevronImageState == 2 ? 1 : 0;
        if (m == 0)
          return;
        localObject2 = new CTabFolderEvent(this);
        ((CTabFolderEvent)localObject2).widget = this;
        ((CTabFolderEvent)localObject2).time = paramEvent.time;
        ((CTabFolderEvent)localObject2).x = this.chevronRect.x;
        ((CTabFolderEvent)localObject2).y = this.chevronRect.y;
        ((CTabFolderEvent)localObject2).width = this.chevronRect.width;
        ((CTabFolderEvent)localObject2).height = this.chevronRect.height;
        ((CTabFolderEvent)localObject2).doit = true;
        for (i2 = 0; i2 < this.folderListeners.length; i2++)
          this.folderListeners[i2].showList((CTabFolderEvent)localObject2);
        if ((((CTabFolderEvent)localObject2).doit) && (!isDisposed()))
          showList(this.chevronRect);
        return;
      }
      if (this.minRect.contains(i, j))
      {
        m = this.minImageState == 2 ? 1 : 0;
        this.minImageState = 32;
        redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
        if (m == 0)
          return;
        localObject2 = new CTabFolderEvent(this);
        ((CTabFolderEvent)localObject2).widget = this;
        ((CTabFolderEvent)localObject2).time = paramEvent.time;
        for (i2 = 0; i2 < this.folderListeners.length; i2++)
          if (this.minimized)
            this.folderListeners[i2].restore((CTabFolderEvent)localObject2);
          else
            this.folderListeners[i2].minimize((CTabFolderEvent)localObject2);
        return;
      }
      if (this.maxRect.contains(i, j))
      {
        m = this.maxImageState == 2 ? 1 : 0;
        this.maxImageState = 32;
        redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
        if (m == 0)
          return;
        localObject2 = new CTabFolderEvent(this);
        ((CTabFolderEvent)localObject2).widget = this;
        ((CTabFolderEvent)localObject2).time = paramEvent.time;
        for (i2 = 0; i2 < this.folderListeners.length; i2++)
          if (this.maximized)
            this.folderListeners[i2].restore((CTabFolderEvent)localObject2);
          else
            this.folderListeners[i2].maximize((CTabFolderEvent)localObject2);
        return;
      }
      CTabItem localCTabItem2 = null;
      int i1;
      Object localObject3;
      if (this.single)
      {
        if (this.selectedIndex != -1)
        {
          localObject2 = this.items[this.selectedIndex].getBounds();
          if (((Rectangle)localObject2).contains(i, j))
            localCTabItem2 = this.items[this.selectedIndex];
        }
      }
      else
        for (i1 = 0; i1 < this.items.length; i1++)
        {
          localObject3 = this.items[i1].getBounds();
          if (((Rectangle)localObject3).contains(i, j))
            localCTabItem2 = this.items[i1];
        }
      if ((localCTabItem2 != null) && (localCTabItem2.closeRect.contains(i, j)))
      {
        i1 = localCTabItem2.closeImageState == 2 ? 1 : 0;
        localCTabItem2.closeImageState = 32;
        redraw(localCTabItem2.closeRect.x, localCTabItem2.closeRect.y, localCTabItem2.closeRect.width, localCTabItem2.closeRect.height, false);
        if (i1 == 0)
          return;
        localObject3 = new CTabFolderEvent(this);
        ((CTabFolderEvent)localObject3).widget = this;
        ((CTabFolderEvent)localObject3).time = paramEvent.time;
        ((CTabFolderEvent)localObject3).item = localCTabItem2;
        ((CTabFolderEvent)localObject3).doit = true;
        Object localObject4;
        for (i3 = 0; i3 < this.folderListeners.length; i3++)
        {
          localObject4 = this.folderListeners[i3];
          ((CTabFolder2Listener)localObject4).close((CTabFolderEvent)localObject3);
        }
        for (i3 = 0; i3 < this.tabListeners.length; i3++)
        {
          localObject4 = this.tabListeners[i3];
          ((CTabFolderListener)localObject4).itemClosed((CTabFolderEvent)localObject3);
        }
        if (((CTabFolderEvent)localObject3).doit)
          localCTabItem2.dispose();
        if ((!isDisposed()) && (localCTabItem2.isDisposed()))
        {
          Display localDisplay = getDisplay();
          localObject4 = localDisplay.getCursorLocation();
          localObject4 = localDisplay.map(null, this, ((Point)localObject4).x, ((Point)localObject4).y);
          localCTabItem3 = getItem((Point)localObject4);
          if (localCTabItem3 != null)
            if (localCTabItem3.closeRect.contains((Point)localObject4))
            {
              if ((localCTabItem3.closeImageState != 2) && (localCTabItem3.closeImageState != 32))
              {
                localCTabItem3.closeImageState = 32;
                redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
              }
            }
            else if (localCTabItem3.closeImageState != 0)
            {
              localCTabItem3.closeImageState = 0;
              redraw(localCTabItem3.closeRect.x, localCTabItem3.closeRect.y, localCTabItem3.closeRect.width, localCTabItem3.closeRect.height, false);
            }
        }
        return;
      }
      break;
    }
  }

  void onPageTraversal(Event paramEvent)
  {
    int i = this.items.length;
    if (i == 0)
      return;
    int j = this.selectedIndex;
    if (j == -1)
    {
      j = 0;
    }
    else
    {
      int k = paramEvent.detail == 512 ? 1 : -1;
      if (!this.mru)
      {
        j = (this.selectedIndex + k + i) % i;
      }
      else
      {
        int[] arrayOfInt = new int[this.items.length];
        int m = 0;
        int n = -1;
        for (int i1 = 0; i1 < this.items.length; i1++)
          if (this.items[i1].showing)
          {
            if (i1 == this.selectedIndex)
              n = m;
            arrayOfInt[(m++)] = i1;
          }
        if ((n + k >= 0) && (n + k < m))
        {
          j = arrayOfInt[(n + k)];
        }
        else if (this.showChevron)
        {
          CTabFolderEvent localCTabFolderEvent = new CTabFolderEvent(this);
          localCTabFolderEvent.widget = this;
          localCTabFolderEvent.time = paramEvent.time;
          localCTabFolderEvent.x = this.chevronRect.x;
          localCTabFolderEvent.y = this.chevronRect.y;
          localCTabFolderEvent.width = this.chevronRect.width;
          localCTabFolderEvent.height = this.chevronRect.height;
          localCTabFolderEvent.doit = true;
          for (int i2 = 0; i2 < this.folderListeners.length; i2++)
            this.folderListeners[i2].showList(localCTabFolderEvent);
          if ((localCTabFolderEvent.doit) && (!isDisposed()))
            showList(this.chevronRect);
        }
      }
    }
    setSelection(j, true);
  }

  void onPaint(Event paramEvent)
  {
    if (this.inDispose)
      return;
    Font localFont1 = getFont();
    if ((this.oldFont == null) || (!this.oldFont.equals(localFont1)))
    {
      this.oldFont = localFont1;
      if (!updateTabHeight(false))
      {
        updateItems();
        redraw();
        return;
      }
    }
    GC localGC = paramEvent.gc;
    Font localFont2 = localGC.getFont();
    Color localColor1 = localGC.getBackground();
    Color localColor2 = localGC.getForeground();
    Point localPoint = getSize();
    Rectangle localRectangle1 = new Rectangle(0, 0, localPoint.x, localPoint.y);
    this.renderer.draw(-1, 24, localRectangle1, localGC);
    localGC.setFont(localFont2);
    localGC.setForeground(localColor2);
    localGC.setBackground(localColor1);
    this.renderer.draw(-2, 24, localRectangle1, localGC);
    localGC.setFont(localFont2);
    localGC.setForeground(localColor2);
    localGC.setBackground(localColor1);
    if (!this.single)
      for (int i = 0; i < this.items.length; i++)
      {
        Rectangle localRectangle2 = this.items[i].getBounds();
        if ((i != this.selectedIndex) && (paramEvent.getBounds().intersects(localRectangle2)))
          this.renderer.draw(i, 0x18 | this.items[i].state, localRectangle2, localGC);
      }
    localGC.setFont(localFont2);
    localGC.setForeground(localColor2);
    localGC.setBackground(localColor1);
    if (this.selectedIndex != -1)
      this.renderer.draw(this.selectedIndex, this.items[this.selectedIndex].state | 0x8 | 0x10, this.items[this.selectedIndex].getBounds(), localGC);
    localGC.setFont(localFont2);
    localGC.setForeground(localColor2);
    localGC.setBackground(localColor1);
    this.renderer.draw(-5, this.maxImageState, this.maxRect, localGC);
    this.renderer.draw(-6, this.minImageState, this.minRect, localGC);
    this.renderer.draw(-7, this.chevronImageState, this.chevronRect, localGC);
    localGC.setFont(localFont2);
    localGC.setForeground(localColor2);
    localGC.setBackground(localColor1);
  }

  void onResize()
  {
    if (updateItems())
      redrawTabs();
    Point localPoint = getSize();
    if (this.oldSize == null)
    {
      redraw();
    }
    else if ((this.onBottom) && (localPoint.y != this.oldSize.y))
    {
      redraw();
    }
    else
    {
      int i = Math.min(localPoint.x, this.oldSize.x);
      Rectangle localRectangle = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
      if (localPoint.x != this.oldSize.x)
        i -= localRectangle.width + localRectangle.x - this.marginWidth + 2;
      if (!this.simple)
        i -= 5;
      int j = Math.min(localPoint.y, this.oldSize.y);
      if (localPoint.y != this.oldSize.y)
        j -= localRectangle.height + localRectangle.y - this.marginHeight;
      int k = Math.max(localPoint.x, this.oldSize.x);
      int m = Math.max(localPoint.y, this.oldSize.y);
      redraw(0, j, k, m - j, false);
      redraw(i, 0, k - i, m, false);
    }
    this.oldSize = localPoint;
  }

  void onTraverse(Event paramEvent)
  {
    if (this.ignoreTraverse)
      return;
    switch (paramEvent.detail)
    {
    case 2:
    case 4:
    case 8:
    case 16:
      Control localControl = getDisplay().getFocusControl();
      if (localControl == this)
        paramEvent.doit = true;
      break;
    case 128:
      paramEvent.doit = onMnemonic(paramEvent, false);
      break;
    case 256:
    case 512:
      paramEvent.doit = (this.items.length > 0);
    }
    this.ignoreTraverse = true;
    notifyListeners(31, paramEvent);
    this.ignoreTraverse = false;
    paramEvent.type = 0;
    if (isDisposed())
      return;
    if (!paramEvent.doit)
      return;
    switch (paramEvent.detail)
    {
    case 128:
      onMnemonic(paramEvent, true);
      paramEvent.detail = 0;
      break;
    case 256:
    case 512:
      onPageTraversal(paramEvent);
      paramEvent.detail = 0;
    }
  }

  void redrawTabs()
  {
    Point localPoint = getSize();
    Rectangle localRectangle = this.renderer.computeTrim(-1, 0, 0, 0, 0, 0);
    if (this.onBottom)
    {
      int i = localRectangle.height + localRectangle.y - this.marginHeight;
      redraw(0, localPoint.y - i - 1, localPoint.x, i + 1, false);
    }
    else
    {
      redraw(0, 0, localPoint.x, -localRectangle.y - this.marginHeight + 1, false);
    }
  }

  public void removeCTabFolder2Listener(CTabFolder2Listener paramCTabFolder2Listener)
  {
    checkWidget();
    if (paramCTabFolder2Listener == null)
      SWT.error(4);
    if (this.folderListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.folderListeners.length; j++)
      if (paramCTabFolder2Listener == this.folderListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.folderListeners.length == 1)
    {
      this.folderListeners = new CTabFolder2Listener[0];
      return;
    }
    CTabFolder2Listener[] arrayOfCTabFolder2Listener = new CTabFolder2Listener[this.folderListeners.length - 1];
    System.arraycopy(this.folderListeners, 0, arrayOfCTabFolder2Listener, 0, i);
    System.arraycopy(this.folderListeners, i + 1, arrayOfCTabFolder2Listener, i, this.folderListeners.length - i - 1);
    this.folderListeners = arrayOfCTabFolder2Listener;
  }

  /** @deprecated */
  public void removeCTabFolderListener(CTabFolderListener paramCTabFolderListener)
  {
    checkWidget();
    if (paramCTabFolderListener == null)
      SWT.error(4);
    if (this.tabListeners.length == 0)
      return;
    int i = -1;
    for (int j = 0; j < this.tabListeners.length; j++)
      if (paramCTabFolderListener == this.tabListeners[j])
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    if (this.tabListeners.length == 1)
    {
      this.tabListeners = new CTabFolderListener[0];
      return;
    }
    CTabFolderListener[] arrayOfCTabFolderListener = new CTabFolderListener[this.tabListeners.length - 1];
    System.arraycopy(this.tabListeners, 0, arrayOfCTabFolderListener, 0, i);
    System.arraycopy(this.tabListeners, i + 1, arrayOfCTabFolderListener, i, this.tabListeners.length - i - 1);
    this.tabListeners = arrayOfCTabFolderListener;
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    removeListener(13, paramSelectionListener);
    removeListener(14, paramSelectionListener);
  }

  public void reskin(int paramInt)
  {
    super.reskin(paramInt);
    for (int i = 0; i < this.items.length; i++)
      this.items[i].reskin(paramInt);
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    this.renderer.createAntialiasColors();
    redraw();
  }

  public void setBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt)
  {
    setBackground(paramArrayOfColor, paramArrayOfInt, false);
  }

  public void setBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt, boolean paramBoolean)
  {
    checkWidget();
    int i;
    if (paramArrayOfColor != null)
    {
      if ((paramArrayOfInt == null) || (paramArrayOfInt.length != paramArrayOfColor.length - 1))
        SWT.error(5);
      for (i = 0; i < paramArrayOfInt.length; i++)
      {
        if ((paramArrayOfInt[i] < 0) || (paramArrayOfInt[i] > 100))
          SWT.error(5);
        if ((i > 0) && (paramArrayOfInt[i] < paramArrayOfInt[(i - 1)]))
          SWT.error(5);
      }
      if (getDisplay().getDepth() < 15)
      {
        paramArrayOfColor = new Color[] { paramArrayOfColor[(paramArrayOfColor.length - 1)] };
        paramArrayOfInt = new int[0];
      }
    }
    int j;
    if ((this.gradientColors != null) && (paramArrayOfColor != null) && (this.gradientColors.length == paramArrayOfColor.length))
    {
      i = 0;
      for (int m = 0; m < this.gradientColors.length; m++)
      {
        if (this.gradientColors[m] == null)
          i = paramArrayOfColor[m] == null ? 1 : 0;
        else
          j = this.gradientColors[m].equals(paramArrayOfColor[m]);
        if (j == 0)
          break;
      }
      if (j != 0)
        for (m = 0; m < this.gradientPercents.length; m++)
        {
          j = this.gradientPercents[m] == paramArrayOfInt[m] ? 1 : 0;
          if (j == 0)
            break;
        }
      if ((j != 0) && (this.gradientVertical == paramBoolean))
        return;
    }
    if (paramArrayOfColor == null)
    {
      this.gradientColors = null;
      this.gradientPercents = null;
      this.gradientVertical = false;
      setBackground(null);
    }
    else
    {
      this.gradientColors = new Color[paramArrayOfColor.length];
      for (j = 0; j < paramArrayOfColor.length; j++)
        this.gradientColors[j] = paramArrayOfColor[j];
      this.gradientPercents = new int[paramArrayOfInt.length];
      for (int k = 0; k < paramArrayOfInt.length; k++)
        this.gradientPercents[k] = paramArrayOfInt[k];
      this.gradientVertical = paramBoolean;
      setBackground(this.gradientColors[(this.gradientColors.length - 1)]);
    }
    redraw();
  }

  public void setBackgroundImage(Image paramImage)
  {
    super.setBackgroundImage(paramImage);
    this.renderer.createAntialiasColors();
    redraw();
  }

  public void setBorderVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.borderVisible == paramBoolean)
      return;
    this.borderVisible = paramBoolean;
    Rectangle localRectangle1 = getClientArea();
    updateItems();
    Rectangle localRectangle2 = getClientArea();
    if (!localRectangle1.equals(localRectangle2))
      notifyListeners(11, new Event());
    redraw();
  }

  void setButtonBounds(GC paramGC)
  {
    Point localPoint1 = getSize();
    Rectangle localRectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
    int n = localRectangle.width + localRectangle.x;
    int i1 = -localRectangle.x;
    int i2 = localRectangle.height + localRectangle.y;
    int i3 = -localRectangle.y;
    int i = this.maxRect.x;
    int j = this.maxRect.y;
    int k = this.maxRect.width;
    int m = this.maxRect.height;
    this.maxRect.x = (this.maxRect.y = this.maxRect.width = this.maxRect.height = 0);
    if (this.showMax)
    {
      Point localPoint2 = this.renderer.computeSize(-5, 0, paramGC, -1, -1);
      this.maxRect.x = (localPoint1.x - n - localPoint2.x - 3);
      if (n > 0)
        this.maxRect.x += 1;
      this.maxRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight + (this.tabHeight - localPoint2.y) / 2 : i3 + (this.tabHeight - localPoint2.y) / 2);
      this.maxRect.width = localPoint2.x;
      this.maxRect.height = localPoint2.y;
    }
    int i7;
    int i10;
    if ((i != this.maxRect.x) || (k != this.maxRect.width) || (j != this.maxRect.y) || (m != this.maxRect.height))
    {
      int i4 = Math.min(i, this.maxRect.x);
      i7 = Math.max(i + k, this.maxRect.x + this.maxRect.width);
      i10 = this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1;
      redraw(i4, i10, i7 - i4, this.tabHeight, false);
    }
    i = this.minRect.x;
    j = this.minRect.y;
    k = this.minRect.width;
    m = this.minRect.height;
    this.minRect.x = (this.minRect.y = this.minRect.width = this.minRect.height = 0);
    if (this.showMin)
    {
      Point localPoint3 = this.renderer.computeSize(-6, 0, paramGC, -1, -1);
      this.minRect.x = (localPoint1.x - n - this.maxRect.width - localPoint3.x - 3);
      if (n > 0)
        this.minRect.x += 1;
      this.minRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight + (this.tabHeight - localPoint3.y) / 2 : i3 + (this.tabHeight - localPoint3.y) / 2);
      this.minRect.width = localPoint3.x;
      this.minRect.height = localPoint3.y;
    }
    int i5;
    if ((i != this.minRect.x) || (k != this.minRect.width) || (j != this.minRect.y) || (m != this.minRect.height))
    {
      i5 = Math.min(i, this.minRect.x);
      i7 = Math.max(i + k, this.minRect.x + this.minRect.width);
      i10 = this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1;
      redraw(i5, i10, i7 - i5, this.tabHeight, false);
    }
    i = this.topRightRect.x;
    j = this.topRightRect.y;
    k = this.topRightRect.width;
    m = this.topRightRect.height;
    this.topRightRect.x = (this.topRightRect.y = this.topRightRect.width = this.topRightRect.height = 0);
    int i8;
    if (this.topRight != null)
    {
      switch (this.topRightAlignment)
      {
      case 4:
        i5 = localPoint1.x - n - 3 - this.maxRect.width - this.minRect.width;
        if ((!this.simple) && (n > 0) && (!this.showMax) && (!this.showMin))
          i5 -= 2;
        if (this.single)
        {
          if ((this.items.length == 0) || (this.selectedIndex == -1))
          {
            this.topRightRect.x = (i1 + 3);
            this.topRightRect.width = (i5 - this.topRightRect.x);
          }
          else
          {
            CTabItem localCTabItem1 = this.items[this.selectedIndex];
            i10 = this.renderer.computeSize(-7, 0, paramGC, -1, -1).x;
            if (localCTabItem1.x + localCTabItem1.width + 7 + i10 >= i5)
              break;
            this.topRightRect.x = (localCTabItem1.x + localCTabItem1.width + 7 + i10);
            this.topRightRect.width = (i5 - this.topRightRect.x);
          }
        }
        else
        {
          if (this.showChevron)
            break;
          if (this.items.length == 0)
          {
            this.topRightRect.x = (i1 + 3);
          }
          else
          {
            i8 = this.items.length - 1;
            CTabItem localCTabItem3 = this.items[i8];
            this.topRightRect.x = (localCTabItem3.x + localCTabItem3.width);
          }
          this.topRightRect.width = Math.max(0, i5 - this.topRightRect.x);
        }
        this.topRightRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1);
        this.topRightRect.height = (this.tabHeight - 1);
        break;
      case 131072:
        Point localPoint4 = this.topRight.computeSize(-1, this.tabHeight, false);
        i8 = localPoint1.x - n - 3 - this.maxRect.width - this.minRect.width;
        if ((!this.simple) && (n > 0) && (!this.showMax) && (!this.showMin))
          i8 -= 2;
        this.topRightRect.x = (i8 - localPoint4.x);
        this.topRightRect.width = localPoint4.x;
        this.topRightRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1);
        this.topRightRect.height = (this.tabHeight - 1);
      }
      this.topRight.setBounds(this.topRightRect);
    }
    int i11;
    if ((i != this.topRightRect.x) || (k != this.topRightRect.width) || (j != this.topRightRect.y) || (m != this.topRightRect.height))
    {
      int i6 = Math.min(i, this.topRightRect.x);
      i8 = Math.max(i + k, this.topRightRect.x + this.topRightRect.width);
      i11 = this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1;
      redraw(i6, i11, i8 - i6, this.tabHeight, false);
    }
    i = this.chevronRect.x;
    j = this.chevronRect.y;
    k = this.chevronRect.width;
    m = this.chevronRect.height;
    this.chevronRect.x = (this.chevronRect.y = this.chevronRect.height = this.chevronRect.width = 0);
    Point localPoint5 = this.renderer.computeSize(-7, 0, paramGC, -1, -1);
    int i9;
    if (this.single)
    {
      if ((this.selectedIndex == -1) || (this.items.length > 1))
      {
        this.chevronRect.width = localPoint5.x;
        this.chevronRect.height = localPoint5.y;
        this.chevronRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight + (this.tabHeight - this.chevronRect.height) / 2 : i3 + (this.tabHeight - this.chevronRect.height) / 2);
        if (this.selectedIndex == -1)
        {
          this.chevronRect.x = (localPoint1.x - n - 3 - this.minRect.width - this.maxRect.width - this.topRightRect.width - this.chevronRect.width);
        }
        else
        {
          CTabItem localCTabItem2 = this.items[this.selectedIndex];
          i11 = localPoint1.x - n - 3 - this.minRect.width - this.maxRect.width - this.chevronRect.width;
          if (this.topRightRect.width > 0)
            i11 -= this.topRightRect.width + 3;
          this.chevronRect.x = Math.min(localCTabItem2.x + localCTabItem2.width + 3, i11);
        }
        if (n > 0)
          this.chevronRect.x += 1;
      }
    }
    else if (this.showChevron)
    {
      this.chevronRect.width = localPoint5.x;
      this.chevronRect.height = localPoint5.y;
      i9 = 0;
      for (i11 = -1; (i9 < this.priority.length) && (this.items[this.priority[i9]].showing); i11 = Math.max(i11, this.priority[(i9++)]));
      if (i11 == -1)
        i11 = this.firstIndex;
      CTabItem localCTabItem4 = this.items[i11];
      int i13 = localCTabItem4.x + localCTabItem4.width + 3;
      if ((!this.simple) && (i11 == this.selectedIndex))
        i13 -= this.renderer.curveIndent;
      this.chevronRect.x = Math.min(i13, getRightItemEdge(paramGC));
      this.chevronRect.y = (this.onBottom ? localPoint1.y - i2 - this.tabHeight + (this.tabHeight - this.chevronRect.height) / 2 : i3 + (this.tabHeight - this.chevronRect.height) / 2);
    }
    if ((i != this.chevronRect.x) || (k != this.chevronRect.width) || (j != this.chevronRect.y) || (m != this.chevronRect.height))
    {
      i9 = Math.min(i, this.chevronRect.x);
      i11 = Math.max(i + k, this.chevronRect.x + this.chevronRect.width);
      int i12 = this.onBottom ? localPoint1.y - i2 - this.tabHeight : i3 + 1;
      redraw(i9, i12, i11 - i9, this.tabHeight, false);
    }
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    if ((paramFont != null) && (paramFont.equals(getFont())))
      return;
    super.setFont(paramFont);
    this.oldFont = getFont();
    if (!updateTabHeight(false))
    {
      updateItems();
      redraw();
    }
  }

  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    redraw();
  }

  public void setInsertMark(CTabItem paramCTabItem, boolean paramBoolean)
  {
    checkWidget();
  }

  public void setInsertMark(int paramInt, boolean paramBoolean)
  {
    checkWidget();
    if ((paramInt < -1) || (paramInt >= getItemCount()))
      SWT.error(5);
  }

  boolean setItemLocation(GC paramGC)
  {
    boolean bool = false;
    if (this.items.length == 0)
      return false;
    Rectangle localRectangle1 = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
    int i = -localRectangle1.x;
    int j = localRectangle1.height + localRectangle1.y;
    int k = -localRectangle1.y;
    Point localPoint1 = getSize();
    int m = this.onBottom ? Math.max(j, localPoint1.y - j - this.tabHeight) : k;
    Point localPoint2 = this.renderer.computeSize(-8, 0, paramGC, -1, -1);
    int n;
    int i1;
    int i3;
    if (this.single)
    {
      n = getDisplay().getBounds().width + 10;
      for (i1 = 0; i1 < this.items.length; i1++)
      {
        CTabItem localCTabItem1 = this.items[i1];
        if (i1 == this.selectedIndex)
        {
          this.firstIndex = this.selectedIndex;
          i3 = localCTabItem1.x;
          int i4 = localCTabItem1.y;
          localCTabItem1.x = i;
          localCTabItem1.y = m;
          localCTabItem1.showing = true;
          if ((this.showClose) || (localCTabItem1.showClose))
          {
            localCTabItem1.closeRect.x = (i - this.renderer.computeTrim(i1, 0, 0, 0, 0, 0).x);
            localCTabItem1.closeRect.y = (this.onBottom ? localPoint1.y - j - this.tabHeight + (this.tabHeight - localPoint2.y) / 2 : k + (this.tabHeight - localPoint2.y) / 2);
          }
          if ((localCTabItem1.x != i3) || (localCTabItem1.y != i4))
            bool = true;
        }
        else
        {
          localCTabItem1.x = n;
          localCTabItem1.showing = false;
        }
      }
    }
    else
    {
      n = getRightItemEdge(paramGC);
      i1 = n - i;
      int i2 = 0;
      for (i3 = 0; i3 < this.priority.length; i3++)
      {
        CTabItem localCTabItem2 = this.items[this.priority[i3]];
        i2 += localCTabItem2.width;
        localCTabItem2.showing = (i3 == 0);
      }
      i3 = -this.renderer.computeTrim(-2, 0, 0, 0, 0, 0).x;
      int i5 = getDisplay().getBounds().width + 10;
      this.firstIndex = (this.items.length - 1);
      for (int i6 = 0; i6 < this.items.length; i6++)
      {
        CTabItem localCTabItem3 = this.items[i6];
        if (!localCTabItem3.showing)
        {
          if (localCTabItem3.x != i5)
            bool = true;
          localCTabItem3.x = i5;
        }
        else
        {
          this.firstIndex = Math.min(this.firstIndex, i6);
          if ((localCTabItem3.x != i3) || (localCTabItem3.y != m))
            bool = true;
          localCTabItem3.x = i3;
          localCTabItem3.y = m;
          int i7 = 0;
          if (i6 == this.selectedIndex)
            i7 |= 2;
          Rectangle localRectangle2 = this.renderer.computeTrim(i6, i7, 0, 0, 0, 0);
          localCTabItem3.closeRect.x = (localCTabItem3.x + localCTabItem3.width - (localRectangle2.width + localRectangle2.x) - localPoint2.x);
          localCTabItem3.closeRect.y = (this.onBottom ? localPoint1.y - j - this.tabHeight + (this.tabHeight - localPoint2.y) / 2 : k + (this.tabHeight - localPoint2.y) / 2);
          i3 += localCTabItem3.width;
          if ((!this.simple) && (i6 == this.selectedIndex))
            i3 -= this.renderer.curveIndent;
        }
      }
    }
    return bool;
  }

  boolean setItemSize(GC paramGC)
  {
    boolean bool = false;
    if (isDisposed())
      return bool;
    Point localPoint1 = getSize();
    if ((localPoint1.x <= 0) || (localPoint1.y <= 0))
      return bool;
    Rectangle localRectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
    int i = localRectangle.width + localRectangle.x;
    int j = -localRectangle.x;
    this.showChevron = false;
    Object localObject1;
    Point localPoint2;
    if (this.single)
    {
      this.showChevron = true;
      if (this.selectedIndex != -1)
      {
        localObject1 = this.items[this.selectedIndex];
        k = this.renderer.computeSize(this.selectedIndex, 2, paramGC, -1, -1).x;
        k = Math.min(k, getRightItemEdge(paramGC) - j);
        if ((((CTabItem)localObject1).height != this.tabHeight) || (((CTabItem)localObject1).width != k))
        {
          bool = true;
          ((CTabItem)localObject1).shortenedText = null;
          ((CTabItem)localObject1).shortenedTextWidth = 0;
          ((CTabItem)localObject1).height = this.tabHeight;
          ((CTabItem)localObject1).width = k;
          ((CTabItem)localObject1).closeRect.width = (((CTabItem)localObject1).closeRect.height = 0);
          if ((this.showClose) || (((CTabItem)localObject1).showClose))
          {
            localPoint2 = this.renderer.computeSize(-8, 2, paramGC, -1, -1);
            ((CTabItem)localObject1).closeRect.width = localPoint2.x;
            ((CTabItem)localObject1).closeRect.height = localPoint2.y;
          }
        }
      }
      return bool;
    }
    if (this.items.length == 0)
      return bool;
    int k = localPoint1.x - j - i - 3;
    if (this.showMin)
      k -= this.renderer.computeSize(-6, 0, paramGC, -1, -1).x;
    if (this.showMax)
      k -= this.renderer.computeSize(-5, 0, paramGC, -1, -1).x;
    if ((this.topRightAlignment == 131072) && (this.topRight != null))
    {
      localPoint2 = this.topRight.computeSize(-1, -1, false);
      k -= localPoint2.x + 3;
    }
    k = Math.max(0, k);
    int m = 0;
    int[] arrayOfInt = new int[this.items.length];
    int i2;
    for (int n = 0; n < this.priority.length; n++)
    {
      int i1 = this.priority[n];
      i2 = 16777216;
      if (i1 == this.selectedIndex)
        i2 |= 2;
      arrayOfInt[i1] = this.renderer.computeSize(i1, i2, paramGC, -1, -1).x;
      m += arrayOfInt[i1];
      if (m > k)
        break;
    }
    Object localObject2;
    if (m > k)
    {
      this.showChevron = (this.items.length > 1);
      if (this.showChevron)
        k -= this.renderer.computeSize(-7, 0, paramGC, -1, -1).x;
      localObject1 = arrayOfInt;
      n = this.selectedIndex != -1 ? this.selectedIndex : 0;
      if (k < localObject1[n])
        localObject1[n] = Math.max(0, k);
    }
    else
    {
      n = 0;
      localObject2 = new int[this.items.length];
      int i3;
      for (i2 = 0; i2 < this.items.length; i2++)
      {
        i3 = 0;
        if (i2 == this.selectedIndex)
          i3 |= 2;
        localObject2[i2] = this.renderer.computeSize(i2, i3, paramGC, -1, -1).x;
        n += localObject2[i2];
      }
      if (n <= k)
      {
        localObject1 = localObject2;
      }
      else
      {
        for (i2 = (k - m) / this.items.length; ; i2++)
        {
          i3 = 0;
          int i4 = 0;
          for (int i5 = 0; i5 < this.items.length; i5++)
            if (localObject2[i5] > arrayOfInt[i5] + i2)
            {
              i4 += arrayOfInt[i5] + i2;
              i3++;
            }
            else
            {
              i4 += localObject2[i5];
            }
          if (i4 >= k)
            i2--;
          else
            if ((i3 == 0) || (k - i4 < i3))
              break;
        }
        localObject1 = new int[this.items.length];
        for (i3 = 0; i3 < this.items.length; i3++)
          localObject1[i3] = Math.min(localObject2[i3], arrayOfInt[i3] + i2);
      }
    }
    for (n = 0; n < this.items.length; n++)
    {
      localObject2 = this.items[n];
      i2 = localObject1[n];
      if ((((CTabItem)localObject2).height != this.tabHeight) || (((CTabItem)localObject2).width != i2))
      {
        bool = true;
        ((CTabItem)localObject2).shortenedText = null;
        ((CTabItem)localObject2).shortenedTextWidth = 0;
        ((CTabItem)localObject2).height = this.tabHeight;
        ((CTabItem)localObject2).width = i2;
        ((CTabItem)localObject2).closeRect.width = (((CTabItem)localObject2).closeRect.height = 0);
        if (((this.showClose) || (((CTabItem)localObject2).showClose)) && ((n == this.selectedIndex) || (this.showUnselectedClose)))
        {
          Point localPoint3 = this.renderer.computeSize(-8, 0, paramGC, -1, -1);
          ((CTabItem)localObject2).closeRect.width = localPoint3.x;
          ((CTabItem)localObject2).closeRect.height = localPoint3.y;
        }
      }
    }
    return bool;
  }

  public void setMaximizeVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.showMax == paramBoolean)
      return;
    this.showMax = paramBoolean;
    updateItems();
    redraw();
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  public void setMaximized(boolean paramBoolean)
  {
    checkWidget();
    if (this.maximized == paramBoolean)
      return;
    if ((paramBoolean) && (this.minimized))
      setMinimized(false);
    this.maximized = paramBoolean;
    redraw(this.maxRect.x, this.maxRect.y, this.maxRect.width, this.maxRect.height, false);
  }

  public void setMinimizeVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.showMin == paramBoolean)
      return;
    this.showMin = paramBoolean;
    updateItems();
    redraw();
  }

  public void setMinimized(boolean paramBoolean)
  {
    checkWidget();
    if (this.minimized == paramBoolean)
      return;
    if ((paramBoolean) && (this.maximized))
      setMaximized(false);
    this.minimized = paramBoolean;
    redraw(this.minRect.x, this.minRect.y, this.minRect.width, this.minRect.height, false);
  }

  public void setMinimumCharacters(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      SWT.error(6);
    if (this.minChars == paramInt)
      return;
    this.minChars = paramInt;
    if (updateItems())
      redrawTabs();
  }

  public void setMRUVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.mru == paramBoolean)
      return;
    this.mru = paramBoolean;
    if (!this.mru)
    {
      int i = this.firstIndex;
      int j = 0;
      for (int k = this.firstIndex; k < this.items.length; k++)
        this.priority[(j++)] = k;
      for (k = 0; k < i; k++)
        this.priority[(j++)] = k;
      if (updateItems())
        redrawTabs();
    }
  }

  public void setRenderer(CTabFolderRenderer paramCTabFolderRenderer)
  {
    checkWidget();
    if (this.renderer == paramCTabFolderRenderer)
      return;
    if (this.renderer != null)
      this.renderer.dispose();
    if (paramCTabFolderRenderer == null)
      paramCTabFolderRenderer = new CTabFolderRenderer(this);
    this.renderer = paramCTabFolderRenderer;
    updateTabHeight(false);
    Rectangle localRectangle1 = getClientArea();
    updateItems();
    Rectangle localRectangle2 = getClientArea();
    if (!localRectangle1.equals(localRectangle2))
      notifyListeners(11, new Event());
    redraw();
  }

  public void setSelection(CTabItem paramCTabItem)
  {
    checkWidget();
    if (paramCTabItem == null)
      SWT.error(4);
    int i = indexOf(paramCTabItem);
    setSelection(i);
  }

  public void setSelection(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt >= this.items.length))
      return;
    CTabItem localCTabItem = this.items[paramInt];
    if (this.selectedIndex == paramInt)
    {
      showItem(localCTabItem);
      return;
    }
    int i = this.selectedIndex;
    this.selectedIndex = paramInt;
    if (i != -1)
    {
      this.items[i].closeImageState = 8;
      this.items[i].state &= -3;
    }
    localCTabItem.closeImageState = 0;
    localCTabItem.showing = false;
    localCTabItem.state |= 2;
    Control localControl1 = localCTabItem.control;
    Control localControl2 = null;
    if (i != -1)
      localControl2 = this.items[i].control;
    if (localControl1 != localControl2)
    {
      if ((localControl1 != null) && (!localControl1.isDisposed()))
      {
        localControl1.setBounds(getClientArea());
        localControl1.setVisible(true);
      }
      if ((localControl2 != null) && (!localControl2.isDisposed()))
        localControl2.setVisible(false);
    }
    showItem(localCTabItem);
    redraw();
  }

  void setSelection(int paramInt, boolean paramBoolean)
  {
    int i = this.selectedIndex;
    setSelection(paramInt);
    if ((paramBoolean) && (this.selectedIndex != i) && (this.selectedIndex != -1))
    {
      Event localEvent = new Event();
      localEvent.item = getItem(this.selectedIndex);
      notifyListeners(13, localEvent);
    }
  }

  public void setSelectionBackground(Color paramColor)
  {
    checkWidget();
    setSelectionHighlightGradientColor(null);
    if (this.selectionBackground == paramColor)
      return;
    if (paramColor == null)
      paramColor = getDisplay().getSystemColor(25);
    this.selectionBackground = paramColor;
    this.renderer.createAntialiasColors();
    if (this.selectedIndex > -1)
      redraw();
  }

  public void setSelectionBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt)
  {
    setSelectionBackground(paramArrayOfColor, paramArrayOfInt, false);
  }

  public void setSelectionBackground(Color[] paramArrayOfColor, int[] paramArrayOfInt, boolean paramBoolean)
  {
    checkWidget();
    Color localColor = null;
    int j;
    int i;
    if (paramArrayOfColor != null)
    {
      if ((paramArrayOfInt == null) || ((paramArrayOfInt.length != paramArrayOfColor.length - 1) && (paramArrayOfInt.length != paramArrayOfColor.length - 2)))
        SWT.error(5);
      for (j = 0; j < paramArrayOfInt.length; j++)
      {
        if ((paramArrayOfInt[j] < 0) || (paramArrayOfInt[j] > 100))
          SWT.error(5);
        if ((j > 0) && (paramArrayOfInt[j] < paramArrayOfInt[(j - 1)]))
          SWT.error(5);
      }
      if (paramArrayOfInt.length == paramArrayOfColor.length - 2)
      {
        localColor = paramArrayOfColor[(paramArrayOfColor.length - 1)];
        i = paramArrayOfColor.length - 1;
      }
      else
      {
        i = paramArrayOfColor.length;
      }
      if (getDisplay().getDepth() < 15)
      {
        paramArrayOfColor = new Color[] { paramArrayOfColor[(i - 1)] };
        i = paramArrayOfColor.length;
        paramArrayOfInt = new int[0];
      }
    }
    else
    {
      i = 0;
    }
    int k;
    if (this.selectionBgImage == null)
    {
      if ((this.selectionGradientColors != null) && (paramArrayOfColor != null) && (this.selectionGradientColors.length == i))
      {
        j = 0;
        for (int m = 0; m < this.selectionGradientColors.length; m++)
        {
          if (this.selectionGradientColors[m] == null)
            j = paramArrayOfColor[m] == null ? 1 : 0;
          else
            k = this.selectionGradientColors[m].equals(paramArrayOfColor[m]);
          if (k == 0)
            break;
        }
        if (k != 0)
          for (m = 0; m < this.selectionGradientPercents.length; m++)
          {
            k = this.selectionGradientPercents[m] == paramArrayOfInt[m] ? 1 : 0;
            if (k == 0)
              break;
          }
        if ((k == 0) || (this.selectionGradientVertical != paramBoolean));
      }
    }
    else
      this.selectionBgImage = null;
    if (paramArrayOfColor == null)
    {
      this.selectionGradientColors = null;
      this.selectionGradientPercents = null;
      this.selectionGradientVertical = false;
      setSelectionBackground(null);
      setSelectionHighlightGradientColor(null);
    }
    else
    {
      this.selectionGradientColors = new Color[i];
      for (k = 0; k < i; k++)
        this.selectionGradientColors[k] = paramArrayOfColor[k];
      this.selectionGradientPercents = new int[paramArrayOfInt.length];
      for (k = 0; k < paramArrayOfInt.length; k++)
        this.selectionGradientPercents[k] = paramArrayOfInt[k];
      this.selectionGradientVertical = paramBoolean;
      setSelectionBackground(this.selectionGradientColors[(this.selectionGradientColors.length - 1)]);
      setSelectionHighlightGradientColor(localColor);
    }
    if (this.selectedIndex > -1)
      redraw();
  }

  void setSelectionHighlightGradientColor(Color paramColor)
  {
    this.renderer.setSelectionHighlightGradientColor(paramColor);
  }

  public void setSelectionBackground(Image paramImage)
  {
    checkWidget();
    setSelectionHighlightGradientColor(null);
    if (paramImage == this.selectionBgImage)
      return;
    if (paramImage != null)
    {
      this.selectionGradientColors = null;
      this.selectionGradientPercents = null;
      this.renderer.disposeSelectionHighlightGradientColors();
    }
    this.selectionBgImage = paramImage;
    this.renderer.createAntialiasColors();
    if (this.selectedIndex > -1)
      redraw();
  }

  public void setSelectionForeground(Color paramColor)
  {
    checkWidget();
    if (this.selectionForeground == paramColor)
      return;
    if (paramColor == null)
      paramColor = getDisplay().getSystemColor(24);
    this.selectionForeground = paramColor;
    if (this.selectedIndex > -1)
      redraw();
  }

  public void setSimple(boolean paramBoolean)
  {
    checkWidget();
    if (this.simple != paramBoolean)
    {
      this.simple = paramBoolean;
      Rectangle localRectangle1 = getClientArea();
      updateItems();
      Rectangle localRectangle2 = getClientArea();
      if (!localRectangle1.equals(localRectangle2))
        notifyListeners(11, new Event());
      redraw();
    }
  }

  public void setSingle(boolean paramBoolean)
  {
    checkWidget();
    if (this.single != paramBoolean)
    {
      this.single = paramBoolean;
      if (!paramBoolean)
        for (int i = 0; i < this.items.length; i++)
          if ((i != this.selectedIndex) && (this.items[i].closeImageState == 0))
            this.items[i].closeImageState = 8;
      Rectangle localRectangle1 = getClientArea();
      updateItems();
      Rectangle localRectangle2 = getClientArea();
      if (!localRectangle1.equals(localRectangle2))
        notifyListeners(11, new Event());
      redraw();
    }
  }

  public void setTabHeight(int paramInt)
  {
    checkWidget();
    if (paramInt < -1)
      SWT.error(5);
    this.fixedTabHeight = paramInt;
    updateTabHeight(false);
  }

  public void setTabPosition(int paramInt)
  {
    checkWidget();
    if ((paramInt != 128) && (paramInt != 1024))
      SWT.error(5);
    if (this.onBottom != (paramInt == 1024))
    {
      this.onBottom = (paramInt == 1024);
      updateTabHeight(true);
      Rectangle localRectangle1 = getClientArea();
      updateItems();
      Rectangle localRectangle2 = getClientArea();
      if (!localRectangle1.equals(localRectangle2))
        notifyListeners(11, new Event());
      redraw();
    }
  }

  public void setTopRight(Control paramControl)
  {
    setTopRight(paramControl, 131072);
  }

  public void setTopRight(Control paramControl, int paramInt)
  {
    checkWidget();
    if ((paramInt != 131072) && (paramInt != 4))
      SWT.error(5);
    if ((paramControl != null) && (paramControl.getParent() != this))
      SWT.error(5);
    this.topRight = paramControl;
    this.topRightAlignment = paramInt;
    if (updateItems())
      redraw();
  }

  public void setUnselectedCloseVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.showUnselectedClose == paramBoolean)
      return;
    this.showUnselectedClose = paramBoolean;
    updateItems();
    redraw();
  }

  public void setUnselectedImageVisible(boolean paramBoolean)
  {
    checkWidget();
    if (this.showUnselectedImage == paramBoolean)
      return;
    this.showUnselectedImage = paramBoolean;
    updateItems();
    redraw();
  }

  public void showItem(CTabItem paramCTabItem)
  {
    checkWidget();
    if (paramCTabItem == null)
      SWT.error(4);
    if (paramCTabItem.isDisposed())
      SWT.error(5);
    int i = indexOf(paramCTabItem);
    if (i == -1)
      SWT.error(5);
    int j = -1;
    for (int k = 0; k < this.priority.length; k++)
      if (this.priority[k] == i)
      {
        j = k;
        break;
      }
    if (this.mru)
    {
      int[] arrayOfInt = new int[this.priority.length];
      System.arraycopy(this.priority, 0, arrayOfInt, 1, j);
      System.arraycopy(this.priority, j + 1, arrayOfInt, j + 1, this.priority.length - j - 1);
      arrayOfInt[0] = i;
      this.priority = arrayOfInt;
    }
    if (paramCTabItem.isShowing())
      return;
    updateItems(i);
    redrawTabs();
  }

  void showList(Rectangle paramRectangle)
  {
    if ((this.items.length == 0) || (!this.showChevron))
      return;
    if ((this.showMenu == null) || (this.showMenu.isDisposed()))
    {
      this.showMenu = new Menu(getShell(), getStyle() & 0x6000000);
    }
    else
    {
      MenuItem[] arrayOfMenuItem = this.showMenu.getItems();
      for (int j = 0; j < arrayOfMenuItem.length; j++)
        arrayOfMenuItem[j].dispose();
    }
    for (int i = 0; i < this.items.length; i++)
    {
      CTabItem localCTabItem = this.items[i];
      if (!localCTabItem.showing)
      {
        localObject = new MenuItem(this.showMenu, 0);
        ((MenuItem)localObject).setText(localCTabItem.getText());
        ((MenuItem)localObject).setImage(localCTabItem.getImage());
        ((MenuItem)localObject).setData("CTabFolder_showList_Index", localCTabItem);
        ((MenuItem)localObject).addSelectionListener(new SelectionAdapter()
        {
          public void widgetSelected(SelectionEvent paramAnonymousSelectionEvent)
          {
            MenuItem localMenuItem = (MenuItem)paramAnonymousSelectionEvent.widget;
            int i = CTabFolder.this.indexOf((CTabItem)localMenuItem.getData("CTabFolder_showList_Index"));
            CTabFolder.this.setSelection(i, true);
          }
        });
      }
    }
    i = paramRectangle.x;
    int k = paramRectangle.y + paramRectangle.height;
    Object localObject = getDisplay().map(this, null, i, k);
    this.showMenu.setLocation(((Point)localObject).x, ((Point)localObject).y);
    this.showMenu.setVisible(true);
  }

  public void showSelection()
  {
    checkWidget();
    if (this.selectedIndex != -1)
      showItem(getSelection());
  }

  void _setToolTipText(int paramInt1, int paramInt2)
  {
    String str1 = getToolTipText();
    String str2 = _getToolTip(paramInt1, paramInt2);
    if ((str2 == null) || (!str2.equals(str1)))
      setToolTipText(str2);
  }

  boolean updateItems()
  {
    return updateItems(this.selectedIndex);
  }

  boolean updateItems(int paramInt)
  {
    GC localGC = new GC(this);
    if ((!this.single) && (!this.mru) && (paramInt != -1))
    {
      int i = paramInt;
      int j;
      if (this.priority[0] < paramInt)
      {
        Rectangle localRectangle = this.renderer.computeTrim(-3, 0, 0, 0, 0, 0);
        j = -localRectangle.x;
        int k = getRightItemEdge(localGC) - j;
        int m = 0;
        int[] arrayOfInt = new int[this.items.length];
        int i1;
        for (int n = this.priority[0]; n <= paramInt; n++)
        {
          i1 = 16777216;
          if (n == this.selectedIndex)
            i1 |= 2;
          arrayOfInt[n] = this.renderer.computeSize(n, i1, localGC, -1, -1).x;
          m += arrayOfInt[n];
          if (m > k)
            break;
        }
        if (m > k)
        {
          m = 0;
          for (n = paramInt; n >= 0; n--)
          {
            i1 = 16777216;
            if (n == this.selectedIndex)
              i1 |= 2;
            if (arrayOfInt[n] == 0)
              arrayOfInt[n] = this.renderer.computeSize(n, i1, localGC, -1, -1).x;
            m += arrayOfInt[n];
            if (m > k)
              break;
            i = n;
          }
        }
        else
        {
          i = this.priority[0];
          for (n = paramInt + 1; n < this.items.length; n++)
          {
            i1 = 16777216;
            if (n == this.selectedIndex)
              i1 |= 2;
            arrayOfInt[n] = this.renderer.computeSize(n, i1, localGC, -1, -1).x;
            m += arrayOfInt[n];
            if (m >= k)
              break;
          }
          if (m < k)
            for (n = this.priority[0] - 1; n >= 0; n--)
            {
              i1 = 16777216;
              if (n == this.selectedIndex)
                i1 |= 2;
              if (arrayOfInt[n] == 0)
                arrayOfInt[n] = this.renderer.computeSize(n, i1, localGC, -1, -1).x;
              m += arrayOfInt[n];
              if (m > k)
                break;
              i = n;
            }
        }
      }
      if (i != this.priority[0])
      {
        bool2 = false;
        for (j = i; j < this.items.length; j++)
          this.priority[(bool2++)] = j;
        for (j = 0; j < i; j++)
          this.priority[(bool2++)] = j;
      }
    }
    boolean bool1 = this.showChevron;
    boolean bool2 = setItemSize(localGC);
    bool2 |= setItemLocation(localGC);
    setButtonBounds(localGC);
    bool2 |= this.showChevron ^ bool1;
    if ((bool2) && (getToolTipText() != null))
    {
      Point localPoint = getDisplay().getCursorLocation();
      localPoint = toControl(localPoint);
      _setToolTipText(localPoint.x, localPoint.y);
    }
    localGC.dispose();
    return bool2;
  }

  boolean updateTabHeight(boolean paramBoolean)
  {
    int i = this.tabHeight;
    GC localGC = new GC(this);
    this.tabHeight = this.renderer.computeSize(-2, 0, localGC, -1, -1).y;
    localGC.dispose();
    if ((!paramBoolean) && (this.tabHeight == i))
      return false;
    this.oldSize = null;
    notifyListeners(11, new Event());
    return true;
  }

  String _getToolTip(int paramInt1, int paramInt2)
  {
    if ((this.showMin) && (this.minRect.contains(paramInt1, paramInt2)))
      return this.minimized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Minimize");
    if ((this.showMax) && (this.maxRect.contains(paramInt1, paramInt2)))
      return this.maximized ? SWT.getMessage("SWT_Restore") : SWT.getMessage("SWT_Maximize");
    if ((this.showChevron) && (this.chevronRect.contains(paramInt1, paramInt2)))
      return SWT.getMessage("SWT_ShowList");
    CTabItem localCTabItem = getItem(new Point(paramInt1, paramInt2));
    if (localCTabItem == null)
      return null;
    if (!localCTabItem.showing)
      return null;
    if (((this.showClose) || (localCTabItem.showClose)) && (localCTabItem.closeRect.contains(paramInt1, paramInt2)))
      return SWT.getMessage("SWT_Close");
    return localCTabItem.getToolTipText();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CTabFolder
 * JD-Core Version:    0.6.2
 */