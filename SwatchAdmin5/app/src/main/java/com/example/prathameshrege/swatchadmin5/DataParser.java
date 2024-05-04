package com.example.prathameshrege.swatchadmin5;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class DataParser extends AsyncTask<Void,Void,Integer> {
    Context c;
    String jsonData;
    RecyclerView rv;
    ProgressDialog pd;
    ArrayList<Data> data = new ArrayList<>();

    public DataParser(Context c, String jsonData, RecyclerView rv)
    {
        this.c= c;
        this.jsonData = jsonData;
        this.rv =rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setMessage("Parsing.......");
        pd.show();

    }

    @Override
    protected Integer doInBackground(Void... voids) {
        pd.setMessage("Parsing Started");
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        pd.dismiss();

        if(result == 0)
        {
            Toast.makeText(c,"Unable to parse",Toast.LENGTH_SHORT).show();
        }
        else
        {
            RecycleAdapter rh = new RecycleAdapter(c,data);
            rv.setAdapter(rh);
        }

    }
    private int parse ()
    {
        try
        {
            JSONArray ja = new JSONArray(jsonData);
            JSONObject jo = null;
            data.clear();
            Data d ;
            for (int i= 0 ; i <ja.length() ; i++)
            {
                jo =ja.getJSONObject(i);
                String id = jo.getString("id");
                String img = jo.getString("Image");
                String user = jo.getString("username");
                String comp = jo.getString("complaint");
                String priority = jo.getString("priority");
                String address = jo.getString("address");
                String phone = jo.getString("phoneno");
                String mail = jo.getString("email");

                d = new Data();

                d.setComplaintId(id);
                d.setImage(img);
                d.setUsername(user);
                d.setComplaint(comp);
                d.setPriority(priority);
                d.setAddress(address);
                d.setPhone(phone);
                d.setEmail(mail);

                data.add(d);


            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
