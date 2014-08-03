package net.htmlparser.jericho;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;

final class StreamEncodingDetector
{
  private final InputStream inputStream;
  private String encoding = null;
  private String encodingSpecificationInfo = null;
  private boolean definitive = true;
  private boolean documentSpecifiedEncodingPossible = true;
  private static final String UTF_16 = "UTF-16";
  private static final String UTF_16BE = "UTF-16BE";
  private static final String UTF_16LE = "UTF-16LE";
  private static final String UTF_8 = "UTF-8";
  private static final String ISO_8859_1 = "ISO-8859-1";
  private static final String EBCDIC = "Cp037";
  private static final String SCSU = "SCSU";
  private static final String UTF_7 = "UTF-7";
  private static final String UTF_EBCDIC = "UTF-EBCDIC";
  private static final String BOCU_1 = "BOCU-1";
  private static final String UTF_32 = "UTF-32";
  private static final String UTF_32BE = "UTF-32BE";
  private static final String UTF_32LE = "UTF-32LE";

  public StreamEncodingDetector(URLConnection paramURLConnection)
    throws IOException
  {
    Object localObject = (paramURLConnection instanceof HttpURLConnection) ? (HttpURLConnection)paramURLConnection : null;
    InputStream localInputStream = paramURLConnection.getInputStream();
    String str = paramURLConnection.getContentType();
    if (str != null)
    {
      this.encoding = Source.getCharsetParameterFromHttpHeaderValue(str);
      if (this.encoding != null)
      {
        this.inputStream = localInputStream;
        this.encodingSpecificationInfo = ("HTTP header Content-Type: " + str);
        return;
      }
    }
    this.inputStream = (localInputStream.markSupported() ? localInputStream : new BufferedInputStream(localInputStream));
    init();
  }

  public StreamEncodingDetector(InputStream paramInputStream)
    throws IOException
  {
    this.inputStream = (paramInputStream.markSupported() ? paramInputStream : new BufferedInputStream(paramInputStream));
    init();
  }

  public InputStream getInputStream()
  {
    return this.inputStream;
  }

  public String getEncoding()
  {
    return this.encoding;
  }

  public String getEncodingSpecificationInfo()
  {
    return this.encodingSpecificationInfo;
  }

  public boolean isDifinitive()
  {
    return this.definitive;
  }

  public boolean isDocumentSpecifiedEncodingPossible()
  {
    return this.documentSpecifiedEncodingPossible;
  }

  public Reader openReader()
    throws UnsupportedEncodingException
  {
    if (this.encoding == null)
      return new InputStreamReader(this.inputStream, "ISO-8859-1");
    if (!Charset.isSupported(this.encoding))
      throw new UnsupportedEncodingException(this.encoding + " - " + this.encodingSpecificationInfo);
    return new InputStreamReader(this.inputStream, this.encoding);
  }

  private boolean setEncoding(String paramString1, String paramString2)
  {
    this.encoding = paramString1;
    this.encodingSpecificationInfo = paramString2;
    return true;
  }

  private boolean init()
    throws IOException
  {
    this.inputStream.mark(4);
    int i = this.inputStream.read();
    if (i == -1)
      return setEncoding(null, "empty input stream");
    int j = this.inputStream.read();
    int k = this.inputStream.read();
    int m = this.inputStream.read();
    this.inputStream.reset();
    if (i == 239)
    {
      if ((j == 187) && (k == 191))
        return setEncoding("UTF-8", "UTF-8 Byte Order Mark (EF BB BF)");
    }
    else if (i == 254)
    {
      if (j == 255)
        return setEncoding("UTF-16", "UTF-16 big-endian Byte Order Mark (FE FF)");
    }
    else if (i == 255)
    {
      if (j == 254)
      {
        if ((k == 0) && (m == 0))
          return setEncoding("UTF-32", "UTF-32 little-endian Byte Order Mark (FF EE 00 00)");
        return setEncoding("UTF-16", "UTF-16 little-endian Byte Order Mark (FF EE)");
      }
    }
    else if (i == 0)
    {
      if ((j == 0) && (k == 254) && (m == 255))
        return setEncoding("UTF-32", "UTF-32 big-endian Byte Order Mark (00 00 FE FF)");
    }
    else if (i == 14)
    {
      if ((j == 254) && (k == 255))
        return setEncoding("SCSU", "SCSU Byte Order Mark (0E FE FF)");
    }
    else if (i == 43)
    {
      if ((j == 47) && (k == 118))
        return setEncoding("UTF-7", "UTF-7 Byte Order Mark (2B 2F 76)");
    }
    else if (i == 221)
    {
      if ((j == 115) && (k == 102) && (m == 115))
        return setEncoding("UTF-EBCDIC", "UTF-EBCDIC Byte Order Mark (DD 73 66 73)");
    }
    else if ((i == 251) && (j == 238) && (k == 40))
      return setEncoding("BOCU-1", "BOCU-1 Byte Order Mark (FB EE 28)");
    this.definitive = false;
    if (m == -1)
    {
      this.documentSpecifiedEncodingPossible = false;
      if ((j == -1) || (k != -1))
        return setEncoding("ISO-8859-1", "default 8-bit ASCII-compatible encoding (stream 3 bytes long)");
      if (i == 0)
        return setEncoding("UTF-16BE", "default 16-bit BE encoding (byte stream starts with 00, stream 2 bytes long)");
      if (j == 0)
        return setEncoding("UTF-16LE", "default 16-bit LE encoding (byte stream pattern XX 00, stream 2 bytes long)");
      return setEncoding("ISO-8859-1", "default 8-bit ASCII-compatible encoding (no 00 bytes present, stream 2 bytes long)");
    }
    if (i == 0)
    {
      if (j == 0)
        return setEncoding("UTF-32BE", "default 32-bit BE encoding (byte stream starts with 00 00)");
      return setEncoding("UTF-16BE", "default 16-bit BE encoding (byte stream starts with 00)");
    }
    if (m == 0)
    {
      if (k == 0)
        return setEncoding("UTF-32LE", "default 32-bit LE encoding (byte stream starts with pattern XX ?? 00 00)");
      return setEncoding("UTF-16LE", "default 16-bit LE encoding (byte stream stars with pattern XX ?? XX 00)");
    }
    if (j == 0)
      return setEncoding("UTF-16LE", "default 16-bit LE encoding (byte stream starts with pattern XX 00 ?? XX)");
    if (k == 0)
      return setEncoding("UTF-16BE", "default 16-bit BE encoding (byte stream starts with pattern XX XX 00 XX)");
    if (i == 76)
    {
      if ((j == 111) && (k == 167) && (m == 148))
        return setEncoding("Cp037", "default EBCDIC encoding (<?xml...> detected)");
      if ((j == 90) && (k == 196) && (m == 214))
        return setEncoding("Cp037", "default EBCDIC encoding (<!DOCTYPE...> detected)");
      if ((j & k & m & 0x80) != 0)
        return setEncoding("Cp037", "default EBCDIC-compatible encoding (HTML element detected)");
    }
    return setEncoding("ISO-8859-1", "default 8-bit ASCII-compatible encoding (no 00 bytes present in first four bytes of stream)");
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.StreamEncodingDetector
 * JD-Core Version:    0.6.2
 */