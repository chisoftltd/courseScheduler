package org.eclipse.swt.internal.win32;

import org.eclipse.swt.internal.C;
import org.eclipse.swt.internal.Library;

public class OS extends C
{
  public static final boolean IsWin32s;
  public static final boolean IsWin95;
  public static final boolean IsWinNT;
  public static final boolean IsWinCE;
  public static final boolean IsPPC;
  public static final boolean IsHPC;
  public static final boolean IsSP;
  public static final boolean IsDBLocale;
  public static final boolean IsUnicode;
  public static final int WIN32_MAJOR;
  public static final int WIN32_MINOR;
  public static final int WIN32_VERSION;
  public static final int COMCTL32_MAJOR;
  public static final int COMCTL32_MINOR;
  public static final int COMCTL32_VERSION;
  public static final int SHELL32_MAJOR = ((DLLVERSIONINFO)localObject).dwMajorVersion;
  public static final int SHELL32_MINOR = ((DLLVERSIONINFO)localObject).dwMinorVersion;
  public static final int SHELL32_VERSION = VERSION(SHELL32_MAJOR, SHELL32_MINOR);
  public static final String NO_MANIFEST = "org.eclipse.swt.internal.win32.OS.NO_MANIFEST";
  public static final int VER_PLATFORM_WIN32s = 0;
  public static final int VER_PLATFORM_WIN32_WINDOWS = 1;
  public static final int VER_PLATFORM_WIN32_NT = 2;
  public static final int VER_PLATFORM_WIN32_CE = 3;
  public static final int HEAP_ZERO_MEMORY = 8;
  public static final int ACTCTX_FLAG_RESOURCE_NAME_VALID = 8;
  public static final int ACTCTX_FLAG_SET_PROCESS_DEFAULT = 16;
  public static final int MANIFEST_RESOURCE_ID = 2;
  public static final int SM_DBCSENABLED = 42;
  public static final int SM_IMMENABLED = 82;
  public static final int LANG_KOREAN = 18;
  public static final int MAX_PATH = 260;
  static final int SYS_COLOR_INDEX_FLAG = IsWinCE ? 1073741824 : 0;
  public static final int ABS_DOWNDISABLED = 8;
  public static final int ABS_DOWNHOT = 6;
  public static final int ABS_DOWNNORMAL = 5;
  public static final int ABS_DOWNPRESSED = 7;
  public static final int ABS_LEFTDISABLED = 12;
  public static final int ABS_LEFTHOT = 10;
  public static final int ABS_LEFTNORMAL = 9;
  public static final int ABS_LEFTPRESSED = 11;
  public static final int ABS_RIGHTDISABLED = 16;
  public static final int ABS_RIGHTHOT = 14;
  public static final int ABS_RIGHTNORMAL = 13;
  public static final int ABS_RIGHTPRESSED = 15;
  public static final int ABS_UPDISABLED = 4;
  public static final int ABS_UPHOT = 2;
  public static final int ABS_UPNORMAL = 1;
  public static final int ABS_UPPRESSED = 3;
  public static final int AC_SRC_OVER = 0;
  public static final int AC_SRC_ALPHA = 1;
  public static final int ALTERNATE = 1;
  public static final int ASSOCF_NOTRUNCATE = 32;
  public static final int ASSOCF_INIT_IGNOREUNKNOWN = 1024;
  public static final int ASSOCSTR_COMMAND = 1;
  public static final int ASSOCSTR_DEFAULTICON = 15;
  public static final int ASSOCSTR_FRIENDLYAPPNAME = 4;
  public static final int ASSOCSTR_FRIENDLYDOCNAME = 3;
  public static final int AW_SLIDE = 262144;
  public static final int AW_ACTIVATE = 131072;
  public static final int AW_BLEND = 524288;
  public static final int AW_HIDE = 65536;
  public static final int AW_CENTER = 16;
  public static final int AW_HOR_POSITIVE = 1;
  public static final int AW_HOR_NEGATIVE = 2;
  public static final int AW_VER_POSITIVE = 4;
  public static final int AW_VER_NEGATIVE = 8;
  public static final int ATTR_INPUT = 0;
  public static final int ATTR_TARGET_CONVERTED = 1;
  public static final int ATTR_CONVERTED = 2;
  public static final int ATTR_TARGET_NOTCONVERTED = 3;
  public static final int ATTR_INPUT_ERROR = 4;
  public static final int ATTR_FIXEDCONVERTED = 5;
  public static final int BCM_FIRST = 5632;
  public static final int BCM_GETIDEALSIZE = 5633;
  public static final int BCM_GETIMAGELIST = 5635;
  public static final int BCM_GETNOTE = 5642;
  public static final int BCM_GETNOTELENGTH = 5643;
  public static final int BCM_SETIMAGELIST = 5634;
  public static final int BCM_SETNOTE = 5641;
  public static final int BDR_RAISEDOUTER = 1;
  public static final int BDR_SUNKENOUTER = 2;
  public static final int BDR_RAISEDINNER = 4;
  public static final int BDR_SUNKENINNER = 8;
  public static final int BDR_OUTER = 3;
  public static final int BDR_INNER = 12;
  public static final int BDR_RAISED = 5;
  public static final int BDR_SUNKEN = 10;
  public static final int BFFM_INITIALIZED = 1;
  public static final int BFFM_SETSELECTION = IsUnicode ? 1127 : 1126;
  public static final int BFFM_VALIDATEFAILED = IsUnicode ? 4 : 3;
  public static final int BFFM_VALIDATEFAILEDW = 4;
  public static final int BFFM_VALIDATEFAILEDA = 3;
  public static final int BF_ADJUST = 8192;
  public static final int BF_LEFT = 1;
  public static final int BF_TOP = 2;
  public static final int BF_RIGHT = 4;
  public static final int BF_BOTTOM = 8;
  public static final int BF_RECT = 15;
  public static final int BIF_EDITBOX = 16;
  public static final int BIF_NEWDIALOGSTYLE = 64;
  public static final int BIF_RETURNONLYFSDIRS = 1;
  public static final int BIF_VALIDATE = 32;
  public static final int BITSPIXEL = 12;
  public static final int BI_BITFIELDS = 3;
  public static final int BI_RGB = 0;
  public static final int BLACKNESS = 66;
  public static final int BLACK_BRUSH = 4;
  public static final int BUTTON_IMAGELIST_ALIGN_LEFT = 0;
  public static final int BUTTON_IMAGELIST_ALIGN_RIGHT = 1;
  public static final int BUTTON_IMAGELIST_ALIGN_CENTER = 4;
  public static final int BM_CLICK = 245;
  public static final int BM_GETCHECK = 240;
  public static final int BM_SETCHECK = 241;
  public static final int BM_SETIMAGE = 247;
  public static final int BM_SETSTYLE = 244;
  public static final int BN_CLICKED = 0;
  public static final int BN_DOUBLECLICKED = 5;
  public static final int BPBF_COMPATIBLEBITMAP = 0;
  public static final int BPBF_DIB = 1;
  public static final int BPBF_TOPDOWNDIB = 2;
  public static final int BPBF_TOPDOWNMONODIB = 3;
  public static final int BPPF_ERASE = 1;
  public static final int BPPF_NOCLIP = 2;
  public static final int BPPF_NONCLIENT = 4;
  public static final int BP_PUSHBUTTON = 1;
  public static final int BP_RADIOBUTTON = 2;
  public static final int BP_CHECKBOX = 3;
  public static final int BP_GROUPBOX = 4;
  public static final int BST_CHECKED = 1;
  public static final int BST_INDETERMINATE = 2;
  public static final int BST_UNCHECKED = 0;
  public static final int BS_3STATE = 5;
  public static final int BS_BITMAP = 128;
  public static final int BS_CENTER = 768;
  public static final int BS_CHECKBOX = 2;
  public static final int BS_COMMANDLINK = 14;
  public static final int BS_DEFPUSHBUTTON = 1;
  public static final int BS_FLAT = 32768;
  public static final int BS_GROUPBOX = 7;
  public static final int BS_ICON = 64;
  public static final int BS_LEFT = 256;
  public static final int BS_NOTIFY = 16384;
  public static final int BS_OWNERDRAW = 11;
  public static final int BS_PATTERN = 3;
  public static final int BS_PUSHBUTTON = 0;
  public static final int BS_PUSHLIKE = 4096;
  public static final int BS_RADIOBUTTON = 4;
  public static final int BS_RIGHT = 512;
  public static final int BS_SOLID = 0;
  public static final int BTNS_AUTOSIZE = 16;
  public static final int BTNS_BUTTON = 0;
  public static final int BTNS_CHECK = 2;
  public static final int BTNS_CHECKGROUP = 6;
  public static final int BTNS_DROPDOWN = 8;
  public static final int BTNS_GROUP = 4;
  public static final int BTNS_SEP = 1;
  public static final int BTNS_SHOWTEXT = 64;
  public static final int CBN_DROPDOWN = 7;
  public static final int CBN_EDITCHANGE = 5;
  public static final int CBN_KILLFOCUS = 4;
  public static final int CBN_SELCHANGE = 1;
  public static final int CBN_SETFOCUS = 3;
  public static final int CBS_AUTOHSCROLL = 64;
  public static final int CBS_DROPDOWN = 2;
  public static final int CBS_DROPDOWNLIST = 3;
  public static final int CBS_CHECKEDNORMAL = 5;
  public static final int CBS_MIXEDNORMAL = 9;
  public static final int CBS_NOINTEGRALHEIGHT = 1024;
  public static final int CBS_SIMPLE = 1;
  public static final int CBS_UNCHECKEDNORMAL = 1;
  public static final int CBS_CHECKEDDISABLED = 8;
  public static final int CBS_CHECKEDHOT = 6;
  public static final int CBS_CHECKEDPRESSED = 7;
  public static final int CBS_MIXEDDISABLED = 0;
  public static final int CBS_MIXEDHOT = 0;
  public static final int CBS_MIXEDPRESSED = 0;
  public static final int CBS_UNCHECKEDDISABLED = 4;
  public static final int CBS_UNCHECKEDHOT = 2;
  public static final int CBS_UNCHECKEDPRESSED = 3;
  public static final int CB_ADDSTRING = 323;
  public static final int CB_DELETESTRING = 324;
  public static final int CB_ERR = -1;
  public static final int CB_ERRSPACE = -2;
  public static final int CB_FINDSTRINGEXACT = 344;
  public static final int CB_GETCOUNT = 326;
  public static final int CB_GETCURSEL = 327;
  public static final int CB_GETDROPPEDCONTROLRECT = 338;
  public static final int CB_GETDROPPEDSTATE = 343;
  public static final int CB_GETDROPPEDWIDTH = 351;
  public static final int CB_GETEDITSEL = 320;
  public static final int CB_GETHORIZONTALEXTENT = 349;
  public static final int CB_GETITEMHEIGHT = 340;
  public static final int CB_GETLBTEXT = 328;
  public static final int CB_GETLBTEXTLEN = 329;
  public static final int CB_INSERTSTRING = 330;
  public static final int CB_LIMITTEXT = 321;
  public static final int CB_RESETCONTENT = 331;
  public static final int CB_SELECTSTRING = 333;
  public static final int CB_SETCURSEL = 334;
  public static final int CB_SETDROPPEDWIDTH = 352;
  public static final int CB_SETEDITSEL = 322;
  public static final int CB_SETHORIZONTALEXTENT = 350;
  public static final int CB_SETITEMHEIGHT = 339;
  public static final int CB_SHOWDROPDOWN = 335;
  public static final int CBXS_NORMAL = 1;
  public static final int CBXS_HOT = 2;
  public static final int CBXS_PRESSED = 3;
  public static final int CBXS_DISABLED = 4;
  public static final int CCHDEVICENAME = 32;
  public static final int CCHFORMNAME = 32;
  public static final int CCHILDREN_SCROLLBAR = 5;
  public static final int CCM_FIRST = 8192;
  public static final int CCM_SETBKCOLOR = 8193;
  public static final int CCM_SETVERSION = 8199;
  public static final int CCS_NODIVIDER = 64;
  public static final int CCS_NORESIZE = 4;
  public static final int CCS_VERT = 128;
  public static final int CC_ANYCOLOR = 256;
  public static final int CC_ENABLEHOOK = 16;
  public static final int CC_FULLOPEN = 2;
  public static final int CC_RGBINIT = 1;
  public static final int CDDS_POSTERASE = 4;
  public static final int CDDS_POSTPAINT = 2;
  public static final int CDDS_PREERASE = 3;
  public static final int CDDS_PREPAINT = 1;
  public static final int CDDS_ITEM = 65536;
  public static final int CDDS_ITEMPOSTPAINT = 65538;
  public static final int CDDS_ITEMPREPAINT = 65537;
  public static final int CDDS_SUBITEM = 131072;
  public static final int CDDS_SUBITEMPOSTPAINT = 196610;
  public static final int CDDS_SUBITEMPREPAINT = 196609;
  public static final int CDIS_SELECTED = 1;
  public static final int CDIS_GRAYED = 2;
  public static final int CDIS_DISABLED = 4;
  public static final int CDIS_CHECKED = 8;
  public static final int CDIS_FOCUS = 16;
  public static final int CDIS_DEFAULT = 32;
  public static final int CDIS_DROPHILITED = 4096;
  public static final int CDIS_HOT = 64;
  public static final int CDIS_MARKED = 128;
  public static final int CDIS_INDETERMINATE = 256;
  public static final int CDM_FIRST = 1124;
  public static final int CDM_GETSPEC = 1124;
  public static final int CDN_FIRST = -601;
  public static final int CDN_SELCHANGE = -602;
  public static final int CDRF_DODEFAULT = 0;
  public static final int CDRF_DOERASE = 8;
  public static final int CDRF_NEWFONT = 2;
  public static final int CDRF_NOTIFYITEMDRAW = 32;
  public static final int CDRF_NOTIFYPOSTERASE = 64;
  public static final int CDRF_NOTIFYPOSTPAINT = 16;
  public static final int CDRF_NOTIFYSUBITEMDRAW = 32;
  public static final int CDRF_SKIPDEFAULT = 4;
  public static final int CDRF_SKIPPOSTPAINT = 256;
  public static final int CFE_AUTOCOLOR = 1073741824;
  public static final int CFE_ITALIC = 2;
  public static final int CFE_STRIKEOUT = 8;
  public static final int CFE_UNDERLINE = 4;
  public static final int CFM_BOLD = 1;
  public static final int CFM_CHARSET = 134217728;
  public static final int CFM_COLOR = 1073741824;
  public static final int CFM_FACE = 536870912;
  public static final int CFM_ITALIC = 2;
  public static final int CFM_SIZE = -2147483648;
  public static final int CFM_STRIKEOUT = 8;
  public static final int CFM_UNDERLINE = 4;
  public static final int CFM_WEIGHT = 4194304;
  public static final int CFS_POINT = 2;
  public static final int CFS_RECT = 1;
  public static final int CFS_CANDIDATEPOS = 64;
  public static final int CFS_EXCLUDE = 128;
  public static final int CF_EFFECTS = 256;
  public static final int CF_INITTOLOGFONTSTRUCT = 64;
  public static final int CF_SCREENFONTS = 1;
  public static final int CF_TEXT = 1;
  public static final int CF_UNICODETEXT = 13;
  public static final int CF_USESTYLE = 128;
  public static final int CLR_DEFAULT = -16777216;
  public static final int CLR_INVALID = -1;
  public static final int CLR_NONE = -1;
  public static final int CLSCTX_INPROC_SERVER = 1;
  public static final int CSIDL_APPDATA = 26;
  public static final int CSIDL_LOCAL_APPDATA = 28;
  public static final int COLORONCOLOR = 3;
  public static final int COLOR_3DDKSHADOW = 0x15 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_3DFACE = 0xF | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_3DHIGHLIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_3DHILIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_3DLIGHT = 0x16 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_3DSHADOW = 0x10 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_ACTIVECAPTION = 0x2 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_BTNFACE = 0xF | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_BTNHIGHLIGHT = 0x14 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_BTNSHADOW = 0x10 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_BTNTEXT = 0x12 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_CAPTIONTEXT = 0x9 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_GRADIENTACTIVECAPTION = 0x1B | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_GRADIENTINACTIVECAPTION = 0x1C | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_GRAYTEXT = 0x11 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_HIGHLIGHT = 0xD | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_HIGHLIGHTTEXT = 0xE | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_HOTLIGHT = 0x1A | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_INACTIVECAPTION = 0x3 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_INACTIVECAPTIONTEXT = 0x13 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_INFOBK = 0x18 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_INFOTEXT = 0x17 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_MENU = 0x4 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_MENUTEXT = 0x7 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_SCROLLBAR = SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_WINDOW = 0x5 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_WINDOWFRAME = 0x6 | SYS_COLOR_INDEX_FLAG;
  public static final int COLOR_WINDOWTEXT = 0x8 | SYS_COLOR_INDEX_FLAG;
  public static final int COMPLEXREGION = 3;
  public static final int CP_ACP = 0;
  public static final int CP_UTF8 = 65001;
  public static final int CP_DROPDOWNBUTTON = 1;
  public static final int CP_INSTALLED = 1;
  public static final int CPS_COMPLETE = 1;
  public static final int CS_BYTEALIGNWINDOW = 8192;
  public static final int CS_DBLCLKS = 8;
  public static final int CS_DROPSHADOW = 131072;
  public static final int CS_GLOBALCLASS = 16384;
  public static final int CS_HREDRAW = 2;
  public static final int CS_VREDRAW = 1;
  public static final int CS_OWNDC = 32;
  public static final int CW_USEDEFAULT = -2147483648;
  public static final String DATETIMEPICK_CLASS = "SysDateTimePick32";
  public static final int DATE_LONGDATE = 2;
  public static final int DATE_SHORTDATE = 1;
  public static final int DATE_YEARMONTH = 8;
  public static final int DCX_CACHE = 2;
  public static final int DCX_CLIPCHILDREN = 8;
  public static final int DCX_CLIPSIBLINGS = 16;
  public static final int DCX_INTERSECTRGN = 128;
  public static final int DCX_WINDOW = 1;
  public static final int DEFAULT_CHARSET = 1;
  public static final int DEFAULT_GUI_FONT = 17;
  public static final int DFCS_BUTTONCHECK = 0;
  public static final int DFCS_CHECKED = 1024;
  public static final int DFCS_FLAT = 16384;
  public static final int DFCS_INACTIVE = 256;
  public static final int DFCS_PUSHED = 512;
  public static final int DFCS_SCROLLDOWN = 1;
  public static final int DFCS_SCROLLLEFT = 2;
  public static final int DFCS_SCROLLRIGHT = 3;
  public static final int DFCS_SCROLLUP = 0;
  public static final int DFC_BUTTON = 4;
  public static final int DFC_SCROLL = 3;
  public static final int DIB_RGB_COLORS = 0;
  public static final int DISP_E_EXCEPTION = -2147352567;
  public static final int DI_NORMAL = 3;
  public static final int DI_NOMIRROR = 16;
  public static final int DLGC_BUTTON = 8192;
  public static final int DLGC_HASSETSEL = 8;
  public static final int DLGC_STATIC = 256;
  public static final int DLGC_WANTALLKEYS = 4;
  public static final int DLGC_WANTARROWS = 1;
  public static final int DLGC_WANTCHARS = 128;
  public static final int DLGC_WANTTAB = 2;
  public static final short DMCOLLATE_FALSE = 0;
  public static final short DMCOLLATE_TRUE = 1;
  public static final int DM_SETDEFID = 1025;
  public static final int DM_COLLATE = 32768;
  public static final int DM_COPIES = 256;
  public static final int DM_ORIENTATION = 1;
  public static final short DMORIENT_PORTRAIT = 1;
  public static final short DMORIENT_LANDSCAPE = 2;
  public static final int DSS_DISABLED = 32;
  public static final int DSTINVERT = 5570569;
  public static final int DST_BITMAP = 4;
  public static final int DST_ICON = 3;
  public static final int DT_BOTTOM = 8;
  public static final int DT_CALCRECT = 1024;
  public static final int DT_CENTER = 1;
  public static final int DT_EDITCONTROL = 8192;
  public static final int DT_EXPANDTABS = 64;
  public static final int DT_ENDELLIPSIS = 32768;
  public static final int DT_HIDEPREFIX = 1048576;
  public static final int DT_LEFT = 0;
  public static final int DT_NOPREFIX = 2048;
  public static final int DT_RASPRINTER = 2;
  public static final int DT_RIGHT = 2;
  public static final int DT_RTLREADING = 131072;
  public static final int DT_SINGLELINE = 32;
  public static final int DT_TOP = 0;
  public static final int DT_VCENTER = 4;
  public static final int DT_WORDBREAK = 16;
  public static final int DTM_FIRST = 4096;
  public static final int DTM_GETSYSTEMTIME = 4097;
  public static final int DTM_SETFORMAT = IsUnicode ? 4146 : 4101;
  public static final int DTM_SETSYSTEMTIME = 4098;
  public static final int DTN_FIRST = -760;
  public static final int DTN_DATETIMECHANGE = -759;
  public static final int DTN_CLOSEUP = -753;
  public static final int DTN_DROPDOWN = -754;
  public static final int DTS_LONGDATEFORMAT = 4;
  public static final int DTS_SHORTDATECENTURYFORMAT = 12;
  public static final int DTS_SHORTDATEFORMAT = 0;
  public static final int DTS_TIMEFORMAT = 9;
  public static final int DTS_UPDOWN = 1;
  public static final int DWM_BB_ENABLE = 1;
  public static final int DWM_BB_BLURREGION = 2;
  public static final int DWM_BB_TRANSITIONONMAXIMIZED = 4;
  public static final int E_POINTER = -2147467261;
  public static final int EBP_NORMALGROUPBACKGROUND = 5;
  public static final int EBP_NORMALGROUPCOLLAPSE = 6;
  public static final int EBP_NORMALGROUPEXPAND = 7;
  public static final int EBP_NORMALGROUPHEAD = 8;
  public static final int EBNGC_NORMAL = 1;
  public static final int EBNGC_HOT = 2;
  public static final int EBNGC_PRESSED = 3;
  public static final int EBP_HEADERBACKGROUND = 1;
  public static final int EC_LEFTMARGIN = 1;
  public static final int EC_RIGHTMARGIN = 2;
  public static final int ECOOP_AND = 3;
  public static final int ECOOP_OR = 2;
  public static final int ECO_AUTOHSCROLL = 128;
  public static final int EDGE_RAISED = 5;
  public static final int EDGE_SUNKEN = 10;
  public static final int EDGE_ETCHED = 6;
  public static final int EDGE_BUMP = 9;
  public static final int ELF_VENDOR_SIZE = 4;
  public static final int EM_CANUNDO = 198;
  public static final int EM_CHARFROMPOS = 215;
  public static final int EM_DISPLAYBAND = 1075;
  public static final int EM_GETFIRSTVISIBLELINE = 206;
  public static final int EM_GETLIMITTEXT = 213;
  public static final int EM_GETLINE = 196;
  public static final int EM_GETLINECOUNT = 186;
  public static final int EM_GETMARGINS = 212;
  public static final int EM_GETPASSWORDCHAR = 210;
  public static final int EM_GETSCROLLPOS = 1245;
  public static final int EM_GETSEL = 176;
  public static final int EM_LIMITTEXT = 197;
  public static final int EM_LINEFROMCHAR = 201;
  public static final int EM_LINEINDEX = 187;
  public static final int EM_LINELENGTH = 193;
  public static final int EM_LINESCROLL = 182;
  public static final int EM_POSFROMCHAR = 214;
  public static final int EM_REPLACESEL = 194;
  public static final int EM_SCROLLCARET = 183;
  public static final int EM_SETBKGNDCOLOR = 1091;
  public static final int EM_SETLIMITTEXT = 197;
  public static final int EM_SETMARGINS = 211;
  public static final int EM_SETOPTIONS = 1101;
  public static final int EM_SETPARAFORMAT = 1095;
  public static final int EM_SETPASSWORDCHAR = 204;
  public static final int EM_SETCUEBANNER = 5377;
  public static final int EM_SETREADONLY = 207;
  public static final int EM_SETRECT = 179;
  public static final int EM_SETSEL = 177;
  public static final int EM_SETTABSTOPS = 203;
  public static final int EM_UNDO = 199;
  public static final int EMR_EXTCREATEFONTINDIRECTW = 82;
  public static final int EMR_EXTTEXTOUTW = 84;
  public static final int EN_ALIGN_LTR_EC = 1792;
  public static final int EN_ALIGN_RTL_EC = 1793;
  public static final int EN_CHANGE = 768;
  public static final int EP_EDITTEXT = 1;
  public static final int ERROR_NO_MORE_ITEMS = 259;
  public static final int ESB_DISABLE_BOTH = 3;
  public static final int ESB_ENABLE_BOTH = 0;
  public static final int ES_AUTOHSCROLL = 128;
  public static final int ES_AUTOVSCROLL = 64;
  public static final int ES_CENTER = 1;
  public static final int ES_MULTILINE = 4;
  public static final int ES_NOHIDESEL = 256;
  public static final int ES_PASSWORD = 32;
  public static final int ES_READONLY = 2048;
  public static final int ES_RIGHT = 2;
  public static final int ETO_CLIPPED = 4;
  public static final int ETS_NORMAL = 1;
  public static final int ETS_HOT = 2;
  public static final int ETS_SELECTED = 3;
  public static final int ETS_DISABLED = 4;
  public static final int ETS_FOCUSED = 5;
  public static final int ETS_READONLY = 6;
  public static final int EVENT_OBJECT_FOCUS = 32773;
  public static final int EVENT_OBJECT_LOCATIONCHANGE = 32779;
  public static final int EVENT_OBJECT_SELECTIONWITHIN = 32777;
  public static final int EVENT_OBJECT_VALUECHANGE = 32782;
  public static final short FADF_FIXEDSIZE = 16;
  public static final short FADF_HAVEVARTYPE = 128;
  public static final int FALT = 16;
  public static final int FCONTROL = 8;
  public static final int FE_FONTSMOOTHINGCLEARTYPE = 2;
  public static final int FEATURE_DISABLE_NAVIGATION_SOUNDS = 21;
  public static final int FILE_ATTRIBUTE_DIRECTORY = 16;
  public static final int FILE_ATTRIBUTE_NORMAL = 128;
  public static final int FILE_MAP_READ = 4;
  public static final int FNERR_INVALIDFILENAME = 12290;
  public static final int FNERR_BUFFERTOOSMALL = 12291;
  public static final int FOF_SILENT = 4;
  public static final int FOF_NOCONFIRMATION = 16;
  public static final int FOF_NOCONFIRMMKDIR = 512;
  public static final int FOF_NOERRORUI = 1024;
  public static final int FOF_NO_UI = 1556;
  public static final int FORMAT_MESSAGE_ALLOCATE_BUFFER = 256;
  public static final int FORMAT_MESSAGE_FROM_SYSTEM = 4096;
  public static final int FORMAT_MESSAGE_IGNORE_INSERTS = 512;
  public static final int FR_PRIVATE = 16;
  public static final int FSHIFT = 4;
  public static final int FVIRTKEY = 1;
  public static final int GBS_NORMAL = 1;
  public static final int GBS_DISABLED = 2;
  public static final int GCP_REORDER = 2;
  public static final int GCP_GLYPHSHAPE = 16;
  public static final int GCP_CLASSIN = 524288;
  public static final int GCP_LIGATE = 32;
  public static final int GCS_COMPSTR = 8;
  public static final int GCS_RESULTSTR = 2048;
  public static final int GCS_COMPATTR = 16;
  public static final int GCS_COMPCLAUSE = 32;
  public static final int GCS_CURSORPOS = 128;
  public static final int GDT_VALID = 0;
  public static final int GET_FEATURE_FROM_PROCESS = 2;
  public static final int GGI_MARK_NONEXISTING_GLYPHS = 1;
  public static final int GLPS_CLOSED = 1;
  public static final int GLPS_OPENED = 2;
  public static final int GM_ADVANCED = 2;
  public static final int GMDI_USEDISABLED = 1;
  public static final int GMEM_FIXED = 0;
  public static final int GMEM_ZEROINIT = 64;
  public static final int GN_CONTEXTMENU = 1000;
  public static final int GPTR = 64;
  public static final int GRADIENT_FILL_RECT_H = 0;
  public static final int GRADIENT_FILL_RECT_V = 1;
  public static final int GTL_NUMBYTES = 16;
  public static final int GTL_NUMCHARS = 8;
  public static final int GTL_PRECISE = 2;
  public static final int GT_DEFAULT = 0;
  public static final int GUI_16BITTASK = 32;
  public static final int GUI_CARETBLINKING = 1;
  public static final int GUI_INMENUMODE = 4;
  public static final int GUI_INMOVESIZE = 2;
  public static final int GUI_POPUPMENUMODE = 16;
  public static final int GUI_SYSTEMMENUMODE = 8;
  public static final int GWL_EXSTYLE = -20;
  public static final int GWL_ID = -12;
  public static final int GWL_HWNDPARENT = -8;
  public static final int GWL_STYLE = -16;
  public static final int GWL_USERDATA = -21;
  public static final int GWL_WNDPROC = -4;
  public static final int GWLP_ID = -12;
  public static final int GWLP_HWNDPARENT = -8;
  public static final int GWLP_USERDATA = -21;
  public static final int GWLP_WNDPROC = -4;
  public static final int GW_CHILD = 5;
  public static final int GW_HWNDFIRST = 0;
  public static final int GW_HWNDLAST = 1;
  public static final int GW_HWNDNEXT = 2;
  public static final int GW_HWNDPREV = 3;
  public static final int GW_OWNER = 4;
  public static final int HBMMENU_CALLBACK = -1;
  public static final int HCBT_CREATEWND = 3;
  public static final int HCF_HIGHCONTRASTON = 1;
  public static final int HDF_BITMAP = 8192;
  public static final int HDF_BITMAP_ON_RIGHT = 4096;
  public static final int HDF_CENTER = 2;
  public static final int HDF_JUSTIFYMASK = 3;
  public static final int HDF_IMAGE = 2048;
  public static final int HDF_LEFT = 0;
  public static final int HDF_RIGHT = 1;
  public static final int HDF_SORTUP = 1024;
  public static final int HDF_SORTDOWN = 512;
  public static final int HDI_BITMAP = 16;
  public static final int HDI_IMAGE = 32;
  public static final int HDI_ORDER = 128;
  public static final int HDI_TEXT = 2;
  public static final int HDI_WIDTH = 1;
  public static final int HDI_FORMAT = 4;
  public static final int HDM_FIRST = 4608;
  public static final int HDM_DELETEITEM = 4610;
  public static final int HDM_GETBITMAPMARGIN = 4629;
  public static final int HDM_GETITEMCOUNT = 4608;
  public static final int HDM_GETITEMA = 4611;
  public static final int HDM_GETITEMW = 4619;
  public static final int HDM_GETITEM = IsUnicode ? 4619 : 4611;
  public static final int HDM_GETITEMRECT = 4615;
  public static final int HDM_GETORDERARRAY = 4625;
  public static final int HDM_HITTEST = 4614;
  public static final int HDM_INSERTITEMA = 4609;
  public static final int HDM_INSERTITEMW = 4618;
  public static final int HDM_INSERTITEM = IsUnicode ? 4618 : 4609;
  public static final int HDM_LAYOUT = 4613;
  public static final int HDM_ORDERTOINDEX = 4623;
  public static final int HDM_SETIMAGELIST = 4616;
  public static final int HDM_SETITEMA = 4612;
  public static final int HDM_SETITEMW = 4620;
  public static final int HDM_SETITEM = IsUnicode ? 4620 : 4612;
  public static final int HDM_SETORDERARRAY = 4626;
  public static final int HDN_FIRST = -300;
  public static final int HDN_BEGINDRAG = -310;
  public static final int HDN_BEGINTRACK = IsUnicode ? -326 : -306;
  public static final int HDN_BEGINTRACKW = -326;
  public static final int HDN_BEGINTRACKA = -306;
  public static final int HDN_DIVIDERDBLCLICKA = -305;
  public static final int HDN_DIVIDERDBLCLICKW = -325;
  public static final int HDN_DIVIDERDBLCLICK = IsUnicode ? -325 : -305;
  public static final int HDN_ENDDRAG = -311;
  public static final int HDN_ITEMCHANGED = IsUnicode ? -321 : -301;
  public static final int HDN_ITEMCHANGEDW = -321;
  public static final int HDN_ITEMCHANGEDA = -301;
  public static final int HDN_ITEMCHANGINGW = -320;
  public static final int HDN_ITEMCHANGINGA = -300;
  public static final int HDN_ITEMCLICKW = -322;
  public static final int HDN_ITEMCLICKA = -302;
  public static final int HDN_ITEMDBLCLICKW = -323;
  public static final int HDN_ITEMDBLCLICKA = -303;
  public static final int HDN_ITEMDBLCLICK = IsUnicode ? -323 : -303;
  public static final int HDS_BUTTONS = 2;
  public static final int HDS_DRAGDROP = 64;
  public static final int HDS_FULLDRAG = 128;
  public static final int HDS_HIDDEN = 8;
  public static final int HELPINFO_MENUITEM = 2;
  public static final int HHT_ONDIVIDER = 4;
  public static final int HHT_ONDIVOPEN = 8;
  public static final int HICF_ARROWKEYS = 2;
  public static final int HICF_LEAVING = 32;
  public static final int HICF_MOUSE = 1;
  public static final int HINST_COMMCTRL = -1;
  public static final int HKEY_CLASSES_ROOT = -2147483648;
  public static final int HKEY_CURRENT_USER = -2147483647;
  public static final int HKEY_LOCAL_MACHINE = -2147483646;
  public static final int HORZRES = 8;
  public static final int HTBORDER = 18;
  public static final int HTCAPTION = 2;
  public static final int HTCLIENT = 1;
  public static final int HTERROR = -2;
  public static final int HTHSCROLL = 6;
  public static final int HTMENU = 5;
  public static final int HTNOWHERE = 0;
  public static final int HTSYSMENU = 3;
  public static final int HTTRANSPARENT = -1;
  public static final int HTVSCROLL = 7;
  public static final int HWND_BOTTOM = 1;
  public static final int HWND_TOP = 0;
  public static final int HWND_TOPMOST = -1;
  public static final int HWND_NOTOPMOST = -2;
  public static final int ICC_COOL_CLASSES = 1024;
  public static final int ICC_DATE_CLASSES = 256;
  public static final int ICM_NOTOPEN = 0;
  public static final int ICON_BIG = 1;
  public static final int ICON_SMALL = 0;
  public static final int I_IMAGECALLBACK = -1;
  public static final int I_IMAGENONE = -2;
  public static final int IDABORT = 3;
  public static final int IDANI_CAPTION = 3;
  public static final int IDB_STD_SMALL_COLOR = 0;
  public static final int IDC_APPSTARTING = 32650;
  public static final int IDC_ARROW = 32512;
  public static final int IDC_CROSS = 32515;
  public static final int IDC_HAND = 32649;
  public static final int IDC_HELP = 32651;
  public static final int IDC_IBEAM = 32513;
  public static final int IDC_NO = 32648;
  public static final int IDC_SIZE = 32640;
  public static final int IDC_SIZEALL = 32646;
  public static final int IDC_SIZENESW = 32643;
  public static final int IDC_SIZENS = 32645;
  public static final int IDC_SIZENWSE = 32642;
  public static final int IDC_SIZEWE = 32644;
  public static final int IDC_UPARROW = 32516;
  public static final int IDC_WAIT = 32514;
  public static final int IDI_APPLICATION = 32512;
  public static final int IDNO = 7;
  public static final int IDOK = 1;
  public static final int IDRETRY = 4;
  public static final int IDYES = 6;
  public static final int ILC_COLOR = 0;
  public static final int ILC_COLOR16 = 16;
  public static final int ILC_COLOR24 = 24;
  public static final int ILC_COLOR32 = 32;
  public static final int ILC_COLOR4 = 4;
  public static final int ILC_COLOR8 = 8;
  public static final int ILC_MASK = 1;
  public static final int ILC_MIRROR = 8192;
  public static final int ILD_NORMAL = 0;
  public static final int ILD_SELECTED = 4;
  public static final int IMAGE_BITMAP = 0;
  public static final int IMAGE_CURSOR = 2;
  public static final int IMAGE_ICON = 1;
  public static final int IME_CMODE_FULLSHAPE = 8;
  public static final int IME_CMODE_KATAKANA = 2;
  public static final int IME_CMODE_NATIVE = 1;
  public static final int IME_CMODE_ROMAN = 16;
  public static final int IMEMOUSE_LDOWN = 1;
  public static final int INFINITE = -1;
  public static final int INPUT_KEYBOARD = 1;
  public static final int INPUT_MOUSE = 0;
  public static final int INTERNET_OPTION_END_BROWSER_SESSION = 42;
  public static final int KEY_ENUMERATE_SUB_KEYS = 8;
  public static final int KEY_NOTIFY = 16;
  public static final int KEY_QUERY_VALUE = 1;
  public static final int KEY_READ = 131097;
  public static final int KEYEVENTF_EXTENDEDKEY = 1;
  public static final int KEYEVENTF_KEYUP = 2;
  public static final int L_MAX_URL_LENGTH = 2084;
  public static final int LANG_NEUTRAL = 0;
  public static final int LANG_USER_DEFAULT = 1024;
  public static final int LAYOUT_RTL = 1;
  public static final int LAYOUT_BITMAPORIENTATIONPRESERVED = 8;
  public static final int LBN_DBLCLK = 2;
  public static final int LBN_SELCHANGE = 1;
  public static final int LBS_EXTENDEDSEL = 2048;
  public static final int LBS_MULTIPLESEL = 8;
  public static final int LBS_NOINTEGRALHEIGHT = 256;
  public static final int LBS_NOTIFY = 1;
  public static final int LB_ADDSTRING = 384;
  public static final int LB_DELETESTRING = 386;
  public static final int LB_ERR = -1;
  public static final int LB_ERRSPACE = -2;
  public static final int LB_FINDSTRINGEXACT = 418;
  public static final int LB_GETCARETINDEX = 415;
  public static final int LB_GETCOUNT = 395;
  public static final int LB_GETCURSEL = 392;
  public static final int LB_GETHORIZONTALEXTENT = 403;
  public static final int LB_GETITEMHEIGHT = 417;
  public static final int LB_GETITEMRECT = 408;
  public static final int LB_GETSEL = 391;
  public static final int LB_GETSELCOUNT = 400;
  public static final int LB_GETSELITEMS = 401;
  public static final int LB_GETTEXT = 393;
  public static final int LB_GETTEXTLEN = 394;
  public static final int LB_GETTOPINDEX = 398;
  public static final int LB_INITSTORAGE = 424;
  public static final int LB_INSERTSTRING = 385;
  public static final int LB_RESETCONTENT = 388;
  public static final int LB_SELITEMRANGE = 411;
  public static final int LB_SELITEMRANGEEX = 387;
  public static final int LB_SETANCHORINDEX = 61852;
  public static final int LB_SETCARETINDEX = 414;
  public static final int LB_SETCURSEL = 390;
  public static final int LB_SETHORIZONTALEXTENT = 404;
  public static final int LB_SETSEL = 389;
  public static final int LB_SETTOPINDEX = 407;
  public static final int LF_FULLFACESIZE = 64;
  public static final int LF_FACESIZE = 32;
  public static final int LGRPID_ARABIC = 13;
  public static final int LGRPID_HEBREW = 12;
  public static final int LGRPID_INSTALLED = 1;
  public static final int LIF_ITEMINDEX = 1;
  public static final int LIF_STATE = 2;
  public static final int LIS_FOCUSED = 1;
  public static final int LIS_ENABLED = 2;
  public static final int LISS_HOT = 2;
  public static final int LISS_SELECTED = 3;
  public static final int LISS_SELECTEDNOTFOCUS = 5;
  public static final int LM_GETIDEALHEIGHT = 1793;
  public static final int LM_SETITEM = 1794;
  public static final int LM_GETITEM = 1795;
  public static final int LCID_SUPPORTED = 2;
  public static final int LOCALE_IDEFAULTANSICODEPAGE = 4100;
  public static final int LOCALE_IDATE = 33;
  public static final int LOCALE_ITIME = 35;
  public static final int LOCALE_RETURN_NUMBER = 536870912;
  public static final int LOCALE_S1159 = 40;
  public static final int LOCALE_S2359 = 41;
  public static final int LOCALE_SDECIMAL = 14;
  public static final int LOCALE_SISO3166CTRYNAME = 90;
  public static final int LOCALE_SISO639LANGNAME = 89;
  public static final int LOCALE_SLONGDATE = 32;
  public static final int LOCALE_SSHORTDATE = 31;
  public static final int LOCALE_STIMEFORMAT = 4099;
  public static final int LOCALE_SYEARMONTH = 4102;
  public static final int LOCALE_SDAYNAME1 = 42;
  public static final int LOCALE_SDAYNAME2 = 43;
  public static final int LOCALE_SDAYNAME3 = 44;
  public static final int LOCALE_SDAYNAME4 = 45;
  public static final int LOCALE_SDAYNAME5 = 46;
  public static final int LOCALE_SDAYNAME6 = 47;
  public static final int LOCALE_SDAYNAME7 = 48;
  public static final int LOCALE_SMONTHNAME1 = 56;
  public static final int LOCALE_SMONTHNAME2 = 57;
  public static final int LOCALE_SMONTHNAME3 = 58;
  public static final int LOCALE_SMONTHNAME4 = 59;
  public static final int LOCALE_SMONTHNAME5 = 60;
  public static final int LOCALE_SMONTHNAME6 = 61;
  public static final int LOCALE_SMONTHNAME7 = 62;
  public static final int LOCALE_SMONTHNAME8 = 63;
  public static final int LOCALE_SMONTHNAME9 = 64;
  public static final int LOCALE_SMONTHNAME10 = 65;
  public static final int LOCALE_SMONTHNAME11 = 66;
  public static final int LOCALE_SMONTHNAME12 = 67;
  public static final int LOCALE_USER_DEFAULT = 1024;
  public static final int LOGPIXELSX = 88;
  public static final int LOGPIXELSY = 90;
  public static final int LPSTR_TEXTCALLBACK = -1;
  public static final int LR_DEFAULTCOLOR = 0;
  public static final int LR_SHARED = 32768;
  public static final int LVCFMT_BITMAP_ON_RIGHT = 4096;
  public static final int LVCFMT_CENTER = 2;
  public static final int LVCFMT_IMAGE = 2048;
  public static final int LVCFMT_LEFT = 0;
  public static final int LVCFMT_RIGHT = 1;
  public static final int LVCF_FMT = 1;
  public static final int LVCF_IMAGE = 16;
  public static final int LVCFMT_JUSTIFYMASK = 3;
  public static final int LVCF_TEXT = 4;
  public static final int LVCF_WIDTH = 2;
  public static final int LVHT_ONITEM = 14;
  public static final int LVHT_ONITEMICON = 2;
  public static final int LVHT_ONITEMLABEL = 4;
  public static final int LVHT_ONITEMSTATEICON = 8;
  public static final int LVIF_IMAGE = 2;
  public static final int LVIF_INDENT = 16;
  public static final int LVIF_STATE = 8;
  public static final int LVIF_TEXT = 1;
  public static final int LVIM_AFTER = 1;
  public static final int LVIR_BOUNDS = 0;
  public static final int LVIR_ICON = 1;
  public static final int LVIR_LABEL = 2;
  public static final int LVIR_SELECTBOUNDS = 3;
  public static final int LVIS_DROPHILITED = 8;
  public static final int LVIS_FOCUSED = 1;
  public static final int LVIS_SELECTED = 2;
  public static final int LVIS_STATEIMAGEMASK = 61440;
  public static final int LVM_FIRST = 4096;
  public static final int LVM_APPROXIMATEVIEWRECT = 4160;
  public static final int LVM_CREATEDRAGIMAGE = 4129;
  public static final int LVM_DELETEALLITEMS = 4105;
  public static final int LVM_DELETECOLUMN = 4124;
  public static final int LVM_DELETEITEM = 4104;
  public static final int LVM_ENSUREVISIBLE = 4115;
  public static final int LVM_GETBKCOLOR = 4096;
  public static final int LVM_GETCOLUMN = IsUnicode ? 4191 : 4121;
  public static final int LVM_GETCOLUMNORDERARRAY = 4155;
  public static final int LVM_GETCOLUMNWIDTH = 4125;
  public static final int LVM_GETCOUNTPERPAGE = 4136;
  public static final int LVM_GETEXTENDEDLISTVIEWSTYLE = 4151;
  public static final int LVM_GETHEADER = 4127;
  public static final int LVM_GETIMAGELIST = 4098;
  public static final int LVM_GETITEM = IsUnicode ? 4171 : 4101;
  public static final int LVM_GETITEMW = 4171;
  public static final int LVM_GETITEMA = 4101;
  public static final int LVM_GETITEMCOUNT = 4100;
  public static final int LVM_GETITEMRECT = 4110;
  public static final int LVM_GETITEMSTATE = 4140;
  public static final int LVM_GETNEXTITEM = 4108;
  public static final int LVM_GETSELECTEDCOLUMN = 4270;
  public static final int LVM_GETSELECTEDCOUNT = 4146;
  public static final int LVM_GETSTRINGWIDTH = IsUnicode ? 4183 : 4113;
  public static final int LVM_GETSUBITEMRECT = 4152;
  public static final int LVM_GETTEXTCOLOR = 4131;
  public static final int LVM_GETTOOLTIPS = 4174;
  public static final int LVM_GETTOPINDEX = 4135;
  public static final int LVM_HITTEST = 4114;
  public static final int LVM_INSERTCOLUMN = IsUnicode ? 4193 : 4123;
  public static final int LVM_INSERTITEM = IsUnicode ? 4173 : 4103;
  public static final int LVM_REDRAWITEMS = 4117;
  public static final int LVM_SCROLL = 4116;
  public static final int LVM_SETBKCOLOR = 4097;
  public static final int LVM_SETCALLBACKMASK = 4107;
  public static final int LVM_SETCOLUMN = IsUnicode ? 4192 : 4122;
  public static final int LVM_SETCOLUMNORDERARRAY = 4154;
  public static final int LVM_SETCOLUMNWIDTH = 4126;
  public static final int LVM_SETEXTENDEDLISTVIEWSTYLE = 4150;
  public static final int LVM_SETIMAGELIST = 4099;
  public static final int LVM_SETINSERTMARK = 4262;
  public static final int LVM_SETITEM = IsUnicode ? 4172 : 4102;
  public static final int LVM_SETITEMCOUNT = 4143;
  public static final int LVM_SETITEMSTATE = 4139;
  public static final int LVM_SETSELECTIONMARK = 4163;
  public static final int LVM_SETSELECTEDCOLUMN = 4236;
  public static final int LVM_SETTEXTBKCOLOR = 4134;
  public static final int LVM_SETTEXTCOLOR = 4132;
  public static final int LVM_SETTOOLTIPS = 4170;
  public static final int LVM_SUBITEMHITTEST = 4153;
  public static final int LVNI_FOCUSED = 1;
  public static final int LVNI_SELECTED = 2;
  public static final int LVN_BEGINDRAG = -109;
  public static final int LVN_BEGINRDRAG = -111;
  public static final int LVN_COLUMNCLICK = -108;
  public static final int LVN_FIRST = -100;
  public static final int LVN_GETDISPINFOA = -150;
  public static final int LVN_GETDISPINFOW = -177;
  public static final int LVN_ITEMACTIVATE = -114;
  public static final int LVN_ITEMCHANGED = -101;
  public static final int LVN_MARQUEEBEGIN = -156;
  public static final int LVN_ODFINDITEMA = -152;
  public static final int LVN_ODFINDITEMW = -179;
  public static final int LVN_ODSTATECHANGED = -115;
  public static final int LVP_LISTITEM = 1;
  public static final int LVSCW_AUTOSIZE = -1;
  public static final int LVSCW_AUTOSIZE_USEHEADER = -2;
  public static final int LVSICF_NOINVALIDATEALL = 1;
  public static final int LVSICF_NOSCROLL = 2;
  public static final int LVSIL_SMALL = 1;
  public static final int LVSIL_STATE = 2;
  public static final int LVS_EX_DOUBLEBUFFER = 65536;
  public static final int LVS_EX_FULLROWSELECT = 32;
  public static final int LVS_EX_GRIDLINES = 1;
  public static final int LVS_EX_HEADERDRAGDROP = 16;
  public static final int LVS_EX_LABELTIP = 16384;
  public static final int LVS_EX_ONECLICKACTIVATE = 64;
  public static final int LVS_EX_SUBITEMIMAGES = 2;
  public static final int LVS_EX_TRACKSELECT = 8;
  public static final int LVS_EX_TRANSPARENTBKGND = 8388608;
  public static final int LVS_EX_TWOCLICKACTIVATE = 128;
  public static final int LVS_LIST = 3;
  public static final int LVS_NOCOLUMNHEADER = 16384;
  public static final int LVS_NOSCROLL = 8192;
  public static final int LVS_OWNERDATA = 4096;
  public static final int LVS_OWNERDRAWFIXED = 1024;
  public static final int LVS_REPORT = 1;
  public static final int LVS_SHAREIMAGELISTS = 64;
  public static final int LVS_SHOWSELALWAYS = 8;
  public static final int LVS_SINGLESEL = 4;
  public static final int LWA_COLORKEY = 1;
  public static final int LWA_ALPHA = 2;
  public static final int MAX_LINKID_TEXT = 48;
  public static final int MA_NOACTIVATE = 3;
  public static final int MB_ABORTRETRYIGNORE = 2;
  public static final int MB_APPLMODAL = 0;
  public static final int MB_ICONERROR = 16;
  public static final int MB_ICONINFORMATION = 64;
  public static final int MB_ICONQUESTION = 32;
  public static final int MB_ICONWARNING = 48;
  public static final int MB_OK = 0;
  public static final int MB_OKCANCEL = 1;
  public static final int MB_PRECOMPOSED = 1;
  public static final int MB_RETRYCANCEL = 5;
  public static final int MB_RIGHT = 524288;
  public static final int MB_RTLREADING = 1048576;
  public static final int MB_SYSTEMMODAL = 4096;
  public static final int MB_TASKMODAL = 8192;
  public static final int MB_TOPMOST = 262144;
  public static final int MB_YESNO = 4;
  public static final int MB_YESNOCANCEL = 3;
  public static final int MCHT_CALENDAR = 131072;
  public static final int MCHT_CALENDARDATE = 131073;
  public static final int MCM_FIRST = 4096;
  public static final int MCM_GETCURSEL = 4097;
  public static final int MCM_GETMINREQRECT = 4105;
  public static final int MCM_HITTEST = 4110;
  public static final int MCM_SETCURSEL = 4098;
  public static final int MCN_FIRST = -750;
  public static final int MCN_SELCHANGE = -749;
  public static final int MCN_SELECT = -746;
  public static final int MCS_NOTODAY = 16;
  public static final int MDIS_ALLCHILDSTYLES = 1;
  public static final int MFS_CHECKED = 8;
  public static final int MFS_DISABLED = 3;
  public static final int MFS_GRAYED = 3;
  public static final int MFT_RADIOCHECK = 512;
  public static final int MFT_RIGHTJUSTIFY = 16384;
  public static final int MFT_RIGHTORDER = 8192;
  public static final int MFT_SEPARATOR = 2048;
  public static final int MFT_STRING = 0;
  public static final int MF_BYCOMMAND = 0;
  public static final int MF_BYPOSITION = 1024;
  public static final int MF_CHECKED = 8;
  public static final int MF_DISABLED = 2;
  public static final int MF_ENABLED = 0;
  public static final int MF_GRAYED = 1;
  public static final int MF_HILITE = 128;
  public static final int MF_POPUP = 16;
  public static final int MF_SEPARATOR = 2048;
  public static final int MF_SYSMENU = 8192;
  public static final int MF_UNCHECKED = 0;
  public static final int MIIM_BITMAP = 128;
  public static final int MIIM_DATA = 32;
  public static final int MIIM_ID = 2;
  public static final int MIIM_STATE = 1;
  public static final int MIIM_STRING = 64;
  public static final int MIIM_SUBMENU = 4;
  public static final int MIIM_TYPE = 16;
  public static final int MIM_BACKGROUND = 2;
  public static final int MIM_STYLE = 16;
  public static final int MK_ALT = 32;
  public static final int MK_CONTROL = 8;
  public static final int MK_LBUTTON = 1;
  public static final int MK_MBUTTON = 16;
  public static final int MK_RBUTTON = 2;
  public static final int MK_SHIFT = 4;
  public static final int MK_XBUTTON1 = 32;
  public static final int MK_XBUTTON2 = 64;
  public static final int MM_TEXT = 1;
  public static final int MNC_CLOSE = 1;
  public static final int MNS_CHECKORBMP = 67108864;
  public static final int MONITOR_DEFAULTTONEAREST = 2;
  public static final int MONITORINFOF_PRIMARY = 1;
  public static final String MONTHCAL_CLASS = "SysMonthCal32";
  public static final int MOUSEEVENTF_ABSOLUTE = 32768;
  public static final int MOUSEEVENTF_LEFTDOWN = 2;
  public static final int MOUSEEVENTF_LEFTUP = 4;
  public static final int MOUSEEVENTF_MIDDLEDOWN = 32;
  public static final int MOUSEEVENTF_MIDDLEUP = 64;
  public static final int MOUSEEVENTF_MOVE = 1;
  public static final int MOUSEEVENTF_RIGHTDOWN = 8;
  public static final int MOUSEEVENTF_RIGHTUP = 16;
  public static final int MOUSEEVENTF_VIRTUALDESK = 16384;
  public static final int MOUSEEVENTF_WHEEL = 2048;
  public static final int MOUSEEVENTF_XDOWN = 128;
  public static final int MOUSEEVENTF_XUP = 256;
  public static final int MSGF_DIALOGBOX = 0;
  public static final int MSGF_COMMCTRL_BEGINDRAG = 16896;
  public static final int MSGF_COMMCTRL_SIZEHEADER = 16897;
  public static final int MSGF_COMMCTRL_DRAGSELECT = 16898;
  public static final int MSGF_COMMCTRL_TOOLBARCUST = 16899;
  public static final int MSGF_MAINLOOP = 8;
  public static final int MSGF_MENU = 2;
  public static final int MSGF_MOVE = 3;
  public static final int MSGF_MESSAGEBOX = 1;
  public static final int MSGF_NEXTWINDOW = 6;
  public static final int MSGF_SCROLLBAR = 5;
  public static final int MSGF_SIZE = 4;
  public static final int MSGF_USER = 4096;
  public static final int MWMO_INPUTAVAILABLE = 4;
  public static final int MWT_LEFTMULTIPLY = 2;
  public static final int NI_COMPOSITIONSTR = 21;
  public static final int NIF_ICON = 2;
  public static final int NIF_INFO = 16;
  public static final int NIF_MESSAGE = 1;
  public static final int NIF_STATE = 8;
  public static final int NIF_TIP = 4;
  public static final int NIIF_ERROR = 3;
  public static final int NIIF_INFO = 1;
  public static final int NIIF_NONE = 0;
  public static final int NIIF_WARNING = 2;
  public static final int NIM_ADD = 0;
  public static final int NIM_DELETE = 2;
  public static final int NIM_MODIFY = 1;
  public static final int NIN_SELECT = 1024;
  public static final int NINF_KEY = 1;
  public static final int NIN_KEYSELECT = 1025;
  public static final int NIN_BALLOONSHOW = 1026;
  public static final int NIN_BALLOONHIDE = 1027;
  public static final int NIN_BALLOONTIMEOUT = 1028;
  public static final int NIN_BALLOONUSERCLICK = 1029;
  public static final int NIS_HIDDEN = 1;
  public static final int NM_FIRST = 0;
  public static final int NM_CLICK = -2;
  public static final int NM_CUSTOMDRAW = -12;
  public static final int NM_DBLCLK = -3;
  public static final int NM_RECOGNIZEGESTURE = -16;
  public static final int NM_RELEASEDCAPTURE = -16;
  public static final int NM_RETURN = -4;
  public static final int NOTIFYICONDATAA_V2_SIZE = NOTIFYICONDATAA_V2_SIZE();
  public static final int NOTIFYICONDATAW_V2_SIZE = NOTIFYICONDATAW_V2_SIZE();
  public static final int NOTIFYICONDATA_V2_SIZE = IsUnicode ? NOTIFYICONDATAW_V2_SIZE : NOTIFYICONDATAA_V2_SIZE;
  public static final int NOTSRCCOPY = 3342344;
  public static final int NULLREGION = 1;
  public static final int NULL_BRUSH = 5;
  public static final int NULL_PEN = 8;
  public static final int NUMRESERVED = 106;
  public static final int OBJID_WINDOW = 0;
  public static final int OBJID_SYSMENU = -1;
  public static final int OBJID_TITLEBAR = -2;
  public static final int OBJID_MENU = -3;
  public static final int OBJID_CLIENT = -4;
  public static final int OBJID_VSCROLL = -5;
  public static final int OBJID_HSCROLL = -6;
  public static final int OBJID_SIZEGRIP = -7;
  public static final int OBJID_CARET = -8;
  public static final int OBJID_CURSOR = -9;
  public static final int OBJID_ALERT = -10;
  public static final int OBJID_SOUND = -11;
  public static final int OBJID_QUERYCLASSNAMEIDX = -12;
  public static final int OBJID_NATIVEOM = -16;
  public static final int OBJ_BITMAP = 7;
  public static final int OBJ_FONT = 6;
  public static final int OBJ_PEN = 1;
  public static final int OBM_CHECKBOXES = 32759;
  public static final int ODS_SELECTED = 1;
  public static final int ODT_MENU = 1;
  public static final int OFN_ALLOWMULTISELECT = 512;
  public static final int OFN_EXPLORER = 524288;
  public static final int OFN_ENABLEHOOK = 32;
  public static final int OFN_ENABLESIZING = 8388608;
  public static final int OFN_HIDEREADONLY = 4;
  public static final int OFN_NOCHANGEDIR = 8;
  public static final int OFN_OVERWRITEPROMPT = 2;
  public static final int OIC_BANG = 32515;
  public static final int OIC_HAND = 32513;
  public static final int OIC_INFORMATION = 32516;
  public static final int OIC_QUES = 32514;
  public static final int OIC_WINLOGO = 32517;
  public static final int OPAQUE = 2;
  public static final int PATCOPY = 15728673;
  public static final int PATINVERT = 5898313;
  public static final int PBM_GETPOS = 1032;
  public static final int PBM_GETRANGE = 1031;
  public static final int PBM_GETSTATE = 1041;
  public static final int PBM_SETBARCOLOR = 1033;
  public static final int PBM_SETBKCOLOR = 8193;
  public static final int PBM_SETMARQUEE = 1034;
  public static final int PBM_SETPOS = 1026;
  public static final int PBM_SETRANGE32 = 1030;
  public static final int PBM_SETSTATE = 1040;
  public static final int PBM_STEPIT = 1029;
  public static final int PBS_MARQUEE = 8;
  public static final int PBS_SMOOTH = 1;
  public static final int PBS_VERTICAL = 4;
  public static final int PBS_NORMAL = 1;
  public static final int PBS_HOT = 2;
  public static final int PBS_PRESSED = 3;
  public static final int PBS_DISABLED = 4;
  public static final int PBS_DEFAULTED = 5;
  public static final int PBST_NORMAL = 1;
  public static final int PBST_ERROR = 2;
  public static final int PBST_PAUSED = 3;
  public static final int PD_ALLPAGES = 0;
  public static final int PD_COLLATE = 16;
  public static final int PD_PAGENUMS = 2;
  public static final int PD_PRINTTOFILE = 32;
  public static final int PD_RETURNDC = 256;
  public static final int PD_RETURNDEFAULT = 1024;
  public static final int PD_SELECTION = 1;
  public static final int PD_USEDEVMODECOPIESANDCOLLATE = 262144;
  public static final int PT_CLOSEFIGURE = 1;
  public static final int PT_LINETO = 2;
  public static final int PT_BEZIERTO = 4;
  public static final int PT_MOVETO = 6;
  public static final int PFM_TABSTOPS = 16;
  public static final int PHYSICALHEIGHT = 111;
  public static final int PHYSICALOFFSETX = 112;
  public static final int PHYSICALOFFSETY = 113;
  public static final int PHYSICALWIDTH = 110;
  public static final int PLANES = 14;
  public static final int PM_NOREMOVE = 0;
  public static final int PM_NOYIELD = 2;
  public static final int QS_HOTKEY = 128;
  public static final int QS_KEY = 1;
  public static final int QS_MOUSEMOVE = 2;
  public static final int QS_MOUSEBUTTON = 4;
  public static final int QS_MOUSE = 6;
  public static final int QS_INPUT = 7;
  public static final int QS_POSTMESSAGE = 8;
  public static final int QS_TIMER = 16;
  public static final int QS_PAINT = 32;
  public static final int QS_SENDMESSAGE = 64;
  public static final int QS_ALLINPUT = 127;
  public static final int PM_QS_INPUT = 458752;
  public static final int PM_QS_POSTMESSAGE = 9961472;
  public static final int PM_QS_PAINT = 2097152;
  public static final int PM_QS_SENDMESSAGE = 4194304;
  public static final int PM_REMOVE = 1;
  public static final String PROGRESS_CLASS = "msctls_progress32";
  public static final int PP_BAR = 1;
  public static final int PP_BARVERT = 2;
  public static final int PP_CHUNK = 3;
  public static final int PP_CHUNKVERT = 4;
  public static final int PRF_CHILDREN = 16;
  public static final int PRF_CLIENT = 4;
  public static final int PRF_ERASEBKGND = 8;
  public static final int PRF_NONCLIENT = 2;
  public static final int PROGRESSCHUNKSIZE = 2411;
  public static final int PROGRESSSPACESIZE = 2412;
  public static final int PS_DASH = 1;
  public static final int PS_DASHDOT = 3;
  public static final int PS_DASHDOTDOT = 4;
  public static final int PS_DOT = 2;
  public static final int PS_ENDCAP_FLAT = 512;
  public static final int PS_ENDCAP_SQUARE = 256;
  public static final int PS_ENDCAP_ROUND = 0;
  public static final int PS_ENDCAP_MASK = 3840;
  public static final int PS_GEOMETRIC = 65536;
  public static final int PS_JOIN_BEVEL = 4096;
  public static final int PS_JOIN_MASK = 61440;
  public static final int PS_JOIN_MITER = 8192;
  public static final int PS_JOIN_ROUND = 0;
  public static final int PS_SOLID = 0;
  public static final int PS_STYLE_MASK = 15;
  public static final int PS_TYPE_MASK = 983040;
  public static final int PS_USERSTYLE = 7;
  public static final int R2_COPYPEN = 13;
  public static final int R2_XORPEN = 7;
  public static final int RASTERCAPS = 38;
  public static final int RASTER_FONTTYPE = 1;
  public static final int RBBIM_CHILD = 16;
  public static final int RBBIM_CHILDSIZE = 32;
  public static final int RBBIM_COLORS = 2;
  public static final int RBBIM_HEADERSIZE = 2048;
  public static final int RBBIM_ID = 256;
  public static final int RBBIM_IDEALSIZE = 512;
  public static final int RBBIM_SIZE = 64;
  public static final int RBBIM_STYLE = 1;
  public static final int RBBIM_TEXT = 4;
  public static final int RBBS_BREAK = 1;
  public static final int RBBS_GRIPPERALWAYS = 128;
  public static final int RBBS_NOGRIPPER = 256;
  public static final int RBBS_USECHEVRON = 512;
  public static final int RBBS_VARIABLEHEIGHT = 64;
  public static final int RBN_FIRST = -831;
  public static final int RBN_BEGINDRAG = -835;
  public static final int RBN_CHILDSIZE = -839;
  public static final int RBN_CHEVRONPUSHED = -841;
  public static final int RBN_HEIGHTCHANGE = -831;
  public static final int RBS_DBLCLKTOGGLE = 32768;
  public static final int RBS_BANDBORDERS = 1024;
  public static final int RBS_VARHEIGHT = 512;
  public static final int RB_DELETEBAND = 1026;
  public static final int RB_GETBANDBORDERS = 1058;
  public static final int RB_GETBANDCOUNT = 1036;
  public static final int RB_GETBANDINFO = IsUnicode ? 1052 : 1053;
  public static final int RB_GETBANDMARGINS = 1064;
  public static final int RB_GETBARHEIGHT = 1051;
  public static final int RB_GETBKCOLOR = 1044;
  public static final int RB_GETRECT = 1033;
  public static final int RB_GETTEXTCOLOR = 1046;
  public static final int RB_IDTOINDEX = 1040;
  public static final int RB_INSERTBAND = IsUnicode ? 1034 : 1025;
  public static final int RB_MOVEBAND = 1063;
  public static final int RB_SETBANDINFO = IsUnicode ? 1035 : 1030;
  public static final int RB_SETBKCOLOR = 1043;
  public static final int RB_SETTEXTCOLOR = 1045;
  public static final int RC_BITBLT = 1;
  public static final int RC_PALETTE = 256;
  public static final int RDW_ALLCHILDREN = 128;
  public static final int RDW_ERASE = 4;
  public static final int RDW_FRAME = 1024;
  public static final int RDW_INVALIDATE = 1;
  public static final int RDW_UPDATENOW = 256;
  public static final int READ_CONTROL = 131072;
  public static final String REBARCLASSNAME = "ReBarWindow32";
  public static final int RGN_AND = 1;
  public static final int RGN_COPY = 5;
  public static final int RGN_DIFF = 4;
  public static final int RGN_ERROR = 0;
  public static final int RGN_OR = 2;
  public static final int RP_BAND = 3;
  public static final int SBP_ARROWBTN = 1;
  public static final int SBP_THUMBBTNHORZ = 2;
  public static final int SBP_THUMBBTNVERT = 3;
  public static final int SBP_LOWERTRACKHORZ = 4;
  public static final int SBP_UPPERTRACKHORZ = 5;
  public static final int SBP_LOWERTRACKVERT = 6;
  public static final int SBP_UPPERTRACKVERT = 7;
  public static final int SBP_GRIPPERHORZ = 8;
  public static final int SBP_GRIPPERVERT = 9;
  public static final int SBP_SIZEBOX = 10;
  public static final int SBS_HORZ = 0;
  public static final int SBS_VERT = 1;
  public static final int SB_BOTH = 3;
  public static final int SB_BOTTOM = 7;
  public static final int SB_NONE = 0;
  public static final int SB_CONST_ALPHA = 1;
  public static final int SB_PIXEL_ALPHA = 2;
  public static final int SB_PREMULT_ALPHA = 4;
  public static final int SB_CTL = 2;
  public static final int SB_ENDSCROLL = 8;
  public static final int SB_HORZ = 0;
  public static final int SB_LINEDOWN = 1;
  public static final int SB_LINEUP = 0;
  public static final int SB_PAGEDOWN = 3;
  public static final int SB_PAGEUP = 2;
  public static final int SB_THUMBPOSITION = 4;
  public static final int SB_THUMBTRACK = 5;
  public static final int SB_TOP = 6;
  public static final int SB_VERT = 1;
  public static final int SCF_ALL = 4;
  public static final int SCF_DEFAULT = 0;
  public static final int SCF_SELECTION = 1;
  public static final int SC_CLOSE = 61536;
  public static final int SC_HSCROLL = 61568;
  public static final int SC_KEYMENU = 61696;
  public static final int SC_MAXIMIZE = 61488;
  public static final int SC_MINIMIZE = 61472;
  public static final int SC_NEXTWINDOW = 61504;
  public static final int SC_RESTORE = 61728;
  public static final int SC_SIZE = 61440;
  public static final int SC_TASKLIST = 61744;
  public static final int SC_VSCROLL = 61552;
  public static final int SCRBS_NORMAL = 1;
  public static final int SCRBS_HOT = 2;
  public static final int SCRBS_PRESSED = 3;
  public static final int SCRBS_DISABLED = 4;
  public static final int SEM_FAILCRITICALERRORS = 1;
  public static final int SET_FEATURE_ON_PROCESS = 2;
  public static final int SF_RTF = 2;
  public static final int SHADEBLENDCAPS = 120;
  public static final int SHCMBF_HIDDEN = 2;
  public static final int SHCMBM_OVERRIDEKEY = 1427;
  public static final int SHCMBM_SETSUBMENU = 1424;
  public static final int SHGFP_TYPE_CURRENT = 0;
  public static final int SHCMBM_GETSUBMENU = 1425;
  public static final int SHGFI_ICON = 256;
  public static final int SHGFI_SMALLICON = 1;
  public static final int SHGFI_USEFILEATTRIBUTES = 16;
  public static final int SHMBOF_NODEFAULT = 1;
  public static final int SHMBOF_NOTIFY = 2;
  public static final int SHRG_RETURNCMD = 1;
  public static final int SIGDN_FILESYSPATH = -2147123200;
  public static final int SIF_ALL = 23;
  public static final int SIF_DISABLENOSCROLL = 8;
  public static final int SIF_PAGE = 2;
  public static final int SIF_POS = 4;
  public static final int SIF_RANGE = 1;
  public static final int SIF_TRACKPOS = 16;
  public static final int SIP_DOWN = 1;
  public static final int SIP_UP = 0;
  public static final int SIPF_ON = 1;
  public static final int SIZE_RESTORED = 0;
  public static final int SIZE_MINIMIZED = 1;
  public static final int SIZE_MAXIMIZED = 2;
  public static final int SIZEPALETTE = 104;
  public static final int SM_CMONITORS = 80;
  public static final int SM_CXBORDER = 5;
  public static final int SM_CXCURSOR = 13;
  public static final int SM_CXDOUBLECLK = 36;
  public static final int SM_CYDOUBLECLK = 37;
  public static final int SM_CXEDGE = 45;
  public static final int SM_CXFOCUSBORDER = 83;
  public static final int SM_CXHSCROLL = 21;
  public static final int SM_CXICON = 11;
  public static final int SM_CYICON = 12;
  public static final int SM_CXVIRTUALSCREEN = 78;
  public static final int SM_CYVIRTUALSCREEN = 79;
  public static final int SM_CXSMICON = 49;
  public static final int SM_CYSMICON = 50;
  public static final int SM_CXSCREEN = 0;
  public static final int SM_XVIRTUALSCREEN = 76;
  public static final int SM_YVIRTUALSCREEN = 77;
  public static final int SM_CXVSCROLL = 2;
  public static final int SM_CYBORDER = 6;
  public static final int SM_CYCURSOR = 14;
  public static final int SM_CYFOCUSBORDER = 84;
  public static final int SM_CYHSCROLL = 3;
  public static final int SM_CYMENU = 15;
  public static final int SM_CXMINTRACK = 34;
  public static final int SM_CYMINTRACK = 35;
  public static final int SM_CMOUSEBUTTONS = 43;
  public static final int SM_CYSCREEN = 1;
  public static final int SM_CYVSCROLL = 20;
  public static final int SPI_GETFONTSMOOTHINGTYPE = 8202;
  public static final int SPI_GETHIGHCONTRAST = 66;
  public static final int SPI_GETWORKAREA = 48;
  public static final int SPI_GETMOUSEVANISH = 4128;
  public static final int SPI_GETNONCLIENTMETRICS = 41;
  public static final int SPI_GETWHEELSCROLLLINES = 104;
  public static final int SPI_GETCARETWIDTH = 8198;
  public static final int SPI_SETSIPINFO = 224;
  public static final int SPI_SETHIGHCONTRAST = 67;
  public static final int SRCAND = 8913094;
  public static final int SRCCOPY = 13369376;
  public static final int SRCINVERT = 6684742;
  public static final int SRCPAINT = 15597702;
  public static final int SS_BITMAP = 14;
  public static final int SS_CENTER = 1;
  public static final int SS_CENTERIMAGE = 512;
  public static final int SS_EDITCONTROL = 8192;
  public static final int SS_ICON = 3;
  public static final int SS_LEFT = 0;
  public static final int SS_LEFTNOWORDWRAP = 12;
  public static final int SS_NOTIFY = 256;
  public static final int SS_OWNERDRAW = 13;
  public static final int SS_REALSIZEIMAGE = 2048;
  public static final int SS_RIGHT = 2;
  public static final int SSA_FALLBACK = 32;
  public static final int SSA_GLYPHS = 128;
  public static final int SSA_METAFILE = 2048;
  public static final int SSA_LINK = 4096;
  public static final int STANDARD_RIGHTS_READ = 131072;
  public static final int STARTF_USESHOWWINDOW = 1;
  public static final int STATE_SYSTEM_INVISIBLE = 32768;
  public static final int STATE_SYSTEM_OFFSCREEN = 65536;
  public static final int STATE_SYSTEM_UNAVAILABLE = 1;
  public static final int STD_COPY = 1;
  public static final int STD_CUT = 0;
  public static final int STD_FILENEW = 6;
  public static final int STD_FILEOPEN = 7;
  public static final int STD_FILESAVE = 8;
  public static final int STD_PASTE = 2;
  public static final int STM_GETIMAGE = 371;
  public static final int STM_SETIMAGE = 370;
  public static final int SWP_ASYNCWINDOWPOS = 16384;
  public static final int SWP_DRAWFRAME = 32;
  public static final int SWP_NOACTIVATE = 16;
  public static final int SWP_NOCOPYBITS = 256;
  public static final int SWP_NOMOVE = 2;
  public static final int SWP_NOREDRAW = 8;
  public static final int SWP_NOSIZE = 1;
  public static final int SWP_NOZORDER = 4;
  public static final int SW_ERASE = 4;
  public static final int SW_HIDE = 0;
  public static final int SW_INVALIDATE = 2;
  public static final int SW_MINIMIZE = 6;
  public static final int SW_PARENTOPENING = 3;
  public static final int SW_RESTORE = IsWinCE ? 13 : 9;
  public static final int SW_SCROLLCHILDREN = 1;
  public static final int SW_SHOW = 5;
  public static final int SW_SHOWMAXIMIZED = IsWinCE ? 11 : 3;
  public static final int SW_SHOWMINIMIZED = 2;
  public static final int SW_SHOWMINNOACTIVE = 7;
  public static final int SW_SHOWNA = 8;
  public static final int SW_SHOWNOACTIVATE = 4;
  public static final int SYNCHRONIZE = 1048576;
  public static final int SYSRGN = 4;
  public static final int SYSTEM_FONT = 13;
  public static final int S_OK = 0;
  public static final int TABP_TABITEM = 1;
  public static final int TABP_TABITEMLEFTEDGE = 2;
  public static final int TABP_TABITEMRIGHTEDGE = 3;
  public static final int TABP_TABITEMBOTHEDGE = 4;
  public static final int TABP_TOPTABITEM = 5;
  public static final int TABP_TOPTABITEMLEFTEDGE = 6;
  public static final int TABP_TOPTABITEMRIGHTEDGE = 7;
  public static final int TABP_TOPTABITEMBOTHEDGE = 8;
  public static final int TABP_PANE = 9;
  public static final int TABP_BODY = 10;
  public static final int TBIF_COMMAND = 32;
  public static final int TBIF_STATE = 4;
  public static final int TBIF_IMAGE = 1;
  public static final int TBIF_LPARAM = 16;
  public static final int TBIF_SIZE = 64;
  public static final int TBIF_STYLE = 8;
  public static final int TBIF_TEXT = 2;
  public static final int TB_GETEXTENDEDSTYLE = 1109;
  public static final int TBM_GETLINESIZE = 1048;
  public static final int TBM_GETPAGESIZE = 1046;
  public static final int TBM_GETPOS = 1024;
  public static final int TBM_GETRANGEMAX = 1026;
  public static final int TBM_GETRANGEMIN = 1025;
  public static final int TBM_GETTHUMBRECT = 1049;
  public static final int TBM_SETLINESIZE = 1047;
  public static final int TBM_SETPAGESIZE = 1045;
  public static final int TBM_SETPOS = 1029;
  public static final int TBM_SETRANGEMAX = 1032;
  public static final int TBM_SETRANGEMIN = 1031;
  public static final int TBM_SETTICFREQ = 1044;
  public static final int TBN_DROPDOWN = -710;
  public static final int TBN_FIRST = -700;
  public static final int TBN_HOTITEMCHANGE = -713;
  public static final int TBSTATE_CHECKED = 1;
  public static final int TBSTATE_PRESSED = 2;
  public static final int TBSTYLE_CUSTOMERASE = 8192;
  public static final int TBSTYLE_DROPDOWN = 8;
  public static final int TBSTATE_ENABLED = 4;
  public static final int TBSTYLE_AUTOSIZE = 16;
  public static final int TBSTYLE_EX_DOUBLEBUFFER = 128;
  public static final int TBSTYLE_EX_DRAWDDARROWS = 1;
  public static final int TBSTYLE_EX_HIDECLIPPEDBUTTONS = 16;
  public static final int TBSTYLE_EX_MIXEDBUTTONS = 8;
  public static final int TBSTYLE_FLAT = 2048;
  public static final int TBSTYLE_LIST = 4096;
  public static final int TBSTYLE_TOOLTIPS = 256;
  public static final int TBSTYLE_TRANSPARENT = 32768;
  public static final int TBSTYLE_WRAPABLE = 512;
  public static final int TBS_AUTOTICKS = 1;
  public static final int TBS_BOTH = 8;
  public static final int TBS_DOWNISLEFT = 1024;
  public static final int TBS_HORZ = 0;
  public static final int TBS_VERT = 2;
  public static final int TB_ADDSTRING = IsUnicode ? 1101 : 1052;
  public static final int TB_AUTOSIZE = 1057;
  public static final int TB_BUTTONCOUNT = 1048;
  public static final int TB_BUTTONSTRUCTSIZE = 1054;
  public static final int TB_COMMANDTOINDEX = 1049;
  public static final int TB_DELETEBUTTON = 1046;
  public static final int TB_ENDTRACK = 8;
  public static final int TB_GETBUTTON = 1047;
  public static final int TB_GETBUTTONINFO = IsUnicode ? 1087 : 1089;
  public static final int TB_GETBUTTONSIZE = 1082;
  public static final int TB_GETBUTTONTEXT = IsUnicode ? 1099 : 1069;
  public static final int TB_GETDISABLEDIMAGELIST = 1079;
  public static final int TB_GETHOTIMAGELIST = 1077;
  public static final int TB_GETHOTITEM = 1095;
  public static final int TB_GETIMAGELIST = 1073;
  public static final int TB_GETITEMRECT = 1053;
  public static final int TB_GETPADDING = 1110;
  public static final int TB_GETROWS = 1064;
  public static final int TB_GETSTATE = 1042;
  public static final int TB_GETTOOLTIPS = 1059;
  public static final int TB_INSERTBUTTON = IsUnicode ? 1091 : 1045;
  public static final int TB_LOADIMAGES = 1074;
  public static final int TB_MAPACCELERATOR = 1024 + (IsUnicode ? 90 : 78);
  public static final int TB_SETBITMAPSIZE = 1056;
  public static final int TB_SETBUTTONINFO = IsUnicode ? 1088 : 1090;
  public static final int TB_SETBUTTONSIZE = 1055;
  public static final int TB_SETDISABLEDIMAGELIST = 1078;
  public static final int TB_SETEXTENDEDSTYLE = 1108;
  public static final int TB_SETHOTIMAGELIST = 1076;
  public static final int TB_SETHOTITEM = 1096;
  public static final int TB_SETIMAGELIST = 1072;
  public static final int TB_SETPARENT = 1061;
  public static final int TB_SETROWS = 1063;
  public static final int TB_SETSTATE = 1041;
  public static final int TB_THUMBPOSITION = 4;
  public static final int TBPF_NOPROGRESS = 0;
  public static final int TBPF_INDETERMINATE = 1;
  public static final int TBPF_NORMAL = 2;
  public static final int TBPF_ERROR = 4;
  public static final int TBPF_PAUSED = 8;
  public static final int TCIF_IMAGE = 2;
  public static final int TCIF_TEXT = 1;
  public static final int TCI_SRCCHARSET = 1;
  public static final int TCI_SRCCODEPAGE = 2;
  public static final int TCM_ADJUSTRECT = 4904;
  public static final int TCM_DELETEITEM = 4872;
  public static final int TCM_GETCURSEL = 4875;
  public static final int TCM_GETITEMCOUNT = 4868;
  public static final int TCM_GETITEMRECT = 4874;
  public static final int TCM_GETTOOLTIPS = 4909;
  public static final int TCM_HITTEST = 4877;
  public static final int TCM_INSERTITEM = IsUnicode ? 4926 : 4871;
  public static final int TCM_SETCURSEL = 4876;
  public static final int TCM_SETIMAGELIST = 4867;
  public static final int TCM_SETITEM = IsUnicode ? 4925 : 4870;
  public static final int TCN_SELCHANGE = -551;
  public static final int TCN_SELCHANGING = -552;
  public static final int TCS_BOTTOM = 2;
  public static final int TCS_FOCUSNEVER = 32768;
  public static final int TCS_MULTILINE = 512;
  public static final int TCS_TABS = 0;
  public static final int TCS_TOOLTIPS = 16384;
  public static final int TECHNOLOGY = 2;
  public static final int TF_ATTR_INPUT = 0;
  public static final int TF_ATTR_TARGET_CONVERTED = 1;
  public static final int TF_ATTR_CONVERTED = 2;
  public static final int TF_ATTR_TARGET_NOTCONVERTED = 3;
  public static final int TF_ATTR_INPUT_ERROR = 4;
  public static final int TF_ATTR_FIXEDCONVERTED = 5;
  public static final int TF_ATTR_OTHER = -1;
  public static final int TF_CT_NONE = 0;
  public static final int TF_CT_SYSCOLOR = 1;
  public static final int TF_CT_COLORREF = 2;
  public static final int TF_LS_NONE = 0;
  public static final int TF_LS_SOLID = 1;
  public static final int TF_LS_DOT = 2;
  public static final int TF_LS_DASH = 3;
  public static final int TF_LS_SQUIGGLE = 4;
  public static final int TIME_NOSECONDS = 2;
  public static final int TIS_NORMAL = 1;
  public static final int TIS_HOT = 2;
  public static final int TIS_SELECTED = 3;
  public static final int TIS_DISABLED = 4;
  public static final int TIS_FOCUSED = 5;
  public static final int TKP_TRACK = 1;
  public static final int TKP_TRACKVERT = 2;
  public static final int TKP_THUMB = 3;
  public static final int TKP_THUMBBOTTOM = 4;
  public static final int TKP_THUMBTOP = 5;
  public static final int TKP_THUMBVERT = 6;
  public static final int TKP_THUMBLEFT = 7;
  public static final int TKP_THUMBRIGHT = 8;
  public static final int TKP_TICS = 9;
  public static final int TKP_TICSVERT = 10;
  public static final int TME_HOVER = 1;
  public static final int TME_LEAVE = 2;
  public static final int TME_QUERY = 1073741824;
  public static final int TMPF_VECTOR = 2;
  public static final int TMT_CONTENTMARGINS = 3602;
  public static final String TOOLBARCLASSNAME = "ToolbarWindow32";
  public static final String TOOLTIPS_CLASS = "tooltips_class32";
  public static final int TP_BUTTON = 1;
  public static final int TP_DROPDOWNBUTTON = 2;
  public static final int TP_SPLITBUTTON = 3;
  public static final int TP_SPLITBUTTONDROPDOWN = 4;
  public static final int TP_SEPARATOR = 5;
  public static final int TP_SEPARATORVERT = 6;
  public static final int TPM_LEFTALIGN = 0;
  public static final int TPM_LEFTBUTTON = 0;
  public static final int TPM_RIGHTBUTTON = 2;
  public static final int TPM_RIGHTALIGN = 8;
  public static final String TRACKBAR_CLASS = "msctls_trackbar32";
  public static final int TRANSPARENT = 1;
  public static final int TREIS_DISABLED = 4;
  public static final int TREIS_HOT = 2;
  public static final int TREIS_NORMAL = 1;
  public static final int TREIS_SELECTED = 3;
  public static final int TREIS_SELECTEDNOTFOCUS = 5;
  public static final int TS_MIN = 0;
  public static final int TS_TRUE = 1;
  public static final int TS_DRAW = 2;
  public static final int TS_NORMAL = 1;
  public static final int TS_HOT = 2;
  public static final int TS_PRESSED = 3;
  public static final int TS_DISABLED = 4;
  public static final int TS_CHECKED = 5;
  public static final int TS_HOTCHECKED = 6;
  public static final int TTDT_AUTOMATIC = 0;
  public static final int TTDT_RESHOW = 1;
  public static final int TTDT_AUTOPOP = 2;
  public static final int TTDT_INITIAL = 3;
  public static final int TTF_ABSOLUTE = 128;
  public static final int TTF_IDISHWND = 1;
  public static final int TTF_SUBCLASS = 16;
  public static final int TTF_RTLREADING = 4;
  public static final int TTF_TRACK = 32;
  public static final int TTF_TRANSPARENT = 256;
  public static final int TTI_NONE = 0;
  public static final int TTI_INFO = 1;
  public static final int TTI_WARNING = 2;
  public static final int TTI_ERROR = 3;
  public static final int TTM_ACTIVATE = 1025;
  public static final int TTM_ADDTOOL = IsUnicode ? 1074 : 1028;
  public static final int TTM_ADJUSTRECT = 1055;
  public static final int TTM_GETCURRENTTOOLA = 1039;
  public static final int TTM_GETCURRENTTOOLW = 1083;
  public static final int TTM_GETCURRENTTOOL = 1024 + (IsUnicode ? 59 : 15);
  public static final int TTM_GETDELAYTIME = 1045;
  public static final int TTM_DELTOOL = IsUnicode ? 1075 : 1029;
  public static final int TTM_GETTOOLINFO = 1024 + (IsUnicode ? 53 : 8);
  public static final int TTM_NEWTOOLRECT = 1024 + (IsUnicode ? 52 : 6);
  public static final int TTM_POP = 1052;
  public static final int TTM_SETDELAYTIME = 1027;
  public static final int TTM_SETMAXTIPWIDTH = 1048;
  public static final int TTM_SETTITLEA = 1056;
  public static final int TTM_SETTITLEW = 1057;
  public static final int TTM_SETTITLE = 1024 + (IsUnicode ? 33 : 32);
  public static final int TTM_TRACKPOSITION = 1042;
  public static final int TTM_TRACKACTIVATE = 1041;
  public static final int TTM_UPDATE = 1053;
  public static final int TTN_FIRST = -520;
  public static final int TTN_GETDISPINFO = IsUnicode ? -530 : -520;
  public static final int TTN_GETDISPINFOW = -530;
  public static final int TTN_GETDISPINFOA = -520;
  public static final int TTN_POP = -522;
  public static final int TTN_SHOW = -521;
  public static final int TTS_ALWAYSTIP = 1;
  public static final int TTS_BALLOON = 64;
  public static final int TTS_NOANIMATE = 16;
  public static final int TTS_NOFADE = 32;
  public static final int TTS_NOPREFIX = 2;
  public static final int TV_FIRST = 4352;
  public static final int TVE_COLLAPSE = 1;
  public static final int TVE_COLLAPSERESET = 32768;
  public static final int TVE_EXPAND = 2;
  public static final int TVGN_CARET = 9;
  public static final int TVGN_CHILD = 4;
  public static final int TVGN_DROPHILITED = 8;
  public static final int TVGN_FIRSTVISIBLE = 5;
  public static final int TVGN_LASTVISIBLE = 10;
  public static final int TVGN_NEXT = 1;
  public static final int TVGN_NEXTVISIBLE = 6;
  public static final int TVGN_PARENT = 3;
  public static final int TVGN_PREVIOUS = 2;
  public static final int TVGN_PREVIOUSVISIBLE = 7;
  public static final int TVGN_ROOT = 0;
  public static final int TVHT_ONITEM = 70;
  public static final int TVHT_ONITEMBUTTON = 16;
  public static final int TVHT_ONITEMICON = 2;
  public static final int TVHT_ONITEMINDENT = 8;
  public static final int TVHT_ONITEMRIGHT = 32;
  public static final int TVHT_ONITEMLABEL = 4;
  public static final int TVHT_ONITEMSTATEICON = 64;
  public static final int TVIF_HANDLE = 16;
  public static final int TVIF_IMAGE = 2;
  public static final int TVIF_INTEGRAL = 128;
  public static final int TVIF_PARAM = 4;
  public static final int TVIF_SELECTEDIMAGE = 32;
  public static final int TVIF_STATE = 8;
  public static final int TVIF_TEXT = 1;
  public static final int TVIS_DROPHILITED = 8;
  public static final int TVIS_EXPANDED = 32;
  public static final int TVIS_SELECTED = 2;
  public static final int TVIS_STATEIMAGEMASK = 61440;
  public static final int TVI_FIRST = -65535;
  public static final int TVI_LAST = -65534;
  public static final int TVI_ROOT = -65536;
  public static final int TVI_SORT = -65533;
  public static final int TVM_CREATEDRAGIMAGE = 4370;
  public static final int TVM_DELETEITEM = 4353;
  public static final int TVM_ENSUREVISIBLE = 4372;
  public static final int TVM_EXPAND = 4354;
  public static final int TVM_GETBKCOLOR = 4383;
  public static final int TVM_GETCOUNT = 4357;
  public static final int TVM_GETEXTENDEDSTYLE = 4397;
  public static final int TVM_GETIMAGELIST = 4360;
  public static final int TVM_GETITEM = IsUnicode ? 4414 : 4364;
  public static final int TVM_GETITEMHEIGHT = 4380;
  public static final int TVM_GETITEMRECT = 4356;
  public static final int TVM_GETITEMSTATE = 4391;
  public static final int TVM_GETNEXTITEM = 4362;
  public static final int TVM_GETTEXTCOLOR = 4384;
  public static final int TVM_GETTOOLTIPS = 4377;
  public static final int TVM_GETVISIBLECOUNT = 4368;
  public static final int TVM_HITTEST = 4369;
  public static final int TVM_INSERTITEM = IsUnicode ? 4402 : 4352;
  public static final int TVM_MAPACCIDTOHTREEITEM = 4394;
  public static final int TVM_MAPHTREEITEMTOACCID = 4395;
  public static final int TVM_SELECTITEM = 4363;
  public static final int TVM_SETBKCOLOR = 4381;
  public static final int TVM_SETEXTENDEDSTYLE = 4396;
  public static final int TVM_SETIMAGELIST = 4361;
  public static final int TVM_SETINSERTMARK = 4378;
  public static final int TVM_SETITEM = IsUnicode ? 4415 : 4365;
  public static final int TVM_SETITEMHEIGHT = 4379;
  public static final int TVM_SETSCROLLTIME = 4385;
  public static final int TVM_SETTEXTCOLOR = 4382;
  public static final int TVM_SORTCHILDREN = 4371;
  public static final int TVM_SORTCHILDRENCB = 4373;
  public static final int TVN_BEGINDRAGW = -456;
  public static final int TVN_BEGINDRAGA = -407;
  public static final int TVN_BEGINRDRAGW = -457;
  public static final int TVN_BEGINRDRAGA = -408;
  public static final int TVN_FIRST = -400;
  public static final int TVN_GETDISPINFOA = -403;
  public static final int TVN_GETDISPINFOW = -452;
  public static final int TVN_ITEMCHANGINGW = -417;
  public static final int TVN_ITEMCHANGINGA = -416;
  public static final int TVN_ITEMEXPANDEDA = -406;
  public static final int TVN_ITEMEXPANDEDW = -455;
  public static final int TVN_ITEMEXPANDINGW = -454;
  public static final int TVN_ITEMEXPANDINGA = -405;
  public static final int TVN_SELCHANGEDW = -451;
  public static final int TVN_SELCHANGEDA = -402;
  public static final int TVN_SELCHANGINGW = -450;
  public static final int TVN_SELCHANGINGA = -401;
  public static final int TVP_GLYPH = 2;
  public static final int TVP_TREEITEM = 1;
  public static final int TVSIL_NORMAL = 0;
  public static final int TVSIL_STATE = 2;
  public static final int TVS_DISABLEDRAGDROP = 16;
  public static final int TVS_EX_AUTOHSCROLL = 32;
  public static final int TVS_EX_DOUBLEBUFFER = 4;
  public static final int TVS_EX_DIMMEDCHECKBOXES = 512;
  public static final int TVS_EX_DRAWIMAGEASYNC = 1024;
  public static final int TVS_EX_EXCLUSIONCHECKBOXES = 256;
  public static final int TVS_EX_FADEINOUTEXPANDOS = 64;
  public static final int TVS_EX_MULTISELECT = 2;
  public static final int TVS_EX_NOINDENTSTATE = 8;
  public static final int TVS_EX_PARTIALCHECKBOXES = 128;
  public static final int TVS_EX_RICHTOOLTIP = 16;
  public static final int TVS_FULLROWSELECT = 4096;
  public static final int TVS_HASBUTTONS = 1;
  public static final int TVS_HASLINES = 2;
  public static final int TVS_LINESATROOT = 4;
  public static final int TVS_NOHSCROLL = 32768;
  public static final int TVS_NONEVENHEIGHT = 16384;
  public static final int TVS_NOSCROLL = 8192;
  public static final int TVS_NOTOOLTIPS = 128;
  public static final int TVS_SHOWSELALWAYS = 32;
  public static final int TVS_TRACKSELECT = 512;
  public static final int UDM_GETACCEL = 1132;
  public static final int UDM_GETRANGE32 = 1136;
  public static final int UDM_GETPOS = 1128;
  public static final int UDM_GETPOS32 = 1138;
  public static final int UDM_SETACCEL = 1131;
  public static final int UDM_SETRANGE32 = 1135;
  public static final int UDM_SETPOS = 1127;
  public static final int UDM_SETPOS32 = 1137;
  public static final int UDN_DELTAPOS = -722;
  public static final int UDS_ALIGNLEFT = 8;
  public static final int UDS_ALIGNRIGHT = 4;
  public static final int UDS_AUTOBUDDY = 16;
  public static final int UDS_WRAP = 1;
  public static final int UIS_CLEAR = 2;
  public static final int UIS_INITIALIZE = 3;
  public static final int UIS_SET = 1;
  public static final int UISF_HIDEACCEL = 2;
  public static final int UISF_HIDEFOCUS = 1;
  public static final String UPDOWN_CLASS = "msctls_updown32";
  public static final int USP_E_SCRIPT_NOT_IN_FONT = -2147220992;
  public static final int VERTRES = 10;
  public static final int VK_BACK = 8;
  public static final int VK_CANCEL = 3;
  public static final int VK_CAPITAL = 20;
  public static final int VK_CONTROL = 17;
  public static final int VK_DECIMAL = 110;
  public static final int VK_DELETE = 46;
  public static final int VK_DIVIDE = 111;
  public static final int VK_DOWN = 40;
  public static final int VK_END = 35;
  public static final int VK_ESCAPE = 27;
  public static final int VK_F1 = 112;
  public static final int VK_F10 = 121;
  public static final int VK_F11 = 122;
  public static final int VK_F12 = 123;
  public static final int VK_F13 = 124;
  public static final int VK_F14 = 125;
  public static final int VK_F15 = 126;
  public static final int VK_F16 = 127;
  public static final int VK_F17 = 128;
  public static final int VK_F18 = 129;
  public static final int VK_F19 = 130;
  public static final int VK_F20 = 131;
  public static final int VK_F2 = 113;
  public static final int VK_F3 = 114;
  public static final int VK_F4 = 115;
  public static final int VK_F5 = 116;
  public static final int VK_F6 = 117;
  public static final int VK_F7 = 118;
  public static final int VK_F8 = 119;
  public static final int VK_F9 = 120;
  public static final int VK_HOME = 36;
  public static final int VK_INSERT = 45;
  public static final int VK_L = 76;
  public static final int VK_LBUTTON = 1;
  public static final int VK_LEFT = 37;
  public static final int VK_LCONTROL = 162;
  public static final int VK_LMENU = 164;
  public static final int VK_LSHIFT = 160;
  public static final int VK_MBUTTON = 4;
  public static final int VK_MENU = 18;
  public static final int VK_MULTIPLY = 106;
  public static final int VK_N = 78;
  public static final int VK_NEXT = 34;
  public static final int VK_NUMLOCK = 144;
  public static final int VK_NUMPAD0 = 96;
  public static final int VK_NUMPAD1 = 97;
  public static final int VK_NUMPAD2 = 98;
  public static final int VK_NUMPAD3 = 99;
  public static final int VK_NUMPAD4 = 100;
  public static final int VK_NUMPAD5 = 101;
  public static final int VK_NUMPAD6 = 102;
  public static final int VK_NUMPAD7 = 103;
  public static final int VK_NUMPAD8 = 104;
  public static final int VK_NUMPAD9 = 105;
  public static final int VK_PAUSE = 19;
  public static final int VK_PRIOR = 33;
  public static final int VK_RBUTTON = 2;
  public static final int VK_RETURN = 13;
  public static final int VK_RIGHT = 39;
  public static final int VK_RCONTROL = 163;
  public static final int VK_RMENU = 165;
  public static final int VK_RSHIFT = 161;
  public static final int VK_SCROLL = 145;
  public static final int VK_SEPARATOR = 108;
  public static final int VK_SHIFT = 16;
  public static final int VK_SNAPSHOT = 44;
  public static final int VK_SPACE = 32;
  public static final int VK_SUBTRACT = 109;
  public static final int VK_TAB = 9;
  public static final int VK_UP = 38;
  public static final int VK_XBUTTON1 = 5;
  public static final int VK_XBUTTON2 = 6;
  public static final int VK_ADD = 107;
  public static final int VK_APP1 = 193;
  public static final int VK_APP2 = 194;
  public static final int VK_APP3 = 195;
  public static final int VK_APP4 = 196;
  public static final int VK_APP5 = 197;
  public static final int VK_APP6 = 198;
  public static final int VT_BOOL = 11;
  public static final int VT_LPWSTR = 31;
  public static final short VARIANT_TRUE = -1;
  public static final short VARIANT_FALSE = 0;
  public static final String WC_HEADER = "SysHeader32";
  public static final String WC_LINK = "SysLink";
  public static final String WC_LISTVIEW = "SysListView32";
  public static final String WC_TABCONTROL = "SysTabControl32";
  public static final String WC_TREEVIEW = "SysTreeView32";
  public static final int WINDING = 2;
  public static final int WH_CBT = 5;
  public static final int WH_GETMESSAGE = 3;
  public static final int WH_MSGFILTER = -1;
  public static final int WH_FOREGROUNDIDLE = 11;
  public static final int WHEEL_DELTA = 120;
  public static final int WHEEL_PAGESCROLL = -1;
  public static final int WHITE_BRUSH = 0;
  public static final int WHITENESS = 16711778;
  public static final int WM_ACTIVATE = 6;
  public static final int WM_ACTIVATEAPP = 28;
  public static final int WM_APP = 32768;
  public static final int WM_DWMCOLORIZATIONCOLORCHANGED = 800;
  public static final int WM_CANCELMODE = 31;
  public static final int WM_CAPTURECHANGED = 533;
  public static final int WM_CHANGEUISTATE = 295;
  public static final int WM_CHAR = 258;
  public static final int WM_CLEAR = 771;
  public static final int WM_CLOSE = 16;
  public static final int WM_COMMAND = 273;
  public static final int WM_CONTEXTMENU = 123;
  public static final int WM_COPY = 769;
  public static final int WM_CREATE = 1;
  public static final int WM_CTLCOLORBTN = 309;
  public static final int WM_CTLCOLORDLG = 310;
  public static final int WM_CTLCOLOREDIT = 307;
  public static final int WM_CTLCOLORLISTBOX = 308;
  public static final int WM_CTLCOLORMSGBOX = 306;
  public static final int WM_CTLCOLORSCROLLBAR = 311;
  public static final int WM_CTLCOLORSTATIC = 312;
  public static final int WM_CUT = 768;
  public static final int WM_DEADCHAR = 259;
  public static final int WM_DESTROY = 2;
  public static final int WM_DRAWITEM = 43;
  public static final int WM_ENDSESSION = 22;
  public static final int WM_ENTERIDLE = 289;
  public static final int WM_ERASEBKGND = 20;
  public static final int WM_GETDLGCODE = 135;
  public static final int WM_GETFONT = 49;
  public static final int WM_GETOBJECT = 61;
  public static final int WM_GETMINMAXINFO = 36;
  public static final int WM_HELP = 83;
  public static final int WM_HOTKEY = 786;
  public static final int WM_HSCROLL = 276;
  public static final int WM_IME_CHAR = 646;
  public static final int WM_IME_COMPOSITION = 271;
  public static final int WM_IME_COMPOSITION_START = 269;
  public static final int WM_IME_ENDCOMPOSITION = 270;
  public static final int WM_INITDIALOG = 272;
  public static final int WM_INITMENUPOPUP = 279;
  public static final int WM_INPUTLANGCHANGE = 81;
  public static final int WM_KEYDOWN = 256;
  public static final int WM_KEYFIRST = 256;
  public static final int WM_KEYLAST = 264;
  public static final int WM_KEYUP = 257;
  public static final int WM_KILLFOCUS = 8;
  public static final int WM_LBUTTONDBLCLK = 515;
  public static final int WM_LBUTTONDOWN = 513;
  public static final int WM_LBUTTONUP = 514;
  public static final int WM_MBUTTONDBLCLK = 521;
  public static final int WM_MBUTTONDOWN = 519;
  public static final int WM_MBUTTONUP = 520;
  public static final int WM_MEASUREITEM = 44;
  public static final int WM_MENUCHAR = 288;
  public static final int WM_MENUSELECT = 287;
  public static final int WM_MOUSEACTIVATE = 33;
  public static final int WM_MOUSEFIRST = 512;
  public static final int WM_MOUSEHOVER = 673;
  public static final int WM_MOUSELEAVE = 675;
  public static final int WM_MOUSEMOVE = 512;
  public static final int WM_MOUSEWHEEL = 522;
  public static final int WM_MOUSEHWHEEL = 526;
  public static final int WM_MOUSELAST = 525;
  public static final int WM_MOVE = 3;
  public static final int WM_NCACTIVATE = 134;
  public static final int WM_NCCALCSIZE = 131;
  public static final int WM_NCHITTEST = 132;
  public static final int WM_NCLBUTTONDOWN = 161;
  public static final int WM_NCPAINT = 133;
  public static final int WM_NOTIFY = 78;
  public static final int WM_NULL = 0;
  public static final int WM_PAINT = 15;
  public static final int WM_PALETTECHANGED = 785;
  public static final int WM_PARENTNOTIFY = 528;
  public static final int WM_PASTE = 770;
  public static final int WM_PRINT = 791;
  public static final int WM_PRINTCLIENT = 792;
  public static final int WM_QUERYENDSESSION = 17;
  public static final int WM_QUERYNEWPALETTE = 783;
  public static final int WM_QUERYOPEN = 19;
  public static final int WM_QUERYUISTATE = 297;
  public static final int WM_RBUTTONDBLCLK = 518;
  public static final int WM_RBUTTONDOWN = 516;
  public static final int WM_RBUTTONUP = 517;
  public static final int WM_SETCURSOR = 32;
  public static final int WM_SETFOCUS = 7;
  public static final int WM_SETFONT = 48;
  public static final int WM_SETICON = 128;
  public static final int WM_SETREDRAW = 11;
  public static final int WM_SETTEXT = 12;
  public static final int WM_SETTINGCHANGE = 26;
  public static final int WM_SHOWWINDOW = 24;
  public static final int WM_SIZE = 5;
  public static final int WM_SYSCHAR = 262;
  public static final int WM_SYSCOLORCHANGE = 21;
  public static final int WM_SYSCOMMAND = 274;
  public static final int WM_SYSKEYDOWN = 260;
  public static final int WM_SYSKEYUP = 261;
  public static final int WM_TIMER = 275;
  public static final int WM_THEMECHANGED = 794;
  public static final int WM_UNDO = 772;
  public static final int WM_UPDATEUISTATE = 296;
  public static final int WM_USER = 1024;
  public static final int WM_VSCROLL = 277;
  public static final int WM_WINDOWPOSCHANGED = 71;
  public static final int WM_WINDOWPOSCHANGING = 70;
  public static final int WS_BORDER = 8388608;
  public static final int WS_CAPTION = 12582912;
  public static final int WS_CHILD = 1073741824;
  public static final int WS_CLIPCHILDREN = 33554432;
  public static final int WS_CLIPSIBLINGS = 67108864;
  public static final int WS_DISABLED = 67108864;
  public static final int WS_EX_APPWINDOW = 262144;
  public static final int WS_EX_CAPTIONOKBTN = -2147483648;
  public static final int WS_EX_CLIENTEDGE = 512;
  public static final int WS_EX_COMPOSITED = 33554432;
  public static final int WS_EX_DLGMODALFRAME = 1;
  public static final int WS_EX_LAYERED = 524288;
  public static final int WS_EX_LAYOUTRTL = 4194304;
  public static final int WS_EX_LEFTSCROLLBAR = 16384;
  public static final int WS_EX_MDICHILD = 64;
  public static final int WS_EX_NOINHERITLAYOUT = 1048576;
  public static final int WS_EX_NOACTIVATE = 134217728;
  public static final int WS_EX_RIGHT = 4096;
  public static final int WS_EX_RTLREADING = 8192;
  public static final int WS_EX_STATICEDGE = 131072;
  public static final int WS_EX_TOOLWINDOW = 128;
  public static final int WS_EX_TOPMOST = 8;
  public static final int WS_EX_TRANSPARENT = 32;
  public static final int WS_HSCROLL = 1048576;
  public static final int WS_MAXIMIZEBOX = IsWinCE ? 131072 : 65536;
  public static final int WS_MINIMIZEBOX = IsWinCE ? 65536 : 131072;
  public static final int WS_OVERLAPPED = IsWinCE ? 12582912 : 0;
  public static final int WS_OVERLAPPEDWINDOW = 13565952;
  public static final int WS_POPUP = -2147483648;
  public static final int WS_SYSMENU = 524288;
  public static final int WS_TABSTOP = 65536;
  public static final int WS_THICKFRAME = 262144;
  public static final int WS_VISIBLE = 268435456;
  public static final int WS_VSCROLL = 2097152;
  public static final int WM_XBUTTONDOWN = 523;
  public static final int WM_XBUTTONUP = 524;
  public static final int WM_XBUTTONDBLCLK = 525;
  public static final int XBUTTON1 = 1;
  public static final int XBUTTON2 = 2;
  public static final int PROCESS_DUP_HANDLE = 64;
  public static final int PROCESS_VM_READ = 16;
  public static final int DUPLICATE_SAME_ACCESS = 2;

