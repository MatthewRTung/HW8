package cs3500.planner.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import cs3500.planner.provider.model.Day;
import cs3500.planner.provider.model.IEvent;
import cs3500.planner.provider.model.ILocation;
import cs3500.planner.provider.model.ITime;
import cs3500.planner.provider.model.IUser;

/**
 * EventAdapter is an adapter class that adapts an EventModel object to provide compatibility
 * with the planning system by implementing the IEvent interface.
 */
public class EventAdapter implements IEvent {
  private final EventModel event;
  private final IUser creator;
  private final ArrayList<IUser> inviteeUsers;

  /**
   * Constructs an EventAdapter with the given EventModel, creator, and invitee users.
   *
   * @param event        the EventModel object to adapt
   * @param creator      the creator of the event
   * @param inviteeUsers the list of users invited to the event
   */
  public EventAdapter(EventModel event, IUser creator, ArrayList<IUser> inviteeUsers) {
    this.event = event;
    this.creator = creator;
    this.inviteeUsers = inviteeUsers;
  }

  /**
   * Returns the name of the event.
   *
   * @return the name of the event
   */
  @Override
  public String getEventName() {
    return event.getName();
  }

  /**
   * Returns the creator of the event.
   *
   * @return the creator of the event
   */
  @Override
  public IUser getCreator() {
    return creator;
  }

  /**
   * Returns the location of the event.
   *
   * @return the location of the event
   */
  @Override
  public ILocation getLocation() {
    return new LocationAdapter(event.getLocation(),
            event.isOnline());
  }

  /**
   * Returns the start time of the event.
   *
   * @return the start time of the event
   */
  @Override
  public ITime getStartTime() {
    return new TimeAdapter(event.getStartTime());
  }

  /**
   * Returns the day of the week when the event starts.
   *
   * @return the day of the week when the event starts
   */
  @Override
  public Day getStartDay() {
    return Day.valueOf(event.getStartTime().getDayOfWeek().name());
  }

  /**
   * Returns the end time of the event.
   *
   * @return the end time of the event
   */
  @Override
  public ITime getEndTime() {
    return new TimeAdapter(event.getEndTime());
  }

  /**
   * Returns the day of the week when the event ends.
   *
   * @return the day of the week when the event ends
   */

  @Override
  public Day getEndDay() {
    return Day.valueOf(event.getEndTime().getDayOfWeek().name());
  }

  /**
   * Returns the list of users invited to the event.
   *
   * @return the list of users invited to the event
   */
  @Override
  public ArrayList<IUser> getInvitees() {
    return inviteeUsers;
  }

  /**
   * Returns the duration of the event in hours and minutes.
   *
   * @return the duration of the event in hours and minutes
   */
  @Override
  public List<Integer> getDuration() {
    Duration duration = Duration.between(event.getStartTime(), event.getEndTime());
    return List.of((int) duration.toHours(), (int) (duration.toMinutes() % 60));
  }
}
