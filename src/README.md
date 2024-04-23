Overview of NUPlanner System:
- The NU Planner System is used for scheduling and managing events.

- To get started with the NUPlanner system, you can create and instantiate of the central system
  and add a user and their events like so:

CentralSystemModel centralSystem = new CentralSystem();
String userId = "user123";
centralSystem.addUser(userId);

EventModel event = new Event("CS101 Lecture", "Room 101", false,
LocalDateTime.of(2024, 3, 14, 10, 0),
LocalDateTime.of(2024, 3, 14, 11, 0));
centralSystem.createEvent(userId, event);

Key Components:
- CentralSystem: Manages user schedules, can be used as the entry point for adding or removing
  users and their events.
- Schedule: Represents a single user's schedule, that contains all their events and methods
  to manipulate these events.
- Event: Crates an individual event with properties such as name, start and end times, location,
  and weather it is online or not.
- EventFrame: GUI allowing the user to create, modify, or delete an event
- CentralSystemFrame: GUI allowing for a visualization of the current user's schedule (includes
a dropdown of every user within cnetral system)
- ScheduleController: connects the model to the view

Key Subcomponents
- EventModel and ScheduleModel: These interfaces define the main behaviors that should be given
  by any implementation of an event or schedule to make sure that it is managed across the system.
- XMLConfigurator: Uses XML files to read and write event data, allows for a way to externalize
  schedule information.

Source Organization
- model/: Contains core classes like CentralSystem, Event, and Schedule that define the system's
  data model.
- view/: Includes PlannerTextView that gives textual visualization of schedules.
- XML/: Contains the XMLConfigurator class, used for reading and writing XML data.
- controller/: Contains the ScheduleController class, used for connecting the model to the view

We also documented an invariant in the Event class on line 46. We documented what the class
invariant was and how we enforced it. (We weren't exactly sure where to write it so we put comments
and also noted it here in the ReadMe just in case)

Changes for PT2:
- Created a read only interface for each class in the model that we made. The model for each class 
- extends the ReadOnlyModel that we created.
- Implemented the methods that we added the readonly implementations.
- Used the readonly interfaces to hide the implementation details of the class and only give the 
- client.
- Added to the view, so EventFrame and its interface, CentralSystemFrame, and its interface.
- Implemented those classes for the GUI and their frame.

Changes for PT3:
- implemented a controller to adhere to the MVC pattern, creating a ScheduleController class that interacts with the model and the view.
- connected the view classes (CentralSystemFrame, EventFrame, ScheduleFrame) to the controller by adding a setController method and adjusting constructors.
- refactored event handling in the GUI, delegating actions to the controller to ensure the view doesn't directly modify the model.
- introduced a ScheduleFrame class for event scheduling, allowing users to input details for automatic scheduling based on strategies.
- implemented scheduling strategies ("Any time" and "Work hours") in the controller for automatic event scheduling.
- modified ScheduleFrame to include fields for scheduling strategies and replaced the user combo box with a text field for invitees.
- improved the method for gathering event details, enhancing date and time parsing. 
- added logic in the controller for creating, modifying, and removing events, using the model's capabilities and updating the view accordingly.
- adjusted the layout and size of ScheduleFrame to improve user interaction and visual appeal.
- introduced methods to manage the "current user" in the model and controller for better scheduling and event management.
- enhanced error handling and user feedback in the GUI, ensuring users are informed about operations and issues.


Changes for made for customers for HW8:
- Refactored our code so that fields and parameters use interface types instead of Object types for our customers
- Created a strategy package to handle strategic scheduling
- Created SchedulingStrategy interface with 2 classes each implementing their strategy
- Removed the strategy implementation from view into each respective class
- Created adapters for the provided code
We made all of those changes because, otherwise, our code wouldn't work with our customers. Making these changes
did not break our code, and, if anything, enhanced it as explained in the self review.

What works and doesn't work:
- Given that the code we were provided had a lot of issues and didn't work correctly (given that it was 
tightly coupled), there wasn't much we could do. Our implementation doesn't work.

Extra Credit:

Level 0:

Level 1:
For this level we decided to create two class. SaturdayEventRenderer and StandardEventRenderer. We noticed that there was a lot of code that
could be reused since both classes had the same functionality but were just slightly different in the way they determined. So we made
Schedule an abstract class and had the two SaturdaySchedule and StandardSchedule extend the Schedule class.

Level 2:
For this level we decided to create some new classes to help us create the new planner where the starting day is saturday.
We created some new classes to help us render the events that we wanted to add. We made one for the Saturday and one for Sunday.
The SaturdayEventRenderer class changes the way events are laid out on the grid where StandardEventRenderer keeps the functionality
of the old code.

Level 3:
For this level we only had to make minor adjustments to our strategy based on the starting das as Saturday. We changed our WorkHoursStrategy class
by altering the findStartTime() method only and refactoring of the helper method to be used by both AnyTime and WorkHours since it was the same logic.