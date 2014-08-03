/*     */ package gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EventListener;
/*     */ import java.util.List;
/*     */ import java.util.Observable;
/*     */ import org.eclipse.swt.events.PaintEvent;
/*     */ import org.eclipse.swt.events.PaintListener;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.events.SelectionListener;
/*     */ import org.eclipse.swt.graphics.Color;
/*     */ import org.eclipse.swt.graphics.GC;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.eclipse.swt.layout.FillLayout;
/*     */ import org.eclipse.swt.layout.FormAttachment;
/*     */ import org.eclipse.swt.layout.FormData;
/*     */ import org.eclipse.swt.layout.FormLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ 
/*     */ public class ScheduleOutputScreen extends Composite
/*     */ {
/*     */   public Shell shell;
/*     */   private Text row8;
/*     */   private Text row9;
/*     */   private Text row10;
/*     */   private Text row11;
/*     */   private Text row12;
/*     */   private Text row13;
/*     */   private Text row14;
/*     */   private Text row15;
/*     */   private Text row16;
/*     */   private Text row17;
/*     */   private Text row18;
/*     */   private Text timeCol;
/*     */   private Text monCol;
/*     */   private Text tuesCol;
/*     */   private Text wedCol;
/*     */   private Text thursCol;
/*     */   private Text friCol;
/*     */   private Label instrLabel;
/*     */   private Text schedIndex;
/*     */   private Button nextButton;
/*     */   private Button previousButton;
/*     */   private int currentSched;
/*     */   private int numScheds;
/*     */   private static ScheduleOutputScreen outputScreen;
/*     */   public NextButtonObservable nextEvent;
/*     */   public PreviousButtonObservable previousEvent;
/*     */   public final List<EventListener> eventListeners;
/*  68 */   public static final List<Text> createdCourses = new ArrayList();
/*     */ 
/*     */   private ScheduleOutputScreen(Shell parent, int style) {
/*  71 */     super(parent, style);
/*     */ 
/*  73 */     this.shell = parent;
/*  74 */     this.nextEvent = new NextButtonObservable();
/*  75 */     this.previousEvent = new PreviousButtonObservable();
/*  76 */     this.eventListeners = new ArrayList();
/*     */   }
/*     */ 
/*     */   public static ScheduleOutputScreen getInstance()
/*     */   {
/*  82 */     if (outputScreen == null) {
/*  83 */       outputScreen = new ScheduleOutputScreen(new Shell(
/*  84 */         Display.getCurrent()), 0);
/*     */     }
/*  86 */     return outputScreen;
/*     */   }
/*     */ 
/*     */   public void showGUI()
/*     */   {
/*  96 */     Display display = this.shell.getDisplay();
/*  97 */     Point size = getSize();
/*  98 */     this.shell.setLayout(new FillLayout());
/*  99 */     this.shell.layout();
/* 100 */     if ((size.x == 0) && (size.y == 0)) {
/* 101 */       pack();
/* 102 */       this.shell.pack();
/*     */     } else {
/* 104 */       Rectangle shellBounds = this.shell.computeTrim(0, 0, size.x, size.y);
/* 105 */       this.shell.setSize(shellBounds.width, shellBounds.height);
/* 106 */       this.shell.setLocation(600, 100);
/*     */     }
/* 108 */     this.shell.open();
/* 109 */     while (!this.shell.isDisposed())
/* 110 */       if (!display.readAndDispatch())
/* 111 */         display.sleep();
/*     */   }
/*     */ 
/*     */   public List<Text> drawCourse(String courseInfo, List<Double> courseStarts, List<Double> courseEnds, List<String> courseDays)
/*     */   {
/* 122 */     for (int i = 0; i < courseStarts.size(); i++) {
/* 123 */       double topStart = 0.0D;
/* 124 */       double courseEndAdjusted = ((Double)courseEnds.get(i)).doubleValue();
/* 125 */       int extraMins = 0;
/* 126 */       int courseDurationPixels = 0;
/* 127 */       int leftStart = 0;
/*     */ 
/* 131 */       if (courseEndAdjusted % 100.0D == 0.0D)
/*     */       {
/* 133 */         extraMins = 0;
/* 134 */         courseEndAdjusted -= 40 + extraMins;
/*     */       }
/* 136 */       else if (courseEndAdjusted % 100.0D == 15.0D)
/*     */       {
/* 138 */         extraMins = 15;
/* 139 */         courseEndAdjusted -= 40 + extraMins;
/*     */       }
/* 141 */       else if (courseEndAdjusted % 100.0D == 30.0D)
/*     */       {
/* 143 */         extraMins = 30;
/* 144 */         courseEndAdjusted -= 40 + extraMins;
/*     */       }
/* 146 */       else if (courseEndAdjusted % 100.0D == 45.0D)
/*     */       {
/* 148 */         extraMins = 45;
/* 149 */         courseEndAdjusted -= 40 + extraMins;
/*     */       }
/*     */ 
/* 152 */       double courseRangeAdjusted = courseEndAdjusted - ((Double)courseStarts.get(i)).doubleValue();
/* 153 */       int hoursInMins = (int)(courseRangeAdjusted / 100.0D) * 60;
/* 154 */       int mins = (int)(courseRangeAdjusted % 100.0D);
/* 155 */       courseDurationPixels = hoursInMins + mins + extraMins;
/*     */ 
/* 158 */       if (((Double)courseStarts.get(i)).doubleValue() % 100.0D == 0.0D)
/*     */       {
/* 160 */         double diff = (((Double)courseStarts.get(i)).doubleValue() - 800.0D) / 100.0D;
/* 161 */         topStart = 57.0D + 60.0D * diff;
/*     */       }
/*     */       else {
/* 164 */         double diff2 = (((Double)courseStarts.get(i)).doubleValue() - 830.0D) / 100.0D;
/* 165 */         topStart = 87.0D + 60.0D * diff2;
/*     */       }
/*     */ 
/* 169 */       if (((String)courseDays.get(i)).equals("Mo"))
/* 170 */         leftStart = 90;
/* 171 */       else if (((String)courseDays.get(i)).equals("Tu"))
/* 172 */         leftStart = 200;
/* 173 */       else if (((String)courseDays.get(i)).equals("We"))
/* 174 */         leftStart = 310;
/* 175 */       else if (((String)courseDays.get(i)).equals("Th"))
/* 176 */         leftStart = 420;
/* 177 */       else if (((String)courseDays.get(i)).equals("Fr")) {
/* 178 */         leftStart = 530;
/*     */       }
/*     */ 
/* 181 */       Text newClass = new Text(this, 16777290);
/*     */ 
/* 183 */       newClass.setText(courseInfo);
/* 184 */       FormData newClassLData = new FormData();
/* 185 */       newClassLData.left = new FormAttachment(0, 1000, leftStart);
/* 186 */       newClassLData.top = new FormAttachment(0, 1000, (int)topStart);
/* 187 */       newClassLData.width = 100;
/* 188 */       newClassLData.height = courseDurationPixels;
/* 189 */       newClass.setLayoutData(newClassLData);
/* 190 */       newClass.setBackground(new Color(Display.getDefault(), 170, 255, 
/* 191 */         170));
/* 192 */       createdCourses.add(newClass);
/*     */     }
/* 194 */     layout(true);
/* 195 */     layout(true, true);
/*     */ 
/* 197 */     return createdCourses;
/*     */   }
/*     */ 
/*     */   public void setSchedIndex(int currentIndex, int numSchedules)
/*     */   {
/* 202 */     this.currentSched = (currentIndex + 1);
/* 203 */     this.numScheds = (numSchedules + 1);
/*     */ 
/* 205 */     this.schedIndex = new Text(this, 16777226);
/*     */ 
/* 207 */     FormData schedIndexLData = new FormData();
/* 208 */     schedIndexLData.left = new FormAttachment(0, 1000, 300);
/* 209 */     schedIndexLData.top = new FormAttachment(0, 1000, 741);
/* 210 */     schedIndexLData.width = 40;
/* 211 */     schedIndexLData.height = 20;
/* 212 */     this.schedIndex.setLayoutData(schedIndexLData);
/* 213 */     this.schedIndex.setBackground(getDisplay().getSystemColor(
/* 214 */       1));
/* 215 */     this.schedIndex.setText(this.currentSched + " / " + this.numScheds);
/*     */ 
/* 218 */     this.schedIndex.update();
/* 219 */     initGUI();
/*     */   }
/*     */ 
/*     */   public void refresh() {
/* 223 */     for (Text t : createdCourses) {
/* 224 */       t.dispose();
/*     */     }
/* 226 */     this.schedIndex.dispose();
/*     */   }
/*     */ 
/*     */   private void initGUI() {
/*     */     try {
/* 231 */       FormLayout thisLayout = new FormLayout();
/* 232 */       setLayout(thisLayout);
/* 233 */       setSize(653, 810);
/* 234 */       getShell().setText("Generated Schedules");
/* 235 */       drawSchedule();
/*     */ 
/* 238 */       addPaintListener(new PaintListener() {
/*     */         public void paintControl(PaintEvent e) {
/* 240 */           e.gc.setLineWidth(2);
/*     */ 
/* 243 */           e.gc.setForeground(new Color(Display.getDefault(), 240, 255, 255));
/*     */ 
/* 246 */           for (int y = 55; y <= 657; y += 60) {
/* 247 */             e.gc.drawLine(32, y, 635, y);
/*     */           }
/* 249 */           for (int x = 88; x <= 530; x += 110)
/* 250 */             e.gc.drawLine(x, 31, x, 717);
/*     */         }
/*     */       });
/* 254 */       layout();
/*     */     } catch (Exception e) {
/* 256 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void drawSchedule()
/*     */   {
/* 262 */     this.instrLabel = new Label(this, 0);
/* 263 */     FormData instrLabelLData = new FormData();
/* 264 */     instrLabelLData.left = new FormAttachment(0, 1000, 32);
/* 265 */     instrLabelLData.top = new FormAttachment(0, 1000, 776);
/* 266 */     instrLabelLData.width = 600;
/* 267 */     instrLabelLData.height = 15;
/* 268 */     this.instrLabel.setLayoutData(instrLabelLData);
/* 269 */     this.instrLabel.setText("For your convenience, the 5 digit class number is included below the course name to copy/paste into SIS to enroll.");
/*     */ 
/* 272 */     this.row8 = new Text(this, 74);
/* 273 */     FormData row8LData = new FormData();
/* 274 */     row8LData.left = new FormAttachment(0, 1000, 32);
/* 275 */     row8LData.top = new FormAttachment(0, 1000, 57);
/* 276 */     row8LData.width = 48;
/* 277 */     row8LData.height = 60;
/* 278 */     this.row8.setLayoutData(row8LData);
/* 279 */     this.row8.setText("8:00 am");
/* 280 */     this.row8.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 281 */       204));
/*     */ 
/* 284 */     this.row9 = new Text(this, 74);
/* 285 */     FormData row9LData = new FormData();
/* 286 */     row9LData.left = new FormAttachment(0, 1000, 32);
/* 287 */     row9LData.top = new FormAttachment(0, 1000, 117);
/* 288 */     row9LData.width = 48;
/* 289 */     row9LData.height = 60;
/* 290 */     this.row9.setLayoutData(row9LData);
/* 291 */     this.row9.setText("9:00 am");
/* 292 */     this.row9.setBackground(new Color(Display.getDefault(), 255, 255, 
/* 293 */       195));
/*     */ 
/* 297 */     this.row10 = new Text(this, 74);
/* 298 */     FormData row10LData = new FormData();
/* 299 */     row10LData.left = new FormAttachment(0, 1000, 32);
/* 300 */     row10LData.top = new FormAttachment(0, 1000, 177);
/* 301 */     row10LData.width = 48;
/* 302 */     row10LData.height = 60;
/* 303 */     this.row10.setLayoutData(row10LData);
/* 304 */     this.row10.setText("10:00 am");
/* 305 */     this.row10.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 306 */       204));
/*     */ 
/* 309 */     this.row11 = new Text(this, 74);
/* 310 */     FormData row11LData = new FormData();
/* 311 */     row11LData.left = new FormAttachment(0, 1000, 32);
/* 312 */     row11LData.top = new FormAttachment(0, 1000, 237);
/* 313 */     row11LData.width = 48;
/* 314 */     row11LData.height = 60;
/* 315 */     this.row11.setLayoutData(row11LData);
/* 316 */     this.row11.setText("11:00 am");
/* 317 */     this.row11.setBackground(new Color(Display.getDefault(), 255, 255, 
/* 318 */       195));
/*     */ 
/* 321 */     this.row12 = new Text(this, 74);
/* 322 */     FormData row12LData = new FormData();
/* 323 */     row12LData.left = new FormAttachment(0, 1000, 32);
/* 324 */     row12LData.top = new FormAttachment(0, 1000, 297);
/* 325 */     row12LData.width = 48;
/* 326 */     row12LData.height = 60;
/* 327 */     this.row12.setLayoutData(row12LData);
/* 328 */     this.row12.setText("12:00 pm");
/* 329 */     this.row12.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 330 */       204));
/*     */ 
/* 333 */     this.row13 = new Text(this, 74);
/* 334 */     FormData row13LData = new FormData();
/* 335 */     row13LData.left = new FormAttachment(0, 1000, 32);
/* 336 */     row13LData.top = new FormAttachment(0, 1000, 357);
/* 337 */     row13LData.width = 48;
/* 338 */     row13LData.height = 60;
/* 339 */     this.row13.setLayoutData(row13LData);
/* 340 */     this.row13.setText("1:00 pm");
/* 341 */     this.row13.setBackground(new Color(Display.getDefault(), 255, 255, 
/* 342 */       195));
/*     */ 
/* 345 */     this.row14 = new Text(this, 74);
/* 346 */     FormData row14LData = new FormData();
/* 347 */     row14LData.left = new FormAttachment(0, 1000, 32);
/* 348 */     row14LData.top = new FormAttachment(0, 1000, 417);
/* 349 */     row14LData.width = 48;
/* 350 */     row14LData.height = 60;
/* 351 */     this.row14.setLayoutData(row14LData);
/* 352 */     this.row14.setText("2:00 pm");
/* 353 */     this.row14.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 354 */       204));
/*     */ 
/* 357 */     this.row15 = new Text(this, 74);
/* 358 */     FormData row15LData = new FormData();
/* 359 */     row15LData.left = new FormAttachment(0, 1000, 32);
/* 360 */     row15LData.top = new FormAttachment(0, 1000, 477);
/* 361 */     row15LData.width = 48;
/* 362 */     row15LData.height = 60;
/* 363 */     this.row15.setLayoutData(row15LData);
/* 364 */     this.row15.setText("3:00 pm");
/* 365 */     this.row15.setBackground(new Color(Display.getDefault(), 255, 255, 
/* 366 */       195));
/*     */ 
/* 369 */     this.row16 = new Text(this, 74);
/* 370 */     FormData row16LData = new FormData();
/* 371 */     row16LData.left = new FormAttachment(0, 1000, 32);
/* 372 */     row16LData.top = new FormAttachment(0, 1000, 537);
/* 373 */     row16LData.width = 48;
/* 374 */     row16LData.height = 60;
/* 375 */     this.row16.setLayoutData(row16LData);
/* 376 */     this.row16.setText("4:00 pm");
/* 377 */     this.row16.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 378 */       204));
/*     */ 
/* 381 */     this.row17 = new Text(this, 74);
/* 382 */     FormData row17LData = new FormData();
/* 383 */     row17LData.left = new FormAttachment(0, 1000, 32);
/* 384 */     row17LData.top = new FormAttachment(0, 1000, 597);
/* 385 */     row17LData.width = 48;
/* 386 */     row17LData.height = 60;
/* 387 */     this.row17.setLayoutData(row17LData);
/* 388 */     this.row17.setText("5:00 pm");
/* 389 */     this.row17.setBackground(new Color(Display.getDefault(), 255, 255, 
/* 390 */       195));
/*     */ 
/* 393 */     this.row18 = new Text(this, 74);
/* 394 */     FormData row18LData = new FormData();
/* 395 */     row18LData.left = new FormAttachment(0, 1000, 32);
/* 396 */     row18LData.top = new FormAttachment(0, 1000, 657);
/* 397 */     row18LData.width = 48;
/* 398 */     row18LData.height = 60;
/* 399 */     this.row18.setLayoutData(row18LData);
/* 400 */     this.row18.setText("6:00 pm");
/* 401 */     this.row18.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 402 */       204));
/*     */ 
/* 405 */     this.timeCol = new Text(this, 16777290);
/*     */ 
/* 407 */     FormData timeColLData = new FormData();
/* 408 */     timeColLData.left = new FormAttachment(0, 1000, 32);
/* 409 */     timeColLData.top = new FormAttachment(0, 1000, 31);
/* 410 */     timeColLData.width = 48;
/* 411 */     timeColLData.height = 20;
/* 412 */     this.timeCol.setLayoutData(timeColLData);
/* 413 */     this.timeCol.setText("Time");
/* 414 */     this.timeCol.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 415 */       51));
/*     */ 
/* 418 */     this.monCol = new Text(this, 16777290);
/*     */ 
/* 420 */     this.monCol.setText("Monday");
/* 421 */     FormData monColLData = new FormData();
/* 422 */     monColLData.left = new FormAttachment(0, 1000, 90);
/* 423 */     monColLData.top = new FormAttachment(0, 1000, 31);
/* 424 */     monColLData.width = 100;
/* 425 */     monColLData.height = 20;
/* 426 */     this.monCol.setLayoutData(monColLData);
/* 427 */     this.monCol.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 428 */       51));
/*     */ 
/* 431 */     this.tuesCol = new Text(this, 16777290);
/*     */ 
/* 433 */     this.tuesCol.setText("Tuesday");
/* 434 */     FormData tuesColLData = new FormData();
/* 435 */     tuesColLData.left = new FormAttachment(0, 1000, 200);
/* 436 */     tuesColLData.top = new FormAttachment(0, 1000, 31);
/* 437 */     tuesColLData.width = 100;
/* 438 */     tuesColLData.height = 20;
/* 439 */     this.tuesCol.setLayoutData(tuesColLData);
/* 440 */     this.tuesCol.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 441 */       51));
/*     */ 
/* 444 */     this.wedCol = new Text(this, 16777290);
/*     */ 
/* 446 */     this.wedCol.setText("Wednesday");
/* 447 */     FormData wedColLData = new FormData();
/* 448 */     wedColLData.left = new FormAttachment(0, 1000, 310);
/* 449 */     wedColLData.top = new FormAttachment(0, 1000, 31);
/* 450 */     wedColLData.width = 100;
/* 451 */     wedColLData.height = 20;
/* 452 */     this.wedCol.setLayoutData(wedColLData);
/* 453 */     this.wedCol.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 454 */       51));
/*     */ 
/* 457 */     this.thursCol = new Text(this, 16777290);
/*     */ 
/* 459 */     this.thursCol.setText("Thursday");
/* 460 */     FormData thursColLData = new FormData();
/* 461 */     thursColLData.left = new FormAttachment(0, 1000, 420);
/* 462 */     thursColLData.top = new FormAttachment(0, 1000, 31);
/* 463 */     thursColLData.width = 100;
/* 464 */     thursColLData.height = 20;
/* 465 */     this.thursCol.setLayoutData(thursColLData);
/* 466 */     this.thursCol.setBackground(new Color(Display.getDefault(), 255, 
/* 467 */       204, 51));
/*     */ 
/* 470 */     this.friCol = new Text(this, 16777290);
/*     */ 
/* 472 */     this.friCol.setText("Friday");
/* 473 */     FormData friColLData = new FormData();
/* 474 */     friColLData.left = new FormAttachment(0, 1000, 530);
/* 475 */     friColLData.top = new FormAttachment(0, 1000, 31);
/* 476 */     friColLData.width = 100;
/* 477 */     friColLData.height = 20;
/* 478 */     this.friCol.setLayoutData(friColLData);
/* 479 */     this.friCol.setBackground(new Color(Display.getDefault(), 255, 204, 
/* 480 */       51));
/*     */ 
/* 483 */     this.previousButton = new Button(this, 16777224);
/* 484 */     FormData previousButtonLData = new FormData();
/* 485 */     previousButtonLData.left = new FormAttachment(0, 1000, 170);
/* 486 */     previousButtonLData.top = new FormAttachment(0, 1000, 736);
/* 487 */     previousButtonLData.width = 70;
/* 488 */     previousButtonLData.height = 30;
/* 489 */     this.previousButton.setLayoutData(previousButtonLData);
/* 490 */     this.previousButton.setText("Previous");
/* 491 */     this.previousButton.addSelectionListener(new SelectionListener()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent arg0)
/*     */       {
/* 496 */         if (ScheduleOutputScreen.this.currentSched != 1)
/*     */         {
/* 498 */           ScheduleOutputScreen.this.refresh();
/*     */         }
/* 500 */         ScheduleOutputScreen.this.previousEvent.announce();
/*     */       }
/*     */ 
/*     */       public void widgetDefaultSelected(SelectionEvent arg0)
/*     */       {
/*     */       }
/*     */     });
/* 512 */     this.nextButton = new Button(this, 16777224);
/* 513 */     FormData nextButtonLData = new FormData();
/* 514 */     nextButtonLData.left = new FormAttachment(0, 1000, 400);
/* 515 */     nextButtonLData.top = new FormAttachment(0, 1000, 736);
/* 516 */     nextButtonLData.width = 70;
/* 517 */     nextButtonLData.height = 30;
/* 518 */     this.nextButton.setLayoutData(nextButtonLData);
/* 519 */     this.nextButton.setText("Next");
/* 520 */     this.nextButton.addSelectionListener(new SelectionListener()
/*     */     {
/*     */       public void widgetSelected(SelectionEvent arg0)
/*     */       {
/* 525 */         if (ScheduleOutputScreen.this.currentSched < ScheduleOutputScreen.this.numScheds)
/*     */         {
/* 527 */           ScheduleOutputScreen.this.refresh();
/*     */         }
/* 529 */         ScheduleOutputScreen.this.nextEvent.announce();
/*     */       }
/*     */ 
/*     */       public void widgetDefaultSelected(SelectionEvent arg0)
/*     */       {
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void setNull()
/*     */   {
/* 603 */     outputScreen = null;
/* 604 */     this.shell.dispose();
/*     */   }
/*     */ 
/*     */   public class NextButtonObservable extends Observable
/*     */   {
/*     */     public NextButtonObservable()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void announce()
/*     */     {
/* 548 */       synchronized (this)
/*     */       {
/* 550 */         for (int i = 0; i < ScheduleOutputScreen.this.eventListeners.size(); i++)
/* 551 */           if ((ScheduleOutputScreen.this.eventListeners.get(i) instanceof ScheduleOutputScreen.NextButtonObserver)) {
/* 552 */             ScheduleOutputScreen.NextButtonObserver obs = 
/* 553 */               (ScheduleOutputScreen.NextButtonObserver)ScheduleOutputScreen.this.eventListeners
/* 553 */               .get(i);
/* 554 */             obs.update();
/*     */           }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void addObserver(EventListener observer)
/*     */     {
/* 566 */       ScheduleOutputScreen.this.eventListeners.add(observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface NextButtonObserver extends EventListener {
/*     */     public abstract void update();
/*     */   }
/*     */ 
/*     */   public class PreviousButtonObservable extends Observable {
/*     */     public PreviousButtonObservable() {
/*     */     }
/*     */ 
/* 578 */     public void announce() { synchronized (this)
/*     */       {
/* 580 */         for (int i = 0; i < ScheduleOutputScreen.this.eventListeners.size(); i++)
/* 581 */           if ((ScheduleOutputScreen.this.eventListeners.get(i) instanceof ScheduleOutputScreen.PreviousButtonObserver)) {
/* 582 */             ScheduleOutputScreen.PreviousButtonObserver obs = 
/* 583 */               (ScheduleOutputScreen.PreviousButtonObserver)ScheduleOutputScreen.this.eventListeners
/* 583 */               .get(i);
/* 584 */             obs.update();
/*     */           }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void addObserver(EventListener observer)
/*     */     {
/* 596 */       ScheduleOutputScreen.this.eventListeners.add(observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface PreviousButtonObserver extends EventListener
/*     */   {
/*     */     public abstract void update();
/*     */   }
/*     */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     gui.ScheduleOutputScreen
 * JD-Core Version:    0.6.2
 */