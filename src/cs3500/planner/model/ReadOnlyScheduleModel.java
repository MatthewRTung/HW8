package cs3500.planner.model;

import java.util.List;

/**
 * Represents a read-only view of a schedule model that is implemented by ScheduleModel.
 */
public interface ReadOnlyScheduleModel {

  /**
   * Retrieves all events in the schedule.
   *
   * @return a list of all events currently in the schedule.
   */
  List<Event> getEvents();

  /**
   * Checks if a specific event conflicts with any existing events in the schedule.
   * @param event the event to check for potential conflicts
   * @return true if the schedule is free for the event's duration, false otherwise.
   */
  boolean isFree(Event event);
}
