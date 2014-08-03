package org.eclipse.swt.custom;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.accessibility.Accessible;
import org.eclipse.swt.accessibility.AccessibleAdapter;
import org.eclipse.swt.accessibility.AccessibleAttributeAdapter;
import org.eclipse.swt.accessibility.AccessibleAttributeEvent;
import org.eclipse.swt.accessibility.AccessibleControlAdapter;
import org.eclipse.swt.accessibility.AccessibleControlEvent;
import org.eclipse.swt.accessibility.AccessibleEvent;
import org.eclipse.swt.accessibility.AccessibleTextAttributeEvent;
import org.eclipse.swt.accessibility.AccessibleTextEvent;
import org.eclipse.swt.accessibility.AccessibleTextExtendedAdapter;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.internal.BidiUtil;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.IME;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.TypedListener;

public class StyledText extends Canvas
{
  static final char TAB = '\t';
  static final String PlatformLineDelimiter = System.getProperty("line.separator");
  static final int BIDI_CARET_WIDTH = 3;
  static final int DEFAULT_WIDTH = 64;
  static final int DEFAULT_HEIGHT = 64;
  static final int V_SCROLL_RATE = 50;
  static final int H_SCROLL_RATE = 10;
  static final int ExtendedModify = 3000;
  static final int LineGetBackground = 3001;
  static final int LineGetStyle = 3002;
  static final int TextChanging = 3003;
  static final int TextSet = 3004;
  static final int VerifyKey = 3005;
  static final int TextChanged = 3006;
  static final int LineGetSegments = 3007;
  static final int PaintObject = 3008;
  static final int WordNext = 3009;
  static final int WordPrevious = 3010;
  static final int CaretMoved = 3011;
  static final int PREVIOUS_OFFSET_TRAILING = 0;
  static final int OFFSET_LEADING = 1;
  Color selectionBackground;
  Color selectionForeground;
  StyledTextContent content;
  StyledTextRenderer renderer;
  Listener listener;
  TextChangeListener textChangeListener;
  int verticalScrollOffset = 0;
  int horizontalScrollOffset = 0;
  int topIndex = 0;
  int topIndexY;
  int clientAreaHeight = 0;
  int clientAreaWidth = 0;
  int tabLength = 4;
  int[] tabs;
  int leftMargin;
  int topMargin;
  int rightMargin;
  int bottomMargin;
  Color marginColor;
  int columnX;
  int caretOffset;
  int caretAlignment;
  Point selection = new Point(0, 0);
  Point clipboardSelection;
  int selectionAnchor;
  Point doubleClickSelection;
  boolean editable = true;
  boolean wordWrap = false;
  boolean doubleClickEnabled = true;
  boolean overwrite = false;
  int textLimit = -1;
  Hashtable keyActionMap = new Hashtable();
  Color background = null;
  Color foreground = null;
  Clipboard clipboard;
  int clickCount;
  int autoScrollDirection = 0;
  int autoScrollDistance = 0;
  int lastTextChangeStart;
  int lastTextChangeNewLineCount;
  int lastTextChangeNewCharCount;
  int lastTextChangeReplaceLineCount;
  int lastTextChangeReplaceCharCount;
  int lastCharCount = 0;
  int lastLineBottom;
  boolean isMirrored;
  boolean bidiColoring = false;
  Image leftCaretBitmap = null;
  Image rightCaretBitmap = null;
  int caretDirection = 0;
  int caretWidth = 0;
  Caret defaultCaret = null;
  boolean updateCaretDirection = true;
  boolean fixedLineHeight;
  boolean dragDetect = true;
  IME ime;
  Cursor cursor;
  int alignment;
  boolean justify;
  int indent;
  int wrapIndent;
  int lineSpacing;
  int alignmentMargin;
  int newOrientation = 0;
  int accCaretOffset;
  boolean blockSelection;
  int blockXAnchor = -1;
  int blockYAnchor = -1;
  int blockXLocation = -1;
  int blockYLocation = -1;
  static final boolean IS_MAC = ("carbon".equals(str)) || ("cocoa".equals(str));
  static final boolean IS_GTK = "gtk".equals(str);
  static final boolean IS_MOTIF = "motif".equals(str);

  static
  {
    String str = SWT.getPlatform();
  }

  public StyledText(Composite paramComposite, int paramInt)
  {
    super(paramComposite, checkStyle(paramInt));
    super.setForeground(getForeground());
    super.setDragDetect(false);
    Display localDisplay = getDisplay();
    this.isMirrored = ((super.getStyle() & 0x8000000) != 0);
    this.fixedLineHeight = true;
    if ((paramInt & 0x8) != 0)
      setEditable(false);
    this.leftMargin = (this.rightMargin = isBidiCaret() ? 2 : 0);
    if (((paramInt & 0x4) != 0) && ((paramInt & 0x800) != 0))
      this.leftMargin = (this.topMargin = this.rightMargin = this.bottomMargin = 2);
    this.alignment = (paramInt & 0x1024000);
    if (this.alignment == 0)
      this.alignment = 16384;
    this.clipboard = new Clipboard(localDisplay);
    installDefaultContent();
    this.renderer = new StyledTextRenderer(getDisplay(), this);
    this.renderer.setContent(this.content);
    this.renderer.setFont(getFont(), this.tabLength);
    this.ime = new IME(this, 0);
    this.defaultCaret = new Caret(this, 0);
    if ((paramInt & 0x40) != 0)
      setWordWrap(true);
    if (isBidiCaret())
    {
      createCaretBitmaps();
      Runnable local1 = new Runnable()
      {
        public void run()
        {
          int i = BidiUtil.getKeyboardLanguage() == 1 ? 131072 : 16384;
          if (i == StyledText.this.caretDirection)
            return;
          if (StyledText.this.getCaret() != StyledText.this.defaultCaret)
            return;
          Point localPoint = StyledText.this.getPointAtOffset(StyledText.this.caretOffset);
          StyledText.this.setCaretLocation(localPoint, i);
        }
      };
      BidiUtil.addLanguageListener(this, local1);
    }
    setCaret(this.defaultCaret);
    calculateScrollBars();
    createKeyBindings();
    super.setCursor(localDisplay.getSystemCursor(19));
    installListeners();
    initializeAccessible();
    setData("DEFAULT_DROP_TARGET_EFFECT", new StyledTextDropTargetEffect(this));
  }

  public void addExtendedModifyListener(ExtendedModifyListener paramExtendedModifyListener)
  {
    checkWidget();
    if (paramExtendedModifyListener == null)
      SWT.error(4);
    StyledTextListener localStyledTextListener = new StyledTextListener(paramExtendedModifyListener);
    addListener(3000, localStyledTextListener);
  }

  public void addBidiSegmentListener(BidiSegmentListener paramBidiSegmentListener)
  {
    checkWidget();
    if (paramBidiSegmentListener == null)
      SWT.error(4);
    addListener(3007, new StyledTextListener(paramBidiSegmentListener));
  }

  public void addCaretListener(CaretListener paramCaretListener)
  {
    checkWidget();
    if (paramCaretListener == null)
      SWT.error(4);
    addListener(3011, new StyledTextListener(paramCaretListener));
  }

  public void addLineBackgroundListener(LineBackgroundListener paramLineBackgroundListener)
  {
    checkWidget();
    if (paramLineBackgroundListener == null)
      SWT.error(4);
    if (!isListening(3001))
      this.renderer.clearLineBackground(0, this.content.getLineCount());
    addListener(3001, new StyledTextListener(paramLineBackgroundListener));
  }

  public void addLineStyleListener(LineStyleListener paramLineStyleListener)
  {
    checkWidget();
    if (paramLineStyleListener == null)
      SWT.error(4);
    if (!isListening(3002))
    {
      setStyleRanges(0, 0, null, null, true);
      this.renderer.clearLineStyle(0, this.content.getLineCount());
    }
    addListener(3002, new StyledTextListener(paramLineStyleListener));
    setCaretLocation();
  }

