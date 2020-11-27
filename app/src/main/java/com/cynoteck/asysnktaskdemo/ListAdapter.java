package com.cynoteck.asysnktaskdemo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    List<ModelClass> modelClasses;
    List<ModelPhone> phoneList;

    public ListAdapter(List<ModelClass> modelClasses) {
        this.modelClasses = modelClasses;
    }
//    public ListAdapter(List<ModelClass> modelClasses, List<ModelPhone> phoneList) {
//        this.modelClasses = modelClasses;
//        this.phoneList = phoneList;
//    }
    @NonNull
    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapter.MyViewHolder holder, int position) {
        holder.firstname.setText(modelClasses.get(position).getFirstName());
        holder.lastname.setText(modelClasses.get(position).getLastName());
        holder.email.setText(modelClasses.get(position).getEmail());
        holder.phone.setText(modelClasses.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstname, lastname, phone, email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);

        }
    }
}
