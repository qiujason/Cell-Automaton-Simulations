package cellsociety.model;

import static org.junit.jupiter.api.Assertions.*;

import cellsociety.configuration.Grid;
import cellsociety.configuration.PropertyReader;
import cellsociety.model.Wator.WatorCell;
import cellsociety.model.Wator.WatorStates;
import org.junit.jupiter.api.Test;

class WatorTest {

  @Test
  void updateAllFish() {
    WatorCell[][] expected = {
        {new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys)},
        {new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys)},
        {new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys)},
        {new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys)},
        {new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys), new WatorCell(WatorStates.FISH, PropertyReader.defaultOptionalKeys)}
    };
    PropertyReader reader = new PropertyReader("property_lists/Wator/AllFish.properties");
    Grid grid = reader.gridFromPropertyFile();
    grid.updateNewStates();

    for (int i = 0; i < expected.length; i++) {
      for (int j = 0; j < expected[i].length; j++) {
        assertEquals(expected[i][j].getMyState(), grid.getMyCells().get(i).get(j).getMyState());
      }
    }
  }

}