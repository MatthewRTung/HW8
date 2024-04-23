package cs3500.planner.view;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

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
import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;

/**
 * The GUI that creates the event frame for users to input event details.
 */
public class EventFrame extends JFrame implements EventView {
  private IScheduleFeatures controller;
  private EventModel currentEvent;

  private JTextField eventNameField;
  private JTextField eventLocationField;
  private JCheckBox isOnlineCheckbox;
  private JComboBox<DayOfWeek> startingDayComboBox;
  private JComboBox<DayOfWeek> endingDayComboBox;
  private JTextField startingTimeField;
  private JTextField endingTimeField;
  private JList<String> userList;
  private final JButton removeButton;
  private final JButton modifyButton;
  private final JButton createButton;

  /**
   * Constructor for EventFrame to initialize the fields for the class.
   */
  public EventFrame() {
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

  /**
   * Method used to set the details of the event for each event.
   * @param event The event to set the details of.
   */
  @Override
  public void setEventDetails(EventModel event) {
    eventNameField.setText(event.getName());
    eventLocationField.setText(event.getLocation());
    isOnlineCheckbox.setSelected(event.isOnline());
    DayOfWeek startDay = event.getStartTime().getDayOfWeek();
    startingDayComboBox.setSelectedItem(startDay);
    startingTimeField.setText(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    DayOfWeek endDay = event.getEndTime().getDayOfWeek();
    endingDayComboBox.setSelectedItem(endDay);
    endingTimeField.setText(event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
    DefaultListModel<String> listModel = new DefaultListModel<>();
    for (String user : event.getInvitees()) {
      listModel.addElement(user);
    }
    userList.setModel(listModel);
    userList.setSelectedValue(event.getHostId(), true);
  }

  /**
   * Method used to display if there is an error with inputting event details.
   * @param message The error message to display.
   */
  @Override
  public void displayError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
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
    DayOfWeek[] daysOfWeek = DayOfWeek.values();
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
    DayOfWeek[] daysOfWeek = DayOfWeek.values();
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
    constraints.gridwidth = 2; // Span four columns for list
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

  //method to set the current event
  public void setCurrentEvent(Event event) {
    this.currentEvent = event;
    setEventDetails(event);
  }

  // helper method for creating an event
  private void createEvent() {
    if (validateInput()) {
      Event event = gatherEventDetails();
      controller.createEvent(event);
    } else {
      displayError("Please fill in all required fields.");
    }
  }

  // helper method for modifying an event
  private void modifyEvent() {
    if (validateInput() && currentEvent != null) {
      Event updatedEvent = gatherEventDetails();
      controller.modifyEvent(currentEvent.getHostId(), currentEvent.getName(), updatedEvent);
    } else {
      displayError("Please fill in all required fields.");
    }
  }

  // helper method for removing an event
  private void removeEvent() {
    if (currentEvent != null) {
      controller.removeEvent(currentEvent.getHostId(), currentEvent.getName());
    } else {
      displayError("No event selected to remove.");
    }
  }

  // helper method for getting the event details of the current inputs of the current event
  private Event gatherEventDetails() {
    String name = eventNameField.getText();
    String location = eventLocationField.getText();
    boolean isOnline = isOnlineCheckbox.isSelected();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    DayOfWeek startDay = (DayOfWeek) startingDayComboBox.getSelectedItem();
    DayOfWeek endDay = (DayOfWeek) endingDayComboBox.getSelectedItem();
    LocalDate startDate = adjustDateToSelectedDayOfWeek(LocalDate.of(1,1,1), startDay);
    LocalDate endDate = adjustDateToSelectedDayOfWeek(LocalDate.of(1, 1, 1), endDay);
    LocalDateTime startTime = null;
    LocalDateTime endTime = null;
    try {
      LocalTime parsedStartTime = LocalTime.parse(startingTimeField.getText(), timeFormatter);
      LocalTime parsedEndTime = LocalTime.parse(endingTimeField.getText(), timeFormatter);
      startTime = LocalDateTime.of(startDate, parsedStartTime);
      endTime = LocalDateTime.of(endDate, parsedEndTime);
    } catch (DateTimeParseException e) {
      JOptionPane.showMessageDialog(this, "Invalid time format. Please use 'HH:mm'.",
              "Format Error", JOptionPane.ERROR_MESSAGE);
      return null;
    }
    return new Event(name, location, isOnline, startTime, endTime, false,
            controller.getCurrentUser());
  }

  // Helper method to adjust the date to the selected day of the week
  private LocalDate adjustDateToSelectedDayOfWeek(LocalDate date, DayOfWeek dayOfWeek) {
    if (dayOfWeek != null) {
      return date.with(TemporalAdjusters.nextOrSame(dayOfWeek));
    }
    return date;
  }

  //helper method to ensure that we have valid inputs
  private boolean validateInput() {
    return !eventNameField.getText().trim().isEmpty() &&
            !startingTimeField.getText().trim().isEmpty() &&
            !endingTimeField.getText().trim().isEmpty();
  }
}