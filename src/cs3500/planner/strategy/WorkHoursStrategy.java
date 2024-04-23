package cs3500.planner.strategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

import cs3500.planner.model.CentralSystemModel;

/**
 * WorkHoursStrategy is a scheduling strategy that finds the earliest available time slot
 * within the standard work hours (9:00 AM to 5:00 PM) from Monday to Friday, where all specified
 * users, including the current user, can participate for the specified duration.
 * If a time slot is not available within the current week's work hours, it continues searching
 * from the next Monday.
 */
public class WorkHoursStrategy extends SchedulingStrategyBase {

  /**
   * Constructs a WorkHoursStrategy with the given CentralSystemModel.
   *
   * @param model the CentralSystemModel to use for scheduling
   */
  public WorkHoursStrategy(CentralSystemModel model, DayOfWeek dayOfWeek) {
    super(model);
  }

  /**
   * Finds the earliest available start time within the standard work hours from Monday to Friday,
   * where all specified users, including the current user, can participate for the specified
   * duration. If a time slot is not available within the current week's work hours, it continues
   * searching from the next Monday.
   *
   * @param currentUser the name of the current user
   * @param invitees    a comma-separated string of names of users to include in the scheduling
   * @param duration    the duration of the event in minutes
   * @return the LocalDateTime representing the start time of the available slot, or null if
   *         no such slot is found within the standard work hours from Monday to Friday
   */
  @Override
  public LocalDateTime findStartTime(String currentUser, String invitees, int duration) {
    LocalDateTime currentTime = LocalDateTime.now().with(TemporalAdjusters.nextOrSame(
            DayOfWeek.MONDAY)).with(LocalTime.of(9, 0));
    //end of week
    LocalDateTime weekEndTime = currentTime.plusDays(4).with(LocalTime.of(17, 0));
    //loop over work hours
    while (currentTime.isBefore(weekEndTime)) {
      //checks within work hours
      if (currentTime.getHour() >= 9 && currentTime.getHour() < 17) {
        //checks if timeslot is available
        if (isTimeSlotAvailable(currentUser, invitees, currentTime, duration)) {
          return currentTime;
        }
      }
      //next min
      currentTime = currentTime.plusMinutes(1);
      //reset to next day at 9:00 am if after work hours or weekend
      if (currentTime.getHour() >= 17 || currentTime.getDayOfWeek() == DayOfWeek.SATURDAY) {
        currentTime = currentTime.plusDays(1).with(LocalTime.of(9, 0));
      }
    }
    //no time found in current week
    return null;
  }

  // helper method for determining whether the provided time stamp is open
  private boolean isTimeSlotAvailable(String currentUser, String invitees,
                                      LocalDateTime startTime, int duration) {
    return timeSlotHelper(currentUser, invitees, startTime, duration);
  }
}

