package cs3500.planner.view;

import java.awt.Graphics;
import java.awt.Rectangle;

import cs3500.planner.model.EventModel;

public interface EventRenderer {
  void render(Graphics g, EventModel event, Rectangle rect);
}

