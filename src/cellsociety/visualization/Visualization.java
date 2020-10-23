package cellsociety.visualization;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.Cell;
import java.io.File;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class creates a simulation application and initializes the buttons, stylesheet, and language
 * associated with it.  It also renders the cells associated with a simulation.
 *
 * The class is tied to ButtonHandler, since this class manages the visualization of buttons, while
 * ButtonHandler manages the actual button functionality.
 *
 * @author Jack Ellwood
 */
public class Visualization extends Application {

  public static final String STYLESHEET_FOLDER = "visualization_resources/stylesheets/";
  public static final String INITIAL_STATES = "initial_states";
  public static final String PROPERTY_LISTS = "property_lists";
  public static final String LANGUAGE_FOLDER = ".resources.languages.";
  public static final String LANGUAGE_FOLDER_DATA = "visualization_resources/languages_data";
  public static final String DEFAULT_LANGUAGE = "English";
  public static final String VISUALIZATION_ERRORS = ".resources.VisualizationErrors";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double INITIAL_HEIGHT = 800;
  public static final double INITIAL_WIDTH = 800;
  public static final double FRAMES_PER_SECOND = 2;
  public static final double SIMULATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double UPDATE_RATE = 0.0166667;
  public static final double GRID_FRACTION = 0.75;
  public static final String COLORS = "Colors";
  public static final String PHOTOS = "Photos";

  private Stage myGridStage;
  private Scene myGridScene;
  private BorderPane myRoot;
  private Group myGridGroup;
  private ResourceBundle myLanguageResources;
  private ResourceBundle myVisualizationErrors;
  private Timeline myAnimation;
  private PropertyReader myPropertyReader;
  private ComboBox<String> mySimulations;
  private ComboBox<String> myStyles;
  private ComboBox<String> myLanguages;
  private double myLastHeight;
  private double myLastWidth;

  private ButtonHandler myButtonHandler;

  /**
   * Starts the simulation application
   *
   * @param stage which the simulation is rendered in
   */
  @Override
  public void start(Stage stage) {
    stage.setTitle(HEADER);
    myGridScene = setupScene(INITIAL_WIDTH, INITIAL_HEIGHT, DEFAULT_LANGUAGE);
    stage.setScene(myGridScene);
    stage.show();
    stage.show();
    myGridStage = stage;
    myButtonHandler.initializeNewView();
  }

  /**
   * Initializes the scene, including setting up animations, creating the navigation pane, and using
   * a stylesheet to format the GUI
   *
   * @param width initial width of the simulation window
   * @param height initial height of the simulation window
   * @param language initial language used for application
   * @return the scene to display
   */
  public Scene setupScene(double width, double height, String language) {
    myLanguageResources =
        ResourceBundle.getBundle(Visualization.class.getPackageName() + LANGUAGE_FOLDER + language);
    myVisualizationErrors = ResourceBundle
        .getBundle(Visualization.class.getPackageName() + VISUALIZATION_ERRORS);

    myRoot = new BorderPane();
    myGridGroup = new Group();
    myGridGroup.setId("GridGroup");
    myRoot.setCenter(myGridGroup);
    myAnimation = new Timeline();
    myButtonHandler = new ButtonHandler(myAnimation, this, myLanguageResources,
        myVisualizationErrors);

    setupAnimations(width, height);
    makeNavigationPane();

    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets()
        .add(getClass().getResource("/" + STYLESHEET_FOLDER + "LightMode.css").toExternalForm());

    return scene;
  }

  private void setupAnimations(double width, double height) {
    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(SIMULATION_DELAY),
        e -> myButtonHandler.step(myPropertyReader));
    myAnimation.getKeyFrames().add(frame);

