package modeltests;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import cs3500.planner.model.CentralSystemModel;
import cs3500.planner.model.CentralSystemStub;
import cs3500.planner.model.Event;
import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.ScheduleModel;
import cs3500.planner.view.CentralSystemFrameStub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the scheduleEventWithStrategy method in the scheduling system.
 * This class contains tests that verify the functionality of scheduling events
 * using different strategies like "Any time" and "Work hours".
 */
public class ScheduleEventWithStrategyTest {
  private ScheduleController controller;
  private CentralSystemStub model;
  private CentralSystemFrameStub view;
  private ScheduleModel scheduleModel;
  private CentralSystemModel centralSystemModel;

  @Before
  public void setup() {
    model = new CentralSystemStub();
    view = new CentralSystemFrameStub(model);
    controller = new ScheduleController(view, scheduleModel);
    controller.launch(centralSystemModel);
  }

  @Test
  public void testAnyTimeStrategyWithAvailableSlot() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 30, "user1",
            "", "Any time");
    assertTrue(model.isEventAddedForUser("user1", new Event("Meeting", "Office", false,
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), false, "user1")));
  }

  @Test
  public void testAnyTimeStrategyWithNoAvailableSlot() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    //Assuming there's no slot available in the stub implementation for this test
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 30,
            "user1", "", "Any time");
    assertFalse(view.isViewUpdated());
    assertTrue(view.isErrorDisplayed("No available time slot found for the event."));
  }

  @Test
  public void gvailableSlot() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 30, "user1",
            "", "Work hours");
    assertTrue(model.isEventAddedForUser("user1", new Event("Meeting", "Office", false,
            LocalDateTime.now(), LocalDateTime.now().plusMinutes(30), false, "user1")));
  }

  @Test
  public void testWorkHoursStrategyWithNoAvailableSlot() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    // Assuming there's no slot available during work hours in the stub implementation for this test
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 30, "user1",
            "", "Work hours");
    assertFalse(view.isViewUpdated());
    assertTrue(view.isErrorDisplayed("No available time slot found for the event."));
  }

  @Test
  public void testInvalidStrategy() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 30, "user1",
            "", "Invalid strategy");
    assertFalse(model.isUserAdded("user1"));
    assertFalse(view.isViewUpdated());
  }

  @Test
  public void testZeroDuration() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    controller.scheduleEventWithStrategy("Meeting", "Office", false, 0, "user1",
            "", "Any time");
    assertFalse(model.isEventAddedForUser("user1", new Event("Meeting", "Office", false,
            LocalDateTime.now(), LocalDateTime.now(), false, "user1")));
    assertTrue(view.isErrorDisplayed("No available time slot found for the event."));
  }

  @Test
  public void testOverlappingEvents() {
    model.addUser("user1");
    controller.setCurrentUser("user1");
    LocalDateTime now = LocalDateTime.now();
    Event existingEvent = new Event("Existing Meeting", "Office", false, now, now.plusHours(1),
            false, "user1");
    model.createEvent("user1", existingEvent);
    controller.scheduleEventWithStrategy("New Meeting", "Office", false, 60, "user1",
            "", "Any time");
    assertFalse(view.isViewUpdated());
    assertTrue(view.isErrorDisplayed("No available time slot found for the event."));
  }
}

