package cs3500.planner.view;

import cs3500.planner.controller.IScheduleFeatures;

/**
 * The ScheduleView interface defines the essential method that any schedule view class
 * should implement to display a list of events in the planner application. It serves
 * as a contract for showing schedules in a structured format.
 */
public interface ScheduleView {

  /**
   * Sets the controller that this view should interact with. The controller
   * acts as the intermediary between the view and the model, handling user
   * actions, updating the model, and reflecting changes in the view.
   *
   * @param controller The controller to set for this view.
   */
  void setController(IScheduleFeatures controller);
}
