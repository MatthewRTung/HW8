package cs3500.planner.provider.view;

import cs3500.planner.provider.controller.IFeatures;
import cs3500.planner.provider.model.IUser;
import cs3500.planner.provider.model.ReadOnlyPlannerModel;


/**
 * Represents a frame for a schedule.
 */
public interface IScheduleFrame {
  /**
   * Adds a feature listener.
   * @param features the features to add
   */
  void addFeatureListener(IFeatures features);

  /**
   * Gets the ROModel for the Controller to see and update.
   */
  ReadOnlyPlannerModel getROModel();

  /**
   * Gets the currently selected user, for the controller to use in order to update the UI
   * for the selected user after a change has occurred.
   * @return the string of the name of the selected user.
   */
  String getSelectedUser();

  /**
   * Updates the currently selected user's schedule in the view. Used in the controller.
   * @param selectedUser the currently selected user and user's view to update.
   */
  void displayUserSchedule(String selectedUser);

  /**
   * Updates the combo box of users of the view after an XML has been read in.
   */
  void setClientListComboBox();

  /**
   * Finds the user associated with the username. Safe since all users are assumed to have
   * different names.
   */
  IUser findUserByName(String username);
}
