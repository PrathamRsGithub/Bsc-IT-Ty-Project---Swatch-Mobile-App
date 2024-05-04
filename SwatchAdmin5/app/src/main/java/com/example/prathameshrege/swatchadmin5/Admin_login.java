package com.example.prathameshrege.swatchadmin5;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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

public class Admin_login extends AppCompatActivity {

    EditText EmailEt, PasswordEt;
    UserSessionManager session;
    SavedSharedPrefrences session2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        EmailEt= (EditText)findViewById(R.id.editText);
        PasswordEt= (EditText)findViewById(R.id.editText4);

        Toast.makeText(getApplicationContext(),
                "User Login Status: " + session.isUserLoggedIn(),
                Toast.LENGTH_LONG).show();

    }

    //verfing in database
    public void OnLogin(View view)
    {
        final String email=EmailEt.getText().toString();
        final String password=PasswordEt.getText().toString();
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
                String login_url = "http://swatchdemo.dx.am/client/Admin_login.php";  //change the ipAddress
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
                    session.createUserLoginSession("email", email);
                    session2.setUserName(context,email);

                    Intent i = new Intent(context, HomePageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        if(email.trim().length() > 0 && password.trim().length() > 0) {
            signin_validater sv = new signin_validater(this);
            sv.execute(type, email, password);
        }
        else{

            // user didn't entered username or password
            Toast.makeText(getApplicationContext(),
                    "Please enter username and password",
                    Toast.LENGTH_LONG).show();

        }
    }
}
