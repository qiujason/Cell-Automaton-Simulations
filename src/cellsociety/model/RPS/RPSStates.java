package cellsociety.model.RPS;


public enum RPSStates {
  ROCK(0), PAPER(1), SCISSORS(2);

  private final int value;

  RPSStates(int value) {
    this.value = value;
  }

  public static Enum<?> getStateFromValue(int value) {
    return values()[value];
  }
}
