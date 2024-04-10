package cs3500.planner.view;

import java.util.Collection;

import cs3500.planner.model.CentralSystemModel;

/**
 * A stub implementation of the ScheduleFrame class for testing purposes.
 */
public class ScheduleFrameStub extends ScheduleFrame {

  /**
   * Constructs a new ScheduleFrameStub instance.
   *
   * @param model The central system model that this frame interacts with.
   */
  public ScheduleFrameStub(CentralSystemModel model) {
    super(model);
  }

  /**
   * Displays the schedule of events.
   * This method simulates the display of events in the schedule by printing them to the console.
   *
   * @param events A collection of event descriptions to be displayed.
   */
  public void displaySchedule(Collection<String> events) {
    System.out.println("Schedule Displayed: " + events);
  }

  /**
   * Displays an error message.
   * This method simulates the display of an error message in the schedule frame by printing it to the console.
   *
   * @param message The error message to be displayed.
   */
  public void showError(String message) {
    System.out.println("Error in Schedule Frame: " + message);
  }
}
