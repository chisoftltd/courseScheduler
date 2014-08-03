package org.eclipse.swt.internal.ole.win32;

public class IAccessible extends IDispatch
{
  public IAccessible(int paramInt)
  {
    super(paramInt);
  }

  public int get_accParent(int paramInt)
  {
    return COM.VtblCall(7, this.address, paramInt);
  }

  public int get_accChildCount(int paramInt)
  {
    return COM.VtblCall(8, this.address, paramInt);
  }

  public int get_accChild(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(9, this.address, paramInt1, paramInt2);
  }

  public int get_accName(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(10, this.address, paramInt1, paramInt2);
  }

  public int get_accValue(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(11, this.address, paramInt1, paramInt2);
  }

  public int get_accDescription(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(12, this.address, paramInt1, paramInt2);
  }

  public int get_accRole(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(13, this.address, paramInt1, paramInt2);
  }

  public int get_accState(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(14, this.address, paramInt1, paramInt2);
  }

  public int get_accHelp(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(15, this.address, paramInt1, paramInt2);
  }

  public int get_accHelpTopic(int paramInt1, int paramInt2, int paramInt3)
  {
    return COM.VtblCall_PVARIANTP(16, this.address, paramInt1, paramInt2, paramInt3);
  }

  public int get_accKeyboardShortcut(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(17, this.address, paramInt1, paramInt2);
  }

  public int get_accFocus(int paramInt)
  {
    return COM.VtblCall(18, this.address, paramInt);
  }

  public int get_accSelection(int paramInt)
  {
    return COM.VtblCall(19, this.address, paramInt);
  }

  public int get_accDefaultAction(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(20, this.address, paramInt1, paramInt2);
  }

  public int accSelect(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_IVARIANT(21, this.address, paramInt1, paramInt2);
  }

  public int accLocation(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    return COM.VtblCall_PPPPVARIANT(22, this.address, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public int accNavigate(int paramInt1, int paramInt2, int paramInt3)
  {
    return COM.VtblCall_IVARIANTP(23, this.address, paramInt1, paramInt2, paramInt3);
  }

  public int accHitTest(int paramInt1, int paramInt2, int paramInt3)
  {
    return COM.VtblCall(24, this.address, paramInt1, paramInt2, paramInt3);
  }

  public int accDoDefaultAction(int paramInt)
  {
    return COM.VtblCall_VARIANT(25, this.address, paramInt);
  }

  public int put_accName(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(26, this.address, paramInt1, paramInt2);
  }

  public int put_accValue(int paramInt1, int paramInt2)
  {
    return COM.VtblCall_VARIANTP(27, this.address, paramInt1, paramInt2);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.ole.win32.IAccessible
 * JD-Core Version:    0.6.2
 */