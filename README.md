# WeatherTrack App & Coding Problems Repository

This repository contains a simple Android weather tracking application along with solutions to two classic algorithm problems: **N-Queens** and **Cycle Detection in a Directed Graph**.

---

## 📱 WeatherTrack App

**WeatherTrack** is an Android app designed to help users track daily weather statistics in their city. The app fetches mock weather data, stores it locally, and displays a weekly summary of temperature trends.

### ✨ Features

- **Fetch Weather**: Retrieves current weather (temperature, humidity, condition) from a static/mock API and stores it locally using Room.
- **Auto Sync**: Uses `WorkManager` to automatically fetch and save weather data every 6 hours in the background.
- **Manual Refresh**: Provides a button to manually fetch the latest weather.
- **Weekly Summary Screen**: Displays a 7-day graph/list of temperature changes. Users can tap a day to view detailed stats.
- **Error Handling**: User-friendly messages for no internet, API errors, and database issues.

### 🧱 Architecture

- **Language**: Java
- **Pattern**: MVVM (Model-View-ViewModel)
- **Layers**:
  - `ViewModel`: Handles UI logic and state
  - `Repository`: Acts as a mediator between ViewModel and data sources
  - `Data Source`: Includes network (mock API) and local Room database

### 📂 Key Modules

- `MainActivity` – UI and lifecycle management
- `WeatherViewModel` – Business logic and LiveData
- `WeatherRepository` – Data coordination
- `WeatherApiService` – Mock weather data provider
- `WeatherDatabase` – Room database setup
- `WeatherWorker` – Background fetch scheduler using WorkManager

### 🔧 Dependencies

- Room (local database)
- Retrofit (mock network calls)
- WorkManager (background tasks)
- MPAndroidChart (for graph plotting)

---

## 💡 Coding Challenges

### 1. N-Queens Problem

Solves the N-Queens problem using backtracking. The solution returns all possible arrangements of placing N queens on an N×N board such that no two queens threaten each other.

**File**: `N-Queens.cpp`

### 2. Detect Cycle in Directed Graph

Implements a graph traversal algorithm to detect if there's a cycle in a directed graph using DFS and recursion stack tracking.

**File**: `Detect-Cycle-DirectedGraph.cpp`

---

## 🏁 Getting Started

### WeatherTrack App

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/WeatherTrackApp.git
Open in Android Studio.

Sync Gradle and Run the project on an emulator or device.

Coding Challenges
Navigate to the coding_questions/ folder.

Compile and run the C++ files:

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.

🙋‍♂️ Author
Pintu Kumar Baranwal
Final Year Student, IIT BHU
Passionate about Android development, algorithms, and building scalable software.
