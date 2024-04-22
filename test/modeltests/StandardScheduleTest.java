package modeltests;

import org.junit.Before;
import org.junit.Test;

import cs3500.planner.model.EventModel;
import cs3500.planner.model.StandardSchedule;

public class StandardScheduleTest {
  private StandardSchedule schedule;

  @Before
  public void setUp() {
    schedule = new StandardSchedule();
  }

  @Test
  public void testAddEvent_WithNoConflicts() {
  }
}
