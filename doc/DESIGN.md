# Simulation Design Final
### Names 
Hayden Lau - hpl5
Jack Ellwood - jce22
Jason Qiu - jq39

## Team Roles and Responsibilities

 * Team Member #1
 
    * Hayden Lau - Backend (Configuration)

 * Team Member #2
 
    * Jason Qiu - Backend (Simulation/Model)

 * Team Member #3

    * Jack Ellwood - Frontend (Visualization)

## Design goals

#### What Features are Easy to Add

 * New Simulations - new simulations are very easy to add as they only require a csv file with a header 
                    denoting the grid rows and columns and number values denoting the initial states of 
                    each cell.

 * New Types of Simulations - new types of simulations are very easy to add as they only require a 
                                new rules class and state class. Reflection will take care of the rest
                                and instantiate the new simulation models via the name of the class
 
 * New Types of Grids - 
 
 * Additional Functional Buttons - 
 
 * Colors/Styling -
 
 * Languages - 

## High-level Design

#### Core Classes

 * PropertyReader - 
 
 * Grid (CSV/Probability/TotalLocation) - 
  
 * Cell (GameOfLife/Percolation/RPS/Segregation/SpreadingFire/Wator) - the cell class is the core
 abstract class by which cells are instantiated within the simulation. They store the values and
 changes of states within each cell and support interactions with its neighbors. Specific simulation
 cell class inherit the cell class and the specific rules for the simulation is implemented there.
 
 * Edge (Finite/InfiniteHorizontal/InfiniteVertical/Toroidal) - the edge class is the core abstract 
 class by which cells determine its neighbors. The class is made specifically so that cells on the edge
 can make decisions on what neighbors to include depending on the type of edge class. Edge classes inherit
 this class to implement the specific decisions for that type of edge policy.
 
 * Neighborhood(Cardinal/Ordinal/Complete) - the neighborhood is the core abstract class by which cells 
 can keep track of its neighbors. The class is made so that cells create a grid of neighbors so that
 it can easily access the states and interact with its neighbors. Neighbor classes inherit this class
 to implement what type of neighbor policy to follow. The core class has no abstract methods but it
 can't be instantiated because the specific neighborhood class has to pass in a 2D grid of booleans
 representing what neighbors it should keep track of. 
 
 * Visualization - 
 
 * ButtonHandler - 
 
## Assumptions that Affect the Design

#### Features Affected by Assumptions

 * Users will have properties files and CSV files if they want to add in new simulations

 * Folders and files have to follow a certain formatting and naming (ex. GameOfLife/beacon.properties)
    * The naming has to be consistent and follow case conventions exactly
    * Reflection is used extensively throughout the project, so many files have to follow a specific
    format (like simulation_name + Cell or neighbor_policy + Neighborhood for class names)
  
## Significant differences from Original Plan

 * Neighborhood using Lists instead of Arrays

## New Features HowTo

#### Easy to Add Features

To add new simulations into the project, there are a couple things that one needs to do. One will 
need to add in new directories/folders to the data/property_lists; data/initial_states; and 
src/model/Cells that are all titled the name of the new type of simulations. In the 
initial_states folder, one should put a CSV file in the same format as the rest (first row 
containing two cells for the rows and columns in that order; and numerical values in the 
following rows/columns that correspond to the states). In the property_lists folder, one should 
put in properties files that contain the required keys and values corresponding to them. In the
 Cells folder, one needs to make a Cell Java class that extends the abstract class Cell in which 
 the rules of the simulation type are written for setting new states. There also needs to be a new 
 Enum class in that file with the states that each cell can be.

#### Other Features not yet Done

 * Different grid location shapes (square, hexagonal, etc.)