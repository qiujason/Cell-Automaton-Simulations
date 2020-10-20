package cellsociety.visualization;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.Cell;
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
  public static final String LANGUAGE_FOLDER = "visualization_resources/languages";
  public static final String DEFAULT_LANGUAGE = "english";
  public static final String BUTTON_PROPERTIES = "visualization_resources.buttons";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double INITIAL_HEIGHT = 800;
  public static final double INITIAL_WIDTH = 800;
  public static final double FRAMES_PER_SECOND = 2;
  public static final double SIMULATION_DELAY = 1 / FRAMES_PER_SECOND;
  public static final double UPDATE_RATE = 0.0166667;
  public static final double GRID_FRACTION = 0.75;
  public static final String COLORS = "Colors";
  public static final String PHOTOS = "Photos";

  private Stage myStage;
  private Scene myScene;
  private BorderPane myRoot;
  private Group myGridGroup;
  private ResourceBundle myLanguageResources;
  private Timeline myAnimation;
  private Timeline myGridUpdater;
  private PropertyReader myPropertyReader;
  private ComboBox<String> mySimulations;
  private ComboBox<String> myStyles;
  private ComboBox<String> myLanguages;
  private double myLastHeight;
  private double myLastWidth;

  private ButtonHandler myButtonHandler;

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle(HEADER);
    myScene = setupScene(INITIAL_WIDTH, INITIAL_HEIGHT, DEFAULT_LANGUAGE);
    stage.setScene(myScene);
    stage.show();
    myStage = stage;
  }

  public Scene setupScene(double width, double height, String language) {
    myLanguageResources = ResourceBundle.getBundle(LANGUAGE_FOLDER + "\\." + language);
    myRoot = new BorderPane();
    myGridGroup = new Group();
    myGridGroup.setId("GridGroup");
    myRoot.setCenter(myGridGroup);

    myAnimation = new Timeline();

    myButtonHandler = new ButtonHandler(myAnimation, this, myLanguageResources);

    myAnimation.setCycleCount(Timeline.INDEFINITE);
    KeyFrame frame = new KeyFrame(Duration.seconds(SIMULATION_DELAY), e -> myButtonHandler.step(myPropertyReader));
    myAnimation.getKeyFrames().add(frame);

    myGridUpdater = new Timeline();
    myGridUpdater.setCycleCount(Timeline.INDEFINITE);
    KeyFrame cellUpdater = new KeyFrame(Duration.seconds(UPDATE_RATE), e -> updateCells());
    myGridUpdater.getKeyFrames().add(cellUpdater);
    myGridUpdater.play();
    myLastHeight = height;
    myLastWidth = width;

    makeNavigationPane();
    Scene scene = new Scene(myRoot, width, height);
    scene.getStylesheets().add(getClass().getResource("/" + STYLESHEET_FOLDER + "LightMode.css").toExternalForm());

    return scene;
  }

  private void updateCells() {
    if(myLastHeight != myScene.getHeight() || myLastWidth != myScene.getWidth()){
      myLastHeight = myScene.getHeight();
      myLastWidth = myScene.getWidth();
      visualizeGrid(myButtonHandler.getGrid(), myPropertyReader);
    }
  }

  private void makeNavigationPane() {
    HBox buttonPane = new HBox();
    makeButton("RestartButton", event -> myButtonHandler.restart(mySimulations ,myPropertyReader), buttonPane);
    makeButton("PlayButton", event -> myButtonHandler.play(), buttonPane);
    makeButton("PauseButton", event -> myButtonHandler.pause(), buttonPane);
    makeButton("StepButton", event -> myButtonHandler.step(myPropertyReader), buttonPane);
    makeButton("SavedSimulation", event -> myButtonHandler.saveSimulation(myPropertyReader, mySimulations), buttonPane);
    makeButton("SpeedUp", event -> myButtonHandler.speedUp(myRoot), buttonPane);
    makeButton("SlowDown", event -> myButtonHandler.slowDown(myRoot), buttonPane);
    makeButton("SetColors", event -> myButtonHandler.setColorsOrPhotos(myPropertyReader, COLORS), buttonPane);
    makeButton("SetPhotos", event -> myButtonHandler.setColorsOrPhotos(myPropertyReader, PHOTOS), buttonPane);
    myRoot.setBottom(buttonPane);
//    ResourceBundle buttons = ResourceBundle.getBundle(BUTTON_PROPERTIES);
//    for(String button : buttons.keySet()){
//      EventHandler<ActionEvent> me = event -> {
//        try {
//          this.getClass().getMethod(buttons.getString(button));
//        } catch (NoSuchMethodException e) {
//          e.printStackTrace();
//        }
//      };
//      makeButton(button, event -> {
//        try {
//          this.getClass().getMethod(buttons.getString(button));
//        } catch (NoSuchMethodException e) {
//          e.printStackTrace();
//        }
//      }, buttonPane);
//    }
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
    mySimulations.setOnAction(event -> {
      myButtonHandler.chooseSimulation(mySimulations);
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
    myStyles.setOnAction(event -> myButtonHandler.setStyle(myStyles));
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

  private void initializeLanguages() {
    myLanguages = new ComboBox<>();
    myLanguages.setId("SetLanguage");
    myLanguages.setOnAction(event -> myButtonHandler.setLanguage(myLanguages));
    myLanguages.setPromptText(myLanguageResources.getString("SetLanguage"));
    Path languages = null;
    try {
      languages = Paths.get(
          Objects.requireNonNull(Simulation.class.getClassLoader().getResource(LANGUAGE_FOLDER)).toURI());
    } catch (URISyntaxException e) {
      e.printStackTrace();
    }
    for(File language : languages.toFile().listFiles()){
      myLanguages.getItems().add(language.getName().split("\\.")[0]);
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

//  public void restart() {
//    myAnimation.stop();
//    chooseSimulation();
//  }
//
//  public void play() {
//    myAnimation.play();
//  }
//
//  public void pause() {
//    myAnimation.pause();
//  }
//
//  public void step() {
//    myGrid.updateNewStates();
//    visualizeGrid();
//  }
//
//  public void speedUp() {
//    myAnimation.setRate(myAnimation.getRate() * 2);
//    myRoot.setRight(new Text("x" + myAnimation.getRate()));
//    if (myAnimation.getRate() == 1) {
//      myRoot.setRight(null);
//    }
//  }
//
//  public void slowDown() {
//    myAnimation.setRate(myAnimation.getRate() / 2);
//    myRoot.setRight(new Text("x" + myAnimation.getRate()));
//    if (myAnimation.getRate() == 1) {
//      myRoot.setRight(null);
//    }
//  }
//
//  public void setColors() {
//    TextInputDialog dialog = new TextInputDialog();
//    dialog.setTitle(myLanguageResources.getString("SetColors"));
//    dialog.setHeaderText(myLanguageResources.getString("SetColors"));
//    String newColor = null;
//    for(Enum<?> state : myStates){
//      dialog.setContentText(myLanguageResources.getString("SetColorsDialogue") + state.toString());
//      Optional<String> enteredColor = dialog.showAndWait();
//      if (enteredColor.isPresent()) {
//        newColor = enteredColor.get();
//      }
//      myPropertyReader.setProperty(state.toString(), newColor);
//      dialog.setContentText("");
//    }
//    visualizeGrid();
//  }
//
//  public void setPhotos() {
//    TextInputDialog dialog = new TextInputDialog();
//    dialog.setTitle(myLanguageResources.getString("SetPhotos"));
//    dialog.setHeaderText(myLanguageResources.getString("SetPhotos"));
//    String newColor = null;
//    for(Enum<?> state : myStates){
//      dialog.setContentText(myLanguageResources.getString("SetPhotosDialogue") + state.toString());
//      Optional<String> enteredPhoto = dialog.showAndWait();
//      if (enteredPhoto.isPresent()) {
//        newColor = enteredPhoto.get();
//      }
//      myPropertyReader.setProperty(state.toString(), newColor);
//    }
//    visualizeGrid();
//  }
//
//  public void setStyle() {
//    myScene.getStylesheets().clear();
//    myScene.getStylesheets().add(getClass().getResource("/" + STYLESHEET_FOLDER + myStyles.getValue() + ".css").toExternalForm());
//  }
//
//  private void setLanguage() {
//    myScene = setupScene(INITIAL_WIDTH, INITIAL_HEIGHT, myLanguages.getValue());
//    myStage.setScene(myScene);
//  }
//
//  private void chooseSimulation() {
//    String pathName =
//        PROPERTY_LISTS + "/" + mySimulations.getValue().split(" ")[2] + "/" + mySimulations
//            .getValue().split(" ")[0] + ".properties";
//    myPropertyReader = new PropertyReader(pathName);
//    myStates = new ArrayList<>();
//
//    myGrid = myPropertyReader.gridFromPropertyFile();
//    getSimulationStates();
//    visualizeGrid();
//  }

  protected void visualizeGrid(Grid grid, PropertyReader propertyReader) {
    myPropertyReader = propertyReader;
    myGridGroup.getChildren().clear();
    double x = myScene.getWidth() / 8;
    double y = myScene.getHeight() / 8;
    int cellLabel = 0;
    double cellSize = Math.min(GRID_FRACTION * myScene.getHeight(), GRID_FRACTION * myScene.getWidth()) / grid.getMyCells().size();
    for (List<Cell> row : grid.getMyCells()) {
      for (Cell cell : row) {
        visualizeCell(x, y, cellLabel, cellSize, cell, grid);
        x += cellSize;
        cellLabel++;
      }
      y += cellSize;
      x = myScene.getWidth() / 8;
    }
  }

  private void visualizeCell(double x, double y, int cellLabel, double cellSize, Cell cell, Grid grid) {
    Rectangle cellRectangle = new Rectangle(x, y, cellSize, cellSize);
    cellRectangle.setId("cell" + cellLabel);
    cellRectangle.setOnMouseClicked(e -> myButtonHandler.setCellState(cellLabel, myPropertyReader));
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

  // Necessary for testing
  public Timeline getAnimation() {
    return myAnimation;
  }

  // Necessary for testing
  public Scene getScene(){
    return myScene;
  }

  protected void setScene(Scene scene){
    myStage.setScene(scene);
  }

  public static void main(String[] args) {
    launch(args);
  }

}
