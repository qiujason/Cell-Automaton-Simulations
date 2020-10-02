# Simulation Lab Discussion

## Hayden Lau - hpl5
## Jack Ellwood - jce22
## Jason Qiu - jq39


## Rock Paper Scissors

### High Level Design Ideas

Create an abstract "weapon" class that can be extended and used for multiple implementations of weapon.
You might use a data structure to store the weapons that a certain class can "beat," and those that
it can't.  We can create another "player" abstraction that can be extended for a human player class
and a computer player class.

### CRC Card Classes

This class's purpose or value is to manage something:
```java
 public abstract class Weapons {
     private String myType;

     public abstract boolean doesBeat(Weapon opponentWeapon);
     public String getType();
 }
```

This class's purpose or value is to be useful:
```java
 public abstract class Player {
     private String myName;
     private int myScore;
     public abstract Weapon chooseWeapon();
     public void setScore(int score);
     public int getScore();
 }
```

The class to run a game:
```java
 public class Game {
     private List<Player> myPlayers;
     private List<Weapons> myWeapons;
     private Map<Weapons, List<Weapons>> myWeaponsThatWin;
      
     public Game(List<Player> players, List<Weapons> weapons);
     public void nextRound(List<Players> players);
     public List<Player> chooseWinner(List<Pair<Player, Weapons>> playersAndWeapons);
     public void addNewWeapon(Weapon weapon);

 }
```

### Use Cases

### Use Cases

 * A new game is started with five players, their scores are reset to 0.
 ```java
Game myGame = new Game(players, weapons);
for(Player player : players){
  player.setScore(0);
}
 ```

 * A player chooses his RPS "weapon" with which he wants to play for this round.
 ```java
 player.chooseWeapon();
 ```

 * Given three players' choices, one player wins the round, and their scores are updated.
 ```java
 List<Player> winner = myGame.chooseWinner(playersAndWeapons);
 if(winner.size() == 1){
  winner.setScore(winner.getScore() + 1);
 }
 ```

 * Given two players' choices, they tie, and need to play the current round again.
 ```java
 List<Player> winners = myGame.chooseWinner(playersAndWeapons);
 if(winner.size() > 1){
  myGame.nextRound(winners);
 }
```

 * A new choice is added to an existing game and its relationship to all the other choices is updated.
 ```java
 myGame.addNewWeapon(weapon);
 ```

 * A new game is added to the system, with its own relationships for its all its "weapons".
 ```java
 myGame = new Game(players, weapons);
 ```


## Cell Society

### High Level Design Ideas


### CRC Card Classes

This class's purpose or value is to manage something:
```java
public class Something {
    public int getTotal (Collection<Integer> data)
    public Value getValue ()
}
```

This class's purpose or value is to be useful:
```java
public class Value {
    public void update (int data)
}
```

### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Move to the next generation: update all cells in a simulation from their current state to their next state
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
Something thing = new Something();
Value v = thing.getValue();
v.update(13);
```
