package cs3500.planner.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import cs3500.planner.model.EventModel;

import javax.swing.JComboBox;

import cs3500.planner.model.EventAdapter;
import cs3500.planner.provider.model.IEvent;
import cs3500.planner.provider.model.IUser;
import cs3500.planner.provider.view.ISchedulePanel;

/**
 * ScheduleAdapter is an adapter class that adapts a Schedule object and an ISchedulePanel object
 * to provide compatibility with the planner system by implementing the ISchedulePanel interface.
 */
public class ScheduleAdapter implements ISchedulePanel {

  private final ISchedulePanel schedulePanel;
  private final IUser currentUser;
  private final EventModel currentEvent;

  /**
   * Constructs a ScheduleAdapter with the given Schedule, ISchedulePanel, current user, and
   * current event.
   *
   * @param schedulePanel the ISchedulePanel object to adapt
   * @param currrentUser   the current user
   * @param currentEvent  the current event
   */
  public ScheduleAdapter(ISchedulePanel schedulePanel, IUser currrentUser,
                         EventModel currentEvent) {
    this.schedulePanel = schedulePanel;
    this.currentUser = currrentUser;
    this.currentEvent = currentEvent;
  }

  /**
   * Updates the schedule with the given list of events.
   *
   * @param events the list of events to update the schedule with
   */
  @Override
  public void updateSchedule(List<IEvent> events) {
    List<IEvent> adaptedEvents = events.stream()
            .map(event -> new EventAdapter(currentEvent, currentUser, new ArrayList<>()))
            .collect(Collectors.toList());
    schedulePanel.updateSchedule(adaptedEvents);
  }

  /**
   * Adds users to the combo box and returns it.
   *
   * @param listOfUsers the list of users to add to the combo box
   * @return the combo box with users added
   */

  @Override
  public JComboBox<String> addUsersToComboBox(List<IUser> listOfUsers) {
    return schedulePanel.addUsersToComboBox(listOfUsers);
  }

  /**
   * Sets the client list combo box.
   */
  @Override
  public void setClientListComboBox() {
    schedulePanel.setClientListComboBox();
  }
}