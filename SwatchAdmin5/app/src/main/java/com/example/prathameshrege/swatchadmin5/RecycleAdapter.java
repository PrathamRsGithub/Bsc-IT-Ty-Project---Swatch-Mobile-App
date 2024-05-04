package com.example.prathameshrege.swatchadmin5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

class RecycleAdapter extends RecyclerView.Adapter<RecycleHolder>{
    Context c;
    ArrayList<Data> data;

    public RecycleAdapter(Context c, ArrayList<Data> data)
    {
        this.c=c;
        this.data=data;
    }
    @Override
    public RecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card,parent,false);
        return new RecycleHolder(v);
    }

    @Override
    public void onBindViewHolder(RecycleHolder holder, int position) {
        String pic;
        Data dta = data.get(position);
        holder.ComplaintId.setText(dta.getComplaintId());
        holder.Username.setText(dta.getUsername());
        holder.Complaint.setText(dta.getComplaint());
        holder.Priority.setText(dta.getPriority());
        holder.Address.setText(dta.getAddress());
        holder.Phone.setText(dta.getPhone());
        holder.Email.setText(dta.getEmail());
        pic=dta.getImage();
        byte[] encodedImg = Base64.decode(pic,Base64.DEFAULT);
        Bitmap decodedImg= BitmapFactory.decodeByteArray(encodedImg,0,encodedImg.length);
        holder.img.setImageBitmap(decodedImg);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
