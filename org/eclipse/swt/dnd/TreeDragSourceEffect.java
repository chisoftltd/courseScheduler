package org.eclipse.swt.dnd;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.win32.BITMAP;
import org.eclipse.swt.internal.win32.BITMAPINFOHEADER;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.POINT;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SHDRAGIMAGE;
import org.eclipse.swt.internal.win32.SIZE;
import org.eclipse.swt.internal.win32.TCHAR;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class TreeDragSourceEffect extends DragSourceEffect
{
  Image dragSourceImage = null;

  public TreeDragSourceEffect(Tree paramTree)
  {
    super(paramTree);
  }

  public void dragFinished(DragSourceEvent paramDragSourceEvent)
  {
    if (this.dragSourceImage != null)
      this.dragSourceImage.dispose();
    this.dragSourceImage = null;
  }

  public void dragStart(DragSourceEvent paramDragSourceEvent)
  {
    paramDragSourceEvent.image = getDragSourceImage(paramDragSourceEvent);
  }

  Image getDragSourceImage(DragSourceEvent paramDragSourceEvent)
  {
    if (this.dragSourceImage != null)
      this.dragSourceImage.dispose();
    this.dragSourceImage = null;
    int n;
    int i1;
    int i2;
    int i3;
    Object localObject2;
    int i8;
    if ((!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(5, 1)))
    {
      localObject1 = new SHDRAGIMAGE();
      int i = OS.RegisterWindowMessage(new TCHAR(0, "ShellGetDragImage", true));
      if (OS.SendMessage(this.control.handle, i, 0, (SHDRAGIMAGE)localObject1) != 0)
      {
        if ((this.control.getStyle() & 0x8000000) != 0)
          paramDragSourceEvent.offsetX = (((SHDRAGIMAGE)localObject1).sizeDragImage.cx - ((SHDRAGIMAGE)localObject1).ptOffset.x);
        else
          paramDragSourceEvent.offsetX = ((SHDRAGIMAGE)localObject1).ptOffset.x;
        paramDragSourceEvent.offsetY = ((SHDRAGIMAGE)localObject1).ptOffset.y;
        j = ((SHDRAGIMAGE)localObject1).hbmpDragImage;
        if (j != 0)
        {
          BITMAP localBITMAP1 = new BITMAP();
          OS.GetObject(j, BITMAP.sizeof, localBITMAP1);
          int m = localBITMAP1.bmWidth;
          n = localBITMAP1.bmHeight;
          i1 = OS.GetDC(0);
          i2 = OS.CreateCompatibleDC(i1);
          i3 = OS.SelectObject(i2, j);
          int i4 = OS.CreateCompatibleDC(i1);
          BITMAPINFOHEADER localBITMAPINFOHEADER = new BITMAPINFOHEADER();
          localBITMAPINFOHEADER.biSize = BITMAPINFOHEADER.sizeof;
          localBITMAPINFOHEADER.biWidth = m;
          localBITMAPINFOHEADER.biHeight = (-n);
          localBITMAPINFOHEADER.biPlanes = 1;
          localBITMAPINFOHEADER.biBitCount = 32;
          localBITMAPINFOHEADER.biCompression = 0;
          byte[] arrayOfByte1 = new byte[BITMAPINFOHEADER.sizeof];
          OS.MoveMemory(arrayOfByte1, localBITMAPINFOHEADER, BITMAPINFOHEADER.sizeof);
          localObject2 = new int[1];
          int i7 = OS.CreateDIBSection(0, arrayOfByte1, 0, (int[])localObject2, 0, 0);
          if (i7 == 0)
            SWT.error(2);
          i8 = OS.SelectObject(i4, i7);
          BITMAP localBITMAP2 = new BITMAP();
          OS.GetObject(i7, BITMAP.sizeof, localBITMAP2);
          int i9 = localBITMAP2.bmWidthBytes * localBITMAP2.bmHeight;
          OS.BitBlt(i4, 0, 0, m, n, i2, 0, 0, 13369376);
          byte[] arrayOfByte2 = new byte[i9];
          OS.MoveMemory(arrayOfByte2, localBITMAP2.bmBits, i9);
          PaletteData localPaletteData = new PaletteData(65280, 16711680, -16777216);
          ImageData localImageData = new ImageData(m, n, localBITMAP1.bmBitsPixel, localPaletteData, localBITMAP1.bmWidthBytes, arrayOfByte2);
          if (((SHDRAGIMAGE)localObject1).crColorKey == -1)
          {
            byte[] arrayOfByte3 = new byte[m * n];
            int i10 = localBITMAP2.bmWidthBytes - m * 4;
            int i11 = 0;
            int i12 = 3;
            for (int i13 = 0; i13 < n; i13++)
            {
              for (int i14 = 0; i14 < m; i14++)
              {
                arrayOfByte3[(i11++)] = arrayOfByte2[i12];
                i12 += 4;
              }
              i12 += i10;
            }
            localImageData.alphaData = arrayOfByte3;
          }
          else
          {
            localImageData.transparentPixel = (((SHDRAGIMAGE)localObject1).crColorKey << 8);
          }
          this.dragSourceImage = new Image(this.control.getDisplay(), localImageData);
          OS.SelectObject(i4, i8);
          OS.DeleteDC(i4);
          OS.DeleteObject(i7);
          OS.SelectObject(i2, i3);
          OS.DeleteDC(i2);
          OS.ReleaseDC(0, i1);
          OS.DeleteObject(j);
          return this.dragSourceImage;
        }
      }
      return null;
    }
    Object localObject1 = (Tree)this.control;
    if ((((Tree)localObject1).isListening(40)) || (((Tree)localObject1).isListening(42)))
      return null;
    TreeItem[] arrayOfTreeItem = ((Tree)localObject1).getSelection();
    if (arrayOfTreeItem.length == 0)
      return null;
    int j = OS.SendMessage(((Tree)localObject1).handle, 4360, 0, 0);
    if (j != 0)
    {
      int k = Math.min(arrayOfTreeItem.length, 10);
      Rectangle localRectangle1 = arrayOfTreeItem[0].getBounds(0);
      for (n = 1; n < k; n++)
        localRectangle1 = localRectangle1.union(arrayOfTreeItem[n].getBounds(0));
      n = OS.GetDC(((Tree)localObject1).handle);
      i1 = OS.CreateCompatibleDC(n);
      i2 = OS.CreateCompatibleBitmap(n, localRectangle1.width, localRectangle1.height);
      i3 = OS.SelectObject(i1, i2);
      RECT localRECT = new RECT();
      localRECT.right = localRectangle1.width;
      localRECT.bottom = localRectangle1.height;
      int i5 = OS.GetStockObject(0);
      OS.FillRect(i1, localRECT, i5);
      for (int i6 = 0; i6 < k; i6++)
      {
        localObject2 = arrayOfTreeItem[i6];
        Rectangle localRectangle2 = ((TreeItem)localObject2).getBounds(0);
        i8 = OS.SendMessage(((Tree)localObject1).handle, 4370, 0, ((TreeItem)localObject2).handle);
        OS.ImageList_Draw(i8, 0, i1, localRectangle2.x - localRectangle1.x, localRectangle2.y - localRectangle1.y, 4);
        OS.ImageList_Destroy(i8);
      }
      OS.SelectObject(i1, i3);
      OS.DeleteDC(i1);
      OS.ReleaseDC(((Tree)localObject1).handle, n);
      Display localDisplay = ((Tree)localObject1).getDisplay();
      this.dragSourceImage = Image.win32_new(localDisplay, 0, i2);
      return this.dragSourceImage;
    }
    return null;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.dnd.TreeDragSourceEffect
 * JD-Core Version:    0.6.2
 */