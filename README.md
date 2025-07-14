# Tree Database App 🌳

A test project implementing a tree-like data structure in an Android application using modern architecture principles.

## Context

This project was created as a **technical assignment for a product company**. While the app itself does not have standalone product value, it **simulates working with complex, hierarchical data structures and large datasets**, which makes it a solid demonstration of architectural decisions and technical skills.

## Technologies
- Kotlin
- Jetpack Compose
- Dagger/Hilt
- Room
- Clean Architecture + MVVM
- Flow, Kotlin Coroutines

## Screenshot
![App Screenshot](./readme-src/screenshot.png)

## Main requirement
implement synchronization through this interface:

```
interface TreeDatabaseApi {
  suspend fun fetchNode(id: String): NodeData?
  suspend fun apply(nodes: List<NodeData>)
}
```

## Goals

The primary goal was not just to meet the assignment requirements but to build the app using **modern Android development best practices**, including:

- **Jetpack Compose** for UI
- **Dagger/Hilt** for DI
- **Unidirectional data flow (MVI pattern)**
- **Clean Architecture** with clear separation into `data`, `domain`, and `presentation` layers
- **Kotlin Coroutines** and **StateFlow** for reactive state management
- **Decoupled logic** for caching and syncing between in-memory and persistent representations of the tree

## Features

- Add, edit, and delete tree nodes
- Two separate views:
    - One for the **cached tree**
    - One for the **database tree**
- Ability to sync changes between cache and database
- Visualization of recursive tree structures
- Optimized for performance when dealing with large node trees

## Main components
![Architecture Diagram](./readme-src/diagram.jpg)

## Limitations
- This is a sandbox-style test project and **not intended for production**
- Minimal UI/UX polish due to focus on architecture and logic
- No persistent database (in-memory simulation for both cache and "database")

## Outcome

Despite the abstract nature of the task, I was able to **successfully implement all the requested features** and demonstrate a clean and scalable project structure. The experience also shows how to **model, navigate, and manage large and nested data trees** efficiently in a Compose-based Android app.
