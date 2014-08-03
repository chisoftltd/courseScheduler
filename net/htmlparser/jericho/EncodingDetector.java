package net.htmlparser.jericho;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.charset.Charset;

final class EncodingDetector
{
  private final InputStream inputStream;
  private String encoding = null;
  private String encodingSpecificationInfo = null;
  private final String preliminaryEncoding;
  private final String preliminaryEncodingSpecificationInfo;
  private final String alternativePreliminaryEncoding;
  private static final int PREVIEW_BYTE_COUNT = 2048;
  private static final String UTF_8 = "UTF-8";
  private static final String ISO_8859_1 = "ISO-8859-1";

  public EncodingDetector(URLConnection paramURLConnection)
    throws IOException
  {
    this(new StreamEncodingDetector(paramURLConnection));
  }

  public EncodingDetector(InputStream paramInputStream)
    throws IOException
  {
    this(new StreamEncodingDetector(paramInputStream));
  }

  public EncodingDetector(InputStream paramInputStream, String paramString)
    throws IOException
  {
    this(paramInputStream, paramString, "preliminary encoding set explicitly", null);
    if (!Charset.isSupported(paramString))
      throw new UnsupportedEncodingException(paramString + " specified as preliminaryEncoding constructor argument");
    detectDocumentSpecifiedEncoding();
  }

  private EncodingDetector(StreamEncodingDetector paramStreamEncodingDetector)
    throws IOException
  {
    this(paramStreamEncodingDetector, "ISO-8859-1");
  }

  private EncodingDetector(StreamEncodingDetector paramStreamEncodingDetector, String paramString)
    throws IOException
  {
    this(paramStreamEncodingDetector.getInputStream(), paramStreamEncodingDetector.getEncoding(), paramStreamEncodingDetector.getEncodingSpecificationInfo(), paramString);
    if ((paramStreamEncodingDetector.isDifinitive()) || (!paramStreamEncodingDetector.isDocumentSpecifiedEncodingPossible()))
      setEncoding(this.preliminaryEncoding, this.preliminaryEncodingSpecificationInfo);
    else
      detectDocumentSpecifiedEncoding();
  }

  private EncodingDetector(InputStream paramInputStream, String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    this.inputStream = (paramInputStream.markSupported() ? paramInputStream : new BufferedInputStream(paramInputStream));
    this.preliminaryEncoding = paramString1;
    this.preliminaryEncodingSpecificationInfo = paramString2;
    this.alternativePreliminaryEncoding = paramString3;
    if ((paramString3 != null) && (!Charset.isSupported(paramString3)))
      throw new UnsupportedEncodingException(paramString3 + " specified as alternativePreliminaryEncoding constructor argument");
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

  public String getPreliminaryEncoding()
  {
    return this.preliminaryEncoding;
  }

  public String getPreliminaryEncodingSpecificationInfo()
  {
    return this.preliminaryEncodingSpecificationInfo;
  }

  public Reader openReader()
    throws UnsupportedEncodingException
  {
    if (this.encoding == null)
      return new InputStreamReader(this.inputStream, "ISO-8859-1");
    if (!Charset.isSupported(this.encoding))
      throw new UnsupportedEncodingException(this.encoding + ": " + this.encodingSpecificationInfo);
    return new InputStreamReader(this.inputStream, this.encoding);
  }

  private boolean setEncoding(String paramString1, String paramString2)
  {
    this.encoding = paramString1;
    this.encodingSpecificationInfo = paramString2;
    return true;
  }

  private boolean detectDocumentSpecifiedEncoding()
    throws IOException
  {
    this.inputStream.mark(2048);
    String str1;
    if (Charset.isSupported(this.preliminaryEncoding))
    {
      str1 = this.preliminaryEncoding;
    }
    else
    {
      if (this.alternativePreliminaryEncoding == null)
        throw new UnsupportedEncodingException(this.preliminaryEncoding + ": " + this.preliminaryEncodingSpecificationInfo);
      str1 = this.alternativePreliminaryEncoding;
    }
    Source localSource = getPreviewSource(str1);
    this.inputStream.reset();
    Logger localLogger = localSource.getLogger();
    localSource.setLogger(null);
    if ((this.preliminaryEncoding != str1) && (localLogger.isWarnEnabled()))
      localLogger.warn("Alternative encoding " + str1 + " substituted for unsupported preliminary encoding " + this.preliminaryEncoding + ": " + this.preliminaryEncodingSpecificationInfo);
    String str2;
    if (localSource.getDocumentSpecifiedEncoding() == null)
    {
      if (localSource.isXML())
        return setEncoding("UTF-8", "mandatory XML encoding when no BOM or encoding declaration is present");
      str2 = "no encoding specified in document";
    }
    else
    {
      if (Charset.isSupported(localSource.getDocumentSpecifiedEncoding()))
        return setEncoding(localSource.getDocumentSpecifiedEncoding(), localSource.getEncodingSpecificationInfo());
      str2 = "encoding " + localSource.getDocumentSpecifiedEncoding() + " specified in document is not supported";
      if (localLogger.isWarnEnabled())
        localLogger.warn("Unsupported encoding " + localSource.getDocumentSpecifiedEncoding() + " specified in document, using preliminary encoding " + str1 + " instead");
    }
    if (this.preliminaryEncoding != str1)
      return setEncoding(str1, "alternative encoding substituted for unsupported preliminary encoding " + this.preliminaryEncoding + ": " + this.preliminaryEncodingSpecificationInfo + ", " + str2);
    return setEncoding(this.preliminaryEncoding, this.preliminaryEncodingSpecificationInfo + ", " + str2);
  }

  private Source getPreviewSource(String paramString)
    throws IOException
  {
    byte[] arrayOfByte = new byte[2048];
    for (int i = 0; i < 2048; i++)
    {
      int j = this.inputStream.read();
      if (j == -1)
        break;
      arrayOfByte[i] = ((byte)j);
    }
    return new Source(new InputStreamReader(new ByteArrayInputStream(arrayOfByte, 0, i), paramString), null);
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.EncodingDetector
 * JD-Core Version:    0.6.2
 */