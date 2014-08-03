package net.htmlparser.jericho;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class CharacterEntityReference extends CharacterReference
{
  private String name;
  public static final char _nbsp = ' ';
  public static final char _iexcl = '¡';
  public static final char _cent = '¢';
  public static final char _pound = '£';
  public static final char _curren = '¤';
  public static final char _yen = '¥';
  public static final char _brvbar = '¦';
  public static final char _sect = '§';
  public static final char _uml = '¨';
  public static final char _copy = '©';
  public static final char _ordf = 'ª';
  public static final char _laquo = '«';
  public static final char _not = '¬';
  public static final char _shy = '­';
  public static final char _reg = '®';
  public static final char _macr = '¯';
  public static final char _deg = '°';
  public static final char _plusmn = '±';
  public static final char _sup2 = '²';
  public static final char _sup3 = '³';
  public static final char _acute = '´';
  public static final char _micro = 'µ';
  public static final char _para = '¶';
  public static final char _middot = '·';
  public static final char _cedil = '¸';
  public static final char _sup1 = '¹';
  public static final char _ordm = 'º';
  public static final char _raquo = '»';
  public static final char _frac14 = '¼';
  public static final char _frac12 = '½';
  public static final char _frac34 = '¾';
  public static final char _iquest = '¿';
  public static final char _Agrave = 'À';
  public static final char _Aacute = 'Á';
  public static final char _Acirc = 'Â';
  public static final char _Atilde = 'Ã';
  public static final char _Auml = 'Ä';
  public static final char _Aring = 'Å';
  public static final char _AElig = 'Æ';
  public static final char _Ccedil = 'Ç';
  public static final char _Egrave = 'È';
  public static final char _Eacute = 'É';
  public static final char _Ecirc = 'Ê';
  public static final char _Euml = 'Ë';
  public static final char _Igrave = 'Ì';
  public static final char _Iacute = 'Í';
  public static final char _Icirc = 'Î';
  public static final char _Iuml = 'Ï';
  public static final char _ETH = 'Ð';
  public static final char _Ntilde = 'Ñ';
  public static final char _Ograve = 'Ò';
  public static final char _Oacute = 'Ó';
  public static final char _Ocirc = 'Ô';
  public static final char _Otilde = 'Õ';
  public static final char _Ouml = 'Ö';
  public static final char _times = '×';
  public static final char _Oslash = 'Ø';
  public static final char _Ugrave = 'Ù';
  public static final char _Uacute = 'Ú';
  public static final char _Ucirc = 'Û';
  public static final char _Uuml = 'Ü';
  public static final char _Yacute = 'Ý';
  public static final char _THORN = 'Þ';
  public static final char _szlig = 'ß';
  public static final char _agrave = 'à';
  public static final char _aacute = 'á';
  public static final char _acirc = 'â';
  public static final char _atilde = 'ã';
  public static final char _auml = 'ä';
  public static final char _aring = 'å';
  public static final char _aelig = 'æ';
  public static final char _ccedil = 'ç';
  public static final char _egrave = 'è';
  public static final char _eacute = 'é';
  public static final char _ecirc = 'ê';
  public static final char _euml = 'ë';
  public static final char _igrave = 'ì';
  public static final char _iacute = 'í';
  public static final char _icirc = 'î';
  public static final char _iuml = 'ï';
  public static final char _eth = 'ð';
  public static final char _ntilde = 'ñ';
  public static final char _ograve = 'ò';
  public static final char _oacute = 'ó';
  public static final char _ocirc = 'ô';
  public static final char _otilde = 'õ';
  public static final char _ouml = 'ö';
  public static final char _divide = '÷';
  public static final char _oslash = 'ø';
  public static final char _ugrave = 'ù';
  public static final char _uacute = 'ú';
  public static final char _ucirc = 'û';
  public static final char _uuml = 'ü';
  public static final char _yacute = 'ý';
  public static final char _thorn = 'þ';
  public static final char _yuml = 'ÿ';
  public static final char _fnof = 'ƒ';
  public static final char _Alpha = 'Α';
  public static final char _Beta = 'Β';
  public static final char _Gamma = 'Γ';
  public static final char _Delta = 'Δ';
  public static final char _Epsilon = 'Ε';
  public static final char _Zeta = 'Ζ';
  public static final char _Eta = 'Η';
  public static final char _Theta = 'Θ';
  public static final char _Iota = 'Ι';
  public static final char _Kappa = 'Κ';
  public static final char _Lambda = 'Λ';
  public static final char _Mu = 'Μ';
  public static final char _Nu = 'Ν';
  public static final char _Xi = 'Ξ';
  public static final char _Omicron = 'Ο';
  public static final char _Pi = 'Π';
  public static final char _Rho = 'Ρ';
  public static final char _Sigma = 'Σ';
  public static final char _Tau = 'Τ';
  public static final char _Upsilon = 'Υ';
  public static final char _Phi = 'Φ';
  public static final char _Chi = 'Χ';
  public static final char _Psi = 'Ψ';
  public static final char _Omega = 'Ω';
  public static final char _alpha = 'α';
  public static final char _beta = 'β';
  public static final char _gamma = 'γ';
  public static final char _delta = 'δ';
  public static final char _epsilon = 'ε';
  public static final char _zeta = 'ζ';
  public static final char _eta = 'η';
  public static final char _theta = 'θ';
  public static final char _iota = 'ι';
  public static final char _kappa = 'κ';
  public static final char _lambda = 'λ';
  public static final char _mu = 'μ';
  public static final char _nu = 'ν';
  public static final char _xi = 'ξ';
  public static final char _omicron = 'ο';
  public static final char _pi = 'π';
  public static final char _rho = 'ρ';
  public static final char _sigmaf = 'ς';
  public static final char _sigma = 'σ';
  public static final char _tau = 'τ';
  public static final char _upsilon = 'υ';
  public static final char _phi = 'φ';
  public static final char _chi = 'χ';
  public static final char _psi = 'ψ';
  public static final char _omega = 'ω';
  public static final char _thetasym = 'ϑ';
  public static final char _upsih = 'ϒ';
  public static final char _piv = 'ϖ';
  public static final char _bull = '•';
  public static final char _hellip = '…';
  public static final char _prime = '′';
  public static final char _Prime = '″';
  public static final char _oline = '‾';
  public static final char _frasl = '⁄';
  public static final char _weierp = '℘';
  public static final char _image = 'ℑ';
  public static final char _real = 'ℜ';
  public static final char _trade = '™';
  public static final char _alefsym = 'ℵ';
  public static final char _larr = '←';
  public static final char _uarr = '↑';
  public static final char _rarr = '→';
  public static final char _darr = '↓';
  public static final char _harr = '↔';
  public static final char _crarr = '↵';
  public static final char _lArr = '⇐';
  public static final char _uArr = '⇑';
  public static final char _rArr = '⇒';
  public static final char _dArr = '⇓';
  public static final char _hArr = '⇔';
  public static final char _forall = '∀';
  public static final char _part = '∂';
  public static final char _exist = '∃';
  public static final char _empty = '∅';
  public static final char _nabla = '∇';
  public static final char _isin = '∈';
  public static final char _notin = '∉';
  public static final char _ni = '∋';
  public static final char _prod = '∏';
  public static final char _sum = '∑';
  public static final char _minus = '−';
  public static final char _lowast = '∗';
  public static final char _radic = '√';
  public static final char _prop = '∝';
  public static final char _infin = '∞';
  public static final char _ang = '∠';
  public static final char _and = '∧';
  public static final char _or = '∨';
  public static final char _cap = '∩';
  public static final char _cup = '∪';
  public static final char _int = '∫';
  public static final char _there4 = '∴';
  public static final char _sim = '∼';
  public static final char _cong = '≅';
  public static final char _asymp = '≈';
  public static final char _ne = '≠';
  public static final char _equiv = '≡';
  public static final char _le = '≤';
  public static final char _ge = '≥';
  public static final char _sub = '⊂';
  public static final char _sup = '⊃';
  public static final char _nsub = '⊄';
  public static final char _sube = '⊆';
  public static final char _supe = '⊇';
  public static final char _oplus = '⊕';
  public static final char _otimes = '⊗';
  public static final char _perp = '⊥';
  public static final char _sdot = '⋅';
  public static final char _lceil = '⌈';
  public static final char _rceil = '⌉';
  public static final char _lfloor = '⌊';
  public static final char _rfloor = '⌋';
  public static final char _lang = '〈';
  public static final char _rang = '〉';
  public static final char _loz = '◊';
  public static final char _spades = '♠';
  public static final char _clubs = '♣';
  public static final char _hearts = '♥';
  public static final char _diams = '♦';
  public static final char _quot = '"';
  public static final char _amp = '&';
  public static final char _lt = '<';
  public static final char _gt = '>';
  public static final char _OElig = 'Œ';
  public static final char _oelig = 'œ';
  public static final char _Scaron = 'Š';
  public static final char _scaron = 'š';
  public static final char _Yuml = 'Ÿ';
  public static final char _circ = 'ˆ';
  public static final char _tilde = '˜';
  public static final char _ensp = ' ';
  public static final char _emsp = ' ';
  public static final char _thinsp = ' ';
  public static final char _zwnj = '‌';
  public static final char _zwj = '‍';
  public static final char _lrm = '‎';
  public static final char _rlm = '‏';
  public static final char _ndash = '–';
  public static final char _mdash = '—';
  public static final char _lsquo = '‘';
  public static final char _rsquo = '’';
  public static final char _sbquo = '‚';
  public static final char _ldquo = '“';
  public static final char _rdquo = '”';
  public static final char _bdquo = '„';
  public static final char _dagger = '†';
  public static final char _Dagger = '‡';
  public static final char _permil = '‰';
  public static final char _lsaquo = '‹';
  public static final char _rsaquo = '›';
  public static final char _euro = '€';
  public static final char _apos = '\'';
  private static Map<String, Integer> NAME_TO_CODE_POINT_MAP = new HashMap(512, 1.0F);
  private static IntStringHashMap CODE_POINT_TO_NAME_MAP;
  private static int MAX_NAME_LENGTH = 0;

  private CharacterEntityReference(Source paramSource, int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramSource, paramInt1, paramInt2, paramInt3);
    this.name = getName(paramInt3);
  }

  public String getName()
  {
    return this.name;
  }

  public static String getName(char paramChar)
  {
    return getName(paramChar);
  }

  public static String getName(int paramInt)
  {
    return CODE_POINT_TO_NAME_MAP.get(paramInt);
  }

  public static int getCodePointFromName(String paramString)
  {
    Integer localInteger = (Integer)NAME_TO_CODE_POINT_MAP.get(paramString);
    if (localInteger == null)
    {
      String str = paramString.toLowerCase();
      if (str != paramString)
        localInteger = (Integer)NAME_TO_CODE_POINT_MAP.get(str);
    }
    return localInteger != null ? localInteger.intValue() : -1;
  }

  public String getCharacterReferenceString()
  {
    return getCharacterReferenceString(this.name);
  }

  public static String getCharacterReferenceString(int paramInt)
  {
    if (paramInt > 65535)
      return null;
    String str = getName(paramInt);
    return str != null ? getCharacterReferenceString(str) : null;
  }

  public static Map<String, Integer> getNameToCodePointMap()
  {
    return NAME_TO_CODE_POINT_MAP;
  }

  public String getDebugInfo()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append('"');
    try
    {
      appendCharacterReferenceString(localStringBuilder, this.name);
      localStringBuilder.append("\" ");
      appendUnicodeText(localStringBuilder, this.codePoint);
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
    localStringBuilder.append(' ').append(super.getDebugInfo());
    return localStringBuilder.toString();
  }

  private static String getCharacterReferenceString(String paramString)
  {
    try
    {
      return appendCharacterReferenceString(new StringBuilder(), paramString).toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException(localIOException);
    }
  }

  static final Appendable appendCharacterReferenceString(Appendable paramAppendable, String paramString)
    throws IOException
  {
    return paramAppendable.append('&').append(paramString).append(';');
  }

  static CharacterReference construct(Source paramSource, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + 1;
    int j = i + MAX_NAME_LENGTH;
    int k = paramSource.end - 1;
    int n = i;
    int i1 = 0;
    int m;
    String str;
    while (true)
    {
      char c = paramSource.charAt(n);
      if (c == ';')
      {
        m = n + 1;
        str = paramSource.subSequence(i, n).toString();
        break;
      }
      if (!isValidReferenceNameChar(c))
      {
        i1 = 1;
      }
      else if (n == k)
      {
        i1 = 1;
        n++;
      }
      if (i1 != 0)
      {
        if (paramInt2 == -1)
          return null;
        m = n;
        str = paramSource.subSequence(i, n).toString();
        break;
      }
      n++;
      if (n > j)
        return null;
    }
    int i2 = getCodePointFromName(str);
    if ((i2 == -1) || ((i1 != 0) && (i2 > paramInt2)))
      return null;
    return new CharacterEntityReference(paramSource, paramInt1, m, i2);
  }

  private static final boolean isValidReferenceNameChar(char paramChar)
  {
    return (paramChar >= 'A') && (paramChar <= 'z') && ((paramChar <= 'Z') || (paramChar >= 'a'));
  }

  static
  {
    NAME_TO_CODE_POINT_MAP.put("nbsp", new Integer(160));
    NAME_TO_CODE_POINT_MAP.put("iexcl", new Integer(161));
    NAME_TO_CODE_POINT_MAP.put("cent", new Integer(162));
    NAME_TO_CODE_POINT_MAP.put("pound", new Integer(163));
    NAME_TO_CODE_POINT_MAP.put("curren", new Integer(164));
    NAME_TO_CODE_POINT_MAP.put("yen", new Integer(165));
    NAME_TO_CODE_POINT_MAP.put("brvbar", new Integer(166));
    NAME_TO_CODE_POINT_MAP.put("sect", new Integer(167));
    NAME_TO_CODE_POINT_MAP.put("uml", new Integer(168));
    NAME_TO_CODE_POINT_MAP.put("copy", new Integer(169));
    NAME_TO_CODE_POINT_MAP.put("ordf", new Integer(170));
    NAME_TO_CODE_POINT_MAP.put("laquo", new Integer(171));
    NAME_TO_CODE_POINT_MAP.put("not", new Integer(172));
    NAME_TO_CODE_POINT_MAP.put("shy", new Integer(173));
    NAME_TO_CODE_POINT_MAP.put("reg", new Integer(174));
    NAME_TO_CODE_POINT_MAP.put("macr", new Integer(175));
    NAME_TO_CODE_POINT_MAP.put("deg", new Integer(176));
    NAME_TO_CODE_POINT_MAP.put("plusmn", new Integer(177));
    NAME_TO_CODE_POINT_MAP.put("sup2", new Integer(178));
    NAME_TO_CODE_POINT_MAP.put("sup3", new Integer(179));
    NAME_TO_CODE_POINT_MAP.put("acute", new Integer(180));
    NAME_TO_CODE_POINT_MAP.put("micro", new Integer(181));
    NAME_TO_CODE_POINT_MAP.put("para", new Integer(182));
    NAME_TO_CODE_POINT_MAP.put("middot", new Integer(183));
    NAME_TO_CODE_POINT_MAP.put("cedil", new Integer(184));
    NAME_TO_CODE_POINT_MAP.put("sup1", new Integer(185));
    NAME_TO_CODE_POINT_MAP.put("ordm", new Integer(186));
    NAME_TO_CODE_POINT_MAP.put("raquo", new Integer(187));
    NAME_TO_CODE_POINT_MAP.put("frac14", new Integer(188));
    NAME_TO_CODE_POINT_MAP.put("frac12", new Integer(189));
    NAME_TO_CODE_POINT_MAP.put("frac34", new Integer(190));
    NAME_TO_CODE_POINT_MAP.put("iquest", new Integer(191));
    NAME_TO_CODE_POINT_MAP.put("Agrave", new Integer(192));
    NAME_TO_CODE_POINT_MAP.put("Aacute", new Integer(193));
    NAME_TO_CODE_POINT_MAP.put("Acirc", new Integer(194));
    NAME_TO_CODE_POINT_MAP.put("Atilde", new Integer(195));
    NAME_TO_CODE_POINT_MAP.put("Auml", new Integer(196));
    NAME_TO_CODE_POINT_MAP.put("Aring", new Integer(197));
    NAME_TO_CODE_POINT_MAP.put("AElig", new Integer(198));
    NAME_TO_CODE_POINT_MAP.put("Ccedil", new Integer(199));
    NAME_TO_CODE_POINT_MAP.put("Egrave", new Integer(200));
    NAME_TO_CODE_POINT_MAP.put("Eacute", new Integer(201));
    NAME_TO_CODE_POINT_MAP.put("Ecirc", new Integer(202));
    NAME_TO_CODE_POINT_MAP.put("Euml", new Integer(203));
    NAME_TO_CODE_POINT_MAP.put("Igrave", new Integer(204));
    NAME_TO_CODE_POINT_MAP.put("Iacute", new Integer(205));
    NAME_TO_CODE_POINT_MAP.put("Icirc", new Integer(206));
    NAME_TO_CODE_POINT_MAP.put("Iuml", new Integer(207));
    NAME_TO_CODE_POINT_MAP.put("ETH", new Integer(208));
    NAME_TO_CODE_POINT_MAP.put("Ntilde", new Integer(209));
    NAME_TO_CODE_POINT_MAP.put("Ograve", new Integer(210));
    NAME_TO_CODE_POINT_MAP.put("Oacute", new Integer(211));
    NAME_TO_CODE_POINT_MAP.put("Ocirc", new Integer(212));
    NAME_TO_CODE_POINT_MAP.put("Otilde", new Integer(213));
    NAME_TO_CODE_POINT_MAP.put("Ouml", new Integer(214));
    NAME_TO_CODE_POINT_MAP.put("times", new Integer(215));
    NAME_TO_CODE_POINT_MAP.put("Oslash", new Integer(216));
    NAME_TO_CODE_POINT_MAP.put("Ugrave", new Integer(217));
    NAME_TO_CODE_POINT_MAP.put("Uacute", new Integer(218));
    NAME_TO_CODE_POINT_MAP.put("Ucirc", new Integer(219));
    NAME_TO_CODE_POINT_MAP.put("Uuml", new Integer(220));
    NAME_TO_CODE_POINT_MAP.put("Yacute", new Integer(221));
    NAME_TO_CODE_POINT_MAP.put("THORN", new Integer(222));
    NAME_TO_CODE_POINT_MAP.put("szlig", new Integer(223));
    NAME_TO_CODE_POINT_MAP.put("agrave", new Integer(224));
    NAME_TO_CODE_POINT_MAP.put("aacute", new Integer(225));
    NAME_TO_CODE_POINT_MAP.put("acirc", new Integer(226));
    NAME_TO_CODE_POINT_MAP.put("atilde", new Integer(227));
    NAME_TO_CODE_POINT_MAP.put("auml", new Integer(228));
    NAME_TO_CODE_POINT_MAP.put("aring", new Integer(229));
    NAME_TO_CODE_POINT_MAP.put("aelig", new Integer(230));
    NAME_TO_CODE_POINT_MAP.put("ccedil", new Integer(231));
    NAME_TO_CODE_POINT_MAP.put("egrave", new Integer(232));
    NAME_TO_CODE_POINT_MAP.put("eacute", new Integer(233));
    NAME_TO_CODE_POINT_MAP.put("ecirc", new Integer(234));
    NAME_TO_CODE_POINT_MAP.put("euml", new Integer(235));
    NAME_TO_CODE_POINT_MAP.put("igrave", new Integer(236));
    NAME_TO_CODE_POINT_MAP.put("iacute", new Integer(237));
    NAME_TO_CODE_POINT_MAP.put("icirc", new Integer(238));
    NAME_TO_CODE_POINT_MAP.put("iuml", new Integer(239));
    NAME_TO_CODE_POINT_MAP.put("eth", new Integer(240));
    NAME_TO_CODE_POINT_MAP.put("ntilde", new Integer(241));
    NAME_TO_CODE_POINT_MAP.put("ograve", new Integer(242));
    NAME_TO_CODE_POINT_MAP.put("oacute", new Integer(243));
    NAME_TO_CODE_POINT_MAP.put("ocirc", new Integer(244));
    NAME_TO_CODE_POINT_MAP.put("otilde", new Integer(245));
    NAME_TO_CODE_POINT_MAP.put("ouml", new Integer(246));
    NAME_TO_CODE_POINT_MAP.put("divide", new Integer(247));
    NAME_TO_CODE_POINT_MAP.put("oslash", new Integer(248));
    NAME_TO_CODE_POINT_MAP.put("ugrave", new Integer(249));
    NAME_TO_CODE_POINT_MAP.put("uacute", new Integer(250));
    NAME_TO_CODE_POINT_MAP.put("ucirc", new Integer(251));
    NAME_TO_CODE_POINT_MAP.put("uuml", new Integer(252));
    NAME_TO_CODE_POINT_MAP.put("yacute", new Integer(253));
    NAME_TO_CODE_POINT_MAP.put("thorn", new Integer(254));
    NAME_TO_CODE_POINT_MAP.put("yuml", new Integer(255));
    NAME_TO_CODE_POINT_MAP.put("fnof", new Integer(402));
    NAME_TO_CODE_POINT_MAP.put("Alpha", new Integer(913));
    NAME_TO_CODE_POINT_MAP.put("Beta", new Integer(914));
    NAME_TO_CODE_POINT_MAP.put("Gamma", new Integer(915));
    NAME_TO_CODE_POINT_MAP.put("Delta", new Integer(916));
    NAME_TO_CODE_POINT_MAP.put("Epsilon", new Integer(917));
    NAME_TO_CODE_POINT_MAP.put("Zeta", new Integer(918));
    NAME_TO_CODE_POINT_MAP.put("Eta", new Integer(919));
    NAME_TO_CODE_POINT_MAP.put("Theta", new Integer(920));
    NAME_TO_CODE_POINT_MAP.put("Iota", new Integer(921));
    NAME_TO_CODE_POINT_MAP.put("Kappa", new Integer(922));
    NAME_TO_CODE_POINT_MAP.put("Lambda", new Integer(923));
    NAME_TO_CODE_POINT_MAP.put("Mu", new Integer(924));
    NAME_TO_CODE_POINT_MAP.put("Nu", new Integer(925));
    NAME_TO_CODE_POINT_MAP.put("Xi", new Integer(926));
    NAME_TO_CODE_POINT_MAP.put("Omicron", new Integer(927));
    NAME_TO_CODE_POINT_MAP.put("Pi", new Integer(928));
    NAME_TO_CODE_POINT_MAP.put("Rho", new Integer(929));
    NAME_TO_CODE_POINT_MAP.put("Sigma", new Integer(931));
    NAME_TO_CODE_POINT_MAP.put("Tau", new Integer(932));
    NAME_TO_CODE_POINT_MAP.put("Upsilon", new Integer(933));
    NAME_TO_CODE_POINT_MAP.put("Phi", new Integer(934));
    NAME_TO_CODE_POINT_MAP.put("Chi", new Integer(935));
    NAME_TO_CODE_POINT_MAP.put("Psi", new Integer(936));
    NAME_TO_CODE_POINT_MAP.put("Omega", new Integer(937));
    NAME_TO_CODE_POINT_MAP.put("alpha", new Integer(945));
    NAME_TO_CODE_POINT_MAP.put("beta", new Integer(946));
    NAME_TO_CODE_POINT_MAP.put("gamma", new Integer(947));
    NAME_TO_CODE_POINT_MAP.put("delta", new Integer(948));
    NAME_TO_CODE_POINT_MAP.put("epsilon", new Integer(949));
    NAME_TO_CODE_POINT_MAP.put("zeta", new Integer(950));
    NAME_TO_CODE_POINT_MAP.put("eta", new Integer(951));
    NAME_TO_CODE_POINT_MAP.put("theta", new Integer(952));
    NAME_TO_CODE_POINT_MAP.put("iota", new Integer(953));
    NAME_TO_CODE_POINT_MAP.put("kappa", new Integer(954));
    NAME_TO_CODE_POINT_MAP.put("lambda", new Integer(955));
    NAME_TO_CODE_POINT_MAP.put("mu", new Integer(956));
    NAME_TO_CODE_POINT_MAP.put("nu", new Integer(957));
    NAME_TO_CODE_POINT_MAP.put("xi", new Integer(958));
    NAME_TO_CODE_POINT_MAP.put("omicron", new Integer(959));
    NAME_TO_CODE_POINT_MAP.put("pi", new Integer(960));
    NAME_TO_CODE_POINT_MAP.put("rho", new Integer(961));
    NAME_TO_CODE_POINT_MAP.put("sigmaf", new Integer(962));
    NAME_TO_CODE_POINT_MAP.put("sigma", new Integer(963));
    NAME_TO_CODE_POINT_MAP.put("tau", new Integer(964));
    NAME_TO_CODE_POINT_MAP.put("upsilon", new Integer(965));
    NAME_TO_CODE_POINT_MAP.put("phi", new Integer(966));
    NAME_TO_CODE_POINT_MAP.put("chi", new Integer(967));
    NAME_TO_CODE_POINT_MAP.put("psi", new Integer(968));
    NAME_TO_CODE_POINT_MAP.put("omega", new Integer(969));
    NAME_TO_CODE_POINT_MAP.put("thetasym", new Integer(977));
    NAME_TO_CODE_POINT_MAP.put("upsih", new Integer(978));
    NAME_TO_CODE_POINT_MAP.put("piv", new Integer(982));
    NAME_TO_CODE_POINT_MAP.put("bull", new Integer(8226));
    NAME_TO_CODE_POINT_MAP.put("hellip", new Integer(8230));
    NAME_TO_CODE_POINT_MAP.put("prime", new Integer(8242));
    NAME_TO_CODE_POINT_MAP.put("Prime", new Integer(8243));
    NAME_TO_CODE_POINT_MAP.put("oline", new Integer(8254));
    NAME_TO_CODE_POINT_MAP.put("frasl", new Integer(8260));
    NAME_TO_CODE_POINT_MAP.put("weierp", new Integer(8472));
    NAME_TO_CODE_POINT_MAP.put("image", new Integer(8465));
    NAME_TO_CODE_POINT_MAP.put("real", new Integer(8476));
    NAME_TO_CODE_POINT_MAP.put("trade", new Integer(8482));
    NAME_TO_CODE_POINT_MAP.put("alefsym", new Integer(8501));
    NAME_TO_CODE_POINT_MAP.put("larr", new Integer(8592));
    NAME_TO_CODE_POINT_MAP.put("uarr", new Integer(8593));
    NAME_TO_CODE_POINT_MAP.put("rarr", new Integer(8594));
    NAME_TO_CODE_POINT_MAP.put("darr", new Integer(8595));
    NAME_TO_CODE_POINT_MAP.put("harr", new Integer(8596));
    NAME_TO_CODE_POINT_MAP.put("crarr", new Integer(8629));
    NAME_TO_CODE_POINT_MAP.put("lArr", new Integer(8656));
    NAME_TO_CODE_POINT_MAP.put("uArr", new Integer(8657));
    NAME_TO_CODE_POINT_MAP.put("rArr", new Integer(8658));
    NAME_TO_CODE_POINT_MAP.put("dArr", new Integer(8659));
    NAME_TO_CODE_POINT_MAP.put("hArr", new Integer(8660));
    NAME_TO_CODE_POINT_MAP.put("forall", new Integer(8704));
    NAME_TO_CODE_POINT_MAP.put("part", new Integer(8706));
    NAME_TO_CODE_POINT_MAP.put("exist", new Integer(8707));
    NAME_TO_CODE_POINT_MAP.put("empty", new Integer(8709));
    NAME_TO_CODE_POINT_MAP.put("nabla", new Integer(8711));
    NAME_TO_CODE_POINT_MAP.put("isin", new Integer(8712));
    NAME_TO_CODE_POINT_MAP.put("notin", new Integer(8713));
    NAME_TO_CODE_POINT_MAP.put("ni", new Integer(8715));
    NAME_TO_CODE_POINT_MAP.put("prod", new Integer(8719));
    NAME_TO_CODE_POINT_MAP.put("sum", new Integer(8721));
    NAME_TO_CODE_POINT_MAP.put("minus", new Integer(8722));
    NAME_TO_CODE_POINT_MAP.put("lowast", new Integer(8727));
    NAME_TO_CODE_POINT_MAP.put("radic", new Integer(8730));
    NAME_TO_CODE_POINT_MAP.put("prop", new Integer(8733));
    NAME_TO_CODE_POINT_MAP.put("infin", new Integer(8734));
    NAME_TO_CODE_POINT_MAP.put("ang", new Integer(8736));
    NAME_TO_CODE_POINT_MAP.put("and", new Integer(8743));
    NAME_TO_CODE_POINT_MAP.put("or", new Integer(8744));
    NAME_TO_CODE_POINT_MAP.put("cap", new Integer(8745));
    NAME_TO_CODE_POINT_MAP.put("cup", new Integer(8746));
    NAME_TO_CODE_POINT_MAP.put("int", new Integer(8747));
    NAME_TO_CODE_POINT_MAP.put("there4", new Integer(8756));
    NAME_TO_CODE_POINT_MAP.put("sim", new Integer(8764));
    NAME_TO_CODE_POINT_MAP.put("cong", new Integer(8773));
    NAME_TO_CODE_POINT_MAP.put("asymp", new Integer(8776));
    NAME_TO_CODE_POINT_MAP.put("ne", new Integer(8800));
    NAME_TO_CODE_POINT_MAP.put("equiv", new Integer(8801));
    NAME_TO_CODE_POINT_MAP.put("le", new Integer(8804));
    NAME_TO_CODE_POINT_MAP.put("ge", new Integer(8805));
    NAME_TO_CODE_POINT_MAP.put("sub", new Integer(8834));
    NAME_TO_CODE_POINT_MAP.put("sup", new Integer(8835));
    NAME_TO_CODE_POINT_MAP.put("nsub", new Integer(8836));
    NAME_TO_CODE_POINT_MAP.put("sube", new Integer(8838));
    NAME_TO_CODE_POINT_MAP.put("supe", new Integer(8839));
    NAME_TO_CODE_POINT_MAP.put("oplus", new Integer(8853));
    NAME_TO_CODE_POINT_MAP.put("otimes", new Integer(8855));
    NAME_TO_CODE_POINT_MAP.put("perp", new Integer(8869));
    NAME_TO_CODE_POINT_MAP.put("sdot", new Integer(8901));
    NAME_TO_CODE_POINT_MAP.put("lceil", new Integer(8968));
    NAME_TO_CODE_POINT_MAP.put("rceil", new Integer(8969));
    NAME_TO_CODE_POINT_MAP.put("lfloor", new Integer(8970));
    NAME_TO_CODE_POINT_MAP.put("rfloor", new Integer(8971));
    NAME_TO_CODE_POINT_MAP.put("lang", new Integer(9001));
    NAME_TO_CODE_POINT_MAP.put("rang", new Integer(9002));
    NAME_TO_CODE_POINT_MAP.put("loz", new Integer(9674));
    NAME_TO_CODE_POINT_MAP.put("spades", new Integer(9824));
    NAME_TO_CODE_POINT_MAP.put("clubs", new Integer(9827));
    NAME_TO_CODE_POINT_MAP.put("hearts", new Integer(9829));
    NAME_TO_CODE_POINT_MAP.put("diams", new Integer(9830));
    NAME_TO_CODE_POINT_MAP.put("quot", new Integer(34));
    NAME_TO_CODE_POINT_MAP.put("amp", new Integer(38));
    NAME_TO_CODE_POINT_MAP.put("lt", new Integer(60));
    NAME_TO_CODE_POINT_MAP.put("gt", new Integer(62));
    NAME_TO_CODE_POINT_MAP.put("OElig", new Integer(338));
    NAME_TO_CODE_POINT_MAP.put("oelig", new Integer(339));
    NAME_TO_CODE_POINT_MAP.put("Scaron", new Integer(352));
    NAME_TO_CODE_POINT_MAP.put("scaron", new Integer(353));
    NAME_TO_CODE_POINT_MAP.put("Yuml", new Integer(376));
    NAME_TO_CODE_POINT_MAP.put("circ", new Integer(710));
    NAME_TO_CODE_POINT_MAP.put("tilde", new Integer(732));
    NAME_TO_CODE_POINT_MAP.put("ensp", new Integer(8194));
    NAME_TO_CODE_POINT_MAP.put("emsp", new Integer(8195));
    NAME_TO_CODE_POINT_MAP.put("thinsp", new Integer(8201));
    NAME_TO_CODE_POINT_MAP.put("zwnj", new Integer(8204));
    NAME_TO_CODE_POINT_MAP.put("zwj", new Integer(8205));
    NAME_TO_CODE_POINT_MAP.put("lrm", new Integer(8206));
    NAME_TO_CODE_POINT_MAP.put("rlm", new Integer(8207));
    NAME_TO_CODE_POINT_MAP.put("ndash", new Integer(8211));
    NAME_TO_CODE_POINT_MAP.put("mdash", new Integer(8212));
    NAME_TO_CODE_POINT_MAP.put("lsquo", new Integer(8216));
    NAME_TO_CODE_POINT_MAP.put("rsquo", new Integer(8217));
    NAME_TO_CODE_POINT_MAP.put("sbquo", new Integer(8218));
    NAME_TO_CODE_POINT_MAP.put("ldquo", new Integer(8220));
    NAME_TO_CODE_POINT_MAP.put("rdquo", new Integer(8221));
    NAME_TO_CODE_POINT_MAP.put("bdquo", new Integer(8222));
    NAME_TO_CODE_POINT_MAP.put("dagger", new Integer(8224));
    NAME_TO_CODE_POINT_MAP.put("Dagger", new Integer(8225));
    NAME_TO_CODE_POINT_MAP.put("permil", new Integer(8240));
    NAME_TO_CODE_POINT_MAP.put("lsaquo", new Integer(8249));
    NAME_TO_CODE_POINT_MAP.put("rsaquo", new Integer(8250));
    NAME_TO_CODE_POINT_MAP.put("euro", new Integer(8364));
    NAME_TO_CODE_POINT_MAP.put("apos", new Integer(39));
    CODE_POINT_TO_NAME_MAP = new IntStringHashMap((int)(NAME_TO_CODE_POINT_MAP.size() / 0.75F), 1.0F);
    Iterator localIterator = NAME_TO_CODE_POINT_MAP.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      if (MAX_NAME_LENGTH < str.length())
        MAX_NAME_LENGTH = str.length();
      CODE_POINT_TO_NAME_MAP.put(((Integer)localEntry.getValue()).intValue(), str);
    }
    MAX_ENTITY_REFERENCE_LENGTH = MAX_NAME_LENGTH + 2;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.CharacterEntityReference
 * JD-Core Version:    0.6.2
 */