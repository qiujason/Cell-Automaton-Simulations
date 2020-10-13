package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.visualization.Simulation;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class VisualizationTest extends DukeApplicationTest {

  private Simulation mySimulation;
  private Scene myScene;
  private Timeline myAnimation;
  private ResourceBundle myResources;

  private Button myRestartButton;
  private Button myPlayButton;
  private Button myPauseButton;
  private Button myStepButton;
  private Button mySaveSimulationButton;
  private Button mySpeedUpButton;
  private Button mySlowDownButton;
  private Button mySetColorsButton;
  private Button mySetPhotosButton;
  private ComboBox<String> mySimulations;
  private Group myGridGroup;

  @Override
  public void start(Stage stage) throws Exception {
    mySimulation = new Simulation();
    myResources = ResourceBundle.getBundle(Simulation.PROPERTIES);

    myScene = mySimulation.setupScene(Simulation.INITIAL_WIDTH, Simulation.INITIAL_HEIGHT);
    stage.setScene(myScene);
    stage.show();

    myAnimation = mySimulation.getAnimation();

    myRestartButton = lookup("#RestartButton").query();
    myPlayButton = lookup("#PlayButton").query();
    myPauseButton = lookup("#PauseButton").query();
    myStepButton = lookup("#StepButton").query();
    mySaveSimulationButton = lookup("#SavedSimulation").query();
    mySpeedUpButton = lookup("#SpeedUp").query();
    mySlowDownButton = lookup("#SlowDown").query();
    mySetColorsButton = lookup("#SetColors").query();
    mySetPhotosButton = lookup("#SetPhotos").query();
    mySimulations = lookup("#SimulationSelect").query();
    myGridGroup = lookup("#GridGroup").query();
  }

  @Test
  public void testInitialDisplay() {
    assertEquals(myResources.getString("RestartButton"), myRestartButton.getText());
    assertEquals(myResources.getString("PlayButton"), myPlayButton.getText());
    assertEquals(myResources.getString("PauseButton"), myPauseButton.getText());
    assertEquals(myResources.getString("StepButton"), myStepButton.getText());
    assertEquals(myResources.getString("SavedSimulation"), mySaveSimulationButton.getText());
    assertEquals(myResources.getString("SimulationSelect"), mySimulations.getPromptText());
    assertEquals(myResources.getString("SpeedUp"), mySpeedUpButton.getText());
    assertEquals(myResources.getString("SlowDown"), mySlowDownButton.getText());
    assertEquals(myResources.getString("SetColors"), mySetColorsButton.getText());
    assertEquals(myResources.getString("SetPhotos"), mySetPhotosButton.getText());
  }

  @Test
  public void testBlockConfiguration() {
    select(mySimulations, "Beacon");
    assertEquals(36, myGridGroup.getChildren().size());
    select(mySimulations, "Blinker");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "Boat");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "Corner");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "Edges");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "Square");
    assertEquals(4, myGridGroup.getChildren().size());
    select(mySimulations, "Toad");
    assertEquals(36, myGridGroup.getChildren().size());
    select(mySimulations, "Tub");
    assertEquals(25, myGridGroup.getChildren().size());
  }

  @Test
  public void testStepButton() {
    select(mySimulations, "Beacon");
    clickOn(myStepButton);
    Rectangle cellRectangle = lookup("#cell14").query();
    assertEquals(Simulation.DEAD_COLOR, cellRectangle.getFill());
  }

  @Test
  public void testRestartButton() {
    select(mySimulations, "Beacon");
    clickOn(myStepButton);
    clickOn(myRestartButton);
    Rectangle cellRectangle = lookup("#cell14").query();
    assertEquals(Simulation.ALIVE_COLOR, cellRectangle.getFill());
    assertEquals(myAnimation.statusProperty().getValue(), Status.STOPPED);
  }

  @Test
  public void testPlayButton() {
    select(mySimulations, "Beacon");
    clickOn(myPlayButton);
    assertEquals(myAnimation.statusProperty().getValue(), Status.RUNNING);
  }

  @Test
  public void testPauseButton() {
    select(mySimulations, "Beacon");
    clickOn(myPlayButton);
    assertEquals(myAnimation.statusProperty().getValue(), Status.RUNNING);
    clickOn(myPauseButton);
    assertEquals(myAnimation.statusProperty().getValue(), Status.PAUSED);
  }

  @Test
  public void testSpeedUpButton() {
    clickOn(mySpeedUpButton);
    assertEquals(2, myAnimation.getRate());
  }

  @Test
  public void testSlowDownButton() {
    clickOn(mySlowDownButton);
    assertEquals(0.5, myAnimation.getRate());
  }

  @Test
  public void testSaveSimulation() {
    select(mySimulations, "Beacon");
    clickOn(mySaveSimulationButton);
    press(KeyCode.J);
    press(KeyCode.ENTER);
    press(KeyCode.J);
    press(KeyCode.ENTER);
    press(KeyCode.J);
    press(KeyCode.ENTER);
    assertTrue(mySimulations.getItems().contains("j"));
  }

}