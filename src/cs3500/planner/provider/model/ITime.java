package cs3500.planner.provider.model;

public interface ITime {
  /**
   * gets the hours of the time (00-23).
   * @return time as an int
   */
  int getHours();


  /**
   * gets the minutes of the time
   * @return int (00-59)
   */
  int getMinutes();

  /**
   * gets the day of the time, as a day.
   * @return day of the time as a Day enum
   */
  Day getDay();

  /**
   * constructs a Time Object from a 4 char long String and a Day.
   * These were taken from the XML and fed into this method by the XML reader.
   * @param str the time as a string
   * @param day the day
   * @return Time
   */
  static ITime buildTime(String str, Day day) {
    String subhr = str.substring(0, 2);
    String submin = str.substring(2, 4);
    int hrs = Integer.parseInt(subhr);
    int mins = Integer.parseInt(submin);
//    return new ITime() {
//      @Override
//      public int getHours() {
//        return hrs;
//      }
//
//      @Override
//      public int getMinutes() {
//        return mins;
//      }
//
//      @Override
//      public Day getDay() {
//        return day;
//      }
//    };
    return new Time(hrs, mins, day);
  }
}
