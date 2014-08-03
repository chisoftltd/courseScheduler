package org.eclipse.swt.custom;

public class BusyIndicator
{
  static int nextBusyId = 1;
  static final String BUSYID_NAME = "SWT BusyIndicator";
  static final String BUSY_CURSOR = "SWT BusyIndicator Cursor";

  // ERROR //
  public static void showWhile(org.eclipse.swt.widgets.Display paramDisplay, java.lang.Runnable paramRunnable)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +7 -> 8
    //   4: iconst_4
    //   5: invokestatic 25	org/eclipse/swt/SWT:error	(I)V
    //   8: aload_0
    //   9: ifnonnull +18 -> 27
    //   12: invokestatic 31	org/eclipse/swt/widgets/Display:getCurrent	()Lorg/eclipse/swt/widgets/Display;
    //   15: astore_0
    //   16: aload_0
    //   17: ifnonnull +10 -> 27
    //   20: aload_1
    //   21: invokeinterface 37 1 0
    //   26: return
    //   27: new 42	java/lang/Integer
    //   30: dup
    //   31: getstatic 18	org/eclipse/swt/custom/BusyIndicator:nextBusyId	I
    //   34: invokespecial 44	java/lang/Integer:<init>	(I)V
    //   37: astore_2
    //   38: getstatic 18	org/eclipse/swt/custom/BusyIndicator:nextBusyId	I
    //   41: iconst_1
    //   42: iadd
    //   43: putstatic 18	org/eclipse/swt/custom/BusyIndicator:nextBusyId	I
    //   46: aload_0
    //   47: iconst_1
    //   48: invokevirtual 46	org/eclipse/swt/widgets/Display:getSystemCursor	(I)Lorg/eclipse/swt/graphics/Cursor;
    //   51: astore_3
    //   52: aload_0
    //   53: invokevirtual 50	org/eclipse/swt/widgets/Display:getShells	()[Lorg/eclipse/swt/widgets/Shell;
    //   56: astore 4
    //   58: iconst_0
    //   59: istore 5
    //   61: goto +46 -> 107
    //   64: aload 4
    //   66: iload 5
    //   68: aaload
    //   69: ldc 10
    //   71: invokevirtual 54	org/eclipse/swt/widgets/Shell:getData	(Ljava/lang/String;)Ljava/lang/Object;
    //   74: checkcast 42	java/lang/Integer
    //   77: astore 6
    //   79: aload 6
    //   81: ifnonnull +23 -> 104
    //   84: aload 4
    //   86: iload 5
    //   88: aaload
    //   89: aload_3
    //   90: invokevirtual 60	org/eclipse/swt/widgets/Shell:setCursor	(Lorg/eclipse/swt/graphics/Cursor;)V
    //   93: aload 4
    //   95: iload 5
    //   97: aaload
    //   98: ldc 10
    //   100: aload_2
    //   101: invokevirtual 64	org/eclipse/swt/widgets/Shell:setData	(Ljava/lang/String;Ljava/lang/Object;)V
    //   104: iinc 5 1
    //   107: iload 5
    //   109: aload 4
    //   111: arraylength
    //   112: if_icmplt -48 -> 64
    //   115: aload_1
    //   116: invokeinterface 37 1 0
    //   121: goto +79 -> 200
    //   124: astore 6
    //   126: jsr +6 -> 132
    //   129: aload 6
    //   131: athrow
    //   132: astore 5
    //   134: aload_0
    //   135: invokevirtual 50	org/eclipse/swt/widgets/Display:getShells	()[Lorg/eclipse/swt/widgets/Shell;
    //   138: astore 4
    //   140: iconst_0
    //   141: istore 7
    //   143: goto +47 -> 190
    //   146: aload 4
    //   148: iload 7
    //   150: aaload
    //   151: ldc 10
    //   153: invokevirtual 54	org/eclipse/swt/widgets/Shell:getData	(Ljava/lang/String;)Ljava/lang/Object;
    //   156: checkcast 42	java/lang/Integer
    //   159: astore 8
    //   161: aload 8
    //   163: aload_2
    //   164: if_acmpne +23 -> 187
    //   167: aload 4
    //   169: iload 7
    //   171: aaload
    //   172: aconst_null
    //   173: invokevirtual 60	org/eclipse/swt/widgets/Shell:setCursor	(Lorg/eclipse/swt/graphics/Cursor;)V
    //   176: aload 4
    //   178: iload 7
    //   180: aaload
    //   181: ldc 10
    //   183: aconst_null
    //   184: invokevirtual 64	org/eclipse/swt/widgets/Shell:setData	(Ljava/lang/String;Ljava/lang/Object;)V
    //   187: iinc 7 1
    //   190: iload 7
    //   192: aload 4
    //   194: arraylength
    //   195: if_icmplt -49 -> 146
    //   198: ret 5
    //   200: jsr -68 -> 132
    //   203: return
    //
    // Exception table:
    //   from	to	target	type
    //   115	124	124	finally
    //   200	203	124	finally
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.custom.BusyIndicator
 * JD-Core Version:    0.6.2
 */