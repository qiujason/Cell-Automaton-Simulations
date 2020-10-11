package cellsociety.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class PropertyReader {

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("ConfigurationErrors");
  private static final String[] REQUIRED_KEYS = new String[]{
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };
  private static final Pair[] DEFAULT_OPTIONAL_KEYS = new Pair[]{
      new Pair<>("probCatch", 0.3),
      new Pair<>("winThreshold", 3)
  };

  private final Properties properties;

  public PropertyReader(InputStream inputStream) throws IOException, ConfigurationException {
    properties = new Properties();
    properties.load(inputStream);
    for(String requiredKey : REQUIRED_KEYS){
      if(properties.getProperty(requiredKey)==null){
        throw new ConfigurationException(String.format(RESOURCE_BUNDLE.getString("missingRequiredProperty"), requiredKey));
      }
    }
    for(Pair optionalKey : DEFAULT_OPTIONAL_KEYS){
      if(properties.getProperty((String) optionalKey.getKey())==null){
        properties.setProperty((String) optionalKey.getKey(), String.valueOf(optionalKey.getValue()));
      }
    }
  }

  public Grid gridFromPropertyFile() throws ConfigurationException {
    Path path = Path.of(properties.getProperty("csvFile"));
    String simulationName = properties.getProperty("simulationType");
    return new Grid(path, simulationName);
  }
}
