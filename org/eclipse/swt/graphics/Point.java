package org.eclipse.swt.graphics;

import org.eclipse.swt.internal.SerializableCompatibility;

public final class Point
  implements SerializableCompatibility
{
  public int x;
  public int y;
  static final long serialVersionUID = 3257002163938146354L;

  public Point(int paramInt1, int paramInt2)
  {
    this.x = paramInt1;
    this.y = paramInt2;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this)
      return true;
    if (!(paramObject instanceof Point))
      return false;
    Point localPoint = (Point)paramObject;
    return (localPoint.x == this.x) && (localPoint.y == this.y);
  }

  public int hashCode()
  {
    return this.x ^ this.y;
  }

  public String toString()
  {
    return "Point {" + this.x + ", " + this.y + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Point
 * JD-Core Version:    0.6.2
 */