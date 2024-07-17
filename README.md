# TDD_DogFormationApp

**TDD_DogFormation** is a sleek Android app crafted using Test-Driven Development (TDD) and Clean Architecture. Fetch and display delightful dog breeds from the Dog CEO API in a dynamic RecyclerView, featuring images and detailed breed information. Leveraging Kotlin, Hilt for DI, and Coroutine Flows for a responsive and maintainable codebase, this project also showcases KSP for efficient annotation processing and Kotlin DSL for a modern build setup.

## Features

- Display a list of dog breeds with images.
- Show comma-separated sub-breeds for each dog breed.
- Mark breeds as favorites.
- Search for specific dog breeds.
- See all the favorited dogs by clicking the Floating Action Button
- Implemented using TDD and Clean Architecture for maintainable and testable code.

## Technologies Used

- **Kotlin**
- **Material**
- **Hilt for Dependency Injection**
- **Coroutines and Flow for asynchronous programming**
- **Retrofit for networking**
- **Jetpack Components (Navigation Graph, ViewModel)**
- **JUnit and MockK for testing**
- **Dog CEO API**

## Architecture

The project follows Clean Architecture, which separates the code into distinct layers:

- **Presentation Layer**: Contains the UI-related code (Activities, Fragments, ViewModels).
- **Domain Layer**: Contains the business logic, including use cases and entity models.
- **Data Layer**: Contains the data sources, repositories, and models for network and database operations.

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Gradle 7.0 or later

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/TDD_DogFormation.git
   cd TDD_DogFormation
   ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Run the app on an emulator or a physical device.

### Dog CEO API

The app fetches data from the Dog CEO API. No API key is required.

## Tests

The project includes unit and integration tests written with JUnit and MockK. To run the tests:

1. Open the project in Android Studio.
2. Navigate to the `test` directory.
3. Right-click on the test classes or methods and select `Run`.

## Additional Information

### KSP (Kotlin Symbol Processing)

KSP is used in this project to process Kotlin annotations and generate code at compile-time. It provides similar capabilities to KAPT but is designed to be faster and more efficient.

### Kotlin DSL

The project uses Kotlin DSL for Gradle build scripts instead of the traditional Groovy syntax. This offers better syntax highlighting, autocompletion, and type-safety.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

Happy coding! If you have any questions or feedback, feel free to open an issue or contact the project maintainers.

---

Feel free to adjust the links, example test, and other details to match your actual project setup.