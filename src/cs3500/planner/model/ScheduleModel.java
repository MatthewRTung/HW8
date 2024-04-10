package cs3500.planner.model;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Represents an event in the system.
 */
public interface ScheduleModel extends ReadOnlyScheduleModel {
  /**
   * Adds an event to the schedule if it does not conflict with existing events.
   * @param event The event to add.
   * @return true if the event was successfully added, false if it conflicts with an existing event.
   */
  boolean addEvent(Event event);

  /**
   * Removes a specified event from the schedule.
   * @param eventId The eventId of the event to remove.
   * @return true if the event was successfully removed, false if the event was not found.
   */
  boolean removeEvent(String eventId);

  /**
   * Modifies details of an existing event.
   * @param eventId The eventId of the event to modify.
   * @param newEvent The new event details to apply.
   * @return true if the event was successfully modified, false if the modification violates
   *         constraints or the event was not found.
   */
  boolean modifyEvent(String eventId, Event newEvent);

  /**
   * Finds and returns a list of events happening at a specific time.
   * @param dateTime The date and time for which to find events.
   * @return A list of events occurring at the specified time.
   */
  List<Event> getEventsAt(LocalDateTime dateTime);

  /**
   * Retrieves all events scheduled for a specific user.
   * @return A list of all events in the schedule.
   */
  List<Event> getAllEvents();

  /**
   * Checks if a specific time slot in the schedule is free.
   * @param startTime The start time of the slot to check.
   * @param endTime The end time of the slot to check.
   * @return true if the time slot is free, false otherwise.
   */
  boolean isTimeSlotFree(LocalDateTime startTime, LocalDateTime endTime);

  /**
   * Retrieves an event by its eventId.
   * @param eventId The unique identifier of the event to retrieve.
   * @return The event with the specified ID, or null if not found.
   */
  Event getEventById(String eventId);

  /**
   * Retrieves a map of events organized by the day of the week.
   *
   * @return A map where each key is a DayOfWeek and each value is a list of events for that day.
   */
  Map<DayOfWeek, List<Event>> getWeeklyEvents();
}