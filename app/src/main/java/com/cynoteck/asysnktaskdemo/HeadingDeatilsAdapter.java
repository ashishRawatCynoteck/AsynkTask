package com.cynoteck.asysnktaskdemo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cynoteck.asysnktaskdemo.getPetDetailsResponse.GetPetResponse;

import java.util.List;

public class HeadingDeatilsAdapter extends  RecyclerView.Adapter<HeadingDeatilsAdapter.MyViewHolder>  {
    List<ModelClass> modelClasses;
    int headingPostion;
    OnItemClickListner onItemClickListner;
    public HeadingDeatilsAdapter(List<ModelClass> modelClasses, int headingPostion,OnItemClickListner onItemClickListner) {
        this.modelClasses = modelClasses;
        this.headingPostion = headingPostion;
        this.onItemClickListner=onItemClickListner;
    }

    @NonNull
    @Override
    public HeadingDeatilsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.headind_details_list, parent, false);
        HeadingDeatilsAdapter.MyViewHolder vh = new HeadingDeatilsAdapter.MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HeadingDeatilsAdapter.MyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        if (position==headingPostion) {
            holder.lastname.setText(modelClasses.get(headingPostion).getId());
            holder.email.setText(modelClasses.get(headingPostion).getPetId());
            holder.phone.setText(modelClasses.get(headingPostion).getDOB());
        }else {
            holder.phone.setVisibility(View.GONE);
            holder.email.setVisibility(View.GONE);
            holder.lastname.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView  lastname, phone, email;
        LinearLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout =itemView.findViewById(R.id.layout);
            lastname = itemView.findViewById(R.id.lastname);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);

            lastname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListner!=null){
                        onItemClickListner.onItemClick(getAdapterPosition(),modelClasses);
                    }
                }
            });



        }
    }
}
