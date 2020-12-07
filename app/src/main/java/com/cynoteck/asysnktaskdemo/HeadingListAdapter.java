package com.cynoteck.asysnktaskdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HeadingListAdapter extends RecyclerView.Adapter<HeadingListAdapter.MyViewHolder> {
    List<ModelClass> modelClasses;
    RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();

    public HeadingListAdapter(List<ModelClass> modelClasses) {
        this.modelClasses = modelClasses;
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
        holder.firstname.setText(modelClasses.get(position).getFirstName());

        //second Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.headingDeatilsRV.getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setInitialPrefetchItemCount(modelClasses.size());
        HeadingDeatilsAdapter appointmentListAdapter = new HeadingDeatilsAdapter(modelClasses,position);
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
