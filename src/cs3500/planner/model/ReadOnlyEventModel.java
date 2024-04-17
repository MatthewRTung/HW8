package cs3500.planner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Provides a read-only view of an event's details.
 */
public interface ReadOnlyEventModel {

  /**
   * Gets the name of the event.
   * @return the name of the event.
   */
  String getEventName();

  /**
   * Gets the start time of the event.
   * @return the start time of the event.
   */
  LocalDateTime getStartTime();

  /**
   * Gets the end time of the event.
   * @return the end time of the event.
   */
  LocalDateTime getEndTime();


  public String getName();


  public boolean isOnline();


  public String getLocation();



  public String getHostID();
}
