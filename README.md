# Android Interview Sample App

## App
An Android application utilizing the public Pokedex API (https://pokedexapi.com/) to display Pokemon as a list.
It fetches paginated Pokemon data from the API, caches it to the local database, which then displays
gets display list on the Home Screen. Users can click on a Pokemon from the list, which then goes 
to the profile page and pulls that individual Pokemon's information from the local database and displays that to the user.

More Information About Pokemon API
https://pokeapi.co/

https://pokeapi.co/docs/v2#resource-listspagination-section

### Design Overview
#### General Project Structure (Multi-Modular)
Multi-modular project setup enforces separation of concerns, improves build times, and helps reusability.
- Modules have distinct responsibilities
- Gradle can build modules in parallel, changes to one module will only recompile that specific module the modules that it depend on it
- Reusable - modules can easily be reused to create new screens and features 

```
app - Assembles all the feauture modules, defines navigation graph, houses the MainActivity
    main
        - MainActivity
        - Navigation routes        
core - Core components shared by all features
    common-ui - shared UI components, elements, themes, colors, resources, strings some navigation components
        - res (drawables, themes)
    data - implements the Repository pattern, create the single source of truth for the UI
        - HomeRepository
        - HomeRepositoryImpl
        - ProfileRepository
        - ProfileRepositoryImpl
        - provides the IO Disptacher as well
    database - defines the local database (Room) for caching Pokemon Information
        - DAO (Data Access Objects)
        - mappings between Entitys and Domain object
    model - defines the Pokemon model used across multiple layers of the application
    network - manages the remote data fetching and provides the API client to the data layer
feature
    home - UI, ViewModel logic for displaying the main list of Pokemon
    profile - UI, ViewModel logic for displaying the detailed profile for a single Pokemon
gradle
    libs.versions - manage libraries, versions
build.gradle
settings.gradle    
```

#### App Architecture
MVVM App Architecture - separate view from business logic from data
- UI Layer: Composables in feature modules observe `StateFlow` exposed by the viewModel 
  - Compose Navigation determines how to move between screens
  - "Dumb" Content Holders: They just display data and handle click events
- ViewModels (Domain Layer): ViewModels contain the presentation logic and handle user events
  - isLoading
  - determines ~when~ to fetch data
- Data Layer: Uses the Repository pattern to abstract where the data is coming from 
  - fetches the data and determines the correct way to process and restore it


#### Libraries and Tools
UI: Jetpack Compose, Compose Navigation, Coil (for GIF loading)
Dependency Injection: Dagger Hilt
Database: Room
Networking: Retrofit (and OkHTTP by extension), Kotlinx.Serialization for parsing JSON
Coroutines for asynchronous tasks
Testing: Junit, `kotlinx-coroutines-test`, `mockk` for mocking Repositories

#### Things of Note
- Unit Testing
  - EntityMappers, ViewModel have unit tests 


#### Potential Improvements
- Separation of Domain Objects and How They Are Used
- Additional Unit Testing in Networking Layer