  static
  {
    Library.loadLibrary("swt");
    Object localObject = new OSVERSIONINFOW();
    ((OSVERSIONINFO)localObject).dwOSVersionInfoSize = OSVERSIONINFOW.sizeof;
    if (!GetVersionExW((OSVERSIONINFOW)localObject))
    {
      localObject = new OSVERSIONINFOA();
      ((OSVERSIONINFO)localObject).dwOSVersionInfoSize = OSVERSIONINFOA.sizeof;
      GetVersionExA((OSVERSIONINFOA)localObject);
    }
    OSVERSIONINFO.sizeof = ((OSVERSIONINFO)localObject).dwOSVersionInfoSize;
    IsWin32s = ((OSVERSIONINFO)localObject).dwPlatformId == 0;
    IsWin95 = ((OSVERSIONINFO)localObject).dwPlatformId == 1;
    IsWinNT = ((OSVERSIONINFO)localObject).dwPlatformId == 2;
    IsWinCE = ((OSVERSIONINFO)localObject).dwPlatformId == 3;
    IsSP = IsSP();
    IsPPC = IsPPC();
    IsHPC = (IsWinCE) && (!IsPPC) && (!IsSP);
    WIN32_MAJOR = ((OSVERSIONINFO)localObject).dwMajorVersion;
    WIN32_MINOR = ((OSVERSIONINFO)localObject).dwMinorVersion;
    WIN32_VERSION = VERSION(WIN32_MAJOR, WIN32_MINOR);
    IsUnicode = (!IsWin32s) && (!IsWin95);
    int k;
    int m;
    if ((System.getProperty("org.eclipse.swt.internal.win32.OS.NO_MANIFEST") == null) && (!IsWinCE) && (WIN32_VERSION >= VERSION(5, 1)))
    {
      TCHAR localTCHAR1 = new TCHAR(0, 260);
      j = GetLibraryHandle();
      while (GetModuleFileName(j, localTCHAR1, localTCHAR1.length()) == localTCHAR1.length())
        localTCHAR1 = new TCHAR(0, localTCHAR1.length() + 260);
      k = GetProcessHeap();
      m = localTCHAR1.length() * (IsUnicode ? 2 : 1);
      int n = HeapAlloc(k, 8, m);
      MoveMemory(n, localTCHAR1, m);
      ACTCTX localACTCTX = new ACTCTX();
      localACTCTX.cbSize = ACTCTX.sizeof;
      localACTCTX.dwFlags = 24;
      localACTCTX.lpSource = n;
      localACTCTX.lpResourceName = 2;
      int i2 = CreateActCtx(localACTCTX);
      if (n != 0)
        HeapFree(k, 0, n);
      int[] arrayOfInt = new int[1];
      ActivateActCtx(i2, arrayOfInt);
    }
    if ((!IsWinCE) && (WIN32_VERSION >= VERSION(6, 0)))
      SetProcessDPIAware();
    int i = GetSystemMetrics(42) != 0 ? 1 : 0;
    int j = GetSystemMetrics(82) != 0 ? 1 : 0;
    IsDBLocale = (i != 0) || (j != 0);
    if ((!IsWinCE) && (WIN32_VERSION == VERSION(5, 1)))
    {
      k = GetSystemDefaultUILanguage();
      m = PRIMARYLANGID(k);
      if (m == 18)
      {
        OSVERSIONINFOEXA localOSVERSIONINFOEXA = IsUnicode ? new OSVERSIONINFOEXW() : new OSVERSIONINFOEXA();
        localOSVERSIONINFOEXA.dwOSVersionInfoSize = OSVERSIONINFOEX.sizeof;
        GetVersionEx(localOSVERSIONINFOEXA);
        if (localOSVERSIONINFOEXA.wServicePackMajor < 2)
          ImmDisableTextFrameService(0);
      }
    }
    localObject = new DLLVERSIONINFO();
    ((DLLVERSIONINFO)localObject).cbSize = DLLVERSIONINFO.sizeof;
    ((DLLVERSIONINFO)localObject).dwMajorVersion = 4;
    ((DLLVERSIONINFO)localObject).dwMinorVersion = 0;
    TCHAR localTCHAR2 = new TCHAR(0, "comctl32.dll", true);
    j = LoadLibrary(localTCHAR2);
    String str;
    byte[] arrayOfByte;
    int i1;
    if (j != 0)
    {
      str = "";
      arrayOfByte = new byte[str.length()];
      for (i1 = 0; i1 < arrayOfByte.length; i1++)
        arrayOfByte[i1] = ((byte)str.charAt(i1));
      i1 = GetProcAddress(j, arrayOfByte);
      if (i1 != 0)
        Call(i1, (DLLVERSIONINFO)localObject);
      FreeLibrary(j);
    }
    COMCTL32_MAJOR = ((DLLVERSIONINFO)localObject).dwMajorVersion;
    COMCTL32_MINOR = ((DLLVERSIONINFO)localObject).dwMinorVersion;
    COMCTL32_VERSION = VERSION(COMCTL32_MAJOR, COMCTL32_MINOR);
    localObject = new DLLVERSIONINFO();
    ((DLLVERSIONINFO)localObject).cbSize = DLLVERSIONINFO.sizeof;
    ((DLLVERSIONINFO)localObject).dwMajorVersion = 4;
    localTCHAR2 = new TCHAR(0, "Shell32.dll", true);
    j = LoadLibrary(localTCHAR2);
    if (j != 0)
    {
      str = "";
      arrayOfByte = new byte[str.length()];
      for (i1 = 0; i1 < arrayOfByte.length; i1++)
        arrayOfByte[i1] = ((byte)str.charAt(i1));
      i1 = GetProcAddress(j, arrayOfByte);
      if (i1 != 0)
        Call(i1, (DLLVERSIONINFO)localObject);
      FreeLibrary(j);
    }
  }

