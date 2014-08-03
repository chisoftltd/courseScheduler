package org.eclipse.swt.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.internal.win32.LVHITTESTINFO;
import org.eclipse.swt.internal.win32.LVINSERTMARK;
import org.eclipse.swt.internal.win32.LVITEM;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class TableDropTargetEffect extends DropTargetEffect
{
  static final int SCROLL_HYSTERESIS = 200;
  int scrollIndex = -1;
  long scrollBeginTime;
  TableItem dropHighlight;
  int iItemInsert = -1;

  public TableDropTargetEffect(Table paramTable)
  {
    super(paramTable);
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
    this.scrollBeginTime = 0L;
    this.scrollIndex = -1;
    this.dropHighlight = null;
    this.iItemInsert = -1;
  }

  public void dragLeave(DropTargetEvent paramDropTargetEvent)
  {
    Table localTable = (Table)this.control;
    int i = localTable.handle;
    Object localObject;
    if (this.dropHighlight != null)
    {
      localObject = new LVITEM();
      ((LVITEM)localObject).stateMask = 8;
      OS.SendMessage(i, 4139, -1, (LVITEM)localObject);
      this.dropHighlight = null;
    }
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)) && (this.iItemInsert != -1))
    {
      localObject = new LVINSERTMARK();
      ((LVINSERTMARK)localObject).cbSize = LVINSERTMARK.sizeof;
      ((LVINSERTMARK)localObject).iItem = -1;
      OS.SendMessage(i, 4262, 0, (LVINSERTMARK)localObject);
      this.iItemInsert = -1;
    }
    this.scrollBeginTime = 0L;
    this.scrollIndex = -1;
  }

  public void dragOver(DropTargetEvent paramDropTargetEvent)
  {
    Table localTable = (Table)getControl();
    int i = checkEffect(paramDropTargetEvent.feedback);
    int j = localTable.handle;
    Point localPoint = new Point(paramDropTargetEvent.x, paramDropTargetEvent.y);
    localPoint = localTable.toControl(localPoint);
    LVHITTESTINFO localLVHITTESTINFO = new LVHITTESTINFO();
    localLVHITTESTINFO.x = localPoint.x;
    localLVHITTESTINFO.y = localPoint.y;
    OS.SendMessage(j, 4114, 0, localLVHITTESTINFO);
    if ((i & 0x8) == 0)
    {
      this.scrollBeginTime = 0L;
      this.scrollIndex = -1;
    }
    else if ((localLVHITTESTINFO.iItem != -1) && (this.scrollIndex == localLVHITTESTINFO.iItem) && (this.scrollBeginTime != 0L))
    {
      if (System.currentTimeMillis() >= this.scrollBeginTime)
      {
        int k = Math.max(0, OS.SendMessage(j, 4135, 0, 0));
        int m = OS.SendMessage(j, 4100, 0, 0);
        int n = this.scrollIndex - 1 < k ? Math.max(0, this.scrollIndex - 1) : Math.min(m - 1, this.scrollIndex + 1);
        int i1 = 1;
        if (localLVHITTESTINFO.iItem == k)
        {
          i1 = localLVHITTESTINFO.iItem != n ? 1 : 0;
        }
        else
        {
          RECT localRECT1 = new RECT();
          localRECT1.left = 0;
          if (OS.SendMessage(j, 4110, localLVHITTESTINFO.iItem, localRECT1) != 0)
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
          OS.SendMessage(j, 4115, n, 0);
          localTable.redraw();
        }
        this.scrollBeginTime = 0L;
        this.scrollIndex = -1;
      }
    }
    else
    {
      this.scrollBeginTime = (System.currentTimeMillis() + 200L);
      this.scrollIndex = localLVHITTESTINFO.iItem;
    }
    Object localObject;
    if ((localLVHITTESTINFO.iItem != -1) && ((i & 0x1) != 0))
    {
      localObject = localTable.getItem(localLVHITTESTINFO.iItem);
      if (this.dropHighlight != localObject)
      {
        LVITEM localLVITEM = new LVITEM();
        localLVITEM.stateMask = 8;
        OS.SendMessage(j, 4139, -1, localLVITEM);
        localLVITEM.state = 8;
        OS.SendMessage(j, 4139, localLVHITTESTINFO.iItem, localLVITEM);
        this.dropHighlight = ((TableItem)localObject);
      }
    }
    else if (this.dropHighlight != null)
    {
      localObject = new LVITEM();
      ((LVITEM)localObject).stateMask = 8;
      OS.SendMessage(j, 4139, -1, (LVITEM)localObject);
      this.dropHighlight = null;
    }
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
      if ((localLVHITTESTINFO.iItem != -1) && ((i & 0x6) != 0))
      {
        localObject = new LVINSERTMARK();
        ((LVINSERTMARK)localObject).cbSize = LVINSERTMARK.sizeof;
        ((LVINSERTMARK)localObject).dwFlags = ((i & 0x4) != 0 ? 1 : 0);
        ((LVINSERTMARK)localObject).iItem = localLVHITTESTINFO.iItem;
        if (OS.SendMessage(j, 4262, 0, (LVINSERTMARK)localObject) != 0)
          this.iItemInsert = localLVHITTESTINFO.iItem;
      }
      else if (this.iItemInsert != -1)
      {
        localObject = new LVINSERTMARK();
        ((LVINSERTMARK)localObject).cbSize = LVINSERTMARK.sizeof;
        ((LVINSERTMARK)localObject).iItem = -1;
        OS.SendMessage(j, 4262, 0, (LVINSERTMARK)localObject);
        this.iItemInsert = -1;
      }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.TableDropTargetEffect
 * JD-Core Version:    0.6.2
 */