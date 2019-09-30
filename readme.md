# GIF Searcher App

An app to load GIFs from [Giphy API](https://developers.giphy.com/)

### Used libraries:

 - [Retrofit](https://square.github.io/retrofit/) 
 - [Paging Library](https://developer.android.com/topic/libraries/architecture/paging)
 - [Fresco](https://frescolib.org/)
 
 
 ### How to build app
 
 
 - In order to communicate with GIPHY API the app needs a private _api_key_

 - This key is added to app resources on build stage automatically by Gradle

 - Gradle takes the key from `[project_dir]/apikey.properties` file, where `[project_dir]` is your project root directory. The format of this file is shown below.
  
```
	//[project_dir]/apikey.properties
	GIPHY_API_KEY="My_giphy_api_key"
```	

 - So, to build the app clone this repository with `git clone https://github.com/CharnyshMM/GIFSearcher.git`
	
 - Then create the file `[project_dir]/apikey.properties` and put your api key in it
 - Build the app