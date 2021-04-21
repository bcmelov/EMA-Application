# Bára Čmelová

## Introduction
Basic implementation of Etnetera Mobile Academy 2021 - Android (EMA) application.
Application provides an access to list of participants of EMA 2021 to users with valid credentials. Each participant has his own profile, which can be accessed by click on user in UserListFragment, and can be marked and saved as favourite.


## Description
LoginActivity: Activity with login form (username, password).
UserListFragment: Fragment with list of participants of EMA 2021. Participants can be filtered based on studied platform (Android/iOS).
UserProfileFragment: Fragment with detailed information about the participant (email, Slack, LinkedIn, profile picture, name, platform, skills (Android, Kotlin, iOS, Swift) on scale 0-10, homework (state: pushed, review, merged)).
ErrorFragment: Landing page in case of errors. On click of a button returns user to UserListFragment.
Database: Database of participants saved as favourite.


## Tools and libraries
- Room: database access
- Retrofit: HTTP Client
- OkHttp
- Glide: image loading (user icons)
- Hilt: used for dependency injections of Shared Preferences
- LiveData (mutable, immutable): fetching and observing data from API endpoints
- Push down effect for buttons: com.github.thekhaeng:pushdown-anim-click:1.1.1

## Image resources:
https://www.flaticon.com/
https://www.iconfinder.com/