# GMC_Challenge: Music Player



## Option 2: UI 
### Challenge: 
Create an application in whatever style you choose that interacts with two simple classes (that
you create) called Element and Item. Elements contain a list of Items. You can randomly
create these associations or make them have real world significance as you see fit. The app will
look different based on whether the phone is portrait or landscape mode or if the app’s
smallest width is bigger than 943dp (noted below as tablet mode). 

 In portrait mode, the app: 
* Has a hamburger menu that, when clicked, displays a list of Elements. 
* When an Element is chosen from the menu, the main activity displays a list of
Items for that Element  along with a title indicating which Element  was
chosen. 
* When an Item is chosen, it is highlighted in the list and maintains that
highlighting when backgrounding the app or changing orientation. 
* When the hamburger menu is re-opened, the Element currently chosen will
be highlighted. 

 In landscape mode or tablet mode, the app: 
* Shows two lists side-by-side with no hamburger menu. The leftmost list shows
the list of Elements. The rightmost list shows the Items corresponding with the
chosen Element (if any). 

 Important point: When the app is rotated from portrait to landscape mode, the current
chosen Item and Element (if any) must be maintained. That is, if Element 2 is chosen
and Item 4 is highlighted in the list when in portrait mode and the app is turned to
landscape mode, then Element 2 must be highlighted in the left list
and Item 4 highlighted in the right list. 
 
Deliverable: 
* Provide APK and source code for app satisfying the above criteria. 
 
