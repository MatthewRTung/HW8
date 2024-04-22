package cs3500.planner.view;

import java.awt.*;

import cs3500.planner.model.EventModel;

public class StandardEventRenderer implements EventRenderer {
  @Override
  public void render(Graphics g, EventModel event, Rectangle rect) {
    Color transparent = new Color(177, 60, 60, 128);
    g.setColor(transparent);
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }
}
