package cellsociety.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
      new Pair<>("winThreshold", 3),
      new Pair<>("satisfiedThreshold", 0.3)
  };

  private final Properties properties;
  private final String file;

  public PropertyReader(String propertyFile) throws ConfigurationException {
    file = propertyFile;
    resourceBundle = ResourceBundle
        .getBundle(getClass().getPackageName() + ".resources.ConfigurationErrors");
    try {
      properties = new Properties();
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(file);
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
    } catch (IOException e) {
      throw new ConfigurationException(resourceBundle.getString("errorReadingPropertiesFile"));
    }
  }

  public String getProperty(String key) {
    String ret = properties.getProperty(key);
    if (ret == null) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("errorGettingProperty"), key));
    }
    return ret;
  }

  public void setProperty(String key, String value) {
    properties.setProperty(key, value);
    try {
      PrintWriter writer =
          new PrintWriter(
              new File(this.getClass().getResource(file).getPath()));
      properties.store(writer, null);
    } catch (IOException e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("errorWritingToPropertiesFile"), key, file));
    }
  }

  public Grid gridFromPropertyFile() throws ConfigurationException {
    Path path;
    try {
      path = Paths
          .get(getClass().getClassLoader().getResource(String
              .format("initial_states/%s/%s", properties.getProperty("simulationType"),
                  properties.getProperty("csvFile"))).toURI());
    } catch (URISyntaxException e) {
      throw new ConfigurationException(
          String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }

    String simulationName = properties.getProperty("simulationType");
    return new Grid(path, simulationName);
  }
}
