package cellsociety;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Simulation extends Application {

  public static final String STYLESHEET = "/myStyles.css";
  public static final String PROPERTIES = "SimulationButtons";
  public static final String HEADER = "Jack, Hayden, and Jason's Simulation";
  public static final double HEIGHT = 600;
  public static final double WIDTH = 600;

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
    stage.setTitle(HEADER);
    myScene = setupScene(WIDTH, HEIGHT);
    stage.setScene(myScene);
    stage.show();
  }

  public Scene setupScene(double width, double height){
    myResources = ResourceBundle.getBundle(PROPERTIES);
    BorderPane root = new BorderPane();

    root.setTop(makeNavigationPane());
    Scene scene = new Scene(root, width, height);
    scene.getStylesheets().add(getClass().getResource(STYLESHEET).toExternalForm());

    return scene;
  }

  private Node makeNavigationPane(){
    HBox navigationPane = new HBox();
    myRestartButton = makeButton("RestartButton", event -> restart());
    navigationPane.getChildren().add(myRestartButton);
    myPlayButton = makeButton("PlayButton", event -> play());
    navigationPane.getChildren().add(myPlayButton);
    myPauseButton = makeButton("PauseButton", event -> pause());
    navigationPane.getChildren().add(myPauseButton);
    myStepButton = makeButton("StepButton", event -> step());
    navigationPane.getChildren().add(myStepButton);
    mySimulationSelectButton = makeButton("SimulationSelect", event -> chooseSimulation());
    navigationPane.getChildren().add(mySimulationSelectButton);
    return navigationPane;
  }

  private Button makeButton(String buttonName, EventHandler<ActionEvent> buttonAction){
    Button newButton = new Button();
    String buttonLabel = myResources.getString(buttonName);
    newButton.setText(buttonLabel);
    newButton.setId(buttonName);
    newButton.setOnAction(buttonAction);
    return newButton;
  }

  private void restart(){

  }

  private void play(){

  }

  private void pause(){

  }

  private void step(){

  }

  private void chooseSimulation(){

  }

}
