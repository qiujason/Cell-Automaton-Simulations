package cellsociety;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CellTest {

  @Test
  void setMySquareNeighbors() throws URISyntaxException {
    Path path = Paths.get(getClass().getClassLoader().getResource("initial_states/Square.csv").toURI());
    try {
      Grid grid = new Grid(path);
      Cell[][] expected = {
          {null, null, null},
          {null, grid.getMyCells().get(0).get(0), grid.getMyCells().get(0).get(1)},
          {null, grid.getMyCells().get(1).get(0), grid.getMyCells().get(1).get(1)}
      };
      Cell[][] actual = grid.getMyCells().get(0).get(0).getMyNeighbors().getMyCells();
      for(int i = 0; i < expected.length; i++){
        for(int j = 0; j < expected[i].length; j++){
          if(expected[i][j]==null){
            assertEquals(expected[i][j], actual[i][j]);
          } else {
            assertEquals(expected[i][j].getMyValue(), actual[i][j].getMyValue());
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}