  public static int VERSION(int paramInt1, int paramInt2)
  {
    return paramInt1 << 16 | paramInt2;
  }

  public static final native int ACCEL_sizeof();

  public static final native int ACTCTX_sizeof();

  public static final native int BITMAP_sizeof();

  public static final native int BITMAPINFOHEADER_sizeof();

  public static final native int BLENDFUNCTION_sizeof();

  public static final native int BP_PAINTPARAMS_sizeof();

  public static final native int BROWSEINFO_sizeof();

  public static final native int BUTTON_IMAGELIST_sizeof();

  public static final native int CANDIDATEFORM_sizeof();

  public static final native int CHOOSECOLOR_sizeof();

  public static final native int CHOOSEFONT_sizeof();

  public static final native int COMBOBOXINFO_sizeof();

  public static final native int COMPOSITIONFORM_sizeof();

  public static final native int CREATESTRUCT_sizeof();

  public static final native int DEVMODEA_sizeof();

  public static final native int DEVMODEW_sizeof();

  public static final native int DIBSECTION_sizeof();

  public static final native int DLLVERSIONINFO_sizeof();

  public static final native int DOCHOSTUIINFO_sizeof();

  public static final native int DOCINFO_sizeof();

  public static final native int DRAWITEMSTRUCT_sizeof();

