package org.eclipse.swt.browser;

import org.eclipse.swt.internal.SWTEventListener;

public abstract interface AuthenticationListener extends SWTEventListener
{
  public abstract void authenticate(AuthenticationEvent paramAuthenticationEvent);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.AuthenticationListener
 * JD-Core Version:    0.6.2
 */