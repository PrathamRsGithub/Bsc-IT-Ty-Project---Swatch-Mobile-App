package com.example.prathameshrege.swatch52;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Picture;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        String email;
    String complaint;
    String priority;
    String img;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b;
        b=getIntent().getExtras();
        String op = b.getString("email");
        NavigationView nav = (NavigationView)findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        View header = nav.getHeaderView(0);
        TextView textView= (TextView)header.findViewById(R.id.textview);
        textView.setText(op);

        email = textView.getText().toString();
        //Initial fragment
        Home homeFragment= new Home();
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main,homeFragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Home");



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    //Navigaton bar
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Fragment fragment=null;
        String title= null;
        int id = item.getItemId();
        if (id == R.id.nav_Home) {
            fragment = new Home();
            title="Home";
        } else if (id == R.id.nav_Complaints) {
            fragment = new Complaints();
            title="Complaints";

        } else if (id == R.id.nav_Help) {
            fragment = new Help();
            title="Help";

        } else if (id == R.id.nav_About) {
            fragment = new About();
            title="About";

        } else if (id == R.id.nav_Logout) {

            Intent i =new Intent(this,SignIn.class);
            startActivity(i);

        }
        if(fragment !=null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.main, fragment);
            ft.commit();
            getSupportActionBar().setTitle(title);


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //Camera Button
    public static final int j=1;


    public void LaunchCamera(View view) {
        Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i,j);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==j && resultCode==RESULT_OK)
        {
            Bitmap photo =(Bitmap)data.getExtras().get("data");
            Intent f=new Intent(this,Camera.class);
            f.putExtra("image",photo);
            f.putExtra("email",email);
            startActivity(f);

        }
    }

    public void profile(View view) {
        Bundle b = getIntent().getExtras();
        complaint= b.getString("comp");
        priority= b.getString("priority");
        img =b.getString("Image");
        category =b.getString("category");
        Intent i= new Intent(HomePageActivity.this,Profile.class);
        i.putExtra("email",email);
        startActivity(i);
    }
}

