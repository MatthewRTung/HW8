package cs3500.planner.strategy;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.ScheduleModel;

/**
 * AnyTimeStrategy is a scheduling strategy that finds the earliest available time slot
 * within the next week where all specified users, including the current user, can participate.
 * It searches for a time slot in minute increments starting from the current time and progresses
 * through the next week.
 */
public class AnyTimeStrategy implements SchedulingStrategy {
  private CentralSystemModel model;

  /**
   * Constructs an AnyTimeStrategy with the given CentralSystemModel.
   *
   * @param model the CentralSystemModel to use for scheduling
   */
  public AnyTimeStrategy(CentralSystemModel model) {
    this.model = model;
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
    LocalDateTime currentTime = LocalDateTime.now().with(DayOfWeek.SUNDAY).with(LocalTime.MIN);
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

