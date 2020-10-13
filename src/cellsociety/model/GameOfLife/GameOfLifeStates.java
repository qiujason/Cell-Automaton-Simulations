package cellsociety.model.GameOfLife;


public enum GameOfLifeStates {
  DEAD(0), ALIVE(1);

  private final int value;

  GameOfLifeStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
