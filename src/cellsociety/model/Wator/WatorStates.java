package cellsociety.model.Wator;

public enum WatorStates {
  EMPTY(0), FISH(1), SHARK(2), DEAD;

  private final int value;

  WatorStates() {
    this.value = -1;
  }

  WatorStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
