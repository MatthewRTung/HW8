package cs3500.planner.model;

import java.time.LocalDateTime;

import cs3500.planner.provider.model.Day;
import cs3500.planner.provider.model.ITime;

/**
 * TimeAdapter is an adapter class that adapts a LocalDateTime object to provide compatibility
 * with the planning system by implementing the ITime interface.
 */
public class TimeAdapter implements ITime {

  private final LocalDateTime time;

  /**
   * Constructs a TimeAdapter with the given LocalDateTime object to adapt.
   *
   * @param time the LocalDateTime object to adapt
   */
  public TimeAdapter(LocalDateTime time) {
    this.time = time;
  }

  /**
   * Returns the hour of the time represented by this TimeAdapter.
   *
   * @return the hour component of the time
   */
  @Override
  public int getHours() {
    return time.getHour();
  }

  /**
   * Returns the minute of the time represented by this TimeAdapter.
   *
   * @return the minute component of the time
   */
  @Override
  public int getMinutes() {
    return time.getMinute();
  }

  /**
   * Returns the day of the week of the time represented by this TimeAdapter.
   *
   * @return the day of the week
   */
  @Override
  public Day getDay() {
    return Day.valueOf(time.getDayOfWeek().name());
  }
}
