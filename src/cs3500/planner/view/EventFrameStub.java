package cs3500.planner.view;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;

/**
 * Stub implementation used for testing.
 */
public class EventFrameStub extends EventFrame {

  /**
   * Constructor for EventFrame to initialize the fields for the class.
   *
   * @param model the model to be used.
   */
  public EventFrameStub(CentralSystem model) {
    super();
  }

  /**
   * Method to set the details of an event.
   * @param event The event to set the details of.
   */
  public void setEventDetails(Event event) {
    System.out.println("Event Details Set: " + event.getEventName());
  }

  /**
   * Method used to display a message to the user.
   * @param message The message to be displayed.
   */
  public void showError(String message) {
    System.out.println("Error: " + message);
  }
}
