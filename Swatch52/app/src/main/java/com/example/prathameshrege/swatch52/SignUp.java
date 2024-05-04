package com.example.prathameshrege.swatch52;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
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

public class SignUp extends AppCompatActivity {

    EditText name, email, password, cpassword, phoneno;
    String str_name ,str_email ,str_password ,str_cpassword ,str_phoneno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        name = (EditText)findViewById(R.id.Name);
        email= (EditText)findViewById(R.id.Email);
        password= (EditText)findViewById(R.id.Password);
        cpassword=(EditText)findViewById(R.id.Cpassword);
        phoneno= (EditText)findViewById(R.id.Phone);

    }

    public void  Register(View view)
    {
        Initialize();
        if (!validate()) {
            Toast.makeText(this, "Signup has Been Failed", Toast.LENGTH_SHORT).show();
        } else {
            OnSignupSuccess();
        }
    }


    public void OnSignupSuccess() {


        //Insert The Values Inside The Database
        String type = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type ,str_name ,str_email ,str_password,str_phoneno);

    }

    public boolean validate() {

        boolean valid = true;
        if (str_name.isEmpty()|| str_name.length() > 25) {
            name.setError("Please Enter a valid Name");
            valid=false;
        }
        if(str_email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(str_email).matches())
        {
            email.setError("Please Enter a valid Email ID");
            valid=false;
        }
        if(str_password.isEmpty())
        {
            password.setError("Please Enter a valid Password");
            valid=false;
        }
        if(str_cpassword.equals(str_password)){}
        else
        {
            cpassword.setError("Please Enter Same Password As Above");
            valid=false;
        }
        if(str_phoneno.isEmpty()|| str_phoneno.length()<10)
        {
            phoneno.setError("Please Enter a valid Phone no");
            valid=false;
        }
        return valid;

    }
    public void Initialize()
    {
        str_name = name.getText().toString();
        str_email = email.getText().toString();
        str_password = password.getText().toString();
        str_cpassword = cpassword.getText().toString();
        str_phoneno = phoneno.getText().toString();
    }

    //Go To Signin page
    public void Signin (View view)
    {
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }


    //Backgroundworker class
    public class BackgroundWorker extends AsyncTask<String, Void, String> {

        Context context;
        AlertDialog alertDialog;
        BackgroundWorker(Context ctx)
        {
            context = ctx;
        }


        @Override
        protected String doInBackground(String... params) {

            String type=params[0];
            String login_url ="http://swatchdemo.dx.am/client/register.php";  //change the ipAddress
            if(type.equals("register"))
            {
                try {

                    String Name = params[1];
                    String Email = params[2];
                    String Password = params[3];
                    String Phone = params[4];

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection =(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                    String post_data = URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(Name,"UTF-8")+"&"
                            +URLEncoder.encode("Email","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")+"&"
                            +URLEncoder.encode("Password","UTF-8")+"="+URLEncoder.encode(Password,"UTF-8")+"&"
                            +URLEncoder.encode("Phone","UTF-8")+"="+URLEncoder.encode(Phone,"UTF-8");
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
        protected void onPreExecute() {
            alertDialog =new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
        }


        @Override
        protected void onPostExecute(String result) {

            if(result.equals("User Registration sucessful"))
            {
                abc();
                Toast toast = Toast.makeText(context,"User Registration sucessful", Toast.LENGTH_LONG);
                toast.show();
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

    public void abc()
    {
        Intent i = new Intent(this, SignIn.class);
        startActivity(i);
    }

}

