package cellsociety.configuration;

/**
 * specific type of exception for Configuration package
 *
 * @author Hayden Lau
 */
public class ConfigurationException extends RuntimeException {

  /**
   * exception for Configuration package
   *
   * @param message message to add to the exception
   */
  public ConfigurationException(String message) {
    super(message);
  }

}
