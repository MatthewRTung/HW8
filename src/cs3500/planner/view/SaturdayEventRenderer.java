package cs3500.planner.view;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

import cs3500.planner.model.EventModel;

/**
 * SaturdayEventRenderer to help render schedule starting on saturday.
 */
public class SaturdayEventRenderer implements EventRenderer {
  @Override
  public void render(Graphics g, EventModel event, Rectangle rect) {
    int newCol = (event.getStartTime().getDayOfWeek().getValue() + 1) % 7;
    Rectangle newRect = new Rectangle(
            rect.x + (newCol - event.getStartTime().getDayOfWeek().getValue()) * rect.width,
            rect.y,
            rect.width,
            rect.height
    );
    Color transparent = new Color(177, 60, 60, 128);
    g.setColor(transparent);
    g.fillRect(newRect.x, newRect.y, newRect.width, newRect.height);
  }
}
