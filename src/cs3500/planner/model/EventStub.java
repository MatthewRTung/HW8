package cs3500.planner.model;

import java.time.LocalDateTime;

/**
 * EventStub is a stub implementation of the Event interface used for testing.
 */
public class EventStub extends Event {
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
  public EventStub(String name, String location, boolean isOnline, LocalDateTime startTime, LocalDateTime endTime, boolean isHybrid, String hostId) {
    super(name, location, isOnline, startTime, endTime, isHybrid, hostId);
  }
}
