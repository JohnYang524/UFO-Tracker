# UFO-Tracker
Purpose of Document:

This document is intended as a development note for the development of the UFOTracker Android Application.
This document talks about the what I have done, choice of design for each component of the project, and level of efforts spent for development.

What is UFOTracker App?

UFO Tracker is an app that keeps track of U.F.O. (Unidentified Flying Object) sightings.

Features Implemented:
-	The application displays a list of UFO sightings. 
-	Each sighting includes: 
    ○ the date of the sighting 
    ○ the type of the sighting
	○ the speed at which the UFO was travelling 
-	Each sighting belongs in one of two categories according to its type. 
-	Display each sighting under corresponding category tab.
-	The list of sightings should be stored in memory only
-	A “+” button will allow adding UFO sightings 
-	Ability to remove an entry 
    o	When an entry in the displayed list of items is selected, display a Remove button
    o	Tap on a list item repeatedly will toggle Remove button visibility
    o	When the remove button is clicked, the selected entry will be removed 
-	Layout implemented to match design spec.

 
What I have done:

-	The app is built using MVVM architecture.

-	Main UI components
    o	MainActivity – Main Activity that holds an AppBarLayout and a TabLayout which is set up using a ViewPager.
    o	SightingListFragment - A fragment displaying a list of Sighting items with a RecyclerView.
    o	TabPagerAdapter - The ViewPager uses TabPagerAdapter [FragmentPagerAdapter] to create a fragment [SightingListFragment] corresponding to each tab in the TabLayout.
    o	SightingListAdapter - RecyclerView adapter.

-	Data Model
    o	Sighting – A Sighting object is constructed with 
        	String date
        	SightingType type
        	String speed
    o	SightingType – Enum -> Blob, LAMPSHADE, MYSTERIOUS_LIGHTS
        	SightingCategory category -> Category the type belongs to
        	int imageId -> Resource id of corresponding image resource
    o	SightingCategory – Enum -> STRANGE_FLYERS, MYSTERIOUS_LIGHTS
        	int tabIndex -> Corresponding tab index in TabLayout
-	ViewModel
    o	SightlingListViewModel - ViewModel for keeping business logic and handling data updates in SightingListFragment. 
        	List contains all Sighting objects:
            MutableLiveData<List<Sighting>> mSightingsList;
        	The Fragment is observing a filtered data list: LiveData<List<Sighting>> filteredSightings
        	Whenever there is a data change in mSightingsList, we will also refresh the filtered list. And once Fragment has detected the change, it will initialize RecyclerView/Adapter or          notify Adapter of data change.
        	filteredSightings is mapped with tab index for each tab.
-	Adding a Sighting
    o	Once user clicks the Add button in MainActivity layout, MainActivity will create test data and notify all attached listeners (ListRefreshListener) of this data change. 
    o	SightingListFragment will receive this callback and call ViewModel to refresh data list.
    o	Once data list is updated, the LiveData observer will be notified to update the recyclerView data accordingly
-	Removing a Sighting
    o	SightingListAdapter expose a listener interface (ListItemClickListener) and requires a listener to be passed in when constructing the SightingListAdapter object.
    o	The listener will be called back when ViewHolder item(s) is clicked on.
    o	Clicking on a list item will toggle visibility of a Remove button
    o	Clicking on Remove button will call back to onItemRemoved() function implemented by listener. 
    o	Fragment (listener) will then call remove method in ViewModel to remove corresponding data from the SightingList and trigger UI updates.

Time Spent:

-	Effort Spent:
    o	Github setup: 5min
    o	Design: 15min
    o	Create resources files and Implement UI components: 45 mins
    o	Update UI to match design spec: 20min
    o	Data update flow: 25min
    o	Testing, debugging, and code cleaning up: 20min
    o	Documentation: 15min

