# MidTermMobile

## Student Manager (MVVM + Room + Coroutines)

This app now has a local Student Manager feature built with:

- MVVM: `StudentViewModel` + `StudentViewModelFactory`
- Room: `AppDatabase`, `StudentDao`, `Student` entity
- Repository: `StudentRepository`
- Coroutines + Flow: realtime list update from Room to UI

## PostgreSQL (attach later)

Extension points are ready in:

- `PostgresSyncDataSource`
- `NoOpPostgresSyncDataSource` (placeholder)
- `StudentRepository.syncFromPostgres()`
- `StudentRepository.syncToPostgres()`

Current flow keeps data local in Room. Later, connect `PostgresSyncDataSource` to your backend API (which writes/reads PostgreSQL), then call sync methods from UI when needed.
