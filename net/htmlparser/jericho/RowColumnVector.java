package net.htmlparser.jericho;

import java.util.ArrayList;

public final class RowColumnVector
{
  private final int row;
  private final int column;
  private final int pos;
  private static final RowColumnVector FIRST = new RowColumnVector(1, 1, 0);
  private static final RowColumnVector[] STREAMED = new RowColumnVector[0];

  private RowColumnVector(int paramInt1, int paramInt2, int paramInt3)
  {
    this.row = paramInt1;
    this.column = paramInt2;
    this.pos = paramInt3;
  }

  private RowColumnVector(int paramInt)
  {
    this(-1, -1, paramInt);
  }

  public int getRow()
  {
    return this.row;
  }

  public int getColumn()
  {
    return this.column;
  }

  public int getPos()
  {
    return this.pos;
  }

  public String toString()
  {
    return appendTo(new StringBuilder(20)).toString();
  }

  StringBuilder appendTo(StringBuilder paramStringBuilder)
  {
    if (this.row != -1)
      return paramStringBuilder.append("(r").append(this.row).append(",c").append(this.column).append(",p").append(this.pos).append(')');
    return paramStringBuilder.append("(p").append(this.pos).append(')');
  }

  static RowColumnVector[] getCacheArray(Source paramSource)
  {
    if (paramSource.isStreamed())
      return STREAMED;
    int i = paramSource.end - 1;
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    localArrayList.add(FIRST);
    int k = 1;
    while (j <= i)
    {
      int m = paramSource.charAt(j);
      if ((m == 10) || ((m == 13) && ((j == i) || (paramSource.charAt(j + 1) != '\n'))))
        localArrayList.add(new RowColumnVector(++k, 1, j + 1));
      j++;
    }
    return (RowColumnVector[])localArrayList.toArray(new RowColumnVector[localArrayList.size()]);
  }

  static RowColumnVector get(RowColumnVector[] paramArrayOfRowColumnVector, int paramInt)
  {
    if (paramArrayOfRowColumnVector == STREAMED)
      return new RowColumnVector(paramInt);
    int i = 0;
    int j = paramArrayOfRowColumnVector.length - 1;
    while (true)
    {
      int k = i + j >> 1;
      RowColumnVector localRowColumnVector = paramArrayOfRowColumnVector[k];
      if (localRowColumnVector.pos < paramInt)
      {
        if (k == j)
          return new RowColumnVector(localRowColumnVector.row, paramInt - localRowColumnVector.pos + 1, paramInt);
        i = k + 1;
      }
      else if (localRowColumnVector.pos > paramInt)
      {
        j = k - 1;
      }
      else
      {
        return localRowColumnVector;
      }
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.RowColumnVector
 * JD-Core Version:    0.6.2
 */