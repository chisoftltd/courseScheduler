/*    */ package models;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import util.CompareByDay;
/*    */ 
/*    */ public class Schedule
/*    */ {
/*    */   private List<Section> sections;
/*    */   private double rating;
/*    */ 
/*    */   public Schedule()
/*    */   {
/* 13 */     this.rating = 0.0D;
/* 14 */     this.sections = new ArrayList();
/*    */   }
/*    */ 
/*    */   public void setSections(List<Section> sections) {
/* 18 */     this.sections = sections;
/*    */   }
/*    */   public void addSection(Section sec) {
/* 21 */     this.sections.add(sec);
/*    */   }
/*    */   public List<Section> getSections() {
/* 24 */     return this.sections;
/*    */   }
/*    */   public double getRating() {
/* 27 */     return this.rating;
/*    */   }
/*    */   public void increaseRatingBy(double rating) {
/* 30 */     this.rating += rating;
/*    */   }
/*    */   public String toString() {
/* 33 */     String ret = "";
/* 34 */     Collections.sort(this.sections, new CompareByDay());
/* 35 */     for (Section s : this.sections) {
/* 36 */       ret = ret + s.toString() + "\n";
/*    */     }
/* 38 */     return ret;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     models.Schedule
 * JD-Core Version:    0.6.2
 */