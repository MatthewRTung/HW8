package cs3500.planner.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * A stub implementation of the Schedule interface used to test ScheduleController.
 */
public class ScheduleStub extends Schedule {

  public Map<DayOfWeek, List<EventModel>> getWeeklyEvents() {
    return Map.of();
  }

  /**
   * Constructor for the ScheduleStub.
   * @return A list of events.
   */
  public List<EventModel> getEvents() {
    // Return a fixed collection of events for testing
    return Collections.singletonList(new Event("Test", "Team Meeting", true,
            LocalDateTime.now(), LocalDateTime.now().plusHours(2), false, "Host1"));
  }

  /**
   * Constructor for the ScheduleStub.
   * @param event The event to add.
   * @return True if the event was added successfully.
   */
  public boolean addEvent(Event event) {
    // Simulate adding an event successfully
    return true;
  }

  /**
   * Constructor for the ScheduleStub.
   * @param event The event to remove.
   * @return True if the event was removed successfully.
   */
  public boolean removeEvent(Event event) {
    return true;
  }
}
