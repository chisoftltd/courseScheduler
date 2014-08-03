package net.htmlparser.jericho;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class OutputDocument
  implements CharStreamSource
{
  private CharSequence sourceText;
  private ArrayList<OutputSegment> outputSegments = new ArrayList();

  public OutputDocument(Source paramSource)
  {
    if (paramSource == null)
      throw new IllegalArgumentException("source argument must not be null");
    this.sourceText = paramSource;
  }

  public OutputDocument(Segment paramSegment)
  {
    if (paramSegment == null)
      throw new IllegalArgumentException("segment argument must not be null");
    Source localSource = paramSegment.source;
    this.sourceText = localSource;
    if (paramSegment.begin > 0)
      remove(new Segment(localSource, 0, paramSegment.begin));
    if (paramSegment.end < localSource.end)
      remove(new Segment(localSource, paramSegment.end, localSource.end));
  }

  OutputDocument(ParseText paramParseText)
  {
    this.sourceText = paramParseText;
  }

  public CharSequence getSourceText()
  {
    return this.sourceText;
  }

  public void remove(Segment paramSegment)
  {
    register(new RemoveOutputSegment(paramSegment));
  }

  public void remove(Collection<? extends Segment> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Segment localSegment = (Segment)localIterator.next();
      remove(localSegment);
    }
  }

  public void insert(int paramInt, CharSequence paramCharSequence)
  {
    register(new StringOutputSegment(paramInt, paramInt, paramCharSequence));
  }

  public void replace(Segment paramSegment, CharSequence paramCharSequence)
  {
    replace(paramSegment.getBegin(), paramSegment.getEnd(), paramCharSequence);
  }

  public void replace(int paramInt1, int paramInt2, CharSequence paramCharSequence)
  {
    register(new StringOutputSegment(paramInt1, paramInt2, paramCharSequence));
  }

  public void replace(int paramInt1, int paramInt2, char paramChar)
  {
    register(new CharOutputSegment(paramInt1, paramInt2, paramChar));
  }

  public void replace(FormControl paramFormControl)
  {
    paramFormControl.replaceInOutputDocument(this);
  }

  public void replace(FormFields paramFormFields)
  {
    paramFormFields.replaceInOutputDocument(this);
  }

  public Map<String, String> replace(Attributes paramAttributes, boolean paramBoolean)
  {
    AttributesOutputSegment localAttributesOutputSegment = new AttributesOutputSegment(paramAttributes, paramBoolean);
    register(localAttributesOutputSegment);
    return localAttributesOutputSegment.getMap();
  }

  public void replace(Attributes paramAttributes, Map<String, String> paramMap)
  {
    register(new AttributesOutputSegment(paramAttributes, paramMap));
  }

  public void replaceWithSpaces(int paramInt1, int paramInt2)
  {
    register(new BlankOutputSegment(paramInt1, paramInt2));
  }

  public void register(OutputSegment paramOutputSegment)
  {
    this.outputSegments.add(paramOutputSegment);
  }

  public void writeTo(Writer paramWriter)
    throws IOException
  {
    try
    {
      appendTo(paramWriter);
    }
    finally
    {
      paramWriter.flush();
    }
  }

  public void writeTo(Writer paramWriter, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      appendTo(paramWriter, paramInt1, paramInt2);
    }
    finally
    {
      paramWriter.flush();
    }
  }

  public void appendTo(Appendable paramAppendable)
    throws IOException
  {
    appendTo(paramAppendable, 0, this.sourceText.length());
  }

  public void appendTo(Appendable paramAppendable, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.outputSegments.isEmpty())
    {
      paramAppendable.append(this.sourceText, paramInt1, paramInt2);
      return;
    }
    int i = paramInt1;
    Collections.sort(this.outputSegments, OutputSegment.COMPARATOR);
    Iterator localIterator = this.outputSegments.iterator();
    while (localIterator.hasNext())
    {
      OutputSegment localOutputSegment = (OutputSegment)localIterator.next();
      if ((localOutputSegment.getEnd() >= i) && ((localOutputSegment.getEnd() != i) || (localOutputSegment.getBegin() >= i)))
      {
        if ((localOutputSegment.getBegin() > paramInt2) || ((localOutputSegment.getBegin() == paramInt2) && (localOutputSegment.getEnd() > paramInt2)))
          break;
        if (localOutputSegment.getBegin() > i)
          paramAppendable.append(this.sourceText, i, localOutputSegment.getBegin());
        if ((localOutputSegment.getBegin() < i) && ((localOutputSegment instanceof BlankOutputSegment)))
        {
          int j = localOutputSegment.getEnd();
          while (i < j)
          {
            paramAppendable.append(' ');
            i++;
          }
        }
        else
        {
          localOutputSegment.appendTo(paramAppendable);
          i = localOutputSegment.getEnd();
        }
      }
    }
    if (i < paramInt2)
      paramAppendable.append(this.sourceText, i, paramInt2);
  }

  public long getEstimatedMaximumOutputLength()
  {
    long l = this.sourceText.length();
    Iterator localIterator = this.outputSegments.iterator();
    while (localIterator.hasNext())
    {
      OutputSegment localOutputSegment = (OutputSegment)localIterator.next();
      int i = localOutputSegment.getEnd() - localOutputSegment.getBegin();
      l += localOutputSegment.getEstimatedMaximumOutputLength() - i;
    }
    return l >= 0L ? l : -1L;
  }

  public String toString()
  {
    return CharStreamSourceUtil.toString(this);
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = getRegisteredOutputSegments().iterator();
    while (localIterator.hasNext())
    {
      OutputSegment localOutputSegment = (OutputSegment)localIterator.next();
      if ((localOutputSegment instanceof BlankOutputSegment))
        localStringBuilder.append("Replace with Spaces: ");
      else if ((localOutputSegment instanceof RemoveOutputSegment))
        localStringBuilder.append("Remove: ");
      else
        localStringBuilder.append("Replace: ");
      if ((this.sourceText instanceof Source))
      {
        localObject = (Source)this.sourceText;
        localStringBuilder.append('(');
        ((Source)localObject).getRowColumnVector(localOutputSegment.getBegin()).appendTo(localStringBuilder);
        localStringBuilder.append('-');
        ((Source)localObject).getRowColumnVector(localOutputSegment.getEnd()).appendTo(localStringBuilder);
        localStringBuilder.append(')');
      }
      else
      {
        localStringBuilder.append("(p").append(localOutputSegment.getBegin()).append("-p").append(localOutputSegment.getEnd()).append(')');
      }
      localStringBuilder.append(' ');
      Object localObject = localOutputSegment.toString();
      if (((String)localObject).length() <= 20)
        localStringBuilder.append((String)localObject);
      else
        localStringBuilder.append(((String)localObject).substring(0, 20)).append("...");
      localStringBuilder.append(Config.NewLine);
    }
    return localStringBuilder.toString();
  }

  public List<OutputSegment> getRegisteredOutputSegments()
  {
    Collections.sort(this.outputSegments, OutputSegment.COMPARATOR);
    return this.outputSegments;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.OutputDocument
 * JD-Core Version:    0.6.2
 */