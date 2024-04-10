package controllertests;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import cs3500.planner.controller.ScheduleController;
import cs3500.planner.model.CentralSystemStub;
import cs3500.planner.view.CentralSystemFrameStub;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScheduleControllerTest {
  private ScheduleController controller;
  private CentralSystemFrameStub view;
  private CentralSystemStub model;

  @Before
  public void setUp() {
    model = new CentralSystemStub();
    view = new CentralSystemFrameStub(model);
    controller = new ScheduleController(view);
  }

  @Test
  public void testLoadXMLAction_Success() {
    model.simulateLoadEventsSuccess();
    File mockFile = new File("CSStudent.xml");
    controller.loadXMLAction(mockFile);
    assertTrue(view.isViewUpdated());

  }

  @Test
  public void testLoadXMLAction_Failure() {
    model.simulateLoadEventsFailure();
    File mockFile = new File("invalid.xml");
    controller.loadXMLAction(mockFile);
    assertFalse(view.isViewUpdated());
  }

  @Test
  public void testSaveSchedules_Success() {
    view.updateView();
    controller.saveSchedules();
    view.isErrorDisplayed("Schedules saved successfully.");
  }

}
