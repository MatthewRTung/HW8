package cs3500.planner.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Schedule class that represents a schedule of events for a single user.
 */
public class Schedule implements ScheduleModel {
  //list of all events in the user's schedule
  private List<EventModel> events;

  /**
   * Constructor an empty schedule with no events.
   */
  public Schedule() {
    this.events = new ArrayList<>();
  }

  /**
   * Adds an event to the schedule if it does not conflict with existing events.
   * @param event The event to add.
   * @return true if the event was successfully added, false if it conflicts with an existing event.
   */

  @Override
  public boolean addEvent(EventModel event) {
    if (isTimeSlotFree(event.getStartTime(), event.getEndTime())) {
      events.add(event);
      return true;
    }
    return false;
  }

  /**
   * Removes a specified event from the schedule.
   * @param eventId The eventId of the event to remove.
   * @return true if the event was successfully removed, false if the event was not found.
   */

  @Override
  public boolean removeEvent(String eventId) {
    return events.removeIf(event -> event.getName().equals(eventId));
  }

  /**
   * Modifies details of an existing event.
   * @param eventId The eventId of the event to modify.
   * @param newEvent The new event details to apply.
   * @return true if the event was successfully modified, false if the modification violates
   *         constraints or the event was not found.
   */

  @Override
  public boolean modifyEvent(String eventId, EventModel newEvent) {
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).getName().equals(eventId)) {
        if (isTimeSlotFree(newEvent.getStartTime(), newEvent.getEndTime())
                || events.get(i).getStartTime().equals(newEvent.getStartTime())
                && events.get(i).getEndTime().equals(newEvent.getEndTime())) {
          events.set(i, newEvent);
          return true;
        } else {
          return false;
        }
      }
    }
    return false;
  }

  /**
   * Finds and returns a list of events happening at a specific time.
   * @param dateTime The date and time for which to find events.
   * @return A list of events occurring at the specified time.
   */

  @Override
  public List<EventModel> getEventsAt(LocalDateTime dateTime) {
    return events.stream()
            .filter(event -> !event.getStartTime().isAfter(dateTime)
                    && !event.getEndTime().isBefore(dateTime))
            .collect(Collectors.toList());
  }

  /**
   * Retrieves all events scheduled for a specific user.
   * @return A list of all events in the schedule.
   */

  @Override
  public List<EventModel> getAllEvents() {
    return new ArrayList<>(events);
  }

  /**
   * Checks if a specific time slot in the schedule is free.
   * @param start The start time of the slot to check.
   * @param end The end time of the slot to check.
   * @return true if the time slot is free, false otherwise.
   */

  @Override
  public boolean isTimeSlotFree(LocalDateTime start, LocalDateTime end) {
    return events.stream()
            .noneMatch(event -> event.getStartTime()
                    .isBefore(end) && event.getEndTime().isAfter(start));
  }

  /**
   * Retrieves an event by its eventId.
   * @param eventId The unique identifier of the event to retrieve.
   * @return The event with the specified ID, or null if not found.
   */

  @Override
  public EventModel getEventById(String eventId) {
    return events.stream()
            .filter(event -> event.getName().equals(eventId))
            .findFirst()
            .orElse(null);
  }

  /**
   * Retrieves a map of events organized by the day of the week.
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
      LocalDate eventDate = event.getStartTime().toLocalDate();
      DayOfWeek dayOfWeek = eventDate.getDayOfWeek();
      List<EventModel> eventsForDay = weeklyEvents.get(dayOfWeek);
      eventsForDay.add(event);
    }
    return weeklyEvents;
  }

  /**
   * Gets a list of all events in a schedule.
   * @return the list of all events in a schedule.
   */

  @Override
  public List<EventModel> getEvents() {
    return new ArrayList<>(events);
  }

  /**
   * Checks whether a given time in the schedule is open for the event.
   * @param event the event to check for potential conflicts
   * @return true if the time is open, false otherwise.
   */
  @Override
  public boolean isFree(EventModel event) {
    return isTimeSlotFree(event.getStartTime(), event.getEndTime());
  }
}