package com.example.prathameshrege.swatchadmin5;

import android.provider.ContactsContract;
import android.renderscript.RenderScript;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

class RecycleHolder extends RecyclerView.ViewHolder{

    TextView ComplaintId,Username, Complaint, Priority,Address,Phone, Email;
    ImageView img;

    public RecycleHolder(View itemView) {
        super(itemView);


        img =  itemView.findViewById(R.id.imageView4);
        ComplaintId = itemView.findViewById(R.id.textView19);
        Username = itemView.findViewById(R.id.textView20);
        Complaint = itemView.findViewById(R.id.textView21);
        Priority = itemView.findViewById(R.id.textView22);
        Address = itemView.findViewById(R.id.textView23);
        Phone = itemView.findViewById(R.id.textView24);
        Email = itemView.findViewById(R.id.textView25);
    }
}