package cellsociety.visualization;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Simulation extends Application {

  public static final String STYLESHEET = "/visualization_resources/myStyles.css";
  public static final String INITIAL_STATES = "initial_states";
  public static final String PROPERTY_LISTS = "property_lists";
  public static final String PROPERTIES = "visualization_resources.visualization";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double INITIAL_HEIGHT = 600;
  public static final double INITIAL_WIDTH = 800;
  public static final double FRAMES_PER_SECOND = 2;
  public static final double SIMULATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final Color ALIVE_COLOR = Color.BLACK;
  public static final Color DEAD_COLOR = Color.WHITE;

  // TODO: Make it so these can be edited
  private double GRID_SIZE = 3 * INITIAL_WIDTH / 4;
  private double GRID_UPPER_LEFT_CORNER = INITIAL_WIDTH / 8;

  private Scene myScene;
  private BorderPane myRoot;
  private Grid myGrid;
  private Group myGridGroup;
  private ResourceBundle myResources;
  private Timeline myAnimation;
  private PropertyReader myPropertyReader;
  private ComboBox<String> mySimulations;

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle(HEADER);
    myScene = setupScene(INITIAL_WIDTH, INITIAL_HEIGHT);
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
    Button myRestartButton = makeButton("RestartButton", event -> restart());
    navigationPane.getChildren().add(myRestartButton);
    Button myPlayButton = makeButton("PlayButton", event -> play());
    navigationPane.getChildren().add(myPlayButton);
    Button myPauseButton = makeButton("PauseButton", event -> pause());
    navigationPane.getChildren().add(myPauseButton);
    Button myStepButton = makeButton("StepButton", event -> step());
    navigationPane.getChildren().add(myStepButton);
    Button mySaveSimulationButton = makeButton("SavedSimulation", event -> saveSimulation());
    navigationPane.getChildren().add(mySaveSimulationButton);
    Button mySpeedUpButton = makeButton("SpeedUp", event -> speedUp());
    navigationPane.getChildren().add(mySpeedUpButton);
    Button mySlowDownButton = makeButton("SlowDown", event -> slowDown());
    navigationPane.getChildren().add(mySlowDownButton);
    Button mySetColorsButton = makeButton("SetColors", event -> setColors());
    navigationPane.getChildren().add(mySetColorsButton);
    Button mySetPhotosButton = makeButton("SetPhotos", event -> setPhotos());
    navigationPane.getChildren().add(mySetPhotosButton);
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
          Objects.requireNonNull(Simulation.class.getClassLoader().getResource(PROPERTY_LISTS))
              .toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for (File path : Objects.requireNonNull(simulations.toFile().listFiles())) {
      for (File file : path.listFiles()) {
        if (file.getName().split("\\.").length > 0 && !path.getName().equals("TestProperties")) {
          myPropertyReader = new PropertyReader(
              PROPERTY_LISTS + "/" + path.getName() + "/" + file.getName());
          mySimulations.getItems().add(file.getName().split("\\.")[0] + " - " + myPropertyReader
              .getProperty("simulationType"));
        }
      }
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
    myAnimation.stop();
    chooseSimulation();
  }

  private void play() {
    myAnimation.play();
  }

  private void pause() {
    myAnimation.pause();
  }

  private void step() {
    myGrid.updateNewStates();
    visualizeGrid();
  }

  private void speedUp() {
    myAnimation.setRate(myAnimation.getRate() * 2);
    myRoot.setLeft(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      myRoot.setLeft(null);
    }
  }

  private void slowDown() {
    myAnimation.setRate(myAnimation.getRate() / 2);
    myRoot.setLeft(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      myRoot.setLeft(null);
    }
  }

  private void setColors() {

  }

  private void setPhotos() {

  }

  // TODO: Edit property reader input when merging
  private void chooseSimulation() {
    String pathName =
        PROPERTY_LISTS + "/" + mySimulations.getValue().split(" ")[2] + "/" + mySimulations
            .getValue().split(" ")[0] + ".properties";
    myPropertyReader = new PropertyReader(pathName);

    myGrid = myPropertyReader.gridFromPropertyFile();
    visualizeGrid();
  }

  private void visualizeGrid() {
    myGridGroup.getChildren().clear();
    double x = GRID_UPPER_LEFT_CORNER;
    double y = GRID_UPPER_LEFT_CORNER;
    int cellLabel = 0;
    double cellSize = GRID_SIZE / myGrid.getMyCells().size();
    for (List<Cell> row : myGrid.getMyCells()) {
      for (Cell cell : row) {
        visualizeCell(x, y, cellLabel, cellSize, cell);
        x += cellSize;
        cellLabel++;
      }
      y += cellSize;
      x = GRID_UPPER_LEFT_CORNER;
    }
  }

  // TODO: Need to generalize how to create a grid
  private void visualizeCell(double x, double y, int cellLabel, double cellSize, Cell cell) {
    Rectangle cellRectangle = new Rectangle(x, y, cellSize, cellSize);
    cellRectangle.setId("cell" + cellLabel);
    cellRectangle.setOnMouseClicked(e -> setCellState(cellRectangle));
    if (cell.getMyState().toString().equals("DEAD")) {
      cellRectangle.setFill(DEAD_COLOR);
      cellRectangle.setStroke(ALIVE_COLOR);
    } else {
      cellRectangle.setFill(ALIVE_COLOR);
      cellRectangle.setStroke(DEAD_COLOR);
    }
    myGridGroup.getChildren().add(cellRectangle);
  }

  private void setCellState(Rectangle cellRectangle) {
    // TODO: Figure out how to access specific cell state

  }

  private void saveSimulation() {
    JFrame saver = new JFrame();
    String filename = JOptionPane.showInputDialog(saver, "SaveSimulationAs");
    String author = JOptionPane.showInputDialog(saver, "Author");
    String description = JOptionPane.showInputDialog(saver, "Description");

    String simType = myPropertyReader.getProperty("simulationType");

    String[] keySet = new String[]{"simulationType", "simulationTitle", "configAuthor", "description", "csvFile"};

    myGrid.saveCurrentGrid("data/" + INITIAL_STATES + "/" + simType + "/" + filename + ".csv");
    File file = new File("data/" + PROPERTY_LISTS + "/" + simType + "/" + filename + ".properties");
    try {
      FileWriter outputFile = new FileWriter(file,false);
      Properties savedProperty = new Properties();
      savedProperty.put("simulationType", simType);
      savedProperty.put("simulationTitle", filename);
      savedProperty.put("configAuthor", author);
      savedProperty.put("description", description);
      savedProperty.put("csvFile", filename + ".csv");
      savedProperty.store(outputFile, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    mySimulations.getItems().add(filename + " - " + simType);
  }

  // Necessary for testing
  public Timeline getAnimation() {
    return myAnimation;
  }

  public static void main(String[] args) {
    launch(args);
  }

}
