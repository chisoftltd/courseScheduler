package net.htmlparser.jericho;

import java.util.Comparator;

final class OutputSegmentComparator
  implements Comparator<OutputSegment>
{
  public int compare(OutputSegment paramOutputSegment1, OutputSegment paramOutputSegment2)
  {
    if (paramOutputSegment1.getBegin() < paramOutputSegment2.getBegin())
      return -1;
    if (paramOutputSegment1.getBegin() > paramOutputSegment2.getBegin())
      return 1;
    if (paramOutputSegment1.getEnd() < paramOutputSegment2.getEnd())
      return -1;
    if (paramOutputSegment1.getEnd() > paramOutputSegment2.getEnd())
      return 1;
    return 0;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.OutputSegmentComparator
 * JD-Core Version:    0.6.2
 */