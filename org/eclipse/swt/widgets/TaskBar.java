package org.eclipse.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.PROPERTYKEY;
import org.eclipse.swt.internal.win32.TCHAR;

public class TaskBar extends Widget
{
  int itemCount;
  TaskItem[] items = new TaskItem[4];
  int mTaskbarList3;
  static final char[] EXE_PATH;
  static final char[] ICO_DIR = { 'i', 'c', 'o', '_', 'd', 'i', 'r' };
  static final PROPERTYKEY PKEY_Title = new PROPERTYKEY();
  static final PROPERTYKEY PKEY_AppUserModel_IsDestListSeparator = new PROPERTYKEY();
  static final byte[] CLSID_TaskbarList = new byte[16];
  static final byte[] CLSID_DestinationList = new byte[16];
  static final byte[] CLSID_EnumerableObjectCollection = new byte[16];
  static final byte[] CLSID_ShellLink = new byte[16];
  static final byte[] CLSID_FileOperation = new byte[16];
  static final byte[] IID_ITaskbarList3 = new byte[16];
  static final byte[] IID_ICustomDestinationList = new byte[16];
  static final byte[] IID_IObjectArray = new byte[16];
  static final byte[] IID_IObjectCollection = new byte[16];
  static final byte[] IID_IShellLinkW = new byte[16];
  static final byte[] IID_IPropertyStore = new byte[16];
  static final byte[] IID_IShellItem = new byte[16];
  static final byte[] IID_IFileOperation = new byte[16];
  static final byte[] FOLDERID_LocalAppData = new byte[16];

  static
  {
    OS.IIDFromString("".toCharArray(), CLSID_TaskbarList);
    OS.IIDFromString("".toCharArray(), CLSID_DestinationList);
    OS.IIDFromString("".toCharArray(), CLSID_EnumerableObjectCollection);
    OS.IIDFromString("".toCharArray(), CLSID_ShellLink);
    OS.IIDFromString("".toCharArray(), CLSID_FileOperation);
    OS.IIDFromString("".toCharArray(), IID_ITaskbarList3);
    OS.IIDFromString("".toCharArray(), IID_ICustomDestinationList);
    OS.IIDFromString("".toCharArray(), IID_IObjectArray);
    OS.IIDFromString("".toCharArray(), IID_IObjectCollection);
    OS.IIDFromString("".toCharArray(), IID_IShellLinkW);
    OS.IIDFromString("".toCharArray(), IID_IPropertyStore);
    OS.IIDFromString("".toCharArray(), IID_IShellItem);
    OS.IIDFromString("".toCharArray(), IID_IFileOperation);
    OS.IIDFromString("".toCharArray(), FOLDERID_LocalAppData);
    OS.PSPropertyKeyFromString("".toCharArray(), PKEY_Title);
    OS.PSPropertyKeyFromString("".toCharArray(), PKEY_AppUserModel_IsDestListSeparator);
    for (TCHAR localTCHAR = new TCHAR(0, 260); OS.GetModuleFileName(0, localTCHAR, localTCHAR.length()) == localTCHAR.length(); localTCHAR = new TCHAR(0, localTCHAR.length() + 260));
    int i = localTCHAR.strlen();
    EXE_PATH = new char[i + 1];
    System.arraycopy(localTCHAR.chars, 0, EXE_PATH, 0, i);
  }

  TaskBar(Display paramDisplay, int paramInt)
  {
    if (paramDisplay == null)
      paramDisplay = Display.getCurrent();
    if (paramDisplay == null)
      paramDisplay = Display.getDefault();
    if (!paramDisplay.isValidThread())
      error(22);
    this.display = paramDisplay;
    createHandle();
    reskinWidget();
  }

  void createHandle()
  {
    int[] arrayOfInt = new int[1];
    int i = OS.CoCreateInstance(CLSID_TaskbarList, 0, 1, IID_ITaskbarList3, arrayOfInt);
    if (i != 0)
      error(2);
    this.mTaskbarList3 = arrayOfInt[0];
  }

  void createItem(TaskItem paramTaskItem, int paramInt)
  {
    if (paramInt == -1)
      paramInt = this.itemCount;
    if ((paramInt < 0) || (paramInt > this.itemCount))
      error(6);
    if (this.itemCount == this.items.length)
    {
      TaskItem[] arrayOfTaskItem = new TaskItem[this.items.length + 4];
      System.arraycopy(this.items, 0, arrayOfTaskItem, 0, this.items.length);
      this.items = arrayOfTaskItem;
    }
    System.arraycopy(this.items, paramInt, this.items, paramInt + 1, this.itemCount++ - paramInt);
    this.items[paramInt] = paramTaskItem;
  }

