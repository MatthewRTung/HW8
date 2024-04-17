package cs3500.planner.provider.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A schedule that is paired with a User. Consists of all events for that user.
 */
public interface INUPlanner {
  /**
   * Allows access to the events.
   *
   * @return events field.
   */
  List<IEvent> getEvents();

  /**
   * Creates an event for a single schedule. Public so that Central Planner can access them
   * to create events for each specific schedule.
   *
   * @param name      name of the event.
   * @param creator   creator of the event.
   * @param loc       location of the event.
   * @param startTime start time of the event.
   * @param endTime   end time of the event.
   * @param invitees  invitees of the event.
   */
  void createEvent(String name, IUser creator, ILocation loc, ITime startTime,
                   ITime endTime, ArrayList<IUser> invitees);

  /**
   * Modifies an event for a single schedule. Public so that Central Planner can access them
   * to modify events for each specific schedule.
   *
   * @param name      name of the event.
   * @param creator   creator of the event.
   * @param loc       location of the event.
   * @param startTime start time of the event.
   * @param endTime   end time of the event.
   * @param invitees  invitees of the event.
   */
  void modifyEvent(IEvent e1, String name, IUser creator, ILocation loc, ITime startTime,
                   ITime endTime, ArrayList<IUser> invitees);

  /**
   * Deletes an event for a single schedule. Public so that Central Planner can access them
   * to delete events for each specific schedule.
   */
  void deleteEvent(IEvent e);

  /**
   * Determines whether there is a time overlap. public so that it can act as a helper/tool.
   *
   * @param e the event to add to the schedule.
   * @return boolean true of there is a time overlap, false if there is not.
   */
  boolean hasTimeOverlap(IEvent e);
}
