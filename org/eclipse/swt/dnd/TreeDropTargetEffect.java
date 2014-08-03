package org.eclipse.swt.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.TVHITTESTINFO;
import org.eclipse.swt.internal.win32.TVITEM;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;

public class TreeDropTargetEffect extends DropTargetEffect
{
  static final int SCROLL_HYSTERESIS = 200;
  static final int EXPAND_HYSTERESIS = 1000;
  int dropIndex;
  int scrollIndex;
  long scrollBeginTime;
  int expandIndex;
  long expandBeginTime;
  TreeItem insertItem;
  boolean insertBefore;

  public TreeDropTargetEffect(Tree paramTree)
  {
    super(paramTree);
  }

  int checkEffect(int paramInt)
  {
    if ((paramInt & 0x1) != 0)
      paramInt = paramInt & 0xFFFFFFFB & 0xFFFFFFFD;
    if ((paramInt & 0x2) != 0)
      paramInt &= -5;
    return paramInt;
  }

  public void dragEnter(DropTargetEvent paramDropTargetEvent)
  {
    this.dropIndex = -1;
    this.insertItem = null;
    this.expandBeginTime = 0L;
    this.expandIndex = -1;
    this.scrollBeginTime = 0L;
    this.scrollIndex = -1;
  }

  public void dragLeave(DropTargetEvent paramDropTargetEvent)
  {
    Tree localTree = (Tree)this.control;
    int i = localTree.handle;
    if (this.dropIndex != -1)
    {
      TVITEM localTVITEM = new TVITEM();
      localTVITEM.hItem = this.dropIndex;
      localTVITEM.mask = 8;
      localTVITEM.stateMask = 8;
      localTVITEM.state = 0;
      OS.SendMessage(i, OS.TVM_SETITEM, 0, localTVITEM);
      this.dropIndex = -1;
    }
    if (this.insertItem != null)
    {
      localTree.setInsertMark(null, false);
      this.insertItem = null;
    }
    this.expandBeginTime = 0L;
    this.expandIndex = -1;
    this.scrollBeginTime = 0L;
    this.scrollIndex = -1;
  }

  public void dragOver(DropTargetEvent paramDropTargetEvent)
  {
    Tree localTree = (Tree)getControl();
    int i = checkEffect(paramDropTargetEvent.feedback);
    int j = localTree.handle;
    Point localPoint = new Point(paramDropTargetEvent.x, paramDropTargetEvent.y);
    localPoint = localTree.toControl(localPoint);
    TVHITTESTINFO localTVHITTESTINFO = new TVHITTESTINFO();
    localTVHITTESTINFO.x = localPoint.x;
    localTVHITTESTINFO.y = localPoint.y;
    OS.SendMessage(j, 4369, 0, localTVHITTESTINFO);
    int k = localTVHITTESTINFO.hItem;
    if ((i & 0x8) == 0)
    {
      this.scrollBeginTime = 0L;
      this.scrollIndex = -1;
    }
    else if ((k != -1) && (this.scrollIndex == k) && (this.scrollBeginTime != 0L))
    {
      if (System.currentTimeMillis() >= this.scrollBeginTime)
      {
        int m = OS.SendMessage(j, 4362, 5, 0);
        int n = OS.SendMessage(j, 4362, k == m ? 7 : 6, k);
        int i1 = 1;
        if (k == m)
        {
          i1 = n != 0 ? 1 : 0;
        }
        else
        {
          RECT localRECT1 = new RECT();
          if (OS.TreeView_GetItemRect(j, n, localRECT1, true))
          {
            RECT localRECT2 = new RECT();
            OS.GetClientRect(j, localRECT2);
            POINT localPOINT = new POINT();
            localPOINT.x = localRECT1.left;
            localPOINT.y = localRECT1.top;
            if (OS.PtInRect(localRECT2, localPOINT))
            {
              localPOINT.y = localRECT1.bottom;
              if (OS.PtInRect(localRECT2, localPOINT))
                i1 = 0;
            }
          }
        }
        if (i1 != 0)
        {
          OS.SendMessage(j, 4372, 0, n);
          localTree.redraw();
        }
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1;
      }
    }
    else
    {
      this.scrollBeginTime = (System.currentTimeMillis() + 200L);
      this.scrollIndex = k;
    }
    Object localObject1;
    Object localObject2;
    if ((i & 0x10) == 0)
    {
      this.expandBeginTime = 0L;
      this.expandIndex = -1;
    }
    else if ((k != -1) && (this.expandIndex == k) && (this.expandBeginTime != 0L))
    {
      if (System.currentTimeMillis() >= this.expandBeginTime)
      {
        if (OS.SendMessage(j, 4362, 4, k) != 0)
        {
          localObject1 = (TreeItem)localTree.getDisplay().findWidget(localTree.handle, k);
          if ((localObject1 != null) && (!((TreeItem)localObject1).getExpanded()))
          {
            ((TreeItem)localObject1).setExpanded(true);
            localTree.redraw();
            localObject2 = new Event();
            ((Event)localObject2).item = ((Widget)localObject1);
            localTree.notifyListeners(17, (Event)localObject2);
          }
        }
        this.expandBeginTime = 0L;
        this.expandIndex = -1;
      }
    }
    else
    {
      this.expandBeginTime = (System.currentTimeMillis() + 1000L);
      this.expandIndex = k;
    }
    if ((this.dropIndex != -1) && ((this.dropIndex != k) || ((i & 0x1) == 0)))
    {
      localObject1 = new TVITEM();
      ((TVITEM)localObject1).hItem = this.dropIndex;
      ((TVITEM)localObject1).mask = 8;
      ((TVITEM)localObject1).stateMask = 8;
      ((TVITEM)localObject1).state = 0;
      OS.SendMessage(j, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
      this.dropIndex = -1;
    }
    if ((k != -1) && (k != this.dropIndex) && ((i & 0x1) != 0))
    {
      localObject1 = new TVITEM();
      ((TVITEM)localObject1).hItem = k;
      ((TVITEM)localObject1).mask = 8;
      ((TVITEM)localObject1).stateMask = 8;
      ((TVITEM)localObject1).state = 8;
      OS.SendMessage(j, OS.TVM_SETITEM, 0, (TVITEM)localObject1);
      this.dropIndex = k;
    }
    if (((i & 0x2) != 0) || ((i & 0x4) != 0))
    {
      boolean bool = (i & 0x2) != 0;
      localObject2 = (TreeItem)localTree.getDisplay().findWidget(localTree.handle, k);
      if (localObject2 != null)
      {
        if ((localObject2 != this.insertItem) || (bool != this.insertBefore))
          localTree.setInsertMark((TreeItem)localObject2, bool);
        this.insertItem = ((TreeItem)localObject2);
        this.insertBefore = bool;
      }
      else
      {
        if (this.insertItem != null)
          localTree.setInsertMark(null, false);
        this.insertItem = null;
      }
    }
    else
    {
      if (this.insertItem != null)
        localTree.setInsertMark(null, false);
      this.insertItem = null;
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.TreeDropTargetEffect
 * JD-Core Version:    0.6.2
 */