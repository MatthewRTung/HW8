package cs3500.planner.model;

import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * StandardSchedule class that creates the schedule starting on Sunday.
 */
public class StandardSchedule extends Schedule {

  /**
   * Retrieves a map of events organized by the day of the week starting on Saturday.
   *
   * @return A map where each key is a DayOfWeek and each value is a list of events for that day.
   */
  @Override
  public Map<DayOfWeek, List<EventModel>> getWeeklyEvents() {
    Map<DayOfWeek, List<EventModel>> weeklyEvents = new EnumMap<>(DayOfWeek.class);
    for (DayOfWeek day : DayOfWeek.values()) {
      weeklyEvents.put(day, new ArrayList<>());
    }
    for (EventModel event : events) {
      weeklyEvents.get(event.getStartTime().getDayOfWeek()).add(event);
    }
    return weeklyEvents;
  }
}
