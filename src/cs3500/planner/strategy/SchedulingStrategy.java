package cs3500.planner.strategy;

import java.time.LocalDateTime;

/**
 * The {@code SchedulingStrategy} interface defines a common protocol for different scheduling
 * strategies. Implementations of this interface determine the availability of time slots based
 * on specific criteria and scheduling logic.
 * Implementing this interface allows the encapsulation of different algorithms for finding the
 * next available time slot, which can vary from finding any available time to finding one within
 * specific constraints like work hours.
 */
public interface SchedulingStrategy {
  /**
   * Determines the next available time slot for scheduling an event given the user, their
   * invitees, and the event's duration.
   *
   * @param user The user ID of the event organizer.
   * @param invitees A comma-separated list of invitee IDs for the event.
   * @param duration The duration of the event in minutes.
   * @return A {@code LocalDateTime} representing the start time of the next available time slot,
   *         or {@code null} if no suitable time slot is found.
   */
  LocalDateTime findStartTime(String user, String invitees, int duration);
}
