# Tree Database Manager 🌳

Проект для управления древовидной структурой данных с синхронизацией между локальным кэшем и удалённым источником.

## 📱 Стек технологий
- **Язык:** Kotlin
- **UI:** Jetpack Compose
- **DI:** Hilt
- **Локальная БД:** Room
- **Архитектура:** Clean Architecture + MVVM
- **Многопоточность:** Kotlin Coroutines

## 🏗️ Архитектура
![Architecture Diagram](./readme-src/diagram.jpg)

Компоненты:
- **Data Layer**:
  - `RemoteDataSource` - работа с удалённым API
  - `LocalDataSource` - управление локальным кэшем (Room)
- **Domain Layer**:
  - `Interactors` - бизнес-логика
  - `TreeDatabaseRepository` - медиатор между источниками
- **Presentation Layer**:
  - `MainScreenViewModel` - управление состоянием
  - `MainScreen` - Compose UI

## 🖼️ Скриншот интерфейса
![App Screenshot](./readme-src/screenshot.png)

Функционал:
- Просмотр иерархической структуры данных
- CRUD-операции с узлами дерева
- Синхронизация между:
  - Локальным кэшем (`Cached Elements`)
  - Удалённой БД (`Remote Database`)
- Управление кэшем (сброс, принудительная загрузка)

## 🚀 Особенности реализации
- Двунаправленная синхронизация данных
- Оптимизированные запросы к Room
- Состояние UI через Compose MutableState
- Валидация операций на уровне Domain
