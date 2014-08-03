/*     */ package gui;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.EventListener;
/*     */ import java.util.List;
/*     */ import java.util.Observable;
/*     */ import org.eclipse.swt.custom.CTabFolder;
/*     */ import org.eclipse.swt.custom.CTabItem;
/*     */ import org.eclipse.swt.events.SelectionEvent;
/*     */ import org.eclipse.swt.events.SelectionListener;
/*     */ import org.eclipse.swt.graphics.Color;
/*     */ import org.eclipse.swt.graphics.Point;
/*     */ import org.eclipse.swt.graphics.Rectangle;
/*     */ import org.eclipse.swt.layout.FillLayout;
/*     */ import org.eclipse.swt.layout.FormAttachment;
/*     */ import org.eclipse.swt.layout.FormData;
/*     */ import org.eclipse.swt.layout.FormLayout;
/*     */ import org.eclipse.swt.widgets.Button;
/*     */ import org.eclipse.swt.widgets.Combo;
/*     */ import org.eclipse.swt.widgets.Composite;
/*     */ import org.eclipse.swt.widgets.Control;
/*     */ import org.eclipse.swt.widgets.Display;
/*     */ import org.eclipse.swt.widgets.Event;
/*     */ import org.eclipse.swt.widgets.Label;
/*     */ import org.eclipse.swt.widgets.Listener;
/*     */ import org.eclipse.swt.widgets.Shell;
/*     */ import org.eclipse.swt.widgets.Text;
/*     */ 
/*     */ public class CoursesInputScreen extends Composite
/*     */ {
/*     */   public searchButtonObservable searchEvent;
/*     */   public final List<EventListener> eventListeners;
/*     */   private Shell shell;
/*     */   private Text errorMessage;
/*     */   private CTabFolder tabFolder;
/*     */   private Text courseMnemonic1;
/*     */   private Text courseMnemonic2;
/*     */   private Text courseNumber3;
/*     */   private Text courseNumber4;
/*     */   private Text courseMnemonic5;
/*     */   private Button searchButton;
/*     */   private Label course4Label;
/*     */   private Label course5Label;
/*     */   private Label course3Label;
/*     */   private Label course2Label;
/*     */   private Label course1Label;
/*     */   private Label instructionsLabel;
/*     */   private Label numberLabel;
/*     */   private Label mnemonicLabel;
/*     */   private Text courseNumber5;
/*     */   private Text courseMnemonic4;
/*     */   private Text courseMnemonic3;
/*     */   private Text courseNumber2;
/*     */   private Text courseNumber1;
/*     */   private Button check1;
/*     */   private Button check2;
/*     */   private Button check3;
/*     */   private Combo multiDays;
/*     */   private Text filter2Start;
/*     */   private Text filter3End;
/*     */ 
/*     */   public void showGUI()
/*     */   {
/*  42 */     Point size = getSize();
/*  43 */     this.shell.setLayout(new FillLayout());
/*  44 */     this.shell.layout();
/*  45 */     if ((size.x == 0) && (size.y == 0)) {
/*  46 */       pack();
/*  47 */       this.shell.pack();
/*     */     } else {
/*  49 */       Rectangle shellBounds = this.shell.computeTrim(0, 0, size.x, size.y);
/*  50 */       this.shell.setSize(shellBounds.width, shellBounds.height);
/*  51 */       this.shell.setLocation(80, 100);
/*     */     }
/*     */ 
/*  54 */     this.shell.open();
/*     */ 
/*  57 */     this.shell.addListener(21, new Listener()
/*     */     {
/*     */       public void handleEvent(Event event)
/*     */       {
/*  61 */         System.exit(0);
/*     */       }
/*     */     });
/*  65 */     while (!this.shell.isDisposed())
/*  66 */       if (!this.shell.getDisplay().readAndDispatch())
/*  67 */         this.shell.getDisplay().sleep();
/*     */   }
/*     */ 
/*     */   public CoursesInputScreen(CTabFolder parent, int style)
/*     */   {
/*  98 */     super(parent, style);
/*  99 */     this.tabFolder = parent;
/* 100 */     this.shell = this.tabFolder.getShell();
/* 101 */     initGUI();
/*     */ 
/* 103 */     this.searchEvent = new searchButtonObservable();
/* 104 */     this.eventListeners = new ArrayList();
/*     */   }
/*     */ 
/*     */   protected void checkSubclass()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void initGUI()
/*     */   {
/*     */     try
/*     */     {
/* 116 */       FormLayout thisLayout = new FormLayout();
/* 117 */       setLayout(thisLayout);
/* 118 */       setSize(495, 299);
/* 119 */       getShell().setText("Course Input");
/*     */ 
/* 122 */       FormData errorMessageLData = new FormData();
/* 123 */       errorMessageLData.left = new FormAttachment(0, 1000, 10);
/* 124 */       errorMessageLData.top = new FormAttachment(0, 1000, 168);
/* 125 */       errorMessageLData.width = 259;
/* 126 */       errorMessageLData.height = 109;
/* 127 */       this.errorMessage = new Text(this, 586);
/* 128 */       this.errorMessage.setLayoutData(errorMessageLData);
/* 129 */       this.errorMessage.setForeground(getDisplay().getSystemColor(3));
/* 130 */       this.errorMessage.setVisible(false);
/*     */ 
/* 133 */       this.course5Label = new Label(this, 0);
/* 134 */       FormData course5LabelLData = new FormData();
/* 135 */       course5LabelLData.left = new FormAttachment(0, 1000, 295);
/* 136 */       course5LabelLData.top = new FormAttachment(0, 1000, 211);
/* 137 */       course5LabelLData.width = 49;
/* 138 */       course5LabelLData.height = 15;
/* 139 */       this.course5Label.setLayoutData(course5LabelLData);
/* 140 */       this.course5Label.setText("Course 5:");
/*     */ 
/* 143 */       this.course4Label = new Label(this, 0);
/* 144 */       FormData course4LabelLData = new FormData();
/* 145 */       course4LabelLData.left = new FormAttachment(0, 1000, 295);
/* 146 */       course4LabelLData.top = new FormAttachment(0, 1000, 170);
/* 147 */       course4LabelLData.width = 49;
/* 148 */       course4LabelLData.height = 15;
/* 149 */       this.course4Label.setLayoutData(course4LabelLData);
/* 150 */       this.course4Label.setText("Course 4:");
/*     */ 
/* 153 */       this.course3Label = new Label(this, 0);
/* 154 */       FormData course3LabelLData = new FormData();
/* 155 */       course3LabelLData.left = new FormAttachment(0, 1000, 295);
/* 156 */       course3LabelLData.top = new FormAttachment(0, 1000, 129);
/* 157 */       course3LabelLData.width = 49;
/* 158 */       course3LabelLData.height = 15;
/* 159 */       this.course3Label.setLayoutData(course3LabelLData);
/* 160 */       this.course3Label.setText("Course 3:");
/*     */ 
/* 163 */       this.course2Label = new Label(this, 0);
/* 164 */       FormData course2LabelLData = new FormData();
/* 165 */       course2LabelLData.left = new FormAttachment(0, 1000, 295);
/* 166 */       course2LabelLData.top = new FormAttachment(0, 1000, 86);
/* 167 */       course2LabelLData.width = 49;
/* 168 */       course2LabelLData.height = 15;
/* 169 */       this.course2Label.setLayoutData(course2LabelLData);
/* 170 */       this.course2Label.setText("Course 2:");
/*     */ 
/* 173 */       this.course1Label = new Label(this, 0);
/* 174 */       FormData course1LabelLData = new FormData();
/* 175 */       course1LabelLData.left = new FormAttachment(0, 1000, 295);
/* 176 */       course1LabelLData.top = new FormAttachment(0, 1000, 47);
/* 177 */       course1LabelLData.width = 49;
/* 178 */       course1LabelLData.height = 15;
/* 179 */       this.course1Label.setLayoutData(course1LabelLData);
/* 180 */       this.course1Label.setText("Course 1:");
/*     */ 
/* 183 */       this.instructionsLabel = new Label(this, 64);
/* 184 */       FormData instructionsLabelLData = new FormData();
/* 185 */       instructionsLabelLData.left = new FormAttachment(0, 1000, 10);
/* 186 */       instructionsLabelLData.top = new FormAttachment(0, 1000, 21);
/* 187 */       instructionsLabelLData.width = 254;
/* 188 */       instructionsLabelLData.height = 135;
/* 189 */       this.instructionsLabel.setLayoutData(instructionsLabelLData);
/* 190 */       this.instructionsLabel
/* 191 */         .setText("Welcome to our automated Course Scheduler! Please enter up to 5 courses you would like to take and sit back and relax while we generate possible schedules for you.\n\nPlease enter the course mnemonic then the number, ex: cs 2150\n\nAuthors: Jeff Kusi, Patrick Fakhry, Isaac Bawuah");
/*     */ 
/* 196 */       this.numberLabel = new Label(this, 0);
/* 197 */       FormData numberLabelLData = new FormData();
/* 198 */       numberLabelLData.left = new FormAttachment(0, 1000, 415);
/* 199 */       numberLabelLData.top = new FormAttachment(0, 1000, 21);
/* 200 */       numberLabelLData.width = 48;
/* 201 */       numberLabelLData.height = 14;
/* 202 */       this.numberLabel.setLayoutData(numberLabelLData);
/* 203 */       this.numberLabel.setText("Number");
/*     */ 
/* 206 */       this.mnemonicLabel = new Label(this, 0);
/* 207 */       FormData mnemonicLabelLData = new FormData();
/* 208 */       mnemonicLabelLData.left = new FormAttachment(0, 1000, 345);
/* 209 */       mnemonicLabelLData.top = new FormAttachment(0, 1000, 21);
/* 210 */       mnemonicLabelLData.width = 58;
/* 211 */       mnemonicLabelLData.height = 15;
/* 212 */       this.mnemonicLabel.setLayoutData(mnemonicLabelLData);
/* 213 */       this.mnemonicLabel.setText("Mnemonic");
/*     */ 
/* 216 */       FormData courseMnemonic1LData = new FormData();
/* 217 */       courseMnemonic1LData.left = new FormAttachment(0, 1000, 350);
/* 218 */       courseMnemonic1LData.top = new FormAttachment(0, 1000, 47);
/* 219 */       courseMnemonic1LData.width = 44;
/* 220 */       courseMnemonic1LData.height = 20;
/* 221 */       this.courseMnemonic1 = new Text(this, 0);
/* 222 */       this.courseMnemonic1.setLayoutData(courseMnemonic1LData);
/* 223 */       this.courseMnemonic1.setTextLimit(4);
/*     */ 
/* 226 */       FormData courseNumber1LData = new FormData();
/* 227 */       courseNumber1LData.left = new FormAttachment(0, 1000, 412);
/* 228 */       courseNumber1LData.top = new FormAttachment(0, 1000, 47);
/* 229 */       courseNumber1LData.width = 50;
/* 230 */       courseNumber1LData.height = 20;
/* 231 */       this.courseNumber1 = new Text(this, 0);
/* 232 */       this.courseNumber1.setLayoutData(courseNumber1LData);
/* 233 */       this.courseNumber1.setTextLimit(4);
/*     */ 
/* 236 */       FormData courseMnemonic2LData = new FormData();
/* 237 */       courseMnemonic2LData.left = new FormAttachment(0, 1000, 350);
/* 238 */       courseMnemonic2LData.top = new FormAttachment(0, 1000, 86);
/* 239 */       courseMnemonic2LData.width = 44;
/* 240 */       courseMnemonic2LData.height = 20;
/* 241 */       this.courseMnemonic2 = new Text(this, 1);
/* 242 */       this.courseMnemonic2.setLayoutData(courseMnemonic2LData);
/* 243 */       this.courseMnemonic2.setTextLimit(4);
/*     */ 
/* 247 */       FormData courseNumber2LData = new FormData();
/* 248 */       courseNumber2LData.left = new FormAttachment(0, 1000, 412);
/* 249 */       courseNumber2LData.top = new FormAttachment(0, 1000, 86);
/* 250 */       courseNumber2LData.width = 50;
/* 251 */       courseNumber2LData.height = 20;
/* 252 */       this.courseNumber2 = new Text(this, 0);
/* 253 */       this.courseNumber2.setLayoutData(courseNumber2LData);
/* 254 */       this.courseNumber2.setTextLimit(4);
/*     */ 
/* 257 */       FormData courseMnemonic3LData = new FormData();
/* 258 */       courseMnemonic3LData.left = new FormAttachment(0, 1000, 350);
/* 259 */       courseMnemonic3LData.top = new FormAttachment(0, 1000, 129);
/* 260 */       courseMnemonic3LData.width = 44;
/* 261 */       courseMnemonic3LData.height = 20;
/* 262 */       this.courseMnemonic3 = new Text(this, 0);
/* 263 */       this.courseMnemonic3.setLayoutData(courseMnemonic3LData);
/* 264 */       this.courseMnemonic3.setTextLimit(4);
/*     */ 
/* 267 */       FormData courseNumber3LData = new FormData();
/* 268 */       courseNumber3LData.left = new FormAttachment(0, 1000, 412);
/* 269 */       courseNumber3LData.top = new FormAttachment(0, 1000, 129);
/* 270 */       courseNumber3LData.width = 50;
/* 271 */       courseNumber3LData.height = 20;
/* 272 */       this.courseNumber3 = new Text(this, 0);
/* 273 */       this.courseNumber3.setLayoutData(courseNumber3LData);
/* 274 */       this.courseNumber3.setTextLimit(4);
/*     */ 
/* 277 */       FormData courseMnemonic4LData = new FormData();
/* 278 */       courseMnemonic4LData.left = new FormAttachment(0, 1000, 350);
/* 279 */       courseMnemonic4LData.top = new FormAttachment(0, 1000, 170);
/* 280 */       courseMnemonic4LData.width = 44;
/* 281 */       courseMnemonic4LData.height = 20;
/* 282 */       this.courseMnemonic4 = new Text(this, 0);
/* 283 */       this.courseMnemonic4.setLayoutData(courseMnemonic4LData);
/* 284 */       this.courseMnemonic4.setTextLimit(4);
/*     */ 
/* 287 */       FormData courseNumber4LData = new FormData();
/* 288 */       courseNumber4LData.left = new FormAttachment(0, 1000, 412);
/* 289 */       courseNumber4LData.top = new FormAttachment(0, 1000, 170);
/* 290 */       courseNumber4LData.width = 50;
/* 291 */       courseNumber4LData.height = 20;
/* 292 */       this.courseNumber4 = new Text(this, 0);
/* 293 */       this.courseNumber4.setLayoutData(courseNumber4LData);
/* 294 */       this.courseNumber4.setTextLimit(4);
/*     */ 
/* 297 */       FormData courseMnemonic5LData = new FormData();
/* 298 */       courseMnemonic5LData.left = new FormAttachment(0, 1000, 350);
/* 299 */       courseMnemonic5LData.top = new FormAttachment(0, 1000, 211);
/* 300 */       courseMnemonic5LData.width = 44;
/* 301 */       courseMnemonic5LData.height = 20;
/* 302 */       this.courseMnemonic5 = new Text(this, 0);
/* 303 */       this.courseMnemonic5.setLayoutData(courseMnemonic5LData);
/* 304 */       this.courseMnemonic5.setTextLimit(4);
/*     */ 
/* 307 */       FormData courseNumber5LData = new FormData();
/* 308 */       courseNumber5LData.left = new FormAttachment(0, 1000, 412);
/* 309 */       courseNumber5LData.top = new FormAttachment(0, 1000, 211);
/* 310 */       courseNumber5LData.width = 50;
/* 311 */       courseNumber5LData.height = 20;
/* 312 */       this.courseNumber5 = new Text(this, 0);
/* 313 */       this.courseNumber5.setLayoutData(courseNumber5LData);
/* 314 */       this.courseNumber5.setTextLimit(4);
/*     */ 
/* 333 */       this.searchButton = new Button(this, 16777224);
/* 334 */       FormData searchButtonLData = new FormData();
/* 335 */       searchButtonLData.left = new FormAttachment(0, 1000, 379);
/* 336 */       searchButtonLData.top = new FormAttachment(0, 1000, 247);
/* 337 */       searchButtonLData.width = 68;
/* 338 */       searchButtonLData.height = 24;
/* 339 */       this.searchButton.setLayoutData(searchButtonLData);
/* 340 */       this.searchButton.setText("Search");
/*     */ 
/* 342 */       this.searchButton.addSelectionListener(new SelectionListener()
/*     */       {
/*     */         public void widgetSelected(SelectionEvent arg0)
/*     */         {
/* 347 */           ScheduleOutputScreen.getInstance().setNull();
/* 348 */           CoursesInputScreen.this.searchEvent.announce();
/*     */         }
/*     */ 
/*     */         public void widgetDefaultSelected(SelectionEvent arg0)
/*     */         {
/*     */         }
/*     */       });
/* 358 */       layout();
/*     */ 
/* 360 */       this.tabFolder.setSelectionBackground(new Color(Display.getDefault(), 240, 160, 160));
/*     */ 
/* 363 */       CTabItem tab1 = new CTabItem(this.tabFolder, 0);
/* 364 */       tab1.setText("Course Input");
/* 365 */       tab1.setControl(this);
/*     */ 
/* 367 */       CTabItem tab2 = new CTabItem(this.tabFolder, 0);
/* 368 */       tab2.setText("Schedule Preferences");
/* 369 */       tab2.setControl(getTab2Control());
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 373 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<String[]> getCourseList() {
/* 378 */     List courseList = new ArrayList();
/*     */ 
/* 380 */     courseList.add(new String[] { this.courseMnemonic1.getText(), 
/* 381 */       this.courseNumber1.getText() });
/* 382 */     courseList.add(new String[] { this.courseMnemonic2.getText(), 
/* 383 */       this.courseNumber2.getText() });
/* 384 */     courseList.add(new String[] { this.courseMnemonic3.getText(), 
/* 385 */       this.courseNumber3.getText() });
/* 386 */     courseList.add(new String[] { this.courseMnemonic4.getText(), 
/* 387 */       this.courseNumber4.getText() });
/* 388 */     courseList.add(new String[] { this.courseMnemonic5.getText(), 
/* 389 */       this.courseNumber5.getText() });
/* 390 */     clearErrors();
/*     */ 
/* 392 */     return courseList;
/*     */   }
/*     */ 
/*     */   public void clearErrors() {
/* 396 */     this.errorMessage.setText("Errors(s): \n");
/* 397 */     this.errorMessage.update();
/* 398 */     this.errorMessage.setVisible(false);
/*     */   }
/*     */ 
/*     */   public void displayError(String s)
/*     */   {
/* 403 */     this.errorMessage.setText("Error(s): \n" + s);
/* 404 */     this.errorMessage.setVisible(true);
/*     */   }
/*     */ 
/*     */   private Control getTab2Control()
/*     */   {
/* 440 */     Composite comp = new Composite(this.tabFolder, 0);
/* 441 */     comp.setLayout(new FormLayout());
/*     */ 
/* 443 */     Label filterInstrLabel = new Label(comp, 64);
/* 444 */     FormData filterInstrLabelLData = new FormData();
/* 445 */     filterInstrLabelLData.left = new FormAttachment(0, 10);
/* 446 */     filterInstrLabelLData.top = new FormAttachment(0, 21);
/* 447 */     filterInstrLabelLData.width = 370;
/* 448 */     filterInstrLabelLData.height = 50;
/* 449 */     filterInstrLabel.setLayoutData(filterInstrLabelLData);
/* 450 */     filterInstrLabel.setText("Please check the boxes below and enter the corresponding text if you would like to filter the results based on your scheduling preferences.\nThen select the Course Input tab and click Search!");
/*     */ 
/* 455 */     FormData check1Data = new FormData();
/* 456 */     check1Data.left = new FormAttachment(0, 10);
/* 457 */     check1Data.top = new FormAttachment(0, 81);
/* 458 */     this.check1 = new Button(comp, 32);
/* 459 */     this.check1.setLayoutData(check1Data);
/* 460 */     this.check1.setText("No classes on: ");
/*     */ 
/* 462 */     this.multiDays = new Combo(comp, 2056);
/* 463 */     FormData multiDaysData = new FormData();
/* 464 */     multiDaysData.left = new FormAttachment(28, 10);
/* 465 */     multiDaysData.top = new FormAttachment(0, 81);
/* 466 */     multiDaysData.width = 18;
/* 467 */     multiDaysData.height = 20;
/* 468 */     this.multiDays.setLayoutData(multiDaysData);
/* 469 */     this.multiDays.setItems(new String[] { "Mo", "Tu", "We", "Th", "Fr" });
/* 470 */     this.multiDays.select(0);
/*     */ 
/* 472 */     Label filter1Label = new Label(comp, 0);
/* 473 */     FormData filter1LabelLData = new FormData();
/* 474 */     filter1LabelLData.left = new FormAttachment(38, 10);
/* 475 */     filter1LabelLData.top = new FormAttachment(0, 81);
/* 476 */     filter1LabelLData.width = 250;
/* 477 */     filter1LabelLData.height = 20;
/* 478 */     filter1Label.setLayoutData(filter1LabelLData);
/* 479 */     filter1Label.setText("Select the day you don't want classes");
/*     */ 
/* 482 */     FormData check2Data = new FormData();
/* 483 */     check2Data.left = new FormAttachment(0, 10);
/* 484 */     check2Data.top = new FormAttachment(0, 121);
/* 485 */     this.check2 = new Button(comp, 32);
/* 486 */     this.check2.setLayoutData(check2Data);
/* 487 */     this.check2.setText("Start Schedule After: ");
/*     */ 
/* 489 */     FormData filter2Data = new FormData();
/* 490 */     filter2Data.left = new FormAttachment(28, 10);
/* 491 */     filter2Data.top = new FormAttachment(0, 121);
/* 492 */     filter2Data.width = 35;
/* 493 */     filter2Data.height = 20;
/* 494 */     this.filter2Start = new Text(comp, 0);
/* 495 */     this.filter2Start.setLayoutData(filter2Data);
/* 496 */     this.filter2Start.setTextLimit(4);
/*     */ 
/* 498 */     Label filter2Label = new Label(comp, 0);
/* 499 */     FormData filter2LabelLData = new FormData();
/* 500 */     filter2LabelLData.left = new FormAttachment(38, 10);
/* 501 */     filter2LabelLData.top = new FormAttachment(0, 121);
/* 502 */     filter2LabelLData.width = 250;
/* 503 */     filter2LabelLData.height = 20;
/* 504 */     filter2Label.setLayoutData(filter2LabelLData);
/* 505 */     filter2Label.setText("Enter military time, ex: 1100 or 1730");
/*     */ 
/* 508 */     FormData check3Data = new FormData();
/* 509 */     check3Data.left = new FormAttachment(0, 10);
/* 510 */     check3Data.top = new FormAttachment(5, 146);
/* 511 */     this.check3 = new Button(comp, 32);
/* 512 */     this.check3.setLayoutData(check3Data);
/* 513 */     this.check3.setText("End Schedule Before: ");
/*     */ 
/* 515 */     FormData filter3Data = new FormData();
/* 516 */     filter3Data.left = new FormAttachment(28, 10);
/* 517 */     filter3Data.top = new FormAttachment(0, 161);
/* 518 */     filter3Data.width = 35;
/* 519 */     filter3Data.height = 20;
/* 520 */     this.filter3End = new Text(comp, 0);
/* 521 */     this.filter3End.setLayoutData(filter3Data);
/* 522 */     this.filter3End.setTextLimit(4);
/*     */ 
/* 524 */     Label filter3Label = new Label(comp, 0);
/* 525 */     FormData filter3LabelLData = new FormData();
/* 526 */     filter3LabelLData.left = new FormAttachment(38, 10);
/* 527 */     filter3LabelLData.top = new FormAttachment(0, 161);
/* 528 */     filter3LabelLData.width = 250;
/* 529 */     filter3LabelLData.height = 20;
/* 530 */     filter3Label.setLayoutData(filter3LabelLData);
/* 531 */     filter3Label.setText("Enter military time, ex: 1100 or 1730");
/*     */ 
/* 533 */     return comp;
/*     */   }
/*     */ 
/*     */   public boolean getDayChecked()
/*     */   {
/* 538 */     return this.check1.getSelection();
/*     */   }
/*     */ 
/*     */   public boolean getStartChecked()
/*     */   {
/* 543 */     return this.check2.getSelection();
/*     */   }
/*     */ 
/*     */   public boolean getEndChecked()
/*     */   {
/* 548 */     return this.check3.getSelection();
/*     */   }
/*     */ 
/*     */   public String getDayChosen()
/*     */   {
/* 553 */     return this.multiDays.getItem(this.multiDays.getSelectionIndex());
/*     */   }
/*     */ 
/*     */   public String getStartChosen()
/*     */   {
/* 558 */     return this.filter2Start.getText();
/*     */   }
/*     */ 
/*     */   public String getEndChosen()
/*     */   {
/* 563 */     return this.filter3End.getText();
/*     */   }
/*     */ 
/*     */   public static abstract interface SearchButtonObserver extends EventListener
/*     */   {
/*     */     public abstract void update();
/*     */   }
/*     */ 
/*     */   public class searchButtonObservable extends Observable
/*     */   {
/*     */     public searchButtonObservable()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void announce()
/*     */     {
/* 418 */       synchronized (this)
/*     */       {
/* 420 */         for (int i = 0; i < CoursesInputScreen.this.eventListeners.size(); i++) {
/* 421 */           CoursesInputScreen.SearchButtonObserver obs = 
/* 422 */             (CoursesInputScreen.SearchButtonObserver)CoursesInputScreen.this.eventListeners
/* 422 */             .get(i);
/* 423 */           obs.update();
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void addObserver(CoursesInputScreen.SearchButtonObserver searchButtonObserver)
/*     */     {
/* 434 */       CoursesInputScreen.this.eventListeners.add(searchButtonObserver);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     gui.CoursesInputScreen
 * JD-Core Version:    0.6.2
 */