package cs3500.planner.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;

/**
 * Class used to read and write XML files.
 */
public class XMLConfigurator implements XMLProcessor {

  @Override
  public List<EventModel> readXMLFile(String filePath) {
    List<EventModel> events = new ArrayList<>();
    LocalDate referenceDate;
    referenceDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    try {
      Document doc = parseXMLDocument(filePath);
      NodeList nList = doc.getElementsByTagName("event");
      for (int temp = 0; temp < nList.getLength(); temp++) {
        Node nNode = nList.item(temp);
        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
          Event event = parseEventElement((Element) nNode, referenceDate);
          events.add(event);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return events;
  }

  @Override
  public void writeXMLFile(List<EventModel> events, String filePath) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document doc = builder.newDocument();
      Element rootElement = doc.createElement("events");
      doc.appendChild(rootElement);
      for (EventModel event : events) {
        Element eventElement = createEventElement(doc, event);
        rootElement.appendChild(eventElement);
      }
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(filePath));
      transformer.transform(source, result);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * This method reads the user ID from the schedule tag in the XML file.
   *
   * @param filePath The path to the XML file.
   * @return The user ID as a String.
   */
  public String readScheduleUserId(String filePath) {
    try {
      Document doc = parseXMLDocument(filePath);
      Element scheduleElement = (Element) doc.getElementsByTagName("schedule").item(0);
      return scheduleElement.getAttribute("id");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "";
  }

  //helper method that parses an XML file and returns a DOM Document object.
  private Document parseXMLDocument(String filePath) throws Exception {
    File inputFile = new File(filePath);
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(inputFile);
    doc.getDocumentElement().normalize();
    return doc;
  }

  //helper method to parse an event from an XML file
  private Event parseEventElement(Element eElement, LocalDate referenceDate) {
    String name = getTextContent(eElement, "name").replace("\"", "");
    String location = getTextContent(eElement, "place").replace("\"", "");
    NodeList users = eElement.getElementsByTagName("uid");
    String hostId = users.getLength() > 0 ? users.item(0).getTextContent().replace("\"", "") : ""; // Assume first user is the host
    boolean isOnline = Boolean.parseBoolean(getTextContent(eElement, "online"));
    LocalDateTime startDateTime = parseDateTime(eElement, "start-day", "start", referenceDate);
    LocalDateTime endDateTime = parseDateTime(eElement, "end-day", "end", referenceDate);
    Event event = new Event(name, location, isOnline, startDateTime, endDateTime, false, hostId);
    parseUserIds(eElement, event);
    return event;
  }

  //helper method to return the text content of the first element with the given tag name
  private String getTextContent(Element element, String tagName) {
    return element.getElementsByTagName(tagName).item(0).getTextContent();
  }

  //helper method to parse the date and time from the XML element and return a LocalDateTime object
  private LocalDateTime parseDateTime(Element eElement, String dayTagName, String timeTagName,
                                      LocalDate referenceDate) {
    String dayStr = getTextContent(eElement, dayTagName).toUpperCase();
    DayOfWeek day = DayOfWeek.valueOf(dayStr);
    LocalDate date = referenceDate.with(TemporalAdjusters.nextOrSame(day));
    String timeStr = getTextContent(eElement, timeTagName);
    LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HHmm"));
    return LocalDateTime.of(date, time);
  }

  //helper method to parse the user ID from the XML element and add them to the event
  private void parseUserIds(Element eElement, Event event) {
    NodeList users = eElement.getElementsByTagName("uid");
    for (int i = 0; i < users.getLength(); i++) {
      String userId = users.item(i).getTextContent().replace("\"", "");
      event.addInvitee(userId);
    }
  }

  //helper method to create and XML element for an event
  private Element createEventElement(Document doc, EventModel event) {
    Element eventElement = doc.createElement("event");
    Element name = doc.createElement("name");
    name.appendChild(doc.createTextNode(event.getName()));
    eventElement.appendChild(name);
    Element time = createTimeElement(doc, event);
    eventElement.appendChild(time);
    Element location = createLocationElement(doc, event);
    eventElement.appendChild(location);
    Element users = createUsersElement(doc, event);
    eventElement.appendChild(users);
    return eventElement;
  }

  //helper method to create the XML element for the time of an event
  private Element createTimeElement(Document doc, EventModel event) {
    Element time = doc.createElement("time");
    Element startDay = doc.createElement("start-day");
    startDay.appendChild(doc.createTextNode(event.getStartTime().getDayOfWeek().toString()));
    time.appendChild(startDay);
    Element start = doc.createElement("start");
    start.appendChild(doc.createTextNode(event.getStartTime().
            format(DateTimeFormatter.ofPattern("HHmm"))));
    time.appendChild(start);
    Element endDay = doc.createElement("end-day");
    endDay.appendChild(doc.createTextNode(event.getEndTime().getDayOfWeek().toString()));
    time.appendChild(endDay);
    Element end = doc.createElement("end");
    end.appendChild(doc.createTextNode(event.getEndTime().
            format(DateTimeFormatter.ofPattern("HHmm"))));
    time.appendChild(end);
    return time;
  }

  //helper method to create the XML element for the location of an event
  private Element createLocationElement(Document doc, EventModel event) {
    Element location = doc.createElement("location");
    Element online = doc.createElement("online");
    online.appendChild(doc.createTextNode(Boolean.toString(event.isOnline())));
    location.appendChild(online);
    Element place = doc.createElement("place");
    place.appendChild(doc.createTextNode(event.getLocation()));
    location.appendChild(place);
    return location;
  }

  //helper method to create the XML element for the users of an event
  private Element createUsersElement(Document doc, EventModel event) {
    Element users = doc.createElement("users");
    for (String user : event.getInvitees()) {
      Element userId = doc.createElement("uid");
      userId.appendChild(doc.createTextNode(user));
      users.appendChild(userId);
    }
    return users;
  }
}


