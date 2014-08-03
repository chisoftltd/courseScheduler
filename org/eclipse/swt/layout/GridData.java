package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

public final class GridData
{
  public int verticalAlignment = 2;
  public int horizontalAlignment = 1;
  public int widthHint = -1;
  public int heightHint = -1;
  public int horizontalIndent = 0;
  public int verticalIndent = 0;
  public int horizontalSpan = 1;
  public int verticalSpan = 1;
  public boolean grabExcessHorizontalSpace = false;
  public boolean grabExcessVerticalSpace = false;
  public int minimumWidth = 0;
  public int minimumHeight = 0;
  public boolean exclude = false;
  public static final int BEGINNING = 1;
  public static final int CENTER = 2;
  public static final int END = 3;
  public static final int FILL = 4;
  public static final int VERTICAL_ALIGN_BEGINNING = 2;
  public static final int VERTICAL_ALIGN_CENTER = 4;
  public static final int VERTICAL_ALIGN_END = 8;
  public static final int VERTICAL_ALIGN_FILL = 16;
  public static final int HORIZONTAL_ALIGN_BEGINNING = 32;
  public static final int HORIZONTAL_ALIGN_CENTER = 64;
  public static final int HORIZONTAL_ALIGN_END = 128;
  public static final int HORIZONTAL_ALIGN_FILL = 256;
  public static final int GRAB_HORIZONTAL = 512;
  public static final int GRAB_VERTICAL = 1024;
  public static final int FILL_VERTICAL = 1040;
  public static final int FILL_HORIZONTAL = 768;
  public static final int FILL_BOTH = 1808;
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

  public GridData()
  {
  }

  public GridData(int paramInt)
  {
    if ((paramInt & 0x2) != 0)
      this.verticalAlignment = 1;
    if ((paramInt & 0x4) != 0)
      this.verticalAlignment = 2;
    if ((paramInt & 0x10) != 0)
      this.verticalAlignment = 4;
    if ((paramInt & 0x8) != 0)
      this.verticalAlignment = 3;
    if ((paramInt & 0x20) != 0)
      this.horizontalAlignment = 1;
    if ((paramInt & 0x40) != 0)
      this.horizontalAlignment = 2;
    if ((paramInt & 0x100) != 0)
      this.horizontalAlignment = 4;
    if ((paramInt & 0x80) != 0)
      this.horizontalAlignment = 3;
    this.grabExcessHorizontalSpace = ((paramInt & 0x200) != 0);
    this.grabExcessVerticalSpace = ((paramInt & 0x400) != 0);
  }

  public GridData(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramInt1, paramInt2, paramBoolean1, paramBoolean2, 1, 1);
  }

  public GridData(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, int paramInt3, int paramInt4)
  {
    this.horizontalAlignment = paramInt1;
    this.verticalAlignment = paramInt2;
    this.grabExcessHorizontalSpace = paramBoolean1;
    this.grabExcessVerticalSpace = paramBoolean2;
    this.horizontalSpan = paramInt3;
    this.verticalSpan = paramInt4;
  }

  public GridData(int paramInt1, int paramInt2)
  {
    this.widthHint = paramInt1;
    this.heightHint = paramInt2;
  }

  void computeSize(Control paramControl, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if ((this.cacheWidth != -1) && (this.cacheHeight != -1))
      return;
    Point localPoint;
    if ((paramInt1 == this.widthHint) && (paramInt2 == this.heightHint))
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
    this.defaultWidth = (this.defaultHeight = -1);
    this.currentWidth = (this.currentHeight = -1);
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    String str1 = "";
    switch (this.horizontalAlignment)
    {
    case 4:
      str1 = "SWT.FILL";
      break;
    case 1:
      str1 = "SWT.BEGINNING";
      break;
    case 16384:
      str1 = "SWT.LEFT";
      break;
    case 16777224:
      str1 = "SWT.END";
      break;
    case 3:
      str1 = "GridData.END";
      break;
    case 131072:
      str1 = "SWT.RIGHT";
      break;
    case 16777216:
      str1 = "SWT.CENTER";
      break;
    case 2:
      str1 = "GridData.CENTER";
      break;
    default:
      str1 = "Undefined " + this.horizontalAlignment;
    }
    String str2 = "";
    switch (this.verticalAlignment)
    {
    case 4:
      str2 = "SWT.FILL";
      break;
    case 1:
      str2 = "SWT.BEGINNING";
      break;
    case 128:
      str2 = "SWT.TOP";
      break;
    case 16777224:
      str2 = "SWT.END";
      break;
    case 3:
      str2 = "GridData.END";
      break;
    case 1024:
      str2 = "SWT.BOTTOM";
      break;
    case 16777216:
      str2 = "SWT.CENTER";
      break;
    case 2:
      str2 = "GridData.CENTER";
      break;
    default:
      str2 = "Undefined " + this.verticalAlignment;
    }
    String str3 = getName() + " {";
    str3 = str3 + "horizontalAlignment=" + str1 + " ";
    if (this.horizontalIndent != 0)
      str3 = str3 + "horizontalIndent=" + this.horizontalIndent + " ";
    if (this.horizontalSpan != 1)
      str3 = str3 + "horizontalSpan=" + this.horizontalSpan + " ";
    if (this.grabExcessHorizontalSpace)
      str3 = str3 + "grabExcessHorizontalSpace=" + this.grabExcessHorizontalSpace + " ";
    if (this.widthHint != -1)
      str3 = str3 + "widthHint=" + this.widthHint + " ";
    if (this.minimumWidth != 0)
      str3 = str3 + "minimumWidth=" + this.minimumWidth + " ";
    str3 = str3 + "verticalAlignment=" + str2 + " ";
    if (this.verticalIndent != 0)
      str3 = str3 + "verticalIndent=" + this.verticalIndent + " ";
    if (this.verticalSpan != 1)
      str3 = str3 + "verticalSpan=" + this.verticalSpan + " ";
    if (this.grabExcessVerticalSpace)
      str3 = str3 + "grabExcessVerticalSpace=" + this.grabExcessVerticalSpace + " ";
    if (this.heightHint != -1)
      str3 = str3 + "heightHint=" + this.heightHint + " ";
    if (this.minimumHeight != 0)
      str3 = str3 + "minimumHeight=" + this.minimumHeight + " ";
    if (this.exclude)
      str3 = str3 + "exclude=" + this.exclude + " ";
    str3 = str3.trim();
    str3 = str3 + "}";
    return str3;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.GridData
 * JD-Core Version:    0.6.2
 */