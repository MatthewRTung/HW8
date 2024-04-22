package cs3500.planner.view;

import java.awt.*;

import cs3500.planner.model.EventModel;

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
