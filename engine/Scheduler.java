/*    */ package engine;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import models.Course;
/*    */ import models.Range;
/*    */ import models.Schedule;
/*    */ import models.Section;
/*    */ 
/*    */ public class Scheduler
/*    */ {
/*    */   List<Schedule> finalSchedules;
/*    */   private static Scheduler scheduler;
/*    */ 
/*    */   public static Scheduler getInstance()
/*    */   {
/* 23 */     if (scheduler == null) {
/* 24 */       scheduler = new Scheduler();
/*    */     }
/* 26 */     return scheduler;
/*    */   }
/*    */ 
/*    */   public List<Schedule> makeSchedule(List<Course> courses)
/*    */   {
/* 31 */     List generated = new ArrayList();
/* 32 */     for (int i = 0; i < courses.size(); i++) {
/* 33 */       generated = incorporateCourse(generated, (Course)courses.get(i));
/*    */     }
/*    */ 
/* 37 */     return generated;
/*    */   }
/*    */ 
/*    */   public List<Schedule> incorporateCourse(List<Schedule> oldSchedules, Course course)
/*    */   {
/* 42 */     List newSchedules = new ArrayList();
/* 43 */     if (oldSchedules.isEmpty()) {
/* 44 */       for (Section sec : course.getLectures()) {
/* 45 */         Schedule sched = new Schedule();
/* 46 */         sched.addSection(sec);
/* 47 */         newSchedules.add(sched);
/*    */       }
/* 49 */       return newSchedules;
/*    */     }
/*    */     Iterator localIterator2;
/* 51 */     for (??? = oldSchedules.iterator(); ???.hasNext(); 
/* 52 */       localIterator2.hasNext())
/*    */     {
/* 51 */       Schedule sched = (Schedule)???.next();
/* 52 */       localIterator2 = course.getLectures().iterator(); continue; Section courseSection = (Section)localIterator2.next();
/* 53 */       boolean conflicts = false;
/* 54 */       for (Section sec2 : sched.getSections()) {
/* 55 */         conflicts = isConflicting(courseSection, sec2);
/* 56 */         if (conflicts)
/*    */         {
/*    */           break;
/*    */         }
/*    */       }
/* 61 */       if (!conflicts) {
/* 62 */         Schedule generatedSched = new Schedule();
/* 63 */         Object combined = new ArrayList();
/* 64 */         ((List)combined).addAll(sched.getSections());
/* 65 */         ((List)combined).add(courseSection);
/* 66 */         generatedSched.setSections((List)combined);
/* 67 */         newSchedules.add(generatedSched);
/*    */       }
/*    */     }
/*    */ 
/* 71 */     return newSchedules;
/*    */   }
/*    */ 
/*    */   public boolean isConflicting(Section a, Section b)
/*    */   {
/* 78 */     Section small = a.getDayToTime().keySet().size() < b.getDayToTime()
/* 79 */       .keySet().size() ? a : b;
/* 80 */     Section big = small.equals(a) ? b : a;
/* 81 */     for (String day : small.getDayToTime().keySet()) {
/* 82 */       Range smallDayRange = (Range)small.getDayToTime().get(day);
/*    */ 
/* 84 */       if (big.getDayToTime().containsKey(day)) {
/* 85 */         Range bigDayRange = (Range)big.getDayToTime().get(day);
/* 86 */         return Range.conflict(smallDayRange, bigDayRange);
/*    */       }
/*    */     }
/*    */ 
/* 90 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     engine.Scheduler
 * JD-Core Version:    0.6.2
 */