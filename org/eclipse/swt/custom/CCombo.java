package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleTextAdapter;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Scrollable;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TypedListener;

public class CCombo extends Composite
{
  Text text;
  List list;
  int visibleItemCount = 5;
  Shell popup;
  Button arrow;
  boolean hasFocus;
  Listener listener;
  Listener filter;
  Color foreground;
  Color background;
  Font font;
  Shell _shell = super.getShell();
  static final String PACKAGE_PREFIX = "org.eclipse.swt.custom.";

  public CCombo(Composite paramComposite, int paramInt)
  {
    super(paramComposite, paramInt = checkStyle(paramInt));
    int i = 4;
    if ((paramInt & 0x8) != 0)
      i |= 8;
    if ((paramInt & 0x800000) != 0)
      i |= 8388608;
    this.text = new Text(this, i);
    int j = 1028;
    if ((paramInt & 0x800000) != 0)
      j |= 8388608;
    this.arrow = new Button(this, j);
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (CCombo.this.isDisposed())
          return;
        if (CCombo.this.popup == paramAnonymousEvent.widget)
        {
          CCombo.this.popupEvent(paramAnonymousEvent);
          return;
        }
        if (CCombo.this.text == paramAnonymousEvent.widget)
        {
          CCombo.this.textEvent(paramAnonymousEvent);
          return;
        }
        if (CCombo.this.list == paramAnonymousEvent.widget)
        {
          CCombo.this.listEvent(paramAnonymousEvent);
          return;
        }
        if (CCombo.this.arrow == paramAnonymousEvent.widget)
        {
          CCombo.this.arrowEvent(paramAnonymousEvent);
          return;
        }
        if (CCombo.this == paramAnonymousEvent.widget)
        {
          CCombo.this.comboEvent(paramAnonymousEvent);
          return;
        }
        if (CCombo.this.getShell() == paramAnonymousEvent.widget)
          CCombo.this.getDisplay().asyncExec(new CCombo.2(this));
      }
    };
    this.filter = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        if (CCombo.this.isDisposed())
          return;
        if (paramAnonymousEvent.type == 13)
        {
          if ((paramAnonymousEvent.widget instanceof ScrollBar))
            CCombo.this.handleScroll(paramAnonymousEvent);
          return;
        }
        Shell localShell = ((Control)paramAnonymousEvent.widget).getShell();
        if (localShell == CCombo.this.getShell())
          CCombo.this.handleFocus(16);
      }
    };
    int[] arrayOfInt1 = { 12, 15, 10, 11 };
    for (int k = 0; k < arrayOfInt1.length; k++)
      addListener(arrayOfInt1[k], this.listener);
    int[] arrayOfInt2 = { 14, 29, 1, 2, 35, 24, 3, 4, 8, 6, 7, 32, 5, 37, 31, 15, 25 };
    for (int m = 0; m < arrayOfInt2.length; m++)
      this.text.addListener(arrayOfInt2[m], this.listener);
    int[] arrayOfInt3 = { 29, 3, 6, 7, 32, 5, 4, 37, 13, 15 };
    for (int n = 0; n < arrayOfInt3.length; n++)
      this.arrow.addListener(arrayOfInt3[n], this.listener);
    createPopup(null, -1);
    initAccessible();
  }

  static int checkStyle(int paramInt)
  {
    int i = 109053960;
    return 0x80000 | paramInt & i;
  }

  public void add(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    this.list.add(paramString);
  }

  public void add(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    this.list.add(paramString, paramInt);
  }

  public void addModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      SWT.error(4);
    TypedListener localTypedListener = new TypedListener(paramModifyListener);
    addListener(24, localTypedListener);
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

  public void addVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      SWT.error(4);
    TypedListener localTypedListener = new TypedListener(paramVerifyListener);
    addListener(25, localTypedListener);
  }

  void arrowEvent(Event paramEvent)
  {
    Point localPoint;
    switch (paramEvent.type)
    {
    case 15:
      handleFocus(15);
      break;
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    case 29:
    case 32:
      localPoint = getDisplay().map(this.arrow, this, paramEvent.x, paramEvent.y);
      paramEvent.x = localPoint.x;
      paramEvent.y = localPoint.y;
      notifyListeners(paramEvent.type, paramEvent);
      paramEvent.type = 0;
      break;
    case 37:
      localPoint = getDisplay().map(this.arrow, this, paramEvent.x, paramEvent.y);
      paramEvent.x = localPoint.x;
      paramEvent.y = localPoint.y;
      notifyListeners(37, paramEvent);
      paramEvent.type = 0;
      if ((!isDisposed()) && (paramEvent.doit) && (paramEvent.count != 0))
      {
        paramEvent.doit = false;
        int i = getSelectionIndex();
        if (paramEvent.count > 0)
          select(Math.max(i - 1, 0));
        else
          select(Math.min(i + 1, getItemCount() - 1));
        if (i != getSelectionIndex())
        {
          Event localEvent = new Event();
          localEvent.time = paramEvent.time;
          localEvent.stateMask = paramEvent.stateMask;
          notifyListeners(13, localEvent);
        }
        if (!isDisposed())
          break;
      }
      break;
    case 13:
      this.text.setFocus();
      dropDown(!isDropped());
    }
  }

  protected void checkSubclass()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (!str.substring(0, i + 1).equals("org.eclipse.swt.custom."))
      SWT.error(43);
  }

  public void clearSelection()
  {
    checkWidget();
    this.text.clearSelection();
    this.list.deselectAll();
  }

  void comboEvent(Event paramEvent)
  {
    switch (paramEvent.type)
    {
    case 12:
      removeListener(12, this.listener);
      notifyListeners(12, paramEvent);
      paramEvent.type = 0;
      if ((this.popup != null) && (!this.popup.isDisposed()))
      {
        this.list.removeListener(12, this.listener);
        this.popup.dispose();
      }
      Shell localShell = getShell();
      localShell.removeListener(27, this.listener);
      Display localDisplay = getDisplay();
      localDisplay.removeFilter(15, this.filter);
      this.popup = null;
      this.text = null;
      this.list = null;
      this.arrow = null;
      this._shell = null;
      break;
    case 15:
      Control localControl = getDisplay().getFocusControl();
      if ((localControl == this.arrow) || (localControl == this.list))
        return;
      if (isDropped())
        this.list.setFocus();
      else
        this.text.setFocus();
      break;
    case 10:
      dropDown(false);
      break;
    case 11:
      internalLayout(false);
    case 13:
    case 14:
    }
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = 0;
    int j = 0;
    String[] arrayOfString = this.list.getItems();
    GC localGC = new GC(this.text);
    int k = localGC.stringExtent(" ").x;
    int m = localGC.stringExtent(this.text.getText()).x;
    for (int n = 0; n < arrayOfString.length; n++)
      m = Math.max(localGC.stringExtent(arrayOfString[n]).x, m);
    localGC.dispose();
    Point localPoint1 = this.text.computeSize(-1, -1, paramBoolean);
    Point localPoint2 = this.arrow.computeSize(-1, -1, paramBoolean);
    Point localPoint3 = this.list.computeSize(-1, -1, paramBoolean);
    int i1 = getBorderWidth();
    j = Math.max(localPoint1.y, localPoint2.y);
    i = Math.max(m + 2 * k + localPoint2.x + 2 * i1, localPoint3.x);
    if (paramInt1 != -1)
      i = paramInt1;
    if (paramInt2 != -1)
      j = paramInt2;
    return new Point(i + 2 * i1, j + 2 * i1);
  }

  public void copy()
  {
    checkWidget();
    this.text.copy();
  }

  void createPopup(String[] paramArrayOfString, int paramInt)
  {
    this.popup = new Shell(getShell(), 16392);
    int i = getStyle();
    int j = 516;
    if ((i & 0x800000) != 0)
      j |= 8388608;
    if ((i & 0x4000000) != 0)
      j |= 67108864;
    if ((i & 0x2000000) != 0)
      j |= 33554432;
    this.list = new List(this.popup, j);
    if (this.font != null)
      this.list.setFont(this.font);
    if (this.foreground != null)
      this.list.setForeground(this.foreground);
    if (this.background != null)
      this.list.setBackground(this.background);
    int[] arrayOfInt1 = { 21, 9, 27 };
    for (int k = 0; k < arrayOfInt1.length; k++)
      this.popup.addListener(arrayOfInt1[k], this.listener);
    int[] arrayOfInt2 = { 4, 13, 31, 1, 2, 15, 12 };
    for (int m = 0; m < arrayOfInt2.length; m++)
      this.list.addListener(arrayOfInt2[m], this.listener);
    if (paramArrayOfString != null)
      this.list.setItems(paramArrayOfString);
    if (paramInt != -1)
      this.list.setSelection(paramInt);
  }

  public void cut()
  {
    checkWidget();
    this.text.cut();
  }

  public void deselect(int paramInt)
  {
    checkWidget();
    if ((paramInt >= 0) && (paramInt < this.list.getItemCount()) && (paramInt == this.list.getSelectionIndex()) && (this.text.getText().equals(this.list.getItem(paramInt))))
    {
      this.text.setText("");
      this.list.deselect(paramInt);
    }
  }

  public void deselectAll()
  {
    checkWidget();
    this.text.setText("");
    this.list.deselectAll();
  }

  void dropDown(boolean paramBoolean)
  {
    if (paramBoolean == isDropped())
      return;
    Display localDisplay = getDisplay();
    if (!paramBoolean)
    {
      localDisplay.removeFilter(13, this.filter);
      this.popup.setVisible(false);
      if ((!isDisposed()) && (isFocusControl()))
        this.text.setFocus();
      return;
    }
    if (!isVisible())
      return;
    if (getShell() != this.popup.getParent())
    {
      localObject = this.list.getItems();
      i = this.list.getSelectionIndex();
      this.list.removeListener(12, this.listener);
      this.popup.dispose();
      this.popup = null;
      this.list = null;
      createPopup((String[])localObject, i);
    }
    Object localObject = getSize();
    int i = this.list.getItemCount();
    i = i == 0 ? this.visibleItemCount : Math.min(this.visibleItemCount, i);
    int j = this.list.getItemHeight() * i;
    Point localPoint1 = this.list.computeSize(-1, j, false);
    this.list.setBounds(1, 1, Math.max(((Point)localObject).x - 2, localPoint1.x), localPoint1.y);
    int k = this.list.getSelectionIndex();
    if (k != -1)
      this.list.setTopIndex(k);
    Rectangle localRectangle1 = this.list.getBounds();
    Rectangle localRectangle2 = localDisplay.map(getParent(), null, getBounds());
    Point localPoint2 = getSize();
    Rectangle localRectangle3 = getMonitor().getClientArea();
    int m = Math.max(localPoint2.x, localRectangle1.width + 2);
    int n = localRectangle1.height + 2;
    int i1 = localRectangle2.x;
    int i2 = localRectangle2.y + localPoint2.y;
    if (i2 + n > localRectangle3.y + localRectangle3.height)
      i2 = localRectangle2.y - n;
    if (i1 + m > localRectangle3.x + localRectangle3.width)
      i1 = localRectangle3.x + localRectangle3.width - localRectangle1.width;
    this.popup.setBounds(i1, i2, m, n);
    this.popup.setVisible(true);
    if (isFocusControl())
      this.list.setFocus();
    localDisplay.removeFilter(13, this.filter);
    localDisplay.addFilter(13, this.filter);
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

  String getAssociatedLabel()
  {
    Control[] arrayOfControl = getParent().getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      if (arrayOfControl[i] == this)
      {
        if (i <= 0)
          break;
        Control localControl = arrayOfControl[(i - 1)];
        if ((localControl instanceof Label))
          return ((Label)localControl).getText();
        if (!(localControl instanceof CLabel))
          break;
        return ((CLabel)localControl).getText();
      }
    return null;
  }

  public Control[] getChildren()
  {
    checkWidget();
    return new Control[0];
  }

  public boolean getEditable()
  {
    checkWidget();
    return this.text.getEditable();
  }

  public String getItem(int paramInt)
  {
    checkWidget();
    return this.list.getItem(paramInt);
  }

  public int getItemCount()
  {
    checkWidget();
    return this.list.getItemCount();
  }

  public int getItemHeight()
  {
    checkWidget();
    return this.list.getItemHeight();
  }

  public String[] getItems()
  {
    checkWidget();
    return this.list.getItems();
  }

  public boolean getListVisible()
  {
    checkWidget();
    return isDropped();
  }

  public Menu getMenu()
  {
    return this.text.getMenu();
  }

  public Point getSelection()
  {
    checkWidget();
    return this.text.getSelection();
  }

  public int getSelectionIndex()
  {
    checkWidget();
    return this.list.getSelectionIndex();
  }

  public Shell getShell()
  {
    checkWidget();
    Shell localShell = super.getShell();
    if (localShell != this._shell)
    {
      if ((this._shell != null) && (!this._shell.isDisposed()))
        this._shell.removeListener(27, this.listener);
      this._shell = localShell;
    }
    return this._shell;
  }

  public int getStyle()
  {
    int i = super.getStyle();
    i &= -9;
    if (!this.text.getEditable())
      i |= 8;
    return i;
  }

  public String getText()
  {
    checkWidget();
    return this.text.getText();
  }

  public int getTextHeight()
  {
    checkWidget();
    return this.text.getLineHeight();
  }

  public int getTextLimit()
  {
    checkWidget();
    return this.text.getTextLimit();
  }

  public int getVisibleItemCount()
  {
    checkWidget();
    return this.visibleItemCount;
  }

  void handleFocus(int paramInt)
  {
    Object localObject1;
    Object localObject2;
    Object localObject3;
    switch (paramInt)
    {
    case 15:
      if (this.hasFocus)
        return;
      if (getEditable())
        this.text.selectAll();
      this.hasFocus = true;
      localObject1 = getShell();
      ((Shell)localObject1).removeListener(27, this.listener);
      ((Shell)localObject1).addListener(27, this.listener);
      localObject2 = getDisplay();
      ((Display)localObject2).removeFilter(15, this.filter);
      ((Display)localObject2).addFilter(15, this.filter);
      localObject3 = new Event();
      notifyListeners(15, (Event)localObject3);
      break;
    case 16:
      if (!this.hasFocus)
        return;
      localObject1 = getDisplay().getFocusControl();
      if ((localObject1 == this.arrow) || (localObject1 == this.list) || (localObject1 == this.text))
        return;
      this.hasFocus = false;
      localObject2 = getShell();
      ((Shell)localObject2).removeListener(27, this.listener);
      localObject3 = getDisplay();
      ((Display)localObject3).removeFilter(15, this.filter);
      Event localEvent = new Event();
      notifyListeners(16, localEvent);
    }
  }

  void handleScroll(Event paramEvent)
  {
    ScrollBar localScrollBar = (ScrollBar)paramEvent.widget;
    Scrollable localScrollable = localScrollBar.getParent();
    if (localScrollable.equals(this.list))
      return;
    if (isParentScrolling(localScrollable))
      dropDown(false);
  }

  public int indexOf(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    return this.list.indexOf(paramString);
  }

  public int indexOf(String paramString, int paramInt)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    return this.list.indexOf(paramString, paramInt);
  }

  void initAccessible()
  {
    AccessibleAdapter local4 = new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str1 = null;
        String str2 = CCombo.this.getAssociatedLabel();
        if (str2 != null)
          str1 = CCombo.this.stripMnemonic(str2);
        paramAnonymousAccessibleEvent.result = str1;
      }

      public void getKeyboardShortcut(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str1 = null;
        String str2 = CCombo.this.getAssociatedLabel();
        if (str2 != null)
        {
          char c = CCombo.this._findMnemonic(str2);
          if (c != 0)
            str1 = "Alt+" + c;
        }
        paramAnonymousAccessibleEvent.result = str1;
      }

      public void getHelp(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = CCombo.this.getToolTipText();
      }
    };
    getAccessible().addAccessibleListener(local4);
    this.text.getAccessible().addAccessibleListener(local4);
    this.list.getAccessible().addAccessibleListener(local4);
    this.arrow.getAccessible().addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = (CCombo.this.isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"));
      }

      public void getKeyboardShortcut(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = "Alt+Down Arrow";
      }

      public void getHelp(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = CCombo.this.getToolTipText();
      }
    });
    getAccessible().addAccessibleTextListener(new AccessibleTextAdapter()
    {
      public void getCaretOffset(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        paramAnonymousAccessibleTextEvent.offset = CCombo.this.text.getCaretPosition();
      }

      public void getSelectionRange(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        Point localPoint = CCombo.this.text.getSelection();
        paramAnonymousAccessibleTextEvent.offset = localPoint.x;
        paramAnonymousAccessibleTextEvent.length = (localPoint.y - localPoint.x);
      }
    });
    getAccessible().addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getChildAtPoint(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Point localPoint = CCombo.this.toControl(paramAnonymousAccessibleControlEvent.x, paramAnonymousAccessibleControlEvent.y);
        if (CCombo.this.getBounds().contains(localPoint))
          paramAnonymousAccessibleControlEvent.childID = -1;
      }

      public void getLocation(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        Rectangle localRectangle = CCombo.this.getBounds();
        Point localPoint = CCombo.this.getParent().toDisplay(localRectangle.x, localRectangle.y);
        paramAnonymousAccessibleControlEvent.x = localPoint.x;
        paramAnonymousAccessibleControlEvent.y = localPoint.y;
        paramAnonymousAccessibleControlEvent.width = localRectangle.width;
        paramAnonymousAccessibleControlEvent.height = localRectangle.height;
      }

      public void getChildCount(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 0;
      }

      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 46;
      }

      public void getState(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 0;
      }

      public void getValue(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.result = CCombo.this.getText();
      }
    });
    this.text.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = (CCombo.this.text.getEditable() ? 42 : 41);
      }
    });
    this.arrow.getAccessible().addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getDefaultAction(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.result = (CCombo.this.isDropped() ? SWT.getMessage("SWT_Close") : SWT.getMessage("SWT_Open"));
      }
    });
  }

  boolean isDropped()
  {
    return this.popup.getVisible();
  }

  public boolean isFocusControl()
  {
    checkWidget();
    if ((this.text.isFocusControl()) || (this.arrow.isFocusControl()) || (this.list.isFocusControl()) || (this.popup.isFocusControl()))
      return true;
    return super.isFocusControl();
  }

  boolean isParentScrolling(Control paramControl)
  {
    for (Composite localComposite = getParent(); localComposite != null; localComposite = localComposite.getParent())
      if (localComposite.equals(paramControl))
        return true;
    return false;
  }

  void internalLayout(boolean paramBoolean)
  {
    if (isDropped())
      dropDown(false);
    Rectangle localRectangle = getClientArea();
    int i = localRectangle.width;
    int j = localRectangle.height;
    Point localPoint = this.arrow.computeSize(-1, j, paramBoolean);
    this.text.setBounds(0, 0, i - localPoint.x, j);
    this.arrow.setBounds(i - localPoint.x, 0, localPoint.x, localPoint.y);
  }

  void listEvent(Event paramEvent)
  {
    Event localEvent1;
    switch (paramEvent.type)
    {
    case 12:
      if (getShell() != this.popup.getParent())
      {
        String[] arrayOfString = this.list.getItems();
        int j = this.list.getSelectionIndex();
        this.popup = null;
        this.list = null;
        createPopup(arrayOfString, j);
      }
      break;
    case 15:
      handleFocus(15);
      break;
    case 4:
      if (paramEvent.button != 1)
        return;
      dropDown(false);
      break;
    case 13:
      int i = this.list.getSelectionIndex();
      if (i == -1)
        return;
      this.text.setText(this.list.getItem(i));
      this.text.selectAll();
      this.list.setSelection(i);
      Event localEvent2 = new Event();
      localEvent2.time = paramEvent.time;
      localEvent2.stateMask = paramEvent.stateMask;
      localEvent2.doit = paramEvent.doit;
      notifyListeners(13, localEvent2);
      paramEvent.doit = localEvent2.doit;
      break;
    case 31:
      switch (paramEvent.detail)
      {
      case 2:
      case 4:
      case 32:
      case 64:
        paramEvent.doit = false;
        break;
      case 8:
      case 16:
        paramEvent.doit = this.text.traverse(paramEvent.detail);
        paramEvent.detail = 0;
        if (paramEvent.doit)
          dropDown(false);
        return;
      }
      localEvent1 = new Event();
      localEvent1.time = paramEvent.time;
      localEvent1.detail = paramEvent.detail;
      localEvent1.doit = paramEvent.doit;
      localEvent1.character = paramEvent.character;
      localEvent1.keyCode = paramEvent.keyCode;
      localEvent1.keyLocation = paramEvent.keyLocation;
      notifyListeners(31, localEvent1);
      paramEvent.doit = localEvent1.doit;
      paramEvent.detail = localEvent1.detail;
      break;
    case 2:
      localEvent1 = new Event();
      localEvent1.time = paramEvent.time;
      localEvent1.character = paramEvent.character;
      localEvent1.keyCode = paramEvent.keyCode;
      localEvent1.keyLocation = paramEvent.keyLocation;
      localEvent1.stateMask = paramEvent.stateMask;
      notifyListeners(2, localEvent1);
      paramEvent.doit = localEvent1.doit;
      break;
    case 1:
      if (paramEvent.character == '\033')
        dropDown(false);
      if (((paramEvent.stateMask & 0x10000) != 0) && ((paramEvent.keyCode == 16777217) || (paramEvent.keyCode == 16777218)))
        dropDown(false);
      if (paramEvent.character == '\r')
      {
        dropDown(false);
        localEvent1 = new Event();
        localEvent1.time = paramEvent.time;
        localEvent1.stateMask = paramEvent.stateMask;
        notifyListeners(14, localEvent1);
      }
      if (!isDisposed())
      {
        localEvent1 = new Event();
        localEvent1.time = paramEvent.time;
        localEvent1.character = paramEvent.character;
        localEvent1.keyCode = paramEvent.keyCode;
        localEvent1.keyLocation = paramEvent.keyLocation;
        localEvent1.stateMask = paramEvent.stateMask;
        notifyListeners(1, localEvent1);
        paramEvent.doit = localEvent1.doit;
      }
      break;
    }
  }

  public void paste()
  {
    checkWidget();
    this.text.paste();
  }

  void popupEvent(Event paramEvent)
  {
    switch (paramEvent.type)
    {
    case 9:
      Rectangle localRectangle1 = this.list.getBounds();
      Color localColor = getDisplay().getSystemColor(2);
      paramEvent.gc.setForeground(localColor);
      paramEvent.gc.drawRectangle(0, 0, localRectangle1.width + 1, localRectangle1.height + 1);
      break;
    case 21:
      paramEvent.doit = false;
      dropDown(false);
      break;
    case 27:
      if (!"carbon".equals(SWT.getPlatform()))
      {
        Point localPoint1 = this.arrow.toControl(getDisplay().getCursorLocation());
        Point localPoint2 = this.arrow.getSize();
        Rectangle localRectangle2 = new Rectangle(0, 0, localPoint2.x, localPoint2.y);
        if (!localRectangle2.contains(localPoint1))
          dropDown(false);
      }
      else
      {
        dropDown(false);
      }
      break;
    }
  }

  public void redraw()
  {
    super.redraw();
    this.text.redraw();
    this.arrow.redraw();
    if (this.popup.isVisible())
      this.list.redraw();
  }

  public void redraw(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    super.redraw(paramInt1, paramInt2, paramInt3, paramInt4, true);
  }

  public void remove(int paramInt)
  {
    checkWidget();
    this.list.remove(paramInt);
  }

  public void remove(int paramInt1, int paramInt2)
  {
    checkWidget();
    this.list.remove(paramInt1, paramInt2);
  }

  public void remove(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    this.list.remove(paramString);
  }

  public void removeAll()
  {
    checkWidget();
    this.text.setText("");
    this.list.removeAll();
  }

  public void removeModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      SWT.error(4);
    removeListener(24, paramModifyListener);
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    removeListener(13, paramSelectionListener);
    removeListener(14, paramSelectionListener);
  }

  public void removeVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      SWT.error(4);
    removeListener(25, paramVerifyListener);
  }

  public void select(int paramInt)
  {
    checkWidget();
    if (paramInt == -1)
    {
      this.list.deselectAll();
      this.text.setText("");
      return;
    }
    if ((paramInt >= 0) && (paramInt < this.list.getItemCount()) && (paramInt != getSelectionIndex()))
    {
      this.text.setText(this.list.getItem(paramInt));
      this.text.selectAll();
      this.list.select(paramInt);
      this.list.showSelection();
    }
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    this.background = paramColor;
    if (this.text != null)
      this.text.setBackground(paramColor);
    if (this.list != null)
      this.list.setBackground(paramColor);
    if (this.arrow != null)
      this.arrow.setBackground(paramColor);
  }

  public void setEditable(boolean paramBoolean)
  {
    checkWidget();
    this.text.setEditable(paramBoolean);
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    if (this.popup != null)
      this.popup.setVisible(false);
    if (this.text != null)
      this.text.setEnabled(paramBoolean);
    if (this.arrow != null)
      this.arrow.setEnabled(paramBoolean);
  }

  public boolean setFocus()
  {
    checkWidget();
    if ((!isEnabled()) || (!isVisible()))
      return false;
    if (isFocusControl())
      return true;
    return this.text.setFocus();
  }

  public void setFont(Font paramFont)
  {
    super.setFont(paramFont);
    this.font = paramFont;
    this.text.setFont(paramFont);
    this.list.setFont(paramFont);
    internalLayout(true);
  }

  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    this.foreground = paramColor;
    if (this.text != null)
      this.text.setForeground(paramColor);
    if (this.list != null)
      this.list.setForeground(paramColor);
    if (this.arrow != null)
      this.arrow.setForeground(paramColor);
  }

  public void setItem(int paramInt, String paramString)
  {
    checkWidget();
    this.list.setItem(paramInt, paramString);
  }

  public void setItems(String[] paramArrayOfString)
  {
    checkWidget();
    this.list.setItems(paramArrayOfString);
    if (!this.text.getEditable())
      this.text.setText("");
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  public void setListVisible(boolean paramBoolean)
  {
    checkWidget();
    dropDown(paramBoolean);
  }

  public void setMenu(Menu paramMenu)
  {
    this.text.setMenu(paramMenu);
  }

  public void setSelection(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      SWT.error(4);
    this.text.setSelection(paramPoint.x, paramPoint.y);
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    int i = this.list.indexOf(paramString);
    if (i == -1)
    {
      this.list.deselectAll();
      this.text.setText(paramString);
      return;
    }
    this.text.setText(paramString);
    this.text.selectAll();
    this.list.setSelection(i);
    this.list.showSelection();
  }

  public void setTextLimit(int paramInt)
  {
    checkWidget();
    this.text.setTextLimit(paramInt);
  }

  public void setToolTipText(String paramString)
  {
    checkWidget();
    super.setToolTipText(paramString);
    this.arrow.setToolTipText(paramString);
    this.text.setToolTipText(paramString);
  }

  public void setVisible(boolean paramBoolean)
  {
    super.setVisible(paramBoolean);
    if (isDisposed())
      return;
    if ((this.popup == null) || (this.popup.isDisposed()))
      return;
    if (!paramBoolean)
      this.popup.setVisible(false);
  }

  public void setVisibleItemCount(int paramInt)
  {
    checkWidget();
    if (paramInt < 0)
      return;
    this.visibleItemCount = paramInt;
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

  void textEvent(Event paramEvent)
  {
    Object localObject;
    Event localEvent2;
    Event localEvent1;
    switch (paramEvent.type)
    {
    case 15:
      handleFocus(15);
      break;
    case 14:
      dropDown(false);
      localObject = new Event();
      ((Event)localObject).time = paramEvent.time;
      ((Event)localObject).stateMask = paramEvent.stateMask;
      notifyListeners(14, (Event)localObject);
      break;
    case 5:
    case 6:
    case 7:
    case 8:
    case 29:
    case 32:
      localObject = getDisplay().map(this.text, this, paramEvent.x, paramEvent.y);
      paramEvent.x = ((Point)localObject).x;
      paramEvent.y = ((Point)localObject).y;
      notifyListeners(paramEvent.type, paramEvent);
      paramEvent.type = 0;
      break;
    case 1:
      localObject = new Event();
      ((Event)localObject).time = paramEvent.time;
      ((Event)localObject).character = paramEvent.character;
      ((Event)localObject).keyCode = paramEvent.keyCode;
      ((Event)localObject).keyLocation = paramEvent.keyLocation;
      ((Event)localObject).stateMask = paramEvent.stateMask;
      notifyListeners(1, (Event)localObject);
      if (!isDisposed())
      {
        paramEvent.doit = ((Event)localObject).doit;
        if ((paramEvent.doit) && ((paramEvent.keyCode == 16777217) || (paramEvent.keyCode == 16777218)))
        {
          paramEvent.doit = false;
          if ((paramEvent.stateMask & 0x10000) != 0)
          {
            boolean bool1 = isDropped();
            this.text.selectAll();
            if (!bool1)
              setFocus();
            dropDown(!bool1);
          }
          else
          {
            int j = getSelectionIndex();
            if (paramEvent.keyCode == 16777217)
              select(Math.max(j - 1, 0));
            else
              select(Math.min(j + 1, getItemCount() - 1));
            if (j != getSelectionIndex())
            {
              Event localEvent3 = new Event();
              localEvent3.time = paramEvent.time;
              localEvent3.stateMask = paramEvent.stateMask;
              notifyListeners(13, localEvent3);
            }
            if (!isDisposed())
              break;
          }
        }
      }
      break;
    case 2:
      localObject = new Event();
      ((Event)localObject).time = paramEvent.time;
      ((Event)localObject).character = paramEvent.character;
      ((Event)localObject).keyCode = paramEvent.keyCode;
      ((Event)localObject).keyLocation = paramEvent.keyLocation;
      ((Event)localObject).stateMask = paramEvent.stateMask;
      notifyListeners(2, (Event)localObject);
      paramEvent.doit = ((Event)localObject).doit;
      break;
    case 35:
      localObject = new Event();
      ((Event)localObject).time = paramEvent.time;
      notifyListeners(35, (Event)localObject);
      break;
    case 24:
      this.list.deselectAll();
      localObject = new Event();
      ((Event)localObject).time = paramEvent.time;
      notifyListeners(24, (Event)localObject);
      break;
    case 3:
      localObject = getDisplay().map(this.text, this, paramEvent.x, paramEvent.y);
      localEvent2 = new Event();
      localEvent2.button = paramEvent.button;
      localEvent2.count = paramEvent.count;
      localEvent2.stateMask = paramEvent.stateMask;
      localEvent2.time = paramEvent.time;
      localEvent2.x = ((Point)localObject).x;
      localEvent2.y = ((Point)localObject).y;
      notifyListeners(3, localEvent2);
      if (!isDisposed())
      {
        paramEvent.doit = localEvent2.doit;
        if (paramEvent.doit)
        {
          if (paramEvent.button != 1)
            return;
          if (this.text.getEditable())
            return;
          boolean bool2 = isDropped();
          this.text.selectAll();
          if (!bool2)
            setFocus();
          dropDown(!bool2);
        }
      }
      break;
    case 4:
      localObject = getDisplay().map(this.text, this, paramEvent.x, paramEvent.y);
      localEvent2 = new Event();
      localEvent2.button = paramEvent.button;
      localEvent2.count = paramEvent.count;
      localEvent2.stateMask = paramEvent.stateMask;
      localEvent2.time = paramEvent.time;
      localEvent2.x = ((Point)localObject).x;
      localEvent2.y = ((Point)localObject).y;
      notifyListeners(4, localEvent2);
      if (!isDisposed())
      {
        paramEvent.doit = localEvent2.doit;
        if (paramEvent.doit)
        {
          if (paramEvent.button != 1)
            return;
          if (this.text.getEditable())
            return;
          this.text.selectAll();
        }
      }
      break;
    case 37:
      notifyListeners(37, paramEvent);
      paramEvent.type = 0;
      if ((!isDisposed()) && (paramEvent.doit) && (paramEvent.count != 0))
      {
        paramEvent.doit = false;
        int i = getSelectionIndex();
        if (paramEvent.count > 0)
          select(Math.max(i - 1, 0));
        else
          select(Math.min(i + 1, getItemCount() - 1));
        if (i != getSelectionIndex())
        {
          localEvent2 = new Event();
          localEvent2.time = paramEvent.time;
          localEvent2.stateMask = paramEvent.stateMask;
          notifyListeners(13, localEvent2);
        }
        if (!isDisposed())
          break;
      }
      break;
    case 31:
      switch (paramEvent.detail)
      {
      case 32:
      case 64:
        paramEvent.doit = false;
        break;
      case 8:
        paramEvent.doit = traverse(8);
        paramEvent.detail = 0;
        return;
      }
      localEvent1 = new Event();
      localEvent1.time = paramEvent.time;
      localEvent1.detail = paramEvent.detail;
      localEvent1.doit = paramEvent.doit;
      localEvent1.character = paramEvent.character;
      localEvent1.keyCode = paramEvent.keyCode;
      localEvent1.keyLocation = paramEvent.keyLocation;
      notifyListeners(31, localEvent1);
      paramEvent.doit = localEvent1.doit;
      paramEvent.detail = localEvent1.detail;
      break;
    case 25:
      localEvent1 = new Event();
      localEvent1.text = paramEvent.text;
      localEvent1.start = paramEvent.start;
      localEvent1.end = paramEvent.end;
      localEvent1.character = paramEvent.character;
      localEvent1.keyCode = paramEvent.keyCode;
      localEvent1.keyLocation = paramEvent.keyLocation;
      localEvent1.stateMask = paramEvent.stateMask;
      notifyListeners(25, localEvent1);
      paramEvent.text = localEvent1.text;
      paramEvent.doit = localEvent1.doit;
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 16:
    case 17:
    case 18:
    case 19:
    case 20:
    case 21:
    case 22:
    case 23:
    case 26:
    case 27:
    case 28:
    case 30:
    case 33:
    case 34:
    case 36:
    }
  }

  public boolean traverse(int paramInt)
  {
    if ((paramInt == 64) || (paramInt == 16))
      return this.text.traverse(paramInt);
    return super.traverse(paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.CCombo
 * JD-Core Version:    0.6.2
 */