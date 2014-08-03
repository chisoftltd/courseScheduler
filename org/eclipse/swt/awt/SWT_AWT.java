package org.eclipse.swt.awt;

import java.awt.Canvas;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.Compatibility;
import org.eclipse.swt.internal.Library;
import org.eclipse.swt.internal.win32.MSG;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class SWT_AWT
{
  public static String embeddedFrameClass;
  static String EMBEDDED_FRAME_KEY = "org.eclipse.swt.awt.SWT_AWT.embeddedFrame";
  static boolean loaded;
  static boolean swingInitialized;

  static final native int getAWTHandle(Canvas paramCanvas);

  static synchronized void loadLibrary()
  {
    if (loaded)
      return;
    loaded = true;
    Toolkit.getDefaultToolkit();
    try
    {
      System.loadLibrary("jawt");
    }
    catch (Throwable localThrowable)
    {
    }
    Library.loadLibrary("swt-awt");
  }

  static synchronized void initializeSwing()
  {
    if (swingInitialized)
      return;
    swingInitialized = true;
    try
    {
      Class[] arrayOfClass = new Class[0];
      Object[] arrayOfObject = new Object[0];
      Class localClass = Class.forName("javax.swing.UIManager");
      Method localMethod = localClass.getMethod("getDefaults", arrayOfClass);
      if (localMethod != null)
        localMethod.invoke(localClass, arrayOfObject);
    }
    catch (Throwable localThrowable)
    {
    }
  }

  public static Frame getFrame(Composite paramComposite)
  {
    if (paramComposite == null)
      SWT.error(4);
    if ((paramComposite.getStyle() & 0x1000000) == 0)
      return null;
    return (Frame)paramComposite.getData(EMBEDDED_FRAME_KEY);
  }

  public static Frame new_Frame(Composite paramComposite)
  {
    if (paramComposite == null)
      SWT.error(4);
    if ((paramComposite.getStyle() & 0x1000000) == 0)
      SWT.error(5);
    int i = paramComposite.handle;
    Frame[] arrayOfFrame = new Frame[1];
    Throwable[] arrayOfThrowable = new Throwable[1];
    Runnable local1 = new Runnable()
    {
      private final Throwable[] val$exception;
      private final int val$handle;

      public void run()
      {
        try
        {
          Class localClass = null;
          try
          {
            String str = SWT_AWT.embeddedFrameClass != null ? SWT_AWT.embeddedFrameClass : "sun.awt.windows.WEmbeddedFrame";
            localClass = Class.forName(str);
          }
          catch (Throwable localThrowable1)
          {
            this.val$exception[0] = localThrowable1;
          }
          Object localObject1;
          while (true)
          {
            return;
            SWT_AWT.initializeSwing();
            localObject1 = null;
            Constructor localConstructor = null;
            try
            {
              localConstructor = localClass.getConstructor(new Class[] { Integer.TYPE });
              localObject1 = localConstructor.newInstance(new Object[] { new Integer(this.val$handle) });
            }
            catch (Throwable localThrowable3)
            {
              try
              {
                localConstructor = localClass.getConstructor(new Class[] { Long.TYPE });
                localObject1 = localConstructor.newInstance(new Object[] { new Long(this.val$handle) });
              }
              catch (Throwable localThrowable2)
              {
                this.val$exception[0] = localThrowable2;
              }
            }
          }
          Frame localFrame = (Frame)localObject1;
          localFrame.addNotify();
          try
          {
            localClass = Class.forName("sun.awt.windows.WComponentPeer");
            Field localField = localClass.getDeclaredField("winGraphicsConfig");
            localField.setAccessible(true);
            localField.set(localFrame.getPeer(), localFrame.getGraphicsConfiguration());
          }
          catch (Throwable localThrowable4)
          {
          }
          SWT_AWT.this[0] = localFrame;
        }
        finally
        {
          synchronized (SWT_AWT.this)
          {
            SWT_AWT.this.notify();
          }
        }
        jsr -29;
      }
    };
    if ((EventQueue.isDispatchThread()) || (paramComposite.getDisplay().getSyncThread() != null))
    {
      local1.run();
    }
    else
    {
      EventQueue.invokeLater(local1);
      OS.ReplyMessage(0);
      int j = 0;
      localObject1 = new MSG();
      int k = 4194306;
      while ((arrayOfFrame[0] == null) && (arrayOfThrowable[0] == null))
      {
        OS.PeekMessage((MSG)localObject1, 0, 0, 0, k);
        try
        {
          synchronized (arrayOfFrame)
          {
            arrayOfFrame.wait(50L);
          }
        }
        catch (InterruptedException localInterruptedException)
        {
          j = 1;
        }
      }
      if (j != 0)
        Compatibility.interrupt();
    }
    if (arrayOfThrowable[0] != null)
      SWT.error(20, arrayOfThrowable[0]);
    Frame localFrame = arrayOfFrame[0];
    paramComposite.setData(EMBEDDED_FRAME_KEY, localFrame);
    Object localObject1 = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 20:
          EventQueue.invokeLater(new SWT_AWT.3(this, SWT_AWT.this));
          break;
        case 19:
          EventQueue.invokeLater(new SWT_AWT.4(this, SWT_AWT.this));
        }
      }
    };
    Shell localShell = paramComposite.getShell();
    localShell.addListener(20, (Listener)localObject1);
    localShell.addListener(19, (Listener)localObject1);
    ??? = new Listener()
    {
      private final Listener val$shellListener;
      private final Frame val$frame;

      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          Shell localShell = SWT_AWT.this.getShell();
          localShell.removeListener(20, this.val$shellListener);
          localShell.removeListener(19, this.val$shellListener);
          SWT_AWT.this.setVisible(false);
          EventQueue.invokeLater(new SWT_AWT.6(this, this.val$frame));
          break;
        case 15:
        case 26:
          EventQueue.invokeLater(new SWT_AWT.7(this, this.val$frame));
          break;
        case 27:
          EventQueue.invokeLater(new SWT_AWT.8(this, this.val$frame));
        }
      }
    };
    if (Library.JAVA_VERSION < Library.JAVA_VERSION(1, 5, 0))
      paramComposite.addListener(26, (Listener)???);
    else
      paramComposite.addListener(15, (Listener)???);
    paramComposite.addListener(27, (Listener)???);
    paramComposite.addListener(12, (Listener)???);
    paramComposite.getDisplay().asyncExec(new Runnable()
    {
      private final Frame val$frame;

      public void run()
      {
        if (SWT_AWT.this.isDisposed())
          return;
        Rectangle localRectangle = SWT_AWT.this.getClientArea();
        EventQueue.invokeLater(new SWT_AWT.10(this, this.val$frame, localRectangle));
      }
    });
    return localFrame;
  }

  public static Shell new_Shell(Display paramDisplay, Canvas paramCanvas)
  {
    if (paramDisplay == null)
      SWT.error(4);
    if (paramCanvas == null)
      SWT.error(4);
    int i = 0;
    try
    {
      loadLibrary();
      i = getAWTHandle(paramCanvas);
    }
    catch (Throwable localThrowable)
    {
      SWT.error(20, localThrowable);
    }
    if (i == 0)
      SWT.error(5, null, " [peer not created]");
    Shell localShell = Shell.win32_new(paramDisplay, i);
    ComponentAdapter local11 = new ComponentAdapter()
    {
      private final Shell val$shell;
      private final Canvas val$parent;

      public void componentResized(ComponentEvent paramAnonymousComponentEvent)
      {
        SWT_AWT.this.syncExec(new SWT_AWT.12(this, this.val$shell, this.val$parent));
      }
    };
    paramCanvas.addComponentListener(local11);
    localShell.addListener(12, new Listener()
    {
      private final ComponentListener val$listener;

      public void handleEvent(Event paramAnonymousEvent)
      {
        SWT_AWT.this.removeComponentListener(this.val$listener);
      }
    });
    localShell.setVisible(true);
    return localShell;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.awt.SWT_AWT
 * JD-Core Version:    0.6.2
 */