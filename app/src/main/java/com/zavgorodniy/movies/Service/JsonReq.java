package com.zavgorodniy.movies.Service;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Connect to server, create request, accept data and parse it
 * @author Lego
 * @version 1.0
 */
public class JsonReq extends AsyncTask<Integer, Integer, String> {

    /**variable holds http connection */
    HttpURLConnection urlConnection = null;

    /**variable stores input stream data */
    BufferedReader reader = null;

    /**request result */
    String resultJson = "";

    /**save info to list in class controller */
    Controller controller;

    /**holds reference to MainActivity */
    AsyncResult asyncResult;

    /** Logs*/
    public static String LOG_TAG = "my_log";

    /**
     * Accept reference to MainActivity
     * @param asyncResult
     */
    public JsonReq(JsonReq.AsyncResult asyncResult){
        this.asyncResult = asyncResult;
    }

    /**
     * Connect to server and send request, then getting json result
     * @param params holds id of genre
     * @return
     */
    @Override
    protected String doInBackground(Integer... params) {
        try {
            URL url = new URL("http://api.themoviedb.org/3/genre/"+params[0]+"/movies?api_key=ae55d48a6fab6cd75142c455dda352ab&language=ru");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            resultJson = buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultJson;
    }

    /**
     * Parse request and put date in arraylist 
     * @param strJson
     */
    @Override
    protected void onPostExecute(String strJson) {
        super.onPostExecute(strJson);
        controller = Controller.getInstance();

        JSONObject dataJsonObj;

        try {
            dataJsonObj = new JSONObject(strJson);
            JSONArray movies = dataJsonObj.getJSONArray("results");

            for (int i = 0; i < movies.length(); i++) {
                JSONObject movie = movies.getJSONObject(i);

                String name = movie.getString("original_title");
                Log.d(LOG_TAG, "name: " + name);
                String genre = movie.getString("genre_ids");
                Log.d(LOG_TAG, "genre: " + genre);
                String date = movie.getString("release_date");
                Log.d(LOG_TAG, "date: " + date);
                String rating = movie.getString("vote_average");
                Log.d(LOG_TAG, "rating: " + rating);
                String description = movie.getString("overview");
                Log.d(LOG_TAG, "description: " + description);
                String imageId = movie.getString("poster_path");
                Log.d(LOG_TAG, "imageId: " + imageId);

                Item item = new Item(name,genre,date,rating,description,imageId);
                controller.setItems(item);
            }

            asyncResult.onResult(controller.getItems());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * //TODO progressbar
     * @param result
     */
    @Override
    protected void onProgressUpdate(Integer... result) {
        // [... Сообщите о результате через обновление пользовательского
        // интерфейса, диалоговое окно или уведомление ...]
    }

    /**
     * reference to MainActivity
     */
    public interface AsyncResult{
        void onResult(List<Item> Item);
    }
}