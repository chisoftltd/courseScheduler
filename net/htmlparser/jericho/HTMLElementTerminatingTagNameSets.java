package net.htmlparser.jericho;

import java.util.Set;

final class HTMLElementTerminatingTagNameSets
{
  public final Set<String> TerminatingStartTagNameSet;
  public final Set<String> TerminatingEndTagNameSet;
  public final Set<String> NonterminatingElementNameSet;

  public HTMLElementTerminatingTagNameSets(Set<String> paramSet1, Set<String> paramSet2, Set<String> paramSet3)
  {
    this.TerminatingStartTagNameSet = paramSet1;
    this.TerminatingEndTagNameSet = paramSet2;
    this.NonterminatingElementNameSet = paramSet3;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.HTMLElementTerminatingTagNameSets
 * JD-Core Version:    0.6.2
 */