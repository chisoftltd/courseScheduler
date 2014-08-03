package org.eclipse.swt.printing;

import org.eclipse.swt.graphics.DeviceData;

public final class PrinterData extends DeviceData
{
  public String driver;
  public String name;
  public int scope = 0;
  public int startPage = 1;
  public int endPage = 1;
  public boolean printToFile = false;
  public String fileName;
  public int copyCount = 1;
  public boolean collate = false;
  public int orientation = 1;
  public static final int ALL_PAGES = 0;
  public static final int PAGE_RANGE = 1;
  public static final int SELECTION = 2;
  public static final int PORTRAIT = 1;
  public static final int LANDSCAPE = 2;
  byte[] otherData;

  public PrinterData()
  {
  }

  public PrinterData(String paramString1, String paramString2)
  {
    this.driver = paramString1;
    this.name = paramString2;
  }

  public String toString()
  {
    return "PrinterData {driver = " + this.driver + ", name = " + this.name + "}";
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.printing.PrinterData
 * JD-Core Version:    0.6.2
 */