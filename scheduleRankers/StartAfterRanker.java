/*    */ package scheduleRankers;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import models.Range;
/*    */ import models.Schedule;
/*    */ import models.Section;
/*    */ 
/*    */ public class StartAfterRanker
/*    */   implements ScheduleRanker
/*    */ {
/*    */   double time;
/*    */ 
/*    */   public StartAfterRanker(double givenTime)
/*    */   {
/* 10 */     this.time = givenTime;
/*    */   }
/*    */ 
/*    */   public int rank(Schedule schedule)
/*    */   {
/* 15 */     int occurencies = 0;
/*    */     Iterator localIterator2;
/* 17 */     for (Iterator localIterator1 = schedule.getSections().iterator(); localIterator1.hasNext(); 
/* 18 */       localIterator2.hasNext())
/*    */     {
/* 17 */       Section sec = (Section)localIterator1.next();
/* 18 */       localIterator2 = sec.getDayToTime().keySet().iterator(); continue; String day = (String)localIterator2.next();
/* 19 */       Range r = (Range)sec.getDayToTime().get(day);
/* 20 */       if (r.getStart() < this.time) {
/* 21 */         occurencies++;
/*    */       }
/*    */     }
/*    */ 
/* 25 */     return occurencies;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.StartAfterRanker
 * JD-Core Version:    0.6.2
 */