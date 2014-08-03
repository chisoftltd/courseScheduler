/*     */ package util;
/*     */ 
/*     */ import engine.Start;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import models.Course;
/*     */ import models.Range;
/*     */ import models.Section;
/*     */ import net.htmlparser.jericho.Element;
/*     */ import net.htmlparser.jericho.Segment;
/*     */ import net.htmlparser.jericho.Source;
/*     */ 
/*     */ public class PageParser
/*     */ {
/*     */   public List<Course> getCourses(List<String> coursesPages)
/*     */   {
/*  23 */     List courses = new ArrayList();
/*     */ 
/*  25 */     for (int i = 0; i < coursesPages.size(); i++) {
/*  26 */       String coursePage = (String)coursesPages.get(i);
/*     */       try
/*     */       {
/*  29 */         course = parse(coursePage);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */         Course course;
/*  32 */         int num = i + 1;
/*  33 */         Start.errors.add("Course " + num + " was not included because it is " + 
/*  34 */           "not offered or is invalid.");
/*  35 */         continue;
/*     */       }
/*     */       Course course;
/*  37 */       courses.add(course);
/*     */     }
/*  39 */     return courses;
/*     */   }
/*     */ 
/*     */   public Course parse(String coursePage) {
/*  43 */     Source source = new Source(coursePage);
/*  44 */     List courseNums = source.getAllElementsByClass("CourseNum");
/*  45 */     Course course = new Course();
/*     */     String rawCourseStr;
/*  46 */     for (Element courseNumTag : courseNums) {
/*  47 */       rawCourseStr = courseNumTag.getContent().toString();
/*  48 */       String courseNumber = rawCourseStr.substring(rawCourseStr
/*  49 */         .lastIndexOf(";") + 1);
/*     */ 
/*  51 */       course.setCourseNum(courseNumber);
/*     */     }
/*     */ 
/*  54 */     List courseNames = source.getAllElementsByClass("CourseName");
/*  55 */     for (Element courseNameTag : courseNames) {
/*  56 */       String courseName = courseNameTag.getContent().toString();
/*  57 */       course.setCourseName(courseName);
/*     */     }
/*     */ 
/*  61 */     String secTag = course.getCourseNum().replaceAll("\\s", "");
/*     */ 
/*  63 */     List sectionTags = source
/*  64 */       .getAllElementsByClass("SectionOdd S " + secTag);
/*  65 */     sectionTags.addAll(source.getAllElementsByClass("SectionEven S " + 
/*  66 */       secTag));
/*  67 */     String sectionRegex = "'right'>([\\d]{5}).+'>([\\d]{1,}).+strong>([\\w]+)<.+\\((\\d).+>.+>((Closed)|(Open)|(Waitlist\\s\\([\\d]+\\)))<.+>([\\d]+\\s/\\s[\\d]+).+strong>([\\w\\s]+)</strong></td><td>(.+)<.+td>([\\w\\s\\d]{1,}).+";
/*     */ 
/*  71 */     Pattern sectionPattern = Pattern.compile(sectionRegex);
/*  72 */     List lectures = new ArrayList();
/*  73 */     List labs = new ArrayList();
/*  74 */     for (Element element : sectionTags) {
/*  75 */       String sectTagStr = element.toString();
/*  76 */       Matcher findSection = sectionPattern.matcher(sectTagStr);
/*     */ 
/*  78 */       if (findSection.find())
/*     */       {
/*  80 */         String secMnemo = findSection.group(1);
/*  81 */         String secNum = findSection.group(2);
/*  82 */         String type = findSection.group(3);
/*  83 */         String credits = findSection.group(4);
/*     */ 
/*  85 */         String open = findSection.group(5);
/*  86 */         String status = findSection.group(9);
/*  87 */         String teacher = findSection.group(10);
/*  88 */         String schedule = findSection.group(11);
/*  89 */         String location = findSection.group(12);
/*  90 */         Section sec = new Section(secNum, teacher, location, secMnemo, 
/*  91 */           status);
/*     */ 
/*  93 */         sec.setType(type);
/*  94 */         sec.setCredits(credits);
/*  95 */         sec.setOpen(open);
/*  96 */         sec.setSchedule(schedule);
/*  97 */         sec.setCourseName(course.getCourseNum());
/*  98 */         Map meetSched = new HashMap();
/*  99 */         String[] days = { "Mo", "Tu", "We", "Th", "Fr" };
/*     */ 
/* 101 */         String[] parsedBySpace = schedule.split("\\s");
/* 102 */         for (int i = 0; i < days.length; i++) {
/* 103 */           if (schedule.contains(days[i])) {
/* 104 */             Range r = new Range(toMilitaryTime(parsedBySpace[1]), 
/* 105 */               toMilitaryTime(parsedBySpace[3]));
/* 106 */             meetSched.put(days[i], r);
/*     */           }
/*     */         }
/* 109 */         sec.setDayToTime(meetSched);
/* 110 */         if ((sec.getType().equals("Laboratory")) || (sec.getType().equals("Discussion")))
/*     */         {
/* 112 */           labs.add(sec);
/*     */         }
/* 114 */         if ((sec.getType().equals("Lecture")) || (sec.getType().equals("Seminar"))) {
/* 115 */           lectures.add(sec);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 121 */     course.setLectures(lectures);
/* 122 */     course.setLabs(labs);
/* 123 */     return course;
/*     */   }
/*     */ 
/*     */   public int toMilitaryTime(String s)
/*     */   {
/* 128 */     s = s.trim();
/* 129 */     String[] hourMin = s.split(":");
/* 130 */     String hour = hourMin[0];
/* 131 */     int time = 0;
/* 132 */     if (hourMin[1].charAt(hourMin[1].length() - 2) == 'P') {
/* 133 */       String min = hourMin[1].substring(0, hourMin[1].indexOf('P'));
/* 134 */       time = Integer.parseInt(hour + min);
/* 135 */       if (Integer.parseInt(hour) != 12)
/* 136 */         time += 1200;
/*     */     }
/* 138 */     if (hourMin[1].charAt(hourMin[1].length() - 2) == 'A') {
/* 139 */       String min = hourMin[1].substring(0, hourMin[1].indexOf('A'));
/* 140 */       time = Integer.parseInt(hour + min);
/*     */     }
/* 142 */     return time;
/*     */   }
/*     */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     util.PageParser
 * JD-Core Version:    0.6.2
 */