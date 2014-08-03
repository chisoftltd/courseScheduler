package org.eclipse.swt.layout;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Scrollable;

public final class GridLayout extends Layout
{
  public int numColumns = 1;
  public boolean makeColumnsEqualWidth = false;
  public int marginWidth = 5;
  public int marginHeight = 5;
  public int marginLeft = 0;
  public int marginTop = 0;
  public int marginRight = 0;
  public int marginBottom = 0;
  public int horizontalSpacing = 5;
  public int verticalSpacing = 5;

  public GridLayout()
  {
  }

  public GridLayout(int paramInt, boolean paramBoolean)
  {
    this.numColumns = paramInt;
    this.makeColumnsEqualWidth = paramBoolean;
  }

  protected Point computeSize(Composite paramComposite, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Point localPoint = layout(paramComposite, false, 0, 0, paramInt1, paramInt2, paramBoolean);
    if (paramInt1 != -1)
      localPoint.x = paramInt1;
    if (paramInt2 != -1)
      localPoint.y = paramInt2;
    return localPoint;
  }

  protected boolean flushCache(Control paramControl)
  {
    Object localObject = paramControl.getLayoutData();
    if (localObject != null)
      ((GridData)localObject).flushCache();
    return true;
  }

  GridData getData(Control[][] paramArrayOfControl, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    Control localControl = paramArrayOfControl[paramInt1][paramInt2];
    if (localControl != null)
    {
      GridData localGridData = (GridData)localControl.getLayoutData();
      int i = Math.max(1, Math.min(localGridData.horizontalSpan, paramInt4));
      int j = Math.max(1, localGridData.verticalSpan);
      int k = paramBoolean ? paramInt1 + j - 1 : paramInt1 - j + 1;
      int m = paramBoolean ? paramInt2 + i - 1 : paramInt2 - i + 1;
      if ((k >= 0) && (k < paramInt3) && (m >= 0) && (m < paramInt4) && (localControl == paramArrayOfControl[k][m]))
        return localGridData;
    }
    return null;
  }

  protected void layout(Composite paramComposite, boolean paramBoolean)
  {
    Rectangle localRectangle = paramComposite.getClientArea();
    layout(paramComposite, true, localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height, paramBoolean);
  }

