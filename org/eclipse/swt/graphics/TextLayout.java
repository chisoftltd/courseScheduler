package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.Callback;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.gdip.Gdip;
import org.eclipse.swt.internal.gdip.Rect;
import org.eclipse.swt.internal.win32.EMR;
import org.eclipse.swt.internal.win32.EMREXTCREATEFONTINDIRECTW;
import org.eclipse.swt.internal.win32.EXTLOGFONTW;
import org.eclipse.swt.internal.win32.LOGBRUSH;
import org.eclipse.swt.internal.win32.LOGFONT;
import org.eclipse.swt.internal.win32.LOGFONTA;
import org.eclipse.swt.internal.win32.LOGFONTW;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.internal.win32.OUTLINETEXTMETRIC;
import org.eclipse.swt.internal.win32.OUTLINETEXTMETRICA;
import org.eclipse.swt.internal.win32.OUTLINETEXTMETRICW;
import org.eclipse.swt.internal.win32.RECT;
import org.eclipse.swt.internal.win32.SCRIPT_ANALYSIS;
import org.eclipse.swt.internal.win32.SCRIPT_CONTROL;
import org.eclipse.swt.internal.win32.SCRIPT_FONTPROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_ITEM;
import org.eclipse.swt.internal.win32.SCRIPT_LOGATTR;
import org.eclipse.swt.internal.win32.SCRIPT_PROPERTIES;
import org.eclipse.swt.internal.win32.SCRIPT_STATE;
import org.eclipse.swt.internal.win32.TEXTMETRIC;
import org.eclipse.swt.internal.win32.TEXTMETRICA;
import org.eclipse.swt.internal.win32.TEXTMETRICW;

public final class TextLayout extends Resource
{
  Font font;
  String text;
  String segmentsText;
  int lineSpacing = 0;
  int ascent;
  int descent;
  int alignment;
  int wrapWidth = this.ascent = this.descent = -1;
  int orientation = 33554432;
  int indent;
  int wrapIndent;
  boolean justify;
  int[] tabs;
  int[] segments;
  char[] segmentsChars;
  StyleItem[] styles = new StyleItem[2];
  int stylesCount;
  StyleItem[] allRuns;
  StyleItem[][] runs;
  int[] lineOffset;
  int[] lineY;
  int[] lineWidth;
  int mLangFontLink2;
  static final char LTR_MARK = '‎';
  static final char RTL_MARK = '‏';
  static final int SCRIPT_VISATTR_SIZEOF = 2;
  static final int GOFFSET_SIZEOF = 8;
  static final byte[] CLSID_CMultiLanguage = new byte[16];
  static final byte[] IID_IMLangFontLink2 = new byte[16];
  static final int MERGE_MAX = 512;
  static final int TOO_MANY_RUNS = 1024;
  static final int UNDERLINE_IME_DOT = 65536;
  static final int UNDERLINE_IME_DASH = 131072;
  static final int UNDERLINE_IME_THICK = 196608;

  static
  {
    OS.IIDFromString("".toCharArray(), CLSID_CMultiLanguage);
    OS.IIDFromString("".toCharArray(), IID_IMLangFontLink2);
  }

  public TextLayout(Device paramDevice)
  {
    super(paramDevice);
    this.styles[0] = new StyleItem();
    this.styles[1] = new StyleItem();
    this.stylesCount = 2;
    this.text = "";
    int[] arrayOfInt = new int[1];
    OS.OleInitialize(0);
    if (OS.CoCreateInstance(CLSID_CMultiLanguage, 0, 1, IID_IMLangFontLink2, arrayOfInt) == 0)
      this.mLangFontLink2 = arrayOfInt[0];
    init();
  }

  RECT addClipRect(StyleItem paramStyleItem, RECT paramRECT1, RECT paramRECT2, int paramInt1, int paramInt2)
  {
    if (paramRECT2 != null)
    {
      if (paramRECT1 == null)
      {
        paramRECT1 = new RECT();
        OS.SetRect(paramRECT1, -1, paramRECT2.top, -1, paramRECT2.bottom);
      }
      int i = (this.orientation & 0x4000000) != 0 ? 1 : 0;
      if ((paramStyleItem.start <= paramInt1) && (paramInt1 <= paramStyleItem.start + paramStyleItem.length))
        if ((paramStyleItem.analysis.fRTL ^ i))
          paramRECT1.right = paramRECT2.left;
        else
          paramRECT1.left = paramRECT2.left;
      if ((paramStyleItem.start <= paramInt2) && (paramInt2 <= paramStyleItem.start + paramStyleItem.length))
        if ((paramStyleItem.analysis.fRTL ^ i))
          paramRECT1.left = paramRECT2.right;
        else
          paramRECT1.right = paramRECT2.right;
    }
    return paramRECT1;
  }

  void breakRun(StyleItem paramStyleItem)
  {
    if (paramStyleItem.psla != 0)
      return;
    char[] arrayOfChar = new char[paramStyleItem.length];
    this.segmentsText.getChars(paramStyleItem.start, paramStyleItem.start + paramStyleItem.length, arrayOfChar, 0);
    int i = OS.GetProcessHeap();
    paramStyleItem.psla = OS.HeapAlloc(i, 8, SCRIPT_LOGATTR.sizeof * arrayOfChar.length);
    if (paramStyleItem.psla == 0)
      SWT.error(2);
    OS.ScriptBreak(arrayOfChar, arrayOfChar.length, paramStyleItem.analysis, paramStyleItem.psla);
  }

  void checkLayout()
  {
    if (isDisposed())
      SWT.error(44);
  }

