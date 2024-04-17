package cs3500.planner.model;

import java.util.Map;

/**
 * CentralSystemModel interface that allows for mutable operations for the central system model
 * of the planner system.
 */
public interface CentralSystemModel extends ReadOnlyCentralSystemModel {

  /**
   * Adds a new user to the system.
   *
   * @param userId The UserId for the new user.
   */
  void addUser(String userId);

  /**
   * Removes a user from the system.
   *
   * @param userId The UserID of the user to remove.
   * @return true if the user was successfully removed, false if the user does not exist.
   */
  boolean removeUser(String userId);

  /**
   * Retrieves the schedule for a specified user.
   *
   * @param userId The UserId of the user whose schedule is to be retrieved.
   * @return The ScheduleModel associated with the given user, or null if the user
   *         does not exist.
   */
  ScheduleModel getUserSchedule(String userId);

  /**
   * Creates a new event in a user's schedule.
   *
   * @param userId The UserId of the user for whom the event is to be created.
   * @param event  The event to be added to the user's schedule.
   * @return true if the event was successfully created and added, false if not
   *         or the user does not exist.
   */
  boolean createEvent(String userId, EventModel event);

  /**
   * Modifies an existing event in a user's schedule.
   *
   * @param userId The UserId of the user whose event is to be modified.
   * @param eventId The UserId of the event to be modified.
   * @param updatedEvent The updated event details to replace the existing event.
   * @return true if the event was successfully modified, false if the event does not exist,
   *         or the user does not exist.
   */
  boolean modifyEvent(String userId, String eventId, EventModel updatedEvent);

  /**
   * Deletes an event from a user's schedule.
   *
   * @param userId  The UserId of the user whose event is to be deleted.
   * @param eventId The UserId of the event to be deleted.
   * @return true if the event was successfully deleted, false if the event or user does not exist.
   */
  boolean deleteEvent(String userId, String eventId);

  /**
   * Retrieves the list of all the schedules in the system.
   * @return The list of all schedules.
   */
  Map<String, ScheduleModel> getAllSchedules();
}