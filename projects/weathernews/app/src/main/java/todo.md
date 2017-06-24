# Programming Assignment: Mobile

Develop a weather forecast application.

Your application must implement the following user stories:

 - As a user, I can specify a location for viewing weather forecasts
 - As a user, I can view the weekly weather forecast of my specified location

You may acquire weather information from [OpenWeatherMap's Weather API](http://openweathermap.org/api), or the API of your choice. You are free to use any appropriate libraries or web APIs to implement your application. Your application should work on either iOS or Android.

If you are unfamiliar with mobile application development, here are a few hints for building your application.

- Your application can have the following 3 views:
	- a weekly forecast view
	- a current weather forecast view showing today's and tomorrow's weather
	- a view for selecting the user's location or region
    - The user should be able to easily switch between weekly and current forecast views
    - Send a request to the appropriate API when switching to a view, then update the appropriate location when the response is received
    - Access the settings view via
	- the action bar on Android