    Timeline myGridUpdater = new Timeline();
    myGridUpdater.setCycleCount(Timeline.INDEFINITE);
    KeyFrame cellUpdater = new KeyFrame(Duration.seconds(UPDATE_RATE), e -> resizeCells());
    myGridUpdater.getKeyFrames().add(cellUpdater);
    myGridUpdater.play();
    myLastHeight = height;
    myLastWidth = width;
  }

  private void makeNavigationPane() {
    initializeButtons();
    initializeComboBoxes();
  }

  private void initializeButtons() {
    HBox buttonPane = new HBox();
    makeButton("RestartButton", event -> myButtonHandler.restart(mySimulations), buttonPane);
    makeButton("PlayButton", event -> myButtonHandler.play(), buttonPane);
    makeButton("PauseButton", event -> myButtonHandler.pause(), buttonPane);
    makeButton("StepButton", event -> myButtonHandler.step(myPropertyReader), buttonPane);
    makeButton("SavedSimulation",
        event -> myButtonHandler.saveSimulation(myPropertyReader, mySimulations), buttonPane);
    makeButton("SpeedUp", event -> myButtonHandler.speedUp(myRoot), buttonPane);
    makeButton("SlowDown", event -> myButtonHandler.slowDown(myRoot), buttonPane);
    makeButton("SetColors", event -> myButtonHandler.setColorsOrPhotos(myPropertyReader, COLORS),
        buttonPane);
    makeButton("SetPhotos", event -> myButtonHandler.setColorsOrPhotos(myPropertyReader, PHOTOS),
        buttonPane);
    makeButton("NewView", event -> myButtonHandler.startNewView(), buttonPane);
    myRoot.setBottom(buttonPane);
  }

  private void makeButton(String buttonName, EventHandler<ActionEvent> buttonAction,
      Pane navigationPane) {
    Button newButton = new Button();
    String buttonLabel = myLanguageResources.getString(buttonName);
    newButton.setText(buttonLabel);
    newButton.setId(buttonName);
    newButton.setOnAction(buttonAction);
    newButton.getStyleClass().add("button");
    navigationPane.getChildren().add(newButton);
  }

  private void initializeComboBoxes() {
    HBox comboBoxPane = new HBox();

    initializeSimulationOptions();
    mySimulations.getStyleClass().add("button");
    comboBoxPane.getChildren().add(mySimulations);

    initializeStyleOptions();
    myStyles.getStyleClass().add("button");
    comboBoxPane.getChildren().add(myStyles);

    initializeLanguages();
    myLanguages.getStyleClass().add("button");
    comboBoxPane.getChildren().add(myLanguages);

    myRoot.setTop(comboBoxPane);
  }

  private void initializeSimulationOptions() {
    mySimulations = new ComboBox<>();
    mySimulations.setId("SimulationSelect");
    mySimulations.setOnAction(event -> myButtonHandler.chooseSimulation(mySimulations));
    mySimulations.setPromptText(myLanguageResources.getString("SimulationSelect"));
    Path simulations;
    try {
      simulations = Paths.get(
          Objects.requireNonNull(Visualization.class.getClassLoader().getResource(PROPERTY_LISTS))
              .toURI());
    } catch (URISyntaxException e) {
      throw new VisualizationException(myVisualizationErrors.getString("simulationNotFound"));
    }
    addSimulationsToComboBox(simulations);
  }

  private void addSimulationsToComboBox(Path simulations) {
    for (File path : Objects.requireNonNull(simulations.toFile().listFiles())) {
      for (File file : Objects.requireNonNull(path.listFiles())) {
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
    myStyles.setOnAction(event -> myButtonHandler.setStyle(myStyles));
    myStyles.setPromptText(myLanguageResources.getString("SetStyle"));
    getComboBoxValues(STYLESHEET_FOLDER, myStyles);
  }

  private void initializeLanguages() {
    myLanguages = new ComboBox<>();
    myLanguages.setId("SetLanguage");
    myLanguages.setOnAction(event -> myButtonHandler.setLanguage(myLanguages));
    myLanguages.setPromptText(myLanguageResources.getString("SetLanguage"));
    getComboBoxValues(LANGUAGE_FOLDER_DATA, myLanguages);
  }

  private void getComboBoxValues(String stylesheetFolder, ComboBox<String> myBox) {
    Path values;
    try {
      values = Paths.get(
          Objects.requireNonNull(Visualization.class.getClassLoader().getResource(stylesheetFolder))
              .toURI());
    } catch (URISyntaxException e) {
      throw new VisualizationException(myVisualizationErrors.getString("badComboBoxValue"));
    }
    for (File value : Objects.requireNonNull(values.toFile().listFiles())) {
      myBox.getItems().add(value.getName().split("\\.")[0]);
    }
  }

  private void resizeCells() {
    if (myLastHeight != myGridScene.getHeight() || myLastWidth != myGridScene.getWidth()) {
      myLastHeight = myGridScene.getHeight();
      myLastWidth = myGridScene.getWidth();
      visualizeGrid(myButtonHandler.getGrid(), myPropertyReader);
    }
  }

  protected void visualizeGrid(Grid grid, PropertyReader propertyReader) {
    myPropertyReader = propertyReader;
    myGridGroup.getChildren().clear();
    double x = myGridScene.getWidth() / 8;
    double y = myGridScene.getHeight() / 8;
    int cellLabel = 0;
    double cellSize =
        Math.min(GRID_FRACTION * myGridScene.getHeight(), GRID_FRACTION * myGridScene.getWidth())
            / grid.getMyCells().size();
    for (List<Cell> row : grid.getMyCells()) {
      for (Cell cell : row) {
        visualizeCell(x, y, cellLabel, cellSize, cell);
        x += cellSize;
        cellLabel++;
      }
      y += cellSize;
      x = myGridScene.getWidth() / 8;
    }
  }

  private void visualizeCell(double x, double y, int cellLabel, double cellSize, Cell cell) {
    Rectangle cellRectangle = new Rectangle(x, y, cellSize, cellSize);
    cellRectangle.setId("cell" + cellLabel);
    cellRectangle.setOnMouseClicked(e -> myButtonHandler.setCellState(cellLabel, myPropertyReader));
    Enum<?> currentState = cell.getMyState();

    String myFillAsString = myPropertyReader.getProperty(currentState.toString());
    fillCell(cellRectangle, myFillAsString);

    cellRectangle.setStroke(Color.WHITE);
    myGridGroup.getChildren().add(cellRectangle);
  }

  private void fillCell(Rectangle cellRectangle, String myFillAsString) {
    if (myFillAsString.split("\\.").length > 1) {
      String imagePath = "/visualization_resources/images/" + myFillAsString;
      Image stateImage;
      try {
        stateImage = new Image(String.valueOf(getClass().getResource(imagePath).toURI()));
      } catch (URISyntaxException e) {
        throw new VisualizationException(
            String.format(myVisualizationErrors.getString("imageToCellError"),
                myFillAsString));
      }
      ImagePattern stateImagePattern = new ImagePattern(stateImage);
      cellRectangle.setFill(stateImagePattern);
    } else {
      Color myColor = Color.valueOf(myFillAsString);
      cellRectangle.setFill(myColor);
    }
  }

  protected Scene getScene() {
    return myGridScene;
  }

  protected void setScene(Scene scene) {
    myGridStage.setScene(scene);
  }

  /**
   * Used to access the current animation (used by VisualizationTest to test some button functionality)
   *
   * @return the current animation
   */
  public Timeline getAnimation() {
    return myAnimation;
  }

  /**
   * Gets the current ButtonHandler being used by the application (also used in VisualizationTest)
   *
   * @return the current ButtonHandler
   */
  public ButtonHandler getButtonHandler() {
    return myButtonHandler;
  }

  /**
   * Main method that runs the application
   *
   * @param args command line arguments (none required)
   */
  public static void main(String[] args) {
    launch(args);
  }

}
