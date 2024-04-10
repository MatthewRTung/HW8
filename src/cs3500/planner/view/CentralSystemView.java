package cs3500.planner.view;

/**
<<<<<<< HEAD
 * Interface for the CentralSystemFrame that creates the GUI for the NUPlanner.
=======
 * The CentralSystemView interface defines the essential methods that any view class
 * should implement to interact with the central system. It serves as a contract
 * for updating the view and displaying errors.
>>>>>>> 5dcc095b6bb7ce2ee355f4fd70988f8bd94280ef
 */
public interface CentralSystemView {

  /**
<<<<<<< HEAD
   * Method that updates the view of the CentralSystemFrame.
=======
   * Updates the view to reflect any changes in the model or the state of the application.
   * This method is typically called when the data displayed in the view needs to be refreshed,
   * such as after a change in the underlying model or a user action.
>>>>>>> 5dcc095b6bb7ce2ee355f4fd70988f8bd94280ef
   */
  void updateView();

  /**
<<<<<<< HEAD
   * Method that displays an error message in the CentralSystemFrame.
   * @param message the error message to be displayed
=======
   * Displays an error message to the user. This method is used to communicate error
   * conditions or exceptional situations to the user in a consistent manner.
   *
   * @param message The error message to be displayed. This message should be clear
   *                and informative so that the user understands the nature of the error.
>>>>>>> 5dcc095b6bb7ce2ee355f4fd70988f8bd94280ef
   */
  void displayError(String message);
}
