package org.eclipse.swt.internal.image;

import org.eclipse.swt.SWT;

public class PngChunkReader
{
  LEDataInputStream inputStream;
  PngFileReadState readState;
  PngIhdrChunk headerChunk;
  PngPlteChunk paletteChunk;

  PngChunkReader(LEDataInputStream paramLEDataInputStream)
  {
    this.inputStream = paramLEDataInputStream;
    this.readState = new PngFileReadState();
    this.headerChunk = null;
  }

  PngIhdrChunk getIhdrChunk()
  {
    if (this.headerChunk == null)
      try
      {
        PngChunk localPngChunk = PngChunk.readNextFromStream(this.inputStream);
        if (localPngChunk == null)
          SWT.error(40);
        this.headerChunk = ((PngIhdrChunk)localPngChunk);
        this.headerChunk.validate(this.readState, null);
      }
      catch (ClassCastException localClassCastException)
      {
        SWT.error(40);
      }
    return this.headerChunk;
  }

  PngChunk readNextChunk()
  {
    if (this.headerChunk == null)
      return getIhdrChunk();
    PngChunk localPngChunk = PngChunk.readNextFromStream(this.inputStream);
    if (localPngChunk == null)
      SWT.error(40);
    switch (localPngChunk.getChunkType())
    {
    case 5:
      ((PngTrnsChunk)localPngChunk).validate(this.readState, this.headerChunk, this.paletteChunk);
      break;
    case 1:
      localPngChunk.validate(this.readState, this.headerChunk);
      this.paletteChunk = ((PngPlteChunk)localPngChunk);
      break;
    case 2:
    case 3:
    case 4:
    default:
      localPngChunk.validate(this.readState, this.headerChunk);
    }
    if ((this.readState.readIDAT) && (localPngChunk.getChunkType() != 2))
      this.readState.readPixelData = true;
    return localPngChunk;
  }

  boolean readPixelData()
  {
    return this.readState.readPixelData;
  }

  boolean hasMoreChunks()
  {
    return !this.readState.readIEND;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.PngChunkReader
 * JD-Core Version:    0.6.2
 */