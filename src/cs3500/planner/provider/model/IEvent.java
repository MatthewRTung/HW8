package cs3500.planner.provider.model;


import java.util.ArrayList;
import java.util.List;

/**
 * Interface to keep track of an event. An event contains all information required to create
 * an event.
 */
public interface IEvent {
  /**
   * gets an event name.
   * @return the name of the event as a String
   */
  String getEventName();

  /**
   * gets the creator of an event.
   * @return the creator of an event as an IUser.
   */
  IUser getCreator();

  /**
   * gets the location of an event.
   * @return the location as an ILocation.
   */
  ILocation getLocation();

  /**
   * gets the start time of an event.
   * @return the time as an ITime.
   */
  ITime getStartTime();

  /**
   * gets the start day of an event.
   * @return the day as a Day.
   */
  Day getStartDay();

  /**
   * gets the end time of an event.
   * @return the day as an ITime.
   */
  ITime getEndTime();

  /**
   * gets the end day of an event.
   * @return the day as a Day.
   */
  Day getEndDay();

  /**
   * gets the invitees of an event.
   * @return the invitees as a list of IUser
   */
  ArrayList<IUser> getInvitees();

  /**
   * gets the duration, in minutes, of an event.
   * @return the duration of an event, as a list of minutes in regards to the entire week,
   * of the event.
   */
  List<Integer> getDuration();

  /**
   * builds an event
   */
  static IEvent buildEvent(String name, IUser creator, ILocation loc, ITime startTime,
                           ITime endTime, ArrayList<IUser> invitees) {
    return new Event(name, creator, loc, startTime, endTime, invitees);
  }
}
