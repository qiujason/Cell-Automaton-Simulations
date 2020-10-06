package cellsociety;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Grid {

  private List<List<Cell>> myCells;
  private Map<Cell, Integer> newState;

  public Grid(Path cellFile) throws IOException {
    newState = new HashMap<>();
    myCells = build2DArray(cellFile);
    if(myCells!=null){
      establishNeighbors();
    }
  }

  private List<List<Cell>> build2DArray(Path cellFile) throws IOException {
    Scanner sc = new Scanner(cellFile);
    sc.useDelimiter(",|\\n");//sets the delimiter pattern
    int rows = getNextInteger(sc);
    int cols = getNextInteger(sc);
    List<List<Cell>> ret = new ArrayList<>();
    for (int k = cols - 2; k > 0; k--) { // gets to end of header line
      sc.next();
    }
    for (int i = 0; i < rows; i++) {
      ret.add(i, new ArrayList<>());
      for (int j = 0; j < cols; j++) {
        int cellValue = getNextInteger(sc);
        if (cellValue == -1) {
          return null;
        }
        Cell newCell = new Cell(cellValue);
        ret.get(i).add(j, newCell);
        newState.put(newCell, cellValue);
      }
    }
    sc.close();  //closes the scanner
    return ret;
  }

  public Path saveCurrentGrid(String filePath) throws IOException {
    File file = new File(filePath);
    FileWriter outputFile = new FileWriter(file,false);
    int row = myCells.size();
    if(row == 0){
      return null;
    }
    int col = myCells.get(row-1).size();
    CSVWriter csvWriter = new CSVWriter(outputFile);

    List<String[]> data = new ArrayList<>();
    String[] header = new String[col];
    header[0] = String.valueOf(row);
    header[1] = String.valueOf(col);
    data.add(header);
    for(int i = 0; i<row; i++){
      String[] newRow = new String[col];
      for(int j = 0; j<col; j++){
        newRow[j] = String.valueOf(myCells.get(i).get(j).getMyValue());
      }
      data.add(newRow);
    }
    csvWriter.writeAll(data);
    csvWriter.close();
    return Path.of(filePath);
  }

  private void establishNeighbors() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        cell.setMyNeighbors(this);
      }
    }
  }

  private int getNextInteger(Scanner sc) {
    final String UTF8_BOM = "\uFEFF";
    final String CARRIAGE_RETURN = "\r";
    if (sc.hasNext()) {
      String nextNumber = sc.next();
      nextNumber = nextNumber.replace(UTF8_BOM, ""); // accounts for the BOM Character
      nextNumber = nextNumber
          .replace(CARRIAGE_RETURN, ""); // accounts for the carriage return character
      nextNumber = nextNumber.replace("\"","");
      return Integer.parseInt(nextNumber);
    }
    return -1;
  }

  public List<List<Cell>> getMyCells() {
    return myCells;
  }

  public Map<Cell, Integer> getNewState() {
    return newState;
  }

  public void updateNewStates() {
    getNewStates();
    updateStates();
  }

  private void getNewStates() {
    for (List<Cell> cells : myCells) {
      for (Cell cell : cells) {
        newState.put(cell, cell.getNewState());
      }
    }
  }

  private void updateStates() {
    for (Entry<Cell, Integer> cellState : newState.entrySet()) {
      cellState.getKey().updateCell(cellState.getValue());
    }
  }
}