  void computeRuns(GC paramGC)
  {
    if (this.runs != null)
      return;
    int i = paramGC != null ? paramGC.handle : this.device.internal_new_GC(null);
    int j = OS.CreateCompatibleDC(i);
    this.allRuns = itemize();
    for (int k = 0; k < this.allRuns.length - 1; k++)
    {
      localObject1 = this.allRuns[k];
      OS.SelectObject(j, getItemFont((StyleItem)localObject1));
      shape(j, (StyleItem)localObject1);
    }
    SCRIPT_LOGATTR localSCRIPT_LOGATTR = new SCRIPT_LOGATTR();
    Object localObject1 = new SCRIPT_PROPERTIES();
    int m = this.indent;
    int n = 0;
    int i1 = 1;
    Object localObject3;
    Object localObject2;
    for (int i2 = 0; i2 < this.allRuns.length - 1; i2++)
    {
      StyleItem localStyleItem1 = this.allRuns[i2];
      int i7;
      int i9;
      if ((this.tabs != null) && (localStyleItem1.tab))
      {
        i4 = this.tabs.length;
        for (int i5 = 0; i5 < i4; i5++)
          if (this.tabs[i5] > m)
          {
            localStyleItem1.width = (this.tabs[i5] - m);
            break;
          }
        if (i5 == i4)
        {
          i7 = this.tabs[(i4 - 1)];
          i8 = i4 > 1 ? this.tabs[(i4 - 1)] - this.tabs[(i4 - 2)] : this.tabs[0];
          if (i8 > 0)
          {
            while (i7 <= m)
              i7 += i8;
            localStyleItem1.width = (i7 - m);
          }
        }
        i7 = localStyleItem1.length;
        if (i7 > 1)
        {
          i8 = i5 + i7 - 1;
          if (i8 < i4)
          {
            localStyleItem1.width += this.tabs[i8] - this.tabs[i5];
          }
          else
          {
            if (i5 < i4)
            {
              localStyleItem1.width += this.tabs[(i4 - 1)] - this.tabs[i5];
              i7 -= i4 - 1 - i5;
            }
            i9 = i4 > 1 ? this.tabs[(i4 - 1)] - this.tabs[(i4 - 2)] : this.tabs[0];
            localStyleItem1.width += i9 * (i7 - 1);
          }
        }
      }
      if ((this.wrapWidth != -1) && (m + localStyleItem1.width > this.wrapWidth) && (!localStyleItem1.tab) && (!localStyleItem1.lineBreak))
      {
        i4 = 0;
        int[] arrayOfInt = new int[localStyleItem1.length];
        if ((localStyleItem1.style != null) && (localStyleItem1.style.metrics != null))
          arrayOfInt[0] = localStyleItem1.width;
        else
          OS.ScriptGetLogicalWidths(localStyleItem1.analysis, localStyleItem1.length, localStyleItem1.glyphCount, localStyleItem1.advances, localStyleItem1.clusters, localStyleItem1.visAttrs, arrayOfInt);
        i7 = 0;
        i8 = this.wrapWidth - m;
        while (i7 + arrayOfInt[i4] < i8)
          i7 += arrayOfInt[(i4++)];
        i9 = i4;
        int i10 = i2;
        while (i2 >= n)
        {
          breakRun(localStyleItem1);
          while (i4 >= 0)
          {
            OS.MoveMemory(localSCRIPT_LOGATTR, localStyleItem1.psla + i4 * SCRIPT_LOGATTR.sizeof, SCRIPT_LOGATTR.sizeof);
            if ((localSCRIPT_LOGATTR.fSoftBreak) || (localSCRIPT_LOGATTR.fWhiteSpace))
              break;
            i4--;
          }
          if ((i4 == 0) && (i2 != n) && (!localStyleItem1.tab) && (localSCRIPT_LOGATTR.fSoftBreak) && (!localSCRIPT_LOGATTR.fWhiteSpace))
          {
            OS.MoveMemory((SCRIPT_PROPERTIES)localObject1, this.device.scripts[localStyleItem1.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
            int i12 = ((SCRIPT_PROPERTIES)localObject1).langid;
            localObject3 = this.allRuns[(i2 - 1)];
            OS.MoveMemory((SCRIPT_PROPERTIES)localObject1, this.device.scripts[localObject3.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
            if ((((SCRIPT_PROPERTIES)localObject1).langid == i12) || (i12 == 0) || (((SCRIPT_PROPERTIES)localObject1).langid == 0))
            {
              breakRun((StyleItem)localObject3);
              OS.MoveMemory(localSCRIPT_LOGATTR, ((StyleItem)localObject3).psla + (((StyleItem)localObject3).length - 1) * SCRIPT_LOGATTR.sizeof, SCRIPT_LOGATTR.sizeof);
              if (!localSCRIPT_LOGATTR.fWhiteSpace)
                i4 = -1;
            }
          }
          if ((i4 >= 0) || (i2 == n))
            break;
          localStyleItem1 = this.allRuns[(--i2)];
          i4 = localStyleItem1.length - 1;
        }
        if ((i4 == 0) && (i2 != n) && (!localStyleItem1.tab))
          localStyleItem1 = this.allRuns[(--i2)];
        else if ((i4 <= 0) && (i2 == n))
          if ((m == this.wrapWidth) && (i10 > 0))
          {
            i2 = i10 - 1;
            localStyleItem1 = this.allRuns[i2];
            i4 = localStyleItem1.length;
          }
          else
          {
            i2 = i10;
            localStyleItem1 = this.allRuns[i2];
            i4 = Math.max(1, i9);
          }
        breakRun(localStyleItem1);
        while (i4 < localStyleItem1.length)
        {
          OS.MoveMemory(localSCRIPT_LOGATTR, localStyleItem1.psla + i4 * SCRIPT_LOGATTR.sizeof, SCRIPT_LOGATTR.sizeof);
          if (!localSCRIPT_LOGATTR.fWhiteSpace)
            break;
          i4++;
        }
        if ((i4 > 0) && (i4 < localStyleItem1.length))
        {
          localObject2 = new StyleItem();
          ((StyleItem)localObject2).start = (localStyleItem1.start + i4);
          ((StyleItem)localObject2).length = (localStyleItem1.length - i4);
          ((StyleItem)localObject2).style = localStyleItem1.style;
          ((StyleItem)localObject2).analysis = cloneScriptAnalysis(localStyleItem1.analysis);
          localStyleItem1.free();
          localStyleItem1.length = i4;
          OS.SelectObject(j, getItemFont(localStyleItem1));
          localStyleItem1.analysis.fNoGlyphIndex = false;
          shape(j, localStyleItem1);
          OS.SelectObject(j, getItemFont((StyleItem)localObject2));
          ((StyleItem)localObject2).analysis.fNoGlyphIndex = false;
          shape(j, (StyleItem)localObject2);
          localObject3 = new StyleItem[this.allRuns.length + 1];
          System.arraycopy(this.allRuns, 0, localObject3, 0, i2 + 1);
          System.arraycopy(this.allRuns, i2 + 1, localObject3, i2 + 2, this.allRuns.length - i2 - 1);
          this.allRuns = ((StyleItem[])localObject3);
          this.allRuns[(i2 + 1)] = localObject2;
        }
        if (i2 != this.allRuns.length - 2)
          localStyleItem1.softBreak = (localStyleItem1.lineBreak = 1);
      }
      m += localStyleItem1.width;
      if (localStyleItem1.lineBreak)
      {
        n = i2 + 1;
        m = localStyleItem1.softBreak ? this.wrapIndent : this.indent;
        i1++;
      }
    }
    m = 0;
    this.runs = new StyleItem[i1][];
    this.lineOffset = new int[i1 + 1];
    this.lineY = new int[i1 + 1];
    this.lineWidth = new int[i1];
    i2 = 0;
    int i3 = 0;
    int i4 = Math.max(0, this.ascent);
    int i6 = Math.max(0, this.descent);
    StyleItem[] arrayOfStyleItem = new StyleItem[this.allRuns.length];
    for (int i8 = 0; i8 < this.allRuns.length; i8++)
    {
      StyleItem localStyleItem2 = this.allRuns[i8];
      arrayOfStyleItem[(i2++)] = localStyleItem2;
      m += localStyleItem2.width;
      i4 = Math.max(i4, localStyleItem2.ascent);
      i6 = Math.max(i6, localStyleItem2.descent);
      if ((localStyleItem2.lineBreak) || (i8 == this.allRuns.length - 1))
      {
        if ((i2 == 1) && ((i8 == this.allRuns.length - 1) || (!localStyleItem2.softBreak)))
        {
          TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
          OS.SelectObject(j, getItemFont(localStyleItem2));
          OS.GetTextMetrics(j, localTEXTMETRICA);
          localStyleItem2.ascent = localTEXTMETRICA.tmAscent;
          localStyleItem2.descent = localTEXTMETRICA.tmDescent;
          i4 = Math.max(i4, localStyleItem2.ascent);
          i6 = Math.max(i6, localStyleItem2.descent);
        }
        this.runs[i3] = new StyleItem[i2];
        System.arraycopy(arrayOfStyleItem, 0, this.runs[i3], 0, i2);
        if ((this.justify) && (this.wrapWidth != -1) && (localStyleItem2.softBreak) && (m > 0))
        {
          int i11 = this.wrapIndent;
          if (i3 == 0)
          {
            i11 = this.indent;
          }
          else
          {
            localObject2 = this.runs[(i3 - 1)];
            localObject3 = localObject2[(localObject2.length - 1)];
            if ((((StyleItem)localObject3).lineBreak) && (!((StyleItem)localObject3).softBreak))
              i11 = this.indent;
          }
          m += i11;
          i13 = OS.GetProcessHeap();
          i14 = 0;
          for (int i15 = 0; i15 < this.runs[i3].length; i15++)
          {
            StyleItem localStyleItem4 = this.runs[i3][i15];
            int i16 = localStyleItem4.width * this.wrapWidth / m;
            if (i16 != localStyleItem4.width)
            {
              localStyleItem4.justify = OS.HeapAlloc(i13, 8, localStyleItem4.glyphCount * 4);
              if (localStyleItem4.justify == 0)
                SWT.error(2);
              OS.ScriptJustify(localStyleItem4.visAttrs, localStyleItem4.advances, localStyleItem4.glyphCount, i16 - localStyleItem4.width, 2, localStyleItem4.justify);
              localStyleItem4.width = i16;
            }
            i14 += localStyleItem4.width;
          }
          m = i14;
        }
        this.lineWidth[i3] = m;
        StyleItem localStyleItem3 = this.runs[i3][(i2 - 1)];
        int i13 = localStyleItem3.start + localStyleItem3.length;
        this.runs[i3] = reorder(this.runs[i3], i8 == this.allRuns.length - 1 ? 1 : false);
        localStyleItem3 = this.runs[i3][(i2 - 1)];
        if ((localStyleItem2.softBreak) && (localStyleItem2 != localStyleItem3))
        {
          localStyleItem2.softBreak = (localStyleItem2.lineBreak = 0);
          localStyleItem3.softBreak = (localStyleItem3.lineBreak = 1);
        }
        m = getLineIndent(i3);
        for (int i14 = 0; i14 < this.runs[i3].length; i14++)
        {
          this.runs[i3][i14].x = m;
          m += this.runs[i3][i14].width;
        }
        i3++;
        this.lineY[i3] = (this.lineY[(i3 - 1)] + i4 + i6 + this.lineSpacing);
        this.lineOffset[i3] = i13;
        i2 = m = 0;
        i4 = Math.max(0, this.ascent);
        i6 = Math.max(0, this.descent);
      }
    }
    if (j != 0)
      OS.DeleteDC(j);
    if (paramGC == null)
      this.device.internal_dispose_GC(i, null);
  }

  void destroy()
  {
    freeRuns();
    this.font = null;
    this.text = null;
    this.segmentsText = null;
    this.tabs = null;
    this.styles = null;
    this.runs = null;
    this.lineOffset = null;
    this.lineY = null;
    this.lineWidth = null;
    this.segments = null;
    this.segmentsChars = null;
    if (this.mLangFontLink2 != 0)
    {
      OS.VtblCall(2, this.mLangFontLink2);
      this.mLangFontLink2 = 0;
    }
    OS.OleUninitialize();
  }

  SCRIPT_ANALYSIS cloneScriptAnalysis(SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS)
  {
    SCRIPT_ANALYSIS localSCRIPT_ANALYSIS = new SCRIPT_ANALYSIS();
    localSCRIPT_ANALYSIS.eScript = paramSCRIPT_ANALYSIS.eScript;
    localSCRIPT_ANALYSIS.fRTL = paramSCRIPT_ANALYSIS.fRTL;
    localSCRIPT_ANALYSIS.fLayoutRTL = paramSCRIPT_ANALYSIS.fLayoutRTL;
    localSCRIPT_ANALYSIS.fLinkBefore = paramSCRIPT_ANALYSIS.fLinkBefore;
    localSCRIPT_ANALYSIS.fLinkAfter = paramSCRIPT_ANALYSIS.fLinkAfter;
    localSCRIPT_ANALYSIS.fLogicalOrder = paramSCRIPT_ANALYSIS.fLogicalOrder;
    localSCRIPT_ANALYSIS.fNoGlyphIndex = paramSCRIPT_ANALYSIS.fNoGlyphIndex;
    localSCRIPT_ANALYSIS.s = new SCRIPT_STATE();
    localSCRIPT_ANALYSIS.s.uBidiLevel = paramSCRIPT_ANALYSIS.s.uBidiLevel;
    localSCRIPT_ANALYSIS.s.fOverrideDirection = paramSCRIPT_ANALYSIS.s.fOverrideDirection;
    localSCRIPT_ANALYSIS.s.fInhibitSymSwap = paramSCRIPT_ANALYSIS.s.fInhibitSymSwap;
    localSCRIPT_ANALYSIS.s.fCharShape = paramSCRIPT_ANALYSIS.s.fCharShape;
    localSCRIPT_ANALYSIS.s.fDigitSubstitute = paramSCRIPT_ANALYSIS.s.fDigitSubstitute;
    localSCRIPT_ANALYSIS.s.fInhibitLigate = paramSCRIPT_ANALYSIS.s.fInhibitLigate;
    localSCRIPT_ANALYSIS.s.fDisplayZWG = paramSCRIPT_ANALYSIS.s.fDisplayZWG;
    localSCRIPT_ANALYSIS.s.fArabicNumContext = paramSCRIPT_ANALYSIS.s.fArabicNumContext;
    localSCRIPT_ANALYSIS.s.fGcpClusters = paramSCRIPT_ANALYSIS.s.fGcpClusters;
    localSCRIPT_ANALYSIS.s.fReserved = paramSCRIPT_ANALYSIS.s.fReserved;
    localSCRIPT_ANALYSIS.s.fEngineReserved = paramSCRIPT_ANALYSIS.s.fEngineReserved;
    return localSCRIPT_ANALYSIS;
  }

  int[] computePolyline(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt4 - paramInt2;
    int j = 2 * i;
    int k = Compatibility.ceil(paramInt3 - paramInt1, j);
    if ((k == 0) && (paramInt3 - paramInt1 > 2))
      k = 1;
    int m = (2 * k + 1) * 2;
    if (m < 0)
      return new int[0];
    int[] arrayOfInt = new int[m];
    for (int n = 0; n < k; n++)
    {
      int i1 = 4 * n;
      arrayOfInt[i1] = (paramInt1 + j * n);
      arrayOfInt[(i1 + 1)] = paramInt4;
      arrayOfInt[(i1 + 2)] = (arrayOfInt[i1] + j / 2);
      arrayOfInt[(i1 + 3)] = paramInt2;
    }
    arrayOfInt[(m - 2)] = (paramInt1 + j * k);
    arrayOfInt[(m - 1)] = paramInt4;
    return arrayOfInt;
  }

  int createGdipBrush(int paramInt1, int paramInt2)
  {
    int i = (paramInt2 & 0xFF) << 24 | paramInt1 >> 16 & 0xFF | paramInt1 & 0xFF00 | (paramInt1 & 0xFF) << 16;
    int j = Gdip.Color_new(i);
    int k = Gdip.SolidBrush_new(j);
    Gdip.Color_delete(j);
    return k;
  }

  int createGdipBrush(Color paramColor, int paramInt)
  {
    return createGdipBrush(paramColor.handle, paramInt);
  }

  public void draw(GC paramGC, int paramInt1, int paramInt2)
  {
    draw(paramGC, paramInt1, paramInt2, -1, -1, null, null);
  }

  public void draw(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2)
  {
    draw(paramGC, paramInt1, paramInt2, paramInt3, paramInt4, paramColor1, paramColor2, 0);
  }

  public void draw(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Color paramColor1, Color paramColor2, int paramInt5)
  {
    checkLayout();
    computeRuns(paramGC);
    if (paramGC == null)
      SWT.error(4);
    if (paramGC.isDisposed())
      SWT.error(5);
    if ((paramColor1 != null) && (paramColor1.isDisposed()))
      SWT.error(5);
    if ((paramColor2 != null) && (paramColor2.isDisposed()))
      SWT.error(5);
    int i = this.text.length();
    if ((i == 0) && (paramInt5 == 0))
      return;
    int j = paramGC.handle;
    Rectangle localRectangle = paramGC.getClipping();
    GCData localGCData = paramGC.data;
    int k = localGCData.gdipGraphics;
    int m = localGCData.foreground;
    int n = OS.GetSysColor(OS.COLOR_HOTLIGHT);
    int i1 = localGCData.alpha;
    int i2 = k != 0 ? 1 : 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    if (i2 != 0)
    {
      paramGC.checkGC(1);
      i3 = paramGC.getFgBrush();
    }
    else
    {
      i5 = OS.SaveDC(j);
      if ((localGCData.style & 0x8000000) != 0)
        OS.SetLayout(j, OS.GetLayout(j) | 0x1);
    }
    boolean bool = (paramInt3 <= paramInt4) && (paramInt3 != -1) && (paramInt4 != -1);
    int i6 = 0;
    int i7 = 0;
    int i8 = 0;
    int i9 = 0;
    int i10 = 0;
    int i11 = 0;
    if ((bool) || ((paramInt5 & 0x100000) != 0))
    {
      int i12 = paramColor1 != null ? paramColor1.handle : OS.GetSysColor(OS.COLOR_HIGHLIGHTTEXT);
      i13 = paramColor2 != null ? paramColor2.handle : OS.GetSysColor(OS.COLOR_HIGHLIGHT);
      if (i2 != 0)
      {
        i6 = createGdipBrush(i13, i1);
        i7 = createGdipBrush(i12, i1);
      }
      else
      {
        i10 = OS.CreateSolidBrush(i13);
        i11 = i12;
      }
      if (bool)
      {
        paramInt3 = translateOffset(Math.min(Math.max(0, paramInt3), i - 1));
        paramInt4 = translateOffset(Math.min(Math.max(0, paramInt4), i - 1));
      }
    }
    RECT localRECT1 = new RECT();
    OS.SetBkMode(j, 1);
    for (int i13 = 0; i13 < this.runs.length; i13++)
    {
      int i14 = paramInt1 + getLineIndent(i13);
      int i15 = paramInt2 + this.lineY[i13];
      StyleItem[] arrayOfStyleItem = this.runs[i13];
      int i16 = this.lineY[(i13 + 1)] - this.lineY[i13] - this.lineSpacing;
      int i17;
      int i18;
      if ((paramInt5 != 0) && ((bool) || ((paramInt5 & 0x100000) != 0)))
      {
        i17 = 0;
        if ((i13 == this.runs.length - 1) && ((paramInt5 & 0x100000) != 0))
        {
          i17 = 1;
        }
        else
        {
          StyleItem localStyleItem1 = arrayOfStyleItem[(arrayOfStyleItem.length - 1)];
          if ((localStyleItem1.lineBreak) && (!localStyleItem1.softBreak))
          {
            if ((paramInt3 <= localStyleItem1.start) && (localStyleItem1.start <= paramInt4))
              i17 = 1;
          }
          else
          {
            int i19 = localStyleItem1.start + localStyleItem1.length - 1;
            if ((paramInt3 <= i19) && (i19 < paramInt4) && ((paramInt5 & 0x10000) != 0))
              i17 = 1;
          }
        }
        if (i17 != 0)
        {
          if ((paramInt5 & 0x10000) != 0)
            i18 = OS.IsWin95 ? 32767 : 117440511;
          else
            i18 = i16 / 3;
          if (i2 != 0)
          {
            Gdip.Graphics_FillRectangle(k, i6, i14 + this.lineWidth[i13], i15, i18, i16);
          }
          else
          {
            OS.SelectObject(j, i10);
            OS.PatBlt(j, i14 + this.lineWidth[i13], i15, i18, i16, 15728673);
          }
        }
      }
      if ((i14 <= localRectangle.x + localRectangle.width) && (i14 + this.lineWidth[i13] >= localRectangle.x))
      {
        i17 = i14;
        for (i18 = 0; i18 < arrayOfStyleItem.length; i18++)
        {
          StyleItem localStyleItem2 = arrayOfStyleItem[i18];
          if (localStyleItem2.length != 0)
          {
            if (i14 > localRectangle.x + localRectangle.width)
              break;
            if ((i14 + localStyleItem2.width >= localRectangle.x) && ((!localStyleItem2.lineBreak) || (localStyleItem2.softBreak)))
            {
              OS.SetRect(localRECT1, i14, i15, i14 + localStyleItem2.width, i15 + i16);
              if (i2 != 0)
                drawRunBackgroundGDIP(localStyleItem2, k, localRECT1, paramInt3, paramInt4, i1, i6, bool);
              else
                drawRunBackground(localStyleItem2, j, localRECT1, paramInt3, paramInt4, i10, bool);
            }
            i14 += localStyleItem2.width;
          }
        }
        i18 = Math.max(0, this.ascent);
        int i20 = 0;
        for (int i21 = 0; i21 < arrayOfStyleItem.length; i21++)
        {
          i18 = Math.max(i18, arrayOfStyleItem[i21].ascent);
          i20 = Math.min(i20, arrayOfStyleItem[i21].underlinePos);
        }
        RECT localRECT2 = null;
        RECT localRECT3 = null;
        RECT localRECT4 = null;
        RECT localRECT5 = null;
        i14 = i17;
        for (int i22 = 0; i22 < arrayOfStyleItem.length; i22++)
        {
          StyleItem localStyleItem3 = arrayOfStyleItem[i22];
          TextStyle localTextStyle = localStyleItem3.style;
          int i23 = (localTextStyle != null) && ((localTextStyle.underline) || (localTextStyle.strikeout) || (localTextStyle.borderStyle != 0)) ? 1 : 0;
          if (localStyleItem3.length != 0)
          {
            if ((i14 > localRectangle.x + localRectangle.width) && (i23 == 0))
              break;
            if ((i14 + localStyleItem3.width >= localRectangle.x) || (i23 != 0))
            {
              int i24 = (localStyleItem3.tab) && (i23 == 0) ? 1 : 0;
              if ((i24 == 0) && ((!localStyleItem3.lineBreak) || (localStyleItem3.softBreak)) && ((localTextStyle == null) || (localTextStyle.metrics == null)))
              {
                OS.SetRect(localRECT1, i14, i15, i14 + localStyleItem3.width, i15 + i16);
                int i25;
                if (i2 != 0)
                {
                  i25 = getItemFont(localStyleItem3);
                  if (i25 != i9)
                  {
                    i9 = i25;
                    if (i8 != 0)
                      Gdip.Font_delete(i8);
                    i26 = OS.SelectObject(j, i25);
                    i8 = Gdip.Font_new(j, i25);
                    OS.SelectObject(j, i26);
                    if (i8 == 0)
                      SWT.error(2);
                    if (!Gdip.Font_IsAvailable(i8))
                    {
                      Gdip.Font_delete(i8);
                      i8 = 0;
                    }
                  }
                  int i26 = i3;
                  if ((localTextStyle != null) && (localTextStyle.underline) && (localTextStyle.underlineStyle == 4))
                  {
                    if (i4 == 0)
                      i4 = createGdipBrush(n, i1);
                    i26 = i4;
                  }
                  if ((i8 != 0) && (!localStyleItem3.analysis.fNoGlyphIndex))
                  {
                    localRECT5 = drawRunTextGDIP(k, localStyleItem3, localRECT1, i8, i18, i26, i7, paramInt3, paramInt4, i1);
                  }
                  else
                  {
                    int i27 = (localTextStyle != null) && (localTextStyle.underline) && (localTextStyle.underlineStyle == 4) ? n : m;
                    localRECT5 = drawRunTextGDIPRaster(k, localStyleItem3, localRECT1, i18, i27, i11, paramInt3, paramInt4);
                  }
                  localRECT3 = drawUnderlineGDIP(k, paramInt1, i15 + i18, i20, i15 + i16, arrayOfStyleItem, i22, i26, i7, localRECT3, localRECT5, paramInt3, paramInt4, i1);
                  localRECT4 = drawStrikeoutGDIP(k, paramInt1, i15 + i18, arrayOfStyleItem, i22, i26, i7, localRECT4, localRECT5, paramInt3, paramInt4, i1);
                  localRECT2 = drawBorderGDIP(k, paramInt1, i15, i16, arrayOfStyleItem, i22, i26, i7, localRECT2, localRECT5, paramInt3, paramInt4, i1);
                }
                else
                {
                  i25 = (localTextStyle != null) && (localTextStyle.underline) && (localTextStyle.underlineStyle == 4) ? n : m;
                  localRECT5 = drawRunText(j, localStyleItem3, localRECT1, i18, i25, i11, paramInt3, paramInt4);
                  localRECT3 = drawUnderline(j, paramInt1, i15 + i18, i20, i15 + i16, arrayOfStyleItem, i22, i25, i11, localRECT3, localRECT5, paramInt3, paramInt4);
                  localRECT4 = drawStrikeout(j, paramInt1, i15 + i18, arrayOfStyleItem, i22, i25, i11, localRECT4, localRECT5, paramInt3, paramInt4);
                  localRECT2 = drawBorder(j, paramInt1, i15, i16, arrayOfStyleItem, i22, i25, i11, localRECT2, localRECT5, paramInt3, paramInt4);
                }
              }
            }
            i14 += localStyleItem3.width;
          }
        }
      }
    }
    if (i6 != 0)
      Gdip.SolidBrush_delete(i6);
    if (i7 != 0)
      Gdip.SolidBrush_delete(i7);
    if (i4 != 0)
      Gdip.SolidBrush_delete(i4);
    if (i8 != 0)
      Gdip.Font_delete(i8);
    if (i5 != 0)
      OS.RestoreDC(j, i5);
    if (i10 != 0)
      OS.DeleteObject(i10);
  }

  RECT drawBorder(int paramInt1, int paramInt2, int paramInt3, int paramInt4, StyleItem[] paramArrayOfStyleItem, int paramInt5, int paramInt6, int paramInt7, RECT paramRECT1, RECT paramRECT2, int paramInt8, int paramInt9)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt5];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (localTextStyle.borderStyle == 0)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt8, paramInt9);
    if ((paramInt5 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentBorder(paramArrayOfStyleItem[(paramInt5 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt5; (m > 0) && (localTextStyle.isAdherentBorder(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt8 <= paramInt9) && (paramInt8 != -1) && (paramInt9 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt8 <= j) && (k <= paramInt9) ? 1 : 0;
      if (localTextStyle.borderColor != null)
      {
        paramInt6 = localTextStyle.borderColor.handle;
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        paramInt6 = paramInt7;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        paramInt6 = localTextStyle.foreground.handle;
      }
      int i1 = 1;
      int i2 = 0;
      switch (localTextStyle.borderStyle)
      {
      case 1:
        break;
      case 2:
        i2 = 1;
        break;
      case 4:
        i2 = 2;
      case 3:
      }
      int i3 = OS.SelectObject(paramInt1, OS.GetStockObject(5));
      LOGBRUSH localLOGBRUSH = new LOGBRUSH();
      localLOGBRUSH.lbStyle = 0;
      localLOGBRUSH.lbColor = paramInt6;
      int i4 = OS.ExtCreatePen(i2 | 0x10000, Math.max(1, i1), localLOGBRUSH, 0, null);
      int i5 = OS.SelectObject(paramInt1, i4);
      OS.Rectangle(paramInt1, paramInt2 + i, paramInt3, paramInt2 + localStyleItem.x + localStyleItem.width, paramInt3 + paramInt4);
      OS.SelectObject(paramInt1, i5);
      OS.DeleteObject(i4);
      if (paramRECT1 != null)
      {
        int i6 = OS.SaveDC(paramInt1);
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        OS.IntersectClipRect(paramInt1, paramRECT1.left, paramRECT1.top, paramRECT1.right, paramRECT1.bottom);
        localLOGBRUSH.lbColor = paramInt7;
        int i7 = OS.ExtCreatePen(i2 | 0x10000, Math.max(1, i1), localLOGBRUSH, 0, null);
        i5 = OS.SelectObject(paramInt1, i7);
        OS.Rectangle(paramInt1, paramInt2 + i, paramInt3, paramInt2 + localStyleItem.x + localStyleItem.width, paramInt3 + paramInt4);
        OS.RestoreDC(paramInt1, i6);
        OS.SelectObject(paramInt1, i5);
        OS.DeleteObject(i7);
      }
      OS.SelectObject(paramInt1, i3);
      return null;
    }
    return paramRECT1;
  }

  RECT drawBorderGDIP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, StyleItem[] paramArrayOfStyleItem, int paramInt5, int paramInt6, int paramInt7, RECT paramRECT1, RECT paramRECT2, int paramInt8, int paramInt9, int paramInt10)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt5];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (localTextStyle.borderStyle == 0)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt8, paramInt9);
    if ((paramInt5 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentBorder(paramArrayOfStyleItem[(paramInt5 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt5; (m > 0) && (localTextStyle.isAdherentBorder(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt8 <= paramInt9) && (paramInt8 != -1) && (paramInt9 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt8 <= j) && (k <= paramInt9) ? 1 : 0;
      int i1 = paramInt6;
      if (localTextStyle.borderColor != null)
      {
        i1 = createGdipBrush(localTextStyle.borderColor, paramInt10);
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        i1 = paramInt7;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        i1 = createGdipBrush(localTextStyle.foreground, paramInt10);
      }
      int i2 = 1;
      int i3 = 0;
      switch (localTextStyle.borderStyle)
      {
      case 1:
        break;
      case 2:
        i3 = 1;
        break;
      case 4:
        i3 = 2;
      case 3:
      }
      int i4 = Gdip.Pen_new(i1, i2);
      Gdip.Pen_SetDashStyle(i4, i3);
      Gdip.Graphics_SetPixelOffsetMode(paramInt1, 3);
      int i5 = Gdip.Graphics_GetSmoothingMode(paramInt1);
      Gdip.Graphics_SetSmoothingMode(paramInt1, 3);
      if (paramRECT1 != null)
      {
        int i6 = Gdip.Graphics_Save(paramInt1);
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        Rect localRect = new Rect();
        localRect.X = paramRECT1.left;
        localRect.Y = paramRECT1.top;
        localRect.Width = (paramRECT1.right - paramRECT1.left);
        localRect.Height = (paramRECT1.bottom - paramRECT1.top);
        Gdip.Graphics_SetClip(paramInt1, localRect, 4);
        Gdip.Graphics_DrawRectangle(paramInt1, i4, paramInt2 + i, paramInt3, localStyleItem.x + localStyleItem.width - i - 1, paramInt4 - 1);
        Gdip.Graphics_Restore(paramInt1, i6);
        i6 = Gdip.Graphics_Save(paramInt1);
        Gdip.Graphics_SetClip(paramInt1, localRect, 1);
        int i7 = Gdip.Pen_new(paramInt7, i2);
        Gdip.Pen_SetDashStyle(i7, i3);
        Gdip.Graphics_DrawRectangle(paramInt1, i7, paramInt2 + i, paramInt3, localStyleItem.x + localStyleItem.width - i - 1, paramInt4 - 1);
        Gdip.Pen_delete(i7);
        Gdip.Graphics_Restore(paramInt1, i6);
      }
      else
      {
        Gdip.Graphics_DrawRectangle(paramInt1, i4, paramInt2 + i, paramInt3, localStyleItem.x + localStyleItem.width - i - 1, paramInt4 - 1);
      }
      Gdip.Graphics_SetPixelOffsetMode(paramInt1, 4);
      Gdip.Graphics_SetSmoothingMode(paramInt1, i5);
      Gdip.Pen_delete(i4);
      if ((i1 != paramInt7) && (i1 != paramInt6))
        Gdip.SolidBrush_delete(i1);
      return null;
    }
    return paramRECT1;
  }

  void drawRunBackground(StyleItem paramStyleItem, int paramInt1, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int i = paramStyleItem.start + paramStyleItem.length - 1;
    int j = (paramBoolean) && (paramInt2 <= paramStyleItem.start) && (paramInt3 >= i) ? 1 : 0;
    if (j != 0)
    {
      OS.SelectObject(paramInt1, paramInt4);
      OS.PatBlt(paramInt1, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top, 15728673);
    }
    else
    {
      if ((paramStyleItem.style != null) && (paramStyleItem.style.background != null))
      {
        k = paramStyleItem.style.background.handle;
        int m = OS.CreateSolidBrush(k);
        int n = OS.SelectObject(paramInt1, m);
        OS.PatBlt(paramInt1, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top, 15728673);
        OS.SelectObject(paramInt1, n);
        OS.DeleteObject(m);
      }
      int k = (paramBoolean) && (paramInt2 <= i) && (paramStyleItem.start <= paramInt3) ? 1 : 0;
      if (k != 0)
      {
        getPartialSelection(paramStyleItem, paramInt2, paramInt3, paramRECT);
        OS.SelectObject(paramInt1, paramInt4);
        OS.PatBlt(paramInt1, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top, 15728673);
      }
    }
  }

  void drawRunBackgroundGDIP(StyleItem paramStyleItem, int paramInt1, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
  {
    int i = paramStyleItem.start + paramStyleItem.length - 1;
    int j = (paramBoolean) && (paramInt2 <= paramStyleItem.start) && (paramInt3 >= i) ? 1 : 0;
    if (j != 0)
    {
      Gdip.Graphics_FillRectangle(paramInt1, paramInt5, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top);
    }
    else
    {
      if ((paramStyleItem.style != null) && (paramStyleItem.style.background != null))
      {
        k = createGdipBrush(paramStyleItem.style.background, paramInt4);
        Gdip.Graphics_FillRectangle(paramInt1, k, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top);
        Gdip.SolidBrush_delete(k);
      }
      int k = (paramBoolean) && (paramInt2 <= i) && (paramStyleItem.start <= paramInt3) ? 1 : 0;
      if (k != 0)
      {
        getPartialSelection(paramStyleItem, paramInt2, paramInt3, paramRECT);
        if (paramRECT.left > paramRECT.right)
        {
          int m = paramRECT.left;
          paramRECT.left = paramRECT.right;
          paramRECT.right = m;
        }
        Gdip.Graphics_FillRectangle(paramInt1, paramInt5, paramRECT.left, paramRECT.top, paramRECT.right - paramRECT.left, paramRECT.bottom - paramRECT.top);
      }
    }
  }

  RECT drawRunText(int paramInt1, StyleItem paramStyleItem, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = paramStyleItem.start + paramStyleItem.length - 1;
    int j = (paramInt5 <= paramInt6) && (paramInt5 != -1) && (paramInt6 != -1) ? 1 : 0;
    int k = (j != 0) && (paramInt5 <= paramStyleItem.start) && (paramInt6 >= i) ? 1 : 0;
    int m = (j != 0) && (k == 0) && (paramInt5 <= i) && (paramStyleItem.start <= paramInt6) ? 1 : 0;
    int n = (this.orientation & 0x4000000) != 0 ? -1 : 0;
    int i1 = paramRECT.left + n;
    int i2 = paramRECT.top + (paramInt2 - paramStyleItem.ascent);
    int i3 = getItemFont(paramStyleItem);
    OS.SelectObject(paramInt1, i3);
    if (k != 0)
      paramInt3 = paramInt4;
    else if ((paramStyleItem.style != null) && (paramStyleItem.style.foreground != null))
      paramInt3 = paramStyleItem.style.foreground.handle;
    OS.SetTextColor(paramInt1, paramInt3);
    OS.ScriptTextOut(paramInt1, paramStyleItem.psc, i1, i2, 0, null, paramStyleItem.analysis, 0, 0, paramStyleItem.glyphs, paramStyleItem.glyphCount, paramStyleItem.advances, paramStyleItem.justify, paramStyleItem.goffsets);
    if (m != 0)
    {
      getPartialSelection(paramStyleItem, paramInt5, paramInt6, paramRECT);
      OS.SetTextColor(paramInt1, paramInt4);
      OS.ScriptTextOut(paramInt1, paramStyleItem.psc, i1, i2, 4, paramRECT, paramStyleItem.analysis, 0, 0, paramStyleItem.glyphs, paramStyleItem.glyphCount, paramStyleItem.advances, paramStyleItem.justify, paramStyleItem.goffsets);
    }
    return (k != 0) || (m != 0) ? paramRECT : null;
  }

  RECT drawRunTextGDIP(int paramInt1, StyleItem paramStyleItem, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    int i = paramStyleItem.start + paramStyleItem.length - 1;
    int j = (paramInt6 <= paramInt7) && (paramInt6 != -1) && (paramInt7 != -1) ? 1 : 0;
    int k = (j != 0) && (paramInt6 <= paramStyleItem.start) && (paramInt7 >= i) ? 1 : 0;
    int m = (j != 0) && (k == 0) && (paramInt6 <= i) && (paramStyleItem.start <= paramInt7) ? 1 : 0;
    int n = paramRECT.top + paramInt3;
    int i1 = paramRECT.left;
    int i2 = paramInt4;
    if (k != 0)
      i2 = paramInt5;
    else if ((paramStyleItem.style != null) && (paramStyleItem.style.foreground != null))
      i2 = createGdipBrush(paramStyleItem.style.foreground, paramInt8);
    int i3 = 0;
    Rect localRect = null;
    if (m != 0)
    {
      localRect = new Rect();
      getPartialSelection(paramStyleItem, paramInt6, paramInt7, paramRECT);
      localRect.X = paramRECT.left;
      localRect.Y = paramRECT.top;
      localRect.Width = (paramRECT.right - paramRECT.left);
      localRect.Height = (paramRECT.bottom - paramRECT.top);
      i3 = Gdip.Graphics_Save(paramInt1);
      Gdip.Graphics_SetClip(paramInt1, localRect, 4);
    }
    int i4 = 0;
    int i5 = (this.orientation & 0x4000000) != 0 ? 1 : 0;
    if (i5 != 0)
    {
      switch (Gdip.Brush_GetType(i2))
      {
      case 4:
        Gdip.LinearGradientBrush_ScaleTransform(i2, -1.0F, 1.0F, 0);
        Gdip.LinearGradientBrush_TranslateTransform(i2, -2 * i1 - paramStyleItem.width, 0.0F, 0);
        break;
      case 2:
        Gdip.TextureBrush_ScaleTransform(i2, -1.0F, 1.0F, 0);
        Gdip.TextureBrush_TranslateTransform(i2, -2 * i1 - paramStyleItem.width, 0.0F, 0);
      case 3:
      }
      i4 = Gdip.Graphics_Save(paramInt1);
      Gdip.Graphics_ScaleTransform(paramInt1, -1.0F, 1.0F, 0);
      Gdip.Graphics_TranslateTransform(paramInt1, -2 * i1 - paramStyleItem.width, 0.0F, 0);
    }
    int[] arrayOfInt = new int[paramStyleItem.glyphCount];
    float[] arrayOfFloat = new float[paramStyleItem.glyphCount * 2];
    OS.memmove(arrayOfInt, paramStyleItem.justify != 0 ? paramStyleItem.justify : paramStyleItem.advances, paramStyleItem.glyphCount * 4);
    int i6 = i1;
    int i7 = 0;
    int i8 = 0;
    while (i7 < arrayOfInt.length)
    {
      arrayOfFloat[(i8++)] = i6;
      arrayOfFloat[(i8++)] = n;
      i6 += arrayOfInt[i7];
      i7++;
    }
    Gdip.Graphics_DrawDriverString(paramInt1, paramStyleItem.glyphs, paramStyleItem.glyphCount, paramInt2, i2, arrayOfFloat, 0, 0);
    if (m != 0)
    {
      if (i5 != 0)
        Gdip.Graphics_Restore(paramInt1, i4);
      Gdip.Graphics_Restore(paramInt1, i3);
      i3 = Gdip.Graphics_Save(paramInt1);
      Gdip.Graphics_SetClip(paramInt1, localRect, 1);
      if (i5 != 0)
      {
        i4 = Gdip.Graphics_Save(paramInt1);
        Gdip.Graphics_ScaleTransform(paramInt1, -1.0F, 1.0F, 0);
        Gdip.Graphics_TranslateTransform(paramInt1, -2 * i1 - paramStyleItem.width, 0.0F, 0);
      }
      Gdip.Graphics_DrawDriverString(paramInt1, paramStyleItem.glyphs, paramStyleItem.glyphCount, paramInt2, paramInt5, arrayOfFloat, 0, 0);
      Gdip.Graphics_Restore(paramInt1, i3);
    }
    if (i5 != 0)
    {
      switch (Gdip.Brush_GetType(i2))
      {
      case 4:
        Gdip.LinearGradientBrush_ResetTransform(i2);
        break;
      case 2:
        Gdip.TextureBrush_ResetTransform(i2);
      case 3:
      }
      Gdip.Graphics_Restore(paramInt1, i4);
    }
    if ((i2 != paramInt5) && (i2 != paramInt4))
      Gdip.SolidBrush_delete(i2);
    return (k != 0) || (m != 0) ? paramRECT : null;
  }

  RECT drawRunTextGDIPRaster(int paramInt1, StyleItem paramStyleItem, RECT paramRECT, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int i = 0;
    Gdip.Graphics_SetPixelOffsetMode(paramInt1, 3);
    int j = Gdip.Region_new();
    if (j == 0)
      SWT.error(2);
    Gdip.Graphics_GetClip(paramInt1, j);
    if (!Gdip.Region_IsInfinite(j, paramInt1))
      i = Gdip.Region_GetHRGN(j, paramInt1);
    Gdip.Region_delete(j);
    Gdip.Graphics_SetPixelOffsetMode(paramInt1, 4);
    float[] arrayOfFloat = (float[])null;
    int k = Gdip.Matrix_new(1.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F);
    if (k == 0)
      SWT.error(2);
    Gdip.Graphics_GetTransform(paramInt1, k);
    if (!Gdip.Matrix_IsIdentity(k))
    {
      arrayOfFloat = new float[6];
      Gdip.Matrix_GetElements(k, arrayOfFloat);
    }
    Gdip.Matrix_delete(k);
    int m = Gdip.Graphics_GetHDC(paramInt1);
    int n = OS.SaveDC(m);
    if (arrayOfFloat != null)
    {
      OS.SetGraphicsMode(m, 2);
      OS.SetWorldTransform(m, arrayOfFloat);
    }
    if (i != 0)
    {
      OS.SelectClipRgn(m, i);
      OS.DeleteObject(i);
    }
    if ((this.orientation & 0x4000000) != 0)
      OS.SetLayout(m, OS.GetLayout(m) | 0x1);
    OS.SetBkMode(m, 1);
    RECT localRECT = drawRunText(m, paramStyleItem, paramRECT, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    OS.RestoreDC(m, n);
    Gdip.Graphics_ReleaseHDC(paramInt1, m);
    return localRECT;
  }

  RECT drawStrikeout(int paramInt1, int paramInt2, int paramInt3, StyleItem[] paramArrayOfStyleItem, int paramInt4, int paramInt5, int paramInt6, RECT paramRECT1, RECT paramRECT2, int paramInt7, int paramInt8)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt4];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (!localTextStyle.strikeout)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt7, paramInt8);
    if ((paramInt4 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentStrikeout(paramArrayOfStyleItem[(paramInt4 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt4; (m > 0) && (localTextStyle.isAdherentStrikeout(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt7 <= paramInt8) && (paramInt7 != -1) && (paramInt8 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt7 <= j) && (k <= paramInt8) ? 1 : 0;
      if (localTextStyle.strikeoutColor != null)
      {
        paramInt5 = localTextStyle.strikeoutColor.handle;
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        paramInt5 = paramInt6;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        paramInt5 = localTextStyle.foreground.handle;
      }
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramInt2 + i, paramInt3 - localStyleItem.strikeoutPos, paramInt2 + localStyleItem.x + localStyleItem.width, paramInt3 - localStyleItem.strikeoutPos + localStyleItem.strikeoutThickness);
      int i1 = OS.CreateSolidBrush(paramInt5);
      OS.FillRect(paramInt1, localRECT, i1);
      OS.DeleteObject(i1);
      if (paramRECT1 != null)
      {
        int i2 = OS.CreateSolidBrush(paramInt6);
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        OS.SetRect(paramRECT1, Math.max(localRECT.left, paramRECT1.left), localRECT.top, Math.min(localRECT.right, paramRECT1.right), localRECT.bottom);
        OS.FillRect(paramInt1, paramRECT1, i2);
        OS.DeleteObject(i2);
      }
      return null;
    }
    return paramRECT1;
  }

  RECT drawStrikeoutGDIP(int paramInt1, int paramInt2, int paramInt3, StyleItem[] paramArrayOfStyleItem, int paramInt4, int paramInt5, int paramInt6, RECT paramRECT1, RECT paramRECT2, int paramInt7, int paramInt8, int paramInt9)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt4];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (!localTextStyle.strikeout)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt7, paramInt8);
    if ((paramInt4 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentStrikeout(paramArrayOfStyleItem[(paramInt4 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt4; (m > 0) && (localTextStyle.isAdherentStrikeout(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt7 <= paramInt8) && (paramInt7 != -1) && (paramInt8 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt7 <= j) && (k <= paramInt8) ? 1 : 0;
      int i1 = paramInt5;
      if (localTextStyle.strikeoutColor != null)
      {
        i1 = createGdipBrush(localTextStyle.strikeoutColor, paramInt9);
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        paramInt5 = paramInt6;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        i1 = createGdipBrush(localTextStyle.foreground, paramInt9);
      }
      if (paramRECT1 != null)
      {
        int i2 = Gdip.Graphics_Save(paramInt1);
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        Rect localRect = new Rect();
        localRect.X = paramRECT1.left;
        localRect.Y = paramRECT1.top;
        localRect.Width = (paramRECT1.right - paramRECT1.left);
        localRect.Height = (paramRECT1.bottom - paramRECT1.top);
        Gdip.Graphics_SetClip(paramInt1, localRect, 4);
        Gdip.Graphics_FillRectangle(paramInt1, i1, paramInt2 + i, paramInt3 - localStyleItem.strikeoutPos, localStyleItem.x + localStyleItem.width - i, localStyleItem.strikeoutThickness);
        Gdip.Graphics_Restore(paramInt1, i2);
        i2 = Gdip.Graphics_Save(paramInt1);
        Gdip.Graphics_SetClip(paramInt1, localRect, 1);
        Gdip.Graphics_FillRectangle(paramInt1, paramInt6, paramInt2 + i, paramInt3 - localStyleItem.strikeoutPos, localStyleItem.x + localStyleItem.width - i, localStyleItem.strikeoutThickness);
        Gdip.Graphics_Restore(paramInt1, i2);
      }
      else
      {
        Gdip.Graphics_FillRectangle(paramInt1, i1, paramInt2 + i, paramInt3 - localStyleItem.strikeoutPos, localStyleItem.x + localStyleItem.width - i, localStyleItem.strikeoutThickness);
      }
      if ((i1 != paramInt6) && (i1 != paramInt5))
        Gdip.SolidBrush_delete(i1);
      return null;
    }
    return paramRECT1;
  }

  RECT drawUnderline(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, StyleItem[] paramArrayOfStyleItem, int paramInt6, int paramInt7, int paramInt8, RECT paramRECT1, RECT paramRECT2, int paramInt9, int paramInt10)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt6];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (!localTextStyle.underline)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt9, paramInt10);
    if ((paramInt6 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentUnderline(paramArrayOfStyleItem[(paramInt6 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt6; (m > 0) && (localTextStyle.isAdherentUnderline(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt9 <= paramInt10) && (paramInt9 != -1) && (paramInt10 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt9 <= j) && (k <= paramInt10) ? 1 : 0;
      if (localTextStyle.underlineColor != null)
      {
        paramInt7 = localTextStyle.underlineColor.handle;
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        paramInt7 = paramInt8;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        paramInt7 = localTextStyle.foreground.handle;
      }
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramInt2 + i, paramInt3 - paramInt4, paramInt2 + localStyleItem.x + localStyleItem.width, paramInt3 - paramInt4 + localStyleItem.underlineThickness);
      if (paramRECT1 != null)
      {
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        OS.SetRect(paramRECT1, Math.max(localRECT.left, paramRECT1.left), localRECT.top, Math.min(localRECT.right, paramRECT1.right), localRECT.bottom);
      }
      int i1;
      int i2;
      int i3;
      int i5;
      switch (localTextStyle.underlineStyle)
      {
      case 2:
      case 3:
        i1 = 1;
        i2 = 2 * i1;
        i3 = Math.min(localRECT.top - i2 / 2, paramInt5 - i2 - 1);
        int[] arrayOfInt = computePolyline(localRECT.left, i3, localRECT.right, i3 + i2);
        i5 = OS.CreatePen(0, i1, paramInt7);
        int i6 = OS.SelectObject(paramInt1, i5);
        int i7 = OS.SaveDC(paramInt1);
        OS.IntersectClipRect(paramInt1, localRECT.left, i3, localRECT.right + 1, i3 + i2 + 1);
        OS.Polyline(paramInt1, arrayOfInt, arrayOfInt.length / 2);
        int i8 = arrayOfInt.length;
        if ((i8 >= 2) && (i1 <= 1))
          OS.SetPixel(paramInt1, arrayOfInt[(i8 - 2)], arrayOfInt[(i8 - 1)], paramInt7);
        OS.SelectObject(paramInt1, i6);
        OS.DeleteObject(i5);
        OS.RestoreDC(paramInt1, i7);
        if (paramRECT1 != null)
        {
          i5 = OS.CreatePen(0, i1, paramInt8);
          i6 = OS.SelectObject(paramInt1, i5);
          i7 = OS.SaveDC(paramInt1);
          OS.IntersectClipRect(paramInt1, paramRECT1.left, i3, paramRECT1.right + 1, i3 + i2 + 1);
          OS.Polyline(paramInt1, arrayOfInt, arrayOfInt.length / 2);
          if ((i8 >= 2) && (i1 <= 1))
            OS.SetPixel(paramInt1, arrayOfInt[(i8 - 2)], arrayOfInt[(i8 - 1)], paramInt8);
          OS.SelectObject(paramInt1, i6);
          OS.DeleteObject(i5);
          OS.RestoreDC(paramInt1, i7);
        }
        break;
      case 0:
      case 1:
      case 4:
      case 196608:
        if (localTextStyle.underlineStyle == 196608)
        {
          localRECT.top -= localStyleItem.underlineThickness;
          if (paramRECT1 != null)
            paramRECT1.top -= localStyleItem.underlineThickness;
        }
        i1 = localTextStyle.underlineStyle == 1 ? localRECT.bottom + localStyleItem.underlineThickness * 2 : localRECT.bottom;
        if (i1 > paramInt5)
        {
          OS.OffsetRect(localRECT, 0, paramInt5 - i1);
          if (paramRECT1 != null)
            OS.OffsetRect(paramRECT1, 0, paramInt5 - i1);
        }
        i2 = OS.CreateSolidBrush(paramInt7);
        OS.FillRect(paramInt1, localRECT, i2);
        if (localTextStyle.underlineStyle == 1)
        {
          OS.SetRect(localRECT, localRECT.left, localRECT.top + localStyleItem.underlineThickness * 2, localRECT.right, localRECT.bottom + localStyleItem.underlineThickness * 2);
          OS.FillRect(paramInt1, localRECT, i2);
        }
        OS.DeleteObject(i2);
        if (paramRECT1 != null)
        {
          i3 = OS.CreateSolidBrush(paramInt8);
          OS.FillRect(paramInt1, paramRECT1, i3);
          if (localTextStyle.underlineStyle == 1)
          {
            OS.SetRect(paramRECT1, paramRECT1.left, localRECT.top, paramRECT1.right, localRECT.bottom);
            OS.FillRect(paramInt1, paramRECT1, i3);
          }
          OS.DeleteObject(i3);
        }
        break;
      case 65536:
      case 131072:
        i3 = localTextStyle.underlineStyle == 131072 ? 1 : 2;
        int i4 = OS.CreatePen(i3, 1, paramInt7);
        i5 = OS.SelectObject(paramInt1, i4);
        OS.SetRect(localRECT, localRECT.left, paramInt3 + localStyleItem.descent, localRECT.right, paramInt3 + localStyleItem.descent + localStyleItem.underlineThickness);
        OS.MoveToEx(paramInt1, localRECT.left, localRECT.top, 0);
        OS.LineTo(paramInt1, localRECT.right, localRECT.top);
        OS.SelectObject(paramInt1, i5);
        OS.DeleteObject(i4);
        if (paramRECT1 != null)
        {
          i4 = OS.CreatePen(i3, 1, paramInt8);
          i5 = OS.SelectObject(paramInt1, i4);
          OS.SetRect(paramRECT1, paramRECT1.left, localRECT.top, paramRECT1.right, localRECT.bottom);
          OS.MoveToEx(paramInt1, paramRECT1.left, paramRECT1.top, 0);
          OS.LineTo(paramInt1, paramRECT1.right, paramRECT1.top);
          OS.SelectObject(paramInt1, i5);
          OS.DeleteObject(i4);
        }
        break;
      }
      return null;
    }
    return paramRECT1;
  }

  RECT drawUnderlineGDIP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, StyleItem[] paramArrayOfStyleItem, int paramInt6, int paramInt7, int paramInt8, RECT paramRECT1, RECT paramRECT2, int paramInt9, int paramInt10, int paramInt11)
  {
    StyleItem localStyleItem = paramArrayOfStyleItem[paramInt6];
    TextStyle localTextStyle = localStyleItem.style;
    if (localTextStyle == null)
      return null;
    if (!localTextStyle.underline)
      return null;
    paramRECT1 = addClipRect(localStyleItem, paramRECT1, paramRECT2, paramInt9, paramInt10);
    if ((paramInt6 + 1 >= paramArrayOfStyleItem.length) || (!localTextStyle.isAdherentUnderline(paramArrayOfStyleItem[(paramInt6 + 1)].style)))
    {
      int i = localStyleItem.x;
      int j = localStyleItem.start;
      int k = localStyleItem.start + localStyleItem.length - 1;
      for (int m = paramInt6; (m > 0) && (localTextStyle.isAdherentUnderline(paramArrayOfStyleItem[(m - 1)].style)); m--)
      {
        i = paramArrayOfStyleItem[(m - 1)].x;
        j = Math.min(j, paramArrayOfStyleItem[(m - 1)].start);
        k = Math.max(k, paramArrayOfStyleItem[(m - 1)].start + paramArrayOfStyleItem[(m - 1)].length - 1);
      }
      m = (paramInt9 <= paramInt10) && (paramInt9 != -1) && (paramInt10 != -1) ? 1 : 0;
      int n = (m != 0) && (paramInt9 <= j) && (k <= paramInt10) ? 1 : 0;
      int i1 = paramInt7;
      if (localTextStyle.underlineColor != null)
      {
        i1 = createGdipBrush(localTextStyle.underlineColor, paramInt11);
        paramRECT1 = null;
      }
      else if (n != 0)
      {
        i1 = paramInt8;
        paramRECT1 = null;
      }
      else if (localTextStyle.foreground != null)
      {
        i1 = createGdipBrush(localTextStyle.foreground, paramInt11);
      }
      RECT localRECT = new RECT();
      OS.SetRect(localRECT, paramInt2 + i, paramInt3 - paramInt4, paramInt2 + localStyleItem.x + localStyleItem.width, paramInt3 - paramInt4 + localStyleItem.underlineThickness);
      Rect localRect1 = null;
      if (paramRECT1 != null)
      {
        if (paramRECT1.left == -1)
          paramRECT1.left = 0;
        if (paramRECT1.right == -1)
          paramRECT1.right = 524287;
        OS.SetRect(paramRECT1, Math.max(localRECT.left, paramRECT1.left), localRECT.top, Math.min(localRECT.right, paramRECT1.right), localRECT.bottom);
        localRect1 = new Rect();
        localRect1.X = paramRECT1.left;
        localRect1.Y = paramRECT1.top;
        localRect1.Width = (paramRECT1.right - paramRECT1.left);
        localRect1.Height = (paramRECT1.bottom - paramRECT1.top);
      }
      int i2 = 0;
      Gdip.Graphics_SetPixelOffsetMode(paramInt1, 3);
      int i3 = Gdip.Graphics_GetSmoothingMode(paramInt1);
      Gdip.Graphics_SetSmoothingMode(paramInt1, 3);
      int i4;
      int i5;
      int i6;
      switch (localTextStyle.underlineStyle)
      {
      case 2:
      case 3:
        i4 = 1;
        i5 = 2 * i4;
        i6 = Math.min(localRECT.top - i5 / 2, paramInt5 - i5 - 1);
        int[] arrayOfInt = computePolyline(localRECT.left, i6, localRECT.right, i6 + i5);
        int i8 = Gdip.Pen_new(i1, i4);
        i2 = Gdip.Graphics_Save(paramInt1);
        if (localRect1 != null)
        {
          Gdip.Graphics_SetClip(paramInt1, localRect1, 4);
        }
        else
        {
          Rect localRect2 = new Rect();
          localRect2.X = localRECT.left;
          localRect2.Y = i6;
          localRect2.Width = (localRECT.right - localRECT.left);
          localRect2.Height = (i5 + 1);
          Gdip.Graphics_SetClip(paramInt1, localRect2, 1);
        }
        Gdip.Graphics_DrawLines(paramInt1, i8, arrayOfInt, arrayOfInt.length / 2);
        if (localRect1 != null)
        {
          int i9 = Gdip.Pen_new(paramInt8, i4);
          Gdip.Graphics_Restore(paramInt1, i2);
          i2 = Gdip.Graphics_Save(paramInt1);
          Gdip.Graphics_SetClip(paramInt1, localRect1, 1);
          Gdip.Graphics_DrawLines(paramInt1, i9, arrayOfInt, arrayOfInt.length / 2);
          Gdip.Pen_delete(i9);
        }
        Gdip.Graphics_Restore(paramInt1, i2);
        Gdip.Pen_delete(i8);
        if (i2 != 0)
          Gdip.Graphics_Restore(paramInt1, i2);
        break;
      case 0:
      case 1:
      case 4:
      case 196608:
        if (localTextStyle.underlineStyle == 196608)
          localRECT.top -= localStyleItem.underlineThickness;
        i4 = localTextStyle.underlineStyle == 1 ? localRECT.bottom + localStyleItem.underlineThickness * 2 : localRECT.bottom;
        if (i4 > paramInt5)
          OS.OffsetRect(localRECT, 0, paramInt5 - i4);
        if (localRect1 != null)
        {
          localRect1.Y = localRECT.top;
          if (localTextStyle.underlineStyle == 196608)
            localRect1.Height = (localStyleItem.underlineThickness * 2);
          if (localTextStyle.underlineStyle == 1)
            localRect1.Height = (localStyleItem.underlineThickness * 3);
          i2 = Gdip.Graphics_Save(paramInt1);
          Gdip.Graphics_SetClip(paramInt1, localRect1, 4);
        }
        Gdip.Graphics_FillRectangle(paramInt1, i1, localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
        if (localTextStyle.underlineStyle == 1)
          Gdip.Graphics_FillRectangle(paramInt1, i1, localRECT.left, localRECT.top + localStyleItem.underlineThickness * 2, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
        if (localRect1 != null)
        {
          Gdip.Graphics_Restore(paramInt1, i2);
          i2 = Gdip.Graphics_Save(paramInt1);
          Gdip.Graphics_SetClip(paramInt1, localRect1, 1);
          Gdip.Graphics_FillRectangle(paramInt1, paramInt8, localRECT.left, localRECT.top, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
          if (localTextStyle.underlineStyle == 1)
            Gdip.Graphics_FillRectangle(paramInt1, paramInt8, localRECT.left, localRECT.top + localStyleItem.underlineThickness * 2, localRECT.right - localRECT.left, localRECT.bottom - localRECT.top);
          Gdip.Graphics_Restore(paramInt1, i2);
        }
        break;
      case 65536:
      case 131072:
        i5 = Gdip.Pen_new(i1, 1.0F);
        i6 = localTextStyle.underlineStyle == 65536 ? 2 : 1;
        Gdip.Pen_SetDashStyle(i5, i6);
        if (localRect1 != null)
        {
          i2 = Gdip.Graphics_Save(paramInt1);
          Gdip.Graphics_SetClip(paramInt1, localRect1, 4);
        }
        Gdip.Graphics_DrawLine(paramInt1, i5, localRECT.left, paramInt3 + localStyleItem.descent, localStyleItem.width - localStyleItem.length, paramInt3 + localStyleItem.descent);
        if (localRect1 != null)
        {
          Gdip.Graphics_Restore(paramInt1, i2);
          i2 = Gdip.Graphics_Save(paramInt1);
          Gdip.Graphics_SetClip(paramInt1, localRect1, 1);
          int i7 = Gdip.Pen_new(i1, 1.0F);
          Gdip.Pen_SetDashStyle(i7, i6);
          Gdip.Graphics_DrawLine(paramInt1, i7, localRECT.left, paramInt3 + localStyleItem.descent, localStyleItem.width - localStyleItem.length, paramInt3 + localStyleItem.descent);
          Gdip.Graphics_Restore(paramInt1, i2);
          Gdip.Pen_delete(i7);
        }
        Gdip.Pen_delete(i5);
      }
      if ((i1 != paramInt8) && (i1 != paramInt7))
        Gdip.SolidBrush_delete(i1);
      Gdip.Graphics_SetPixelOffsetMode(paramInt1, 4);
      Gdip.Graphics_SetSmoothingMode(paramInt1, i3);
      return null;
    }
    return paramRECT1;
  }

  void freeRuns()
  {
    if (this.allRuns == null)
      return;
    for (int i = 0; i < this.allRuns.length; i++)
    {
      StyleItem localStyleItem = this.allRuns[i];
      localStyleItem.free();
    }
    this.allRuns = null;
    this.runs = null;
    this.segmentsText = null;
  }

  public int getAlignment()
  {
    checkLayout();
    return this.alignment;
  }

  public int getAscent()
  {
    checkLayout();
    return this.ascent;
  }

  public Rectangle getBounds()
  {
    checkLayout();
    computeRuns(null);
    int i = 0;
    if (this.wrapWidth != -1)
      i = this.wrapWidth;
    else
      for (int j = 0; j < this.runs.length; j++)
        i = Math.max(i, this.lineWidth[j] + getLineIndent(j));
    return new Rectangle(0, 0, i, this.lineY[(this.lineY.length - 1)]);
  }

  public Rectangle getBounds(int paramInt1, int paramInt2)
  {
    checkLayout();
    computeRuns(null);
    int i = this.text.length();
    if (i == 0)
      return new Rectangle(0, 0, 0, 0);
    if (paramInt1 > paramInt2)
      return new Rectangle(0, 0, 0, 0);
    paramInt1 = Math.min(Math.max(0, paramInt1), i - 1);
    paramInt2 = Math.min(Math.max(0, paramInt2), i - 1);
    paramInt1 = translateOffset(paramInt1);
    paramInt2 = translateOffset(paramInt2);
    int j = 2147483647;
    int k = 0;
    int m = 2147483647;
    int n = 0;
    int i1 = (this.orientation & 0x4000000) != 0 ? 1 : 0;
    for (int i2 = 0; i2 < this.allRuns.length - 1; i2++)
    {
      StyleItem localStyleItem = this.allRuns[i2];
      int i3 = localStyleItem.start + localStyleItem.length;
      if (i3 > paramInt1)
      {
        if (localStyleItem.start > paramInt2)
          break;
        int i4 = localStyleItem.x;
        int i5 = localStyleItem.x + localStyleItem.width;
        Object localObject;
        int i7;
        if ((localStyleItem.start <= paramInt1) && (paramInt1 < i3))
        {
          i6 = 0;
          if ((localStyleItem.style != null) && (localStyleItem.style.metrics != null))
          {
            localObject = localStyleItem.style.metrics;
            i6 = ((GlyphMetrics)localObject).width * (paramInt1 - localStyleItem.start);
          }
          else if (!localStyleItem.tab)
          {
            localObject = new int[1];
            i7 = localStyleItem.justify != 0 ? localStyleItem.justify : localStyleItem.advances;
            OS.ScriptCPtoX(paramInt1 - localStyleItem.start, false, localStyleItem.length, localStyleItem.glyphCount, localStyleItem.clusters, localStyleItem.visAttrs, i7, localStyleItem.analysis, (int[])localObject);
            i6 = i1 != 0 ? localStyleItem.width - localObject[0] : localObject[0];
          }
          if ((localStyleItem.analysis.fRTL ^ i1))
            i5 = localStyleItem.x + i6;
          else
            i4 = localStyleItem.x + i6;
        }
        if ((localStyleItem.start <= paramInt2) && (paramInt2 < i3))
        {
          i6 = localStyleItem.width;
          if ((localStyleItem.style != null) && (localStyleItem.style.metrics != null))
          {
            localObject = localStyleItem.style.metrics;
            i6 = ((GlyphMetrics)localObject).width * (paramInt2 - localStyleItem.start + 1);
          }
          else if (!localStyleItem.tab)
          {
            localObject = new int[1];
            i7 = localStyleItem.justify != 0 ? localStyleItem.justify : localStyleItem.advances;
            OS.ScriptCPtoX(paramInt2 - localStyleItem.start, true, localStyleItem.length, localStyleItem.glyphCount, localStyleItem.clusters, localStyleItem.visAttrs, i7, localStyleItem.analysis, (int[])localObject);
            i6 = i1 != 0 ? localStyleItem.width - localObject[0] : localObject[0];
          }
          if ((localStyleItem.analysis.fRTL ^ i1))
            i4 = localStyleItem.x + i6;
          else
            i5 = localStyleItem.x + i6;
        }
        for (int i6 = 0; (i6 < this.runs.length) && (this.lineOffset[(i6 + 1)] <= localStyleItem.start); i6++);
        j = Math.min(j, i4);
        k = Math.max(k, i5);
        m = Math.min(m, this.lineY[i6]);
        n = Math.max(n, this.lineY[(i6 + 1)] - this.lineSpacing);
      }
    }
    return new Rectangle(j, m, k - j, n - m);
  }

  public int getDescent()
  {
    checkLayout();
    return this.descent;
  }

  public Font getFont()
  {
    checkLayout();
    return this.font;
  }

  public int getIndent()
  {
    checkLayout();
    return this.indent;
  }

  public boolean getJustify()
  {
    checkLayout();
    return this.justify;
  }

  int getItemFont(StyleItem paramStyleItem)
  {
    if (paramStyleItem.fallbackFont != 0)
      return paramStyleItem.fallbackFont;
    if ((paramStyleItem.style != null) && (paramStyleItem.style.font != null))
      return paramStyleItem.style.font.handle;
    if (this.font != null)
      return this.font.handle;
    return this.device.systemFont.handle;
  }

  public int getLevel(int paramInt)
  {
    checkLayout();
    computeRuns(null);
    int i = this.text.length();
    if ((paramInt < 0) || (paramInt > i))
      SWT.error(6);
    paramInt = translateOffset(paramInt);
    for (int j = 1; j < this.allRuns.length; j++)
      if (this.allRuns[j].start > paramInt)
        return this.allRuns[(j - 1)].analysis.s.uBidiLevel;
    return (this.orientation & 0x4000000) != 0 ? 1 : 0;
  }

  public Rectangle getLineBounds(int paramInt)
  {
    checkLayout();
    computeRuns(null);
    if ((paramInt < 0) || (paramInt >= this.runs.length))
      SWT.error(6);
    int i = getLineIndent(paramInt);
    int j = this.lineY[paramInt];
    int k = this.lineWidth[paramInt];
    int m = this.lineY[(paramInt + 1)] - j - this.lineSpacing;
    return new Rectangle(i, j, k, m);
  }

  public int getLineCount()
  {
    checkLayout();
    computeRuns(null);
    return this.runs.length;
  }

  int getLineIndent(int paramInt)
  {
    int i = this.wrapIndent;
    Object localObject;
    if (paramInt == 0)
    {
      i = this.indent;
    }
    else
    {
      StyleItem[] arrayOfStyleItem = this.runs[(paramInt - 1)];
      localObject = arrayOfStyleItem[(arrayOfStyleItem.length - 1)];
      if ((((StyleItem)localObject).lineBreak) && (!((StyleItem)localObject).softBreak))
        i = this.indent;
    }
    if (this.wrapWidth != -1)
    {
      int j = 1;
      if (this.justify)
      {
        localObject = this.runs[paramInt];
        if (localObject[(localObject.length - 1)].softBreak)
          j = 0;
      }
      if (j != 0)
      {
        int k = this.lineWidth[paramInt] + i;
        switch (this.alignment)
        {
        case 16777216:
          i += (this.wrapWidth - k) / 2;
          break;
        case 131072:
          i += this.wrapWidth - k;
        }
      }
    }
    return i;
  }

  public int getLineIndex(int paramInt)
  {
    checkLayout();
    computeRuns(null);
    int i = this.text.length();
    if ((paramInt < 0) || (paramInt > i))
      SWT.error(6);
    paramInt = translateOffset(paramInt);
    for (int j = 0; j < this.runs.length; j++)
      if (this.lineOffset[(j + 1)] > paramInt)
        return j;
    return this.runs.length - 1;
  }

  public FontMetrics getLineMetrics(int paramInt)
  {
    checkLayout();
    computeRuns(null);
    if ((paramInt < 0) || (paramInt >= this.runs.length))
      SWT.error(6);
    int i = this.device.internal_new_GC(null);
    int j = OS.CreateCompatibleDC(i);
    TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
    OS.SelectObject(j, this.font != null ? this.font.handle : this.device.systemFont.handle);
    OS.GetTextMetrics(j, localTEXTMETRICA);
    OS.DeleteDC(j);
    this.device.internal_dispose_GC(i, null);
    int k = Math.max(localTEXTMETRICA.tmAscent, this.ascent);
    int m = Math.max(localTEXTMETRICA.tmDescent, this.descent);
    int n = localTEXTMETRICA.tmInternalLeading;
    if (this.text.length() != 0)
    {
      StyleItem[] arrayOfStyleItem = this.runs[paramInt];
      for (int i1 = 0; i1 < arrayOfStyleItem.length; i1++)
      {
        StyleItem localStyleItem = arrayOfStyleItem[i1];
        if (localStyleItem.ascent > k)
        {
          k = localStyleItem.ascent;
          n = localStyleItem.leading;
        }
        m = Math.max(m, localStyleItem.descent);
      }
    }
    localTEXTMETRICA.tmAscent = k;
    localTEXTMETRICA.tmDescent = m;
    localTEXTMETRICA.tmHeight = (k + m);
    localTEXTMETRICA.tmInternalLeading = n;
    localTEXTMETRICA.tmAveCharWidth = 0;
    return FontMetrics.win32_new(localTEXTMETRICA);
  }

  public int[] getLineOffsets()
  {
    checkLayout();
    computeRuns(null);
    int[] arrayOfInt = new int[this.lineOffset.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = untranslateOffset(this.lineOffset[i]);
    return arrayOfInt;
  }

  public Point getLocation(int paramInt, boolean paramBoolean)
  {
    checkLayout();
    computeRuns(null);
    int i = this.text.length();
    if ((paramInt < 0) || (paramInt > i))
      SWT.error(6);
    i = this.segmentsText.length();
    paramInt = translateOffset(paramInt);
    for (int j = 0; j < this.runs.length; j++)
      if (this.lineOffset[(j + 1)] > paramInt)
        break;
    j = Math.min(j, this.runs.length - 1);
    if (paramInt == i)
      return new Point(getLineIndent(j) + this.lineWidth[j], this.lineY[j]);
    int k = -1;
    int m = this.allRuns.length;
    while (m - k > 1)
    {
      int n = (m + k) / 2;
      StyleItem localStyleItem = this.allRuns[n];
      if (localStyleItem.start > paramInt)
      {
        m = n;
      }
      else if (localStyleItem.start + localStyleItem.length <= paramInt)
      {
        k = n;
      }
      else
      {
        int i1;
        if ((localStyleItem.style != null) && (localStyleItem.style.metrics != null))
        {
          GlyphMetrics localGlyphMetrics = localStyleItem.style.metrics;
          i1 = localGlyphMetrics.width * (paramInt - localStyleItem.start + (paramBoolean ? 1 : 0));
        }
        else if (localStyleItem.tab)
        {
          i1 = (paramBoolean) || (paramInt == i) ? localStyleItem.width : 0;
        }
        else
        {
          int i2 = paramInt - localStyleItem.start;
          int i3 = localStyleItem.length;
          int i4 = localStyleItem.glyphCount;
          int[] arrayOfInt = new int[1];
          int i5 = localStyleItem.justify != 0 ? localStyleItem.justify : localStyleItem.advances;
          OS.ScriptCPtoX(i2, paramBoolean, i3, i4, localStyleItem.clusters, localStyleItem.visAttrs, i5, localStyleItem.analysis, arrayOfInt);
          i1 = (this.orientation & 0x4000000) != 0 ? localStyleItem.width - arrayOfInt[0] : arrayOfInt[0];
        }
        return new Point(localStyleItem.x + i1, this.lineY[j]);
      }
    }
    return new Point(0, 0);
  }

  public int getNextOffset(int paramInt1, int paramInt2)
  {
    checkLayout();
    return _getOffset(paramInt1, paramInt2, true);
  }

  int _getOffset(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    computeRuns(null);
    int i = this.text.length();
    if ((paramInt1 < 0) || (paramInt1 > i))
      SWT.error(6);
    if ((paramBoolean) && (paramInt1 == i))
      return i;
    if ((!paramBoolean) && (paramInt1 == 0))
      return 0;
    int j = paramBoolean ? 1 : -1;
    if ((paramInt2 & 0x1) != 0)
      return paramInt1 + j;
    i = this.segmentsText.length();
    paramInt1 = translateOffset(paramInt1);
    SCRIPT_LOGATTR localSCRIPT_LOGATTR = new SCRIPT_LOGATTR();
    SCRIPT_PROPERTIES localSCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
    int k = paramBoolean ? 0 : this.allRuns.length - 1;
    paramInt1 = validadeOffset(paramInt1, j);
    do
    {
      StyleItem localStyleItem = this.allRuns[k];
      if ((localStyleItem.start <= paramInt1) && (paramInt1 < localStyleItem.start + localStyleItem.length))
      {
        if ((localStyleItem.lineBreak) && (!localStyleItem.softBreak))
          return untranslateOffset(localStyleItem.start);
        if (localStyleItem.tab)
          return untranslateOffset(localStyleItem.start);
        OS.MoveMemory(localSCRIPT_PROPERTIES, this.device.scripts[localStyleItem.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
        int m = (!localSCRIPT_PROPERTIES.fNeedsCaretInfo) && (!localSCRIPT_PROPERTIES.fNeedsWordBreaking) ? 0 : 1;
        if (m != 0)
          breakRun(localStyleItem);
        while ((localStyleItem.start <= paramInt1) && (paramInt1 < localStyleItem.start + localStyleItem.length))
        {
          if (m != 0)
            OS.MoveMemory(localSCRIPT_LOGATTR, localStyleItem.psla + (paramInt1 - localStyleItem.start) * SCRIPT_LOGATTR.sizeof, SCRIPT_LOGATTR.sizeof);
          boolean bool1;
          boolean bool2;
          switch (paramInt2)
          {
          case 2:
            if (localSCRIPT_PROPERTIES.fNeedsCaretInfo)
            {
              if ((!localSCRIPT_LOGATTR.fInvalid) && (localSCRIPT_LOGATTR.fCharStop))
                return untranslateOffset(paramInt1);
            }
            else
              return untranslateOffset(paramInt1);
            break;
          case 4:
          case 16:
            if (localSCRIPT_PROPERTIES.fNeedsWordBreaking)
            {
              if ((!localSCRIPT_LOGATTR.fInvalid) && (localSCRIPT_LOGATTR.fWordStop))
                return untranslateOffset(paramInt1);
            }
            else if (paramInt1 > 0)
            {
              bool1 = Compatibility.isLetterOrDigit(this.segmentsText.charAt(paramInt1));
              bool2 = Compatibility.isLetterOrDigit(this.segmentsText.charAt(paramInt1 - 1));
              if (((bool1 != bool2) || (!bool1)) && (!Compatibility.isWhitespace(this.segmentsText.charAt(paramInt1))))
                return untranslateOffset(paramInt1);
            }
            break;
          case 8:
            if (paramInt1 > 0)
            {
              bool1 = Compatibility.isLetterOrDigit(this.segmentsText.charAt(paramInt1));
              bool2 = Compatibility.isLetterOrDigit(this.segmentsText.charAt(paramInt1 - 1));
              if ((!bool1) && (bool2))
                return untranslateOffset(paramInt1);
            }
            break;
          }
          paramInt1 = validadeOffset(paramInt1, j);
        }
      }
      k += j;
    }
    while ((k >= 0) && (k < this.allRuns.length - 1) && (paramInt1 >= 0) && (paramInt1 < i));
    return paramBoolean ? this.text.length() : 0;
  }

  public int getOffset(Point paramPoint, int[] paramArrayOfInt)
  {
    checkLayout();
    if (paramPoint == null)
      SWT.error(4);
    return getOffset(paramPoint.x, paramPoint.y, paramArrayOfInt);
  }

  public int getOffset(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    checkLayout();
    computeRuns(null);
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length < 1))
      SWT.error(5);
    int j = this.runs.length;
    for (int i = 0; i < j; i++)
      if (this.lineY[(i + 1)] > paramInt2)
        break;
    i = Math.min(i, this.runs.length - 1);
    StyleItem[] arrayOfStyleItem = this.runs[i];
    int k = getLineIndent(i);
    if (paramInt1 >= k + this.lineWidth[i])
      paramInt1 = k + this.lineWidth[i] - 1;
    if (paramInt1 < k)
      paramInt1 = k;
    int m = -1;
    int n = arrayOfStyleItem.length;
    while (n - m > 1)
    {
      int i1 = (n + m) / 2;
      StyleItem localStyleItem2 = arrayOfStyleItem[i1];
      if (localStyleItem2.x > paramInt1)
      {
        n = i1;
      }
      else if (localStyleItem2.x + localStyleItem2.width <= paramInt1)
      {
        m = i1;
      }
      else
      {
        if ((localStyleItem2.lineBreak) && (!localStyleItem2.softBreak))
          return untranslateOffset(localStyleItem2.start);
        int i2 = paramInt1 - localStyleItem2.x;
        if ((localStyleItem2.style != null) && (localStyleItem2.style.metrics != null))
        {
          GlyphMetrics localGlyphMetrics = localStyleItem2.style.metrics;
          if (localGlyphMetrics.width > 0)
          {
            if (paramArrayOfInt != null)
              paramArrayOfInt[0] = (i2 % localGlyphMetrics.width < localGlyphMetrics.width / 2 ? 0 : 1);
            return untranslateOffset(localStyleItem2.start + i2 / localGlyphMetrics.width);
          }
        }
        if (localStyleItem2.tab)
        {
          if (paramArrayOfInt != null)
            paramArrayOfInt[0] = (paramInt1 < localStyleItem2.x + localStyleItem2.width / 2 ? 0 : 1);
          return untranslateOffset(localStyleItem2.start);
        }
        int i3 = localStyleItem2.length;
        int i4 = localStyleItem2.glyphCount;
        int[] arrayOfInt1 = new int[1];
        int[] arrayOfInt2 = new int[1];
        if ((this.orientation & 0x4000000) != 0)
          i2 = localStyleItem2.width - i2;
        int i5 = localStyleItem2.justify != 0 ? localStyleItem2.justify : localStyleItem2.advances;
        OS.ScriptXtoCP(i2, i3, i4, localStyleItem2.clusters, localStyleItem2.visAttrs, i5, localStyleItem2.analysis, arrayOfInt1, arrayOfInt2);
        if (paramArrayOfInt != null)
          paramArrayOfInt[0] = arrayOfInt2[0];
        return untranslateOffset(localStyleItem2.start + arrayOfInt1[0]);
      }
    }
    if (paramArrayOfInt != null)
      paramArrayOfInt[0] = 0;
    if (arrayOfStyleItem.length == 1)
    {
      StyleItem localStyleItem1 = arrayOfStyleItem[0];
      if ((localStyleItem1.lineBreak) && (!localStyleItem1.softBreak))
        return untranslateOffset(localStyleItem1.start);
    }
    return untranslateOffset(this.lineOffset[(i + 1)]);
  }

  public int getOrientation()
  {
    checkLayout();
    return this.orientation;
  }

  void getPartialSelection(StyleItem paramStyleItem, int paramInt1, int paramInt2, RECT paramRECT)
  {
    int i = paramStyleItem.start + paramStyleItem.length - 1;
    int j = Math.max(paramInt1, paramStyleItem.start) - paramStyleItem.start;
    int k = Math.min(paramInt2, i) - paramStyleItem.start;
    int m = paramStyleItem.length;
    int n = paramStyleItem.glyphCount;
    int[] arrayOfInt = new int[1];
    int i1 = paramRECT.left;
    int i2 = paramStyleItem.justify != 0 ? paramStyleItem.justify : paramStyleItem.advances;
    OS.ScriptCPtoX(j, false, m, n, paramStyleItem.clusters, paramStyleItem.visAttrs, i2, paramStyleItem.analysis, arrayOfInt);
    int i3 = (this.orientation & 0x4000000) != 0 ? paramStyleItem.width - arrayOfInt[0] : arrayOfInt[0];
    paramRECT.left = (i1 + i3);
    OS.ScriptCPtoX(k, true, m, n, paramStyleItem.clusters, paramStyleItem.visAttrs, i2, paramStyleItem.analysis, arrayOfInt);
    i3 = (this.orientation & 0x4000000) != 0 ? paramStyleItem.width - arrayOfInt[0] : arrayOfInt[0];
    paramRECT.right = (i1 + i3);
  }

  public int getPreviousOffset(int paramInt1, int paramInt2)
  {
    checkLayout();
    return _getOffset(paramInt1, paramInt2, false);
  }

  public int[] getRanges()
  {
    checkLayout();
    Object localObject = new int[this.stylesCount * 2];
    int i = 0;
    for (int j = 0; j < this.stylesCount - 1; j++)
      if (this.styles[j].style != null)
      {
        localObject[(i++)] = this.styles[j].start;
        localObject[(i++)] = (this.styles[(j + 1)].start - 1);
      }
    if (i != localObject.length)
    {
      int[] arrayOfInt = new int[i];
      System.arraycopy(localObject, 0, arrayOfInt, 0, i);
      localObject = arrayOfInt;
    }
    return localObject;
  }

  public int[] getSegments()
  {
    checkLayout();
    return this.segments;
  }

  public char[] getSegmentsChars()
  {
    checkLayout();
    return this.segmentsChars;
  }

  String getSegmentsText()
  {
    int i = this.text.length();
    if (i == 0)
      return this.text;
    if (this.segments == null)
      return this.text;
    int j = this.segments.length;
    if (j == 0)
      return this.text;
    if (this.segmentsChars == null)
    {
      if (j == 1)
        return this.text;
      if ((j == 2) && (this.segments[0] == 0) && (this.segments[1] == i))
        return this.text;
    }
    char[] arrayOfChar1 = new char[i];
    this.text.getChars(0, i, arrayOfChar1, 0);
    char[] arrayOfChar2 = new char[i + j];
    int k = 0;
    int m = 0;
    int n = this.orientation == 67108864 ? 8207 : 8206;
    int i1;
    while (k < i)
      if ((m < j) && (k == this.segments[m]))
      {
        i1 = (this.segmentsChars != null) && (this.segmentsChars.length > m) ? this.segmentsChars[m] : n;
        arrayOfChar2[(k + m++)] = i1;
      }
      else
      {
        arrayOfChar2[(k + m)] = arrayOfChar1[(k++)];
      }
    while (m < j)
    {
      this.segments[m] = k;
      i1 = (this.segmentsChars != null) && (this.segmentsChars.length > m) ? this.segmentsChars[m] : n;
      arrayOfChar2[(k + m++)] = i1;
    }
    return new String(arrayOfChar2, 0, arrayOfChar2.length);
  }

  public int getSpacing()
  {
    checkLayout();
    return this.lineSpacing;
  }

  public TextStyle getStyle(int paramInt)
  {
    checkLayout();
    int i = this.text.length();
    if ((paramInt < 0) || (paramInt >= i))
      SWT.error(6);
    for (int j = 1; j < this.stylesCount; j++)
      if (this.styles[j].start > paramInt)
        return this.styles[(j - 1)].style;
    return null;
  }

  public TextStyle[] getStyles()
  {
    checkLayout();
    Object localObject = new TextStyle[this.stylesCount];
    int i = 0;
    for (int j = 0; j < this.stylesCount; j++)
      if (this.styles[j].style != null)
        localObject[(i++)] = this.styles[j].style;
    if (i != localObject.length)
    {
      TextStyle[] arrayOfTextStyle = new TextStyle[i];
      System.arraycopy(localObject, 0, arrayOfTextStyle, 0, i);
      localObject = arrayOfTextStyle;
    }
    return localObject;
  }

  public int[] getTabs()
  {
    checkLayout();
    return this.tabs;
  }

  public String getText()
  {
    checkLayout();
    return this.text;
  }

  public int getWidth()
  {
    checkLayout();
    return this.wrapWidth;
  }

  public int getWrapIndent()
  {
    checkLayout();
    return this.wrapIndent;
  }

  public boolean isDisposed()
  {
    return this.device == null;
  }

  StyleItem[] itemize()
  {
    this.segmentsText = getSegmentsText();
    int i = this.segmentsText.length();
    SCRIPT_CONTROL localSCRIPT_CONTROL = new SCRIPT_CONTROL();
    SCRIPT_STATE localSCRIPT_STATE = new SCRIPT_STATE();
    int j = i + 1;
    if ((this.orientation & 0x4000000) != 0)
    {
      localSCRIPT_STATE.uBidiLevel = 1;
      localSCRIPT_STATE.fArabicNumContext = true;
    }
    OS.ScriptApplyDigitSubstitution(null, localSCRIPT_CONTROL, localSCRIPT_STATE);
    int k = OS.GetProcessHeap();
    int m = OS.HeapAlloc(k, 8, j * SCRIPT_ITEM.sizeof);
    if (m == 0)
      SWT.error(2);
    int[] arrayOfInt = new int[1];
    char[] arrayOfChar = new char[i];
    this.segmentsText.getChars(0, i, arrayOfChar, 0);
    OS.ScriptItemize(arrayOfChar, i, j, localSCRIPT_CONTROL, localSCRIPT_STATE, m, arrayOfInt);
    StyleItem[] arrayOfStyleItem = merge(m, arrayOfInt[0]);
    OS.HeapFree(k, 0, m);
    return arrayOfStyleItem;
  }

  StyleItem[] merge(int paramInt1, int paramInt2)
  {
    if (this.styles.length > this.stylesCount)
    {
      StyleItem[] arrayOfStyleItem1 = new StyleItem[this.stylesCount];
      System.arraycopy(this.styles, 0, arrayOfStyleItem1, 0, this.stylesCount);
      this.styles = arrayOfStyleItem1;
    }
    int i = 0;
    int j = 0;
    int k = this.segmentsText.length();
    int m = 0;
    int n = 0;
    StyleItem[] arrayOfStyleItem2 = new StyleItem[paramInt2 + this.stylesCount];
    SCRIPT_ITEM localSCRIPT_ITEM = new SCRIPT_ITEM();
    int i1 = -1;
    int i2 = 0;
    int i3 = 0;
    int i4 = paramInt2 > 1024 ? 1 : 0;
    SCRIPT_PROPERTIES localSCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
    while (j < k)
    {
      localStyleItem = new StyleItem();
      localStyleItem.start = j;
      localStyleItem.style = this.styles[n].style;
      arrayOfStyleItem2[(i++)] = localStyleItem;
      OS.MoveMemory(localSCRIPT_ITEM, paramInt1 + m * SCRIPT_ITEM.sizeof, SCRIPT_ITEM.sizeof);
      localStyleItem.analysis = localSCRIPT_ITEM.a;
      localSCRIPT_ITEM.a = new SCRIPT_ANALYSIS();
      if (i3 != 0)
      {
        localStyleItem.analysis.fLinkBefore = true;
        i3 = 0;
      }
      int i5 = this.segmentsText.charAt(j);
      switch (i5)
      {
      case 10:
      case 13:
        localStyleItem.lineBreak = true;
        break;
      case 9:
        localStyleItem.tab = true;
      case 11:
      case 12:
      }
      char c1;
      if (i1 == -1)
      {
        i2 = m + 1;
        OS.MoveMemory(localSCRIPT_ITEM, paramInt1 + i2 * SCRIPT_ITEM.sizeof, SCRIPT_ITEM.sizeof);
        i1 = localSCRIPT_ITEM.iCharPos;
        if ((i2 < paramInt2) && (i5 == 13) && (this.segmentsText.charAt(i1) == '\n'))
        {
          i2 = m + 2;
          OS.MoveMemory(localSCRIPT_ITEM, paramInt1 + i2 * SCRIPT_ITEM.sizeof, SCRIPT_ITEM.sizeof);
          i1 = localSCRIPT_ITEM.iCharPos;
        }
        if ((i2 < paramInt2) && (i4 != 0) && (!localStyleItem.lineBreak))
        {
          OS.MoveMemory(localSCRIPT_PROPERTIES, this.device.scripts[localStyleItem.analysis.eScript], SCRIPT_PROPERTIES.sizeof);
          if ((!localSCRIPT_PROPERTIES.fComplex) || (localStyleItem.tab))
            for (i6 = 0; i6 < 512; i6++)
            {
              if (i2 == paramInt2)
                break;
              c1 = this.segmentsText.charAt(i1);
              if ((c1 == '\n') || (c1 == '\r'))
                break;
              if ((c1 == '\t') != localStyleItem.tab)
                break;
              OS.MoveMemory(localSCRIPT_PROPERTIES, this.device.scripts[localSCRIPT_ITEM.a.eScript], SCRIPT_PROPERTIES.sizeof);
              if ((!localStyleItem.tab) && (localSCRIPT_PROPERTIES.fComplex))
                break;
              i2++;
              OS.MoveMemory(localSCRIPT_ITEM, paramInt1 + i2 * SCRIPT_ITEM.sizeof, SCRIPT_ITEM.sizeof);
              i1 = localSCRIPT_ITEM.iCharPos;
            }
        }
      }
      int i6 = translateOffset(this.styles[(n + 1)].start);
      if (i6 <= i1)
      {
        n++;
        j = i6;
        if ((j < i1) && (j > 0) && (j < k))
        {
          c1 = this.segmentsText.charAt(j - 1);
          char c2 = this.segmentsText.charAt(j);
          if ((Compatibility.isLetter(c1)) && (Compatibility.isLetter(c2)))
          {
            localStyleItem.analysis.fLinkAfter = true;
            i3 = 1;
          }
        }
      }
      if (i1 <= i6)
      {
        m = i2;
        j = i1;
        i1 = -1;
      }
      localStyleItem.length = (j - localStyleItem.start);
    }
    StyleItem localStyleItem = new StyleItem();
    localStyleItem.start = k;
    OS.MoveMemory(localSCRIPT_ITEM, paramInt1 + paramInt2 * SCRIPT_ITEM.sizeof, SCRIPT_ITEM.sizeof);
    localStyleItem.analysis = localSCRIPT_ITEM.a;
    arrayOfStyleItem2[(i++)] = localStyleItem;
    if (arrayOfStyleItem2.length != i)
    {
      StyleItem[] arrayOfStyleItem3 = new StyleItem[i];
      System.arraycopy(arrayOfStyleItem2, 0, arrayOfStyleItem3, 0, i);
      return arrayOfStyleItem3;
    }
    return arrayOfStyleItem2;
  }

  StyleItem[] reorder(StyleItem[] paramArrayOfStyleItem, boolean paramBoolean)
  {
    int i = paramArrayOfStyleItem.length;
    if (i <= 1)
      return paramArrayOfStyleItem;
    byte[] arrayOfByte = new byte[i];
    for (int j = 0; j < i; j++)
      arrayOfByte[j] = ((byte)(paramArrayOfStyleItem[j].analysis.s.uBidiLevel & 0x1F));
    StyleItem localStyleItem1 = paramArrayOfStyleItem[(i - 1)];
    if ((localStyleItem1.lineBreak) && (!localStyleItem1.softBreak))
      arrayOfByte[(i - 1)] = 0;
    int[] arrayOfInt = new int[i];
    OS.ScriptLayout(i, arrayOfByte, null, arrayOfInt);
    StyleItem[] arrayOfStyleItem = new StyleItem[i];
    for (int k = 0; k < i; k++)
      arrayOfStyleItem[arrayOfInt[k]] = paramArrayOfStyleItem[k];
    if ((this.orientation & 0x4000000) != 0)
    {
      if (paramBoolean)
        i--;
      for (k = 0; k < i / 2; k++)
      {
        StyleItem localStyleItem2 = arrayOfStyleItem[k];
        arrayOfStyleItem[k] = arrayOfStyleItem[(i - k - 1)];
        arrayOfStyleItem[(i - k - 1)] = localStyleItem2;
      }
    }
    return arrayOfStyleItem;
  }

  public void setAlignment(int paramInt)
  {
    checkLayout();
    int i = 16924672;
    paramInt &= i;
    if (paramInt == 0)
      return;
    if ((paramInt & 0x4000) != 0)
      paramInt = 16384;
    if ((paramInt & 0x20000) != 0)
      paramInt = 131072;
    if (this.alignment == paramInt)
      return;
    freeRuns();
    this.alignment = paramInt;
  }

  public void setAscent(int paramInt)
  {
    checkLayout();
    if (paramInt < -1)
      SWT.error(5);
    if (this.ascent == paramInt)
      return;
    freeRuns();
    this.ascent = paramInt;
  }

  public void setDescent(int paramInt)
  {
    checkLayout();
    if (paramInt < -1)
      SWT.error(5);
    if (this.descent == paramInt)
      return;
    freeRuns();
    this.descent = paramInt;
  }

  public void setFont(Font paramFont)
  {
    checkLayout();
    if ((paramFont != null) && (paramFont.isDisposed()))
      SWT.error(5);
    Font localFont = this.font;
    if (localFont == paramFont)
      return;
    this.font = paramFont;
    if ((localFont != null) && (localFont.equals(paramFont)))
      return;
    freeRuns();
  }

  public void setIndent(int paramInt)
  {
    checkLayout();
    if (paramInt < 0)
      return;
    if (this.indent == paramInt)
      return;
    freeRuns();
    this.indent = paramInt;
  }

  public void setJustify(boolean paramBoolean)
  {
    checkLayout();
    if (this.justify == paramBoolean)
      return;
    freeRuns();
    this.justify = paramBoolean;
  }

  public void setOrientation(int paramInt)
  {
    checkLayout();
    int i = 100663296;
    paramInt &= i;
    if (paramInt == 0)
      return;
    if ((paramInt & 0x2000000) != 0)
      paramInt = 33554432;
    if (this.orientation == paramInt)
      return;
    this.orientation = paramInt;
    freeRuns();
  }

  public void setSegments(int[] paramArrayOfInt)
  {
    checkLayout();
    if ((this.segments == null) && (paramArrayOfInt == null))
      return;
    if ((this.segments != null) && (paramArrayOfInt != null) && (this.segments.length == paramArrayOfInt.length))
    {
      for (int i = 0; i < paramArrayOfInt.length; i++)
        if (this.segments[i] != paramArrayOfInt[i])
          break;
      if (i == paramArrayOfInt.length)
        return;
    }
    freeRuns();
    this.segments = paramArrayOfInt;
  }

  public void setSegmentsChars(char[] paramArrayOfChar)
  {
    checkLayout();
    if ((this.segmentsChars == null) && (paramArrayOfChar == null))
      return;
    if ((this.segmentsChars != null) && (paramArrayOfChar != null) && (this.segmentsChars.length == paramArrayOfChar.length))
    {
      for (int i = 0; i < paramArrayOfChar.length; i++)
        if (this.segmentsChars[i] != paramArrayOfChar[i])
          break;
      if (i == paramArrayOfChar.length)
        return;
    }
    freeRuns();
    this.segmentsChars = paramArrayOfChar;
  }

  public void setSpacing(int paramInt)
  {
    checkLayout();
    if (paramInt < 0)
      SWT.error(5);
    if (this.lineSpacing == paramInt)
      return;
    freeRuns();
    this.lineSpacing = paramInt;
  }

  public void setStyle(TextStyle paramTextStyle, int paramInt1, int paramInt2)
  {
    checkLayout();
    int i = this.text.length();
    if (i == 0)
      return;
    if (paramInt1 > paramInt2)
      return;
    paramInt1 = Math.min(Math.max(0, paramInt1), i - 1);
    paramInt2 = Math.min(Math.max(0, paramInt2), i - 1);
    int j = -1;
    StyleItem localStyleItem1 = this.stylesCount;
    while (localStyleItem1 - j > 1)
    {
      StyleItem localStyleItem2 = (localStyleItem1 + j) / 2;
      if (this.styles[(localStyleItem2 + 1)].start > paramInt1)
        localStyleItem1 = localStyleItem2;
      else
        j = localStyleItem2;
    }
    if ((localStyleItem1 >= 0) && (localStyleItem1 < this.stylesCount))
    {
      localStyleItem3 = this.styles[localStyleItem1];
      if ((localStyleItem3.start == paramInt1) && (this.styles[(localStyleItem1 + 1)].start - 1 == paramInt2))
        if (paramTextStyle == null)
        {
          if (localStyleItem3.style != null);
        }
        else if (paramTextStyle.equals(localStyleItem3.style))
          return;
    }
    freeRuns();
    StyleItem localStyleItem3 = localStyleItem1;
    for (StyleItem localStyleItem4 = localStyleItem3; localStyleItem4 < this.stylesCount; localStyleItem4++)
      if (this.styles[(localStyleItem4 + 1)].start > paramInt2)
        break;
    int m;
    if (localStyleItem3 == localStyleItem4)
    {
      k = this.styles[localStyleItem3].start;
      m = this.styles[(localStyleItem4 + 1)].start - 1;
      if ((k == paramInt1) && (m == paramInt2))
      {
        this.styles[localStyleItem3].style = paramTextStyle;
        return;
      }
      if ((k != paramInt1) && (m != paramInt2))
      {
        int n = this.stylesCount + 2;
        if (n > this.styles.length)
        {
          int i1 = Math.min(n + 1024, Math.max(64, n * 2));
          StyleItem[] arrayOfStyleItem2 = new StyleItem[i1];
          System.arraycopy(this.styles, 0, arrayOfStyleItem2, 0, this.stylesCount);
          this.styles = arrayOfStyleItem2;
        }
        System.arraycopy(this.styles, localStyleItem4 + 1, this.styles, localStyleItem4 + 3, this.stylesCount - localStyleItem4 - 1);
        StyleItem localStyleItem6 = new StyleItem();
        localStyleItem6.start = paramInt1;
        localStyleItem6.style = paramTextStyle;
        this.styles[(localStyleItem3 + 1)] = localStyleItem6;
        localStyleItem6 = new StyleItem();
        localStyleItem6.start = (paramInt2 + 1);
        localStyleItem6.style = this.styles[localStyleItem3].style;
        this.styles[(localStyleItem3 + 2)] = localStyleItem6;
        this.stylesCount = n;
        return;
      }
    }
    if (paramInt1 == this.styles[localStyleItem3].start)
      localStyleItem3--;
    if (paramInt2 == this.styles[(localStyleItem4 + 1)].start - 1)
      localStyleItem4++;
    int k = this.stylesCount + 1 - (localStyleItem4 - localStyleItem3 - 1);
    if (k > this.styles.length)
    {
      m = Math.min(k + 1024, Math.max(64, k * 2));
      StyleItem[] arrayOfStyleItem1 = new StyleItem[m];
      System.arraycopy(this.styles, 0, arrayOfStyleItem1, 0, this.stylesCount);
      this.styles = arrayOfStyleItem1;
    }
    System.arraycopy(this.styles, localStyleItem4, this.styles, localStyleItem3 + 2, this.stylesCount - localStyleItem4);
    StyleItem localStyleItem5 = new StyleItem();
    localStyleItem5.start = paramInt1;
    localStyleItem5.style = paramTextStyle;
    this.styles[(localStyleItem3 + 1)] = localStyleItem5;
    this.styles[(localStyleItem3 + 2)].start = (paramInt2 + 1);
    this.stylesCount = k;
  }

  public void setTabs(int[] paramArrayOfInt)
  {
    checkLayout();
    if ((this.tabs == null) && (paramArrayOfInt == null))
      return;
    if ((this.tabs != null) && (paramArrayOfInt != null) && (this.tabs.length == paramArrayOfInt.length))
    {
      for (int i = 0; i < paramArrayOfInt.length; i++)
        if (this.tabs[i] != paramArrayOfInt[i])
          break;
      if (i == paramArrayOfInt.length)
        return;
    }
    freeRuns();
    this.tabs = paramArrayOfInt;
  }

  public void setText(String paramString)
  {
    checkLayout();
    if (paramString == null)
      SWT.error(4);
    if (paramString.equals(this.text))
      return;
    freeRuns();
    this.text = paramString;
    this.styles = new StyleItem[2];
    this.styles[0] = new StyleItem();
    this.styles[1] = new StyleItem();
    this.styles[1].start = paramString.length();
    this.stylesCount = 2;
  }

  public void setWidth(int paramInt)
  {
    checkLayout();
    if ((paramInt < -1) || (paramInt == 0))
      SWT.error(5);
    if (this.wrapWidth == paramInt)
      return;
    freeRuns();
    this.wrapWidth = paramInt;
  }

  public void setWrapIndent(int paramInt)
  {
    checkLayout();
    if (paramInt < 0)
      return;
    if (this.wrapIndent == paramInt)
      return;
    freeRuns();
    this.wrapIndent = paramInt;
  }

  boolean shape(int paramInt1, StyleItem paramStyleItem, char[] paramArrayOfChar, int[] paramArrayOfInt, int paramInt2, SCRIPT_PROPERTIES paramSCRIPT_PROPERTIES)
  {
    int i = (!paramSCRIPT_PROPERTIES.fComplex) && (!paramStyleItem.analysis.fNoGlyphIndex) ? 1 : 0;
    if (i != 0)
    {
      short[] arrayOfShort1 = new short[paramArrayOfChar.length];
      if (OS.ScriptGetCMap(paramInt1, paramStyleItem.psc, paramArrayOfChar, paramArrayOfChar.length, 0, arrayOfShort1) != 0)
      {
        if (paramStyleItem.psc != 0)
        {
          OS.ScriptFreeCache(paramStyleItem.psc);
          paramArrayOfInt[0] = 0;
          OS.MoveMemory(paramStyleItem.psc, new int[1], OS.PTR_SIZEOF);
        }
        return false;
      }
    }
    int j = OS.ScriptShape(paramInt1, paramStyleItem.psc, paramArrayOfChar, paramArrayOfChar.length, paramInt2, paramStyleItem.analysis, paramStyleItem.glyphs, paramStyleItem.clusters, paramStyleItem.visAttrs, paramArrayOfInt);
    paramStyleItem.glyphCount = paramArrayOfInt[0];
    if (i != 0)
      return true;
    if (j != -2147220992)
    {
      if (paramStyleItem.analysis.fNoGlyphIndex)
        return true;
      SCRIPT_FONTPROPERTIES localSCRIPT_FONTPROPERTIES = new SCRIPT_FONTPROPERTIES();
      localSCRIPT_FONTPROPERTIES.cBytes = SCRIPT_FONTPROPERTIES.sizeof;
      OS.ScriptGetFontProperties(paramInt1, paramStyleItem.psc, localSCRIPT_FONTPROPERTIES);
      short[] arrayOfShort2 = new short[paramArrayOfInt[0]];
      OS.MoveMemory(arrayOfShort2, paramStyleItem.glyphs, arrayOfShort2.length * 2);
      for (int k = 0; k < arrayOfShort2.length; k++)
        if (arrayOfShort2[k] == localSCRIPT_FONTPROPERTIES.wgDefault)
          break;
      if (k == arrayOfShort2.length)
        return true;
    }
    if (paramStyleItem.psc != 0)
    {
      OS.ScriptFreeCache(paramStyleItem.psc);
      paramArrayOfInt[0] = 0;
      OS.MoveMemory(paramStyleItem.psc, new int[1], OS.PTR_SIZEOF);
    }
    paramStyleItem.glyphCount = 0;
    return false;
  }

  void shape(int paramInt, StyleItem paramStyleItem)
  {
    if ((paramStyleItem.tab) || (paramStyleItem.lineBreak))
      return;
    if (paramStyleItem.glyphs != 0)
      return;
    int[] arrayOfInt1 = new int[1];
    char[] arrayOfChar = new char[paramStyleItem.length];
    this.segmentsText.getChars(paramStyleItem.start, paramStyleItem.start + paramStyleItem.length, arrayOfChar, 0);
    int i = arrayOfChar.length * 3 / 2 + 16;
    int j = OS.GetProcessHeap();
    paramStyleItem.glyphs = OS.HeapAlloc(j, 8, i * 2);
    if (paramStyleItem.glyphs == 0)
      SWT.error(2);
    paramStyleItem.clusters = OS.HeapAlloc(j, 8, i * 2);
    if (paramStyleItem.clusters == 0)
      SWT.error(2);
    paramStyleItem.visAttrs = OS.HeapAlloc(j, 8, i * 2);
    if (paramStyleItem.visAttrs == 0)
      SWT.error(2);
    paramStyleItem.psc = OS.HeapAlloc(j, 8, OS.PTR_SIZEOF);
    if (paramStyleItem.psc == 0)
      SWT.error(2);
    int k = paramStyleItem.analysis.eScript;
    SCRIPT_PROPERTIES localSCRIPT_PROPERTIES = new SCRIPT_PROPERTIES();
    OS.MoveMemory(localSCRIPT_PROPERTIES, this.device.scripts[k], SCRIPT_PROPERTIES.sizeof);
    boolean bool = shape(paramInt, paramStyleItem, arrayOfChar, arrayOfInt1, i, localSCRIPT_PROPERTIES);
    if ((!bool) && (localSCRIPT_PROPERTIES.fPrivateUseArea))
    {
      paramStyleItem.analysis.fNoGlyphIndex = true;
      bool = shape(paramInt, paramStyleItem, arrayOfChar, arrayOfInt1, i, localSCRIPT_PROPERTIES);
    }
    Object localObject1;
    Object localObject2;
    if (!bool)
    {
      int m = OS.GetCurrentObject(paramInt, 6);
      int n = 0;
      localObject1 = new char[Math.min(arrayOfChar.length, 2)];
      localObject2 = new SCRIPT_LOGATTR();
      breakRun(paramStyleItem);
      int i1 = 0;
      for (int i2 = 0; i2 < arrayOfChar.length; i2++)
      {
        OS.MoveMemory((SCRIPT_LOGATTR)localObject2, paramStyleItem.psla + i2 * SCRIPT_LOGATTR.sizeof, SCRIPT_LOGATTR.sizeof);
        if (!((SCRIPT_LOGATTR)localObject2).fWhiteSpace)
        {
          localObject1[(i1++)] = arrayOfChar[i2];
          if (i1 == localObject1.length)
            break;
        }
      }
      int i6;
      Object localObject3;
      Object localObject4;
      if (i1 > 0)
      {
        i2 = OS.HeapAlloc(j, 8, OS.SCRIPT_STRING_ANALYSIS_sizeof());
        int i3 = OS.CreateEnhMetaFile(paramInt, null, null, null);
        int i4 = OS.SelectObject(i3, m);
        int i5 = 6304;
        if (OS.ScriptStringAnalyse(i3, (char[])localObject1, i1, 0, -1, i5, 0, null, null, 0, 0, 0, i2) == 0)
        {
          OS.ScriptStringOut(i2, 0, 0, 0, null, 0, 0, false);
          OS.ScriptStringFree(i2);
        }
        OS.HeapFree(j, 0, i2);
        OS.SelectObject(i3, i4);
        i6 = OS.CloseEnhMetaFile(i3);
        EMREXTCREATEFONTINDIRECTW localEMREXTCREATEFONTINDIRECTW = new EMREXTCREATEFONTINDIRECTW();
        1.MetaFileEnumProc localMetaFileEnumProc = new 1.MetaFileEnumProc(localEMREXTCREATEFONTINDIRECTW);
        int i8 = 0;
        if (i8 != 0)
          localMetaFileEnumProc.metaFileEnumProc(0, 0, 0, 0, 0);
        Callback localCallback = new Callback(localMetaFileEnumProc, "metaFileEnumProc", 5);
        int i9 = localCallback.getAddress();
        if (i9 == 0)
          SWT.error(3);
        OS.EnumEnhMetaFile(0, i6, i9, 0, null);
        OS.DeleteEnhMetaFile(i6);
        localCallback.dispose();
        n = OS.CreateFontIndirectW(localEMREXTCREATEFONTINDIRECTW.elfw.elfLogFont);
      }
      else
      {
        for (i2 = 0; i2 < this.allRuns.length - 1; i2++)
          if (this.allRuns[i2] == paramStyleItem)
          {
            if (i2 > 0)
            {
              localObject3 = this.allRuns[(i2 - 1)];
              if ((((StyleItem)localObject3).fallbackFont != 0) && (((StyleItem)localObject3).analysis.eScript == paramStyleItem.analysis.eScript))
              {
                localObject4 = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
                OS.GetObject(((StyleItem)localObject3).fallbackFont, LOGFONT.sizeof, (LOGFONT)localObject4);
                n = OS.CreateFontIndirect((LOGFONT)localObject4);
              }
            }
            if ((n != 0) || (i2 + 1 >= this.allRuns.length - 1))
              break;
            localObject3 = this.allRuns[(i2 + 1)];
            if (((StyleItem)localObject3).analysis.eScript != paramStyleItem.analysis.eScript)
              break;
            shape(paramInt, (StyleItem)localObject3);
            if (((StyleItem)localObject3).fallbackFont == 0)
              break;
            localObject4 = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
            OS.GetObject(((StyleItem)localObject3).fallbackFont, LOGFONT.sizeof, (LOGFONT)localObject4);
            n = OS.CreateFontIndirect((LOGFONT)localObject4);
            break;
          }
      }
      if (n != 0)
      {
        OS.SelectObject(paramInt, n);
        if ((bool = shape(paramInt, paramStyleItem, arrayOfChar, arrayOfInt1, i, localSCRIPT_PROPERTIES)))
          paramStyleItem.fallbackFont = n;
      }
      if ((!bool) && (!localSCRIPT_PROPERTIES.fComplex))
      {
        paramStyleItem.analysis.fNoGlyphIndex = true;
        if ((bool = shape(paramInt, paramStyleItem, arrayOfChar, arrayOfInt1, i, localSCRIPT_PROPERTIES)))
          paramStyleItem.fallbackFont = n;
        else
          paramStyleItem.analysis.fNoGlyphIndex = false;
      }
      if ((!bool) && (this.mLangFontLink2 != 0))
      {
        int[] arrayOfInt3 = new int[1];
        localObject3 = new int[1];
        localObject4 = new int[1];
        OS.VtblCall(4, this.mLangFontLink2, arrayOfChar, arrayOfChar.length, 0, (int[])localObject3, (int[])localObject4);
        if (OS.VtblCall(10, this.mLangFontLink2, paramInt, localObject3[0], arrayOfChar[0], arrayOfInt3) == 0)
        {
          LOGFONTA localLOGFONTA = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
          OS.GetObject(arrayOfInt3[0], LOGFONT.sizeof, localLOGFONTA);
          OS.VtblCall(8, this.mLangFontLink2, arrayOfInt3[0]);
          i6 = OS.CreateFontIndirect(localLOGFONTA);
          int i7 = OS.SelectObject(paramInt, i6);
          if ((bool = shape(paramInt, paramStyleItem, arrayOfChar, arrayOfInt1, i, localSCRIPT_PROPERTIES)))
          {
            paramStyleItem.fallbackFont = i6;
          }
          else
          {
            OS.SelectObject(paramInt, i7);
            OS.DeleteObject(i6);
          }
        }
      }
      if (!bool)
        OS.SelectObject(paramInt, m);
      if ((n != 0) && (n != paramStyleItem.fallbackFont))
        OS.DeleteObject(n);
    }
    if (!bool)
    {
      OS.ScriptShape(paramInt, paramStyleItem.psc, arrayOfChar, arrayOfChar.length, i, paramStyleItem.analysis, paramStyleItem.glyphs, paramStyleItem.clusters, paramStyleItem.visAttrs, arrayOfInt1);
      paramStyleItem.glyphCount = arrayOfInt1[0];
    }
    int[] arrayOfInt2 = new int[3];
    paramStyleItem.advances = OS.HeapAlloc(j, 8, paramStyleItem.glyphCount * 4);
    if (paramStyleItem.advances == 0)
      SWT.error(2);
    paramStyleItem.goffsets = OS.HeapAlloc(j, 8, paramStyleItem.glyphCount * 8);
    if (paramStyleItem.goffsets == 0)
      SWT.error(2);
    OS.ScriptPlace(paramInt, paramStyleItem.psc, paramStyleItem.glyphs, paramStyleItem.glyphCount, paramStyleItem.visAttrs, paramStyleItem.analysis, paramStyleItem.advances, paramStyleItem.goffsets, arrayOfInt2);
    paramStyleItem.width = (arrayOfInt2[0] + arrayOfInt2[1] + arrayOfInt2[2]);
    TextStyle localTextStyle = paramStyleItem.style;
    if (localTextStyle != null)
    {
      localObject1 = null;
      if ((localTextStyle.underline) || (localTextStyle.strikeout))
      {
        localObject1 = OS.IsUnicode ? new OUTLINETEXTMETRICW() : new OUTLINETEXTMETRICA();
        if (OS.GetOutlineTextMetrics(paramInt, OUTLINETEXTMETRIC.sizeof, (OUTLINETEXTMETRIC)localObject1) == 0)
          localObject1 = null;
      }
      if (localTextStyle.metrics != null)
      {
        localObject2 = localTextStyle.metrics;
        paramStyleItem.width = (((GlyphMetrics)localObject2).width * Math.max(1, paramStyleItem.glyphCount));
        paramStyleItem.ascent = ((GlyphMetrics)localObject2).ascent;
        paramStyleItem.descent = ((GlyphMetrics)localObject2).descent;
        paramStyleItem.leading = 0;
      }
      else
      {
        localObject2 = null;
        if (localObject1 != null)
        {
          localObject2 = OS.IsUnicode ? ((OUTLINETEXTMETRICW)localObject1).otmTextMetrics : ((OUTLINETEXTMETRICA)localObject1).otmTextMetrics;
        }
        else
        {
          localObject2 = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
          OS.GetTextMetrics(paramInt, (TEXTMETRIC)localObject2);
        }
        paramStyleItem.ascent = ((TEXTMETRIC)localObject2).tmAscent;
        paramStyleItem.descent = ((TEXTMETRIC)localObject2).tmDescent;
        paramStyleItem.leading = ((TEXTMETRIC)localObject2).tmInternalLeading;
      }
      if (localObject1 != null)
      {
        paramStyleItem.underlinePos = ((OUTLINETEXTMETRIC)localObject1).otmsUnderscorePosition;
        paramStyleItem.underlineThickness = Math.max(1, ((OUTLINETEXTMETRIC)localObject1).otmsUnderscoreSize);
        paramStyleItem.strikeoutPos = ((OUTLINETEXTMETRIC)localObject1).otmsStrikeoutPosition;
        paramStyleItem.strikeoutThickness = Math.max(1, ((OUTLINETEXTMETRIC)localObject1).otmsStrikeoutSize);
      }
      else
      {
        paramStyleItem.underlinePos = 1;
        paramStyleItem.underlineThickness = 1;
        paramStyleItem.strikeoutPos = (paramStyleItem.ascent / 2);
        paramStyleItem.strikeoutThickness = 1;
      }
      paramStyleItem.ascent += localTextStyle.rise;
      paramStyleItem.descent -= localTextStyle.rise;
    }
    else
    {
      localObject1 = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
      OS.GetTextMetrics(paramInt, (TEXTMETRIC)localObject1);
      paramStyleItem.ascent = ((TEXTMETRIC)localObject1).tmAscent;
      paramStyleItem.descent = ((TEXTMETRIC)localObject1).tmDescent;
      paramStyleItem.leading = ((TEXTMETRIC)localObject1).tmInternalLeading;
    }
  }

  int validadeOffset(int paramInt1, int paramInt2)
  {
    paramInt1 = untranslateOffset(paramInt1);
    return translateOffset(paramInt1 + paramInt2);
  }

  public String toString()
  {
    if (isDisposed())
      return "TextLayout {*DISPOSED*}";
    return "TextLayout {}";
  }

  int translateOffset(int paramInt)
  {
    int i = this.text.length();
    if (i == 0)
      return paramInt;
    if (this.segments == null)
      return paramInt;
    int j = this.segments.length;
    if (j == 0)
      return paramInt;
    if (this.segmentsChars == null)
    {
      if (j == 1)
        return paramInt;
      if ((j == 2) && (this.segments[0] == 0) && (this.segments[1] == i))
        return paramInt;
    }
    for (int k = 0; (k < j) && (paramInt - k >= this.segments[k]); k++)
      paramInt++;
    return paramInt;
  }

  int untranslateOffset(int paramInt)
  {
    int i = this.text.length();
    if (i == 0)
      return paramInt;
    if (this.segments == null)
      return paramInt;
    int j = this.segments.length;
    if (j == 0)
      return paramInt;
    if (this.segmentsChars == null)
    {
      if (j == 1)
        return paramInt;
      if ((j == 2) && (this.segments[0] == 0) && (this.segments[1] == i))
        return paramInt;
    }
    for (int k = 0; (k < j) && (paramInt > this.segments[k]); k++)
      paramInt--;
    return paramInt;
  }

  class 1$MetaFileEnumProc
  {
    private final EMREXTCREATEFONTINDIRECTW val$emr;

    1$MetaFileEnumProc(EMREXTCREATEFONTINDIRECTW arg2)
    {
      Object localObject;
      this.val$emr = localObject;
    }

    int metaFileEnumProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    {
      OS.MoveMemory(this.val$emr.emr, paramInt3, EMR.sizeof);
      switch (this.val$emr.emr.iType)
      {
      case 82:
        OS.MoveMemory(this.val$emr, paramInt3, EMREXTCREATEFONTINDIRECTW.sizeof);
        break;
      case 84:
        return 0;
      case 83:
      }
      return 1;
    }
  }

  class StyleItem
  {
    TextStyle style;
    int start;
    int length;
    boolean lineBreak;
    boolean softBreak;
    boolean tab;
    SCRIPT_ANALYSIS analysis;
    int psc = 0;
    int glyphs;
    int glyphCount;
    int clusters;
    int visAttrs;
    int advances;
    int goffsets;
    int width;
    int ascent;
    int descent;
    int leading;
    int x;
    int underlinePos;
    int underlineThickness;
    int strikeoutPos;
    int strikeoutThickness;
    int justify;
    int psla;
    int fallbackFont;

    StyleItem()
    {
    }

    void free()
    {
      int i = OS.GetProcessHeap();
      if (this.psc != 0)
      {
        OS.ScriptFreeCache(this.psc);
        OS.HeapFree(i, 0, this.psc);
        this.psc = 0;
      }
      if (this.glyphs != 0)
      {
        OS.HeapFree(i, 0, this.glyphs);
        this.glyphs = 0;
        this.glyphCount = 0;
      }
      if (this.clusters != 0)
      {
        OS.HeapFree(i, 0, this.clusters);
        this.clusters = 0;
      }
      if (this.visAttrs != 0)
      {
        OS.HeapFree(i, 0, this.visAttrs);
        this.visAttrs = 0;
      }
      if (this.advances != 0)
      {
        OS.HeapFree(i, 0, this.advances);
        this.advances = 0;
      }
      if (this.goffsets != 0)
      {
        OS.HeapFree(i, 0, this.goffsets);
        this.goffsets = 0;
      }
      if (this.justify != 0)
      {
        OS.HeapFree(i, 0, this.justify);
        this.justify = 0;
      }
      if (this.psla != 0)
      {
        OS.HeapFree(i, 0, this.psla);
        this.psla = 0;
      }
      if (this.fallbackFont != 0)
      {
        OS.DeleteObject(this.fallbackFont);
        this.fallbackFont = 0;
      }
      this.width = (this.ascent = this.descent = this.x = 0);
      this.lineBreak = (this.softBreak = 0);
    }

    public String toString()
    {
      return "StyleItem {" + this.start + ", " + this.style + "}";
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.TextLayout
 * JD-Core Version:    0.6.2
 */