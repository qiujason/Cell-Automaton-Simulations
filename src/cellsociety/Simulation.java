package cellsociety;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation extends Application {

  public static final String STYLESHEET = "/myStyles.css";
  public static final String INITIAL_STATES = "initial_states";
  public static final String PROPERTIES = "SimulationButtons";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double HEIGHT = 600;
  public static final double WIDTH = 600;
  public static final double GRID_SIZE = 3 * WIDTH / 4;
  public static final double GRID_UPPER_LEFT_CORNER = WIDTH / 8;
  public static final double FRAMES_PER_SECOND = 2;
  public static final double SIMULATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final Color ALIVE_COLOR = Color.BLACK;
  public static final Color DEAD_COLOR = Color.WHITE;

  private Scene myScene;
  private BorderPane myRoot;
  private Grid myGrid;
  private Group myGridGroup;
  private ResourceBundle myResources;
  private Timeline myAnimation;

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
    myGridGroup = new Group();
    myGridGroup.setId("GridGroup");
    myRoot.setCenter(myGridGroup);

    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(SIMULATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(frame);

    myRoot.setTop(makeNavigationPane());
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

    return scene;
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
    myAnimation.play();
  }

  private void pause() {
    myAnimation.stop();
  }

  private void step() {
    myGrid.updateNewStates();
    updateGrid();
  }

  private void chooseSimulation() {
    String pathName = INITIAL_STATES + "/" + mySimulations.getValue() + ".csv";
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

  private void updateGrid() {
    myGridGroup.getChildren().clear();
    double x = GRID_UPPER_LEFT_CORNER;
    double y = GRID_UPPER_LEFT_CORNER;
    int cellLabel = 0;
    double cellSize = GRID_SIZE / myGrid.getMyCells().size();
    for (List<Cell> row : myGrid.getMyCells()) {
      for (Cell cell : row) {
        Rectangle cellRectangle = new Rectangle(x, y, cellSize, cellSize);
        cellRectangle.setId("cell" + cellLabel);
        if (cell.getMyValue() == 0) {
          cellRectangle.setFill(DEAD_COLOR);
          cellRectangle.setStroke(ALIVE_COLOR);
        } else {
          cellRectangle.setFill(ALIVE_COLOR);
          cellRectangle.setStroke(DEAD_COLOR);
        }
        myGridGroup.getChildren().add(cellRectangle);
        x += cellSize;
        cellLabel++;
      }
      y += cellSize;
      x = GRID_UPPER_LEFT_CORNER;
    }
  }

  public static void main(String[] args) {
    launch(args);
  }

}
