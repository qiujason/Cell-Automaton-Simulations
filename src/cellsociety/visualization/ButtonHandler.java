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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class ButtonHandler {

  private Timeline myAnimation;
  private Visualization myVisualization;
  private ResourceBundle myLanguageResources;
  private List<Enum<?>> myStates;
  private Grid myGrid;

  public ButtonHandler(Timeline animation, Visualization visualization, ResourceBundle languageResources) {
    myAnimation = animation;
    myVisualization = visualization;
    myLanguageResources = languageResources;
  }

  public void restart(ComboBox<String> simulations, PropertyReader propertyReader) {
    myAnimation.stop();
    chooseSimulation(simulations);
  }

  public void play() {
    myAnimation.play();
  }

  public void pause() {
    myAnimation.pause();
  }

  public void step(PropertyReader propertyReader) {
    myGrid.updateNewStates();
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  public void speedUp(BorderPane root) {
    myAnimation.setRate(myAnimation.getRate() * 2);
    root.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      root.setRight(null);
    }
  }

  public void slowDown(BorderPane root) {
    myAnimation.setRate(myAnimation.getRate() / 2);
    root.setRight(new Text("x" + myAnimation.getRate()));
    if (myAnimation.getRate() == 1) {
      root.setRight(null);
    }
  }

  public void setColorsOrPhotos(PropertyReader propertyReader, String colorOrPhoto) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(myLanguageResources.getString("Set" + colorOrPhoto));
    dialog.setHeaderText(myLanguageResources.getString("Set" + colorOrPhoto));
    String newColorOrPhoto = null;
    for(Enum<?> state : myStates){
      dialog.setContentText(myLanguageResources.getString("Set" + colorOrPhoto + "Dialogue") + state.toString());
      Optional<String> enteredColorOrPhoto = dialog.showAndWait();
      if (enteredColorOrPhoto.isPresent()) {
        newColorOrPhoto = enteredColorOrPhoto.get();
      }
      propertyReader.setProperty(state.toString(), newColorOrPhoto);
      dialog.setContentText("");
    }
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  public void setStyle(ComboBox<String> styles) {
    myVisualization.getScene().getStylesheets().clear();
    myVisualization.getScene().getStylesheets().add(getClass().getResource("/" + Visualization.STYLESHEET_FOLDER + styles.getValue() + ".css").toExternalForm());
  }

  protected void setLanguage(ComboBox<String> languages) {
    Scene scene = myVisualization
        .setupScene(myVisualization.getScene().getWidth(), myVisualization.getScene().getHeight(), languages.getValue());
    myVisualization.setScene(scene);
  }

  protected void chooseSimulation(ComboBox<String> simulations) {
    String pathName =
        Visualization.PROPERTY_LISTS + "/" + simulations.getValue().split(" ")[2] + "/" + simulations
            .getValue().split(" ")[0] + ".properties";
    PropertyReader propertyReader = new PropertyReader(pathName);
    myStates = new ArrayList<>();

    myGrid = propertyReader.gridFromPropertyFile();
    getSimulationStates(propertyReader);
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  private void getSimulationStates(PropertyReader propertyReader) {
    String simType = propertyReader.getProperty("simulationType");
    Class<?> clazz = null;

    try {
      clazz = Class.forName("cellsociety.model.Cells." + simType + "." + simType + "States");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    if (clazz != null) {
      for (Object state : clazz.getEnumConstants()){
        myStates.add((Enum<?>) state);
      }
    }
  }

  protected void setCellState(int cellLabel, PropertyReader propertyReader) {
    int row = cellLabel / myGrid.getMyCells().get(0).size();
    int column = cellLabel % myGrid.getMyCells().get(0).size();
    Cell myCell = myGrid.getMyCells().get(row).get(column);
    for(int i = 0; i < myStates.size() - 1; i++){
      if(myCell.getMyState().equals(myStates.get(0))){
        myCell.setMyState(myStates.get(i+1));
        myVisualization.visualizeGrid(myGrid, propertyReader);
        return;
      }
    }
    myCell.setMyState(myStates.get(0));
    myVisualization.visualizeGrid(myGrid, propertyReader);
  }

  // TODO: Generalize this method further
  public void saveSimulation(PropertyReader propertyReader, ComboBox<String> simulations) {
    TextInputDialog dialog = new TextInputDialog();
    dialog.setTitle(myLanguageResources.getString("SavedSimulation"));
    dialog.setHeaderText(myLanguageResources.getString("SavedSimulation"));

    String filename = null;
    String author = null;
    String description = null;

    dialog.setContentText(myLanguageResources.getString("SaveSimulationAs"));
    Optional<String> enteredFilename = dialog.showAndWait();
    if (enteredFilename.isPresent()) {
      filename = enteredFilename.get();
    }
    dialog.setContentText(myLanguageResources.getString("Author"));
    Optional<String> enteredAuthor = dialog.showAndWait();
    if (enteredAuthor.isPresent()) {
      author = enteredAuthor.get();
    }
    dialog.setContentText(myLanguageResources.getString("Description"));
    Optional<String> enteredDescription = dialog.showAndWait();
    if (enteredDescription.isPresent()) {
      description = enteredDescription.get();
    }

    String simType = propertyReader.getProperty("simulationType");

    myGrid.saveCurrentGrid("data/" + Visualization.INITIAL_STATES + "/" + simType + "/" + filename + ".csv");
    File file = new File("data/" + Visualization.PROPERTY_LISTS + "/" + simType + "/" + filename + ".properties");
    try {
      FileWriter outputFile = new FileWriter(file, false);
      Properties savedProperty = new Properties();
      savedProperty.put("simulationType", simType);
      savedProperty.put("simulationTitle", filename);
      savedProperty.put("configAuthor", author);
      savedProperty.put("description", description);
      savedProperty.put("csvFile", filename + ".csv");
      for(Enum<?> state : myStates){
        savedProperty.put(state.toString(), propertyReader.getProperty(state.toString()));
      }
      savedProperty.store(outputFile, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
    simulations.getItems().add(filename + " - " + simType);
  }

  protected Grid getGrid() {
    return myGrid;
  }

}
