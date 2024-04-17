package cs3500.planner.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.SwingConstants;

import java.awt.Rectangle;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cs3500.planner.controller.IScheduleFeatures;
import cs3500.planner.model.Event;
import cs3500.planner.model.EventModel;

/**
 * CentralSystemFrame to create the central system gui grid that displays the schedule.
 */
public class CentralSystemFrame extends JFrame implements CentralSystemView {
  private IScheduleFeatures controller;
  private JPanel schedulePanel;
  private JComboBox<String> userDropDown;
  private final List<EventModel> events;
  private final Map<Rectangle, EventModel> eventRectangles;
  private final Point dragStart;
  private final Point dragEnd;

  /**
   * Constructor for the CentralSystemFrame.
   */
  public CentralSystemFrame() {
    super("Planner Central System");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());
    events = new ArrayList<>();
    eventRectangles = new HashMap<>();
    EventFrame currentFrame = null;
    dragStart = null;
    dragEnd = null;
    setController(controller);
    initializeMenu();
    initializeSchedulePanel();
    eventListener();
    this.pack();
    this.setVisible(true);
  }

  /**
   * Sets the controller that this view should interact with. The controller
   * acts as the intermediary between the view and the model, handling user
   * actions, updating the model, and reflecting changes in the view.
   *
   * @param controller The controller to set for this view.
   */
  public void setController(IScheduleFeatures controller) {
    if (controller != null) {
      this.controller = controller;
    }
  }

  /**
   * Triggers a repaint of this frame and its schedule panel. This method is
   * overridden to ensure that both the frame and the schedule panel are
   * repainted whenever a repaint is requested.
   */
  @Override
  public void repaint() {
    super.repaint();
    if (schedulePanel != null) {
      schedulePanel.repaint();
    }
  }

  /**
   * Updates the view based on the current state of the model. This includes
   * updating the user drop-down to reflect the current users and their schedules.
   * If a user is selected, their schedule is loaded and displayed. This method
   * handles the user selection logic and updates the display accordingly.
   */
  @Override
  public void updateView() {
    //get the user and handle null users
    Object selectedItem = userDropDown.getSelectedItem();
    String selectedUser = (selectedItem != null) ? selectedItem.toString() : null;
    //remove action listener
    ActionListener[] listeners = userDropDown.getActionListeners();
    for (ActionListener listener : listeners) {
      userDropDown.removeActionListener(listener);
    }
    //update drop-down
    userDropDown.removeAllItems();
    userDropDown.addItem("<none>");
    controller.addDropDown(userDropDown);
    //restore action listener
    for (ActionListener listener : listeners) {
      userDropDown.addActionListener(listener);
    }
    //set the selected item if it exists
    if (selectedUser != null && Objects.equals(userDropDown.getItemCount(), selectedUser)) {
      userDropDown.setSelectedItem(selectedUser);
    }
    //load the schedule if its valid
    if (selectedUser != null && !"<none>".equals(selectedUser)) {
      controller.switchUser(selectedUser);
      //loadUserSchedule(selectedUser);
    } else {
      events.clear();
      schedulePanel.repaint();
    }
  }

  /**
   * Displays an error message to the user.
   *
   * @param message The error message to be displayed.
   */
  @Override
  public void displayError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Shows a generic message to the user, typically for information or confirmation.
   *
   * @param message The message to be displayed.
   */
  @Override
  public void showMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Finalizes the initialization of the view components and makes the view visible.
   * This method is typically called after all view components have been created and
   * configured but just before the user interface is displayed to the user.
   */
  @Override
  public void finalizeInitialization() {
    eventListener();
    initializeControlPanel();
    this.pack();
    this.setVisible(true);
  }

  //helper method to initialize the menu bar
  private void initializeMenu() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem loadMenuItem = new JMenuItem("Add calendar");
    loadMenuItem.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int option = fileChooser.showOpenDialog(CentralSystemFrame.this);
      if (option == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();
        controller.loadXMLAction(selectedFile);
      }
    });
    JMenuItem saveMenuItem = new JMenuItem("Save calendars");
    saveMenuItem.addActionListener(e -> controller.saveSchedules());

    fileMenu.add(loadMenuItem);
    fileMenu.add(saveMenuItem);
    menuBar.add(fileMenu);
    this.setJMenuBar(menuBar);
  }

  //helper method to initialize the schedule panel
  private void initializeSchedulePanel() {
    schedulePanel = new JPanel() {
      protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        drawGrid(graphics);
        drawEvents(graphics);
        drawSelection(graphics);
      }
    };
    schedulePanel.setPreferredSize(new Dimension(800, 600));
    this.add(schedulePanel, BorderLayout.CENTER);
  }

  //helper method to draw the grid
  private void drawGrid(Graphics graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    int rows = 24;
    int cols = 7;
    int cellWidth = schedulePanel.getWidth() / cols;
    int cellHeight = schedulePanel.getHeight() / rows;
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        graphics.drawRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
      }
      if (i % 4 == 0) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, i * cellHeight - 1, schedulePanel.getWidth(), 2);
      }
    }
  }

  //helper method to draw the events
  private void drawEvents(Graphics graphics) {
    eventRectangles.clear();
    int cellWidth = schedulePanel.getWidth() / 7;
    int cellHeight = schedulePanel.getHeight() / 24;
    graphics.setColor(Color.RED);
    for (EventModel event : events) {
      LocalDateTime startTime = event.getStartTime();
      LocalDateTime endTime = event.getEndTime();
      int dayCol = (startTime.getDayOfWeek().getValue() == 7) ? 0 :
              startTime.getDayOfWeek().getValue();
      int startHour = startTime.getHour();
      int endHour = endTime.getHour();
      long daySpan = java.time.temporal.ChronoUnit.DAYS.between(
              startTime.toLocalDate(), endTime.toLocalDate());
      for (long day = 0; day <= daySpan; day++) {
        int column = (dayCol + (int) day) % 7;
        int startY = startHour * cellHeight;
        int endY = (day < daySpan) ? 24 * cellHeight : endHour * cellHeight;
        int rectHeight = endY - startY;
        graphics.fillRect(column * cellWidth, startY, cellWidth, rectHeight);
        Rectangle eventRect = new Rectangle(column * cellWidth, startY, cellWidth, rectHeight);
        eventRectangles.put(eventRect, event);
        startHour = 0;
      }
    }
  }

  //helper method to create the bottom panel and buttons
  private void initializeControlPanel() {
    JPanel controlPanel = new JPanel(new GridLayout(1, 0, 5, 0));
    JButton loadButton = new JButton("Create event");
    JButton scheduleButton = new JButton("Schedule event");
    //takes list of user-names to pick from
    userDropDown = new JComboBox<>();
    userDropDown.addItem("<none>");
    controller.addDropDown(userDropDown);
    //In CentralSystemFrame, where you initialize the JComboBox
    userDropDown.addActionListener(e -> {
      String selectedUser = (String) userDropDown.getSelectedItem();
      if (selectedUser != null && !"<none>".equals(selectedUser)) {
        events.clear();
        events.addAll(controller.switchUser(selectedUser));
        controller.setCurrentUser(selectedUser);
        schedulePanel.repaint();
      }
    });
    loadButton.addActionListener(e -> {
      controller.openEventFrame();
    });
    scheduleButton.addActionListener(e -> controller.openScheduleFrame());
    controlPanel.add(userDropDown);
    controlPanel.add(loadButton);
    controlPanel.add(scheduleButton);
    loadButton.setHorizontalAlignment(SwingConstants.CENTER);
    scheduleButton.setHorizontalAlignment(SwingConstants.CENTER);
    this.add(controlPanel, BorderLayout.SOUTH);
  }

  //helper method that adds a mouse listener to the schedule panel. if a user clicks
  //on an event, it will open the event details window
  private void eventListener() {
    schedulePanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent mouseEvent) {
        for (Map.Entry<Rectangle, EventModel> entry : eventRectangles.entrySet()) {
          if (entry.getKey().contains(mouseEvent.getPoint())) {
            EventModel event = entry.getValue();
            controller.openEventDetails(event);
            break;
          }
        }
      }
    });
  }

  //helper method to draw the selected rectangle
  private void drawSelection(Graphics g) {
    if (dragStart != null && dragEnd != null) {
      g.setColor(Color.BLUE);
      int x = Math.min(dragStart.x, dragEnd.x);
      int y = Math.min(dragStart.y, dragEnd.y);
      int width = Math.abs(dragStart.x - dragEnd.x);
      int height = Math.abs(dragStart.y - dragEnd.y);
      g.drawRect(x, y, width, height);
    }
  }
}