  public void addModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      SWT.error(4);
    addListener(24, new TypedListener(paramModifyListener));
  }

  public void addPaintObjectListener(PaintObjectListener paramPaintObjectListener)
  {
    checkWidget();
    if (paramPaintObjectListener == null)
      SWT.error(4);
    addListener(3008, new StyledTextListener(paramPaintObjectListener));
  }

  public void addSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    addListener(13, new TypedListener(paramSelectionListener));
  }

  public void addVerifyKeyListener(VerifyKeyListener paramVerifyKeyListener)
  {
    checkWidget();
    if (paramVerifyKeyListener == null)
      SWT.error(4);
    addListener(3005, new StyledTextListener(paramVerifyKeyListener));
  }

  public void addVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      SWT.error(4);
    addListener(25, new TypedListener(paramVerifyListener));
  }

  public void addWordMovementListener(MovementListener paramMovementListener)
  {
    checkWidget();
    if (this.listener == null)
      SWT.error(4);
    addListener(3009, new StyledTextListener(paramMovementListener));
    addListener(3010, new StyledTextListener(paramMovementListener));
  }

  public void append(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    int i = Math.max(getCharCount(), 0);
    replaceTextRange(i, 0, paramString);
  }

  void calculateScrollBars()
  {
    ScrollBar localScrollBar1 = getHorizontalBar();
    ScrollBar localScrollBar2 = getVerticalBar();
    setScrollBars(true);
    if (localScrollBar2 != null)
      localScrollBar2.setIncrement(getVerticalIncrement());
    if (localScrollBar1 != null)
      localScrollBar1.setIncrement(getHorizontalIncrement());
  }

  void calculateTopIndex(int paramInt)
  {
    int i = this.topIndex;
    int j = this.topIndexY;
    int k;
    int m;
    if (isFixedLineHeight())
    {
      k = getVerticalIncrement();
      if (k == 0)
        return;
      this.topIndex = Compatibility.ceil(getVerticalScrollOffset(), k);
      if (this.topIndex > 0)
        if (this.clientAreaHeight > 0)
        {
          m = getVerticalScrollOffset() + this.clientAreaHeight;
          int n = this.topIndex * k;
          int i1 = m - n;
          if (i1 < k)
            this.topIndex -= 1;
        }
        else if (this.topIndex >= this.content.getLineCount())
        {
          this.topIndex = (this.content.getLineCount() - 1);
        }
    }
    else if (paramInt >= 0)
    {
      paramInt -= this.topIndexY;
      k = this.topIndex;
      m = this.content.getLineCount();
      while (k < m)
      {
        if (paramInt <= 0)
          break;
        paramInt -= this.renderer.getLineHeight(k++);
      }
      if ((k < m) && (-paramInt + this.renderer.getLineHeight(k) <= this.clientAreaHeight - this.topMargin - this.bottomMargin))
      {
        this.topIndex = k;
        this.topIndexY = (-paramInt);
      }
      else
      {
        this.topIndex = (k - 1);
        this.topIndexY = (-this.renderer.getLineHeight(this.topIndex) - paramInt);
      }
    }
    else
    {
      paramInt -= this.topIndexY;
      for (k = this.topIndex; k > 0; k--)
      {
        m = this.renderer.getLineHeight(k - 1);
        if (paramInt + m > 0)
          break;
        paramInt += m;
      }
      if ((k == 0) || (-paramInt + this.renderer.getLineHeight(k) <= this.clientAreaHeight - this.topMargin - this.bottomMargin))
      {
        this.topIndex = k;
        this.topIndexY = (-paramInt);
      }
      else
      {
        this.topIndex = (k - 1);
        this.topIndexY = (-this.renderer.getLineHeight(this.topIndex) - paramInt);
      }
    }
    if ((this.topIndex != i) || (j != this.topIndexY))
    {
      this.renderer.calculateClientArea();
      setScrollBars(false);
    }
  }

  static int checkStyle(int paramInt)
  {
    if ((paramInt & 0x4) != 0)
    {
      paramInt &= -835;
    }
    else
    {
      paramInt |= 2;
      if ((paramInt & 0x40) != 0)
        paramInt &= -257;
    }
    paramInt |= 538181632;
    return paramInt & 0xFEFFFFFF;
  }

  void claimBottomFreeSpace()
  {
    if (this.ime.getCompositionOffset() != -1)
      return;
    int i;
    if (isFixedLineHeight())
    {
      i = Math.max(0, this.renderer.getHeight() - this.clientAreaHeight);
      if (i < getVerticalScrollOffset())
        scrollVertical(i - getVerticalScrollOffset(), true);
    }
    else
    {
      i = getPartialBottomIndex();
      int j = getLinePixel(i + 1);
      if (this.clientAreaHeight > j)
        scrollVertical(-getAvailableHeightAbove(this.clientAreaHeight - j), true);
    }
  }

  void claimRightFreeSpace()
  {
    int i = Math.max(0, this.renderer.getWidth() - this.clientAreaWidth);
    if (i < this.horizontalScrollOffset)
      scrollHorizontal(i - this.horizontalScrollOffset, true);
  }

  void clearBlockSelection(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
      resetSelection();
    this.blockXAnchor = (this.blockYAnchor = -1);
    this.blockXLocation = (this.blockYLocation = -1);
    this.caretDirection = 0;
    updateCaretVisibility();
    super.redraw();
    if (paramBoolean2)
      sendSelectionEvent();
  }

  void clearSelection(boolean paramBoolean)
  {
    int i = this.selection.x;
    int j = this.selection.y;
    resetSelection();
    if (j - i > 0)
    {
      int k = this.content.getCharCount();
      int m = Math.min(i, k);
      int n = Math.min(j, k);
      if (n - m > 0)
        internalRedrawRange(m, n - m);
      if (paramBoolean)
        sendSelectionEvent();
    }
  }

  public Point computeSize(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = (getStyle() & 0x4) != 0 ? 1 : this.content.getLineCount();
    int j = 0;
    int k = 0;
    if ((paramInt1 == -1) || (paramInt2 == -1))
    {
      Display localDisplay = getDisplay();
      n = localDisplay.getClientArea().height;
      for (int i1 = 0; i1 < i; i1++)
      {
        TextLayout localTextLayout = this.renderer.getTextLayout(i1);
        int i2 = localTextLayout.getWidth();
        if (this.wordWrap)
          localTextLayout.setWidth(paramInt1 == 0 ? 1 : paramInt1);
        Rectangle localRectangle2 = localTextLayout.getBounds();
        k += localRectangle2.height;
        j = Math.max(j, localRectangle2.width);
        localTextLayout.setWidth(i2);
        this.renderer.disposeTextLayout(localTextLayout);
        if ((isFixedLineHeight()) && (k > n))
          break;
      }
      if (isFixedLineHeight())
        k = i * this.renderer.getLineHeight();
    }
    if (j == 0)
      j = 64;
    if (k == 0)
      k = 64;
    if (paramInt1 != -1)
      j = paramInt1;
    if (paramInt2 != -1)
      k = paramInt2;
    int m = this.leftMargin + this.rightMargin + getCaretWidth();
    int n = this.topMargin + this.bottomMargin;
    Rectangle localRectangle1 = computeTrim(0, 0, j + m, k + n);
    return new Point(localRectangle1.width, localRectangle1.height);
  }

  public void copy()
  {
    checkWidget();
    copySelection(1);
  }

  public void copy(int paramInt)
  {
    checkWidget();
    copySelection(paramInt);
  }

  boolean copySelection(int paramInt)
  {
    if ((paramInt != 1) && (paramInt != 2))
      return false;
    try
    {
      if ((this.blockSelection) && (this.blockXLocation != -1))
      {
        String str = getBlockSelectionText(PlatformLineDelimiter);
        if (str.length() > 0)
        {
          TextTransfer localTextTransfer = TextTransfer.getInstance();
          Object[] arrayOfObject = { str };
          Transfer[] arrayOfTransfer = { localTextTransfer };
          this.clipboard.setContents(arrayOfObject, arrayOfTransfer, paramInt);
          return true;
        }
      }
      else
      {
        int i = this.selection.y - this.selection.x;
        if (i > 0)
        {
          setClipboardContent(this.selection.x, i, paramInt);
          return true;
        }
      }
    }
    catch (SWTError localSWTError)
    {
      if (localSWTError.code != 2002)
        throw localSWTError;
    }
    return false;
  }

  public int getAlignment()
  {
    checkWidget();
    return this.alignment;
  }

  int getAvailableHeightAbove(int paramInt)
  {
    int i = this.verticalScrollOffset;
    if (i == -1)
    {
      int j = this.topIndex - 1;
      i = -this.topIndexY;
      if (this.topIndexY > 0)
        i += this.renderer.getLineHeight(j--);
      while ((paramInt > i) && (j >= 0))
        i += this.renderer.getLineHeight(j--);
    }
    return Math.min(paramInt, i);
  }

  int getAvailableHeightBellow(int paramInt)
  {
    int i = getPartialBottomIndex();
    int j = getLinePixel(i);
    int k = this.renderer.getLineHeight(i);
    int m = 0;
    int n = this.clientAreaHeight - this.topMargin - this.bottomMargin;
    if (j + k > n)
      m = k - (n - j);
    int i1 = i + 1;
    int i2 = this.content.getLineCount();
    while ((paramInt > m) && (i1 < i2))
      m += this.renderer.getLineHeight(i1++);
    return Math.min(paramInt, m);
  }

  public Color getMarginColor()
  {
    checkWidget();
    return this.marginColor != null ? this.marginColor : getBackground();
  }

  String getModelDelimitedText(String paramString)
  {
    int i = paramString.length();
    if (i == 0)
      return paramString;
    int j = 0;
    int k = 0;
    int m = 0;
    StringBuffer localStringBuffer = new StringBuffer(i);
    String str = getLineDelimiter();
    while (m < i)
    {
      if (j != -1)
        j = paramString.indexOf('\r', m);
      if (k != -1)
        k = paramString.indexOf('\n', m);
      if ((k == -1) && (j == -1))
        break;
      if (((j < k) && (j != -1)) || (k == -1))
      {
        localStringBuffer.append(paramString.substring(m, j));
        if (k == j + 1)
          m = k + 1;
        else
          m = j + 1;
      }
      else
      {
        localStringBuffer.append(paramString.substring(m, k));
        m = k + 1;
      }
      if (isSingleLine())
        break;
      localStringBuffer.append(str);
    }
    if ((m < i) && ((!isSingleLine()) || (localStringBuffer.length() == 0)))
      localStringBuffer.append(paramString.substring(m));
    return localStringBuffer.toString();
  }

  boolean checkDragDetect(Event paramEvent)
  {
    if (!isListening(29))
      return false;
    if (IS_MOTIF)
    {
      if (paramEvent.button != 2)
        return false;
    }
    else if (paramEvent.button != 1)
      return false;
    if ((this.blockSelection) && (this.blockXLocation != -1))
    {
      Rectangle localRectangle = getBlockSelectionRectangle();
      if (localRectangle.contains(paramEvent.x, paramEvent.y))
        return dragDetect(paramEvent);
    }
    else
    {
      if (this.selection.x == this.selection.y)
        return false;
      int i = getOffsetAtPoint(paramEvent.x, paramEvent.y, null, true);
      if ((this.selection.x <= i) && (i < this.selection.y))
        return dragDetect(paramEvent);
    }
    return false;
  }

  void createKeyBindings()
  {
    int i = isMirrored() ? 16777219 : 16777220;
    int j = isMirrored() ? 16777220 : 16777219;
    setKeyBinding(16777217, 16777217);
    setKeyBinding(16777218, 16777218);
    if (IS_MAC)
    {
      setKeyBinding(j | SWT.MOD1, 16777223);
      setKeyBinding(i | SWT.MOD1, 16777224);
      setKeyBinding(16777223, 17039367);
      setKeyBinding(16777224, 17039368);
      setKeyBinding(0x1000001 | SWT.MOD1, 17039367);
      setKeyBinding(0x1000002 | SWT.MOD1, 17039368);
      setKeyBinding(i | SWT.MOD3, 17039364);
      setKeyBinding(j | SWT.MOD3, 17039363);
    }
    else
    {
      setKeyBinding(16777223, 16777223);
      setKeyBinding(16777224, 16777224);
      setKeyBinding(0x1000007 | SWT.MOD1, 17039367);
      setKeyBinding(0x1000008 | SWT.MOD1, 17039368);
      setKeyBinding(i | SWT.MOD1, 17039364);
      setKeyBinding(j | SWT.MOD1, 17039363);
    }
    setKeyBinding(16777221, 16777221);
    setKeyBinding(16777222, 16777222);
    setKeyBinding(0x1000005 | SWT.MOD1, 17039365);
    setKeyBinding(0x1000006 | SWT.MOD1, 17039366);
    setKeyBinding(i, 16777220);
    setKeyBinding(j, 16777219);
    setKeyBinding(0x1000001 | SWT.MOD2, 16908289);
    setKeyBinding(0x1000002 | SWT.MOD2, 16908290);
    if (IS_MAC)
    {
      setKeyBinding(j | SWT.MOD1 | SWT.MOD2, 16908295);
      setKeyBinding(i | SWT.MOD1 | SWT.MOD2, 16908296);
      setKeyBinding(0x1000007 | SWT.MOD2, 17170439);
      setKeyBinding(0x1000008 | SWT.MOD2, 17170440);
      setKeyBinding(0x1000001 | SWT.MOD1 | SWT.MOD2, 17170439);
      setKeyBinding(0x1000002 | SWT.MOD1 | SWT.MOD2, 17170440);
      setKeyBinding(i | SWT.MOD2 | SWT.MOD3, 17170436);
      setKeyBinding(j | SWT.MOD2 | SWT.MOD3, 17170435);
    }
    else
    {
      setKeyBinding(0x1000007 | SWT.MOD2, 16908295);
      setKeyBinding(0x1000008 | SWT.MOD2, 16908296);
      setKeyBinding(0x1000007 | SWT.MOD1 | SWT.MOD2, 17170439);
      setKeyBinding(0x1000008 | SWT.MOD1 | SWT.MOD2, 17170440);
      setKeyBinding(i | SWT.MOD1 | SWT.MOD2, 17170436);
      setKeyBinding(j | SWT.MOD1 | SWT.MOD2, 17170435);
    }
    setKeyBinding(0x1000005 | SWT.MOD2, 16908293);
    setKeyBinding(0x1000006 | SWT.MOD2, 16908294);
    setKeyBinding(0x1000005 | SWT.MOD1 | SWT.MOD2, 17170437);
    setKeyBinding(0x1000006 | SWT.MOD1 | SWT.MOD2, 17170438);
    setKeyBinding(i | SWT.MOD2, 16908292);
    setKeyBinding(j | SWT.MOD2, 16908291);
    setKeyBinding(0x58 | SWT.MOD1, 131199);
    setKeyBinding(0x43 | SWT.MOD1, 17039369);
    setKeyBinding(0x56 | SWT.MOD1, 16908297);
    if (IS_MAC)
    {
      setKeyBinding(0x7F | SWT.MOD2, 127);
      setKeyBinding(0x8 | SWT.MOD3, 262152);
      setKeyBinding(0x7F | SWT.MOD3, 262271);
    }
    else
    {
      setKeyBinding(0x7F | SWT.MOD2, 131199);
      setKeyBinding(0x1000009 | SWT.MOD1, 17039369);
      setKeyBinding(0x1000009 | SWT.MOD2, 16908297);
    }
    setKeyBinding(0x8 | SWT.MOD2, 8);
    setKeyBinding(8, 8);
    setKeyBinding(127, 127);
    setKeyBinding(0x8 | SWT.MOD1, 262152);
    setKeyBinding(0x7F | SWT.MOD1, 262271);
    setKeyBinding(16777225, 16777225);
  }

  void createCaretBitmaps()
  {
    int i = 3;
    Display localDisplay = getDisplay();
    if (this.leftCaretBitmap != null)
    {
      if ((this.defaultCaret != null) && (this.leftCaretBitmap.equals(this.defaultCaret.getImage())))
        this.defaultCaret.setImage(null);
      this.leftCaretBitmap.dispose();
    }
    int j = this.renderer.getLineHeight();
    this.leftCaretBitmap = new Image(localDisplay, i, j);
    GC localGC = new GC(this.leftCaretBitmap);
    localGC.setBackground(localDisplay.getSystemColor(2));
    localGC.fillRectangle(0, 0, i, j);
    localGC.setForeground(localDisplay.getSystemColor(1));
    localGC.drawLine(0, 0, 0, j);
    localGC.drawLine(0, 0, i - 1, 0);
    localGC.drawLine(0, 1, 1, 1);
    localGC.dispose();
    if (this.rightCaretBitmap != null)
    {
      if ((this.defaultCaret != null) && (this.rightCaretBitmap.equals(this.defaultCaret.getImage())))
        this.defaultCaret.setImage(null);
      this.rightCaretBitmap.dispose();
    }
    this.rightCaretBitmap = new Image(localDisplay, i, j);
    localGC = new GC(this.rightCaretBitmap);
    localGC.setBackground(localDisplay.getSystemColor(2));
    localGC.fillRectangle(0, 0, i, j);
    localGC.setForeground(localDisplay.getSystemColor(1));
    localGC.drawLine(i - 1, 0, i - 1, j);
    localGC.drawLine(0, 0, i - 1, 0);
    localGC.drawLine(i - 1, 1, 1, 1);
    localGC.dispose();
  }

  public void cut()
  {
    checkWidget();
    if (copySelection(1))
      if ((this.blockSelection) && (this.blockXLocation != -1))
        insertBlockSelectionText('\000', 0);
      else
        doDelete();
  }

  void doAutoScroll(Event paramEvent)
  {
    int i = getCaretLine();
    if ((paramEvent.y > this.clientAreaHeight - this.bottomMargin) && (i != this.content.getLineCount() - 1))
      doAutoScroll(1024, paramEvent.y - (this.clientAreaHeight - this.bottomMargin));
    else if ((paramEvent.y < this.topMargin) && (i != 0))
      doAutoScroll(128, this.topMargin - paramEvent.y);
    else if ((paramEvent.x < this.leftMargin) && (!this.wordWrap))
      doAutoScroll(16777219, this.leftMargin - paramEvent.x);
    else if ((paramEvent.x > this.clientAreaWidth - this.rightMargin) && (!this.wordWrap))
      doAutoScroll(16777220, paramEvent.x - (this.clientAreaWidth - this.rightMargin));
    else
      endAutoScroll();
  }

  void doAutoScroll(int paramInt1, int paramInt2)
  {
    this.autoScrollDistance = paramInt2;
    if (this.autoScrollDirection == paramInt1)
      return;
    Object localObject = null;
    Display localDisplay = getDisplay();
    if (paramInt1 == 128)
    {
      localObject = new Runnable()
      {
        private final Display val$display;

        public void run()
        {
          if (StyledText.this.autoScrollDirection == 128)
          {
            if (StyledText.this.blockSelection)
            {
              int i = StyledText.this.getVerticalScrollOffset();
              int j = StyledText.this.blockYLocation - i;
              int k = Math.max(-StyledText.this.autoScrollDistance, -i);
              if (k != 0)
              {
                StyledText.this.setBlockSelectionLocation(StyledText.this.blockXLocation - StyledText.this.horizontalScrollOffset, j + k, true);
                StyledText.this.scrollVertical(k, true);
              }
            }
            else
            {
              StyledText.this.doSelectionPageUp(StyledText.this.autoScrollDistance);
            }
            this.val$display.timerExec(50, this);
          }
        }
      };
      this.autoScrollDirection = paramInt1;
      localDisplay.timerExec(50, (Runnable)localObject);
    }
    else if (paramInt1 == 1024)
    {
      localObject = new Runnable()
      {
        private final Display val$display;

        public void run()
        {
          if (StyledText.this.autoScrollDirection == 1024)
          {
            if (StyledText.this.blockSelection)
            {
              int i = StyledText.this.getVerticalScrollOffset();
              int j = StyledText.this.blockYLocation - i;
              int k = StyledText.this.renderer.getHeight() - i - StyledText.this.clientAreaHeight;
              int m = Math.min(StyledText.this.autoScrollDistance, Math.max(0, k));
              if (m != 0)
              {
                StyledText.this.setBlockSelectionLocation(StyledText.this.blockXLocation - StyledText.this.horizontalScrollOffset, j + m, true);
                StyledText.this.scrollVertical(m, true);
              }
            }
            else
            {
              StyledText.this.doSelectionPageDown(StyledText.this.autoScrollDistance);
            }
            this.val$display.timerExec(50, this);
          }
        }
      };
      this.autoScrollDirection = paramInt1;
      localDisplay.timerExec(50, (Runnable)localObject);
    }
    else if (paramInt1 == 16777220)
    {
      localObject = new Runnable()
      {
        private final Display val$display;

        public void run()
        {
          if (StyledText.this.autoScrollDirection == 16777220)
          {
            if (StyledText.this.blockSelection)
            {
              int i = StyledText.this.blockXLocation - StyledText.this.horizontalScrollOffset;
              int j = StyledText.this.renderer.getWidth() - StyledText.this.horizontalScrollOffset - StyledText.this.clientAreaWidth;
              int k = Math.min(StyledText.this.autoScrollDistance, Math.max(0, j));
              if (k != 0)
              {
                StyledText.this.setBlockSelectionLocation(i + k, StyledText.this.blockYLocation - StyledText.this.getVerticalScrollOffset(), true);
                StyledText.this.scrollHorizontal(k, true);
              }
            }
            else
            {
              StyledText.this.doVisualNext();
              StyledText.this.setMouseWordSelectionAnchor();
              StyledText.this.doMouseSelection();
            }
            this.val$display.timerExec(10, this);
          }
        }
      };
      this.autoScrollDirection = paramInt1;
      localDisplay.timerExec(10, (Runnable)localObject);
    }
    else if (paramInt1 == 16777219)
    {
      localObject = new Runnable()
      {
        private final Display val$display;

        public void run()
        {
          if (StyledText.this.autoScrollDirection == 16777219)
          {
            if (StyledText.this.blockSelection)
            {
              int i = StyledText.this.blockXLocation - StyledText.this.horizontalScrollOffset;
              int j = Math.max(-StyledText.this.autoScrollDistance, -StyledText.this.horizontalScrollOffset);
              if (j != 0)
              {
                StyledText.this.setBlockSelectionLocation(i + j, StyledText.this.blockYLocation - StyledText.this.getVerticalScrollOffset(), true);
                StyledText.this.scrollHorizontal(j, true);
              }
            }
            else
            {
              StyledText.this.doVisualPrevious();
              StyledText.this.setMouseWordSelectionAnchor();
              StyledText.this.doMouseSelection();
            }
            this.val$display.timerExec(10, this);
          }
        }
      };
      this.autoScrollDirection = paramInt1;
      localDisplay.timerExec(10, (Runnable)localObject);
    }
  }

  void doBackspace()
  {
    Event localEvent = new Event();
    localEvent.text = "";
    if (this.selection.x != this.selection.y)
    {
      localEvent.start = this.selection.x;
      localEvent.end = this.selection.y;
      sendKeyEvent(localEvent);
    }
    else if (this.caretOffset > 0)
    {
      int i = this.content.getLineAtOffset(this.caretOffset);
      int j = this.content.getOffsetAtLine(i);
      if (this.caretOffset == j)
      {
        j = this.content.getOffsetAtLine(i - 1);
        localEvent.start = (j + this.content.getLine(i - 1).length());
        localEvent.end = this.caretOffset;
      }
      else
      {
        int k = 0;
        String str = this.content.getLine(i);
        int m = str.charAt(this.caretOffset - j - 1);
        if ((56320 <= m) && (m <= 57343) && (this.caretOffset - j - 2 >= 0))
        {
          m = str.charAt(this.caretOffset - j - 2);
          k = (55296 <= m) && (m <= 56319) ? 1 : 0;
        }
        TextLayout localTextLayout = this.renderer.getTextLayout(i);
        int n = localTextLayout.getPreviousOffset(this.caretOffset - j, k != 0 ? 2 : 1);
        this.renderer.disposeTextLayout(localTextLayout);
        localEvent.start = (n + j);
        localEvent.end = this.caretOffset;
      }
      sendKeyEvent(localEvent);
    }
  }

  void doBlockColumn(boolean paramBoolean)
  {
    if (this.blockXLocation == -1)
      setBlockSelectionOffset(this.caretOffset, false);
    int i = this.blockXLocation - this.horizontalScrollOffset;
    int j = this.blockYLocation - getVerticalScrollOffset();
    int[] arrayOfInt = new int[1];
    int k = getOffsetAtPoint(i, j, arrayOfInt, true);
    int m;
    int n;
    if (k != -1)
    {
      k += arrayOfInt[0];
      m = this.content.getLineAtOffset(k);
      if (paramBoolean)
        n = getClusterNext(k, m);
      else
        n = getClusterPrevious(k, m);
      k = n != k ? n : -1;
    }
    if (k != -1)
    {
      setBlockSelectionOffset(k, true);
      showCaret();
    }
    else
    {
      m = paramBoolean ? this.renderer.averageCharWidth : -this.renderer.averageCharWidth;
      n = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
      i = Math.max(0, Math.min(this.blockXLocation + m, n)) - this.horizontalScrollOffset;
      setBlockSelectionLocation(i, j, true);
      Rectangle localRectangle = new Rectangle(i, j, 0, 0);
      showLocation(localRectangle, true);
    }
  }

  void doBlockWord(boolean paramBoolean)
  {
    if (this.blockXLocation == -1)
      setBlockSelectionOffset(this.caretOffset, false);
    int i = this.blockXLocation - this.horizontalScrollOffset;
    int j = this.blockYLocation - getVerticalScrollOffset();
    int[] arrayOfInt = new int[1];
    int k = getOffsetAtPoint(i, j, arrayOfInt, true);
    int m;
    int n;
    Object localObject;
    if (k != -1)
    {
      k += arrayOfInt[0];
      m = this.content.getLineAtOffset(k);
      n = this.content.getOffsetAtLine(m);
      localObject = this.content.getLine(m);
      int i1 = ((String)localObject).length();
      int i2 = k;
      if (paramBoolean)
      {
        if (k < n + i1)
          i2 = getWordNext(k, 4);
      }
      else if (k > n)
        i2 = getWordPrevious(k, 4);
      k = i2 != k ? i2 : -1;
    }
    if (k != -1)
    {
      setBlockSelectionOffset(k, true);
      showCaret();
    }
    else
    {
      m = (paramBoolean ? this.renderer.averageCharWidth : -this.renderer.averageCharWidth) * 6;
      n = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
      i = Math.max(0, Math.min(this.blockXLocation + m, n)) - this.horizontalScrollOffset;
      setBlockSelectionLocation(i, j, true);
      localObject = new Rectangle(i, j, 0, 0);
      showLocation((Rectangle)localObject, true);
    }
  }

  void doBlockLineVertical(boolean paramBoolean)
  {
    if (this.blockXLocation == -1)
      setBlockSelectionOffset(this.caretOffset, false);
    int i = this.blockYLocation - getVerticalScrollOffset();
    int j = getLineIndex(i);
    if (paramBoolean)
    {
      if (j > 0)
      {
        i = getLinePixel(j - 1);
        setBlockSelectionLocation(this.blockXLocation - this.horizontalScrollOffset, i, true);
        if (i < this.topMargin)
          scrollVertical(i - this.topMargin, true);
      }
    }
    else
    {
      int k = this.content.getLineCount();
      if (j + 1 < k)
      {
        i = getLinePixel(j + 2) - 1;
        setBlockSelectionLocation(this.blockXLocation - this.horizontalScrollOffset, i, true);
        int m = this.clientAreaHeight - this.bottomMargin;
        if (i > m)
          scrollVertical(i - m, true);
      }
    }
  }

  void doBlockLineHorizontal(boolean paramBoolean)
  {
    if (this.blockXLocation == -1)
      setBlockSelectionOffset(this.caretOffset, false);
    int i = this.blockXLocation - this.horizontalScrollOffset;
    int j = this.blockYLocation - getVerticalScrollOffset();
    int k = getLineIndex(j);
    int m = this.content.getOffsetAtLine(k);
    String str = this.content.getLine(k);
    int n = str.length();
    int[] arrayOfInt = new int[1];
    int i1 = getOffsetAtPoint(i, j, arrayOfInt, true);
    int i2;
    if (i1 != -1)
    {
      i1 += arrayOfInt[0];
      i2 = i1;
      if (paramBoolean)
      {
        if (i1 < m + n)
          i2 = m + n;
      }
      else if (i1 > m)
        i2 = m;
      i1 = i2 != i1 ? i2 : -1;
    }
    else if (!paramBoolean)
    {
      i1 = m + n;
    }
    if (i1 != -1)
    {
      setBlockSelectionOffset(i1, true);
      showCaret();
    }
    else
    {
      i2 = Math.max(this.clientAreaWidth - this.rightMargin - this.leftMargin, this.renderer.getWidth());
      i = (paramBoolean ? i2 : 0) - this.horizontalScrollOffset;
      setBlockSelectionLocation(i, j, true);
      Rectangle localRectangle = new Rectangle(i, j, 0, 0);
      showLocation(localRectangle, true);
    }
  }

  void doBlockSelection(boolean paramBoolean)
  {
    if (this.caretOffset > this.selectionAnchor)
    {
      this.selection.x = this.selectionAnchor;
      this.selection.y = this.caretOffset;
    }
    else
    {
      this.selection.x = this.caretOffset;
      this.selection.y = this.selectionAnchor;
    }
    updateCaretVisibility();
    setCaretLocation();
    super.redraw();
    if (paramBoolean)
      sendSelectionEvent();
    sendAccessibleTextCaretMoved();
  }

  void doContent(char paramChar)
  {
    if ((this.blockSelection) && (this.blockXLocation != -1))
    {
      insertBlockSelectionText(paramChar, 0);
      return;
    }
    Event localEvent = new Event();
    localEvent.start = this.selection.x;
    localEvent.end = this.selection.y;
    if ((paramChar == '\r') || (paramChar == '\n'))
    {
      if (!isSingleLine())
        localEvent.text = getLineDelimiter();
    }
    else if ((this.selection.x == this.selection.y) && (this.overwrite) && (paramChar != '\t'))
    {
      int i = this.content.getLineAtOffset(localEvent.end);
      int j = this.content.getOffsetAtLine(i);
      String str = this.content.getLine(i);
      if (localEvent.end < j + str.length())
        localEvent.end += 1;
      localEvent.text = new String(new char[] { paramChar });
    }
    else
    {
      localEvent.text = new String(new char[] { paramChar });
    }
    if (localEvent.text != null)
    {
      if ((this.textLimit > 0) && (this.content.getCharCount() - (localEvent.end - localEvent.start) >= this.textLimit))
        return;
      sendKeyEvent(localEvent);
    }
  }

  void doContentEnd()
  {
    if (isSingleLine())
    {
      doLineEnd();
    }
    else
    {
      int i = this.content.getCharCount();
      if (this.caretOffset < i)
      {
        setCaretOffset(i, -1);
        showCaret();
      }
    }
  }

  void doContentStart()
  {
    if (this.caretOffset > 0)
    {
      setCaretOffset(0, -1);
      showCaret();
    }
  }

  void doCursorPrevious()
  {
    if (this.selection.y - this.selection.x > 0)
    {
      setCaretOffset(this.selection.x, 1);
      showCaret();
    }
    else
    {
      doSelectionCursorPrevious();
    }
  }

  void doCursorNext()
  {
    if (this.selection.y - this.selection.x > 0)
    {
      setCaretOffset(this.selection.y, 0);
      showCaret();
    }
    else
    {
      doSelectionCursorNext();
    }
  }

  void doDelete()
  {
    Event localEvent = new Event();
    localEvent.text = "";
    if (this.selection.x != this.selection.y)
    {
      localEvent.start = this.selection.x;
      localEvent.end = this.selection.y;
      sendKeyEvent(localEvent);
    }
    else if (this.caretOffset < this.content.getCharCount())
    {
      int i = this.content.getLineAtOffset(this.caretOffset);
      int j = this.content.getOffsetAtLine(i);
      int k = this.content.getLine(i).length();
      if (this.caretOffset == j + k)
      {
        localEvent.start = this.caretOffset;
        localEvent.end = this.content.getOffsetAtLine(i + 1);
      }
      else
      {
        localEvent.start = this.caretOffset;
        localEvent.end = getClusterNext(this.caretOffset, i);
      }
      sendKeyEvent(localEvent);
    }
  }

  void doDeleteWordNext()
  {
    if (this.selection.x != this.selection.y)
    {
      doDelete();
    }
    else
    {
      Event localEvent = new Event();
      localEvent.text = "";
      localEvent.start = this.caretOffset;
      localEvent.end = getWordNext(this.caretOffset, 4);
      sendKeyEvent(localEvent);
    }
  }

  void doDeleteWordPrevious()
  {
    if (this.selection.x != this.selection.y)
    {
      doBackspace();
    }
    else
    {
      Event localEvent = new Event();
      localEvent.text = "";
      localEvent.start = getWordPrevious(this.caretOffset, 4);
      localEvent.end = this.caretOffset;
      sendKeyEvent(localEvent);
    }
  }

  void doLineDown(boolean paramBoolean)
  {
    int i = getCaretLine();
    int j = this.content.getLineCount();
    int k = 0;
    int m = 0;
    if (this.wordWrap)
    {
      int n = this.content.getOffsetAtLine(i);
      i2 = this.caretOffset - n;
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      int i4 = getVisualLineIndex(localTextLayout, i2);
      int i5 = localTextLayout.getLineCount();
      if (i4 == i5 - 1)
      {
        m = i == j - 1 ? 1 : 0;
        i++;
      }
      else
      {
        k = localTextLayout.getLineBounds(i4 + 1).y;
      }
      this.renderer.disposeTextLayout(localTextLayout);
    }
    else
    {
      m = i == j - 1 ? 1 : 0;
      i++;
    }
    if (m != 0)
    {
      if (paramBoolean)
        setCaretOffset(this.content.getCharCount(), -1);
    }
    else
    {
      int[] arrayOfInt = new int[1];
      i2 = getOffsetAtPoint(this.columnX, k, i, arrayOfInt);
      setCaretOffset(i2, arrayOfInt[0]);
    }
    int i1 = this.columnX;
    int i2 = this.horizontalScrollOffset;
    if (paramBoolean)
    {
      setMouseWordSelectionAnchor();
      doSelection(16777220);
    }
    showCaret();
    int i3 = i2 - this.horizontalScrollOffset;
    this.columnX = (i1 + i3);
  }

  void doLineEnd()
  {
    int i = getCaretLine();
    int j = this.content.getOffsetAtLine(i);
    int k;
    if (this.wordWrap)
    {
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      int n = this.caretOffset - j;
      int i1 = getVisualLineIndex(localTextLayout, n);
      int[] arrayOfInt = localTextLayout.getLineOffsets();
      k = j + arrayOfInt[(i1 + 1)];
      this.renderer.disposeTextLayout(localTextLayout);
    }
    else
    {
      int m = this.content.getLine(i).length();
      k = j + m;
    }
    if (this.caretOffset < k)
    {
      setCaretOffset(k, 0);
      showCaret();
    }
  }

  void doLineStart()
  {
    int i = getCaretLine();
    int j = this.content.getOffsetAtLine(i);
    if (this.wordWrap)
    {
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      int k = this.caretOffset - j;
      int m = getVisualLineIndex(localTextLayout, k);
      int[] arrayOfInt = localTextLayout.getLineOffsets();
      j += arrayOfInt[m];
      this.renderer.disposeTextLayout(localTextLayout);
    }
    if (this.caretOffset > j)
    {
      setCaretOffset(j, 1);
      showCaret();
    }
  }

  void doLineUp(boolean paramBoolean)
  {
    int i = getCaretLine();
    int j = 0;
    int k = 0;
    if (this.wordWrap)
    {
      int m = this.content.getOffsetAtLine(i);
      i1 = this.caretOffset - m;
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      int i3 = getVisualLineIndex(localTextLayout, i1);
      if (i3 == 0)
      {
        k = i == 0 ? 1 : 0;
        if (k == 0)
        {
          i--;
          j = this.renderer.getLineHeight(i) - 1;
        }
      }
      else
      {
        j = localTextLayout.getLineBounds(i3 - 1).y;
      }
      this.renderer.disposeTextLayout(localTextLayout);
    }
    else
    {
      k = i == 0 ? 1 : 0;
      i--;
    }
    if (k != 0)
    {
      if (paramBoolean)
        setCaretOffset(0, -1);
    }
    else
    {
      int[] arrayOfInt = new int[1];
      i1 = getOffsetAtPoint(this.columnX, j, i, arrayOfInt);
      setCaretOffset(i1, arrayOfInt[0]);
    }
    int n = this.columnX;
    int i1 = this.horizontalScrollOffset;
    if (paramBoolean)
      setMouseWordSelectionAnchor();
    showCaret();
    if (paramBoolean)
      doSelection(16777219);
    int i2 = i1 - this.horizontalScrollOffset;
    this.columnX = (n + i2);
  }

  void doMouseLinkCursor()
  {
    Display localDisplay = getDisplay();
    Point localPoint = localDisplay.getCursorLocation();
    localPoint = localDisplay.map(null, this, localPoint);
    doMouseLinkCursor(localPoint.x, localPoint.y);
  }

  void doMouseLinkCursor(int paramInt1, int paramInt2)
  {
    int i = getOffsetAtPoint(paramInt1, paramInt2, null, true);
    Display localDisplay = getDisplay();
    Cursor localCursor = this.cursor;
    if (this.renderer.hasLink(i))
    {
      localCursor = localDisplay.getSystemCursor(21);
    }
    else if (this.cursor == null)
    {
      int j = this.blockSelection ? 2 : 19;
      localCursor = localDisplay.getSystemCursor(j);
    }
    if (localCursor != getCursor())
      super.setCursor(localCursor);
  }

  void doMouseLocationChange(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = getLineIndex(paramInt2);
    this.updateCaretDirection = true;
    if (this.blockSelection)
    {
      paramInt1 = Math.max(this.leftMargin, Math.min(paramInt1, this.clientAreaWidth - this.rightMargin));
      paramInt2 = Math.max(this.topMargin, Math.min(paramInt2, this.clientAreaHeight - this.bottomMargin));
      if ((this.doubleClickEnabled) && (this.clickCount > 1))
      {
        int j = (this.clickCount & 0x1) == 0 ? 1 : 0;
        if (j != 0)
        {
          Point localPoint1 = getPointAtOffset(this.doubleClickSelection.x);
          int[] arrayOfInt2 = new int[1];
          n = getOffsetAtPoint(paramInt1, paramInt2, arrayOfInt2, true);
          if (n != -1)
          {
            if (paramInt1 > localPoint1.x)
            {
              n = getWordNext(n + arrayOfInt2[0], 8);
              setBlockSelectionOffset(this.doubleClickSelection.x, n, true);
            }
            else
            {
              n = getWordPrevious(n + arrayOfInt2[0], 16);
              setBlockSelectionOffset(this.doubleClickSelection.y, n, true);
            }
          }
          else if (paramInt1 > localPoint1.x)
          {
            setBlockSelectionLocation(localPoint1.x, localPoint1.y, paramInt1, paramInt2, true);
          }
          else
          {
            Point localPoint2 = getPointAtOffset(this.doubleClickSelection.y);
            setBlockSelectionLocation(localPoint2.x, localPoint2.y, paramInt1, paramInt2, true);
          }
        }
        else
        {
          setBlockSelectionLocation(this.blockXLocation, paramInt2, true);
        }
        return;
      }
      if (paramBoolean)
      {
        if (this.blockXLocation == -1)
          setBlockSelectionOffset(this.caretOffset, false);
      }
      else
        clearBlockSelection(true, false);
      arrayOfInt1 = new int[1];
      k = getOffsetAtPoint(paramInt1, paramInt2, arrayOfInt1, true);
      if (k != -1)
      {
        if (paramBoolean)
          setBlockSelectionOffset(k + arrayOfInt1[0], true);
      }
      else
      {
        if ((isFixedLineHeight()) && (this.renderer.fixedPitch))
        {
          m = this.renderer.averageCharWidth;
          paramInt1 = (paramInt1 + m / 2 - this.leftMargin + this.horizontalScrollOffset) / m * m + this.leftMargin - this.horizontalScrollOffset;
        }
        setBlockSelectionLocation(paramInt1, paramInt2, true);
        return;
      }
    }
    if ((i < 0) || ((isSingleLine()) && (i > 0)))
      return;
    int[] arrayOfInt1 = new int[1];
    int k = getOffsetAtPoint(paramInt1, paramInt2, arrayOfInt1);
    int m = arrayOfInt1[0];
    if ((this.doubleClickEnabled) && (this.clickCount > 1))
      k = doMouseWordSelect(paramInt1, k, i);
    int n = this.content.getLineAtOffset(k);
    int i1 = ((paramInt2 < 0) || (paramInt2 >= this.clientAreaHeight)) && (n != 0) && (n != this.content.getLineCount() - 1) ? 0 : 1;
    int i2 = ((paramInt1 < 0) || (paramInt1 >= this.clientAreaWidth)) && (!this.wordWrap) && (n == this.content.getLineAtOffset(this.caretOffset)) ? 0 : 1;
    if ((i1 != 0) && (i2 != 0) && ((k != this.caretOffset) || (m != this.caretAlignment)))
    {
      setCaretOffset(k, m);
      if (paramBoolean)
        doMouseSelection();
      showCaret();
    }
    if (!paramBoolean)
    {
      setCaretOffset(k, m);
      clearSelection(true);
    }
  }

  void doMouseSelection()
  {
    if ((this.caretOffset <= this.selection.x) || ((this.caretOffset > this.selection.x) && (this.caretOffset < this.selection.y) && (this.selectionAnchor == this.selection.x)))
      doSelection(16777219);
    else
      doSelection(16777220);
  }

  int doMouseWordSelect(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt2 < this.selectionAnchor) && (this.selectionAnchor == this.selection.x))
      this.selectionAnchor = this.doubleClickSelection.y;
    else if ((paramInt2 > this.selectionAnchor) && (this.selectionAnchor == this.selection.y))
      this.selectionAnchor = this.doubleClickSelection.x;
    if ((paramInt1 >= 0) && (paramInt1 < this.clientAreaWidth))
    {
      int i = (this.clickCount & 0x1) == 0 ? 1 : 0;
      if (this.caretOffset == this.selection.x)
      {
        if (i != 0)
          paramInt2 = getWordPrevious(paramInt2, 16);
        else
          paramInt2 = this.content.getOffsetAtLine(paramInt3);
      }
      else if (i != 0)
      {
        paramInt2 = getWordNext(paramInt2, 8);
      }
      else
      {
        int j = this.content.getCharCount();
        if (paramInt3 + 1 < this.content.getLineCount())
          j = this.content.getOffsetAtLine(paramInt3 + 1);
        paramInt2 = j;
      }
    }
    return paramInt2;
  }

  void doPageDown(boolean paramBoolean, int paramInt)
  {
    if (isSingleLine())
      return;
    int i = this.columnX;
    int j = this.horizontalScrollOffset;
    int m;
    int n;
    int i1;
    int i2;
    Object localObject;
    int i3;
    int i4;
    if (isFixedLineHeight())
    {
      k = this.content.getLineCount();
      m = getCaretLine();
      if (m < k - 1)
      {
        n = this.renderer.getLineHeight();
        i1 = (paramInt == -1 ? this.clientAreaHeight : paramInt) / n;
        i2 = Math.min(k - m - 1, i1);
        i2 = Math.max(1, i2);
        localObject = new int[1];
        i3 = getOffsetAtPoint(this.columnX, getLinePixel(m + i2), (int[])localObject);
        setCaretOffset(i3, localObject[0]);
        if (paramBoolean)
          doSelection(16777220);
        i4 = k * getVerticalIncrement();
        int i5 = this.clientAreaHeight;
        int i6 = getVerticalScrollOffset();
        int i7 = i6 + i2 * getVerticalIncrement();
        if (i7 + i5 > i4)
          i7 = i4 - i5;
        if (i7 > i6)
          scrollVertical(i7 - i6, true);
      }
    }
    else
    {
      k = this.content.getLineCount();
      m = getCaretLine();
      Rectangle localRectangle;
      if (paramInt == -1)
      {
        n = getPartialBottomIndex();
        i2 = getLinePixel(n);
        i1 = this.renderer.getLineHeight(n);
        paramInt = i2;
        if (i2 + i1 <= this.clientAreaHeight)
        {
          paramInt += i1;
        }
        else if (this.wordWrap)
        {
          localObject = this.renderer.getTextLayout(n);
          i3 = this.clientAreaHeight - i2;
          for (i4 = 0; i4 < ((TextLayout)localObject).getLineCount(); i4++)
          {
            localRectangle = ((TextLayout)localObject).getLineBounds(i4);
            if (localRectangle.contains(localRectangle.x, i3))
            {
              paramInt += localRectangle.y;
              break;
            }
          }
          this.renderer.disposeTextLayout((TextLayout)localObject);
        }
      }
      else
      {
        n = getLineIndex(paramInt);
        i2 = getLinePixel(n);
        if (this.wordWrap)
        {
          localObject = this.renderer.getTextLayout(n);
          i3 = paramInt - i2;
          for (i4 = 0; i4 < ((TextLayout)localObject).getLineCount(); i4++)
          {
            localRectangle = ((TextLayout)localObject).getLineBounds(i4);
            if (localRectangle.contains(localRectangle.x, i3))
            {
              paramInt = i2 + localRectangle.y + localRectangle.height;
              break;
            }
          }
          this.renderer.disposeTextLayout((TextLayout)localObject);
        }
        else
        {
          paramInt = i2 + this.renderer.getLineHeight(n);
        }
      }
      i2 = paramInt;
      if (this.wordWrap)
      {
        localObject = this.renderer.getTextLayout(m);
        i3 = this.caretOffset - this.content.getOffsetAtLine(m);
        n = getVisualLineIndex((TextLayout)localObject, i3);
        i2 += ((TextLayout)localObject).getLineBounds(n).y;
        this.renderer.disposeTextLayout((TextLayout)localObject);
      }
      n = m;
      for (i1 = this.renderer.getLineHeight(n); (i2 - i1 >= 0) && (n < k - 1); i1 = this.renderer.getLineHeight(++n))
        i2 -= i1;
      localObject = new int[1];
      i3 = getOffsetAtPoint(this.columnX, i2, n, (int[])localObject);
      setCaretOffset(i3, localObject[0]);
      if (paramBoolean)
        doSelection(16777220);
      paramInt = getAvailableHeightBellow(paramInt);
      scrollVertical(paramInt, true);
      if (paramInt == 0)
        setCaretLocation();
    }
    showCaret();
    int k = j - this.horizontalScrollOffset;
    this.columnX = (i + k);
  }

  void doPageEnd()
  {
    if (isSingleLine())
    {
      doLineEnd();
    }
    else
    {
      int j;
      int i;
      if (this.wordWrap)
      {
        j = getPartialBottomIndex();
        TextLayout localTextLayout = this.renderer.getTextLayout(j);
        int k = this.clientAreaHeight - this.bottomMargin - getLinePixel(j);
        for (int m = localTextLayout.getLineCount() - 1; m >= 0; m--)
        {
          Rectangle localRectangle = localTextLayout.getLineBounds(m);
          if (k >= localRectangle.y + localRectangle.height)
            break;
        }
        if ((m == -1) && (j > 0))
          i = this.content.getOffsetAtLine(j - 1) + this.content.getLine(j - 1).length();
        else
          i = this.content.getOffsetAtLine(j) + Math.max(0, localTextLayout.getLineOffsets()[(m + 1)] - 1);
        this.renderer.disposeTextLayout(localTextLayout);
      }
      else
      {
        j = getBottomIndex();
        i = this.content.getOffsetAtLine(j) + this.content.getLine(j).length();
      }
      if (this.caretOffset < i)
      {
        setCaretOffset(i, 1);
        showCaret();
      }
    }
  }

  void doPageStart()
  {
    int i;
    if (this.wordWrap)
    {
      int k;
      int j;
      if (this.topIndexY > 0)
      {
        k = this.topIndex - 1;
        j = this.renderer.getLineHeight(k) - this.topIndexY;
      }
      else
      {
        k = this.topIndex;
        j = -this.topIndexY;
      }
      TextLayout localTextLayout = this.renderer.getTextLayout(k);
      int m = 0;
      int n = localTextLayout.getLineCount();
      while (m < n)
      {
        Rectangle localRectangle = localTextLayout.getLineBounds(m);
        if (j <= localRectangle.y)
          break;
        m++;
      }
      if (m == n)
        i = this.content.getOffsetAtLine(k + 1);
      else
        i = this.content.getOffsetAtLine(k) + localTextLayout.getLineOffsets()[m];
      this.renderer.disposeTextLayout(localTextLayout);
    }
    else
    {
      i = this.content.getOffsetAtLine(this.topIndex);
    }
    if (this.caretOffset > i)
    {
      setCaretOffset(i, 1);
      showCaret();
    }
  }

  void doPageUp(boolean paramBoolean, int paramInt)
  {
    if (isSingleLine())
      return;
    int i = this.horizontalScrollOffset;
    int j = this.columnX;
    int m;
    int n;
    int i1;
    Object localObject;
    int i2;
    if (isFixedLineHeight())
    {
      k = getCaretLine();
      if (k > 0)
      {
        m = this.renderer.getLineHeight();
        n = (paramInt == -1 ? this.clientAreaHeight : paramInt) / m;
        i1 = Math.max(1, Math.min(k, n));
        k -= i1;
        localObject = new int[1];
        i2 = getOffsetAtPoint(this.columnX, getLinePixel(k), (int[])localObject);
        setCaretOffset(i2, localObject[0]);
        if (paramBoolean)
          doSelection(16777219);
        int i3 = getVerticalScrollOffset();
        int i5 = Math.max(0, i3 - i1 * getVerticalIncrement());
        if (i5 < i3)
          scrollVertical(i5 - i3, true);
      }
    }
    else
    {
      k = getCaretLine();
      if (paramInt == -1)
      {
        if (this.topIndexY == 0)
        {
          paramInt = this.clientAreaHeight;
        }
        else
        {
          if (this.topIndex > 0)
          {
            n = this.topIndex - 1;
            m = this.renderer.getLineHeight(n);
            paramInt = this.clientAreaHeight - this.topIndexY;
            i1 = m - this.topIndexY;
          }
          else
          {
            n = this.topIndex;
            m = this.renderer.getLineHeight(n);
            paramInt = this.clientAreaHeight - (m + this.topIndexY);
            i1 = -this.topIndexY;
          }
          if (this.wordWrap)
          {
            localObject = this.renderer.getTextLayout(n);
            for (i2 = 0; i2 < ((TextLayout)localObject).getLineCount(); i2++)
            {
              Rectangle localRectangle1 = ((TextLayout)localObject).getLineBounds(i2);
              if (localRectangle1.contains(localRectangle1.x, i1))
              {
                paramInt += m - (localRectangle1.y + localRectangle1.height);
                break;
              }
            }
            this.renderer.disposeTextLayout((TextLayout)localObject);
          }
        }
      }
      else
      {
        n = getLineIndex(this.clientAreaHeight - paramInt);
        i1 = getLinePixel(n);
        if (this.wordWrap)
        {
          localObject = this.renderer.getTextLayout(n);
          i2 = i1;
          for (int i4 = 0; i4 < ((TextLayout)localObject).getLineCount(); i4++)
          {
            Rectangle localRectangle2 = ((TextLayout)localObject).getLineBounds(i4);
            if (localRectangle2.contains(localRectangle2.x, i2))
            {
              paramInt = this.clientAreaHeight - (i1 + localRectangle2.y);
              break;
            }
          }
          this.renderer.disposeTextLayout((TextLayout)localObject);
        }
        else
        {
          paramInt = this.clientAreaHeight - i1;
        }
      }
      i1 = paramInt;
      if (this.wordWrap)
      {
        localObject = this.renderer.getTextLayout(k);
        i2 = this.caretOffset - this.content.getOffsetAtLine(k);
        n = getVisualLineIndex((TextLayout)localObject, i2);
        i1 += ((TextLayout)localObject).getBounds().height - ((TextLayout)localObject).getLineBounds(n).y;
        this.renderer.disposeTextLayout((TextLayout)localObject);
      }
      n = k;
      for (m = this.renderer.getLineHeight(n); (i1 - m >= 0) && (n > 0); m = this.renderer.getLineHeight(--n))
        i1 -= m;
      m = this.renderer.getLineHeight(n);
      localObject = new int[1];
      i2 = getOffsetAtPoint(this.columnX, m - i1, n, (int[])localObject);
      setCaretOffset(i2, localObject[0]);
      if (paramBoolean)
        doSelection(16777219);
      paramInt = getAvailableHeightAbove(paramInt);
      scrollVertical(-paramInt, true);
      if (paramInt == 0)
        setCaretLocation();
    }
    showCaret();
    int k = i - this.horizontalScrollOffset;
    this.columnX = (j + k);
  }

  void doSelection(int paramInt)
  {
    int i = -1;
    int j = -1;
    if (this.selectionAnchor == -1)
      this.selectionAnchor = this.selection.x;
    if (paramInt == 16777219)
    {
      if (this.caretOffset < this.selection.x)
      {
        j = this.selection.x;
        i = this.selection.x = this.caretOffset;
        if (this.selection.y != this.selectionAnchor)
        {
          j = this.selection.y;
          this.selection.y = this.selectionAnchor;
        }
      }
      else if ((this.selectionAnchor == this.selection.x) && (this.caretOffset < this.selection.y))
      {
        j = this.selection.y;
        i = this.selection.y = this.caretOffset;
      }
    }
    else if (this.caretOffset > this.selection.y)
    {
      i = this.selection.y;
      j = this.selection.y = this.caretOffset;
      if (this.selection.x != this.selectionAnchor)
      {
        i = this.selection.x;
        this.selection.x = this.selectionAnchor;
      }
    }
    else if ((this.selectionAnchor == this.selection.y) && (this.caretOffset > this.selection.x))
    {
      i = this.selection.x;
      j = this.selection.x = this.caretOffset;
    }
    if ((i != -1) && (j != -1))
    {
      internalRedrawRange(i, j - i);
      sendSelectionEvent();
    }
    sendAccessibleTextCaretMoved();
  }

  void doSelectionCursorNext()
  {
    int i = getCaretLine();
    int j = this.content.getOffsetAtLine(i);
    int k = this.caretOffset - j;
    int m;
    int n;
    if (k < this.content.getLine(i).length())
    {
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      k = localTextLayout.getNextOffset(k, 2);
      int i1 = localTextLayout.getLineOffsets()[localTextLayout.getLineIndex(k)];
      this.renderer.disposeTextLayout(localTextLayout);
      m = k + j;
      n = k == i1 ? 1 : 0;
      setCaretOffset(m, n);
      showCaret();
    }
    else if ((i < this.content.getLineCount() - 1) && (!isSingleLine()))
    {
      i++;
      m = this.content.getOffsetAtLine(i);
      n = 0;
      setCaretOffset(m, n);
      showCaret();
    }
  }

  void doSelectionCursorPrevious()
  {
    int i = getCaretLine();
    int j = this.content.getOffsetAtLine(i);
    int k = this.caretOffset - j;
    int m;
    if (k > 0)
    {
      m = getClusterPrevious(this.caretOffset, i);
      setCaretOffset(m, 1);
      showCaret();
    }
    else if (i > 0)
    {
      i--;
      j = this.content.getOffsetAtLine(i);
      m = j + this.content.getLine(i).length();
      setCaretOffset(m, 1);
      showCaret();
    }
  }

  void doSelectionLineDown()
  {
    int i = this.columnX = getPointAtOffset(this.caretOffset).x;
    doLineDown(true);
    this.columnX = i;
  }

  void doSelectionLineUp()
  {
    int i = this.columnX = getPointAtOffset(this.caretOffset).x;
    doLineUp(true);
    this.columnX = i;
  }

  void doSelectionPageDown(int paramInt)
  {
    int i = this.columnX = getPointAtOffset(this.caretOffset).x;
    doPageDown(true, paramInt);
    this.columnX = i;
  }

  void doSelectionPageUp(int paramInt)
  {
    int i = this.columnX = getPointAtOffset(this.caretOffset).x;
    doPageUp(true, paramInt);
    this.columnX = i;
  }

  void doSelectionWordNext()
  {
    int i = getWordNext(this.caretOffset, 4);
    if ((!isSingleLine()) || (this.content.getLineAtOffset(this.caretOffset) == this.content.getLineAtOffset(i)))
    {
      setCaretOffset(i, 1);
      showCaret();
    }
  }

  void doSelectionWordPrevious()
  {
    int i = getWordPrevious(this.caretOffset, 4);
    setCaretOffset(i, 1);
    showCaret();
  }

  void doVisualPrevious()
  {
    int i = getClusterPrevious(this.caretOffset, getCaretLine());
    setCaretOffset(i, -1);
    showCaret();
  }

  void doVisualNext()
  {
    int i = getClusterNext(this.caretOffset, getCaretLine());
    setCaretOffset(i, -1);
    showCaret();
  }

  void doWordNext()
  {
    if (this.selection.y - this.selection.x > 0)
    {
      setCaretOffset(this.selection.y, -1);
      showCaret();
    }
    else
    {
      doSelectionWordNext();
    }
  }

  void doWordPrevious()
  {
    if (this.selection.y - this.selection.x > 0)
    {
      setCaretOffset(this.selection.x, -1);
      showCaret();
    }
    else
    {
      doSelectionWordPrevious();
    }
  }

  void endAutoScroll()
  {
    this.autoScrollDirection = 0;
  }

  public Color getBackground()
  {
    checkWidget();
    if (this.background == null)
      return getDisplay().getSystemColor(25);
    return this.background;
  }

  public int getBaseline()
  {
    checkWidget();
    return this.renderer.getBaseline();
  }

  public int getBaseline(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getCharCount()))
      SWT.error(6);
    if (isFixedLineHeight())
      return this.renderer.getBaseline();
    int i = this.content.getLineAtOffset(paramInt);
    int j = this.content.getOffsetAtLine(i);
    TextLayout localTextLayout = this.renderer.getTextLayout(i);
    int k = localTextLayout.getLineIndex(Math.min(paramInt - j, localTextLayout.getText().length()));
    FontMetrics localFontMetrics = localTextLayout.getLineMetrics(k);
    this.renderer.disposeTextLayout(localTextLayout);
    return localFontMetrics.getAscent() + localFontMetrics.getLeading();
  }

  /** @deprecated */
  public boolean getBidiColoring()
  {
    checkWidget();
    return this.bidiColoring;
  }

  public boolean getBlockSelection()
  {
    checkWidget();
    return this.blockSelection;
  }

  Rectangle getBlockSelectionPosition()
  {
    int i = getLineIndex(this.blockYAnchor - getVerticalScrollOffset());
    int j = getLineIndex(this.blockYLocation - getVerticalScrollOffset());
    if (i > j)
    {
      k = i;
      i = j;
      j = k;
    }
    int k = this.blockXAnchor;
    int m = this.blockXLocation;
    if (k > m)
    {
      k = this.blockXLocation;
      m = this.blockXAnchor;
    }
    return new Rectangle(k - this.horizontalScrollOffset, i, m - this.horizontalScrollOffset, j);
  }

  public Rectangle getBlockSelectionBounds()
  {
    Rectangle localRectangle;
    if ((this.blockSelection) && (this.blockXLocation != -1))
    {
      localRectangle = getBlockSelectionRectangle();
    }
    else
    {
      Point localPoint1 = getPointAtOffset(this.selection.x);
      Point localPoint2 = getPointAtOffset(this.selection.y);
      int i = getLineHeight(this.selection.y);
      localRectangle = new Rectangle(localPoint1.x, localPoint1.y, localPoint2.x - localPoint1.x, localPoint2.y + i - localPoint1.y);
      if (this.selection.x == this.selection.y)
        localRectangle.width = getCaretWidth();
    }
    localRectangle.x += this.horizontalScrollOffset;
    localRectangle.y += getVerticalScrollOffset();
    return localRectangle;
  }

  Rectangle getBlockSelectionRectangle()
  {
    Rectangle localRectangle = getBlockSelectionPosition();
    localRectangle.y = getLinePixel(localRectangle.y);
    localRectangle.width -= localRectangle.x;
    localRectangle.height = (getLinePixel(localRectangle.height + 1) - localRectangle.y);
    return localRectangle;
  }

  String getBlockSelectionText(String paramString)
  {
    Rectangle localRectangle = getBlockSelectionPosition();
    int i = localRectangle.y;
    int j = localRectangle.height;
    int k = localRectangle.x;
    int m = localRectangle.width;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int n = i; n <= j; n++)
    {
      int i1 = getOffsetAtPoint(k, 0, n, null);
      int i2 = getOffsetAtPoint(m, 0, n, null);
      if (i1 > i2)
      {
        int i3 = i1;
        i1 = i2;
        i2 = i3;
      }
      String str = this.content.getTextRange(i1, i2 - i1);
      localStringBuffer.append(str);
      if (n < j)
        localStringBuffer.append(paramString);
    }
    return localStringBuffer.toString();
  }

  int getBottomIndex()
  {
    int j;
    int k;
    int m;
    int i;
    if (isFixedLineHeight())
    {
      j = 1;
      k = this.renderer.getLineHeight();
      if (k != 0)
      {
        m = this.topIndex * k - getVerticalScrollOffset();
        j = (this.clientAreaHeight - m) / k;
      }
      i = Math.min(this.content.getLineCount() - 1, this.topIndex + Math.max(0, j - 1));
    }
    else
    {
      j = this.clientAreaHeight - this.bottomMargin;
      i = getLineIndex(j);
      if (i > 0)
      {
        k = getLinePixel(i);
        m = this.renderer.getLineHeight(i);
        if ((k + m > j) && (getLinePixel(i - 1) >= this.topMargin))
          i--;
      }
    }
    return i;
  }

  public int getBottomMargin()
  {
    checkWidget();
    return this.bottomMargin;
  }

  Rectangle getBoundsAtOffset(int paramInt)
  {
    int i = this.content.getLineAtOffset(paramInt);
    int j = this.content.getOffsetAtLine(i);
    String str = this.content.getLine(i);
    int k;
    Rectangle localRectangle;
    if (str.length() != 0)
    {
      k = paramInt - j;
      TextLayout localTextLayout = this.renderer.getTextLayout(i);
      localRectangle = localTextLayout.getBounds(k, k);
      this.renderer.disposeTextLayout(localTextLayout);
    }
    else
    {
      localRectangle = new Rectangle(0, 0, 0, this.renderer.getLineHeight());
    }
    if ((paramInt == this.caretOffset) && (!this.wordWrap))
    {
      k = j + str.length();
      if (paramInt == k)
        localRectangle.width += getCaretWidth();
    }
    localRectangle.x += this.leftMargin - this.horizontalScrollOffset;
    localRectangle.y += getLinePixel(i);
    return localRectangle;
  }

  public int getCaretOffset()
  {
    checkWidget();
    return this.caretOffset;
  }

  int getCaretWidth()
  {
    Caret localCaret = getCaret();
    if (localCaret == null)
      return 0;
    return localCaret.getSize().x;
  }

  Object getClipboardContent(int paramInt)
  {
    TextTransfer localTextTransfer = TextTransfer.getInstance();
    return this.clipboard.getContents(localTextTransfer, paramInt);
  }

  int getClusterNext(int paramInt1, int paramInt2)
  {
    int i = this.content.getOffsetAtLine(paramInt2);
    TextLayout localTextLayout = this.renderer.getTextLayout(paramInt2);
    paramInt1 -= i;
    paramInt1 = localTextLayout.getNextOffset(paramInt1, 2);
    paramInt1 += i;
    this.renderer.disposeTextLayout(localTextLayout);
    return paramInt1;
  }

  int getClusterPrevious(int paramInt1, int paramInt2)
  {
    int i = this.content.getOffsetAtLine(paramInt2);
    TextLayout localTextLayout = this.renderer.getTextLayout(paramInt2);
    paramInt1 -= i;
    paramInt1 = localTextLayout.getPreviousOffset(paramInt1, 2);
    paramInt1 += i;
    this.renderer.disposeTextLayout(localTextLayout);
    return paramInt1;
  }

  public StyledTextContent getContent()
  {
    checkWidget();
    return this.content;
  }

  public boolean getDragDetect()
  {
    checkWidget();
    return this.dragDetect;
  }

  public boolean getDoubleClickEnabled()
  {
    checkWidget();
    return this.doubleClickEnabled;
  }

  public boolean getEditable()
  {
    checkWidget();
    return this.editable;
  }

  public Color getForeground()
  {
    checkWidget();
    if (this.foreground == null)
      return getDisplay().getSystemColor(24);
    return this.foreground;
  }

  int getHorizontalIncrement()
  {
    return this.renderer.averageCharWidth;
  }

  public int getHorizontalIndex()
  {
    checkWidget();
    return this.horizontalScrollOffset / getHorizontalIncrement();
  }

  public int getHorizontalPixel()
  {
    checkWidget();
    return this.horizontalScrollOffset;
  }

  public int getIndent()
  {
    checkWidget();
    return this.indent;
  }

  public boolean getJustify()
  {
    checkWidget();
    return this.justify;
  }

  public int getKeyBinding(int paramInt)
  {
    checkWidget();
    Integer localInteger = (Integer)this.keyActionMap.get(new Integer(paramInt));
    return localInteger == null ? 0 : localInteger.intValue();
  }

  public int getCharCount()
  {
    checkWidget();
    return this.content.getCharCount();
  }

  public String getLine(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || ((paramInt > 0) && (paramInt >= this.content.getLineCount())))
      SWT.error(6);
    return this.content.getLine(paramInt);
  }

  public int getLineAlignment(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return this.renderer.getLineAlignment(paramInt, this.alignment);
  }

  public int getLineAtOffset(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > getCharCount()))
      SWT.error(6);
    return this.content.getLineAtOffset(paramInt);
  }

  public Color getLineBackground(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return isListening(3001) ? null : this.renderer.getLineBackground(paramInt, null);
  }

  public Bullet getLineBullet(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return isListening(3002) ? null : this.renderer.getLineBullet(paramInt, null);
  }

  StyledTextEvent getLineBackgroundData(int paramInt, String paramString)
  {
    return sendLineEvent(3001, paramInt, paramString);
  }

  public int getLineCount()
  {
    checkWidget();
    return this.content.getLineCount();
  }

  int getLineCountWhole()
  {
    if (isFixedLineHeight())
    {
      int i = this.renderer.getLineHeight();
      return i != 0 ? this.clientAreaHeight / i : 1;
    }
    return getBottomIndex() - this.topIndex + 1;
  }

  public String getLineDelimiter()
  {
    checkWidget();
    return this.content.getLineDelimiter();
  }

  public int getLineHeight()
  {
    checkWidget();
    return this.renderer.getLineHeight();
  }

  public int getLineHeight(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getCharCount()))
      SWT.error(6);
    if (isFixedLineHeight())
      return this.renderer.getLineHeight();
    int i = this.content.getLineAtOffset(paramInt);
    int j = this.content.getOffsetAtLine(i);
    TextLayout localTextLayout = this.renderer.getTextLayout(i);
    int k = localTextLayout.getLineIndex(Math.min(paramInt - j, localTextLayout.getText().length()));
    int m = localTextLayout.getLineBounds(k).height;
    this.renderer.disposeTextLayout(localTextLayout);
    return m;
  }

  public int getLineIndent(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return isListening(3002) ? 0 : this.renderer.getLineIndent(paramInt, this.indent);
  }

  public boolean getLineJustify(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return isListening(3002) ? false : this.renderer.getLineJustify(paramInt, this.justify);
  }

  public int getLineSpacing()
  {
    checkWidget();
    return this.lineSpacing;
  }

  StyledTextEvent getLineStyleData(int paramInt, String paramString)
  {
    return sendLineEvent(3002, paramInt, paramString);
  }

  public int getLinePixel(int paramInt)
  {
    checkWidget();
    int i = this.content.getLineCount();
    paramInt = Math.max(0, Math.min(i, paramInt));
    if (isFixedLineHeight())
    {
      j = this.renderer.getLineHeight();
      return paramInt * j - getVerticalScrollOffset() + this.topMargin;
    }
    if (paramInt == this.topIndex)
      return this.topIndexY + this.topMargin;
    int j = this.topIndexY;
    int k;
    if (paramInt > this.topIndex)
      for (k = this.topIndex; k < paramInt; k++)
        j += this.renderer.getLineHeight(k);
    else
      for (k = this.topIndex - 1; k >= paramInt; k--)
        j -= this.renderer.getLineHeight(k);
    return j + this.topMargin;
  }

  public int getLineIndex(int paramInt)
  {
    checkWidget();
    paramInt -= this.topMargin;
    int j;
    int k;
    if (isFixedLineHeight())
    {
      i = this.renderer.getLineHeight();
      j = (paramInt + getVerticalScrollOffset()) / i;
      k = this.content.getLineCount();
      j = Math.max(0, Math.min(k - 1, j));
      return j;
    }
    if (paramInt == this.topIndexY)
      return this.topIndex;
    int i = this.topIndex;
    if (paramInt < this.topIndexY)
    {
      do
      {
        paramInt += this.renderer.getLineHeight(--i);
        if (paramInt >= this.topIndexY)
          break;
      }
      while (i > 0);
    }
    else
    {
      j = this.content.getLineCount();
      for (k = this.renderer.getLineHeight(i); (paramInt - k >= this.topIndexY) && (i < j - 1); k = this.renderer.getLineHeight(++i))
        paramInt -= k;
    }
    return i;
  }

  public int[] getLineTabStops(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    if (isListening(3002))
      return null;
    int[] arrayOfInt1 = this.renderer.getLineTabStops(paramInt, null);
    if (arrayOfInt1 == null)
      arrayOfInt1 = this.tabs;
    if (arrayOfInt1 == null)
      return new int[] { this.renderer.tabWidth };
    int[] arrayOfInt2 = new int[arrayOfInt1.length];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, arrayOfInt1.length);
    return arrayOfInt2;
  }

  public int getLineWrapIndent(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > this.content.getLineCount()))
      SWT.error(5);
    return isListening(3002) ? 0 : this.renderer.getLineWrapIndent(paramInt, this.wrapIndent);
  }

  public int getLeftMargin()
  {
    checkWidget();
    return this.leftMargin - this.alignmentMargin;
  }

  public Point getLocationAtOffset(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt > getCharCount()))
      SWT.error(6);
    return getPointAtOffset(paramInt);
  }

  public int getOffsetAtLine(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || ((paramInt > 0) && (paramInt >= this.content.getLineCount())))
      SWT.error(6);
    return this.content.getOffsetAtLine(paramInt);
  }

  public int getOffsetAtLocation(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      SWT.error(4);
    int[] arrayOfInt = new int[1];
    int i = getOffsetAtPoint(paramPoint.x, paramPoint.y, arrayOfInt, true);
    if (i == -1)
      SWT.error(5);
    return i + arrayOfInt[0];
  }

  int getOffsetAtPoint(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    int i = getLineIndex(paramInt2);
    paramInt2 -= getLinePixel(i);
    return getOffsetAtPoint(paramInt1, paramInt2, i, paramArrayOfInt);
  }

  int getOffsetAtPoint(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    TextLayout localTextLayout = this.renderer.getTextLayout(paramInt3);
    paramInt1 += this.horizontalScrollOffset - this.leftMargin;
    int[] arrayOfInt = new int[1];
    int i = localTextLayout.getOffset(paramInt1, paramInt2, arrayOfInt);
    if (paramArrayOfInt != null)
      paramArrayOfInt[0] = 1;
    if (arrayOfInt[0] != 0)
    {
      int j = localTextLayout.getLineIndex(i + arrayOfInt[0]);
      int k = localTextLayout.getLineOffsets()[j];
      if (i + arrayOfInt[0] == k)
      {
        i += arrayOfInt[0];
        if (paramArrayOfInt != null)
          paramArrayOfInt[0] = 0;
      }
      else
      {
        String str = this.content.getLine(paramInt3);
        int m = 0;
        int n;
        if (paramArrayOfInt != null)
        {
          for (n = i; (n > 0) && (Character.isDigit(str.charAt(n))); n--);
          if ((n == 0) && (Character.isDigit(str.charAt(n))))
            m = isMirrored() ? 1 : 0;
          else
            m = localTextLayout.getLevel(n) & 0x1;
        }
        i += arrayOfInt[0];
        if (paramArrayOfInt != null)
        {
          n = localTextLayout.getLevel(i) & 0x1;
          if ((m ^ n) != 0)
            paramArrayOfInt[0] = 0;
          else
            paramArrayOfInt[0] = 1;
        }
      }
    }
    this.renderer.disposeTextLayout(localTextLayout);
    return i + this.content.getOffsetAtLine(paramInt3);
  }

  int getOffsetAtPoint(int paramInt1, int paramInt2, int[] paramArrayOfInt, boolean paramBoolean)
  {
    if (((paramBoolean) && (paramInt2 + getVerticalScrollOffset() < 0)) || (paramInt1 + this.horizontalScrollOffset < 0))
      return -1;
    int i = getPartialBottomIndex();
    int j = getLinePixel(i + 1);
    if ((paramBoolean) && (paramInt2 > j))
      return -1;
    int k = getLineIndex(paramInt2);
    int m = this.content.getOffsetAtLine(k);
    TextLayout localTextLayout = this.renderer.getTextLayout(k);
    paramInt1 += this.horizontalScrollOffset - this.leftMargin;
    paramInt2 -= getLinePixel(k);
    int n = localTextLayout.getOffset(paramInt1, paramInt2, paramArrayOfInt);
    Rectangle localRectangle = localTextLayout.getLineBounds(localTextLayout.getLineIndex(n));
    this.renderer.disposeTextLayout(localTextLayout);
    if ((paramBoolean) && ((localRectangle.x > paramInt1) || (paramInt1 > localRectangle.x + localRectangle.width)))
      return -1;
    return n + m;
  }

  public int getOrientation()
  {
    checkWidget();
    if (IS_MAC)
    {
      int i = super.getStyle();
      return i & 0x6000000;
    }
    return isMirrored() ? 67108864 : 33554432;
  }

  int getPartialBottomIndex()
  {
    if (isFixedLineHeight())
    {
      int i = this.renderer.getLineHeight();
      int j = Compatibility.ceil(this.clientAreaHeight, i);
      return Math.max(0, Math.min(this.content.getLineCount(), this.topIndex + j) - 1);
    }
    return getLineIndex(this.clientAreaHeight - this.bottomMargin);
  }

  int getPartialTopIndex()
  {
    if (isFixedLineHeight())
    {
      int i = this.renderer.getLineHeight();
      return getVerticalScrollOffset() / i;
    }
    return this.topIndexY <= 0 ? this.topIndex : this.topIndex - 1;
  }

  String getPlatformDelimitedText(TextWriter paramTextWriter)
  {
    int i = paramTextWriter.getStart() + paramTextWriter.getCharCount();
    int j = this.content.getLineAtOffset(paramTextWriter.getStart());
    int k = this.content.getLineAtOffset(i);
    String str = this.content.getLine(k);
    int m = this.content.getOffsetAtLine(k);
    for (int n = j; n <= k; n++)
    {
      paramTextWriter.writeLine(this.content.getLine(n), this.content.getOffsetAtLine(n));
      if (n < k)
        paramTextWriter.writeLineDelimiter(PlatformLineDelimiter);
    }
    if (i > m + str.length())
      paramTextWriter.writeLineDelimiter(PlatformLineDelimiter);
    paramTextWriter.close();
    return paramTextWriter.toString();
  }

  public int[] getRanges()
  {
    checkWidget();
    if (!isListening(3002))
    {
      int[] arrayOfInt = this.renderer.getRanges(0, this.content.getCharCount());
      if (arrayOfInt != null)
        return arrayOfInt;
    }
    return new int[0];
  }

  public int[] getRanges(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = getCharCount();
    int j = paramInt1 + paramInt2;
    if ((paramInt1 > j) || (paramInt1 < 0) || (j > i))
      SWT.error(6);
    if (!isListening(3002))
    {
      int[] arrayOfInt = this.renderer.getRanges(paramInt1, paramInt2);
      if (arrayOfInt != null)
        return arrayOfInt;
    }
    return new int[0];
  }

  public int getRightMargin()
  {
    checkWidget();
    return this.rightMargin;
  }

  public Point getSelection()
  {
    checkWidget();
    return new Point(this.selection.x, this.selection.y);
  }

  public Point getSelectionRange()
  {
    checkWidget();
    return new Point(this.selection.x, this.selection.y - this.selection.x);
  }

  public int[] getSelectionRanges()
  {
    checkWidget();
    if ((this.blockSelection) && (this.blockXLocation != -1))
    {
      Rectangle localRectangle = getBlockSelectionPosition();
      int i = localRectangle.y;
      int j = localRectangle.height;
      int k = localRectangle.x;
      int m = localRectangle.width;
      int[] arrayOfInt = new int[(j - i + 1) * 2];
      int n = 0;
      for (int i1 = i; i1 <= j; i1++)
      {
        int i2 = getOffsetAtPoint(k, 0, i1, null);
        int i3 = getOffsetAtPoint(m, 0, i1, null);
        if (i2 > i3)
        {
          int i4 = i2;
          i2 = i3;
          i3 = i4;
        }
        arrayOfInt[(n++)] = i2;
        arrayOfInt[(n++)] = (i3 - i2);
      }
      return arrayOfInt;
    }
    return new int[] { this.selection.x, this.selection.y - this.selection.x };
  }

  public Color getSelectionBackground()
  {
    checkWidget();
    if (this.selectionBackground == null)
      return getDisplay().getSystemColor(26);
    return this.selectionBackground;
  }

  public int getSelectionCount()
  {
    checkWidget();
    if ((this.blockSelection) && (this.blockXLocation != -1))
      return getBlockSelectionText(this.content.getLineDelimiter()).length();
    return getSelectionRange().y;
  }

  public Color getSelectionForeground()
  {
    checkWidget();
    if (this.selectionForeground == null)
      return getDisplay().getSystemColor(27);
    return this.selectionForeground;
  }

  public String getSelectionText()
  {
    checkWidget();
    if ((this.blockSelection) && (this.blockXLocation != -1))
      return getBlockSelectionText(this.content.getLineDelimiter());
    return this.content.getTextRange(this.selection.x, this.selection.y - this.selection.x);
  }

  public int getStyle()
  {
    int i = super.getStyle();
    i &= -234881025;
    i |= getOrientation();
    if (isMirrored())
      i |= 134217728;
    return i;
  }

  StyledTextEvent getBidiSegments(int paramInt, String paramString)
  {
    if (!isBidi())
      return null;
    if (!isListening(3007))
    {
      localStyledTextEvent = new StyledTextEvent(this.content);
      localStyledTextEvent.segments = getBidiSegmentsCompatibility(paramString, paramInt);
      return localStyledTextEvent;
    }
    StyledTextEvent localStyledTextEvent = sendLineEvent(3007, paramInt, paramString);
    if ((localStyledTextEvent == null) || (localStyledTextEvent.segments == null) || (localStyledTextEvent.segments.length == 0))
      return null;
    int i = paramString.length();
    int[] arrayOfInt = localStyledTextEvent.segments;
    int j = arrayOfInt.length;
    int k;
    if (localStyledTextEvent.segmentsChars == null)
    {
      if (arrayOfInt[0] != 0)
        SWT.error(5);
      for (k = 1; k < j; k++)
        if ((arrayOfInt[k] <= arrayOfInt[(k - 1)]) || (arrayOfInt[k] > i))
          SWT.error(5);
      if (arrayOfInt[(j - 1)] != i)
      {
        arrayOfInt = new int[j + 1];
        System.arraycopy(localStyledTextEvent.segments, 0, arrayOfInt, 0, j);
        arrayOfInt[j] = i;
      }
      localStyledTextEvent.segments = arrayOfInt;
    }
    else
    {
      for (k = 1; k < j; k++)
        if ((localStyledTextEvent.segments[k] < localStyledTextEvent.segments[(k - 1)]) || (localStyledTextEvent.segments[k] > i))
          SWT.error(5);
    }
    return localStyledTextEvent;
  }

  int[] getBidiSegmentsCompatibility(String paramString, int paramInt)
  {
    int i = paramString.length();
    if (!this.bidiColoring)
      return new int[] { 0, i };
    StyleRange[] arrayOfStyleRange = (StyleRange[])null;
    StyledTextEvent localStyledTextEvent = getLineStyleData(paramInt, paramString);
    if (localStyledTextEvent != null)
      arrayOfStyleRange = localStyledTextEvent.styles;
    else
      arrayOfStyleRange = this.renderer.getStyleRanges(paramInt, i, true);
    if ((arrayOfStyleRange == null) || (arrayOfStyleRange.length == 0))
      return new int[] { 0, i };
    int j = 0;
    int k = 1;
    while ((j < arrayOfStyleRange.length) && (arrayOfStyleRange[j].start == 0) && (arrayOfStyleRange[j].length == i))
      j++;
    int[] arrayOfInt1 = new int[(arrayOfStyleRange.length - j) * 2 + 2];
    for (int m = j; m < arrayOfStyleRange.length; m++)
    {
      StyleRange localStyleRange = arrayOfStyleRange[m];
      int n = Math.max(localStyleRange.start - paramInt, 0);
      int i1 = Math.max(localStyleRange.start + localStyleRange.length - paramInt, n);
      i1 = Math.min(i1, paramString.length());
      if ((m > 0) && (k > 1) && (((n >= arrayOfInt1[(k - 2)]) && (n <= arrayOfInt1[(k - 1)])) || ((i1 >= arrayOfInt1[(k - 2)]) && (i1 <= arrayOfInt1[(k - 1)]) && (localStyleRange.similarTo(arrayOfStyleRange[(m - 1)])))))
      {
        arrayOfInt1[(k - 2)] = Math.min(arrayOfInt1[(k - 2)], n);
        arrayOfInt1[(k - 1)] = Math.max(arrayOfInt1[(k - 1)], i1);
      }
      else
      {
        if (n > arrayOfInt1[(k - 1)])
        {
          arrayOfInt1[k] = n;
          k++;
        }
        arrayOfInt1[k] = i1;
        k++;
      }
    }
    if (i > arrayOfInt1[(k - 1)])
    {
      arrayOfInt1[k] = i;
      k++;
    }
    if (k == arrayOfInt1.length)
      return arrayOfInt1;
    int[] arrayOfInt2 = new int[k];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, k);
    return arrayOfInt2;
  }

  public StyleRange getStyleRangeAtOffset(int paramInt)
  {
    checkWidget();
    if ((paramInt < 0) || (paramInt >= getCharCount()))
      SWT.error(5);
    if (!isListening(3002))
    {
      StyleRange[] arrayOfStyleRange = this.renderer.getStyleRanges(paramInt, 1, true);
      if (arrayOfStyleRange != null)
        return arrayOfStyleRange[0];
    }
    return null;
  }

  public StyleRange[] getStyleRanges()
  {
    checkWidget();
    return getStyleRanges(0, this.content.getCharCount(), true);
  }

  public StyleRange[] getStyleRanges(boolean paramBoolean)
  {
    checkWidget();
    return getStyleRanges(0, this.content.getCharCount(), paramBoolean);
  }

  public StyleRange[] getStyleRanges(int paramInt1, int paramInt2)
  {
    checkWidget();
    return getStyleRanges(paramInt1, paramInt2, true);
  }

  public StyleRange[] getStyleRanges(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = getCharCount();
    int j = paramInt1 + paramInt2;
    if ((paramInt1 > j) || (paramInt1 < 0) || (j > i))
      SWT.error(6);
    if (!isListening(3002))
    {
      StyleRange[] arrayOfStyleRange = this.renderer.getStyleRanges(paramInt1, paramInt2, paramBoolean);
      if (arrayOfStyleRange != null)
        return arrayOfStyleRange;
    }
    return new StyleRange[0];
  }

  public int getTabs()
  {
    checkWidget();
    return this.tabLength;
  }

  public int[] getTabStops()
  {
    checkWidget();
    if (this.tabs == null)
      return new int[] { this.renderer.tabWidth };
    int[] arrayOfInt = new int[this.tabs.length];
    System.arraycopy(this.tabs, 0, arrayOfInt, 0, this.tabs.length);
    return arrayOfInt;
  }

  public String getText()
  {
    checkWidget();
    return this.content.getTextRange(0, getCharCount());
  }

  public String getText(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = getCharCount();
    if ((paramInt1 < 0) || (paramInt1 >= i) || (paramInt2 < 0) || (paramInt2 >= i) || (paramInt1 > paramInt2))
      SWT.error(6);
    return this.content.getTextRange(paramInt1, paramInt2 - paramInt1 + 1);
  }

  public Rectangle getTextBounds(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = getCharCount();
    if ((paramInt1 < 0) || (paramInt1 >= i) || (paramInt2 < 0) || (paramInt2 >= i) || (paramInt1 > paramInt2))
      SWT.error(6);
    int j = this.content.getLineAtOffset(paramInt1);
    int k = this.content.getLineAtOffset(paramInt2);
    int m = getLinePixel(j);
    int n = 0;
    int i1 = 2147483647;
    int i2 = 0;
    for (int i3 = j; i3 <= k; i3++)
    {
      int i4 = this.content.getOffsetAtLine(i3);
      TextLayout localTextLayout = this.renderer.getTextLayout(i3);
      int i5 = localTextLayout.getText().length();
      if (i5 > 0)
      {
        if (i3 == j)
        {
          if (i3 == k)
            localRectangle = localTextLayout.getBounds(paramInt1 - i4, paramInt2 - i4);
          else
            localRectangle = localTextLayout.getBounds(paramInt1 - i4, i5);
          m += localRectangle.y;
        }
        else if (i3 == k)
        {
          localRectangle = localTextLayout.getBounds(0, paramInt2 - i4);
        }
        else
        {
          localRectangle = localTextLayout.getBounds();
        }
        i1 = Math.min(i1, localRectangle.x);
        i2 = Math.max(i2, localRectangle.x + localRectangle.width);
        n += localRectangle.height;
      }
      else
      {
        n += this.renderer.getLineHeight();
      }
      this.renderer.disposeTextLayout(localTextLayout);
    }
    Rectangle localRectangle = new Rectangle(i1, m, i2 - i1, n);
    localRectangle.x += this.leftMargin - this.horizontalScrollOffset;
    return localRectangle;
  }

  public String getTextRange(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = getCharCount();
    int j = paramInt1 + paramInt2;
    if ((paramInt1 > j) || (paramInt1 < 0) || (j > i))
      SWT.error(6);
    return this.content.getTextRange(paramInt1, paramInt2);
  }

  public int getTextLimit()
  {
    checkWidget();
    return this.textLimit;
  }

  public int getTopIndex()
  {
    checkWidget();
    return this.topIndex;
  }

  public int getTopMargin()
  {
    checkWidget();
    return this.topMargin;
  }

  public int getTopPixel()
  {
    checkWidget();
    return getVerticalScrollOffset();
  }

  int getVerticalIncrement()
  {
    return this.renderer.getLineHeight();
  }

  int getVerticalScrollOffset()
  {
    if (this.verticalScrollOffset == -1)
    {
      this.renderer.calculate(0, this.topIndex);
      int i = 0;
      for (int j = 0; j < this.topIndex; j++)
        i += this.renderer.getLineHeight(j);
      i -= this.topIndexY;
      this.verticalScrollOffset = i;
    }
    return this.verticalScrollOffset;
  }

  int getVisualLineIndex(TextLayout paramTextLayout, int paramInt)
  {
    int i = paramTextLayout.getLineIndex(paramInt);
    int[] arrayOfInt = paramTextLayout.getLineOffsets();
    if ((i != 0) && (paramInt == arrayOfInt[i]))
    {
      int j = paramTextLayout.getLineBounds(i).y;
      int k = getCaret().getLocation().y - this.topMargin - getLinePixel(getCaretLine());
      if (j > k)
        i--;
    }
    return i;
  }

  int getCaretDirection()
  {
    if (!isBidiCaret())
      return -1;
    if (this.ime.getCompositionOffset() != -1)
      return -1;
    if ((!this.updateCaretDirection) && (this.caretDirection != 0))
      return this.caretDirection;
    this.updateCaretDirection = false;
    int i = getCaretLine();
    int j = this.content.getOffsetAtLine(i);
    String str = this.content.getLine(i);
    int k = this.caretOffset - j;
    int m = str.length();
    if (m == 0)
      return isMirrored() ? 131072 : 16384;
    if ((this.caretAlignment == 0) && (k > 0))
      k--;
    if ((k == m) && (k > 0))
      k--;
    while ((k > 0) && (Character.isDigit(str.charAt(k))))
      k--;
    if ((k == 0) && (Character.isDigit(str.charAt(k))))
      return isMirrored() ? 131072 : 16384;
    TextLayout localTextLayout = this.renderer.getTextLayout(i);
    int n = localTextLayout.getLevel(k);
    this.renderer.disposeTextLayout(localTextLayout);
    return (n & 0x1) != 0 ? 131072 : 16384;
  }

  int getCaretLine()
  {
    return this.content.getLineAtOffset(this.caretOffset);
  }

  int getWrapWidth()
  {
    if ((this.wordWrap) && (!isSingleLine()))
    {
      int i = this.clientAreaWidth - this.leftMargin - this.rightMargin;
      return i > 0 ? i : 1;
    }
    return -1;
  }

  int getWordNext(int paramInt1, int paramInt2)
  {
    return getWordNext(paramInt1, paramInt2, false);
  }

  int getWordNext(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    int k;
    int j;
    String str;
    if (paramInt1 >= getCharCount())
    {
      i = paramInt1;
      k = this.content.getLineCount() - 1;
      j = this.content.getOffsetAtLine(k);
      str = this.content.getLine(k);
    }
    else
    {
      k = this.content.getLineAtOffset(paramInt1);
      j = this.content.getOffsetAtLine(k);
      str = this.content.getLine(k);
      int m = str.length();
      if (paramInt1 >= j + m)
      {
        i = this.content.getOffsetAtLine(k + 1);
      }
      else
      {
        TextLayout localTextLayout = this.renderer.getTextLayout(k);
        i = j + localTextLayout.getNextOffset(paramInt1 - j, paramInt2);
        this.renderer.disposeTextLayout(localTextLayout);
      }
    }
    if (paramBoolean)
      return i;
    return sendWordBoundaryEvent(3009, paramInt2, paramInt1, i, str, j);
  }

  int getWordPrevious(int paramInt1, int paramInt2)
  {
    return getWordPrevious(paramInt1, paramInt2, false);
  }

  int getWordPrevious(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i;
    int k;
    int j;
    String str1;
    if (paramInt1 <= 0)
    {
      i = 0;
      k = this.content.getLineAtOffset(i);
      j = this.content.getOffsetAtLine(k);
      str1 = this.content.getLine(k);
    }
    else
    {
      k = this.content.getLineAtOffset(paramInt1);
      j = this.content.getOffsetAtLine(k);
      str1 = this.content.getLine(k);
      if (paramInt1 == j)
      {
        String str2 = this.content.getLine(k - 1);
        int n = this.content.getOffsetAtLine(k - 1);
        i = n + str2.length();
      }
      else
      {
        int m = Math.min(paramInt1 - j, str1.length());
        TextLayout localTextLayout = this.renderer.getTextLayout(k);
        i = j + localTextLayout.getPreviousOffset(m, paramInt2);
        this.renderer.disposeTextLayout(localTextLayout);
      }
    }
    if (paramBoolean)
      return i;
    return sendWordBoundaryEvent(3010, paramInt2, paramInt1, i, str1, j);
  }

  public boolean getWordWrap()
  {
    checkWidget();
    return this.wordWrap;
  }

  public int getWrapIndent()
  {
    checkWidget();
    return this.wrapIndent;
  }

  Point getPointAtOffset(int paramInt)
  {
    int i = this.content.getLineAtOffset(paramInt);
    String str = this.content.getLine(i);
    int j = this.content.getOffsetAtLine(i);
    int k = paramInt - j;
    int m = str.length();
    if (i < this.content.getLineCount() - 1)
    {
      int n = this.content.getOffsetAtLine(i + 1) - 1;
      if ((m < k) && (k <= n))
        k = m;
    }
    TextLayout localTextLayout = this.renderer.getTextLayout(i);
    Point localPoint;
    if ((m != 0) && (k <= m))
      if (k == m)
      {
        localPoint = localTextLayout.getLocation(k - 1, true);
        break label225;
      }
    switch (this.caretAlignment)
    {
    case 1:
      localPoint = localTextLayout.getLocation(k, false);
      break;
    case 0:
    default:
      if (k == 0)
      {
        localPoint = localTextLayout.getLocation(k, false);
      }
      else
      {
        localPoint = localTextLayout.getLocation(k - 1, true);
        break;
        localPoint = new Point(localTextLayout.getIndent(), 0);
      }
      break;
    }
    label225: this.renderer.disposeTextLayout(localTextLayout);
    localPoint.x += this.leftMargin - this.horizontalScrollOffset;
    localPoint.y += getLinePixel(i);
    return localPoint;
  }

  public void insert(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    if (this.blockSelection)
    {
      insertBlockSelectionText(paramString, false);
    }
    else
    {
      Point localPoint = getSelectionRange();
      replaceTextRange(localPoint.x, localPoint.y, paramString);
    }
  }

  int insertBlockSelectionText(String paramString, boolean paramBoolean)
  {
    int i = 1;
    for (int j = 0; j < paramString.length(); j++)
    {
      k = paramString.charAt(j);
      if ((k == 10) || (k == 13))
      {
        i++;
        if ((k == 13) && (j + 1 < paramString.length()) && (paramString.charAt(j + 1) == '\n'))
          j++;
      }
    }
    String[] arrayOfString = new String[i];
    int k = 0;
    i = 0;
    int n;
    for (int m = 0; m < paramString.length(); m++)
    {
      n = paramString.charAt(m);
      if ((n == 10) || (n == 13))
      {
        arrayOfString[(i++)] = paramString.substring(k, m);
        if ((n == 13) && (m + 1 < paramString.length()) && (paramString.charAt(m + 1) == '\n'))
          m++;
        k = m + 1;
      }
    }
    arrayOfString[(i++)] = paramString.substring(k);
    int i3;
    if (paramBoolean)
    {
      m = 0;
      for (n = 0; n < arrayOfString.length; n++)
      {
        int i1 = arrayOfString[n].length();
        m = Math.max(m, i1);
      }
      for (n = 0; n < arrayOfString.length; n++)
      {
        String str1 = arrayOfString[n];
        i3 = str1.length();
        if (i3 < m)
        {
          int i4 = m - i3;
          StringBuffer localStringBuffer = new StringBuffer(i3 + i4);
          localStringBuffer.append(str1);
          for (i7 = 0; i7 < i4; i7++)
            localStringBuffer.append(' ');
          arrayOfString[n] = localStringBuffer.toString();
        }
      }
    }
    int i2;
    if (this.blockXLocation != -1)
    {
      Rectangle localRectangle = getBlockSelectionPosition();
      m = localRectangle.y;
      n = localRectangle.height;
      i2 = localRectangle.x;
      i3 = localRectangle.width;
    }
    else
    {
      m = n = getCaretLine();
      i2 = i3 = getPointAtOffset(this.caretOffset).x;
    }
    k = this.caretOffset;
    int i5 = getCaretLine();
    int i6 = 0;
    for (int i7 = m; i7 <= n; i7++)
    {
      String str2 = i6 < i ? arrayOfString[(i6++)] : "";
      int i9 = sendTextEvent(i2, i3, i7, str2, paramBoolean);
      if (i7 == i5)
        k = i9;
    }
    while (i6 < i)
    {
      int i8 = sendTextEvent(i2, i2, i7, arrayOfString[(i6++)], paramBoolean);
      if (i7 == i5)
        k = i8;
      i7++;
    }
    return k;
  }

  void insertBlockSelectionText(char paramChar, int paramInt)
  {
    if ((paramChar == '\r') || (paramChar == '\n'))
      return;
    Rectangle localRectangle = getBlockSelectionPosition();
    int i = localRectangle.y;
    int j = localRectangle.height;
    int k = localRectangle.x;
    int m = localRectangle.width;
    int[] arrayOfInt = new int[1];
    int n = 0;
    int i1 = 0;
    String str1 = paramChar != 0 ? new String(new char[] { paramChar }) : "";
    int i2 = str1.length();
    for (int i3 = i; i3 <= j; i3++)
    {
      String str2 = this.content.getLine(i3);
      int i5 = this.content.getOffsetAtLine(i3);
      int i6 = i5 + str2.length();
      int i7 = getLinePixel(i3);
      int i8 = getOffsetAtPoint(k, i7, arrayOfInt, true);
      int i9 = i8 == -1 ? 1 : 0;
      if (i9 != 0)
        i8 = k < this.leftMargin ? i5 : i6;
      else
        i8 += arrayOfInt[0];
      int i10 = getOffsetAtPoint(m, i7, arrayOfInt, true);
      if (i10 == -1)
        i10 = m < this.leftMargin ? i5 : i6;
      else
        i10 += arrayOfInt[0];
      if (i8 > i10)
      {
        int i11 = i8;
        i8 = i10;
        i10 = i11;
      }
      if ((i8 == i10) && (i9 == 0))
        switch (paramInt)
        {
        case 8:
          if (i8 > i5)
            i8 = getClusterPrevious(i8, i3);
          break;
        case 127:
          if (i10 < i6)
            i10 = getClusterNext(i10, i3);
          break;
        }
      if (i9 != 0)
      {
        if (str2.length() >= i1)
        {
          i1 = str2.length();
          n = i6 + i2;
        }
      }
      else
      {
        n = i8 + i2;
        i1 = this.content.getCharCount();
      }
      Event localEvent = new Event();
      localEvent.text = str1;
      localEvent.start = i8;
      localEvent.end = i10;
      sendKeyEvent(localEvent);
    }
    i3 = getPointAtOffset(n).x;
    int i4 = getVerticalScrollOffset();
    setBlockSelectionLocation(i3, this.blockYAnchor - i4, i3, this.blockYLocation - i4, false);
  }

  void installDefaultContent()
  {
    this.textChangeListener = new TextChangeListener()
    {
      public void textChanging(TextChangingEvent paramAnonymousTextChangingEvent)
      {
        StyledText.this.handleTextChanging(paramAnonymousTextChangingEvent);
      }

      public void textChanged(TextChangedEvent paramAnonymousTextChangedEvent)
      {
        StyledText.this.handleTextChanged(paramAnonymousTextChangedEvent);
      }

      public void textSet(TextChangedEvent paramAnonymousTextChangedEvent)
      {
        StyledText.this.handleTextSet(paramAnonymousTextChangedEvent);
      }
    };
    this.content = new DefaultContent();
    this.content.addTextChangeListener(this.textChangeListener);
  }

  void installListeners()
  {
    ScrollBar localScrollBar1 = getVerticalBar();
    ScrollBar localScrollBar2 = getHorizontalBar();
    this.listener = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          StyledText.this.handleDispose(paramAnonymousEvent);
          break;
        case 1:
          StyledText.this.handleKeyDown(paramAnonymousEvent);
          break;
        case 2:
          StyledText.this.handleKeyUp(paramAnonymousEvent);
          break;
        case 3:
          StyledText.this.handleMouseDown(paramAnonymousEvent);
          break;
        case 4:
          StyledText.this.handleMouseUp(paramAnonymousEvent);
          break;
        case 5:
          StyledText.this.handleMouseMove(paramAnonymousEvent);
          break;
        case 9:
          StyledText.this.handlePaint(paramAnonymousEvent);
          break;
        case 11:
          StyledText.this.handleResize(paramAnonymousEvent);
          break;
        case 31:
          StyledText.this.handleTraverse(paramAnonymousEvent);
        }
      }
    };
    addListener(12, this.listener);
    addListener(1, this.listener);
    addListener(2, this.listener);
    addListener(3, this.listener);
    addListener(4, this.listener);
    addListener(5, this.listener);
    addListener(9, this.listener);
    addListener(11, this.listener);
    addListener(31, this.listener);
    this.ime.addListener(43, new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.detail)
        {
        case 3:
          StyledText.this.handleCompositionSelection(paramAnonymousEvent);
          break;
        case 1:
          StyledText.this.handleCompositionChanged(paramAnonymousEvent);
          break;
        case 2:
          StyledText.this.handleCompositionOffset(paramAnonymousEvent);
        }
      }
    });
    if (localScrollBar1 != null)
      localScrollBar1.addListener(13, new Listener()
      {
        public void handleEvent(Event paramAnonymousEvent)
        {
          StyledText.this.handleVerticalScroll(paramAnonymousEvent);
        }
      });
    if (localScrollBar2 != null)
      localScrollBar2.addListener(13, new Listener()
      {
        public void handleEvent(Event paramAnonymousEvent)
        {
          StyledText.this.handleHorizontalScroll(paramAnonymousEvent);
        }
      });
  }

  void internalRedrawRange(int paramInt1, int paramInt2)
  {
    if (paramInt2 <= 0)
      return;
    int i = paramInt1 + paramInt2;
    int j = this.content.getLineAtOffset(paramInt1);
    int k = this.content.getLineAtOffset(i);
    int m = getPartialBottomIndex();
    int n = getPartialTopIndex();
    if ((j > m) || (k < n))
      return;
    if (n > j)
    {
      j = n;
      paramInt1 = 0;
    }
    else
    {
      paramInt1 -= this.content.getOffsetAtLine(j);
    }
    if (m < k)
    {
      k = m + 1;
      i = 0;
    }
    else
    {
      i -= this.content.getOffsetAtLine(k);
    }
    TextLayout localTextLayout = this.renderer.getTextLayout(j);
    int i1 = this.leftMargin - this.horizontalScrollOffset;
    int i2 = getLinePixel(j);
    int[] arrayOfInt = localTextLayout.getLineOffsets();
    int i3 = localTextLayout.getLineIndex(Math.min(paramInt1, localTextLayout.getText().length()));
    if ((this.wordWrap) && (i3 > 0) && (arrayOfInt[i3] == paramInt1))
    {
      Rectangle localRectangle1 = localTextLayout.getLineBounds(i3 - 1);
      localRectangle1.x = localRectangle1.width;
      localRectangle1.width = (this.clientAreaWidth - this.rightMargin - localRectangle1.x);
      localRectangle1.x += i1;
      localRectangle1.y += i2;
      super.redraw(localRectangle1.x, localRectangle1.y, localRectangle1.width, localRectangle1.height, false);
    }
    Rectangle localRectangle3;
    if (j == k)
    {
      int i4 = localTextLayout.getLineIndex(Math.min(i, localTextLayout.getText().length()));
      if (i3 == i4)
      {
        localRectangle3 = localTextLayout.getBounds(paramInt1, i - 1);
        localRectangle3.x += i1;
        localRectangle3.y += i2;
        super.redraw(localRectangle3.x, localRectangle3.y, localRectangle3.width, localRectangle3.height, false);
        this.renderer.disposeTextLayout(localTextLayout);
        return;
      }
    }
    Rectangle localRectangle2 = localTextLayout.getBounds(paramInt1, arrayOfInt[(i3 + 1)] - 1);
    if (localRectangle2.height == 0)
    {
      localRectangle3 = localTextLayout.getLineBounds(i3);
      localRectangle2.x = localRectangle3.width;
      localRectangle2.y = localRectangle3.y;
      localRectangle2.height = localRectangle3.height;
    }
    localRectangle2.x += i1;
    localRectangle2.y += i2;
    localRectangle2.width = (this.clientAreaWidth - this.rightMargin - localRectangle2.x);
    super.redraw(localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height, false);
    if (j != k)
    {
      this.renderer.disposeTextLayout(localTextLayout);
      localTextLayout = this.renderer.getTextLayout(k);
      arrayOfInt = localTextLayout.getLineOffsets();
    }
    int i5 = localTextLayout.getLineIndex(Math.min(i, localTextLayout.getText().length()));
    Rectangle localRectangle4 = localTextLayout.getBounds(arrayOfInt[i5], i - 1);
    if (localRectangle4.height == 0)
    {
      Rectangle localRectangle5 = localTextLayout.getLineBounds(i5);
      localRectangle4.y = localRectangle5.y;
      localRectangle4.height = localRectangle5.height;
    }
    localRectangle4.x += i1;
    localRectangle4.y += getLinePixel(k);
    super.redraw(localRectangle4.x, localRectangle4.y, localRectangle4.width, localRectangle4.height, false);
    this.renderer.disposeTextLayout(localTextLayout);
    int i6 = localRectangle2.y + localRectangle2.height;
    if (localRectangle4.y > i6)
      super.redraw(this.leftMargin, i6, this.clientAreaWidth - this.rightMargin - this.leftMargin, localRectangle4.y - i6, false);
  }

  void handleCompositionOffset(Event paramEvent)
  {
    int[] arrayOfInt = new int[1];
    paramEvent.index = getOffsetAtPoint(paramEvent.x, paramEvent.y, arrayOfInt, true);
    paramEvent.count = arrayOfInt[0];
  }

  void handleCompositionSelection(Event paramEvent)
  {
    paramEvent.start = this.selection.x;
    paramEvent.end = this.selection.y;
    paramEvent.text = getSelectionText();
  }

  void handleCompositionChanged(Event paramEvent)
  {
    String str = paramEvent.text;
    int i = paramEvent.start;
    int j = paramEvent.end;
    int k = str.length();
    if (k == this.ime.getCommitCount())
    {
      this.content.replaceTextRange(i, j - i, "");
      setCaretOffset(this.ime.getCompositionOffset(), -1);
      this.caretWidth = 0;
      this.caretDirection = 0;
    }
    else
    {
      this.content.replaceTextRange(i, j - i, str);
      int m = -1;
      if (this.ime.getWideCaret())
      {
        i = this.ime.getCompositionOffset();
        int n = getCaretLine();
        int i1 = this.content.getOffsetAtLine(n);
        TextLayout localTextLayout = this.renderer.getTextLayout(n);
        this.caretWidth = localTextLayout.getBounds(i - i1, i + k - 1 - i1).width;
        this.renderer.disposeTextLayout(localTextLayout);
        m = 1;
      }
      setCaretOffset(this.ime.getCaretOffset(), m);
    }
    showCaret();
  }

  void handleDispose(Event paramEvent)
  {
    removeListener(12, this.listener);
    notifyListeners(12, paramEvent);
    paramEvent.type = 0;
    this.clipboard.dispose();
    if (this.renderer != null)
    {
      this.renderer.dispose();
      this.renderer = null;
    }
    if (this.content != null)
    {
      this.content.removeTextChangeListener(this.textChangeListener);
      this.content = null;
    }
    if (this.defaultCaret != null)
    {
      this.defaultCaret.dispose();
      this.defaultCaret = null;
    }
    if (this.leftCaretBitmap != null)
    {
      this.leftCaretBitmap.dispose();
      this.leftCaretBitmap = null;
    }
    if (this.rightCaretBitmap != null)
    {
      this.rightCaretBitmap.dispose();
      this.rightCaretBitmap = null;
    }
    if (isBidiCaret())
      BidiUtil.removeLanguageListener(this);
    this.selectionBackground = null;
    this.selectionForeground = null;
    this.marginColor = null;
    this.textChangeListener = null;
    this.selection = null;
    this.doubleClickSelection = null;
    this.keyActionMap = null;
    this.background = null;
    this.foreground = null;
    this.clipboard = null;
    this.tabs = null;
  }

  void handleHorizontalScroll(Event paramEvent)
  {
    int i = getHorizontalBar().getSelection() - this.horizontalScrollOffset;
    scrollHorizontal(i, false);
  }

  void handleKey(Event paramEvent)
  {
    this.caretAlignment = 0;
    int i;
    int j;
    if (paramEvent.keyCode != 0)
    {
      i = getKeyBinding(paramEvent.keyCode | paramEvent.stateMask);
    }
    else
    {
      i = getKeyBinding(paramEvent.character | paramEvent.stateMask);
      if ((i == 0) && ((paramEvent.stateMask & 0x40000) != 0) && (paramEvent.character <= '\037'))
      {
        j = paramEvent.character + '@';
        i = getKeyBinding(j | paramEvent.stateMask);
      }
    }
    if (i == 0)
    {
      j = 0;
      if (IS_MAC)
        j = ((paramEvent.stateMask ^ 0x400000) != 0) && ((paramEvent.stateMask ^ 0x420000) != 0) ? 0 : 1;
      else if (IS_MOTIF)
        j = ((paramEvent.stateMask ^ 0x40000) != 0) && ((paramEvent.stateMask ^ 0x60000) != 0) ? 0 : 1;
      else
        j = ((paramEvent.stateMask ^ 0x10000) != 0) && ((paramEvent.stateMask ^ 0x40000) != 0) && ((paramEvent.stateMask ^ 0x30000) != 0) && ((paramEvent.stateMask ^ 0x60000) != 0) ? 0 : 1;
      if (((j == 0) && (paramEvent.character > '\037') && (paramEvent.character != '')) || (paramEvent.character == '\r') || (paramEvent.character == '\n') || (paramEvent.character == '\t'))
      {
        doContent(paramEvent.character);
        update();
      }
    }
    else
    {
      invokeAction(i);
    }
  }

  void handleKeyDown(Event paramEvent)
  {
    if (this.clipboardSelection == null)
      this.clipboardSelection = new Point(this.selection.x, this.selection.y);
    this.newOrientation = 0;
    Event localEvent = new Event();
    localEvent.character = paramEvent.character;
    localEvent.keyCode = paramEvent.keyCode;
    localEvent.keyLocation = paramEvent.keyLocation;
    localEvent.stateMask = paramEvent.stateMask;
    localEvent.doit = true;
    notifyListeners(3005, localEvent);
    if (localEvent.doit)
    {
      if (((paramEvent.stateMask & SWT.MODIFIER_MASK) == 262144) && (paramEvent.keyCode == 131072) && (isBidiCaret()))
        this.newOrientation = (paramEvent.keyLocation == 16384 ? 33554432 : 67108864);
      handleKey(paramEvent);
    }
  }

  void handleKeyUp(Event paramEvent)
  {
    if ((this.clipboardSelection != null) && ((this.clipboardSelection.x != this.selection.x) || (this.clipboardSelection.y != this.selection.y)))
      copySelection(2);
    this.clipboardSelection = null;
    if (this.newOrientation != 0)
    {
      if (this.newOrientation != getOrientation())
      {
        Event localEvent = new Event();
        localEvent.doit = true;
        notifyListeners(44, localEvent);
        if (localEvent.doit)
          setOrientation(this.newOrientation);
      }
      this.newOrientation = 0;
    }
  }

  void handleMouseDown(Event paramEvent)
  {
    forceFocus();
    if ((this.dragDetect) && (checkDragDetect(paramEvent)))
      return;
    if (paramEvent.button == 2)
    {
      String str = (String)getClipboardContent(2);
      if ((str != null) && (str.length() > 0))
      {
        doMouseLocationChange(paramEvent.x, paramEvent.y, false);
        Event localEvent = new Event();
        localEvent.start = this.selection.x;
        localEvent.end = this.selection.y;
        localEvent.text = getModelDelimitedText(str);
        sendKeyEvent(localEvent);
      }
    }
    if ((paramEvent.button != 1) || ((IS_MAC) && ((paramEvent.stateMask & SWT.MOD4) != 0)))
      return;
    this.clickCount = paramEvent.count;
    boolean bool;
    if (this.clickCount == 1)
    {
      bool = (paramEvent.stateMask & SWT.MOD2) != 0;
      doMouseLocationChange(paramEvent.x, paramEvent.y, bool);
    }
    else if (this.doubleClickEnabled)
    {
      bool = (this.clickCount & 0x1) == 0;
      int i = getOffsetAtPoint(paramEvent.x, paramEvent.y, null);
      int j = this.content.getLineAtOffset(i);
      int k = this.content.getOffsetAtLine(j);
      int m;
      if (bool)
      {
        m = this.blockSelection ? k : 0;
        int n = this.blockSelection ? k + this.content.getLine(j).length() : this.content.getCharCount();
        int i1 = Math.max(m, getWordPrevious(i, 16));
        int i2 = Math.min(n, getWordNext(i1, 8));
        setSelection(i1, i2 - i1, false, true);
        sendSelectionEvent();
      }
      else if (this.blockSelection)
      {
        setBlockSelectionLocation(this.leftMargin, paramEvent.y, this.clientAreaWidth - this.rightMargin, paramEvent.y, true);
      }
      else
      {
        m = this.content.getCharCount();
        if (j + 1 < this.content.getLineCount())
          m = this.content.getOffsetAtLine(j + 1);
        setSelection(k, m - k, false, false);
        sendSelectionEvent();
      }
      this.doubleClickSelection = new Point(this.selection.x, this.selection.y);
      showCaret();
    }
  }

  void handleMouseMove(Event paramEvent)
  {
    if (this.clickCount > 0)
    {
      update();
      doAutoScroll(paramEvent);
      doMouseLocationChange(paramEvent.x, paramEvent.y, true);
    }
    if (this.renderer.hasLinks)
      doMouseLinkCursor(paramEvent.x, paramEvent.y);
  }

  void handleMouseUp(Event paramEvent)
  {
    this.clickCount = 0;
    endAutoScroll();
    if (paramEvent.button == 1)
      copySelection(2);
  }

  void handlePaint(Event paramEvent)
  {
    if ((paramEvent.width == 0) || (paramEvent.height == 0))
      return;
    if ((this.clientAreaWidth == 0) || (this.clientAreaHeight == 0))
      return;
    int i = getLineIndex(paramEvent.y);
    int j = getLinePixel(i);
    int k = paramEvent.y + paramEvent.height;
    GC localGC = paramEvent.gc;
    Color localColor1 = getBackground();
    Color localColor2 = getForeground();
    if (k > 0)
    {
      int m = isSingleLine() ? 1 : this.content.getLineCount();
      int n = this.leftMargin - this.horizontalScrollOffset;
      for (int i1 = i; (j < k) && (i1 < m); i1++)
        j += this.renderer.drawLine(i1, n, j, localGC, localColor1, localColor2);
      if (j < k)
      {
        localGC.setBackground(localColor1);
        drawBackground(localGC, 0, j, this.clientAreaWidth, k - j);
      }
    }
    if ((this.blockSelection) && (this.blockXLocation != -1))
    {
      localGC.setBackground(getSelectionBackground());
      Rectangle localRectangle = getBlockSelectionRectangle();
      localGC.drawRectangle(localRectangle.x, localRectangle.y, Math.max(1, localRectangle.width - 1), Math.max(1, localRectangle.height - 1));
      localGC.setAdvanced(true);
      if (localGC.getAdvanced())
      {
        localGC.setAlpha(100);
        localGC.fillRectangle(localRectangle);
        localGC.setAdvanced(false);
      }
    }
    localGC.setBackground(this.marginColor != null ? this.marginColor : localColor1);
    if (this.topMargin > 0)
      drawBackground(localGC, 0, 0, this.clientAreaWidth, this.topMargin);
    if (this.bottomMargin > 0)
      drawBackground(localGC, 0, this.clientAreaHeight - this.bottomMargin, this.clientAreaWidth, this.bottomMargin);
    if (this.leftMargin - this.alignmentMargin > 0)
      drawBackground(localGC, 0, 0, this.leftMargin - this.alignmentMargin, this.clientAreaHeight);
    if (this.rightMargin > 0)
      drawBackground(localGC, this.clientAreaWidth - this.rightMargin, 0, this.rightMargin, this.clientAreaHeight);
  }

  void handleResize(Event paramEvent)
  {
    int i = this.clientAreaHeight;
    int j = this.clientAreaWidth;
    Rectangle localRectangle = getClientArea();
    this.clientAreaHeight = localRectangle.height;
    this.clientAreaWidth = localRectangle.width;
    int k;
    if ((j != this.clientAreaWidth) && (this.rightMargin > 0))
    {
      k = (j < this.clientAreaWidth ? j : this.clientAreaWidth) - this.rightMargin;
      super.redraw(k, 0, this.rightMargin, i, false);
    }
    if ((i != this.clientAreaHeight) && (this.bottomMargin > 0))
    {
      k = (i < this.clientAreaHeight ? i : this.clientAreaHeight) - this.bottomMargin;
      super.redraw(0, k, j, this.bottomMargin, false);
    }
    if (this.wordWrap)
    {
      if (j != this.clientAreaWidth)
      {
        this.renderer.reset(0, this.content.getLineCount());
        this.verticalScrollOffset = -1;
        this.renderer.calculateIdle();
        super.redraw();
      }
      if (i != this.clientAreaHeight)
      {
        if (i == 0)
          this.topIndexY = 0;
        setScrollBars(true);
      }
      setCaretLocation();
    }
    else
    {
      this.renderer.calculateClientArea();
      setScrollBars(true);
      claimRightFreeSpace();
      if (this.clientAreaWidth != 0)
      {
        ScrollBar localScrollBar = getHorizontalBar();
        if ((localScrollBar != null) && (localScrollBar.getVisible()) && (this.horizontalScrollOffset != localScrollBar.getSelection()))
        {
          localScrollBar.setSelection(this.horizontalScrollOffset);
          this.horizontalScrollOffset = localScrollBar.getSelection();
        }
      }
    }
    updateCaretVisibility();
    claimBottomFreeSpace();
    setAlignment();
  }

  void handleTextChanged(TextChangedEvent paramTextChangedEvent)
  {
    int i = this.ime.getCompositionOffset();
    if ((i != -1) && (this.lastTextChangeStart < i))
      this.ime.setCompositionOffset(i + this.lastTextChangeNewCharCount - this.lastTextChangeReplaceCharCount);
    int j = this.content.getLineAtOffset(this.lastTextChangeStart);
    resetCache(j, 0);
    if ((!isFixedLineHeight()) && (this.topIndex > j))
    {
      this.topIndex = j;
      this.topIndexY = 0;
      super.redraw();
    }
    else
    {
      int k = j + this.lastTextChangeNewLineCount;
      int m = getLinePixel(j);
      int n = getLinePixel(k + 1);
      if (this.lastLineBottom != n)
      {
        super.redraw();
      }
      else
      {
        super.redraw(0, m, this.clientAreaWidth, n - m, false);
        redrawLinesBullet(this.renderer.redrawLines);
      }
    }
    this.renderer.redrawLines = null;
    if ((!this.blockSelection) || (this.blockXLocation == -1))
      updateSelection(this.lastTextChangeStart, this.lastTextChangeReplaceCharCount, this.lastTextChangeNewCharCount);
    if ((this.lastTextChangeReplaceLineCount > 0) || (this.wordWrap))
      claimBottomFreeSpace();
    if (this.lastTextChangeReplaceCharCount > 0)
      claimRightFreeSpace();
    sendAccessibleTextChanged(this.lastTextChangeStart, this.lastTextChangeNewCharCount, 0);
    this.lastCharCount += this.lastTextChangeNewCharCount;
    this.lastCharCount -= this.lastTextChangeReplaceCharCount;
    setAlignment();
  }

  void handleTextChanging(TextChangingEvent paramTextChangingEvent)
  {
    if (paramTextChangingEvent.replaceCharCount < 0)
    {
      paramTextChangingEvent.start += paramTextChangingEvent.replaceCharCount;
      paramTextChangingEvent.replaceCharCount *= -1;
    }
    this.lastTextChangeStart = paramTextChangingEvent.start;
    this.lastTextChangeNewLineCount = paramTextChangingEvent.newLineCount;
    this.lastTextChangeNewCharCount = paramTextChangingEvent.newCharCount;
    this.lastTextChangeReplaceLineCount = paramTextChangingEvent.replaceLineCount;
    this.lastTextChangeReplaceCharCount = paramTextChangingEvent.replaceCharCount;
    int i = this.content.getLineAtOffset(paramTextChangingEvent.start);
    int j = getLinePixel(i + paramTextChangingEvent.replaceLineCount + 1);
    int k = getLinePixel(i + 1) + paramTextChangingEvent.newLineCount * this.renderer.getLineHeight();
    this.lastLineBottom = k;
    if ((j < 0) && (k < 0))
    {
      this.lastLineBottom += j - k;
      this.verticalScrollOffset += k - j;
      calculateTopIndex(k - j);
      setScrollBars(true);
    }
    else
    {
      scrollText(j, k);
    }
    sendAccessibleTextChanged(this.lastTextChangeStart, 0, this.lastTextChangeReplaceCharCount);
    this.renderer.textChanging(paramTextChangingEvent);
    int m = this.content.getCharCount() - paramTextChangingEvent.replaceCharCount + paramTextChangingEvent.newCharCount;
    if (this.caretOffset > m)
      setCaretOffset(m, -1);
  }

  void handleTextSet(TextChangedEvent paramTextChangedEvent)
  {
    reset();
    int i = getCharCount();
    sendAccessibleTextChanged(0, i, this.lastCharCount);
    this.lastCharCount = i;
    setAlignment();
  }

  void handleTraverse(Event paramEvent)
  {
    switch (paramEvent.detail)
    {
    case 2:
    case 256:
    case 512:
      paramEvent.doit = true;
      break;
    case 4:
    case 8:
    case 16:
      if ((getStyle() & 0x4) != 0)
        paramEvent.doit = true;
      else if ((!this.editable) || ((paramEvent.stateMask & SWT.MODIFIER_MASK) != 0))
        paramEvent.doit = true;
      break;
    }
  }

  void handleVerticalScroll(Event paramEvent)
  {
    int i = getVerticalBar().getSelection() - getVerticalScrollOffset();
    scrollVertical(i, false);
  }

  void initializeAccessible()
  {
    Accessible localAccessible = getAccessible();
    localAccessible.addAccessibleListener(new AccessibleAdapter()
    {
      public void getName(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str1 = null;
        String str2 = StyledText.this.getAssociatedLabel();
        if (str2 != null)
          str1 = StyledText.this.stripMnemonic(str2);
        paramAnonymousAccessibleEvent.result = str1;
      }

      public void getHelp(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        paramAnonymousAccessibleEvent.result = StyledText.this.getToolTipText();
      }

      public void getKeyboardShortcut(AccessibleEvent paramAnonymousAccessibleEvent)
      {
        String str1 = null;
        String str2 = StyledText.this.getAssociatedLabel();
        if (str2 != null)
        {
          char c = StyledText.this._findMnemonic(str2);
          if (c != 0)
            str1 = "Alt+" + c;
        }
        paramAnonymousAccessibleEvent.result = str1;
      }
    });
    localAccessible.addAccessibleTextListener(new AccessibleTextExtendedAdapter()
    {
      public void getCaretOffset(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        paramAnonymousAccessibleTextEvent.offset = StyledText.this.getCaretOffset();
      }

      public void setCaretOffset(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText.this.setCaretOffset(paramAnonymousAccessibleTextEvent.offset);
        paramAnonymousAccessibleTextEvent.result = "OK";
      }

      public void getSelectionRange(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        Point localPoint = StyledText.this.getSelectionRange();
        paramAnonymousAccessibleTextEvent.offset = localPoint.x;
        paramAnonymousAccessibleTextEvent.length = localPoint.y;
      }

      public void addSelection(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        Point localPoint = localStyledText.getSelection();
        if (localPoint.x == localPoint.y)
        {
          int i = paramAnonymousAccessibleTextEvent.end;
          if (i == -1)
            i = localStyledText.getCharCount();
          localStyledText.setSelection(paramAnonymousAccessibleTextEvent.start, i);
          paramAnonymousAccessibleTextEvent.result = "OK";
        }
      }

      public void getSelection(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        Object localObject;
        int i;
        if ((localStyledText.blockSelection) && (localStyledText.blockXLocation != -1))
        {
          localObject = localStyledText.getBlockSelectionPosition();
          i = ((Rectangle)localObject).y + paramAnonymousAccessibleTextEvent.index;
          int j = localStyledText.getLinePixel(i);
          paramAnonymousAccessibleTextEvent.ranges = getRanges(((Rectangle)localObject).x, j, ((Rectangle)localObject).width, j);
          if (paramAnonymousAccessibleTextEvent.ranges.length > 0)
          {
            paramAnonymousAccessibleTextEvent.start = paramAnonymousAccessibleTextEvent.ranges[0];
            paramAnonymousAccessibleTextEvent.end = paramAnonymousAccessibleTextEvent.ranges[(paramAnonymousAccessibleTextEvent.ranges.length - 1)];
          }
        }
        else if (paramAnonymousAccessibleTextEvent.index == 0)
        {
          localObject = localStyledText.getSelection();
          paramAnonymousAccessibleTextEvent.start = ((Point)localObject).x;
          paramAnonymousAccessibleTextEvent.end = ((Point)localObject).y;
          if (paramAnonymousAccessibleTextEvent.start > paramAnonymousAccessibleTextEvent.end)
          {
            i = paramAnonymousAccessibleTextEvent.start;
            paramAnonymousAccessibleTextEvent.start = paramAnonymousAccessibleTextEvent.end;
            paramAnonymousAccessibleTextEvent.end = i;
          }
        }
      }

      public void getSelectionCount(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        Object localObject;
        if ((localStyledText.blockSelection) && (localStyledText.blockXLocation != -1))
        {
          localObject = localStyledText.getBlockSelectionPosition();
          paramAnonymousAccessibleTextEvent.count = (((Rectangle)localObject).height - ((Rectangle)localObject).y + 1);
        }
        else
        {
          localObject = localStyledText.getSelection();
          paramAnonymousAccessibleTextEvent.count = (((Point)localObject).x == ((Point)localObject).y ? 0 : 1);
        }
      }

      public void removeSelection(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        if (paramAnonymousAccessibleTextEvent.index == 0)
        {
          if (localStyledText.blockSelection)
            localStyledText.clearBlockSelection(true, false);
          else
            localStyledText.clearSelection(false);
          paramAnonymousAccessibleTextEvent.result = "OK";
        }
      }

      public void setSelection(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        if (paramAnonymousAccessibleTextEvent.index != 0)
          return;
        StyledText localStyledText = StyledText.this;
        Point localPoint = localStyledText.getSelection();
        if (localPoint.x == localPoint.y)
          return;
        int i = paramAnonymousAccessibleTextEvent.end;
        if (i == -1)
          i = localStyledText.getCharCount();
        localStyledText.setSelection(paramAnonymousAccessibleTextEvent.start, i);
        paramAnonymousAccessibleTextEvent.result = "OK";
      }

      public void getCharacterCount(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        paramAnonymousAccessibleTextEvent.count = StyledText.this.getCharCount();
      }

      public void getOffsetAtPoint(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        Point localPoint = new Point(paramAnonymousAccessibleTextEvent.x, paramAnonymousAccessibleTextEvent.y);
        Display localDisplay = localStyledText.getDisplay();
        localPoint = localDisplay.map(null, localStyledText, localPoint);
        paramAnonymousAccessibleTextEvent.offset = localStyledText.getOffsetAtPoint(localPoint.x, localPoint.y, null, true);
      }

      public void getTextBounds(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        int i = paramAnonymousAccessibleTextEvent.start;
        int j = paramAnonymousAccessibleTextEvent.end;
        int k = localStyledText.getCharCount();
        i = Math.max(0, Math.min(i, k));
        j = Math.max(0, Math.min(j, k));
        if (i > j)
        {
          m = i;
          i = j;
          j = m;
        }
        int m = localStyledText.getLineAtOffset(i);
        int n = localStyledText.getLineAtOffset(j);
        Rectangle[] arrayOfRectangle = new Rectangle[n - m + 1];
        Rectangle localRectangle1 = null;
        int i1 = 0;
        Display localDisplay = localStyledText.getDisplay();
        for (int i2 = m; i2 <= n; i2++)
        {
          Rectangle localRectangle2 = new Rectangle(0, 0, 0, 0);
          localRectangle2.y = localStyledText.getLinePixel(i2);
          localRectangle2.height = localStyledText.renderer.getLineHeight(i2);
          if (i2 == m)
            localRectangle2.x = localStyledText.getPointAtOffset(i).x;
          else
            localRectangle2.x = (localStyledText.leftMargin - localStyledText.horizontalScrollOffset);
          if (i2 == n)
          {
            localRectangle2.width = (localStyledText.getPointAtOffset(j).x - localRectangle2.x);
          }
          else
          {
            TextLayout localTextLayout = localStyledText.renderer.getTextLayout(i2);
            localRectangle2.width = (localTextLayout.getBounds().width - localRectangle2.x);
            localStyledText.renderer.disposeTextLayout(localTextLayout);
          }
          Rectangle tmp268_265 = localDisplay.map(localStyledText, null, localRectangle2);
          localRectangle2 = tmp268_265;
          arrayOfRectangle[(i1++)] = tmp268_265;
          if (localRectangle1 == null)
            localRectangle1 = new Rectangle(localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
          else
            localRectangle1.add(localRectangle2);
        }
        paramAnonymousAccessibleTextEvent.rectangles = arrayOfRectangle;
        if (localRectangle1 != null)
        {
          paramAnonymousAccessibleTextEvent.x = localRectangle1.x;
          paramAnonymousAccessibleTextEvent.y = localRectangle1.y;
          paramAnonymousAccessibleTextEvent.width = localRectangle1.width;
          paramAnonymousAccessibleTextEvent.height = localRectangle1.height;
        }
      }

      int[] getRanges(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
      {
        StyledText localStyledText = StyledText.this;
        int i = localStyledText.getLineIndex(paramAnonymousInt2);
        int j = localStyledText.getLineIndex(paramAnonymousInt4);
        int k = j - i + 1;
        int[] arrayOfInt1 = new int[k * 2];
        int m = 0;
        for (int n = i; n <= j; n++)
        {
          String str = localStyledText.content.getLine(n);
          int i1 = localStyledText.content.getOffsetAtLine(n);
          int i2 = i1 + str.length();
          int i3 = localStyledText.getLinePixel(n);
          int i4 = localStyledText.getOffsetAtPoint(paramAnonymousInt1, i3, null, true);
          if (i4 == -1)
            i4 = paramAnonymousInt1 < localStyledText.leftMargin ? i1 : i2;
          int[] arrayOfInt2 = new int[1];
          int i5 = localStyledText.getOffsetAtPoint(paramAnonymousInt3, i3, arrayOfInt2, true);
          if (i5 == -1)
            i5 = paramAnonymousInt3 < localStyledText.leftMargin ? i1 : i2;
          else
            i5 += arrayOfInt2[0];
          if (i4 > i5)
          {
            int i6 = i4;
            i4 = i5;
            i5 = i6;
          }
          arrayOfInt1[(m++)] = i4;
          arrayOfInt1[(m++)] = i5;
        }
        return arrayOfInt1;
      }

      public void getRanges(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        Point localPoint = new Point(paramAnonymousAccessibleTextEvent.x, paramAnonymousAccessibleTextEvent.y);
        Display localDisplay = localStyledText.getDisplay();
        localPoint = localDisplay.map(null, localStyledText, localPoint);
        paramAnonymousAccessibleTextEvent.ranges = getRanges(localPoint.x, localPoint.y, localPoint.x + paramAnonymousAccessibleTextEvent.width, localPoint.y + paramAnonymousAccessibleTextEvent.height);
        if (paramAnonymousAccessibleTextEvent.ranges.length > 0)
        {
          paramAnonymousAccessibleTextEvent.start = paramAnonymousAccessibleTextEvent.ranges[0];
          paramAnonymousAccessibleTextEvent.end = paramAnonymousAccessibleTextEvent.ranges[(paramAnonymousAccessibleTextEvent.ranges.length - 1)];
        }
      }

      public void getText(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        int i = paramAnonymousAccessibleTextEvent.start;
        int j = paramAnonymousAccessibleTextEvent.end;
        int k = localStyledText.getCharCount();
        if (j == -1)
          j = k;
        i = Math.max(0, Math.min(i, k));
        j = Math.max(0, Math.min(j, k));
        if (i > j)
        {
          m = i;
          i = j;
          j = m;
        }
        int m = paramAnonymousAccessibleTextEvent.count;
        int n;
        int i1;
        switch (paramAnonymousAccessibleTextEvent.type)
        {
        case 5:
          break;
        case 0:
          n = 0;
          if (m > 0)
          {
            while (m-- > 0)
            {
              i1 = localStyledText.getWordNext(j, 2);
              if ((i1 == k) || (i1 == j))
                break;
              j = i1;
              n++;
            }
            i = j;
            j = localStyledText.getWordNext(j, 2);
          }
          else
          {
            while (m++ < 0)
            {
              i1 = localStyledText.getWordPrevious(i, 2);
              if (i1 == i)
                break;
              i = i1;
              n--;
            }
            j = localStyledText.getWordNext(i, 2);
          }
          m = n;
          break;
        case 1:
          n = 0;
          if (m > 0)
          {
            while (m-- > 0)
            {
              i1 = localStyledText.getWordNext(j, 16, true);
              if (i1 == j)
                break;
              n++;
              j = i1;
            }
            i = j;
            j = localStyledText.getWordNext(i, 8, true);
          }
          else
          {
            if (localStyledText.getWordPrevious(Math.min(i + 1, k), 16, true) == i)
              m++;
            while (m <= 0)
            {
              i1 = localStyledText.getWordPrevious(i, 16, true);
              if (i1 == i)
                break;
              m++;
              i = i1;
              if (m != 0)
                n--;
            }
            if ((m <= 0) && (i == 0))
              j = i;
            else
              j = localStyledText.getWordNext(i, 8, true);
          }
          m = n;
          break;
        case 2:
        case 3:
        case 4:
          n = m > 0 ? j : i;
          i1 = localStyledText.getLineAtOffset(n) + m;
          i1 = Math.max(0, Math.min(i1, localStyledText.getLineCount() - 1));
          i = localStyledText.getOffsetAtLine(i1);
          String str = localStyledText.getLine(i1);
          j = i + str.length();
          m = i1 - localStyledText.getLineAtOffset(n);
        }
        paramAnonymousAccessibleTextEvent.start = i;
        paramAnonymousAccessibleTextEvent.end = j;
        paramAnonymousAccessibleTextEvent.count = m;
        paramAnonymousAccessibleTextEvent.result = localStyledText.content.getTextRange(i, j - i);
      }

      public void getVisibleRanges(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        paramAnonymousAccessibleTextEvent.ranges = getRanges(StyledText.this.leftMargin, StyledText.this.topMargin, StyledText.this.clientAreaWidth - StyledText.this.rightMargin, StyledText.this.clientAreaHeight - StyledText.this.bottomMargin);
        if (paramAnonymousAccessibleTextEvent.ranges.length > 0)
        {
          paramAnonymousAccessibleTextEvent.start = paramAnonymousAccessibleTextEvent.ranges[0];
          paramAnonymousAccessibleTextEvent.end = paramAnonymousAccessibleTextEvent.ranges[(paramAnonymousAccessibleTextEvent.ranges.length - 1)];
        }
      }

      public void scrollText(AccessibleTextEvent paramAnonymousAccessibleTextEvent)
      {
        StyledText localStyledText = StyledText.this;
        int i = StyledText.this.getTopPixel();
        int j = localStyledText.getHorizontalPixel();
        Object localObject;
        switch (paramAnonymousAccessibleTextEvent.type)
        {
        case 0:
        case 2:
        case 4:
        case 6:
          localObject = localStyledText.getBoundsAtOffset(paramAnonymousAccessibleTextEvent.start);
          if (paramAnonymousAccessibleTextEvent.type != 2)
            j = j + ((Rectangle)localObject).x - localStyledText.leftMargin;
          if (paramAnonymousAccessibleTextEvent.type != 4)
            i = i + ((Rectangle)localObject).y - localStyledText.topMargin;
          break;
        case 1:
        case 3:
        case 5:
          localObject = localStyledText.getBoundsAtOffset(paramAnonymousAccessibleTextEvent.end - 1);
          if (paramAnonymousAccessibleTextEvent.type != 3)
            j = j - localStyledText.clientAreaWidth + ((Rectangle)localObject).x + ((Rectangle)localObject).width + localStyledText.rightMargin;
          if (paramAnonymousAccessibleTextEvent.type != 5)
            i = i - localStyledText.clientAreaHeight + ((Rectangle)localObject).y + ((Rectangle)localObject).height + localStyledText.bottomMargin;
          break;
        case 7:
          localObject = new Point(paramAnonymousAccessibleTextEvent.x, paramAnonymousAccessibleTextEvent.y);
          Display localDisplay = localStyledText.getDisplay();
          localObject = localDisplay.map(null, localStyledText, (Point)localObject);
          Rectangle localRectangle = localStyledText.getBoundsAtOffset(paramAnonymousAccessibleTextEvent.start);
          i = i - ((Point)localObject).y + localRectangle.y;
          j = j - ((Point)localObject).x + localRectangle.x;
        }
        localStyledText.setTopPixel(i);
        localStyledText.setHorizontalPixel(j);
        paramAnonymousAccessibleTextEvent.result = "OK";
      }
    });
    localAccessible.addAccessibleAttributeListener(new AccessibleAttributeAdapter()
    {
      public void getAttributes(AccessibleAttributeEvent paramAnonymousAccessibleAttributeEvent)
      {
        StyledText localStyledText = StyledText.this;
        paramAnonymousAccessibleAttributeEvent.leftMargin = localStyledText.getLeftMargin();
        paramAnonymousAccessibleAttributeEvent.topMargin = localStyledText.getTopMargin();
        paramAnonymousAccessibleAttributeEvent.rightMargin = localStyledText.getRightMargin();
        paramAnonymousAccessibleAttributeEvent.bottomMargin = localStyledText.getBottomMargin();
        paramAnonymousAccessibleAttributeEvent.tabStops = localStyledText.getTabStops();
        paramAnonymousAccessibleAttributeEvent.justify = localStyledText.getJustify();
        paramAnonymousAccessibleAttributeEvent.alignment = localStyledText.getAlignment();
        paramAnonymousAccessibleAttributeEvent.indent = localStyledText.getIndent();
      }

      public void getTextAttributes(AccessibleTextAttributeEvent paramAnonymousAccessibleTextAttributeEvent)
      {
        StyledText localStyledText = StyledText.this;
        int i = localStyledText.getCharCount();
        if ((!StyledText.this.isListening(3002)) && (localStyledText.renderer.styleCount == 0))
        {
          paramAnonymousAccessibleTextAttributeEvent.start = 0;
          paramAnonymousAccessibleTextAttributeEvent.end = i;
          paramAnonymousAccessibleTextAttributeEvent.textStyle = new TextStyle(localStyledText.getFont(), localStyledText.foreground, localStyledText.background);
          return;
        }
        int j = Math.max(0, Math.min(paramAnonymousAccessibleTextAttributeEvent.offset, i - 1));
        int k = localStyledText.getLineAtOffset(j);
        int m = localStyledText.getOffsetAtLine(k);
        int n = localStyledText.getLineCount();
        j -= m;
        TextLayout localTextLayout = localStyledText.renderer.getTextLayout(k);
        int i1 = localTextLayout.getText().length();
        if (i1 > 0)
          paramAnonymousAccessibleTextAttributeEvent.textStyle = localTextLayout.getStyle(Math.max(0, Math.min(j, i1 - 1)));
        if (paramAnonymousAccessibleTextAttributeEvent.textStyle == null)
        {
          paramAnonymousAccessibleTextAttributeEvent.textStyle = new TextStyle(localStyledText.getFont(), localStyledText.foreground, localStyledText.background);
        }
        else if ((paramAnonymousAccessibleTextAttributeEvent.textStyle.foreground == null) || (paramAnonymousAccessibleTextAttributeEvent.textStyle.background == null) || (paramAnonymousAccessibleTextAttributeEvent.textStyle.font == null))
        {
          localObject = new TextStyle(paramAnonymousAccessibleTextAttributeEvent.textStyle);
          if (((TextStyle)localObject).foreground == null)
            ((TextStyle)localObject).foreground = localStyledText.foreground;
          if (((TextStyle)localObject).background == null)
            ((TextStyle)localObject).background = localStyledText.background;
          if (((TextStyle)localObject).font == null)
            ((TextStyle)localObject).font = localStyledText.getFont();
          paramAnonymousAccessibleTextAttributeEvent.textStyle = ((TextStyle)localObject);
        }
        if (j >= i1)
        {
          paramAnonymousAccessibleTextAttributeEvent.start = (m + i1);
          if (k + 1 < n)
            paramAnonymousAccessibleTextAttributeEvent.end = localStyledText.getOffsetAtLine(k + 1);
          else
            paramAnonymousAccessibleTextAttributeEvent.end = i;
          return;
        }
        Object localObject = localTextLayout.getRanges();
        localStyledText.renderer.disposeTextLayout(localTextLayout);
        int i2 = 0;
        int i5;
        for (int i3 = 0; i2 < localObject.length; i3 = i5 + 1)
        {
          int i4 = localObject[(i2++)];
          i5 = localObject[(i2++)];
          if ((i4 <= j) && (j <= i5))
          {
            paramAnonymousAccessibleTextAttributeEvent.start = (m + i4);
            paramAnonymousAccessibleTextAttributeEvent.end = (m + i5 + 1);
            return;
          }
          if (i4 > j)
          {
            paramAnonymousAccessibleTextAttributeEvent.start = (m + i3);
            paramAnonymousAccessibleTextAttributeEvent.end = (m + i4);
            return;
          }
        }
        if (i2 == localObject.length)
        {
          paramAnonymousAccessibleTextAttributeEvent.start = (m + i3);
          if (k + 1 < n)
            paramAnonymousAccessibleTextAttributeEvent.end = localStyledText.getOffsetAtLine(k + 1);
          else
            paramAnonymousAccessibleTextAttributeEvent.end = i;
        }
      }
    });
    localAccessible.addAccessibleControlListener(new AccessibleControlAdapter()
    {
      public void getRole(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.detail = 42;
      }

      public void getState(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        int i = 0;
        if (StyledText.this.isEnabled())
          i |= 1048576;
        if (StyledText.this.isFocusControl())
          i |= 4;
        if (!StyledText.this.isVisible())
          i |= 32768;
        if (!StyledText.this.getEditable())
          i |= 64;
        if (StyledText.this.isSingleLine())
          i |= 134217728;
        else
          i |= 268435456;
        paramAnonymousAccessibleControlEvent.detail = i;
      }

      public void getValue(AccessibleControlEvent paramAnonymousAccessibleControlEvent)
      {
        paramAnonymousAccessibleControlEvent.result = StyledText.this.getText();
      }
    });
    addListener(15, new Listener()
    {
      private final Accessible val$accessible;

      public void handleEvent(Event paramAnonymousEvent)
      {
        this.val$accessible.setFocus(-1);
      }
    });
  }

  String getAssociatedLabel()
  {
    Control[] arrayOfControl = getParent().getChildren();
    for (int i = 0; i < arrayOfControl.length; i++)
      if (arrayOfControl[i] == this)
      {
        if (i <= 0)
          break;
        Control localControl = arrayOfControl[(i - 1)];
        if ((localControl instanceof Label))
          return ((Label)localControl).getText();
        if (!(localControl instanceof CLabel))
          break;
        return ((CLabel)localControl).getText();
      }
    return null;
  }

  String stripMnemonic(String paramString)
  {
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return paramString;
      if (paramString.charAt(i) != '&')
        return paramString.substring(0, i - 1) + paramString.substring(i, j);
      i++;
    }
    while (i < j);
    return paramString;
  }

  char _findMnemonic(String paramString)
  {
    if (paramString == null)
      return '\000';
    int i = 0;
    int j = paramString.length();
    do
    {
      while ((i < j) && (paramString.charAt(i) != '&'))
        i++;
      i++;
      if (i >= j)
        return '\000';
      if (paramString.charAt(i) != '&')
        return Character.toLowerCase(paramString.charAt(i));
      i++;
    }
    while (i < j);
    return '\000';
  }

  public void invokeAction(int paramInt)
  {
    checkWidget();
    if ((this.blockSelection) && (invokeBlockAction(paramInt)))
      return;
    this.updateCaretDirection = true;
    switch (paramInt)
    {
    case 16777217:
      doLineUp(false);
      clearSelection(true);
      break;
    case 16777218:
      doLineDown(false);
      clearSelection(true);
      break;
    case 16777223:
      doLineStart();
      clearSelection(true);
      break;
    case 16777224:
      doLineEnd();
      clearSelection(true);
      break;
    case 16777219:
      doCursorPrevious();
      clearSelection(true);
      break;
    case 16777220:
      doCursorNext();
      clearSelection(true);
      break;
    case 16777221:
      doPageUp(false, -1);
      clearSelection(true);
      break;
    case 16777222:
      doPageDown(false, -1);
      clearSelection(true);
      break;
    case 17039363:
      doWordPrevious();
      clearSelection(true);
      break;
    case 17039364:
      doWordNext();
      clearSelection(true);
      break;
    case 17039367:
      doContentStart();
      clearSelection(true);
      break;
    case 17039368:
      doContentEnd();
      clearSelection(true);
      break;
    case 17039365:
      doPageStart();
      clearSelection(true);
      break;
    case 17039366:
      doPageEnd();
      clearSelection(true);
      break;
    case 16908289:
      doSelectionLineUp();
      break;
    case 262209:
      selectAll();
      break;
    case 16908290:
      doSelectionLineDown();
      break;
    case 16908295:
      doLineStart();
      doSelection(16777219);
      break;
    case 16908296:
      doLineEnd();
      doSelection(16777220);
      break;
    case 16908291:
      doSelectionCursorPrevious();
      doSelection(16777219);
      break;
    case 16908292:
      doSelectionCursorNext();
      doSelection(16777220);
      break;
    case 16908293:
      doSelectionPageUp(-1);
      break;
    case 16908294:
      doSelectionPageDown(-1);
      break;
    case 17170435:
      doSelectionWordPrevious();
      doSelection(16777219);
      break;
    case 17170436:
      doSelectionWordNext();
      doSelection(16777220);
      break;
    case 17170439:
      doContentStart();
      doSelection(16777219);
      break;
    case 17170440:
      doContentEnd();
      doSelection(16777220);
      break;
    case 17170437:
      doPageStart();
      doSelection(16777219);
      break;
    case 17170438:
      doPageEnd();
      doSelection(16777220);
      break;
    case 131199:
      cut();
      break;
    case 17039369:
      copy();
      break;
    case 16908297:
      paste();
      break;
    case 8:
      doBackspace();
      break;
    case 127:
      doDelete();
      break;
    case 262152:
      doDeleteWordPrevious();
      break;
    case 262271:
      doDeleteWordNext();
      break;
    case 16777225:
      this.overwrite = (!this.overwrite);
      break;
    case 16777226:
      setBlockSelection(!this.blockSelection);
    }
  }

  boolean invokeBlockAction(int paramInt)
  {
    switch (paramInt)
    {
    case 16777217:
    case 16777218:
    case 16777219:
    case 16777220:
    case 16777221:
    case 16777222:
    case 16777223:
    case 16777224:
    case 17039363:
    case 17039364:
    case 17039365:
    case 17039366:
    case 17039367:
    case 17039368:
      clearBlockSelection(false, false);
      return false;
    case 16908289:
      doBlockLineVertical(true);
      return true;
    case 16908290:
      doBlockLineVertical(false);
      return true;
    case 16908295:
      doBlockLineHorizontal(false);
      return true;
    case 16908296:
      doBlockLineHorizontal(true);
      return false;
    case 16908291:
      doBlockColumn(false);
      return true;
    case 16908292:
      doBlockColumn(true);
      return true;
    case 17170435:
      doBlockWord(false);
      return true;
    case 17170436:
      doBlockWord(true);
      return true;
    case 262209:
      return false;
    case 16908293:
    case 16908294:
    case 17170437:
    case 17170438:
    case 17170439:
    case 17170440:
      return true;
    case 131199:
    case 16908297:
    case 17039369:
      return false;
    case 8:
    case 127:
      if (this.blockXLocation != -1)
      {
        insertBlockSelectionText('\000', paramInt);
        return true;
      }
      return false;
    case 262152:
    case 262271:
      return this.blockXLocation != -1;
    }
    return false;
  }

  boolean isBidi()
  {
    return (IS_GTK) || (IS_MAC) || (BidiUtil.isBidiPlatform()) || (this.isMirrored);
  }

  boolean isBidiCaret()
  {
    return BidiUtil.isBidiPlatform();
  }

  boolean isFixedLineHeight()
  {
    return this.fixedLineHeight;
  }

  boolean isLineDelimiter(int paramInt)
  {
    int i = this.content.getLineAtOffset(paramInt);
    int j = this.content.getOffsetAtLine(i);
    int k = paramInt - j;
    return k > this.content.getLine(i).length();
  }

  boolean isMirrored()
  {
    return this.isMirrored;
  }

  boolean isSingleLine()
  {
    return (getStyle() & 0x4) != 0;
  }

  void modifyContent(Event paramEvent, boolean paramBoolean)
  {
    paramEvent.doit = true;
    notifyListeners(25, paramEvent);
    if (paramEvent.doit)
    {
      StyledTextEvent localStyledTextEvent = null;
      int i = paramEvent.end - paramEvent.start;
      if (isListening(3000))
      {
        localStyledTextEvent = new StyledTextEvent(this.content);
        localStyledTextEvent.start = paramEvent.start;
        localStyledTextEvent.end = (paramEvent.start + paramEvent.text.length());
        localStyledTextEvent.text = this.content.getTextRange(paramEvent.start, i);
      }
      if ((paramBoolean) && (paramEvent.text.length() == 0))
      {
        int j = this.content.getLineAtOffset(paramEvent.start);
        int k = this.content.getOffsetAtLine(j);
        TextLayout localTextLayout = this.renderer.getTextLayout(j);
        int m = localTextLayout.getLevel(paramEvent.start - k);
        int n = this.content.getLineAtOffset(paramEvent.end);
        if (j != n)
        {
          this.renderer.disposeTextLayout(localTextLayout);
          k = this.content.getOffsetAtLine(n);
          localTextLayout = this.renderer.getTextLayout(n);
        }
        int i1 = localTextLayout.getLevel(paramEvent.end - k);
        this.renderer.disposeTextLayout(localTextLayout);
        if (m != i1)
          this.caretAlignment = 0;
        else
          this.caretAlignment = 1;
      }
      this.content.replaceTextRange(paramEvent.start, i, paramEvent.text);
      if ((paramBoolean) && ((!this.blockSelection) || (this.blockXLocation == -1)))
      {
        setSelection(paramEvent.start + paramEvent.text.length(), 0, true, false);
        showCaret();
      }
      notifyListeners(24, paramEvent);
      if (isListening(3000))
        notifyListeners(3000, localStyledTextEvent);
    }
  }

  void paintObject(GC paramGC, int paramInt1, int paramInt2, int paramInt3, int paramInt4, StyleRange paramStyleRange, Bullet paramBullet, int paramInt5)
  {
    if (isListening(3008))
    {
      StyledTextEvent localStyledTextEvent = new StyledTextEvent(this.content);
      localStyledTextEvent.gc = paramGC;
      localStyledTextEvent.x = paramInt1;
      localStyledTextEvent.y = paramInt2;
      localStyledTextEvent.ascent = paramInt3;
      localStyledTextEvent.descent = paramInt4;
      localStyledTextEvent.style = paramStyleRange;
      localStyledTextEvent.bullet = paramBullet;
      localStyledTextEvent.bulletIndex = paramInt5;
      notifyListeners(3008, localStyledTextEvent);
    }
  }

  public void paste()
  {
    checkWidget();
    String str = (String)getClipboardContent(1);
    if ((str != null) && (str.length() > 0))
    {
      if (this.blockSelection)
      {
        boolean bool = (isFixedLineHeight()) && (this.renderer.fixedPitch);
        int i = insertBlockSelectionText(str, bool);
        setCaretOffset(i, -1);
        clearBlockSelection(true, true);
        setCaretLocation();
        return;
      }
      Event localEvent = new Event();
      localEvent.start = this.selection.x;
      localEvent.end = this.selection.y;
      localEvent.text = getModelDelimitedText(str);
      sendKeyEvent(localEvent);
    }
  }

  public void print()
  {
    checkWidget();
    Printer localPrinter = new Printer();
    StyledTextPrintOptions localStyledTextPrintOptions = new StyledTextPrintOptions();
    localStyledTextPrintOptions.printTextForeground = true;
    localStyledTextPrintOptions.printTextBackground = true;
    localStyledTextPrintOptions.printTextFontStyle = true;
    localStyledTextPrintOptions.printLineBackground = true;
    new Printing(this, localPrinter, localStyledTextPrintOptions).run();
    localPrinter.dispose();
  }

  public Runnable print(Printer paramPrinter)
  {
    checkWidget();
    if (paramPrinter == null)
      SWT.error(4);
    StyledTextPrintOptions localStyledTextPrintOptions = new StyledTextPrintOptions();
    localStyledTextPrintOptions.printTextForeground = true;
    localStyledTextPrintOptions.printTextBackground = true;
    localStyledTextPrintOptions.printTextFontStyle = true;
    localStyledTextPrintOptions.printLineBackground = true;
    return print(paramPrinter, localStyledTextPrintOptions);
  }

  public Runnable print(Printer paramPrinter, StyledTextPrintOptions paramStyledTextPrintOptions)
  {
    checkWidget();
    if ((paramPrinter == null) || (paramStyledTextPrintOptions == null))
      SWT.error(4);
    return new Printing(this, paramPrinter, paramStyledTextPrintOptions);
  }

  public void redraw()
  {
    super.redraw();
    int i = getPartialBottomIndex() - this.topIndex + 1;
    this.renderer.reset(this.topIndex, i);
    this.renderer.calculate(this.topIndex, i);
    setScrollBars(false);
    doMouseLinkCursor();
  }

  public void redraw(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    super.redraw(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean);
    if (paramInt4 > 0)
    {
      int i = getLineIndex(paramInt2);
      int j = getLineIndex(paramInt2 + paramInt4);
      resetCache(i, j - i + 1);
      doMouseLinkCursor();
    }
  }

  void redrawLines(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = paramInt1 + paramInt2 - 1;
    int j = getPartialBottomIndex();
    int k = getPartialTopIndex();
    if ((paramInt1 > j) || (i < k))
      return;
    if (paramInt1 < k)
      paramInt1 = k;
    if (i > j)
      i = j;
    int m = getLinePixel(paramInt1);
    int n = getLinePixel(i + 1);
    if (paramBoolean)
      n = this.clientAreaHeight - this.bottomMargin;
    int i1 = this.clientAreaWidth - this.leftMargin - this.rightMargin;
    super.redraw(this.leftMargin, m, i1, n - m, true);
  }

  void redrawLinesBullet(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt == null)
      return;
    int i = getPartialTopIndex();
    int j = getPartialBottomIndex();
    for (int k = 0; k < paramArrayOfInt.length; k++)
    {
      int m = paramArrayOfInt[k];
      if ((i <= m) && (m <= j))
      {
        int n = -1;
        Bullet localBullet = this.renderer.getLineBullet(m, null);
        if (localBullet != null)
        {
          StyleRange localStyleRange = localBullet.style;
          GlyphMetrics localGlyphMetrics = localStyleRange.metrics;
          n = localGlyphMetrics.width;
        }
        if (n == -1)
          n = getClientArea().width;
        int i1 = this.renderer.getLineHeight(m);
        int i2 = getLinePixel(m);
        super.redraw(0, i2, n, i1, false);
      }
    }
  }

  public void redrawRange(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    int i = paramInt1 + paramInt2;
    int j = this.content.getCharCount();
    if ((paramInt1 > i) || (paramInt1 < 0) || (i > j))
      SWT.error(6);
    int k = this.content.getLineAtOffset(paramInt1);
    int m = this.content.getLineAtOffset(i);
    resetCache(k, m - k + 1);
    internalRedrawRange(paramInt1, paramInt2);
    doMouseLinkCursor();
  }

  public void removeBidiSegmentListener(BidiSegmentListener paramBidiSegmentListener)
  {
    checkWidget();
    if (paramBidiSegmentListener == null)
      SWT.error(4);
    removeListener(3007, paramBidiSegmentListener);
  }

  public void removeCaretListener(CaretListener paramCaretListener)
  {
    checkWidget();
    if (paramCaretListener == null)
      SWT.error(4);
    removeListener(3011, paramCaretListener);
  }

  public void removeExtendedModifyListener(ExtendedModifyListener paramExtendedModifyListener)
  {
    checkWidget();
    if (paramExtendedModifyListener == null)
      SWT.error(4);
    removeListener(3000, paramExtendedModifyListener);
  }

  public void removeLineBackgroundListener(LineBackgroundListener paramLineBackgroundListener)
  {
    checkWidget();
    if (paramLineBackgroundListener == null)
      SWT.error(4);
    removeListener(3001, paramLineBackgroundListener);
  }

  public void removeLineStyleListener(LineStyleListener paramLineStyleListener)
  {
    checkWidget();
    if (paramLineStyleListener == null)
      SWT.error(4);
    removeListener(3002, paramLineStyleListener);
    setCaretLocation();
  }

  public void removeModifyListener(ModifyListener paramModifyListener)
  {
    checkWidget();
    if (paramModifyListener == null)
      SWT.error(4);
    removeListener(24, paramModifyListener);
  }

  public void removePaintObjectListener(PaintObjectListener paramPaintObjectListener)
  {
    checkWidget();
    if (paramPaintObjectListener == null)
      SWT.error(4);
    removeListener(3008, paramPaintObjectListener);
  }

  public void removeSelectionListener(SelectionListener paramSelectionListener)
  {
    checkWidget();
    if (paramSelectionListener == null)
      SWT.error(4);
    removeListener(13, paramSelectionListener);
  }

  public void removeVerifyListener(VerifyListener paramVerifyListener)
  {
    checkWidget();
    if (paramVerifyListener == null)
      SWT.error(4);
    removeListener(25, paramVerifyListener);
  }

  public void removeVerifyKeyListener(VerifyKeyListener paramVerifyKeyListener)
  {
    if (paramVerifyKeyListener == null)
      SWT.error(4);
    removeListener(3005, paramVerifyKeyListener);
  }

  public void removeWordMovementListener(MovementListener paramMovementListener)
  {
    checkWidget();
    if (paramMovementListener == null)
      SWT.error(4);
    removeListener(3009, paramMovementListener);
    removeListener(3010, paramMovementListener);
  }

  public void replaceStyleRanges(int paramInt1, int paramInt2, StyleRange[] paramArrayOfStyleRange)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if (paramArrayOfStyleRange == null)
      SWT.error(4);
    setStyleRanges(paramInt1, paramInt2, null, paramArrayOfStyleRange, false);
  }

  public void replaceTextRange(int paramInt1, int paramInt2, String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    int i = getCharCount();
    int j = paramInt1 + paramInt2;
    if ((paramInt1 > j) || (paramInt1 < 0) || (j > i))
      SWT.error(6);
    Event localEvent = new Event();
    localEvent.start = paramInt1;
    localEvent.end = j;
    localEvent.text = paramString;
    modifyContent(localEvent, false);
  }

  void reset()
  {
    ScrollBar localScrollBar1 = getVerticalBar();
    ScrollBar localScrollBar2 = getHorizontalBar();
    setCaretOffset(0, -1);
    this.topIndex = 0;
    this.topIndexY = 0;
    this.verticalScrollOffset = 0;
    this.horizontalScrollOffset = 0;
    resetSelection();
    this.renderer.setContent(this.content);
    if (localScrollBar1 != null)
      localScrollBar1.setSelection(0);
    if (localScrollBar2 != null)
      localScrollBar2.setSelection(0);
    resetCache(0, 0);
    setCaretLocation();
    super.redraw();
  }

  void resetCache(int paramInt1, int paramInt2)
  {
    int i = this.renderer.maxWidthLineIndex;
    this.renderer.reset(paramInt1, paramInt2);
    this.renderer.calculateClientArea();
    if ((i >= 0) && (i < this.content.getLineCount()))
      this.renderer.calculate(i, 1);
    setScrollBars(true);
    if (!isFixedLineHeight())
    {
      if (this.topIndex > paramInt1)
        this.verticalScrollOffset = -1;
      this.renderer.calculateIdle();
    }
  }

  void resetSelection()
  {
    this.selection.x = (this.selection.y = this.caretOffset);
    this.selectionAnchor = -1;
    sendAccessibleTextCaretMoved();
  }

  public void scroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
  {
    super.scroll(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, false);
    if (paramBoolean)
    {
      int i = paramInt1 - paramInt3;
      int j = paramInt2 - paramInt4;
      Control[] arrayOfControl = getChildren();
      for (int k = 0; k < arrayOfControl.length; k++)
      {
        Control localControl = arrayOfControl[k];
        Rectangle localRectangle = localControl.getBounds();
        localControl.setLocation(localRectangle.x + i, localRectangle.y + j);
      }
    }
  }

  boolean scrollHorizontal(int paramInt, boolean paramBoolean)
  {
    if (paramInt == 0)
      return false;
    if (this.wordWrap)
      return false;
    ScrollBar localScrollBar = getHorizontalBar();
    if ((localScrollBar != null) && (paramBoolean))
      localScrollBar.setSelection(this.horizontalScrollOffset + paramInt);
    int i = this.clientAreaHeight - this.topMargin - this.bottomMargin;
    int j;
    int k;
    if (paramInt > 0)
    {
      j = this.leftMargin + paramInt;
      k = this.clientAreaWidth - j - this.rightMargin;
      if (k > 0)
        scroll(this.leftMargin, this.topMargin, j, this.topMargin, k, i, true);
      if (j > k)
        super.redraw(this.leftMargin + k, this.topMargin, paramInt - k, i, true);
    }
    else
    {
      j = this.leftMargin - paramInt;
      k = this.clientAreaWidth - j - this.rightMargin;
      if (k > 0)
        scroll(j, this.topMargin, this.leftMargin, this.topMargin, k, i, true);
      if (j > k)
        super.redraw(this.leftMargin + k, this.topMargin, -paramInt - k, i, true);
    }
    this.horizontalScrollOffset += paramInt;
    setCaretLocation();
    return true;
  }

  boolean scrollVertical(int paramInt, boolean paramBoolean)
  {
    if (paramInt == 0)
      return false;
    if (this.verticalScrollOffset != -1)
    {
      ScrollBar localScrollBar = getVerticalBar();
      if ((localScrollBar != null) && (paramBoolean))
        localScrollBar.setSelection(this.verticalScrollOffset + paramInt);
      int i = this.clientAreaWidth - this.leftMargin - this.rightMargin;
      int j;
      int k;
      int m;
      int n;
      if (paramInt > 0)
      {
        j = this.topMargin + paramInt;
        k = this.clientAreaHeight - j - this.bottomMargin;
        if (k > 0)
          scroll(this.leftMargin, this.topMargin, this.leftMargin, j, i, k, true);
        if (j > k)
        {
          m = Math.max(0, this.topMargin + k);
          n = Math.min(this.clientAreaHeight, paramInt - k);
          super.redraw(this.leftMargin, m, i, n, true);
        }
      }
      else
      {
        j = this.topMargin - paramInt;
        k = this.clientAreaHeight - j - this.bottomMargin;
        if (k > 0)
          scroll(this.leftMargin, j, this.leftMargin, this.topMargin, i, k, true);
        if (j > k)
        {
          m = Math.max(0, this.topMargin + k);
          n = Math.min(this.clientAreaHeight, -paramInt - k);
          super.redraw(this.leftMargin, m, i, n, true);
        }
      }
      this.verticalScrollOffset += paramInt;
      calculateTopIndex(paramInt);
    }
    else
    {
      calculateTopIndex(paramInt);
      super.redraw();
    }
    setCaretLocation();
    return true;
  }

  void scrollText(int paramInt1, int paramInt2)
  {
    if (paramInt1 == paramInt2)
      return;
    int i = paramInt2 - paramInt1;
    int j = this.clientAreaWidth - this.leftMargin - this.rightMargin;
    int k;
    if (i > 0)
      k = this.clientAreaHeight - paramInt1 - this.bottomMargin;
    else
      k = this.clientAreaHeight - paramInt2 - this.bottomMargin;
    scroll(this.leftMargin, paramInt2, this.leftMargin, paramInt1, j, k, true);
    if ((paramInt1 + k > 0) && (this.topMargin > paramInt1))
      super.redraw(this.leftMargin, i, j, this.topMargin, false);
    if ((paramInt2 + k > 0) && (this.topMargin > paramInt2))
      super.redraw(this.leftMargin, 0, j, this.topMargin, false);
    if ((this.clientAreaHeight - this.bottomMargin < paramInt1 + k) && (this.clientAreaHeight > paramInt1))
      super.redraw(this.leftMargin, this.clientAreaHeight - this.bottomMargin + i, j, this.bottomMargin, false);
    if ((this.clientAreaHeight - this.bottomMargin < paramInt2 + k) && (this.clientAreaHeight > paramInt2))
      super.redraw(this.leftMargin, this.clientAreaHeight - this.bottomMargin, j, this.bottomMargin, false);
  }

  void sendAccessibleTextCaretMoved()
  {
    if (this.caretOffset != this.accCaretOffset)
    {
      this.accCaretOffset = this.caretOffset;
      getAccessible().textCaretMoved(this.caretOffset);
    }
  }

  void sendAccessibleTextChanged(int paramInt1, int paramInt2, int paramInt3)
  {
    Accessible localAccessible = getAccessible();
    if (paramInt3 != 0)
      localAccessible.textChanged(1, paramInt1, paramInt3);
    if (paramInt2 != 0)
      localAccessible.textChanged(0, paramInt1, paramInt2);
  }

  public void selectAll()
  {
    checkWidget();
    if (this.blockSelection)
    {
      this.renderer.calculate(0, this.content.getLineCount());
      setScrollBars(false);
      int i = getVerticalScrollOffset();
      int j = this.leftMargin - this.horizontalScrollOffset;
      int k = this.topMargin - i;
      int m = this.renderer.getWidth() - this.rightMargin - this.horizontalScrollOffset;
      int n = this.renderer.getHeight() - this.bottomMargin - i;
      setBlockSelectionLocation(j, k, m, n, false);
      return;
    }
    setSelection(0, Math.max(getCharCount(), 0));
  }

  void sendKeyEvent(Event paramEvent)
  {
    if (this.editable)
      modifyContent(paramEvent, true);
  }

  StyledTextEvent sendLineEvent(int paramInt1, int paramInt2, String paramString)
  {
    StyledTextEvent localStyledTextEvent = null;
    if (isListening(paramInt1))
    {
      localStyledTextEvent = new StyledTextEvent(this.content);
      localStyledTextEvent.detail = paramInt2;
      localStyledTextEvent.text = paramString;
      localStyledTextEvent.alignment = this.alignment;
      localStyledTextEvent.indent = this.indent;
      localStyledTextEvent.wrapIndent = this.wrapIndent;
      localStyledTextEvent.justify = this.justify;
      notifyListeners(paramInt1, localStyledTextEvent);
    }
    return localStyledTextEvent;
  }

  void sendSelectionEvent()
  {
    getAccessible().textSelectionChanged();
    Event localEvent = new Event();
    localEvent.x = this.selection.x;
    localEvent.y = this.selection.y;
    notifyListeners(13, localEvent);
  }

  int sendTextEvent(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean)
  {
    int i = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    int[] arrayOfInt3;
    int[] arrayOfInt1;
    int m;
    int n;
    int[] arrayOfInt2;
    if (paramInt3 < this.content.getLineCount())
    {
      arrayOfInt3 = new int[1];
      arrayOfInt1 = getOffsetAtPoint(paramInt1, getLinePixel(paramInt3), arrayOfInt3, true);
      if (arrayOfInt1 == -1)
      {
        m = this.content.getOffsetAtLine(paramInt3);
        n = this.content.getLine(paramInt3).length();
        arrayOfInt1 = arrayOfInt2 = m + n;
        if (paramBoolean)
        {
          TextLayout localTextLayout = this.renderer.getTextLayout(paramInt3);
          i = localTextLayout.getBounds().width;
          this.renderer.disposeTextLayout(localTextLayout);
        }
      }
      else
      {
        arrayOfInt1 += arrayOfInt3[0];
        arrayOfInt2 = paramInt1 == paramInt2 ? arrayOfInt1 : getOffsetAtPoint(paramInt2, 0, paramInt3, null);
        paramBoolean = false;
      }
    }
    else
    {
      arrayOfInt1 = arrayOfInt2 = this.content.getCharCount();
      localStringBuffer.append(this.content.getLineDelimiter());
    }
    int j;
    if (arrayOfInt1 > arrayOfInt2)
    {
      arrayOfInt3 = arrayOfInt1;
      arrayOfInt1 = arrayOfInt2;
      j = arrayOfInt3;
    }
    if (paramBoolean)
    {
      int k = paramInt1 - i + this.horizontalScrollOffset - this.leftMargin;
      m = k / this.renderer.averageCharWidth;
      for (n = 0; n < m; n++)
        localStringBuffer.append(' ');
    }
    localStringBuffer.append(paramString);
    Event localEvent = new Event();
    localEvent.start = arrayOfInt1;
    localEvent.end = j;
    localEvent.text = localStringBuffer.toString();
    sendKeyEvent(localEvent);
    return localEvent.start + localEvent.text.length();
  }

  int sendWordBoundaryEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, int paramInt5)
  {
    if (isListening(paramInt1))
    {
      StyledTextEvent localStyledTextEvent = new StyledTextEvent(this.content);
      localStyledTextEvent.detail = paramInt5;
      localStyledTextEvent.text = paramString;
      localStyledTextEvent.count = paramInt2;
      localStyledTextEvent.start = paramInt3;
      localStyledTextEvent.end = paramInt4;
      notifyListeners(paramInt1, localStyledTextEvent);
      paramInt3 = localStyledTextEvent.end;
      if (paramInt3 != paramInt4)
      {
        int i = getCharCount();
        if (paramInt3 < 0)
          paramInt3 = 0;
        else if (paramInt3 > i)
          paramInt3 = i;
        else if (isLineDelimiter(paramInt3))
          SWT.error(5);
      }
      return paramInt3;
    }
    return paramInt4;
  }

  void setAlignment()
  {
    if ((getStyle() & 0x4) == 0)
      return;
    int i = this.renderer.getLineAlignment(0, this.alignment);
    int j = 0;
    if (i != 16384)
    {
      this.renderer.calculate(0, 1);
      int k = this.renderer.getWidth() - this.alignmentMargin;
      j = this.clientAreaWidth - k;
      if (j < 0)
        j = 0;
      if (i == 16777216)
        j /= 2;
    }
    if (this.alignmentMargin != j)
    {
      this.leftMargin -= this.alignmentMargin;
      this.leftMargin += j;
      this.alignmentMargin = j;
      resetCache(0, 1);
      setCaretLocation();
      super.redraw();
    }
  }

  public void setAlignment(int paramInt)
  {
    checkWidget();
    paramInt &= 16924672;
    if ((paramInt == 0) || (this.alignment == paramInt))
      return;
    this.alignment = paramInt;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    setAlignment();
    super.redraw();
  }

  public void setBackground(Color paramColor)
  {
    checkWidget();
    this.background = paramColor;
    super.setBackground(paramColor);
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setBlockSelection(boolean paramBoolean)
  {
    checkWidget();
    if ((getStyle() & 0x4) != 0)
      return;
    if (paramBoolean == this.blockSelection)
      return;
    if (this.wordWrap)
      return;
    this.blockSelection = paramBoolean;
    int j;
    if (this.cursor == null)
    {
      Display localDisplay = getDisplay();
      j = paramBoolean ? 2 : 19;
      super.setCursor(localDisplay.getSystemCursor(j));
    }
    if (paramBoolean)
    {
      int i = this.selection.x;
      j = this.selection.y;
      if (i != j)
        setBlockSelectionOffset(i, j, false);
    }
    else
    {
      clearBlockSelection(false, false);
    }
  }

  public void setBlockSelectionBounds(Rectangle paramRectangle)
  {
    checkWidget();
    if (paramRectangle == null)
      SWT.error(4);
    setBlockSelectionBounds(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height);
  }

  public void setBlockSelectionBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    int i = getVerticalScrollOffset();
    if (!this.blockSelection)
    {
      paramInt1 -= this.horizontalScrollOffset;
      paramInt2 -= i;
      j = getOffsetAtPoint(paramInt1, paramInt2, null);
      k = getOffsetAtPoint(paramInt1 + paramInt3 - 1, paramInt2 + paramInt4 - 1, null);
      setSelection(j, k - j, false, false);
      setCaretLocation();
      return;
    }
    int j = this.topMargin;
    int k = this.leftMargin;
    int m = this.renderer.getHeight() - this.bottomMargin;
    int n = Math.max(this.clientAreaWidth, this.renderer.getWidth()) - this.rightMargin;
    int i1 = Math.max(k, Math.min(n, paramInt1)) - this.horizontalScrollOffset;
    int i2 = Math.max(j, Math.min(m, paramInt2)) - i;
    int i3 = Math.max(k, Math.min(n, paramInt1 + paramInt3)) - this.horizontalScrollOffset;
    int i4 = Math.max(j, Math.min(m, paramInt2 + paramInt4 - 1)) - i;
    if ((isFixedLineHeight()) && (this.renderer.fixedPitch))
    {
      int i5 = this.renderer.averageCharWidth;
      i1 = (i1 - this.leftMargin + this.horizontalScrollOffset) / i5 * i5 + this.leftMargin - this.horizontalScrollOffset;
      i3 = (i3 + i5 / 2 - this.leftMargin + this.horizontalScrollOffset) / i5 * i5 + this.leftMargin - this.horizontalScrollOffset;
    }
    setBlockSelectionLocation(i1, i2, i3, i4, false);
  }

  void setBlockSelectionLocation(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = getVerticalScrollOffset();
    this.blockXLocation = (paramInt1 + this.horizontalScrollOffset);
    this.blockYLocation = (paramInt2 + i);
    int[] arrayOfInt = new int[1];
    int j = getOffsetAtPoint(paramInt1, paramInt2, arrayOfInt);
    setCaretOffset(j, arrayOfInt[0]);
    if (this.blockXAnchor == -1)
    {
      this.blockXAnchor = this.blockXLocation;
      this.blockYAnchor = this.blockYLocation;
      this.selectionAnchor = this.caretOffset;
    }
    doBlockSelection(paramBoolean);
  }

  void setBlockSelectionLocation(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int i = getVerticalScrollOffset();
    this.blockXAnchor = (paramInt1 + this.horizontalScrollOffset);
    this.blockYAnchor = (paramInt2 + i);
    this.selectionAnchor = getOffsetAtPoint(paramInt1, paramInt2, null);
    setBlockSelectionLocation(paramInt3, paramInt4, paramBoolean);
  }

  void setBlockSelectionOffset(int paramInt, boolean paramBoolean)
  {
    Point localPoint = getPointAtOffset(paramInt);
    int i = getVerticalScrollOffset();
    this.blockXLocation = (localPoint.x + this.horizontalScrollOffset);
    this.blockYLocation = (localPoint.y + i);
    setCaretOffset(paramInt, -1);
    if (this.blockXAnchor == -1)
    {
      this.blockXAnchor = this.blockXLocation;
      this.blockYAnchor = this.blockYLocation;
      this.selectionAnchor = this.caretOffset;
    }
    doBlockSelection(paramBoolean);
  }

  void setBlockSelectionOffset(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    int i = getVerticalScrollOffset();
    Point localPoint = getPointAtOffset(paramInt1);
    this.blockXAnchor = (localPoint.x + this.horizontalScrollOffset);
    this.blockYAnchor = (localPoint.y + i);
    this.selectionAnchor = paramInt1;
    setBlockSelectionOffset(paramInt2, paramBoolean);
  }

  public void setCaret(Caret paramCaret)
  {
    checkWidget();
    super.setCaret(paramCaret);
    this.caretDirection = 0;
    if (paramCaret != null)
      setCaretLocation();
  }

  /** @deprecated */
  public void setBidiColoring(boolean paramBoolean)
  {
    checkWidget();
    this.bidiColoring = paramBoolean;
  }

  public void setBottomMargin(int paramInt)
  {
    checkWidget();
    setMargins(this.leftMargin, this.topMargin, this.rightMargin, paramInt);
  }

  void setCaretLocation()
  {
    Point localPoint = getPointAtOffset(this.caretOffset);
    setCaretLocation(localPoint, getCaretDirection());
  }

  void setCaretLocation(Point paramPoint, int paramInt)
  {
    Caret localCaret = getCaret();
    if (localCaret != null)
    {
      int i = localCaret == this.defaultCaret ? 1 : 0;
      int j = this.renderer.getLineHeight();
      int k = j;
      if ((!isFixedLineHeight()) && (i != 0))
      {
        k = getBoundsAtOffset(this.caretOffset).height;
        if (k != j)
          paramInt = -1;
      }
      int m = paramInt;
      if (isMirrored())
        if (m == 16384)
          m = 131072;
        else if (m == 131072)
          m = 16384;
      if ((i != 0) && (m == 131072))
        paramPoint.x -= localCaret.getSize().x - 1;
      if (i != 0)
        localCaret.setBounds(paramPoint.x, paramPoint.y, this.caretWidth, k);
      else
        localCaret.setLocation(paramPoint);
      if (paramInt != this.caretDirection)
      {
        this.caretDirection = paramInt;
        if (i != 0)
          if (m == -1)
            this.defaultCaret.setImage(null);
          else if (m == 16384)
            this.defaultCaret.setImage(this.leftCaretBitmap);
          else if (m == 131072)
            this.defaultCaret.setImage(this.rightCaretBitmap);
        if (this.caretDirection == 16384)
          BidiUtil.setKeyboardLanguage(0);
        else if (this.caretDirection == 131072)
          BidiUtil.setKeyboardLanguage(1);
      }
      updateCaretVisibility();
    }
    this.columnX = paramPoint.x;
  }

  public void setCaretOffset(int paramInt)
  {
    checkWidget();
    int i = getCharCount();
    if ((i > 0) && (paramInt != this.caretOffset))
    {
      if (paramInt < 0)
        paramInt = 0;
      else if (paramInt > i)
        paramInt = i;
      else if (isLineDelimiter(paramInt))
        SWT.error(5);
      setCaretOffset(paramInt, 0);
      if (this.blockSelection)
        clearBlockSelection(true, false);
      else
        clearSelection(false);
    }
    setCaretLocation();
  }

  void setCaretOffset(int paramInt1, int paramInt2)
  {
    if (this.caretOffset != paramInt1)
    {
      this.caretOffset = paramInt1;
      if (isListening(3011))
      {
        StyledTextEvent localStyledTextEvent = new StyledTextEvent(this.content);
        localStyledTextEvent.end = this.caretOffset;
        notifyListeners(3011, localStyledTextEvent);
      }
    }
    if (paramInt2 != -1)
      this.caretAlignment = paramInt2;
  }

  void setClipboardContent(int paramInt1, int paramInt2, int paramInt3)
    throws SWTError
  {
    if ((paramInt3 == 2) && (!IS_MOTIF) && (!IS_GTK))
      return;
    TextTransfer localTextTransfer = TextTransfer.getInstance();
    TextWriter localTextWriter = new TextWriter(paramInt1, paramInt2);
    String str1 = getPlatformDelimitedText(localTextWriter);
    Object[] arrayOfObject;
    Transfer[] arrayOfTransfer;
    if (paramInt3 == 2)
    {
      arrayOfObject = new Object[] { str1 };
      arrayOfTransfer = new Transfer[] { localTextTransfer };
    }
    else
    {
      RTFTransfer localRTFTransfer = RTFTransfer.getInstance();
      RTFWriter localRTFWriter = new RTFWriter(paramInt1, paramInt2);
      String str2 = getPlatformDelimitedText(localRTFWriter);
      arrayOfObject = new Object[] { str2, str1 };
      arrayOfTransfer = new Transfer[] { localRTFTransfer, localTextTransfer };
    }
    this.clipboard.setContents(arrayOfObject, arrayOfTransfer, paramInt3);
  }

  public void setContent(StyledTextContent paramStyledTextContent)
  {
    checkWidget();
    if (paramStyledTextContent == null)
      SWT.error(4);
    if (this.content != null)
      this.content.removeTextChangeListener(this.textChangeListener);
    this.content = paramStyledTextContent;
    this.content.addTextChangeListener(this.textChangeListener);
    reset();
  }

  public void setCursor(Cursor paramCursor)
  {
    checkWidget();
    if ((paramCursor != null) && (paramCursor.isDisposed()))
      SWT.error(5);
    this.cursor = paramCursor;
    if (paramCursor == null)
    {
      Display localDisplay = getDisplay();
      int i = this.blockSelection ? 2 : 19;
      super.setCursor(localDisplay.getSystemCursor(i));
    }
    else
    {
      super.setCursor(paramCursor);
    }
  }

  public void setDoubleClickEnabled(boolean paramBoolean)
  {
    checkWidget();
    this.doubleClickEnabled = paramBoolean;
  }

  public void setDragDetect(boolean paramBoolean)
  {
    checkWidget();
    this.dragDetect = paramBoolean;
  }

  public void setEditable(boolean paramBoolean)
  {
    checkWidget();
    this.editable = paramBoolean;
  }

  public void setFont(Font paramFont)
  {
    checkWidget();
    int i = this.renderer.getLineHeight();
    super.setFont(paramFont);
    this.renderer.setFont(getFont(), this.tabLength);
    if (isFixedLineHeight())
    {
      int j = this.renderer.getLineHeight();
      if (j != i)
      {
        int k = getVerticalScrollOffset() * j / i - getVerticalScrollOffset();
        scrollVertical(k, true);
      }
    }
    resetCache(0, this.content.getLineCount());
    claimBottomFreeSpace();
    calculateScrollBars();
    if (isBidiCaret())
      createCaretBitmaps();
    this.caretDirection = 0;
    setCaretLocation();
    super.redraw();
  }

  public void setForeground(Color paramColor)
  {
    checkWidget();
    this.foreground = paramColor;
    super.setForeground(getForeground());
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setHorizontalIndex(int paramInt)
  {
    checkWidget();
    if (getCharCount() == 0)
      return;
    if (paramInt < 0)
      paramInt = 0;
    paramInt *= getHorizontalIncrement();
    if (this.clientAreaWidth > 0)
    {
      int i = this.renderer.getWidth();
      if (paramInt > i - this.clientAreaWidth)
        paramInt = Math.max(0, i - this.clientAreaWidth);
    }
    scrollHorizontal(paramInt - this.horizontalScrollOffset, true);
  }

  public void setHorizontalPixel(int paramInt)
  {
    checkWidget();
    if (getCharCount() == 0)
      return;
    if (paramInt < 0)
      paramInt = 0;
    if (this.clientAreaWidth > 0)
    {
      int i = this.renderer.getWidth();
      if (paramInt > i - this.clientAreaWidth)
        paramInt = Math.max(0, i - this.clientAreaWidth);
    }
    scrollHorizontal(paramInt - this.horizontalScrollOffset, true);
  }

  public void setIndent(int paramInt)
  {
    checkWidget();
    if ((this.indent == paramInt) || (paramInt < 0))
      return;
    this.indent = paramInt;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setJustify(boolean paramBoolean)
  {
    checkWidget();
    if (this.justify == paramBoolean)
      return;
    this.justify = paramBoolean;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setKeyBinding(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = paramInt1 & SWT.MODIFIER_MASK;
    char c = (char)(paramInt1 & 0x100FFFF);
    if (Compatibility.isLetter(c))
    {
      int j = Character.toUpperCase(c);
      int k = j | i;
      if (paramInt2 == 0)
        this.keyActionMap.remove(new Integer(k));
      else
        this.keyActionMap.put(new Integer(k), new Integer(paramInt2));
      j = Character.toLowerCase(c);
      k = j | i;
      if (paramInt2 == 0)
        this.keyActionMap.remove(new Integer(k));
      else
        this.keyActionMap.put(new Integer(k), new Integer(paramInt2));
    }
    else if (paramInt2 == 0)
    {
      this.keyActionMap.remove(new Integer(paramInt1));
    }
    else
    {
      this.keyActionMap.put(new Integer(paramInt1), new Integer(paramInt2));
    }
  }

  public void setLeftMargin(int paramInt)
  {
    checkWidget();
    setMargins(paramInt, this.topMargin, this.rightMargin, this.bottomMargin);
  }

  public void setLineAlignment(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    this.renderer.setLineAlignment(paramInt1, paramInt2, paramInt3);
    resetCache(paramInt1, paramInt2);
    redrawLines(paramInt1, paramInt2, false);
    int i = getCaretLine();
    if ((paramInt1 <= i) && (i < paramInt1 + paramInt2))
      setCaretLocation();
    setAlignment();
  }

  public void setLineBackground(int paramInt1, int paramInt2, Color paramColor)
  {
    checkWidget();
    if (isListening(3001))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    if (paramColor != null)
      this.renderer.setLineBackground(paramInt1, paramInt2, paramColor);
    else
      this.renderer.clearLineBackground(paramInt1, paramInt2);
    redrawLines(paramInt1, paramInt2, false);
  }

  public void setLineBullet(int paramInt1, int paramInt2, Bullet paramBullet)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    int i = getLinePixel(paramInt1 + paramInt2);
    this.renderer.setLineBullet(paramInt1, paramInt2, paramBullet);
    resetCache(paramInt1, paramInt2);
    int j = getLinePixel(paramInt1 + paramInt2);
    redrawLines(paramInt1, paramInt2, i != j);
    int k = getCaretLine();
    if ((paramInt1 <= k) && (k < paramInt1 + paramInt2))
      setCaretLocation();
  }

  void setVariableLineHeight()
  {
    if (!this.fixedLineHeight)
      return;
    this.fixedLineHeight = false;
    this.renderer.calculateIdle();
  }

  public void setLineIndent(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    int i = getLinePixel(paramInt1 + paramInt2);
    this.renderer.setLineIndent(paramInt1, paramInt2, paramInt3);
    resetCache(paramInt1, paramInt2);
    int j = getLinePixel(paramInt1 + paramInt2);
    redrawLines(paramInt1, paramInt2, i != j);
    int k = getCaretLine();
    if ((paramInt1 <= k) && (k < paramInt1 + paramInt2))
      setCaretLocation();
  }

  public void setLineJustify(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    this.renderer.setLineJustify(paramInt1, paramInt2, paramBoolean);
    resetCache(paramInt1, paramInt2);
    redrawLines(paramInt1, paramInt2, false);
    int i = getCaretLine();
    if ((paramInt1 <= i) && (i < paramInt1 + paramInt2))
      setCaretLocation();
  }

  public void setLineSpacing(int paramInt)
  {
    checkWidget();
    if ((this.lineSpacing == paramInt) || (paramInt < 0))
      return;
    this.lineSpacing = paramInt;
    setVariableLineHeight();
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setLineTabStops(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    if (paramArrayOfInt != null)
    {
      i = 0;
      int[] arrayOfInt = new int[paramArrayOfInt.length];
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        if (paramArrayOfInt[j] < i)
          SWT.error(5);
        int tmp78_77 = paramArrayOfInt[j];
        i = tmp78_77;
        arrayOfInt[j] = tmp78_77;
      }
      this.renderer.setLineTabStops(paramInt1, paramInt2, arrayOfInt);
    }
    else
    {
      this.renderer.setLineTabStops(paramInt1, paramInt2, null);
    }
    resetCache(paramInt1, paramInt2);
    redrawLines(paramInt1, paramInt2, false);
    int i = getCaretLine();
    if ((paramInt1 <= i) && (i < paramInt1 + paramInt2))
      setCaretLocation();
  }

  public void setLineWrapIndent(int paramInt1, int paramInt2, int paramInt3)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramInt1 < 0) || (paramInt1 + paramInt2 > this.content.getLineCount()))
      SWT.error(5);
    int i = getLinePixel(paramInt1 + paramInt2);
    this.renderer.setLineWrapIndent(paramInt1, paramInt2, paramInt3);
    resetCache(paramInt1, paramInt2);
    int j = getLinePixel(paramInt1 + paramInt2);
    redrawLines(paramInt1, paramInt2, i != j);
    int k = getCaretLine();
    if ((paramInt1 <= k) && (k < paramInt1 + paramInt2))
      setCaretLocation();
  }

  public void setMarginColor(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    this.marginColor = paramColor;
    super.redraw();
  }

  public void setMargins(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkWidget();
    this.leftMargin = Math.max(0, paramInt1);
    this.topMargin = Math.max(0, paramInt2);
    this.rightMargin = Math.max(0, paramInt3);
    this.bottomMargin = Math.max(0, paramInt4);
    resetCache(0, this.content.getLineCount());
    setScrollBars(true);
    setCaretLocation();
    setAlignment();
    super.redraw();
  }

  void setMouseWordSelectionAnchor()
  {
    if (this.clickCount > 1)
      if (this.caretOffset < this.doubleClickSelection.x)
        this.selectionAnchor = this.doubleClickSelection.y;
      else if (this.caretOffset > this.doubleClickSelection.y)
        this.selectionAnchor = this.doubleClickSelection.x;
  }

  public void setOrientation(int paramInt)
  {
    if ((paramInt & 0x6000000) == 0)
      return;
    if (((paramInt & 0x4000000) != 0) && ((paramInt & 0x2000000) != 0))
      return;
    if (((paramInt & 0x4000000) != 0) && (isMirrored()))
      return;
    if (((paramInt & 0x2000000) != 0) && (!isMirrored()))
      return;
    if (!BidiUtil.setOrientation(this, paramInt))
      return;
    this.isMirrored = ((paramInt & 0x4000000) != 0);
    this.caretDirection = 0;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    this.keyActionMap.clear();
    createKeyBindings();
    super.redraw();
  }

  public void setRightMargin(int paramInt)
  {
    checkWidget();
    setMargins(this.leftMargin, this.topMargin, paramInt, this.bottomMargin);
  }

  void setScrollBars(boolean paramBoolean)
  {
    int i = 1;
    int j;
    if ((paramBoolean) || (!isFixedLineHeight()))
    {
      localScrollBar = getVerticalBar();
      if (localScrollBar != null)
      {
        j = this.renderer.getHeight();
        if (this.clientAreaHeight < j)
        {
          localScrollBar.setMaximum(j - this.topMargin - this.bottomMargin);
          localScrollBar.setThumb(this.clientAreaHeight - this.topMargin - this.bottomMargin);
          localScrollBar.setPageIncrement(this.clientAreaHeight - this.topMargin - this.bottomMargin);
        }
        else if ((localScrollBar.getThumb() != i) || (localScrollBar.getMaximum() != i))
        {
          localScrollBar.setValues(localScrollBar.getSelection(), localScrollBar.getMinimum(), i, i, localScrollBar.getIncrement(), i);
        }
      }
    }
    ScrollBar localScrollBar = getHorizontalBar();
    if ((localScrollBar != null) && (localScrollBar.getVisible()))
    {
      j = this.renderer.getWidth();
      if (this.clientAreaWidth < j)
      {
        localScrollBar.setMaximum(j - this.leftMargin - this.rightMargin);
        localScrollBar.setThumb(this.clientAreaWidth - this.leftMargin - this.rightMargin);
        localScrollBar.setPageIncrement(this.clientAreaWidth - this.leftMargin - this.rightMargin);
      }
      else if ((localScrollBar.getThumb() != i) || (localScrollBar.getMaximum() != i))
      {
        localScrollBar.setValues(localScrollBar.getSelection(), localScrollBar.getMinimum(), i, i, localScrollBar.getIncrement(), i);
      }
    }
  }

  public void setSelection(int paramInt)
  {
    setSelection(paramInt, paramInt);
  }

  public void setSelection(Point paramPoint)
  {
    checkWidget();
    if (paramPoint == null)
      SWT.error(4);
    setSelection(paramPoint.x, paramPoint.y);
  }

  public void setSelectionBackground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    this.selectionBackground = paramColor;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setSelectionForeground(Color paramColor)
  {
    checkWidget();
    if ((paramColor != null) && (paramColor.isDisposed()))
      SWT.error(5);
    this.selectionForeground = paramColor;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setSelection(int paramInt1, int paramInt2)
  {
    setSelectionRange(paramInt1, paramInt2 - paramInt1);
    showSelection();
  }

  void setSelection(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramInt1 + paramInt2;
    if (paramInt1 > i)
    {
      int j = i;
      i = paramInt1;
      paramInt1 = j;
    }
    if ((this.selection.x != paramInt1) || (this.selection.y != i) || ((paramInt2 > 0) && (this.selectionAnchor != this.selection.x)) || ((paramInt2 < 0) && (this.selectionAnchor != this.selection.y)))
      if ((this.blockSelection) && (paramBoolean2))
      {
        setBlockSelectionOffset(paramInt1, i, paramBoolean1);
      }
      else
      {
        clearSelection(paramBoolean1);
        if (paramInt2 < 0)
        {
          this.selectionAnchor = (this.selection.y = i);
          this.selection.x = paramInt1;
          setCaretOffset(paramInt1, 0);
        }
        else
        {
          this.selectionAnchor = (this.selection.x = paramInt1);
          this.selection.y = i;
          setCaretOffset(i, 0);
        }
        internalRedrawRange(this.selection.x, this.selection.y - this.selection.x);
        sendAccessibleTextCaretMoved();
      }
  }

  public void setSelectionRange(int paramInt1, int paramInt2)
  {
    checkWidget();
    int i = getCharCount();
    paramInt1 = Math.max(0, Math.min(paramInt1, i));
    int j = paramInt1 + paramInt2;
    if (j < 0)
      paramInt2 = -paramInt1;
    else if (j > i)
      paramInt2 = i - paramInt1;
    if ((isLineDelimiter(paramInt1)) || (isLineDelimiter(paramInt1 + paramInt2)))
      SWT.error(5);
    setSelection(paramInt1, paramInt2, false, true);
    setCaretLocation();
  }

  public void setStyleRange(StyleRange paramStyleRange)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if (paramStyleRange != null)
    {
      if (paramStyleRange.isUnstyled())
        setStyleRanges(paramStyleRange.start, paramStyleRange.length, null, null, false);
      else
        setStyleRanges(paramStyleRange.start, 0, null, new StyleRange[] { paramStyleRange }, false);
    }
    else
      setStyleRanges(0, 0, null, null, true);
  }

  public void setStyleRanges(int paramInt1, int paramInt2, int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramArrayOfInt == null) || (paramArrayOfStyleRange == null))
      setStyleRanges(paramInt1, paramInt2, null, null, false);
    else
      setStyleRanges(paramInt1, paramInt2, paramArrayOfInt, paramArrayOfStyleRange, false);
  }

  public void setStyleRanges(int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if ((paramArrayOfInt == null) || (paramArrayOfStyleRange == null))
      setStyleRanges(0, 0, null, null, true);
    else
      setStyleRanges(0, 0, paramArrayOfInt, paramArrayOfStyleRange, true);
  }

  void setStyleRanges(int paramInt1, int paramInt2, int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange, boolean paramBoolean)
  {
    int i = this.content.getCharCount();
    int j = paramInt1 + paramInt2;
    if ((paramInt1 > j) || (paramInt1 < 0))
      SWT.error(6);
    int i2;
    if (paramArrayOfStyleRange != null)
    {
      if (j > i)
        SWT.error(6);
      if ((paramArrayOfInt != null) && (paramArrayOfInt.length != paramArrayOfStyleRange.length << 1))
        SWT.error(5);
      k = 0;
      boolean bool = false;
      for (n = 0; n < paramArrayOfStyleRange.length; n++)
      {
        if (paramArrayOfStyleRange[n] == null)
          SWT.error(5);
        if (paramArrayOfInt != null)
        {
          i1 = paramArrayOfInt[(n << 1)];
          i2 = paramArrayOfInt[((n << 1) + 1)];
        }
        else
        {
          i1 = paramArrayOfStyleRange[n].start;
          i2 = paramArrayOfStyleRange[n].length;
        }
        if (i2 < 0)
          SWT.error(5);
        if ((i1 < 0) || (i1 + i2 > i))
          SWT.error(5);
        if (k > i1)
          SWT.error(5);
        bool |= paramArrayOfStyleRange[n].isVariableHeight();
        k = i1 + i2;
      }
      if (bool)
        setVariableLineHeight();
    }
    int k = paramInt1;
    int m = j;
    if ((paramArrayOfStyleRange != null) && (paramArrayOfStyleRange.length > 0))
      if (paramArrayOfInt != null)
      {
        k = paramArrayOfInt[0];
        m = paramArrayOfInt[(paramArrayOfInt.length - 2)] + paramArrayOfInt[(paramArrayOfInt.length - 1)];
      }
      else
      {
        k = paramArrayOfStyleRange[0].start;
        m = paramArrayOfStyleRange[(paramArrayOfStyleRange.length - 1)].start + paramArrayOfStyleRange[(paramArrayOfStyleRange.length - 1)].length;
      }
    int n = 0;
    int i3;
    if ((!isFixedLineHeight()) && (!paramBoolean))
    {
      i1 = this.content.getLineAtOffset(Math.max(j, m));
      i2 = getPartialTopIndex();
      i3 = getPartialBottomIndex();
      if ((i2 <= i1) && (i1 <= i3))
        n = getLinePixel(i1 + 1);
    }
    if (paramBoolean)
      this.renderer.setStyleRanges(null, null);
    else
      this.renderer.updateRanges(paramInt1, paramInt2, paramInt2);
    if ((paramArrayOfStyleRange != null) && (paramArrayOfStyleRange.length > 0))
      this.renderer.setStyleRanges(paramArrayOfInt, paramArrayOfStyleRange);
    if (paramBoolean)
    {
      resetCache(0, this.content.getLineCount());
      super.redraw();
    }
    else
    {
      i1 = this.content.getLineAtOffset(Math.min(paramInt1, k));
      i2 = this.content.getLineAtOffset(Math.max(j, m));
      resetCache(i1, i2 - i1 + 1);
      i3 = getPartialTopIndex();
      int i4 = getPartialBottomIndex();
      if ((i1 <= i4) && (i2 >= i3))
      {
        int i5 = 0;
        int i6 = this.clientAreaHeight;
        if ((i3 <= i1) && (i1 <= i4))
          i5 = Math.max(0, getLinePixel(i1));
        if ((i3 <= i2) && (i2 <= i4))
          i6 = getLinePixel(i2 + 1);
        if ((!isFixedLineHeight()) && (i6 != n))
          i6 = this.clientAreaHeight;
        super.redraw(0, i5, this.clientAreaWidth, i6 - i5, false);
      }
    }
    int i1 = this.columnX;
    setCaretLocation();
    this.columnX = i1;
    doMouseLinkCursor();
  }

  public void setStyleRanges(StyleRange[] paramArrayOfStyleRange)
  {
    checkWidget();
    if (isListening(3002))
      return;
    if (paramArrayOfStyleRange == null)
      SWT.error(4);
    setStyleRanges(0, 0, null, paramArrayOfStyleRange, true);
  }

  public void setTabs(int paramInt)
  {
    checkWidget();
    this.tabLength = paramInt;
    this.renderer.setFont(null, paramInt);
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setTabStops(int[] paramArrayOfInt)
  {
    checkWidget();
    if (paramArrayOfInt != null)
    {
      int i = 0;
      int[] arrayOfInt = new int[paramArrayOfInt.length];
      for (int j = 0; j < paramArrayOfInt.length; j++)
      {
        if (paramArrayOfInt[j] < i)
          SWT.error(5);
        int tmp40_39 = paramArrayOfInt[j];
        i = tmp40_39;
        arrayOfInt[j] = tmp40_39;
      }
      this.tabs = arrayOfInt;
    }
    else
    {
      this.tabs = null;
    }
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  public void setText(String paramString)
  {
    checkWidget();
    if (paramString == null)
      SWT.error(4);
    Event localEvent = new Event();
    localEvent.start = 0;
    localEvent.end = getCharCount();
    localEvent.text = paramString;
    localEvent.doit = true;
    notifyListeners(25, localEvent);
    if (localEvent.doit)
    {
      StyledTextEvent localStyledTextEvent = null;
      if (isListening(3000))
      {
        localStyledTextEvent = new StyledTextEvent(this.content);
        localStyledTextEvent.start = localEvent.start;
        localStyledTextEvent.end = (localEvent.start + localEvent.text.length());
        localStyledTextEvent.text = this.content.getTextRange(localEvent.start, localEvent.end - localEvent.start);
      }
      this.content.setText(localEvent.text);
      notifyListeners(24, localEvent);
      if (localStyledTextEvent != null)
        notifyListeners(3000, localStyledTextEvent);
    }
  }

  public void setTextLimit(int paramInt)
  {
    checkWidget();
    if (paramInt == 0)
      SWT.error(7);
    this.textLimit = paramInt;
  }

  public void setTopIndex(int paramInt)
  {
    checkWidget();
    if (getCharCount() == 0)
      return;
    int i = this.content.getLineCount();
    int j;
    if (isFixedLineHeight())
    {
      int k = Math.max(1, Math.min(i, getLineCountWhole()));
      if (paramInt < 0)
        paramInt = 0;
      else if (paramInt > i - k)
        paramInt = i - k;
      j = getLinePixel(paramInt);
    }
    else
    {
      paramInt = Math.max(0, Math.min(i - 1, paramInt));
      j = getLinePixel(paramInt);
      if (j > 0)
        j = getAvailableHeightBellow(j);
      else
        j = getAvailableHeightAbove(j);
    }
    scrollVertical(j, true);
  }

  public void setTopMargin(int paramInt)
  {
    checkWidget();
    setMargins(this.leftMargin, paramInt, this.rightMargin, this.bottomMargin);
  }

  public void setTopPixel(int paramInt)
  {
    checkWidget();
    if (getCharCount() == 0)
      return;
    if (paramInt < 0)
      paramInt = 0;
    int i = this.content.getLineCount();
    int j = this.clientAreaHeight - this.topMargin - this.bottomMargin;
    int k = getVerticalScrollOffset();
    if (isFixedLineHeight())
    {
      int m = Math.max(0, i * getVerticalIncrement() - j);
      if (paramInt > m)
        paramInt = m;
      paramInt -= k;
    }
    else
    {
      paramInt -= k;
      if (paramInt > 0)
        paramInt = getAvailableHeightBellow(paramInt);
    }
    scrollVertical(paramInt, true);
  }

  public void setWordWrap(boolean paramBoolean)
  {
    checkWidget();
    if ((getStyle() & 0x4) != 0)
      return;
    if (this.wordWrap == paramBoolean)
      return;
    if ((this.wordWrap) && (this.blockSelection))
      setBlockSelection(false);
    this.wordWrap = paramBoolean;
    setVariableLineHeight();
    resetCache(0, this.content.getLineCount());
    this.horizontalScrollOffset = 0;
    ScrollBar localScrollBar = getHorizontalBar();
    if (localScrollBar != null)
      localScrollBar.setVisible(!this.wordWrap);
    setScrollBars(true);
    setCaretLocation();
    super.redraw();
  }

  public void setWrapIndent(int paramInt)
  {
    checkWidget();
    if ((this.wrapIndent == paramInt) || (paramInt < 0))
      return;
    this.wrapIndent = paramInt;
    resetCache(0, this.content.getLineCount());
    setCaretLocation();
    super.redraw();
  }

  boolean showLocation(Rectangle paramRectangle, boolean paramBoolean)
  {
    boolean bool = false;
    if (paramRectangle.y < this.topMargin)
      bool = scrollVertical(paramRectangle.y - this.topMargin, true);
    else if (paramRectangle.y + paramRectangle.height > this.clientAreaHeight - this.bottomMargin)
      if (this.clientAreaHeight - this.topMargin - this.bottomMargin <= 0)
        bool = scrollVertical(paramRectangle.y - this.topMargin, true);
      else
        bool = scrollVertical(paramRectangle.y + paramRectangle.height - (this.clientAreaHeight - this.bottomMargin), true);
    int i = this.clientAreaWidth - this.rightMargin - this.leftMargin;
    if (i > 0)
    {
      int j = paramBoolean ? i / 4 : 0;
      int k;
      int m;
      if (paramRectangle.x < this.leftMargin)
      {
        k = Math.max(this.leftMargin - paramRectangle.x, j);
        m = this.horizontalScrollOffset;
        bool = scrollHorizontal(-Math.min(m, k), true);
      }
      else if (paramRectangle.x + paramRectangle.width > this.clientAreaWidth - this.rightMargin)
      {
        k = Math.max(paramRectangle.x + paramRectangle.width - (this.clientAreaWidth - this.rightMargin), j);
        m = this.renderer.getWidth() - this.horizontalScrollOffset - this.clientAreaWidth;
        bool = scrollHorizontal(Math.min(m, k), true);
      }
    }
    return bool;
  }

  void showCaret()
  {
    Rectangle localRectangle = getBoundsAtOffset(this.caretOffset);
    if (!showLocation(localRectangle, true))
      setCaretLocation();
  }

  public void showSelection()
  {
    checkWidget();
    int i = this.caretOffset == this.selection.x ? 1 : 0;
    int j;
    int k;
    if (i != 0)
    {
      j = this.selection.y;
      k = this.selection.x;
    }
    else
    {
      j = this.selection.x;
      k = this.selection.y;
    }
    Rectangle localRectangle1 = getBoundsAtOffset(j);
    Rectangle localRectangle2 = getBoundsAtOffset(k);
    int m = this.clientAreaWidth - this.leftMargin - this.rightMargin;
    int n = localRectangle2.x - localRectangle1.x <= m ? 1 : i != 0 ? 0 : localRectangle1.x - localRectangle2.x <= m ? 1 : 0;
    if (n != 0)
    {
      if (showLocation(localRectangle1, false))
        localRectangle2 = getBoundsAtOffset(k);
      localRectangle2.width = (k == this.caretOffset ? getCaretWidth() : 0);
      showLocation(localRectangle2, false);
    }
    else
    {
      showLocation(localRectangle2, true);
    }
  }

  void updateCaretVisibility()
  {
    Caret localCaret = getCaret();
    if (localCaret != null)
      if ((this.blockSelection) && (this.blockXLocation != -1))
      {
        localCaret.setVisible(false);
      }
      else
      {
        Point localPoint1 = localCaret.getLocation();
        Point localPoint2 = localCaret.getSize();
        boolean bool = (this.topMargin <= localPoint1.y + localPoint2.y) && (localPoint1.y <= this.clientAreaHeight - this.bottomMargin) && (this.leftMargin <= localPoint1.x + localPoint2.x) && (localPoint1.x <= this.clientAreaWidth - this.rightMargin);
        localCaret.setVisible(bool);
      }
  }

  void updateSelection(int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.selection.y <= paramInt1)
    {
      if (this.wordWrap)
        setCaretLocation();
      return;
    }
    if (this.selection.x < paramInt1)
      internalRedrawRange(this.selection.x, paramInt1 - this.selection.x);
    if ((this.selection.y > paramInt1 + paramInt2) && (this.selection.x < paramInt1 + paramInt2))
    {
      int i = paramInt3 - paramInt2;
      int j = paramInt1 + paramInt3;
      internalRedrawRange(j, this.selection.y + i - j);
    }
    if ((this.selection.y > paramInt1) && (this.selection.x < paramInt1 + paramInt2))
      setSelection(paramInt1 + paramInt3, 0, true, false);
    else
      setSelection(this.selection.x + paramInt3 - paramInt2, this.selection.y - this.selection.x, true, false);
    setCaretLocation();
  }

  static class Printing
    implements Runnable
  {
    static final int LEFT = 0;
    static final int CENTER = 1;
    static final int RIGHT = 2;
    Printer printer;
    StyledTextRenderer printerRenderer;
    StyledTextPrintOptions printOptions;
    Rectangle clientArea;
    FontData fontData;
    Font printerFont;
    Hashtable resources;
    int tabLength;
    GC gc;
    int pageWidth;
    int startPage;
    int endPage;
    int scope;
    int startLine;
    int endLine;
    boolean singleLine;
    Point selection = null;
    boolean mirrored;
    int lineSpacing;
    int printMargin;

    Printing(StyledText paramStyledText, Printer paramPrinter, StyledTextPrintOptions paramStyledTextPrintOptions)
    {
      this.printer = paramPrinter;
      this.printOptions = paramStyledTextPrintOptions;
      this.mirrored = ((paramStyledText.getStyle() & 0x8000000) != 0);
      this.singleLine = paramStyledText.isSingleLine();
      this.startPage = 1;
      this.endPage = 2147483647;
      PrinterData localPrinterData = paramPrinter.getPrinterData();
      this.scope = localPrinterData.scope;
      if (this.scope == 1)
      {
        this.startPage = localPrinterData.startPage;
        this.endPage = localPrinterData.endPage;
        if (this.endPage < this.startPage)
        {
          int i = this.endPage;
          this.endPage = this.startPage;
          this.startPage = i;
        }
      }
      else if (this.scope == 2)
      {
        this.selection = paramStyledText.getSelectionRange();
      }
      this.printerRenderer = new StyledTextRenderer(paramPrinter, null);
      this.printerRenderer.setContent(copyContent(paramStyledText.getContent()));
      cacheLineData(paramStyledText);
    }

    void cacheLineData(StyledText paramStyledText)
    {
      StyledTextRenderer localStyledTextRenderer = paramStyledText.renderer;
      localStyledTextRenderer.copyInto(this.printerRenderer);
      this.fontData = paramStyledText.getFont().getFontData()[0];
      this.tabLength = paramStyledText.tabLength;
      int i = this.printerRenderer.lineCount;
      Object localObject2;
      Object localObject3;
      if ((paramStyledText.isListening(3001)) || ((paramStyledText.isBidi()) && (paramStyledText.isListening(3007))) || (paramStyledText.isListening(3002)))
      {
        localObject1 = this.printerRenderer.content;
        for (int j = 0; j < i; j++)
        {
          String str = ((StyledTextContent)localObject1).getLine(j);
          int m = ((StyledTextContent)localObject1).getOffsetAtLine(j);
          localObject2 = paramStyledText.getLineBackgroundData(m, str);
          if ((localObject2 != null) && (((StyledTextEvent)localObject2).lineBackground != null))
            this.printerRenderer.setLineBackground(j, 1, ((StyledTextEvent)localObject2).lineBackground);
          if (paramStyledText.isBidi())
          {
            localObject2 = paramStyledText.getBidiSegments(m, str);
            if (localObject2 != null)
            {
              this.printerRenderer.setLineSegments(j, 1, ((StyledTextEvent)localObject2).segments);
              this.printerRenderer.setLineSegmentChars(j, 1, ((StyledTextEvent)localObject2).segmentsChars);
            }
          }
          localObject2 = paramStyledText.getLineStyleData(m, str);
          if (localObject2 != null)
          {
            this.printerRenderer.setLineIndent(j, 1, ((StyledTextEvent)localObject2).indent);
            this.printerRenderer.setLineAlignment(j, 1, ((StyledTextEvent)localObject2).alignment);
            this.printerRenderer.setLineJustify(j, 1, ((StyledTextEvent)localObject2).justify);
            this.printerRenderer.setLineBullet(j, 1, ((StyledTextEvent)localObject2).bullet);
            localObject3 = ((StyledTextEvent)localObject2).styles;
            if ((localObject3 != null) && (localObject3.length > 0))
              this.printerRenderer.setStyleRanges(((StyledTextEvent)localObject2).ranges, (StyleRange[])localObject3);
          }
        }
      }
      Object localObject1 = paramStyledText.getDisplay().getDPI();
      Point localPoint = this.printer.getDPI();
      this.resources = new Hashtable();
      for (int k = 0; k < i; k++)
      {
        Color localColor = this.printerRenderer.getLineBackground(k, null);
        if (localColor != null)
          if (this.printOptions.printLineBackground)
          {
            localObject2 = (Color)this.resources.get(localColor);
            if (localObject2 == null)
            {
              localObject2 = new Color(this.printer, localColor.getRGB());
              this.resources.put(localColor, localObject2);
            }
            this.printerRenderer.setLineBackground(k, 1, (Color)localObject2);
          }
          else
          {
            this.printerRenderer.setLineBackground(k, 1, null);
          }
        int i1 = this.printerRenderer.getLineIndent(k, 0);
        if (i1 != 0)
          this.printerRenderer.setLineIndent(k, 1, i1 * localPoint.x / ((Point)localObject1).x);
      }
      StyleRange[] arrayOfStyleRange = this.printerRenderer.styles;
      for (int n = 0; n < this.printerRenderer.styleCount; n++)
      {
        StyleRange localStyleRange = arrayOfStyleRange[n];
        localObject3 = localStyleRange.font;
        if (localStyleRange.font != null)
        {
          localObject4 = (Font)this.resources.get(localObject3);
          if (localObject4 == null)
          {
            localObject4 = new Font(this.printer, ((Font)localObject3).getFontData());
            this.resources.put(localObject3, localObject4);
          }
          localStyleRange.font = ((Font)localObject4);
        }
        Object localObject4 = localStyleRange.foreground;
        if (localObject4 != null)
        {
          localObject5 = (Color)this.resources.get(localObject4);
          if (this.printOptions.printTextForeground)
          {
            if (localObject5 == null)
            {
              localObject5 = new Color(this.printer, ((Color)localObject4).getRGB());
              this.resources.put(localObject4, localObject5);
            }
            localStyleRange.foreground = ((Color)localObject5);
          }
          else
          {
            localStyleRange.foreground = null;
          }
        }
        localObject4 = localStyleRange.background;
        if (localObject4 != null)
        {
          localObject5 = (Color)this.resources.get(localObject4);
          if (this.printOptions.printTextBackground)
          {
            if (localObject5 == null)
            {
              localObject5 = new Color(this.printer, ((Color)localObject4).getRGB());
              this.resources.put(localObject4, localObject5);
            }
            localStyleRange.background = ((Color)localObject5);
          }
          else
          {
            localStyleRange.background = null;
          }
        }
        if (!this.printOptions.printTextFontStyle)
          localStyleRange.fontStyle = 0;
        localStyleRange.rise = (localStyleRange.rise * localPoint.y / ((Point)localObject1).y);
        Object localObject5 = localStyleRange.metrics;
        if (localObject5 != null)
        {
          ((GlyphMetrics)localObject5).ascent = (((GlyphMetrics)localObject5).ascent * localPoint.y / ((Point)localObject1).y);
          ((GlyphMetrics)localObject5).descent = (((GlyphMetrics)localObject5).descent * localPoint.y / ((Point)localObject1).y);
          ((GlyphMetrics)localObject5).width = (((GlyphMetrics)localObject5).width * localPoint.x / ((Point)localObject1).x);
        }
      }
      this.lineSpacing = (paramStyledText.lineSpacing * localPoint.y / ((Point)localObject1).y);
      if (this.printOptions.printLineNumbers)
        this.printMargin = (3 * localPoint.x / ((Point)localObject1).x);
    }

    StyledTextContent copyContent(StyledTextContent paramStyledTextContent)
    {
      DefaultContent localDefaultContent = new DefaultContent();
      int i = 0;
      for (int j = 0; j < paramStyledTextContent.getLineCount(); j++)
      {
        int k;
        if (j < paramStyledTextContent.getLineCount() - 1)
          k = paramStyledTextContent.getOffsetAtLine(j + 1);
        else
          k = paramStyledTextContent.getCharCount();
        localDefaultContent.replaceTextRange(i, 0, paramStyledTextContent.getTextRange(i, k - i));
        i = k;
      }
      return localDefaultContent;
    }

    void dispose()
    {
      if (this.gc != null)
      {
        this.gc.dispose();
        this.gc = null;
      }
      if (this.resources != null)
      {
        Enumeration localEnumeration = this.resources.elements();
        while (localEnumeration.hasMoreElements())
        {
          Resource localResource = (Resource)localEnumeration.nextElement();
          localResource.dispose();
        }
        this.resources = null;
      }
      if (this.printerFont != null)
      {
        this.printerFont.dispose();
        this.printerFont = null;
      }
      if (this.printerRenderer != null)
      {
        this.printerRenderer.dispose();
        this.printerRenderer = null;
      }
    }

    void init()
    {
      Rectangle localRectangle = this.printer.computeTrim(0, 0, 0, 0);
      Point localPoint = this.printer.getDPI();
      this.printerFont = new Font(this.printer, this.fontData.getName(), this.fontData.getHeight(), 0);
      this.clientArea = this.printer.getClientArea();
      this.pageWidth = this.clientArea.width;
      this.clientArea.x = (localPoint.x + localRectangle.x);
      this.clientArea.y = (localPoint.y + localRectangle.y);
      this.clientArea.width -= this.clientArea.x + localRectangle.width;
      this.clientArea.height -= this.clientArea.y + localRectangle.height;
      int i = this.mirrored ? 67108864 : 33554432;
      this.gc = new GC(this.printer, i);
      this.gc.setFont(this.printerFont);
      this.printerRenderer.setFont(this.printerFont, this.tabLength);
      int j = this.printerRenderer.getLineHeight();
      if (this.printOptions.header != null)
      {
        this.clientArea.y += j * 2;
        this.clientArea.height -= j * 2;
      }
      if (this.printOptions.footer != null)
        this.clientArea.height -= j * 2;
      StyledTextContent localStyledTextContent = this.printerRenderer.content;
      this.startLine = 0;
      this.endLine = (this.singleLine ? 0 : localStyledTextContent.getLineCount() - 1);
      if (this.scope == 1)
      {
        int k = this.clientArea.height / j;
        this.startLine = ((this.startPage - 1) * k);
      }
      else if (this.scope == 2)
      {
        this.startLine = localStyledTextContent.getLineAtOffset(this.selection.x);
        if (this.selection.y > 0)
          this.endLine = localStyledTextContent.getLineAtOffset(this.selection.x + this.selection.y - 1);
        else
          this.endLine = (this.startLine - 1);
      }
    }

    void print()
    {
      Color localColor1 = this.gc.getBackground();
      Color localColor2 = this.gc.getForeground();
      int i = this.clientArea.y;
      int j = this.clientArea.x;
      int k = this.clientArea.width;
      int m = this.startPage;
      int n = this.clientArea.y + this.clientArea.height;
      int i1 = this.gc.getStyle() & 0x6000000;
      TextLayout localTextLayout1 = null;
      if ((this.printOptions.printLineNumbers) || (this.printOptions.header != null) || (this.printOptions.footer != null))
      {
        localTextLayout1 = new TextLayout(this.printer);
        localTextLayout1.setFont(this.printerFont);
      }
      Object localObject;
      int i6;
      if (this.printOptions.printLineNumbers)
      {
        i2 = 0;
        int i3 = this.endLine - this.startLine + 1;
        localObject = this.printOptions.lineLabels;
        if (localObject != null)
        {
          for (int i4 = this.startLine; i4 < Math.min(i3, localObject.length); i4++)
            if (localObject[i4] != null)
            {
              localTextLayout1.setText(localObject[i4]);
              i6 = localTextLayout1.getBounds().width;
              i2 = Math.max(i2, i6);
            }
        }
        else
        {
          StringBuffer localStringBuffer = new StringBuffer("0");
          while (i3 /= 10 > 0)
            localStringBuffer.append("0");
          localTextLayout1.setText(localStringBuffer.toString());
          i2 = localTextLayout1.getBounds().width;
        }
        i2 += this.printMargin;
        if (i2 > k)
          i2 = k;
        j += i2;
        k -= i2;
      }
      for (int i2 = this.startLine; (i2 <= this.endLine) && (m <= this.endPage); i2++)
      {
        if (i == this.clientArea.y)
        {
          this.printer.startPage();
          printDecoration(m, true, localTextLayout1);
        }
        TextLayout localTextLayout2 = this.printerRenderer.getTextLayout(i2, i1, k, this.lineSpacing);
        localObject = this.printerRenderer.getLineBackground(i2, localColor1);
        int i5 = i + localTextLayout2.getBounds().height;
        if (i5 <= n)
        {
          printLine(j, i, this.gc, localColor2, (Color)localObject, localTextLayout2, localTextLayout1, i2);
          i = i5;
        }
        else
        {
          i6 = localTextLayout2.getLineCount();
          while ((i5 > n) && (i6 > 0))
          {
            i6--;
            i5 -= localTextLayout2.getLineBounds(i6).height + localTextLayout2.getSpacing();
          }
          if (i6 == 0)
          {
            printDecoration(m, false, localTextLayout1);
            this.printer.endPage();
            m++;
            if (m <= this.endPage)
            {
              this.printer.startPage();
              printDecoration(m, true, localTextLayout1);
              i = this.clientArea.y;
              printLine(j, i, this.gc, localColor2, (Color)localObject, localTextLayout2, localTextLayout1, i2);
              i += localTextLayout2.getBounds().height;
            }
          }
          else
          {
            int i7 = i5 - i;
            this.gc.setClipping(this.clientArea.x, i, this.clientArea.width, i7);
            printLine(j, i, this.gc, localColor2, (Color)localObject, localTextLayout2, localTextLayout1, i2);
            this.gc.setClipping(null);
            printDecoration(m, false, localTextLayout1);
            this.printer.endPage();
            m++;
            if (m <= this.endPage)
            {
              this.printer.startPage();
              printDecoration(m, true, localTextLayout1);
              i = this.clientArea.y - i7;
              int i8 = localTextLayout2.getBounds().height;
              this.gc.setClipping(this.clientArea.x, this.clientArea.y, this.clientArea.width, i8 - i7);
              printLine(j, i, this.gc, localColor2, (Color)localObject, localTextLayout2, localTextLayout1, i2);
              this.gc.setClipping(null);
              i += i8;
            }
          }
        }
        this.printerRenderer.disposeTextLayout(localTextLayout2);
      }
      if ((m <= this.endPage) && (i > this.clientArea.y))
      {
        printDecoration(m, false, localTextLayout1);
        this.printer.endPage();
      }
      if (localTextLayout1 != null)
        localTextLayout1.dispose();
    }

    void printDecoration(int paramInt, boolean paramBoolean, TextLayout paramTextLayout)
    {
      String str1 = paramBoolean ? this.printOptions.header : this.printOptions.footer;
      if (str1 == null)
        return;
      int i = 0;
      for (int j = 0; j < 3; j++)
      {
        int k = str1.indexOf("\t", i);
        if (k == -1)
        {
          str2 = str1.substring(i);
          printDecorationSegment(str2, j, paramInt, paramBoolean, paramTextLayout);
          break;
        }
        String str2 = str1.substring(i, k);
        printDecorationSegment(str2, j, paramInt, paramBoolean, paramTextLayout);
        i = k + "\t".length();
      }
    }

    void printDecorationSegment(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, TextLayout paramTextLayout)
    {
      int i = paramString.indexOf("<page>");
      int j;
      if (i != -1)
      {
        j = "<page>".length();
        StringBuffer localStringBuffer = new StringBuffer(paramString.substring(0, i));
        localStringBuffer.append(paramInt2);
        localStringBuffer.append(paramString.substring(i + j));
        paramString = localStringBuffer.toString();
      }
      if (paramString.length() > 0)
      {
        paramTextLayout.setText(paramString);
        j = paramTextLayout.getBounds().width;
        int k = this.printerRenderer.getLineHeight();
        int m = 0;
        if (paramInt1 == 0)
          m = this.clientArea.x;
        else if (paramInt1 == 1)
          m = (this.pageWidth - j) / 2;
        else if (paramInt1 == 2)
          m = this.clientArea.x + this.clientArea.width - j;
        int n;
        if (paramBoolean)
          n = this.clientArea.y - k * 2;
        else
          n = this.clientArea.y + this.clientArea.height + k;
        paramTextLayout.draw(this.gc, m, n);
      }
    }

    void printLine(int paramInt1, int paramInt2, GC paramGC, Color paramColor1, Color paramColor2, TextLayout paramTextLayout1, TextLayout paramTextLayout2, int paramInt3)
    {
      Object localObject;
      if (paramColor2 != null)
      {
        localObject = paramTextLayout1.getBounds();
        paramGC.setBackground(paramColor2);
        paramGC.fillRectangle(paramInt1, paramInt2, ((Rectangle)localObject).width, ((Rectangle)localObject).height);
      }
      if (this.printOptions.printLineNumbers)
      {
        localObject = paramTextLayout1.getLineMetrics(0);
        paramTextLayout2.setAscent(((FontMetrics)localObject).getAscent() + ((FontMetrics)localObject).getLeading());
        paramTextLayout2.setDescent(((FontMetrics)localObject).getDescent());
        String[] arrayOfString = this.printOptions.lineLabels;
        if (arrayOfString != null)
        {
          if ((paramInt3 >= 0) && (paramInt3 < arrayOfString.length) && (arrayOfString[paramInt3] != null))
            paramTextLayout2.setText(arrayOfString[paramInt3]);
          else
            paramTextLayout2.setText("");
        }
        else
          paramTextLayout2.setText(String.valueOf(paramInt3));
        int i = paramInt1 - this.printMargin - paramTextLayout2.getBounds().width;
        paramTextLayout2.draw(paramGC, i, paramInt2);
        paramTextLayout2.setAscent(-1);
        paramTextLayout2.setDescent(-1);
      }
      paramGC.setForeground(paramColor1);
      paramTextLayout1.draw(paramGC, paramInt1, paramInt2);
    }

    public void run()
    {
      String str = this.printOptions.jobName;
      if (str == null)
        str = "Printing";
      if (this.printer.startJob(str))
      {
        init();
        print();
        dispose();
        this.printer.endJob();
      }
    }
  }

  class RTFWriter extends StyledText.TextWriter
  {
    static final int DEFAULT_FOREGROUND = 0;
    static final int DEFAULT_BACKGROUND = 1;
    Vector colorTable = new Vector();
    Vector fontTable = new Vector();
    boolean WriteUnicode;

    public RTFWriter(int paramInt1, int arg3)
    {
      super(paramInt1, i);
      this.colorTable.addElement(StyledText.this.getForeground());
      this.colorTable.addElement(StyledText.this.getBackground());
      this.fontTable.addElement(StyledText.this.getFont());
      setUnicode();
    }

    public void close()
    {
      if (!isClosed())
      {
        writeHeader();
        write("");
        super.close();
      }
    }

    int getColorIndex(Color paramColor, int paramInt)
    {
      if (paramColor == null)
        return paramInt;
      int i = this.colorTable.indexOf(paramColor);
      if (i == -1)
      {
        i = this.colorTable.size();
        this.colorTable.addElement(paramColor);
      }
      return i;
    }

    int getFontIndex(Font paramFont)
    {
      int i = this.fontTable.indexOf(paramFont);
      if (i == -1)
      {
        i = this.fontTable.size();
        this.fontTable.addElement(paramFont);
      }
      return i;
    }

    void setUnicode()
    {
      String str1 = System.getProperty("os.name").toLowerCase();
      String str2 = System.getProperty("os.version");
      int i = 0;
      if ((str1.startsWith("windows nt")) && (str2 != null))
      {
        int j = str2.indexOf('.');
        if (j != -1)
        {
          str2 = str2.substring(0, j);
          try
          {
            i = Integer.parseInt(str2);
          }
          catch (NumberFormatException localNumberFormatException)
          {
          }
        }
      }
      this.WriteUnicode = ((!str1.startsWith("windows 95")) && (!str1.startsWith("windows 98")) && (!str1.startsWith("windows me")) && ((!str1.startsWith("windows nt")) || (i > 4)));
    }

    void write(String paramString, int paramInt1, int paramInt2)
    {
      for (int i = paramInt1; i < paramInt2; i++)
      {
        char c = paramString.charAt(i);
        if ((c > '') && (this.WriteUnicode))
        {
          if (i > paramInt1)
            write(paramString.substring(paramInt1, i));
          write("\\u");
          write(Integer.toString((short)c));
          write('?');
          paramInt1 = i + 1;
        }
        else if ((c == '}') || (c == '{') || (c == '\\'))
        {
          if (i > paramInt1)
            write(paramString.substring(paramInt1, i));
          write('\\');
          write(c);
          paramInt1 = i + 1;
        }
      }
      if (paramInt1 < paramInt2)
        write(paramString.substring(paramInt1, paramInt2));
    }

    void writeHeader()
    {
      StringBuffer localStringBuffer = new StringBuffer();
      FontData localFontData = StyledText.this.getFont().getFontData()[0];
      localStringBuffer.append("{\\rtf1\\ansi");
      String str = System.getProperty("file.encoding").toLowerCase();
      if ((str.startsWith("cp")) || (str.startsWith("ms")))
      {
        str = str.substring(2, str.length());
        localStringBuffer.append("\\ansicpg");
        localStringBuffer.append(str);
      }
      localStringBuffer.append("\\uc1\\deff0{\\fonttbl{\\f0\\fnil ");
      localStringBuffer.append(localFontData.getName());
      localStringBuffer.append(";");
      Object localObject;
      for (int i = 1; i < this.fontTable.size(); i++)
      {
        localStringBuffer.append("\\f");
        localStringBuffer.append(i);
        localStringBuffer.append(" ");
        localObject = ((Font)this.fontTable.elementAt(i)).getFontData()[0];
        localStringBuffer.append(((FontData)localObject).getName());
        localStringBuffer.append(";");
      }
      localStringBuffer.append("}}\n{\\colortbl");
      for (i = 0; i < this.colorTable.size(); i++)
      {
        localObject = (Color)this.colorTable.elementAt(i);
        localStringBuffer.append("\\red");
        localStringBuffer.append(((Color)localObject).getRed());
        localStringBuffer.append("\\green");
        localStringBuffer.append(((Color)localObject).getGreen());
        localStringBuffer.append("\\blue");
        localStringBuffer.append(((Color)localObject).getBlue());
        localStringBuffer.append(";");
      }
      localStringBuffer.append("}\n{\\f0\\fs");
      localStringBuffer.append(localFontData.getHeight() * 2);
      localStringBuffer.append(" ");
      write(localStringBuffer.toString(), 0);
    }

    public void writeLine(String paramString, int paramInt)
    {
      if (isClosed())
        SWT.error(39);
      int i = StyledText.this.content.getLineAtOffset(paramInt);
      StyledTextEvent localStyledTextEvent = StyledText.this.getLineStyleData(paramInt, paramString);
      int j;
      int k;
      boolean bool;
      int[] arrayOfInt;
      StyleRange[] arrayOfStyleRange;
      if (localStyledTextEvent != null)
      {
        j = localStyledTextEvent.alignment;
        k = localStyledTextEvent.indent;
        bool = localStyledTextEvent.justify;
        arrayOfInt = localStyledTextEvent.ranges;
        arrayOfStyleRange = localStyledTextEvent.styles;
      }
      else
      {
        j = StyledText.this.renderer.getLineAlignment(i, StyledText.this.alignment);
        k = StyledText.this.renderer.getLineIndent(i, StyledText.this.indent);
        bool = StyledText.this.renderer.getLineJustify(i, StyledText.this.justify);
        arrayOfInt = StyledText.this.renderer.getRanges(paramInt, paramString.length());
        arrayOfStyleRange = StyledText.this.renderer.getStyleRanges(paramInt, paramString.length(), false);
      }
      if (arrayOfStyleRange == null)
        arrayOfStyleRange = new StyleRange[0];
      Color localColor = StyledText.this.renderer.getLineBackground(i, null);
      localStyledTextEvent = StyledText.this.getLineBackgroundData(paramInt, paramString);
      if ((localStyledTextEvent != null) && (localStyledTextEvent.lineBackground != null))
        localColor = localStyledTextEvent.lineBackground;
      writeStyledLine(paramString, paramInt, arrayOfInt, arrayOfStyleRange, localColor, k, j, bool);
    }

    public void writeLineDelimiter(String paramString)
    {
      if (isClosed())
        SWT.error(39);
      write(paramString, 0, paramString.length());
      write("\\par ");
    }

    void writeStyledLine(String paramString, int paramInt1, int[] paramArrayOfInt, StyleRange[] paramArrayOfStyleRange, Color paramColor, int paramInt2, int paramInt3, boolean paramBoolean)
    {
      int i = paramString.length();
      int j = getStart();
      int k = j - paramInt1;
      if (k >= i)
        return;
      int m = Math.max(0, k);
      write("\\fi");
      write(paramInt2);
      switch (paramInt3)
      {
      case 16384:
        write("\\ql");
        break;
      case 16777216:
        write("\\qc");
        break;
      case 131072:
        write("\\qr");
      }
      if (paramBoolean)
        write("\\qj");
      write(" ");
      if (paramColor != null)
      {
        write("{\\highlight");
        write(getColorIndex(paramColor, 1));
        write(" ");
      }
      int n = j + super.getCharCount();
      int i1 = Math.min(i, n - paramInt1);
      for (int i2 = 0; i2 < paramArrayOfStyleRange.length; i2++)
      {
        StyleRange localStyleRange = paramArrayOfStyleRange[i2];
        int i3;
        int i4;
        if (paramArrayOfInt != null)
        {
          i3 = paramArrayOfInt[(i2 << 1)] - paramInt1;
          i4 = i3 + paramArrayOfInt[((i2 << 1) + 1)];
        }
        else
        {
          i3 = localStyleRange.start - paramInt1;
          i4 = i3 + localStyleRange.length;
        }
        if (i4 >= k)
        {
          if (i3 >= i1)
            break;
          if (m < i3)
          {
            write(paramString, m, i3);
            m = i3;
          }
          write("{\\cf");
          write(getColorIndex(localStyleRange.foreground, 0));
          int i5 = getColorIndex(localStyleRange.background, 1);
          if (i5 != 1)
          {
            write("\\highlight");
            write(i5);
          }
          Font localFont = localStyleRange.font;
          if (localFont != null)
          {
            i6 = getFontIndex(localFont);
            write("\\f");
            write(i6);
            FontData localFontData = localFont.getFontData()[0];
            write("\\fs");
            write(localFontData.getHeight() * 2);
          }
          else
          {
            if ((localStyleRange.fontStyle & 0x1) != 0)
              write("\\b");
            if ((localStyleRange.fontStyle & 0x2) != 0)
              write("\\i");
          }
          if (localStyleRange.underline)
            write("\\ul");
          if (localStyleRange.strikeout)
            write("\\strike");
          write(" ");
          int i6 = Math.min(i4, i1);
          i6 = Math.max(i6, m);
          write(paramString, m, i6);
          if (localFont == null)
          {
            if ((localStyleRange.fontStyle & 0x1) != 0)
              write("\\b0");
            if ((localStyleRange.fontStyle & 0x2) != 0)
              write("\\i0");
          }
          if (localStyleRange.underline)
            write("\\ul0");
          if (localStyleRange.strikeout)
            write("\\strike0");
          write("}");
          m = i6;
        }
      }
      if (m < i1)
        write(paramString, m, i1);
      if (paramColor != null)
        write("}");
    }
  }

  class TextWriter
  {
    private StringBuffer buffer;
    private int startOffset;
    private int endOffset;
    private boolean isClosed = false;

    public TextWriter(int paramInt1, int arg3)
    {
      int i;
      this.buffer = new StringBuffer(i);
      this.startOffset = paramInt1;
      this.endOffset = (paramInt1 + i);
    }

    public void close()
    {
      if (!this.isClosed)
        this.isClosed = true;
    }

    public int getCharCount()
    {
      return this.endOffset - this.startOffset;
    }

    public int getStart()
    {
      return this.startOffset;
    }

    public boolean isClosed()
    {
      return this.isClosed;
    }

    public String toString()
    {
      return this.buffer.toString();
    }

    void write(String paramString)
    {
      this.buffer.append(paramString);
    }

    void write(String paramString, int paramInt)
    {
      if ((paramInt < 0) || (paramInt > this.buffer.length()))
        return;
      this.buffer.insert(paramInt, paramString);
    }

    void write(int paramInt)
    {
      this.buffer.append(paramInt);
    }

    void write(char paramChar)
    {
      this.buffer.append(paramChar);
    }

    public void writeLine(String paramString, int paramInt)
    {
      if (this.isClosed)
        SWT.error(39);
      int i = this.startOffset - paramInt;
      int j = paramString.length();
      if (i >= j)
        return;
      int k;
      if (i > 0)
        k = i;
      else
        k = 0;
      int m = Math.min(j, this.endOffset - paramInt);
      if (k < m)
        write(paramString.substring(k, m));
    }

    public void writeLineDelimiter(String paramString)
    {
      if (this.isClosed)
        SWT.error(39);
      write(paramString);
    }
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.StyledText
 * JD-Core Version:    0.6.2
 */