  void createItems()
  {
    Shell[] arrayOfShell = this.display.getShells();
    for (int i = 0; i < arrayOfShell.length; i++)
      getItem(arrayOfShell[i]);
    getItem(null);
  }

  int createShellLink(MenuItem paramMenuItem, String paramString)
  {
    int i = paramMenuItem.getStyle();
    if ((i & 0x40) != 0)
      return 0;
    int[] arrayOfInt = new int[1];
    int j = OS.CoCreateInstance(CLSID_ShellLink, 0, 1, IID_IShellLinkW, arrayOfInt);
    if (j != 0)
      error(2);
    int k = arrayOfInt[0];
    int m = OS.GetProcessHeap();
    int n = OS.HeapAlloc(m, 8, OS.PROPVARIANT_sizeof());
    int i1 = 0;
    PROPERTYKEY localPROPERTYKEY;
    if ((i & 0x2) != 0)
    {
      OS.MoveMemory(n, new short[] { 11 }, 2);
      OS.MoveMemory(n + 8, new short[] { -1 }, 2);
      localPROPERTYKEY = PKEY_AppUserModel_IsDestListSeparator;
    }
    else
    {
      String str1 = paramMenuItem.getText();
      int i3 = str1.length();
      char[] arrayOfChar = new char[i3 + 1];
      str1.getChars(0, i3, arrayOfChar, 0);
      i1 = OS.HeapAlloc(m, 8, arrayOfChar.length * 2);
      OS.MoveMemory(i1, arrayOfChar, arrayOfChar.length * 2);
      OS.MoveMemory(n, new short[] { 31 }, 2);
      OS.MoveMemory(n + 8, new int[] { i1 }, OS.PTR_SIZEOF);
      localPROPERTYKEY = PKEY_Title;
      j = OS.VtblCall(20, k, EXE_PATH);
      if (j != 0)
        error(5);
      str1 = "--launcher.openFile /SWTINTERNAL_ID" + paramMenuItem.id;
      i3 = str1.length();
      arrayOfChar = new char[i3 + 1];
      str1.getChars(0, i3, arrayOfChar, 0);
      j = OS.VtblCall(11, k, arrayOfChar);
      if (j != 0)
        error(5);
      Image localImage = paramMenuItem.getImage();
      if ((localImage != null) && (paramString != null))
      {
        String str2 = paramString + "\\menu" + paramMenuItem.id + ".ico";
        ImageData localImageData;
        if (paramMenuItem.hBitmap != 0)
        {
          localObject = Image.win32_new(this.display, 0, paramMenuItem.hBitmap);
          localImageData = ((Image)localObject).getImageData();
        }
        else
        {
          localImageData = localImage.getImageData();
        }
        Object localObject = new ImageLoader();
        ((ImageLoader)localObject).data = new ImageData[] { localImageData };
        ((ImageLoader)localObject).save(str2, 3);
        i3 = str2.length();
        arrayOfChar = new char[i3 + 1];
        str2.getChars(0, i3, arrayOfChar, 0);
        j = OS.VtblCall(17, k, arrayOfChar, 0);
        if (j != 0)
          error(5);
      }
    }
    j = OS.VtblCall(0, k, IID_IPropertyStore, arrayOfInt);
    if (j != 0)
      error(2);
    int i2 = arrayOfInt[0];
    j = OS.VtblCall(6, i2, localPROPERTYKEY, n);
    if (j != 0)
      error(5);
    OS.VtblCall(7, i2);
    OS.VtblCall(2, i2);
    OS.HeapFree(m, 0, n);
    if (i1 != 0)
      OS.HeapFree(m, 0, i1);
    return k;
  }

  int createShellLinkArray(MenuItem[] paramArrayOfMenuItem, String paramString)
  {
    if (paramArrayOfMenuItem == null)
      return 0;
    if (paramArrayOfMenuItem.length == 0)
      return 0;
    int[] arrayOfInt = new int[1];
    int i = OS.CoCreateInstance(CLSID_EnumerableObjectCollection, 0, 1, IID_IObjectCollection, arrayOfInt);
    if (i != 0)
      error(2);
    int j = arrayOfInt[0];
    for (int k = 0; k < paramArrayOfMenuItem.length; k++)
    {
      int m = createShellLink(paramArrayOfMenuItem[k], paramString);
      if (m != 0)
      {
        i = OS.VtblCall(5, j, m);
        if (i != 0)
          error(5);
        OS.VtblCall(2, m);
      }
    }
    i = OS.VtblCall(0, j, IID_IObjectArray, arrayOfInt);
    if (i != 0)
      error(2);
    k = arrayOfInt[0];
    OS.VtblCall(2, j);
    return k;
  }

