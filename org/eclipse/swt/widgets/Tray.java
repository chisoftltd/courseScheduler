package org.eclipse.swt.widgets;

public class Tray extends Widget
{
  int itemCount;
  TrayItem[] items = new TrayItem[4];

  Tray(Display paramDisplay, int paramInt)
  {
    if (paramDisplay == null)
      paramDisplay = Display.getCurrent();
    if (paramDisplay == null)
      paramDisplay = Display.getDefault();
    if (!paramDisplay.isValidThread())
      error(22);
    this.display = paramDisplay;
    reskinWidget();
  }

  void createItem(TrayItem paramTrayItem, int paramInt)
  {
    if ((paramInt < 0) || (paramInt > this.itemCount))
      error(6);
    if (this.itemCount == this.items.length)
    {
      TrayItem[] arrayOfTrayItem = new TrayItem[this.items.length + 4];
      System.arraycopy(this.items, 0, arrayOfTrayItem, 0, this.items.length);
      this.items = arrayOfTrayItem;
    }
    System.arraycopy(this.items, paramInt, this.items, paramInt + 1, this.itemCount++ - paramInt);
    this.items[paramInt] = paramTrayItem;
  }

  void destroyItem(TrayItem paramTrayItem)
  {
    for (int i = 0; i < this.itemCount; i++)
      if (this.items[i] == paramTrayItem)
        break;
    if (i == this.itemCount)
      return;
    System.arraycopy(this.items, i + 1, this.items, i, --this.itemCount - i);
    this.items[this.itemCount] = null;
  }

  public TrayItem getItem(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt >= this.itemCount))
      error(6);
    return this.items[paramInt];
  }

  public int getItemCount()
  {
    checkWidget();
    return this.itemCount;
  }

  public TrayItem[] getItems()
  {
    checkWidget();
    TrayItem[] arrayOfTrayItem = new TrayItem[this.itemCount];
    System.arraycopy(this.items, 0, arrayOfTrayItem, 0, arrayOfTrayItem.length);
    return arrayOfTrayItem;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      for (int i = 0; i < this.items.length; i++)
      {
        TrayItem localTrayItem = this.items[i];
        if ((localTrayItem != null) && (!localTrayItem.isDisposed()))
          localTrayItem.release(false);
      }
      this.items = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this.display.tray == this)
      this.display.tray = null;
  }

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
      for (int i = 0; i < this.items.length; i++)
      {
        TrayItem localTrayItem = this.items[i];
        if (localTrayItem != null)
          localTrayItem.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Tray
 * JD-Core Version:    0.6.2
 */