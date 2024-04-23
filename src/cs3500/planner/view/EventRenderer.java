package cs3500.planner.view;

import java.awt.Graphics;
import java.awt.Rectangle;

import cs3500.planner.model.EventModel;

/**
 * Interface to render events.
 */
public interface EventRenderer {
  /**
   * Method to render the events on the planner.
   * @param g graphic
   * @param event the event being rendered
   * @param rect the rectangle being drawn
   */
  void render(Graphics g, EventModel event, Rectangle rect);
}

