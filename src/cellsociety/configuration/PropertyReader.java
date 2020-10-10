package cellsociety.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.util.Pair;

public class PropertyReader {
  private Properties properties;
  private ResourceBundle resourceBundle;
  private final String[] requiredKeys = new String[]{"simulationType", "simulationTitle", "configAuthor", "description", "csvFile"};
  private final Pair[] optionalKeys = new Pair[]{};

  public PropertyReader(InputStream inputStream) throws IOException, ConfigurationException {
    properties = new Properties();
    resourceBundle = ResourceBundle.getBundle("ConfigurationErrors");
    properties.load(inputStream);
    for(String requiredKey : requiredKeys){
      if(properties.getProperty(requiredKey)==null){
        throw new ConfigurationException(String.format(resourceBundle.getString("missingRequiredProperty"), requiredKey));
      }
    }
    for(Pair optionalKey : optionalKeys){
      if(properties.getProperty((String) optionalKey.getKey())==null){
        properties.setProperty((String) optionalKey.getKey(), (String) optionalKey.getValue());
      }
    }
  }

  public Grid gridFromPropertyFile() throws ConfigurationException {
    Path path = Path.of(properties.getProperty("csvFile"));
    String simulationName = properties.getProperty("simulationType");
    return new Grid(path, simulationName);
  }
}
