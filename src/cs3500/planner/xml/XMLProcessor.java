package cs3500.planner.xml;

import java.util.List;

import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;

/**
 * Used to read and write XML files.
 */
public interface XMLProcessor {
  /**
   * Reads an XML file and returns a list of events.
   * @param filePath the path to the XML file
   * @return a list of events
   */
  List<EventModel> readXMLFile(String filePath);

  /**
   * Writes a list of events to an XML file.
   * @param events a list of events
   * @param filePath the path to the XML file
   */
  void writeXMLFile(List<EventModel> events, String filePath);
}
