package cs3500.planner.controller;

import java.io.File;
import java.util.List;

import javax.swing.JComboBox;

import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.EventModel;

/**
 * Interface for defining the actions and features for scheduling within the application.
 * This interface provides methods for interacting with the central system model, managing events,
 * and updating the view.
 */
public interface IScheduleFeatures {

  /**
   * Loads events from an XML file into the application.
   *
   * @param file The XML file containing events to be loaded.
   */
  void loadXMLAction(File file);

  /**
   * Saves the current state of all schedules in the application.
   */
  void saveSchedules();

  /**
   * Creates a new event and adds it to the schedule.
   *
   * @param event The event to be added.
   */
  void createEvent(EventModel event);

  /**
   * Modifies an existing event in the user's schedule.
   *
   * @param userId The ID of the user whose event is being modified.
   * @param eventId The ID of the event to be modified.
   * @param event The new event details to replace the existing event.
   */
  void modifyEvent(String userId, String eventId, EventModel event);

  /**
   * Removes an event from the user's schedule.
   *
   * @param userId The ID of the user whose event is being removed.
   * @param eventId The ID of the event to be removed.
   */
  boolean removeEvent(String userId, String eventId);

  /**
   * Opens the frame for creating a new event.
   */
  void openEventFrame();

  /**
   * Switches the current view to show the schedule of a different user.
   *
   * @param userID The ID of the user whose schedule should be displayed.
   * @return A list of events for the specified user.
   */
  List<EventModel> switchUser(String userID);

  /**
   * Launches the application with a given central system model.
   *
   @param m The central system model to be used in the application.
   */
  void launch(CentralSystemModel m);

  /**
   * Opens the event details frame for a specific event.
   *
   * @param event The event for which the details should be displayed.
   */
  void openEventDetails(EventModel event);

  /**
   * Adds a dropdown component to the view, typically for user selection.
   *
   * @param dropDown The JComboBox to be added to the view.
   */
  void addDropDown(JComboBox<String> dropDown);

  /**
   * Opens the frame for scheduling an event using a specified strategy.
   */
  void openScheduleFrame();

  /**
   * Schedules an event with a specified strategy.
   *
   * @param name The name of the event.
   * @param location The location of the event.
   * @param isOnline Indicates whether the event is online.
   * @param duration The duration of the event.
   * @param user The user ID of the event creator.
   * @param invitees A comma-separated list of invitees.
   * @param strategy The scheduling strategy to use.
   */
  void scheduleEventWithStrategy(String name, String location, boolean isOnline, int duration,
                                 String user, String invitees, String strategy);

  /**
   * Gets the ID of the current user.
   *
   * @return The current user's ID.
   */
  String getCurrentUser();

  /**
   * Sets the current user in the application.
   *
   * @param user The user ID to be set as the current user.
   */
  void setCurrentUser(String user);

  /**
   * Sets the strategy provided by command line.
   *
   * @param strategy The strategy to be set as.
   */
  void setDefaultSchedulingStrategy(String strategy);
}