  void destroyItem(TaskItem paramTaskItem)
  {
    for (int i = 0; i < this.itemCount; i++)
      if (this.items[i] == paramTaskItem)
        break;
    if (i == this.itemCount)
      return;
    System.arraycopy(this.items, i + 1, this.items, i, --this.itemCount - i);
    this.items[this.itemCount] = null;
  }

  String getDirectory(char[] paramArrayOfChar)
  {
    char[] arrayOfChar1 = new char[paramArrayOfChar.length];
    for (int i = 0; i < paramArrayOfChar.length; i++)
    {
      int j = paramArrayOfChar[i];
      switch (j)
      {
      case 34:
      case 42:
      case 47:
      case 58:
      case 60:
      case 62:
      case 63:
      case 92:
      case 124:
        arrayOfChar1[i] = '_';
        break;
      default:
        arrayOfChar1[i] = j;
      }
    }
    String str = null;
    int[] arrayOfInt = new int[1];
    int k = OS.SHCreateItemInKnownFolder(FOLDERID_LocalAppData, 0, null, IID_IShellItem, arrayOfInt);
    if (k == 0)
    {
      int m = arrayOfInt[0];
      k = OS.CoCreateInstance(CLSID_FileOperation, 0, 1, IID_IFileOperation, arrayOfInt);
      if (k == 0)
      {
        int n = arrayOfInt[0];
        k = OS.VtblCall(5, n, 1556);
        if (k == 0)
        {
          int i1 = getDirectory(m, n, arrayOfChar1, false);
          if (i1 != 0)
          {
            int i2 = getDirectory(i1, n, ICO_DIR, true);
            if (i2 != 0)
            {
              k = OS.VtblCall(5, i2, -2147123200, arrayOfInt);
              if (k == 0)
              {
                int i3 = arrayOfInt[0];
                int i4 = OS.wcslen(i3);
                char[] arrayOfChar2 = new char[i4];
                OS.MoveMemory(arrayOfChar2, i3, i4 * 2);
                str = new String(arrayOfChar2);
                OS.CoTaskMemFree(i3);
              }
              OS.VtblCall(2, i2);
            }
            OS.VtblCall(2, i1);
          }
        }
        OS.VtblCall(2, n);
      }
      OS.VtblCall(2, m);
    }
    return str;
  }

  int getDirectory(int paramInt1, int paramInt2, char[] paramArrayOfChar, boolean paramBoolean)
  {
    int[] arrayOfInt = new int[1];
    int i = OS.SHCreateItemFromRelativeName(paramInt1, paramArrayOfChar, 0, IID_IShellItem, arrayOfInt);
    if (i == 0)
    {
      if (paramBoolean)
      {
        i = OS.VtblCall(18, paramInt2, arrayOfInt[0], 0);
        OS.VtblCall(2, arrayOfInt[0]);
        if (i == 0)
        {
          i = OS.VtblCall(20, paramInt2, paramInt1, 16, paramArrayOfChar, null, 0);
          if (i == 0)
          {
            i = OS.VtblCall(21, paramInt2);
            if (i == 0)
            {
              i = OS.SHCreateItemFromRelativeName(paramInt1, paramArrayOfChar, 0, IID_IShellItem, arrayOfInt);
              if (i == 0)
                return arrayOfInt[0];
            }
          }
        }
      }
      else
      {
        return arrayOfInt[0];
      }
    }
    else
    {
      i = OS.VtblCall(20, paramInt2, paramInt1, 16, paramArrayOfChar, null, 0);
      if (i == 0)
      {
        i = OS.VtblCall(21, paramInt2);
        if (i == 0)
        {
          i = OS.SHCreateItemFromRelativeName(paramInt1, paramArrayOfChar, 0, IID_IShellItem, arrayOfInt);
          if (i == 0)
            return arrayOfInt[0];
        }
      }
    }
    return 0;
  }

  public TaskItem getItem(int paramInt)
  {
    checkWidget();
    createItems();
    if ((paramInt < 0) || (paramInt >= this.itemCount))
      error(6);
    return this.items[paramInt];
  }

  public TaskItem getItem(Shell paramShell)
  {
    checkWidget();
    for (int i = 0; i < this.items.length; i++)
      if ((this.items[i] != null) && (this.items[i].shell == paramShell))
        return this.items[i];
    TaskItem localTaskItem = new TaskItem(this, 0);
    if (paramShell != null)
      localTaskItem.setShell(paramShell);
    return localTaskItem;
  }

