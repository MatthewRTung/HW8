package cs3500.planner.model;

import java.util.List;

/**
 * Provides a read-only view of a user's schedule.
 */
public interface ReadOnlyCentralSystemModel {

  /**
   * Retrieves a list of all usernames in the planner system.
   * @return a list of usernames.
   */
  List<String> getUserName();

  /**
   * Checks if a given event conflicts with the schedules of any invited users.
   * @param event the event to check for conflicts.
   * @param userName the name of the user to check the event against.
   * @return true if there is a conflict with the user's schedule, false otherwise.
   */
  boolean doesEventConflict(Event event, String userName);

  /**
   * Retrieves a list of events for a specified user.
   * @param userName the name of the user whose events are to be retrieved.
   * @return a list of events associated with the specified user.
   */
  List<Event> getEventsForUser(String userName);
}
