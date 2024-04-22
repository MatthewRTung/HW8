package controllertests;

import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

import java.io.File;

import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystemStub;
import cs3500.planner.model.Event;
import cs3500.planner.model.EventStub;
import cs3500.planner.model.ScheduleModel;
import cs3500.planner.view.CentralSystemFrameStub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for the ScheduleController class.
 */
public class ScheduleControllerTest {

  private ScheduleController controller;
  private CentralSystemFrameStub view;
  private CentralSystemStub model;
  private ScheduleModel scheduleModel;

  @Before
  public void setUp() {
    model = new CentralSystemStub();
    view = new CentralSystemFrameStub(model);
    controller = new ScheduleController(view, scheduleModel);
  }

  @Test
  public void testLoadXMLAction_Success() {
    model.simulateLoadEventsSuccess();
    File mockFile = new File("CSStudent.xml");
    controller.loadXMLAction(mockFile);
    assertTrue(view.isViewUpdated());
  }

  @Test
  public void testLoadXMLAction_Failure() {
    model.simulateLoadEventsFailure();
    File mockFile = new File("invalid.xml");
    controller.loadXMLAction(mockFile);
    assertFalse(view.isViewUpdated());
  }

  @Test
  public void testAddEvent_Success() {
    LocalDateTime start = LocalDateTime.of(2024, 4, 11, 10, 0);
    LocalDateTime end = LocalDateTime.of(2024, 4, 11, 11, 0);
    EventStub newEvent = new EventStub("Meeting", "Conference Room", false, start, end,
            false, "Host1");
    controller.createEvent(newEvent);
    assertTrue(model.isEventAddedForUser("Host1", newEvent));
    assertTrue(view.isViewUpdated());
  }

  @Test
  public void testAddEvent_Failure() {
    LocalDateTime start = LocalDateTime.of(2024, 4, 11, 10, 0);
    LocalDateTime end = LocalDateTime.of(2024, 4, 11, 11, 0);
    EventStub invalidEvent = new EventStub("", "", false, start, end, false, "Host1");
    controller.createEvent(invalidEvent);
    assertFalse(model.isEventAddedForUser("Host1", invalidEvent));
    assertFalse(view.isViewUpdated());
  }

  @Test
  public void testRemoveEvent_Success() {
    LocalDateTime start = LocalDateTime.of(2024, 4, 12, 14, 0);
    LocalDateTime end = LocalDateTime.of(2024, 4, 12, 15, 0);
    Event existingEvent = new Event("Conference", "Home", true, start, end, false, "Host2");
    model.createEvent("Host2", existingEvent);
    assertTrue(controller.removeEvent("Host2", "Conference"));
    assertFalse(model.isEventAddedForUser("Host2", existingEvent));
    assertTrue(view.isViewUpdated());
  }

  @Test
  public void testRemoveEvent_Failure() {
    Event nonExistingEvent = new Event("", "Unknown", false,
            LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host3");
    assertFalse(controller.removeEvent("Host3", ""));
    assertFalse(view.isViewUpdated());
  }

  @Test
  public void testErrorHandling_NullValues() {
    try {
      controller.createEvent(null);
      fail("No exception for null event");
    } catch (NullPointerException e) {
      assertTrue(view.isErrorDisplayed("Null event provided"));
    }
  }

  @Test
  public void testUserAction_ClickEventCreation() {
    LocalDateTime start = LocalDateTime.of(2024, 12, 25, 15, 30);
    LocalDateTime end = LocalDateTime.of(2024, 12, 25, 16, 30);
    EventStub clickEvent = new EventStub("Christmas Party", "Office", false, start, end, true,
            "Host5");
    controller.createEvent(clickEvent);
    assertTrue(model.isEventAddedForUser("Host5", clickEvent));
    assertTrue(view.isViewUpdated());
  }

  @Test
  public void testUserAction_UpdateEvent() {
    LocalDateTime start = LocalDateTime.of(2024, 10, 10, 10, 0);
    LocalDateTime end = LocalDateTime.of(2024, 10, 10, 11, 0);
    EventStub existingEvent = new EventStub("Workshop", "Lab", true, start, end, false, "Host6");
    model.createEvent("Host6", existingEvent);
    LocalDateTime newEnd = end.plusHours(2);
    existingEvent.setEndTime(newEnd);
    controller.createEvent(existingEvent);
    assertTrue("Event should be updated", model.isEventAddedForUser("Host6", existingEvent));
    assertTrue("View should update to show new event end time", view.isViewUpdated());
  }

  @Test
  public void testUserAction_DeleteEvent() {
    // Add an event and then delete it
    LocalDateTime start = LocalDateTime.of(2024, 5, 20, 14, 0);
    LocalDateTime end = LocalDateTime.of(2024, 5, 20, 15, 0);
    EventStub toDeleteEvent = new EventStub("Team Meeting", "Board Room", false, start, end,
            false, "Host7");
    model.createEvent("Host7", toDeleteEvent);

    // Simulate user deleting the event
    controller.removeEvent("Host7", "Team Meeting");
    assertFalse("Event should be removed", model.isEventAddedForUser("Host7", toDeleteEvent));
    assertTrue("View should update to reflect event deletion", view.isViewUpdated());
  }
}
