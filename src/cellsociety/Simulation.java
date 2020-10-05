package cellsociety;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Simulation extends Application {

  public static final String STYLESHEET = "/myStyles.css";
  public static final String INITIAL_STATES = "initial_states";
  public static final String PROPERTIES = "SimulationButtons";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double HEIGHT = 600;
  public static final double WIDTH = 600;

  private Scene myScene;
  private BorderPane myRoot;
  private Grid myGrid;
  private ResourceBundle myResources;

  private Button myRestartButton;
  private Button myPlayButton;
  private Button myPauseButton;
  private Button myStepButton;
  private ComboBox<String> mySimulations;

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle(HEADER);
    myScene = setupScene(WIDTH, HEIGHT);
    stage.setScene(myScene);
    stage.show();
  }

  public Scene setupScene(double width, double height) {
    myResources = ResourceBundle.getBundle(PROPERTIES);
    myRoot = new BorderPane();

    myRoot.setTop(makeNavigationPane());
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

    return scene;
  }

  private void updateGrid() {
    Group gridGroup = new Group();
    // FILL IN
    // Use some geometry for scaling
    // Use updateNewStates() from Grid
    // Use black for 1 and white for 0
    myRoot.setCenter(gridGroup);
  }

  private Node makeNavigationPane() {
    HBox navigationPane = new HBox();
    myRestartButton = makeButton("RestartButton", event -> restart());
    navigationPane.getChildren().add(myRestartButton);
    myPlayButton = makeButton("PlayButton", event -> play());
    navigationPane.getChildren().add(myPlayButton);
    myPauseButton = makeButton("PauseButton", event -> pause());
    navigationPane.getChildren().add(myPauseButton);
    myStepButton = makeButton("StepButton", event -> step());
    navigationPane.getChildren().add(myStepButton);
    initializeSimulationOptions();
    navigationPane.getChildren().add(mySimulations);
    return navigationPane;
  }

  private void initializeSimulationOptions() {
    mySimulations = new ComboBox<>();
    mySimulations.setId("SimulationSelect");
    mySimulations.setOnAction(event -> {
      chooseSimulation();
    });
    mySimulations.setPromptText(myResources.getString("SimulationSelect"));
    Path simulations = null;
    try {
      simulations = Paths.get(
          Objects.requireNonNull(Simulation.class.getClassLoader().getResource(INITIAL_STATES))
              .toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for (File file : Objects.requireNonNull(simulations.toFile().listFiles())) {
      mySimulations.getItems().add(file.getName().split("\\.")[0]);
    }
  }

  private Button makeButton(String buttonName, EventHandler<ActionEvent> buttonAction) {
    Button newButton = new Button();
    String buttonLabel = myResources.getString(buttonName);
    newButton.setText(buttonLabel);
    newButton.setId(buttonName);
    newButton.setOnAction(buttonAction);
    return newButton;
  }

  private void restart() {
    chooseSimulation();
  }

  private void play() {

  }

  private void pause() {

  }

  private void step() {

  }

  private void chooseSimulation() {
    String pathName = "/" + mySimulations.getValue() + ".csv";
    Path pathToSimulation = null;

    try {
      pathToSimulation = Paths.get(
          Objects.requireNonNull(Simulation.class.getClassLoader().getResource(pathName)).toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    try {
      myGrid = new Grid(pathToSimulation);
    } catch (IOException e) {
      e.printStackTrace();
    }
    updateGrid();
  }

}