Stretch Goal: 
Use a free music API (such as last.fm&#39;s tag.getTopTags and tag.getTopTracks) to get a list of top
genres or tags and a list of songs that apply to that genre/tag. In place of Elements, display a
list of top tags/genres (strategy for what to show while looking up the tags/genres is up to you.
When an tag/genre is clicked, show a list of songs that apply to that tag/genre. When a song is
clicked, simulate playing that song in whatever way you want.

## Description
This app follows the master-detail pattern, when on landscape or when using tablet whose mind-width is greater than 900dp; it will show on the left side the tags/genres (elements) and on the right side the tracks (items), after you clicked an element and won't show the hamburger menu.

When the app is on portrait mode it will show an empty screen and when you hit the hamburger menu; it will open a drawer which shows the tags/genres (elements) and if you click an element, it will load a fragment with the right tracks (items).

If you clicked an item (track), the item will change color and the play me icon will change into a pause me icon and a foreground service will be launch. If you click on a selected item (track), it will unselect itself (the color and icon will change back to their original values). Also if you click on a different item (track), the current selected item (track) will change to its original values and the selected item (track) will change its color and icon.

The service allow us to launch a notification switch songs, so if you hit next/previous it will go to the next/previous item (track) in the recycler view and in the service, also when you click on a different item (track) the service itself will be change in the service notification.

If you rotate the device or put the app on the background, the selected element (Tag/Genre) and/or item (Track) will remember their last position and restore it when the activity gets recreated.

## Choosen libraries and languages
* Kotlin
    I chose Kotlin instead of Java for because it generates less boiler plate code and its null safety feature makes greate to reduce crashes because of null pointer exception, which is probably the most common exception.
* RecyclerView
    I chose RecyclerViews because provides better perfomance againts listview, thanks to it's ability to recycle items because of its viewholder. Instead of loading 100 items at the same time, we can load 10 items at a time for example and when you scroll, you can use the items not visible to the user and then some items that you were using go out of the visibility range and as you keep scrolling, you can re-use the first items that became invisible and load new data.
* CardView
    I chose CardViews in order to follow some of the material design principles.
* Google architecture components (Livedata + Viewmodels)
    I chose Viewmodels and Livedata because thanks to its lifecycle awareness, whenever there is a configuration change, the data in the viewmodel gets preserved and we can reduce potential memory leaks, as long as we don't pass an activity context to the viewmodel. 
    
    Also through the use of livedata, we can load data into our view in an asynchronously manner using an observer pattern and therefore we won't have to worry about possible race conditions between the network data loading and the view loading, when we do "viewmodel.livedata.observe", whatever gets executed inside the lambda of the call it's guaranteed to get executed after the activity and fragment gets fully loaded. 

    We can also get ride of callbacks that may come from implementing interfaces (if we've choosen MVP) and in doing so we can make our code less tight couple, because unlike MVP where presenter know about the view, in MVVM the view model doesn't know about the View and therefore it gets less tight couple ergo it gets easier to test a view model.
* RxJava
    I choose RxJava instead of async task, coroutines and intent services for the following reasons:
    - Async tasks have the nasty problem that if you pass the activity context to the async task and then you rotate the device, it can cause a memory leak and therefore the async task was never an option.
    - Intent service have the problem that you can't do parallel execution, unlike RxJava where you can chain calls and do parallel execution if you want to, you can even do different operations in RxJava such as map, flatmap, concatmap, zip, merge, skip, etc. 
    - Coroutines seemed like a logical option if we go with Kotlin, they are easy to use and it allows us to do sequential programming, and because we need to use Kotlin; we can do similar operations to RxJava, but its weakness comes that they don't work directly with Java. If I've choosen Java, I would probably have to create a wrapper if I wanted to use coroutines and also RxJava support more operations for now.
    - RxJava for me is the logical option for now, because it allows us do run intensive cpu operations that may block the main thread in an asychronously manner, we can chain multiple calls, do parallel execution and manipulate our streams and it doesn't matter if we use Java or Kotlin. Using an observale pattern, RxJava is a powerful tool.
* Koin
    Dependency injection is a powerful pattern that allows us to instead of manually creating and passing the dependencies ourselves, we let some sort of factory to add the dependencies for us and we really don't have to worry about adding those dependencies ourselves. With this approach you take the dependencies of a class and provide them rather than having the class instance obtain them itself.
    As a consequence of DI, we get the next benefits:
    - Reusability of code: It's easier to swap out implementations of a dependency. Code reuse is improved because of inversion of control, and classes no longer control how their dependencies are created, but instead work with any configuration.
    - Ease of refactoring: The dependencies become a verifiable part of the API surface, so they can be checked at object-creation time or at compile time rather than being hidden as implementation details.
    - Ease of testing: A class doesn't manage its dependencies, so when you're testing it, you can pass in different implementations to test all of your different cases.
    
    The reason I chose Koin instead of Dagger, was because not only is Koin easier to implement, but also I haven't had yet the opportunity to work with Koin and I wanted to play with it.
* Retrofit
    I choose Retrofit instead of Volley for the following reasons:
    Retrofit has full support for POST requests and multi part file uploads, with a sweet API to boot. Volley supports POST requests but you'll have to convert your Java objects to JSONObjects yourself (e.g., with Gson). Also supports multi part requests but you need to add these additional classes or equivalent.
* Gson
    To serialize the json response into a pojo.
* Round image
    For loading images as a circle.
* Eventbus
    I found it more convenient easier to send data between a fragment and a service instead of using interfaces and intents if I were to use a bound service, and also easier than having to implement a local broadcast receiver.
* JUnit
    To write tests.
    Thanks to the decision of using viewmodels and dependency injection, I can decouple my code and make it easier to mock my repositories for my viewmodel tests. 

    If I've not decouple my repository and instead just created the repository in my viewmodel, I wouldn't have been able to mock my repository and therefore I wouldn't have been able to easily isolate my viewmodel in order to just test my viewmodel and when we are testing, we want to make sure that we just test our class and our class only, and the rest of the dependencies we just want to mock their behavior because later we will write tests for those other dependencies.
* Mockito
    To mock my dependencies.
* Espresso
    To create my ui-tests. I created to branches for my ui-tests. The "espresso_tests_idling_example" uses idling resource so espresso waits for the network call to complete before finishing the test, this works when you are not running your tests behind a firewall. In case you are not able to run the tests because of the network, I created the branch "espresso_tests_mock_example", using build variants I created a mock repository and a mock module that injects the mock repository in my viewmodels so instead of making real network calls, we mock the calls not just when doing ui-tests, but also when running the app. This mock repository only runs when you select the mock variant.
* Foreground service
    I used a foreground service and a notification to simulate the music player. This notification allows us to control the tracks of the app.

## Architecture
[![Build Status](https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/mvvm.png)](https://s3.ap-south-1.amazonaws.com/mindorks-server-uploads/mvvm.png)
### View
My view consists on 1 main activity, 2 fragments (1 element fragment and 1 item fragment), 2 recyler views (1 for elements and 1 for items), which can follow the master detail view pattern. Using view models and livedata through the use of the observer pattern I can observer for responses from the viewmodel in a life cycle aware manner. That means that in case of an configuration change I can preserve the data inside my viewmodel and also I can avoid race conditions as previously explained.

### View model
Using Google architectural patterns is the easiest way to implement this pattern, also by using RxJava I can easly subscribe and observe for data from my model in an asynchronously manner in a worker thread and do different operations, in my case in my view model I just observe and wait for the result and do the actual operations and subscriptions in my model.

### Model
Using the repository pattern I can achieve such separation between the actual network, queries and other data access logic from the rest of the application, that not only decouples by leaving the single responsability of accesing my data source to the repo but it makes it easier to test my viewmodel since there is less logic I have to worry about and I can easly write tests for my viewmodels and repositories. 
I also do my thread context switching and RxJava operations in my model, in my case I can extract sublevels that I need from my data classes and just send the data to my view model and my view model just needs to worry in deciding what to tell the view to show; a list of something, an empty list or an error.