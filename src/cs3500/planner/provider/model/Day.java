package cs3500.planner.provider.model;

/**
 * represents each day of the week for two weeks.
 * values determine order of days.
 */
public enum Day {
  SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3),
  THURSDAY(4), FRIDAY(5), SATURDAY(6),
  SUNDAY2(7), MONDAY2(8), TUESDAY2(9), WEDNESDAY2(10),
  THURSDAY2(11), FRIDAY2(12), SATURDAY2(13);

  private final int dayOfWeek;

  Day(int value) {
    this.dayOfWeek = value;
  }

  public int getDayOfWeek() {
    return this.dayOfWeek;
  }

  /**
   * turns a Day into a correlating String.
   * @return String value of Day
   */
  public String toString() {
    switch (this) {
      case SUNDAY:
        return "Sunday";
      case MONDAY:
        return "Monday";
      case TUESDAY:
        return "Tuesday";
      case WEDNESDAY:
        return "Wednesday";
      case THURSDAY:
        return "Thursday";
      case FRIDAY:
        return "Friday";
      case SATURDAY:
        return "Saturday";
      case SUNDAY2:
        return "Sunday";
      case MONDAY2:
        return "Monday";
      case TUESDAY2:
        return "Tuesday";
      case WEDNESDAY2:
        return "Wednesday";
      case THURSDAY2:
        return "Thursday";
      case FRIDAY2:
        return "Friday";
      case SATURDAY2:
        return "Saturday";
      default:
        throw new IllegalArgumentException("Unsupported Day: " + this);
    }

  }

  /**
   * Builds a day from a string.
   * @param str the day in a string
   * @return the day enum
   */
  public static Day buildDay(String str) {
    switch (str) {
      case "Sunday":
        return Day.SUNDAY;
      case "Monday":
        return Day.MONDAY;
      case "Tuesday":
        return Day.TUESDAY;
      case "Wednesday":
        return Day.WEDNESDAY;
      case "Thursday":
        return Day.THURSDAY;
      case "Friday":
        return Day.FRIDAY;
      case "Saturday":
        return Day.SATURDAY;
      default:
        throw new IllegalArgumentException("Unsupported Day: " + str);
    }

  }

}
