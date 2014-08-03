package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.BufferOverflowException;
import java.nio.CharBuffer;

final class StreamedText
  implements CharSequence
{
  private final Reader reader;
  private char[] buffer;
  private boolean expandableBuffer;
  private int bufferBegin = 0;
  private int readerPos = 0;
  private int minRequiredBufferBegin = 0;
  private int end = 2147483647;
  public static int INITIAL_EXPANDABLE_BUFFER_SIZE = 8192;

  public StreamedText(Reader paramReader, char[] paramArrayOfChar)
  {
    this.reader = paramReader;
    setBuffer(paramArrayOfChar);
  }

  public StreamedText(Reader paramReader)
  {
    this(paramReader, null);
  }

  private StreamedText(char[] paramArrayOfChar, int paramInt)
  {
    this.reader = null;
    this.buffer = paramArrayOfChar;
    this.expandableBuffer = false;
    this.end = paramInt;
    this.readerPos = 2147483647;
  }

  public StreamedText(char[] paramArrayOfChar)
  {
    this(paramArrayOfChar, paramArrayOfChar.length);
  }

  public StreamedText(CharBuffer paramCharBuffer)
  {
    this(paramCharBuffer.array(), paramCharBuffer.length());
  }

  public StreamedText(CharSequence paramCharSequence)
  {
    this(toCharArray(paramCharSequence));
  }

  public StreamedText setBuffer(char[] paramArrayOfChar)
  {
    if (paramArrayOfChar != null)
    {
      this.buffer = paramArrayOfChar;
      this.expandableBuffer = false;
    }
    else
    {
      this.buffer = new char[INITIAL_EXPANDABLE_BUFFER_SIZE];
      this.expandableBuffer = true;
    }
    return this;
  }

  public boolean hasExpandableBuffer()
  {
    return this.expandableBuffer;
  }

  public char charAt(int paramInt)
  {
    if (paramInt >= this.readerPos)
      readToPosition(paramInt);
    checkPos(paramInt);
    return this.buffer[(paramInt - this.bufferBegin)];
  }

  public void setMinRequiredBufferBegin(int paramInt)
  {
    if (paramInt < this.bufferBegin)
      throw new IllegalArgumentException("Cannot set minimum required buffer begin to already discarded position " + paramInt);
    this.minRequiredBufferBegin = paramInt;
  }

  public int getMinRequiredBufferBegin()
  {
    return this.minRequiredBufferBegin;
  }

  public int length()
  {
    if (this.end == 2147483647)
      throw new IllegalStateException("Length of streamed text cannot be determined until end of file has been reached");
    return this.end;
  }

  public int getEnd()
  {
    return this.end;
  }

  private void prepareBufferRange(int paramInt1, int paramInt2)
  {
    int i = paramInt2 - 1;
    if (i > this.readerPos)
      readToPosition(i);
    checkPos(paramInt1);
    if (paramInt2 > this.end)
      throw new IndexOutOfBoundsException();
  }

  public void writeTo(Writer paramWriter, int paramInt1, int paramInt2)
    throws IOException
  {
    prepareBufferRange(paramInt1, paramInt2);
    paramWriter.write(this.buffer, paramInt1 - this.bufferBegin, paramInt2 - paramInt1);
  }

  public String substring(int paramInt1, int paramInt2)
  {
    prepareBufferRange(paramInt1, paramInt2);
    return new String(this.buffer, paramInt1 - this.bufferBegin, paramInt2 - paramInt1);
  }

  public CharSequence subSequence(int paramInt1, int paramInt2)
  {
    return getCharBuffer(paramInt1, paramInt2);
  }

  public CharBuffer getCharBuffer(int paramInt1, int paramInt2)
  {
    prepareBufferRange(paramInt1, paramInt2);
    return CharBuffer.wrap(this.buffer, paramInt1 - this.bufferBegin, paramInt2 - paramInt1);
  }

  public String toString()
  {
    throw new UnsupportedOperationException("Streamed text can not be converted to a string");
  }

  public String getDebugInfo()
  {
    return "Buffer size: \"" + this.buffer.length + "\", bufferBegin=" + this.bufferBegin + ", minRequiredBufferBegin=" + this.minRequiredBufferBegin + ", readerPos=" + this.readerPos;
  }

  public char[] getBuffer()
  {
    return this.buffer;
  }

  public int getBufferBegin()
  {
    return this.bufferBegin;
  }

  private void checkPos(int paramInt)
  {
    if (paramInt < this.bufferBegin)
      throw new IllegalStateException("StreamedText position " + paramInt + " has been discarded");
    if (paramInt >= this.end)
      throw new IndexOutOfBoundsException();
  }

  public int getBufferOverflowPosition()
  {
    return this.minRequiredBufferBegin + this.buffer.length;
  }

  private void readToPosition(int paramInt)
  {
    try
    {
      if (paramInt >= this.bufferBegin + this.buffer.length)
      {
        if (paramInt >= this.minRequiredBufferBegin + this.buffer.length)
        {
          if (!this.expandableBuffer)
            throw new BufferOverflowException();
          expandBuffer(paramInt - this.minRequiredBufferBegin + 1);
        }
        discardUsedText();
      }
      while (this.readerPos <= paramInt)
      {
        int i = this.reader.read(this.buffer, this.readerPos - this.bufferBegin, this.bufferBegin + this.buffer.length - this.readerPos);
        if (i == -1)
        {
          this.end = this.readerPos;
          break;
        }
        this.readerPos += i;
      }
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  private void expandBuffer(int paramInt)
    throws IOException
  {
    int i = this.buffer.length * 2;
    if (i < paramInt)
      i = paramInt;
    char[] arrayOfChar = new char[i];
    shiftBuffer(this.buffer, arrayOfChar);
    this.buffer = arrayOfChar;
  }

  private void discardUsedText()
    throws IOException
  {
    if (this.minRequiredBufferBegin == this.bufferBegin)
      return;
    shiftBuffer(this.buffer, this.buffer);
  }

  private void shiftBuffer(char[] paramArrayOfChar1, char[] paramArrayOfChar2)
    throws IOException
  {
    int i = this.minRequiredBufferBegin - this.bufferBegin;
    int j = this.readerPos - this.bufferBegin;
    for (int k = i; k < j; k++)
      paramArrayOfChar2[(k - i)] = paramArrayOfChar1[k];
    this.bufferBegin = this.minRequiredBufferBegin;
    while (this.readerPos < this.bufferBegin)
    {
      long l = this.reader.skip(this.bufferBegin - this.readerPos);
      if (l == 0L)
      {
        this.end = this.readerPos;
        break;
      }
      this.readerPos = ((int)(this.readerPos + l));
    }
  }

  String getCurrentBufferContent()
  {
    return substring(this.bufferBegin, Math.min(this.end, this.readerPos));
  }

  private static char[] toCharArray(CharSequence paramCharSequence)
  {
    if ((paramCharSequence instanceof String))
      return ((String)paramCharSequence).toCharArray();
    char[] arrayOfChar = new char[paramCharSequence.length()];
    for (int i = 0; i < arrayOfChar.length; i++)
      arrayOfChar[i] = paramCharSequence.charAt(i);
    return arrayOfChar;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StreamedText
 * JD-Core Version:    0.6.2
 */