package modeltests;

import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;
import cs3500.planner.model.SaturdaySchedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class to test SaturdaySchedule.
 */
public class SaturdayScheduleTest {
  private SaturdaySchedule saturdaySchedule;

  @Before
  public void setUp() {
    saturdaySchedule = new SaturdaySchedule();
    saturdaySchedule.addEvent(new Event("Event 1", "Location 1", false,
            LocalDateTime.of(2023, 4, 15, 10, 0),
            LocalDateTime.of(2023, 4, 15, 12, 0), false, "Matt"));
    saturdaySchedule.addEvent(new Event("Event 2", "Location 2", true,
            LocalDateTime.of(2023, 4, 16, 14, 0), // Sunday
            LocalDateTime.of(2023, 4, 16, 16, 0), false, "Host"));
  }

  @Test
  public void testWeeklyEventMapping() {
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertEquals("Should have 1 event on Saturday", 1, weeklyEvents.get(DayOfWeek.SATURDAY).size());
    assertEquals("Event 1 should be on Saturday", "Event 1", weeklyEvents.get(DayOfWeek.SATURDAY)
            .get(0).getName());
    assertEquals("Should have 1 event on Sunday", 1, weeklyEvents.get(DayOfWeek.SUNDAY).size());
    assertEquals("Event 2 should be on Sunday", "Event 2", weeklyEvents.get(DayOfWeek.SUNDAY)
            .get(0).getName());
  }

  @Test
  public void testMultipleEventsSingleDay() {
    SaturdaySchedule schedule = new SaturdaySchedule();
    LocalDateTime saturdayMorning = LocalDateTime.of(2023, 4, 15, 9, 0);
    LocalDateTime saturdayNoon = LocalDateTime.of(2023, 4, 15, 12, 0);
    LocalDateTime saturdayEvening = LocalDateTime.of(2023, 4, 15, 18, 0);
    schedule.addEvent(new Event("Morning Meeting", "Office", false, saturdayMorning,
            saturdayMorning.plusHours(2), false, "Host1"));
    schedule.addEvent(new Event("Lunch", "Cafeteria", true, saturdayNoon, saturdayNoon.plusHours(1),
            false, "Host2"));
    schedule.addEvent(new Event("Evening Review", "Office", false, saturdayEvening,
            saturdayEvening.plusHours(3), false, "Host3"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = schedule.getWeeklyEvents();
    assertEquals("Should have 3 events on Saturday", 3, weeklyEvents.get(DayOfWeek.SATURDAY)
            .size());
    assertEquals("First event should be 'Morning Meeting'", "Morning Meeting", weeklyEvents.
            get(DayOfWeek.SATURDAY).get(0).getName());
    assertEquals("Second event should be 'Lunch'", "Lunch", weeklyEvents.get(DayOfWeek.SATURDAY)
            .get(1).getName());
    assertEquals("Third event should be 'Evening Review'", "Evening Review", weeklyEvents.
            get(DayOfWeek.SATURDAY).get(2).getName());
  }

  @Test
  public void testSingleEventPlacement() {
    LocalDateTime date = LocalDateTime.of(2023, 10, 14, 10, 0);
    saturdaySchedule.addEvent(new Event("Workshop", "Conference Room", true, date,
            date.plusHours(2), false, "Host1"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertEquals("Saturday should have 1 event", 1, weeklyEvents.get(DayOfWeek.SATURDAY).size());
    assertEquals("Event should be 'Workshop'", "Workshop", weeklyEvents.get(DayOfWeek.SATURDAY)
            .get(0).getName());
  }

  @Test
  public void testEventsSpanningMultipleDays() {
    LocalDateTime start = LocalDateTime.of(2023, 10, 13, 22, 0); //Friday night
    LocalDateTime end = LocalDateTime.of(2023, 10, 14, 2, 0); //Into Saturday
    saturdaySchedule.addEvent(new Event("Overnight Coding Session", "Online", true, start,
            end, false, "Host3"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertEquals("Friday should have 1 event", 1, weeklyEvents.get(DayOfWeek.FRIDAY).size());
    assertEquals("Saturday should also have 1 event", 1, weeklyEvents.get(DayOfWeek.SATURDAY)
            .size());
  }

  @Test
  public void testEventsAtExtremeTimes() {
    LocalDateTime earlyMorning = LocalDateTime.of(2023, 10, 14, 0, 0); // Early Saturday
    LocalDateTime lateNight = LocalDateTime.of(2023, 10, 14, 23, 59); // Late Saturday
    saturdaySchedule.addEvent(new Event("Early Bird Session", "Online", true, earlyMorning,
            earlyMorning.plusHours(1), false, "Host5"));
    saturdaySchedule.addEvent(new Event("Night Owl Webinar", "Online", true, lateNight,
            lateNight.plusMinutes(1), false, "Host6"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertEquals("Saturday should have 2 events", 2, weeklyEvents.get(DayOfWeek.SATURDAY).size());
  }

  @Test
  public void testWeekWrapping() {
    LocalDateTime sundayMorning = LocalDateTime.of(2023, 10, 15, 10, 0);
    saturdaySchedule.addEvent(new Event("Sunday Seminar", "Hall", true, sundayMorning,
            sundayMorning.plusHours(2), false, "Host7"));
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertEquals("Sunday should be counted correctly", 1, weeklyEvents.get(DayOfWeek.SUNDAY)
            .size());
  }

  @Test
  public void testHandlingOfEmptyData() {
    Map<DayOfWeek, List<EventModel>> weeklyEvents = saturdaySchedule.getWeeklyEvents();
    assertTrue("Map should be empty", weeklyEvents.isEmpty()
            || weeklyEvents.values().stream().allMatch(List::isEmpty));
  }


}
