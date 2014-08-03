/*    */ package scheduleRankers;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import models.Schedule;
/*    */ import models.Section;
/*    */ 
/*    */ public class NoDaysRanker
/*    */   implements ScheduleRanker
/*    */ {
/*    */   Set<String> days;
/*    */ 
/*    */   public NoDaysRanker(List<String> givenDays)
/*    */   {
/* 18 */     this.days = new HashSet(givenDays);
/*    */   }
/*    */ 
/*    */   public int rank(Schedule schedule)
/*    */   {
/* 23 */     int occurencies = 0;
/*    */     Iterator localIterator2;
/* 25 */     for (Iterator localIterator1 = schedule.getSections().iterator(); localIterator1.hasNext(); 
/* 26 */       localIterator2.hasNext())
/*    */     {
/* 25 */       Section sec = (Section)localIterator1.next();
/* 26 */       localIterator2 = sec.getDayToTime().keySet().iterator(); continue; String day = (String)localIterator2.next();
/* 27 */       if (this.days.contains(day)) {
/* 28 */         occurencies++;
/*    */       }
/*    */     }
/*    */ 
/* 32 */     return occurencies;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.NoDaysRanker
 * JD-Core Version:    0.6.2
 */