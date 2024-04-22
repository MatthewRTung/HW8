package viewtests;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;
import cs3500.planner.model.Schedule;
import cs3500.planner.model.ScheduleModel;
import cs3500.planner.view.PlannerTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the PlannerTextView class.
 */
public class PlannerTextViewTest {
  private CentralSystem centralSystem;
  private PlannerTextView plannerTextView;

  @Before
  public void setUp() {
    centralSystem = new CentralSystem();
    plannerTextView = new PlannerTextView(centralSystem);
  }

  @Test
  public void testToStringWithEmptyCentralSystem() {
    String expected = "";
    assertEquals(expected, plannerTextView.toString().trim());
  }

  @Test
  public void testToStringWithSingleUserNoEvents() {
    centralSystem.addUser("user1");
    String expected = "User: user1\nMONDAY:\n" +
            "\n" +
            "TUESDAY:\n" +
            "\n" +
            "WEDNESDAY:\n" +
            "\n" +
            "THURSDAY:\n" +
            "\n" +
            "FRIDAY:\n" +
            "\n" +
            "SATURDAY:\n" +
            "\n" +
            "SUNDAY:";
    assertEquals(expected, plannerTextView.toString().trim());
  }

  @Test
  public void testToStringWithMultipleUsersEmptySchedules() {
    centralSystem.addUser("user1");
    centralSystem.addUser("user2");
    String expected = "User: user1\nMONDAY:\n" +
            "\n" +
            "TUESDAY:\n" +
            "\n" +
            "WEDNESDAY:\n" +
            "\n" +
            "THURSDAY:\n" +
            "\n" +
            "FRIDAY:\n" +
            "\n" +
            "SATURDAY:\n" +
            "\n" +
            "SUNDAY:\n" +

            "\n\nUser: user2\nMONDAY:\n" +
            "\n" +
            "TUESDAY:\n" +
            "\n" +
            "WEDNESDAY:\n" +
            "\n" +
            "THURSDAY:\n" +
            "\n" +
            "FRIDAY:\n" +
            "\n" +
            "SATURDAY:\n" +
            "\n" +
            "SUNDAY:";
    assertEquals(expected, plannerTextView.toString().trim());
  }

  @Test
  public void testToStringMultipleUsersVariousEvents() {
    centralSystem.addUser("user1");
    centralSystem.addUser("user2");
    ScheduleModel user1Schedule = centralSystem.getUserSchedule("user1");
    ScheduleModel user2Schedule = centralSystem.getUserSchedule("user2");
    user1Schedule.addEvent(new Event("User1 Event", "Location 1", false, LocalDateTime.now()
            , LocalDateTime.now().plusHours(1), false, "user1"));
    user2Schedule.addEvent(new Event("User2 Event", "Online", true, LocalDateTime.now()
            .plusHours(2), LocalDateTime.now().plusHours(3), true, "user2"));
    String expected = "User: user1\n...User: user2\n...";
    String actual = plannerTextView.toString().trim();
    assertTrue(actual.startsWith("User: user1"));
    assertTrue(actual.contains("User1 Event"));
    assertTrue(actual.contains("User2 Event"));
  }
}
