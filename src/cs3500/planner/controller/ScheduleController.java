package cs3500.planner.controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;
import cs3500.planner.model.ScheduleModel;
import cs3500.planner.view.CentralSystemFrame;
import cs3500.planner.view.EventFrame;
import cs3500.planner.view.ScheduleFrame;
import cs3500.planner.xml.XMLConfigurator;


/**
 * Class for implementing the actions and features for scheduling within the application.
 * This class implements methods for interacting with the central system model, managing events,
 * and updating the view.
 */
public class ScheduleController implements IScheduleFeatures {
  private CentralSystemModel model;
  private final CentralSystemFrame view;
  private String currentUser;
  private EventFrame currentFrame;
  private String schedulingStrategy;


  /**
   * Constructs a ScheduleController with the given view.
   * @param view the current CentralSystemFrame.
   */
  public ScheduleController(CentralSystemFrame view) {
    this.view = view;
  }

  /**
   * Sets the current user in the application.
   *
   * @param userId The user ID to be set as the current user.
   */
  public void setCurrentUser(String userId) {
    this.currentUser = userId;
  }

  /**
   * Gets the ID of the current user.
   *
   * @return The current user's ID.
   */
  public String getCurrentUser() {
    return currentUser;
  }

  /**
   * Sets the default scheduling strategy for the controller.
   * @param schedulingStrategy The scheduling strategy to be used ("anytime" or "workhours").
   */
  public void setDefaultSchedulingStrategy(String schedulingStrategy) {
    this.schedulingStrategy = schedulingStrategy;
  }

  /**
   * Loads events from an XML file into the application.
   *
   * @param file The XML file containing events to be loaded.
   */
  @Override
  public void loadXMLAction(File file) {
    XMLConfigurator configurator = new XMLConfigurator();
    try {
      String userId = configurator.readScheduleUserId(file.getAbsolutePath());
      List<EventModel> events = configurator.readXMLFile(file.getAbsolutePath());
      if (!model.getUserName().contains(userId)) {
        model.addUser(userId);
      }
      for (EventModel event : events) {
        model.createEvent(userId, event);
      }
      view.updateView();
    } catch (Exception ex) {
      view.displayError("Failed to load the schedule from the XML file: " + ex.getMessage());
    }
  }

