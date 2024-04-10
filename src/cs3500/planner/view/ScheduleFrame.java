package cs3500.planner.view;

import cs3500.planner.controller.IScheduleFeatures;
import cs3500.planner.model.CentralSystemModel;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Component;

/**
 * The ScheduleFrame class implements the essential method to display a list of events in the planner application.
 */
public class ScheduleFrame extends JFrame implements ScheduleView {
  private final JTextField eventNameField;
  private final JTextField eventLocationField;
  private final JCheckBox isOnlineCheckbox;
  private final JTextField durationField;
  private final JTextField inviteesField;
  private final JButton scheduleButton;
  private IScheduleFeatures controller;
  private final JComboBox<String> strategyComboBox;

  /**
   * Constructs a ScheduleFrame, which is a GUI window for scheduling events in the
   * provided CentralSystemModel. The frame includes fields for event name, location,
   * duration, invitees, and a checkbox for online events. It also includes a combo box
   * for selecting scheduling strategy and a button to initiate scheduling.
   *
   * @param model The CentralSystemModel that this ScheduleFrame will interact with
   *              to schedule new events.
   */
  public ScheduleFrame(CentralSystemModel model) {
    super("Schedule Viewer");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setSize(500, 400);
    eventNameField = new JTextField(20);
    eventLocationField = new JTextField(20);
    isOnlineCheckbox = new JCheckBox("Online");
    durationField = new JTextField("90");
    inviteesField = new JTextField();
    scheduleButton = new JButton("Schedule event");
    strategyComboBox = new JComboBox<>(new String[]{"Any time", "Work hours"});
    layoutComponents();
  }

  /**
   * Sets the controller that this view should interact with. The controller
   * acts as the intermediary between the view and the model, handling user
   * actions, updating the model, and reflecting changes in the view.
   *
   * @param controller The controller to set for this view.
   */
  public void setController(IScheduleFeatures controller) {
    this.controller = controller;
  }

  // helper method to set the layout each part of the GUI
  private void layoutComponents() {
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10);
    addComponent(gbc, 0, 0, new JLabel("Event name: "));
    addComponent(gbc, 1, 0, eventNameField);
    addComponent(gbc, 0, 1, new JLabel("Location: "));
    addComponent(gbc, 1, 1, eventLocationField);
    addComponent(gbc, 0, 2, isOnlineCheckbox, 2);
    addComponent(gbc, 0, 3, new JLabel("Duration in minutes: "));
    addComponent(gbc, 1, 3, durationField);
    addComponent(gbc, 0, 4, new JLabel("Invitees (comma-separated):"));
    addComponent(gbc, 1, 4, inviteesField);
    addComponent(gbc, 0, 5, new JLabel("Scheduling Strategy:"));
    addComponent(gbc, 1, 5, strategyComboBox);
    scheduleButton.addActionListener(e -> scheduleEvent());
    addComponent(gbc, 0, 6, scheduleButton, 2);
  }

  // helper method to make adding components easier
  private void addComponent(GridBagConstraints gbc, int x, int y, Component component) {
    addComponent(gbc, x, y, component, 1);
  }

  // helper method to make adding components easier
  private void addComponent(GridBagConstraints gbc, int x, int y, Component component, int width) {
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = width;
    add(component, gbc);
  }

  // helper method for scheduling an event when the button is clicked
  private void scheduleEvent() {
    String name = eventNameField.getText();
    String location = eventLocationField.getText();
    boolean isOnline = isOnlineCheckbox.isSelected();
    int duration = Integer.parseInt(durationField.getText());
    String invitees = inviteesField.getText();
    String strategy = (String) strategyComboBox.getSelectedItem();
    String currentUser = controller.getCurrentUser();
    controller.scheduleEventWithStrategy(name, location, isOnline, duration, currentUser,
            invitees, strategy);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }


}
