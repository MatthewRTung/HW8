package cs3500.planner.view;

import cs3500.planner.model.EventModel;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Color;

public class HostEventDecorator implements EventRenderer {
  private final EventRenderer wrapped;
  private final String currentUser;

  public HostEventDecorator(EventRenderer wrapped, String currentUser) {
    this.wrapped = wrapped;
    this.currentUser = currentUser;
  }

  @Override
  public void render(Graphics g, EventModel event, Rectangle rect) {
    if (event.getHostId().equals(currentUser)) {
      Color transparent = new Color(35, 200, 225, 128);
      g.setColor(transparent);
      g.fillRect(rect.x, rect.y, rect.width, rect.height);
    } else {
      wrapped.render(g, event, rect);
    }
  }
}

