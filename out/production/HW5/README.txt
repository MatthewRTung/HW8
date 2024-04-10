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

We also documented an invariant in the Event class on line 46. We documented what the class
invariant was and how we enforced it. (We weren't exactly sure where to write it so we put comments
and also noted it here in the ReadMe just in case)