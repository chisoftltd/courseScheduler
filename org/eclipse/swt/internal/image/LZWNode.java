package org.eclipse.swt.internal.image;

final class LZWNode
{
  public LZWNode left;
  public LZWNode right;
  public LZWNode children;
  public int code;
  public int prefix;
  public int suffix;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.LZWNode
 * JD-Core Version:    0.6.2
 */