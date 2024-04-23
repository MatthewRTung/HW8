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
 * PlannerAdapter is an adapter class that adapts a CentralSystem object to provide compatibility
 * with the planning system by implementing the INUPlanner interface.
 */
public class PlannerAdapter implements INUPlanner {

  private final CentralSystemModel centralSystem;

  /**
   * Constructs a PlannerAdapter with the given CentralSystem object to adapt.
   *
   * @param centralSystem the CentralSystem object to adapt
   */
  public PlannerAdapter(CentralSystemModel centralSystem) {
    this.centralSystem = centralSystem;
  }

  /**
   * Retrieves the list of events from the adapted CentralSystem.
   *
   * @return the list of events
   */
  @Override
  public List<IEvent> getEvents() {
    String currentUser = centralSystem.getUserName().toString();
    List<EventModel> events = centralSystem.getEventsForUser(currentUser);
    return events.stream()
            .map(eventModel -> new EventAdapter(eventModel, null, new ArrayList<>()))
            .collect(Collectors.toList());
  }

  /**
   * Creates a new event in the adapted CentralSystem.
   *
   * @param name     the name of the event
   * @param creator  the creator of the event
   * @param loc      the location of the event
   * @param startTime the start time of the event
   * @param endTime  the end time of the event
   * @param invitees the list of users invited to the event
   */
  @Override
  public void createEvent(String name, IUser creator, ILocation loc, ITime startTime,
                          ITime endTime, ArrayList<IUser> invitees) {
    LocalDateTime startDateTime = convertITimeToLocalDateTime(startTime);
    LocalDateTime endDateTime = convertITimeToLocalDateTime(endTime);
    boolean isOnline = loc.getOnline();
    String location = loc.getAddress();
    EventModel newEvent = new Event(name, location, isOnline, startDateTime, endDateTime,
            isOnline, creator.getUserName());
    centralSystem.createEvent(creator.getUserName(), newEvent);
  }

  /**
   * Modifies an existing event in the adapted CentralSystem.
   *
   * @param e1       the event to modify
   * @param name     the new name of the event
   * @param creator  the new creator of the event
   * @param loc      the new location of the event
   * @param startTime the new start time of the event
   * @param endTime  the new end time of the event
   * @param invitees the new list of users invited to the event
   */
  @Override
  public void modifyEvent(IEvent e1, String name, IUser creator, ILocation loc, ITime startTime,
                          ITime endTime, ArrayList<IUser> invitees) {
    EventModel eventToModify = findEventByIEvent(e1);
    if (eventToModify != null) {
      LocalDateTime startDateTime = convertITimeToLocalDateTime(startTime);
      LocalDateTime endDateTime = convertITimeToLocalDateTime(endTime);
      boolean isOnline = loc.getOnline();
      String location = loc.getAddress();
      EventModel updatedEvent = new Event(name, location, isOnline, startDateTime, endDateTime,
              isOnline, creator.getUserName());
      centralSystem.modifyEvent(creator.getUserName(), eventToModify.getHostId(), updatedEvent);
    }
  }

  /**
   * Deletes an existing event from the adapted CentralSystem.
   *
   * @param e the event to delete
   */
  @Override
  public void deleteEvent(IEvent e) {
    EventModel eventToRemove = findEventByIEvent(e);
    if (eventToRemove != null) {
      centralSystem.deleteEvent(eventToRemove.getHostId(), eventToRemove.getName());
    }
  }

  /**
   * Checks if an event has a time overlap in the adapted CentralSystem.
   *
   * @param e the event to check for time overlap
   * @return true if there is a time overlap, false otherwise
   */
  @Override
  public boolean hasTimeOverlap(IEvent e) {
    EventModel event = findEventByIEvent(e);
    return event != null && centralSystem.doesEventConflict(event, event.getHostId());
  }

  // finds an event using an IEvent
  private EventModel findEventByIEvent(IEvent iEvent) {
    return centralSystem.getEventsForUser(iEvent.getCreator().getUserName()).stream()
            .filter(event -> event.getName().equals(iEvent.getEventName()))
            .findFirst()
            .orElse(null);
  }

  // convets ITime to LocalDateTime
  private LocalDateTime convertITimeToLocalDateTime(ITime iTime) {
    return LocalDateTime.of(2021, 1, 1, iTime.getHours(),
            iTime.getMinutes());
  }
}
