/*    */ package util;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PageRetriever
/*    */ {
/* 16 */   String SEARCHURL = "http://rabi.phys.virginia.edu/mySIS/CS2/page.php?Semester=1112&Type=Search";
/*    */ 
/*    */   public ArrayList<String> getCoursePages(List<String[]> coursesToSearch) {
/* 19 */     ArrayList pages = new ArrayList();
/*    */ 
/* 23 */     for (String[] course : coursesToSearch) {
/* 24 */       String[] adaptedCourse = new String[11];
/* 25 */       for (int i = 0; i < course.length; i++) {
/* 26 */         adaptedCourse[i] = course[i];
/*    */       }
/* 28 */       pages.add(search(adaptedCourse));
/*    */     }
/*    */ 
/* 34 */     return pages;
/*    */   }
/*    */ 
/*    */   public String search(String[] values) {
/* 38 */     String[] inputs = { "iMnemonic", "iNumber", "iInstructor", "iBuilding", 
/* 39 */       "iRoom", "iDays", "iTime", "iUnits", "iTitle", "iTopic", 
/* 40 */       "iDescription" };
/* 41 */     String page = "";
/* 42 */     Map data = new HashMap();
/* 43 */     for (int i = 0; i < inputs.length; i++)
/* 44 */       data.put(inputs[i], values[i] == null ? "" : values[i]);
/*    */     try
/*    */     {
/* 47 */       URL siteUrl = new URL(this.SEARCHURL);
/* 48 */       HttpURLConnection conn = (HttpURLConnection)siteUrl
/* 49 */         .openConnection();
/* 50 */       conn.setRequestMethod("POST");
/* 51 */       conn.setDoOutput(true);
/* 52 */       conn.setDoInput(true);
/*    */ 
/* 54 */       DataOutputStream out = new DataOutputStream(conn.getOutputStream());
/*    */ 
/* 56 */       String content = "";
/* 57 */       for (int i = 0; i < inputs.length; i++)
/*    */       {
/* 59 */         if (i != 0) {
/* 60 */           content = content + "&";
/*    */         }
/* 62 */         content = content + inputs[i] + 
/* 63 */           "=" + 
/* 64 */           URLEncoder.encode(values[i] == null ? "" : values[i], 
/* 65 */           "UTF-8");
/*    */       }
/*    */ 
/* 68 */       out.writeBytes(content);
/* 69 */       out.flush();
/* 70 */       out.close();
/* 71 */       BufferedReader in = new BufferedReader(new InputStreamReader(
/* 72 */         conn.getInputStream()));
/*    */ 
/* 74 */       File output = new File("out.html");
/* 75 */       if (output.isFile()) {
/* 76 */         output.delete();
/* 77 */         output.createNewFile();
/*    */       }
/* 79 */       while (in.ready()) {
/* 80 */         page = page + in.readLine() + "\n";
/*    */       }
/* 82 */       in.close();
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/*    */     }
/*    */ 
/* 90 */     return page;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     util.PageRetriever
 * JD-Core Version:    0.6.2
 */