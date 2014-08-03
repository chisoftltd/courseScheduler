package org.eclipse.swt.browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.mozilla.XPCOM;
import org.eclipse.swt.internal.mozilla.XPCOMObject;
import org.eclipse.swt.internal.mozilla.nsEmbedString;
import org.eclipse.swt.internal.mozilla.nsIAuthInformation;
import org.eclipse.swt.internal.mozilla.nsIChannel;
import org.eclipse.swt.internal.mozilla.nsID;
import org.eclipse.swt.internal.mozilla.nsIDOMWindow;
import org.eclipse.swt.internal.mozilla.nsIMemory;
import org.eclipse.swt.internal.mozilla.nsIPromptService;
import org.eclipse.swt.internal.mozilla.nsIPromptService2;
import org.eclipse.swt.internal.mozilla.nsIServiceManager;
import org.eclipse.swt.internal.mozilla.nsISupports;
import org.eclipse.swt.internal.mozilla.nsIURI;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

class PromptService2
{
  XPCOMObject supports;
  XPCOMObject promptService;
  XPCOMObject promptService2;
  int refCount = 0;
  static final String[] certErrorCodes = { "ssl_error_bad_cert_domain", "sec_error_ca_cert_invalid", "sec_error_expired_certificate", "sec_error_expired_issuer_certificate", "sec_error_inadequate_key_usage", "sec_error_unknown_issuer", "sec_error_untrusted_cert", "sec_error_untrusted_issuer" };

