package cs3500.planner.strategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import cs3500.planner.model.CentralSystemModel;

/**
 * AnyTimeStrategy is a scheduling strategy that finds the earliest available time slot
 * within the next week when all specified users, including the current user, can participate.
 * It searches for a time slot in minute increments starting from the current time and progresses
 * through the next week.
 */
public class AnyTimeStrategy extends SchedulingStrategyBase {

  /**
   * Constructs an AnyTimeStrategy with the given CentralSystemModel.
   *
   * @param model the CentralSystemModel to use for scheduling
   */
  public AnyTimeStrategy(CentralSystemModel model) {
    super(model);
  }

  /**
   * Finds the earliest available start time within the next week where all specified users,
   * including the current user, can participate for the specified duration.
   *
   * @param currentUser the name of the current user
   * @param invitees    a comma-separated string of names of users to include in the scheduling
   * @param duration    the duration of the event in minutes
   * @return the LocalDateTime representing the start time of the available slot, or null if
   *         no such slot is found within the next week
   */
  @Override
  public LocalDateTime findStartTime(String currentUser, String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(
            DayOfWeek.SATURDAY)).with(LocalTime.MIN);
    LocalDateTime endTime = currentTime.plusWeeks(1);
    while (currentTime.isBefore(endTime)) {
      if (isTimeSlotAvailable(currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(1);
    }
    return null;
  }

  // helper method for determining whether the provided time stamp is open
  protected boolean isTimeSlotAvailable(String currentUser, String invitees,
                                      LocalDateTime startTime, int duration) {
    return timeSlotHelper(currentUser, invitees, startTime, duration);
  }
}

