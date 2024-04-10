package cs3500.planner.view;

import java.util.List;

import cs3500.planner.model.Event;

/**
 * Interface for the ScheduleView used to display the schedule.
=======

/**
 * The ScheduleView interface defines the essential method that any schedule view class
 * should implement to display a list of events in the planner application. It serves
 * as a contract for showing schedules in a structured format.
>>>>>>> 5dcc095b6bb7ce2ee355f4fd70988f8bd94280ef
 */
public interface ScheduleView {

  /**
<<<<<<< HEAD
   * Method used to display the schedule for different users.
   * @param events the list of events to be displayed
=======
   * Displays a list of events in the schedule view. This method is responsible for
   * presenting the events in a user-friendly manner, allowing users to view the
   * schedule at a glance. Implementing classes should define how the events are
   * formatted and displayed within the context of their specific user interface.
   *
   * @param events The list of events to be displayed in the schedule view. Each event
   *               contains details that should be presented to the user.
>>>>>>> 5dcc095b6bb7ce2ee355f4fd70988f8bd94280ef
   */
  void displaySchedule(List<Event> events);
}
