package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

class PngPlteChunk extends PngChunk
{
  int paletteSize = this.length / 3;

  PngPlteChunk(PaletteData paramPaletteData)
  {
    super(paramPaletteData.getRGBs().length * 3);
    setType(TYPE_PLTE);
    setPaletteData(paramPaletteData);
    setCRC(computeCRC());
  }

  PngPlteChunk(byte[] paramArrayOfByte)
  {
    super(paramArrayOfByte);
  }

  int getChunkType()
  {
    return 1;
  }

  int getPaletteSize()
  {
    return this.paletteSize;
  }

  PaletteData getPaletteData()
  {
    RGB[] arrayOfRGB = new RGB[this.paletteSize];
    for (int i = 0; i < arrayOfRGB.length; i++)
    {
      int j = 8 + i * 3;
      int k = this.reference[j] & 0xFF;
      int m = this.reference[(j + 1)] & 0xFF;
      int n = this.reference[(j + 2)] & 0xFF;
      arrayOfRGB[i] = new RGB(k, m, n);
    }
    return new PaletteData(arrayOfRGB);
  }

  void setPaletteData(PaletteData paramPaletteData)
  {
    RGB[] arrayOfRGB = paramPaletteData.getRGBs();
    for (int i = 0; i < arrayOfRGB.length; i++)
    {
      int j = 8 + i * 3;
      this.reference[j] = ((byte)arrayOfRGB[i].red);
      this.reference[(j + 1)] = ((byte)arrayOfRGB[i].green);
      this.reference[(j + 2)] = ((byte)arrayOfRGB[i].blue);
    }
  }

  void validate(PngFileReadState paramPngFileReadState, PngIhdrChunk paramPngIhdrChunk)
  {
    if ((!paramPngFileReadState.readIHDR) || (paramPngFileReadState.readPLTE) || (paramPngFileReadState.readTRNS) || (paramPngFileReadState.readIDAT) || (paramPngFileReadState.readIEND))
      SWT.error(40);
    else
      paramPngFileReadState.readPLTE = true;
    super.validate(paramPngFileReadState, paramPngIhdrChunk);
    if (getLength() % 3 != 0)
      SWT.error(40);
    if (1 << paramPngIhdrChunk.getBitDepth() < this.paletteSize)
      SWT.error(40);
    if (256 < this.paletteSize)
      SWT.error(40);
  }

  void contributeToString(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("\n\tPalette size:");
    paramStringBuffer.append(this.paletteSize);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngPlteChunk
 * JD-Core Version:    0.6.2
 */