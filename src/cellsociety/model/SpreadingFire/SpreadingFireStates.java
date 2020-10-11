package cellsociety.model.SpreadingFire;


public enum SpreadingFireStates {
  EMPTY(0), TREE(1), BURNING(2);

  private final int value;

  SpreadingFireStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
