package cs3500.planner.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.format.DateTimeParseException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;

import cs3500.planner.controller.IScheduleFeatures;
import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;

/**
 * The GUI that creates the event frame for users to input event details.
 */
public class EventFrame extends JFrame implements EventView {
  private IScheduleFeatures controller;
  private CentralSystemFrame frame;
  private Event currentEvent;

  private JTextField eventNameField;
  private JTextField eventLocationField;
  private JCheckBox isOnlineCheckbox;
  private JComboBox<String> startingDayComboBox;
  private JComboBox<String> endingDayComboBox;
  private JTextField startingTimeField;
  private JTextField endingTimeField;
  private JList<String> userList;
  private JButton removeButton;
  private JButton modifyButton;
  private JButton createButton;

  /**
   * Constructor for EventFrame to initialize the fields for the class.
   */
  public EventFrame(CentralSystem centralSystem) {
    super("Event Planner");
    removeButton = new JButton("Remove Event");
    modifyButton = new JButton("Modify Event");
    createButton = new JButton("Create Event");
    modifyButton.addActionListener(e -> modifyEvent());
    removeButton.addActionListener(e -> removeEvent());
    createButton.addActionListener(e -> createEvent());
    initializeComponents();
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.pack();
    this.setVisible(true);
  }

  /**
   * Sets the controller.
   * @param controller to be set.
   */
  public void setController(IScheduleFeatures controller) {
    this.controller = controller;
  }

