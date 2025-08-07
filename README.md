# assessment_souce_code_kafabih

GitHub User Search App
This is an Android application built with Jetpack Compose that allows users to search for GitHub users, view a list of results, and see detailed information about a selected user. The app is designed with a focus on modern Android development practices, including a clean architecture, dependency injection, and local data persistence.

Features
User Search: Search for GitHub users by their username.

Paginated List: Search results are loaded in pages for a smooth scrolling experience.

User Details: View detailed information for a user, including their name, follower/following count, public repositories, and email.

Local Caching: All successfully fetched user data is saved to a local Room database, allowing for offline access and persistence between app sessions.

Modern UI: A clean, modern user interface built entirely with Jetpack Compose, featuring a skeleton loading effect for a polished user experience.

Background Sync: Uses WorkManager to schedule periodic background updates to the local cache (example implementation).

Network Debugging: Integrates Chucker to inspect all network traffic directly on the device in debug builds.

Runtime Permissions: Programmatically requests necessary permissions (e.g., notifications for Chucker on Android 13+).

Architecture
This project follows the principles of Clean Architecture with an MVVM (Model-View-ViewModel) pattern in the presentation layer. This approach was chosen to create a separation of concerns, making the codebase more scalable, maintainable, and testable.

The application is divided into three main layers:

Presentation Layer: This layer is responsible for the UI and user interactions.

UI (Composable Functions): Built with Jetpack Compose, these are responsible for displaying the state provided by the ViewModel.

ViewModel: Manages the UI state and exposes it to the UI via StateFlow. It communicates with the Domain layer to execute business logic.

Domain Layer: This is the core of the application and contains the business logic.

Use Cases: These are individual classes that encapsulate a single business rule (e.g., SearchUsersUseCase, GetUserDetailsUseCase). They are independent of the Android framework.

Data Layer: This layer is responsible for all data operations.

Repository: The single source of truth for the app's data. It decides whether to fetch data from the network or the local cache.

Data Sources:

Remote: Retrofit and Moshi are used to communicate with the GitHub REST API.

Local: Room is used to manage the local SQLite database for caching user data.

How to Build and Run
Prerequisites
Android Studio (latest stable version recommended)

JDK 17 or higher

Steps
Clone the repository:

git clone https://github.com/Kafabih/assessment_souce_code_kafabih

Open in Android Studio:

Open Android Studio.

Select "Open an Existing Project" and navigate to the cloned repository folder.

Sync Gradle:

Wait for Android Studio to sync the project with the Gradle files. This will download all the necessary dependencies defined in the libs.versions.toml file.

Run the app:

Select an emulator or connect a physical device.

Click the "Run" button (or press Shift + F10).

Additional Features & Challenges
Improvements
Chucker: Integrated for easy inspection of network requests and responses in debug builds. This is incredibly helpful for debugging API issues.

WorkManager: A simple CacheUpdateWorker is included to demonstrate how background data syncing can be implemented to keep the local cache fresh.

Moshi: Used for JSON parsing, which is generally more performant and less prone to reflection issues than Gson.

Unit & UI Testing: The project is set up with dependencies for JUnit, MockK, and Compose UI tests, providing a foundation for comprehensive test coverage.

Challenges Encountered
Dependency Management: A key challenge in modern Android development is ensuring that all dependencies, especially those related to the Kotlin and Compose compilers, are compatible. This was solved by using a libs.versions.toml file to manage all versions centrally and aligning them to a stable, compatible set.

Room Migrations: Setting up Room's auto-migration feature required careful configuration of the Room Gradle plugin to handle schema exports (exportSchema = true). The process involved first generating the initial schema for version 1 before implementing the migration to version 2.