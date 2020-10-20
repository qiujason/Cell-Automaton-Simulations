package cellsociety.visualization;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Cells.Cell;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ButtonHandler {

  private final Timeline myAnimation;
  private final Visualization myVisualization;
  private final ResourceBundle myLanguageResources;
  private final ResourceBundle myVisualizationErrors;
  private List<Enum<?>> myStates;
  private Grid myGrid;
  private Stage myGraphStage;
  private LineChart<Number, Number> mySimulationChart;
  private int myIterationCounter;

  public ButtonHandler(Timeline animation, Visualization visualization,
      ResourceBundle languageResources, ResourceBundle visualizationErrors) {
    myAnimation = animation;
    myVisualization = visualization;
    myLanguageResources = languageResources;
    myVisualizationErrors = visualizationErrors;
  }

  protected void restart(ComboBox<String> simulations) {
    myAnimation.stop();
    chooseSimulation(simulations);
  }

  protected void play() {
    myAnimation.play();
  }

  protected void pause() {
    myAnimation.pause();
  }

  protected void step(PropertyReader propertyReader) {
    myGrid.updateNewStates();
    myVisualization.visualizeGrid(myGrid, propertyReader);
    updateGraph();
  }

  protected void speedUp(BorderPane root) {
    myAnimation.setRate(myAnimation.getRate() * 2);
    root.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      root.setRight(null);
    }
  }

  protected void slowDown(BorderPane root) {
    myAnimation.setRate(myAnimation.getRate() / 2);
    root.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      root.setRight(null);
    }
  }

  protected void setColorsOrPhotos(PropertyReader propertyReader, String colorOrPhoto) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(myLanguageResources.getString("Set" + colorOrPhoto));
    dialog.setHeaderText(myLanguageResources.getString("Set" + colorOrPhoto));
    String newColorOrPhoto = null;
    for (Enum<?> state : myStates) {
      dialog.setContentText(
          myLanguageResources.getString("Set" + colorOrPhoto + "Dialogue") + state.toString());
      Optional<String> enteredColorOrPhoto = dialog.showAndWait();
      if (enteredColorOrPhoto.isPresent()) {
        newColorOrPhoto = enteredColorOrPhoto.get();
      }
      propertyReader.setProperty(state.toString(), newColorOrPhoto);
      dialog.setContentText("");
    }
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  protected void setStyle(ComboBox<String> styles) {
    myVisualization.getScene().getStylesheets().clear();
    myVisualization.getScene().getStylesheets().add(
        getClass().getResource("/" + Visualization.STYLESHEET_FOLDER + styles.getValue() + ".css")
            .toExternalForm());
  }

  protected void setLanguage(ComboBox<String> languages) {
    Scene scene = myVisualization
        .setupScene(myVisualization.getScene().getWidth(), myVisualization.getScene().getHeight(),
            languages.getValue());
    myVisualization.setScene(scene);
  }

  protected void initializeNewView() {
    myGraphStage = new Stage();
    myGraphStage.setTitle("State Graph");

    NumberAxis iterations = new NumberAxis();
    NumberAxis numberOfStates = new NumberAxis();
    iterations.setLabel("Iterations");
    numberOfStates.setLabel("Number of States");

    mySimulationChart = new LineChart<>(iterations, numberOfStates);

    mySimulationChart.setTitle("Simulation Graph");

    Scene myGraphScene = new Scene(mySimulationChart, Visualization.INITIAL_WIDTH,
        Visualization.INITIAL_HEIGHT);
    myGraphStage.setScene(myGraphScene);
  }

  protected void startNewView() {
    myGraphStage.show();
  }

  private void initializeGraph() {
    mySimulationChart.getData().clear();
    myIterationCounter = 0;
    for (Enum<?> state : myStates) {
      XYChart.Series<Number, Number> stateSeries = new XYChart.Series<>();
      stateSeries.setName(state.toString());
      stateSeries.getData()
          .add(new XYChart.Data<>(myIterationCounter, calculateNumberOfCellsInState(state)));
      mySimulationChart.getData().add(stateSeries);
    }
  }

  private void updateGraph() {
    myIterationCounter++;
    for (XYChart.Series<Number, Number> series : mySimulationChart.getData()) {
      for (Enum<?> state : myStates) {
        if (("Series[" + state.toString() + "]").equals(series.toString())) {
          series.getData()
              .add(new XYChart.Data<>(myIterationCounter, calculateNumberOfCellsInState(state)));
        }
      }
    }
  }

  private int calculateNumberOfCellsInState(Enum<?> state) {
    int totalNumberInState = 0;
    for (List<Cell> row : myGrid.getMyCells()) {
      for (Cell cell : row) {
        if (state.equals(cell.getMyState())) {
          totalNumberInState++;
        }
      }
    }
    return totalNumberInState;
  }

  protected void chooseSimulation(ComboBox<String> simulations) {
    String pathName =
        Visualization.PROPERTY_LISTS + "/" + simulations.getValue().split(" ")[2] + "/"
            + simulations
            .getValue().split(" ")[0] + ".properties";
    PropertyReader propertyReader = new PropertyReader(pathName);
    myStates = new ArrayList<>();

    myGrid = propertyReader.gridFromPropertyFile();
    getSimulationStates(propertyReader);
    myVisualization.visualizeGrid(myGrid, propertyReader);
    initializeGraph();
  }

  private void getSimulationStates(PropertyReader propertyReader) {
    String simType = propertyReader.getProperty("simulationType");
    Class<?> clazz;

    try {
      clazz = Class.forName("cellsociety.model.Cells." + simType + "." + simType + "States");
    } catch (ClassNotFoundException e) {
      throw new VisualizationException(
          String.format(myVisualizationErrors.getString("simulationClassNotFound"), simType));
    }

    for (Object state : clazz.getEnumConstants()) {
      myStates.add((Enum<?>) state);
    }
  }

  protected void setCellState(int cellLabel, PropertyReader propertyReader) {
    int row = cellLabel / myGrid.getMyCells().get(0).size();
    int column = cellLabel % myGrid.getMyCells().get(0).size();
    Cell myCell = myGrid.getMyCells().get(row).get(column);
    for (int i = 0; i < myStates.size() - 1; i++) {
      if (myCell.getMyState().equals(myStates.get(0))) {
        myCell.setMyState(myStates.get(i + 1));
        myVisualization.visualizeGrid(myGrid, propertyReader);
        return;
      }
    }
    myCell.setMyState(myStates.get(0));
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  protected void saveSimulation(PropertyReader propertyReader, ComboBox<String> simulations) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(myLanguageResources.getString("SavedSimulation"));
    dialog.setHeaderText(myLanguageResources.getString("SavedSimulation"));

    String[] propertyValues = promptUser(dialog);
    String simType = createNewSimulation(propertyReader, propertyValues[0], propertyValues[1],
        propertyValues[2]);
    simulations.getItems().add(propertyValues[0] + " - " + simType);
  }

  private String[] promptUser(TextInputDialog dialog) {
    String[] propertyKeys = {"SaveSimulationAs", "Author", "Description"};
    String[] propertyValues = new String[3];

    for (int i = 0; i < 3; i++) {
      dialog.setContentText(myLanguageResources.getString(propertyKeys[i]));
      Optional<String> enteredText = dialog.showAndWait();
      if (enteredText.isPresent()) {
        propertyValues[i] = enteredText.get();
      }
    }
    return propertyValues;
  }

  private String createNewSimulation(PropertyReader propertyReader, String filename, String author,
      String description) {

    String simType = propertyReader.getProperty("simulationType");
    myGrid.saveCurrentGrid(
        "data/" + Visualization.INITIAL_STATES + "/" + simType + "/" + filename + ".csv");
    File file = new File(
        "data/" + Visualization.PROPERTY_LISTS + "/" + simType + "/" + filename + ".properties");
    try {
      FileWriter outputFile = new FileWriter(file, false);
      Properties savedProperty = new Properties();
      savedProperty.put("simulationType", simType);
      savedProperty.put("simulationTitle", filename);
      savedProperty.put("configAuthor", author);
      savedProperty.put("description", description);
      savedProperty.put("csvFile", filename + ".csv");
      for (Enum<?> state : myStates) {
        savedProperty.put(state.toString(), propertyReader.getProperty(state.toString()));
      }
      savedProperty.store(outputFile, null);
    } catch (IOException e) {
      throw new VisualizationException(
          String.format(myVisualizationErrors.getString("simulationSaveError"),
              filename));
    }
    return simType;
  }

  protected Grid getGrid() {
    return myGrid;
  }

  public LineChart<Number, Number> getSimulationChart() {
    return mySimulationChart;
  }

}