  public static final native int DROPFILES_sizeof();

  public static final native int DWM_BLURBEHIND_sizeof();

  public static final native int EMR_sizeof();

  public static final native int EMREXTCREATEFONTINDIRECTW_sizeof();

  public static final native int EXTLOGFONTW_sizeof();

  public static final native int EXTLOGPEN_sizeof();

  public static final native int FILETIME_sizeof();

  public static final native int GCP_RESULTS_sizeof();

  public static final native int GRADIENT_RECT_sizeof();

  public static final native int GUITHREADINFO_sizeof();

  public static final native int HDITEM_sizeof();

  public static final native int HDLAYOUT_sizeof();

  public static final native int HDHITTESTINFO_sizeof();

  public static final native int HELPINFO_sizeof();

  public static final native int HIGHCONTRAST_sizeof();

  public static final native int ICONINFO_sizeof();

  public static final native int INITCOMMONCONTROLSEX_sizeof();

  public static final native int INPUT_sizeof();

  public static final native int KEYBDINPUT_sizeof();

  public static final native int LITEM_sizeof();

  public static final native int LOGBRUSH_sizeof();

  public static final native int LOGFONTA_sizeof();

  public static final native int LOGFONTW_sizeof();

  public static final native int LOGPEN_sizeof();

  public static final native int LVCOLUMN_sizeof();

