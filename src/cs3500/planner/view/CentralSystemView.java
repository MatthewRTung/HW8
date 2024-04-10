package cs3500.planner.view;

import cs3500.planner.controller.IScheduleFeatures;

/**
 * Interface for the CentralSystemFrame that creates the GUI for the NUPlanner.
 * The CentralSystemView interface defines the essential methods that any view class
 * should implement to interact with the central system. It serves as a contract
 * for updating the view and displaying errors.
 */
public interface CentralSystemView {

  /**
   * Method that updates the view of the CentralSystemFrame.
   * Updates the view to reflect any changes in the model or the state of the application.
   * This method is typically called when the data displayed in the view needs to be refreshed,
   * such as after a change in the underlying model or a user action.
   */
  void updateView();

  /**
   * Displays an error message to the user. This method is used to communicate error
   * conditions or exceptional situations to the user in a consistent manner.
   * @param message The error message to be displayed. This message should be clear
   *                and informative so that the user understands the nature of the error.
   */
  void displayError(String message);

  /**
   * Shows a generic message to the user, typically for information or confirmation.
   *
   * @param message The message to be displayed.
   */
  void showMessage(String message);

  /**
   * Finalizes the initialization of the view components and makes the view visible.
   * This method is typically called after all view components have been created and
   * configured but just before the user interface is displayed to the user.
   */
  void finalizeInitialization();

  /**
   * Sets the controller that this view should interact with. The controller
   * acts as the intermediary between the view and the model, handling user
   * actions, updating the model, and reflecting changes in the view.
   *
   * @param controller The controller to set for this view.
   */
  void setController(IScheduleFeatures controller);
}
