package cs3500.planner.view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import cs3500.planner.controller.IScheduleFeatures;
import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.Event;

public class ScheduleFrame extends JFrame implements ScheduleView {
  private JTextField eventNameField;
  private JTextField eventLocationField;
  private JCheckBox isOnlineCheckbox;
  private JTextField durationField;
  private JTextField inviteesField;
  private JButton scheduleButton;
  private IScheduleFeatures controller;
  private JComboBox<String> strategyComboBox;


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
    scheduleButton.addActionListener(e -> scheduleEvent());
    strategyComboBox = new JComboBox<>(new String[]{"Any time", "Work hours"});
    layoutComponents();
  }

  public void setController(IScheduleFeatures controller) {
    this.controller = controller;
  }

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

  private void addComponent(GridBagConstraints gbc, int x, int y, Component component) {
    addComponent(gbc, x, y, component, 1);
  }

  private void addComponent(GridBagConstraints gbc, int x, int y, Component component, int width) {
    gbc.gridx = x;
    gbc.gridy = y;
    gbc.gridwidth = width;
    add(component, gbc);
  }

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
  }


  @Override
  public void displaySchedule(List<Event> events) {

  }


}