  public static final native int LVHITTESTINFO_sizeof();

  public static final native int LVITEM_sizeof();

  public static final native int LVINSERTMARK_sizeof();

  public static final native int MARGINS_sizeof();

  public static final native int MCHITTESTINFO_sizeof();

  public static final native int MEASUREITEMSTRUCT_sizeof();

  public static final native int MENUBARINFO_sizeof();

  public static final native int MENUINFO_sizeof();

  public static final native int MENUITEMINFO_sizeof();

  public static final native int MINMAXINFO_sizeof();

  public static final native int MOUSEINPUT_sizeof();

  public static final native int MONITORINFO_sizeof();

  public static final native int MSG_sizeof();

  public static final native int NMCUSTOMDRAW_sizeof();

  public static final native int NMHDR_sizeof();

  public static final native int NMHEADER_sizeof();

  public static final native int NMLINK_sizeof();

  public static final native int NMLISTVIEW_sizeof();

  public static final native int NMLVCUSTOMDRAW_sizeof();

  public static final native int NMLVDISPINFO_sizeof();

  public static final native int NMLVFINDITEM_sizeof();

  public static final native int NMLVODSTATECHANGE_sizeof();

  public static final native int NMREBARCHEVRON_sizeof();

  public static final native int NMREBARCHILDSIZE_sizeof();

  public static final native int NMRGINFO_sizeof();

  public static final native int NMTBHOTITEM_sizeof();

  public static final native int NMTREEVIEW_sizeof();

  public static final native int NMTOOLBAR_sizeof();

  public static final native int NMTTDISPINFOA_sizeof();

  public static final native int NMTTDISPINFOW_sizeof();

  public static final native int NMTTCUSTOMDRAW_sizeof();

  public static final native int NMTVCUSTOMDRAW_sizeof();

  public static final native int NMTVDISPINFO_sizeof();

  public static final native int NMTVITEMCHANGE_sizeof();

  public static final native int NMUPDOWN_sizeof();

  public static final native int NONCLIENTMETRICSA_sizeof();

  public static final native int NONCLIENTMETRICSW_sizeof();

  public static final native int NOTIFYICONDATAA_V2_SIZE();

  public static final native int NOTIFYICONDATAW_V2_SIZE();

  public static final native int OFNOTIFY_sizeof();

  public static final native int OPENFILENAME_sizeof();

  public static final native int OSVERSIONINFOA_sizeof();

  public static final native int OSVERSIONINFOW_sizeof();

  public static final native int OSVERSIONINFOEXA_sizeof();

  public static final native int OSVERSIONINFOEXW_sizeof();

  public static final native int OUTLINETEXTMETRICA_sizeof();

  public static final native int OUTLINETEXTMETRICW_sizeof();

  public static final native int PAINTSTRUCT_sizeof();

  public static final native int PANOSE_sizeof();

  public static final native int POINT_sizeof();

  public static final native int PRINTDLG_sizeof();

  public static final native int PROCESS_INFORMATION_sizeof();

  public static final native int PROPVARIANT_sizeof();

  public static final native int PROPERTYKEY_sizeof();

  public static final native int REBARBANDINFO_sizeof();

  public static final native int RECT_sizeof();

  public static final native int SAFEARRAY_sizeof();

  public static final native int SAFEARRAYBOUND_sizeof();

  public static final native int SCRIPT_ANALYSIS_sizeof();

  public static final native int SCRIPT_CONTROL_sizeof();

  public static final native int SCRIPT_DIGITSUBSTITUTE_sizeof();

  public static final native int SCRIPT_FONTPROPERTIES_sizeof();

  public static final native int SCRIPT_ITEM_sizeof();

  public static final native int SCRIPT_LOGATTR_sizeof();

  public static final native int SCRIPT_PROPERTIES_sizeof();

  public static final native int SCRIPT_STATE_sizeof();

  public static final native int SCRIPT_STRING_ANALYSIS_sizeof();

  public static final native int SCROLLBARINFO_sizeof();

  public static final native int SCROLLINFO_sizeof();

  public static final native int SHACTIVATEINFO_sizeof();

  public static final native int SHDRAGIMAGE_sizeof();

  public static final native int SHELLEXECUTEINFO_sizeof();

  public static final native int SHFILEINFOA_sizeof();

  public static final native int SHFILEINFOW_sizeof();

  public static final native int SHMENUBARINFO_sizeof();

  public static final native int SHRGINFO_sizeof();

  public static final native int SIPINFO_sizeof();

  public static final native int SIZE_sizeof();

  public static final native int STARTUPINFO_sizeof();

  public static final native int SYSTEMTIME_sizeof();

  public static final native int TBBUTTON_sizeof();

  public static final native int TBBUTTONINFO_sizeof();

  public static final native int TCITEM_sizeof();

  public static final native int TCHITTESTINFO_sizeof();

  public static final native int TEXTMETRICA_sizeof();

  public static final native int TEXTMETRICW_sizeof();

  public static final native int TF_DA_COLOR_sizeof();

  public static final native int TF_DISPLAYATTRIBUTE_sizeof();

  public static final native int TOOLINFO_sizeof();

  public static final native int TRACKMOUSEEVENT_sizeof();

  public static final native int TRIVERTEX_sizeof();

  public static final native int TVHITTESTINFO_sizeof();

  public static final native int TVINSERTSTRUCT_sizeof();

  public static final native int TVITEM_sizeof();

  public static final native int TVITEMEX_sizeof();

  public static final native int TVSORTCB_sizeof();

  public static final native int UDACCEL_sizeof();

  public static final native int WINDOWPLACEMENT_sizeof();

  public static final native int WINDOWPOS_sizeof();

  public static final native int WNDCLASS_sizeof();

