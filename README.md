# Jetpack Compose Android Sample
This repository is a sample project showcasing the capabilities of Jetpack Compose, the modern Android UI toolkit. Explore and learn the fundamentals of declarative UI development with this hands-on example.
## Dependencies
|Tool| Descriptioin |
|--|--|
| Kotlin | Programming Language |
| Kotlin Coroutines | For async operations |
| Jetpack Coompose | The whole UI of the app is built in Compose |
| Compose Navigation | Navigation betwen different screens |
| Hilt | For dependency injection |
| Room | data persistent library |
| Kotlinx Serialization | To mapping json on POJO models |
| Ktor Client | A network library to consume REST APIs |
## Software Architecture 
### Modules
There are two main modules: 
- `app` module is the main module that holds all features and framework code 
- `buildSrc` module to manage the *gradle* dependencies 
### Design Pattern
The project code follows SOLID principles by implementing the Clean Architecture. 
I have used MVVM and MVI as architectural design patterns. 
### Author
Waseem Abbas 

Sr. Mobile Software Engineer (Android | KMP | Flutter) 

[waseemabbas.com](https://waseemabbas.com)
