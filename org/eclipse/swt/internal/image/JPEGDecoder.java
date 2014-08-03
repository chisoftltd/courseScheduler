package org.eclipse.swt.internal.image;

import java.io.IOException;
import java.io.InputStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.ImageLoaderEvent;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;

public class JPEGDecoder
{
  static final int DCTSIZE = 8;
  static final int DCTSIZE2 = 64;
  static final int NUM_QUANT_TBLS = 4;
  static final int NUM_HUFF_TBLS = 4;
  static final int NUM_ARITH_TBLS = 16;
  static final int MAX_COMPS_IN_SCAN = 4;
  static final int MAX_COMPONENTS = 10;
  static final int MAX_SAMP_FACTOR = 4;
  static final int D_MAX_BLOCKS_IN_MCU = 10;
  static final int HUFF_LOOKAHEAD = 8;
  static final int MAX_Q_COMPS = 4;
  static final int IFAST_SCALE_BITS = 2;
  static final int MAXJSAMPLE = 255;
  static final int CENTERJSAMPLE = 128;
  static final int MIN_GET_BITS = 25;
  static final int INPUT_BUFFER_SIZE = 4096;
  static final int SCALEBITS = 16;
  static final int ONE_HALF = 32768;
  static final int RGB_RED = 2;
  static final int RGB_GREEN = 1;
  static final int RGB_BLUE = 0;
  static final int RGB_PIXELSIZE = 3;
  static final int JBUF_PASS_THRU = 0;
  static final int JBUF_SAVE_SOURCE = 1;
  static final int JBUF_CRANK_DEST = 2;
  static final int JBUF_SAVE_AND_PASS = 3;
  static final int JPEG_MAX_DIMENSION = 65500;
  static final int BITS_IN_JSAMPLE = 8;
  static final int JDITHER_NONE = 0;
  static final int JDITHER_ORDERED = 1;
  static final int JDITHER_FS = 2;
  static final int JDCT_ISLOW = 0;
  static final int JDCT_IFAST = 1;
  static final int JDCT_FLOAT = 2;
  static final int JDCT_DEFAULT = 0;
  static final int JCS_UNKNOWN = 0;
  static final int JCS_GRAYSCALE = 1;
  static final int JCS_RGB = 2;
  static final int JCS_YCbCr = 3;
  static final int JCS_CMYK = 4;
  static final int JCS_YCCK = 5;
  static final int SAVED_COEFS = 6;
  static final int Q01_POS = 1;
  static final int Q10_POS = 8;
  static final int Q20_POS = 16;
  static final int Q11_POS = 9;
  static final int Q02_POS = 2;
  static final int CTX_PREPARE_FOR_IMCU = 0;
  static final int CTX_PROCESS_IMCU = 1;
  static final int CTX_POSTPONED_ROW = 2;
  static final int APP0_DATA_LEN = 14;
  static final int APP14_DATA_LEN = 12;
  static final int APPN_DATA_LEN = 14;
  static final int M_SOF0 = 192;
  static final int M_SOF1 = 193;
  static final int M_SOF2 = 194;
  static final int M_SOF3 = 195;
  static final int M_SOF5 = 197;
  static final int M_SOF6 = 198;
  static final int M_SOF7 = 199;
  static final int M_JPG = 200;
  static final int M_SOF9 = 201;
  static final int M_SOF10 = 202;
  static final int M_SOF11 = 203;
  static final int M_SOF13 = 205;
  static final int M_SOF14 = 206;
  static final int M_SOF15 = 207;
  static final int M_DHT = 196;
  static final int M_DAC = 204;
  static final int M_RST0 = 208;
  static final int M_RST1 = 209;
  static final int M_RST2 = 210;
  static final int M_RST3 = 211;
  static final int M_RST4 = 212;
  static final int M_RST5 = 213;
  static final int M_RST6 = 214;
  static final int M_RST7 = 215;
  static final int M_SOI = 216;
  static final int M_EOI = 217;
  static final int M_SOS = 218;
  static final int M_DQT = 219;
  static final int M_DNL = 220;
  static final int M_DRI = 221;
  static final int M_DHP = 222;
  static final int M_EXP = 223;
  static final int M_APP0 = 224;
  static final int M_APP1 = 225;
  static final int M_APP2 = 226;
  static final int M_APP3 = 227;
  static final int M_APP4 = 228;
  static final int M_APP5 = 229;
  static final int M_APP6 = 230;
  static final int M_APP7 = 231;
  static final int M_APP8 = 232;
  static final int M_APP9 = 233;
  static final int M_APP10 = 234;
  static final int M_APP11 = 235;
  static final int M_APP12 = 236;
  static final int M_APP13 = 237;
  static final int M_APP14 = 238;
  static final int M_APP15 = 239;
  static final int M_JPG0 = 240;
  static final int M_JPG13 = 253;
  static final int M_COM = 254;
  static final int M_TEM = 1;
  static final int M_ERROR = 256;
  static final int CSTATE_START = 100;
  static final int CSTATE_SCANNING = 101;
  static final int CSTATE_RAW_OK = 102;
  static final int CSTATE_WRCOEFS = 103;
  static final int DSTATE_START = 200;
  static final int DSTATE_INHEADER = 201;
  static final int DSTATE_READY = 202;
  static final int DSTATE_PRELOAD = 203;
  static final int DSTATE_PRESCAN = 204;
  static final int DSTATE_SCANNING = 205;
  static final int DSTATE_RAW_OK = 206;
  static final int DSTATE_BUFIMAGE = 207;
  static final int DSTATE_BUFPOST = 208;
  static final int DSTATE_RDCOEFS = 209;
  static final int DSTATE_STOPPING = 210;
  static final int JPEG_REACHED_SOS = 1;
  static final int JPEG_REACHED_EOI = 2;
  static final int JPEG_ROW_COMPLETED = 3;
  static final int JPEG_SCAN_COMPLETED = 4;
  static final int JPEG_SUSPENDED = 0;
  static final int JPEG_HEADER_OK = 1;
  static final int JPEG_HEADER_TABLES_ONLY = 2;
  static final int DECOMPRESS_DATA = 0;
  static final int DECOMPRESS_SMOOTH_DATA = 1;
  static final int DECOMPRESS_ONEPASS = 2;
  static final int CONSUME_DATA = 0;
  static final int DUMMY_CONSUME_DATA = 1;
  static final int PROCESS_DATA_SIMPLE_MAIN = 0;
  static final int PROCESS_DATA_CONTEXT_MAIN = 1;
  static final int PROCESS_DATA_CRANK_POST = 2;
  static final int POST_PROCESS_1PASS = 0;
  static final int POST_PROCESS_DATA_UPSAMPLE = 1;
  static final int NULL_CONVERT = 0;
  static final int GRAYSCALE_CONVERT = 1;
  static final int YCC_RGB_CONVERT = 2;
  static final int GRAY_RGB_CONVERT = 3;
  static final int YCCK_CMYK_CONVERT = 4;
  static final int NOOP_UPSAMPLE = 0;
  static final int FULLSIZE_UPSAMPLE = 1;
  static final int H2V1_FANCY_UPSAMPLE = 2;
  static final int H2V1_UPSAMPLE = 3;
  static final int H2V2_FANCY_UPSAMPLE = 4;
  static final int H2V2_UPSAMPLE = 5;
  static final int INT_UPSAMPLE = 6;
  static final int INPUT_CONSUME_INPUT = 0;
  static final int COEF_CONSUME_INPUT = 1;
  static int[] extend_test = { 0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384 };
  static int[] extend_offset = { 0, -1, -3, -7, -15, -31, -63, -127, -255, -511, -1023, -2047, -4095, -8191, -16383, -32767 };
  static int[] jpeg_natural_order = { 0, 1, 8, 16, 9, 2, 3, 10, 17, 24, 32, 25, 18, 11, 4, 5, 12, 19, 26, 33, 40, 48, 41, 34, 27, 20, 13, 6, 7, 14, 21, 28, 35, 42, 49, 56, 57, 50, 43, 36, 29, 22, 15, 23, 30, 37, 44, 51, 58, 59, 52, 45, 38, 31, 39, 46, 53, 60, 61, 54, 47, 55, 62, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63, 63 };
  static final int CONST_BITS = 13;
  static final int PASS1_BITS = 2;
  static final int RANGE_MASK = 1023;

  static void error()
  {
    SWT.error(40);
  }

  static void error(int paramInt)
  {
    SWT.error(paramInt);
  }

  static void error(String paramString)
  {
    SWT.error(40, null, paramString);
  }

  static void jinit_marker_reader(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_marker_reader localjpeg_marker_reader = paramjpeg_decompress_struct.marker = new jpeg_marker_reader();
    localjpeg_marker_reader.length_limit_COM = 0;
    reset_marker_reader(paramjpeg_decompress_struct);
  }

  static void jinit_d_coef_controller(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = new jpeg_d_coef_controller();
    paramjpeg_decompress_struct.coef = localjpeg_d_coef_controller;
    localjpeg_d_coef_controller.coef_bits_latch = null;
    if (paramBoolean)
    {
      for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
      {
        jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
        int j = localjpeg_component_info.v_samp_factor;
        if (paramjpeg_decompress_struct.progressive_mode)
          j *= 3;
        localjpeg_d_coef_controller.whole_image[i] = new short[(int)jround_up(localjpeg_component_info.height_in_blocks, localjpeg_component_info.v_samp_factor)][(int)jround_up(localjpeg_component_info.width_in_blocks, localjpeg_component_info.h_samp_factor)][64];
      }
      localjpeg_d_coef_controller.decompress_data = 0;
      localjpeg_d_coef_controller.coef_arrays = localjpeg_d_coef_controller.whole_image[0];
    }
    else
    {
      localjpeg_d_coef_controller.MCU_buffer = new short[10][64];
      localjpeg_d_coef_controller.decompress_data = 2;
      localjpeg_d_coef_controller.coef_arrays = null;
    }
  }

  static void start_output_pass(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    if (localjpeg_d_coef_controller.coef_arrays != null)
      if ((paramjpeg_decompress_struct.do_block_smoothing) && (smoothing_ok(paramjpeg_decompress_struct)))
        localjpeg_d_coef_controller.decompress_data = 1;
      else
        localjpeg_d_coef_controller.decompress_data = 0;
    paramjpeg_decompress_struct.output_iMCU_row = 0;
  }

  static void jpeg_create_decompress(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.is_decompressor = true;
    paramjpeg_decompress_struct.marker_list = null;
    jinit_marker_reader(paramjpeg_decompress_struct);
    jinit_input_controller(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.global_state = 200;
  }

  static void jpeg_calc_output_dimensions(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.global_state != 202)
      error();
    paramjpeg_decompress_struct.output_width = paramjpeg_decompress_struct.image_width;
    paramjpeg_decompress_struct.output_height = paramjpeg_decompress_struct.image_height;
    switch (paramjpeg_decompress_struct.out_color_space)
    {
    case 1:
      paramjpeg_decompress_struct.out_color_components = 1;
      break;
    case 2:
    case 3:
      paramjpeg_decompress_struct.out_color_components = 3;
      break;
    case 4:
    case 5:
      paramjpeg_decompress_struct.out_color_components = 4;
      break;
    default:
      paramjpeg_decompress_struct.out_color_components = paramjpeg_decompress_struct.num_components;
    }
    paramjpeg_decompress_struct.output_components = (paramjpeg_decompress_struct.quantize_colors ? 1 : paramjpeg_decompress_struct.out_color_components);
    if (use_merged_upsample(paramjpeg_decompress_struct))
      paramjpeg_decompress_struct.rec_outbuf_height = paramjpeg_decompress_struct.max_v_samp_factor;
    else
      paramjpeg_decompress_struct.rec_outbuf_height = 1;
  }

  static boolean use_merged_upsample(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if ((paramjpeg_decompress_struct.do_fancy_upsampling) || (paramjpeg_decompress_struct.CCIR601_sampling))
      return false;
    if ((paramjpeg_decompress_struct.jpeg_color_space != 3) || (paramjpeg_decompress_struct.num_components != 3) || (paramjpeg_decompress_struct.out_color_space != 2) || (paramjpeg_decompress_struct.out_color_components != 3))
      return false;
    if ((paramjpeg_decompress_struct.comp_info[0].h_samp_factor != 2) || (paramjpeg_decompress_struct.comp_info[1].h_samp_factor != 1) || (paramjpeg_decompress_struct.comp_info[2].h_samp_factor != 1) || (paramjpeg_decompress_struct.comp_info[0].v_samp_factor > 2) || (paramjpeg_decompress_struct.comp_info[1].v_samp_factor != 1) || (paramjpeg_decompress_struct.comp_info[2].v_samp_factor != 1))
      return false;
    return (paramjpeg_decompress_struct.comp_info[0].DCT_scaled_size == paramjpeg_decompress_struct.min_DCT_scaled_size) && (paramjpeg_decompress_struct.comp_info[1].DCT_scaled_size == paramjpeg_decompress_struct.min_DCT_scaled_size) && (paramjpeg_decompress_struct.comp_info[2].DCT_scaled_size == paramjpeg_decompress_struct.min_DCT_scaled_size);
  }

  static void prepare_range_limit_table(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    byte[] arrayOfByte = new byte[1408];
    int j = 256;
    paramjpeg_decompress_struct.sample_range_limit_offset = j;
    paramjpeg_decompress_struct.sample_range_limit = arrayOfByte;
    for (int i = 0; i <= 255; i++)
      arrayOfByte[(i + j)] = ((byte)i);
    j += 128;
    for (i = 128; i < 512; i++)
      arrayOfByte[(i + j)] = -1;
    System.arraycopy(paramjpeg_decompress_struct.sample_range_limit, paramjpeg_decompress_struct.sample_range_limit_offset, arrayOfByte, j + 896, 128);
  }

  static void build_ycc_rgb_table(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_color_deconverter localjpeg_color_deconverter = paramjpeg_decompress_struct.cconvert;
    localjpeg_color_deconverter.Cr_r_tab = new int[256];
    localjpeg_color_deconverter.Cb_b_tab = new int[256];
    localjpeg_color_deconverter.Cr_g_tab = new int[256];
    localjpeg_color_deconverter.Cb_g_tab = new int[256];
    int i = 0;
    for (int j = -128; i <= 255; j++)
    {
      localjpeg_color_deconverter.Cr_r_tab[i] = (91881 * j + 32768 >> 16);
      localjpeg_color_deconverter.Cb_b_tab[i] = (116130 * j + 32768 >> 16);
      localjpeg_color_deconverter.Cr_g_tab[i] = (-46802 * j);
      localjpeg_color_deconverter.Cb_g_tab[i] = (-22554 * j + 32768);
      i++;
    }
  }

  static void jinit_color_deconverter(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_color_deconverter localjpeg_color_deconverter = paramjpeg_decompress_struct.cconvert = new jpeg_color_deconverter();
    switch (paramjpeg_decompress_struct.jpeg_color_space)
    {
    case 1:
      if (paramjpeg_decompress_struct.num_components != 1)
        error();
      break;
    case 2:
    case 3:
      if (paramjpeg_decompress_struct.num_components != 3)
        error();
      break;
    case 4:
    case 5:
      if (paramjpeg_decompress_struct.num_components != 4)
        error();
      break;
    default:
      if (paramjpeg_decompress_struct.num_components < 1)
        error();
      break;
    }
    switch (paramjpeg_decompress_struct.out_color_space)
    {
    case 1:
      paramjpeg_decompress_struct.out_color_components = 1;
      if ((paramjpeg_decompress_struct.jpeg_color_space == 1) || (paramjpeg_decompress_struct.jpeg_color_space == 3))
      {
        localjpeg_color_deconverter.color_convert = 1;
        for (int i = 1; i < paramjpeg_decompress_struct.num_components; i++)
          paramjpeg_decompress_struct.comp_info[i].component_needed = false;
      }
      else
      {
        error();
      }
      break;
    case 2:
      paramjpeg_decompress_struct.out_color_components = 3;
      if (paramjpeg_decompress_struct.jpeg_color_space == 3)
      {
        localjpeg_color_deconverter.color_convert = 2;
        build_ycc_rgb_table(paramjpeg_decompress_struct);
      }
      else if (paramjpeg_decompress_struct.jpeg_color_space == 1)
      {
        localjpeg_color_deconverter.color_convert = 3;
      }
      else if (paramjpeg_decompress_struct.jpeg_color_space == 2)
      {
        localjpeg_color_deconverter.color_convert = 0;
      }
      else
      {
        error();
      }
      break;
    case 4:
      paramjpeg_decompress_struct.out_color_components = 4;
      if (paramjpeg_decompress_struct.jpeg_color_space == 5)
      {
        localjpeg_color_deconverter.color_convert = 4;
        build_ycc_rgb_table(paramjpeg_decompress_struct);
      }
      else if (paramjpeg_decompress_struct.jpeg_color_space == 4)
      {
        localjpeg_color_deconverter.color_convert = 0;
      }
      else
      {
        error();
      }
      break;
    case 3:
    default:
      if (paramjpeg_decompress_struct.out_color_space == paramjpeg_decompress_struct.jpeg_color_space)
      {
        paramjpeg_decompress_struct.out_color_components = paramjpeg_decompress_struct.num_components;
        localjpeg_color_deconverter.color_convert = 0;
      }
      else
      {
        error();
      }
      break;
    }
    if (paramjpeg_decompress_struct.quantize_colors)
      paramjpeg_decompress_struct.output_components = 1;
    else
      paramjpeg_decompress_struct.output_components = paramjpeg_decompress_struct.out_color_components;
  }

  static void jinit_d_post_controller(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean)
  {
    jpeg_d_post_controller localjpeg_d_post_controller = paramjpeg_decompress_struct.post = new jpeg_d_post_controller();
    localjpeg_d_post_controller.whole_image = null;
    localjpeg_d_post_controller.buffer = null;
    if (paramjpeg_decompress_struct.quantize_colors)
      error(20);
  }