  //helper method to initialize the components
  private void initializeComponents() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(5, 5, 5, 5);
    initializeEventName(constraints);
    initializeEventLocation(constraints);
    initializeOnlineCheckbox(constraints);
    initializeStartingTime(constraints);
    initializeStartingDay(constraints);
    initializeEndingTime(constraints);
    initializeEndingDay(constraints);
    initializeUserList(constraints);
    initializeRemoveButton(constraints);
    initializeModifyButton(constraints);
    initializeCreateButton(constraints);
    initializeReset(constraints);
  }

  //helper method to add/modify an event in the system(not implemented yet)
  private void initializeEventName(GridBagConstraints constraints) {
    //Event Name
    eventNameField = new JTextField();
    constraints.gridx = 0;
    constraints.gridy = 0;
    this.add(new JLabel("Event Name:"), constraints);
    constraints.gridx = 1;
    constraints.gridwidth = 1;
    this.add(eventNameField, constraints);
  }

  //helper method to create the event location button
  private void initializeEventLocation(GridBagConstraints constraints) {
    //Location Label
    eventLocationField = new JTextField();
    constraints.gridx = 0;
    constraints.gridy = 1;
    constraints.gridwidth = 1;
    this.add(new JLabel("Location:"), constraints);
    constraints.gridx = 1;
    constraints.gridwidth = 1;
    this.add(eventLocationField, constraints);
  }

  //helper method to create the is online checkbox
  private void initializeOnlineCheckbox(GridBagConstraints constraints) {
    //Online Checkbox
    isOnlineCheckbox = new JCheckBox("Is Online");
    constraints.gridx = 0;
    constraints.gridy = 2;
    constraints.gridwidth = 4; // Span two columns
    this.add(isOnlineCheckbox, constraints);
  }

  //helper method to create the starting day box
  private void initializeStartingDay(GridBagConstraints constraints) {
    //Starting Day ComboBox
    String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    startingDayComboBox = new JComboBox<>(daysOfWeek);
    constraints.gridx = 0;
    constraints.gridy = 3;
    constraints.gridwidth = 1;
    this.add(new JLabel("Starting Day:"), constraints);
    constraints.gridx = 0;
    constraints.gridy = 4;
    this.add(startingDayComboBox, constraints);
  }

  //helper method to create the starting time box
  private void initializeStartingTime(GridBagConstraints constraints) {
    //Starting Time Field
    startingTimeField = new JTextField();
    constraints.gridx = 0;
    constraints.gridy = 5;
    constraints.gridwidth = 1;
    this.add(new JLabel("Starting Time:"), constraints);
    constraints.gridy = 6;
    this.add(startingTimeField, constraints);
  }

  //helper method to create the end day box
  private void initializeEndingDay(GridBagConstraints constraints) {
    //Ending Day ComboBox
    String[] daysOfWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
    endingDayComboBox = new JComboBox<>(daysOfWeek);
    constraints.gridx = 1;
    constraints.gridy = 3;
    this.add(new JLabel("Ending Day:"), constraints);
    constraints.gridx = 4;
    this.add(endingDayComboBox, constraints);
    constraints.gridx = 1;
    constraints.gridy = 4;
    this.add(endingDayComboBox, constraints);
  }

  //helper method to create the end time box
  private void initializeEndingTime(GridBagConstraints constraints) {
    //Ending Time Field
    endingTimeField = new JTextField();
    constraints.gridx = 1;
    constraints.gridy = 5;
    constraints.gridwidth = 1;
    this.add(new JLabel("Ending Time:"), constraints);
    constraints.gridy = 6;
    this.add(endingTimeField, constraints);
  }

  //helper method to create the list of invited useres
  private void initializeUserList(GridBagConstraints constraints) {
    //Users List
    userList = new JList<>(new DefaultListModel<>());
    userList.setVisibleRowCount(4);
    JScrollPane userListScrollPane = new JScrollPane(userList);
    constraints.gridx = 0;
    constraints.gridy = 8;
    constraints.gridwidth = 3; // Span four columns for list
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weightx = 1.0;
    constraints.weighty = 1.0;
    this.add(userListScrollPane, constraints);
    constraints.gridx = 0;
    constraints.gridy = 7;
    constraints.gridwidth = 2;
    this.add(new JLabel("Available Users:"), constraints);
  }

  //helper method to reset the frame
  private void initializeReset(GridBagConstraints constraints) {
    //Reset
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.weighty = 0;
  }

  //helper method to add/modify button
  private void initializeModifyButton(GridBagConstraints constraints) {
    //Modify Button
    constraints.gridx = 1;
    constraints.gridy = 9;
    constraints.gridwidth = 1;
    this.add(modifyButton, constraints);
  }

  //helper method to remove button
  private void initializeRemoveButton(GridBagConstraints constraints) {
    //Remove Button
    constraints.gridx = 0;
    constraints.gridy = 9;
    constraints.gridwidth = 1;
    this.add(removeButton, constraints);
  }

  private void initializeCreateButton(GridBagConstraints constraints) {
    //Remove Button
    constraints.gridx = 2;
    constraints.gridy = 9;
    constraints.gridwidth = 1;
    this.add(createButton, constraints);
  }

  // 1. Add a method to set the current event
  public void setCurrentEvent(Event event) {
    this.currentEvent = event;
    setEventDetails(event); // Populate the frame with the event details
  }

  private void createEvent() {
    if (validateInput()) {
      Event event = gatherEventDetails();
      controller.createEvent(event);
    } else {
      displayError("Please fill in all required fields.");
    }
  }

  private void modifyEvent() {
    if (validateInput() && currentEvent != null) {
      Event updatedEvent = gatherEventDetails();
      controller.modifyEvent(currentEvent.getHostId(), currentEvent.getName(), updatedEvent);
    } else {
      displayError("Please fill in all required fields.");
    }
  }

  private void removeEvent() {
    if (currentEvent != null) {
      controller.removeEvent(currentEvent.getHostId(), currentEvent.getName());
    } else {
      displayError("No event selected to remove.");
    }
  }

  private Event gatherEventDetails() {
    String name = eventNameField.getText();
    String location = eventLocationField.getText();
    boolean isOnline = isOnlineCheckbox.isSelected();

    // Define the expected time format
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    // Get the current date to combine with the time
    LocalDate currentDate = LocalDate.now();

    // Initialize start and end times to null
    LocalDateTime startTime = null;
    LocalDateTime endTime = null;

    try {
      // Combine the current date with the parsed time
      LocalTime parsedStartTime = LocalTime.parse(startingTimeField.getText(), timeFormatter);
      LocalTime parsedEndTime = LocalTime.parse(endingTimeField.getText(), timeFormatter);
      startTime = LocalDateTime.of(currentDate, parsedStartTime);
      endTime = LocalDateTime.of(currentDate, parsedEndTime);
    } catch (DateTimeParseException e) {
      // Handle incorrect format
      JOptionPane.showMessageDialog(this, "Invalid time format. Please use 'HH:mm'.",
              "Format Error", JOptionPane.ERROR_MESSAGE);
      return null; // Return null or handle this scenario appropriately
    }

    // Assuming the Event constructor takes the user ID as the last parameter
    return new Event(name, location, isOnline, startTime, endTime, false, controller.getCurrentUser());
  }



  //helper method to ensure that we have valid inputs
  private boolean validateInput() {
    return !eventNameField.getText().trim().isEmpty() &&
            !startingTimeField.getText().trim().isEmpty() &&
            !endingTimeField.getText().trim().isEmpty();
  }

  //helper method to get the event details
  public String getEventDetails() {
    String result = "";
    result += eventNameField.getText().trim() + " ";
    result += eventLocationField.getText().trim() + " ";
    result += isOnlineCheckbox.isSelected() + " ";
    result += startingDayComboBox.getSelectedItem() + " ";
    result += startingTimeField.getText().trim() + " ";
    result += endingDayComboBox.getSelectedItem() + " ";
    result += endingTimeField.getText().trim();
    return result;
  }


  @Override
  public void setEventDetails(Event event) {
    eventNameField.setText(event.getName());
    eventLocationField.setText(event.getLocation());
    isOnlineCheckbox.setSelected(event.isOnline());
    startingDayComboBox.setSelectedItem(event.getStartTime().getDayOfWeek().toString());
    startingTimeField.setText(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    endingDayComboBox.setSelectedItem(event.getEndTime().getDayOfWeek().toString());
    endingTimeField.setText(event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (String user : event.getInvitees()) {
      listModel.addElement(user);
    }
    userList.setModel(listModel);
    userList.setSelectedValue(event.getHostId(), true);
  }

  @Override
  public void displayError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }


}