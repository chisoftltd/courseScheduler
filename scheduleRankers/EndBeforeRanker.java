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
/*    */ public class EndBeforeRanker
/*    */   implements ScheduleRanker
/*    */ {
/*    */   private double time;
/*    */ 
/*    */   public EndBeforeRanker(double time)
/*    */   {
/* 11 */     this.time = time;
/*    */   }
/*    */ 
/*    */   public int rank(Schedule schedule)
/*    */   {
/* 18 */     int occurencies = 0;
/*    */     Iterator localIterator2;
/* 20 */     for (Iterator localIterator1 = schedule.getSections().iterator(); localIterator1.hasNext(); 
/* 21 */       localIterator2.hasNext())
/*    */     {
/* 20 */       Section sec = (Section)localIterator1.next();
/* 21 */       localIterator2 = sec.getDayToTime().keySet().iterator(); continue; String day = (String)localIterator2.next();
/* 22 */       Range r = (Range)sec.getDayToTime().get(day);
/* 23 */       if (r.getEnd() > this.time) {
/* 24 */         occurencies++;
/*    */       }
/*    */     }
/*    */ 
/* 28 */     return occurencies;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.EndBeforeRanker
 * JD-Core Version:    0.6.2
 */