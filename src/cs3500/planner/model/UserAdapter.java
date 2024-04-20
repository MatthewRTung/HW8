package cs3500.planner.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cs3500.planner.provider.model.IEvent;
import cs3500.planner.provider.model.ILocation;
import cs3500.planner.provider.model.INUPlanner;
import cs3500.planner.provider.model.ITime;
import cs3500.planner.provider.model.IUser;

/**
 * UserAdapter is an adapter class that adapts an IUser object to provide compatibility with
 * the planning system by implementing the IUser interface.
 */
public class UserAdapter implements IUser {

  private final IUser user;
  private ArrayList<IUser> invitedUsers;

  /**
   * Constructs a UserAdapter with the given IUser object to adapt.
   *
   * @param user the IUser object to adapt
   */
  public UserAdapter(IUser user) {
    this.user = user;
  }

  /**
   * Adds event to the user's schedule.
   * @param e the event to add.
   */
  @Override
  public void addEvent(IEvent e) {
    Event adaptedEvent = convertIEventToEvent(e);
    user.addEvent(new EventAdapter(adaptedEvent, user, invitedUsers));
  }

  /**
   * Removes event from user's schedule.
   * @param e the event to remove.
   */
  @Override
  public void removeEvent(IEvent e) {
    Event adaptedEvent = convertIEventToEvent(e);
    user.removeEvent(new EventAdapter(adaptedEvent, user, invitedUsers));
  }

  /**
   * Gets the name of the user.
   * @return user name.
   */
  @Override
  public String getUserName() {
    return user.getUserName();
  }

  /**
   * Gets the schedule of the user.
   * @return the user's schedule.
   */
  @Override
  public INUPlanner getSchedule() {
    return user.getSchedule();
  }

  /**
   * Adds all events to a planner.
   * @param plan a NUPlanner with events to include in a user's schedule.
   */
  @Override
  public void addAllEvents(INUPlanner plan) {
    List<IEvent> externalEvents = plan.getEvents();
    for (IEvent externalEvent : externalEvents) {
      Event internalEvent = convertIEventToEvent(externalEvent);
      addEvent(new EventAdapter(internalEvent, user, invitedUsers));
    }
  }

  // converts IEvent to an Event
  private Event convertIEventToEvent(IEvent iEvent) {
    String name = iEvent.getEventName();
    ILocation iLocation = iEvent.getLocation();
    String location = iLocation.getAddress();
    boolean isOnline = location.toLowerCase().contains("online");
    ITime startTime = iEvent.getStartTime();
    LocalDateTime startDateTime = convertITimeToLocalDateTime(startTime);
    ITime endTime = iEvent.getEndTime();
    LocalDateTime endDateTime = convertITimeToLocalDateTime(endTime);
    boolean isHybrid = location.toLowerCase().contains("hybrid");
    String hostId = "";
    Event event = new Event(name, location, isOnline, startDateTime, endDateTime, isHybrid, hostId);
    List<String> invitees = iEvent.getInvitees().stream()
            .map(IUser::getUserName)
            .collect(Collectors.toList());
    for (String userId : invitees) {
      event.addInvitee(userId);
    }

    return event;
  }

  // converts ITime to LocalDateTime
  private LocalDateTime convertITimeToLocalDateTime(ITime iTime) {
    return LocalDateTime.of(2021, 1, 1, iTime.getHours(), iTime.getMinutes());
  }
}
