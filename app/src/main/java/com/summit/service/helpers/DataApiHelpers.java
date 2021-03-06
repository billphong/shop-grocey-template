package com.summit.service.helpers;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.summit.service.asyns.VolleyCallback;
import com.summit.service.asyns.VolleyJsonCallback;
import com.summit.service.commons.RequestQueueSingleton;
import com.summit.service.filters.BaseFilter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DataApiHelpers {

    public static String GetData(String urlApi){
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL url = new URL(urlApi);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
            return forecastJsonStr;
        } catch (IOException e) {
            Log.e("PlaceholderFragment", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }

    public static <T> void Post(Context context, String urlApi, final T t, final VolleyCallback callback){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlApi,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        if(callback != null){
                            callback.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if(error != null) {
                            //Log.d("Error.Response", error.getMessage());
                        }
                        if(callback != null){
                            callback.onError(error);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                ObjectMapper objectMapper = new ObjectMapper();
                TypeFactory factory = TypeFactory.defaultInstance();
                MapType type = factory.constructMapType(Map.class, String.class, String.class);
                Map<String, String>  params = objectMapper.convertValue(t, type);

                return params;
            }
        };

        RequestQueueSingleton.getInstance(context).addToRequestQueue(postRequest);
    }

    public static <T> void PostJson(Context context, String urlApi, final JSONObject t, final VolleyJsonCallback callback){

        SendRequestJson(false, context, urlApi, t, callback);
    }

    public static <T> void GetJson(Context context, String urlApi, final JSONObject t, final VolleyJsonCallback callback){

        SendRequestJson(true, context, urlApi, t, callback);
    }

    private static <T> void SendRequestJson(boolean isGet, Context context, String urlApi, final JSONObject t, final VolleyJsonCallback callback){

        int method = isGet ? Request.Method.GET : Request.Method.POST;

        JsonObjectRequest postRequest = new JsonObjectRequest (method, urlApi, t,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response
                        Log.d("Response", response.toString());
                        if(callback != null){
                            callback.onSuccess(response);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if(error != null) {
                            //Log.d("Error.Response", error.getMessage());
                        }
                        if(callback != null){
                            callback.onError(error);
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        RequestQueueSingleton.getInstance(context).addToRequestQueue(postRequest);
    }
}
