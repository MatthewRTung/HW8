package cs3500.planner.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CentralSystem class that is used as the main system of the planner system. Allows for
 * managing a collection of user schedules.
 */
public class CentralSystem implements CentralSystemModel {
  //Maps user Ids to their ScheduleModel objects
  private final Map<String, ScheduleModel> userSchedules;

  /**
   * Constructs a new CentralSystem object with an empty schedule.
   */
  public CentralSystem() {
    userSchedules = new HashMap<>();
  }

  @Override
  public void addUser(String userId) {
    if (!userSchedules.containsKey(userId)) {
      userSchedules.put(userId, new StandardSchedule());
    }
  }

  /**4
   * Removes a user from the system.
   *
   * @param userId The UserID of the user to remove.
   * @return true if the user was successfully removed, false if the user does not exist.
   */

  @Override
  public boolean removeUser(String userId) {
    if (userSchedules.containsKey(userId)) {
      userSchedules.remove(userId);
      return true;
    }
    return false;
  }

  /**
   * Retrieves the schedule for a specified user.
   *
   * @param userId The UserId of the user whose schedule is to be retrieved.
   * @return The ScheduleModel associated with the given user, or null if the user
   *         does not exist.
   */

  @Override
  public ScheduleModel getUserSchedule(String userId) {
    if (userId == null || userId.isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be empty or null.");
    }
    return userSchedules.get(userId);
  }

  /**
   * Creates a new event in a user's schedule.
   *
   * @param userId The UserId of the user for whom the event is to be created.
   * @param event  The event to be added to the user's schedule.
   * @return true if the event was successfully created and added, false if not
   *         or the user does not exist.
   */

  @Override
  public boolean createEvent(String userId, EventModel event) {
    ScheduleModel schedule = userSchedules.get(userId);
    if (schedule != null && schedule.isTimeSlotFree(event.getStartTime(), event.getEndTime())) {
      return schedule.addEvent(event);
    }
    return false;
  }

  /**
   * Modifies an existing event in a user's schedule.
   *
   * @param userId The UserId of the user whose event is to be modified.
   * @param eventId The UserId of the event to be modified.
   * @param updatedEvent The updated event details to replace the existing event.
   * @return true if the event was successfully modified, false if the event does not exist,
   *         or the user does not exist.
   */

  @Override
  public boolean modifyEvent(String userId, String eventId, EventModel updatedEvent) {
    ScheduleModel schedule = userSchedules.get(userId);
    if (schedule != null) {
      EventModel existingEvent = schedule.getEventById(eventId);
      if (existingEvent != null) {
        if (!existingEvent.getHostId().equals(updatedEvent.getHostId())) {
          return false;
        }
        return schedule.modifyEvent(eventId, updatedEvent);
      }
    }
    return false;
  }

  /**
   * Deletes an event from a user's schedule.
   *
   * @param userId  The UserId of the user whose event is to be deleted.
   * @param eventId The UserId of the event to be deleted.
   * @return true if the event was successfully deleted, false if the event or user does not exist.
   */

  @Override
  public boolean deleteEvent(String userId, String eventId) {
    ScheduleModel schedule = userSchedules.get(userId);
    if (schedule != null) {
      return schedule.removeEvent(eventId);
    }
    return false;
  }

  /**
   * Retrieves the list of all the schedules in the system.
   * @return The list of all schedules.
   */
  @Override
  public Map<String, ScheduleModel> getAllSchedules() {
    return new HashMap<>(userSchedules);
  }

  /**
   * Retrieves the list of all the names of the users in the system.
   * @return the list of all names.
   */

  @Override
  public List<String> getUserName() {
    return new ArrayList<>(userSchedules.keySet());
  }

  /**
   * Checks to see if there is an event conflict with a specific user.
   * @param event the event to check for conflicts.
   * @param userName the name of the user to check the event against.
   * @return true or false based on whether there is a conflict or not.
   */

  @Override
  public boolean doesEventConflict(EventModel event, String userName) {
    ScheduleModel userSchedule = userSchedules.get(userName);
    if (userSchedule == null) {
      throw new IllegalArgumentException("User does not exist.");
    }
    return !userSchedule.isFree(event);
  }

  /**
   * Gets a list of events for a specific user.
   * @param userName the name of the user whose events are to be retrieved.
   * @return a list of events for that specific user.
   */

  @Override
  public List<EventModel> getEventsForUser(String userName) {
    ScheduleModel userSchedule = userSchedules.get(userName);
    if (userSchedule == null) {
      throw new IllegalArgumentException("User does not exist.");
    }
    return userSchedule.getEvents();
  }
}