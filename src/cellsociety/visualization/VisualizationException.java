package cellsociety.visualization;

/**
 * This class is used to throw exceptions that arise in the visualization class.  It produces error
 * messages from the VisualizationErrors property file.
 *
 * @author Jack Ellwood
 */
public class VisualizationException extends RuntimeException {

  /**
   * Handles a Visualization exception
   *
   * @param message associated error message
   */
  public VisualizationException(String message) {
    super(message);
  }

}
