package cs3500.planner.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents an event in the system.
 */
public interface EventModel extends ReadOnlyEventModel {
  /**
   * Checks if the event overlaps with another event.
   * Events are only allowed to overlap if the original event ends where the next event begins.
   * @param event the event to check for overlap
   * @return true if the event overlaps with another event, false otherwise
   */
  boolean overlaps(EventModel event);

  /**
   * Adds a user to the event's list of invitees.
   * @param userId the ID of the user to add
   */
  void addInvitee(String userId);

  /**
   * Removes a user from the event's list of invitees.
   * @param userId the ID of the user to remove
   */
  void removeInvitee(String userId);

  /**
   * Gets the location of the event.
   */
  String getLocation();

  /**
   * Returns the name of the event.
   * @return the name of the event
   */
  String getName();

  /**
   * Returns the start time of the event.
   * @return the start time of the event
   */
  LocalDateTime getStartTime();

  /**
   * Returns the end time of the event.
   * @return the end time of the event
   */
  LocalDateTime getEndTime();

  /**
   * Returns a list of user IDs of invitees to the event.
   * @return a list of user IDs of invitees
   */
  List<String> getInvitees();

  /**
   * Indicates whether the event is available online.
   * @return true if the event is available online, false otherwise
   */
  boolean isOnline();

  /**
   * Checks if a specific user is invited to the event.
   * @param userId the ID of the user to check
   * @return true if the user is invited, false otherwise
   */
  boolean isUserInvited(String userId);

  /**
   * Checks if the event is currently happening based on the current time.
   * @return true if the event is currently happening, false otherwise
   */
  boolean isCurrentlyHappening();

  /**
   * Compares this event with another event for sorting, primarily based on start time.
   * @param other the event to compare with
   * @return a negative integer, zero, or a positive integer as this event is less than, equal to,
   *         or greater than the specified event
   */
  int compareTo(EventModel other);

  /**
   * Gets the host ID of the event.
   * @return the host ID of the event
   */
  String getHostId();
}