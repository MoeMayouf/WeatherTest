# OpenWeatherMap Five Day Forecast

# How to Run:

- Clone project
- If required, run the Clean Project from Android Studio
- Build the project, an active Internet connection might be required
- An emulator or physical device is required to run the project

# Project Overview

# Architecture:

I chose MVP for this project. In addition to the defining characteristics of this pattern, my familiarity with it helped me to prototype in a fast and efficient manner as the task was timed to about four hours. This also allowed me to write modular, concise and testable code with a great degree of separation of Concerns. As the project&#39;s scope grows, Clean architecture can be slowly introduced, with MVP governing the presentation layer to enable further separation of concerns and prevent code-reuse._

# Dependency Injection:

I used Dagger to provide Dependency Injection. It further aided with the vision of a clean, testable project. Elements that would need a wider availability are properly scoped as singletons and are injected at app level. Modules are logically grouped based on the functional traits of the contained dependencies. AppComponent is exposed from the Application class for the activity to initialise and use Dagger.

# Network:

I used Retrofit for the network layer. Serialisation and Deserialisation is done with Gson and RxJava converter is used enable integration with Rx framework. A logger is implemented for debugging purposes with log level set to Basic._

# Database:

I used Room for local persistence. When the data is fetched from the network, a copy of raw data is stored in the local database for offline use/error handling.

# Repository:

Repository pattern is used to further abstract the retrival logic from the presenters. Currently, if there is an error with retrieving the data from the API, and a copy is available in the local database, the latter is used.

# UI:

The app currently shows the five-day weather forecast for the city of Madrid in metric units. It uses icons to indicate a graphical representation of the state of weather for a three-hour duration. It uses a Recyclerview to show the list of predictions and FastAdapter is used as backing adapter for its multitude of functionalities. Glide is used to fetch the images and cache/recycle them in a memory-efficient manner.

# Proposed Improvements

# Features

- Ability to switch between unit of choice, since the data is stored as kelvins any manipulations would be easier down the line
- Dividing days into sections and using the sticky header functionality of FastAdapter to further categorize data
- Allowing user to select a city, with the app&#39;s modular architecture all that is needed is to pass in the new Id. When this functionality is implemented, the presenter should be in charge of resolving the ID for the chosen city instead of being passed directly from the activity.
- Support for landscape layouts
- Subtle, but relevant animations

# Tech Debt:

- Implementing a subcomponent for the UI layer and using scopes to maintain consistency and modularity with Dagger
- Message indicating absence of connectivity and a local cached copy of data
- Implementing more complex logic in repository to leverage cached data
- UI for retry mechanism
- UI refinements
- Using new custom modules for Glide
- Creating styles for common UI attributes
- Creating variables for remaining gradle dependencies