  static void make_funny_pointers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    int m = paramjpeg_decompress_struct.min_DCT_scaled_size;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int k = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      byte[][] arrayOfByte2 = localjpeg_d_main_controller.xbuffer[0][i];
      int n = localjpeg_d_main_controller.xbuffer_offset[0][i];
      byte[][] arrayOfByte3 = localjpeg_d_main_controller.xbuffer[1][i];
      int i1 = localjpeg_d_main_controller.xbuffer_offset[1][i];
      byte[][] arrayOfByte1 = localjpeg_d_main_controller.buffer[i];
      for (int j = 0; j < k * (m + 2); j++)
      {
        byte[] tmp111_110 = arrayOfByte1[j];
        arrayOfByte3[(j + i1)] = tmp111_110;
        arrayOfByte2[(j + n)] = tmp111_110;
      }
      for (j = 0; j < k * 2; j++)
      {
        arrayOfByte3[(k * (m - 2) + j + i1)] = arrayOfByte1[(k * m + j)];
        arrayOfByte3[(k * m + j + i1)] = arrayOfByte1[(k * (m - 2) + j)];
      }
      for (j = 0; j < k; j++)
        arrayOfByte2[(j - k + n)] = arrayOfByte2[(0 + n)];
    }
  }

  static void alloc_funny_pointers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    int k = paramjpeg_decompress_struct.min_DCT_scaled_size;
    localjpeg_d_main_controller.xbuffer[0] = new byte[paramjpeg_decompress_struct.num_components][][];
    localjpeg_d_main_controller.xbuffer[1] = new byte[paramjpeg_decompress_struct.num_components][][];
    localjpeg_d_main_controller.xbuffer_offset[0] = new int[paramjpeg_decompress_struct.num_components];
    localjpeg_d_main_controller.xbuffer_offset[1] = new int[paramjpeg_decompress_struct.num_components];
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int j = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      byte[][] arrayOfByte = new byte[2 * (j * (k + 4))][];
      int m = j;
      localjpeg_d_main_controller.xbuffer_offset[0][i] = m;
      localjpeg_d_main_controller.xbuffer[0][i] = arrayOfByte;
      m += j * (k + 4);
      localjpeg_d_main_controller.xbuffer_offset[1][i] = m;
      localjpeg_d_main_controller.xbuffer[1][i] = arrayOfByte;
    }
  }

  static void jinit_d_main_controller(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main = new jpeg_d_main_controller();
    if (paramBoolean)
      error();
    int k;
    if (paramjpeg_decompress_struct.upsample.need_context_rows)
    {
      if (paramjpeg_decompress_struct.min_DCT_scaled_size < 2)
        error();
      alloc_funny_pointers(paramjpeg_decompress_struct);
      k = paramjpeg_decompress_struct.min_DCT_scaled_size + 2;
    }
    else
    {
      k = paramjpeg_decompress_struct.min_DCT_scaled_size;
    }
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int j = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      localjpeg_d_main_controller.buffer[i] = new byte[j * k][localjpeg_component_info.width_in_blocks * localjpeg_component_info.DCT_scaled_size];
    }
  }

  static long jround_up(long paramLong1, long paramLong2)
  {
    paramLong1 += paramLong2 - 1L;
    return paramLong1 - paramLong1 % paramLong2;
  }

  static void jinit_upsampler(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_upsampler localjpeg_upsampler = new jpeg_upsampler();
    paramjpeg_decompress_struct.upsample = localjpeg_upsampler;
    localjpeg_upsampler.need_context_rows = false;
    if (paramjpeg_decompress_struct.CCIR601_sampling)
      error();
    int k = (paramjpeg_decompress_struct.do_fancy_upsampling) && (paramjpeg_decompress_struct.min_DCT_scaled_size > 1) ? 1 : 0;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int m = localjpeg_component_info.h_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      int n = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      int i1 = paramjpeg_decompress_struct.max_h_samp_factor;
      int i2 = paramjpeg_decompress_struct.max_v_samp_factor;
      localjpeg_upsampler.rowgroup_height[i] = n;
      int j = 1;
      if (!localjpeg_component_info.component_needed)
      {
        localjpeg_upsampler.methods[i] = 0;
        j = 0;
      }
      else if ((m == i1) && (n == i2))
      {
        localjpeg_upsampler.methods[i] = 1;
        j = 0;
      }
      else if ((m * 2 == i1) && (n == i2))
      {
        if ((k != 0) && (localjpeg_component_info.downsampled_width > 2))
          localjpeg_upsampler.methods[i] = 2;
        else
          localjpeg_upsampler.methods[i] = 3;
      }
      else if ((m * 2 == i1) && (n * 2 == i2))
      {
        if ((k != 0) && (localjpeg_component_info.downsampled_width > 2))
        {
          localjpeg_upsampler.methods[i] = 4;
          localjpeg_upsampler.need_context_rows = true;
        }
        else
        {
          localjpeg_upsampler.methods[i] = 5;
        }
      }
      else if ((i1 % m == 0) && (i2 % n == 0))
      {
        localjpeg_upsampler.methods[i] = 6;
        localjpeg_upsampler.h_expand[i] = ((byte)(i1 / m));
        localjpeg_upsampler.v_expand[i] = ((byte)(i2 / n));
      }
      else
      {
        error();
      }
      if (j != 0)
        localjpeg_upsampler.color_buf[i] = new byte[paramjpeg_decompress_struct.max_v_samp_factor][(int)jround_up(paramjpeg_decompress_struct.output_width, paramjpeg_decompress_struct.max_h_samp_factor)];
    }
  }

  static void jinit_phuff_decoder(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.entropy = new phuff_entropy_decoder();
    paramjpeg_decompress_struct.coef_bits = new int[paramjpeg_decompress_struct.num_components][64];
    int[][] arrayOfInt = paramjpeg_decompress_struct.coef_bits;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
      for (int j = 0; j < 64; j++)
        arrayOfInt[i][j] = -1;
  }

  static void jinit_huff_decoder(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.entropy = new huff_entropy_decoder();
  }

  static void jinit_inverse_dct(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_inverse_dct localjpeg_inverse_dct = paramjpeg_decompress_struct.idct = new jpeg_inverse_dct();
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      localjpeg_component_info.dct_table = new int[64];
      localjpeg_inverse_dct.cur_method[i] = -1;
    }
  }

  static void jpeg_idct_islow(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, short[] paramArrayOfShort, byte[][] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    byte[] arrayOfByte2 = paramjpeg_decompress_struct.sample_range_limit;
    int i9 = paramjpeg_decompress_struct.sample_range_limit_offset + 128;
    int[] arrayOfInt3 = paramjpeg_decompress_struct.workspace;
    short[] arrayOfShort = paramArrayOfShort;
    int[] arrayOfInt1 = paramjpeg_component_info.dct_table;
    int[] arrayOfInt2 = arrayOfInt3;
    int i11 = 0;
    int i12 = 0;
    int i13 = 0;
    int i5;
    int i6;
    int i4;
    int k;
    int m;
    int i;
    int j;
    int n;
    int i3;
    int i1;
    int i2;
    int i7;
    int i8;
    for (int i10 = 8; i10 > 0; i10--)
      if ((arrayOfShort[(8 + i11)] == 0) && (arrayOfShort[(16 + i11)] == 0) && (arrayOfShort[(24 + i11)] == 0) && (arrayOfShort[(32 + i11)] == 0) && (arrayOfShort[(40 + i11)] == 0) && (arrayOfShort[(48 + i11)] == 0) && (arrayOfShort[(56 + i11)] == 0))
      {
        i14 = arrayOfShort[(0 + i11)] * arrayOfInt1[(0 + i12)] << 2;
        arrayOfInt2[(0 + i13)] = i14;
        arrayOfInt2[(8 + i13)] = i14;
        arrayOfInt2[(16 + i13)] = i14;
        arrayOfInt2[(24 + i13)] = i14;
        arrayOfInt2[(32 + i13)] = i14;
        arrayOfInt2[(40 + i13)] = i14;
        arrayOfInt2[(48 + i13)] = i14;
        arrayOfInt2[(56 + i13)] = i14;
        i11++;
        i12++;
        i13++;
      }
      else
      {
        i5 = arrayOfShort[(16 + i11)] * arrayOfInt1[(16 + i12)];
        i6 = arrayOfShort[(48 + i11)] * arrayOfInt1[(48 + i12)];
        i4 = (i5 + i6) * 4433;
        k = i4 + i6 * -15137;
        m = i4 + i5 * 6270;
        i5 = arrayOfShort[(0 + i11)] * arrayOfInt1[(0 + i12)];
        i6 = arrayOfShort[(32 + i11)] * arrayOfInt1[(32 + i12)];
        i = i5 + i6 << 13;
        j = i5 - i6 << 13;
        n = i + m;
        i3 = i - m;
        i1 = j + k;
        i2 = j - k;
        i = arrayOfShort[(56 + i11)] * arrayOfInt1[(56 + i12)];
        j = arrayOfShort[(40 + i11)] * arrayOfInt1[(40 + i12)];
        k = arrayOfShort[(24 + i11)] * arrayOfInt1[(24 + i12)];
        m = arrayOfShort[(8 + i11)] * arrayOfInt1[(8 + i12)];
        i4 = i + m;
        i5 = j + k;
        i6 = i + k;
        i7 = j + m;
        i8 = (i6 + i7) * 9633;
        i *= 2446;
        j *= 16819;
        k *= 25172;
        m *= 12299;
        i4 *= -7373;
        i5 *= -20995;
        i6 *= -16069;
        i7 *= -3196;
        i6 += i8;
        i7 += i8;
        i += i4 + i6;
        j += i5 + i7;
        k += i5 + i6;
        m += i4 + i7;
        arrayOfInt2[(0 + i13)] = (n + m + 1024 >> 11);
        arrayOfInt2[(56 + i13)] = (n - m + 1024 >> 11);
        arrayOfInt2[(8 + i13)] = (i1 + k + 1024 >> 11);
        arrayOfInt2[(48 + i13)] = (i1 - k + 1024 >> 11);
        arrayOfInt2[(16 + i13)] = (i2 + j + 1024 >> 11);
        arrayOfInt2[(40 + i13)] = (i2 - j + 1024 >> 11);
        arrayOfInt2[(24 + i13)] = (i3 + i + 1024 >> 11);
        arrayOfInt2[(32 + i13)] = (i3 - i + 1024 >> 11);
        i11++;
        i12++;
        i13++;
      }
    int i14 = 0;
    arrayOfInt2 = arrayOfInt3;
    i13 = 0;
    for (i10 = 0; i10 < 8; i10++)
    {
      byte[] arrayOfByte1 = paramArrayOfByte[(i10 + paramInt1)];
      i14 = paramInt2;
      if ((arrayOfInt2[(1 + i13)] == 0) && (arrayOfInt2[(2 + i13)] == 0) && (arrayOfInt2[(3 + i13)] == 0) && (arrayOfInt2[(4 + i13)] == 0) && (arrayOfInt2[(5 + i13)] == 0) && (arrayOfInt2[(6 + i13)] == 0) && (arrayOfInt2[(7 + i13)] == 0))
      {
        int i15 = arrayOfByte2[(i9 + (arrayOfInt2[(0 + i13)] + 16 >> 5 & 0x3FF))];
        arrayOfByte1[(0 + i14)] = i15;
        arrayOfByte1[(1 + i14)] = i15;
        arrayOfByte1[(2 + i14)] = i15;
        arrayOfByte1[(3 + i14)] = i15;
        arrayOfByte1[(4 + i14)] = i15;
        arrayOfByte1[(5 + i14)] = i15;
        arrayOfByte1[(6 + i14)] = i15;
        arrayOfByte1[(7 + i14)] = i15;
        i13 += 8;
      }
      else
      {
        i5 = arrayOfInt2[(2 + i13)];
        i6 = arrayOfInt2[(6 + i13)];
        i4 = (i5 + i6) * 4433;
        k = i4 + i6 * -15137;
        m = i4 + i5 * 6270;
        i = arrayOfInt2[(0 + i13)] + arrayOfInt2[(4 + i13)] << 13;
        j = arrayOfInt2[(0 + i13)] - arrayOfInt2[(4 + i13)] << 13;
        n = i + m;
        i3 = i - m;
        i1 = j + k;
        i2 = j - k;
        i = arrayOfInt2[(7 + i13)];
        j = arrayOfInt2[(5 + i13)];
        k = arrayOfInt2[(3 + i13)];
        m = arrayOfInt2[(1 + i13)];
        i4 = i + m;
        i5 = j + k;
        i6 = i + k;
        i7 = j + m;
        i8 = (i6 + i7) * 9633;
        i *= 2446;
        j *= 16819;
        k *= 25172;
        m *= 12299;
        i4 *= -7373;
        i5 *= -20995;
        i6 *= -16069;
        i7 *= -3196;
        i6 += i8;
        i7 += i8;
        i += i4 + i6;
        j += i5 + i7;
        k += i5 + i6;
        m += i4 + i7;
        arrayOfByte1[(0 + i14)] = arrayOfByte2[(i9 + (n + m + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(7 + i14)] = arrayOfByte2[(i9 + (n - m + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(1 + i14)] = arrayOfByte2[(i9 + (i1 + k + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(6 + i14)] = arrayOfByte2[(i9 + (i1 - k + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(2 + i14)] = arrayOfByte2[(i9 + (i2 + j + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(5 + i14)] = arrayOfByte2[(i9 + (i2 - j + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(3 + i14)] = arrayOfByte2[(i9 + (i3 + i + 131072 >> 18 & 0x3FF))];
        arrayOfByte1[(4 + i14)] = arrayOfByte2[(i9 + (i3 - i + 131072 >> 18 & 0x3FF))];
        i13 += 8;
      }
    }
  }

  static void upsample(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, byte[][] paramArrayOfByte1, int[] paramArrayOfInt3, int paramInt2)
  {
    sep_upsample(paramjpeg_decompress_struct, paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2, paramInt1, paramArrayOfByte1, paramArrayOfInt3, paramInt2);
  }

  static boolean smoothing_ok(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    boolean bool = false;
    if ((!paramjpeg_decompress_struct.progressive_mode) || (paramjpeg_decompress_struct.coef_bits == null))
      return false;
    if (localjpeg_d_coef_controller.coef_bits_latch == null)
      localjpeg_d_coef_controller.coef_bits_latch = new int[paramjpeg_decompress_struct.num_components * 6];
    int[] arrayOfInt2 = localjpeg_d_coef_controller.coef_bits_latch;
    int k = 0;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      JQUANT_TBL localJQUANT_TBL;
      if ((localJQUANT_TBL = localjpeg_component_info.quant_table) == null)
        return false;
      if ((localJQUANT_TBL.quantval[0] == 0) || (localJQUANT_TBL.quantval[1] == 0) || (localJQUANT_TBL.quantval[8] == 0) || (localJQUANT_TBL.quantval[16] == 0) || (localJQUANT_TBL.quantval[9] == 0) || (localJQUANT_TBL.quantval[2] == 0))
        return false;
      int[] arrayOfInt1 = paramjpeg_decompress_struct.coef_bits[i];
      if (arrayOfInt1[0] < 0)
        return false;
      for (int j = 1; j <= 5; j++)
      {
        arrayOfInt2[(j + k)] = arrayOfInt1[j];
        if (arrayOfInt1[j] != 0)
          bool = true;
      }
      k += 6;
    }
    return bool;
  }

  static void master_selection(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_decomp_master localjpeg_decomp_master = paramjpeg_decompress_struct.master;
    jpeg_calc_output_dimensions(paramjpeg_decompress_struct);
    prepare_range_limit_table(paramjpeg_decompress_struct);
    long l = paramjpeg_decompress_struct.output_width * paramjpeg_decompress_struct.out_color_components;
    int i = (int)l;
    if (i != l)
      error();
    localjpeg_decomp_master.pass_number = 0;
    localjpeg_decomp_master.using_merged_upsample = use_merged_upsample(paramjpeg_decompress_struct);
    localjpeg_decomp_master.quantizer_1pass = null;
    localjpeg_decomp_master.quantizer_2pass = null;
    if ((!paramjpeg_decompress_struct.quantize_colors) || (!paramjpeg_decompress_struct.buffered_image))
    {
      paramjpeg_decompress_struct.enable_1pass_quant = false;
      paramjpeg_decompress_struct.enable_external_quant = false;
      paramjpeg_decompress_struct.enable_2pass_quant = false;
    }
    if (paramjpeg_decompress_struct.quantize_colors)
      error(20);
    if (!paramjpeg_decompress_struct.raw_data_out)
    {
      if (localjpeg_decomp_master.using_merged_upsample)
      {
        error();
      }
      else
      {
        jinit_color_deconverter(paramjpeg_decompress_struct);
        jinit_upsampler(paramjpeg_decompress_struct);
      }
      jinit_d_post_controller(paramjpeg_decompress_struct, paramjpeg_decompress_struct.enable_2pass_quant);
    }
    jinit_inverse_dct(paramjpeg_decompress_struct);
    if (paramjpeg_decompress_struct.arith_code)
      error();
    else if (paramjpeg_decompress_struct.progressive_mode)
      jinit_phuff_decoder(paramjpeg_decompress_struct);
    else
      jinit_huff_decoder(paramjpeg_decompress_struct);
    boolean bool = (paramjpeg_decompress_struct.inputctl.has_multiple_scans) || (paramjpeg_decompress_struct.buffered_image);
    jinit_d_coef_controller(paramjpeg_decompress_struct, bool);
    if (!paramjpeg_decompress_struct.raw_data_out)
      jinit_d_main_controller(paramjpeg_decompress_struct, false);
    start_input_pass(paramjpeg_decompress_struct);
  }

  static void jinit_master_decompress(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_decomp_master localjpeg_decomp_master = new jpeg_decomp_master();
    paramjpeg_decompress_struct.master = localjpeg_decomp_master;
    localjpeg_decomp_master.is_dummy_pass = false;
    master_selection(paramjpeg_decompress_struct);
  }

  static void jcopy_sample_rows(byte[][] paramArrayOfByte1, int paramInt1, byte[][] paramArrayOfByte2, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt4;
    int k = paramInt1;
    int m = paramInt2;
    for (int j = paramInt3; j > 0; j--)
    {
      byte[] arrayOfByte1 = paramArrayOfByte1[(k++)];
      byte[] arrayOfByte2 = paramArrayOfByte2[(m++)];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, i);
    }
  }

  static boolean jpeg_start_decompress(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.global_state == 202)
    {
      jinit_master_decompress(paramjpeg_decompress_struct);
      if (paramjpeg_decompress_struct.buffered_image)
      {
        paramjpeg_decompress_struct.global_state = 207;
        return true;
      }
      paramjpeg_decompress_struct.global_state = 203;
    }
    if (paramjpeg_decompress_struct.global_state == 203)
    {
      if (paramjpeg_decompress_struct.inputctl.has_multiple_scans)
      {
        int i;
        do
        {
          i = consume_input(paramjpeg_decompress_struct);
          if (i == 0)
            return false;
        }
        while (i != 2);
      }
      paramjpeg_decompress_struct.output_scan_number = paramjpeg_decompress_struct.input_scan_number;
    }
    else if (paramjpeg_decompress_struct.global_state != 204)
    {
      error();
    }
    return output_pass_setup(paramjpeg_decompress_struct);
  }

  static void prepare_for_output_pass(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_decomp_master localjpeg_decomp_master = paramjpeg_decompress_struct.master;
    if (localjpeg_decomp_master.is_dummy_pass)
    {
      error(20);
    }
    else
    {
      if ((paramjpeg_decompress_struct.quantize_colors) && (paramjpeg_decompress_struct.colormap == null))
        if ((paramjpeg_decompress_struct.two_pass_quantize) && (paramjpeg_decompress_struct.enable_2pass_quant))
        {
          paramjpeg_decompress_struct.cquantize = localjpeg_decomp_master.quantizer_2pass;
          localjpeg_decomp_master.is_dummy_pass = true;
        }
        else if (paramjpeg_decompress_struct.enable_1pass_quant)
        {
          paramjpeg_decompress_struct.cquantize = localjpeg_decomp_master.quantizer_1pass;
        }
        else
        {
          error();
        }
      paramjpeg_decompress_struct.idct.start_pass(paramjpeg_decompress_struct);
      start_output_pass(paramjpeg_decompress_struct);
      if (!paramjpeg_decompress_struct.raw_data_out)
      {
        if (!localjpeg_decomp_master.using_merged_upsample)
          paramjpeg_decompress_struct.cconvert.start_pass(paramjpeg_decompress_struct);
        paramjpeg_decompress_struct.upsample.start_pass(paramjpeg_decompress_struct);
        if (paramjpeg_decompress_struct.quantize_colors)
          paramjpeg_decompress_struct.cquantize.start_pass(paramjpeg_decompress_struct, localjpeg_decomp_master.is_dummy_pass);
        paramjpeg_decompress_struct.post.start_pass(paramjpeg_decompress_struct, localjpeg_decomp_master.is_dummy_pass ? 3 : 0);
        paramjpeg_decompress_struct.main.start_pass(paramjpeg_decompress_struct, 0);
      }
    }
  }

  static boolean jpeg_resync_to_restart(jpeg_decompress_struct paramjpeg_decompress_struct, int paramInt)
  {
    int i = paramjpeg_decompress_struct.unread_marker;
    int j = 1;
    while (true)
    {
      if (i < 192)
        j = 2;
      else if ((i < 208) || (i > 215))
        j = 3;
      else if ((i == 208 + (paramInt + 1 & 0x7)) || (i == 208 + (paramInt + 2 & 0x7)))
        j = 3;
      else if ((i == 208 + (paramInt - 1 & 0x7)) || (i == 208 + (paramInt - 2 & 0x7)))
        j = 2;
      else
        j = 1;
      switch (j)
      {
      case 1:
        paramjpeg_decompress_struct.unread_marker = 0;
        return true;
      case 2:
        if (!next_marker(paramjpeg_decompress_struct))
          return false;
        i = paramjpeg_decompress_struct.unread_marker;
        break;
      case 3:
        return true;
      }
    }
  }

  static boolean read_restart_marker(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if ((paramjpeg_decompress_struct.unread_marker == 0) && (!next_marker(paramjpeg_decompress_struct)))
      return false;
    if (paramjpeg_decompress_struct.unread_marker == 208 + paramjpeg_decompress_struct.marker.next_restart_num)
      paramjpeg_decompress_struct.unread_marker = 0;
    else if (!jpeg_resync_to_restart(paramjpeg_decompress_struct, paramjpeg_decompress_struct.marker.next_restart_num))
      return false;
    paramjpeg_decompress_struct.marker.next_restart_num = (paramjpeg_decompress_struct.marker.next_restart_num + 1 & 0x7);
    return true;
  }

  static boolean jpeg_fill_bit_buffer(bitread_working_state parambitread_working_state, int paramInt1, int paramInt2, int paramInt3)
  {
    byte[] arrayOfByte = parambitread_working_state.buffer;
    int i = parambitread_working_state.bytes_in_buffer;
    int j = parambitread_working_state.bytes_offset;
    jpeg_decompress_struct localjpeg_decompress_struct = parambitread_working_state.cinfo;
    if (localjpeg_decompress_struct.unread_marker == 0)
    {
      while (paramInt2 < 25)
      {
        if (j == i)
        {
          if (!fill_input_buffer(localjpeg_decompress_struct))
            return false;
          arrayOfByte = localjpeg_decompress_struct.buffer;
          i = localjpeg_decompress_struct.bytes_in_buffer;
          j = localjpeg_decompress_struct.bytes_offset;
        }
        int k = arrayOfByte[(j++)] & 0xFF;
        if (k == 255)
        {
          do
          {
            if (j == i)
            {
              if (!fill_input_buffer(localjpeg_decompress_struct))
                return false;
              arrayOfByte = localjpeg_decompress_struct.buffer;
              i = localjpeg_decompress_struct.bytes_in_buffer;
              j = localjpeg_decompress_struct.bytes_offset;
            }
            k = arrayOfByte[(j++)] & 0xFF;
          }
          while (k == 255);
          if (k == 0)
          {
            k = 255;
          }
          else
          {
            localjpeg_decompress_struct.unread_marker = k;
            if (paramInt3 > paramInt2)
            {
              if (!localjpeg_decompress_struct.entropy.insufficient_data)
                localjpeg_decompress_struct.entropy.insufficient_data = true;
              paramInt1 <<= 25 - paramInt2;
              paramInt2 = 25;
            }
            parambitread_working_state.buffer = arrayOfByte;
            parambitread_working_state.bytes_in_buffer = i;
            parambitread_working_state.bytes_offset = j;
            parambitread_working_state.get_buffer = paramInt1;
            parambitread_working_state.bits_left = paramInt2;
            return true;
          }
        }
        paramInt1 = paramInt1 << 8 | k;
        paramInt2 += 8;
      }
    }
    else if (paramInt3 > paramInt2)
    {
      if (!localjpeg_decompress_struct.entropy.insufficient_data)
        localjpeg_decompress_struct.entropy.insufficient_data = true;
      paramInt1 <<= 25 - paramInt2;
      paramInt2 = 25;
    }
    parambitread_working_state.buffer = arrayOfByte;
    parambitread_working_state.bytes_in_buffer = i;
    parambitread_working_state.bytes_offset = j;
    parambitread_working_state.get_buffer = paramInt1;
    parambitread_working_state.bits_left = paramInt2;
    return true;
  }

  static int jpeg_huff_decode(bitread_working_state parambitread_working_state, int paramInt1, int paramInt2, d_derived_tbl paramd_derived_tbl, int paramInt3)
  {
    int i = paramInt3;
    if (paramInt2 < i)
    {
      if (!jpeg_fill_bit_buffer(parambitread_working_state, paramInt1, paramInt2, i))
        return -1;
      paramInt1 = parambitread_working_state.get_buffer;
      paramInt2 = parambitread_working_state.bits_left;
    }
    int j = paramInt1 >> paramInt2 -= i & (1 << i) - 1;
    while (j > paramd_derived_tbl.maxcode[i])
    {
      j <<= 1;
      if (paramInt2 < 1)
      {
        if (!jpeg_fill_bit_buffer(parambitread_working_state, paramInt1, paramInt2, 1))
          return -1;
        paramInt1 = parambitread_working_state.get_buffer;
        paramInt2 = parambitread_working_state.bits_left;
      }
      j |= paramInt1 >> --paramInt2 & 0x1;
      i++;
    }
    parambitread_working_state.get_buffer = paramInt1;
    parambitread_working_state.bits_left = paramInt2;
    if (i > 16)
      return 0;
    return paramd_derived_tbl.pub.huffval[(j + paramd_derived_tbl.valoffset[i])] & 0xFF;
  }

  static int decompress_onepass(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    int j = paramjpeg_decompress_struct.MCUs_per_row - 1;
    int k = paramjpeg_decompress_struct.total_iMCU_rows - 1;
    for (int i3 = localjpeg_d_coef_controller.MCU_vert_offset; i3 < localjpeg_d_coef_controller.MCU_rows_per_iMCU_row; i3++)
    {
      for (int i = localjpeg_d_coef_controller.MCU_ctr; i <= j; i++)
      {
        for (int i7 = 0; i7 < paramjpeg_decompress_struct.blocks_in_MCU; i7++)
        {
          short[] arrayOfShort = localjpeg_d_coef_controller.MCU_buffer[i7];
          for (int i8 = 0; i8 < arrayOfShort.length; i8++)
            arrayOfShort[i8] = 0;
        }
        if (!paramjpeg_decompress_struct.entropy.decode_mcu(paramjpeg_decompress_struct, localjpeg_d_coef_controller.MCU_buffer))
        {
          localjpeg_d_coef_controller.MCU_vert_offset = i3;
          localjpeg_d_coef_controller.MCU_ctr = i;
          return 0;
        }
        int m = 0;
        for (int n = 0; n < paramjpeg_decompress_struct.comps_in_scan; n++)
        {
          jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[n];
          if (!localjpeg_component_info.component_needed)
          {
            m += localjpeg_component_info.MCU_blocks;
          }
          else
          {
            int i4 = i < j ? localjpeg_component_info.MCU_width : localjpeg_component_info.last_col_width;
            byte[][] arrayOfByte = paramArrayOfByte[localjpeg_component_info.component_index];
            i7 = paramArrayOfInt[localjpeg_component_info.component_index] + i3 * localjpeg_component_info.DCT_scaled_size;
            int i5 = i * localjpeg_component_info.MCU_sample_width;
            for (int i2 = 0; i2 < localjpeg_component_info.MCU_height; i2++)
            {
              if ((paramjpeg_decompress_struct.input_iMCU_row < k) || (i3 + i2 < localjpeg_component_info.last_row_height))
              {
                int i6 = i5;
                for (int i1 = 0; i1 < i4; i1++)
                {
                  jpeg_idct_islow(paramjpeg_decompress_struct, localjpeg_component_info, localjpeg_d_coef_controller.MCU_buffer[(m + i1)], arrayOfByte, i7, i6);
                  i6 += localjpeg_component_info.DCT_scaled_size;
                }
              }
              m += localjpeg_component_info.MCU_width;
              i7 += localjpeg_component_info.DCT_scaled_size;
            }
          }
        }
      }
      localjpeg_d_coef_controller.MCU_ctr = 0;
    }
    paramjpeg_decompress_struct.output_iMCU_row += 1;
    if (++paramjpeg_decompress_struct.input_iMCU_row < paramjpeg_decompress_struct.total_iMCU_rows)
    {
      localjpeg_d_coef_controller.start_iMCU_row(paramjpeg_decompress_struct);
      return 3;
    }
    finish_input_pass(paramjpeg_decompress_struct);
    return 4;
  }

  static int decompress_smooth_data(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    int i = paramjpeg_decompress_struct.total_iMCU_rows - 1;
    short[] arrayOfShort4 = localjpeg_d_coef_controller.workspace;
    if (arrayOfShort4 == null)
      arrayOfShort4 = localjpeg_d_coef_controller.workspace = new short[64];
    int i24;
    while ((paramjpeg_decompress_struct.input_scan_number <= paramjpeg_decompress_struct.output_scan_number) && (!paramjpeg_decompress_struct.inputctl.eoi_reached))
    {
      if (paramjpeg_decompress_struct.input_scan_number == paramjpeg_decompress_struct.output_scan_number)
      {
        i24 = paramjpeg_decompress_struct.Ss == 0 ? 1 : 0;
        if (paramjpeg_decompress_struct.input_iMCU_row > paramjpeg_decompress_struct.output_iMCU_row + i24)
          break;
      }
      if (consume_input(paramjpeg_decompress_struct) == 0)
        return 0;
    }
    for (int m = 0; m < paramjpeg_decompress_struct.num_components; m++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[m];
      if (localjpeg_component_info.component_needed)
      {
        int i1;
        int i2;
        int i5;
        if (paramjpeg_decompress_struct.output_iMCU_row < i)
        {
          i1 = localjpeg_component_info.v_samp_factor;
          i2 = i1 * 2;
          i5 = 0;
        }
        else
        {
          i1 = localjpeg_component_info.height_in_blocks % localjpeg_component_info.v_samp_factor;
          if (i1 == 0)
            i1 = localjpeg_component_info.v_samp_factor;
          i2 = i1;
          i5 = 1;
        }
        short[][][] arrayOfShort;
        int i4;
        if (paramjpeg_decompress_struct.output_iMCU_row > 0)
        {
          i2 += localjpeg_component_info.v_samp_factor;
          arrayOfShort = localjpeg_d_coef_controller.whole_image[m];
          i24 = (paramjpeg_decompress_struct.output_iMCU_row - 1) * localjpeg_component_info.v_samp_factor;
          i24 += localjpeg_component_info.v_samp_factor;
          i4 = 0;
        }
        else
        {
          arrayOfShort = localjpeg_d_coef_controller.whole_image[m];
          i24 = 0;
          i4 = 1;
        }
        int[] arrayOfInt = localjpeg_d_coef_controller.coef_bits_latch;
        int i25 = m * 6;
        JQUANT_TBL localJQUANT_TBL = localjpeg_component_info.quant_table;
        int i6 = localJQUANT_TBL.quantval[0];
        int i7 = localJQUANT_TBL.quantval[1];
        int i9 = localJQUANT_TBL.quantval[8];
        int i11 = localJQUANT_TBL.quantval[16];
        int i10 = localJQUANT_TBL.quantval[9];
        int i8 = localJQUANT_TBL.quantval[2];
        byte[][] arrayOfByte = paramArrayOfByte[m];
        int i26 = paramArrayOfInt[m];
        for (int n = 0; n < i1; n++)
        {
          short[][] arrayOfShort1 = arrayOfShort[(n + i24)];
          int i27 = 0;
          int i28 = 0;
          int i29 = 0;
          short[][] arrayOfShort2;
          if ((i4 != 0) && (n == 0))
          {
            arrayOfShort2 = arrayOfShort1;
            i28 = i27;
          }
          else
          {
            arrayOfShort2 = arrayOfShort[(n - 1 + i24)];
            i28 = 0;
          }
          short[][] arrayOfShort3;
          if ((i5 != 0) && (n == i1 - 1))
          {
            arrayOfShort3 = arrayOfShort1;
            i29 = i27;
          }
          else
          {
            arrayOfShort3 = arrayOfShort[(n + 1 + i24)];
            i29 = 0;
          }
          int i15;
          int i14;
          int i13 = i14 = i15 = arrayOfShort2[(0 + i28)][0];
          int i18;
          int i17;
          int i16 = i17 = i18 = arrayOfShort1[(0 + i27)][0];
          int i21;
          int i20;
          int i19 = i20 = i21 = arrayOfShort3[(0 + i29)][0];
          int i3 = 0;
          int k = localjpeg_component_info.width_in_blocks - 1;
          for (int j = 0; j <= k; j++)
          {
            System.arraycopy(arrayOfShort1[i27], 0, arrayOfShort4, 0, arrayOfShort4.length);
            if (j < k)
            {
              i15 = arrayOfShort2[(1 + i28)][0];
              i18 = arrayOfShort1[(1 + i27)][0];
              i21 = arrayOfShort3[(1 + i29)][0];
            }
            int i22;
            int i12;
            int i23;
            if (((i22 = arrayOfInt[(1 + i25)]) != 0) && (arrayOfShort4[1] == 0))
            {
              i12 = 36 * i6 * (i16 - i18);
              if (i12 >= 0)
              {
                i23 = ((i7 << 7) + i12) / (i7 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
              }
              else
              {
                i23 = ((i7 << 7) - i12) / (i7 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
                i23 = -i23;
              }
              arrayOfShort4[1] = ((short)i23);
            }
            if (((i22 = arrayOfInt[(2 + i25)]) != 0) && (arrayOfShort4[8] == 0))
            {
              i12 = 36 * i6 * (i14 - i20);
              if (i12 >= 0)
              {
                i23 = ((i9 << 7) + i12) / (i9 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
              }
              else
              {
                i23 = ((i9 << 7) - i12) / (i9 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
                i23 = -i23;
              }
              arrayOfShort4[8] = ((short)i23);
            }
            if (((i22 = arrayOfInt[(3 + i25)]) != 0) && (arrayOfShort4[16] == 0))
            {
              i12 = 9 * i6 * (i14 + i20 - 2 * i17);
              if (i12 >= 0)
              {
                i23 = ((i11 << 7) + i12) / (i11 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
              }
              else
              {
                i23 = ((i11 << 7) - i12) / (i11 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
                i23 = -i23;
              }
              arrayOfShort4[16] = ((short)i23);
            }
            if (((i22 = arrayOfInt[(4 + i25)]) != 0) && (arrayOfShort4[9] == 0))
            {
              i12 = 5 * i6 * (i13 - i15 - i19 + i21);
              if (i12 >= 0)
              {
                i23 = ((i10 << 7) + i12) / (i10 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
              }
              else
              {
                i23 = ((i10 << 7) - i12) / (i10 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
                i23 = -i23;
              }
              arrayOfShort4[9] = ((short)i23);
            }
            if (((i22 = arrayOfInt[(5 + i25)]) != 0) && (arrayOfShort4[2] == 0))
            {
              i12 = 9 * i6 * (i16 + i18 - 2 * i17);
              if (i12 >= 0)
              {
                i23 = ((i8 << 7) + i12) / (i8 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
              }
              else
              {
                i23 = ((i8 << 7) - i12) / (i8 << 8);
                if ((i22 > 0) && (i23 >= 1 << i22))
                  i23 = (1 << i22) - 1;
                i23 = -i23;
              }
              arrayOfShort4[2] = ((short)i23);
            }
            jpeg_idct_islow(paramjpeg_decompress_struct, localjpeg_component_info, arrayOfShort4, arrayOfByte, i26, i3);
            i13 = i14;
            i14 = i15;
            i16 = i17;
            i17 = i18;
            i19 = i20;
            i20 = i21;
            i27++;
            i28++;
            i29++;
            i3 += localjpeg_component_info.DCT_scaled_size;
          }
          i26 += localjpeg_component_info.DCT_scaled_size;
        }
      }
    }
    if (++paramjpeg_decompress_struct.output_iMCU_row < paramjpeg_decompress_struct.total_iMCU_rows)
      return 3;
    return 4;
  }

  static int decompress_data(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    int i = paramjpeg_decompress_struct.total_iMCU_rows - 1;
    while ((paramjpeg_decompress_struct.input_scan_number < paramjpeg_decompress_struct.output_scan_number) || ((paramjpeg_decompress_struct.input_scan_number == paramjpeg_decompress_struct.output_scan_number) && (paramjpeg_decompress_struct.input_iMCU_row <= paramjpeg_decompress_struct.output_iMCU_row)))
      if (consume_input(paramjpeg_decompress_struct) == 0)
        return 0;
    for (int k = 0; k < paramjpeg_decompress_struct.num_components; k++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[k];
      if (localjpeg_component_info.component_needed)
      {
        short[][][] arrayOfShort = localjpeg_d_coef_controller.whole_image[k];
        int i2 = paramjpeg_decompress_struct.output_iMCU_row * localjpeg_component_info.v_samp_factor;
        int n;
        if (paramjpeg_decompress_struct.output_iMCU_row < i)
        {
          n = localjpeg_component_info.v_samp_factor;
        }
        else
        {
          n = localjpeg_component_info.height_in_blocks % localjpeg_component_info.v_samp_factor;
          if (n == 0)
            n = localjpeg_component_info.v_samp_factor;
        }
        byte[][] arrayOfByte = paramArrayOfByte[k];
        int i3 = paramArrayOfInt[k];
        for (int m = 0; m < n; m++)
        {
          short[][] arrayOfShort1 = arrayOfShort[(m + i2)];
          int i4 = 0;
          int i1 = 0;
          for (int j = 0; j < localjpeg_component_info.width_in_blocks; j++)
          {
            jpeg_idct_islow(paramjpeg_decompress_struct, localjpeg_component_info, arrayOfShort1[i4], arrayOfByte, i3, i1);
            i4++;
            i1 += localjpeg_component_info.DCT_scaled_size;
          }
          i3 += localjpeg_component_info.DCT_scaled_size;
        }
      }
    }
    if (++paramjpeg_decompress_struct.output_iMCU_row < paramjpeg_decompress_struct.total_iMCU_rows)
      return 3;
    return 4;
  }

  static void post_process_data(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, byte[][] paramArrayOfByte1, int[] paramArrayOfInt3, int paramInt2)
  {
    upsample(paramjpeg_decompress_struct, paramArrayOfByte, paramArrayOfInt1, paramArrayOfInt2, paramInt1, paramArrayOfByte1, paramArrayOfInt3, paramInt2);
  }

  static void set_bottom_pointers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int m = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size;
      int k = m / paramjpeg_decompress_struct.min_DCT_scaled_size;
      int n = localjpeg_component_info.downsampled_height % m;
      if (n == 0)
        n = m;
      if (i == 0)
        localjpeg_d_main_controller.rowgroups_avail = ((n - 1) / k + 1);
      byte[][] arrayOfByte = localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr][i];
      int i1 = localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr][i];
      for (int j = 0; j < k * 2; j++)
        arrayOfByte[(n + j + i1)] = arrayOfByte[(n - 1 + i1)];
    }
  }

  static void set_wraparound_pointers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    int m = paramjpeg_decompress_struct.min_DCT_scaled_size;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      int k = localjpeg_component_info.v_samp_factor * localjpeg_component_info.DCT_scaled_size / paramjpeg_decompress_struct.min_DCT_scaled_size;
      byte[][] arrayOfByte1 = localjpeg_d_main_controller.xbuffer[0][i];
      int n = localjpeg_d_main_controller.xbuffer_offset[0][i];
      byte[][] arrayOfByte2 = localjpeg_d_main_controller.xbuffer[1][i];
      int i1 = localjpeg_d_main_controller.xbuffer_offset[1][i];
      for (int j = 0; j < k; j++)
      {
        arrayOfByte1[(j - k + n)] = arrayOfByte1[(k * (m + 1) + j + n)];
        arrayOfByte2[(j - k + i1)] = arrayOfByte2[(k * (m + 1) + j + i1)];
        arrayOfByte1[(k * (m + 2) + j + n)] = arrayOfByte1[(j + n)];
        arrayOfByte2[(k * (m + 2) + j + i1)] = arrayOfByte2[(j + i1)];
      }
    }
  }

  static void process_data_crank_post(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt)
  {
    error();
  }

  static void process_data_context_main(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    if (!localjpeg_d_main_controller.buffer_full)
    {
      int i;
      switch (paramjpeg_decompress_struct.coef.decompress_data)
      {
      case 0:
        i = decompress_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr]);
        break;
      case 1:
        i = decompress_smooth_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr]);
        break;
      case 2:
        i = decompress_onepass(paramjpeg_decompress_struct, localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr]);
        break;
      default:
        i = 0;
      }
      if (i == 0)
        return;
      localjpeg_d_main_controller.buffer_full = true;
      localjpeg_d_main_controller.iMCU_row_ctr += 1;
    }
    switch (localjpeg_d_main_controller.context_state)
    {
    case 2:
      post_process_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.rowgroup_ctr, localjpeg_d_main_controller.rowgroups_avail, paramArrayOfByte, paramArrayOfInt, paramInt);
      if (localjpeg_d_main_controller.rowgroup_ctr[0] < localjpeg_d_main_controller.rowgroups_avail)
        return;
      localjpeg_d_main_controller.context_state = 0;
      if (paramArrayOfInt[0] >= paramInt)
        return;
    case 0:
      localjpeg_d_main_controller.rowgroup_ctr[0] = 0;
      localjpeg_d_main_controller.rowgroups_avail = (paramjpeg_decompress_struct.min_DCT_scaled_size - 1);
      if (localjpeg_d_main_controller.iMCU_row_ctr == paramjpeg_decompress_struct.total_iMCU_rows)
        set_bottom_pointers(paramjpeg_decompress_struct);
      localjpeg_d_main_controller.context_state = 1;
    case 1:
      post_process_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.xbuffer[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.xbuffer_offset[localjpeg_d_main_controller.whichptr], localjpeg_d_main_controller.rowgroup_ctr, localjpeg_d_main_controller.rowgroups_avail, paramArrayOfByte, paramArrayOfInt, paramInt);
      if (localjpeg_d_main_controller.rowgroup_ctr[0] < localjpeg_d_main_controller.rowgroups_avail)
        return;
      if (localjpeg_d_main_controller.iMCU_row_ctr == 1)
        set_wraparound_pointers(paramjpeg_decompress_struct);
      localjpeg_d_main_controller.whichptr ^= 1;
      localjpeg_d_main_controller.buffer_full = false;
      localjpeg_d_main_controller.rowgroup_ctr[0] = (paramjpeg_decompress_struct.min_DCT_scaled_size + 1);
      localjpeg_d_main_controller.rowgroups_avail = (paramjpeg_decompress_struct.min_DCT_scaled_size + 2);
      localjpeg_d_main_controller.context_state = 2;
    }
  }

  static void process_data_simple_main(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt)
  {
    jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
    if (!localjpeg_d_main_controller.buffer_full)
    {
      int j;
      switch (paramjpeg_decompress_struct.coef.decompress_data)
      {
      case 0:
        j = decompress_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.buffer, localjpeg_d_main_controller.buffer_offset);
        break;
      case 1:
        j = decompress_smooth_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.buffer, localjpeg_d_main_controller.buffer_offset);
        break;
      case 2:
        j = decompress_onepass(paramjpeg_decompress_struct, localjpeg_d_main_controller.buffer, localjpeg_d_main_controller.buffer_offset);
        break;
      default:
        j = 0;
      }
      if (j == 0)
        return;
      localjpeg_d_main_controller.buffer_full = true;
    }
    int i = paramjpeg_decompress_struct.min_DCT_scaled_size;
    post_process_data(paramjpeg_decompress_struct, localjpeg_d_main_controller.buffer, localjpeg_d_main_controller.buffer_offset, localjpeg_d_main_controller.rowgroup_ctr, i, paramArrayOfByte, paramArrayOfInt, paramInt);
    if (localjpeg_d_main_controller.rowgroup_ctr[0] >= i)
    {
      localjpeg_d_main_controller.buffer_full = false;
      localjpeg_d_main_controller.rowgroup_ctr[0] = 0;
    }
  }

  static int jpeg_read_scanlines(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][] paramArrayOfByte, int paramInt)
  {
    if (paramjpeg_decompress_struct.global_state != 205)
      error();
    if (paramjpeg_decompress_struct.output_scanline >= paramjpeg_decompress_struct.output_height)
      return 0;
    paramjpeg_decompress_struct.row_ctr[0] = 0;
    switch (paramjpeg_decompress_struct.main.process_data)
    {
    case 0:
      process_data_simple_main(paramjpeg_decompress_struct, paramArrayOfByte, paramjpeg_decompress_struct.row_ctr, paramInt);
      break;
    case 1:
      process_data_context_main(paramjpeg_decompress_struct, paramArrayOfByte, paramjpeg_decompress_struct.row_ctr, paramInt);
      break;
    case 2:
      process_data_crank_post(paramjpeg_decompress_struct, paramArrayOfByte, paramjpeg_decompress_struct.row_ctr, paramInt);
      break;
    default:
      error();
    }
    paramjpeg_decompress_struct.output_scanline += paramjpeg_decompress_struct.row_ctr[0];
    return paramjpeg_decompress_struct.row_ctr[0];
  }

  static boolean output_pass_setup(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.global_state != 204)
    {
      prepare_for_output_pass(paramjpeg_decompress_struct);
      paramjpeg_decompress_struct.output_scanline = 0;
      paramjpeg_decompress_struct.global_state = 204;
    }
    while (paramjpeg_decompress_struct.master.is_dummy_pass)
      error();
    paramjpeg_decompress_struct.global_state = (paramjpeg_decompress_struct.raw_data_out ? 206 : 205);
    return true;
  }

  static boolean get_dht(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    byte[] arrayOfByte1 = new byte[17];
    byte[] arrayOfByte2 = new byte[256];
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    i -= 2;
    while (i > 16)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int k = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      arrayOfByte1[0] = 0;
      int m = 0;
      for (int j = 1; j <= 16; j++)
      {
        if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
          fill_input_buffer(paramjpeg_decompress_struct);
        arrayOfByte1[j] = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)];
        m += (arrayOfByte1[j] & 0xFF);
      }
      i -= 17;
      if ((m > 256) || (m > i))
        error();
      for (j = 0; j < m; j++)
      {
        if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
          fill_input_buffer(paramjpeg_decompress_struct);
        arrayOfByte2[j] = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)];
      }
      i -= m;
      JHUFF_TBL localJHUFF_TBL;
      if ((k & 0x10) != 0)
      {
        k -= 16;
        localJHUFF_TBL = paramjpeg_decompress_struct.ac_huff_tbl_ptrs[k] =  = new JHUFF_TBL();
      }
      else
      {
        localJHUFF_TBL = paramjpeg_decompress_struct.dc_huff_tbl_ptrs[k] =  = new JHUFF_TBL();
      }
      if ((k < 0) || (k >= 4))
        error();
      System.arraycopy(arrayOfByte1, 0, localJHUFF_TBL.bits, 0, arrayOfByte1.length);
      System.arraycopy(arrayOfByte2, 0, localJHUFF_TBL.huffval, 0, arrayOfByte2.length);
    }
    if (i != 0)
      error();
    return true;
  }

  static boolean get_dqt(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    i -= 2;
    while (i > 0)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int j = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      int m = j >> 4;
      j &= 15;
      if (j >= 4)
        error();
      if (paramjpeg_decompress_struct.quant_tbl_ptrs[j] == null)
        paramjpeg_decompress_struct.quant_tbl_ptrs[j] = new JQUANT_TBL();
      JQUANT_TBL localJQUANT_TBL = paramjpeg_decompress_struct.quant_tbl_ptrs[j];
      for (int k = 0; k < 64; k++)
      {
        int n;
        if (m != 0)
        {
          if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
            fill_input_buffer(paramjpeg_decompress_struct);
          n = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
          if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
            fill_input_buffer(paramjpeg_decompress_struct);
          n |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
        }
        else
        {
          if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
            fill_input_buffer(paramjpeg_decompress_struct);
          n = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
        }
        localJQUANT_TBL.quantval[jpeg_natural_order[k]] = ((short)n);
      }
      i -= 65;
      if (m != 0)
        i -= 64;
    }
    if (i != 0)
      error();
    return true;
  }

  static boolean get_dri(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (i != 4)
      error();
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int j = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    j |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    paramjpeg_decompress_struct.restart_interval = j;
    return true;
  }

  static boolean get_dac(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    i -= 2;
    while (i > 0)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int j = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int k = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      i -= 2;
      if ((j < 0) || (j >= 32))
        error();
      if (j >= 16)
      {
        paramjpeg_decompress_struct.arith_ac_K[(j - 16)] = ((byte)k);
      }
      else
      {
        paramjpeg_decompress_struct.arith_dc_L[j] = ((byte)(k & 0xF));
        paramjpeg_decompress_struct.arith_dc_U[j] = ((byte)(k >> 4));
        if (paramjpeg_decompress_struct.arith_dc_L[j] > paramjpeg_decompress_struct.arith_dc_U[j])
          error();
      }
    }
    if (i != 0)
      error();
    return true;
  }

  static boolean get_sos(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_component_info localjpeg_component_info = null;
    if (!paramjpeg_decompress_struct.marker.saw_SOF)
      error();
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int m = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if ((i != m * 2 + 6) || (m < 1) || (m > 4))
      error();
    paramjpeg_decompress_struct.comps_in_scan = m;
    for (int j = 0; j < m; j++)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int i1 = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      n = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      for (int k = 0; k < paramjpeg_decompress_struct.num_components; k++)
      {
        localjpeg_component_info = paramjpeg_decompress_struct.comp_info[k];
        if (i1 == localjpeg_component_info.component_id)
          break;
      }
      if (k == paramjpeg_decompress_struct.num_components)
        error();
      paramjpeg_decompress_struct.cur_comp_info[j] = localjpeg_component_info;
      localjpeg_component_info.dc_tbl_no = (n >> 4 & 0xF);
      localjpeg_component_info.ac_tbl_no = (n & 0xF);
    }
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int n = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    paramjpeg_decompress_struct.Ss = n;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    n = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    paramjpeg_decompress_struct.Se = n;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    n = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    paramjpeg_decompress_struct.Ah = (n >> 4 & 0xF);
    paramjpeg_decompress_struct.Al = (n & 0xF);
    paramjpeg_decompress_struct.marker.next_restart_num = 0;
    paramjpeg_decompress_struct.input_scan_number += 1;
    return true;
  }

  static boolean get_sof(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramjpeg_decompress_struct.progressive_mode = paramBoolean1;
    paramjpeg_decompress_struct.arith_code = paramBoolean2;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.data_precision = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF);
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.image_height = ((paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8);
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.image_height |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.image_width = ((paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8);
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.image_width |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.num_components = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF);
    i -= 8;
    if (paramjpeg_decompress_struct.marker.saw_SOF)
      error();
    if ((paramjpeg_decompress_struct.image_height <= 0) || (paramjpeg_decompress_struct.image_width <= 0) || (paramjpeg_decompress_struct.num_components <= 0))
      error();
    if (i != paramjpeg_decompress_struct.num_components * 3)
      error();
    if (paramjpeg_decompress_struct.comp_info == null)
      paramjpeg_decompress_struct.comp_info = new jpeg_component_info[paramjpeg_decompress_struct.num_components];
    for (int k = 0; k < paramjpeg_decompress_struct.num_components; k++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[k] =  = new jpeg_component_info();
      localjpeg_component_info.component_index = k;
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      localjpeg_component_info.component_id = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF);
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      int j = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      localjpeg_component_info.h_samp_factor = (j >> 4 & 0xF);
      localjpeg_component_info.v_samp_factor = (j & 0xF);
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      localjpeg_component_info.quant_tbl_no = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF);
    }
    paramjpeg_decompress_struct.marker.saw_SOF = true;
    return true;
  }

  static void sep_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, byte[][] paramArrayOfByte1, int[] paramArrayOfInt3, int paramInt2)
  {
    jpeg_upsampler localjpeg_upsampler = paramjpeg_decompress_struct.upsample;
    if (localjpeg_upsampler.next_row_out >= paramjpeg_decompress_struct.max_v_samp_factor)
    {
      for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
      {
        jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
        int k = paramArrayOfInt1[i] + paramArrayOfInt2[0] * localjpeg_upsampler.rowgroup_height[i];
        switch (localjpeg_upsampler.methods[i])
        {
        case 0:
          noop_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 1:
          fullsize_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 2:
          h2v1_fancy_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 3:
          h2v1_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 4:
          h2v2_fancy_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 5:
          h2v2_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
          break;
        case 6:
          int_upsample(paramjpeg_decompress_struct, localjpeg_component_info, paramArrayOfByte[i], k, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, i);
        }
      }
      localjpeg_upsampler.next_row_out = 0;
    }
    int j = paramjpeg_decompress_struct.max_v_samp_factor - localjpeg_upsampler.next_row_out;
    if (j > localjpeg_upsampler.rows_to_go)
      j = localjpeg_upsampler.rows_to_go;
    paramInt2 -= paramArrayOfInt3[0];
    if (j > paramInt2)
      j = paramInt2;
    switch (paramjpeg_decompress_struct.cconvert.color_convert)
    {
    case 0:
      null_convert(paramjpeg_decompress_struct, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, localjpeg_upsampler.next_row_out, paramArrayOfByte1, paramArrayOfInt3[0], j);
      break;
    case 1:
      grayscale_convert(paramjpeg_decompress_struct, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, localjpeg_upsampler.next_row_out, paramArrayOfByte1, paramArrayOfInt3[0], j);
      break;
    case 2:
      ycc_rgb_convert(paramjpeg_decompress_struct, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, localjpeg_upsampler.next_row_out, paramArrayOfByte1, paramArrayOfInt3[0], j);
      break;
    case 3:
      gray_rgb_convert(paramjpeg_decompress_struct, localjpeg_upsampler.color_buf, localjpeg_upsampler.color_buf_offset, localjpeg_upsampler.next_row_out, paramArrayOfByte1, paramArrayOfInt3[0], j);
      break;
    case 4:
      error();
    }
    paramArrayOfInt3[0] += j;
    localjpeg_upsampler.rows_to_go -= j;
    localjpeg_upsampler.next_row_out += j;
    if (localjpeg_upsampler.next_row_out >= paramjpeg_decompress_struct.max_v_samp_factor)
      paramArrayOfInt2[0] += 1;
  }

  static void noop_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    paramArrayOfByte1[paramInt2] = null;
  }

  static void fullsize_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    paramArrayOfByte1[paramInt2] = paramArrayOfByte;
    paramArrayOfInt[paramInt2] = paramInt1;
  }

  static void h2v1_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    byte[][] arrayOfByte = paramArrayOfByte1[paramInt2];
    paramArrayOfInt[paramInt2] = 0;
    for (int k = 0; k < paramjpeg_decompress_struct.max_v_samp_factor; k++)
    {
      byte[] arrayOfByte1 = paramArrayOfByte[(k + paramInt1)];
      byte[] arrayOfByte2 = arrayOfByte[k];
      int m = 0;
      int n = 0;
      int j = n + paramjpeg_decompress_struct.output_width;
      while (n < j)
      {
        int i = arrayOfByte1[(m++)];
        arrayOfByte2[(n++)] = i;
        arrayOfByte2[(n++)] = i;
      }
    }
  }

  static void h2v2_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    byte[][] arrayOfByte = paramArrayOfByte1[paramInt2];
    paramArrayOfInt[paramInt2] = 0;
    int m;
    int k = m = 0;
    while (m < paramjpeg_decompress_struct.max_v_samp_factor)
    {
      byte[] arrayOfByte1 = paramArrayOfByte[(k + paramInt1)];
      byte[] arrayOfByte2 = arrayOfByte[m];
      int n = 0;
      int i1 = 0;
      int j = i1 + paramjpeg_decompress_struct.output_width;
      while (i1 < j)
      {
        int i = arrayOfByte1[(n++)];
        arrayOfByte2[(i1++)] = i;
        arrayOfByte2[(i1++)] = i;
      }
      jcopy_sample_rows(arrayOfByte, m, arrayOfByte, m + 1, 1, paramjpeg_decompress_struct.output_width);
      k++;
      m += 2;
    }
  }

  static void h2v1_fancy_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    byte[][] arrayOfByte = paramArrayOfByte1[paramInt2];
    paramArrayOfInt[paramInt2] = 0;
    for (int k = 0; k < paramjpeg_decompress_struct.max_v_samp_factor; k++)
    {
      byte[] arrayOfByte1 = paramArrayOfByte[(k + paramInt1)];
      byte[] arrayOfByte2 = arrayOfByte[k];
      int m = 0;
      int n = 0;
      int i = arrayOfByte1[(m++)] & 0xFF;
      arrayOfByte2[(n++)] = ((byte)i);
      arrayOfByte2[(n++)] = ((byte)(i * 3 + (arrayOfByte1[m] & 0xFF) + 2 >> 2));
      for (int j = paramjpeg_component_info.downsampled_width - 2; j > 0; j--)
      {
        i = (arrayOfByte1[(m++)] & 0xFF) * 3;
        arrayOfByte2[(n++)] = ((byte)(i + (arrayOfByte1[(m - 2)] & 0xFF) + 1 >> 2));
        arrayOfByte2[(n++)] = ((byte)(i + (arrayOfByte1[m] & 0xFF) + 2 >> 2));
      }
      i = arrayOfByte1[m] & 0xFF;
      arrayOfByte2[(n++)] = ((byte)(i * 3 + (arrayOfByte1[(m - 1)] & 0xFF) + 1 >> 2));
      arrayOfByte2[(n++)] = ((byte)i);
    }
  }

  static void h2v2_fancy_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    byte[][] arrayOfByte = paramArrayOfByte1[paramInt2];
    paramArrayOfInt[paramInt2] = 0;
    int i1;
    for (int n = i1 = 0; i1 < paramjpeg_decompress_struct.max_v_samp_factor; n++)
      for (int i2 = 0; i2 < 2; i2++)
      {
        byte[] arrayOfByte1 = paramArrayOfByte[(n + paramInt1)];
        byte[] arrayOfByte2;
        if (i2 == 0)
          arrayOfByte2 = paramArrayOfByte[(n - 1 + paramInt1)];
        else
          arrayOfByte2 = paramArrayOfByte[(n + 1 + paramInt1)];
        byte[] arrayOfByte3 = arrayOfByte[(i1++)];
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i = (arrayOfByte1[(i3++)] & 0xFF) * 3 + (arrayOfByte2[(i4++)] & 0xFF);
        int k = (arrayOfByte1[(i3++)] & 0xFF) * 3 + (arrayOfByte2[(i4++)] & 0xFF);
        arrayOfByte3[(i5++)] = ((byte)(i * 4 + 8 >> 4));
        arrayOfByte3[(i5++)] = ((byte)(i * 3 + k + 7 >> 4));
        int j = i;
        i = k;
        for (int m = paramjpeg_component_info.downsampled_width - 2; m > 0; m--)
        {
          k = (arrayOfByte1[(i3++)] & 0xFF) * 3 + (arrayOfByte2[(i4++)] & 0xFF);
          arrayOfByte3[(i5++)] = ((byte)(i * 3 + j + 8 >> 4));
          arrayOfByte3[(i5++)] = ((byte)(i * 3 + k + 7 >> 4));
          j = i;
          i = k;
        }
        arrayOfByte3[(i5++)] = ((byte)(i * 3 + j + 8 >> 4));
        arrayOfByte3[(i5++)] = ((byte)(i * 4 + 7 >> 4));
      }
  }

  static void int_upsample(jpeg_decompress_struct paramjpeg_decompress_struct, jpeg_component_info paramjpeg_component_info, byte[][] paramArrayOfByte, int paramInt1, byte[][][] paramArrayOfByte1, int[] paramArrayOfInt, int paramInt2)
  {
    jpeg_upsampler localjpeg_upsampler = paramjpeg_decompress_struct.upsample;
    byte[][] arrayOfByte = paramArrayOfByte1[paramInt2];
    paramArrayOfInt[paramInt2] = 0;
    int m = localjpeg_upsampler.h_expand[paramjpeg_component_info.component_index];
    int n = localjpeg_upsampler.v_expand[paramjpeg_component_info.component_index];
    int i2;
    int i1 = i2 = 0;
    while (i2 < paramjpeg_decompress_struct.max_v_samp_factor)
    {
      byte[] arrayOfByte1 = paramArrayOfByte[(i1 + paramInt1)];
      int i3 = 0;
      byte[] arrayOfByte2 = arrayOfByte[i2];
      int i4 = 0;
      int k = i4 + paramjpeg_decompress_struct.output_width;
      while (i4 < k)
      {
        int i = arrayOfByte1[(i3++)];
        for (int j = m; j > 0; j--)
          arrayOfByte2[(i4++)] = i;
      }
      if (n > 1)
        jcopy_sample_rows(arrayOfByte, i2, arrayOfByte, i2 + 1, n - 1, paramjpeg_decompress_struct.output_width);
      i1++;
      i2 += n;
    }
  }

  static void null_convert(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt1, byte[][] paramArrayOfByte1, int paramInt2, int paramInt3)
  {
    int j = paramjpeg_decompress_struct.num_components;
    int k = paramjpeg_decompress_struct.output_width;
    do
    {
      for (int m = 0; m < j; m++)
      {
        byte[] arrayOfByte1 = paramArrayOfByte[m][(paramInt1 + paramArrayOfInt[0])];
        byte[] arrayOfByte2 = paramArrayOfByte1[paramInt2];
        int n = 0;
        switch (m)
        {
        case 2:
          n = 0;
          break;
        case 1:
          n = 1;
          break;
        case 0:
          n = 2;
        }
        int i1 = n;
        int i2 = 0;
        for (int i = k; i > 0; i--)
        {
          arrayOfByte2[i1] = arrayOfByte1[(i2++)];
          i1 += j;
        }
      }
      paramInt1++;
      paramInt2++;
      paramInt3--;
    }
    while (paramInt3 >= 0);
  }

  static void grayscale_convert(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt1, byte[][] paramArrayOfByte1, int paramInt2, int paramInt3)
  {
    jcopy_sample_rows(paramArrayOfByte[0], paramInt1 + paramArrayOfInt[0], paramArrayOfByte1, paramInt2, paramInt3, paramjpeg_decompress_struct.output_width);
  }

  static void gray_rgb_convert(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt1, byte[][] paramArrayOfByte1, int paramInt2, int paramInt3)
  {
    int j = paramjpeg_decompress_struct.output_width;
    do
    {
      byte[] arrayOfByte1 = paramArrayOfByte[0][(paramInt1++ + paramArrayOfInt[0])];
      byte[] arrayOfByte2 = paramArrayOfByte1[(paramInt2++)];
      int k = 0;
      for (int i = 0; i < j; i++)
      {
        byte tmp67_66 = (arrayOfByte2[(0 + k)] = arrayOfByte1[i]);
        arrayOfByte2[(1 + k)] = tmp67_66;
        arrayOfByte2[(2 + k)] = tmp67_66;
        k += 3;
      }
      paramInt3--;
    }
    while (paramInt3 >= 0);
  }

  static void ycc_rgb_convert(jpeg_decompress_struct paramjpeg_decompress_struct, byte[][][] paramArrayOfByte, int[] paramArrayOfInt, int paramInt1, byte[][] paramArrayOfByte1, int paramInt2, int paramInt3)
  {
    jpeg_color_deconverter localjpeg_color_deconverter = paramjpeg_decompress_struct.cconvert;
    int n = paramjpeg_decompress_struct.output_width;
    byte[] arrayOfByte5 = paramjpeg_decompress_struct.sample_range_limit;
    int i1 = paramjpeg_decompress_struct.sample_range_limit_offset;
    int[] arrayOfInt1 = localjpeg_color_deconverter.Cr_r_tab;
    int[] arrayOfInt2 = localjpeg_color_deconverter.Cb_b_tab;
    int[] arrayOfInt3 = localjpeg_color_deconverter.Cr_g_tab;
    int[] arrayOfInt4 = localjpeg_color_deconverter.Cb_g_tab;
    do
    {
      byte[] arrayOfByte2 = paramArrayOfByte[0][(paramInt1 + paramArrayOfInt[0])];
      byte[] arrayOfByte3 = paramArrayOfByte[1][(paramInt1 + paramArrayOfInt[1])];
      byte[] arrayOfByte4 = paramArrayOfByte[2][(paramInt1 + paramArrayOfInt[2])];
      paramInt1++;
      byte[] arrayOfByte1 = paramArrayOfByte1[(paramInt2++)];
      int i2 = 0;
      for (int m = 0; m < n; m++)
      {
        int i = arrayOfByte2[m] & 0xFF;
        int j = arrayOfByte3[m] & 0xFF;
        int k = arrayOfByte4[m] & 0xFF;
        arrayOfByte1[(i2 + 2)] = arrayOfByte5[(i + arrayOfInt1[k] + i1)];
        arrayOfByte1[(i2 + 1)] = arrayOfByte5[(i + (arrayOfInt4[j] + arrayOfInt3[k] >> 16) + i1)];
        arrayOfByte1[(i2 + 0)] = arrayOfByte5[(i + arrayOfInt2[j] + i1)];
        i2 += 3;
      }
      paramInt3--;
    }
    while (paramInt3 >= 0);
  }

  static boolean process_APPn(int paramInt, jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if ((paramInt == 0) || (paramInt == 14))
      return get_interesting_appn(paramjpeg_decompress_struct);
    return skip_variable(paramjpeg_decompress_struct);
  }

  static boolean process_COM(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    return skip_variable(paramjpeg_decompress_struct);
  }

  static void skip_input_data(jpeg_decompress_struct paramjpeg_decompress_struct, int paramInt)
  {
    if (paramInt > 0)
    {
      while (paramInt > paramjpeg_decompress_struct.bytes_in_buffer - paramjpeg_decompress_struct.bytes_offset)
      {
        paramInt -= paramjpeg_decompress_struct.bytes_in_buffer - paramjpeg_decompress_struct.bytes_offset;
        if (!fill_input_buffer(paramjpeg_decompress_struct))
          error();
      }
      paramjpeg_decompress_struct.bytes_offset += paramInt;
    }
  }

  static boolean skip_variable(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    i -= 2;
    if (i > 0)
      skip_input_data(paramjpeg_decompress_struct, i);
    return true;
  }

  static boolean get_interesting_appn(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    byte[] arrayOfByte = new byte[14];
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = (paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF) << 8;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    i |= paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    i -= 2;
    int k;
    if (i >= 14)
      k = 14;
    else if (i > 0)
      k = i;
    else
      k = 0;
    for (int j = 0; j < k; j++)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      arrayOfByte[j] = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)];
    }
    i -= k;
    switch (paramjpeg_decompress_struct.unread_marker)
    {
    case 224:
      examine_app0(paramjpeg_decompress_struct, arrayOfByte, k, i);
      break;
    case 238:
      examine_app14(paramjpeg_decompress_struct, arrayOfByte, k, i);
      break;
    default:
      error();
    }
    if (i > 0)
      skip_input_data(paramjpeg_decompress_struct, i);
    return true;
  }

  static void examine_app0(jpeg_decompress_struct paramjpeg_decompress_struct, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramInt1 + paramInt2;
    if ((paramInt1 >= 14) && ((paramArrayOfByte[0] & 0xFF) == 74) && ((paramArrayOfByte[1] & 0xFF) == 70) && ((paramArrayOfByte[2] & 0xFF) == 73) && ((paramArrayOfByte[3] & 0xFF) == 70) && ((paramArrayOfByte[4] & 0xFF) == 0))
    {
      paramjpeg_decompress_struct.saw_JFIF_marker = true;
      paramjpeg_decompress_struct.JFIF_major_version = paramArrayOfByte[5];
      paramjpeg_decompress_struct.JFIF_minor_version = ((byte)(paramArrayOfByte[6] & 0xFF));
      paramjpeg_decompress_struct.density_unit = ((byte)(paramArrayOfByte[7] & 0xFF));
      paramjpeg_decompress_struct.X_density = ((short)(((paramArrayOfByte[8] & 0xFF) << 8) + (paramArrayOfByte[9] & 0xFF)));
      paramjpeg_decompress_struct.Y_density = ((short)(((paramArrayOfByte[10] & 0xFF) << 8) + (paramArrayOfByte[11] & 0xFF)));
      paramArrayOfByte[12];
      paramArrayOfByte[13];
      i -= 14;
      paramArrayOfByte[12];
      paramArrayOfByte[13];
    }
    else if ((paramInt1 >= 6) && ((paramArrayOfByte[0] & 0xFF) == 74) && ((paramArrayOfByte[1] & 0xFF) == 70) && ((paramArrayOfByte[2] & 0xFF) == 88) && ((paramArrayOfByte[3] & 0xFF) == 88) && ((paramArrayOfByte[4] & 0xFF) == 0))
    {
      switch (paramArrayOfByte[5] & 0xFF)
      {
      case 16:
        break;
      case 17:
        break;
      case 19:
        break;
      case 18:
      }
    }
  }

  static void examine_app14(jpeg_decompress_struct paramjpeg_decompress_struct, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 12) && ((paramArrayOfByte[0] & 0xFF) == 65) && ((paramArrayOfByte[1] & 0xFF) == 100) && ((paramArrayOfByte[2] & 0xFF) == 111) && ((paramArrayOfByte[3] & 0xFF) == 98) && ((paramArrayOfByte[4] & 0xFF) == 101))
    {
      int i = paramArrayOfByte[11] & 0xFF;
      paramjpeg_decompress_struct.saw_Adobe_marker = true;
      paramjpeg_decompress_struct.Adobe_transform = ((byte)i);
    }
  }

  static boolean get_soi(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.marker.saw_SOI)
      error();
    for (int i = 0; i < 16; i++)
    {
      paramjpeg_decompress_struct.arith_dc_L[i] = 0;
      paramjpeg_decompress_struct.arith_dc_U[i] = 1;
      paramjpeg_decompress_struct.arith_ac_K[i] = 5;
    }
    paramjpeg_decompress_struct.restart_interval = 0;
    paramjpeg_decompress_struct.jpeg_color_space = 0;
    paramjpeg_decompress_struct.CCIR601_sampling = false;
    paramjpeg_decompress_struct.saw_JFIF_marker = false;
    paramjpeg_decompress_struct.JFIF_major_version = 1;
    paramjpeg_decompress_struct.JFIF_minor_version = 1;
    paramjpeg_decompress_struct.density_unit = 0;
    paramjpeg_decompress_struct.X_density = 1;
    paramjpeg_decompress_struct.Y_density = 1;
    paramjpeg_decompress_struct.saw_Adobe_marker = false;
    paramjpeg_decompress_struct.Adobe_transform = 0;
    paramjpeg_decompress_struct.marker.saw_SOI = true;
    return true;
  }

  static void jinit_input_controller(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_input_controller localjpeg_input_controller = paramjpeg_decompress_struct.inputctl = new jpeg_input_controller();
    localjpeg_input_controller.has_multiple_scans = false;
    localjpeg_input_controller.eoi_reached = false;
    localjpeg_input_controller.inheaders = true;
  }

  static void reset_marker_reader(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_marker_reader localjpeg_marker_reader = paramjpeg_decompress_struct.marker;
    paramjpeg_decompress_struct.comp_info = null;
    paramjpeg_decompress_struct.input_scan_number = 0;
    paramjpeg_decompress_struct.unread_marker = 0;
    localjpeg_marker_reader.saw_SOI = false;
    localjpeg_marker_reader.saw_SOF = false;
    localjpeg_marker_reader.discarded_bytes = 0;
  }

  static void reset_input_controller(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_input_controller localjpeg_input_controller = paramjpeg_decompress_struct.inputctl;
    localjpeg_input_controller.has_multiple_scans = false;
    localjpeg_input_controller.eoi_reached = false;
    localjpeg_input_controller.inheaders = true;
    reset_marker_reader(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.coef_bits = null;
  }

  static void finish_output_pass(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_decomp_master localjpeg_decomp_master = paramjpeg_decompress_struct.master;
    if (paramjpeg_decompress_struct.quantize_colors)
      error(20);
    localjpeg_decomp_master.pass_number += 1;
  }

  static void jpeg_destroy(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.global_state = 0;
  }

  static void jpeg_destroy_decompress(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_destroy(paramjpeg_decompress_struct);
  }

  static boolean jpeg_input_complete(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if ((paramjpeg_decompress_struct.global_state < 200) || (paramjpeg_decompress_struct.global_state > 210))
      error();
    return paramjpeg_decompress_struct.inputctl.eoi_reached;
  }

  static boolean jpeg_start_output(jpeg_decompress_struct paramjpeg_decompress_struct, int paramInt)
  {
    if ((paramjpeg_decompress_struct.global_state != 207) && (paramjpeg_decompress_struct.global_state != 204))
      error();
    if (paramInt <= 0)
      paramInt = 1;
    if ((paramjpeg_decompress_struct.inputctl.eoi_reached) && (paramInt > paramjpeg_decompress_struct.input_scan_number))
      paramInt = paramjpeg_decompress_struct.input_scan_number;
    paramjpeg_decompress_struct.output_scan_number = paramInt;
    return output_pass_setup(paramjpeg_decompress_struct);
  }

  static boolean jpeg_finish_output(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (((paramjpeg_decompress_struct.global_state == 205) || (paramjpeg_decompress_struct.global_state == 206)) && (paramjpeg_decompress_struct.buffered_image))
    {
      finish_output_pass(paramjpeg_decompress_struct);
      paramjpeg_decompress_struct.global_state = 208;
    }
    else if (paramjpeg_decompress_struct.global_state != 208)
    {
      error();
    }
    while ((paramjpeg_decompress_struct.input_scan_number <= paramjpeg_decompress_struct.output_scan_number) && (!paramjpeg_decompress_struct.inputctl.eoi_reached))
      if (consume_input(paramjpeg_decompress_struct) == 0)
        return false;
    paramjpeg_decompress_struct.global_state = 207;
    return true;
  }

  static boolean jpeg_finish_decompress(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (((paramjpeg_decompress_struct.global_state == 205) || (paramjpeg_decompress_struct.global_state == 206)) && (!paramjpeg_decompress_struct.buffered_image))
    {
      if (paramjpeg_decompress_struct.output_scanline < paramjpeg_decompress_struct.output_height)
        error();
      finish_output_pass(paramjpeg_decompress_struct);
      paramjpeg_decompress_struct.global_state = 210;
    }
    else if (paramjpeg_decompress_struct.global_state == 207)
    {
      paramjpeg_decompress_struct.global_state = 210;
    }
    else if (paramjpeg_decompress_struct.global_state != 210)
    {
      error();
    }
    while (!paramjpeg_decompress_struct.inputctl.eoi_reached)
      if (consume_input(paramjpeg_decompress_struct) == 0)
        return false;
    jpeg_abort(paramjpeg_decompress_struct);
    return true;
  }

  static int jpeg_read_header(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean)
  {
    if ((paramjpeg_decompress_struct.global_state != 200) && (paramjpeg_decompress_struct.global_state != 201))
      error();
    int i = jpeg_consume_input(paramjpeg_decompress_struct);
    switch (i)
    {
    case 1:
      i = 1;
      break;
    case 2:
      if (paramBoolean)
        error();
      jpeg_abort(paramjpeg_decompress_struct);
      i = 2;
      break;
    case 0:
    }
    return i;
  }

  static int dummy_consume_data(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    return 0;
  }

  static int consume_data(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
    for (int i1 = localjpeg_d_coef_controller.MCU_vert_offset; i1 < localjpeg_d_coef_controller.MCU_rows_per_iMCU_row; i1++)
    {
      for (int i = localjpeg_d_coef_controller.MCU_ctr; i < paramjpeg_decompress_struct.MCUs_per_row; i++)
      {
        int j = 0;
        for (int k = 0; k < paramjpeg_decompress_struct.comps_in_scan; k++)
        {
          jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[k];
          int i2 = i * localjpeg_component_info.MCU_width;
          for (int n = 0; n < localjpeg_component_info.MCU_height; n++)
          {
            short[][] arrayOfShort = localjpeg_d_coef_controller.whole_image[localjpeg_component_info.component_index][(n + i1 + paramjpeg_decompress_struct.input_iMCU_row * localjpeg_component_info.v_samp_factor)];
            int i3 = i2;
            for (int m = 0; m < localjpeg_component_info.MCU_width; m++)
              localjpeg_d_coef_controller.MCU_buffer[(j++)] = arrayOfShort[(i3++)];
          }
        }
        if (!paramjpeg_decompress_struct.entropy.decode_mcu(paramjpeg_decompress_struct, localjpeg_d_coef_controller.MCU_buffer))
        {
          localjpeg_d_coef_controller.MCU_vert_offset = i1;
          localjpeg_d_coef_controller.MCU_ctr = i;
          return 0;
        }
      }
      localjpeg_d_coef_controller.MCU_ctr = 0;
    }
    if (++paramjpeg_decompress_struct.input_iMCU_row < paramjpeg_decompress_struct.total_iMCU_rows)
    {
      localjpeg_d_coef_controller.start_iMCU_row(paramjpeg_decompress_struct);
      return 3;
    }
    finish_input_pass(paramjpeg_decompress_struct);
    return 4;
  }

  static int consume_input(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    switch (paramjpeg_decompress_struct.inputctl.consume_input)
    {
    case 1:
      switch (paramjpeg_decompress_struct.coef.consume_data)
      {
      case 0:
        return consume_data(paramjpeg_decompress_struct);
      case 1:
        return dummy_consume_data(paramjpeg_decompress_struct);
      }
      error();
      break;
    case 0:
      return consume_markers(paramjpeg_decompress_struct);
    default:
      error();
    }
    return 0;
  }

  static boolean fill_input_buffer(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    try
    {
      InputStream localInputStream = paramjpeg_decompress_struct.inputStream;
      int i = localInputStream.read(paramjpeg_decompress_struct.buffer);
      if (i <= 0)
      {
        if (paramjpeg_decompress_struct.start_of_file)
          error();
        paramjpeg_decompress_struct.buffer[0] = -1;
        paramjpeg_decompress_struct.buffer[1] = -39;
        i = 2;
      }
      paramjpeg_decompress_struct.bytes_in_buffer = i;
      paramjpeg_decompress_struct.bytes_offset = 0;
      paramjpeg_decompress_struct.start_of_file = false;
    }
    catch (IOException localIOException)
    {
      error(39);
      return false;
    }
    return true;
  }

  static boolean first_marker(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int i = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
      fill_input_buffer(paramjpeg_decompress_struct);
    int j = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
    if ((i != 255) || (j != 216))
      error();
    paramjpeg_decompress_struct.unread_marker = j;
    return true;
  }

  static boolean next_marker(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    int i;
    while (true)
    {
      if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
        fill_input_buffer(paramjpeg_decompress_struct);
      for (i = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF; i != 255; i = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF)
      {
        paramjpeg_decompress_struct.marker.discarded_bytes += 1;
        if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
          fill_input_buffer(paramjpeg_decompress_struct);
      }
      do
      {
        if (paramjpeg_decompress_struct.bytes_offset == paramjpeg_decompress_struct.bytes_in_buffer)
          fill_input_buffer(paramjpeg_decompress_struct);
        i = paramjpeg_decompress_struct.buffer[(paramjpeg_decompress_struct.bytes_offset++)] & 0xFF;
      }
      while (i == 255);
      if (i != 0)
        break;
      paramjpeg_decompress_struct.marker.discarded_bytes += 2;
    }
    if (paramjpeg_decompress_struct.marker.discarded_bytes != 0)
      paramjpeg_decompress_struct.marker.discarded_bytes = 0;
    paramjpeg_decompress_struct.unread_marker = i;
    return true;
  }

  static int read_markers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    while (true)
    {
      if (paramjpeg_decompress_struct.unread_marker == 0)
        if (!paramjpeg_decompress_struct.marker.saw_SOI)
        {
          if (!first_marker(paramjpeg_decompress_struct))
            return 0;
        }
        else if (!next_marker(paramjpeg_decompress_struct))
          return 0;
      switch (paramjpeg_decompress_struct.unread_marker)
      {
      case 216:
        if (!get_soi(paramjpeg_decompress_struct))
          return 0;
        break;
      case 192:
      case 193:
        if (!get_sof(paramjpeg_decompress_struct, false, false))
          return 0;
        break;
      case 194:
        if (!get_sof(paramjpeg_decompress_struct, true, false))
          return 0;
        break;
      case 201:
        if (!get_sof(paramjpeg_decompress_struct, false, true))
          return 0;
        break;
      case 202:
        if (!get_sof(paramjpeg_decompress_struct, true, true))
          return 0;
        break;
      case 195:
      case 197:
      case 198:
      case 199:
      case 200:
      case 203:
      case 205:
      case 206:
      case 207:
        error();
        break;
      case 218:
        if (!get_sos(paramjpeg_decompress_struct))
          return 0;
        paramjpeg_decompress_struct.unread_marker = 0;
        return 1;
      case 217:
        paramjpeg_decompress_struct.unread_marker = 0;
        return 2;
      case 204:
        if (!get_dac(paramjpeg_decompress_struct))
          return 0;
        break;
      case 196:
        if (!get_dht(paramjpeg_decompress_struct))
          return 0;
        break;
      case 219:
        if (!get_dqt(paramjpeg_decompress_struct))
          return 0;
        break;
      case 221:
        if (!get_dri(paramjpeg_decompress_struct))
          return 0;
        break;
      case 224:
      case 225:
      case 226:
      case 227:
      case 228:
      case 229:
      case 230:
      case 231:
      case 232:
      case 233:
      case 234:
      case 235:
      case 236:
      case 237:
      case 238:
      case 239:
        if (!process_APPn(paramjpeg_decompress_struct.unread_marker - 224, paramjpeg_decompress_struct))
          return 0;
        break;
      case 254:
        if (!process_COM(paramjpeg_decompress_struct))
          return 0;
        break;
      case 1:
      case 208:
      case 209:
      case 210:
      case 211:
      case 212:
      case 213:
      case 214:
      case 215:
        break;
      case 220:
        if (!skip_variable(paramjpeg_decompress_struct))
          return 0;
        break;
      default:
        error();
      }
      paramjpeg_decompress_struct.unread_marker = 0;
    }
  }

  static long jdiv_round_up(long paramLong1, long paramLong2)
  {
    return (paramLong1 + paramLong2 - 1L) / paramLong2;
  }

  static void initial_setup(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if ((paramjpeg_decompress_struct.image_height > 65500) || (paramjpeg_decompress_struct.image_width > 65500))
      error();
    if (paramjpeg_decompress_struct.data_precision != 8)
      error(" [data precision=" + paramjpeg_decompress_struct.data_precision + "]");
    if (paramjpeg_decompress_struct.num_components > 10)
      error();
    paramjpeg_decompress_struct.max_h_samp_factor = 1;
    paramjpeg_decompress_struct.max_v_samp_factor = 1;
    jpeg_component_info localjpeg_component_info;
    for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      if ((localjpeg_component_info.h_samp_factor <= 0) || (localjpeg_component_info.h_samp_factor > 4) || (localjpeg_component_info.v_samp_factor <= 0) || (localjpeg_component_info.v_samp_factor > 4))
        error();
      paramjpeg_decompress_struct.max_h_samp_factor = Math.max(paramjpeg_decompress_struct.max_h_samp_factor, localjpeg_component_info.h_samp_factor);
      paramjpeg_decompress_struct.max_v_samp_factor = Math.max(paramjpeg_decompress_struct.max_v_samp_factor, localjpeg_component_info.v_samp_factor);
    }
    paramjpeg_decompress_struct.min_DCT_scaled_size = 8;
    for (i = 0; i < paramjpeg_decompress_struct.num_components; i++)
    {
      localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
      localjpeg_component_info.DCT_scaled_size = 8;
      localjpeg_component_info.width_in_blocks = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_width * localjpeg_component_info.h_samp_factor, paramjpeg_decompress_struct.max_h_samp_factor * 8));
      localjpeg_component_info.height_in_blocks = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_height * localjpeg_component_info.v_samp_factor, paramjpeg_decompress_struct.max_v_samp_factor * 8));
      localjpeg_component_info.downsampled_width = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_width * localjpeg_component_info.h_samp_factor, paramjpeg_decompress_struct.max_h_samp_factor));
      localjpeg_component_info.downsampled_height = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_height * localjpeg_component_info.v_samp_factor, paramjpeg_decompress_struct.max_v_samp_factor));
      localjpeg_component_info.component_needed = true;
      localjpeg_component_info.quant_table = null;
    }
    paramjpeg_decompress_struct.total_iMCU_rows = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_height, paramjpeg_decompress_struct.max_v_samp_factor * 8));
    if ((paramjpeg_decompress_struct.comps_in_scan < paramjpeg_decompress_struct.num_components) || (paramjpeg_decompress_struct.progressive_mode))
      paramjpeg_decompress_struct.inputctl.has_multiple_scans = true;
    else
      paramjpeg_decompress_struct.inputctl.has_multiple_scans = false;
  }

  static void per_scan_setup(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    int k = 0;
    jpeg_component_info localjpeg_component_info;
    if (paramjpeg_decompress_struct.comps_in_scan == 1)
    {
      localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[0];
      paramjpeg_decompress_struct.MCUs_per_row = localjpeg_component_info.width_in_blocks;
      paramjpeg_decompress_struct.MCU_rows_in_scan = localjpeg_component_info.height_in_blocks;
      localjpeg_component_info.MCU_width = 1;
      localjpeg_component_info.MCU_height = 1;
      localjpeg_component_info.MCU_blocks = 1;
      localjpeg_component_info.MCU_sample_width = localjpeg_component_info.DCT_scaled_size;
      localjpeg_component_info.last_col_width = 1;
      k = localjpeg_component_info.height_in_blocks % localjpeg_component_info.v_samp_factor;
      if (k == 0)
        k = localjpeg_component_info.v_samp_factor;
      localjpeg_component_info.last_row_height = k;
      paramjpeg_decompress_struct.blocks_in_MCU = 1;
      paramjpeg_decompress_struct.MCU_membership[0] = 0;
    }
    else
    {
      if ((paramjpeg_decompress_struct.comps_in_scan <= 0) || (paramjpeg_decompress_struct.comps_in_scan > 4))
        error();
      paramjpeg_decompress_struct.MCUs_per_row = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_width, paramjpeg_decompress_struct.max_h_samp_factor * 8));
      paramjpeg_decompress_struct.MCU_rows_in_scan = ((int)jdiv_round_up(paramjpeg_decompress_struct.image_height, paramjpeg_decompress_struct.max_v_samp_factor * 8));
      paramjpeg_decompress_struct.blocks_in_MCU = 0;
      for (int i = 0; i < paramjpeg_decompress_struct.comps_in_scan; i++)
      {
        localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[i];
        localjpeg_component_info.MCU_width = localjpeg_component_info.h_samp_factor;
        localjpeg_component_info.MCU_height = localjpeg_component_info.v_samp_factor;
        localjpeg_component_info.MCU_blocks = (localjpeg_component_info.MCU_width * localjpeg_component_info.MCU_height);
        localjpeg_component_info.MCU_sample_width = (localjpeg_component_info.MCU_width * localjpeg_component_info.DCT_scaled_size);
        k = localjpeg_component_info.width_in_blocks % localjpeg_component_info.MCU_width;
        if (k == 0)
          k = localjpeg_component_info.MCU_width;
        localjpeg_component_info.last_col_width = k;
        k = localjpeg_component_info.height_in_blocks % localjpeg_component_info.MCU_height;
        if (k == 0)
          k = localjpeg_component_info.MCU_height;
        localjpeg_component_info.last_row_height = k;
        int j = localjpeg_component_info.MCU_blocks;
        if (paramjpeg_decompress_struct.blocks_in_MCU + j > 10)
          error();
        while (j-- > 0)
          paramjpeg_decompress_struct.MCU_membership[(paramjpeg_decompress_struct.blocks_in_MCU++)] = i;
      }
    }
  }

  static void latch_quant_tables(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    for (int i = 0; i < paramjpeg_decompress_struct.comps_in_scan; i++)
    {
      jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[i];
      if (localjpeg_component_info.quant_table == null)
      {
        int j = localjpeg_component_info.quant_tbl_no;
        if ((j < 0) || (j >= 4) || (paramjpeg_decompress_struct.quant_tbl_ptrs[j] == null))
          error();
        JQUANT_TBL localJQUANT_TBL = new JQUANT_TBL();
        System.arraycopy(paramjpeg_decompress_struct.quant_tbl_ptrs[j].quantval, 0, localJQUANT_TBL.quantval, 0, localJQUANT_TBL.quantval.length);
        localJQUANT_TBL.sent_table = paramjpeg_decompress_struct.quant_tbl_ptrs[j].sent_table;
        localjpeg_component_info.quant_table = localJQUANT_TBL;
      }
    }
  }

  static void jpeg_make_d_derived_tbl(jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean, int paramInt, d_derived_tbl paramd_derived_tbl)
  {
    int j = 0;
    byte[] arrayOfByte = new byte[257];
    int[] arrayOfInt = new int[257];
    if ((paramInt < 0) || (paramInt >= 4))
      error();
    JHUFF_TBL localJHUFF_TBL = paramBoolean ? paramjpeg_decompress_struct.dc_huff_tbl_ptrs[paramInt] : paramjpeg_decompress_struct.ac_huff_tbl_ptrs[paramInt];
    if (localJHUFF_TBL == null)
      error();
    paramd_derived_tbl.pub = localJHUFF_TBL;
    int i = 0;
    for (int k = 1; k <= 16; k++)
    {
      j = localJHUFF_TBL.bits[k] & 0xFF;
      if ((j < 0) || (i + j > 256))
        error();
      while (j-- != 0)
        arrayOfByte[(i++)] = ((byte)k);
    }
    arrayOfByte[i] = 0;
    int n = i;
    int i3 = 0;
    int m = arrayOfByte[0];
    i = 0;
    while (arrayOfByte[i] != 0)
    {
      while (arrayOfByte[i] == m)
      {
        arrayOfInt[(i++)] = i3;
        i3++;
      }
      if (i3 >= 1 << m)
        error();
      i3 <<= 1;
      m++;
    }
    i = 0;
    for (k = 1; k <= 16; k++)
      if ((localJHUFF_TBL.bits[k] & 0xFF) != 0)
      {
        paramd_derived_tbl.valoffset[k] = (i - arrayOfInt[i]);
        i += (localJHUFF_TBL.bits[k] & 0xFF);
        paramd_derived_tbl.maxcode[k] = arrayOfInt[(i - 1)];
      }
      else
      {
        paramd_derived_tbl.maxcode[k] = -1;
      }
    paramd_derived_tbl.maxcode[17] = 1048575;
    for (int i4 = 0; i4 < paramd_derived_tbl.look_nbits.length; i4++)
      paramd_derived_tbl.look_nbits[i4] = 0;
    i = 0;
    for (k = 1; k <= 8; k++)
    {
      j = 1;
      while (j <= (localJHUFF_TBL.bits[k] & 0xFF))
      {
        int i1 = arrayOfInt[i] << 8 - k;
        for (int i2 = 1 << 8 - k; i2 > 0; i2--)
        {
          paramd_derived_tbl.look_nbits[i1] = k;
          paramd_derived_tbl.look_sym[i1] = localJHUFF_TBL.huffval[i];
          i1++;
        }
        j++;
        i++;
      }
    }
    if (paramBoolean)
      for (j = 0; j < n; j++)
      {
        i4 = localJHUFF_TBL.huffval[j] & 0xFF;
        if ((i4 < 0) || (i4 > 15))
          error();
      }
  }

  static void start_input_pass(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    per_scan_setup(paramjpeg_decompress_struct);
    latch_quant_tables(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.entropy.start_pass(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.coef.start_input_pass(paramjpeg_decompress_struct);
    paramjpeg_decompress_struct.inputctl.consume_input = 1;
  }

  static void finish_input_pass(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.inputctl.consume_input = 0;
  }

  static int consume_markers(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    jpeg_input_controller localjpeg_input_controller = paramjpeg_decompress_struct.inputctl;
    if (localjpeg_input_controller.eoi_reached)
      return 2;
    int i = read_markers(paramjpeg_decompress_struct);
    switch (i)
    {
    case 1:
      if (localjpeg_input_controller.inheaders)
      {
        initial_setup(paramjpeg_decompress_struct);
        localjpeg_input_controller.inheaders = false;
      }
      else
      {
        if (!localjpeg_input_controller.has_multiple_scans)
          error();
        start_input_pass(paramjpeg_decompress_struct);
      }
      break;
    case 2:
      localjpeg_input_controller.eoi_reached = true;
      if (localjpeg_input_controller.inheaders)
      {
        if (paramjpeg_decompress_struct.marker.saw_SOF)
          error();
      }
      else if (paramjpeg_decompress_struct.output_scan_number > paramjpeg_decompress_struct.input_scan_number)
        paramjpeg_decompress_struct.output_scan_number = paramjpeg_decompress_struct.input_scan_number;
      break;
    case 0:
    }
    return i;
  }

  static void default_decompress_parms(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    switch (paramjpeg_decompress_struct.num_components)
    {
    case 1:
      paramjpeg_decompress_struct.jpeg_color_space = 1;
      paramjpeg_decompress_struct.out_color_space = 1;
      break;
    case 3:
      if (paramjpeg_decompress_struct.saw_JFIF_marker)
      {
        paramjpeg_decompress_struct.jpeg_color_space = 3;
      }
      else if (paramjpeg_decompress_struct.saw_Adobe_marker)
      {
        switch (paramjpeg_decompress_struct.Adobe_transform)
        {
        case 0:
          paramjpeg_decompress_struct.jpeg_color_space = 2;
          break;
        case 1:
          paramjpeg_decompress_struct.jpeg_color_space = 3;
          break;
        default:
          paramjpeg_decompress_struct.jpeg_color_space = 3;
          break;
        }
      }
      else
      {
        int i = paramjpeg_decompress_struct.comp_info[0].component_id;
        int j = paramjpeg_decompress_struct.comp_info[1].component_id;
        int k = paramjpeg_decompress_struct.comp_info[2].component_id;
        if ((i == 1) && (j == 2) && (k == 3))
          paramjpeg_decompress_struct.jpeg_color_space = 3;
        else if ((i == 82) && (j == 71) && (k == 66))
          paramjpeg_decompress_struct.jpeg_color_space = 2;
        else
          paramjpeg_decompress_struct.jpeg_color_space = 3;
      }
      paramjpeg_decompress_struct.out_color_space = 2;
      break;
    case 4:
      if (paramjpeg_decompress_struct.saw_Adobe_marker)
        switch (paramjpeg_decompress_struct.Adobe_transform)
        {
        case 0:
          paramjpeg_decompress_struct.jpeg_color_space = 4;
          break;
        case 2:
          paramjpeg_decompress_struct.jpeg_color_space = 5;
          break;
        case 1:
        default:
          paramjpeg_decompress_struct.jpeg_color_space = 5;
          break;
        }
      else
        paramjpeg_decompress_struct.jpeg_color_space = 4;
      paramjpeg_decompress_struct.out_color_space = 4;
      break;
    case 2:
    default:
      paramjpeg_decompress_struct.jpeg_color_space = 0;
      paramjpeg_decompress_struct.out_color_space = 0;
    }
    paramjpeg_decompress_struct.scale_num = 1;
    paramjpeg_decompress_struct.scale_denom = 1;
    paramjpeg_decompress_struct.output_gamma = 1.0D;
    paramjpeg_decompress_struct.buffered_image = false;
    paramjpeg_decompress_struct.raw_data_out = false;
    paramjpeg_decompress_struct.dct_method = 0;
    paramjpeg_decompress_struct.do_fancy_upsampling = true;
    paramjpeg_decompress_struct.do_block_smoothing = true;
    paramjpeg_decompress_struct.quantize_colors = false;
    paramjpeg_decompress_struct.dither_mode = 2;
    paramjpeg_decompress_struct.two_pass_quantize = true;
    paramjpeg_decompress_struct.desired_number_of_colors = 256;
    paramjpeg_decompress_struct.colormap = null;
    paramjpeg_decompress_struct.enable_1pass_quant = false;
    paramjpeg_decompress_struct.enable_external_quant = false;
    paramjpeg_decompress_struct.enable_2pass_quant = false;
  }

  static void init_source(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    paramjpeg_decompress_struct.buffer = new byte[4096];
    paramjpeg_decompress_struct.bytes_in_buffer = 0;
    paramjpeg_decompress_struct.bytes_offset = 0;
    paramjpeg_decompress_struct.start_of_file = true;
  }

  static int jpeg_consume_input(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    int i = 0;
    switch (paramjpeg_decompress_struct.global_state)
    {
    case 200:
      reset_input_controller(paramjpeg_decompress_struct);
      init_source(paramjpeg_decompress_struct);
      paramjpeg_decompress_struct.global_state = 201;
    case 201:
      i = consume_input(paramjpeg_decompress_struct);
      if (i == 1)
      {
        default_decompress_parms(paramjpeg_decompress_struct);
        paramjpeg_decompress_struct.global_state = 202;
      }
      break;
    case 202:
      i = 1;
      break;
    case 203:
    case 204:
    case 205:
    case 206:
    case 207:
    case 208:
    case 210:
      i = consume_input(paramjpeg_decompress_struct);
      break;
    case 209:
    default:
      error();
    }
    return i;
  }

  static void jpeg_abort(jpeg_decompress_struct paramjpeg_decompress_struct)
  {
    if (paramjpeg_decompress_struct.is_decompressor)
      paramjpeg_decompress_struct.global_state = 200;
    else
      paramjpeg_decompress_struct.global_state = 100;
  }

  static boolean isFileFormat(LEDataInputStream paramLEDataInputStream)
  {
    try
    {
      byte[] arrayOfByte = new byte[2];
      paramLEDataInputStream.read(arrayOfByte);
      paramLEDataInputStream.unread(arrayOfByte);
      return ((arrayOfByte[0] & 0xFF) == 255) && ((arrayOfByte[1] & 0xFF) == 216);
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  static ImageData[] loadFromByteStream(InputStream paramInputStream, ImageLoader paramImageLoader)
  {
    jpeg_decompress_struct localjpeg_decompress_struct = new jpeg_decompress_struct();
    localjpeg_decompress_struct.inputStream = paramInputStream;
    jpeg_create_decompress(localjpeg_decompress_struct);
    jpeg_read_header(localjpeg_decompress_struct, true);
    localjpeg_decompress_struct.buffered_image = ((localjpeg_decompress_struct.progressive_mode) && (paramImageLoader.hasListeners()));
    jpeg_start_decompress(localjpeg_decompress_struct);
    PaletteData localPaletteData = null;
    switch (localjpeg_decompress_struct.out_color_space)
    {
    case 2:
      localPaletteData = new PaletteData(255, 65280, 16711680);
      break;
    case 1:
      RGB[] arrayOfRGB = new RGB[256];
      for (j = 0; j < arrayOfRGB.length; j++)
        arrayOfRGB[j] = new RGB(j, j, j);
      localPaletteData = new PaletteData(arrayOfRGB);
      break;
    default:
      error();
    }
    int i = 4;
    int j = ((localjpeg_decompress_struct.output_width * localjpeg_decompress_struct.out_color_components * 8 + 7) / 8 + (i - 1)) / i * i;
    byte[][] arrayOfByte = new byte[1][j];
    byte[] arrayOfByte1 = new byte[j * localjpeg_decompress_struct.output_height];
    ImageData localImageData = ImageData.internal_new(localjpeg_decompress_struct.output_width, localjpeg_decompress_struct.output_height, localPaletteData.isDirect ? 24 : 8, localPaletteData, i, arrayOfByte1, 0, null, null, -1, -1, 4, 0, 0, 0, 0);
    if (localjpeg_decompress_struct.buffered_image)
      do
      {
        m = localjpeg_decompress_struct.input_scan_number - 1;
        jpeg_start_output(localjpeg_decompress_struct, localjpeg_decompress_struct.input_scan_number);
        while (localjpeg_decompress_struct.output_scanline < localjpeg_decompress_struct.output_height)
        {
          n = j * localjpeg_decompress_struct.output_scanline;
          jpeg_read_scanlines(localjpeg_decompress_struct, arrayOfByte, 1);
          System.arraycopy(arrayOfByte[0], 0, arrayOfByte1, n, j);
        }
        jpeg_finish_output(localjpeg_decompress_struct);
        paramImageLoader.notifyListeners(new ImageLoaderEvent(paramImageLoader, (ImageData)localImageData.clone(), m, bool = jpeg_input_complete(localjpeg_decompress_struct)));
      }
      while (!bool);
    else
      while (localjpeg_decompress_struct.output_scanline < localjpeg_decompress_struct.output_height)
      {
        int m;
        int n;
        boolean bool;
        int k = j * localjpeg_decompress_struct.output_scanline;
        jpeg_read_scanlines(localjpeg_decompress_struct, arrayOfByte, 1);
        System.arraycopy(arrayOfByte[0], 0, arrayOfByte1, k, j);
      }
    jpeg_finish_decompress(localjpeg_decompress_struct);
    jpeg_destroy_decompress(localjpeg_decompress_struct);
    return new ImageData[] { localImageData };
  }

  static final class JHUFF_TBL
  {
    byte[] bits = new byte[17];
    byte[] huffval = new byte[256];
    boolean sent_table;
  }

  static final class JQUANT_TBL
  {
    short[] quantval = new short[64];
    boolean sent_table;
  }

  static final class bitread_perm_state
  {
    int get_buffer;
    int bits_left;
  }

  static final class bitread_working_state
  {
    byte[] buffer;
    int bytes_offset;
    int bytes_in_buffer;
    int get_buffer;
    int bits_left;
    JPEGDecoder.jpeg_decompress_struct cinfo;
  }

  static final class d_derived_tbl
  {
    int[] maxcode = new int[18];
    int[] valoffset = new int[17];
    JPEGDecoder.JHUFF_TBL pub;
    int[] look_nbits = new int[256];
    byte[] look_sym = new byte[256];
  }

  static final class huff_entropy_decoder extends JPEGDecoder.jpeg_entropy_decoder
  {
    JPEGDecoder.bitread_perm_state bitstate = new JPEGDecoder.bitread_perm_state();
    JPEGDecoder.savable_state saved = new JPEGDecoder.savable_state();
    int restarts_to_go;
    JPEGDecoder.d_derived_tbl[] dc_derived_tbls = new JPEGDecoder.d_derived_tbl[4];
    JPEGDecoder.d_derived_tbl[] ac_derived_tbls = new JPEGDecoder.d_derived_tbl[4];
    JPEGDecoder.d_derived_tbl[] dc_cur_tbls = new JPEGDecoder.d_derived_tbl[10];
    JPEGDecoder.d_derived_tbl[] ac_cur_tbls = new JPEGDecoder.d_derived_tbl[10];
    boolean[] dc_needed = new boolean[10];
    boolean[] ac_needed = new boolean[10];

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      start_pass_huff_decoder(paramjpeg_decompress_struct);
    }

    boolean decode_mcu(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      huff_entropy_decoder localhuff_entropy_decoder = this;
      JPEGDecoder.bitread_working_state localbitread_working_state = this.br_state_local;
      JPEGDecoder.savable_state localsavable_state = this.state_local;
      if ((paramjpeg_decompress_struct.restart_interval != 0) && (localhuff_entropy_decoder.restarts_to_go == 0) && (!process_restart(paramjpeg_decompress_struct)))
        return false;
      if (!localhuff_entropy_decoder.insufficient_data)
      {
        localbitread_working_state.cinfo = paramjpeg_decompress_struct;
        localbitread_working_state.buffer = paramjpeg_decompress_struct.buffer;
        localbitread_working_state.bytes_in_buffer = paramjpeg_decompress_struct.bytes_in_buffer;
        localbitread_working_state.bytes_offset = paramjpeg_decompress_struct.bytes_offset;
        int j = localhuff_entropy_decoder.bitstate.get_buffer;
        int k = localhuff_entropy_decoder.bitstate.bits_left;
        localsavable_state.last_dc_val[0] = localhuff_entropy_decoder.saved.last_dc_val[0];
        localsavable_state.last_dc_val[1] = localhuff_entropy_decoder.saved.last_dc_val[1];
        localsavable_state.last_dc_val[2] = localhuff_entropy_decoder.saved.last_dc_val[2];
        localsavable_state.last_dc_val[3] = localhuff_entropy_decoder.saved.last_dc_val[3];
        for (int i = 0; i < paramjpeg_decompress_struct.blocks_in_MCU; i++)
        {
          short[] arrayOfShort = paramArrayOfShort[i];
          JPEGDecoder.d_derived_tbl locald_derived_tbl1 = localhuff_entropy_decoder.dc_cur_tbls[i];
          JPEGDecoder.d_derived_tbl locald_derived_tbl2 = localhuff_entropy_decoder.ac_cur_tbls[i];
          int m = 0;
          int i2 = 0;
          if (k < 8)
          {
            if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, 0))
              return false;
            j = localbitread_working_state.get_buffer;
            k = localbitread_working_state.bits_left;
            if (k < 8)
            {
              i2 = 1;
              if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl1, i2)) < 0)
                return false;
              j = localbitread_working_state.get_buffer;
              k = localbitread_working_state.bits_left;
            }
          }
          int i3;
          if (i2 != 1)
          {
            i3 = j >> k - 8 & 0xFF;
            if ((i2 = locald_derived_tbl1.look_nbits[i3]) != 0)
            {
              k -= i2;
              m = locald_derived_tbl1.look_sym[i3] & 0xFF;
            }
            else
            {
              i2 = 9;
              if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl1, i2)) < 0)
                return false;
              j = localbitread_working_state.get_buffer;
              k = localbitread_working_state.bits_left;
            }
          }
          int i1;
          if (m != 0)
          {
            if (k < m)
            {
              if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, m))
                return false;
              j = localbitread_working_state.get_buffer;
              k = localbitread_working_state.bits_left;
            }
            i1 = j >> k -= m & (1 << m) - 1;
            m = i1 < JPEGDecoder.extend_test[m] ? i1 + JPEGDecoder.extend_offset[m] : i1;
          }
          if (localhuff_entropy_decoder.dc_needed[i] != 0)
          {
            i2 = paramjpeg_decompress_struct.MCU_membership[i];
            m += localsavable_state.last_dc_val[i2];
            localsavable_state.last_dc_val[i2] = m;
            arrayOfShort[0] = ((short)m);
          }
          int n;
          if (localhuff_entropy_decoder.ac_needed[i] != 0)
            for (n = 1; n < 64; n++)
            {
              i2 = 0;
              if (k < 8)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, 0))
                  return false;
                j = localbitread_working_state.get_buffer;
                k = localbitread_working_state.bits_left;
                if (k < 8)
                {
                  i2 = 1;
                  if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl2, i2)) < 0)
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
              }
              if (i2 != 1)
              {
                i3 = j >> k - 8 & 0xFF;
                if ((i2 = locald_derived_tbl2.look_nbits[i3]) != 0)
                {
                  k -= i2;
                  m = locald_derived_tbl2.look_sym[i3] & 0xFF;
                }
                else
                {
                  i2 = 9;
                  if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl2, i2)) < 0)
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
              }
              i1 = m >> 4;
              m &= 15;
              if (m != 0)
              {
                n += i1;
                if (k < m)
                {
                  if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, m))
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
                i1 = j >> k -= m & (1 << m) - 1;
                m = i1 < JPEGDecoder.extend_test[m] ? i1 + JPEGDecoder.extend_offset[m] : i1;
                arrayOfShort[JPEGDecoder.jpeg_natural_order[n]] = ((short)m);
              }
              else
              {
                if (i1 != 15)
                  break;
                n += 15;
              }
            }
          else
            for (n = 1; n < 64; n++)
            {
              i2 = 0;
              if (k < 8)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, 0))
                  return false;
                j = localbitread_working_state.get_buffer;
                k = localbitread_working_state.bits_left;
                if (k < 8)
                {
                  i2 = 1;
                  if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl2, i2)) < 0)
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
              }
              if (i2 != 1)
              {
                i3 = j >> k - 8 & 0xFF;
                if ((i2 = locald_derived_tbl2.look_nbits[i3]) != 0)
                {
                  k -= i2;
                  m = locald_derived_tbl2.look_sym[i3] & 0xFF;
                }
                else
                {
                  i2 = 9;
                  if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, j, k, locald_derived_tbl2, i2)) < 0)
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
              }
              i1 = m >> 4;
              m &= 15;
              if (m != 0)
              {
                n += i1;
                if (k < m)
                {
                  if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, j, k, m))
                    return false;
                  j = localbitread_working_state.get_buffer;
                  k = localbitread_working_state.bits_left;
                }
                k -= m;
              }
              else
              {
                if (i1 != 15)
                  break;
                n += 15;
              }
            }
        }
        paramjpeg_decompress_struct.buffer = localbitread_working_state.buffer;
        paramjpeg_decompress_struct.bytes_in_buffer = localbitread_working_state.bytes_in_buffer;
        paramjpeg_decompress_struct.bytes_offset = localbitread_working_state.bytes_offset;
        localhuff_entropy_decoder.bitstate.get_buffer = j;
        localhuff_entropy_decoder.bitstate.bits_left = k;
        localhuff_entropy_decoder.saved.last_dc_val[0] = localsavable_state.last_dc_val[0];
        localhuff_entropy_decoder.saved.last_dc_val[1] = localsavable_state.last_dc_val[1];
        localhuff_entropy_decoder.saved.last_dc_val[2] = localsavable_state.last_dc_val[2];
        localhuff_entropy_decoder.saved.last_dc_val[3] = localsavable_state.last_dc_val[3];
      }
      localhuff_entropy_decoder.restarts_to_go -= 1;
      return true;
    }

    void start_pass_huff_decoder(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      huff_entropy_decoder localhuff_entropy_decoder = this;
      if ((paramjpeg_decompress_struct.Ss == 0) && (paramjpeg_decompress_struct.Se == 63) && (paramjpeg_decompress_struct.Ah == 0));
      JPEGDecoder.jpeg_component_info localjpeg_component_info;
      for (int i = 0; i < paramjpeg_decompress_struct.comps_in_scan; i++)
      {
        localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[i];
        int k = localjpeg_component_info.dc_tbl_no;
        int m = localjpeg_component_info.ac_tbl_no;
        JPEGDecoder.jpeg_make_d_derived_tbl(paramjpeg_decompress_struct, true, k, localhuff_entropy_decoder.dc_derived_tbls[k] =  = new JPEGDecoder.d_derived_tbl());
        JPEGDecoder.jpeg_make_d_derived_tbl(paramjpeg_decompress_struct, false, m, localhuff_entropy_decoder.ac_derived_tbls[m] =  = new JPEGDecoder.d_derived_tbl());
        localhuff_entropy_decoder.saved.last_dc_val[i] = 0;
      }
      for (int j = 0; j < paramjpeg_decompress_struct.blocks_in_MCU; j++)
      {
        i = paramjpeg_decompress_struct.MCU_membership[j];
        localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[i];
        localhuff_entropy_decoder.dc_cur_tbls[j] = localhuff_entropy_decoder.dc_derived_tbls[localjpeg_component_info.dc_tbl_no];
        localhuff_entropy_decoder.ac_cur_tbls[j] = localhuff_entropy_decoder.ac_derived_tbls[localjpeg_component_info.ac_tbl_no];
        if (localjpeg_component_info.component_needed)
        {
          localhuff_entropy_decoder.dc_needed[j] = true;
          localhuff_entropy_decoder.ac_needed[j] = (localjpeg_component_info.DCT_scaled_size > 1 ? 1 : false);
        }
        else
        {
          int tmp231_230 = 0;
          localhuff_entropy_decoder.ac_needed[j] = tmp231_230;
          localhuff_entropy_decoder.dc_needed[j] = tmp231_230;
        }
      }
      localhuff_entropy_decoder.bitstate.bits_left = 0;
      localhuff_entropy_decoder.bitstate.get_buffer = 0;
      localhuff_entropy_decoder.insufficient_data = false;
      localhuff_entropy_decoder.restarts_to_go = paramjpeg_decompress_struct.restart_interval;
    }

    boolean process_restart(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      huff_entropy_decoder localhuff_entropy_decoder = this;
      paramjpeg_decompress_struct.marker.discarded_bytes += localhuff_entropy_decoder.bitstate.bits_left / 8;
      localhuff_entropy_decoder.bitstate.bits_left = 0;
      if (!JPEGDecoder.read_restart_marker(paramjpeg_decompress_struct))
        return false;
      for (int i = 0; i < paramjpeg_decompress_struct.comps_in_scan; i++)
        localhuff_entropy_decoder.saved.last_dc_val[i] = 0;
      localhuff_entropy_decoder.restarts_to_go = paramjpeg_decompress_struct.restart_interval;
      if (paramjpeg_decompress_struct.unread_marker == 0)
        localhuff_entropy_decoder.insufficient_data = false;
      return true;
    }
  }

  static final class jpeg_color_deconverter
  {
    int color_convert;
    int[] Cr_r_tab;
    int[] Cb_b_tab;
    int[] Cr_g_tab;
    int[] Cb_g_tab;

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
    }
  }

  static final class jpeg_color_quantizer
  {
    int[][] sv_colormap;
    int sv_actual;
    int[][] colorindex;
    boolean is_padded;
    int[] Ncolors = new int[4];
    int row_index;
    boolean on_odd_row;

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, boolean paramBoolean)
    {
      JPEGDecoder.error();
    }
  }

  static final class jpeg_component_info
  {
    int component_id;
    int component_index;
    int h_samp_factor;
    int v_samp_factor;
    int quant_tbl_no;
    int dc_tbl_no;
    int ac_tbl_no;
    int width_in_blocks;
    int height_in_blocks;
    int DCT_scaled_size;
    int downsampled_width;
    int downsampled_height;
    boolean component_needed;
    int MCU_width;
    int MCU_height;
    int MCU_blocks;
    int MCU_sample_width;
    int last_col_width;
    int last_row_height;
    JPEGDecoder.JQUANT_TBL quant_table;
    int[] dct_table;
  }

  static final class jpeg_d_coef_controller
  {
    int consume_data;
    int decompress_data;
    short[][][] coef_arrays;
    int MCU_ctr;
    int MCU_vert_offset;
    int MCU_rows_per_iMCU_row;
    short[][] MCU_buffer = new short[10][];
    short[][][][] whole_image = new short[10][][][];
    int[] coef_bits_latch;
    short[] workspace;

    void start_input_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      paramjpeg_decompress_struct.input_iMCU_row = 0;
      start_iMCU_row(paramjpeg_decompress_struct);
    }

    void start_iMCU_row(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      jpeg_d_coef_controller localjpeg_d_coef_controller = paramjpeg_decompress_struct.coef;
      if (paramjpeg_decompress_struct.comps_in_scan > 1)
        localjpeg_d_coef_controller.MCU_rows_per_iMCU_row = 1;
      else if (paramjpeg_decompress_struct.input_iMCU_row < paramjpeg_decompress_struct.total_iMCU_rows - 1)
        localjpeg_d_coef_controller.MCU_rows_per_iMCU_row = paramjpeg_decompress_struct.cur_comp_info[0].v_samp_factor;
      else
        localjpeg_d_coef_controller.MCU_rows_per_iMCU_row = paramjpeg_decompress_struct.cur_comp_info[0].last_row_height;
      localjpeg_d_coef_controller.MCU_ctr = 0;
      localjpeg_d_coef_controller.MCU_vert_offset = 0;
    }
  }

  static final class jpeg_d_main_controller
  {
    int process_data;
    byte[][][] buffer = new byte[10][][];
    int[] buffer_offset = new int[10];
    boolean buffer_full;
    int[] rowgroup_ctr = new int[1];
    byte[][][][] xbuffer = new byte[2][][][];
    int[][] xbuffer_offset = new int[2][];
    int whichptr;
    int context_state;
    int rowgroups_avail;
    int iMCU_row_ctr;

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, int paramInt)
    {
      jpeg_d_main_controller localjpeg_d_main_controller = paramjpeg_decompress_struct.main;
      switch (paramInt)
      {
      case 0:
        if (paramjpeg_decompress_struct.upsample.need_context_rows)
        {
          localjpeg_d_main_controller.process_data = 1;
          JPEGDecoder.make_funny_pointers(paramjpeg_decompress_struct);
          localjpeg_d_main_controller.whichptr = 0;
          localjpeg_d_main_controller.context_state = 0;
          localjpeg_d_main_controller.iMCU_row_ctr = 0;
        }
        else
        {
          localjpeg_d_main_controller.process_data = 0;
        }
        localjpeg_d_main_controller.buffer_full = false;
        localjpeg_d_main_controller.rowgroup_ctr[0] = 0;
        break;
      default:
        JPEGDecoder.error();
      }
    }
  }

  static final class jpeg_d_post_controller
  {
    int post_process_data;
    int[] whole_image;
    int[][] buffer;
    int strip_height;
    int starting_row;
    int next_row;

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, int paramInt)
    {
      jpeg_d_post_controller localjpeg_d_post_controller = paramjpeg_decompress_struct.post;
      switch (paramInt)
      {
      case 0:
        if (paramjpeg_decompress_struct.quantize_colors)
          JPEGDecoder.error(20);
        else
          localjpeg_d_post_controller.post_process_data = 1;
        break;
      default:
        JPEGDecoder.error();
      }
      localjpeg_d_post_controller.starting_row = (localjpeg_d_post_controller.next_row = 0);
    }
  }

  static final class jpeg_decomp_master
  {
    boolean is_dummy_pass;
    int pass_number;
    boolean using_merged_upsample;
    JPEGDecoder.jpeg_color_quantizer quantizer_1pass;
    JPEGDecoder.jpeg_color_quantizer quantizer_2pass;
  }

  static final class jpeg_decompress_struct
  {
    boolean is_decompressor;
    int global_state;
    InputStream inputStream;
    byte[] buffer;
    int bytes_in_buffer;
    int bytes_offset;
    boolean start_of_file;
    int image_width;
    int image_height;
    int num_components;
    int jpeg_color_space;
    int out_color_space;
    int scale_num;
    int scale_denom;
    double output_gamma;
    boolean buffered_image;
    boolean raw_data_out;
    int dct_method;
    boolean do_fancy_upsampling;
    boolean do_block_smoothing;
    boolean quantize_colors;
    int dither_mode;
    boolean two_pass_quantize;
    int desired_number_of_colors;
    boolean enable_1pass_quant;
    boolean enable_external_quant;
    boolean enable_2pass_quant;
    int output_width;
    int output_height;
    int out_color_components;
    int output_components;
    int rec_outbuf_height;
    int actual_number_of_colors;
    int[] colormap;
    int output_scanline;
    int input_scan_number;
    int input_iMCU_row;
    int output_scan_number;
    int output_iMCU_row;
    int[][] coef_bits;
    JPEGDecoder.JQUANT_TBL[] quant_tbl_ptrs = new JPEGDecoder.JQUANT_TBL[4];
    JPEGDecoder.JHUFF_TBL[] dc_huff_tbl_ptrs = new JPEGDecoder.JHUFF_TBL[4];
    JPEGDecoder.JHUFF_TBL[] ac_huff_tbl_ptrs = new JPEGDecoder.JHUFF_TBL[4];
    int data_precision;
    JPEGDecoder.jpeg_component_info[] comp_info;
    boolean progressive_mode;
    boolean arith_code;
    byte[] arith_dc_L = new byte[16];
    byte[] arith_dc_U = new byte[16];
    byte[] arith_ac_K = new byte[16];
    int restart_interval;
    boolean saw_JFIF_marker;
    byte JFIF_major_version;
    byte JFIF_minor_version;
    byte density_unit;
    short X_density;
    short Y_density;
    boolean saw_Adobe_marker;
    byte Adobe_transform;
    boolean CCIR601_sampling;
    JPEGDecoder.jpeg_marker_reader marker_list;
    int max_h_samp_factor;
    int max_v_samp_factor;
    int min_DCT_scaled_size;
    int total_iMCU_rows;
    byte[] sample_range_limit;
    int sample_range_limit_offset;
    int comps_in_scan;
    JPEGDecoder.jpeg_component_info[] cur_comp_info = new JPEGDecoder.jpeg_component_info[4];
    int MCUs_per_row;
    int MCU_rows_in_scan;
    int blocks_in_MCU;
    int[] MCU_membership = new int[10];
    int Ss;
    int Se;
    int Ah;
    int Al;
    int unread_marker;
    int[] workspace = new int[64];
    int[] row_ctr = new int[1];
    JPEGDecoder.jpeg_decomp_master master;
    JPEGDecoder.jpeg_d_main_controller main;
    JPEGDecoder.jpeg_d_coef_controller coef;
    JPEGDecoder.jpeg_d_post_controller post;
    JPEGDecoder.jpeg_input_controller inputctl;
    JPEGDecoder.jpeg_marker_reader marker;
    JPEGDecoder.jpeg_entropy_decoder entropy;
    JPEGDecoder.jpeg_inverse_dct idct;
    JPEGDecoder.jpeg_upsampler upsample;
    JPEGDecoder.jpeg_color_deconverter cconvert;
    JPEGDecoder.jpeg_color_quantizer cquantize;
  }

  static abstract class jpeg_entropy_decoder
  {
    boolean insufficient_data;
    JPEGDecoder.bitread_working_state br_state_local = new JPEGDecoder.bitread_working_state();
    JPEGDecoder.savable_state state_local = new JPEGDecoder.savable_state();

    abstract void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct);

    abstract boolean decode_mcu(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort);
  }

  static final class jpeg_input_controller
  {
    int consume_input;
    boolean has_multiple_scans;
    boolean eoi_reached;
    boolean inheaders;
  }

  static final class jpeg_inverse_dct
  {
    int[] cur_method = new int[10];

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      jpeg_inverse_dct localjpeg_inverse_dct = paramjpeg_decompress_struct.idct;
      int k = 0;
      for (int i = 0; i < paramjpeg_decompress_struct.num_components; i++)
      {
        JPEGDecoder.jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.comp_info[i];
        switch (localjpeg_component_info.DCT_scaled_size)
        {
        case 8:
          switch (paramjpeg_decompress_struct.dct_method)
          {
          case 0:
            k = 0;
            break;
          default:
            JPEGDecoder.error();
          }
          break;
        default:
          JPEGDecoder.error();
        }
        if ((localjpeg_component_info.component_needed) && (localjpeg_inverse_dct.cur_method[i] != k))
        {
          JPEGDecoder.JQUANT_TBL localJQUANT_TBL = localjpeg_component_info.quant_table;
          if (localJQUANT_TBL != null)
          {
            localjpeg_inverse_dct.cur_method[i] = k;
            switch (k)
            {
            case 0:
              int[] arrayOfInt = localjpeg_component_info.dct_table;
              for (int j = 0; j < 64; j++)
                arrayOfInt[j] = localJQUANT_TBL.quantval[j];
              break;
            default:
              JPEGDecoder.error();
            }
          }
        }
      }
    }
  }

  static final class jpeg_marker_reader
  {
    boolean saw_SOI;
    boolean saw_SOF;
    int next_restart_num;
    int discarded_bytes;
    int length_limit_COM;
    int[] length_limit_APPn = new int[16];
  }

  static final class jpeg_upsampler
  {
    boolean need_context_rows;
    byte[][][] color_buf = new byte[10][][];
    int[] color_buf_offset = new int[10];
    int[] methods = new int[10];
    int next_row_out;
    int rows_to_go;
    int[] rowgroup_height = new int[10];
    byte[] h_expand = new byte[10];
    byte[] v_expand = new byte[10];

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      jpeg_upsampler localjpeg_upsampler = paramjpeg_decompress_struct.upsample;
      localjpeg_upsampler.next_row_out = paramjpeg_decompress_struct.max_v_samp_factor;
      localjpeg_upsampler.rows_to_go = paramjpeg_decompress_struct.output_height;
    }
  }

  static final class phuff_entropy_decoder extends JPEGDecoder.jpeg_entropy_decoder
  {
    JPEGDecoder.bitread_perm_state bitstate = new JPEGDecoder.bitread_perm_state();
    JPEGDecoder.savable_state saved = new JPEGDecoder.savable_state();
    int restarts_to_go;
    JPEGDecoder.d_derived_tbl[] derived_tbls = new JPEGDecoder.d_derived_tbl[4];
    JPEGDecoder.d_derived_tbl ac_derived_tbl;
    int[] newnz_pos = new int[64];

    void start_pass(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      start_pass_phuff_decoder(paramjpeg_decompress_struct);
    }

    boolean decode_mcu(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      int i = paramjpeg_decompress_struct.Ss == 0 ? 1 : 0;
      if (paramjpeg_decompress_struct.Ah == 0)
      {
        if (i != 0)
          return decode_mcu_DC_first(paramjpeg_decompress_struct, paramArrayOfShort);
        return decode_mcu_AC_first(paramjpeg_decompress_struct, paramArrayOfShort);
      }
      if (i != 0)
        return decode_mcu_DC_refine(paramjpeg_decompress_struct, paramArrayOfShort);
      return decode_mcu_AC_refine(paramjpeg_decompress_struct, paramArrayOfShort);
    }

    boolean decode_mcu_DC_refine(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      int i = 1 << paramjpeg_decompress_struct.Al;
      JPEGDecoder.bitread_working_state localbitread_working_state = this.br_state_local;
      if ((paramjpeg_decompress_struct.restart_interval != 0) && (localphuff_entropy_decoder.restarts_to_go == 0) && (!process_restart(paramjpeg_decompress_struct)))
        return false;
      localbitread_working_state.cinfo = paramjpeg_decompress_struct;
      localbitread_working_state.buffer = paramjpeg_decompress_struct.buffer;
      localbitread_working_state.bytes_in_buffer = paramjpeg_decompress_struct.bytes_in_buffer;
      localbitread_working_state.bytes_offset = paramjpeg_decompress_struct.bytes_offset;
      int k = localphuff_entropy_decoder.bitstate.get_buffer;
      int m = localphuff_entropy_decoder.bitstate.bits_left;
      for (int j = 0; j < paramjpeg_decompress_struct.blocks_in_MCU; j++)
      {
        short[] arrayOfShort = paramArrayOfShort[j];
        if (m < 1)
        {
          if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, k, m, 1))
            return false;
          k = localbitread_working_state.get_buffer;
          m = localbitread_working_state.bits_left;
        }
        if ((k >> --m & 0x1) != 0)
        {
          int tmp154_153 = 0;
          short[] tmp154_151 = arrayOfShort;
          tmp154_151[tmp154_153] = ((short)(tmp154_151[tmp154_153] | i));
        }
      }
      paramjpeg_decompress_struct.buffer = localbitread_working_state.buffer;
      paramjpeg_decompress_struct.bytes_in_buffer = localbitread_working_state.bytes_in_buffer;
      paramjpeg_decompress_struct.bytes_offset = localbitread_working_state.bytes_offset;
      localphuff_entropy_decoder.bitstate.get_buffer = k;
      localphuff_entropy_decoder.bitstate.bits_left = m;
      localphuff_entropy_decoder.restarts_to_go -= 1;
      return true;
    }

    boolean decode_mcu_AC_refine(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      int i = paramjpeg_decompress_struct.Se;
      int j = 1 << paramjpeg_decompress_struct.Al;
      int k = -1 << paramjpeg_decompress_struct.Al;
      int m = 0;
      JPEGDecoder.bitread_working_state localbitread_working_state = this.br_state_local;
      int[] arrayOfInt = localphuff_entropy_decoder.newnz_pos;
      if ((paramjpeg_decompress_struct.restart_interval != 0) && (localphuff_entropy_decoder.restarts_to_go == 0) && (!process_restart(paramjpeg_decompress_struct)))
        return false;
      if (!localphuff_entropy_decoder.insufficient_data)
      {
        localbitread_working_state.cinfo = paramjpeg_decompress_struct;
        localbitread_working_state.buffer = paramjpeg_decompress_struct.buffer;
        localbitread_working_state.bytes_in_buffer = paramjpeg_decompress_struct.bytes_in_buffer;
        localbitread_working_state.bytes_offset = paramjpeg_decompress_struct.bytes_offset;
        int i3 = localphuff_entropy_decoder.bitstate.get_buffer;
        int i4 = localphuff_entropy_decoder.bitstate.bits_left;
        int i2 = localphuff_entropy_decoder.saved.EOBRUN;
        short[] arrayOfShort1 = paramArrayOfShort[0];
        JPEGDecoder.d_derived_tbl locald_derived_tbl = localphuff_entropy_decoder.ac_derived_tbl;
        int i5 = 0;
        int n = paramjpeg_decompress_struct.Ss;
        int i6;
        short[] arrayOfShort2;
        if (i2 == 0)
          while (n <= i)
          {
            i6 = 0;
            if (i4 < 8)
            {
              if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i3, i4, 0))
              {
                while (i5 > 0)
                  arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                return false;
              }
              i3 = localbitread_working_state.get_buffer;
              i4 = localbitread_working_state.bits_left;
              if (i4 < 8)
              {
                i6 = 1;
                if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i3, i4, locald_derived_tbl, i6)) < 0)
                {
                  while (i5 > 0)
                    arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                  return false;
                }
                i3 = localbitread_working_state.get_buffer;
                i4 = localbitread_working_state.bits_left;
              }
            }
            if (i6 != 1)
            {
              int i7 = i3 >> i4 - 8 & 0xFF;
              if ((i6 = locald_derived_tbl.look_nbits[i7]) != 0)
              {
                i4 -= i6;
                m = locald_derived_tbl.look_sym[i7] & 0xFF;
              }
              else
              {
                i6 = 9;
                if ((m = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i3, i4, locald_derived_tbl, i6)) < 0)
                {
                  while (i5 > 0)
                    arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                  return false;
                }
                i3 = localbitread_working_state.get_buffer;
                i4 = localbitread_working_state.bits_left;
              }
            }
            int i1 = m >> 4;
            m &= 15;
            if (m != 0)
            {
              if (i4 < 1)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i3, i4, 1))
                {
                  while (i5 > 0)
                    arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                  return false;
                }
                i3 = localbitread_working_state.get_buffer;
                i4 = localbitread_working_state.bits_left;
              }
              if ((i3 >> --i4 & 0x1) != 0)
                m = j;
              else
                m = k;
            }
            else if (i1 != 15)
            {
              i2 = 1 << i1;
              if (i1 == 0)
                break;
              if (i4 < i1)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i3, i4, i1))
                {
                  while (i5 > 0)
                    arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                  return false;
                }
                i3 = localbitread_working_state.get_buffer;
                i4 = localbitread_working_state.bits_left;
              }
              i1 = i3 >> i4 -= i1 & (1 << i1) - 1;
              i2 += i1;
              break;
            }
            do
            {
              arrayOfShort2 = arrayOfShort1;
              i6 = JPEGDecoder.jpeg_natural_order[n];
              if (arrayOfShort2[i6] != 0)
              {
                if (i4 < 1)
                {
                  if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i3, i4, 1))
                  {
                    while (i5 > 0)
                      arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                    return false;
                  }
                  i3 = localbitread_working_state.get_buffer;
                  i4 = localbitread_working_state.bits_left;
                }
                if (((i3 >> --i4 & 0x1) != 0) && ((arrayOfShort2[i6] & j) == 0))
                  if (arrayOfShort2[i6] >= 0)
                  {
                    int tmp715_713 = i6;
                    short[] tmp715_711 = arrayOfShort2;
                    tmp715_711[tmp715_713] = ((short)(tmp715_711[tmp715_713] + j));
                  }
                  else
                  {
                    int tmp729_727 = i6;
                    short[] tmp729_725 = arrayOfShort2;
                    tmp729_725[tmp729_727] = ((short)(tmp729_725[tmp729_727] + k));
                  }
              }
              else
              {
                i1--;
                if (i1 < 0)
                  break;
              }
              n++;
            }
            while (n <= i);
            if (m != 0)
            {
              i6 = JPEGDecoder.jpeg_natural_order[n];
              arrayOfShort1[i6] = ((short)m);
              arrayOfInt[(i5++)] = i6;
            }
            n++;
          }
        if (i2 > 0)
        {
          while (n <= i)
          {
            arrayOfShort2 = arrayOfShort1;
            i6 = JPEGDecoder.jpeg_natural_order[n];
            if (arrayOfShort2[i6] != 0)
            {
              if (i4 < 1)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i3, i4, 1))
                {
                  while (i5 > 0)
                    arrayOfShort1[arrayOfInt[(--i5)]] = 0;
                  return false;
                }
                i3 = localbitread_working_state.get_buffer;
                i4 = localbitread_working_state.bits_left;
              }
              if (((i3 >> --i4 & 0x1) != 0) && ((arrayOfShort2[i6] & j) == 0))
                if (arrayOfShort2[i6] >= 0)
                {
                  int tmp920_918 = i6;
                  short[] tmp920_916 = arrayOfShort2;
                  tmp920_916[tmp920_918] = ((short)(tmp920_916[tmp920_918] + j));
                }
                else
                {
                  int tmp934_932 = i6;
                  short[] tmp934_930 = arrayOfShort2;
                  tmp934_930[tmp934_932] = ((short)(tmp934_930[tmp934_932] + k));
                }
            }
            n++;
          }
          i2--;
        }
        paramjpeg_decompress_struct.buffer = localbitread_working_state.buffer;
        paramjpeg_decompress_struct.bytes_in_buffer = localbitread_working_state.bytes_in_buffer;
        paramjpeg_decompress_struct.bytes_offset = localbitread_working_state.bytes_offset;
        localphuff_entropy_decoder.bitstate.get_buffer = i3;
        localphuff_entropy_decoder.bitstate.bits_left = i4;
        localphuff_entropy_decoder.saved.EOBRUN = i2;
      }
      localphuff_entropy_decoder.restarts_to_go -= 1;
      return true;
    }

    boolean decode_mcu_AC_first(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      int i = paramjpeg_decompress_struct.Se;
      int j = paramjpeg_decompress_struct.Al;
      int k = 0;
      JPEGDecoder.bitread_working_state localbitread_working_state = this.br_state_local;
      if ((paramjpeg_decompress_struct.restart_interval != 0) && (localphuff_entropy_decoder.restarts_to_go == 0) && (!process_restart(paramjpeg_decompress_struct)))
        return false;
      if (!localphuff_entropy_decoder.insufficient_data)
      {
        int i1 = localphuff_entropy_decoder.saved.EOBRUN;
        if (i1 > 0)
        {
          i1--;
        }
        else
        {
          localbitread_working_state.cinfo = paramjpeg_decompress_struct;
          localbitread_working_state.buffer = paramjpeg_decompress_struct.buffer;
          localbitread_working_state.bytes_in_buffer = paramjpeg_decompress_struct.bytes_in_buffer;
          localbitread_working_state.bytes_offset = paramjpeg_decompress_struct.bytes_offset;
          int i2 = localphuff_entropy_decoder.bitstate.get_buffer;
          int i3 = localphuff_entropy_decoder.bitstate.bits_left;
          short[] arrayOfShort = paramArrayOfShort[0];
          JPEGDecoder.d_derived_tbl locald_derived_tbl = localphuff_entropy_decoder.ac_derived_tbl;
          for (int m = paramjpeg_decompress_struct.Ss; m <= i; m++)
          {
            int i4 = 0;
            if (i3 < 8)
            {
              if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i2, i3, 0))
                return false;
              i2 = localbitread_working_state.get_buffer;
              i3 = localbitread_working_state.bits_left;
              if (i3 < 8)
              {
                i4 = 1;
                if ((k = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i2, i3, locald_derived_tbl, i4)) < 0)
                  return false;
                i2 = localbitread_working_state.get_buffer;
                i3 = localbitread_working_state.bits_left;
              }
            }
            if (i4 != 1)
            {
              int i5 = i2 >> i3 - 8 & 0xFF;
              if ((i4 = locald_derived_tbl.look_nbits[i5]) != 0)
              {
                i3 -= i4;
                k = locald_derived_tbl.look_sym[i5] & 0xFF;
              }
              else
              {
                i4 = 9;
                if ((k = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i2, i3, locald_derived_tbl, i4)) < 0)
                  return false;
                i2 = localbitread_working_state.get_buffer;
                i3 = localbitread_working_state.bits_left;
              }
            }
            int n = k >> 4;
            k &= 15;
            if (k != 0)
            {
              m += n;
              if (i3 < k)
              {
                if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i2, i3, k))
                  return false;
                i2 = localbitread_working_state.get_buffer;
                i3 = localbitread_working_state.bits_left;
              }
              n = i2 >> i3 -= k & (1 << k) - 1;
              k = n < JPEGDecoder.extend_test[k] ? n + JPEGDecoder.extend_offset[k] : n;
              arrayOfShort[JPEGDecoder.jpeg_natural_order[m]] = ((short)(k << j));
            }
            else if (n == 15)
            {
              m += 15;
            }
            else
            {
              i1 = 1 << n;
              if (n != 0)
              {
                if (i3 < n)
                {
                  if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i2, i3, n))
                    return false;
                  i2 = localbitread_working_state.get_buffer;
                  i3 = localbitread_working_state.bits_left;
                }
                n = i2 >> i3 -= n & (1 << n) - 1;
                i1 += n;
              }
              i1--;
              break;
            }
          }
          paramjpeg_decompress_struct.buffer = localbitread_working_state.buffer;
          paramjpeg_decompress_struct.bytes_in_buffer = localbitread_working_state.bytes_in_buffer;
          paramjpeg_decompress_struct.bytes_offset = localbitread_working_state.bytes_offset;
          localphuff_entropy_decoder.bitstate.get_buffer = i2;
          localphuff_entropy_decoder.bitstate.bits_left = i3;
        }
        localphuff_entropy_decoder.saved.EOBRUN = i1;
      }
      localphuff_entropy_decoder.restarts_to_go -= 1;
      return true;
    }

    boolean decode_mcu_DC_first(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct, short[][] paramArrayOfShort)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      int i = paramjpeg_decompress_struct.Al;
      int j = 0;
      JPEGDecoder.bitread_working_state localbitread_working_state = this.br_state_local;
      JPEGDecoder.savable_state localsavable_state = this.state_local;
      if ((paramjpeg_decompress_struct.restart_interval != 0) && (localphuff_entropy_decoder.restarts_to_go == 0) && (!process_restart(paramjpeg_decompress_struct)))
        return false;
      if (!localphuff_entropy_decoder.insufficient_data)
      {
        localbitread_working_state.cinfo = paramjpeg_decompress_struct;
        localbitread_working_state.buffer = paramjpeg_decompress_struct.buffer;
        localbitread_working_state.bytes_in_buffer = paramjpeg_decompress_struct.bytes_in_buffer;
        localbitread_working_state.bytes_offset = paramjpeg_decompress_struct.bytes_offset;
        int i1 = localphuff_entropy_decoder.bitstate.get_buffer;
        int i2 = localphuff_entropy_decoder.bitstate.bits_left;
        localsavable_state.EOBRUN = localphuff_entropy_decoder.saved.EOBRUN;
        localsavable_state.last_dc_val[0] = localphuff_entropy_decoder.saved.last_dc_val[0];
        localsavable_state.last_dc_val[1] = localphuff_entropy_decoder.saved.last_dc_val[1];
        localsavable_state.last_dc_val[2] = localphuff_entropy_decoder.saved.last_dc_val[2];
        localsavable_state.last_dc_val[3] = localphuff_entropy_decoder.saved.last_dc_val[3];
        for (int m = 0; m < paramjpeg_decompress_struct.blocks_in_MCU; m++)
        {
          short[] arrayOfShort = paramArrayOfShort[m];
          int n = paramjpeg_decompress_struct.MCU_membership[m];
          JPEGDecoder.jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[n];
          JPEGDecoder.d_derived_tbl locald_derived_tbl = localphuff_entropy_decoder.derived_tbls[localjpeg_component_info.dc_tbl_no];
          int i3 = 0;
          if (i2 < 8)
          {
            if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i1, i2, 0))
              return false;
            i1 = localbitread_working_state.get_buffer;
            i2 = localbitread_working_state.bits_left;
            if (i2 < 8)
            {
              i3 = 1;
              if ((j = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i1, i2, locald_derived_tbl, i3)) < 0)
                return false;
              i1 = localbitread_working_state.get_buffer;
              i2 = localbitread_working_state.bits_left;
            }
          }
          if (i3 != 1)
          {
            int i4 = i1 >> i2 - 8 & 0xFF;
            if ((i3 = locald_derived_tbl.look_nbits[i4]) != 0)
            {
              i2 -= i3;
              j = locald_derived_tbl.look_sym[i4] & 0xFF;
            }
            else
            {
              i3 = 9;
              if ((j = JPEGDecoder.jpeg_huff_decode(localbitread_working_state, i1, i2, locald_derived_tbl, i3)) < 0)
                return false;
              i1 = localbitread_working_state.get_buffer;
              i2 = localbitread_working_state.bits_left;
            }
          }
          if (j != 0)
          {
            if (i2 < j)
            {
              if (!JPEGDecoder.jpeg_fill_bit_buffer(localbitread_working_state, i1, i2, j))
                return false;
              i1 = localbitread_working_state.get_buffer;
              i2 = localbitread_working_state.bits_left;
            }
            int k = i1 >> i2 -= j & (1 << j) - 1;
            j = k < JPEGDecoder.extend_test[j] ? k + JPEGDecoder.extend_offset[j] : k;
          }
          j += localsavable_state.last_dc_val[n];
          localsavable_state.last_dc_val[n] = j;
          arrayOfShort[0] = ((short)(j << i));
        }
        paramjpeg_decompress_struct.buffer = localbitread_working_state.buffer;
        paramjpeg_decompress_struct.bytes_in_buffer = localbitread_working_state.bytes_in_buffer;
        paramjpeg_decompress_struct.bytes_offset = localbitread_working_state.bytes_offset;
        localphuff_entropy_decoder.bitstate.get_buffer = i1;
        localphuff_entropy_decoder.bitstate.bits_left = i2;
        localphuff_entropy_decoder.saved.EOBRUN = localsavable_state.EOBRUN;
        localphuff_entropy_decoder.saved.last_dc_val[0] = localsavable_state.last_dc_val[0];
        localphuff_entropy_decoder.saved.last_dc_val[1] = localsavable_state.last_dc_val[1];
        localphuff_entropy_decoder.saved.last_dc_val[2] = localsavable_state.last_dc_val[2];
        localphuff_entropy_decoder.saved.last_dc_val[3] = localsavable_state.last_dc_val[3];
      }
      localphuff_entropy_decoder.restarts_to_go -= 1;
      return true;
    }

    boolean process_restart(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      paramjpeg_decompress_struct.marker.discarded_bytes += localphuff_entropy_decoder.bitstate.bits_left / 8;
      localphuff_entropy_decoder.bitstate.bits_left = 0;
      if (!JPEGDecoder.read_restart_marker(paramjpeg_decompress_struct))
        return false;
      for (int i = 0; i < paramjpeg_decompress_struct.comps_in_scan; i++)
        localphuff_entropy_decoder.saved.last_dc_val[i] = 0;
      localphuff_entropy_decoder.saved.EOBRUN = 0;
      localphuff_entropy_decoder.restarts_to_go = paramjpeg_decompress_struct.restart_interval;
      if (paramjpeg_decompress_struct.unread_marker == 0)
        localphuff_entropy_decoder.insufficient_data = false;
      return true;
    }

    void start_pass_phuff_decoder(JPEGDecoder.jpeg_decompress_struct paramjpeg_decompress_struct)
    {
      phuff_entropy_decoder localphuff_entropy_decoder = this;
      int i = paramjpeg_decompress_struct.Ss == 0 ? 1 : 0;
      int j = 0;
      if (i != 0)
      {
        if (paramjpeg_decompress_struct.Se != 0)
          j = 1;
      }
      else
      {
        if ((paramjpeg_decompress_struct.Ss > paramjpeg_decompress_struct.Se) || (paramjpeg_decompress_struct.Se >= 64))
          j = 1;
        if (paramjpeg_decompress_struct.comps_in_scan != 1)
          j = 1;
      }
      if ((paramjpeg_decompress_struct.Ah != 0) && (paramjpeg_decompress_struct.Al != paramjpeg_decompress_struct.Ah - 1))
        j = 1;
      if (paramjpeg_decompress_struct.Al > 13)
        j = 1;
      if (j != 0)
        JPEGDecoder.error();
      for (int k = 0; k < paramjpeg_decompress_struct.comps_in_scan; k++)
      {
        int i1 = paramjpeg_decompress_struct.cur_comp_info[k].component_index;
        int[] arrayOfInt = paramjpeg_decompress_struct.coef_bits[i1];
        if (i == 0)
          arrayOfInt[0];
        for (int m = paramjpeg_decompress_struct.Ss; m <= paramjpeg_decompress_struct.Se; m++)
        {
          int i2 = arrayOfInt[m] < 0 ? 0 : arrayOfInt[m];
          arrayOfInt[m] = paramjpeg_decompress_struct.Al;
        }
      }
      for (k = 0; k < paramjpeg_decompress_struct.comps_in_scan; k++)
      {
        JPEGDecoder.jpeg_component_info localjpeg_component_info = paramjpeg_decompress_struct.cur_comp_info[k];
        int n;
        if (i != 0)
        {
          if (paramjpeg_decompress_struct.Ah == 0)
          {
            n = localjpeg_component_info.dc_tbl_no;
            JPEGDecoder.jpeg_make_d_derived_tbl(paramjpeg_decompress_struct, true, n, localphuff_entropy_decoder.derived_tbls[n] =  = new JPEGDecoder.d_derived_tbl());
          }
        }
        else
        {
          n = localjpeg_component_info.ac_tbl_no;
          JPEGDecoder.jpeg_make_d_derived_tbl(paramjpeg_decompress_struct, false, n, localphuff_entropy_decoder.derived_tbls[n] =  = new JPEGDecoder.d_derived_tbl());
          localphuff_entropy_decoder.ac_derived_tbl = localphuff_entropy_decoder.derived_tbls[n];
        }
        localphuff_entropy_decoder.saved.last_dc_val[k] = 0;
      }
      localphuff_entropy_decoder.bitstate.bits_left = 0;
      localphuff_entropy_decoder.bitstate.get_buffer = 0;
      localphuff_entropy_decoder.insufficient_data = false;
      localphuff_entropy_decoder.saved.EOBRUN = 0;
      localphuff_entropy_decoder.restarts_to_go = paramjpeg_decompress_struct.restart_interval;
    }
  }

  static final class savable_state
  {
    int EOBRUN;
    int[] last_dc_val = new int[4];
  }
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     org.eclipse.swt.internal.image.JPEGDecoder
 * JD-Core Version:    0.6.2
 */