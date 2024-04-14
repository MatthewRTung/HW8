package cs3500.planner.model;

import java.time.LocalDateTime;

/**
 * EventStub is a stub implementation of the Event interface used for testing.
 */
public class EventStub extends Event {

  private String name;
  private String location;
  private boolean isOnline;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private boolean isHybrid;
  private String hostId;

  /**
   * Constructor for EventStub.
   * @param name The name of the event.
   * @param location The location of the event.
   * @param isOnline True if the event is online, false otherwise.
   * @param startTime The start time of the event.
   * @param endTime The end time of the event.
   * @param isHybrid True if the event is a hybrid, false otherwise.
   * @param hostId The id of the host of the event.
   */
  public EventStub(String name, String location, boolean isOnline, LocalDateTime startTime,
                   LocalDateTime endTime, boolean isHybrid, String hostId) {
    super(name, location, isOnline, startTime, endTime, isHybrid, hostId);
    this.name = name;
    this.location = location;
    this.isOnline = isOnline;
    this.startTime = startTime;
    this.endTime = endTime;
    this.isHybrid = isHybrid;
    this.hostId = hostId;
  }

  /**
   * Gets the name of the event.
   * @return the name of the event.
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the location of the event.
   * @return the name of the location of the event.
   */
  public String getLocation() {
    return location;
  }

  /**
   * Checks whether the event is online or not.
   * @return true if the event is online, false otherwise.
   */
  public boolean isOnline() {
    return isOnline;
  }

  /**
   * Gets the start time of the event.
   * @return the start time of the event.
   */
  public LocalDateTime getStartTime() {
    return startTime;
  }

  /**
   * Gets the end time of the event.
   * @return the end time of the event.
   */
  public LocalDateTime getEndTime() {
    return endTime;
  }

  /**
   * Checks whether the event is online and in person.
   * @return true if the event is both online and in person, false otherwise.
   */
  public boolean isHybrid() {
    return isHybrid;
  }

  /**
   * Returns the host id.
   * @return a string representing the host id.
   */
  public String getHostId() {
    return hostId;
  }

  /**
   * Sets the name of the event.
   * @param name the new name of the event.
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets the location of the event.
   * @param location the new location of the event.
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * Sets whether the event is online or not.
   * @param online true if the event is online, false otherwise.
   */
  public void setOnline(boolean online) {
    isOnline = online;
  }

  /**
   * Sets the start time of the event.
   * @param startTime the new start time of the event.
   */
  public void setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
  }

  /**
   * Sets the end time of the event.
   * @param endTime the new end time of the event.
   */
  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  /**
   * Sets whether the event is online and in person.
   * @param hybrid true if the event is both online and in person, false otherwise.
   */
  public void setHybrid(boolean hybrid) {
    isHybrid = hybrid;
  }

  /**
   * Sets the host id.
   * @param hostId a string representing the host id.
   */
  public void setHostId(String hostId) {
    this.hostId = hostId;
  }
}
