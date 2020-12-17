package com.cynoteck.asysnktaskdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.cynoteck.asysnktaskdemo.getPetDetailsResponse.GetPetResponse;
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
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity{

    AppCompatSpinner spinner_SP;
    Gson gson;
    RecyclerView recyclerView;
    List<ModelClass> modelClassArrayListForFirst = new ArrayList<>();
    GetPetListResponse getPetListResponse;
    ModelClass modelClass = new ModelClass();
    ArrayList<String> personList = new ArrayList<>();
    HashMap<String,String> personHashMap=new HashMap<>();
    String strPersonName="",getStrPersonEmail="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        spinner_SP = findViewById(R.id.spinner_SP);

        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
        MyAsyncGetValues myAsyncTaskTwo = new MyAsyncGetValues();
        myAsyncTaskTwo.execute();

    }


    private void setSpinnerData() {
        {
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,personList);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spinner_SP.setAdapter(aa);
            spinner_SP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    strPersonName=item;
                    Log.e("spnerType",""+strPersonName+"====>"+item);
                    getStrPersonEmail=personHashMap.get(strPersonName);
                    Toast.makeText(MainActivity.this, getStrPersonEmail, Toast.LENGTH_SHORT).show();
                    Log.e("spnerType",""+getStrPersonEmail);

                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public class MyAsyncTask extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://petofyapi.azurewebsites.net/api/report/GetPetList");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjVkMGJkNmQ0LTIzNjQtNGU1Ny04Yzk1LTA3MzZlYTgwMDIyMSIsIm5iZiI6MTYwNzQxNzUyNSwiZXhwIjoxNjEwMDk1OTI1LCJpYXQiOjE2MDc0MTc1MjV9.soWG_8bOerGtkOqt6yz312HpMgsJJEI5dHj7ELq40zI");
                urlConnection.setRequestMethod("POST");   //POST or GET
                urlConnection.connect();

                // Write Request to output stream to server.
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("PageNumber", "1");
                jsonRequest.put("pageSize", "10");
                jsonRequest.put("search_Data", "0");
                JSONObject object = new JSONObject();
                object.put("data",jsonRequest);
                Log.e("eeee",jsonRequest.toString());
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(object.toString());
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
                getPetListResponse = new GetPetListResponse();
                getPetListResponse = gson.fromJson(obj + "", GetPetListResponse.class);
//                personList.add("Select Name");
//                personHashMap.put("Select Name","Email is Empty");

                for (int position =0;position<getPetListResponse.getData().getPetList().size();position++){
                    modelClass = new ModelClass();
                    modelClass.setPetName(getPetListResponse.getData().getPetList().get(position).getPetName());
                    modelClassArrayListForFirst.add(modelClass);
//                    personList.add(getPetParentResponse.getData().get(position).getFirstName());
//                    personHashMap.put(getPetParentResponse.getData().get(position).getFirstName(),getPetParentResponse.getData().get(position).getEmail());

                }
//                Log.e("aaa",personHashMap.toString());


//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
//                recyclerView.setLayoutManager(linearLayoutManager);
//                HeadingListAdapter listAdapter = new HeadingListAdapter(getPetListResponse);
//                recyclerView.setAdapter(listAdapter);
//                listAdapter.notifyDataSetChanged();

//                setSpinnerData();




            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyAsyncGetValues extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://petofyapi.azurewebsites.net/api/report/GetPetList");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjVkMGJkNmQ0LTIzNjQtNGU1Ny04Yzk1LTA3MzZlYTgwMDIyMSIsIm5iZiI6MTYwNzQxNzUyNSwiZXhwIjoxNjEwMDk1OTI1LCJpYXQiOjE2MDc0MTc1MjV9.soWG_8bOerGtkOqt6yz312HpMgsJJEI5dHj7ELq40zI");
                urlConnection.setRequestMethod("POST");   //POST or GET
                urlConnection.connect();

                // Write Request to output stream to server.
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("PageNumber", "1");
                jsonRequest.put("pageSize", "10");
                jsonRequest.put("search_Data", "0");
                JSONObject object = new JSONObject();
                object.put("data",jsonRequest);
                Log.e("eeee",jsonRequest.toString());
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(object.toString());
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
                getPetListResponse = new GetPetListResponse();
                getPetListResponse = gson.fromJson(obj + "", GetPetListResponse.class);
                for (int postion=0;postion<getPetListResponse.getData().getPetList().size();postion++){
                    modelClass = new ModelClass();
                    modelClass.setId(getPetListResponse.getData().getPetList().get(postion).getId());
                    modelClass.setPetId(getPetListResponse.getData().getPetList().get(postion).getPetUniqueId());
                    modelClass.setDOB(getPetListResponse.getData().getPetList().get(postion).getDateOfBirth());
                    for (int i=0;i<modelClassArrayListForFirst.size();i++){
                        modelClassArrayListForFirst.get(i).setId(getPetListResponse.getData().getPetList().get(i).getId());
                        modelClassArrayListForFirst.get(i).setPetId(getPetListResponse.getData().getPetList().get(i).getPetUniqueId());
                        modelClassArrayListForFirst.get(i).setDOB(getPetListResponse.getData().getPetList().get(i).getDateOfBirth());

                        Log.d("DATALOG",modelClassArrayListForFirst.get(i).getId());
                    }
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                HeadingListAdapter listAdapter = new HeadingListAdapter(modelClassArrayListForFirst, new OnItemClickListner() {
                    @Override
                    public void onItemClick(int position, List<ModelClass> modelClasses) {
                        Toast.makeText(MainActivity.this, ""+modelClasses.get(position).getId()+"", Toast.LENGTH_SHORT).show();

                        MyAsyncTaskTwo myAsyncTaskTwo = new MyAsyncTaskTwo();
                        myAsyncTaskTwo.execute(modelClasses.get(position).getId());
                    }
                });
                recyclerView.setAdapter(listAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class MyAsyncTaskTwo extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... strings) {
            String petId;
            petId = strings[0];
            Log.e("id",petId);
            try {
                // Creating & connection Connection with url and required Header.
                URL url = new URL("https://petofyapi.azurewebsites.net/api/pet/GetPetDetail");
                HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bmlxdWVfbmFtZSI6IjVkMGJkNmQ0LTIzNjQtNGU1Ny04Yzk1LTA3MzZlYTgwMDIyMSIsIm5iZiI6MTYwNzQxNzUyNSwiZXhwIjoxNjEwMDk1OTI1LCJpYXQiOjE2MDc0MTc1MjV9.soWG_8bOerGtkOqt6yz312HpMgsJJEI5dHj7ELq40zI");
                urlConnection.setRequestMethod("POST");   //POST or GET
                urlConnection.connect();

                // Write Request to output stream to server.
                JSONObject jsonRequest = new JSONObject();
                jsonRequest.put("id", petId);
                JSONObject object = new JSONObject();
                object.put("data",jsonRequest);
                Log.e("eeee",jsonRequest.toString());
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(object.toString());
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
                GetPetResponse getPetResponse = new GetPetResponse();
                getPetResponse = gson.fromJson(obj + "", GetPetResponse.class);
                Toast.makeText(MainActivity.this, "PetName=>"+getPetResponse.getData().getPetName()+"PetID=>"+getPetResponse.getData().getPetUniqueId(), Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}