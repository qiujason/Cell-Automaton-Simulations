package cellsociety.configuration;

import cellsociety.model.Cells.Cell;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public abstract class Grid {

  protected static final String MODEL_PATH = "cellsociety.model.";

  protected final List<List<Cell>> myCells;
  protected final ResourceBundle resourceBundle;
  protected final String simulationName;
  protected final Map optional;

  public Grid(Path cellFile, String simulationName, Map optional) throws ConfigurationException {
    this.simulationName = simulationName;
    this.optional = optional;
    resourceBundle = ResourceBundle.getBundle(getClass().getPackageName()+".resources.ConfigurationErrors");
    myCells = build2DArray(cellFile);
    if(myCells!=null){
      establishNeighbors();
    }
  }

  abstract List<List<Cell>> build2DArray(Path cellFile) throws ConfigurationException;

  public void saveCurrentGrid(String filePath) throws ConfigurationException {
    File file = new File(filePath);
    try {
      FileWriter outputFile = new FileWriter(file,false);
      int row = myCells.size();
      if(row == 0){
        return;
      }
      int col = myCells.get(row-1).size();
      CSVWriter csvWriter = new CSVWriter(outputFile);

      List<String[]> data = new ArrayList<>();
      String[] header = new String[col];
      header[0] = String.valueOf(row);
      header[1] = String.valueOf(col);
      data.add(header);
      for (List<Cell> myCell : myCells) {
        String[] newRow = new String[col];
        for (int j = 0; j < col; j++) {
          newRow[j] = String.valueOf(myCell.get(j).getMyState());
        }
        data.add(newRow);
      }
      csvWriter.writeAll(data);
      csvWriter.close();
    } catch (IOException e) {
      throw new ConfigurationException(String.format(resourceBundle.getString("errorWritingToFile"), file));
    }
  }

  private void establishNeighbors() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        cell.setMyNeighbors(this, (String)optional.get("neighborPolicy"));
      }
    }
  }

  protected double removeHiddenChars(String fileString) throws ConfigurationException{
    final String UTF8_BOM = "\uFEFF";
    final String CARRIAGE_RETURN = "\r";
    fileString = fileString.replace(UTF8_BOM, ""); // accounts for the BOM Character
    fileString = fileString.replace(CARRIAGE_RETURN, ""); // accounts for the carriage return character
    fileString = fileString.replace("\"","");
    try{
      double ret = Double.parseDouble(fileString);
      return ret;
    }
    catch(Exception e){
      throw new ConfigurationException(String.format(resourceBundle.getString("otherSimulationCreationErrors"), e.getMessage()));
    }
  }

  public List<List<Cell>> getMyCells() {
    return myCells;
  }

  public void updateNewStates() {
    myCells.forEach(cells -> cells.forEach(Cell::setNewState));
    myCells.forEach(cells -> cells.forEach(Cell::updateCell));
  }

}

