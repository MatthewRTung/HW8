package cs3500.planner;

import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystem;

import cs3500.planner.view.CentralSystemFrame;

/**
 * Class that contains the main method for running the program.
 */
public class PlannerRunner {

  /**
   * Constructs the NU Planner system and runs it.
   * @param args command-line arguments.
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Error: Expected a single argument for scheduling strategy" +
              " (\"anytime\" or \"workhours\").");
      System.exit(1);
    }

    String strategy = args[0];
    if (!"anytime".equals(strategy) && !"workhours".equals(strategy)) {
      System.err.println("Error: Invalid scheduling strategy. Acceptable values are" +
              " \"anytime\" or \"workhours\".");
      System.exit(1);
    }

    CentralSystem model = new CentralSystem();
    CentralSystemFrame frame = new CentralSystemFrame(model);
    ScheduleController controller = new ScheduleController(frame);
    frame.setController(controller);
    controller.setDefaultSchedulingStrategy(strategy);

    controller.launch(model);
  }
}