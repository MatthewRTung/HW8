package modeltests;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;
import cs3500.planner.model.StandardSchedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for StandardSchedule.
 */
public class StandardScheduleTest {
  private StandardSchedule schedule;

  @Before
  public void setUp() {
    schedule = new StandardSchedule();
  }

  @Test
  public void testSingleEventPlacement() {
    LocalDateTime sundayDate = LocalDateTime.of(2023, 10, 1, 10, 0);
    schedule.addEvent(new Event("Morning Workshop", "Conference Hall", false, sundayDate,
            sundayDate.plusHours(2), false, "Host1"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Sunday should have 1 event", 1, weeklyEvents.get(DayOfWeek.SUNDAY).size());
    assertEquals("Event should be 'Morning Workshop'", "Morning Workshop", weeklyEvents
            .get(DayOfWeek.SUNDAY).get(0).getName());
  }

  @Test
  public void testMultipleEventsSingleDay() {
    LocalDateTime morning = LocalDateTime.of(2023, 10, 1, 9, 0); //Sunday morning
    LocalDateTime evening = LocalDateTime.of(2023, 10, 1, 17, 0); //Sunday evening
    schedule.addEvent(new Event("Morning Meeting", "Main Office", true, morning,
            morning.plusHours(3), false, "Host2"));
    schedule.addEvent(new Event("Evening Seminar", "Main Office", false, evening,
            evening.plusHours(2), false, "Host3"));

    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Sunday should have 2 events", 2, weeklyEvents.get(DayOfWeek.SUNDAY).size());
  }

  @Test
  public void testEventsSpanningMultipleDays() {
    LocalDateTime start = LocalDateTime.of(2023, 9, 30, 23, 0); //Saturday night
    LocalDateTime end = LocalDateTime.of(2023, 10, 1, 1, 0); //Into Sunday
    schedule.addEvent(new Event("Night Shift", "Factory", true, start, end, false, "Host4"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Saturday should have 1 event", 1, weeklyEvents.get(DayOfWeek.SATURDAY).size());
    assertEquals("Sunday should also have 1 event", 1, weeklyEvents.get(DayOfWeek.SUNDAY).size());
  }

  @Test
  public void testBoundaryConditions() {
    LocalDateTime start = LocalDateTime.of(2023, 9, 30, 23, 59); // Late Saturday night
    LocalDateTime end = LocalDateTime.of(2023, 10, 1, 0, 1); // Early Sunday
    schedule.addEvent(new Event("Late Discussion", "Online", false, start, end, false, "Host5"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Saturday should have 1 event", 1, weeklyEvents.get(DayOfWeek.SATURDAY).size());
    assertEquals("Sunday should have 1 event", 1, weeklyEvents.get(DayOfWeek.SUNDAY).size());
  }

  @Test
  public void testEventsAtExtremeTimes() {
    LocalDateTime earlySunday = LocalDateTime.of(2023, 10, 1, 0, 0); //Early Sunday
    LocalDateTime lateSunday = LocalDateTime.of(2023, 10, 1, 23, 59); //Late Sunday

    schedule.addEvent(new Event("Early Worship", "Church", true, earlySunday,
            earlySunday.plusHours(1), false, "Host6"));
    schedule.addEvent(new Event("Late Night Show", "TV Station", false, lateSunday,
            lateSunday.plusMinutes(30), false, "Host7"));

    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Sunday should have 2 events", 2, weeklyEvents.get(DayOfWeek.SUNDAY).size());
  }

  @Test
  public void testHandlingOfEmptyData() {
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertTrue("Map should be empty", weeklyEvents.isEmpty()
            || weeklyEvents.values().stream().allMatch(List::isEmpty));
  }
}
