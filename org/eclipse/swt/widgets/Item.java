package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;

public abstract class Item extends Widget
{
  String text = "";
  Image image;

  public Item(Widget paramWidget, int paramInt)
  {
    super(paramWidget, paramInt);
  }

  public Item(Widget paramWidget, int paramInt1, int paramInt2)
  {
    this(paramWidget, paramInt1);
  }

  protected void checkSubclass()
  {
  }

  public Image getImage()
  {
    checkWidget();
    return this.image;
  }

  String getNameText()
  {
    return getText();
  }

  public String getText()
  {
    checkWidget();
    return this.text;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    this.text = null;
    this.image = null;
  }

  public void setImage(Image paramImage)
  {
    checkWidget();
    if ((paramImage != null) && (paramImage.isDisposed()))
      error(5);
    this.image = paramImage;
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      error(4);
    this.text = paramString;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.Item
 * JD-Core Version:    0.6.2
 */