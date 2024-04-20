package cs3500.planner.model;

import cs3500.planner.provider.model.ILocation;

/**
 * LocationAdapter is an adapter class that adapts a location represented by a string and
 * a boolean indicating whether it's online or not, to provide compatibility with the
 * planning system by implementing the ILocation interface.
 */
public class LocationAdapter implements ILocation {

  private final String location;
  private final boolean isOnline;

  /**
   * Constructs a LocationAdapter with the given location string and online status.
   *
   * @param location the location string
   * @param isOnline true if the location is online, false otherwise
   */
  public LocationAdapter(String location, boolean isOnline) {
    this.location = location;
    this.isOnline = isOnline;
  }

  /**
   * Returns the address of the location. If the location is online, returns "Online";
   * otherwise, returns the original location string.
   *
   * @return the address of the location
   */
  @Override
  public String getAddress() {
    return isOnline ? "Online" : location;
  }

  /**
   * Returns true if the location is online, false otherwise.
   *
   * @return true if the location is online, false otherwise
   */
  @Override
  public boolean getOnline() {
    return isOnline;
  }
}
