package cs3500.planner.view;

import java.util.List;

import cs3500.planner.model.Event;

/**
 * A textual view for the planner.
 */
public interface PlannerView {
  /**
   * Returns the textual representation of the schedule or system's state.
   * @return A string representing the current state of the schedule or system.
   */
  String toString();

  /**
   * Formats a list of events into a textual representation.
   * @param events The list of events to format.
   * @return A textual representation of the list of events.
   */
  String format(List<Event> events);
}


