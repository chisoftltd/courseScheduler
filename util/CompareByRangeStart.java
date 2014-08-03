/*    */ package util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import models.ISpreadAble;
/*    */ import models.Range;
/*    */ 
/*    */ public class CompareByRangeStart
/*    */   implements Comparator<ISpreadAble>
/*    */ {
/*    */   public int compare(ISpreadAble s1, ISpreadAble s2)
/*    */   {
/* 13 */     int retval = 0;
/* 14 */     retval = s1.getRange().getStart() < s2.getRange().getStart() ? -1 : 1;
/* 15 */     if (s1.getRange().getStart() == s2.getRange().getStart())
/* 16 */       retval = 0;
/* 17 */     return retval;
/*    */   }
/*    */ }

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     util.CompareByRangeStart
 * JD-Core Version:    0.6.2
 */