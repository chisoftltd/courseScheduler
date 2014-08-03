package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public final class FormData
{
  public int width = -1;
  public int height = -1;
  public FormAttachment left;
  public FormAttachment right;
  public FormAttachment top;
  public FormAttachment bottom;
  int cacheWidth = -1;
  int cacheHeight = -1;
  int defaultWhint;
  int defaultHhint;
  int defaultWidth = -1;
  int defaultHeight = -1;
  int currentWhint;
  int currentHhint;
  int currentWidth = -1;
  int currentHeight = -1;
  FormAttachment cacheLeft;
  FormAttachment cacheRight;
  FormAttachment cacheTop;
  FormAttachment cacheBottom;
  boolean isVisited;
  boolean needed;

  public FormData()
  {
  }

  public FormData(int paramInt1, int paramInt2)
  {
    this.width = paramInt1;
    this.height = paramInt2;
  }

  void computeSize(Control paramControl, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.cacheWidth != -1) && (this.cacheHeight != -1))
      return;
    Point localPoint;
    if ((paramInt1 == this.width) && (paramInt2 == this.height))
    {
      if ((this.defaultWidth == -1) || (this.defaultHeight == -1) || (paramInt1 != this.defaultWhint) || (paramInt2 != this.defaultHhint))
      {
        localPoint = paramControl.computeSize(paramInt1, paramInt2, paramBoolean);
        this.defaultWhint = paramInt1;
        this.defaultHhint = paramInt2;
        this.defaultWidth = localPoint.x;
        this.defaultHeight = localPoint.y;
      }
      this.cacheWidth = this.defaultWidth;
      this.cacheHeight = this.defaultHeight;
      return;
    }
    if ((this.currentWidth == -1) || (this.currentHeight == -1) || (paramInt1 != this.currentWhint) || (paramInt2 != this.currentHhint))
    {
      localPoint = paramControl.computeSize(paramInt1, paramInt2, paramBoolean);
      this.currentWhint = paramInt1;
      this.currentHhint = paramInt2;
      this.currentWidth = localPoint.x;
      this.currentHeight = localPoint.y;
    }
    this.cacheWidth = this.currentWidth;
    this.cacheHeight = this.currentHeight;
  }

  void flushCache()
  {
    this.cacheWidth = (this.cacheHeight = -1);
    this.defaultHeight = (this.defaultWidth = -1);
    this.currentHeight = (this.currentWidth = -1);
  }

  int getWidth(Control paramControl, boolean paramBoolean)
  {
    this.needed = true;
    computeSize(paramControl, this.width, this.height, paramBoolean);
    return this.cacheWidth;
  }

  int getHeight(Control paramControl, boolean paramBoolean)
  {
    computeSize(paramControl, this.width, this.height, paramBoolean);
    return this.cacheHeight;
  }

  FormAttachment getBottomAttachment(Control paramControl, int paramInt, boolean paramBoolean)
  {
    if (this.cacheBottom != null)
      return this.cacheBottom;
    if (this.isVisited)
      return this.cacheBottom = new FormAttachment(0, getHeight(paramControl, paramBoolean));
    if (this.bottom == null)
    {
      if (this.top == null)
        return this.cacheBottom = new FormAttachment(0, getHeight(paramControl, paramBoolean));
      return this.cacheBottom = getTopAttachment(paramControl, paramInt, paramBoolean).plus(getHeight(paramControl, paramBoolean));
    }
    Control localControl = this.bottom.control;
    if (localControl != null)
      if (localControl.isDisposed())
        this.bottom.control = (localControl = null);
      else if (localControl.getParent() != paramControl.getParent())
        localControl = null;
    if (localControl == null)
      return this.cacheBottom = this.bottom;
    this.isVisited = true;
    FormData localFormData = (FormData)localControl.getLayoutData();
    FormAttachment localFormAttachment1 = localFormData.getBottomAttachment(localControl, paramInt, paramBoolean);
    FormAttachment localFormAttachment2;
    switch (this.bottom.alignment)
    {
    case 1024:
      this.cacheBottom = localFormAttachment1.plus(this.bottom.offset);
      break;
    case 16777216:
      localFormAttachment2 = localFormData.getTopAttachment(localControl, paramInt, paramBoolean);
      FormAttachment localFormAttachment3 = localFormAttachment1.minus(localFormAttachment2);
      this.cacheBottom = localFormAttachment1.minus(localFormAttachment3.minus(getHeight(paramControl, paramBoolean)).divide(2));
      break;
    default:
      localFormAttachment2 = localFormData.getTopAttachment(localControl, paramInt, paramBoolean);
      this.cacheBottom = localFormAttachment2.plus(this.bottom.offset - paramInt);
    }
    this.isVisited = false;
    return this.cacheBottom;
  }

  FormAttachment getLeftAttachment(Control paramControl, int paramInt, boolean paramBoolean)
  {
    if (this.cacheLeft != null)
      return this.cacheLeft;
    if (this.isVisited)
      return this.cacheLeft = new FormAttachment(0, 0);
    if (this.left == null)
    {
      if (this.right == null)
        return this.cacheLeft = new FormAttachment(0, 0);
      return this.cacheLeft = getRightAttachment(paramControl, paramInt, paramBoolean).minus(getWidth(paramControl, paramBoolean));
    }
    Control localControl = this.left.control;
    if (localControl != null)
      if (localControl.isDisposed())
        this.left.control = (localControl = null);
      else if (localControl.getParent() != paramControl.getParent())
        localControl = null;
    if (localControl == null)
      return this.cacheLeft = this.left;
    this.isVisited = true;
    FormData localFormData = (FormData)localControl.getLayoutData();
    FormAttachment localFormAttachment1 = localFormData.getLeftAttachment(localControl, paramInt, paramBoolean);
    FormAttachment localFormAttachment2;
    switch (this.left.alignment)
    {
    case 16384:
      this.cacheLeft = localFormAttachment1.plus(this.left.offset);
      break;
    case 16777216:
      localFormAttachment2 = localFormData.getRightAttachment(localControl, paramInt, paramBoolean);
      FormAttachment localFormAttachment3 = localFormAttachment2.minus(localFormAttachment1);
      this.cacheLeft = localFormAttachment1.plus(localFormAttachment3.minus(getWidth(paramControl, paramBoolean)).divide(2));
      break;
    default:
      localFormAttachment2 = localFormData.getRightAttachment(localControl, paramInt, paramBoolean);
      this.cacheLeft = localFormAttachment2.plus(this.left.offset + paramInt);
    }
    this.isVisited = false;
    return this.cacheLeft;
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  FormAttachment getRightAttachment(Control paramControl, int paramInt, boolean paramBoolean)
  {
    if (this.cacheRight != null)
      return this.cacheRight;
    if (this.isVisited)
      return this.cacheRight = new FormAttachment(0, getWidth(paramControl, paramBoolean));
    if (this.right == null)
    {
      if (this.left == null)
        return this.cacheRight = new FormAttachment(0, getWidth(paramControl, paramBoolean));
      return this.cacheRight = getLeftAttachment(paramControl, paramInt, paramBoolean).plus(getWidth(paramControl, paramBoolean));
    }
    Control localControl = this.right.control;
    if (localControl != null)
      if (localControl.isDisposed())
        this.right.control = (localControl = null);
      else if (localControl.getParent() != paramControl.getParent())
        localControl = null;
    if (localControl == null)
      return this.cacheRight = this.right;
    this.isVisited = true;
    FormData localFormData = (FormData)localControl.getLayoutData();
    FormAttachment localFormAttachment1 = localFormData.getRightAttachment(localControl, paramInt, paramBoolean);
    FormAttachment localFormAttachment2;
    switch (this.right.alignment)
    {
    case 131072:
      this.cacheRight = localFormAttachment1.plus(this.right.offset);
      break;
    case 16777216:
      localFormAttachment2 = localFormData.getLeftAttachment(localControl, paramInt, paramBoolean);
      FormAttachment localFormAttachment3 = localFormAttachment1.minus(localFormAttachment2);
      this.cacheRight = localFormAttachment1.minus(localFormAttachment3.minus(getWidth(paramControl, paramBoolean)).divide(2));
      break;
    default:
      localFormAttachment2 = localFormData.getLeftAttachment(localControl, paramInt, paramBoolean);
      this.cacheRight = localFormAttachment2.plus(this.right.offset - paramInt);
    }
    this.isVisited = false;
    return this.cacheRight;
  }

  FormAttachment getTopAttachment(Control paramControl, int paramInt, boolean paramBoolean)
  {
    if (this.cacheTop != null)
      return this.cacheTop;
    if (this.isVisited)
      return this.cacheTop = new FormAttachment(0, 0);
    if (this.top == null)
    {
      if (this.bottom == null)
        return this.cacheTop = new FormAttachment(0, 0);
      return this.cacheTop = getBottomAttachment(paramControl, paramInt, paramBoolean).minus(getHeight(paramControl, paramBoolean));
    }
    Control localControl = this.top.control;
    if (localControl != null)
      if (localControl.isDisposed())
        this.top.control = (localControl = null);
      else if (localControl.getParent() != paramControl.getParent())
        localControl = null;
    if (localControl == null)
      return this.cacheTop = this.top;
    this.isVisited = true;
    FormData localFormData = (FormData)localControl.getLayoutData();
    FormAttachment localFormAttachment1 = localFormData.getTopAttachment(localControl, paramInt, paramBoolean);
    FormAttachment localFormAttachment2;
    switch (this.top.alignment)
    {
    case 128:
      this.cacheTop = localFormAttachment1.plus(this.top.offset);
      break;
    case 16777216:
      localFormAttachment2 = localFormData.getBottomAttachment(localControl, paramInt, paramBoolean);
      FormAttachment localFormAttachment3 = localFormAttachment2.minus(localFormAttachment1);
      this.cacheTop = localFormAttachment1.plus(localFormAttachment3.minus(getHeight(paramControl, paramBoolean)).divide(2));
      break;
    default:
      localFormAttachment2 = localFormData.getBottomAttachment(localControl, paramInt, paramBoolean);
      this.cacheTop = localFormAttachment2.plus(this.top.offset + paramInt);
    }
    this.isVisited = false;
    return this.cacheTop;
  }

  public String toString()
  {
    String str = getName() + " {";
    if (this.width != -1)
      str = str + "width=" + this.width + " ";
    if (this.height != -1)
      str = str + "height=" + this.height + " ";
    if (this.left != null)
      str = str + "left=" + this.left + " ";
    if (this.right != null)
      str = str + "right=" + this.right + " ";
    if (this.top != null)
      str = str + "top=" + this.top + " ";
    if (this.bottom != null)
      str = str + "bottom=" + this.bottom + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.FormData
 * JD-Core Version:    0.6.2
 */