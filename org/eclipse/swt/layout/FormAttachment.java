package org.eclipse.swt.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;

public final class FormAttachment
{
  public int numerator;
  public int denominator = 100;
  public int offset;
  public Control control;
  public int alignment;

  public FormAttachment()
  {
  }

  public FormAttachment(int paramInt)
  {
    this(paramInt, 100, 0);
  }

  public FormAttachment(int paramInt1, int paramInt2)
  {
    this(paramInt1, 100, paramInt2);
  }

  public FormAttachment(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt2 == 0)
      SWT.error(7);
    this.numerator = paramInt1;
    this.denominator = paramInt2;
    this.offset = paramInt3;
  }

  public FormAttachment(Control paramControl)
  {
    this(paramControl, 0, -1);
  }

  public FormAttachment(Control paramControl, int paramInt)
  {
    this(paramControl, paramInt, -1);
  }

  public FormAttachment(Control paramControl, int paramInt1, int paramInt2)
  {
    this.control = paramControl;
    this.offset = paramInt1;
    this.alignment = paramInt2;
  }

  FormAttachment divide(int paramInt)
  {
    return new FormAttachment(this.numerator, this.denominator * paramInt, this.offset / paramInt);
  }

  int gcd(int paramInt1, int paramInt2)
  {
    paramInt1 = Math.abs(paramInt1);
    paramInt2 = Math.abs(paramInt2);
    int i;
    if (paramInt1 < paramInt2)
    {
      i = paramInt1;
      paramInt1 = paramInt2;
    }
    for (paramInt2 = i; paramInt2 != 0; paramInt2 = i % paramInt2)
    {
      i = paramInt1;
      paramInt1 = paramInt2;
    }
    return paramInt1;
  }

  FormAttachment minus(FormAttachment paramFormAttachment)
  {
    FormAttachment localFormAttachment = new FormAttachment();
    localFormAttachment.numerator = (this.numerator * paramFormAttachment.denominator - this.denominator * paramFormAttachment.numerator);
    this.denominator *= paramFormAttachment.denominator;
    int i = gcd(localFormAttachment.denominator, localFormAttachment.numerator);
    localFormAttachment.numerator /= i;
    localFormAttachment.denominator /= i;
    this.offset -= paramFormAttachment.offset;
    return localFormAttachment;
  }

  FormAttachment minus(int paramInt)
  {
    return new FormAttachment(this.numerator, this.denominator, this.offset - paramInt);
  }

  FormAttachment plus(FormAttachment paramFormAttachment)
  {
    FormAttachment localFormAttachment = new FormAttachment();
    localFormAttachment.numerator = (this.numerator * paramFormAttachment.denominator + this.denominator * paramFormAttachment.numerator);
    this.denominator *= paramFormAttachment.denominator;
    int i = gcd(localFormAttachment.denominator, localFormAttachment.numerator);
    localFormAttachment.numerator /= i;
    localFormAttachment.denominator /= i;
    this.offset += paramFormAttachment.offset;
    return localFormAttachment;
  }

  FormAttachment plus(int paramInt)
  {
    return new FormAttachment(this.numerator, this.denominator, this.offset + paramInt);
  }

  int solveX(int paramInt)
  {
    if (this.denominator == 0)
      SWT.error(7);
    return this.numerator * paramInt / this.denominator + this.offset;
  }

  int solveY(int paramInt)
  {
    if (this.numerator == 0)
      SWT.error(7);
    return (paramInt - this.offset) * this.denominator / this.numerator;
  }

  public String toString()
  {
    String str = this.numerator + "/" + this.denominator;
    return "{y = (" + str + (this.offset >= 0 ? ")x + " + this.offset : new StringBuffer(")x - ").append(-this.offset).toString()) + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.FormAttachment
 * JD-Core Version:    0.6.2
 */