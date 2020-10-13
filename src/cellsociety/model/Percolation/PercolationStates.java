package cellsociety.model.Percolation;


public enum PercolationStates {
  BLOCKED(0), UNFILLED(1), FILLED(2);

  private final int value;

  PercolationStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
