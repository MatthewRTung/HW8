package cs3500.planner;

import cs3500.planner.controller.IScheduleFeatures;
import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystem;

import cs3500.planner.view.CentralSystemFrame;
import cs3500.planner.view.CentralSystemView;
import cs3500.planner.view.PlannerTextView;
import cs3500.planner.view.PlannerView;

/**
 * Class that contains the main method for running the program.
 */
public class PlannerRunner {

  /**
   * Constructs the NU Planner system and runs it.
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    CentralSystem model = new CentralSystem();
    CentralSystemFrame frame = new CentralSystemFrame(model);
    IScheduleFeatures controller = new ScheduleController(frame);
    frame.setController(controller);
    controller.launch(model);
  }
}