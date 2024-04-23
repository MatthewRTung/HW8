package cs3500.planner;

import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.ScheduleFactory;
import cs3500.planner.model.ScheduleModel;
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
    if (args.length != 2) {
      System.err.println("Usage: java PlannerRunner <strategy> <scheduleType>\n" +
              "strategy: \"anytime\" | \"workhours\"\n" +
              "scheduleType: \"standard\" | \"saturday\"");
      System.exit(1);
    }
    String strategy = args[0];
    String scheduleType = args[1];
    if (!"anytime".equals(strategy) && !"workhours".equals(strategy)) {
      System.err.println("Error: Invalid scheduling strategy. Acceptable values are" +
              " \"anytime\" or \"workhours\".");
      System.exit(1);
    }
    if (!"standard".equals(scheduleType) && !"saturday".equals(scheduleType)) {
      System.err.println("Error: Invalid schedule type. Acceptable values are" +
              " \"standard\" or \"saturday\".");
      System.exit(1);
    }
    //use the factory to create the schedule model based on the command line argument
    ScheduleModel scheduleModel = ScheduleFactory.createSchedule(scheduleType);
    CentralSystemModel centralSystemModel = new CentralSystem();
    CentralSystemFrame frame = new CentralSystemFrame();
    ScheduleController controller = new ScheduleController(frame, scheduleModel);
    frame.setController(controller);
    frame.setInitialRender(scheduleType);
    controller.setDefaultSchedulingStrategy(strategy);
    controller.launch(centralSystemModel);
    frame.setVisible(true);
  }
}