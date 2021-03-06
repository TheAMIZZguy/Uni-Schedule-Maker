# Class and Event Permutation Calculator

## Doing all of the planning for you

###### What will the application do?  Who will use it?
The main objective of this project is for a user to input all 
subevents/classes that they wish to take (along their respective possible times), 
and to then receive all possible schedules possible from such combinations. 
The project should be able to apply tags to the events for increased functionality;
Example: 
1) The user can state that if one specific event takes place,
another specific event in their list shouldn't be placed.
2) Placing event/class priority, so possible schedules with those preferences should 
be listed first

###### Why is this project of Interest to you?  
Planning for a specific class schedule can take a while, and making several of them is 
a tedious task. Similarly, if a class fills up while you are registering can make it
really hard to switch things around if you didn't plan for a specific thing to happen. 
This project will help with that, allowing me to **instantly** apply *filters* to 
know what my  possible options are. Such a project can also be **generalized** to event 
planning where you might want multiple things to happen at a specific panel, but can't
 have them all at the same time.

# User Stories

- As a user, I want to be able to add a class to my list of courses I want to take
- As a user, I want to be able to add extra parts to a class (if it has a lab or tutorial)
- As a user, I want to be able to view the List of Possible Schedules (Lop)
- As a user, I want to be able to choose how to add schedules to the LoP
- As a user, I want to be able to filter through completed schedules
- As a user, I want to be able to delete a schedule from my LoP
- As a user, I want to be able to delete a course from my saved courses
- As a user, I want to be able to save my schedules in a file 
- As a user, I want to be able to be able to load my schedules from a file  
- As a user, I want to be able to save my courses in a file 
- As a user, I want to be able to be able to load my courses from a file  
- As a user, I want to be able to view the loaded and unloaded courses 


# Phase 4: Task 2
- Make appropriate use of the Map interface somewhere in your code. 
   - Class: Model > Course
      - Use: Course Name to Course Time relation

# Phase 4: Task 3
- The biggest thing about the project I feel could use improvement is refactoring in the Course class. 
  - Looking at the class closely it is clear that there is a lot of "triple repetition" 
  - as all the code for subClass, lab, and tutorial are nearly identical and so are repeated often. 
  - The same exact problem also extends to other classes like
CourseAdder which works on Course based data.
- A similar problem also occurs in the JsonReader and JsonWriter classes as there is near identical code for
cases where something is either a CourseList or Schedule list. Which can be easily refactored.
- Other than that, class communication between and within the model and persistence packages are quite good.
- Within the model package MainFrame does most/all of the communication 
   - Since TableSchedulePanel and CourseListDetailer both have MainFrame as a field
      -  Then instead of having their own Scheduler and Course fields, they could use some in MainFrame instead
   - Though there is some "double communication" such as MainFrame -> Designer -> Course and MainFrame -> Course
      - This is not a big problem as the types of uses Designer has of Course and MainFrame has of Course are different
      so refactoring wouldn't help much
   - Also there is probably a way to refactor things so that classes don't need to have a MainFrame object, but I 
   couldn't figure out how. To reduce depenencies. 