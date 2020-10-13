package cellsociety.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertyReader {

  public static Map<String, Double> defaultOptionalKeys = new HashMap<>();

  static {
    defaultOptionalKeys.put("probCatch", 0.3);
    defaultOptionalKeys.put("winThreshold", 3.0);
    defaultOptionalKeys.put("satisfiedThreshold", 0.3);
    defaultOptionalKeys.put("thresholdForBirth", 3.0);
  }

  private static final String[] REQUIRED_KEYS = new String[] {
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };

  private final ResourceBundle resourceBundle;
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
      for (String optionalKey : defaultOptionalKeys.keySet()) {
        if (properties.getProperty(optionalKey) != null) {
          defaultOptionalKeys.put(optionalKey, Double.parseDouble(properties.getProperty(optionalKey)));
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
    return new Grid(path, simulationName, defaultOptionalKeys);
  }
}
