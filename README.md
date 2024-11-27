**Overview**
This project utilizes the DogAPI to display a list of dog breeds and their sub-breeds. Users can:

View all available dog breeds and sub-breeds.
Mark main breeds or specific sub-breeds as favorites.
View favorited items in a centralized favorites list.
Favorites persist across app restarts using Room database integration.

**Project Features**
All Dog Breeds List:

Displays all available dog breeds.
Allows independent favoriting of main breeds and sub-breeds.
Favorites List:

Shows favorited main breeds and sub-breeds.
Includes random images for each item.
Dynamically updates when items are added or removed from favorites.
Persisted Favorites:

Favorites are stored in a local Room database.
Data persists across app sessions.

**Technologies and Libraries Used**
Kotlin: The primary programming language for the project.
Jetpack Compose: For building the modern, declarative UI.
Retrofit: To handle API calls and fetch data from the DogAPI.
Coil: For asynchronous image loading and displaying random breed images.
Hilt: For dependency injection, making the codebase modular and testable.
Room: To persist user favorites in a local SQLite database.
Coroutines: To manage background tasks and ensure smooth API communication.
Material Design: For consistent theming and user-friendly UI components.

