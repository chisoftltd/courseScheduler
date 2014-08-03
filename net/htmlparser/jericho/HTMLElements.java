package net.htmlparser.jericho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class HTMLElements
{
  private static final List<String> ALL = new ArrayList(Arrays.asList(new String[] { "a", "abbr", "acronym", "address", "applet", "area", "b", "base", "basefont", "bdo", "big", "blockquote", "body", "br", "button", "caption", "center", "cite", "code", "col", "colgroup", "dd", "del", "dfn", "dir", "div", "dl", "dt", "em", "fieldset", "font", "form", "frame", "frameset", "h1", "h2", "h3", "h4", "h5", "h6", "head", "hr", "html", "i", "iframe", "img", "input", "ins", "isindex", "kbd", "label", "legend", "li", "link", "map", "menu", "meta", "noframes", "noscript", "object", "ol", "optgroup", "option", "p", "param", "pre", "q", "s", "samp", "script", "select", "small", "span", "strike", "strong", "style", "sub", "sup", "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "tt", "u", "ul", "var" }));
  private static final HTMLElementNameSet BLOCK = new HTMLElementNameSet(new String[] { "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", "dir", "menu", "pre", "dl", "div", "center", "noscript", "noframes", "blockquote", "form", "isindex", "hr", "table", "fieldset", "address" });
  private static final HTMLElementNameSet INLINE = new HTMLElementNameSet(new String[] { "tt", "i", "b", "u", "s", "strike", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", "acronym", "a", "img", "applet", "object", "font", "basefont", "br", "script", "map", "q", "sub", "sup", "span", "bdo", "iframe", "input", "select", "textarea", "label", "button", "ins", "del" });
  private static final HTMLElementNameSet END_TAG_FORBIDDEN_SET = new HTMLElementNameSet(new String[] { "area", "base", "basefont", "br", "col", "frame", "hr", "img", "input", "isindex", "link", "meta", "param" });
  private static final HTMLElementNameSet _UL_OL = new HTMLElementNameSet("ul").union("ol");
  private static final HTMLElementNameSet _DD_DT = new HTMLElementNameSet("dd").union("dt");
  private static final HTMLElementNameSet _THEAD_TBODY_TFOOT_TR = new HTMLElementNameSet("thead").union("tbody").union("tfoot").union("tr");
  private static final HTMLElementNameSet _THEAD_TBODY_TFOOT_TR_TD_TH = new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR).union("td").union("th");
  private static final HTMLElementNameSet DEPRECATED = new HTMLElementNameSet().union("applet").union("basefont").union("center").union("dir").union("font").union("isindex").union("menu").union("s").union("strike").union("u");
  private static final HTMLElementNameSet START_TAG_OPTIONAL_SET = new HTMLElementNameSet().union("body").union("head").union("html").union("tbody");
  private static final HashMap<String, String> CONSTANT_NAME_MAP = buildTagMap();
  private static final HashMap<String, HTMLElementTerminatingTagNameSets> TERMINATING_TAG_NAME_SETS_MAP = buildTerminatingTagNameSetsMap();
  private static final Set<String> END_TAG_OPTIONAL_SET = TERMINATING_TAG_NAME_SETS_MAP.keySet();
  private static final HTMLElementNameSet END_TAG_REQUIRED_SET = new HTMLElementNameSet().union(ALL).minus(END_TAG_FORBIDDEN_SET).minus(END_TAG_OPTIONAL_SET);
  private static final HTMLElementNameSet CLOSING_SLASH_IGNORED_SET = new HTMLElementNameSet().union(END_TAG_OPTIONAL_SET).union(END_TAG_REQUIRED_SET);
  static final HTMLElementNameSet END_TAG_REQUIRED_NESTING_FORBIDDEN_SET = new HTMLElementNameSet().union("a").union("address").union("applet").union("button").union("caption").union("form").union("iframe").union("label").union("legend").union("optgroup").union("script").union("select").union("style").union("textarea").union("title");
  private static final HTMLElementNameSet END_TAG_OPTIONAL_NESTING_FORBIDDEN_SET = new HTMLElementNameSet().union("body").union("colgroup").union("head").union("html").union("option").union("p");
  private static final HTMLElementNameSet NESTING_FORBIDDEN_SET = new HTMLElementNameSet().union(END_TAG_REQUIRED_NESTING_FORBIDDEN_SET).union(END_TAG_OPTIONAL_NESTING_FORBIDDEN_SET).union(END_TAG_FORBIDDEN_SET);

  public static final List<String> getElementNames()
  {
    return ALL;
  }

  public static Set<String> getBlockLevelElementNames()
  {
    return BLOCK;
  }

  public static Set<String> getInlineLevelElementNames()
  {
    return INLINE;
  }

  public static Set<String> getDeprecatedElementNames()
  {
    return DEPRECATED;
  }

  public static Set<String> getEndTagForbiddenElementNames()
  {
    return END_TAG_FORBIDDEN_SET;
  }

  public static Set<String> getEndTagOptionalElementNames()
  {
    return END_TAG_OPTIONAL_SET;
  }

  public static Set<String> getEndTagRequiredElementNames()
  {
    return END_TAG_REQUIRED_SET;
  }

  public static Set<String> getStartTagOptionalElementNames()
  {
    return START_TAG_OPTIONAL_SET;
  }

  public static Set<String> getTerminatingStartTagNames(String paramString)
  {
    HTMLElementTerminatingTagNameSets localHTMLElementTerminatingTagNameSets = getTerminatingTagNameSets(paramString);
    if (localHTMLElementTerminatingTagNameSets == null)
      return null;
    return localHTMLElementTerminatingTagNameSets.TerminatingStartTagNameSet;
  }

  public static Set<String> getTerminatingEndTagNames(String paramString)
  {
    HTMLElementTerminatingTagNameSets localHTMLElementTerminatingTagNameSets = getTerminatingTagNameSets(paramString);
    if (localHTMLElementTerminatingTagNameSets == null)
      return null;
    return localHTMLElementTerminatingTagNameSets.TerminatingEndTagNameSet;
  }

  public static Set<String> getNonterminatingElementNames(String paramString)
  {
    HTMLElementTerminatingTagNameSets localHTMLElementTerminatingTagNameSets = getTerminatingTagNameSets(paramString);
    if (localHTMLElementTerminatingTagNameSets == null)
      return null;
    return localHTMLElementTerminatingTagNameSets.NonterminatingElementNameSet;
  }

  public static Set<String> getNestingForbiddenElementNames()
  {
    return NESTING_FORBIDDEN_SET;
  }

  static final String getConstantElementName(String paramString)
  {
    String str = (String)CONSTANT_NAME_MAP.get(paramString);
    return str != null ? str : paramString;
  }

  static final boolean isClosingSlashIgnored(String paramString)
  {
    return CLOSING_SLASH_IGNORED_SET.contains(paramString);
  }

  static final HTMLElementTerminatingTagNameSets getTerminatingTagNameSets(String paramString)
  {
    return (HTMLElementTerminatingTagNameSets)TERMINATING_TAG_NAME_SETS_MAP.get(paramString);
  }

  private static HashMap<String, HTMLElementTerminatingTagNameSets> buildTerminatingTagNameSetsMap()
  {
    HashMap localHashMap = new HashMap(20, 1.0F);
    localHashMap.put("body", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(), new HTMLElementNameSet("html").union("body"), new HTMLElementNameSet("html")));
    localHashMap.put("colgroup", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR).union("colgroup"), new HTMLElementNameSet("table").union("colgroup"), new HTMLElementNameSet("table")));
    localHashMap.put("dd", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_DD_DT), new HTMLElementNameSet("dl").union("dd"), new HTMLElementNameSet("dl")));
    localHashMap.put("dt", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_DD_DT), new HTMLElementNameSet("dl").union("dt"), new HTMLElementNameSet("dl")));
    localHashMap.put("head", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("body").union("frameset"), new HTMLElementNameSet("html").union("head"), new HTMLElementNameSet()));
    localHashMap.put("html", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(), new HTMLElementNameSet("html"), new HTMLElementNameSet("html")));
    localHashMap.put("li", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("li"), new HTMLElementNameSet(_UL_OL).union("li"), new HTMLElementNameSet(_UL_OL)));
    localHashMap.put("option", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("option").union("optgroup"), new HTMLElementNameSet("select").union("option"), new HTMLElementNameSet()));
    localHashMap.put("p", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(BLOCK).union(_DD_DT).union("th").union("td").union("li"), new HTMLElementNameSet(BLOCK).union(_DD_DT).union("body").union("html").union(_THEAD_TBODY_TFOOT_TR_TD_TH).union("caption").union("legend"), new HTMLElementNameSet()));
    localHashMap.put("tbody", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("tbody").union("tfoot").union("thead"), new HTMLElementNameSet("table").union("tbody"), new HTMLElementNameSet("table")));
    localHashMap.put("td", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR_TD_TH), new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR).union("table").union("td"), new HTMLElementNameSet("table")));
    localHashMap.put("tfoot", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("tbody").union("tfoot").union("thead"), new HTMLElementNameSet("table").union("tfoot"), new HTMLElementNameSet("table")));
    localHashMap.put("th", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR_TD_TH), new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR).union("table").union("th"), new HTMLElementNameSet("table")));
    localHashMap.put("thead", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet("tbody").union("tfoot").union("thead"), new HTMLElementNameSet("table").union("thead"), new HTMLElementNameSet("table")));
    localHashMap.put("tr", new HTMLElementTerminatingTagNameSets(new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR), new HTMLElementNameSet(_THEAD_TBODY_TFOOT_TR).union("table"), new HTMLElementNameSet("table")));
    return localHashMap;
  }

  private static HashMap<String, String> buildTagMap()
  {
    HashMap localHashMap = new HashMap(132, 1.0F);
    Iterator localIterator = ALL.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, str);
    }
    localHashMap.put("!element", "!element");
    localHashMap.put("!attlist", "!attlist");
    localHashMap.put("!entity", "!entity");
    localHashMap.put("!notation", "!notation");
    localHashMap.put("![if", "![if");
    localHashMap.put("![endif", "![endif");
    return localHashMap;
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     net.htmlparser.jericho.HTMLElements
 * JD-Core Version:    0.6.2
 */