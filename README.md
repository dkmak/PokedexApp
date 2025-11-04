# Android Interview Sample App

## App
An Android application utilizing the public Pokedex API (https://pokedexapi.com/) to display Pokemon

### Design Overview
#### General Project Structure
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
MVVM using Jetpack Compose

#### Libraries and Tools
UI: Jetpack Compose, Coil (for GIF loading)

#### Things of Note
- Unit Testing
  - EntityMappers, ViewModel

#### Potential Improvements
