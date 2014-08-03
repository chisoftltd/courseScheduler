package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.nsICertificateDialogs;
import org.eclipse.swt.internal.mozilla.nsIDOMWindow;
import org.eclipse.swt.internal.mozilla.nsIServiceManager;
import org.eclipse.swt.internal.mozilla.nsIWebBrowser;
import org.eclipse.swt.internal.mozilla.nsIX509Cert;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

class PromptDialog extends Dialog
{
  PromptDialog(Shell paramShell, int paramInt)
  {
    super(paramShell, paramInt);
  }

  PromptDialog(Shell paramShell)
  {
    this(paramShell, 0);
  }

  void alertCheck(String paramString1, String paramString2, String paramString3, int[] paramArrayOfInt)
  {
    Shell localShell1 = getParent();
    Shell localShell2 = new Shell(localShell1, 67680);
    if (paramString1 != null)
      localShell2.setText(paramString1);
    GridLayout localGridLayout = new GridLayout();
    localShell2.setLayout(localGridLayout);
    Label localLabel = new Label(localShell2, 64);
    localLabel.setText(paramString2);
    GridData localGridData = new GridData();
    Monitor localMonitor = localShell1.getMonitor();
    int i = localMonitor.getBounds().width * 2 / 3;
    int j = localLabel.computeSize(-1, -1).x;
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localLabel.setLayoutData(localGridData);
    Button localButton1 = paramString3 != null ? new Button(localShell2, 32) : null;
    if (localButton1 != null)
    {
      localButton1.setText(paramString3);
      localButton1.setSelection(paramArrayOfInt[0] != 0);
      localGridData = new GridData();
      localGridData.horizontalAlignment = 1;
      localButton1.setLayoutData(localGridData);
    }
    Button localButton2 = new Button(localShell2, 8);
    localButton2.setText(SWT.getMessage("SWT_OK"));
    localGridData = new GridData();
    localGridData.horizontalAlignment = 2;
    localButton2.setLayoutData(localGridData);
    localButton2.addListener(13, new Listener()
    {
      private final Button val$checkButton;
      private final int[] val$checkValue;
      private final Shell val$shell;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (this.val$checkButton != null)
          this.val$checkValue[0] = (this.val$checkButton.getSelection() ? 1 : 0);
        this.val$shell.close();
      }
    });
    localShell2.pack();
    localShell2.open();
    Display localDisplay = localShell1.getDisplay();
    while (!localShell2.isDisposed())
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
  }

  boolean invalidCert(Browser paramBrowser, String paramString, String[] paramArrayOfString, nsIX509Cert paramnsIX509Cert)
  {
    Shell localShell1 = getParent();
    Display localDisplay = localShell1.getDisplay();
    Monitor localMonitor = localShell1.getMonitor();
    int i = localMonitor.getBounds().width * 2 / 3;
    Shell localShell2 = new Shell(localShell1, 67680);
    localShell2.setText(Compatibility.getMessage("SWT_InvalidCert_Title"));
    localShell2.setLayout(new GridLayout());
    Composite localComposite1 = new Composite(localShell2, 0);
    localComposite1.setLayout(new GridLayout(2, false));
    Image localImage = localDisplay.getSystemImage(8);
    new Label(localComposite1, 0).setImage(localImage);
    Text localText = new Text(localComposite1, 64);
    localText.setLayoutData(new GridData(4, 16777216, true, false));
    localText.setEditable(false);
    localText.setBackground(localShell2.getBackground());
    localText.setText(paramString);
    int j = localComposite1.computeSize(-1, -1).x;
    GridData localGridData = new GridData();
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localComposite1.setLayoutData(localGridData);
    StyledText localStyledText = new StyledText(localShell2, 64);
    localStyledText.setMargins(30, 0, 30, 0);
    localStyledText.setEditable(false);
    localStyledText.setBackground(localShell2.getBackground());
    for (int k = 0; k < paramArrayOfString.length; k++)
      localStyledText.append(paramArrayOfString[k] + '\n');
    StyleRange localStyleRange = new StyleRange();
    localStyleRange.metrics = new GlyphMetrics(0, 0, 30);
    Bullet localBullet = new Bullet(localStyleRange);
    localStyledText.setLineBullet(0, paramArrayOfString.length, localBullet);
    j = localStyledText.computeSize(-1, -1).x;
    localGridData = new GridData();
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localStyledText.setLayoutData(localGridData);
    localText = new Text(localShell2, 4);
    localText.setEditable(false);
    localText.setBackground(localShell2.getBackground());
    localText.setText(Compatibility.getMessage("SWT_InvalidCert_Connect"));
    new Label(localShell2, 0);
    Browser localBrowser = new Browser(localShell2, paramBrowser.getStyle());
    localGridData = new GridData();
    localGridData.exclude = true;
    localBrowser.setLayoutData(localGridData);
    Composite localComposite2 = new Composite(localShell2, 0);
    localComposite2.setLayout(new GridLayout(3, true));
    localComposite2.setLayoutData(new GridData(16777216, 16777216, false, false));
    Button localButton1 = new Button(localComposite2, 8);
    localButton1.setLayoutData(new GridData(4, 4, false, false));
    localButton1.setText(Compatibility.getMessage("SWT_ViewCertificate"));
    localButton1.addListener(13, new Listener()
    {
      private final Browser val$localBrowser;
      private final nsIX509Cert val$cert;
      private final Browser val$browser;

      public void handleEvent(Event paramAnonymousEvent)
      {
        int[] arrayOfInt = new int[1];
        int i = XPCOM.NS_GetServiceManager(arrayOfInt);
        if (i != 0)
          Mozilla.error(i);
        if (arrayOfInt[0] == 0)
          Mozilla.error(-2147467262);
        nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt[0]);
        arrayOfInt[0] = 0;
        byte[] arrayOfByte = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/nsCertificateDialogs;1", true);
        i = localnsIServiceManager.GetServiceByContractID(arrayOfByte, nsICertificateDialogs.NS_ICERTIFICATEDIALOGS_IID, arrayOfInt);
        if (i != 0)
          Mozilla.error(i);
        if (arrayOfInt[0] == 0)
          Mozilla.error(-2147467262);
        localnsIServiceManager.Release();
        nsICertificateDialogs localnsICertificateDialogs = new nsICertificateDialogs(arrayOfInt[0]);
        arrayOfInt[0] = 0;
        PromptDialog.3 local3 = new PromptDialog.3(this, this.val$browser);
        local3.run();
        i = ((Mozilla)this.val$localBrowser.webBrowser).webBrowser.GetContentDOMWindow(arrayOfInt);
        if (i != 0)
          Mozilla.error(i);
        if (arrayOfInt[0] == 0)
          Mozilla.error(-2147467262);
        nsIDOMWindow localnsIDOMWindow = new nsIDOMWindow(arrayOfInt[0]);
        arrayOfInt[0] = 0;
        i = localnsICertificateDialogs.ViewCert(localnsIDOMWindow.getAddress(), this.val$cert.getAddress());
        this.val$browser.getDisplay().timerExec(-1, local3);
        localnsIDOMWindow.Release();
        localnsICertificateDialogs.Release();
      }
    });
    Button localButton2 = new Button(localComposite2, 8);
    localButton2.setLayoutData(new GridData(4, 4, false, false));
    localButton2.setText(Compatibility.getMessage("SWT_OK"));
    Button localButton3 = new Button(localComposite2, 8);
    localButton3.setLayoutData(new GridData(4, 4, false, false));
    localButton3.setText(Compatibility.getMessage("SWT_Cancel"));
    boolean[] arrayOfBoolean = new boolean[1];
    Listener local4 = new Listener()
    {
      private final Shell val$shell;
      private final boolean[] val$result;
      private final Button val$okButton;

      public void handleEvent(Event paramAnonymousEvent)
      {
        this.val$shell.dispose();
        this.val$result[0] = (paramAnonymousEvent.widget == this.val$okButton ? 1 : false);
      }
    };
    localButton2.addListener(13, local4);
    localButton3.addListener(13, local4);
    localButton3.setFocus();
    localShell2.setDefaultButton(localButton3);
    localShell2.pack();
    localShell2.open();
    while (!localShell2.isDisposed())
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
    return arrayOfBoolean[0];
  }

  void confirmEx(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Shell localShell1 = getParent();
    Shell localShell2 = new Shell(localShell1, 67680);
    localShell2.setText(paramString1);
    GridLayout localGridLayout1 = new GridLayout();
    localShell2.setLayout(localGridLayout1);
    Label localLabel = new Label(localShell2, 64);
    localLabel.setText(paramString2);
    GridData localGridData = new GridData();
    Monitor localMonitor = localShell1.getMonitor();
    int i = localMonitor.getBounds().width * 2 / 3;
    int j = localLabel.computeSize(-1, -1).x;
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localLabel.setLayoutData(localGridData);
    Button[] arrayOfButton = new Button[4];
    Listener local5 = new Listener()
    {
      private final Button[] val$buttons;
      private final int[] val$checkValue;
      private final int[] val$result;
      private final Shell val$shell;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (this.val$buttons[0] != null)
          this.val$checkValue[0] = (this.val$buttons[0].getSelection() ? 1 : 0);
        Widget localWidget = paramAnonymousEvent.widget;
        for (int i = 1; i < this.val$buttons.length; i++)
          if (localWidget == this.val$buttons[i])
          {
            this.val$result[0] = (i - 1);
            break;
          }
        this.val$shell.close();
      }
    };
    if (paramString3 != null)
    {
      arrayOfButton[0] = new Button(localShell2, 32);
      arrayOfButton[0].setText(paramString3);
      arrayOfButton[0].setSelection(paramArrayOfInt1[0] != 0);
      localGridData = new GridData();
      localGridData.horizontalAlignment = 1;
      arrayOfButton[0].setLayoutData(localGridData);
    }
    Composite localComposite = new Composite(localShell2, 0);
    localGridData = new GridData();
    localGridData.horizontalAlignment = 2;
    localComposite.setLayoutData(localGridData);
    GridLayout localGridLayout2 = new GridLayout();
    localGridLayout2.makeColumnsEqualWidth = true;
    localComposite.setLayout(localGridLayout2);
    int k = 0;
    if (paramString4 != null)
    {
      arrayOfButton[1] = new Button(localComposite, 8);
      arrayOfButton[1].setText(paramString4);
      arrayOfButton[1].addListener(13, local5);
      arrayOfButton[1].setLayoutData(new GridData(768));
      k++;
    }
    if (paramString5 != null)
    {
      arrayOfButton[2] = new Button(localComposite, 8);
      arrayOfButton[2].setText(paramString5);
      arrayOfButton[2].addListener(13, local5);
      arrayOfButton[2].setLayoutData(new GridData(768));
      k++;
    }
    if (paramString6 != null)
    {
      arrayOfButton[3] = new Button(localComposite, 8);
      arrayOfButton[3].setText(paramString6);
      arrayOfButton[3].addListener(13, local5);
      arrayOfButton[3].setLayoutData(new GridData(768));
      k++;
    }
    localGridLayout2.numColumns = k;
    Button localButton = arrayOfButton[(paramInt + 1)];
    if (localButton != null)
      localShell2.setDefaultButton(localButton);
    localShell2.pack();
    localShell2.open();
    Display localDisplay = localShell1.getDisplay();
    while (!localShell2.isDisposed())
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
  }

  void prompt(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Shell localShell1 = getParent();
    Shell localShell2 = new Shell(localShell1, 67680);
    if (paramString1 != null)
      localShell2.setText(paramString1);
    GridLayout localGridLayout = new GridLayout();
    localShell2.setLayout(localGridLayout);
    Label localLabel = new Label(localShell2, 64);
    localLabel.setText(paramString2);
    GridData localGridData = new GridData();
    Monitor localMonitor = localShell1.getMonitor();
    int i = localMonitor.getBounds().width * 2 / 3;
    int j = localLabel.computeSize(-1, -1).x;
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localLabel.setLayoutData(localGridData);
    Text localText = new Text(localShell2, 2048);
    if (paramArrayOfString[0] != null)
      localText.setText(paramArrayOfString[0]);
    localGridData = new GridData();
    j = localText.computeSize(-1, -1).x;
    if (j > i)
      localGridData.widthHint = i;
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localText.setLayoutData(localGridData);
    Button[] arrayOfButton = new Button[3];
    Listener local6 = new Listener()
    {
      private final Button[] val$buttons;
      private final int[] val$checkValue;
      private final String[] val$value;
      private final Text val$valueText;
      private final int[] val$result;
      private final Shell val$shell;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (this.val$buttons[0] != null)
          this.val$checkValue[0] = (this.val$buttons[0].getSelection() ? 1 : 0);
        this.val$value[0] = this.val$valueText.getText();
        this.val$result[0] = (paramAnonymousEvent.widget == this.val$buttons[1] ? 1 : 0);
        this.val$shell.close();
      }
    };
    if (paramString3 != null)
    {
      arrayOfButton[0] = new Button(localShell2, 32);
      arrayOfButton[0].setText(paramString3);
      arrayOfButton[0].setSelection(paramArrayOfInt1[0] != 0);
      localGridData = new GridData();
      localGridData.horizontalAlignment = 1;
      arrayOfButton[0].setLayoutData(localGridData);
    }
    Composite localComposite = new Composite(localShell2, 0);
    localGridData = new GridData();
    localGridData.horizontalAlignment = 2;
    localComposite.setLayoutData(localGridData);
    localComposite.setLayout(new GridLayout(2, true));
    arrayOfButton[1] = new Button(localComposite, 8);
    arrayOfButton[1].setText(SWT.getMessage("SWT_OK"));
    arrayOfButton[1].setLayoutData(new GridData(768));
    arrayOfButton[1].addListener(13, local6);
    arrayOfButton[2] = new Button(localComposite, 8);
    arrayOfButton[2].setText(SWT.getMessage("SWT_Cancel"));
    arrayOfButton[2].setLayoutData(new GridData(768));
    arrayOfButton[2].addListener(13, local6);
    localShell2.pack();
    localShell2.open();
    Display localDisplay = localShell1.getDisplay();
    while (!localShell2.isDisposed())
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
  }

  void promptUsernameAndPassword(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString1, String[] paramArrayOfString2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    Shell localShell1 = getParent();
    Shell localShell2 = new Shell(localShell1, 67680);
    localShell2.setText(paramString1);
    GridLayout localGridLayout = new GridLayout();
    localShell2.setLayout(localGridLayout);
    Label localLabel1 = new Label(localShell2, 64);
    localLabel1.setText(paramString2);
    GridData localGridData = new GridData();
    Monitor localMonitor = localShell1.getMonitor();
    int i = localMonitor.getBounds().width * 2 / 3;
    int j = localLabel1.computeSize(-1, -1).x;
    localGridData.widthHint = Math.min(j, i);
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localLabel1.setLayoutData(localGridData);
    Label localLabel2 = new Label(localShell2, 0);
    localLabel2.setText(SWT.getMessage("SWT_Username"));
    Text localText1 = new Text(localShell2, 2048);
    if (paramArrayOfString1[0] != null)
      localText1.setText(paramArrayOfString1[0]);
    localGridData = new GridData();
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localText1.setLayoutData(localGridData);
    Label localLabel3 = new Label(localShell2, 0);
    localLabel3.setText(SWT.getMessage("SWT_Password"));
    Text localText2 = new Text(localShell2, 4196352);
    if (paramArrayOfString2[0] != null)
      localText2.setText(paramArrayOfString2[0]);
    localGridData = new GridData();
    localGridData.horizontalAlignment = 4;
    localGridData.grabExcessHorizontalSpace = true;
    localText2.setLayoutData(localGridData);
    Button[] arrayOfButton = new Button[3];
    Listener local7 = new Listener()
    {
      private final Button[] val$buttons;
      private final int[] val$checkValue;
      private final String[] val$user;
      private final Text val$userText;
      private final String[] val$pass;
      private final Text val$passwordText;
      private final int[] val$result;
      private final Shell val$shell;

      public void handleEvent(Event paramAnonymousEvent)
      {
        if (this.val$buttons[0] != null)
          this.val$checkValue[0] = (this.val$buttons[0].getSelection() ? 1 : 0);
        this.val$user[0] = this.val$userText.getText();
        this.val$pass[0] = this.val$passwordText.getText();
        this.val$result[0] = (paramAnonymousEvent.widget == this.val$buttons[1] ? 1 : 0);
        this.val$shell.close();
      }
    };
    if (paramString3 != null)
    {
      arrayOfButton[0] = new Button(localShell2, 32);
      arrayOfButton[0].setText(paramString3);
      arrayOfButton[0].setSelection(paramArrayOfInt1[0] != 0);
      localGridData = new GridData();
      localGridData.horizontalAlignment = 1;
      arrayOfButton[0].setLayoutData(localGridData);
    }
    Composite localComposite = new Composite(localShell2, 0);
    localGridData = new GridData();
    localGridData.horizontalAlignment = 2;
    localComposite.setLayoutData(localGridData);
    localComposite.setLayout(new GridLayout(2, true));
    arrayOfButton[1] = new Button(localComposite, 8);
    arrayOfButton[1].setText(SWT.getMessage("SWT_OK"));
    arrayOfButton[1].setLayoutData(new GridData(768));
    arrayOfButton[1].addListener(13, local7);
    arrayOfButton[2] = new Button(localComposite, 8);
    arrayOfButton[2].setText(SWT.getMessage("SWT_Cancel"));
    arrayOfButton[2].setLayoutData(new GridData(768));
    arrayOfButton[2].addListener(13, local7);
    localShell2.setDefaultButton(arrayOfButton[1]);
    localShell2.pack();
    localShell2.open();
    Display localDisplay = localShell1.getDisplay();
    while (!localShell2.isDisposed())
      if (!localDisplay.readAndDispatch())
        localDisplay.sleep();
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.PromptDialog
 * JD-Core Version:    0.6.2
 */