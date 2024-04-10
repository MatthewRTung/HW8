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

import javax.swing.*;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;
import cs3500.planner.model.Schedule;
import cs3500.planner.view.CentralSystemFrame;
import cs3500.planner.view.EventFrame;
import cs3500.planner.view.ScheduleFrame;
import cs3500.planner.xml.XMLConfigurator;

public class ScheduleController implements IScheduleFeatures {
  private CentralSystem model;
  private CentralSystemFrame view;
  private String currentUser;


  public ScheduleController(CentralSystemFrame view) {
    this.view = view;
    //this.view.setController(this);
  }

  public void setCurrentUser(String userId) {
    this.currentUser = userId;
  }

  public String getCurrentUser() {
    return currentUser;
  }


  @Override
  public void loadXMLAction(File file) {
    XMLConfigurator configurator = new XMLConfigurator();
    try {
      String userId = configurator.readScheduleUserId(file.getAbsolutePath());
      List<Event> events = configurator.readXMLFile(file.getAbsolutePath());
      if (!model.getUserName().contains(userId)) {
        model.addUser(userId);
      }
      for (Event event : events) {
        model.createEvent(userId, event);
      }
      view.updateView();
    } catch (Exception ex) {
      view.displayError("Failed to load the schedule from the XML file: " + ex.getMessage());
    }
  }

//  @Override
//  public void saveSchedules() {
//    JFileChooser fileChooser = new JFileChooser();
//    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//    int option = fileChooser.showSaveDialog(view);
//    if (option == JFileChooser.APPROVE_OPTION) {
//      File directory = fileChooser.getSelectedFile();
//      XMLConfigurator configurator = new XMLConfigurator();
//
//      try {
//        for (Map.Entry<String, Schedule> entry : model.getAllSchedules().entrySet()) {
//          String userId = entry.getKey();
//          Schedule schedule = entry.getValue();
//
//          File scheduleFile = new File(directory, userId + "_schedule.xml");
//
//          // Using XMLConfigurator to write each user's schedule to an XML file
//          configurator.writeXMLFile(schedule.getEvents(), scheduleFile.getAbsolutePath());
//        }
//        view.showMessage("Schedules saved successfully."); // Display success message
//      } catch (Exception e) {
//        view.displayError("Failed to save schedules: " + e.getMessage()); // Display error message
//      }
//    }
//  }

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
          for (Map.Entry<String, Schedule> entry : model.getAllSchedules().entrySet()) {
            String userId = entry.getKey();
            Schedule schedule = entry.getValue();
            File scheduleFile = new File(directory, userId + "_schedule.xml");

            if (!scheduleFile.exists() || confirmOverwrite(scheduleFile)) {
              configurator.writeXMLFile(schedule.getEvents(), scheduleFile.getAbsolutePath());
            }
          }
          view.showMessage("Schedules saved successfully."); // Display success message
        } catch (Exception e) {
          view.displayError("Failed to save schedules: " + e.getMessage()); // Display error message
        }
      } else {
        view.displayError("Invalid directory selected."); // Handle null or invalid directory selection.
      }
    }
  }

  private boolean confirmOverwrite(File file) {
    int dialogResult = JOptionPane.showConfirmDialog(view, "The file " + file.getName() + " already exists. Do you want to overwrite it?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
    return dialogResult == JOptionPane.YES_OPTION;
  }



  @Override
  public void createEvent(Event event) {
    boolean success = model.createEvent(event.getHostId(), event);
    if (success) {
      view.updateView();
    } else {
      view.displayError("Error creating event.");
    }
  }

  @Override
  public void modifyEvent(String userId, String eventId, Event updatedEvent) {
    boolean success = model.modifyEvent(userId, eventId, updatedEvent);
    if (success) {
      view.updateView();
    } else {
      view.displayError("Error modifying event.");
    }
  }

  @Override
  public void removeEvent(String userId, String eventId) {
    boolean success = model.deleteEvent(userId, eventId);
    if (success) {
      view.updateView();
    } else {
      view.displayError("Error deleting event.");
    }
  }


  @Override
  public void openEventFrame() {
    EventFrame eventFrame = new EventFrame(model);
    eventFrame.setController(this);
    eventFrame.setVisible(true);
  }

  // Add a method in ScheduleController to handle event creation from EventFrame
//  public void createEventFromFrame(Event eventDetails) {
//    if (eventDetails != null) {
//      boolean success = model.createEvent(eventDetails.getUserId(), eventDetails);
//      if (success) {
//        view.updateView(); // Reflect the new event in the view
//      } else {
//        view.displayError("Failed to create event. There might be a scheduling conflict.");
//      }
//    } else {
//      view.displayError("Event details are incomplete.");
//    }
//  }


  @Override
  public List<Event> switchUser(String userId) {
    return model.getEventsForUser(userId);
  }


  @Override
  public void launch(CentralSystem m) {
    this.model = m;
    this.view.setController(this);
    this.view.finalizeInitialization();
    this.view.updateView();
  }

  @Override
  public void openEventDetails(Event event, EventFrame currentFrame) {

  }

  public void addDropDown(JComboBox<String> dropDown) {
    for (String userName : model.getUserName()) {
      dropDown.addItem(userName);
    }
  }

  public void openScheduleFrame() {
    ScheduleFrame scheduleFrame = new ScheduleFrame(model);
    scheduleFrame.setController(this);
    scheduleFrame.setVisible(true);

  }

  @Override
  public void scheduleEventWithStrategy(String name, String location, boolean isOnline,
                                        int duration, String user, String invitees, String strategy) {
    String currentUser = getCurrentUser();
    LocalDateTime startTime = null;

    if ("Any time".equals(strategy)) {
      startTime = findFirstAvailableTime(currentUser, invitees, duration);
    } else if ("Work hours".equals(strategy)) {
      startTime = findFirstAvailableTimeDuringWorkHours(currentUser, invitees, duration);
    }

    if (startTime != null) {
      LocalDateTime endTime = startTime.plusMinutes(duration);
      Event newEvent = new Event(name, location, isOnline, startTime, endTime, false, currentUser);
      for (String invitee : Arrays.asList(invitees.split(", "))) {
        newEvent.addInvitee(invitee);
      }
      model.createEvent(currentUser, newEvent);
      view.updateView();
    } else {
      view.displayError("No available time slot found for the event.");
    }
  }

  private LocalDateTime findFirstAvailableTime(String currentUser, String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MIN);
    LocalDateTime endTime = currentTime.plusWeeks(1);

    while (currentTime.isBefore(endTime)) {
      if (isTimeSlotAvailable(currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(30);
    }
    return null;
  }

  private LocalDateTime findFirstAvailableTimeDuringWorkHours(String currentUser, String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY)).with(LocalTime.of(9, 0));
    LocalDateTime endTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

    while (currentTime.isBefore(endTime)) {
      if (currentTime.getHour() >= 9 && currentTime.getHour() < 17 && isTimeSlotAvailable(currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(30);
      if (currentTime.getHour() >= 17) {
        currentTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(9, 0));
      }
    }
    return null;
  }

  private boolean isTimeSlotAvailable(String currentUser, String invitees, LocalDateTime startTime, int duration) {
    List<String> allUsers = new ArrayList<>(Arrays.asList(invitees.split(", ")));
    if (!allUsers.contains(currentUser)) {
      allUsers.add(currentUser);
    }

    LocalDateTime endTime = startTime.plusMinutes(duration);
    for (String user : allUsers) {
      // Check if the user exists in the system before attempting to get their events
      if (!model.getUserName().contains(user)) {
        continue; // If the user does not exist, assume they are available
      }

      List<Event> userEvents = model.getEventsForUser(user);
      for (Event event : userEvents) {
        if (event.getStartTime().isBefore(endTime) && event.getEndTime().isAfter(startTime)) {
          return false; // Time slot is not available due to a conflicting event
        }
      }
    }
    return true; // Time slot is available for all users
  }



}


