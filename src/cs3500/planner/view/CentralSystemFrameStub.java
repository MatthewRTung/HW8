package cs3500.planner.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.planner.model.CentralSystemModel;

/**
 * CentralSystemFrameStub used to test the ScheduleController.
 */
public class CentralSystemFrameStub extends CentralSystemFrame {
  private boolean viewUpdated = false;
  private final List<String> displayedMessages = new ArrayList<>();
  private final List<String> displayedErrors = new ArrayList<>();

  /**
   * Constructor for the CentralSystemFrameStub.
   */
  public CentralSystemFrameStub(CentralSystemModel model) {
    super();
  }

  /**
   * Method used to update the view.
   */
  public void updateView() {
    viewUpdated = true;
  }

  /**
   * Method used to display a message to the user.
   * @param message The message to be displayed.
   */
  public void showMessage(String message) {
    displayedMessages.add(message);
  }

  /**
   * Method used to display an error message to the user.
   * @param message The error message to be displayed.
   */
  public void displayError(String message) {
    displayedErrors.add(message);
  }

  /**
   * Method used to check if the view has been updated.
   * @return True if the view has been updated, false otherwise.
   */
  public boolean isViewUpdated() {
    return viewUpdated;
  }

  /**
   * Method used to check if an error message has been displayed.
   * @param message The error message to check for.
   * @return True if the error message has been displayed, false otherwise.
   */
  public boolean isErrorDisplayed(String message) {
    return displayedErrors.contains(message);
  }
}
