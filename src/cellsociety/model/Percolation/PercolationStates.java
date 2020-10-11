package cellsociety.model.Percolation;


public enum PercolationStates {
  UNFILLED(0), FILLED(1);

  private final int value;

  PercolationStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
