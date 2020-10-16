## Lab Discussion
### 7
### Hayden Lau (hpl5)
### Jack Ellwood (jce22)
### Jason Qiu (jq39)


### Issues in Current Code

#### Method or Class
 * Design issues
 
 Current longest method: src/cellsociety/configuration/Grid.java:37 (build2DArray Method).  Can extract
 part of code into its own method to make code more readable.

 * Design issue
 
 Extracting reflection code from Simulation into its own method to reduce duplicated code.

#### Method or Class
 * Design issues
 
 Need better exception handling, primarily in Simulation.  Should use custom exceptions or provide meaningful
 error messages, rather than just printing the stack trace.

 * Design issue
 
 Using better interfacing/not passing entire Lists between classes (tell, don't ask).

### Refactoring Plan

 * What are the code's biggest issues?
 
 General duplication, lack of encapsulation in some areas, and exception handling

 * Which issues are easy to fix and which are hard?
 
 Duplication and exception handling shouldn't be too hard to fix, but making better encapsulation could
 be a bit more challenging, since it requires interfacing across classes.

 * What are good ways to implement the changes "in place"?
 
 For exception handling, making custom exceptions and providing better error messages.  For duplication
 and long methods, refactoring out and making more intentional, smaller methods.  For encapsulation, better
 protecting instance variables (using private variables and getter/setters that provide immutable data where
 possible).


### Refactoring Work

 * Issue chosen: Fix and Alternatives
 
 Longest method: breaking up the large for loop and refactoring into its own method.

 * Issue chosen: Fix and Alternatives
 
 Fixing optional keys, implementing "tell don't ask" type design in configuration.
