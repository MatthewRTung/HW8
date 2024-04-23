package cs3500.planner.provider.model;

/**
 * Interface for ILocation.
 */
public interface ILocation {

  /**
   * returns the address of the location.
   * @return address, as a string.
   */
  String getAddress();

  /**
   * returns if the event is online.
   * @return true if the event is online, false if the event is not online.
   */
  boolean getOnline();
}
