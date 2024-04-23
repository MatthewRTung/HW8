package cs3500.planner.provider.model;

/**
 * Interface for Time.
 */
public interface ITime {
  /**
   * gets the hours of the time (00-23).
   * @return time as an int
   */
  int getHours();


  /**
   * gets the minutes of the time.
   * @return int (00-59)
   */
  int getMinutes();

  /**
   * gets the day of the time, as a day.
   * @return day of the time as a Day enum
   */
  Day getDay();
}
