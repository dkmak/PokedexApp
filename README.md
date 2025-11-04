# Android Interview Sample App

## App
An Android application utilizing the public Pokedex API (https://pokedexapi.com/) to display Pokemon.
It fetches paginated Pokemon data from the API, caches it to the local database, which then displays
it as a list on the Home Screen. Users can click on a Pokemon from the list, which pulls that individual's 
information from the local database and displays that to the user.

### Design Overview
#### General Project Structure (Multi-Modular)
Multi-modular project setup enforces separation of concerns, improves build times, and helps reusability

```
app - Assembles all the feauture modules, defines navigation graph, houses the MainActivity
    main
        - MainActivity
        - navigation routes        
core - Core components shared by all features
    common-ui - shared UI components, elements, themes, colors, resources, strings some navigation components
        - res (drawables, themes)
    data - implements the Repository pattern, create single source of truth
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

#### Architecture
MVVM Architecture
- UI Layer: Composables in feature modules observe `StateFlow` exposed by the viewModel
  - Compose Navigation determines how to move between screens
- ViewModels (Domain Layer): ViewModels contain the presentation logic and handle user events
- Data Layer: Uses the Repository pattern to abstract where the data is coming from 


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
- Separation of Domain Objects used in Networking, UI
- Additional Unit Testing in Networking Layer
- 
