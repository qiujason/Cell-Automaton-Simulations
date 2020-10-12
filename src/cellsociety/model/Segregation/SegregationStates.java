package cellsociety.model.Segregation;

public enum SegregationStates {
  EMPTY(0), A(1), B(2), SATISFIED, UNSATISFIED;

  private final int value;

  SegregationStates() {
    this.value = -1;
  }

  SegregationStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }

}
