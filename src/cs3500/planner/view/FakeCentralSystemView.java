package cs3500.planner.view;

import java.util.ArrayList;
import java.util.List;

import cs3500.planner.model.CentralSystem;

public class FakeCentralSystemView extends CentralSystemFrame {
  private List<String> messages = new ArrayList<>();
  private String lastError = null;


  /**
   * Constructor for the CentralSystemFrame.
   *
   * @param model the central system model
   */
  public FakeCentralSystemView(CentralSystem model) {
    super(model);
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

  public String getLastError() {
    return lastError;
  }

  public List<String> getMessages() {
    return messages;
  }

  public void reset() {
    messages.clear();
    lastError = null;
  }
}
