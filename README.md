# ShareCare
Мобильное приложение для проекта по "Обучению служением"
>[!NOTE]
>«Добродар» – платформа в формате **мобильного приложения**,​ предназначенная​ для поддержки социально уязвимых категорий граждан: малообеспеченных семей,​ одиноких пенсионеров, людей в трудной жизненной ситуации – **на основе системы​ безвозмездного обмена с автоматизацией создания объявлений с помощью**

### Поддерживаемые платформы:
- iOS (18.2+)
- Android (13+)
>Поддержка Android 12 и ниже **возможна**, если добавить fallback'и к блюру

# QuickStart
1) Поставить [сервер](https://github.com/ShareCare-MAI-project/Backend) _(без него не получится пройти регистрацию и т.п.)_
2) Импортировать проект в Android Studio или IntelliJ IDEA
3) Сбилдить проект: 
    - composeApp для Android и других платформ
    - iosApp для iOS
> Для билда лучше всего пользоваться [плагином KMP](https://plugins.jetbrains.com/plugin/14936-kotlin-multiplatform)

>[!CAUTION]
>iOS можно сбилдить только на MacOS


# Техническая информация
### Стек:
>В основе стека лежит [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html), т.к. это новый тренд в Android-разработке
- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html): Язык
- [Compose Multiplatform](https://www.jetbrains.com/ru-ru/compose-multiplatform/): Кросплатформенный UI
- [Decompose](https://github.com/arkivanov/Decompose): Библиотека для навигации и архитектуры компонентного подхода
- [Ktor](https://ktor.io/): Запросы в сеть
- [Koin](https://insert-koin.io/): Dependency Injection
- [Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings): Хранение пар ключ-значение (обёртка над SharedPreferences и NSUserDefaults)
- [Coil](https://github.com/coil-kt/coil): Асинхронная загрузка картинок
- [Haze](https://chrisbanes.github.io/haze/latest/): Мультиплатформенный блюр :+1:
- Kotlinx: Coroutines, Serialization, DateTime

### Архитектура:
>[!IMPORTANT]
>В проекте представлена **MVVM** архитектура с DDD подходом (Domain-Data-Presentation)
<img width="4483" height="1604" alt="arc" src="https://github.com/user-attachments/assets/fed30a9a-c903-4a98-bcfe-d91d5c17b7bd" />