  PromptService2()
  {
    createCOMInterfaces();
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  void createCOMInterfaces()
  {
    this.supports = new XPCOMObject(new int[] { 2 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Release();
      }
    };
    this.promptService = new XPCOMObject(new int[] { 2, 0, 0, 3, 5, 4, 6, 10, 7, 8, 7, 7 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Alert(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AlertCheck(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Confirm(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.ConfirmCheck(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.ConfirmEx(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7], paramAnonymousArrayOfInt[8], paramAnonymousArrayOfInt[9]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Prompt(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.PromptUsernameAndPassword(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.PromptPassword(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Select(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }
    };
    this.promptService2 = new XPCOMObject(new int[] { 2, 0, 0, 3, 5, 4, 6, 10, 7, 8, 7, 7, 7, 9 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Alert(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AlertCheck(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Confirm(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.ConfirmCheck(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.ConfirmEx(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7], paramAnonymousArrayOfInt[8], paramAnonymousArrayOfInt[9]);
      }

      public int method8(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Prompt(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method9(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.PromptUsernameAndPassword(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7]);
      }

      public int method10(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.PromptPassword(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method11(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.Select(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method12(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.PromptAuth(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6]);
      }

      public int method13(int[] paramAnonymousArrayOfInt)
      {
        return PromptService2.this.AsyncPromptAuth(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2], paramAnonymousArrayOfInt[3], paramAnonymousArrayOfInt[4], paramAnonymousArrayOfInt[5], paramAnonymousArrayOfInt[6], paramAnonymousArrayOfInt[7], paramAnonymousArrayOfInt[8]);
      }
    };
  }

  void disposeCOMInterfaces()
  {
    if (this.supports != null)
    {
      this.supports.dispose();
      this.supports = null;
    }
    if (this.promptService != null)
    {
      this.promptService.dispose();
      this.promptService = null;
    }
    if (this.promptService2 != null)
    {
      this.promptService2.dispose();
      this.promptService2 = null;
    }
  }

  int getAddress()
  {
    return this.promptService2.getAddress();
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) || (paramInt2 == 0))
      return -2147467262;
    nsID localnsID = new nsID();
    XPCOM.memmove(localnsID, paramInt1, 16);
    if (localnsID.Equals(nsISupports.NS_ISUPPORTS_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.supports.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIPromptService.NS_IPROMPTSERVICE_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.promptService.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    if (localnsID.Equals(nsIPromptService2.NS_IPROMPTSERVICE2_IID))
    {
      XPCOM.memmove(paramInt2, new int[] { this.promptService2.getAddress() }, C.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    XPCOM.memmove(paramInt2, new int[1], C.PTR_SIZEOF);
    return -2147467262;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
      disposeCOMInterfaces();
    return this.refCount;
  }

  Browser getBrowser(int paramInt)
  {
    if (paramInt == 0)
      return null;
    nsIDOMWindow localnsIDOMWindow = new nsIDOMWindow(paramInt);
    return Mozilla.findBrowser(localnsIDOMWindow);
  }

  String getLabel(int paramInt1, int paramInt2, int paramInt3)
  {
    String str = null;
    int i = (paramInt1 & 255 * paramInt2) / paramInt2;
    switch (i)
    {
    case 2:
      str = SWT.getMessage("SWT_Cancel");
      break;
    case 4:
      str = SWT.getMessage("SWT_No");
      break;
    case 1:
      str = SWT.getMessage("SWT_OK");
      break;
    case 5:
      str = SWT.getMessage("SWT_Save");
      break;
    case 3:
      str = SWT.getMessage("SWT_Yes");
      break;
    case 127:
      int j = XPCOM.strlen_PRUnichar(paramInt3);
      char[] arrayOfChar = new char[j];
      XPCOM.memmove(arrayOfChar, paramInt3, j * 2);
      str = new String(arrayOfChar);
    }
    return str;
  }

  int Alert(int paramInt1, int paramInt2, int paramInt3)
  {
    Browser localBrowser = getBrowser(paramInt1);
    int i = XPCOM.strlen_PRUnichar(paramInt2);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt2, i * 2);
    String str1 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt3);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt3, i * 2);
    String str2 = new String(arrayOfChar);
    if (localBrowser != null)
      for (int j = 0; j < certErrorCodes.length; j++)
        if (str2.indexOf(certErrorCodes[j]) != -1)
        {
          localObject = (Mozilla)localBrowser.webBrowser;
          ((Mozilla)localObject).isRetrievingBadCert = true;
          localBrowser.setUrl(((Mozilla)localObject).lastNavigateURL);
          return 0;
        }
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    Object localObject = new MessageBox(localShell, 40);
    ((MessageBox)localObject).setText(str1);
    ((MessageBox)localObject).setMessage(str2);
    ((MessageBox)localObject).open();
    return 0;
  }

  int AlertCheck(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    Browser localBrowser = getBrowser(paramInt1);
    int i = XPCOM.strlen_PRUnichar(paramInt2);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt2, i * 2);
    String str1 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt3);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt3, i * 2);
    String str2 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt4);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt4, i * 2);
    String str3 = new String(arrayOfChar);
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    PromptDialog localPromptDialog = new PromptDialog(localShell);
    int[] arrayOfInt = new int[1];
    if (paramInt5 != 0)
      XPCOM.memmove(arrayOfInt, paramInt5, 4);
    localPromptDialog.alertCheck(str1, str2, str3, arrayOfInt);
    if (paramInt5 != 0)
      XPCOM.memmove(paramInt5, arrayOfInt, 4);
    return 0;
  }

  int AsyncPromptAuth(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9)
  {
    return -2147467263;
  }

  int Confirm(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Browser localBrowser = getBrowser(paramInt1);
    if ((localBrowser != null) && (((Mozilla)localBrowser.webBrowser).ignoreAllMessages))
    {
      XPCOM.memmove(paramInt4, new int[] { 1 }, 4);
      return 0;
    }
    int i = XPCOM.strlen_PRUnichar(paramInt2);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt2, i * 2);
    String str1 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt3);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt3, i * 2);
    String str2 = new String(arrayOfChar);
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    MessageBox localMessageBox = new MessageBox(localShell, 292);
    localMessageBox.setText(str1);
    localMessageBox.setMessage(str2);
    int j = localMessageBox.open();
    int[] arrayOfInt = { j == 32 ? 1 : 0 };
    XPCOM.memmove(paramInt4, arrayOfInt, 4);
    return 0;
  }

  int ConfirmCheck(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    return -2147467263;
  }

  int ConfirmEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    Browser localBrowser = getBrowser(paramInt1);
    int i = XPCOM.strlen_PRUnichar(paramInt2);
    char[] arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt2, i * 2);
    String str1 = new String(arrayOfChar);
    i = XPCOM.strlen_PRUnichar(paramInt3);
    arrayOfChar = new char[i];
    XPCOM.memmove(arrayOfChar, paramInt3, i * 2);
    String str2 = new String(arrayOfChar);
    String str3 = null;
    if (paramInt8 != 0)
    {
      i = XPCOM.strlen_PRUnichar(paramInt8);
      arrayOfChar = new char[i];
      XPCOM.memmove(arrayOfChar, paramInt8, i * 2);
      str3 = new String(arrayOfChar);
    }
    String str4 = getLabel(paramInt4, 1, paramInt5);
    String str5 = getLabel(paramInt4, 256, paramInt6);
    String str6 = getLabel(paramInt4, 65536, paramInt7);
    int j = 0;
    if ((paramInt4 & 0x1000000) != 0)
      j = 1;
    else if ((paramInt4 & 0x2000000) != 0)
      j = 2;
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    PromptDialog localPromptDialog = new PromptDialog(localShell);
    int[] arrayOfInt1 = new int[1];
    int[] arrayOfInt2 = new int[1];
    if (paramInt9 != 0)
      XPCOM.memmove(arrayOfInt1, paramInt9, 4);
    localPromptDialog.confirmEx(str1, str2, str3, str4, str5, str6, j, arrayOfInt1, arrayOfInt2);
    if (paramInt9 != 0)
      XPCOM.memmove(paramInt9, arrayOfInt1, 4);
    XPCOM.memmove(paramInt10, arrayOfInt2, 4);
    return 0;
  }

  int Prompt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    Browser localBrowser = getBrowser(paramInt1);
    String str1 = null;
    String str3 = null;
    String[] arrayOfString = new String[1];
    if (paramInt2 != 0)
    {
      i = XPCOM.strlen_PRUnichar(paramInt2);
      arrayOfChar1 = new char[i];
      XPCOM.memmove(arrayOfChar1, paramInt2, i * 2);
      str1 = new String(arrayOfChar1);
    }
    int i = XPCOM.strlen_PRUnichar(paramInt3);
    char[] arrayOfChar1 = new char[i];
    XPCOM.memmove(arrayOfChar1, paramInt3, i * 2);
    String str2 = new String(arrayOfChar1);
    int[] arrayOfInt1 = new int[1];
    XPCOM.memmove(arrayOfInt1, paramInt4, C.PTR_SIZEOF);
    if (arrayOfInt1[0] != 0)
    {
      i = XPCOM.strlen_PRUnichar(arrayOfInt1[0]);
      arrayOfChar1 = new char[i];
      XPCOM.memmove(arrayOfChar1, arrayOfInt1[0], i * 2);
      arrayOfString[0] = new String(arrayOfChar1);
    }
    if (paramInt5 != 0)
    {
      i = XPCOM.strlen_PRUnichar(paramInt5);
      if (i > 0)
      {
        arrayOfChar1 = new char[i];
        XPCOM.memmove(arrayOfChar1, paramInt5, i * 2);
        str3 = new String(arrayOfChar1);
      }
    }
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    PromptDialog localPromptDialog = new PromptDialog(localShell);
    int[] arrayOfInt2 = new int[1];
    int[] arrayOfInt3 = new int[1];
    if (paramInt6 != 0)
      XPCOM.memmove(arrayOfInt2, paramInt6, 4);
    localPromptDialog.prompt(str1, str2, str3, arrayOfString, arrayOfInt2, arrayOfInt3);
    XPCOM.memmove(paramInt7, arrayOfInt3, 4);
    if ((arrayOfInt3[0] == 1) && (arrayOfString[0] != null))
    {
      int[] arrayOfInt4 = new int[1];
      int j = XPCOM.NS_GetServiceManager(arrayOfInt4);
      if (j != 0)
        SWT.error(j);
      if (arrayOfInt4[0] == 0)
        SWT.error(-2147467262);
      nsIServiceManager localnsIServiceManager = new nsIServiceManager(arrayOfInt4[0]);
      arrayOfInt4[0] = 0;
      byte[] arrayOfByte = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
      j = localnsIServiceManager.GetServiceByContractID(arrayOfByte, nsIMemory.NS_IMEMORY_IID, arrayOfInt4);
      if (j != 0)
        SWT.error(j);
      if (arrayOfInt4[0] == 0)
        SWT.error(-2147467262);
      localnsIServiceManager.Release();
      nsIMemory localnsIMemory = new nsIMemory(arrayOfInt4[0]);
      arrayOfInt4[0] = 0;
      int k = arrayOfString[0].length();
      char[] arrayOfChar2 = new char[k + 1];
      arrayOfString[0].getChars(0, k, arrayOfChar2, 0);
      int m = arrayOfChar2.length * 2;
      int n = localnsIMemory.Alloc(m);
      XPCOM.memmove(n, arrayOfChar2, m);
      XPCOM.memmove(paramInt4, new int[] { n }, C.PTR_SIZEOF);
      if (arrayOfInt1[0] != 0)
        localnsIMemory.Free(arrayOfInt1[0]);
      localnsIMemory.Release();
    }
    if (paramInt6 != 0)
      XPCOM.memmove(paramInt6, arrayOfInt2, 4);
    return 0;
  }

  int PromptAuth(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    nsIAuthInformation localnsIAuthInformation = new nsIAuthInformation(paramInt4);
    Browser localBrowser = getBrowser(paramInt1);
    if (localBrowser != null)
    {
      localObject1 = (Mozilla)localBrowser.webBrowser;
      if (localObject1.authCount++ < 3)
        for (int i = 0; i < ((Mozilla)localObject1).authenticationListeners.length; i++)
        {
          localObject2 = new AuthenticationEvent(localBrowser);
          ((AuthenticationEvent)localObject2).location = ((Mozilla)localObject1).lastNavigateURL;
          localObject1.authenticationListeners[i].authenticate((AuthenticationEvent)localObject2);
          if (!((AuthenticationEvent)localObject2).doit)
          {
            XPCOM.memmove(paramInt7, new int[1], 4);
            return 0;
          }
          if ((((AuthenticationEvent)localObject2).user != null) && (((AuthenticationEvent)localObject2).password != null))
          {
            localObject3 = new nsEmbedString(((AuthenticationEvent)localObject2).user);
            int j = localnsIAuthInformation.SetUsername(((nsEmbedString)localObject3).getAddress());
            if (j != 0)
              SWT.error(j);
            ((nsEmbedString)localObject3).dispose();
            localObject3 = new nsEmbedString(((AuthenticationEvent)localObject2).password);
            j = localnsIAuthInformation.SetPassword(((nsEmbedString)localObject3).getAddress());
            if (j != 0)
              SWT.error(j);
            ((nsEmbedString)localObject3).dispose();
            XPCOM.memmove(paramInt7, new int[] { 1 }, 4);
            return 0;
          }
        }
    }
    Object localObject1 = null;
    int[] arrayOfInt1 = new int[1];
    Object localObject2 = new String[1];
    Object localObject3 = new String[1];
    String str1 = SWT.getMessage("SWT_Authentication_Required");
    if ((paramInt5 != 0) && (paramInt6 != 0))
    {
      k = XPCOM.strlen_PRUnichar(paramInt5);
      char[] arrayOfChar1 = new char[k];
      XPCOM.memmove(arrayOfChar1, paramInt5, k * 2);
      localObject1 = new String(arrayOfChar1);
      XPCOM.memmove(arrayOfInt1, paramInt6, 4);
    }
    int k = XPCOM.nsEmbedString_new();
    int m = localnsIAuthInformation.GetUsername(k);
    if (m != 0)
      SWT.error(m);
    int n = XPCOM.nsEmbedString_Length(k);
    int i1 = XPCOM.nsEmbedString_get(k);
    char[] arrayOfChar2 = new char[n];
    XPCOM.memmove(arrayOfChar2, i1, n * 2);
    localObject2[0] = new String(arrayOfChar2);
    XPCOM.nsEmbedString_delete(k);
    k = XPCOM.nsEmbedString_new();
    m = localnsIAuthInformation.GetPassword(k);
    if (m != 0)
      SWT.error(m);
    n = XPCOM.nsEmbedString_Length(k);
    i1 = XPCOM.nsEmbedString_get(k);
    arrayOfChar2 = new char[n];
    XPCOM.memmove(arrayOfChar2, i1, n * 2);
    localObject3[0] = new String(arrayOfChar2);
    XPCOM.nsEmbedString_delete(k);
    k = XPCOM.nsEmbedString_new();
    m = localnsIAuthInformation.GetRealm(k);
    if (m != 0)
      SWT.error(m);
    n = XPCOM.nsEmbedString_Length(k);
    i1 = XPCOM.nsEmbedString_get(k);
    arrayOfChar2 = new char[n];
    XPCOM.memmove(arrayOfChar2, i1, n * 2);
    String str2 = new String(arrayOfChar2);
    XPCOM.nsEmbedString_delete(k);
    nsIChannel localnsIChannel = new nsIChannel(paramInt2);
    int[] arrayOfInt2 = new int[1];
    m = localnsIChannel.GetURI(arrayOfInt2);
    if (m != 0)
      SWT.error(m);
    if (arrayOfInt2[0] == 0)
      Mozilla.error(-2147467262);
    nsIURI localnsIURI = new nsIURI(arrayOfInt2[0]);
    int i2 = XPCOM.nsEmbedCString_new();
    m = localnsIURI.GetHost(i2);
    if (m != 0)
      SWT.error(m);
    n = XPCOM.nsEmbedCString_Length(i2);
    i1 = XPCOM.nsEmbedCString_get(i2);
    byte[] arrayOfByte = new byte[n];
    XPCOM.memmove(arrayOfByte, i1, n);
    String str3 = new String(arrayOfByte);
    XPCOM.nsEmbedCString_delete(i2);
    localnsIURI.Release();
    String str4;
    if ((str2.length() > 0) && (str3.length() > 0))
      str4 = Compatibility.getMessage("SWT_Enter_Username_and_Password", new String[] { str2, str3 });
    else
      str4 = "";
    Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
    PromptDialog localPromptDialog = new PromptDialog(localShell);
    int[] arrayOfInt3 = new int[1];
    localPromptDialog.promptUsernameAndPassword(str1, str4, (String)localObject1, (String[])localObject2, (String[])localObject3, arrayOfInt1, arrayOfInt3);
    XPCOM.memmove(paramInt7, arrayOfInt3, 4);
    if (arrayOfInt3[0] == 1)
    {
      nsEmbedString localnsEmbedString = new nsEmbedString(localObject2[0]);
      m = localnsIAuthInformation.SetUsername(localnsEmbedString.getAddress());
      if (m != 0)
        SWT.error(m);
      localnsEmbedString.dispose();
      localnsEmbedString = new nsEmbedString(localObject3[0]);
      m = localnsIAuthInformation.SetPassword(localnsEmbedString.getAddress());
      if (m != 0)
        SWT.error(m);
      localnsEmbedString.dispose();
    }
    if (paramInt6 != 0)
      XPCOM.memmove(paramInt6, arrayOfInt1, 4);
    return 0;
  }

  int PromptUsernameAndPassword(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    Browser localBrowser = getBrowser(paramInt1);
    String str1 = null;
    String str2 = null;
    Object localObject1;
    Object localObject3;
    if (localBrowser != null)
    {
      localObject1 = (Mozilla)localBrowser.webBrowser;
      if (localObject1.authCount++ < 3)
        for (int i = 0; i < ((Mozilla)localObject1).authenticationListeners.length; i++)
        {
          localObject3 = new AuthenticationEvent(localBrowser);
          ((AuthenticationEvent)localObject3).location = ((Mozilla)localObject1).lastNavigateURL;
          localObject1.authenticationListeners[i].authenticate((AuthenticationEvent)localObject3);
          if (!((AuthenticationEvent)localObject3).doit)
          {
            XPCOM.memmove(paramInt8, new int[1], 4);
            return 0;
          }
          if ((((AuthenticationEvent)localObject3).user != null) && (((AuthenticationEvent)localObject3).password != null))
          {
            str1 = ((AuthenticationEvent)localObject3).user;
            str2 = ((AuthenticationEvent)localObject3).password;
            XPCOM.memmove(paramInt8, new int[] { 1 }, 4);
            break;
          }
        }
    }
    Object localObject4;
    Object localObject5;
    Object localObject2;
    Object localObject6;
    if (str1 == null)
    {
      localObject3 = null;
      String[] arrayOfString = new String[1];
      localObject4 = new String[1];
      if (paramInt2 != 0)
      {
        k = XPCOM.strlen_PRUnichar(paramInt2);
        localObject5 = new char[k];
        XPCOM.memmove((char[])localObject5, paramInt2, k * 2);
        localObject1 = new String((char[])localObject5);
      }
      else
      {
        localObject1 = SWT.getMessage("SWT_Authentication_Required");
      }
      int k = XPCOM.strlen_PRUnichar(paramInt3);
      localObject5 = new char[k];
      XPCOM.memmove((char[])localObject5, paramInt3, k * 2);
      localObject2 = new String((char[])localObject5);
      int[] arrayOfInt1 = new int[1];
      XPCOM.memmove(arrayOfInt1, paramInt4, C.PTR_SIZEOF);
      if (arrayOfInt1[0] != 0)
      {
        k = XPCOM.strlen_PRUnichar(arrayOfInt1[0]);
        localObject5 = new char[k];
        XPCOM.memmove((char[])localObject5, arrayOfInt1[0], k * 2);
        arrayOfString[0] = new String((char[])localObject5);
      }
      localObject6 = new int[1];
      XPCOM.memmove((int[])localObject6, paramInt5, C.PTR_SIZEOF);
      if (localObject6[0] != 0)
      {
        k = XPCOM.strlen_PRUnichar(localObject6[0]);
        localObject5 = new char[k];
        XPCOM.memmove((char[])localObject5, localObject6[0], k * 2);
        localObject4[0] = new String((char[])localObject5);
      }
      if (paramInt6 != 0)
      {
        k = XPCOM.strlen_PRUnichar(paramInt6);
        if (k > 0)
        {
          localObject5 = new char[k];
          XPCOM.memmove((char[])localObject5, paramInt6, k * 2);
          localObject3 = new String((char[])localObject5);
        }
      }
      Shell localShell = localBrowser == null ? new Shell() : localBrowser.getShell();
      PromptDialog localPromptDialog = new PromptDialog(localShell);
      int[] arrayOfInt2 = new int[1];
      int[] arrayOfInt3 = new int[1];
      if (paramInt7 != 0)
        XPCOM.memmove(arrayOfInt2, paramInt7, 4);
      localPromptDialog.promptUsernameAndPassword((String)localObject1, (String)localObject2, (String)localObject3, arrayOfString, (String[])localObject4, arrayOfInt2, arrayOfInt3);
      XPCOM.memmove(paramInt8, arrayOfInt3, 4);
      if (arrayOfInt3[0] == 1)
      {
        str1 = arrayOfString[0];
        str2 = localObject4[0];
      }
      if (paramInt7 != 0)
        XPCOM.memmove(paramInt7, arrayOfInt2, 4);
    }
    if (str1 != null)
    {
      localObject1 = new int[1];
      XPCOM.memmove((int[])localObject1, paramInt4, C.PTR_SIZEOF);
      localObject2 = new int[1];
      XPCOM.memmove((int[])localObject2, paramInt5, C.PTR_SIZEOF);
      localObject3 = new int[1];
      int j = XPCOM.NS_GetServiceManager((int[])localObject3);
      if (j != 0)
        SWT.error(j);
      if (localObject3[0] == 0)
        SWT.error(-2147467262);
      localObject4 = new nsIServiceManager(localObject3[0]);
      localObject3[0] = 0;
      localObject5 = MozillaDelegate.wcsToMbcs(null, "@mozilla.org/xpcom/memory-service;1", true);
      j = ((nsIServiceManager)localObject4).GetServiceByContractID((byte[])localObject5, nsIMemory.NS_IMEMORY_IID, (int[])localObject3);
      if (j != 0)
        SWT.error(j);
      if (localObject3[0] == 0)
        SWT.error(-2147467262);
      ((nsIServiceManager)localObject4).Release();
      nsIMemory localnsIMemory = new nsIMemory(localObject3[0]);
      localObject3[0] = 0;
      if (localObject1[0] != 0)
        localnsIMemory.Free(localObject1[0]);
      if (localObject2[0] != 0)
        localnsIMemory.Free(localObject2[0]);
      localnsIMemory.Release();
      int m = str1.length();
      localObject6 = new char[m + 1];
      str1.getChars(0, m, (char[])localObject6, 0);
      int n = localObject6.length * 2;
      int i1 = C.malloc(n);
      XPCOM.memmove(i1, (char[])localObject6, n);
      XPCOM.memmove(paramInt4, new int[] { i1 }, C.PTR_SIZEOF);
      m = str2.length();
      localObject6 = new char[m + 1];
      str2.getChars(0, m, (char[])localObject6, 0);
      n = localObject6.length * 2;
      i1 = C.malloc(n);
      XPCOM.memmove(i1, (char[])localObject6, n);
      XPCOM.memmove(paramInt5, new int[] { i1 }, C.PTR_SIZEOF);
    }
    return 0;
  }

  int PromptPassword(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    return -2147467263;
  }

  int Select(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    return -2147467263;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.browser.PromptService2
 * JD-Core Version:    0.6.2
 */