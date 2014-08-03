/*    */ package models;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class Course
/*    */ {
/*    */   private String courseName;
/*    */   private String courseNum;
/*    */   private List<Section> lectures;
/*    */   private List<Section> labs;
/*    */ 
/*    */   public String getCourseName()
/*    */   {
/* 13 */     return this.courseName;
/*    */   }
/*    */   public void setCourseName(String courseName) {
/* 16 */     this.courseName = courseName;
/*    */   }
/*    */   public String getCourseNum() {
/* 19 */     return this.courseNum;
/*    */   }
/*    */   public void setCourseNum(String courseNum) {
/* 22 */     this.courseNum = courseNum;
/*    */   }
/*    */   public List<Section> getLectures() {
/* 25 */     return this.lectures;
/*    */   }
/*    */   public void setLectures(List<Section> sections2) {
/* 28 */     this.lectures = sections2;
/*    */   }
/*    */   public String toString() {
/* 31 */     return "courseNumber: " + this.courseNum;
/*    */   }
/*    */   public void setLabs(List<Section> labs) {
/* 34 */     this.labs = labs;
/*    */   }
/*    */   public List<Section> getLabs() {
/* 37 */     return this.labs;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     models.Course
 * JD-Core Version:    0.6.2
 */