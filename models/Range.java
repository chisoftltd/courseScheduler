/*    */ package models;
/*    */ 
/*    */ public class Range
/*    */ {
/*  6 */   private double start = 0.0D;
/*  7 */   private double end = 0.0D;
/*    */ 
/*    */   public Range(double startVal, double endVal) {
/* 10 */     this.start = startVal;
/* 11 */     this.end = endVal;
/*    */   }
/*    */ 
/*    */   public void setVals(double startVal, double endVal)
/*    */   {
/* 16 */     this.start = startVal;
/* 17 */     this.end = endVal;
/*    */   }
/*    */ 
/*    */   public double getStart() {
/* 21 */     return this.start;
/*    */   }
/*    */ 
/*    */   public double getEnd() {
/* 25 */     return this.end;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 29 */     return "( " + this.start + "-" + this.end + ")";
/*    */   }
/*    */ 
/*    */   public double getSize() {
/* 33 */     return getEnd() - getStart();
/*    */   }
/*    */ 
/*    */   public static boolean conflict(Range a, Range b) {
/* 37 */     if (a.getStart() == b.getStart()) {
/* 38 */       return true;
/*    */     }
/* 40 */     if (a.getSize() > b.getSize()) {
/* 41 */       return (b.getStart() < a.getEnd()) && (b.getEnd() > a.getEnd());
/*    */     }
/* 43 */     if (b.getSize() > a.getSize()) {
/* 44 */       return (a.getStart() < b.getEnd()) && (a.getEnd() > b.getEnd());
/*    */     }
/*    */ 
/* 47 */     if (a.getSize() == b.getSize()) {
/* 48 */       return (a.getStart() < b.getEnd()) && (a.getEnd() > b.getStart());
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     models.Range
 * JD-Core Version:    0.6.2
 */