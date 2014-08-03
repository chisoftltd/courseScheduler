package org.eclipse.swt.browser;

import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Widget;

public class AuthenticationEvent extends TypedEvent
{
  public String location;
  public String user;
  public String password;
  public boolean doit = true;
  static final long serialVersionUID = -8322331206780057921L;

  public AuthenticationEvent(Widget paramWidget)
  {
    super(paramWidget);
  }

  public String toString()
  {
    String str = super.toString();
    return str.substring(0, str.length() - 1) + " name=" + this.user + " password=" + this.password + " location=" + this.location + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.AuthenticationEvent
 * JD-Core Version:    0.6.2
 */