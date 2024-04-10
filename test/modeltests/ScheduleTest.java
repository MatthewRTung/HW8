package modeltests;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import cs3500.planner.model.Event;
import cs3500.planner.model.Schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Schedule class.
 */
public class ScheduleTest {
  private Schedule schedule;
  private Event event1;
  private Event event2;

  @Before
  public void setUp() {
    schedule = new Schedule();
    LocalDateTime now = LocalDateTime.now();
    event1 = new Event("Meeting", "Conference Room 101", false, now.minusHours(1),
            now.plusHours(1), false, "Host1");
    event2 = new Event("Webinar", "Online Platform", true, now.plusHours(2), now.plusHours(3),
            false, "Host2");
  }

  @Test
  public void addEventSuccess() {
    assertTrue(schedule.addEvent(event1));
    assertEquals(1, schedule.getAllEvents().size());
  }

  @Test
  public void addEventFailureDueToOverlap() {
    schedule.addEvent(event1);
    assertFalse(schedule.addEvent(new Event("Overlapping Meeting", "Conference Room 102", false,
            event1.getStartTime().plusMinutes(30), event1.getEndTime().plusHours(1), false,
            "Host1")));
    assertEquals(1, schedule.getAllEvents().size());
  }

  @Test
  public void removeEventSuccess() {
    schedule.addEvent(event1);
    schedule.addEvent(event2);
    assertTrue(schedule.removeEvent(event1.getName()));
    assertEquals(1, schedule.getAllEvents().size());
  }

  @Test
  public void removeEventNotFound() {
    schedule.addEvent(event2);
    assertFalse(schedule.removeEvent("Nonexistent Event"));
  }

  @Test
  public void modifyEventFailureDueToOverlap() {
    schedule.addEvent(event1);
    schedule.addEvent(event2);
    assertFalse(schedule.modifyEvent(event1.getName(), new Event("Meeting", "Conference Room 101",
            false, event2.getStartTime(), event2.getEndTime(), false, "Host1")));
  }

  @Test
  public void isTimeSlotFreeTrue() {
    schedule.addEvent(event1);
    assertTrue(schedule.isTimeSlotFree(event2.getStartTime(), event2.getEndTime()));
  }

  @Test
  public void isTimeSlotFreeFalse() {
    schedule.addEvent(event1);
    assertFalse(schedule.isTimeSlotFree(event1.getStartTime().plusMinutes(30), event1.
            getEndTime()));
  }

  @Test
  public void getEventsAtSpecificTime() {
    schedule.addEvent(event1);
    List<Event> eventsAtTime = schedule.getEventsAt(event1.getStartTime().plusMinutes(30));
    assertFalse(eventsAtTime.isEmpty());
    assertEquals(event1.getName(), eventsAtTime.get(0).getName());
  }

  @Test
  public void getWeeklyEventsTest() {
    schedule.addEvent(event1);
    Map<DayOfWeek, List<Event>> weeklyEvents = schedule.getWeeklyEvents();
    DayOfWeek dayOfWeek = event1.getStartTime().getDayOfWeek();
    assertTrue(weeklyEvents.containsKey(dayOfWeek));
    assertFalse(weeklyEvents.get(dayOfWeek).isEmpty());
  }
}
