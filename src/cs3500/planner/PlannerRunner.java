package cs3500.planner;

import java.util.Arrays;

import cs3500.planner.controller.ScheduleAdapter;
import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystem;
import cs3500.planner.provider.view.ISchedulePanel;
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
    if (args.length < 1) {
      System.err.println("Error: No arguments provided. Expecting scheduling strategy.");
      System.exit(1);
    }

    String strategy = args[0];
    boolean useProviderView = Arrays.asList(args).contains("--use-provider-view");

    CentralSystem model = new CentralSystem();

    CentralSystemFrame frame = new CentralSystemFrame();
    ScheduleController controller = new ScheduleController(frame);
    controller.setDefaultSchedulingStrategy(strategy);

    if (useProviderView) {
      ISchedulePanel providerView = null;
      ISchedulePanel view = new ScheduleAdapter(providerView, null, null);
      frame.setController(controller);
    } else {
      frame.setController(controller);
    }
    controller.launch(model);
    frame.setVisible(true);
  }


}