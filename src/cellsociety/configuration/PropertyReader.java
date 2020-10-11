package cellsociety.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class PropertyReader {

  private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("ConfigurationErrors");
  private static final String[] requiredKeys = new String[]{
      "simulationType", "simulationTitle", "configAuthor", "description", "csvFile"
  };
  private static final Pair[] defaultOptionalKeys = new Pair[]{
      new Pair<>("probCatch", 0.3),
      new Pair<>("winThreshold", 3)
  };

  private final Properties properties;

  public PropertyReader(InputStream inputStream) throws IOException, ConfigurationException {
    properties = new Properties();
    properties.load(inputStream);
    for(String requiredKey : requiredKeys){
      if(properties.getProperty(requiredKey)==null){
        throw new ConfigurationException(String.format(resourceBundle.getString("missingRequiredProperty"), requiredKey));
      }
    }
    for(Pair optionalKey : defaultOptionalKeys){
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
