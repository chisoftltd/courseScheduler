package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;

public class Bullet
{
  public int type;
  public StyleRange style;
  public String text;
  int[] linesIndices;
  int count;

  public Bullet(StyleRange paramStyleRange)
  {
    this(1, paramStyleRange);
  }

  public Bullet(int paramInt, StyleRange paramStyleRange)
  {
    if (paramStyleRange == null)
      SWT.error(4);
    if (paramStyleRange.metrics == null)
      SWT.error(4);
    this.type = paramInt;
    this.style = paramStyleRange;
  }

  void addIndices(int paramInt1, int paramInt2)
  {
    int i;
    if (this.linesIndices == null)
    {
      this.linesIndices = new int[paramInt2];
      this.count = paramInt2;
      for (i = 0; i < paramInt2; i++)
        this.linesIndices[i] = (paramInt1 + i);
    }
    else
    {
      for (i = 0; i < this.count; i++)
        if (paramInt1 <= this.linesIndices[i])
          break;
      for (int j = i; j < this.count; j++)
        if (paramInt1 + paramInt2 <= this.linesIndices[j])
          break;
      int k = i + paramInt2 + this.count - j;
      if (k > this.linesIndices.length)
      {
        int[] arrayOfInt = new int[k];
        System.arraycopy(this.linesIndices, 0, arrayOfInt, 0, this.count);
        this.linesIndices = arrayOfInt;
      }
      System.arraycopy(this.linesIndices, j, this.linesIndices, i + paramInt2, this.count - j);
      for (int m = 0; m < paramInt2; m++)
        this.linesIndices[(i + m)] = (paramInt1 + m);
      this.count = k;
    }
  }

  int indexOf(int paramInt)
  {
    for (int i = 0; i < this.count; i++)
      if (this.linesIndices[i] == paramInt)
        return i;
    return -1;
  }

  public int hashCode()
  {
    return this.style.hashCode() ^ this.type;
  }

  int[] removeIndices(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (this.count == 0)
      return null;
    if (paramInt1 > this.linesIndices[(this.count - 1)])
      return null;
    int i = paramInt1 + paramInt2;
    int j = paramInt3 - paramInt2;
    for (int k = 0; k < this.count; k++)
    {
      int m = this.linesIndices[k];
      if (paramInt1 <= m)
      {
        for (int n = k; n < this.count; n++)
          if (this.linesIndices[n] >= i)
            break;
        if (paramBoolean)
          for (int i1 = n; i1 < this.count; i1++)
            this.linesIndices[i1] += j;
        int[] arrayOfInt = new int[this.count - n];
        System.arraycopy(this.linesIndices, n, arrayOfInt, 0, this.count - n);
        System.arraycopy(this.linesIndices, n, this.linesIndices, k, this.count - n);
        this.count -= n - k;
        return arrayOfInt;
      }
    }
    for (k = 0; k < this.count; k++)
      this.linesIndices[k] += j;
    return null;
  }

  int size()
  {
    return this.count;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.Bullet
 * JD-Core Version:    0.6.2
 */