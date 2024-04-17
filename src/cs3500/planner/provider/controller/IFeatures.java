package cs3500.planner.provider.controller;

import java.util.ArrayList;

import cs3500.planner.provider.model.IEvent;
import cs3500.planner.provider.model.IUser;

/**
 * represents the Features interface.
 * shows functionality
 */

public interface IFeatures {
  /**
   * Updates the user's events with the newly created Event decided by the strategy given
   * to the controller.
   * @param eventName the event name.
   * @param host the creator of the event.
   * @param locString the location of the event.
   * @param duration the duration of the event.
   * @param invitees the invitees of the event.
   */
  void scheduleStrat(String eventName, IUser host, String locString, boolean isOnline, String duration,
                     ArrayList<IUser> invitees);

  /**
   * Creates an Event in the Planner. Does not add the event if there is an overlapping event.
   * @param name name of the event
   * @param creator creator of the event
   * @param locString location of the event
   * @param startingTime start time of the event
   * @param startDay start day of the event
   * @param endingTime end time, including the day, of the event
   * @param endDay end day of the event
   * @param invitees invitees of the event
   */
  void createEvent(String name, IUser creator, String locString, boolean isOnline,
                   String startingTime, String startDay, String endingTime, String endDay,
                   ArrayList<IUser> invitees);

  /**
   * Modifies an event from a planner. Must have all required fields to create a new event to
   * be able to modify.
   * @param e1 the event that is being modified.
   * @param name the name of the new event
   * @param creator the creator of the new event
   * @param locString location of the event
   * @param startingTime start time of the event
   * @param startDay start day of the event
   * @param endingTime end time, including the day, of the event
   * @param endDay end day of the event
   * @param invitees invitees of the event
   */
  void modifyEvent(IEvent e1, String name, IUser creator, String locString, boolean isOnline,
                   String startingTime, String startDay, String endingTime, String endDay,
                   ArrayList<IUser> invitees);

  /**
   * Deletes a given event from a planner.
   * @param e the event to be deleted
   */
  void deleteEvent(String name, IUser creator, String locString, boolean isOnline,
                   String startingTime, String startDay, String endingTime, String endDay,
                   ArrayList<IUser> invitees);

  /**
   * Reads the XML of the currently selected user to the XMLPath given from the view.
   * @param xmlPath the path, as a string, of the XML
   */
  void readXML(String xmlPath);

  /**
   * Saves a schedule to the selected directory path of the device.
   * @param directoryPath the path, as a string, of the directory to save the XML.
   */
  void saveSchedule(String directoryPath);
}
