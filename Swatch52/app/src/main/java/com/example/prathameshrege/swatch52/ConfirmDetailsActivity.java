package com.example.prathameshrege.swatch52;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ConfirmDetailsActivity extends AppCompatActivity {
    Geocoder geocoder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_details);
        Bundle b = getIntent().getExtras();
        String complaint =b.getString("final_comp");
        String priority =b.getString("final_priority");
        String pic = b.getString("final_img");
        String classification =b.getString("final_category");
        String mail_id =b.getString("final_email");
        ImageView image = (ImageView)findViewById(R.id.img);
        byte[] encodedImg = Base64.decode(pic,Base64.DEFAULT);
        Bitmap decodedImg= BitmapFactory.decodeByteArray(encodedImg,0,encodedImg.length);
        image.setImageBitmap(decodedImg);
        double lattitude= b.getDouble("lat");
        double longitude= b.getDouble("lon");
        String Ward = b.getString("ward");


        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(lattitude,longitude,1);
            String address = addresses.get(0).getAddressLine(0);
            TextView prior= (TextView) findViewById(R.id.priority);
            TextView comp= (TextView) findViewById(R.id.complaint);
            TextView lat= (TextView) findViewById(R.id.longitude);
            TextView lon= (TextView) findViewById(R.id.lattitude);
            TextView ward= (TextView) findViewById(R.id.ward);
            TextView add =(TextView)findViewById(R.id.address);
            TextView category =(TextView)findViewById(R.id.category);
            TextView emailid =(TextView)findViewById(R.id.email);
            comp.setText(String.valueOf(complaint));
            prior.setText(String.valueOf(priority));
            lat.setText(String.valueOf(lattitude));
            lon.setText(String.valueOf(longitude));
            add.setText(address);
            ward.setText(Ward);
            category.setText(classification);
            emailid.setText(mail_id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
