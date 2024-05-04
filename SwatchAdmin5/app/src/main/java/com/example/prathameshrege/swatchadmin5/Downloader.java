package com.example.prathameshrege.swatchadmin5;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Created by prathameshrege on 09/03/18.
 */

class Downloader extends AsyncTask<Void ,Void ,String > {
    Context c;
    String urlAddress;
    RecyclerView rv;
    ProgressDialog pd;

    public Downloader(Context c, String urlAddress, RecyclerView rv) {
        this.c = c;
        this.urlAddress = urlAddress;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(c);
        pd.setMessage("Downloading.......");
    }

    @Override
    protected String doInBackground(Void... params) {
        pd.setMessage("Started......");
        return this.DownloadData();
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        pd.dismiss();
        if (jsonData == null) {
            Toast.makeText(c, "No data Retrieved", Toast.LENGTH_SHORT).show();
        } else {
            DataParser dp = new DataParser(c, jsonData, rv);
            dp.execute();
        }
    }

    private String DownloadData() {
        HttpURLConnection con = Connector.connect(urlAddress);
        if (con == null) {
            return null;
        }
        try {
            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line;
            StringBuffer jsonData = new StringBuffer();

            while ((line = br.readLine()) != null) {
                jsonData.append(line + "\n");
            }
            br.close();
            is.close();
            return jsonData.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