  Point layout(Composite paramComposite, boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean2)
  {
    if (this.numColumns < 1)
      return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
    Control[] arrayOfControl = paramComposite.getChildren();
    int i = 0;
    Control localControl1;
    GridData localGridData1;
    for (int j = 0; j < arrayOfControl.length; j++)
    {
      localControl1 = arrayOfControl[j];
      localGridData1 = (GridData)localControl1.getLayoutData();
      if ((localGridData1 == null) || (!localGridData1.exclude))
        arrayOfControl[(i++)] = arrayOfControl[j];
    }
    if (i == 0)
      return new Point(this.marginLeft + this.marginWidth * 2 + this.marginRight, this.marginTop + this.marginHeight * 2 + this.marginBottom);
    for (j = 0; j < i; j++)
    {
      localControl1 = arrayOfControl[j];
      localGridData1 = (GridData)localControl1.getLayoutData();
      if (localGridData1 == null)
        localControl1.setLayoutData(localGridData1 = new GridData());
      if (paramBoolean2)
        localGridData1.flushCache();
      localGridData1.computeSize(localControl1, localGridData1.widthHint, localGridData1.heightHint, paramBoolean2);
      if ((localGridData1.grabExcessHorizontalSpace) && (localGridData1.minimumWidth > 0) && (localGridData1.cacheWidth < localGridData1.minimumWidth))
      {
        n = 0;
        if ((localControl1 instanceof Scrollable))
        {
          localObject1 = ((Scrollable)localControl1).computeTrim(0, 0, 0, 0);
          n = ((Rectangle)localObject1).width;
        }
        else
        {
          n = localControl1.getBorderWidth() * 2;
        }
        localGridData1.cacheWidth = (localGridData1.cacheHeight = -1);
        localGridData1.computeSize(localControl1, Math.max(0, localGridData1.minimumWidth - n), localGridData1.heightHint, false);
      }
      if ((localGridData1.grabExcessVerticalSpace) && (localGridData1.minimumHeight > 0))
        localGridData1.cacheHeight = Math.max(localGridData1.cacheHeight, localGridData1.minimumHeight);
    }
    j = 0;
    int k = 0;
    int m = 0;
    int n = this.numColumns;
    Object localObject1 = new Control[4][n];
    for (int i1 = 0; i1 < i; i1++)
    {
      Control localControl2 = arrayOfControl[i1];
      localObject2 = (GridData)localControl2.getLayoutData();
      int i3 = Math.max(1, Math.min(((GridData)localObject2).horizontalSpan, n));
      int i4 = Math.max(1, ((GridData)localObject2).verticalSpan);
      while (true)
      {
        i5 = j + i4;
        if (i5 >= localObject1.length)
        {
          Control[][] arrayOfControl1 = new Control[i5 + 4][n];
          System.arraycopy(localObject1, 0, arrayOfControl1, 0, localObject1.length);
          localObject1 = arrayOfControl1;
        }
        if (localObject1[j] == null)
          localObject1[j] = new Control[n];
        while ((k < n) && (localObject1[j][k] != null))
          k++;
        i6 = k + i3;
        if (i6 <= n)
        {
          for (int i7 = k; (i7 < i6) && (localObject1[j][i7] == null); i7++);
          if (i7 == i6)
            break;
          k = i7;
        }
        if (k + i3 >= n)
        {
          k = 0;
          j++;
        }
      }
      for (i5 = 0; i5 < i4; i5++)
      {
        if (localObject1[(j + i5)] == null)
          localObject1[(j + i5)] = new Control[n];
        for (i6 = 0; i6 < i3; i6++)
          localObject1[(j + i5)][(k + i6)] = localControl2;
      }
      m = Math.max(m, j + i4);
      k += i3;
    }
    i1 = paramInt3 - this.horizontalSpacing * (n - 1) - (this.marginLeft + this.marginWidth * 2 + this.marginRight);
    int i2 = 0;
    Object localObject2 = new int[n];
    int[] arrayOfInt1 = new int[n];
    boolean[] arrayOfBoolean = new boolean[n];
    int i9;
    int i10;
    int i11;
    int i15;
    int i17;
    int i19;
    int i20;
    for (int i5 = 0; i5 < n; i5++)
    {
      GridData localGridData2;
      for (i6 = 0; i6 < m; i6++)
      {
        localGridData2 = getData((Control[][])localObject1, i6, i5, m, n, true);
        if (localGridData2 != null)
        {
          i9 = Math.max(1, Math.min(localGridData2.horizontalSpan, n));
          if (i9 == 1)
          {
            i10 = localGridData2.cacheWidth + localGridData2.horizontalIndent;
            localObject2[i5] = Math.max(localObject2[i5], i10);
            if (localGridData2.grabExcessHorizontalSpace)
            {
              if (arrayOfBoolean[i5] == 0)
                i2++;
              arrayOfBoolean[i5] = true;
            }
            if ((!localGridData2.grabExcessHorizontalSpace) || (localGridData2.minimumWidth != 0))
            {
              i10 = (!localGridData2.grabExcessHorizontalSpace) || (localGridData2.minimumWidth == -1) ? localGridData2.cacheWidth : localGridData2.minimumWidth;
              i10 += localGridData2.horizontalIndent;
              arrayOfInt1[i5] = Math.max(arrayOfInt1[i5], i10);
            }
          }
        }
      }
      for (i6 = 0; i6 < m; i6++)
      {
        localGridData2 = getData((Control[][])localObject1, i6, i5, m, n, false);
        if (localGridData2 != null)
        {
          i9 = Math.max(1, Math.min(localGridData2.horizontalSpan, n));
          if (i9 > 1)
          {
            i10 = 0;
            i11 = 0;
            i12 = 0;
            for (int i13 = 0; i13 < i9; i13++)
            {
              i10 += localObject2[(i5 - i13)];
              i11 += arrayOfInt1[(i5 - i13)];
              if (arrayOfBoolean[(i5 - i13)] != 0)
                i12++;
            }
            if ((localGridData2.grabExcessHorizontalSpace) && (i12 == 0))
            {
              i2++;
              arrayOfBoolean[i5] = true;
            }
            i13 = localGridData2.cacheWidth + localGridData2.horizontalIndent - i10 - (i9 - 1) * this.horizontalSpacing;
            if (i13 > 0)
              if (this.makeColumnsEqualWidth)
              {
                i15 = (i13 + i10) / i9;
                i17 = (i13 + i10) % i9;
                i19 = -1;
                for (i20 = 0; i20 < i9; i20++)
                {
                  int tmp1178_1177 = (i5 - i20);
                  i19 = tmp1178_1177;
                  localObject2[tmp1178_1177] = Math.max(i15, localObject2[(i5 - i20)]);
                }
                if (i19 > -1)
                  localObject2[i19] += i17;
              }
              else if (i12 == 0)
              {
                localObject2[i5] += i13;
              }
              else
              {
                i15 = i13 / i12;
                i17 = i13 % i12;
                i19 = -1;
                for (i20 = 0; i20 < i9; i20++)
                  if (arrayOfBoolean[(i5 - i20)] != 0)
                    localObject2[(i19 = i5 - i20)] += i15;
                if (i19 > -1)
                  localObject2[i19] += i17;
              }
            if ((!localGridData2.grabExcessHorizontalSpace) || (localGridData2.minimumWidth != 0))
            {
              i13 = (!localGridData2.grabExcessHorizontalSpace) || (localGridData2.minimumWidth == -1) ? localGridData2.cacheWidth : localGridData2.minimumWidth;
              i13 += localGridData2.horizontalIndent - i11 - (i9 - 1) * this.horizontalSpacing;
              if (i13 > 0)
                if (i12 == 0)
                {
                  arrayOfInt1[i5] += i13;
                }
                else
                {
                  i15 = i13 / i12;
                  i17 = i13 % i12;
                  i19 = -1;
                  for (i20 = 0; i20 < i9; i20++)
                    if (arrayOfBoolean[(i5 - i20)] != 0)
                      arrayOfInt1[(i19 = i5 - i20)] += i15;
                  if (i19 > -1)
                    arrayOfInt1[i19] += i17;
                }
            }
          }
        }
      }
    }
    int i21;
    int i22;
    int i24;
    int i25;
    if (this.makeColumnsEqualWidth)
    {
      i5 = 0;
      i6 = 0;
      for (i8 = 0; i8 < n; i8++)
      {
        i5 = Math.max(i5, arrayOfInt1[i8]);
        i6 = Math.max(i6, localObject2[i8]);
      }
      i6 = (paramInt3 == -1) || (i2 == 0) ? i6 : Math.max(i5, i1 / n);
      for (i8 = 0; i8 < n; i8++)
      {
        arrayOfBoolean[i8] = (i2 > 0 ? 1 : false);
        localObject2[i8] = i6;
      }
    }
    else if ((paramInt3 != -1) && (i2 > 0))
    {
      i5 = 0;
      for (i6 = 0; i6 < n; i6++)
        i5 += localObject2[i6];
      i6 = i2;
      i8 = (i1 - i5) / i6;
      i9 = (i1 - i5) % i6;
      for (i10 = -1; i5 != i1; i10 = -1)
      {
        for (i11 = 0; i11 < n; i11++)
          if (arrayOfBoolean[i11] != 0)
            if (localObject2[i11] + i8 > arrayOfInt1[i11])
            {
              int tmp1734_1732 = i11;
              i10 = tmp1734_1732;
              localObject2[tmp1734_1732] = (localObject2[i11] + i8);
            }
            else
            {
              localObject2[i11] = arrayOfInt1[i11];
              arrayOfBoolean[i11] = false;
              i6--;
            }
        if (i10 > -1)
          localObject2[i10] += i9;
        for (i11 = 0; i11 < n; i11++)
          for (i12 = 0; i12 < m; i12++)
          {
            GridData localGridData3 = getData((Control[][])localObject1, i12, i11, m, n, false);
            if (localGridData3 != null)
            {
              i15 = Math.max(1, Math.min(localGridData3.horizontalSpan, n));
              if ((i15 > 1) && ((!localGridData3.grabExcessHorizontalSpace) || (localGridData3.minimumWidth != 0)))
              {
                i17 = 0;
                i19 = 0;
                for (i20 = 0; i20 < i15; i20++)
                {
                  i17 += localObject2[(i11 - i20)];
                  if (arrayOfBoolean[(i11 - i20)] != 0)
                    i19++;
                }
                i20 = (!localGridData3.grabExcessHorizontalSpace) || (localGridData3.minimumWidth == -1) ? localGridData3.cacheWidth : localGridData3.minimumWidth;
                i20 += localGridData3.horizontalIndent - i17 - (i15 - 1) * this.horizontalSpacing;
                if (i20 > 0)
                  if (i19 == 0)
                  {
                    localObject2[i11] += i20;
                  }
                  else
                  {
                    i21 = i20 / i19;
                    i22 = i20 % i19;
                    i24 = -1;
                    for (i25 = 0; i25 < i15; i25++)
                      if (arrayOfBoolean[(i11 - i25)] != 0)
                        localObject2[(i24 = i11 - i25)] += i21;
                    if (i24 > -1)
                      localObject2[i24] += i22;
                  }
              }
            }
          }
        if (i6 == 0)
          break;
        i5 = 0;
        for (i11 = 0; i11 < n; i11++)
          i5 += localObject2[i11];
        i8 = (i1 - i5) / i6;
        i9 = (i1 - i5) % i6;
      }
    }
    GridData[] arrayOfGridData = (GridData[])null;
    int i6 = 0;
    if (paramInt3 != -1)
      for (i8 = 0; i8 < n; i8++)
        for (i9 = 0; i9 < m; i9++)
        {
          localObject3 = getData((Control[][])localObject1, i9, i8, m, n, false);
          if ((localObject3 != null) && (((GridData)localObject3).heightHint == -1))
          {
            localObject4 = localObject1[i9][i8];
            i12 = Math.max(1, Math.min(((GridData)localObject3).horizontalSpan, n));
            i14 = 0;
            for (i15 = 0; i15 < i12; i15++)
              i14 += localObject2[(i8 - i15)];
            i14 += (i12 - 1) * this.horizontalSpacing - ((GridData)localObject3).horizontalIndent;
            if (((i14 != ((GridData)localObject3).cacheWidth) && (((GridData)localObject3).horizontalAlignment == 4)) || (((GridData)localObject3).cacheWidth > i14))
            {
              i15 = 0;
              if ((localObject4 instanceof Scrollable))
              {
                Rectangle localRectangle = ((Scrollable)localObject4).computeTrim(0, 0, 0, 0);
                i15 = localRectangle.width;
              }
              else
              {
                i15 = ((Control)localObject4).getBorderWidth() * 2;
              }
              ((GridData)localObject3).cacheWidth = (((GridData)localObject3).cacheHeight = -1);
              ((GridData)localObject3).computeSize((Control)localObject4, Math.max(0, i14 - i15), ((GridData)localObject3).heightHint, false);
              if ((((GridData)localObject3).grabExcessVerticalSpace) && (((GridData)localObject3).minimumHeight > 0))
                ((GridData)localObject3).cacheHeight = Math.max(((GridData)localObject3).cacheHeight, ((GridData)localObject3).minimumHeight);
              if (arrayOfGridData == null)
                arrayOfGridData = new GridData[i];
              arrayOfGridData[(i6++)] = localObject3;
            }
          }
        }
    int i8 = paramInt4 - this.verticalSpacing * (m - 1) - (this.marginTop + this.marginHeight * 2 + this.marginBottom);
    i2 = 0;
    int[] arrayOfInt2 = new int[m];
    Object localObject3 = new int[m];
    Object localObject4 = new boolean[m];
    int i18;
    int i26;
    int i27;
    for (int i12 = 0; i12 < m; i12++)
    {
      GridData localGridData4;
      for (i14 = 0; i14 < n; i14++)
      {
        localGridData4 = getData((Control[][])localObject1, i12, i14, m, n, true);
        if (localGridData4 != null)
        {
          i18 = Math.max(1, Math.min(localGridData4.verticalSpan, m));
          if (i18 == 1)
          {
            i19 = localGridData4.cacheHeight + localGridData4.verticalIndent;
            arrayOfInt2[i12] = Math.max(arrayOfInt2[i12], i19);
            if (localGridData4.grabExcessVerticalSpace)
            {
              if (localObject4[i12] == 0)
                i2++;
              localObject4[i12] = 1;
            }
            if ((!localGridData4.grabExcessVerticalSpace) || (localGridData4.minimumHeight != 0))
            {
              i19 = (!localGridData4.grabExcessVerticalSpace) || (localGridData4.minimumHeight == -1) ? localGridData4.cacheHeight : localGridData4.minimumHeight;
              i19 += localGridData4.verticalIndent;
              localObject3[i12] = Math.max(localObject3[i12], i19);
            }
          }
        }
      }
      for (i14 = 0; i14 < n; i14++)
      {
        localGridData4 = getData((Control[][])localObject1, i12, i14, m, n, false);
        if (localGridData4 != null)
        {
          i18 = Math.max(1, Math.min(localGridData4.verticalSpan, m));
          if (i18 > 1)
          {
            i19 = 0;
            i20 = 0;
            i21 = 0;
            for (i22 = 0; i22 < i18; i22++)
            {
              i19 += arrayOfInt2[(i12 - i22)];
              i20 += localObject3[(i12 - i22)];
              if (localObject4[(i12 - i22)] != 0)
                i21++;
            }
            if ((localGridData4.grabExcessVerticalSpace) && (i21 == 0))
            {
              i2++;
              localObject4[i12] = 1;
            }
            i22 = localGridData4.cacheHeight + localGridData4.verticalIndent - i19 - (i18 - 1) * this.verticalSpacing;
            if (i22 > 0)
              if (i21 == 0)
              {
                arrayOfInt2[i12] += i22;
              }
              else
              {
                i24 = i22 / i21;
                i25 = i22 % i21;
                i26 = -1;
                for (i27 = 0; i27 < i18; i27++)
                  if (localObject4[(i12 - i27)] != 0)
                    arrayOfInt2[(i26 = i12 - i27)] += i24;
                if (i26 > -1)
                  arrayOfInt2[i26] += i25;
              }
            if ((!localGridData4.grabExcessVerticalSpace) || (localGridData4.minimumHeight != 0))
            {
              i22 = (!localGridData4.grabExcessVerticalSpace) || (localGridData4.minimumHeight == -1) ? localGridData4.cacheHeight : localGridData4.minimumHeight;
              i22 += localGridData4.verticalIndent - i20 - (i18 - 1) * this.verticalSpacing;
              if (i22 > 0)
                if (i21 == 0)
                {
                  localObject3[i12] += i22;
                }
                else
                {
                  i24 = i22 / i21;
                  i25 = i22 % i21;
                  i26 = -1;
                  for (i27 = 0; i27 < i18; i27++)
                    if (localObject4[(i12 - i27)] != 0)
                      localObject3[(i26 = i12 - i27)] += i24;
                  if (i26 > -1)
                    localObject3[i26] += i25;
                }
            }
          }
        }
      }
    }
    int i28;
    if ((paramInt4 != -1) && (i2 > 0))
    {
      i12 = 0;
      for (i14 = 0; i14 < m; i14++)
        i12 += arrayOfInt2[i14];
      i14 = i2;
      i16 = (i8 - i12) / i14;
      i18 = (i8 - i12) % i14;
      for (i19 = -1; i12 != i8; i19 = -1)
      {
        for (i20 = 0; i20 < m; i20++)
          if (localObject4[i20] != 0)
            if (arrayOfInt2[i20] + i16 > localObject3[i20])
            {
              int tmp3275_3273 = i20;
              i19 = tmp3275_3273;
              arrayOfInt2[tmp3275_3273] = (arrayOfInt2[i20] + i16);
            }
            else
            {
              arrayOfInt2[i20] = localObject3[i20];
              localObject4[i20] = 0;
              i14--;
            }
        if (i19 > -1)
          arrayOfInt2[i19] += i18;
        for (i20 = 0; i20 < m; i20++)
          for (i21 = 0; i21 < n; i21++)
          {
            GridData localGridData6 = getData((Control[][])localObject1, i20, i21, m, n, false);
            if (localGridData6 != null)
            {
              i24 = Math.max(1, Math.min(localGridData6.verticalSpan, m));
              if ((i24 > 1) && ((!localGridData6.grabExcessVerticalSpace) || (localGridData6.minimumHeight != 0)))
              {
                i25 = 0;
                i26 = 0;
                for (i27 = 0; i27 < i24; i27++)
                {
                  i25 += arrayOfInt2[(i20 - i27)];
                  if (localObject4[(i20 - i27)] != 0)
                    i26++;
                }
                i27 = (!localGridData6.grabExcessVerticalSpace) || (localGridData6.minimumHeight == -1) ? localGridData6.cacheHeight : localGridData6.minimumHeight;
                i27 += localGridData6.verticalIndent - i25 - (i24 - 1) * this.verticalSpacing;
                if (i27 > 0)
                  if (i26 == 0)
                  {
                    arrayOfInt2[i20] += i27;
                  }
                  else
                  {
                    i28 = i27 / i26;
                    int i29 = i27 % i26;
                    int i30 = -1;
                    for (int i31 = 0; i31 < i24; i31++)
                      if (localObject4[(i20 - i31)] != 0)
                        arrayOfInt2[(i30 = i20 - i31)] += i28;
                    if (i30 > -1)
                      arrayOfInt2[i30] += i29;
                  }
              }
            }
          }
        if (i14 == 0)
          break;
        i12 = 0;
        for (i20 = 0; i20 < m; i20++)
          i12 += arrayOfInt2[i20];
        i16 = (i8 - i12) / i14;
        i18 = (i8 - i12) % i14;
      }
    }
    if (paramBoolean1)
    {
      i12 = paramInt2 + this.marginTop + this.marginHeight;
      for (i14 = 0; i14 < m; i14++)
      {
        i16 = paramInt1 + this.marginLeft + this.marginWidth;
        for (i18 = 0; i18 < n; i18++)
        {
          GridData localGridData5 = getData((Control[][])localObject1, i14, i18, m, n, true);
          if (localGridData5 != null)
          {
            i20 = Math.max(1, Math.min(localGridData5.horizontalSpan, n));
            i21 = Math.max(1, localGridData5.verticalSpan);
            int i23 = 0;
            i24 = 0;
            for (i25 = 0; i25 < i20; i25++)
              i23 += localObject2[(i18 + i25)];
            for (i25 = 0; i25 < i21; i25++)
              i24 += arrayOfInt2[(i14 + i25)];
            i23 += this.horizontalSpacing * (i20 - 1);
            i25 = i16 + localGridData5.horizontalIndent;
            i26 = Math.min(localGridData5.cacheWidth, i23);
            switch (localGridData5.horizontalAlignment)
            {
            case 2:
            case 16777216:
              i25 += Math.max(0, (i23 - localGridData5.horizontalIndent - i26) / 2);
              break;
            case 3:
            case 131072:
            case 16777224:
              i25 += Math.max(0, i23 - localGridData5.horizontalIndent - i26);
              break;
            case 4:
              i26 = i23 - localGridData5.horizontalIndent;
            }
            i24 += this.verticalSpacing * (i21 - 1);
            i27 = i12 + localGridData5.verticalIndent;
            i28 = Math.min(localGridData5.cacheHeight, i24);
            switch (localGridData5.verticalAlignment)
            {
            case 2:
            case 16777216:
              i27 += Math.max(0, (i24 - localGridData5.verticalIndent - i28) / 2);
              break;
            case 3:
            case 1024:
            case 16777224:
              i27 += Math.max(0, i24 - localGridData5.verticalIndent - i28);
              break;
            case 4:
              i28 = i24 - localGridData5.verticalIndent;
            }
            Object localObject5 = localObject1[i14][i18];
            if (localObject5 != null)
              localObject5.setBounds(i25, i27, i26, i28);
          }
          i16 += localObject2[i18] + this.horizontalSpacing;
        }
        i12 += arrayOfInt2[i14] + this.verticalSpacing;
      }
    }
    for (i12 = 0; i12 < i6; i12++)
      arrayOfGridData[i12].cacheWidth = (arrayOfGridData[i12].cacheHeight = -1);
    i12 = 0;
    int i14 = 0;
    for (int i16 = 0; i16 < n; i16++)
      i12 += localObject2[i16];
    for (i16 = 0; i16 < m; i16++)
      i14 += arrayOfInt2[i16];
    i12 += this.horizontalSpacing * (n - 1) + this.marginLeft + this.marginWidth * 2 + this.marginRight;
    i14 += this.verticalSpacing * (m - 1) + this.marginTop + this.marginHeight * 2 + this.marginBottom;
    return new Point(i12, i14);
  }

