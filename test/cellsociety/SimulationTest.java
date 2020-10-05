package cellsociety;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class SimulationTest extends DukeApplicationTest {

  private Simulation mySimulation;
  private Scene myScene;
  private Grid myGrid;
  private ResourceBundle myResources;

  private Button myRestartButton;
  private Button myPlayButton;
  private Button myPauseButton;
  private Button myStepButton;
  private Button mySimulationSelectButton;

  @Override
  public void start(Stage stage) throws Exception {
    mySimulation = new Simulation();
    myResources = ResourceBundle.getBundle(Simulation.PROPERTIES);

    myScene = mySimulation.setupScene(Simulation.WIDTH, Simulation.HEIGHT);
    stage.setScene(myScene);
    stage.show();

    myRestartButton = lookup("#RestartButton").query();
    myPlayButton = lookup("#PlayButton").query();
    myPauseButton = lookup("#PauseButton").query();
    myStepButton = lookup("#StepButton").query();
    mySimulationSelectButton = lookup("#SimulationSelect").query();
  }

  @Test
  public void testInitialDisplay() {
    assertEquals(myResources.getString("RestartButton"), myRestartButton.getText());
    assertEquals(myResources.getString("PlayButton"), myPlayButton.getText());
    assertEquals(myResources.getString("PauseButton"), myPauseButton.getText());
    assertEquals(myResources.getString("StepButton"), myStepButton.getText());
    assertEquals(myResources.getString("SimulationSelect"), mySimulationSelectButton.getText());
  }

}