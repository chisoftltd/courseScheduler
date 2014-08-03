package org.eclipse.swt.custom;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Sash;

public class SashForm extends Composite
{
  public int SASH_WIDTH = 3;
  int sashStyle;
  Sash[] sashes = new Sash[0];
  Color background = null;
  Color foreground = null;
  Control[] controls = new Control[0];
  Control maxControl = null;
  Listener sashListener;
  static final int DRAG_MINIMUM = 20;

  public SashForm(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    super.setLayout(new SashFormLayout());
    this.sashStyle = ((paramInt & 0x200) != 0 ? 256 : 512);
    if ((paramInt & 0x800) != 0)
      this.sashStyle |= 2048;
    if ((paramInt & 0x10000) != 0)
      this.sashStyle |= 65536;
    this.sashListener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        SashForm.this.onDragSash(paramAnonymousEvent);
      }
    };
  }

  static int checkStyle(int paramInt)
  {
    int i = 100665344;
    return paramInt & i;
  }

  Sash createSash()
  {
    Sash localSash = new Sash(this, this.sashStyle);
    localSash.setBackground(this.background);
    localSash.setForeground(this.foreground);
    localSash.setToolTipText(getToolTipText());
    localSash.addListener(13, this.sashListener);
    return localSash;
  }

  public int getOrientation()
  {
    return (this.sashStyle & 0x200) != 0 ? 256 : 512;
  }

  public int getSashWidth()
  {
    checkWidget();
    return this.SASH_WIDTH;
  }

  public int getStyle()
  {
    int i = super.getStyle();
    i |= (getOrientation() == 512 ? 512 : 256);
    if ((this.sashStyle & 0x10000) != 0)
      i |= 65536;
    return i;
  }

  public Control getMaximizedControl()
  {
    return this.maxControl;
  }

  public int[] getWeights()
  {
    checkWidget();
    Control[] arrayOfControl = getControls(false);
    int[] arrayOfInt = new int[arrayOfControl.length];
    for (int i = 0; i < arrayOfControl.length; i++)
    {
      Object localObject = arrayOfControl[i].getLayoutData();
      if ((localObject != null) && ((localObject instanceof SashFormData)))
        arrayOfInt[i] = ((int)(((SashFormData)localObject).weight * 1000L >> 16));
      else
        arrayOfInt[i] = 200;
    }
    return arrayOfInt;
  }

  Control[] getControls(boolean paramBoolean)
  {
    Control[] arrayOfControl1 = getChildren();
    Object localObject = new Control[0];
    for (int i = 0; i < arrayOfControl1.length; i++)
      if ((!(arrayOfControl1[i] instanceof Sash)) && ((!paramBoolean) || (arrayOfControl1[i].getVisible())))
      {
        Control[] arrayOfControl2 = new Control[localObject.length + 1];
        System.arraycopy(localObject, 0, arrayOfControl2, 0, localObject.length);
        arrayOfControl2[localObject.length] = arrayOfControl1[i];
        localObject = arrayOfControl2;
      }
    return localObject;
  }

  void onDragSash(Event paramEvent)
  {
    Sash localSash = (Sash)paramEvent.widget;
    int i = -1;
    for (int j = 0; j < this.sashes.length; j++)
      if (this.sashes[j] == localSash)
      {
        i = j;
        break;
      }
    if (i == -1)
      return;
    Control localControl1 = this.controls[i];
    Control localControl2 = this.controls[(i + 1)];
    Rectangle localRectangle1 = localControl1.getBounds();
    Rectangle localRectangle2 = localControl2.getBounds();
    Rectangle localRectangle3 = localSash.getBounds();
    Rectangle localRectangle4 = getClientArea();
    int k = 0;
    int m;
    int n;
    Object localObject1;
    Object localObject2;
    if (getOrientation() == 256)
    {
      k = (localRectangle1.width >= 20) && (localRectangle2.width >= 20) ? 0 : 1;
      m = localRectangle2.x + localRectangle2.width - localRectangle1.x;
      n = paramEvent.x - localRectangle3.x;
      localRectangle1.width += n;
      localRectangle2.x += n;
      localRectangle2.width -= n;
      if (localRectangle1.width < 20)
      {
        localRectangle1.width = 20;
        localRectangle2.x = (localRectangle1.x + localRectangle1.width + localRectangle3.width);
        localRectangle2.width = (m - localRectangle2.x);
        paramEvent.x = (localRectangle1.x + localRectangle1.width);
        paramEvent.doit = false;
      }
      if (localRectangle2.width < 20)
      {
        localRectangle1.width = (m - 20 - localRectangle3.width);
        localRectangle2.x = (localRectangle1.x + localRectangle1.width + localRectangle3.width);
        localRectangle2.width = 20;
        paramEvent.x = (localRectangle1.x + localRectangle1.width);
        paramEvent.doit = false;
      }
      localObject1 = localControl1.getLayoutData();
      if ((localObject1 == null) || (!(localObject1 instanceof SashFormData)))
      {
        localObject1 = new SashFormData();
        localControl1.setLayoutData(localObject1);
      }
      localObject2 = localControl2.getLayoutData();
      if ((localObject2 == null) || (!(localObject2 instanceof SashFormData)))
      {
        localObject2 = new SashFormData();
        localControl2.setLayoutData(localObject2);
      }
      ((SashFormData)localObject1).weight = (((localRectangle1.width << 16) + localRectangle4.width - 1L) / localRectangle4.width);
      ((SashFormData)localObject2).weight = (((localRectangle2.width << 16) + localRectangle4.width - 1L) / localRectangle4.width);
    }
    else
    {
      k = (localRectangle1.height >= 20) && (localRectangle2.height >= 20) ? 0 : 1;
      m = localRectangle2.y + localRectangle2.height - localRectangle1.y;
      n = paramEvent.y - localRectangle3.y;
      localRectangle1.height += n;
      localRectangle2.y += n;
      localRectangle2.height -= n;
      if (localRectangle1.height < 20)
      {
        localRectangle1.height = 20;
        localRectangle2.y = (localRectangle1.y + localRectangle1.height + localRectangle3.height);
        localRectangle2.height = (m - localRectangle2.y);
        paramEvent.y = (localRectangle1.y + localRectangle1.height);
        paramEvent.doit = false;
      }
      if (localRectangle2.height < 20)
      {
        localRectangle1.height = (m - 20 - localRectangle3.height);
        localRectangle2.y = (localRectangle1.y + localRectangle1.height + localRectangle3.height);
        localRectangle2.height = 20;
        paramEvent.y = (localRectangle1.y + localRectangle1.height);
        paramEvent.doit = false;
      }
      localObject1 = localControl1.getLayoutData();
      if ((localObject1 == null) || (!(localObject1 instanceof SashFormData)))
      {
        localObject1 = new SashFormData();
        localControl1.setLayoutData(localObject1);
      }
      localObject2 = localControl2.getLayoutData();
      if ((localObject2 == null) || (!(localObject2 instanceof SashFormData)))
      {
        localObject2 = new SashFormData();
        localControl2.setLayoutData(localObject2);
      }
      ((SashFormData)localObject1).weight = (((localRectangle1.height << 16) + localRectangle4.height - 1L) / localRectangle4.height);
      ((SashFormData)localObject2).weight = (((localRectangle2.height << 16) + localRectangle4.height - 1L) / localRectangle4.height);
    }
    if ((k != 0) || ((paramEvent.doit) && (paramEvent.detail != 1)))
    {
      localControl1.setBounds(localRectangle1);
      localSash.setBounds(paramEvent.x, paramEvent.y, paramEvent.width, paramEvent.height);
      localControl2.setBounds(localRectangle2);
    }
  }

  public void setOrientation(int paramInt)
  {
    checkWidget();
    if (getOrientation() == paramInt)
      return;
    if ((paramInt != 256) && (paramInt != 512))
      SWT.error(5);
    this.sashStyle &= -769;
    this.sashStyle |= (paramInt == 512 ? 256 : 512);
    for (int i = 0; i < this.sashes.length; i++)
    {
      this.sashes[i].dispose();
      this.sashes[i] = createSash();
    }
    layout(false);
  }

  public void setBackground(Color paramColor)
  {
    super.setBackground(paramColor);
    this.background = paramColor;
    for (int i = 0; i < this.sashes.length; i++)
      this.sashes[i].setBackground(this.background);
  }

  public void setForeground(Color paramColor)
  {
    super.setForeground(paramColor);
    this.foreground = paramColor;
    for (int i = 0; i < this.sashes.length; i++)
      this.sashes[i].setForeground(this.foreground);
  }

  public void setLayout(Layout paramLayout)
  {
    checkWidget();
  }

  public void setMaximizedControl(Control paramControl)
  {
    checkWidget();
    if (paramControl == null)
    {
      if (this.maxControl != null)
      {
        this.maxControl = null;
        layout(false);
        for (i = 0; i < this.sashes.length; i++)
          this.sashes[i].setVisible(true);
      }
      return;
    }
    for (int i = 0; i < this.sashes.length; i++)
      this.sashes[i].setVisible(false);
    this.maxControl = paramControl;
    layout(false);
  }

  public void setSashWidth(int paramInt)
  {
    checkWidget();
    if (this.SASH_WIDTH == paramInt)
      return;
    this.SASH_WIDTH = paramInt;
    layout(false);
  }

  public void setToolTipText(String paramString)
  {
    super.setToolTipText(paramString);
    for (int i = 0; i < this.sashes.length; i++)
      this.sashes[i].setToolTipText(paramString);
  }

  public void setWeights(int[] paramArrayOfInt)
  {
    checkWidget();
    Control[] arrayOfControl = getControls(false);
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length != arrayOfControl.length))
      SWT.error(5);
    int i = 0;
    for (int j = 0; j < paramArrayOfInt.length; j++)
    {
      if (paramArrayOfInt[j] < 0)
        SWT.error(5);
      i += paramArrayOfInt[j];
    }
    if (i == 0)
      SWT.error(5);
    for (j = 0; j < arrayOfControl.length; j++)
    {
      Object localObject = arrayOfControl[j].getLayoutData();
      if ((localObject == null) || (!(localObject instanceof SashFormData)))
      {
        localObject = new SashFormData();
        arrayOfControl[j].setLayoutData(localObject);
      }
      ((SashFormData)localObject).weight = (((paramArrayOfInt[j] << 16) + i - 1L) / i);
    }
    layout(false);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.SashForm
 * JD-Core Version:    0.6.2
 */