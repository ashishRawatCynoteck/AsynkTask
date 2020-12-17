package com.cynoteck.asysnktaskdemo;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.asysnktaskdemo.getPetDetailsResponse.GetPetData;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HeadingListAdapter extends RecyclerView.Adapter<HeadingListAdapter.MyViewHolder> {

    List<ModelClass> modelClasses;
    RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    OnItemClickListner onItemClickListner;
    public HeadingListAdapter(List<ModelClass> modelClasses,OnItemClickListner onItemClickListner) {
        this.modelClasses = modelClasses;
        this.onItemClickListner=onItemClickListner;
    }
//    public ListAdapter(List<ModelClass> modelClasses, List<ModelPhone> phoneList) {
//        this.modelClasses = modelClasses;
//        this.phoneList = phoneList;
//    }

    @NonNull
    @Override
    public HeadingListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HeadingListAdapter.MyViewHolder holder, int position) {
        //firstName
        holder.firstname.setText(modelClasses.get(position).getPetName());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.headingDeatilsRV.getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setInitialPrefetchItemCount(modelClasses.size());
        HeadingDeatilsAdapter appointmentListAdapter = new HeadingDeatilsAdapter(modelClasses,position,onItemClickListner);
        holder.headingDeatilsRV.setLayoutManager(linearLayoutManager);
        holder.headingDeatilsRV.setAdapter(appointmentListAdapter);
        holder.headingDeatilsRV.setRecycledViewPool(recycledViewPool);
        appointmentListAdapter.notifyDataSetChanged();

    }
    
    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstname;
        RecyclerView headingDeatilsRV;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            headingDeatilsRV = itemView.findViewById(R.id.headingDeatilsRV);

        }
    }

}