  /**
   * Saves the current state of all schedules in the application.
   */
  @Override
  public void saveSchedules() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showSaveDialog(view);
    if (option == JFileChooser.APPROVE_OPTION) {
      File directory = fileChooser.getSelectedFile();
      if (directory != null && directory.isDirectory()) {
        XMLConfigurator configurator = new XMLConfigurator();

        try {
          for (Map.Entry<String, ScheduleModel> entry : model.getAllSchedules().entrySet()) {
            String userId = entry.getKey();
            ScheduleModel schedule = entry.getValue();
            File scheduleFile = new File(directory, userId + "_schedule.xml");

            if (!scheduleFile.exists() || confirmOverwrite(scheduleFile)) {
              configurator.writeXMLFile(schedule.getEvents(), scheduleFile.getAbsolutePath());
            }
          }
          view.showMessage("Schedules saved successfully.");
        } catch (Exception e) {
          view.displayError("Failed to save schedules: " + e.getMessage());
        }
      } else {
        view.displayError("Invalid directory selected.");
      }
    }
  }

  /**
   * Creates a new event and adds it to the schedule.
   *
   * @param event The event to be added.
   */
  @Override
  public void createEvent(EventModel event) {
    boolean success = model.createEvent(getCurrentUser(), event);
    if (success) {
      view.updateView();
    } else {
      view.displayError("Error creating event.");
    }
  }

  /**
   * Modifies an existing event in the user's schedule.
   *
   * @param userId The ID of the user whose event is being modified.
   * @param eventId The ID of the event to be modified.
   * @param updatedEvent The new event details to replace the existing event.
   */
  @Override
  public void modifyEvent(String userId, String eventId, EventModel updatedEvent) {
    boolean success = model.modifyEvent(userId, eventId, updatedEvent);
    if (success) {
      view.updateView();
    } else {
      view.displayError("Error modifying event.");
    }
  }

  /**
   * Removes an event from the user's schedule.
   *
   * @param userId The ID of the user whose event is being removed.
   * @param eventId The ID of the event to be removed.
   */
  @Override
  public boolean removeEvent(String userId, String eventId) {
    boolean success = model.deleteEvent(userId, eventId);
    if (success) {
      view.updateView();
      return true;
    } else {
      view.displayError("Error deleting event.");
      return false;
    }
  }


  /**
   * Opens the frame for creating a new event.
   */
  @Override
  public void openEventFrame() {
    EventFrame eventFrame = new EventFrame(); //HERE
    eventFrame.setController(this);
    eventFrame.setVisible(true);
  }


  /**
   * Switches the current view to show the schedule of a different user.
   *
   * @param userId The ID of the user whose schedule should be displayed.
   * @return A list of events for the specified user.
   */
  @Override
  public List<EventModel> switchUser(String userId) {
    return model.getEventsForUser(userId);
  }


  /**
   * Launches the application with a given central system model.
   *
   * @param m The central system model to be used in the application.
   */
  @Override
  public void launch(CentralSystem m) {
    this.model = m;
    this.view.setController(this);
    this.view.finalizeInitialization();
    this.view.updateView();
  }

  /**
   * Opens the event details frame for a specific event.
   *
   * @param event The event for which the details should be displayed.
   */
  @Override
  public void openEventDetails(EventModel event) {
    if (currentFrame != null && currentFrame.isVisible()) {
      currentFrame.dispose();
    }
    currentFrame = new EventFrame(); // HERE
    currentFrame.setEventDetails(event);
    currentFrame.setVisible(true);
    currentFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        currentFrame = null;
      }
    });
  }

  /**
   * Adds a dropdown component to the view, typically for user selection.
   *
   * @param dropDown The JComboBox to be added to the view.
   */
  @Override
  public void addDropDown(JComboBox<String> dropDown) {
    for (String userName : model.getUserName()) {
      dropDown.addItem(userName);
    }
  }

  /**
   * Opens the frame for scheduling an event using a specified strategy.
   */
  @Override
  public void openScheduleFrame() {
    ScheduleFrame scheduleFrame = new ScheduleFrame(model);
    scheduleFrame.setController(this);
    scheduleFrame.setVisible(true);
  }

  /**
   * Schedules an event with a specified strategy.
   *
   * @param name The name of the event.
   * @param location The location of the event.
   * @param isOnline Indicates whether the event is online.
   * @param duration The duration of the event.
   * @param user The user ID of the event creator.
   * @param invitees A comma-separated list of invitees.
   * @param strategy The scheduling strategy to use.
   */
  @Override
  public void scheduleEventWithStrategy(String name, String location, boolean isOnline,
                                        int duration, String user,
                                        String invitees, String strategy) {
    if (strategy == null || strategy.isEmpty()) {
      strategy = this.schedulingStrategy;
    }
    String currentUser = getCurrentUser();
    LocalDateTime startTime = null;
    if ("Any time".equals(strategy) || "anytime".equals(strategy)) {
      startTime = findFirstAvailableTime(currentUser, invitees, duration);
    } else if ("Work hours".equals(strategy) || "workhours".equals(strategy)) {
      startTime = findFirstAvailableTimeDuringWorkHours(currentUser, invitees, duration);
    }
    if (startTime != null) {
      LocalDateTime endTime = startTime.plusMinutes(duration);
      Event newEvent = new Event(name, location, isOnline, startTime, endTime, false, currentUser);
      for (String invitee : invitees.split(", ")) {
        newEvent.addInvitee(invitee);
      }
      model.createEvent(currentUser, newEvent);
      view.updateView();
    } else {
      view.displayError("No available time slot found for the event.");
    }
  }

  //displays a message to user asking for approval of overriding
  private boolean confirmOverwrite(File file) {
    int dialogResult = JOptionPane.showConfirmDialog(view, "The file " + file.getName() +
            " already exists. Do you want to overwrite it?",
            "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
    return dialogResult == JOptionPane.YES_OPTION;
  }

  //helper method for determining the first available time using the given information
  private LocalDateTime findFirstAvailableTime(String currentUser, String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MIN);
    LocalDateTime endTime = currentTime.plusWeeks(1);
    System.out.println("current time: " + currentTime + " endTime: " + endTime);

    while (currentTime.isBefore(endTime)) {
      if (isTimeSlotAvailable(currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(1);
    }
    return null;
  }

  //helper for determining the first available time for work hours using the given information
  private LocalDateTime findFirstAvailableTimeDuringWorkHours(String currentUser,
                                                              String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(
            DayOfWeek.MONDAY)).with(LocalTime.of(9, 0));
    LocalDateTime endTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

    while (currentTime.isBefore(endTime)) {
      if (currentTime.getHour() >= 9 && currentTime.getHour() < 17 && isTimeSlotAvailable(
              currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(1);
      if (currentTime.getHour() >= 17) {
        currentTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(
                LocalTime.of(9, 0));
      }
    }
    return null;
  }

  //helper for determining whether the starting time and duration is available for the invitees
  private boolean isTimeSlotAvailable(String currentUser, String invitees,
                                      LocalDateTime startTime, int duration) {
    List<String> allUsers = new ArrayList<>(Arrays.asList(invitees.split(", ")));
    if (!allUsers.contains(currentUser)) {
      allUsers.add(currentUser);
    }
    LocalDateTime endTime = startTime.plusMinutes(duration);
    for (String user : allUsers) {
      if (!model.getUserName().contains(user)) {
        continue;
      }
      ScheduleModel userSchedule = model.getUserSchedule(user);
      if (userSchedule != null && !userSchedule.isTimeSlotFree(startTime, endTime)) {
        return false;
      }
    }
    return true;
  }
}


