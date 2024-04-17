package cs3500.planner.provider.strategy;

import cs3500.planner.provider.model.*;

/**
 *represents a Strategy Interface for building an event.
 */
public interface PlannerStrategy {

  /**
   * Builds an event that all invitees can attend.
   * is as long as the duration.
   * @param eventName String
   * @param host User
   * @param loc Location
   * @param duration String
   * @param invitees ArrayList for User
   * @return Event
   */
  Event chooseTime(String eventName, User host, Location loc, String duration,
                          ArrayList<User> invitees);

  /**
   * Builds an instance of Time.
   * @param startHours int
   * @param startMinutes int
   * @param startDay int
   * @return Time
   */
  Time makeTime(int startHours, int startMinutes, int startDay);

  /**
   * Creates a list of users that can attend the event from the list of invitees.
   * @param newEvent Event
   * @param invitees List of Users
   * @return ArrayList of Users
   */
  ArrayList<User> findValidEvent(Event newEvent, ArrayList<User> invitees);
}
