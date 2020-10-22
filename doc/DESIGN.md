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

 * New Simulations - 

 * New Types of Simulations - 
 
 * New Types of Grids - 
 
 * Additional Functional Buttons - 
 
 * Colors/Styling -
 
 * Languages - 

## High-level Design

#### Core Classes

 * PropertyReader - 
 
 * Grid (CSV/Probability/TotalLocation) - 
  
 * Cell (GameOfLife/Percolation/RPS/Segregation/SpreadingFire/Wator) - 
 
 * Edge(Finite/InfiniteHorizontal/InfiniteVertical/Toroidal) - 
 
 * Neighborhood(Cardinal/Ordinal/Complete) - 
 
 * Visualization - 
 
 * ButtonHandler - 
 
## Assumptions that Affect the Design

#### Features Affected by Assumptions

 * Users will have properties files and CSV files if they want to add in new simulations

 * Folders and files have to follow a certain formatting and naming (ex. GameOfLife/beacon.properties)
  
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

 * Different cell shapes