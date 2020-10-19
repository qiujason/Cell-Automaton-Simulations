package cellsociety.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class PropertyReader {

  private final Pair[] defaultOptionalKeys = new Pair[] {
      new Pair("probCatch", 0.3),
      new Pair("winThreshold", 3.0),
      new Pair("satisfiedThreshold", 0.3),
      new Pair("thresholdForBirth", 3.0),
      new Pair("edgePolicy", "finite"),
      new Pair("neighborPolicy", "Complete"),
      new Pair("initialStatePolicy", "Csv")
  };
  private final String[] REQUIRED_KEYS = new String[] {
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };
  private final String CONFIGURATION_PATH = "cellsociety.configuration.";

  private final ResourceBundle configurationErrorsResourceBundle = ResourceBundle
      .getBundle(PropertyReader.class.getPackageName() + ".resources.ConfigurationErrors");
  private final ResourceBundle optionalKeyResourceBundle = ResourceBundle
      .getBundle(PropertyReader.class.getPackageName() + ".resources.OptionalKey");

  private final Properties properties;
  private final String file;

  public PropertyReader(String propertyFile) throws ConfigurationException {
    file = propertyFile;
    try {
      properties = new Properties();
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
      properties.load(inputStream);
      for (String requiredKey : REQUIRED_KEYS) {
        if (properties.getProperty(requiredKey) == null) {
          throw new ConfigurationException(
              String.format(configurationErrorsResourceBundle.getString("missingRequiredProperty"), requiredKey));
        }
      }
      if(properties.getProperty("simulationType").contains(" ")){
        throw new ConfigurationException(String.format(configurationErrorsResourceBundle.getString("faultySimulationType"), "remove spaces"));
      }
      for (Pair optionalKey : defaultOptionalKeys) {
        if (properties.getProperty((String) optionalKey.getKey()) == null) {
          properties.put(optionalKey.getKey(), optionalKey.getValue());
        }
      }
    } catch (IOException e) {
      throw new ConfigurationException(configurationErrorsResourceBundle.getString("errorReadingPropertiesFile"));
    }
  }

  public String getProperty(String key) {
    String property = properties.getProperty(key);
    if (property == null) {
      throw new ConfigurationException(
          String.format(configurationErrorsResourceBundle.getString("errorGettingProperty"), key));
    }
    return property;
  }

  public void setProperty(String key, String value) {
    properties.setProperty(key, value);
    try {
      PrintWriter writer =
          new PrintWriter(
              new File(this.getClass().getResource("/" + file).getPath()));
      properties.store(writer, null);
    } catch (IOException e) {
      throw new ConfigurationException(
          String.format(configurationErrorsResourceBundle.getString("errorWritingToPropertiesFile"), key, file));
    }
  }

  public HashMap<String, Object> optionalKeyMap(String simulationType) throws ConfigurationException{
    HashMap<String, Object> optionalKeyMap = new HashMap<>();
    String optionalKeysForSimulation = optionalKeyResourceBundle.getString(simulationType);
    String[] neededOptionals = optionalKeysForSimulation.split(",");
    for(String neededOptional: neededOptionals){
      String value = properties.getProperty(neededOptional);
      if(value==null){
        throw new ConfigurationException(String.format(configurationErrorsResourceBundle.getString("missingRequiredProperty"), neededOptional));
      }
      optionalKeyMap.put(neededOptional, value);
    }
    return optionalKeyMap;
  }

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
          String.format(configurationErrorsResourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }

    String simulationName = properties.getProperty("simulationType");
    String initialConfiguration = properties.getProperty("initialStatePolicy");

    try {
      Class<?> configurationGrid = Class.forName(CONFIGURATION_PATH + initialConfiguration + "Grid");
      Constructor<?> gridConstructor = configurationGrid.getConstructor(Path.class, String.class, Map.class);
      grid = (Grid) gridConstructor.newInstance(path, simulationName, optionalKeyMap(simulationName));
    } catch (ClassNotFoundException e) {
      throw new ConfigurationException(String.format(configurationErrorsResourceBundle.getString("simulationNotSupported"), initialConfiguration));
    } catch (Exception e) {
      throw new ConfigurationException(String.format(configurationErrorsResourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }

    return grid;
  }
}
