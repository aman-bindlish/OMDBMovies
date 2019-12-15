# OMDB Movies - A sample movies search App (Kotlin)

A basic master/detail android application which shows movies list using Omdb public api.

#### App Features
* App fetches movies list and displays in RecyclerView.
* App built on master/detail flow pattern, so if launched on tab screens will be visible side by side.
* App shows shimmer effect while data is being fetched from server.
* 100% offline support. If data has been fetched once, then it will be served from db (Room).
* Repository class where database serves as a single source of truth for data.
* Error cases handled and an error screen with retry button is shown in case of error.

#### App Architecture 
Based on MVVM architecture and repository pattern, where database serves as a single source of truth for data.

#### App Specs
- Minimum SDK 19
- [Java8](https://java.com/en/download/faq/java8.xml)
- [Kotlin](https://kotlinlang.org/)
- MVVM Architecture
- Android Architecture Components (LiveData, Lifecycle, ViewModel, Room Persistence Library, ConstraintLayout)
- [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [Dagger 2](https://google.github.io/dagger/) for dependency injection.
- [Paging](https://developer.android.com/topic/libraries/architecture/paging) for paging implementation for scrolling and fetching data.
- [Retrofit 2](https://square.github.io/retrofit/) for API integration.
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) for data observation.
- [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel) for data container.
- [Room](https://developer.android.com/topic/libraries/architecture/room) for data persistence.
- [Gson](https://github.com/google/gson) for serialisation.
- [Okhhtp3](https://github.com/square/okhttp) for implementing interceptor, logging and mocking web server.
- [Glide](https://github.com/bumptech/glide) for image loading.

#### App includes following main components:
* A local database with Room that servers as a single source of truth for data to display on UI. 
* A public omdb api for fetching data from server.
* A repository that works with the database and the api service, providing a unified data interface.
* A ViewModel that provides data specific for the UI.
* The UI, which shows a visual representation of the data in the ViewModel.

#### App Packages
* <b>data</b> package containing classes related to api, response and repository.
* <b>db</b> package containing classes for local room database and dao.
* <b>di</b> package containing classes for dependency injection (modules, components).
* <b>ui</b> package containing classes for activity, fragments and adapter.
* <b>viewmodel</b> package for viewmodel classes.

##### You can right back to me if you have any suggestion which can improve the architecture.