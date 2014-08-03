package org.eclipse.swt.custom;

import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.widgets.TypedListener;

class DefaultContent
  implements StyledTextContent
{
  private static final String LineDelimiter = System.getProperty("line.separator");
  Vector textListeners = new Vector();
  char[] textStore = new char[0];
  int gapStart = -1;
  int gapEnd = -1;
  int gapLine = -1;
  int highWatermark = 300;
  int lowWatermark = 50;
  int[][] lines = new int[50][2];
  int lineCount = 0;
  int expandExp = 1;
  int replaceExpandExp = 1;

  DefaultContent()
  {
    setText("");
  }

  void addLineIndex(int paramInt1, int paramInt2)
  {
    int i = this.lines.length;
    if (this.lineCount == i)
    {
      localObject = new int[i + Compatibility.pow2(this.expandExp)][2];
      System.arraycopy(this.lines, 0, localObject, 0, i);
      this.lines = ((int[][])localObject);
      this.expandExp += 1;
    }
    Object localObject = { paramInt1, paramInt2 };
    this.lines[this.lineCount] = localObject;
    this.lineCount += 1;
  }

  int[][] addLineIndex(int paramInt1, int paramInt2, int[][] paramArrayOfInt, int paramInt3)
  {
    int i = paramArrayOfInt.length;
    int[][] arrayOfInt = paramArrayOfInt;
    if (paramInt3 == i)
    {
      arrayOfInt = new int[i + Compatibility.pow2(this.replaceExpandExp)][2];
      this.replaceExpandExp += 1;
      System.arraycopy(paramArrayOfInt, 0, arrayOfInt, 0, i);
    }
    int[] arrayOfInt1 = { paramInt1, paramInt2 };
    arrayOfInt[paramInt3] = arrayOfInt1;
    return arrayOfInt;
  }

  public void addTextChangeListener(TextChangeListener paramTextChangeListener)
  {
    if (paramTextChangeListener == null)
      error(4);
    StyledTextListener localStyledTextListener = new StyledTextListener(paramTextChangeListener);
    this.textListeners.addElement(localStyledTextListener);
  }

  void adjustGap(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (paramInt1 == this.gapStart)
    {
      i = this.gapEnd - this.gapStart - paramInt2;
      if ((this.lowWatermark > i) || (i > this.highWatermark));
    }
    else if ((paramInt1 + paramInt2 == this.gapStart) && (paramInt2 < 0))
    {
      i = this.gapEnd - this.gapStart - paramInt2;
      if ((this.lowWatermark <= i) && (i <= this.highWatermark))
        return;
    }
    moveAndResizeGap(paramInt1, paramInt2, paramInt3);
  }

  void indexLines()
  {
    int i = 0;
    this.lineCount = 0;
    int j = this.textStore.length;
    for (int k = i; k < j; k++)
    {
      int m = this.textStore[k];
      if (m == 13)
      {
        if (k + 1 < j)
        {
          m = this.textStore[(k + 1)];
          if (m == 10)
            k++;
        }
        addLineIndex(i, k - i + 1);
        i = k + 1;
      }
      else if (m == 10)
      {
        addLineIndex(i, k - i + 1);
        i = k + 1;
      }
    }
    addLineIndex(i, k - i);
  }

  boolean isDelimiter(char paramChar)
  {
    if (paramChar == '\r')
      return true;
    return paramChar == '\n';
  }

  protected boolean isValidReplace(int paramInt1, int paramInt2, String paramString)
  {
    int i;
    int j;
    if (paramInt2 == 0)
    {
      if (paramInt1 == 0)
        return true;
      if (paramInt1 == getCharCount())
        return true;
      i = getTextRange(paramInt1 - 1, 1).charAt(0);
      if (i == 13)
      {
        j = getTextRange(paramInt1, 1).charAt(0);
        if (j == 10)
          return false;
      }
    }
    else
    {
      i = getTextRange(paramInt1, 1).charAt(0);
      if ((i == 10) && (paramInt1 != 0))
      {
        j = getTextRange(paramInt1 - 1, 1).charAt(0);
        if (j == 13)
          return false;
      }
      j = getTextRange(paramInt1 + paramInt2 - 1, 1).charAt(0);
      if ((j == 13) && (paramInt1 + paramInt2 != getCharCount()))
      {
        int k = getTextRange(paramInt1 + paramInt2, 1).charAt(0);
        if (k == 10)
          return false;
      }
    }
    return true;
  }

  int[][] indexLines(int paramInt1, int paramInt2, int paramInt3)
  {
    int[][] arrayOfInt1 = new int[paramInt3][2];
    int i = 0;
    int j = 0;
    this.replaceExpandExp = 1;
    for (int k = i; k < paramInt2; k++)
    {
      int m = k + paramInt1;
      if ((m < this.gapStart) || (m >= this.gapEnd))
      {
        int n = this.textStore[m];
        if (n == 13)
        {
          if (m + 1 < this.textStore.length)
          {
            n = this.textStore[(m + 1)];
            if (n == 10)
              k++;
          }
          arrayOfInt1 = addLineIndex(i, k - i + 1, arrayOfInt1, j);
          j++;
          i = k + 1;
        }
        else if (n == 10)
        {
          arrayOfInt1 = addLineIndex(i, k - i + 1, arrayOfInt1, j);
          j++;
          i = k + 1;
        }
      }
    }
    int[][] arrayOfInt2 = new int[j + 1][2];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, j);
    int[] arrayOfInt = { i, k - i };
    arrayOfInt2[j] = arrayOfInt;
    return arrayOfInt2;
  }

  void insert(int paramInt, String paramString)
  {
    if (paramString.length() == 0)
      return;
    int i = getLineAtOffset(paramInt);
    int j = paramString.length();
    int k = paramInt == getCharCount() ? 1 : 0;
    adjustGap(paramInt, j, i);
    int m = getOffsetAtLine(i);
    int n = getPhysicalLine(i).length();
    if (j > 0)
    {
      this.gapStart += j;
      for (int i1 = 0; i1 < paramString.length(); i1++)
        this.textStore[(paramInt + i1)] = paramString.charAt(i1);
    }
    int[][] arrayOfInt = indexLines(m, n, 10);
    int i2 = arrayOfInt.length - 1;
    if (arrayOfInt[i2][1] == 0)
      if (k != 0)
        i2++;
      else
        i2--;
    expandLinesBy(i2);
    for (int i3 = this.lineCount - 1; i3 > i; i3--)
      this.lines[(i3 + i2)] = this.lines[i3];
    for (i3 = 0; i3 < i2; i3++)
    {
      arrayOfInt[i3][0] += m;
      this.lines[(i + i3)] = arrayOfInt[i3];
    }
    if (i2 < arrayOfInt.length)
    {
      arrayOfInt[i2][0] += m;
      this.lines[(i + i2)] = arrayOfInt[i2];
    }
    this.lineCount += i2;
    this.gapLine = getLineAtPhysicalOffset(this.gapStart);
  }

  void moveAndResizeGap(int paramInt1, int paramInt2, int paramInt3)
  {
    char[] arrayOfChar = (char[])null;
    int i = this.gapEnd - this.gapStart;
    int j;
    if (paramInt2 > 0)
      j = this.highWatermark + paramInt2;
    else
      j = this.lowWatermark - paramInt2;
    if (gapExists())
    {
      this.lines[this.gapLine][1] -= i;
      for (k = this.gapLine + 1; k < this.lineCount; k++)
        this.lines[k][0] -= i;
    }
    if (j < 0)
    {
      if (i > 0)
      {
        arrayOfChar = new char[this.textStore.length - i];
        System.arraycopy(this.textStore, 0, arrayOfChar, 0, this.gapStart);
        System.arraycopy(this.textStore, this.gapEnd, arrayOfChar, this.gapStart, arrayOfChar.length - this.gapStart);
        this.textStore = arrayOfChar;
      }
      this.gapStart = (this.gapEnd = paramInt1);
      return;
    }
    arrayOfChar = new char[this.textStore.length + (j - i)];
    int k = paramInt1;
    int m = k + j;
    int n;
    if (i == 0)
    {
      System.arraycopy(this.textStore, 0, arrayOfChar, 0, k);
      System.arraycopy(this.textStore, k, arrayOfChar, m, arrayOfChar.length - m);
    }
    else if (k < this.gapStart)
    {
      n = this.gapStart - k;
      System.arraycopy(this.textStore, 0, arrayOfChar, 0, k);
      System.arraycopy(this.textStore, k, arrayOfChar, m, n);
      System.arraycopy(this.textStore, this.gapEnd, arrayOfChar, m + n, this.textStore.length - this.gapEnd);
    }
    else
    {
      n = k - this.gapStart;
      System.arraycopy(this.textStore, 0, arrayOfChar, 0, this.gapStart);
      System.arraycopy(this.textStore, this.gapEnd, arrayOfChar, this.gapStart, n);
      System.arraycopy(this.textStore, this.gapEnd + n, arrayOfChar, m, arrayOfChar.length - m);
    }
    this.textStore = arrayOfChar;
    this.gapStart = k;
    this.gapEnd = m;
    if (gapExists())
    {
      this.gapLine = paramInt3;
      n = this.gapEnd - this.gapStart;
      this.lines[this.gapLine][1] += n;
      for (int i1 = this.gapLine + 1; i1 < this.lineCount; i1++)
        this.lines[i1][0] += n;
    }
  }

  int lineCount(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return 0;
    int i = 0;
    int j = 0;
    int k = paramInt1;
    if (k >= this.gapStart)
      k += this.gapEnd - this.gapStart;
    while (j < paramInt2)
    {
      if ((k < this.gapStart) || (k >= this.gapEnd))
      {
        int m = this.textStore[k];
        if (m == 13)
        {
          if (k + 1 < this.textStore.length)
          {
            m = this.textStore[(k + 1)];
            if (m == 10)
            {
              k++;
              j++;
            }
          }
          i++;
        }
        else if (m == 10)
        {
          i++;
        }
        j++;
      }
      k++;
    }
    return i;
  }

  int lineCount(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    for (int k = 0; k < j; k++)
    {
      int m = paramString.charAt(k);
      if (m == 13)
      {
        if ((k + 1 < j) && (paramString.charAt(k + 1) == '\n'))
          k++;
        i++;
      }
      else if (m == 10)
      {
        i++;
      }
    }
    return i;
  }

  public int getCharCount()
  {
    int i = this.gapEnd - this.gapStart;
    return this.textStore.length - i;
  }

  public String getLine(int paramInt)
  {
    if ((paramInt >= this.lineCount) || (paramInt < 0))
      error(5);
    int i = this.lines[paramInt][0];
    int j = this.lines[paramInt][1];
    int k = i + j - 1;
    if ((gapExists()) && (k >= this.gapStart))
    {
      if (i < this.gapEnd);
    }
    else
    {
      while ((j - 1 >= 0) && (isDelimiter(this.textStore[(i + j - 1)])))
        j--;
      return new String(this.textStore, i, j);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    int m = this.gapEnd - this.gapStart;
    localStringBuffer.append(this.textStore, i, this.gapStart - i);
    localStringBuffer.append(this.textStore, this.gapEnd, j - m - (this.gapStart - i));
    for (j = localStringBuffer.length(); (j - 1 >= 0) && (isDelimiter(localStringBuffer.charAt(j - 1))); j--);
    return localStringBuffer.toString().substring(0, j);
  }

  public String getLineDelimiter()
  {
    return LineDelimiter;
  }

  String getFullLine(int paramInt)
  {
    int i = this.lines[paramInt][0];
    int j = this.lines[paramInt][1];
    int k = i + j - 1;
    if ((!gapExists()) || (k < this.gapStart) || (i >= this.gapEnd))
      return new String(this.textStore, i, j);
    StringBuffer localStringBuffer = new StringBuffer();
    int m = this.gapEnd - this.gapStart;
    localStringBuffer.append(this.textStore, i, this.gapStart - i);
    localStringBuffer.append(this.textStore, this.gapEnd, j - m - (this.gapStart - i));
    return localStringBuffer.toString();
  }

  String getPhysicalLine(int paramInt)
  {
    int i = this.lines[paramInt][0];
    int j = this.lines[paramInt][1];
    return getPhysicalText(i, j);
  }

  public int getLineCount()
  {
    return this.lineCount;
  }

  public int getLineAtOffset(int paramInt)
  {
    if ((paramInt > getCharCount()) || (paramInt < 0))
      error(5);
    int i;
    if (paramInt < this.gapStart)
      i = paramInt;
    else
      i = paramInt + (this.gapEnd - this.gapStart);
    if (this.lineCount > 0)
    {
      j = this.lineCount - 1;
      if (i == this.lines[j][0] + this.lines[j][1])
        return j;
    }
    int j = this.lineCount;
    int k = -1;
    int m = this.lineCount;
    while (j - k > 1)
    {
      m = (j + k) / 2;
      int n = this.lines[m][0];
      int i1 = n + this.lines[m][1] - 1;
      if (i <= n)
      {
        j = m;
      }
      else
      {
        if (i <= i1)
        {
          j = m;
          break;
        }
        k = m;
      }
    }
    return j;
  }

  int getLineAtPhysicalOffset(int paramInt)
  {
    int i = this.lineCount;
    int j = -1;
    int k = this.lineCount;
    while (i - j > 1)
    {
      k = (i + j) / 2;
      int m = this.lines[k][0];
      int n = m + this.lines[k][1] - 1;
      if (paramInt <= m)
      {
        i = k;
      }
      else
      {
        if (paramInt <= n)
        {
          i = k;
          break;
        }
        j = k;
      }
    }
    return i;
  }

  public int getOffsetAtLine(int paramInt)
  {
    if (paramInt == 0)
      return 0;
    if ((paramInt >= this.lineCount) || (paramInt < 0))
      error(5);
    int i = this.lines[paramInt][0];
    if (i > this.gapEnd)
      return i - (this.gapEnd - this.gapStart);
    return i;
  }

  void expandLinesBy(int paramInt)
  {
    int i = this.lines.length;
    if (i - this.lineCount >= paramInt)
      return;
    int[][] arrayOfInt = new int[i + Math.max(10, paramInt)][2];
    System.arraycopy(this.lines, 0, arrayOfInt, 0, i);
    this.lines = arrayOfInt;
  }

  void error(int paramInt)
  {
    SWT.error(paramInt);
  }

  boolean gapExists()
  {
    return this.gapStart != this.gapEnd;
  }

  String getPhysicalText(int paramInt1, int paramInt2)
  {
    return new String(this.textStore, paramInt1, paramInt2);
  }

  public String getTextRange(int paramInt1, int paramInt2)
  {
    if (this.textStore == null)
      return "";
    if (paramInt2 == 0)
      return "";
    int i = paramInt1 + paramInt2;
    if ((!gapExists()) || (i < this.gapStart))
      return new String(this.textStore, paramInt1, paramInt2);
    if (this.gapStart < paramInt1)
    {
      int j = this.gapEnd - this.gapStart;
      return new String(this.textStore, paramInt1 + j, paramInt2);
    }
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(this.textStore, paramInt1, this.gapStart - paramInt1);
    localStringBuffer.append(this.textStore, this.gapEnd, i - this.gapStart);
    return localStringBuffer.toString();
  }

  public void removeTextChangeListener(TextChangeListener paramTextChangeListener)
  {
    if (paramTextChangeListener == null)
      error(4);
    for (int i = 0; i < this.textListeners.size(); i++)
    {
      TypedListener localTypedListener = (TypedListener)this.textListeners.elementAt(i);
      if (localTypedListener.getEventListener() == paramTextChangeListener)
      {
        this.textListeners.removeElementAt(i);
        break;
      }
    }
  }

  public void replaceTextRange(int paramInt1, int paramInt2, String paramString)
  {
    if (!isValidReplace(paramInt1, paramInt2, paramString))
      SWT.error(5);
    StyledTextEvent localStyledTextEvent = new StyledTextEvent(this);
    localStyledTextEvent.type = 3003;
    localStyledTextEvent.start = paramInt1;
    localStyledTextEvent.replaceLineCount = lineCount(paramInt1, paramInt2);
    localStyledTextEvent.text = paramString;
    localStyledTextEvent.newLineCount = lineCount(paramString);
    localStyledTextEvent.replaceCharCount = paramInt2;
    localStyledTextEvent.newCharCount = paramString.length();
    sendTextEvent(localStyledTextEvent);
    delete(paramInt1, paramInt2, localStyledTextEvent.replaceLineCount + 1);
    insert(paramInt1, paramString);
    localStyledTextEvent = new StyledTextEvent(this);
    localStyledTextEvent.type = 3006;
    sendTextEvent(localStyledTextEvent);
  }

  void sendTextEvent(StyledTextEvent paramStyledTextEvent)
  {
    for (int i = 0; i < this.textListeners.size(); i++)
      ((StyledTextListener)this.textListeners.elementAt(i)).handleEvent(paramStyledTextEvent);
  }

  public void setText(String paramString)
  {
    this.textStore = paramString.toCharArray();
    this.gapStart = -1;
    this.gapEnd = -1;
    this.expandExp = 1;
    indexLines();
    StyledTextEvent localStyledTextEvent = new StyledTextEvent(this);
    localStyledTextEvent.type = 3004;
    localStyledTextEvent.text = "";
    sendTextEvent(localStyledTextEvent);
  }

  void delete(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == 0)
      return;
    int i = getLineAtOffset(paramInt1);
    int j = getOffsetAtLine(i);
    int k = getLineAtOffset(paramInt1 + paramInt2);
    String str = "";
    int m = 0;
    if (paramInt1 + paramInt2 < getCharCount())
    {
      str = getTextRange(paramInt1 + paramInt2 - 1, 2);
      if ((str.charAt(0) == '\r') && (str.charAt(1) == '\n'))
        m = 1;
    }
    adjustGap(paramInt1 + paramInt2, -paramInt2, i);
    int[][] arrayOfInt = indexLines(paramInt1, paramInt2 + (this.gapEnd - this.gapStart), paramInt3);
    if (paramInt1 + paramInt2 == this.gapStart)
      this.gapStart -= paramInt2;
    else
      this.gapEnd += paramInt2;
    int n = paramInt1;
    int i1 = 0;
    while ((n < this.textStore.length) && (i1 == 0))
    {
      if ((n < this.gapStart) || (n >= this.gapEnd))
      {
        char c = this.textStore[n];
        if (isDelimiter(c))
        {
          if ((n + 1 < this.textStore.length) && (c == '\r') && (this.textStore[(n + 1)] == '\n'))
            n++;
          i1 = 1;
        }
      }
      n++;
    }
    this.lines[i][1] = (paramInt1 - j + (n - paramInt1));
    int i2 = arrayOfInt.length - 1;
    if (m != 0)
      i2--;
    for (int i3 = k + 1; i3 < this.lineCount; i3++)
      this.lines[(i3 - i2)] = this.lines[i3];
    this.lineCount -= i2;
    this.gapLine = getLineAtPhysicalOffset(this.gapStart);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.DefaultContent
 * JD-Core Version:    0.6.2
 */