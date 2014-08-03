package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class Browser extends Composite
{
  WebBrowser webBrowser;
  int userStyle;
  boolean isClosing;
  static final String NO_INPUT_METHOD = "org.eclipse.swt.internal.gtk.noInputMethod";
  static final String PACKAGE_PREFIX = "org.eclipse.swt.browser.";
  static final String PROPERTY_USEWEBKITGTK = "org.eclipse.swt.browser.UseWebKitGTK";

  public Browser(Composite paramComposite, int paramInt)
  {
    super(checkParent(paramComposite), checkStyle(paramInt));
    this.userStyle = paramInt;
    String str1 = SWT.getPlatform();
    Display localDisplay = paramComposite.getDisplay();
    if ("gtk".equals(str1))
      localDisplay.setData("org.eclipse.swt.internal.gtk.noInputMethod", null);
    String[] arrayOfString = (String[])null;
    if ((paramInt & 0x8000) != 0)
    {
      arrayOfString = new String[] { "org.eclipse.swt.browser.Mozilla" };
    }
    else if (("win32".equals(str1)) || ("wpf".equals(str1)))
    {
      arrayOfString = new String[] { "org.eclipse.swt.browser.IE" };
    }
    else if ("motif".equals(str1))
    {
      arrayOfString = new String[] { "org.eclipse.swt.browser.Mozilla" };
    }
    else if ("gtk".equals(str1))
    {
      String str2 = System.getProperty("org.eclipse.swt.browser.UseWebKitGTK");
      if ((str2 != null) && (str2.equalsIgnoreCase("true")))
        arrayOfString = new String[] { "org.eclipse.swt.browser.WebKit", "org.eclipse.swt.browser.Mozilla" };
      else
        arrayOfString = new String[] { "org.eclipse.swt.browser.Mozilla" };
    }
    else if (("carbon".equals(str1)) || ("cocoa".equals(str1)))
    {
      arrayOfString = new String[] { "org.eclipse.swt.browser.Safari" };
    }
    else if ("photon".equals(str1))
    {
      arrayOfString = new String[] { "org.eclipse.swt.browser.Voyager" };
    }
    else
    {
      dispose();
      SWT.error(2);
    }
    for (int i = 0; i < arrayOfString.length; i++)
      try
      {
        Class localClass = Class.forName(arrayOfString[i]);
        this.webBrowser = ((WebBrowser)localClass.newInstance());
        if (this.webBrowser != null)
        {
          this.webBrowser.setBrowser(this);
          if (this.webBrowser.create(paramComposite, paramInt))
            return;
        }
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
      }
      catch (InstantiationException localInstantiationException)
      {
      }
    dispose();
    SWT.error(2);
  }

  static Composite checkParent(Composite paramComposite)
  {
    String str = SWT.getPlatform();
    if (!"gtk".equals(str))
      return paramComposite;
    if ((paramComposite != null) && (!paramComposite.isDisposed()))
    {
      Display localDisplay = paramComposite.getDisplay();
      if ((localDisplay != null) && (localDisplay.getThread() == Thread.currentThread()))
        localDisplay.setData("org.eclipse.swt.internal.gtk.noInputMethod", "true");
    }
    return paramComposite;
  }

  static int checkStyle(int paramInt)
  {
    String str = SWT.getPlatform();
    if ((paramInt & 0x8000) != 0)
    {
      if ("carbon".equals(str))
        return paramInt | 0x1000000;
      if ("motif".equals(str))
        return paramInt | 0x1000000;
      return paramInt;
    }
    if ("win32".equals(str))
      return paramInt & 0xFFFFF7FF;
    if ("motif".equals(str))
      return paramInt | 0x1000000;
    return paramInt;
  }

  protected void checkWidget()
  {
    super.checkWidget();
  }

  public static void clearSessions()
  {
    WebBrowser.clearSessions();
  }

  public static String getCookie(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      SWT.error(4);
    if (paramString2 == null)
      SWT.error(4);
    return WebBrowser.GetCookie(paramString1, paramString2);
  }

  public static boolean setCookie(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      SWT.error(4);
    if (paramString2 == null)
      SWT.error(4);
    return WebBrowser.SetCookie(paramString1, paramString2, true);
  }

  public void addAuthenticationListener(AuthenticationListener paramAuthenticationListener)
  {
    checkWidget();
    if (paramAuthenticationListener == null)
      SWT.error(4);
    this.webBrowser.addAuthenticationListener(paramAuthenticationListener);
  }

  public void addCloseWindowListener(CloseWindowListener paramCloseWindowListener)
  {
    checkWidget();
    if (paramCloseWindowListener == null)
      SWT.error(4);
    this.webBrowser.addCloseWindowListener(paramCloseWindowListener);
  }

  public void addLocationListener(LocationListener paramLocationListener)
  {
    checkWidget();
    if (paramLocationListener == null)
      SWT.error(4);
    this.webBrowser.addLocationListener(paramLocationListener);
  }

  public void addOpenWindowListener(OpenWindowListener paramOpenWindowListener)
  {
    checkWidget();
    if (paramOpenWindowListener == null)
      SWT.error(4);
    this.webBrowser.addOpenWindowListener(paramOpenWindowListener);
  }

  public void addProgressListener(ProgressListener paramProgressListener)
  {
    checkWidget();
    if (paramProgressListener == null)
      SWT.error(4);
    this.webBrowser.addProgressListener(paramProgressListener);
  }

  public void addStatusTextListener(StatusTextListener paramStatusTextListener)
  {
    checkWidget();
    if (paramStatusTextListener == null)
      SWT.error(4);
    this.webBrowser.addStatusTextListener(paramStatusTextListener);
  }

  public void addTitleListener(TitleListener paramTitleListener)
  {
    checkWidget();
    if (paramTitleListener == null)
      SWT.error(4);
    this.webBrowser.addTitleListener(paramTitleListener);
  }

  public void addVisibilityWindowListener(VisibilityWindowListener paramVisibilityWindowListener)
  {
    checkWidget();
    if (paramVisibilityWindowListener == null)
      SWT.error(4);
    this.webBrowser.addVisibilityWindowListener(paramVisibilityWindowListener);
  }

  public boolean back()
  {
    checkWidget();
    return this.webBrowser.back();
  }

  protected void checkSubclass()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (!str.substring(0, i + 1).equals("org.eclipse.swt.browser."))
      SWT.error(43);
  }

  public boolean execute(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    return this.webBrowser.execute(paramString);
  }

  public boolean close()
  {
    checkWidget();
    if (this.webBrowser.close())
    {
      this.isClosing = true;
      dispose();
      this.isClosing = false;
      return true;
    }
    return false;
  }

  public Object evaluate(String paramString)
    throws SWTException
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    return this.webBrowser.evaluate(paramString);
  }

  public boolean forward()
  {
    checkWidget();
    return this.webBrowser.forward();
  }

  public String getBrowserType()
  {
    checkWidget();
    return this.webBrowser.getBrowserType();
  }

  public boolean getJavascriptEnabled()
  {
    checkWidget();
    return this.webBrowser.jsEnabled;
  }

  public int getStyle()
  {
    return super.getStyle() | this.userStyle & 0x800;
  }

  public String getText()
  {
    checkWidget();
    return this.webBrowser.getText();
  }

  public String getUrl()
  {
    checkWidget();
    return this.webBrowser.getUrl();
  }

  public Object getWebBrowser()
  {
    checkWidget();
    return this.webBrowser.getWebBrowser();
  }

  public boolean isBackEnabled()
  {
    checkWidget();
    return this.webBrowser.isBackEnabled();
  }

  public boolean isFocusControl()
  {
    checkWidget();
    if (this.webBrowser.isFocusControl())
      return true;
    return super.isFocusControl();
  }

  public boolean isForwardEnabled()
  {
    checkWidget();
    return this.webBrowser.isForwardEnabled();
  }

  public void refresh()
  {
    checkWidget();
    this.webBrowser.refresh();
  }

  public void removeAuthenticationListener(AuthenticationListener paramAuthenticationListener)
  {
    checkWidget();
    if (paramAuthenticationListener == null)
      SWT.error(4);
    this.webBrowser.removeAuthenticationListener(paramAuthenticationListener);
  }

  public void removeCloseWindowListener(CloseWindowListener paramCloseWindowListener)
  {
    checkWidget();
    if (paramCloseWindowListener == null)
      SWT.error(4);
    this.webBrowser.removeCloseWindowListener(paramCloseWindowListener);
  }

  public void removeLocationListener(LocationListener paramLocationListener)
  {
    checkWidget();
    if (paramLocationListener == null)
      SWT.error(4);
    this.webBrowser.removeLocationListener(paramLocationListener);
  }

  public void removeOpenWindowListener(OpenWindowListener paramOpenWindowListener)
  {
    checkWidget();
    if (paramOpenWindowListener == null)
      SWT.error(4);
    this.webBrowser.removeOpenWindowListener(paramOpenWindowListener);
  }

  public void removeProgressListener(ProgressListener paramProgressListener)
  {
    checkWidget();
    if (paramProgressListener == null)
      SWT.error(4);
    this.webBrowser.removeProgressListener(paramProgressListener);
  }

  public void removeStatusTextListener(StatusTextListener paramStatusTextListener)
  {
    checkWidget();
    if (paramStatusTextListener == null)
      SWT.error(4);
    this.webBrowser.removeStatusTextListener(paramStatusTextListener);
  }

  public void removeTitleListener(TitleListener paramTitleListener)
  {
    checkWidget();
    if (paramTitleListener == null)
      SWT.error(4);
    this.webBrowser.removeTitleListener(paramTitleListener);
  }

  public void removeVisibilityWindowListener(VisibilityWindowListener paramVisibilityWindowListener)
  {
    checkWidget();
    if (paramVisibilityWindowListener == null)
      SWT.error(4);
    this.webBrowser.removeVisibilityWindowListener(paramVisibilityWindowListener);
  }

  public void setJavascriptEnabled(boolean paramBoolean)
  {
    checkWidget();
    this.webBrowser.jsEnabled = paramBoolean;
    this.webBrowser.jsEnabledChanged = true;
  }

  public boolean setText(String paramString)
  {
    checkWidget();
    return setText(paramString, true);
  }

  public boolean setText(String paramString, boolean paramBoolean)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    return this.webBrowser.setText(paramString, paramBoolean);
  }

  public boolean setUrl(String paramString)
  {
    checkWidget();
    return setUrl(paramString, null, null);
  }

  public boolean setUrl(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    checkWidget();
    if (paramString1 == null)
      SWT.error(4);
    return this.webBrowser.setUrl(paramString1, paramString2, paramArrayOfString);
  }

  public void stop()
  {
    checkWidget();
    this.webBrowser.stop();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.Browser
 * JD-Core Version:    0.6.2
 */