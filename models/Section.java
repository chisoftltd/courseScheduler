/*     */ package models;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Section
/*     */ {
/*     */   private Map<String, String[]> daysToMeetingTimeAndLocations;
/*     */   private Map<String, Range> dayToTime;
/*     */   private String sectionNum;
/*     */   private String instructor;
/*     */   private String location;
/*     */   private String courseNum;
/*     */   private String status;
/*     */   private String type;
/*     */   private String credits;
/*     */   private String open;
/*     */   private String schedule;
/*     */   private String courseMnemNum;
/*     */ 
/*     */   public Section(String sectionNum, String instructor, String location, String coursenum, String status)
/*     */   {
/*   8 */     this.sectionNum = sectionNum;
/*   9 */     this.instructor = instructor;
/*  10 */     this.location = location;
/*  11 */     this.courseNum = coursenum;
/*  12 */     this.status = status;
/*     */   }
/*     */ 
/*     */   public Map<String, Range> getDayToTime()
/*     */   {
/*  18 */     return this.dayToTime;
/*     */   }
/*     */   public void setDayToTime(Map<String, Range> dayToTime) {
/*  21 */     this.dayToTime = dayToTime;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  28 */     return this.type;
/*     */   }
/*     */   public void setType(String type) {
/*  31 */     this.type = type;
/*     */   }
/*     */   public String getCredits() {
/*  34 */     return this.credits;
/*     */   }
/*     */   public void setCredits(String credits) {
/*  37 */     this.credits = credits;
/*     */   }
/*     */   public String getOpen() {
/*  40 */     return this.open;
/*     */   }
/*     */   public void setOpen(String open) {
/*  43 */     this.open = open;
/*     */   }
/*     */   public String getSchedule() {
/*  46 */     return this.schedule;
/*     */   }
/*     */   public void setSchedule(String schedule) {
/*  49 */     this.schedule = schedule;
/*     */   }
/*     */   public Map<String, String[]> getDaysToMeetingTimeAndLocations() {
/*  52 */     return this.daysToMeetingTimeAndLocations;
/*     */   }
/*     */ 
/*     */   public void setDaysToMeetingTimeAndLocations(HashMap<String, String[]> daysToMeetingTimeAndLocations) {
/*  56 */     this.daysToMeetingTimeAndLocations = daysToMeetingTimeAndLocations;
/*     */   }
/*     */   public String getSectionNum() {
/*  59 */     return this.sectionNum;
/*     */   }
/*     */   public void setSectionNum(String sectionNum) {
/*  62 */     this.sectionNum = sectionNum;
/*     */   }
/*     */   public String getInstructor() {
/*  65 */     return this.instructor;
/*     */   }
/*     */   public void setInstructor(String instructor) {
/*  68 */     this.instructor = instructor;
/*     */   }
/*     */   public String getLocation() {
/*  71 */     return this.location;
/*     */   }
/*     */   public void setLocation(String location) {
/*  74 */     this.location = location;
/*     */   }
/*     */   public void setCourseNum(String coursenum) {
/*  77 */     this.courseNum = coursenum;
/*     */   }
/*     */   public String getCourseNum() {
/*  80 */     return this.courseNum;
/*     */   }
/*     */   public void setStatus(String status) {
/*  83 */     this.status = status;
/*     */   }
/*     */   public String getStatus() {
/*  86 */     return this.status;
/*     */   }
/*     */   public void setCourseName(String courseName) {
/*  89 */     this.courseMnemNum = courseName;
/*     */   }
/*     */   public String getCourseName() {
/*  92 */     return this.courseMnemNum;
/*     */   }
/*     */   public boolean equals(Object o) {
/*  95 */     if ((o instanceof Section)) {
/*  96 */       Section sec = (Section)o;
/*     */ 
/*  98 */       return sec.getCourseNum().equals(this.courseNum);
/*     */     }
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 104 */     String ret = "";
/* 105 */     ret = ret + this.courseMnemNum + " " + this.sectionNum + getDayToTime();
/* 106 */     return ret;
/*     */   }
/*     */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     models.Section
 * JD-Core Version:    0.6.2
 */