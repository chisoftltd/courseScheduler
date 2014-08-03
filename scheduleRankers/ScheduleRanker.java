package scheduleRankers;

import models.Schedule;

public abstract interface ScheduleRanker
{
  public abstract int rank(Schedule paramSchedule);
}

/* Location:           /Users/jeff.kusi/Downloads/AutomatedScheduler - Fakhry & Kusi.jar
 * Qualified Name:     scheduleRankers.ScheduleRanker
 * JD-Core Version:    0.6.2
 */