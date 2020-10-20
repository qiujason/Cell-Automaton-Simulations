package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cellsociety.visualization.Visualization;
import java.util.ResourceBundle;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class VisualizationTest extends DukeApplicationTest {

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
  private ComboBox<String> myStyles;
  private ComboBox<String> myLanguages;
  private Group myGridGroup;

  @Override
  public void start(Stage stage) {
    Visualization myVisualization = new Visualization();
    myResources = ResourceBundle.getBundle(Visualization.LANGUAGE_FOLDER + "\\." + Visualization.DEFAULT_LANGUAGE);
    try {
      myVisualization.start(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
    myAnimation = myVisualization.getAnimation();

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
    myStyles = lookup("#SetStyle").query();
    myLanguages = lookup("#SetLanguage").query();
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
    assertEquals(myResources.getString("SetStyle"), myStyles.getPromptText());
    assertEquals(myResources.getString("SetLanguage"), myLanguages.getPromptText());
  }

  @Test
  public void testBlockConfiguration() {
    select(mySimulations, "Beacon - GameOfLife");
    assertEquals(36, myGridGroup.getChildren().size());
    select(mySimulations, "Blinker - GameOfLife");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "AllBlocked - Percolation");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "StraightLine - Percolation");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "RockDominant - RPS");
    assertEquals(36, myGridGroup.getChildren().size());
    select(mySimulations, "Checkerboard - Segregation");
    assertEquals(36, myGridGroup.getChildren().size());
    select(mySimulations, "Minimal - Segregation");
    assertEquals(16, myGridGroup.getChildren().size());
    select(mySimulations, "CenterOnFireWithSpace - SpreadingFire");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "OneOnFire - SpreadingFire");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "AllFish - Wator");
    assertEquals(25, myGridGroup.getChildren().size());
    select(mySimulations, "FishSharkAlternation - Wator");
    assertEquals(36, myGridGroup.getChildren().size());
  }

  @Test
  public void testStepButton() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(myStepButton);
    Rectangle cellRectangle = lookup("#cell14").query();
    assertEquals(Color.WHITE, cellRectangle.getFill());
  }

  @Test
  public void testRestartButton() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(myStepButton);
    clickOn(myRestartButton);
    Rectangle cellRectangle = lookup("#cell14").query();
    assertEquals(Color.BLACK, cellRectangle.getFill());
    assertEquals(myAnimation.statusProperty().getValue(), Status.STOPPED);
  }

  @Test
  public void testPlayButton() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(myPlayButton);
    assertEquals(myAnimation.statusProperty().getValue(), Status.RUNNING);
  }

  @Test
  public void testPauseButton() {
    select(mySimulations, "Beacon - GameOfLife");
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
  public void testSetCellState() {
    select(mySimulations, "Beacon - GameOfLife");
    Rectangle cellRectangle = lookup("#cell14").query();
    clickOn(cellRectangle);
    cellRectangle = lookup("#cell14").query();
    assertEquals(Color.WHITE, cellRectangle.getFill());
  }

  @Test
  public void testStyleChange() {
    assertEquals(Color.BLACK, myRestartButton.getTextFill());
    select(myStyles, "DarkMode");
    myRestartButton = lookup("#RestartButton").query();
    assertEquals(Color.WHITE, myRestartButton.getTextFill());
  }

  @Test
  public void testLanguageChange() {
    select(myLanguages, "Español");
    myRestartButton = lookup("#RestartButton").query();
    assertEquals("Reiniciar", myRestartButton.getText());
  }

  @Test
  public void testSaveSimulation() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(mySaveSimulationButton);
    press(KeyCode.J);
    press(KeyCode.C);
    press(KeyCode.E);
    press(KeyCode.ENTER);
    press(KeyCode.ENTER);
    press(KeyCode.ENTER);
    mySimulations = lookup("#SimulationSelect").query();
    assertTrue(mySimulations.getItems().contains("jce - GameOfLife"));
  }

  @Test
  public void testSetColor() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(mySetColorsButton);
    press(KeyCode.R);
    press(KeyCode.E);
    press(KeyCode.D);
    press(KeyCode.ENTER);
    press(KeyCode.ENTER);
    Rectangle cellRectangle = lookup("#cell14").query();
    clickOn(cellRectangle);
    cellRectangle = lookup("#cell14").query();
    clickOn(cellRectangle);
    assertEquals(Color.RED, cellRectangle.getFill());
  }

  @Test
  public void testSetPhoto() {
    select(mySimulations, "Beacon - GameOfLife");
    clickOn(mySetColorsButton);
    press(KeyCode.F);
    press(KeyCode.I);
    press(KeyCode.S);
    press(KeyCode.H);
    press(KeyCode.PERIOD);
    press(KeyCode.J);
    press(KeyCode.P);
    press(KeyCode.G);
    press(KeyCode.ENTER);
    press(KeyCode.ENTER);
    Rectangle cellRectangle = lookup("#cell14").query();
    clickOn(cellRectangle);
    cellRectangle = lookup("#cell14").query();
    clickOn(cellRectangle);
    assertEquals("javafx.scene.paint.ImagePattern@4206a205", cellRectangle.getFill().toString());
  }

}