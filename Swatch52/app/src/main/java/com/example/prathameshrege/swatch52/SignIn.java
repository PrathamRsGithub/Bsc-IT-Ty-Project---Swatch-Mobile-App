package com.example.prathameshrege.swatch52;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class SignIn extends AppCompatActivity {

    EditText EmailEt, PasswordEt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        EmailEt= (EditText)findViewById(R.id.editText);
        PasswordEt= (EditText)findViewById(R.id.editText4);
    }

    //verfing in database
    public void OnLogin(View view)
    {
        final String email=EmailEt.getText().toString();
        String password=PasswordEt.getText().toString();
        String type ="login";

        class signin_validater extends AsyncTask<String, Void, String > {

        Context context;
        AlertDialog alertDialog;
        signin_validater(Context ctx)
        {
            context = ctx;
        }

        @Override
        public String doInBackground(String... params) {
            String type = params[0];
            String login_url = "http://swatchdemo.dx.am/client/login.php";  //change the ipAddress
            if (type.equals("login"))
            {
                try {

                    String Email = params[1];
                    String Password = params[2];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"
                            +URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream =httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
                    String result="";
                    String line="";
                    while ((line = bufferedReader.readLine())!=null)
                    {
                        result+=line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute()
        {
            alertDialog =new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("Login sucessful"))
            {
                Intent i = new Intent(context, HomePageActivity.class);
                i.putExtra("email",email);
                context.startActivity(i);
                ((Activity)context).finish();
            }
            else {
                alertDialog.setMessage(result);
                alertDialog.show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
        signin_validater sv = new signin_validater(this);
        sv.execute(type, email, password);
    }


    //Go To Signup page
    public void Signup (View view)
    {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);

    }
}
