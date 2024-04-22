package cs3500.planner.view;

import cs3500.planner.model.EventModel;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;

public class DefaultEventRenderer implements EventRenderer {
  @Override
  public void render(Graphics g, EventModel event, Rectangle rect) {
    Color transparent = new Color(177, 60, 60, 128);
    g.setColor(transparent);
    g.fillRect(rect.x, rect.y, rect.width, rect.height);
  }
}

