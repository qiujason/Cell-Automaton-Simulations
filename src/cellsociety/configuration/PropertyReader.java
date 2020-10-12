package cellsociety.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class PropertyReader {

  private final ResourceBundle resourceBundle;
  private static final String[] REQUIRED_KEYS = new String[]{
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };
  private static final Pair[] DEFAULT_OPTIONAL_KEYS = new Pair[]{
      new Pair<>("probCatch", 0.3),
      new Pair<>("winThreshold", 3)
  };

  private final Properties properties;

  public PropertyReader(InputStream inputStream) throws ConfigurationException {
    resourceBundle = ResourceBundle.getBundle(getClass().getPackageName()+".resources.ConfigurationErrors");
    try {
      properties = new Properties();
      properties.load(inputStream);
      for (String requiredKey : REQUIRED_KEYS) {
        if (properties.getProperty(requiredKey) == null) {
          throw new ConfigurationException(
              String.format(resourceBundle.getString("missingRequiredProperty"), requiredKey));
        }
      }
      for (Pair optionalKey : DEFAULT_OPTIONAL_KEYS) {
        if (properties.getProperty((String) optionalKey.getKey()) == null) {
          properties
              .setProperty((String) optionalKey.getKey(), String.valueOf(optionalKey.getValue()));
        }
      }
    } catch(IOException e) {
      throw new ConfigurationException(resourceBundle.getString("errorReadingPropertiesFile"));
    }
  }

  public Grid gridFromPropertyFile() throws ConfigurationException {
    Path path;
    try {
      path = Paths
          .get(getClass().getClassLoader().getResource(String.format("initial_states/%s", properties.getProperty("csvFile"))).toURI());
    } catch (URISyntaxException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }

    String simulationName = properties.getProperty("simulationType");
    return new Grid(path, simulationName);
  }
}
