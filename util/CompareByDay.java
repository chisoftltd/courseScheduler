/*    */ package util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.Map;
/*    */ import models.Range;
/*    */ import models.Section;
/*    */ 
/*    */ public class CompareByDay
/*    */   implements Comparator<Section>
/*    */ {
/*    */   public int compare(Section sec1, Section sec2)
/*    */   {
/* 13 */     String sec1Day = "";
/* 14 */     String sec2Day = "";
/* 15 */     int ret = 0;
/* 16 */     for (String day : sec1.getDayToTime().keySet()) {
/* 17 */       sec1Day = day;
/*    */     }
/* 19 */     for (String day : sec2.getDayToTime().keySet()) {
/* 20 */       sec2Day = day;
/*    */     }
/* 22 */     ret = sec1Day.compareTo(sec2Day);
/* 23 */     if (ret != 0) {
/* 24 */       return ret;
/*    */     }
/* 26 */     Range r1 = (Range)sec1.getDayToTime().get(sec1Day);
/* 27 */     Range r2 = (Range)sec2.getDayToTime().get(sec2Day);
/* 28 */     ret = r1.getStart() < r2.getStart() ? -1 : 1;
/* 29 */     if (r1.getStart() == r2.getStart())
/* 30 */       ret = 0;
/* 31 */     return ret;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     util.CompareByDay
 * JD-Core Version:    0.6.2
 */