# My Mobile Life




## Documentation

Pagination in Android using Kotlin

This repository provides a simple implementation of pagination in Android using Kotlin.

This is done in two ways:

1. Using Scroll Listener
2. Using Paging Library from the Android Jetpack components to load data in chunks and display it to the user. The data is fetched from an API using Retrofit and displayed in a RecyclerView.

Getting Started
To use this project, you need to have Android Studio installed on your system. You can download the latest version of Android Studio from the official website. Once you have Android Studio installed, follow the steps below:

Clone this repository to your local machine using the following command:

git clone https://github.com/ArditaKr/MyMobileLife.git

Open Android Studio and select "Open an existing Android Studio project" from the welcome screen. Navigate to the location where you cloned the repository and select the project.

Wait for Android Studio to finish building the project. Once the build is complete, you should be able to run the app on an emulator or a physical device.

How it Works
The app uses the Paging 3 library to load data from an API in chunks. The data is displayed in a RecyclerView using a custom adapter. The adapter uses a DiffUtil callback to efficiently update the contents of the RecyclerView when new data is loaded.

The app uses a ViewModel to manage the state of the data and the pagination. The ViewModel exposes a MutableLiveData object that contains the data to be displayed in the RecyclerView. The MutableLiveData object is observed by the Fragment, which updates the RecyclerView when new data is loaded.

The app uses Retrofit to fetch data from an API. The Retrofit service is defined in the ServiceAPI interface. The app uses a Repository class to manage the API calls 





