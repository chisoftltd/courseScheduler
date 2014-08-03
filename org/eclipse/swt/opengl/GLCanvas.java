package org.eclipse.swt.opengl;

import org.eclipse.swt.SWT;
import org.eclipse.swt.internal.opengl.win32.PIXELFORMATDESCRIPTOR;
import org.eclipse.swt.internal.opengl.win32.WGL;
import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class GLCanvas extends Canvas
{
  int context;
  int pixelFormat;
  static final String USE_OWNDC_KEY = "org.eclipse.swt.internal.win32.useOwnDC";

  public GLCanvas(Composite paramComposite, int paramInt, GLData paramGLData)
  {
    super(paramComposite, checkStyle(paramComposite, paramInt));
    paramComposite.getDisplay().setData("org.eclipse.swt.internal.win32.useOwnDC", new Boolean(false));
    if (paramGLData == null)
      SWT.error(4);
    PIXELFORMATDESCRIPTOR localPIXELFORMATDESCRIPTOR = new PIXELFORMATDESCRIPTOR();
    localPIXELFORMATDESCRIPTOR.nSize = 40;
    localPIXELFORMATDESCRIPTOR.nVersion = 1;
    localPIXELFORMATDESCRIPTOR.dwFlags = 36;
    localPIXELFORMATDESCRIPTOR.dwLayerMask = 0;
    localPIXELFORMATDESCRIPTOR.iPixelType = 0;
    if (paramGLData.doubleBuffer)
      localPIXELFORMATDESCRIPTOR.dwFlags |= 1;
    if (paramGLData.stereo)
      localPIXELFORMATDESCRIPTOR.dwFlags |= 2;
    localPIXELFORMATDESCRIPTOR.cRedBits = ((byte)paramGLData.redSize);
    localPIXELFORMATDESCRIPTOR.cGreenBits = ((byte)paramGLData.greenSize);
    localPIXELFORMATDESCRIPTOR.cBlueBits = ((byte)paramGLData.blueSize);
    localPIXELFORMATDESCRIPTOR.cAlphaBits = ((byte)paramGLData.alphaSize);
    localPIXELFORMATDESCRIPTOR.cDepthBits = ((byte)paramGLData.depthSize);
    localPIXELFORMATDESCRIPTOR.cStencilBits = ((byte)paramGLData.stencilSize);
    localPIXELFORMATDESCRIPTOR.cAccumRedBits = ((byte)paramGLData.accumRedSize);
    localPIXELFORMATDESCRIPTOR.cAccumGreenBits = ((byte)paramGLData.accumGreenSize);
    localPIXELFORMATDESCRIPTOR.cAccumBlueBits = ((byte)paramGLData.accumBlueSize);
    localPIXELFORMATDESCRIPTOR.cAccumAlphaBits = ((byte)paramGLData.accumAlphaSize);
    localPIXELFORMATDESCRIPTOR.cAccumBits = ((byte)(localPIXELFORMATDESCRIPTOR.cAccumRedBits + localPIXELFORMATDESCRIPTOR.cAccumGreenBits + localPIXELFORMATDESCRIPTOR.cAccumBlueBits + localPIXELFORMATDESCRIPTOR.cAccumAlphaBits));
    int i = OS.GetDC(this.handle);
    this.pixelFormat = WGL.ChoosePixelFormat(i, localPIXELFORMATDESCRIPTOR);
    if ((this.pixelFormat == 0) || (!WGL.SetPixelFormat(i, this.pixelFormat, localPIXELFORMATDESCRIPTOR)))
    {
      OS.ReleaseDC(this.handle, i);
      dispose();
      SWT.error(38);
    }
    this.context = WGL.wglCreateContext(i);
    if (this.context == 0)
    {
      OS.ReleaseDC(this.handle, i);
      SWT.error(2);
    }
    OS.ReleaseDC(this.handle, i);
    if (paramGLData.shareContext != null)
      WGL.wglShareLists(paramGLData.shareContext.context, this.context);
    Listener local1 = new Listener()
    {
      public void handleEvent(Event paramAnonymousEvent)
      {
        switch (paramAnonymousEvent.type)
        {
        case 12:
          WGL.wglDeleteContext(GLCanvas.this.context);
        }
      }
    };
    addListener(12, local1);
  }

  static int checkStyle(Composite paramComposite, int paramInt)
  {
    if ((paramComposite != null) && (!OS.IsWinCE) && (OS.WIN32_VERSION >= OS.VERSION(6, 0)))
      paramComposite.getDisplay().setData("org.eclipse.swt.internal.win32.useOwnDC", new Boolean(true));
    return paramInt;
  }

  public GLData getGLData()
  {
    checkWidget();
    GLData localGLData = new GLData();
    PIXELFORMATDESCRIPTOR localPIXELFORMATDESCRIPTOR = new PIXELFORMATDESCRIPTOR();
    localPIXELFORMATDESCRIPTOR.nSize = 40;
    int i = OS.GetDC(this.handle);
    WGL.DescribePixelFormat(i, this.pixelFormat, 40, localPIXELFORMATDESCRIPTOR);
    OS.ReleaseDC(this.handle, i);
    localGLData.doubleBuffer = ((localPIXELFORMATDESCRIPTOR.dwFlags & 0x1) != 0);
    localGLData.stereo = ((localPIXELFORMATDESCRIPTOR.dwFlags & 0x2) != 0);
    localGLData.redSize = localPIXELFORMATDESCRIPTOR.cRedBits;
    localGLData.greenSize = localPIXELFORMATDESCRIPTOR.cGreenBits;
    localGLData.blueSize = localPIXELFORMATDESCRIPTOR.cBlueBits;
    localGLData.alphaSize = localPIXELFORMATDESCRIPTOR.cAlphaBits;
    localGLData.depthSize = localPIXELFORMATDESCRIPTOR.cDepthBits;
    localGLData.stencilSize = localPIXELFORMATDESCRIPTOR.cStencilBits;
    localGLData.accumRedSize = localPIXELFORMATDESCRIPTOR.cAccumRedBits;
    localGLData.accumGreenSize = localPIXELFORMATDESCRIPTOR.cAccumGreenBits;
    localGLData.accumBlueSize = localPIXELFORMATDESCRIPTOR.cAccumBlueBits;
    localGLData.accumAlphaSize = localPIXELFORMATDESCRIPTOR.cAccumAlphaBits;
    return localGLData;
  }

  public boolean isCurrent()
  {
    checkWidget();
    return WGL.wglGetCurrentContext() == this.context;
  }

  public void setCurrent()
  {
    checkWidget();
    if (WGL.wglGetCurrentContext() == this.context)
      return;
    int i = OS.GetDC(this.handle);
    WGL.wglMakeCurrent(i, this.context);
    OS.ReleaseDC(this.handle, i);
  }

  public void swapBuffers()
  {
    checkWidget();
    int i = OS.GetDC(this.handle);
    WGL.SwapBuffers(i);
    OS.ReleaseDC(this.handle, i);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.opengl.GLCanvas
 * JD-Core Version:    0.6.2
 */