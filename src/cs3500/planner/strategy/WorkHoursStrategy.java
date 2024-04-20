package cs3500.planner.strategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.ScheduleModel;

/**
 * WorkHoursStrategy is a scheduling strategy that finds the earliest available time slot
 * within the standard work hours (9:00 AM to 5:00 PM) from Monday to Friday, where all specified
 * users, including the current user, can participate for the specified duration.
 * If a time slot is not available within the current week's work hours, it continues searching
 * from the next Monday.
 */
public class WorkHoursStrategy implements SchedulingStrategy {
  private CentralSystemModel model;

  /**
   * Constructs a WorkHoursStrategy with the given CentralSystemModel.
   *
   * @param model the CentralSystemModel to use for scheduling
   */
  public WorkHoursStrategy(CentralSystemModel model) {
    this.model = model;
  }

  /**
   * Finds the earliest available start time within the standard work hours from Monday to Friday,
   * where all specified users, including the current user, can participate for the specified duration.
   * If a time slot is not available within the current week's work hours, it continues searching
   * from the next Monday.
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
    LocalDateTime endTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

    while (currentTime.isBefore(endTime)) {
      if (currentTime.getHour() >= 9 && currentTime.getHour() < 17 && isTimeSlotAvailable(
              currentUser, invitees, currentTime, duration)) {
        return currentTime;
      }
      currentTime = currentTime.plusMinutes(1);
      if (currentTime.getHour() >= 17) {
        currentTime = currentTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(
                LocalTime.of(9, 0));
      }
    }
    return null;
  }

  // helper method for determining whether the provided time stamp is open
  private boolean isTimeSlotAvailable(String currentUser, String invitees,
                                      LocalDateTime startTime, int duration) {
    List<String> allUsers = new ArrayList<>(Arrays.asList(invitees.split(", ")));
    if (!allUsers.contains(currentUser)) {
      allUsers.add(currentUser);
    }
    LocalDateTime endTime = startTime.plusMinutes(duration);
    for (String user : allUsers) {
      if (!model.getUserName().contains(user)) {
        continue;
      }
      ScheduleModel userSchedule = model.getUserSchedule(user);
      if (userSchedule != null && !userSchedule.isTimeSlotFree(startTime, endTime)) {
        return false;
      }
    }
    return true;
  }
}

