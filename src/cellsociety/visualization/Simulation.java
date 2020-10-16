package cellsociety.visualization;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cell;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
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
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
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
  private List<Enum<?>> myStates;
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
              PROPERTY_LISTS + "/" + path.getName() + "/" + file.getName()); // TODO: Why
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
    getSimulationStates();
    JFrame saver = new JFrame();
    for(Enum<?> state : myStates){
      String newColor = JOptionPane.showInputDialog(saver, "Input Color for State " + state.toString());
      myPropertyReader.setProperty(state.toString(), newColor);
    }
    visualizeGrid();
  }

  private void setPhotos() {
    getSimulationStates();
    JFrame saver = new JFrame();
    for(Enum<?> state : myStates){
      String newPhoto = JOptionPane.showInputDialog(saver, "Choose photo for State " + state.toString());
      myPropertyReader.setProperty(state.toString(), newPhoto);
    }
    visualizeGrid();
  }

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

  private void visualizeCell(double x, double y, int cellLabel, double cellSize, Cell cell) {
    Rectangle cellRectangle = new Rectangle(x, y, cellSize, cellSize);
    cellRectangle.setId("cell" + cellLabel);
    cellRectangle.setOnMouseClicked(e -> setCellState(cellLabel));
    Enum<?> currentState = cell.getMyState();
    String myFillAsString = myPropertyReader.getProperty(currentState.toString());
    if(myFillAsString.split("\\.").length > 1){
      Image stateImage = new Image("visualizationResources/images/" + myFillAsString);
      ImagePattern stateImagePattern = new ImagePattern(stateImage);
      cellRectangle.setFill(stateImagePattern);
    }
    Color myColor = Color.valueOf(myFillAsString);
    cellRectangle.setFill(myColor);
    cellRectangle.setStroke(Color.WHITE);
    myGridGroup.getChildren().add(cellRectangle);
  }

  private void setCellState(int cellLabel) {
    int row = cellLabel / myGrid.getMyCells().get(0).size();
    int column = cellLabel % myGrid.getMyCells().get(0).size();
    Cell myCell = myGrid.getMyCells().get(row).get(column);
    for(int i = 0; i < myStates.size() - 1; i++){
      if(myCell.getMyState().equals(myStates.get(0))){
        myCell.setMyState(myStates.get(i+1));
        visualizeGrid();
        return;
      }
    }
    myCell.setMyState(myStates.get(0));
    visualizeGrid();
  }

  private void getSimulationStates() {
    String simType = myPropertyReader.getProperty("simulationType");
    Class<?> clazz = null;

    try {
      clazz = Class.forName("cellsociety.model." + simType + "." + simType + "States");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if (clazz != null) {
      for (Object state : clazz.getEnumConstants()){
        myStates.add((Enum<?>) state);
      }
    }
  }

  private void saveSimulation() {
    JFrame saver = new JFrame();
    String filename = JOptionPane.showInputDialog(saver, "SaveSimulationAs");
    String author = JOptionPane.showInputDialog(saver, "Author");
    String description = JOptionPane.showInputDialog(saver, "Description");

    String simType = myPropertyReader.getProperty("simulationType");

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
