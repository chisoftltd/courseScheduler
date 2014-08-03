/*       */ package org.eclipse.swt.graphics;
/*       */ 
/*       */ import java.io.PrintStream;
/*       */ import org.eclipse.swt.SWT;
/*       */ import org.eclipse.swt.internal.Callback;
/*       */ import org.eclipse.swt.internal.gdip.Gdip;
/*       */ import org.eclipse.swt.internal.win32.LOGFONT;
/*       */ import org.eclipse.swt.internal.win32.LOGFONTA;
/*       */ import org.eclipse.swt.internal.win32.LOGFONTW;
/*       */ import org.eclipse.swt.internal.win32.OS;
/*       */ import org.eclipse.swt.internal.win32.TCHAR;
/*       */ import org.eclipse.swt.internal.win32.TEXTMETRIC;
/*       */ import org.eclipse.swt.internal.win32.TEXTMETRICA;
/*       */ import org.eclipse.swt.internal.win32.TEXTMETRICW;
/*       */ 
/*       */ public abstract class Device
/*       */   implements Drawable
/*       */ {
/*       */   public static boolean DEBUG;
/*       */   boolean debug = DEBUG;
/*       */   boolean tracking = DEBUG;
/*       */   Error[] errors;
/*       */   Object[] objects;
/*       */   Object trackingLock;
/*       */   public int hPalette = 0;
/*       */   int[] colorRefCount;
/*       */   Font systemFont;
/*       */   int nFonts = 256;
/*       */   LOGFONT[] logFonts;
/*       */   TEXTMETRIC metrics;
/*       */   int[] pixels;
/*       */   int[] scripts;
/*       */   int[] gdipToken;
/*       */   int fontCollection;
/*       */   String[] loadedFonts;
/*       */   boolean disposed;
/*       */   protected static Device CurrentDevice;
/*       */   protected static Runnable DeviceFinder;
/*       */ 
/*       */   static
/*       */   {
/*       */     try
/*       */     {
/*       */       Class.forName("org.eclipse.swt.widgets.Display");
/*       */     }
/*       */     catch (ClassNotFoundException localClassNotFoundException)
/*       */     {
/*       */     }
/*       */   }
/*       */ 
/*       */   static synchronized Device getDevice()
/*       */   {
/*       */     if (DeviceFinder != null)
/*       */       DeviceFinder.run();
/*       */     Device localDevice = CurrentDevice;
/*       */     CurrentDevice = null;
/*       */     return localDevice;
/*       */   }
/*       */ 
/*       */   public Device()
/*       */   {
/*       */     this(null);
/*       */   }
/*       */ 
/*       */   public Device(DeviceData paramDeviceData)
/*       */   {
/*       */     synchronized (Device.class)
/*       */     {
/*       */       if (paramDeviceData != null)
/*       */       {
/*       */         this.debug = paramDeviceData.debug;
/*       */         this.tracking = paramDeviceData.tracking;
/*       */       }
/*       */       if (this.tracking)
/*       */       {
/*       */         this.errors = new Error[''];
/*       */         this.objects = new Object[''];
/*       */         this.trackingLock = new Object();
/*       */       }
/*       */       create(paramDeviceData);
/*       */       init();
/*       */     }
/*       */   }
/*       */ 
/*       */   void addFont(String paramString)
/*       */   {
/*       */     if (this.loadedFonts == null)
/*       */       this.loadedFonts = new String[4];
/*       */     int i = this.loadedFonts.length;
/*       */     for (int j = 0; j < i; j++)
/*       */       if (paramString.equals(this.loadedFonts[j]))
/*       */         return;
/*       */     for (j = 0; j < i; j++)
/*       */       if (this.loadedFonts[j] == null)
/*       */         break;
/*       */     if (j == i)
/*       */     {
/*       */       String[] arrayOfString = new String[i + 4];
/*       */       System.arraycopy(this.loadedFonts, 0, arrayOfString, 0, i);
/*       */       this.loadedFonts = arrayOfString;
/*       */     }
/*       */     this.loadedFonts[j] = paramString;
/*       */   }
/*       */ 
/*       */   protected void checkDevice()
/*       */   {
/*       */     if (this.disposed)
/*       */       SWT.error(45);
/*       */   }
/*       */ 
/*       */   // ERROR //
/*       */   void checkGDIP()
/*       */   {
/*       */     // Byte code:
/*       */     //   0: aload_0
/*       */     //   1: getfield 140	org/eclipse/swt/graphics/Device:gdipToken	[I
/*       */     //   4: ifnull +4 -> 8
/*       */     //   7: return
/*       */     //   8: iconst_0
/*       */     //   9: istore_1
/*       */     //   10: getstatic 142	org/eclipse/swt/internal/win32/OS:IsWinCE	Z
/*       */     //   13: ifne +8 -> 21
/*       */     //   16: iconst_1
/*       */     //   17: invokestatic 147	org/eclipse/swt/internal/win32/OS:SetErrorMode	(I)I
/*       */     //   20: istore_1
/*       */     //   21: iconst_1
/*       */     //   22: newarray int
/*       */     //   24: astore_2
/*       */     //   25: new 151	org/eclipse/swt/internal/gdip/GdiplusStartupInput
/*       */     //   28: dup
/*       */     //   29: invokespecial 153	org/eclipse/swt/internal/gdip/GdiplusStartupInput:<init>	()V
/*       */     //   32: astore_3
/*       */     //   33: aload_3
/*       */     //   34: iconst_1
/*       */     //   35: putfield 154	org/eclipse/swt/internal/gdip/GdiplusStartupInput:GdiplusVersion	I
/*       */     //   38: aload_2
/*       */     //   39: aload_3
/*       */     //   40: iconst_0
/*       */     //   41: invokestatic 157	org/eclipse/swt/internal/gdip/Gdip:GdiplusStartup	([ILorg/eclipse/swt/internal/gdip/GdiplusStartupInput;I)I
/*       */     //   44: ifne +148 -> 192
/*       */     //   47: aload_0
/*       */     //   48: aload_2
/*       */     //   49: putfield 140	org/eclipse/swt/graphics/Device:gdipToken	[I
/*       */     //   52: aload_0
/*       */     //   53: getfield 116	org/eclipse/swt/graphics/Device:loadedFonts	[Ljava/lang/String;
/*       */     //   56: ifnull +136 -> 192
/*       */     //   59: aload_0
/*       */     //   60: invokestatic 163	org/eclipse/swt/internal/gdip/Gdip:PrivateFontCollection_new	()I
/*       */     //   63: putfield 167	org/eclipse/swt/graphics/Device:fontCollection	I
/*       */     //   66: aload_0
/*       */     //   67: getfield 167	org/eclipse/swt/graphics/Device:fontCollection	I
/*       */     //   70: ifne +7 -> 77
/*       */     //   73: iconst_2
/*       */     //   74: invokestatic 133	org/eclipse/swt/SWT:error	(I)V
/*       */     //   77: iconst_0
/*       */     //   78: istore 4
/*       */     //   80: goto +59 -> 139
/*       */     //   83: aload_0
/*       */     //   84: getfield 116	org/eclipse/swt/graphics/Device:loadedFonts	[Ljava/lang/String;
/*       */     //   87: iload 4
/*       */     //   89: aaload
/*       */     //   90: astore 5
/*       */     //   92: aload 5
/*       */     //   94: ifnonnull +6 -> 100
/*       */     //   97: goto +52 -> 149
/*       */     //   100: aload 5
/*       */     //   102: invokevirtual 169	java/lang/String:length	()I
/*       */     //   105: istore 6
/*       */     //   107: iload 6
/*       */     //   109: iconst_1
/*       */     //   110: iadd
/*       */     //   111: newarray char
/*       */     //   113: astore 7
/*       */     //   115: aload 5
/*       */     //   117: iconst_0
/*       */     //   118: iload 6
/*       */     //   120: aload 7
/*       */     //   122: iconst_0
/*       */     //   123: invokevirtual 172	java/lang/String:getChars	(II[CI)V
/*       */     //   126: aload_0
/*       */     //   127: getfield 167	org/eclipse/swt/graphics/Device:fontCollection	I
/*       */     //   130: aload 7
/*       */     //   132: invokestatic 176	org/eclipse/swt/internal/gdip/Gdip:PrivateFontCollection_AddFontFile	(I[C)I
/*       */     //   135: pop
/*       */     //   136: iinc 4 1
/*       */     //   139: iload 4
/*       */     //   141: aload_0
/*       */     //   142: getfield 116	org/eclipse/swt/graphics/Device:loadedFonts	[Ljava/lang/String;
/*       */     //   145: arraylength
/*       */     //   146: if_icmplt -63 -> 83
/*       */     //   149: aload_0
/*       */     //   150: aconst_null
/*       */     //   151: putfield 116	org/eclipse/swt/graphics/Device:loadedFonts	[Ljava/lang/String;
/*       */     //   154: goto +38 -> 192
/*       */     //   157: astore_2
/*       */     //   158: bipush 16
/*       */     //   160: aload_2
/*       */     //   161: ldc 180
/*       */     //   163: invokestatic 182	org/eclipse/swt/SWT:error	(ILjava/lang/Throwable;Ljava/lang/String;)V
/*       */     //   166: goto +26 -> 192
/*       */     //   169: astore 9
/*       */     //   171: jsr +6 -> 177
/*       */     //   174: aload 9
/*       */     //   176: athrow
/*       */     //   177: astore 8
/*       */     //   179: getstatic 142	org/eclipse/swt/internal/win32/OS:IsWinCE	Z
/*       */     //   182: ifne +8 -> 190
/*       */     //   185: iload_1
/*       */     //   186: invokestatic 147	org/eclipse/swt/internal/win32/OS:SetErrorMode	(I)I
/*       */     //   189: pop
/*       */     //   190: ret 8
/*       */     //   192: jsr -15 -> 177
/*       */     //   195: return
/*       */     //
/*       */     // Exception table:
/*       */     //   from	to	target	type
/*       */     //   21	154	157	java/lang/Throwable
/*       */     //   21	166	169	finally
/*       */     //   192	195	169	finally
/*       */   }
/*       */ 
/*       */   protected void create(DeviceData paramDeviceData)
/*       */   {
/*       */   }
/*       */ 
/*       */   int computePixels(float paramFloat)
/*       */   {
/*       */     int i = internal_new_GC(null);
/*       */     int j = -(int)(0.5F + paramFloat * OS.GetDeviceCaps(i, 90) / 72.0F);
/*       */     internal_dispose_GC(i, null);
/*       */     return j;
/*       */   }
/*       */ 
/*       */   float computePoints(LOGFONT paramLOGFONT, int paramInt)
/*       */   {
/*       */     int i = internal_new_GC(null);
/*       */     int j = OS.GetDeviceCaps(i, 90);
/*       */     int k = 0;
/*       */     if (paramLOGFONT.lfHeight > 0)
/*       */     {
/*       */       int m = OS.SelectObject(i, paramInt);
/*       */       TEXTMETRICA localTEXTMETRICA = OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA();
/*       */       OS.GetTextMetrics(i, localTEXTMETRICA);
/*       */       OS.SelectObject(i, m);
/*       */       k = paramLOGFONT.lfHeight - localTEXTMETRICA.tmInternalLeading;
/*       */     }
/*       */     else
/*       */     {
/*       */       k = -paramLOGFONT.lfHeight;
/*       */     }
/*       */     internal_dispose_GC(i, null);
/*       */     return k * 72.0F / j;
/*       */   }
/*       */ 
/*       */   protected void destroy()
/*       */   {
/*       */   }
/*       */ 
/*       */   public void dispose()
/*       */   {
/*       */     synchronized (Device.class)
/*       */     {
/*       */       if (isDisposed())
/*       */         return;
/*       */       checkDevice();
/*       */       release();
/*       */       destroy();
/*       */       this.disposed = true;
/*       */       if (this.tracking)
/*       */         synchronized (this.trackingLock)
/*       */         {
/*       */           printErrors();
/*       */           this.objects = null;
/*       */           this.errors = null;
/*       */           this.trackingLock = null;
/*       */         }
/*       */     }
/*       */   }
/*       */ 
/*       */   void dispose_Object(Object paramObject)
/*       */   {
/*       */     synchronized (this.trackingLock)
/*       */     {
/*       */       for (int i = 0; i < this.objects.length; i++)
/*       */         if (this.objects[i] == paramObject)
/*       */         {
/*       */           this.objects[i] = null;
/*       */           this.errors[i] = null;
/*       */           return;
/*       */         }
/*       */     }
/*       */   }
/*       */ 
/*       */   int EnumFontFamProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*       */   {
/*       */     int i = (paramInt3 & 0x1) == 0 ? 1 : 0;
/*       */     int j = paramInt4 == 1 ? 1 : 0;
/*       */     if (i == j)
/*       */     {
/*       */       if (this.nFonts == this.logFonts.length)
/*       */       {
/*       */         localObject = new LOGFONT[this.logFonts.length + 128];
/*       */         System.arraycopy(this.logFonts, 0, localObject, 0, this.nFonts);
/*       */         this.logFonts = ((LOGFONT[])localObject);
/*       */         int[] arrayOfInt = new int[localObject.length];
/*       */         System.arraycopy(this.pixels, 0, arrayOfInt, 0, this.nFonts);
/*       */         this.pixels = arrayOfInt;
/*       */       }
/*       */       Object localObject = this.logFonts[this.nFonts];
/*       */       if (localObject == null)
/*       */         localObject = OS.IsUnicode ? new LOGFONTW() : new LOGFONTA();
/*       */       OS.MoveMemory((LOGFONT)localObject, paramInt1, LOGFONT.sizeof);
/*       */       this.logFonts[this.nFonts] = localObject;
/*       */       if (((LOGFONT)localObject).lfHeight > 0)
/*       */       {
/*       */         OS.MoveMemory(this.metrics, paramInt2, TEXTMETRIC.sizeof);
/*       */         this.pixels[this.nFonts] = (((LOGFONT)localObject).lfHeight - this.metrics.tmInternalLeading);
/*       */       }
/*       */       else
/*       */       {
/*       */         this.pixels[this.nFonts] = (-((LOGFONT)localObject).lfHeight);
/*       */       }
/*       */       this.nFonts += 1;
/*       */     }
/*       */     return 1;
/*       */   }
/*       */ 
/*       */   public Rectangle getBounds()
/*       */   {
/*       */     checkDevice();
/*       */     int i = internal_new_GC(null);
/*       */     int j = OS.GetDeviceCaps(i, 8);
/*       */     int k = OS.GetDeviceCaps(i, 10);
/*       */     internal_dispose_GC(i, null);
/*       */     return new Rectangle(0, 0, j, k);
/*       */   }
/*       */ 
/*       */   public DeviceData getDeviceData()
/*       */   {
/*       */     checkDevice();
/*       */     DeviceData localDeviceData = new DeviceData();
/*       */     localDeviceData.debug = this.debug;
/*       */     localDeviceData.tracking = this.tracking;
/*       */     if (this.tracking)
/*       */       synchronized (this.trackingLock)
/*       */       {
/*       */         int i = 0;
/*       */         int j = this.objects.length;
/*       */         for (int k = 0; k < j; k++)
/*       */           if (this.objects[k] != null)
/*       */             i++;
/*       */         k = 0;
/*       */         localDeviceData.objects = new Object[i];
/*       */         localDeviceData.errors = new Error[i];
/*       */         for (int m = 0; m < j; m++)
/*       */           if (this.objects[m] != null)
/*       */           {
/*       */             localDeviceData.objects[k] = this.objects[m];
/*       */             localDeviceData.errors[k] = this.errors[m];
/*       */             k++;
/*       */           }
/*       */       }
/*       */     localDeviceData.objects = new Object[0];
/*       */     localDeviceData.errors = new Error[0];
/*       */     return localDeviceData;
/*       */   }
/*       */ 
/*       */   public Rectangle getClientArea()
/*       */   {
/*       */     return getBounds();
/*       */   }
/*       */ 
/*       */   public int getDepth()
/*       */   {
/*       */     checkDevice();
/*       */     int i = internal_new_GC(null);
/*       */     int j = OS.GetDeviceCaps(i, 12);
/*       */     int k = OS.GetDeviceCaps(i, 14);
/*       */     internal_dispose_GC(i, null);
/*       */     return j * k;
/*       */   }
/*       */ 
/*       */   public Point getDPI()
/*       */   {
/*       */     checkDevice();
/*       */     int i = internal_new_GC(null);
/*       */     int j = OS.GetDeviceCaps(i, 88);
/*       */     int k = OS.GetDeviceCaps(i, 90);
/*       */     internal_dispose_GC(i, null);
/*       */     return new Point(j, k);
/*       */   }
/*       */ 
/*       */   public FontData[] getFontList(String paramString, boolean paramBoolean)
/*       */   {
/*       */     checkDevice();
/*       */     Callback localCallback = new Callback(this, "EnumFontFamProc", 4);
/*       */     int i = localCallback.getAddress();
/*       */     if (i == 0)
/*       */       SWT.error(3);
/*       */     this.metrics = (OS.IsUnicode ? new TEXTMETRICW() : new TEXTMETRICA());
/*       */     this.pixels = new int[this.nFonts];
/*       */     this.logFonts = new LOGFONT[this.nFonts];
/*       */     for (int j = 0; j < this.logFonts.length; j++)
/*       */       this.logFonts[j] = (OS.IsUnicode ? new LOGFONTW() : new LOGFONTA());
/*       */     this.nFonts = 0;
/*       */     j = 0;
/*       */     int k = internal_new_GC(null);
/*       */     if (paramString == null)
/*       */     {
/*       */       OS.EnumFontFamilies(k, null, i, paramBoolean ? 1 : 0);
/*       */       j = this.nFonts;
/*       */       for (int m = 0; m < j; m++)
/*       */       {
/*       */         LOGFONT localLOGFONT = this.logFonts[m];
/*       */         if (OS.IsUnicode)
/*       */           OS.EnumFontFamiliesW(k, ((LOGFONTW)localLOGFONT).lfFaceName, i, paramBoolean ? 1 : 0);
/*       */         else
/*       */           OS.EnumFontFamiliesA(k, ((LOGFONTA)localLOGFONT).lfFaceName, i, paramBoolean ? 1 : 0);
/*       */       }
/*       */     }
/*       */     else
/*       */     {
/*       */       TCHAR localTCHAR = new TCHAR(0, paramString, true);
/*       */       OS.EnumFontFamilies(k, localTCHAR, i, paramBoolean ? 1 : 0);
/*       */     }
/*       */     int n = OS.GetDeviceCaps(k, 90);
/*       */     internal_dispose_GC(k, null);
/*       */     int i1 = 0;
/*       */     Object localObject = new FontData[this.nFonts - j];
/*       */     for (int i2 = j; i2 < this.nFonts; i2++)
/*       */     {
/*       */       FontData localFontData = FontData.win32_new(this.logFonts[i2], this.pixels[i2] * 72.0F / n);
/*       */       for (int i3 = 0; i3 < i1; i3++)
/*       */         if (localFontData.equals(localObject[i3]))
/*       */           break;
/*       */       if (i3 == i1)
/*       */         localObject[(i1++)] = localFontData;
/*       */     }
/*       */     if (i1 != localObject.length)
/*       */     {
/*       */       FontData[] arrayOfFontData = new FontData[i1];
/*       */       System.arraycopy(localObject, 0, arrayOfFontData, 0, i1);
/*       */       localObject = arrayOfFontData;
/*       */     }
/*       */     localCallback.dispose();
/*       */     this.logFonts = null;
/*       */     this.pixels = null;
/*       */     this.metrics = null;
/*       */     return localObject;
/*       */   }
/*       */ 
/*       */   String getLastError()
/*       */   {
/*       */     int i = OS.GetLastError();
/*       */     if (i == 0)
/*       */       return "";
/*       */     return " [GetLastError=0x" + Integer.toHexString(i) + "]";
/*       */   }
/*       */ 
/*       */   String getLastErrorText()
/*       */   {
/*       */     int i = OS.GetLastError();
/*       */     if (i == 0)
/*       */       return "";
/*       */     int[] arrayOfInt = new int[1];
/*       */     int j = 4864;
/*       */     int k = OS.FormatMessage(j, 0, i, 1024, arrayOfInt, 0, 0);
/*       */     if (k == 0)
/*       */       return " [GetLastError=0x" + Integer.toHexString(i) + "]";
/*       */     TCHAR localTCHAR = new TCHAR(0, k);
/*       */     OS.MoveMemory(localTCHAR, arrayOfInt[0], k * TCHAR.sizeof);
/*       */     if (arrayOfInt[0] != 0)
/*       */       OS.LocalFree(arrayOfInt[0]);
/*       */     return localTCHAR.toString(0, k);
/*       */   }
/*       */ 
/*       */   public Color getSystemColor(int paramInt)
/*       */   {
/*       */     checkDevice();
/*       */     int i = 0;
/*       */     switch (paramInt)
/*       */     {
/*       */     case 1:
/*       */       i = 16777215;
/*       */       break;
/*       */     case 2:
/*       */       i = 0;
/*       */       break;
/*       */     case 3:
/*       */       i = 255;
/*       */       break;
/*       */     case 4:
/*       */       i = 128;
/*       */       break;
/*       */     case 5:
/*       */       i = 65280;
/*       */       break;
/*       */     case 6:
/*       */       i = 32768;
/*       */       break;
/*       */     case 7:
/*       */       i = 65535;
/*       */       break;
/*       */     case 8:
/*       */       i = 32896;
/*       */       break;
/*       */     case 9:
/*       */       i = 16711680;
/*       */       break;
/*       */     case 10:
/*       */       i = 8388608;
/*       */       break;
/*       */     case 11:
/*       */       i = 16711935;
/*       */       break;
/*       */     case 12:
/*       */       i = 8388736;
/*       */       break;
/*       */     case 13:
/*       */       i = 16776960;
/*       */       break;
/*       */     case 14:
/*       */       i = 8421376;
/*       */       break;
/*       */     case 15:
/*       */       i = 12632256;
/*       */       break;
/*       */     case 16:
/*       */       i = 8421504;
/*       */     }
/*       */     return Color.win32_new(this, i);
/*       */   }
/*       */ 
/*       */   public Font getSystemFont()
/*       */   {
/*       */     checkDevice();
/*       */     int i = OS.GetStockObject(13);
/*       */     return Font.win32_new(this, i);
/*       */   }
/*       */ 
/*       */   public boolean getWarnings()
/*       */   {
/*       */     checkDevice();
/*       */     return false;
/*       */   }
/*       */ 
/*       */   protected void init()
/*       */   {
/*       */     if ((this.debug) && (!OS.IsWinCE))
/*       */       OS.GdiSetBatchLimit(1);
/*       */     this.systemFont = getSystemFont();
/*       */     if (!OS.IsWinCE)
/*       */     {
/*       */       int[] arrayOfInt1 = new int[1];
/*       */       int[] arrayOfInt2 = new int[1];
/*       */       OS.ScriptGetProperties(arrayOfInt1, arrayOfInt2);
/*       */       this.scripts = new int[arrayOfInt2[0]];
/*       */       OS.MoveMemory(this.scripts, arrayOfInt1[0], this.scripts.length * OS.PTR_SIZEOF);
/*       */     }
/*       */     int i = internal_new_GC(null);
/*       */     int j = OS.GetDeviceCaps(i, 38);
/*       */     int k = OS.GetDeviceCaps(i, 12);
/*       */     int m = OS.GetDeviceCaps(i, 14);
/*       */     k *= m;
/*       */     if (((j & 0x100) == 0) || (k != 8))
/*       */     {
/*       */       internal_dispose_GC(i, null);
/*       */       return;
/*       */     }
/*       */     int n = OS.GetDeviceCaps(i, 106);
/*       */     int i1 = OS.GetDeviceCaps(i, 104);
/*       */     if ((OS.IsWinCE) && (n == 0) && (i1 >= 20))
/*       */       n = 20;
/*       */     this.colorRefCount = new int[i1];
/*       */     byte[] arrayOfByte1 = new byte[4 + 4 * i1];
/*       */     arrayOfByte1[0] = 0;
/*       */     arrayOfByte1[1] = 3;
/*       */     arrayOfByte1[2] = 0;
/*       */     arrayOfByte1[3] = 1;
/*       */     byte[] arrayOfByte2 = new byte[4 * i1];
/*       */     OS.GetSystemPaletteEntries(i, 0, i1, arrayOfByte2);
/*       */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 4, 4 * i1);
/*       */     for (int i2 = 0; i2 < n / 2; i2++)
/*       */     {
/*       */       this.colorRefCount[i2] = 1;
/*       */       this.colorRefCount[(i1 - 1 - i2)] = 1;
/*       */     }
/*       */     internal_dispose_GC(i, null);
/*       */     this.hPalette = OS.CreatePalette(arrayOfByte1);
/*       */   }
/*       */ 
/*       */   public abstract int internal_new_GC(GCData paramGCData);
/*       */ 
/*       */   public abstract void internal_dispose_GC(int paramInt, GCData paramGCData);
/*       */ 
/*       */   public boolean isDisposed()
/*       */   {
/*       */     synchronized (Device.class)
/*       */     {
/*       */       return this.disposed;
/*       */     }
/*       */   }
/*       */ 
/*       */   public boolean loadFont(String paramString)
/*       */   {
/*       */     checkDevice();
/*       */     if (paramString == null)
/*       */       SWT.error(4);
/*       */     if ((OS.IsWinNT) && (OS.WIN32_VERSION >= OS.VERSION(4, 10)))
/*       */     {
/*       */       TCHAR localTCHAR = new TCHAR(0, paramString, true);
/*       */       boolean bool = OS.AddFontResourceEx(localTCHAR, 16, 0) != 0;
/*       */       if (bool)
/*       */         if (this.gdipToken != null)
/*       */         {
/*       */           if (this.fontCollection == 0)
/*       */           {
/*       */             this.fontCollection = Gdip.PrivateFontCollection_new();
/*       */             if (this.fontCollection == 0)
/*       */               SWT.error(2);
/*       */           }
/*       */           int i = paramString.length();
/*       */           char[] arrayOfChar = new char[i + 1];
/*       */           paramString.getChars(0, i, arrayOfChar, 0);
/*       */           Gdip.PrivateFontCollection_AddFontFile(this.fontCollection, arrayOfChar);
/*       */         }
/*       */         else
/*       */         {
/*       */           addFont(paramString);
/*       */         }
/*       */       return bool;
/*       */     }
/*       */     return false;
/*       */   }
/*       */ 
/*       */   void new_Object(Object paramObject)
/*       */   {
/*       */     synchronized (this.trackingLock)
/*       */     {
/*       */       for (int i = 0; i < this.objects.length; i++)
/*       */         if (this.objects[i] == null)
/*       */         {
/*       */           this.objects[i] = paramObject;
/*       */           this.errors[i] = new Error();
/*       */           return;
/*       */         }
/*       */       Object[] arrayOfObject = new Object[this.objects.length + 128];
/*       */       System.arraycopy(this.objects, 0, arrayOfObject, 0, this.objects.length);
/*       */       arrayOfObject[this.objects.length] = paramObject;
/*       */       this.objects = arrayOfObject;
/*       */       Error[] arrayOfError = new Error[this.errors.length + 128];
/*       */       System.arraycopy(this.errors, 0, arrayOfError, 0, this.errors.length);
/*       */       arrayOfError[this.errors.length] = new Error();
/*       */       this.errors = arrayOfError;
/*       */     }
/*       */   }
/*       */ 
/*       */   void printErrors()
/*       */   {
/*       */     if (!DEBUG)
/*       */       return;
/*       */     if (this.tracking)
/*       */       synchronized (this.trackingLock)
/*       */       {
/*       */         if ((this.objects == null) || (this.errors == null))
/*       */           return;
/*       */         int i = 0;
/*       */         int j = 0;
/*       */         int k = 0;
/*       */         int m = 0;
/*       */         int n = 0;
/*       */         int i1 = 0;
/*       */         int i2 = 0;
/*       */         int i3 = 0;
/*       */         int i4 = 0;
/*       */         int i5 = 0;
/*       */         int i6 = 0;
/*       */         for (int i7 = 0; i7 < this.objects.length; i7++)
/*       */         {
/*       */           Object localObject1 = this.objects[i7];
/*       */           if (localObject1 != null)
/*       */           {
/*       */             i++;
/*       */             if ((localObject1 instanceof Color))
/*       */               j++;
/*       */             if ((localObject1 instanceof Cursor))
/*       */               k++;
/*       */             if ((localObject1 instanceof Font))
/*       */               m++;
/*       */             if ((localObject1 instanceof GC))
/*       */               n++;
/*       */             if ((localObject1 instanceof Image))
/*       */               i1++;
/*       */             if ((localObject1 instanceof Path))
/*       */               i2++;
/*       */             if ((localObject1 instanceof Pattern))
/*       */               i3++;
/*       */             if ((localObject1 instanceof Region))
/*       */               i4++;
/*       */             if ((localObject1 instanceof TextLayout))
/*       */               i5++;
/*       */             if ((localObject1 instanceof Transform))
/*       */               i6++;
/*       */           }
/*       */         }
/*       */         if (i != 0)
/*       */         {
/*       */           String str = "Summary: ";
/*       */           if (j != 0)
/*       */             str = str + j + " Color(s), ";
/*       */           if (k != 0)
/*       */             str = str + k + " Cursor(s), ";
/*       */           if (m != 0)
/*       */             str = str + m + " Font(s), ";
/*       */           if (n != 0)
/*       */             str = str + n + " GC(s), ";
/*       */           if (i1 != 0)
/*       */             str = str + i1 + " Image(s), ";
/*       */           if (i2 != 0)
/*       */             str = str + i2 + " Path(s), ";
/*       */           if (i3 != 0)
/*       */             str = str + i3 + " Pattern(s), ";
/*       */           if (i4 != 0)
/*       */             str = str + i4 + " Region(s), ";
/*       */           if (i5 != 0)
/*       */             str = str + i5 + " TextLayout(s), ";
/*       */           if (i6 != 0)
/*       */             str = str + i6 + " Transforms(s), ";
/*       */           if (str.length() != 0)
/*       */           {
/*       */             str = str.substring(0, str.length() - 2);
/*       */             System.err.println(str);
/*       */           }
/*       */           for (int i8 = 0; i8 < this.errors.length; i8++)
/*       */             if (this.errors[i8] != null)
/*       */               this.errors[i8].printStackTrace(System.err);
/*       */         }
/*       */       }
/*       */   }
/*       */ 
/*       */   protected void release()
/*       */   {
/*       */     if (this.gdipToken != null)
/*       */     {
/*       */       if (this.fontCollection != 0)
/*       */         Gdip.PrivateFontCollection_delete(this.fontCollection);
/*       */       this.fontCollection = 0;
/*       */       Gdip.GdiplusShutdown(this.gdipToken[0]);
/*       */     }
/*       */     this.gdipToken = null;
/*       */     this.scripts = null;
/*       */     if (this.hPalette != 0)
/*       */       OS.DeleteObject(this.hPalette);
/*       */     this.hPalette = 0;
/*       */     this.colorRefCount = null;
/*       */     this.logFonts = null;
/*       */     this.nFonts = 0;
/*       */   }
/*       */ 
/*       */   public void setWarnings(boolean paramBoolean)
/*       */   {
/*       */     checkDevice();
/*       */   }
/*       */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.graphics.Device
 * JD-Core Version:    0.6.2
 */