package com.example.ahmed_ayman.weatherforecast;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    ArrayList<WeatherForecastItem> items;
    private String TAG = MainActivity.class.getSimpleName();
    JSONObject jsonResponse;
    Typeface weatherFont;
    private ProgressDialog pDialog;
    private ListView lv;
    TextView cityField;
   // TextView updatedField;
    TextView detailsField;
    TextView currentTemperatureField;
    TextView weatherIcon;
    TextView date;
    //URL
    Handler handler;

    private static String url = "    http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&units=metric&&cnt=7&APPID=adb221b9d8fbb0804f3c60664ced3631";
   // ArrayList<WeatherItem> weatherForecastItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items = new ArrayList<>();

        String city= new CityPreference(this).getCity();
        updateWeatherData(city);
//        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weather.ttf");
        cityField = (TextView)findViewById(R.id.city_field);
        detailsField = (TextView)findViewById(R.id.details_field);
        currentTemperatureField = (TextView)findViewById(R.id.current_temperature_field);
       // weatherIcon = (TextView)findViewById(R.id.weather_icon);
        date=(TextView) findViewById(R.id.date);
    }

    public void changeCity(String city){
            updateWeatherData(city);
            new CityPreference(this).setCity(city);
    }
    private void updateWeatherData(final String city){
        new FetchTheWeatherData().execute(city);
    }





    private class FetchTheWeatherData extends AsyncTask<String, Object, ArrayList<WeatherForecastItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }
        @Override
        protected ArrayList<WeatherForecastItem> doInBackground(String... arg0) {
            ArrayList<WeatherForecastItem> result = new ArrayList<>();
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(String.format(url,arg0));  //pass the city name
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                   result =Parser.parseJson(new JSONObject(jsonStr));
                } catch (final JSONException e) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (pDialog.isShowing())
                            pDialog.dismiss();
                        showInputDialog();
                        Toast.makeText(getApplicationContext(), "kindly Enter a valid city ..eg,. (Cairo,eg)",    Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

        return  result;
        }

        @Override
        protected void onPostExecute(ArrayList<WeatherForecastItem> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            if(result.size()!=0)
            renderWeather(result.get(0));
            /**
             * Updating parsed JSON data into ListView
             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, ,
//                    R.layout.list_item, new String[]{"name", "email",
//                    "mobile"}, new int[]{R.id.name,
//                    R.id.email, R.id.mobile});
//
//            lv.setAdapter(adapter);
//
        }
    }//Async

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
        private void setWeatherIcon(int actualId){
            int id = actualId / 100;
            String icon = "";
            if(actualId == 800) {
                icon = getString(R.string.weather_sunny);
            }
            else {
                switch(id) {
                    case 2 : icon = getString(R.string.weather_thunder);
                        break;
                    case 3 : icon = getString(R.string.weather_drizzle);
                        break;
                    case 7 : icon = getString(R.string.weather_foggy);
                        break;
                    case 8 : icon = getString(R.string.weather_cloudy);
                        break;
                    case 6 : icon = getString(R.string.weather_snowy);
                        break;
                    case 5 : icon = getString(R.string.weather_rainy);
                        break;
                }
            }
            weatherIcon.setText(icon);
        }

        private void renderWeather(WeatherForecastItem weatherForecastItem){
            try {

                cityField.setText(weatherForecastItem.getCity()); // city + name Cairo Eg
                date.setText(weatherForecastItem.getDate());

                String IMG_URL = "http://openweathermap.org/img/w/"+weatherForecastItem.getIconCode() +".png" ;
             //   HttpURLConnection con = (HttpURLConnection) ( new URL(IMG_URL + weatherForecastItem.getIconCode() +".png")).openConnection();
               //     Log.v("conn",con.toString());
                detailsField.setText(
                        weatherForecastItem.getIconDesc().toUpperCase(Locale.US) +
                                "\n" + "Humidity: " + weatherForecastItem.getHumadity() + "%" +
                                "\n" + "Pressure: " + weatherForecastItem.getPresuure() + " hPa");

                currentTemperatureField.setText(
                        "Day : "+String.format("%.2f", weatherForecastItem.getDayTemp())+ " ℃" +
                        "\nNight : "+String.format("%.2f",weatherForecastItem.getNighTemp())+" ℃" );

            //    setWeatherIcon(weatherForecastItem.getIconId());

                new DownloadImageTask((ImageView) findViewById(R.id.weather_icon))
                        .execute(IMG_URL);

            }catch(Exception e){
                Log.e("SimpleWeather", "One or more fields not found in the JSON data");
            }
        }



    /*Menu Item*/

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_city){
            showInputDialog();
        }
        return false;
    }

    private void showInputDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change city");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newCity=input.getText().toString();
                changeCity(newCity);
            }
        });
        builder.show();
    }

}

