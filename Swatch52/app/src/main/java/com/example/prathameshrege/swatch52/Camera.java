package com.example.prathameshrege.swatch52;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Camera extends AppCompatActivity {

    SeekBar seekBar;
    TextView textView;
    String priority;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        Bitmap bitmap =(Bitmap)b.get("image");
        ImageView images=(ImageView) findViewById(R.id.Image);
        images.setImageBitmap(bitmap);
        seekbar();
    }

    public void seekbar()
    {
        seekBar = (SeekBar) findViewById(R.id.seek);
        textView =(TextView)findViewById(R.id.textView7);

        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int change;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        change= Integer.parseInt(String.valueOf(i));

                        if(change==0) {
                            textView.setText("Basic");
                        }
                        else if(change==1)
                        {
                            textView.setText("Filled");
                        }
                        else if(change==2)
                        {
                            textView.setText("Critical");
                        }
                        else if(change==3)
                        {
                            textView.setText("Very Critical");
                        }
                        else if(change==4)
                        {
                            textView.setText("Overflowing");
                        }


                    }
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {



                    }
                }

        );
    }

    public void Locate (View view)
    {
        EditText editor;
        String comp;
        Intent in= getIntent();
        Bundle b = in.getExtras();
        Bitmap bitmap =(Bitmap)b.get("image");
        Intent i = new Intent(this, MapsActivity.class);
        i.putExtra("Image",bitmap);
        startActivity(i);
    }
}

