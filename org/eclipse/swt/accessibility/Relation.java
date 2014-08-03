package org.eclipse.swt.accessibility;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.ole.win32.COM;
import org.eclipse.swt.internal.ole.win32.COMObject;
import org.eclipse.swt.internal.ole.win32.GUID;
import org.eclipse.swt.internal.win32.OS;

class Relation
{
  Accessible accessible;
  COMObject objIAccessibleRelation;
  int refCount;
  int type;
  Accessible[] targets;
  static final String[] relationTypeString = { "controlledBy", "controllerFor", "describedBy", "descriptionFor", "embeddedBy", "embeds", "flowsFrom", "flowsTo", "labelFor", "labelledBy", "memberOf", "nodeChildOf", "parentWindowOf", "popupFor", "subwindowOf" };
  static final String[] localizedRelationTypeString = { SWT.getMessage("SWT_Controlled_By"), SWT.getMessage("SWT_Controller_For"), SWT.getMessage("SWT_Described_By"), SWT.getMessage("SWT_Description_For"), SWT.getMessage("SWT_Embedded_By"), SWT.getMessage("SWT_Embeds"), SWT.getMessage("SWT_Flows_From"), SWT.getMessage("SWT_Flows_To"), SWT.getMessage("SWT_Label_For"), SWT.getMessage("SWT_Labelled_By"), SWT.getMessage("SWT_Member_Of"), SWT.getMessage("SWT_Node_Child_Of"), SWT.getMessage("SWT_Parent_Window_Of"), SWT.getMessage("SWT_Popup_For"), SWT.getMessage("SWT_Subwindow_Of") };

  Relation(Accessible paramAccessible, int paramInt)
  {
    this.accessible = paramAccessible;
    this.type = paramInt;
    this.targets = new Accessible[0];
    AddRef();
  }

  int getAddress()
  {
    if (this.objIAccessibleRelation == null)
      createIAccessibleRelation();
    return this.objIAccessibleRelation.getAddress();
  }

  void createIAccessibleRelation()
  {
    this.objIAccessibleRelation = new COMObject(new int[] { 2, 0, 0, 1, 1, 1, 2, 3 })
    {
      public int method0(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.QueryInterface(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method1(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.AddRef();
      }

      public int method2(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.Release();
      }

      public int method3(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.get_relationType(paramAnonymousArrayOfInt[0]);
      }

      public int method4(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.get_localizedRelationType(paramAnonymousArrayOfInt[0]);
      }

      public int method5(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.get_nTargets(paramAnonymousArrayOfInt[0]);
      }

      public int method6(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.get_target(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1]);
      }

      public int method7(int[] paramAnonymousArrayOfInt)
      {
        return Relation.this.get_targets(paramAnonymousArrayOfInt[0], paramAnonymousArrayOfInt[1], paramAnonymousArrayOfInt[2]);
      }
    };
  }

  int QueryInterface(int paramInt1, int paramInt2)
  {
    GUID localGUID = new GUID();
    COM.MoveMemory(localGUID, paramInt1, GUID.sizeof);
    if ((COM.IsEqualGUID(localGUID, COM.IIDIUnknown)) || (COM.IsEqualGUID(localGUID, COM.IIDIAccessibleRelation)))
    {
      COM.MoveMemory(paramInt2, new int[] { getAddress() }, OS.PTR_SIZEOF);
      AddRef();
      return 0;
    }
    return -2147467262;
  }

  int AddRef()
  {
    this.refCount += 1;
    return this.refCount;
  }

  int Release()
  {
    this.refCount -= 1;
    if (this.refCount == 0)
    {
      if (this.objIAccessibleRelation != null)
        this.objIAccessibleRelation.dispose();
      this.objIAccessibleRelation = null;
    }
    return this.refCount;
  }

  int get_relationType(int paramInt)
  {
    setString(paramInt, relationTypeString[this.type]);
    return 0;
  }

  int get_localizedRelationType(int paramInt)
  {
    setString(paramInt, localizedRelationTypeString[this.type]);
    return 0;
  }

  int get_nTargets(int paramInt)
  {
    COM.MoveMemory(paramInt, new int[] { this.targets.length }, 4);
    return 0;
  }

  int get_target(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 >= this.targets.length))
      return -2147024809;
    Accessible localAccessible = this.targets[paramInt1];
    localAccessible.AddRef();
    COM.MoveMemory(paramInt2, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    return 0;
  }

  int get_targets(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = Math.min(this.targets.length, paramInt1);
    for (int j = 0; j < i; j++)
    {
      Accessible localAccessible = this.targets[j];
      localAccessible.AddRef();
      COM.MoveMemory(paramInt2 + j * OS.PTR_SIZEOF, new int[] { localAccessible.getAddress() }, OS.PTR_SIZEOF);
    }
    COM.MoveMemory(paramInt3, new int[] { i }, 4);
    return 0;
  }

  void addTarget(Accessible paramAccessible)
  {
    if (containsTarget(paramAccessible))
      return;
    Accessible[] arrayOfAccessible = new Accessible[this.targets.length + 1];
    System.arraycopy(this.targets, 0, arrayOfAccessible, 0, this.targets.length);
    arrayOfAccessible[this.targets.length] = paramAccessible;
    this.targets = arrayOfAccessible;
  }

  boolean containsTarget(Accessible paramAccessible)
  {
    for (int i = 0; i < this.targets.length; i++)
      if (this.targets[i] == paramAccessible)
        return true;
    return false;
  }

  void removeTarget(Accessible paramAccessible)
  {
    if (!containsTarget(paramAccessible))
      return;
    Accessible[] arrayOfAccessible = new Accessible[this.targets.length - 1];
    int i = 0;
    for (int j = 0; j < this.targets.length; j++)
      if (this.targets[j] != paramAccessible)
        arrayOfAccessible[(i++)] = this.targets[j];
    this.targets = arrayOfAccessible;
  }

  boolean hasTargets()
  {
    return this.targets.length > 0;
  }

  void setString(int paramInt, String paramString)
  {
    char[] arrayOfChar = (paramString + "").toCharArray();
    int i = COM.SysAllocString(arrayOfChar);
    COM.MoveMemory(paramInt, new int[] { i }, OS.PTR_SIZEOF);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.accessibility.Relation
 * JD-Core Version:    0.6.2
 */