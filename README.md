# Android Boilerplate

It demonstrates the architecture, tools and guidelines that we use when developing for the Android platform (https://github.com/ribot/android-guidelines)

Libraries and tools included:

- Support libraries
- RecyclerViews and CardViews 
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [SqlBrite](https://github.com/square/sqlbrite)
- [Timber](https://github.com/JakeWharton/timber)
- [Glide](https://github.com/bumptech/glide)
- [AutoValue](https://github.com/google/auto/tree/master/value) with extensions [AutoValueParcel](https://github.com/rharter/auto-value-parcel) and [AutoValueGson](https://github.com/rharter/auto-value-gson)

## Requirements

- JDK 1.8
- [Android SDK](http://developer.android.com/sdk/index.html).
- Android N [(API 24) ](http://developer.android.com/tools/revisions/platforms.html).
- Latest Android SDK Tools and build tools.


## Architecture(todo-mvvm-databinding)

This version of the app is called todo-mvvm-databinding, and is based on the [todo-databinding](https://github.com/googlesamples/android-architecture/tree/todo-databinding/) sample, which uses the [Data Binding Library](http://developer.android.com/tools/data-binding/guide.html#data_objects) to display data and bind UI elements to actions.

The sample demonstrates an alternative implementation of the todo-mvp sample using the [Model-View-ViewModel](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) (MVVM) architecture.

## What you need

Before exploring this sample, you should familiarize yourself with the following topics:

* The [project README](https://github.com/googlesamples/android-architecture/tree/master)
* The [todo-mvp](https://github.com/googlesamples/android-architecture/tree/todo-mvp) sample
* The [todo-databinding](https://github.com/googlesamples/android-architecture/tree/todo-databinding) sample
* The [MVVM](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel) architecture

## Designing the app

The ViewModel in the MVVM architecture plays a similar role to the Presenter in the MVP architecture. The two architectures differ in the way that the View communicates with the ViewModel or Presenter respectively:
* When the app modifies the ViewModel in the MVVM architecture, the View is automatically updated by a library or framework. You can’t update the View directly from the ViewModel, as the ViewModel doesn't have access to the necessary reference.
* You can however update the View from the Presenter in an MVP architecture as it has the necessary reference to the View. When a change is necessary, you can explicitly call the View from the Presenter to update it.
In this project, you use layout files to bind observable fields in the ViewModel to specific UI elements such as a [TextView](https://developer.android.com/reference/android/widget/TextView.html), or [ImageView](https://developer.android.com/reference/android/widget/ImageView.html). The Data Binding Library ensures that the View and ViewModel remain in sync bi-directionally as illustrated by the following diagram.
<img src="https://github.com/googlesamples/android-architecture/wiki/images/mvvm-databinding.png" alt="Data binding keeps the View and ViewModel in sync."/>

The todo-mvvm-databinding sample includes a relatively large number of new classes, as well as many changes to existing classes. For more information on reviewing the changes to this version of the application, see [How to compare samples](https://github.com/googlesamples/android-architecture/wiki/How-to-compare-samples).
## Implementing the app
In the MVVM architecture, Views react to changes in the ViewModel without being explicitly called. However, the MVVM architecture presents some challenges when working with some Android components.

For example, to show a [`Snackbar`](https://developer.android.com/reference/android/support/design/widget/Snackbar.html), you must use a static call to pass a view object:

```java
Snackbar.make(View coordinatorLayout, String text, int length).show();
```

When making use of a Presenter in an MVP architecture, you may call the activity or fragment to delegate responsibility for finding the appropriate view object:

```java
mView.showSnackbar(text)
```

A ViewModel however, doesn’t have the necessary reference to the activity or fragment. Instead, you can manually subscribe the snackbar to an observable field by making the following changes:
* Creating an `ObservableField<String>` in the ViewModel.
* Establishing a subscription that shows a snackbar when the `ObservableField` changes.

The following code snippet illustrates setting up a subscription between an observable field and a callback which triggers the call to show the snackbar:

```java
mViewModel.snackbarText.addOnPropertyChangedCallback(
        new Observable.OnPropertyChangedCallback() {
             @Override
             public void onPropertyChanged(Observable observable, int i) {
                 showSnackBar();
             }
         });

```

## Maintaining the app

You may find it easier to make relatively small changes to this version of the app than todo-mvp. To add new features, you may require some experience with the Data Binding Library. As the Data Binding Library takes care of most of the wiring that you would usually unit test, the number of unit tests in this version is lower. However, the overall test coverage should be similar across both versions.

The Data Binding Library takes care of the communication between some components, so you must be familiar with its capabilities before making changes to the existing code.

The table below summarizes the amount of code used to implement this version of the app. You can use it as a basis for comparison with similar tables provided for each of the other samples in this project.

| Language      | Number of files | Blank lines | Comment lines | Lines of code |
| ------------- | --------------- | ----------- | ------------- | ------------- |
| **Java**    |                   52          |  1166      |      1627     |       3655 (3450 in todo-mvp) |
| **XML**                 |             35        |     127       |      352       |      745 |
| **Total**            |        87       |     1293         |   1979       |     4400 |

## Comparing this sample

The following summary reviews how this solution compares to the todo-mvp base sample:

* <b>Use of architectural frameworks, libraries, or tools: </b>Developers must be familiar with the Data Binding Library.
* <b>UI testing: </b>Identical to todo-mvp
* <b>Ease of amending or adding a feature: </b>Similar effort to todo-mvp
* <b>Learning effort required: </b>This version requires more background learning compared to todo-mvp. You must be familiar with the MVVM architecture, which is conceptually similar to MVP but harder to implement.

## How to implement a new screen following MVP

Imagine you have to implement a sign in screen. 

1. Create a new package under `ui` called `signin`
2. Create an new Activity called `ActivitySignIn`. You could also use a Fragment.
3. Define the view interface that your Activity is going to implement. Create a new interface called `SignInMvvmView` that extends `MvvmView`. Add the methods that you think will be necessary, e.g. `showSignInSuccessful()`
4. Create a `SignInViewModel` class that extends `BaseViewModel<SignInMvvmView>`
5. Implement the methods in `SignInViewModel` that your Activity requires to perform the necessary actions, e.g. `signIn(String email)`. Once the sign in action finishes you should call `getMvvmView().showSignInSuccessful()`.
6. Create a `SignInViewModelTest`and write unit tests for `signIn(email)`. Remember to mock the  `SignInMvvmView` and also the `DataManager`.
7. Make your  `ActivitySignIn` implement `SignInMvvmView` and implement the required methods like `showSignInSuccessful()`
8. In your activity, inject a new instance of `SignInViewModel` and call `viewmodel.attachView(this)` from `onCreate` and `viewmodel.detachView()` from `onDestroy()`. Also, set up a click listener in your button that calls `viewmodel.signIn(email)`.

## New project setup

To quickly start a new project from this boilerplate follow the next steps:

* Download this [repository as a zip](https://github.com/ribot/android-boilerplate/archive/master.zip).
* Change the package name.
  * Rename packages in main, androidTest and test using Android Studio.
  * In `app/build.gradle` file, `packageName` and `testInstrumentationRunner`.
  * In `src/main/AndroidManifest.xml` and `src/debug/AndroidManifest.xml`.
* Create a new git repository, [see GitHub tutorial](https://help.github.com/articles/adding-an-existing-project-to-github-using-the-command-line/).
* Replace the example code with your app code following the same architecture.
* In `app/build.gradle` add the signing config to enable release versions.
* Add Fabric API key and secret to fabric.properties and uncomment Fabric plugin set up in `app/build.gradle`
* Update `proguard-rules.pro` to keep models (see TODO in file) and add extra rules to file if needed.
* Update README with information relevant to the new project.
* Update LICENSE to match the requirements of the new project.

## License

```
    Copyright 2017 Zhouzejine@126.com.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
```

