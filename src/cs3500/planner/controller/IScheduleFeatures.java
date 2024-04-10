package cs3500.planner.controller;

import java.io.File;
import java.util.List;

import javax.swing.*;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;
import cs3500.planner.view.EventFrame;

public interface IScheduleFeatures {
  void loadXMLAction(File file);
  void saveSchedules();
  void createEvent(Event event);
  void modifyEvent(String userId, String eventId, Event event);
  void removeEvent(String userId, String eventId);
  void openEventFrame();
  List<Event> switchUser(String userID);
  void launch(CentralSystem m);
  void openEventDetails(Event event, EventFrame frame);
  void addDropDown(JComboBox<String> dropDown);
  void openScheduleFrame();
  void scheduleEventWithStrategy(String name, String location, boolean isOnline, int duartion,
                                 String user, String invitees, String strategy);
  String getCurrentUser();
  void setCurrentUser(String user);
}

