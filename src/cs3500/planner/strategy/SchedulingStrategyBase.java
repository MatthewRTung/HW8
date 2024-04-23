package cs3500.planner.strategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.ScheduleModel;

/**
 * SchedulingStrategyBase class used to help with duplicate code.
 */
public class SchedulingStrategyBase implements SchedulingStrategy {
  protected final CentralSystemModel model;

  /**
   * Constructor for SchedulingStrategyBase.
   * @param model the model to be taken in.
   */
  public SchedulingStrategyBase(CentralSystemModel model) {
    this.model = model;
  }

  @Override
  public LocalDateTime findStartTime(String user, String invitees, int duration) {
    return null;
  }

  protected boolean timeSlotHelper(String currentUser, String invitees, LocalDateTime startTime,
                                   int duration) {
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
