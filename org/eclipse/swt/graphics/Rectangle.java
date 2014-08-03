package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.SerializableCompatibility;

public final class Rectangle
  implements SerializableCompatibility
{
  public int x;
  public int y;
  public int width;
  public int height;
  static final long serialVersionUID = 3256439218279428914L;

  public Rectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.x = paramInt1;
    this.y = paramInt2;
    this.width = paramInt3;
    this.height = paramInt4;
  }

  public void add(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    int i = this.x < paramRectangle.x ? this.x : paramRectangle.x;
    int j = this.y < paramRectangle.y ? this.y : paramRectangle.y;
    int k = this.x + this.width;
    int m = paramRectangle.x + paramRectangle.width;
    int n = k > m ? k : m;
    k = this.y + this.height;
    m = paramRectangle.y + paramRectangle.height;
    int i1 = k > m ? k : m;
    this.x = i;
    this.y = j;
    this.width = (n - i);
    this.height = (i1 - j);
  }

  public boolean contains(int paramInt1, int paramInt2)
  {
    return (paramInt1 >= this.x) && (paramInt2 >= this.y) && (paramInt1 < this.x + this.width) && (paramInt2 < this.y + this.height);
  }

  public boolean contains(Point paramPoint)
  {
    if (paramPoint == null)
      SWT.error(4);
    return contains(paramPoint.x, paramPoint.y);
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Rectangle))
      return false;
    Rectangle localRectangle = (Rectangle)paramObject;
    return (localRectangle.x == this.x) && (localRectangle.y == this.y) && (localRectangle.width == this.width) && (localRectangle.height == this.height);
  }

  public int hashCode()
  {
    return this.x ^ this.y ^ this.width ^ this.height;
  }

  public void intersect(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    if (this == paramRectangle)
      return;
    int i = this.x > paramRectangle.x ? this.x : paramRectangle.x;
    int j = this.y > paramRectangle.y ? this.y : paramRectangle.y;
    int k = this.x + this.width;
    int m = paramRectangle.x + paramRectangle.width;
    int n = k < m ? k : m;
    k = this.y + this.height;
    m = paramRectangle.y + paramRectangle.height;
    int i1 = k < m ? k : m;
    this.x = (n < i ? 0 : i);
    this.y = (i1 < j ? 0 : j);
    this.width = (n < i ? 0 : n - i);
    this.height = (i1 < j ? 0 : i1 - j);
  }

  public Rectangle intersection(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    if (this == paramRectangle)
      return new Rectangle(this.x, this.y, this.width, this.height);
    int i = this.x > paramRectangle.x ? this.x : paramRectangle.x;
    int j = this.y > paramRectangle.y ? this.y : paramRectangle.y;
    int k = this.x + this.width;
    int m = paramRectangle.x + paramRectangle.width;
    int n = k < m ? k : m;
    k = this.y + this.height;
    m = paramRectangle.y + paramRectangle.height;
    int i1 = k < m ? k : m;
    return new Rectangle(n < i ? 0 : i, i1 < j ? 0 : j, n < i ? 0 : n - i, i1 < j ? 0 : i1 - j);
  }

  public boolean intersects(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return (paramInt1 < this.x + this.width) && (paramInt2 < this.y + this.height) && (paramInt1 + paramInt3 > this.x) && (paramInt2 + paramInt4 > this.y);
  }

  public boolean intersects(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    return (paramRectangle == this) || (intersects(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height));
  }

  public boolean isEmpty()
  {
    return (this.width <= 0) || (this.height <= 0);
  }

  public String toString()
  {
    return "Rectangle {" + this.x + ", " + this.y + ", " + this.width + ", " + this.height + "}";
  }

  public Rectangle union(Rectangle paramRectangle)
  {
    if (paramRectangle == null)
      SWT.error(4);
    int i = this.x < paramRectangle.x ? this.x : paramRectangle.x;
    int j = this.y < paramRectangle.y ? this.y : paramRectangle.y;
    int k = this.x + this.width;
    int m = paramRectangle.x + paramRectangle.width;
    int n = k > m ? k : m;
    k = this.y + this.height;
    m = paramRectangle.y + paramRectangle.height;
    int i1 = k > m ? k : m;
    return new Rectangle(i, j, n - i, i1 - j);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Rectangle
 * JD-Core Version:    0.6.2
 */