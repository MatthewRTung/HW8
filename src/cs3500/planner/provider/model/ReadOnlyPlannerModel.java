package cs3500.planner.provider.model;

import java.util.List;

/**
 * represents an observe only Planner Model.
 */
public interface ReadOnlyPlannerModel {

  /**
   * returns the list of clients in the Planner.
   */
  List<IUser> getClients();

  /**
   * Returns the real model.
   * @return PlannerModel
   */
  PlannerModel getModel();
}
