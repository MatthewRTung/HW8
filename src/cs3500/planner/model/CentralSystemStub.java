package cs3500.planner.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CentralSystemStub is a stub implementation of the CentralSystem interface used for testing.
 */
public class CentralSystemStub extends CentralSystem {
  private List<String> users = new ArrayList<>();
  private Map<String, List<Event>> eventsByUser = new HashMap<>();
  private boolean loadSuccess;

  /**
   * Method that simulates a successful load of events.
   */
  public void simulateLoadEventsSuccess() {
    this.loadSuccess = true;
  }

  /**
   * Method that simulates a failed load of events.
   */
  public void simulateLoadEventsFailure() {
    this.loadSuccess = false;
  }

  /**
   * Method that simulates a successful load of users.
   * @param user The UserId for the new user.
   */
  public void addUser(String user) {
    users.add(user);
  }

  /**
   * Method that simulates getting a list of users.
   * @return A list of users.
   */
  public List<String> getUsers() {
    return new ArrayList<>(users);
  }

  /**
   * Method that simulates removing a user.
   * @param user The UserID of the user to remove.
   * @return True if the user was successfully removed, false otherwise.
   */
  public boolean removeUser(String user) {
    return users.remove(user);
  }

  /**
   * Simulates creating an event for a user.
   * @param userId The UserId of the user for whom the event is to be created.
   * @param event  The event to be added to the user's schedule.
   * @return True if the event was successfully added, false otherwise.
   */
  public boolean createEvent(String userId, Event event) {
    if (!eventsByUser.containsKey(userId)) {
      eventsByUser.put(userId, new ArrayList<>());
    }
    return eventsByUser.get(userId).add(event);
  }

  /**
   * Checks if a user is added to an event.
   * @param user The user to be added to the event.
   * @return True if the user was successfully added, false otherwise.
   */
  public boolean isUserAdded(String user) {
    return users.contains(user);
  }

  /**
   * Checks if an event is added to a user.
   * @param userId The UserId of the user for whom the event is to be modified.
   * @param event The event being checked
   * @return True if the event was successfully modified, false otherwise.
   */
  public boolean isEventAddedForUser(String userId, Event event) {
    return eventsByUser.getOrDefault(userId, Collections.emptyList()).contains(event);
  }
}