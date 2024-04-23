package cs3500.planner.view;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;
import cs3500.planner.model.ScheduleModel;

/**
 * A textual view for the planner in a specific format.
 */
public class PlannerTextView extends JFrame implements PlannerView {

  private final CentralSystem centralSystem;

  /**
   * Creates a new view for the planner.
   * @param centralSystem The central system to use.
   */
  public PlannerTextView(CentralSystem centralSystem) {
    this.centralSystem = centralSystem;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    Map<String, ScheduleModel> allSchedules = centralSystem.getAllSchedules();
    for (Map.Entry<String, ScheduleModel> entry : allSchedules.entrySet()) {
      String userId = entry.getKey();
      ScheduleModel schedule = entry.getValue();
      builder.append("User: ").append(userId).append("\n");
      builder.append(formatSchedule(schedule));
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Helper method to format a single user's schedule.
   * @param schedule The schedule to format.
   * @return A string representing the user's schedule.
   */
  private String formatSchedule(ScheduleModel schedule) {
    StringBuilder builder = new StringBuilder();
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    for (DayOfWeek day : DayOfWeek.values()) {
      List<EventModel> events = weeklyEvents.get(day);
      builder.append(day.toString()).append(":\n");
      for (EventModel event : events) {
        builder.append("\tname: ").append(event.getName()).append("\n");
        builder.append("\ttime: ").append(event.getStartTime()).append(" -> ")
                .append(event.getEndTime()).append("\n");
        builder.append("\tlocation: ").append(event.getLocation()).append("\n");
        builder.append("\tonline: ").append(event.isOnline() ? "true" : "false").append("\n");
        builder.append("\tinvitees: ").append(String.join(", ", event.getInvitees())).append("\n");
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  /**
   * Helper method to format a list of events.
   * @param events The list of events to format.
   * @return A string representing the list of events.
   */
  public String format(List<Event> events) {
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    StringBuilder output = new StringBuilder();
    output.append("User: \n");
    for (String day : days) {
      output.append(day).append(":\n");
      for (Event event : events) {
        if (event.getStartTime().getDayOfWeek().toString().equalsIgnoreCase(day)
                || event.getEndTime().getDayOfWeek().toString().equalsIgnoreCase(day)) {
          output.append("\tname: ").append(event.getName()).append("\n");
          output.append("\ttime: ").append(event.getStartTime().getDayOfWeek()).append(": ")
                  .append(event.getStartTime().toLocalTime()).append(" -> ")
                  .append(event.getEndTime().getDayOfWeek()).append(": ")
                  .append(event.getEndTime().toLocalTime()).append("\n");
          output.append("\tlocation: ").append(event.getLocation()).append("\n");
          output.append("\tonline: ").append(event.isOnline()).append("\n");
          output.append("\tinvitees: ");
          for (String user : event.getInvitees()) {
            output.append(user).append("\n\t\t");
          }
          output.append("\n");
        }
      }
    }
    return output.toString();
  }
}