  public static final int AddFontResourceEx(TCHAR paramTCHAR, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return AddFontResourceExW((char[])localObject, paramInt1, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return AddFontResourceExA((byte[])localObject, paramInt1, paramInt2);
  }

  public static final int AssocQueryString(int paramInt1, int paramInt2, TCHAR paramTCHAR1, TCHAR paramTCHAR2, TCHAR paramTCHAR3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.chars;
      return AssocQueryStringW(paramInt1, paramInt2, (char[])localObject1, (char[])localObject2, (char[])localObject3, paramArrayOfInt);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    Object localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.bytes;
    return AssocQueryStringA(paramInt1, paramInt2, (byte[])localObject1, (byte[])localObject2, (byte[])localObject3, paramArrayOfInt);
  }

  public static final int CallWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (IsUnicode)
      return CallWindowProcW(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    return CallWindowProcA(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public static final int CharUpper(int paramInt)
  {
    if (IsUnicode)
      return CharUpperW(paramInt);
    return CharUpperA(paramInt);
  }

  public static final int CharLower(int paramInt)
  {
    if (IsUnicode)
      return CharLowerW(paramInt);
    return CharLowerA(paramInt);
  }

  public static final boolean ChooseColor(CHOOSECOLOR paramCHOOSECOLOR)
  {
    if (IsUnicode)
      return ChooseColorW(paramCHOOSECOLOR);
    return ChooseColorA(paramCHOOSECOLOR);
  }

  public static final boolean ChooseFont(CHOOSEFONT paramCHOOSEFONT)
  {
    if (IsUnicode)
      return ChooseFontW(paramCHOOSEFONT);
    return ChooseFontA(paramCHOOSEFONT);
  }

  public static final int CreateActCtx(ACTCTX paramACTCTX)
  {
    if (IsUnicode)
      return CreateActCtxW(paramACTCTX);
    return CreateActCtxA(paramACTCTX);
  }

  public static final int CreateAcceleratorTable(byte[] paramArrayOfByte, int paramInt)
  {
    if (IsUnicode)
      return CreateAcceleratorTableW(paramArrayOfByte, paramInt);
    return CreateAcceleratorTableA(paramArrayOfByte, paramInt);
  }

  public static final int CreateDC(TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return CreateDCW((char[])localObject1, (char[])localObject2, paramInt1, paramInt2);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return CreateDCA((byte[])localObject1, (byte[])localObject2, paramInt1, paramInt2);
  }

  public static final int CreateEnhMetaFile(int paramInt, TCHAR paramTCHAR1, RECT paramRECT, TCHAR paramTCHAR2)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return CreateEnhMetaFileW(paramInt, (char[])localObject1, paramRECT, (char[])localObject2);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return CreateEnhMetaFileA(paramInt, (byte[])localObject1, paramRECT, (byte[])localObject2);
  }

  public static final int CreateFontIndirect(int paramInt)
  {
    if (IsUnicode)
      return CreateFontIndirectW(paramInt);
    return CreateFontIndirectA(paramInt);
  }

  public static final int CreateFontIndirect(LOGFONT paramLOGFONT)
  {
    if (IsUnicode)
      return CreateFontIndirectW((LOGFONTW)paramLOGFONT);
    return CreateFontIndirectA((LOGFONTA)paramLOGFONT);
  }

  public static final boolean CreateProcess(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6, int paramInt7, STARTUPINFO paramSTARTUPINFO, PROCESS_INFORMATION paramPROCESS_INFORMATION)
  {
    if (IsUnicode)
      return CreateProcessW(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, paramInt5, paramInt6, paramInt7, paramSTARTUPINFO, paramPROCESS_INFORMATION);
    return CreateProcessA(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean, paramInt5, paramInt6, paramInt7, paramSTARTUPINFO, paramPROCESS_INFORMATION);
  }

  public static final int CreateWindowEx(int paramInt1, TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, CREATESTRUCT paramCREATESTRUCT)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return CreateWindowExW(paramInt1, (char[])localObject1, (char[])localObject2, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramCREATESTRUCT);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return CreateWindowExA(paramInt1, (byte[])localObject1, (byte[])localObject2, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramCREATESTRUCT);
  }

  public static final int DefMDIChildProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return DefMDIChildProcW(paramInt1, paramInt2, paramInt3, paramInt4);
    return DefMDIChildProcA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final int DefFrameProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (IsUnicode)
      return DefFrameProcW(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
    return DefFrameProcA(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public static final int DefWindowProc(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return DefWindowProcW(paramInt1, paramInt2, paramInt3, paramInt4);
    return DefWindowProcA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final int DispatchMessage(MSG paramMSG)
  {
    if (IsUnicode)
      return DispatchMessageW(paramMSG);
    return DispatchMessageA(paramMSG);
  }

  public static final int DragQueryFile(int paramInt1, int paramInt2, TCHAR paramTCHAR, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return DragQueryFileW(paramInt1, paramInt2, (char[])localObject, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return DragQueryFileA(paramInt1, paramInt2, (byte[])localObject, paramInt3);
  }

  public static final boolean DrawState(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10)
  {
    if (IsUnicode)
      return DrawStateW(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
    return DrawStateA(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10);
  }

  public static final int DrawText(int paramInt1, TCHAR paramTCHAR, int paramInt2, RECT paramRECT, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return DrawTextW(paramInt1, (char[])localObject, paramInt2, paramRECT, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return DrawTextA(paramInt1, (byte[])localObject, paramInt2, paramRECT, paramInt3);
  }

  public static final int EnumFontFamilies(int paramInt1, TCHAR paramTCHAR, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return EnumFontFamiliesW(paramInt1, (char[])localObject, paramInt2, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return EnumFontFamiliesA(paramInt1, (byte[])localObject, paramInt2, paramInt3);
  }

  public static final int EnumFontFamiliesEx(int paramInt1, LOGFONT paramLOGFONT, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return EnumFontFamiliesExW(paramInt1, (LOGFONTW)paramLOGFONT, paramInt2, paramInt3, paramInt4);
    return EnumFontFamiliesExA(paramInt1, (LOGFONTA)paramLOGFONT, paramInt2, paramInt3, paramInt4);
  }

  public static final boolean EnumSystemLocales(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return EnumSystemLocalesW(paramInt1, paramInt2);
    return EnumSystemLocalesA(paramInt1, paramInt2);
  }

  public static final boolean EnumSystemLanguageGroups(int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return EnumSystemLanguageGroupsW(paramInt1, paramInt2, paramInt3);
    return EnumSystemLanguageGroupsA(paramInt1, paramInt2, paramInt3);
  }

  public static final int ExpandEnvironmentStrings(TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return ExpandEnvironmentStringsW((char[])localObject1, (char[])localObject2, paramInt);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return ExpandEnvironmentStringsA((byte[])localObject1, (byte[])localObject2, paramInt);
  }

  public static final int ExtractIconEx(TCHAR paramTCHAR, int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return ExtractIconExW((char[])localObject, paramInt1, paramArrayOfInt1, paramArrayOfInt2, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return ExtractIconExA((byte[])localObject, paramInt1, paramArrayOfInt1, paramArrayOfInt2, paramInt2);
  }

  public static final boolean ExtTextOut(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, TCHAR paramTCHAR, int paramInt5, int[] paramArrayOfInt)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return ExtTextOutW(paramInt1, paramInt2, paramInt3, paramInt4, paramRECT, (char[])localObject, paramInt5, paramArrayOfInt);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return ExtTextOutA(paramInt1, paramInt2, paramInt3, paramInt4, paramRECT, (byte[])localObject, paramInt5, paramArrayOfInt);
  }

  public static final int FindWindow(TCHAR paramTCHAR1, TCHAR paramTCHAR2)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return FindWindowW((char[])localObject1, (char[])localObject2);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return FindWindowA((byte[])localObject1, (byte[])localObject2);
  }

  public static final int FormatMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6)
  {
    if (IsUnicode)
      return FormatMessageW(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5, paramInt6);
    return FormatMessageA(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfInt, paramInt5, paramInt6);
  }

  public static final boolean GetCharABCWidths(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
      return GetCharABCWidthsW(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
    return GetCharABCWidthsA(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public static final int GetCharacterPlacement(int paramInt1, TCHAR paramTCHAR, int paramInt2, int paramInt3, GCP_RESULTS paramGCP_RESULTS, int paramInt4)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetCharacterPlacementW(paramInt1, (char[])localObject, paramInt2, paramInt3, paramGCP_RESULTS, paramInt4);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetCharacterPlacementA(paramInt1, (byte[])localObject, paramInt2, paramInt3, paramGCP_RESULTS, paramInt4);
  }

  public static final boolean GetCharWidth(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
      return GetCharWidthW(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
    return GetCharWidthA(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public static final boolean GetClassInfo(int paramInt, TCHAR paramTCHAR, WNDCLASS paramWNDCLASS)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetClassInfoW(paramInt, (char[])localObject, paramWNDCLASS);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetClassInfoA(paramInt, (byte[])localObject, paramWNDCLASS);
  }

  public static final int GetClassName(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetClassNameW(paramInt1, (char[])localObject, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetClassNameA(paramInt1, (byte[])localObject, paramInt2);
  }

  public static final int GetClipboardFormatName(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetClipboardFormatNameW(paramInt1, (char[])localObject, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetClipboardFormatNameA(paramInt1, (byte[])localObject, paramInt2);
  }

  public static final int GetDateFormat(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return GetDateFormatW(paramInt1, paramInt2, paramSYSTEMTIME, (char[])localObject1, (char[])localObject2, paramInt3);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return GetDateFormatA(paramInt1, paramInt2, paramSYSTEMTIME, (byte[])localObject1, (byte[])localObject2, paramInt3);
  }

  public static final int GetKeyNameText(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetKeyNameTextW(paramInt1, (char[])localObject, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetKeyNameTextA(paramInt1, (byte[])localObject, paramInt2);
  }

  public static final int GetLocaleInfo(int paramInt1, int paramInt2, TCHAR paramTCHAR, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetLocaleInfoW(paramInt1, paramInt2, (char[])localObject, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetLocaleInfoA(paramInt1, paramInt2, (byte[])localObject, paramInt3);
  }

  public static final boolean GetMenuItemInfo(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO)
  {
    if (IsUnicode)
      return GetMenuItemInfoW(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
    return GetMenuItemInfoA(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
  }

  public static final boolean GetMessage(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return GetMessageW(paramMSG, paramInt1, paramInt2, paramInt3);
    return GetMessageA(paramMSG, paramInt1, paramInt2, paramInt3);
  }

  public static final int GetModuleFileName(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetModuleFileNameW(paramInt1, (char[])localObject, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetModuleFileNameA(paramInt1, (byte[])localObject, paramInt2);
  }

  public static final int GetModuleHandle(TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetModuleHandleW((char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetModuleHandleA((byte[])localObject);
  }

  public static final boolean GetMonitorInfo(int paramInt, MONITORINFO paramMONITORINFO)
  {
    if (IsUnicode)
      return GetMonitorInfoW(paramInt, paramMONITORINFO);
    return GetMonitorInfoA(paramInt, paramMONITORINFO);
  }

  public static final int GetObject(int paramInt1, int paramInt2, BITMAP paramBITMAP)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramBITMAP);
    return GetObjectA(paramInt1, paramInt2, paramBITMAP);
  }

  public static final int GetObject(int paramInt1, int paramInt2, DIBSECTION paramDIBSECTION)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramDIBSECTION);
    return GetObjectA(paramInt1, paramInt2, paramDIBSECTION);
  }

  public static final int GetObject(int paramInt1, int paramInt2, EXTLOGPEN paramEXTLOGPEN)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramEXTLOGPEN);
    return GetObjectA(paramInt1, paramInt2, paramEXTLOGPEN);
  }

  public static final int GetObject(int paramInt1, int paramInt2, LOGBRUSH paramLOGBRUSH)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramLOGBRUSH);
    return GetObjectA(paramInt1, paramInt2, paramLOGBRUSH);
  }

  public static final int GetObject(int paramInt1, int paramInt2, LOGFONT paramLOGFONT)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, (LOGFONTW)paramLOGFONT);
    return GetObjectA(paramInt1, paramInt2, (LOGFONTA)paramLOGFONT);
  }

  public static final int GetObject(int paramInt1, int paramInt2, LOGPEN paramLOGPEN)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramLOGPEN);
    return GetObjectA(paramInt1, paramInt2, paramLOGPEN);
  }

  public static final int GetObject(int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return GetObjectW(paramInt1, paramInt2, paramInt3);
    return GetObjectA(paramInt1, paramInt2, paramInt3);
  }

  public static final boolean GetOpenFileName(OPENFILENAME paramOPENFILENAME)
  {
    if (IsUnicode)
      return GetOpenFileNameW(paramOPENFILENAME);
    return GetOpenFileNameA(paramOPENFILENAME);
  }

  public static final int GetOutlineTextMetrics(int paramInt1, int paramInt2, OUTLINETEXTMETRIC paramOUTLINETEXTMETRIC)
  {
    if (IsUnicode)
      return GetOutlineTextMetricsW(paramInt1, paramInt2, (OUTLINETEXTMETRICW)paramOUTLINETEXTMETRIC);
    return GetOutlineTextMetricsA(paramInt1, paramInt2, (OUTLINETEXTMETRICA)paramOUTLINETEXTMETRIC);
  }

  public static final int GetProfileString(TCHAR paramTCHAR1, TCHAR paramTCHAR2, TCHAR paramTCHAR3, TCHAR paramTCHAR4, int paramInt)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.chars;
      localObject4 = paramTCHAR4 == null ? null : paramTCHAR4.chars;
      return GetProfileStringW((char[])localObject1, (char[])localObject2, (char[])localObject3, (char[])localObject4, paramInt);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    Object localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.bytes;
    Object localObject4 = paramTCHAR4 == null ? null : paramTCHAR4.bytes;
    return GetProfileStringA((byte[])localObject1, (byte[])localObject2, (byte[])localObject3, (byte[])localObject4, paramInt);
  }

  public static int GetProp(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return GetPropW(paramInt1, paramInt2);
    return GetPropA(paramInt1, paramInt2);
  }

  public static final boolean GetSaveFileName(OPENFILENAME paramOPENFILENAME)
  {
    if (IsUnicode)
      return GetSaveFileNameW(paramOPENFILENAME);
    return GetSaveFileNameA(paramOPENFILENAME);
  }

  public static final void GetStartupInfo(STARTUPINFO paramSTARTUPINFO)
  {
    if (IsUnicode)
      GetStartupInfoW(paramSTARTUPINFO);
    else
      GetStartupInfoA(paramSTARTUPINFO);
  }

  public static final boolean GetTextExtentPoint32(int paramInt1, TCHAR paramTCHAR, int paramInt2, SIZE paramSIZE)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetTextExtentPoint32W(paramInt1, (char[])localObject, paramInt2, paramSIZE);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetTextExtentPoint32A(paramInt1, (byte[])localObject, paramInt2, paramSIZE);
  }

  public static final boolean GetTextMetrics(int paramInt, TEXTMETRIC paramTEXTMETRIC)
  {
    if (IsUnicode)
      return GetTextMetricsW(paramInt, (TEXTMETRICW)paramTEXTMETRIC);
    return GetTextMetricsA(paramInt, (TEXTMETRICA)paramTEXTMETRIC);
  }

  public static final int GetTimeFormat(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return GetTimeFormatW(paramInt1, paramInt2, paramSYSTEMTIME, (char[])localObject1, (char[])localObject2, paramInt3);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return GetTimeFormatA(paramInt1, paramInt2, paramSYSTEMTIME, (byte[])localObject1, (byte[])localObject2, paramInt3);
  }

  public static final boolean GetVersionEx(OSVERSIONINFO paramOSVERSIONINFO)
  {
    if (IsUnicode)
      return GetVersionExW((OSVERSIONINFOW)paramOSVERSIONINFO);
    return GetVersionExA((OSVERSIONINFOA)paramOSVERSIONINFO);
  }

  public static final boolean GetVersionEx(OSVERSIONINFOEX paramOSVERSIONINFOEX)
  {
    if (IsUnicode)
      return GetVersionExW((OSVERSIONINFOEXW)paramOSVERSIONINFOEX);
    return GetVersionExA((OSVERSIONINFOEXA)paramOSVERSIONINFOEX);
  }

  public static final int GetWindowLong(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return GetWindowLongW(paramInt1, paramInt2);
    return GetWindowLongA(paramInt1, paramInt2);
  }

  public static final int GetWindowLongPtr(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return GetWindowLongPtrW(paramInt1, paramInt2);
    return GetWindowLongPtrA(paramInt1, paramInt2);
  }

  public static final int GetWindowText(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GetWindowTextW(paramInt1, (char[])localObject, paramInt2);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GetWindowTextA(paramInt1, (byte[])localObject, paramInt2);
  }

  public static final int GetWindowTextLength(int paramInt)
  {
    if (IsUnicode)
      return GetWindowTextLengthW(paramInt);
    return GetWindowTextLengthA(paramInt);
  }

  public static final int GlobalAddAtom(TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return GlobalAddAtomW((char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return GlobalAddAtomA((byte[])localObject);
  }

  public static final boolean ImmGetCompositionFont(int paramInt, LOGFONT paramLOGFONT)
  {
    if (IsUnicode)
      return ImmGetCompositionFontW(paramInt, (LOGFONTW)paramLOGFONT);
    return ImmGetCompositionFontA(paramInt, (LOGFONTA)paramLOGFONT);
  }

  public static final boolean ImmSetCompositionFont(int paramInt, LOGFONT paramLOGFONT)
  {
    if (IsUnicode)
      return ImmSetCompositionFontW(paramInt, (LOGFONTW)paramLOGFONT);
    return ImmSetCompositionFontA(paramInt, (LOGFONTA)paramLOGFONT);
  }

  public static final int ImmGetCompositionString(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    if (IsUnicode)
      return ImmGetCompositionStringW(paramInt1, paramInt2, paramArrayOfByte, paramInt3);
    return ImmGetCompositionStringA(paramInt1, paramInt2, paramArrayOfByte, paramInt3);
  }

  public static final int ImmGetCompositionString(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    if (IsUnicode)
      return ImmGetCompositionStringW(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
    return ImmGetCompositionStringA(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
  }

  public static final int ImmGetCompositionString(int paramInt1, int paramInt2, TCHAR paramTCHAR, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return ImmGetCompositionStringW(paramInt1, paramInt2, (char[])localObject, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return ImmGetCompositionStringA(paramInt1, paramInt2, (byte[])localObject, paramInt3);
  }

  public static final boolean InternetGetCookie(TCHAR paramTCHAR1, TCHAR paramTCHAR2, TCHAR paramTCHAR3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.chars;
      return InternetGetCookieW((char[])localObject1, (char[])localObject2, (char[])localObject3, paramArrayOfInt);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    Object localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.bytes;
    return InternetGetCookieA((byte[])localObject1, (byte[])localObject2, (byte[])localObject3, paramArrayOfInt);
  }

  public static final boolean InternetSetCookie(TCHAR paramTCHAR1, TCHAR paramTCHAR2, TCHAR paramTCHAR3)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.chars;
      return InternetSetCookieW((char[])localObject1, (char[])localObject2, (char[])localObject3);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    Object localObject3 = paramTCHAR3 == null ? null : paramTCHAR3.bytes;
    return InternetSetCookieA((byte[])localObject1, (byte[])localObject2, (byte[])localObject3);
  }

  public static final boolean InsertMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return InsertMenuW(paramInt1, paramInt2, paramInt3, paramInt4, (char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return InsertMenuA(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])localObject);
  }

  public static final boolean InsertMenuItem(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO)
  {
    if (IsUnicode)
      return InsertMenuItemW(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
    return InsertMenuItemA(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
  }

  public static final int LoadBitmap(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return LoadBitmapW(paramInt1, paramInt2);
    return LoadBitmapA(paramInt1, paramInt2);
  }

  public static final int LoadCursor(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return LoadCursorW(paramInt1, paramInt2);
    return LoadCursorA(paramInt1, paramInt2);
  }

  public static final int LoadIcon(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return LoadIconW(paramInt1, paramInt2);
    return LoadIconA(paramInt1, paramInt2);
  }

  public static final int LoadImage(int paramInt1, TCHAR paramTCHAR, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return LoadImageW(paramInt1, (char[])localObject, paramInt2, paramInt3, paramInt4, paramInt5);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return LoadImageA(paramInt1, (byte[])localObject, paramInt2, paramInt3, paramInt4, paramInt5);
  }

  public static final int LoadImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (IsUnicode)
      return LoadImageW(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    return LoadImageA(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }

  public static final int LoadLibrary(TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return LoadLibraryW((char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return LoadLibraryA((byte[])localObject);
  }

  public static final int LoadString(int paramInt1, int paramInt2, TCHAR paramTCHAR, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return LoadStringW(paramInt1, paramInt2, (char[])localObject, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return LoadStringA(paramInt1, paramInt2, (byte[])localObject, paramInt3);
  }

  public static final int MapVirtualKey(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return MapVirtualKeyW(paramInt1, paramInt2);
    return MapVirtualKeyA(paramInt1, paramInt2);
  }

  public static final int MessageBox(int paramInt1, TCHAR paramTCHAR1, TCHAR paramTCHAR2, int paramInt2)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return MessageBoxW(paramInt1, (char[])localObject1, (char[])localObject2, paramInt2);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return MessageBoxA(paramInt1, (byte[])localObject1, (byte[])localObject2, paramInt2);
  }

  public static final void MoveMemory(int paramInt1, TCHAR paramTCHAR, int paramInt2)
  {
    Object localObject;
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      MoveMemory(paramInt1, (char[])localObject, paramInt2);
    }
    else
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
      MoveMemory(paramInt1, (byte[])localObject, paramInt2);
    }
  }

  public static final void MoveMemory(TCHAR paramTCHAR, int paramInt1, int paramInt2)
  {
    Object localObject;
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      MoveMemory((char[])localObject, paramInt1, paramInt2);
    }
    else
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
      MoveMemory((byte[])localObject, paramInt1, paramInt2);
    }
  }

  public static final void MoveMemory(int paramInt1, DEVMODE paramDEVMODE, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory(paramInt1, (DEVMODEW)paramDEVMODE, paramInt2);
    else
      MoveMemory(paramInt1, (DEVMODEA)paramDEVMODE, paramInt2);
  }

  public static final void MoveMemory(DEVMODE paramDEVMODE, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory((DEVMODEW)paramDEVMODE, paramInt1, paramInt2);
    else
      MoveMemory((DEVMODEA)paramDEVMODE, paramInt1, paramInt2);
  }

  public static final void MoveMemory(int paramInt1, LOGFONT paramLOGFONT, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory(paramInt1, (LOGFONTW)paramLOGFONT, paramInt2);
    else
      MoveMemory(paramInt1, (LOGFONTA)paramLOGFONT, paramInt2);
  }

  public static final void MoveMemory(LOGFONT paramLOGFONT, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory((LOGFONTW)paramLOGFONT, paramInt1, paramInt2);
    else
      MoveMemory((LOGFONTA)paramLOGFONT, paramInt1, paramInt2);
  }

  public static final void MoveMemory(int paramInt1, NMTTDISPINFO paramNMTTDISPINFO, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory(paramInt1, (NMTTDISPINFOW)paramNMTTDISPINFO, paramInt2);
    else
      MoveMemory(paramInt1, (NMTTDISPINFOA)paramNMTTDISPINFO, paramInt2);
  }

  public static final void MoveMemory(NMTTDISPINFO paramNMTTDISPINFO, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory((NMTTDISPINFOW)paramNMTTDISPINFO, paramInt1, paramInt2);
    else
      MoveMemory((NMTTDISPINFOA)paramNMTTDISPINFO, paramInt1, paramInt2);
  }

  public static final void MoveMemory(TEXTMETRIC paramTEXTMETRIC, int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      MoveMemory((TEXTMETRICW)paramTEXTMETRIC, paramInt1, paramInt2);
    else
      MoveMemory((TEXTMETRICA)paramTEXTMETRIC, paramInt1, paramInt2);
  }

  public static final boolean PeekMessage(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return PeekMessageW(paramMSG, paramInt1, paramInt2, paramInt3, paramInt4);
    return PeekMessageA(paramMSG, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final boolean PostMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return PostMessageW(paramInt1, paramInt2, paramInt3, paramInt4);
    return PostMessageA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final boolean PostThreadMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return PostThreadMessageW(paramInt1, paramInt2, paramInt3, paramInt4);
    return PostThreadMessageA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final boolean PrintDlg(PRINTDLG paramPRINTDLG)
  {
    if (IsUnicode)
      return PrintDlgW(paramPRINTDLG);
    return PrintDlgA(paramPRINTDLG);
  }

  public static final int RegEnumKeyEx(int paramInt1, int paramInt2, TCHAR paramTCHAR1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, TCHAR paramTCHAR2, int[] paramArrayOfInt3, FILETIME paramFILETIME)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return RegEnumKeyExW(paramInt1, paramInt2, (char[])localObject1, paramArrayOfInt1, paramArrayOfInt2, (char[])localObject2, paramArrayOfInt3, paramFILETIME);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return RegEnumKeyExA(paramInt1, paramInt2, (byte[])localObject1, paramArrayOfInt1, paramArrayOfInt2, (byte[])localObject2, paramArrayOfInt3, paramFILETIME);
  }

  public static final int RegisterClass(WNDCLASS paramWNDCLASS)
  {
    if (IsUnicode)
      return RegisterClassW(paramWNDCLASS);
    return RegisterClassA(paramWNDCLASS);
  }

  public static final int RegisterClipboardFormat(TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return RegisterClipboardFormatW((char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return RegisterClipboardFormatA((byte[])localObject);
  }

  public static final int RegisterWindowMessage(TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return RegisterWindowMessageW((char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return RegisterWindowMessageA((byte[])localObject);
  }

  public static final int RegOpenKeyEx(int paramInt1, TCHAR paramTCHAR, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return RegOpenKeyExW(paramInt1, (char[])localObject, paramInt2, paramInt3, paramArrayOfInt);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return RegOpenKeyExA(paramInt1, (byte[])localObject, paramInt2, paramInt3, paramArrayOfInt);
  }

  public static final int RegQueryInfoKey(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int paramInt3, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6, int[] paramArrayOfInt7, int[] paramArrayOfInt8, int paramInt4)
  {
    if (IsUnicode)
      return RegQueryInfoKeyW(paramInt1, paramInt2, paramArrayOfInt1, paramInt3, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4, paramArrayOfInt5, paramArrayOfInt6, paramArrayOfInt7, paramArrayOfInt8, paramInt4);
    return RegQueryInfoKeyA(paramInt1, paramInt2, paramArrayOfInt1, paramInt3, paramArrayOfInt2, paramArrayOfInt3, paramArrayOfInt4, paramArrayOfInt5, paramArrayOfInt6, paramArrayOfInt7, paramArrayOfInt8, paramInt4);
  }

  public static final int RegQueryValueEx(int paramInt1, TCHAR paramTCHAR1, int paramInt2, int[] paramArrayOfInt1, TCHAR paramTCHAR2, int[] paramArrayOfInt2)
  {
    if (IsUnicode)
    {
      localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.chars;
      localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.chars;
      return RegQueryValueExW(paramInt1, (char[])localObject1, paramInt2, paramArrayOfInt1, (char[])localObject2, paramArrayOfInt2);
    }
    Object localObject1 = paramTCHAR1 == null ? null : paramTCHAR1.bytes;
    Object localObject2 = paramTCHAR2 == null ? null : paramTCHAR2.bytes;
    return RegQueryValueExA(paramInt1, (byte[])localObject1, paramInt2, paramArrayOfInt1, (byte[])localObject2, paramArrayOfInt2);
  }

  public static final int RegQueryValueEx(int paramInt1, TCHAR paramTCHAR, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return RegQueryValueExW(paramInt1, (char[])localObject, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return RegQueryValueExA(paramInt1, (byte[])localObject, paramInt2, paramArrayOfInt1, paramArrayOfInt2, paramArrayOfInt3);
  }

  public static final int RemoveProp(int paramInt1, int paramInt2)
  {
    if (IsUnicode)
      return RemovePropW(paramInt1, paramInt2);
    return RemovePropA(paramInt1, paramInt2);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return SendMessageW(paramInt1, paramInt2, paramInt3, (char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return SendMessageA(paramInt1, paramInt2, paramInt3, (byte[])localObject);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
    return SendMessageA(paramInt1, paramInt2, paramArrayOfInt1, paramArrayOfInt2);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, SIZE paramSIZE)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramSIZE);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramSIZE);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
    return SendMessageA(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramArrayOfInt);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramArrayOfChar);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramArrayOfChar);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramArrayOfShort);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramArrayOfShort);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramInt4);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, LITEM paramLITEM)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramLITEM);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramLITEM);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, LVCOLUMN paramLVCOLUMN)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramLVCOLUMN);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramLVCOLUMN);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, LVHITTESTINFO paramLVHITTESTINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramLVHITTESTINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramLVHITTESTINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, LVITEM paramLVITEM)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramLVITEM);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramLVITEM);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, LVINSERTMARK paramLVINSERTMARK)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramLVINSERTMARK);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramLVINSERTMARK);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, MARGINS paramMARGINS)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramMARGINS);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramMARGINS);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramPOINT);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramPOINT);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, MCHITTESTINFO paramMCHITTESTINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramMCHITTESTINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramMCHITTESTINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, REBARBANDINFO paramREBARBANDINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramREBARBANDINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramREBARBANDINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramRECT);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramRECT);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, SYSTEMTIME paramSYSTEMTIME)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramSYSTEMTIME);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramSYSTEMTIME);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, SHDRAGIMAGE paramSHDRAGIMAGE)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramSHDRAGIMAGE);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramSHDRAGIMAGE);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TBBUTTON paramTBBUTTON)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTBBUTTON);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTBBUTTON);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TBBUTTONINFO paramTBBUTTONINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTBBUTTONINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTBBUTTONINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TCITEM paramTCITEM)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTCITEM);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTCITEM);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TCHITTESTINFO paramTCHITTESTINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTCHITTESTINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTCHITTESTINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TOOLINFO paramTOOLINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTOOLINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTOOLINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TVHITTESTINFO paramTVHITTESTINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTVHITTESTINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTVHITTESTINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TVINSERTSTRUCT paramTVINSERTSTRUCT)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTVINSERTSTRUCT);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTVINSERTSTRUCT);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TVITEM paramTVITEM)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTVITEM);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTVITEM);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, TVSORTCB paramTVSORTCB)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramTVSORTCB);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramTVSORTCB);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, UDACCEL paramUDACCEL)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramUDACCEL);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramUDACCEL);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, HDHITTESTINFO paramHDHITTESTINFO)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramHDHITTESTINFO);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramHDHITTESTINFO);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, HDITEM paramHDITEM)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramHDITEM);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramHDITEM);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, HDLAYOUT paramHDLAYOUT)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramHDLAYOUT);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramHDLAYOUT);
  }

  public static final int SendMessage(int paramInt1, int paramInt2, int paramInt3, BUTTON_IMAGELIST paramBUTTON_IMAGELIST)
  {
    if (IsUnicode)
      return SendMessageW(paramInt1, paramInt2, paramInt3, paramBUTTON_IMAGELIST);
    return SendMessageA(paramInt1, paramInt2, paramInt3, paramBUTTON_IMAGELIST);
  }

  public static final boolean SetMenuItemInfo(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO)
  {
    if (IsUnicode)
      return SetMenuItemInfoW(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
    return SetMenuItemInfoA(paramInt1, paramInt2, paramBoolean, paramMENUITEMINFO);
  }

  public static boolean SetProp(int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return SetPropW(paramInt1, paramInt2, paramInt3);
    return SetPropA(paramInt1, paramInt2, paramInt3);
  }

  public static final int SetWindowLong(int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return SetWindowLongW(paramInt1, paramInt2, paramInt3);
    return SetWindowLongA(paramInt1, paramInt2, paramInt3);
  }

  public static final int SetWindowLongPtr(int paramInt1, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
      return SetWindowLongPtrW(paramInt1, paramInt2, paramInt3);
    return SetWindowLongPtrA(paramInt1, paramInt2, paramInt3);
  }

  public static final int SetWindowsHookEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (IsUnicode)
      return SetWindowsHookExW(paramInt1, paramInt2, paramInt3, paramInt4);
    return SetWindowsHookExA(paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public static final boolean SetWindowText(int paramInt, TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return SetWindowTextW(paramInt, (char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return SetWindowTextA(paramInt, (byte[])localObject);
  }

  public static final int SHBrowseForFolder(BROWSEINFO paramBROWSEINFO)
  {
    if (IsUnicode)
      return SHBrowseForFolderW(paramBROWSEINFO);
    return SHBrowseForFolderA(paramBROWSEINFO);
  }

  public static final boolean ShellExecuteEx(SHELLEXECUTEINFO paramSHELLEXECUTEINFO)
  {
    if (IsUnicode)
      return ShellExecuteExW(paramSHELLEXECUTEINFO);
    return ShellExecuteExA(paramSHELLEXECUTEINFO);
  }

  public static int SHGetFileInfo(TCHAR paramTCHAR, int paramInt1, SHFILEINFO paramSHFILEINFO, int paramInt2, int paramInt3)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return SHGetFileInfoW((char[])localObject, paramInt1, (SHFILEINFOW)paramSHFILEINFO, paramInt2, paramInt3);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return SHGetFileInfoA((byte[])localObject, paramInt1, (SHFILEINFOA)paramSHFILEINFO, paramInt2, paramInt3);
  }

  public static final boolean Shell_NotifyIcon(int paramInt, NOTIFYICONDATA paramNOTIFYICONDATA)
  {
    if (IsUnicode)
      return Shell_NotifyIconW(paramInt, (NOTIFYICONDATAW)paramNOTIFYICONDATA);
    return Shell_NotifyIconA(paramInt, (NOTIFYICONDATAA)paramNOTIFYICONDATA);
  }

  public static final int SHGetFolderPath(int paramInt1, int paramInt2, int paramInt3, int paramInt4, TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return SHGetFolderPathW(paramInt1, paramInt2, paramInt3, paramInt4, (char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return SHGetFolderPathA(paramInt1, paramInt2, paramInt3, paramInt4, (byte[])localObject);
  }

  public static final boolean SHGetPathFromIDList(int paramInt, TCHAR paramTCHAR)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return SHGetPathFromIDListW(paramInt, (char[])localObject);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return SHGetPathFromIDListA(paramInt, (byte[])localObject);
  }

  public static final int StartDoc(int paramInt, DOCINFO paramDOCINFO)
  {
    if (IsUnicode)
      return StartDocW(paramInt, paramDOCINFO);
    return StartDocA(paramInt, paramDOCINFO);
  }

  public static final boolean SystemParametersInfo(int paramInt1, int paramInt2, RECT paramRECT, int paramInt3)
  {
    if (IsUnicode)
      return SystemParametersInfoW(paramInt1, paramInt2, paramRECT, paramInt3);
    return SystemParametersInfoA(paramInt1, paramInt2, paramRECT, paramInt3);
  }

  public static final boolean SystemParametersInfo(int paramInt1, int paramInt2, HIGHCONTRAST paramHIGHCONTRAST, int paramInt3)
  {
    if (IsUnicode)
      return SystemParametersInfoW(paramInt1, paramInt2, paramHIGHCONTRAST, paramInt3);
    return SystemParametersInfoA(paramInt1, paramInt2, paramHIGHCONTRAST, paramInt3);
  }

  public static final boolean SystemParametersInfo(int paramInt1, int paramInt2, NONCLIENTMETRICS paramNONCLIENTMETRICS, int paramInt3)
  {
    if (IsUnicode)
      return SystemParametersInfoW(paramInt1, paramInt2, (NONCLIENTMETRICSW)paramNONCLIENTMETRICS, paramInt3);
    return SystemParametersInfoA(paramInt1, paramInt2, (NONCLIENTMETRICSA)paramNONCLIENTMETRICS, paramInt3);
  }

  public static final boolean SystemParametersInfo(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3)
  {
    if (IsUnicode)
      return SystemParametersInfoW(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
    return SystemParametersInfoA(paramInt1, paramInt2, paramArrayOfInt, paramInt3);
  }

  public static final int TranslateAccelerator(int paramInt1, int paramInt2, MSG paramMSG)
  {
    if (IsUnicode)
      return TranslateAcceleratorW(paramInt1, paramInt2, paramMSG);
    return TranslateAcceleratorA(paramInt1, paramInt2, paramMSG);
  }

  public static final boolean UnregisterClass(TCHAR paramTCHAR, int paramInt)
  {
    if (IsUnicode)
    {
      localObject = paramTCHAR == null ? null : paramTCHAR.chars;
      return UnregisterClassW((char[])localObject, paramInt);
    }
    Object localObject = paramTCHAR == null ? null : paramTCHAR.bytes;
    return UnregisterClassA((byte[])localObject, paramInt);
  }

  public static final short VkKeyScan(short paramShort)
  {
    if (IsUnicode)
      return VkKeyScanW(paramShort);
    return VkKeyScanA(paramShort);
  }

  public static final native int AbortDoc(int paramInt);

  public static final native boolean ActivateActCtx(int paramInt, int[] paramArrayOfInt);

  public static final native int ActivateKeyboardLayout(int paramInt1, int paramInt2);

  public static final native int AddFontResourceExW(char[] paramArrayOfChar, int paramInt1, int paramInt2);

  public static final native int AddFontResourceExA(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static final native boolean AdjustWindowRectEx(RECT paramRECT, int paramInt1, boolean paramBoolean, int paramInt2);

  public static final native boolean AllowSetForegroundWindow(int paramInt);

  public static final native boolean AlphaBlend(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, BLENDFUNCTION paramBLENDFUNCTION);

  public static final native boolean AnimateWindow(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean Arc(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);

  public static final native int AssocQueryStringA(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int[] paramArrayOfInt);

  public static final native int AssocQueryStringW(int paramInt1, int paramInt2, char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt);

  public static final native boolean AttachThreadInput(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native int BeginBufferedPaint(int paramInt1, RECT paramRECT, int paramInt2, BP_PAINTPARAMS paramBP_PAINTPARAMS, int[] paramArrayOfInt);

  public static final native int BeginDeferWindowPos(int paramInt);

  public static final native int BeginPaint(int paramInt, PAINTSTRUCT paramPAINTSTRUCT);

  public static final native boolean BeginPath(int paramInt);

  public static final native boolean BitBlt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);

  public static final native boolean BringWindowToTop(int paramInt);

  public static final native int BufferedPaintInit();

  public static final native int BufferedPaintSetAlpha(int paramInt, RECT paramRECT, byte paramByte);

  public static final native int BufferedPaintUnInit();

  public static final native int Call(int paramInt, DLLVERSIONINFO paramDLLVERSIONINFO);

  public static final native int CallNextHookEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int CallWindowProcW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int CallWindowProcA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int CharLowerW(int paramInt);

  public static final native int CharLowerA(int paramInt);

  public static final native int CharUpperW(int paramInt);

  public static final native int CharUpperA(int paramInt);

  public static final native boolean CheckMenuItem(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ChooseColorW(CHOOSECOLOR paramCHOOSECOLOR);

  public static final native boolean ChooseColorA(CHOOSECOLOR paramCHOOSECOLOR);

  public static final native boolean ChooseFontW(CHOOSEFONT paramCHOOSEFONT);

  public static final native boolean ChooseFontA(CHOOSEFONT paramCHOOSEFONT);

  public static final native boolean ClientToScreen(int paramInt, POINT paramPOINT);

  public static final native boolean CloseClipboard();

  public static final native int CloseEnhMetaFile(int paramInt);

  public static final native boolean CloseHandle(int paramInt);

  public static final native int CloseThemeData(int paramInt);

  public static final native int CoCreateInstance(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int[] paramArrayOfInt);

  public static final native int CoInternetIsFeatureEnabled(int paramInt1, int paramInt2);

  public static final native int CoInternetSetFeatureEnabled(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native int CombineRgn(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean CommandBar_AddAdornments(int paramInt1, int paramInt2, int paramInt3);

  public static final native int CommandBar_Create(int paramInt1, int paramInt2, int paramInt3);

  public static final native void CommandBar_Destroy(int paramInt);

  public static final native boolean CommandBar_DrawMenuBar(int paramInt1, int paramInt2);

  public static final native int CommandBar_Height(int paramInt);

  public static final native boolean CommandBar_InsertMenubarEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean CommandBar_Show(int paramInt, boolean paramBoolean);

  public static final native int CommDlgExtendedError();

  public static final native int CopyImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int CoTaskMemAlloc(int paramInt);

  public static final native void CoTaskMemFree(int paramInt);

  public static final native int CreateAcceleratorTableW(byte[] paramArrayOfByte, int paramInt);

  public static final native int CreateAcceleratorTableA(byte[] paramArrayOfByte, int paramInt);

  public static final native int CreateActCtxW(ACTCTX paramACTCTX);

  public static final native int CreateActCtxA(ACTCTX paramACTCTX);

  public static final native int CreateBitmap(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  public static final native boolean CreateCaret(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int CreateCompatibleBitmap(int paramInt1, int paramInt2, int paramInt3);

  public static final native int CreateCompatibleDC(int paramInt);

  public static final native int CreateCursor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public static final native int CreateDCW(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt1, int paramInt2);

  public static final native int CreateDCA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2);

  public static final native int CreateDIBSection(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int[] paramArrayOfInt, int paramInt3, int paramInt4);

  public static final native int CreateDIBSection(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4, int paramInt5);

  public static final native int CreateEnhMetaFileW(int paramInt, char[] paramArrayOfChar1, RECT paramRECT, char[] paramArrayOfChar2);

  public static final native int CreateEnhMetaFileA(int paramInt, byte[] paramArrayOfByte1, RECT paramRECT, byte[] paramArrayOfByte2);

  public static final native int CreateFontIndirectW(int paramInt);

  public static final native int CreateFontIndirectA(int paramInt);

  public static final native int CreateFontIndirectW(LOGFONTW paramLOGFONTW);

  public static final native int CreateFontIndirectA(LOGFONTA paramLOGFONTA);

  public static final native int CreateIconIndirect(ICONINFO paramICONINFO);

  public static final native int CreateMenu();

  public static final native int CreatePalette(byte[] paramArrayOfByte);

  public static final native int CreatePatternBrush(int paramInt);

  public static final native int CreatePen(int paramInt1, int paramInt2, int paramInt3);

  public static final native int CreatePolygonRgn(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public static final native int CreatePopupMenu();

  public static final native boolean CreateProcessW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6, int paramInt7, STARTUPINFO paramSTARTUPINFO, PROCESS_INFORMATION paramPROCESS_INFORMATION);

  public static final native boolean CreateProcessA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5, int paramInt6, int paramInt7, STARTUPINFO paramSTARTUPINFO, PROCESS_INFORMATION paramPROCESS_INFORMATION);

  public static final native int CreateRectRgn(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int CreateSolidBrush(int paramInt);

  public static final native int CreateStreamOnHGlobal(int paramInt, boolean paramBoolean, int[] paramArrayOfInt);

  public static final native int CreateWindowExW(int paramInt1, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, CREATESTRUCT paramCREATESTRUCT);

  public static final native int CreateWindowExA(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, CREATESTRUCT paramCREATESTRUCT);

  public static final native int DeferWindowPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8);

  public static final native int DefMDIChildProcW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int DefMDIChildProcA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int DefFrameProcW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int DefFrameProcA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int DefWindowProcW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int DefWindowProcA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean DeleteDC(int paramInt);

  public static final native boolean DeleteEnhMetaFile(int paramInt);

  public static final native boolean DeleteMenu(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean DeleteObject(int paramInt);

  public static final native boolean DestroyAcceleratorTable(int paramInt);

  public static final native boolean DestroyCaret();

  public static final native boolean DestroyCursor(int paramInt);

  public static final native boolean DestroyIcon(int paramInt);

  public static final native boolean DestroyMenu(int paramInt);

  public static final native boolean DestroyWindow(int paramInt);

  public static final native int DispatchMessageW(MSG paramMSG);

  public static final native int DispatchMessageA(MSG paramMSG);

  public static final native boolean DPtoLP(int paramInt1, POINT paramPOINT, int paramInt2);

  public static final native boolean DragDetect(int paramInt, POINT paramPOINT);

  public static final native void DragFinish(int paramInt);

  public static final native int DragQueryFileA(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public static final native int DragQueryFileW(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public static final native boolean DrawAnimatedRects(int paramInt1, int paramInt2, RECT paramRECT1, RECT paramRECT2);

  public static final native boolean DrawEdge(int paramInt1, RECT paramRECT, int paramInt2, int paramInt3);

  public static final native boolean DrawFocusRect(int paramInt, RECT paramRECT);

  public static final native boolean DrawFrameControl(int paramInt1, RECT paramRECT, int paramInt2, int paramInt3);

  public static final native boolean DrawIconEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);

  public static final native boolean DrawMenuBar(int paramInt);

  public static final native boolean DrawStateW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  public static final native boolean DrawStateA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  public static final native int DrawTextW(int paramInt1, char[] paramArrayOfChar, int paramInt2, RECT paramRECT, int paramInt3);

  public static final native int DrawTextA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, RECT paramRECT, int paramInt3);

  public static final native int DrawThemeBackground(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT1, RECT paramRECT2);

  public static final native int DrawThemeEdge(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT1, int paramInt5, int paramInt6, RECT paramRECT2);

  public static final native int DrawThemeIcon(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, int paramInt5, int paramInt6);

  public static final native int DrawThemeParentBackground(int paramInt1, int paramInt2, RECT paramRECT);

  public static final native int DrawThemeText(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int paramInt5, int paramInt6, int paramInt7, RECT paramRECT);

  public static final native int DwmEnableBlurBehindWindow(int paramInt, DWM_BLURBEHIND paramDWM_BLURBEHIND);

  public static final native int DwmExtendFrameIntoClientArea(int paramInt, MARGINS paramMARGINS);

  public static final native boolean Ellipse(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native boolean EnableMenuItem(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean EnableScrollBar(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean EnableWindow(int paramInt, boolean paramBoolean);

  public static final native boolean EnumSystemLanguageGroupsW(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean EnumSystemLanguageGroupsA(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean EnumSystemLocalesW(int paramInt1, int paramInt2);

  public static final native boolean EnumSystemLocalesA(int paramInt1, int paramInt2);

  public static final native boolean EndDeferWindowPos(int paramInt);

  public static final native int EndBufferedPaint(int paramInt, boolean paramBoolean);

  public static final native int EndDoc(int paramInt);

  public static final native int EndPage(int paramInt);

  public static final native int EndPaint(int paramInt, PAINTSTRUCT paramPAINTSTRUCT);

  public static final native boolean EndPath(int paramInt);

  public static final native boolean EnumDisplayMonitors(int paramInt1, RECT paramRECT, int paramInt2, int paramInt3);

  public static final native boolean EnumEnhMetaFile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT);

  public static final native int EnumFontFamiliesW(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3);

  public static final native int EnumFontFamiliesA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);

  public static final native int EnumFontFamiliesExW(int paramInt1, LOGFONTW paramLOGFONTW, int paramInt2, int paramInt3, int paramInt4);

  public static final native int EnumFontFamiliesExA(int paramInt1, LOGFONTA paramLOGFONTA, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean EqualRect(RECT paramRECT1, RECT paramRECT2);

  public static final native boolean EqualRgn(int paramInt1, int paramInt2);

  public static final native int ExcludeClipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int ExpandEnvironmentStringsW(char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt);

  public static final native int ExpandEnvironmentStringsA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt);

  public static final native int ExtCreatePen(int paramInt1, int paramInt2, LOGBRUSH paramLOGBRUSH, int paramInt3, int[] paramArrayOfInt);

  public static final native int ExtCreateRegion(float[] paramArrayOfFloat, int paramInt, int[] paramArrayOfInt);

  public static final native boolean ExtTextOutW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, char[] paramArrayOfChar, int paramInt5, int[] paramArrayOfInt);

  public static final native boolean ExtTextOutA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, byte[] paramArrayOfByte, int paramInt5, int[] paramArrayOfInt);

  public static final native int ExtractIconExW(char[] paramArrayOfChar, int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt2);

  public static final native int ExtractIconExA(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt2);

  public static final native int FillRect(int paramInt1, RECT paramRECT, int paramInt2);

  public static final native boolean FillPath(int paramInt);

  public static final native int FindWindowA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public static final native int FindWindowW(char[] paramArrayOfChar1, char[] paramArrayOfChar2);

  public static final native int FormatMessageA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6);

  public static final native int FormatMessageW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt, int paramInt5, int paramInt6);

  public static final native boolean FreeLibrary(int paramInt);

  public static final native int GdiSetBatchLimit(int paramInt);

  public static final native int GET_WHEEL_DELTA_WPARAM(int paramInt);

  public static final native int GET_X_LPARAM(int paramInt);

  public static final native int GET_Y_LPARAM(int paramInt);

  public static final native int GetACP();

  public static final native short GetAsyncKeyState(int paramInt);

  public static final native int GetActiveWindow();

  public static final native int GetBkColor(int paramInt);

  public static final native int GetCapture();

  public static final native boolean GetCaretPos(POINT paramPOINT);

  public static final native boolean GetCharABCWidthsA(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native boolean GetCharABCWidthsW(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int GetCharacterPlacementW(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, GCP_RESULTS paramGCP_RESULTS, int paramInt4);

  public static final native int GetCharacterPlacementA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, GCP_RESULTS paramGCP_RESULTS, int paramInt4);

  public static final native boolean GetCharWidthA(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native boolean GetCharWidthW(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native boolean GetClassInfoW(int paramInt, char[] paramArrayOfChar, WNDCLASS paramWNDCLASS);

  public static final native boolean GetClassInfoA(int paramInt, byte[] paramArrayOfByte, WNDCLASS paramWNDCLASS);

  public static final native int GetClassNameW(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native int GetClassNameA(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native boolean GetClientRect(int paramInt, RECT paramRECT);

  public static final native int GetClipboardData(int paramInt);

  public static final native int GetClipboardFormatNameA(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GetClipboardFormatNameW(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native int GetClipBox(int paramInt, RECT paramRECT);

  public static final native int GetClipRgn(int paramInt1, int paramInt2);

  public static final native boolean GetComboBoxInfo(int paramInt, COMBOBOXINFO paramCOMBOBOXINFO);

  public static final native int GetCurrentObject(int paramInt1, int paramInt2);

  public static final native int GetCurrentProcessId();

  public static final native int GetCurrentThreadId();

  public static final native int GetCursor();

  public static final native boolean GetCursorPos(POINT paramPOINT);

  public static final native int GetDateFormatW(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3);

  public static final native int GetDateFormatA(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3);

  public static final native int GetDC(int paramInt);

  public static final native int GetDCEx(int paramInt1, int paramInt2, int paramInt3);

  public static final native int GetDesktopWindow();

  public static final native int GetDeviceCaps(int paramInt1, int paramInt2);

  public static final native int GetDialogBaseUnits();

  public static final native int GetDIBColorTable(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int GetDIBits(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte, int paramInt6);

  public static final native int GetDlgItem(int paramInt1, int paramInt2);

  public static final native int GetDoubleClickTime();

  public static final native int GetFocus();

  public static final native int GetFontLanguageInfo(int paramInt);

  public static final native int GetForegroundWindow();

  public static final native int GetGraphicsMode(int paramInt);

  public static final native int GetGlyphIndicesW(int paramInt1, char[] paramArrayOfChar, int paramInt2, short[] paramArrayOfShort, int paramInt3);

  public static final native boolean GetGUIThreadInfo(int paramInt, GUITHREADINFO paramGUITHREADINFO);

  public static final native boolean GetIconInfo(int paramInt, ICONINFO paramICONINFO);

  public static final native int GetKeyboardLayoutList(int paramInt, int[] paramArrayOfInt);

  public static final native int GetKeyboardLayout(int paramInt);

  public static final native short GetKeyState(int paramInt);

  public static final native boolean GetKeyboardState(byte[] paramArrayOfByte);

  public static final native int GetKeyNameTextW(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native int GetKeyNameTextA(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GetLastActivePopup(int paramInt);

  public static final native int GetLastError();

  public static final native boolean GetLayeredWindowAttributes(int paramInt, int[] paramArrayOfInt1, byte[] paramArrayOfByte, int[] paramArrayOfInt2);

  public static final native int GetLayout(int paramInt);

  public static final native int GetLibraryHandle();

  public static final native int GetLocaleInfoW(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public static final native int GetLocaleInfoA(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public static final native int GetMenu(int paramInt);

  public static final native boolean GetMenuBarInfo(int paramInt1, int paramInt2, int paramInt3, MENUBARINFO paramMENUBARINFO);

  public static final native int GetMenuDefaultItem(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean GetMenuInfo(int paramInt, MENUINFO paramMENUINFO);

  public static final native int GetMenuItemCount(int paramInt);

  public static final native boolean GetMenuItemInfoW(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native boolean GetMenuItemInfoA(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native boolean GetMenuItemRect(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT);

  public static final native boolean GetMessageW(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean GetMessageA(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3);

  public static final native int GetMessagePos();

  public static final native int GetMessageTime();

  public static final native int GetMetaRgn(int paramInt1, int paramInt2);

  public static final native int GetThemeColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  public static final native int GetThemeTextExtent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int paramInt5, int paramInt6, RECT paramRECT1, RECT paramRECT2);

  public static final native int GetTextCharset(int paramInt);

  public static final native int GetTickCount();

  public static final native int GetMapMode(int paramInt);

  public static final native int GetModuleFileNameW(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native int GetModuleFileNameA(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GetModuleHandleW(char[] paramArrayOfChar);

  public static final native int GetModuleHandleA(byte[] paramArrayOfByte);

  public static final native boolean GetMonitorInfoW(int paramInt, MONITORINFO paramMONITORINFO);

  public static final native boolean GetMonitorInfoA(int paramInt, MONITORINFO paramMONITORINFO);

  public static final native int GetNearestPaletteIndex(int paramInt1, int paramInt2);

  public static final native int GetObjectA(int paramInt1, int paramInt2, BITMAP paramBITMAP);

  public static final native int GetObjectW(int paramInt1, int paramInt2, BITMAP paramBITMAP);

  public static final native int GetObjectA(int paramInt1, int paramInt2, DIBSECTION paramDIBSECTION);

  public static final native int GetObjectW(int paramInt1, int paramInt2, DIBSECTION paramDIBSECTION);

  public static final native int GetObjectA(int paramInt1, int paramInt2, EXTLOGPEN paramEXTLOGPEN);

  public static final native int GetObjectW(int paramInt1, int paramInt2, EXTLOGPEN paramEXTLOGPEN);

  public static final native int GetObjectA(int paramInt1, int paramInt2, LOGBRUSH paramLOGBRUSH);

  public static final native int GetObjectW(int paramInt1, int paramInt2, LOGBRUSH paramLOGBRUSH);

  public static final native int GetObjectA(int paramInt1, int paramInt2, LOGFONTA paramLOGFONTA);

  public static final native int GetObjectW(int paramInt1, int paramInt2, LOGFONTW paramLOGFONTW);

  public static final native int GetObjectA(int paramInt1, int paramInt2, LOGPEN paramLOGPEN);

  public static final native int GetObjectW(int paramInt1, int paramInt2, LOGPEN paramLOGPEN);

  public static final native int GetObjectA(int paramInt1, int paramInt2, int paramInt3);

  public static final native int GetObjectW(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean GetOpenFileNameW(OPENFILENAME paramOPENFILENAME);

  public static final native boolean GetOpenFileNameA(OPENFILENAME paramOPENFILENAME);

  public static final native int GetOutlineTextMetricsW(int paramInt1, int paramInt2, OUTLINETEXTMETRICW paramOUTLINETEXTMETRICW);

  public static final native int GetOutlineTextMetricsA(int paramInt1, int paramInt2, OUTLINETEXTMETRICA paramOUTLINETEXTMETRICA);

  public static final native int GetPath(int paramInt1, int[] paramArrayOfInt, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GetPaletteEntries(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int GetParent(int paramInt);

  public static final native int GetPixel(int paramInt1, int paramInt2, int paramInt3);

  public static final native int GetPolyFillMode(int paramInt);

  public static final native int GetProcAddress(int paramInt, byte[] paramArrayOfByte);

  public static final native int GetProcessHeap();

  public static final native int GetProcessHeaps(int paramInt, int[] paramArrayOfInt);

  public static final native int GetProfileStringW(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, char[] paramArrayOfChar4, int paramInt);

  public static final native int GetProfileStringA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, int paramInt);

  public static final native int GetPropW(int paramInt1, int paramInt2);

  public static final native int GetPropA(int paramInt1, int paramInt2);

  public static final native int GetRandomRgn(int paramInt1, int paramInt2, int paramInt3);

  public static final native int GetRegionData(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native int GetRgnBox(int paramInt, RECT paramRECT);

  public static final native int GetROP2(int paramInt);

  public static final native boolean GetSaveFileNameW(OPENFILENAME paramOPENFILENAME);

  public static final native boolean GetSaveFileNameA(OPENFILENAME paramOPENFILENAME);

  public static final native boolean GetScrollBarInfo(int paramInt1, int paramInt2, SCROLLBARINFO paramSCROLLBARINFO);

  public static final native boolean GetScrollInfo(int paramInt1, int paramInt2, SCROLLINFO paramSCROLLINFO);

  public static final native void GetStartupInfoW(STARTUPINFO paramSTARTUPINFO);

  public static final native void GetStartupInfoA(STARTUPINFO paramSTARTUPINFO);

  public static final native int GetStockObject(int paramInt);

  public static final native int GetSysColor(int paramInt);

  public static final native int GetSysColorBrush(int paramInt);

  public static final native short GetSystemDefaultUILanguage();

  public static final native int GetSystemMenu(int paramInt, boolean paramBoolean);

  public static final native int GetSystemMetrics(int paramInt);

  public static final native int GetSystemPaletteEntries(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int GetTextColor(int paramInt);

  public static final native boolean GetTextExtentPoint32W(int paramInt1, char[] paramArrayOfChar, int paramInt2, SIZE paramSIZE);

  public static final native boolean GetTextExtentPoint32A(int paramInt1, byte[] paramArrayOfByte, int paramInt2, SIZE paramSIZE);

  public static final native boolean GetTextMetricsW(int paramInt, TEXTMETRICW paramTEXTMETRICW);

  public static final native boolean GetTextMetricsA(int paramInt, TEXTMETRICA paramTEXTMETRICA);

  public static final native int GetThemeInt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt);

  public static final native int GetThemeMargins(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, RECT paramRECT, MARGINS paramMARGINS);

  public static final native int GetThemeBackgroundContentRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT1, RECT paramRECT2);

  public static final native int GetThemeBackgroundExtent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT1, RECT paramRECT2);

  public static final native int GetThemePartSize(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, int paramInt5, SIZE paramSIZE);

  public static final native int GetThemeMetric(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static final native int GetThemeRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT);

  public static final native int GetThemeSysSize(int paramInt1, int paramInt2);

  public static final native int GetTimeFormatW(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt3);

  public static final native int GetTimeFormatA(int paramInt1, int paramInt2, SYSTEMTIME paramSYSTEMTIME, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt3);

  public static final native boolean GetUpdateRect(int paramInt, RECT paramRECT, boolean paramBoolean);

  public static final native int GetUpdateRgn(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native boolean GetVersionExW(OSVERSIONINFOEXW paramOSVERSIONINFOEXW);

  public static final native boolean GetVersionExA(OSVERSIONINFOEXA paramOSVERSIONINFOEXA);

  public static final native boolean GetVersionExW(OSVERSIONINFOW paramOSVERSIONINFOW);

  public static final native boolean GetVersionExA(OSVERSIONINFOA paramOSVERSIONINFOA);

  public static final native int GetWindow(int paramInt1, int paramInt2);

  public static final native int GetWindowLongW(int paramInt1, int paramInt2);

  public static final native int GetWindowLongA(int paramInt1, int paramInt2);

  public static final native int GetWindowLongPtrW(int paramInt1, int paramInt2);

  public static final native int GetWindowLongPtrA(int paramInt1, int paramInt2);

  public static final native int GetWindowDC(int paramInt);

  public static final native boolean GetWindowOrgEx(int paramInt, POINT paramPOINT);

  public static final native boolean GetWindowPlacement(int paramInt, WINDOWPLACEMENT paramWINDOWPLACEMENT);

  public static final native boolean GetWindowRect(int paramInt, RECT paramRECT);

  public static final native int GetWindowRgn(int paramInt1, int paramInt2);

  public static final native int GetWindowTextW(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native int GetWindowTextA(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native int GetWindowTextLengthW(int paramInt);

  public static final native int GetWindowTextLengthA(int paramInt);

  public static final native int GetWindowTheme(int paramInt);

  public static final native int GetWindowThreadProcessId(int paramInt, int[] paramArrayOfInt);

  public static final native boolean GetWorldTransform(int paramInt, float[] paramArrayOfFloat);

  public static final native int GlobalAddAtomW(char[] paramArrayOfChar);

  public static final native int GlobalAddAtomA(byte[] paramArrayOfByte);

  public static final native int GlobalAlloc(int paramInt1, int paramInt2);

  public static final native int GlobalFree(int paramInt);

  public static final native int GlobalLock(int paramInt);

  public static final native int GlobalSize(int paramInt);

  public static final native boolean GlobalUnlock(int paramInt);

  public static final native boolean GradientFill(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int HIWORD(int paramInt);

  public static final native int HeapAlloc(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean HeapFree(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean HeapValidate(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean HideCaret(int paramInt);

  public static final native int HitTestThemeBackground(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, RECT paramRECT, int paramInt6, POINT paramPOINT, short[] paramArrayOfShort);

  public static final native int IIDFromString(char[] paramArrayOfChar, byte[] paramArrayOfByte);

  public static final native int ImageList_Add(int paramInt1, int paramInt2, int paramInt3);

  public static final native int ImageList_AddMasked(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ImageList_BeginDrag(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int ImageList_Create(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native boolean ImageList_Destroy(int paramInt);

  public static final native boolean ImageList_DragEnter(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ImageList_DragLeave(int paramInt);

  public static final native boolean ImageList_DragMove(int paramInt1, int paramInt2);

  public static final native boolean ImageList_DragShowNolock(boolean paramBoolean);

  public static final native boolean ImageList_Draw(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native void ImageList_EndDrag();

  public static final native int ImageList_GetDragImage(POINT paramPOINT1, POINT paramPOINT2);

  public static final native int ImageList_GetIcon(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ImageList_GetIconSize(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int ImageList_GetImageCount(int paramInt);

  public static final native boolean ImageList_Remove(int paramInt1, int paramInt2);

  public static final native boolean ImageList_Replace(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int ImageList_ReplaceIcon(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ImageList_SetIconSize(int paramInt1, int paramInt2, int paramInt3);

  public static final native int ImmAssociateContext(int paramInt1, int paramInt2);

  public static final native int ImmCreateContext();

  public static final native boolean ImmDestroyContext(int paramInt);

  public static final native boolean ImmDisableTextFrameService(int paramInt);

  public static final native boolean ImmGetCompositionFontW(int paramInt, LOGFONTW paramLOGFONTW);

  public static final native boolean ImmGetCompositionFontA(int paramInt, LOGFONTA paramLOGFONTA);

  public static final native int ImmGetCompositionStringW(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public static final native int ImmGetCompositionStringA(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public static final native int ImmGetCompositionStringW(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int ImmGetCompositionStringA(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int ImmGetCompositionStringW(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public static final native int ImmGetContext(int paramInt);

  public static final native boolean ImmGetConversionStatus(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int ImmGetDefaultIMEWnd(int paramInt);

  public static final native boolean ImmGetOpenStatus(int paramInt);

  public static final native boolean ImmNotifyIME(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean ImmReleaseContext(int paramInt1, int paramInt2);

  public static final native boolean ImmSetCompositionFontW(int paramInt, LOGFONTW paramLOGFONTW);

  public static final native boolean ImmSetCompositionFontA(int paramInt, LOGFONTA paramLOGFONTA);

  public static final native boolean ImmSetCompositionWindow(int paramInt, COMPOSITIONFORM paramCOMPOSITIONFORM);

  public static final native boolean ImmSetCandidateWindow(int paramInt, CANDIDATEFORM paramCANDIDATEFORM);

  public static final native boolean ImmSetConversionStatus(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean ImmSetOpenStatus(int paramInt, boolean paramBoolean);

  public static final native void InitCommonControls();

  public static final native boolean InitCommonControlsEx(INITCOMMONCONTROLSEX paramINITCOMMONCONTROLSEX);

  public static final native boolean InSendMessage();

  public static final native boolean InsertMenuW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar);

  public static final native boolean InsertMenuA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  public static final native boolean InsertMenuItemW(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native boolean InsertMenuItemA(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native boolean InternetGetCookieA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, int[] paramArrayOfInt);

  public static final native boolean InternetGetCookieW(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3, int[] paramArrayOfInt);

  public static final native boolean InternetSetCookieA(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);

  public static final native boolean InternetSetCookieW(char[] paramArrayOfChar1, char[] paramArrayOfChar2, char[] paramArrayOfChar3);

  public static final native boolean InternetSetOption(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int IntersectClipRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native boolean IntersectRect(RECT paramRECT1, RECT paramRECT2, RECT paramRECT3);

  public static final native boolean InvalidateRect(int paramInt, RECT paramRECT, boolean paramBoolean);

  public static final native boolean InvalidateRgn(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native boolean IsAppThemed();

  public static final native boolean IsBadReadPtr(int paramInt1, int paramInt2);

  public static final native boolean IsBadWritePtr(int paramInt1, int paramInt2);

  public static final native boolean IsDBCSLeadByte(byte paramByte);

  public static final native boolean IsHungAppWindow(int paramInt);

  public static final native boolean IsIconic(int paramInt);

  public static final native boolean IsPPC();

  public static final native boolean IsSP();

  public static final native boolean IsWindowEnabled(int paramInt);

  public static final native boolean IsWindowVisible(int paramInt);

  public static final native boolean IsZoomed(int paramInt);

  public static final native boolean KillTimer(int paramInt1, int paramInt2);

  public static final native boolean LineTo(int paramInt1, int paramInt2, int paramInt3);

  public static final native int LoadBitmapW(int paramInt1, int paramInt2);

  public static final native int LoadBitmapA(int paramInt1, int paramInt2);

  public static final native int LoadCursorW(int paramInt1, int paramInt2);

  public static final native int LoadCursorA(int paramInt1, int paramInt2);

  public static final native int LoadIconW(int paramInt1, int paramInt2);

  public static final native int LoadIconA(int paramInt1, int paramInt2);

  public static final native int LoadImageW(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int LoadImageA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int LoadImageW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int LoadImageA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int LoadStringW(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public static final native int LoadStringA(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public static final native int LoadLibraryW(char[] paramArrayOfChar);

  public static final native int LoadLibraryA(byte[] paramArrayOfByte);

  public static final native int LocalFree(int paramInt);

  public static final native boolean LockWindowUpdate(int paramInt);

  public static final native int LOWORD(int paramInt);

  public static final native boolean LPtoDP(int paramInt1, POINT paramPOINT, int paramInt2);

  public static final native int MAKEWORD(int paramInt1, int paramInt2);

  public static final native int MAKEWPARAM(int paramInt1, int paramInt2);

  public static final native int MAKELPARAM(int paramInt1, int paramInt2);

  public static final native int MAKELRESULT(int paramInt1, int paramInt2);

  public static final native int MapVirtualKeyW(int paramInt1, int paramInt2);

  public static final native int MapVirtualKeyA(int paramInt1, int paramInt2);

  public static final native int MapWindowPoints(int paramInt1, int paramInt2, POINT paramPOINT, int paramInt3);

  public static final native int MapWindowPoints(int paramInt1, int paramInt2, RECT paramRECT, int paramInt3);

  public static final native boolean MCIWndRegisterClass();

  public static final native boolean MessageBeep(int paramInt);

  public static final native int MessageBoxW(int paramInt1, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt2);

  public static final native int MessageBoxA(int paramInt1, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt2);

  public static final native boolean ModifyWorldTransform(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native int MonitorFromWindow(int paramInt1, int paramInt2);

  public static final native void MoveMemory(char[] paramArrayOfChar, int paramInt1, int paramInt2);

  public static final native void MoveMemory(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public static final native void MoveMemory(byte[] paramArrayOfByte, ACCEL paramACCEL, int paramInt);

  public static final native void MoveMemory(byte[] paramArrayOfByte, BITMAPINFOHEADER paramBITMAPINFOHEADER, int paramInt);

  public static final native void MoveMemory(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public static final native void MoveMemory(long[] paramArrayOfLong, int paramInt1, int paramInt2);

  public static final native void MoveMemory(double[] paramArrayOfDouble, int paramInt1, int paramInt2);

  public static final native void MoveMemory(float[] paramArrayOfFloat, int paramInt1, int paramInt2);

  public static final native void MoveMemory(short[] paramArrayOfShort, int paramInt1, int paramInt2);

  public static final native void MoveMemory(int paramInt1, byte[] paramArrayOfByte, int paramInt2);

  public static final native void MoveMemory(int paramInt1, char[] paramArrayOfChar, int paramInt2);

  public static final native void MoveMemory(int paramInt1, int[] paramArrayOfInt, int paramInt2);

  public static final native void MoveMemory(int paramInt1, int paramInt2, int paramInt3);

  public static final native void MoveMemory(int paramInt1, DEVMODEW paramDEVMODEW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, DEVMODEA paramDEVMODEA, int paramInt2);

  public static final native void MoveMemory(int paramInt1, DOCHOSTUIINFO paramDOCHOSTUIINFO, int paramInt2);

  public static final native void MoveMemory(int paramInt1, GRADIENT_RECT paramGRADIENT_RECT, int paramInt2);

  public static final native void MoveMemory(int paramInt1, LOGFONTW paramLOGFONTW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, LOGFONTA paramLOGFONTA, int paramInt2);

  public static final native void MoveMemory(int paramInt1, MEASUREITEMSTRUCT paramMEASUREITEMSTRUCT, int paramInt2);

  public static final native void MoveMemory(int paramInt1, MINMAXINFO paramMINMAXINFO, int paramInt2);

  public static final native void MoveMemory(int paramInt1, MSG paramMSG, int paramInt2);

  public static final native void MoveMemory(int paramInt1, UDACCEL paramUDACCEL, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMTTDISPINFOW paramNMTTDISPINFOW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMTTDISPINFOA paramNMTTDISPINFOA, int paramInt2);

  public static final native void MoveMemory(int paramInt1, OPENFILENAME paramOPENFILENAME, int paramInt2);

  public static final native void MoveMemory(int paramInt1, RECT paramRECT, int paramInt2);

  public static final native void MoveMemory(int paramInt1, SAFEARRAY paramSAFEARRAY, int paramInt2);

  public static final native void MoveMemory(int paramInt1, TRIVERTEX paramTRIVERTEX, int paramInt2);

  public static final native void MoveMemory(int paramInt1, WINDOWPOS paramWINDOWPOS, int paramInt2);

  public static final native void MoveMemory(BITMAPINFOHEADER paramBITMAPINFOHEADER, byte[] paramArrayOfByte, int paramInt);

  public static final native void MoveMemory(BITMAPINFOHEADER paramBITMAPINFOHEADER, int paramInt1, int paramInt2);

  public static final native void MoveMemory(DEVMODEW paramDEVMODEW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(DEVMODEA paramDEVMODEA, int paramInt1, int paramInt2);

  public static final native void MoveMemory(DOCHOSTUIINFO paramDOCHOSTUIINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(DRAWITEMSTRUCT paramDRAWITEMSTRUCT, int paramInt1, int paramInt2);

  public static final native void MoveMemory(EXTLOGPEN paramEXTLOGPEN, int paramInt1, int paramInt2);

  public static final native void MoveMemory(HDITEM paramHDITEM, int paramInt1, int paramInt2);

  public static final native void MoveMemory(HELPINFO paramHELPINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(LOGFONTW paramLOGFONTW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(LOGFONTA paramLOGFONTA, int paramInt1, int paramInt2);

  public static final native void MoveMemory(MEASUREITEMSTRUCT paramMEASUREITEMSTRUCT, int paramInt1, int paramInt2);

  public static final native void MoveMemory(MINMAXINFO paramMINMAXINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(OFNOTIFY paramOFNOTIFY, int paramInt1, int paramInt2);

  public static final native void MoveMemory(OPENFILENAME paramOPENFILENAME, int paramInt1, int paramInt2);

  public static final native void MoveMemory(POINT paramPOINT, int paramInt1, int paramInt2);

  public static final native void MoveMemory(POINT paramPOINT, long[] paramArrayOfLong, int paramInt);

  public static final native void MoveMemory(NMHDR paramNMHDR, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMRGINFO paramNMRGINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMCUSTOMDRAW paramNMCUSTOMDRAW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTBHOTITEM paramNMTBHOTITEM, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTREEVIEW paramNMTREEVIEW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTVITEMCHANGE paramNMTVITEMCHANGE, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMUPDOWN paramNMUPDOWN, int paramInt1, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMLVCUSTOMDRAW paramNMLVCUSTOMDRAW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMTVCUSTOMDRAW paramNMTVCUSTOMDRAW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMLVDISPINFO paramNMLVDISPINFO, int paramInt2);

  public static final native void MoveMemory(int paramInt1, NMTVDISPINFO paramNMTVDISPINFO, int paramInt2);

  public static final native void MoveMemory(NMLVDISPINFO paramNMLVDISPINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTVDISPINFO paramNMTVDISPINFO, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMLVFINDITEM paramNMLVFINDITEM, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMLVODSTATECHANGE paramNMLVODSTATECHANGE, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMHEADER paramNMHEADER, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMLINK paramNMLINK, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMLISTVIEW paramNMLISTVIEW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMREBARCHILDSIZE paramNMREBARCHILDSIZE, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMREBARCHEVRON paramNMREBARCHEVRON, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTOOLBAR paramNMTOOLBAR, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTTCUSTOMDRAW paramNMTTCUSTOMDRAW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTTDISPINFOW paramNMTTDISPINFOW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(NMTTDISPINFOA paramNMTTDISPINFOA, int paramInt1, int paramInt2);

  public static final native void MoveMemory(RECT paramRECT, int[] paramArrayOfInt, int paramInt);

  public static final native void MoveMemory(SHDRAGIMAGE paramSHDRAGIMAGE, int paramInt1, int paramInt2);

  public static final native void MoveMemory(EMR paramEMR, int paramInt1, int paramInt2);

  public static final native void MoveMemory(EMREXTCREATEFONTINDIRECTW paramEMREXTCREATEFONTINDIRECTW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(int paramInt1, SHDRAGIMAGE paramSHDRAGIMAGE, int paramInt2);

  public static final native void MoveMemory(TEXTMETRICW paramTEXTMETRICW, int paramInt1, int paramInt2);

  public static final native void MoveMemory(TEXTMETRICA paramTEXTMETRICA, int paramInt1, int paramInt2);

  public static final native void MoveMemory(TVITEM paramTVITEM, int paramInt1, int paramInt2);

  public static final native void MoveMemory(WINDOWPOS paramWINDOWPOS, int paramInt1, int paramInt2);

  public static final native void MoveMemory(MSG paramMSG, int paramInt1, int paramInt2);

  public static final native void MoveMemory(UDACCEL paramUDACCEL, int paramInt1, int paramInt2);

  public static final native void MoveMemory(int paramInt1, DROPFILES paramDROPFILES, int paramInt2);

  public static final native void MoveMemory(int paramInt1, double[] paramArrayOfDouble, int paramInt2);

  public static final native void MoveMemory(int paramInt1, float[] paramArrayOfFloat, int paramInt2);

  public static final native void MoveMemory(int paramInt1, long[] paramArrayOfLong, int paramInt2);

  public static final native void MoveMemory(int paramInt1, short[] paramArrayOfShort, int paramInt2);

  public static final native void MoveMemory(SCRIPT_ITEM paramSCRIPT_ITEM, int paramInt1, int paramInt2);

  public static final native void MoveMemory(SCRIPT_LOGATTR paramSCRIPT_LOGATTR, int paramInt1, int paramInt2);

  public static final native void MoveMemory(SCRIPT_PROPERTIES paramSCRIPT_PROPERTIES, int paramInt1, int paramInt2);

  public static final native void MoveMemory(int paramInt1, KEYBDINPUT paramKEYBDINPUT, int paramInt2);

  public static final native void MoveMemory(int paramInt1, MOUSEINPUT paramMOUSEINPUT, int paramInt2);

  public static final native boolean MoveToEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int MsgWaitForMultipleObjectsEx(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int MultiByteToWideChar(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3, char[] paramArrayOfChar, int paramInt4);

  public static final native int MultiByteToWideChar(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar, int paramInt5);

  public static final native void NotifyWinEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean OffsetRect(RECT paramRECT, int paramInt1, int paramInt2);

  public static final native int OffsetRgn(int paramInt1, int paramInt2, int paramInt3);

  public static final native int OleInitialize(int paramInt);

  public static final native void OleUninitialize();

  public static final native boolean OpenClipboard(int paramInt);

  public static final native int OpenThemeData(int paramInt, char[] paramArrayOfChar);

  public static final native boolean PatBlt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native boolean PathIsExe(int paramInt);

  public static final native boolean PeekMessageW(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean PeekMessageA(MSG paramMSG, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean Pie(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9);

  public static final native void POINTSTOPOINT(POINT paramPOINT, int paramInt);

  public static final native boolean Polygon(int paramInt1, int[] paramArrayOfInt, int paramInt2);

  public static final native boolean Polyline(int paramInt1, int[] paramArrayOfInt, int paramInt2);

  public static final native boolean PostMessageW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean PostMessageA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean PostThreadMessageW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean PostThreadMessageA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native short PRIMARYLANGID(int paramInt);

  public static final native boolean PrintDlgW(PRINTDLG paramPRINTDLG);

  public static final native boolean PrintDlgA(PRINTDLG paramPRINTDLG);

  public static final native boolean PrintWindow(int paramInt1, int paramInt2, int paramInt3);

  public static final native int PSPropertyKeyFromString(char[] paramArrayOfChar, PROPERTYKEY paramPROPERTYKEY);

  public static final native boolean PtInRect(RECT paramRECT, POINT paramPOINT);

  public static final native boolean PtInRegion(int paramInt1, int paramInt2, int paramInt3);

  public static final native int RealizePalette(int paramInt);

  public static final native boolean Rectangle(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native boolean RectInRegion(int paramInt, RECT paramRECT);

  public static final native boolean RedrawWindow(int paramInt1, RECT paramRECT, int paramInt2, int paramInt3);

  public static final native int RegCloseKey(int paramInt);

  public static final native int RegEnumKeyExW(int paramInt1, int paramInt2, char[] paramArrayOfChar1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, char[] paramArrayOfChar2, int[] paramArrayOfInt3, FILETIME paramFILETIME);

  public static final native int RegEnumKeyExA(int paramInt1, int paramInt2, byte[] paramArrayOfByte1, int[] paramArrayOfInt1, int[] paramArrayOfInt2, byte[] paramArrayOfByte2, int[] paramArrayOfInt3, FILETIME paramFILETIME);

  public static final native int RegisterClassW(WNDCLASS paramWNDCLASS);

  public static final native int RegisterClassA(WNDCLASS paramWNDCLASS);

  public static final native int RegisterWindowMessageW(char[] paramArrayOfChar);

  public static final native int RegisterWindowMessageA(byte[] paramArrayOfByte);

  public static final native int RegisterClipboardFormatA(byte[] paramArrayOfByte);

  public static final native int RegisterClipboardFormatW(char[] paramArrayOfChar);

  public static final native int RegOpenKeyExW(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int RegOpenKeyExA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int RegQueryInfoKeyW(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int paramInt3, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6, int[] paramArrayOfInt7, int[] paramArrayOfInt8, int paramInt4);

  public static final native int RegQueryInfoKeyA(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int paramInt3, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, int[] paramArrayOfInt5, int[] paramArrayOfInt6, int[] paramArrayOfInt7, int[] paramArrayOfInt8, int paramInt4);

  public static final native int RegQueryValueExW(int paramInt1, char[] paramArrayOfChar1, int paramInt2, int[] paramArrayOfInt1, char[] paramArrayOfChar2, int[] paramArrayOfInt2);

  public static final native int RegQueryValueExW(int paramInt1, char[] paramArrayOfChar, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  public static final native int RegQueryValueExA(int paramInt1, byte[] paramArrayOfByte1, int paramInt2, int[] paramArrayOfInt1, byte[] paramArrayOfByte2, int[] paramArrayOfInt2);

  public static final native int RegQueryValueExA(int paramInt1, byte[] paramArrayOfByte, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3);

  public static final native boolean ReleaseCapture();

  public static final native int ReleaseDC(int paramInt1, int paramInt2);

  public static final native boolean RemoveMenu(int paramInt1, int paramInt2, int paramInt3);

  public static final native int RemovePropA(int paramInt1, int paramInt2);

  public static final native int RemovePropW(int paramInt1, int paramInt2);

  public static final native boolean ReplyMessage(int paramInt);

  public static final native boolean RestoreDC(int paramInt1, int paramInt2);

  public static final native boolean RoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);

  public static final native int SaveDC(int paramInt);

  public static final native boolean ScreenToClient(int paramInt, POINT paramPOINT);

  public static final native int ScriptApplyDigitSubstitution(SCRIPT_DIGITSUBSTITUTE paramSCRIPT_DIGITSUBSTITUTE, SCRIPT_CONTROL paramSCRIPT_CONTROL, SCRIPT_STATE paramSCRIPT_STATE);

  public static final native int ScriptBreak(char[] paramArrayOfChar, int paramInt1, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int paramInt2);

  public static final native int ScriptGetProperties(int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int ScriptCacheGetHeight(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native int ScriptCPtoX(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int[] paramArrayOfInt);

  public static final native int ScriptFreeCache(int paramInt);

  public static final native int ScriptGetFontProperties(int paramInt1, int paramInt2, SCRIPT_FONTPROPERTIES paramSCRIPT_FONTPROPERTIES);

  public static final native int ScriptGetLogicalWidths(SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static final native int ScriptItemize(char[] paramArrayOfChar, int paramInt1, int paramInt2, SCRIPT_CONTROL paramSCRIPT_CONTROL, SCRIPT_STATE paramSCRIPT_STATE, int paramInt3, int[] paramArrayOfInt);

  public static final native int ScriptJustify(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);

  public static final native int ScriptLayout(int paramInt, byte[] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int ScriptPlace(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int paramInt6, int paramInt7, int[] paramArrayOfInt);

  public static final native int ScriptRecordDigitSubstitution(int paramInt, SCRIPT_DIGITSUBSTITUTE paramSCRIPT_DIGITSUBSTITUTE);

  public static final native int ScriptGetCMap(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, short[] paramArrayOfShort);

  public static final native int ScriptShape(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int paramInt5, int paramInt6, int paramInt7, int[] paramArrayOfInt);

  public static final native int ScriptStringAnalyse(int paramInt1, char[] paramArrayOfChar, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, SCRIPT_CONTROL paramSCRIPT_CONTROL, SCRIPT_STATE paramSCRIPT_STATE, int paramInt7, int paramInt8, int paramInt9, int paramInt10);

  public static final native int ScriptStringOut(int paramInt1, int paramInt2, int paramInt3, int paramInt4, RECT paramRECT, int paramInt5, int paramInt6, boolean paramBoolean);

  public static final native int ScriptStringFree(int paramInt);

  public static final native int ScriptTextOut(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, RECT paramRECT, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11, int paramInt12);

  public static final native int ScriptXtoCP(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, SCRIPT_ANALYSIS paramSCRIPT_ANALYSIS, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int ScrollWindowEx(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT1, RECT paramRECT2, int paramInt4, RECT paramRECT3, int paramInt5);

  public static final native int SelectClipRgn(int paramInt1, int paramInt2);

  public static final native int SelectObject(int paramInt1, int paramInt2);

  public static final native int SelectPalette(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native int SendInput(int paramInt1, int paramInt2, int paramInt3);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, LVCOLUMN paramLVCOLUMN);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, LVHITTESTINFO paramLVHITTESTINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, LITEM paramLITEM);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, LVITEM paramLVITEM);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, LVINSERTMARK paramLVINSERTMARK);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, MARGINS paramMARGINS);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, MCHITTESTINFO paramMCHITTESTINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, REBARBANDINFO paramREBARBANDINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, SYSTEMTIME paramSYSTEMTIME);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, SHDRAGIMAGE paramSHDRAGIMAGE);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TBBUTTON paramTBBUTTON);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TBBUTTONINFO paramTBBUTTONINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TCITEM paramTCITEM);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TCHITTESTINFO paramTCHITTESTINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TOOLINFO paramTOOLINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TVHITTESTINFO paramTVHITTESTINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TVINSERTSTRUCT paramTVINSERTSTRUCT);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TVITEM paramTVITEM);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, TVSORTCB paramTVSORTCB);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, UDACCEL paramUDACCEL);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, HDHITTESTINFO paramHDHITTESTINFO);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, HDITEM paramHDITEM);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, HDLAYOUT paramHDLAYOUT);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, BUTTON_IMAGELIST paramBUTTON_IMAGELIST);

  public static final native int SendMessageW(int paramInt1, int paramInt2, int paramInt3, SIZE paramSIZE);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, short[] paramArrayOfShort);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, LVCOLUMN paramLVCOLUMN);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, LVHITTESTINFO paramLVHITTESTINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, LITEM paramLITEM);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, LVINSERTMARK paramLVINSERTMARK);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, LVITEM paramLVITEM);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, MARGINS paramMARGINS);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, MCHITTESTINFO paramMCHITTESTINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, REBARBANDINFO paramREBARBANDINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, RECT paramRECT);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, SYSTEMTIME paramSYSTEMTIME);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, SHDRAGIMAGE paramSHDRAGIMAGE);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TBBUTTON paramTBBUTTON);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TBBUTTONINFO paramTBBUTTONINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TCITEM paramTCITEM);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TCHITTESTINFO paramTCHITTESTINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TOOLINFO paramTOOLINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TVHITTESTINFO paramTVHITTESTINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TVINSERTSTRUCT paramTVINSERTSTRUCT);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TVITEM paramTVITEM);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, TVSORTCB paramTVSORTCB);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, UDACCEL paramUDACCEL);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, HDHITTESTINFO paramHDHITTESTINFO);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, HDITEM paramHDITEM);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, HDLAYOUT paramHDLAYOUT);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, BUTTON_IMAGELIST paramBUTTON_IMAGELIST);

  public static final native int SendMessageA(int paramInt1, int paramInt2, int paramInt3, SIZE paramSIZE);

  public static final native int SetActiveWindow(int paramInt);

  public static final native int SetBkColor(int paramInt1, int paramInt2);

  public static final native int SetBkMode(int paramInt1, int paramInt2);

  public static final native boolean SetBrushOrgEx(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT);

  public static final native int SetCapture(int paramInt);

  public static final native boolean SetCaretPos(int paramInt1, int paramInt2);

  public static final native int SetClipboardData(int paramInt1, int paramInt2);

  public static final native int SetCurrentProcessExplicitAppUserModelID(char[] paramArrayOfChar);

  public static final native int SetCursor(int paramInt);

  public static final native boolean SetCursorPos(int paramInt1, int paramInt2);

  public static final native int SetDIBColorTable(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int SetErrorMode(int paramInt);

  public static final native int SetFocus(int paramInt);

  public static final native boolean SetForegroundWindow(int paramInt);

  public static final native int SetGraphicsMode(int paramInt1, int paramInt2);

  public static final native boolean SetLayeredWindowAttributes(int paramInt1, int paramInt2, byte paramByte, int paramInt3);

  public static final native int SetLayout(int paramInt1, int paramInt2);

  public static final native int SetMapMode(int paramInt1, int paramInt2);

  public static final native int SetMapperFlags(int paramInt1, int paramInt2);

  public static final native boolean SetMenu(int paramInt1, int paramInt2);

  public static final native boolean SetMenuDefaultItem(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean SetMenuInfo(int paramInt, MENUINFO paramMENUINFO);

  public static final native boolean SetMenuItemInfoW(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native boolean SetMenuItemInfoA(int paramInt1, int paramInt2, boolean paramBoolean, MENUITEMINFO paramMENUITEMINFO);

  public static final native int SetMetaRgn(int paramInt);

  public static final native int SetPaletteEntries(int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte);

  public static final native int SetParent(int paramInt1, int paramInt2);

  public static final native int SetPixel(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int SetPolyFillMode(int paramInt1, int paramInt2);

  public static final native boolean SetProcessDPIAware();

  public static final native boolean SetRect(RECT paramRECT, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean SetRectRgn(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int SetROP2(int paramInt1, int paramInt2);

  public static final native boolean SetScrollInfo(int paramInt1, int paramInt2, SCROLLINFO paramSCROLLINFO, boolean paramBoolean);

  public static final native int SetStretchBltMode(int paramInt1, int paramInt2);

  public static final native boolean SetPropW(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean SetPropA(int paramInt1, int paramInt2, int paramInt3);

  public static final native int SetTextAlign(int paramInt1, int paramInt2);

  public static final native int SetTextColor(int paramInt1, int paramInt2);

  public static final native int SetTimer(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean SetViewportExtEx(int paramInt1, int paramInt2, int paramInt3, SIZE paramSIZE);

  public static final native boolean SetViewportOrgEx(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT);

  public static final native int SetWindowLongW(int paramInt1, int paramInt2, int paramInt3);

  public static final native int SetWindowLongA(int paramInt1, int paramInt2, int paramInt3);

  public static final native int SetWindowLongPtrW(int paramInt1, int paramInt2, int paramInt3);

  public static final native int SetWindowLongPtrA(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean SetWindowExtEx(int paramInt1, int paramInt2, int paramInt3, SIZE paramSIZE);

  public static final native boolean SetWindowOrgEx(int paramInt1, int paramInt2, int paramInt3, POINT paramPOINT);

  public static final native boolean SetWindowPlacement(int paramInt, WINDOWPLACEMENT paramWINDOWPLACEMENT);

  public static final native boolean SetWindowPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7);

  public static final native int SetWindowRgn(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native boolean SetWindowTextW(int paramInt, char[] paramArrayOfChar);

  public static final native boolean SetWindowTextA(int paramInt, byte[] paramArrayOfByte);

  public static final native int SetWindowTheme(int paramInt, char[] paramArrayOfChar1, char[] paramArrayOfChar2);

  public static final native int SetWindowsHookExW(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int SetWindowsHookExA(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native boolean SetWorldTransform(int paramInt, float[] paramArrayOfFloat);

  public static final native int SHBrowseForFolderW(BROWSEINFO paramBROWSEINFO);

  public static final native int SHBrowseForFolderA(BROWSEINFO paramBROWSEINFO);

  public static final native boolean SHCreateMenuBar(SHMENUBARINFO paramSHMENUBARINFO);

  public static final native int SHGetFileInfoW(char[] paramArrayOfChar, int paramInt1, SHFILEINFOW paramSHFILEINFOW, int paramInt2, int paramInt3);

  public static final native int SHGetFileInfoA(byte[] paramArrayOfByte, int paramInt1, SHFILEINFOA paramSHFILEINFOA, int paramInt2, int paramInt3);

  public static final native int SHGetFolderPathW(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar);

  public static final native int SHGetFolderPathA(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte);

  public static final native boolean SHHandleWMSettingChange(int paramInt1, int paramInt2, int paramInt3, SHACTIVATEINFO paramSHACTIVATEINFO);

  public static final native int SHRecognizeGesture(SHRGINFO paramSHRGINFO);

  public static final native void SHSendBackToFocusWindow(int paramInt1, int paramInt2, int paramInt3);

  public static final native boolean SHSipPreference(int paramInt1, int paramInt2);

  public static final native boolean ShellExecuteExW(SHELLEXECUTEINFO paramSHELLEXECUTEINFO);

  public static final native boolean ShellExecuteExA(SHELLEXECUTEINFO paramSHELLEXECUTEINFO);

  public static final native boolean Shell_NotifyIconA(int paramInt, NOTIFYICONDATAA paramNOTIFYICONDATAA);

  public static final native boolean Shell_NotifyIconW(int paramInt, NOTIFYICONDATAW paramNOTIFYICONDATAW);

  public static final native int SHGetMalloc(int[] paramArrayOfInt);

  public static final native boolean SHGetPathFromIDListW(int paramInt, char[] paramArrayOfChar);

  public static final native boolean SHGetPathFromIDListA(int paramInt, byte[] paramArrayOfByte);

  public static final native int SHCreateItemInKnownFolder(byte[] paramArrayOfByte1, int paramInt, char[] paramArrayOfChar, byte[] paramArrayOfByte2, int[] paramArrayOfInt);

  public static final native int SHCreateItemFromRelativeName(int paramInt1, char[] paramArrayOfChar, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt);

  public static final native boolean SHSetAppKeyWndAssoc(byte paramByte, int paramInt);

  public static final native boolean ShowCaret(int paramInt);

  public static final native int ShowCursor(boolean paramBoolean);

  public static final native boolean ShowOwnedPopups(int paramInt, boolean paramBoolean);

  public static final native boolean ShowScrollBar(int paramInt1, int paramInt2, boolean paramBoolean);

  public static final native boolean ShowWindow(int paramInt1, int paramInt2);

  public static final native boolean SipGetInfo(SIPINFO paramSIPINFO);

  public static final native int StartDocW(int paramInt, DOCINFO paramDOCINFO);

  public static final native int StartDocA(int paramInt, DOCINFO paramDOCINFO);

  public static final native int StartPage(int paramInt);

  public static final native boolean StretchBlt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11);

  public static final native boolean StrokePath(int paramInt);

  public static final native boolean SystemParametersInfoW(int paramInt1, int paramInt2, HIGHCONTRAST paramHIGHCONTRAST, int paramInt3);

  public static final native boolean SystemParametersInfoA(int paramInt1, int paramInt2, HIGHCONTRAST paramHIGHCONTRAST, int paramInt3);

  public static final native boolean SystemParametersInfoW(int paramInt1, int paramInt2, RECT paramRECT, int paramInt3);

  public static final native boolean SystemParametersInfoA(int paramInt1, int paramInt2, RECT paramRECT, int paramInt3);

  public static final native boolean SystemParametersInfoW(int paramInt1, int paramInt2, NONCLIENTMETRICSW paramNONCLIENTMETRICSW, int paramInt3);

  public static final native boolean SystemParametersInfoA(int paramInt1, int paramInt2, NONCLIENTMETRICSA paramNONCLIENTMETRICSA, int paramInt3);

  public static final native boolean SystemParametersInfoW(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native boolean SystemParametersInfoA(int paramInt1, int paramInt2, int[] paramArrayOfInt, int paramInt3);

  public static final native int ToAscii(int paramInt1, int paramInt2, byte[] paramArrayOfByte, short[] paramArrayOfShort, int paramInt3);

  public static final native int ToUnicode(int paramInt1, int paramInt2, byte[] paramArrayOfByte, char[] paramArrayOfChar, int paramInt3, int paramInt4);

  public static final native boolean TreeView_GetItemRect(int paramInt1, int paramInt2, RECT paramRECT, boolean paramBoolean);

  public static final native boolean TrackMouseEvent(TRACKMOUSEEVENT paramTRACKMOUSEEVENT);

  public static final native boolean TrackPopupMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, RECT paramRECT);

  public static final native int TranslateAcceleratorW(int paramInt1, int paramInt2, MSG paramMSG);

  public static final native int TranslateAcceleratorA(int paramInt1, int paramInt2, MSG paramMSG);

  public static final native boolean TranslateCharsetInfo(int paramInt1, int[] paramArrayOfInt, int paramInt2);

  public static final native boolean TranslateMDISysAccel(int paramInt, MSG paramMSG);

  public static final native boolean TranslateMessage(MSG paramMSG);

  public static final native boolean TransparentBlt(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11);

  public static final native boolean TransparentImage(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11);

  public static final native boolean UnhookWindowsHookEx(int paramInt);

  public static final native boolean UnregisterClassW(char[] paramArrayOfChar, int paramInt);

  public static final native boolean UnregisterClassA(byte[] paramArrayOfByte, int paramInt);

  public static final native boolean UpdateLayeredWindow(int paramInt1, int paramInt2, POINT paramPOINT1, SIZE paramSIZE, int paramInt3, POINT paramPOINT2, int paramInt4, BLENDFUNCTION paramBLENDFUNCTION, int paramInt5);

  public static final native boolean UpdateWindow(int paramInt);

  public static final native boolean ValidateRect(int paramInt, RECT paramRECT);

  public static final native short VkKeyScanW(short paramShort);

  public static final native short VkKeyScanA(short paramShort);

  public static final native int VtblCall(int paramInt1, int paramInt2);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3);

  public static final native int VtblCall(int paramInt1, int paramInt2, long paramLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int[] paramArrayOfInt);

  public static final native int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, int paramInt3, long[] paramArrayOfLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong, int paramInt4, long[] paramArrayOfLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3, int paramInt4, long[] paramArrayOfLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int VtblCall(int paramInt1, int paramInt2, short paramShort, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);

  public static final native int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static final native int VtblCall(int paramInt1, int paramInt2, long[] paramArrayOfLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, byte[] paramArrayOfByte, int[] paramArrayOfInt);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt1, int[] paramArrayOfInt2);

  public static final native int VtblCall(int paramInt1, int paramInt2, TF_DISPLAYATTRIBUTE paramTF_DISPLAYATTRIBUTE);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, long paramLong1, long paramLong2);

  public static final native int VtblCall(int paramInt1, int paramInt2, long paramLong1, long paramLong2, long paramLong3);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public static final native int VtblCall(int paramInt1, int paramInt2, long paramLong, int paramInt3);

  public static final native int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt1, byte[] paramArrayOfByte, int[] paramArrayOfInt2);

  public static final native int VtblCall(int paramInt1, int paramInt2, int[] paramArrayOfInt, byte[] paramArrayOfByte, long[] paramArrayOfLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar);

  public static final native int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3);

  public static final native int VtblCall(int paramInt1, int paramInt2, char[] paramArrayOfChar, long paramLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, PROPERTYKEY paramPROPERTYKEY, int paramInt3);

  public static final native int VtblCall(int paramInt1, int paramInt2, PROPERTYKEY paramPROPERTYKEY, long paramLong);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int paramInt4, char[] paramArrayOfChar1, char[] paramArrayOfChar2, int paramInt5);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt);

  public static final native int VtblCall(int paramInt1, int paramInt2, int paramInt3, long[] paramArrayOfLong);

  public static final native boolean WaitMessage();

  public static final native int WideCharToMultiByte(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, byte[] paramArrayOfByte1, int paramInt4, byte[] paramArrayOfByte2, boolean[] paramArrayOfBoolean);

  public static final native int WideCharToMultiByte(int paramInt1, int paramInt2, char[] paramArrayOfChar, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte, boolean[] paramArrayOfBoolean);

  public static final native int WindowFromDC(int paramInt);

  public static final native int WindowFromPoint(POINT paramPOINT);

  public static final native int wcslen(int paramInt);

  public static final native int MapViewOfFile(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public static final native boolean UnmapViewOfFile(int paramInt);

  public static final native int OpenProcess(int paramInt1, boolean paramBoolean, int paramInt2);

  public static final native int GetCurrentProcess();

  public static final native boolean DuplicateHandle(int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4, boolean paramBoolean, int paramInt5);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.win32.OS
 * JD-Core Version:    0.6.2
 */