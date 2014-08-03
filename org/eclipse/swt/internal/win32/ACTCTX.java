package org.eclipse.swt.internal.win32;

public class ACTCTX
{
  public int cbSize;
  public int dwFlags;
  public int lpSource;
  public short wProcessorArchitecture;
  public short wLangId;
  public int lpAssemblyDirectory;
  public int lpResourceName;
  public int lpApplicationName;
  public int hModule;
  public static final int sizeof = OS.ACTCTX_sizeof();
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.ACTCTX
 * JD-Core Version:    0.6.2
 */