  public int getItemCount()
  {
    checkWidget();
    createItems();
    return this.itemCount;
  }

  public TaskItem[] getItems()
  {
    checkWidget();
    createItems();
    TaskItem[] arrayOfTaskItem = new TaskItem[this.itemCount];
    System.arraycopy(this.items, 0, arrayOfTaskItem, 0, arrayOfTaskItem.length);
    return arrayOfTaskItem;
  }

  void releaseChildren(boolean paramBoolean)
  {
    if (this.items != null)
    {
      for (int i = 0; i < this.items.length; i++)
      {
        TaskItem localTaskItem = this.items[i];
        if ((localTaskItem != null) && (!localTaskItem.isDisposed()))
          localTaskItem.release(false);
      }
      this.items = null;
    }
    super.releaseChildren(paramBoolean);
  }

  void releaseParent()
  {
    super.releaseParent();
    if (this.display.taskBar == this)
      this.display.taskBar = null;
  }

  void releaseWidget()
  {
    super.releaseWidget();
    if (this.mTaskbarList3 != 0)
    {
      OS.VtblCall(2, this.mTaskbarList3);
      this.mTaskbarList3 = 0;
    }
  }

  void reskinChildren(int paramInt)
  {
    if (this.items != null)
      for (int i = 0; i < this.items.length; i++)
      {
        TaskItem localTaskItem = this.items[i];
        if (localTaskItem != null)
          localTaskItem.reskin(paramInt);
      }
    super.reskinChildren(paramInt);
  }

  void setMenu(Menu paramMenu)
  {
    int[] arrayOfInt1 = new int[1];
    int i = OS.CoCreateInstance(CLSID_DestinationList, 0, 1, IID_ICustomDestinationList, arrayOfInt1);
    if (i != 0)
      error(2);
    int j = arrayOfInt1[0];
    String str1 = Display.APP_NAME;
    char[] arrayOfChar = (char[])null;
    if (str1 != null)
    {
      int k = str1.length();
      arrayOfChar = new char[k + 1];
      str1.getChars(0, k, arrayOfChar, 0);
    }
    MenuItem[] arrayOfMenuItem = (MenuItem[])null;
    if ((paramMenu != null) && ((arrayOfMenuItem = paramMenu.getItems()).length != 0))
    {
      String str2 = getDirectory(arrayOfChar);
      int m = createShellLinkArray(arrayOfMenuItem, str2);
      if (m != 0)
      {
        i = OS.VtblCall(3, j, arrayOfChar);
        if (i != 0)
          error(5);
        int[] arrayOfInt2 = new int[1];
        OS.VtblCall(4, j, arrayOfInt2, IID_IObjectArray, arrayOfInt1);
        if (i != 0)
          error(5);
        int n = arrayOfInt1[0];
        int[] arrayOfInt3 = new int[1];
        OS.VtblCall(3, m, arrayOfInt3);
        if (arrayOfInt3[0] != 0)
        {
          i = OS.VtblCall(7, j, m);
          if (i != 0)
            error(5);
        }
        for (int i1 = 0; i1 < arrayOfMenuItem.length; i1++)
        {
          MenuItem localMenuItem = arrayOfMenuItem[i1];
          if ((localMenuItem.getStyle() & 0x40) != 0)
          {
            Menu localMenu = localMenuItem.getMenu();
            if (localMenu != null)
            {
              int i2 = createShellLinkArray(localMenu.getItems(), str2);
              if (i2 != 0)
              {
                OS.VtblCall(3, i2, arrayOfInt3);
                if (arrayOfInt3[0] != 0)
                {
                  String str3 = localMenuItem.getText();
                  int i3 = str3.length();
                  arrayOfChar = new char[i3 + 1];
                  str3.getChars(0, i3, arrayOfChar, 0);
                  i = OS.VtblCall(5, j, arrayOfChar, i2);
                  if (i != 0)
                    error(5);
                }
                OS.VtblCall(2, i2);
              }
            }
          }
        }
        i = OS.VtblCall(8, j);
        if (i != 0)
          error(5);
        if (n != 0)
          OS.VtblCall(2, n);
        OS.VtblCall(2, m);
      }
    }
    else
    {
      i = OS.VtblCall(10, j, arrayOfChar);
      if (i != 0)
        error(5);
    }
    OS.VtblCall(2, j);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.widgets.TaskBar
 * JD-Core Version:    0.6.2
 */