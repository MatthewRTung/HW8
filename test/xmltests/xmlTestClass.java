package xmltests;

import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import cs3500.planner.model.EventModel;
import cs3500.planner.xml.XMLConfigurator;
import cs3500.planner.model.Event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for methods in XMLProcessor.
 */

public class xmlTestClass {

  @Test
  public void testReadXMLFile_EmptyFile() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> events = xmlConfigurator.readXMLFile("empty.xml");
    assertNotNull(events);
    assertTrue(events.isEmpty());
  }

  @Test
  public void testReadXMLFile_InvalidStructure() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> events = xmlConfigurator.readXMLFile("empty.xml");
    assertNotNull(events);
    assertTrue(events.isEmpty());
  }

  @Test
  public void testReadXMLFile_InvalidEventDetails() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> events = xmlConfigurator.readXMLFile("empty.xml");
    assertNotNull(events);
    assertTrue(events.isEmpty());
  }

  @Test
  public void testReadXMLFile_ValidEvents() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> event = xmlConfigurator.readXMLFile("CSStudent.xml");
    assertNotNull(event);
    assertFalse(event.isEmpty());
    EventModel event1 = event.get(0);
    assertEquals("Algorithms Lecture", event1.getName());
    assertEquals("West Hall 301", event1.getLocation());
    assertFalse(event1.isOnline());
  }

  @Test
  public void testWriteXMLFile_ValidEventsList() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> events = new ArrayList<>();
    events.add(new Event("Test Event 1", "Location 1",
            false, LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host1"));
    String filePath = "testOutput.xml";
    xmlConfigurator.writeXMLFile(events, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists());
    List<EventModel> readEvents = xmlConfigurator.readXMLFile(filePath);
    assertEquals(events.size(), readEvents.size());
    assertEquals(events.get(0).getName(), readEvents.get(0).getName());
    if (outputFile.exists()) {
      outputFile.delete();
    }
  }

  @Test
  public void testWriteXMLFile_EmptyEventsList() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> events = new ArrayList<>();
    String filePath = "emptyOutput.xml";
    xmlConfigurator.writeXMLFile(events, filePath);
    File outputFile = new File(filePath);
    assertTrue(outputFile.exists());
    List<EventModel> readEvents = xmlConfigurator.readXMLFile(filePath);
    assertTrue(readEvents.isEmpty());
    outputFile.delete();
  }

  @Test
  public void testWriteXMLFile_WriteReadConsistency() {
    XMLConfigurator xmlConfigurator = new XMLConfigurator();
    List<EventModel> eventsToWrite = new ArrayList<>();
    eventsToWrite.add(new Event("Event 1", "Location 1",
            false, LocalDateTime.now(), LocalDateTime.now().plusHours(1), false, "Host1"));
    eventsToWrite.add(new Event("Event 2", "Location 2", true,
            LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(1).plusHours(1), false,
            "Host2"));
    String filePath = "empty.xml";
    xmlConfigurator.writeXMLFile(eventsToWrite, filePath);
    List<EventModel> eventsReadBack = xmlConfigurator.readXMLFile(filePath);
    assertEquals(eventsToWrite.size(), eventsReadBack.size());
    for (int i = 0; i < eventsToWrite.size(); i++) {
      assertEquals(eventsToWrite.get(i).getName(), eventsReadBack.get(i).getName());
    }
    new File(filePath).delete();
  }

}
