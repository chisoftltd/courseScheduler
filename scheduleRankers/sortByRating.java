/*    */ package scheduleRankers;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import models.Schedule;
/*    */ 
/*    */ public class sortByRating
/*    */   implements Comparator<Schedule>
/*    */ {
/*    */   public int compare(Schedule s1, Schedule s2)
/*    */   {
/* 12 */     if (s1.getRating() == s2.getRating()) {
/* 13 */       return 0;
/*    */     }
/* 15 */     if (s1.getRating() < s2.getRating()) {
/* 16 */       return -1;
/*    */     }
/* 18 */     return 1;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.sortByRating
 * JD-Core Version:    0.6.2
 */