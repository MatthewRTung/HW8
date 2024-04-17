package cs3500.planner.provider.view;


import javax.swing.JComboBox;

/**
 * Represents a panel for a schedule.
 */
public interface ISchedulePanel {
  /**
   * updates the schedule, used for the controller to update the view from any changes
   * performed to the model.
   * @param events the new list of events with changes to be reflected in the model.
   */
  void updateSchedule(List<Event> events);

  /**
   * Makes a combo box for the list of users to navigate between schedules. (doesn't add the
   * combo box to the panel, just makes the combo box)
   * @param listOfUsers the list of users to add to the combo box
   * @return the combo box
   */
  JComboBox<String> addUsersToComboBox(List<User> listOfUsers);

  /**
   * Adds a combo box as the list of users combo box to navigate through different user's schedules.
   */
  void setClientListComboBox();
}
