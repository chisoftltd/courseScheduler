package org.eclipse.swt.internal.image;

class PngFileReadState
{
  boolean readIHDR;
  boolean readPLTE;
  boolean readIDAT;
  boolean readIEND;
  boolean readTRNS;
  boolean readPixelData;
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngFileReadState
 * JD-Core Version:    0.6.2
 */