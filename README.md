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
* [JavaFX CSS Reference Guide](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html)
* [Using JavaFX Charts](https://docs.oracle.com/javafx/2/charts/line-chart.htm#CIHGBCFI)
* [JavaFX Dialogs](https://code.makery.ch/blog/javafx-dialogs-official/)
* [how to control CSS file from the Java controller in JavaFX](https://stackoverflow.com/questions/53539198/how-to-control-css-file-from-the-java-controller-in-javafx)
* [How do I add an image inside a rectangle or a circle in JavaFX?](https://gamedev.stackexchange.com/questions/72924/how-do-i-add-an-image-inside-a-rectangle-or-a-circle-in-javafx)
* [Google Translate](https://www.google.com/search?q=google+translate&rlz=1C1CHBF_enUS759US760&oq=google+trans&aqs=chrome.0.0i131i433i457j69i57j0j0i433l2j0l2j0i433.4037j0j7&sourceid=chrome&ie=UTF-8)

### Running the Program

Main class: Main

Data files needed: 

* properties files in property_lists for each type of simulation 
* csv files in initial_states pertaining to simulation types initial data
* myStyles.css file pertaining to styling for the visualization
* jpg files pertaining to the images for the visualization of the states
* ConfigurationErrors and OptionalKey properties file in the resources folder of the configuration package
* Visualization resources, such as VisualizationErrors, language properties files, stylesheets, and images associated with certain states

Features implemented:

* Configuration:
    * Initial configuration can be randomized through probability for each cell to be of a certain state
    * Initial configuration can be randomized by setting a max number of filled states
    * Initial configuration can be set by passing in a CSV file
    * Properties files are utilized to pass in specific information into the program
    
* Simulation:

* Visualization:
    * Users can easily change between simulations, pause them, play them, step through them, speed them up, or slow them down.
    * When the simulation window size changes, cells are dynamically scaled to the new window size.
    * Cells can be directly clicked on to change state.
    * Users can save the current simulation state and set colors or photos for any state in any simulation and have that information saved.
    * The appearance of the GUI can be changed (e.g. light vs. dark mode)
    * A grid or graph view of the simulation can be viewed, with the graph immediately showing the current state of the grid.
    * Support for English, Spanish, and French.

Errors Handled:

* necessary and optional properties missing from the properties files
* empty CSV data
* mismatched CSV data
* invalid value given in CSV data
* type of cellular automata simulation not supported by the program
* unable to write to a data file
* invalid colors or photos for states in a properties file
* invalid/nonexistant state classes

### Notes/Assumptions

Assumptions or Simplifications:

* All data to configure a simulation will be passed in by properties files and a corresponding csv file
* Data (properties and csv files) must be in the specified folder corresponding to their simulation
* Each properties file includes the states associated with the given simulation and a color or photo associated with each state
    
Interesting data files:

* The Checkerboard segregation simulation is one of the more complex simulations that has numerous different end states based on probabilities.

Known Bugs:

* One small bug is that after changing languages, cells no longer dynamically resize with the window when it's changed (likely something related to the scene being reloaded).

Extra credit:


### Impressions

Overall, this was a difficult and complex assignment that nevertheless split up well between three people, making it a bit more manageable.  We were able to work mostly independently and resolve implementation issues fairly quickly.  We rarely had merge conflicts, which also made collaboration much easier.

