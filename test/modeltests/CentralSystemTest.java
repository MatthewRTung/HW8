package modeltests;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Map;

import cs3500.planner.model.CentralSystem;
import cs3500.planner.model.Event;
import cs3500.planner.model.Schedule;
import cs3500.planner.model.ScheduleModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the CentralSystem class.
 */
public class CentralSystemTest {
  private CentralSystem centralSystem;

  @Before
  public void setUp() {
    centralSystem = new CentralSystem();
  }

  @Test
  public void testAddUser() {
    String userId = "newUser";
    centralSystem.addUser(userId);
    assertNotNull(centralSystem.getUserSchedule(userId));
  }

  @Test
  public void testAddUserWithExistingUser() {
    String userId = "existingUser";
    centralSystem.addUser(userId);
    Event event = new Event("Meeting", "Room 101", false, LocalDateTime.now(), LocalDateTime.now()
            .plusHours(1), false, "hostId");
    assertTrue(centralSystem.createEvent(userId, event));
    centralSystem.addUser(userId);
    assertNotNull(centralSystem.getUserSchedule(userId).getEventById("Meeting"));
  }

  @Test
  public void testRemoveUser() {
    String userId = "userToRemove";
    centralSystem.addUser(userId);
    centralSystem.removeUser(userId);
    assertNull(centralSystem.getUserSchedule(userId));
  }

  @Test
  public void testRemoveNonExistentUser() {
    String userId = "nonExistentUser";
    assertFalse(centralSystem.removeUser(userId));
  }

  @Test
  public void testCreateEventWithValidData() {
    String userId = "userForEvent";
    centralSystem.addUser(userId);
    Event event = new Event("Valid Event", "Room 202", false, LocalDateTime.now(), LocalDateTime
            .now().plusHours(2), false, "hostId");
    assertTrue(centralSystem.createEvent(userId, event));
  }

  @Test
  public void testCreateEventWithOverlappingEvent() {
    String userId = "userWithOverlap";
    centralSystem.addUser(userId);
    LocalDateTime startTime = LocalDateTime.now();
    Event firstEvent = new Event("First Event", "Room 303", true, startTime, startTime.plusHours(1)
            , false, "hostId");
    centralSystem.createEvent(userId, firstEvent);
    Event overlappingEvent = new Event("Overlapping Event", "Online", true, startTime
            .plusMinutes(30), startTime.plusHours(1), true, "hostId");
    assertFalse(centralSystem.createEvent(userId, overlappingEvent));
  }

  @Test
  public void testModifyNonExistentEvent() {
    String userId = "userForNonExistent";
    centralSystem.addUser(userId);
    Event updatedEvent = new Event("Non-Existent Event", "Online", true, LocalDateTime.now(),
            LocalDateTime.now().plusHours(2), true, "hostId");
    assertFalse(centralSystem.modifyEvent(userId, "Non-Existent Event", updatedEvent));
  }

  @Test
  public void testDeleteEvent() {
    String userId = "userForDelete";
    centralSystem.addUser(userId);
    Event event = new Event("Event to Delete", "Room 606", false, LocalDateTime.now(),
            LocalDateTime.now().plusHours(1), false, "hostId");
    centralSystem.createEvent(userId, event);
    assertTrue(centralSystem.deleteEvent(userId, "Event to Delete"));
  }

  @Test
  public void testGetAllSchedules() {
    centralSystem.addUser("user1");
    centralSystem.addUser("user2");
    Map<String, ScheduleModel> schedules = centralSystem.getAllSchedules();
    assertEquals(2, schedules.size());
  }
}
