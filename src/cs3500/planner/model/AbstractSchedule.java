package cs3500.planner.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractSchedule implements ScheduleModel {
  protected List<EventModel> events;

  public AbstractSchedule() {
    this.events = new ArrayList<>();
  }

  @Override
  public boolean addEvent(EventModel event) {
    if (isTimeSlotFree(event.getStartTime(), event.getEndTime())) {
      events.add(event);
      return true;
    }
    return false;
  }

  @Override
  public boolean removeEvent(String eventId) {
    return events.removeIf(event -> event.getName().equals(eventId));
  }

  @Override
  public boolean modifyEvent(String eventId, EventModel newEvent) {
    for (int i = 0; i < events.size(); i++) {
      EventModel currentEvent = events.get(i);
      if (currentEvent.getName().equals(eventId)) {
        if (isTimeSlotFree(newEvent.getStartTime(), newEvent.getEndTime()) ||
                (currentEvent.getStartTime().equals(newEvent.getStartTime()) &&
                        currentEvent.getEndTime().equals(newEvent.getEndTime()))) {
          events.set(i, newEvent);
          return true;
        }
        break;
      }
    }
    return false;
  }

  @Override
  public List<EventModel> getEventsAt(LocalDateTime dateTime) {
    return events.stream()
            .filter(event -> !event.getStartTime().isAfter(dateTime) &&
                    !event.getEndTime().isBefore(dateTime))
            .collect(Collectors.toList());
  }

  @Override
  public List<EventModel> getAllEvents() {
    return new ArrayList<>(events);
  }

  @Override
  public boolean isTimeSlotFree(LocalDateTime startTime, LocalDateTime endTime) {
    return events.stream()
            .noneMatch(event -> event.getStartTime().isBefore(endTime) &&
                    event.getEndTime().isAfter(startTime));
  }

  @Override
  public EventModel getEventById(String eventId) {
    return events.stream()
            .filter(event -> event.getName().equals(eventId))
            .findFirst()
            .orElse(null);
  }

  @Override
  public abstract Map<DayOfWeek, List<EventModel>> getWeeklyEvents();

  @Override
  public List<EventModel> getEvents() {
    return new ArrayList<>(events);
  }

  @Override
  public boolean isFree(EventModel event) {
    return isTimeSlotFree(event.getStartTime(), event.getEndTime());
  }
}
