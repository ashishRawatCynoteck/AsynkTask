package com.cynoteck.asysnktaskdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity{
    Gson gson;
    RecyclerView recyclerView;
    List<ModelClass> modelClassArrayListForFirst = new ArrayList<>();
    GetPetParentResponse getPetParentResponse;
    ModelClass modelClass = new ModelClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        MyAsyncTaskTwo myAsyncTaskTwo = new MyAsyncTaskTwo();
        myAsyncTaskTwo.execute();

    }

    public class MyAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://petofyapi.azurewebsites.net/api/user/GetPetParentList");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjY2YThjY2NkLWE1ZmQtNGY1YS05NTAzLWI2OTJiNTBkZmNmMyIsIm5iZiI6MTYwNjQ2NzExMCwiZXhwIjoxNjA5MDU5MTEwLCJpYXQiOjE2MDY0NjcxMTB9.VpvzFeiMY2pWMqOwt4ulAaDOoeWwT5yK6HsRhQnDXRo");
                urlConnection.setRequestMethod("POST");   //POST or GET
                urlConnection.connect();

                // Write Request to output stream to server.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                out.write(jsonRequest.toString());
                out.close();

                // Check the connection status.
                int statusCode = urlConnection.getResponseCode();

                // Connection success. Proceed to fetch the response.
                if (statusCode == 200) {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        dta.append(chunks);
                    }
                    String returndata = dta.toString();

                    Log.e("resultdata",returndata);
                    return returndata;
                } else {
                    Log.e("FAIL","FAIL!!!!!!!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String resultData) {
            super.onPostExecute(resultData);
            try {
                JSONObject obj = new JSONObject(resultData);
                gson = new Gson();
                getPetParentResponse = new GetPetParentResponse();
                getPetParentResponse = gson.fromJson(obj + "", GetPetParentResponse.class);
                for (int position =0;position<getPetParentResponse.getData().size();position++){
                    modelClass = new ModelClass();
                    modelClass.setFirstName(getPetParentResponse.getData().get(position).getFirstName());
                    modelClassArrayListForFirst.add(modelClass);
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyAsyncTaskTwo extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://petofyapi.azurewebsites.net/api/user/GetPetParentList");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjY2YThjY2NkLWE1ZmQtNGY1YS05NTAzLWI2OTJiNTBkZmNmMyIsIm5iZiI6MTYwNjQ2NzExMCwiZXhwIjoxNjA5MDU5MTEwLCJpYXQiOjE2MDY0NjcxMTB9.VpvzFeiMY2pWMqOwt4ulAaDOoeWwT5yK6HsRhQnDXRo");
                urlConnection.setRequestMethod("POST");   //POST or GET
                urlConnection.connect();

                // Write Request to output stream to server.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
//                out.write(jsonRequest.toString());
                out.close();

                // Check the connection status.
                int statusCode = urlConnection.getResponseCode();

                // Connection success. Proceed to fetch the response.
                if (statusCode == 200) {
                    InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                    InputStreamReader read = new InputStreamReader(it);
                    BufferedReader buff = new BufferedReader(read);
                    StringBuilder dta = new StringBuilder();
                    String chunks;
                    while ((chunks = buff.readLine()) != null) {
                        dta.append(chunks);
                    }
                    String returndata = dta.toString();

                    Log.e("resultdata",returndata);
                    return returndata;
                } else {
                    Log.e("FAIL","FAIL!!!!!!!!!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String resultData) {
            super.onPostExecute(resultData);
            try {
                JSONObject obj = new JSONObject(resultData);
                gson = new Gson();
                getPetParentResponse = new GetPetParentResponse();
                getPetParentResponse = gson.fromJson(obj + "", GetPetParentResponse.class);
                for (int postion=0;postion<getPetParentResponse.getData().size();postion++){
                    modelClass = new ModelClass();
                    modelClass.setPhoneNumber(getPetParentResponse.getData().get(postion).getPhoneNumber());
                    modelClass.setEmail(getPetParentResponse.getData().get(postion).getEmail());
                    modelClass.setLastName(getPetParentResponse.getData().get(postion).getLastName());
                    for (int i=0;i<modelClassArrayListForFirst.size();i++){
                        modelClassArrayListForFirst.get(i).setPhoneNumber(getPetParentResponse.getData().get(i).getPhoneNumber());
                        modelClassArrayListForFirst.get(i).setLastName(getPetParentResponse.getData().get(i).getLastName());
                        modelClassArrayListForFirst.get(i).setEmail(getPetParentResponse.getData().get(i).getEmail());

                        Log.d("DATALOG",modelClassArrayListForFirst.get(i).getPhoneNumber());
                    }
                }
                Log.d("DATALOG",modelClassArrayListForFirst.toString());

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                HeadingListAdapter listAdapter = new HeadingListAdapter(modelClassArrayListForFirst);
                recyclerView.setAdapter(listAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}