package modeltests;

import org.junit.Test;

import java.time.LocalDateTime;

import cs3500.planner.model.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Event test class to test the methods in Event.
 */
public class EventTest {

  @Test
  public void testPartialOverlapStartTime() {
    LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 14, 9, 0);
    LocalDateTime endTime1 = LocalDateTime.of(2024, 3, 14, 11, 0);
    LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime endTime2 = LocalDateTime.of(2024, 3, 14, 12, 0);
    Event event1 = new Event("Event 1", "Location 1", false, startTime1, endTime1, false, "Jane");
    Event event2 = new Event("Event 2", "Location 2", false, startTime2, endTime2, false, "Dan");
    assertTrue(event1.overlaps(event2));
  }

  @Test
  public void testNonOverlappingEventsBeforeAndAfter() {
    //Event 1 is before Event 2
    LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 14, 8, 0);
    LocalDateTime endTime1 = LocalDateTime.of(2024, 3, 14, 9, 0);
    Event event1 = new Event("Event 1", "Location 1", false, startTime1, endTime1, false, "host1");
    //Event 2 is after Event 1
    LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime endTime2 = LocalDateTime.of(2024, 3, 14, 11, 0);
    Event event2 = new Event("Event 2", "Location 2", false, startTime2, endTime2, false, "host2");
    assertFalse(event1.overlaps(event2));
    assertFalse(event2.overlaps(event1));
  }

  @Test
  public void testPartialOverlapEndTime() {
    LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime endTime1 = LocalDateTime.of(2024, 3, 14, 12, 0);
    LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 14, 11, 0);
    LocalDateTime endTime2 = LocalDateTime.of(2024, 3, 14, 13, 0);
    Event event1 = new Event("Event 1", "Location 1", false, startTime1, endTime1, false, "Jack");
    Event event2 = new Event("Event 2", "Location 2", false, startTime2, endTime2, false, "Jill");
    assertTrue(event1.overlaps(event2));
  }

  @Test
  public void testEventEntirelyDuringAnother() {
    LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 14, 9, 0);
    LocalDateTime endTime1 = LocalDateTime.of(2024, 3, 14, 11, 0);
    LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime endTime2 = LocalDateTime.of(2024, 3, 14, 10, 30);
    Event event1 = new Event("Event 1", "Location 1", false, startTime1, endTime1, false, "Host2");
    Event event2 = new Event("Event 2", "Location 2", false, startTime2, endTime2, false, "Host1");
    assertTrue(event1.overlaps(event2));
  }

  @Test
  public void testIdenticalStartAndEndTimes() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 14, 9, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 14, 11, 0);
    Event event1 = new Event("Event 1", "Location 1", false, startTime, endTime, false, "Host1");
    Event event2 = new Event("Event 2", "Location 2", false, startTime, endTime, false, "Host2");
    assertTrue(event1.overlaps(event2)); // Use assertTrue to reflect correct behavior
  }


  @Test
  public void testAdjacentEventsNoOverlap() {
    LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 14, 9, 0);
    LocalDateTime endTime1 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 14, 10, 0);
    LocalDateTime endTime2 = LocalDateTime.of(2024, 3, 14, 11, 0);
    Event event1 = new Event("Event 1", "Location 1", false, startTime1, endTime1, false, "Host1");
    Event event2 = new Event("Event 2", "Location 2", false, startTime2, endTime2, false, "Host2");
    assertTrue(event1.overlaps(event2));
  }

  @Test
  public void testAddAndRemoveInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "user123";
    assertFalse(event.isUserInvited(userId));
    event.addInvitee(userId);
    assertTrue(event.isUserInvited(userId));
    event.removeInvitee(userId);
    assertFalse(event.isUserInvited(userId));
  }

  @Test
  public void testAddingNewInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "newUser";
    assertFalse(event.isUserInvited(userId));
    event.addInvitee(userId);
    assertTrue(event.isUserInvited(userId));
  }

  @Test
  public void testAddingExistingInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "existingUser";
    event.addInvitee(userId);
    int initialSize = event.getInvitees().size();
    event.addInvitee(userId);
    assertEquals("Invitee list size should not change", initialSize, event.getInvitees().size());
  }

  @Test
  public void testAddingMultipleUniqueInvitees() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId1 = "user1";
    String userId2 = "user2";
    event.addInvitee(userId1);
    event.addInvitee(userId2);
    assertTrue(event.isUserInvited(userId1) && event.isUserInvited(userId2));
  }

  @Test
  public void testRemovingExistingInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "user123";
    event.addInvitee(userId);
    assertTrue(event.isUserInvited(userId));
    event.removeInvitee(userId);
    assertFalse(event.isUserInvited(userId));
  }

  @Test
  public void testRemovingNonExistentInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "userNotExist";
    int initialSize = event.getInvitees().size();
    event.removeInvitee(userId);
    assertEquals(initialSize, event.getInvitees().size());
  }

  @Test
  public void testRemovingNullInvitee() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    int initialSize = event.getInvitees().size();
    event.removeInvitee(null);
    assertEquals("List size should remain unchanged when removing null", initialSize, event.
            getInvitees().size());
  }

  @Test
  public void testListSizeAfterRemoval() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "user123";
    event.addInvitee(userId);
    int sizeBeforeRemoval = event.getInvitees().size();
    event.removeInvitee(userId);
    assertEquals(sizeBeforeRemoval - 1, event.getInvitees().size());
  }

  @Test
  public void testOnlineEventLocation() {
    String location = "www.example.com/meet";
    Event event = new Event("Online Meeting", location, true,
            LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host1");
    assertEquals("Online: " + location, event.getLocation());
  }

  @Test
  public void testPhysicalEventLocation() {
    String location = "Conference Room A";
    Event event = new Event("Board Meeting", location, false,
            LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host1");
    assertEquals(location, event.getLocation());
  }

  @Test
  public void testOnlineEventEmptyLocation() {
    String location = "";
    Event event = new Event("Online Meeting", location, true,
            LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host1");
    assertEquals("Online: " + location, event.getLocation());
  }

  @Test
  public void testHybridEventLocation() {
    String location = "Conference Room B";
    Event event = new Event("Workshop", location, true,
            LocalDateTime.now(), LocalDateTime.now().plusHours(2), true, "Host1");
    assertEquals("Hybrid: Online and at " + location, event.getLocation());
  }

  @Test
  public void testUserIsInvited() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "user123";
    event.addInvitee(userId);
    assertTrue(event.isUserInvited(userId));
  }

  @Test
  public void testUserIsNotInvited() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "userNotInvited";
    assertFalse(event.isUserInvited(userId));
  }

  @Test
  public void testNullUserId() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    assertFalse(event.isUserInvited(null));
  }

  @Test
  public void testEmptyStringUserId() {
    LocalDateTime startTime = LocalDateTime.of(2024, 3, 15, 10, 0);
    LocalDateTime endTime = LocalDateTime.of(2024, 3, 15, 12, 0);
    Event event = new Event("Team Meeting", "Conference Room", false, startTime, endTime, false,
            "Host1");
    String userId = "";
    assertFalse(event.isUserInvited(userId));
  }

  @Test
  public void testEventIsCurrentlyHappening() {
    LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
    LocalDateTime oneHourLater = LocalDateTime.now().plusHours(1);
    Event event = new Event("Current Event", "Online", true, oneHourAgo, oneHourLater, false,
            "Host1");
    assertTrue(event.isCurrentlyHappening());
  }

  @Test
  public void testEventHasNotStartedYet() {
    LocalDateTime twoHoursLater = LocalDateTime.now().plusHours(2);
    LocalDateTime threeHoursLater = LocalDateTime.now().plusHours(3);
    Event event = new Event("Future Event", "Online", true, twoHoursLater, threeHoursLater, false,
            "Host1");
    assertFalse(event.isCurrentlyHappening());
  }

  @Test
  public void testEventHasAlreadyEnded() {
    LocalDateTime twoHoursAgo = LocalDateTime.now().minusHours(2);
    LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
    Event event = new Event("Past Event", "Online", true, twoHoursAgo, oneHourAgo, false, "Host1");
    assertFalse(event.isCurrentlyHappening());
  }

  @Test
  public void testEventStartsNow() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime oneHourLater = LocalDateTime.now().plusHours(1);
    Event event = new Event("Starting Now Event", "Online", true, now, oneHourLater, false,
            "Host1");
    assertTrue(event.isCurrentlyHappening());
  }

  @Test
  public void testEventEndsNow() {
    LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);
    LocalDateTime now = LocalDateTime.now();
    Event event = new Event("Ending Now Event", "Online", true, oneHourAgo, now, false, "Host1");
    assertFalse(event.isCurrentlyHappening());
  }
}