  String getName()
  {
    String str = getClass().getName();
    int i = str.lastIndexOf('.');
    if (i == -1)
      return str;
    return str.substring(i + 1, str.length());
  }

  public String toString()
  {
    String str = getName() + " {";
    if (this.numColumns != 1)
      str = str + "numColumns=" + this.numColumns + " ";
    if (this.makeColumnsEqualWidth)
      str = str + "makeColumnsEqualWidth=" + this.makeColumnsEqualWidth + " ";
    if (this.marginWidth != 0)
      str = str + "marginWidth=" + this.marginWidth + " ";
    if (this.marginHeight != 0)
      str = str + "marginHeight=" + this.marginHeight + " ";
    if (this.marginLeft != 0)
      str = str + "marginLeft=" + this.marginLeft + " ";
    if (this.marginRight != 0)
      str = str + "marginRight=" + this.marginRight + " ";
    if (this.marginTop != 0)
      str = str + "marginTop=" + this.marginTop + " ";
    if (this.marginBottom != 0)
      str = str + "marginBottom=" + this.marginBottom + " ";
    if (this.horizontalSpacing != 0)
      str = str + "horizontalSpacing=" + this.horizontalSpacing + " ";
    if (this.verticalSpacing != 0)
      str = str + "verticalSpacing=" + this.verticalSpacing + " ";
    str = str.trim();
    str = str + "}";
    return str;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.layout.GridLayout
 * JD-Core Version:    0.6.2
 */