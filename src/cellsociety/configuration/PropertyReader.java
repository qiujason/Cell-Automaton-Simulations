package cellsociety.configuration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

/**
 * This class is designed to analyze simulation properties files and generate the appropriate grid
 * for the simulation to work on and the front-end visualization to display.
 * <p>
 * If the front-end passes in a faulty String path, the data in the properties file isn't properly
 * formatted or has missing required properties, the constructor will throw a Configuration
 * Exception.
 * <p>
 * Because the class generates grids, it inherently relies on the Grid abstract class and its
 * subclasses.
 * <p>
 * Example: PropertyReader reader = new PropertyReader("property_lists/GameOfLife/square.properties");
 *
 * @author Hayden Lau
 */
public class PropertyReader {

  private final Pair[] defaultOptionalKeys = new Pair[]{
      new Pair("probCatch", "0.3"),
      new Pair("winThreshold", "3.0"),
      new Pair("satisfiedThreshold", "0.3"),
      new Pair("thresholdForBirth", "3.0"),
      new Pair("edgePolicy", "Finite"),
      new Pair("neighborPolicy", "Complete"),
      new Pair("initialStatePolicy", "Csv"),
      new Pair("probability", ".5"),
      new Pair("totalNumber", "1")
  };
  private final String[] REQUIRED_KEYS = new String[]{
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };

  private final ResourceBundle configurationErrorsResourceBundle = ResourceBundle
      .getBundle(PropertyReader.class.getPackageName() + ".resources.ConfigurationErrors");
  private final ResourceBundle optionalKeyResourceBundle = ResourceBundle
      .getBundle(PropertyReader.class.getPackageName() + ".resources.OptionalKey");

  private final Properties properties;
  private final String file;

  /**
   * @param propertyFile - string corresponding to the file path for the properties file
   * @throws ConfigurationException if the file is missing required keys, has a bad simulation
   *                                format, or the file doesn't exist
   */
  public PropertyReader(String propertyFile) throws ConfigurationException {
    file = propertyFile;
    try {
      properties = new Properties();
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
      properties.load(inputStream);
      for (String requiredKey : REQUIRED_KEYS) {
        if (properties.getProperty(requiredKey) == null) {
          throw new ConfigurationException(
              String.format(configurationErrorsResourceBundle.getString("missingRequiredProperty"),
                  requiredKey));
        }
      }
      if (properties.getProperty("simulationType").contains(" ")) {
        throw new ConfigurationException(String
            .format(configurationErrorsResourceBundle.getString("faultySimulationType"),
                "remove spaces"));
      }
      for (Pair optionalKey : defaultOptionalKeys) {
        if (properties.getProperty((String) optionalKey.getKey()) == null) {
          properties.setProperty((String) optionalKey.getKey(), (String) optionalKey.getValue());
        }
      }
    } catch (NullPointerException | IOException e) {
      throw new ConfigurationException(
          configurationErrorsResourceBundle.getString("errorReadingPropertiesFile"));
    }
  }

  /**
   * gets a property value given a key string
   *
   * @param key key pertaining to the property requested
   * @return value pertaining to the property key
   * @throws ConfigurationException if the property pertaining to the key doesn't exist
   */
  public String getProperty(String key) throws ConfigurationException {
    String property = properties.getProperty(key);
    if (property == null) {
      throw new ConfigurationException(
          String.format(configurationErrorsResourceBundle.getString("errorGettingProperty"), key));
    }
    return property;
  }

  /**
   * allows for the simulation to write property values into the properties file
   *
   * @param key   property key to be written to
   * @param value value to be stored
   * @throws ConfigurationException if there was an error writing to the file
   */
  public void setProperty(String key, String value) throws ConfigurationException {
    properties.setProperty(key, value);
    try {
      properties
          .store(new FileOutputStream(this.getClass().getResource("/" + file).getPath()), null);
    } catch (IOException e) {
      throw new ConfigurationException(
          String.format(configurationErrorsResourceBundle.getString("errorWritingToPropertiesFile"),
              key, file));
    }
  }

  /**
   * generates a Map of property keys and values to be passed into grid constructors for the
   * different types of simulations
   *
   * @param simulationType type of simulation to generate a HashMap of optional keys for to pass
   *                       into the simulation
   * @return HashMap of optional keys pertaining to the simulation type and their values
   * @throws ConfigurationException if a necessary optional key is missing from the properties
   */
  public HashMap<String, Object> optionalKeyMap(String simulationType)
      throws ConfigurationException {
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    String optionalKeysForSimulation = optionalKeyResourceBundle.getString(simulationType);
    String[] neededOptionals = optionalKeysForSimulation.split(",");
    for (String neededOptional : neededOptionals) {
      String value = properties.getProperty(neededOptional);
      if (value == null) {
        throw new ConfigurationException(String
            .format(configurationErrorsResourceBundle.getString("missingRequiredProperty"),
                neededOptional));
      }
      optionalKeyMap.put(neededOptional, value);
    }
    return optionalKeyMap;
  }

  /**
   * based on the simulation type and the initial configuration type, this method creates a grid
   * using reflection
   *
   * @return appropriate Grid based on the simulation Type, and initial configuration type
   * @throws ConfigurationException if the CSV file is bad, the simulation requested isn't
   *                                supported, or if there was an error while generating the grid
   */
  public Grid gridFromPropertyFile() throws ConfigurationException {
    Path path;
    Grid grid;
    try {
      path = Paths
          .get(getClass().getClassLoader().getResource(String
              .format("initial_states/%s/%s", properties.getProperty("simulationType"),
                  properties.getProperty("csvFile"))).toURI());
    } catch (URISyntaxException | NullPointerException e) {
      throw new ConfigurationException(
          String
              .format(configurationErrorsResourceBundle.getString("otherSimulationCreationErrors"),
                  e.getMessage()));
    }

    String simulationName = properties.getProperty("simulationType");
    String initialConfiguration = properties.getProperty("initialStatePolicy");

    try {
      String CONFIGURATION_PATH = "cellsociety.configuration.";
      Class<?> configurationGrid = Class
          .forName(CONFIGURATION_PATH + initialConfiguration + "Grid");
      Constructor<?> gridConstructor = configurationGrid
          .getConstructor(Path.class, String.class, Map.class);
      grid = (Grid) gridConstructor
          .newInstance(path, simulationName, optionalKeyMap(simulationName));
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String
          .format(configurationErrorsResourceBundle.getString("simulationNotSupported"),
              simulationName));
    } catch (Exception e) {
      e.printStackTrace();
      throw new ConfigurationException(String
          .format(configurationErrorsResourceBundle.getString("otherSimulationCreationErrors"),
              e.getClass() + " " + e.getMessage()));
    }

    return grid;
  }
}
