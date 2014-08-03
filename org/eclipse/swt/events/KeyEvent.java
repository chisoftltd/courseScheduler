package org.eclipse.swt.events;

import org.eclipse.swt.widgets.Event;

public class KeyEvent extends TypedEvent
{
  public char character;
  public int keyCode;
  public int keyLocation;
  public int stateMask;
  public boolean doit;
  static final long serialVersionUID = 3256442491011412789L;

  public KeyEvent(Event paramEvent)
  {
    super(paramEvent);
    this.character = paramEvent.character;
    this.keyCode = paramEvent.keyCode;
    this.keyLocation = paramEvent.keyLocation;
    this.stateMask = paramEvent.stateMask;
    this.doit = paramEvent.doit;
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " character='" + (this.character == 0 ? "\\0" : new StringBuffer().append(this.character).toString()) + "'" + " keyCode=" + this.keyCode + " keyLocation=" + this.keyLocation + " stateMask=" + this.stateMask + " doit=" + this.doit + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.events.KeyEvent
 * JD-Core Version:    0.6.2
 */