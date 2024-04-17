package cs3500.planner.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.planner.model.CentralSystem;

/**
 * Stub implementation used for testing.
 */
public class FakeCentralSystemView extends CentralSystemFrame {
  private List<String> messages = new ArrayList<>();
  private String lastError = null;


  /**
   * Constructor for the CentralSystemFrame.
   *
   * @param model the central system model
   */
  public FakeCentralSystemView(CentralSystem model) {
    super();
  }

  @Override
  public void updateView() {
    messages.add("View updated.");
  }

  @Override
  public void displayError(String message) {
    lastError = message;
  }

  @Override
  public void showMessage(String message) {
    messages.add(message);
  }

  /**
   * Returns the last error message.
   * @return the last error message.
   */
  public String getLastError() {
    return lastError;
  }

  /**
   * Returns the list of messages.
   * @return the list of messages.
   */
  public List<String> getMessages() {
    return messages;
  }

  /**
   * Resets the list of messages.
   */
  public void reset() {
    messages.clear();
    lastError = null;
  }
}
