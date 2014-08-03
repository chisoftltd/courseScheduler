/*     */ package engine;
/*     */ 
/*     */ import gui.CoursesInputScreen;
/*     */ import gui.CoursesInputScreen.SearchButtonObserver;
/*     */ import gui.CoursesInputScreen.searchButtonObservable;
/*     */ import gui.ScheduleOutputScreen;
/*     */ import gui.ScheduleOutputScreen.NextButtonObservable;
/*     */ import gui.ScheduleOutputScreen.NextButtonObserver;
/*     */ import gui.ScheduleOutputScreen.PreviousButtonObservable;
/*     */ import gui.ScheduleOutputScreen.PreviousButtonObserver;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import models.Course;
/*     */ import models.Range;
/*     */ import models.Schedule;
/*     */ import models.Section;
/*     */ import org.eclipse.swt.custom.CTabFolder;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import scheduleRankers.EndBeforeRanker;
/*     */ import scheduleRankers.Filter;
/*     */ import scheduleRankers.NoDaysRanker;
/*     */ import scheduleRankers.StartAfterRanker;
/*     */ import util.PageParser;
/*     */ import util.PageRetriever;
/*     */ 
/*     */ public class Start
/*     */ {
/*  32 */   public static List<String> errors = new ArrayList();
/*     */   static List<Schedule> unfilteredSchedules;
/*     */   static List<Schedule> filteredSchedules;
/*  35 */   static int index = 0;
/*  36 */   static int max = 0;
/*  37 */   static boolean firstSearch = true;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  42 */     CoursesInputScreen inputScreen = new CoursesInputScreen(
/*  43 */       new CTabFolder(new Shell(Display.getDefault()), 0), 
/*  44 */       0);
/*     */ 
/*  46 */     inputScreen.searchEvent.addObserver(new CoursesInputScreen.SearchButtonObserver()
/*     */     {
/*     */       private List<Course> courses;
/*     */ 
/*     */       public void update()
/*     */       {
/*  53 */         Start.errors.clear();
/*  54 */         List htmlPages = getCoursePages(Start.this);
/*  55 */         this.courses = new PageParser().getCourses(htmlPages);
/*     */ 
/*  59 */         handleErrors(Start.this, this.courses);
/*     */ 
/*  61 */         System.out
/*  62 */           .println("---------------After Filtering-----------------");
/*     */ 
/*  65 */         Scheduler scheduler = Scheduler.getInstance();
/*  66 */         Start.unfilteredSchedules = scheduler.makeSchedule(this.courses);
/*  67 */         Start.filteredSchedules = applyFilters(Start.unfilteredSchedules);
/*  68 */         sendToConsole(Start.filteredSchedules);
/*     */ 
/*  72 */         if ((this.courses.isEmpty()) || (Start.filteredSchedules.isEmpty()))
/*     */         {
/*  74 */           Start.this
/*  75 */             .displayError("No possible schedules exist for the selected courses.");
/*  76 */           return;
/*     */         }
/*     */ 
/*  79 */         Schedule firstSched = (Schedule)Start.filteredSchedules.get(0);
/*     */ 
/*  81 */         ScheduleOutputScreen.getInstance().nextEvent
/*  82 */           .addObserver(new ScheduleOutputScreen.NextButtonObserver()
/*     */         {
/*     */           public void update()
/*     */           {
/*  86 */             if (Start.index < Start.max)
/*  87 */               Start.1.this.displaySchedule(
/*  88 */                 (Schedule)Start.filteredSchedules
/*  88 */                 .get(++Start.index));
/*     */           }
/*     */         });
/*  96 */         ScheduleOutputScreen.getInstance().previousEvent
/*  97 */           .addObserver(new ScheduleOutputScreen.PreviousButtonObserver()
/*     */         {
/*     */           public void update()
/*     */           {
/* 101 */             if (Start.index > 0)
/* 102 */               Start.1.this.displaySchedule(
/* 103 */                 (Schedule)Start.filteredSchedules
/* 103 */                 .get(--Start.index));
/*     */           }
/*     */         });
/* 111 */         Start.max = Start.filteredSchedules.size() - 1;
/* 112 */         Start.index = 0;
/*     */ 
/* 114 */         displaySchedule(firstSched);
/* 115 */         ScheduleOutputScreen.getInstance().showGUI();
/*     */       }
/*     */ 
/*     */       private List<Schedule> applyFilters(List<Schedule> unfilteredSchedules)
/*     */       {
/* 121 */         List rankings = new ArrayList();
/* 122 */         if ((Start.this.getDayChecked()) && (!Start.this.getDayChosen().isEmpty()))
/*     */         {
/* 124 */           List daysArr = new ArrayList();
/* 125 */           daysArr.add(Start.this.getDayChosen());
/* 126 */           rankings.add(new NoDaysRanker(daysArr));
/*     */         }
/* 128 */         if ((Start.this.getStartChecked()) && (!Start.this.getStartChosen().isEmpty()))
/*     */         {
/* 130 */           Double d = Double.valueOf(Start.this.getStartChosen());
/* 131 */           rankings.add(new StartAfterRanker(d.doubleValue()));
/*     */         }
/* 133 */         if ((Start.this.getEndChecked()) && (!Start.this.getEndChosen().isEmpty()))
/*     */         {
/* 135 */           Double d = Double.valueOf(Start.this.getEndChosen());
/* 136 */           rankings.add(new EndBeforeRanker(d.doubleValue()));
/*     */         }
/*     */ 
/* 140 */         Filter filter = new Filter(rankings);
/*     */ 
/* 142 */         List filteredSchedules = filter
/* 143 */           .filter(unfilteredSchedules);
/* 144 */         return filteredSchedules;
/*     */       }
/*     */ 
/*     */       private void sendToConsole(List<Schedule> filteredSchedules) {
/* 148 */         int howManyToShow = 5;
/* 149 */         for (int i = 0; i < filteredSchedules.size(); i++) {
/* 150 */           if (i == howManyToShow) {
/*     */             break;
/*     */           }
/* 153 */           Schedule sched = (Schedule)filteredSchedules.get(i);
/* 154 */           System.out.println(sched.toString());
/*     */         }
/*     */       }
/*     */ 
/*     */       private void displaySchedule(Schedule test)
/*     */       {
/* 178 */         for (Section sec : test.getSections()) {
/* 179 */           String represent = sec.getCourseName() + " - " + 
/* 180 */             sec.getSectionNum();
/* 181 */           represent = represent + "\n" + sec.getCourseNum() + "\n";
/* 182 */           List starts = new ArrayList();
/* 183 */           List ends = new ArrayList();
/* 184 */           List days = new ArrayList();
/* 185 */           String time = "";
/* 186 */           int i = 0;
/* 187 */           for (String day : sec.getDayToTime().keySet()) {
/* 188 */             days.add(day);
/*     */ 
/* 190 */             Range r = (Range)sec.getDayToTime().get(day);
/* 191 */             if (i++ == 0) {
/* 192 */               time = (int)r.getStart() + "-" + (int)r.getEnd();
/*     */             }
/* 194 */             starts.add(Double.valueOf(r.getStart()));
/* 195 */             ends.add(Double.valueOf(r.getEnd()));
/*     */           }
/* 197 */           represent = represent + time;
/*     */ 
/* 200 */           ScheduleOutputScreen.getInstance().drawCourse(represent, 
/* 201 */             starts, ends, days);
/*     */         }
/*     */ 
/* 205 */         ScheduleOutputScreen.getInstance().setSchedIndex(Start.index, Start.max);
/*     */       }
/*     */ 
/*     */       private void handleErrors(CoursesInputScreen inputScreen, List<Course> courses)
/*     */       {
/*     */         boolean open;
/* 211 */         for (Course c : courses)
/*     */         {
/* 214 */           open = false;
/* 215 */           for (Section section : c.getLectures()) {
/* 216 */             if (!section.getOpen().equals("Closed")) {
/* 217 */               open = true;
/* 218 */               break;
/*     */             }
/*     */           }
/* 221 */           if (!open)
/*     */           {
/* 223 */             Start.errors.add("Course " + c.getCourseNum() + 
/* 224 */               " is closed and was not included.");
/*     */           }
/*     */         }
/* 227 */         String errorMessages = "";
/* 228 */         for (String s : Start.errors) {
/* 229 */           errorMessages = errorMessages + s + "\n";
/*     */         }
/*     */ 
/* 232 */         if (!errorMessages.isEmpty())
/* 233 */           inputScreen.displayError(errorMessages);
/*     */       }
/*     */ 
/*     */       private List<String> getCoursePages(CoursesInputScreen inputScreen)
/*     */       {
/* 238 */         List coursesSearch = new ArrayList();
/* 239 */         PageRetriever retriever = new PageRetriever();
/*     */ 
/* 242 */         for (int i = 0; i < inputScreen.getCourseList().size(); i++) {
/* 243 */           if ((!((String[])inputScreen.getCourseList().get(i))[0].isEmpty()) && 
/* 244 */             (!((String[])inputScreen.getCourseList().get(i))[1].isEmpty())) {
/* 245 */             coursesSearch.add((String[])inputScreen.getCourseList().get(i));
/*     */           }
/*     */         }
/*     */ 
/* 249 */         List htmlPages = retriever
/* 250 */           .getCoursePages(coursesSearch);
/* 251 */         return htmlPages;
/*     */       }
/*     */     });
/* 255 */     inputScreen.showGUI();
/*     */   }
/*     */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     engine.Start
 * JD-Core Version:    0.6.2
 */