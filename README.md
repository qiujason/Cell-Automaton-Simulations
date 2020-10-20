visualization
====

This project implements a cellular automata simulator.

Names: Hayden Lau, Jason Qiu, Jack Ellwood

### Timeline

Start Date: October 2, 2020

Finish Date: October 19, 2020

Hours Spent: 50 hours in total

### Primary Roles

Hayden Lau - Backend Development
Jason Qiu - Backend Development
Jack Ellwood - Frontend Development

### Resources Used
* [OpenCSV for CSVReader Documentation](http://opencsv.sourceforge.net/apidocs/com/opencsv/CSVReader.html)
* [OpenCSV for CSVWriter Documentation](http://opencsv.sourceforge.net/apidocs/com/opencsv/CSVWriter.html)

### Running the Program

Main class: Main

Data files needed: 

* properties files in property_lists for each type of simulation 
* csv files in initial_states pertaining to simulation types initial data
* myStyles.css file pertaining to styling for the visualization
* jpg files pertaining to the images for the visualization of the states
* ConfigurationErrors and OptionalKey properties file in the resources folder of the configuration package

Features implemented:

* Configuration:
    * Initial configuration can be randomized through probability for each cell to be of a certain state
    * Initial configuration can be randomized by setting a max number of filled states
    * Initial configuration can be set by passing in a CSV file
    * Properties files are utilized to pass in specific information into the program
    
* Simulation:

* Visualization:


Errors Handled:

* necessary and optional properties missing from the properties files
* empty CSV data
* mismatched CSV data
* invalid value given in CSV data
* type of cellular automata simulation not supported by the program
* unable to write to a data file

### Notes/Assumptions

Assumptions or Simplifications:

* All data to configure a simulation will be passed in by properties files and a corresponding csv file
    
Interesting data files:

Known Bugs:

Extra credit:


### Impressions

