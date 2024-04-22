package cs3500.planner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event class that represents a single event in the planner.
 */
public class Event implements EventModel, Comparable<EventModel> {
  private final String name;
  private final LocalDateTime startTime;
  private final LocalDateTime endTime;
  private final String location;
  private final boolean isOnline;
  private final List<String> userList;
  private final boolean isHybrid;
  private final String hostId;

  /**
   * Constructor for the Event class.
   * @param name name of the event
   * @param location location of the event
   * @param isOnline whether the event is online or not
   * @param startTime the start time of the event
   * @param endTime the end time of the event
   */
  public Event(String name, String location, boolean isOnline, LocalDateTime startTime,
               LocalDateTime endTime, boolean isHybrid, String hostId) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Event name cannot be null or empty.");
    }
    if (location == null || (!isOnline && location.trim().isEmpty())) {
      throw new IllegalArgumentException("Event location cannot be null or empty " +
              "if the event is online.");
    }
    if (startTime == null) {
      throw new IllegalArgumentException("Event start time cannot be null.");
    }
    //Class invariant
    //Here we are checking that the start time is before the end time. This makes sure that the
    //class invariant is maintained when the object is created.
    //The check ensures that the object cannot be created with an invalid time range where the
    //end time is before the start time which enforces the class invariant and enforced in the
    //constructor.
    if (endTime == null || endTime.isBefore(startTime)) {
      throw new IllegalArgumentException("Event end time cannot be null or before the start time.");
    }
    this.name = name;
    this.location = location;
    this.isOnline = isOnline;
    this.startTime = startTime;
    this.endTime = endTime;
    this.userList = new ArrayList<>();
    this.isHybrid = isHybrid;
    this.hostId = hostId;
  }

  /**
   * Checks whether an event overlaps.
   * @param event the event to check for overlap
   * @return true or false based on whether the event overlaps.
   */

  @Override
  public boolean overlaps(EventModel event) {
    return !startTime.isAfter(event.getEndTime()) && !endTime.isBefore(event.getStartTime());
  }

  /**
   * Adds an invitee to the event.
   * @param userId the ID of the user to add
   */

  @Override
  public void addInvitee(String userId) {
    if (!userList.contains(userId)) {
      userList.add(userId);
    }
  }

  /**
   * Removes invitee from the event.
   * @param userId the ID of the user to remove
   */

  @Override
  public void removeInvitee(String userId) {
    userList.remove(userId);
  }

  /**
   * Gets the location of the event.
   * @return the name of the location of the event.
   */

  @Override
  public String getLocation() {
    if (isHybrid) {
      return "Hybrid: Online and at " + location;
    } else if (isOnline) {
      return "Online: " + location;
    } else {
      return location;
    }
  }

  /**
   * Gets the name of the event.
   * @return the name of the event.
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Gets the name of the event.
   * @return the name of the event.
   */

  @Override
  public String getEventName() {
    return name;
  }

  /**
   * Gets the start time of the event.
   * @return the start time of the event.
   */

  @Override
  public LocalDateTime getStartTime() {
    return startTime;
  }

  /**
   * Gets the end time of the event.
   * @return the end time of the event.
   */

  @Override
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * Gets the list of invitees for the event.
   * @return the list of invitees for the event.
   */

  @Override
  public List<String> getInvitees() {
    return new ArrayList<>(userList);
  }

  /**
   * Checks whether the event is online or not.
   * @return true if the event is online, false otherwise.
   */

  @Override
  public boolean isOnline() {
    return isOnline;
  }

  /**
   * Checks if the inputted user is invited.
   * @param userId the ID of the user to check
   * @return true if the user is invited, false otherwise.
   */

  @Override
  public boolean isUserInvited(String userId) {
    return userList.contains(userId);
  }

  /**
   * Checks whether the event is currently happening.
   * @return true if it is, false otherwise.
   */

  @Override
  public boolean isCurrentlyHappening() {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(startTime) && now.isBefore(endTime);
  }

  /**
   * Compares two start times.
   * @param other the event to compare with
   * @return 1 if the event is the same, 0 otherwise.
   */

  @Override
  public int compareTo(EventModel other) {
    return this.startTime.compareTo(other.getStartTime());
  }

  /**
   * Returns the host id.
   * @return a string representing the host id.
   */

  @Override
  public String getHostId() {
    return hostId;
  }

  //method to see if an event is both online and in person
  private boolean isHybrid() {
    return isHybrid;
  }
}