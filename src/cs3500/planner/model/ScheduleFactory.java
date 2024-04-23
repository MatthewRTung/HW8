package cs3500.planner.model;

/**
 * Factory class to help create a new instance of which schedule we want to use.
 */
public class ScheduleFactory {
  /**
   * Method to help create which type of schedule I want. Either start on saturday or sunday.
   * @param type the type of schedule I want.
   * @return the type of schedule dictated by command line args
   */
  public static ScheduleModel createSchedule(String type) {
    switch (type.toLowerCase()) {
      case "standard":
        return new StandardSchedule();
      case "saturday":
        return new SaturdaySchedule();
      default:
        throw new IllegalArgumentException("Unknown schedule type: " + type);
    }
  }
}
