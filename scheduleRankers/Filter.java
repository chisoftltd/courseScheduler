/*    */ package scheduleRankers;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import models.Schedule;
/*    */ 
/*    */ public class Filter
/*    */ {
/*    */   List<ScheduleRanker> rankers;
/*    */ 
/*    */   public Filter(List<ScheduleRanker> rankers)
/*    */   {
/* 17 */     this.rankers = rankers;
/*    */   }
/*    */ 
/*    */   public List<Schedule> filter(List<Schedule> oldSchedules)
/*    */   {
/* 23 */     for (ScheduleRanker ranker : this.rankers) {
/* 24 */       oldSchedules = filterWith(ranker, oldSchedules);
/*    */     }
/* 26 */     Collections.sort(oldSchedules, new sortByRating());
/* 27 */     List removeMe = new ArrayList();
/* 28 */     for (int i = 0; i < oldSchedules.size(); i++) {
/* 29 */       if (((Schedule)oldSchedules.get(i)).getRating() > 0.0D) {
/* 30 */         removeMe.add((Schedule)oldSchedules.get(i));
/*    */       }
/*    */     }
/* 33 */     oldSchedules.removeAll(removeMe);
/* 34 */     return oldSchedules;
/*    */   }
/*    */ 
/*    */   public List<Schedule> filterWith(ScheduleRanker rank, List<Schedule> oldSchedules)
/*    */   {
/* 39 */     for (Schedule sched : oldSchedules) {
/* 40 */       sched.increaseRatingBy(rank.rank(sched));
/*    */     }
/* 42 */     return oldSchedules;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.Filter
 * JD-Core Version:    0.6.2
 */