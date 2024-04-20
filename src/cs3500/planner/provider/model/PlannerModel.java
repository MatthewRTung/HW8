package cs3500.planner.provider.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A Planner Model is used to represent a schedule. It allows for the modification of events whether
 * it is through the User or directly through the Event.
 */
public interface PlannerModel {

  /**
   * Creates an Event in the Planner. Does not add the event if there is an overlapping event.
   * @param name name of the event
   * @param creator creator of the event
   * @param loc location of the event
   * @param startTime start time, including the day, of the event
   * @param endTime end time, including the day, of the event
   * @param invitees invitees of the event
   */
  void createEvent(String name, IUser creator, ILocation loc, ITime startTime,
                   ITime endTime, ArrayList<IUser> invitees);

  /**
   * Modifies an event from a planner. Must have all required fields to create a new event to
   * be able to modify.
   * @param e1 the event that is being modified.
   * @param name the name of the new event
   * @param creator the creator of the new event
   * @param loc the location of the new event
   * @param startTime the start time of the new event
   * @param endTime the end time of the new event
   * @param invitees the invitees of the new event
   */
  void modifyEvent(IEvent e1, String name, IUser creator, ILocation loc, ITime startTime,
                          ITime endTime, ArrayList<IUser> invitees);

  /**
   * Deletes a given event from a planner.
   * @param e the event to be deleted
   */
  void deleteEvent(IEvent e);

  /**
   * Reads in a list of XMLs into the Planner and updates the model's data accordingly.
   * @param filePaths the file paths of the XMLs, in the form of a list.
   */
  void readMultipleXMLs(List<String> filePaths);

  List<IUser> getClients();

  boolean hasTimeOverlap(IEvent event);
}
