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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Simulation extends Application {

  public static final String STYLESHEET_FOLDER = "visualization_resources/stylesheets/";
  public static final String INITIAL_STATES = "initial_states";
  public static final String PROPERTY_LISTS = "property_lists";
  public static final String LANGUAGE_PROPERTIES = "visualization_resources.english";
  public static final String BUTTON_PROPERTIES = "visualization_resources.buttons";
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
  private ResourceBundle myLanguageResources;
  private Timeline myAnimation;
  private PropertyReader myPropertyReader;
  private List<Enum<?>> myStates;
  private ComboBox<String> mySimulations;
  private ComboBox<String> myStyles;

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle(HEADER);
    myScene = setupScene(INITIAL_WIDTH, INITIAL_HEIGHT);
    stage.setScene(myScene);
    stage.show();
  }

  public Scene setupScene(double width, double height) {
    myLanguageResources = ResourceBundle.getBundle(LANGUAGE_PROPERTIES);
    myRoot = new BorderPane();
    myGridGroup = new Group();
    myGridGroup.setId("GridGroup");
    myRoot.setCenter(myGridGroup);

    myAnimation = new Timeline();
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(SIMULATION_DELAY), e -> step());
    myAnimation.getKeyFrames().add(frame);

    myRoot.setLeft(makeNavigationPane());
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(getClass().getResource("/" + STYLESHEET_FOLDER + "lightMode.css").toExternalForm());

    return scene;
  }

  private Node makeNavigationPane() {
    VBox navigationPane = new VBox();
    makeButton("RestartButton", event -> restart(), navigationPane);
    makeButton("PlayButton", event -> play(), navigationPane);
    makeButton("PauseButton", event -> pause(), navigationPane);
    makeButton("StepButton", event -> step(), navigationPane);
    makeButton("SavedSimulation", event -> saveSimulation(), navigationPane);
    makeButton("SpeedUp", event -> speedUp(), navigationPane);
    makeButton("SlowDown", event -> slowDown(), navigationPane);
    makeButton("SetColors", event -> setColors(), navigationPane);
    makeButton("SetPhotos", event -> setPhotos(), navigationPane);
//    ResourceBundle buttons = ResourceBundle.getBundle(BUTTON_PROPERTIES);
//    for(String button : buttons.keySet()){
//      navigationPane.getChildren().add(
//          makeButton(button, event -> {
//            try {
//              this.getClass().getMethod(buttons.getString(button));
//            } catch (NoSuchMethodException e) {
//              e.printStackTrace();
//            }
//          }));
//    }
    initializeSimulationOptions();
    mySimulations.getStyleClass().add("button");
    navigationPane.getChildren().add(mySimulations);

    initializeStyleOptions();
    myStyles.getStyleClass().add("button");
    navigationPane.getChildren().add(myStyles);

    return navigationPane;
  }

  private void initializeSimulationOptions() {
    mySimulations = new ComboBox<>();
    mySimulations.setId("SimulationSelect");
    mySimulations.setOnAction(event -> {
      chooseSimulation();
    });
    mySimulations.setPromptText(myLanguageResources.getString("SimulationSelect"));
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

  private void initializeStyleOptions() {
    myStyles = new ComboBox<>();
    myStyles.setId("SetStyle");
    myStyles.setOnAction(event -> setStyle());
    myStyles.setPromptText(myLanguageResources.getString("SetStyle"));
    Path styles = null;
    try {
      styles = Paths.get(
          Objects.requireNonNull(Simulation.class.getClassLoader().getResource(STYLESHEET_FOLDER)).toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for(File style : styles.toFile().listFiles()){
      myStyles.getItems().add(style.getName().split("\\.")[0]);
    }
  }

  private void makeButton(String buttonName, EventHandler<ActionEvent> buttonAction, Pane navigationPane) {
    Button newButton = new Button();
    String buttonLabel = myLanguageResources.getString(buttonName);
    newButton.setText(buttonLabel);
    newButton.setId(buttonName);
    newButton.setOnAction(buttonAction);
    newButton.getStyleClass().add("button");
    navigationPane.getChildren().add(newButton);
  }

  public void restart() {
    myAnimation.stop();
    chooseSimulation();
  }

  public void play() {
    myAnimation.play();
  }

  public void pause() {
    myAnimation.pause();
  }

  public void step() {
    myGrid.updateNewStates();
    visualizeGrid();
  }

  public void speedUp() {
    myAnimation.setRate(myAnimation.getRate() * 2);
    myRoot.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      myRoot.setRight(null);
    }
  }

  public void slowDown() {
    myAnimation.setRate(myAnimation.getRate() / 2);
    myRoot.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      myRoot.setRight(null);
    }
  }

  // FIXME: Enable saving colors
  public void setColors() {
    getSimulationStates();
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Set Colors");
    String newColor = null;
    for(Enum<?> state : myStates){
      dialog.setContentText("Input Color for State: " + state.toString());
      Optional<String> enteredColor = dialog.showAndWait();
      if (enteredColor.isPresent()) {
        newColor = enteredColor.get();
      }
      myPropertyReader.setProperty(state.toString(), newColor);
    }
    visualizeGrid();
  }

  // FIXME: Enable saving photos
  public void setPhotos() {
    getSimulationStates();
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Set Photos");
    String newColor = null;
    for(Enum<?> state : myStates){
      dialog.setContentText("Choose photo for State: " + state.toString());
      Optional<String> enteredPhoto = dialog.showAndWait();
      if (enteredPhoto.isPresent()) {
        newColor = enteredPhoto.get();
      }
      myPropertyReader.setProperty(state.toString(), newColor);
    }
    visualizeGrid();
  }

  public void setStyle() {
    myScene.getStylesheets().clear();
    myScene.getStylesheets().add(getClass().getResource("/" + STYLESHEET_FOLDER + myStyles.getValue() + ".css").toExternalForm());
  }

  private void chooseSimulation() {
    String pathName =
        PROPERTY_LISTS + "/" + mySimulations.getValue().split(" ")[2] + "/" + mySimulations
            .getValue().split(" ")[0] + ".properties";
    myPropertyReader = new PropertyReader(pathName);
    myStates = new ArrayList<>();

    myGrid = myPropertyReader.gridFromPropertyFile();
    getSimulationStates();
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
      String imagePath = "/visualization_resources/images/" + myFillAsString;
      Image stateImage = null;
      try {
        stateImage = new Image(String.valueOf(getClass().getResource(imagePath).toURI()));
      } catch (URISyntaxException e) {
        e.printStackTrace();
      }
      ImagePattern stateImagePattern = new ImagePattern(stateImage);
      cellRectangle.setFill(stateImagePattern);
    }
    else{
      Color myColor = Color.valueOf(myFillAsString);
      cellRectangle.setFill(myColor);
    }
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

  // TODO: Generalize this method further
  public void saveSimulation() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle("Save Simulation");

    String filename = null;
    String author = null;
    String description = null;

    dialog.setContentText("SaveSimulationAs");
    Optional<String> enteredFilename = dialog.showAndWait();
    if (enteredFilename.isPresent()) {
      filename = enteredFilename.get();
    }
    dialog.setContentText("Author");
    Optional<String> enteredAuthor = dialog.showAndWait();
    if (enteredAuthor.isPresent()) {
      author = enteredAuthor.get();
    }
    dialog.setContentText("Description");
    Optional<String> enteredDescription = dialog.showAndWait();
    if (enteredDescription.isPresent()) {
      description = enteredDescription.get();
    }

    String simType = myPropertyReader.getProperty("simulationType");

    myGrid.saveCurrentGrid("data/" + INITIAL_STATES + "/" + simType + "/" + filename + ".csv");
    File file = new File("data/" + PROPERTY_LISTS + "/" + simType + "/" + filename + ".properties");
    try {
      FileWriter outputFile = new FileWriter(file, false);
      Properties savedProperty = new Properties();
      savedProperty.put("simulationType", simType);
      savedProperty.put("simulationTitle", filename);
      savedProperty.put("configAuthor", author);
      savedProperty.put("description", description);
      savedProperty.put("csvFile", filename + ".csv");
      for(Enum<?> state : myStates){
        savedProperty.put(state.toString(), myPropertyReader.getProperty(state.toString()));
      }
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
