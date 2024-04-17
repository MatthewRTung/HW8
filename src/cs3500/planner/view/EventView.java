package cs3500.planner.view;

import cs3500.planner.model.EventModel;

/**
 * Interface for the EventView to create the event frame to create events for users.
 */
public interface EventView {

  /**
   * Method used to set the details of the event for each event.
   * @param event The event to set the details of.
   */
  void setEventDetails(EventModel event);


  /**
   * Method used to display if there is an error with inputting event details.
   * @param message The error message to display.
   */
  void displayError(String message);
}