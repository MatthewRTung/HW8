package cs3500.planner.model;

public class ScheduleFactory {
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
