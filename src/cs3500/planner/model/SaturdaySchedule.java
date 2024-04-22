package cs3500.planner.model;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class SaturdaySchedule extends Schedule {

  @Override
  public Map<DayOfWeek, List<EventModel>> getWeeklyEvents() {
    Map<DayOfWeek, List<EventModel>> weeklyEvents = new EnumMap<>(DayOfWeek.class);
    for (DayOfWeek day : DayOfWeek.values()) {
      weeklyEvents.put(day, new ArrayList<>());
    }
    for (EventModel event : events) {
      DayOfWeek adjustedDay = DayOfWeek.of((event.getStartTime().getDayOfWeek().getValue() + 1) % 7 + 1);
      weeklyEvents.get(adjustedDay).add(event);
    }
    return weeklyEvents;
  }
}
