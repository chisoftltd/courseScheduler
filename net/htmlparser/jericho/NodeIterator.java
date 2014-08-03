package net.htmlparser.jericho;

import java.util.Iterator;
import java.util.NoSuchElementException;

class NodeIterator
  implements Iterator<Segment>
{
  private final Segment segment;
  private final Source source;
  private int pos;
  private Tag nextTag;
  private CharacterReference characterReferenceAtCurrentPosition = null;
  private final boolean legacyIteratorCompatabilityMode = Source.LegacyIteratorCompatabilityMode;

  public NodeIterator(Segment paramSegment)
  {
    this.segment = paramSegment;
    this.source = paramSegment.source;
    if (paramSegment == this.source)
      this.source.fullSequentialParse();
    this.pos = paramSegment.begin;
    this.nextTag = this.source.getNextTag(this.pos);
    if ((this.nextTag != null) && (this.nextTag.begin >= paramSegment.end))
      this.nextTag = null;
  }

  public boolean hasNext()
  {
    return (this.pos < this.segment.end) || (this.nextTag != null);
  }

  public Segment next()
  {
    int i = this.pos;
    if (this.nextTag != null)
    {
      if (i < this.nextTag.begin)
        return nextNonTagSegment(i, this.nextTag.begin);
      Tag localTag = this.nextTag;
      this.nextTag = this.nextTag.getNextTag();
      if ((this.nextTag != null) && (this.nextTag.begin >= this.segment.end))
        this.nextTag = null;
      if (this.pos < localTag.end)
        this.pos = localTag.end;
      return localTag;
    }
    if (!hasNext())
      throw new NoSuchElementException();
    return nextNonTagSegment(i, this.segment.end);
  }

  private Segment nextNonTagSegment(int paramInt1, int paramInt2)
  {
    if (!this.legacyIteratorCompatabilityMode)
    {
      CharacterReference localCharacterReference1 = this.characterReferenceAtCurrentPosition;
      if (localCharacterReference1 != null)
      {
        this.characterReferenceAtCurrentPosition = null;
        this.pos = localCharacterReference1.end;
        return localCharacterReference1;
      }
      ParseText localParseText = this.source.getParseText();
      for (int i = localParseText.indexOf('&', paramInt1, paramInt2); i != -1; i = localParseText.indexOf('&', i + 1, paramInt2))
      {
        CharacterReference localCharacterReference2 = CharacterReference.construct(this.source, i, Config.UnterminatedCharacterReferenceSettings.ACCEPT_ALL);
        if (localCharacterReference2 != null)
        {
          if (i == paramInt1)
          {
            this.pos = localCharacterReference2.end;
            return localCharacterReference2;
          }
          this.pos = localCharacterReference2.begin;
          this.characterReferenceAtCurrentPosition = localCharacterReference2;
          return new Segment(this.source, paramInt1, this.pos);
        }
      }
    }
    return new Segment(this.source, paramInt1, this.pos = paramInt2);
  }

  public void skipToPos(int paramInt)
  {
    if (paramInt < this.pos)
      return;
    this.pos = paramInt;
    this.nextTag = this.source.getNextTag(paramInt);
  }

  public void remove()
  {
    throw new UnsupportedOperationException();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.NodeIterator
 * JD-Core Version:    0.6.2
 */