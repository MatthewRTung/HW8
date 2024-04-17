package cs3500.planner.provider.model;

/**
 * An interface to represent a user, and actions that can be done to a user. A user consists of
 * a NUPlanner, and calling modifying events on the user affect the user's NUPlanner.
 */
public interface IUser {
  /**
   * adds the given event to a user's schedule, protected so that it can act as a helper
   * to the XMLEditor
   * @param e the event to add.
   */

  void addEvent(IEvent e);

  /**
   * removes the event from the user's schedule, protected so that it can act as a helper
   * to the central planner.
   * @param e the event to remove.
   */
  void removeEvent(IEvent e);

  /**
   * gets the user's name (as a string).
   * @return string, user's name.
   */
  String getUserName();

  /**
   * gets the user's schedule (as a NUPlanner).
   * @return NUPlanner, user's schedule.
   */
  INUPlanner getSchedule();

  /**
   * adds all the events from a given NUPlanner to a user's schedule.
   * @param plan a NUPlanner with events to include in a user's schedule.
   */
  void addAllEvents(INUPlanner plan);
}
