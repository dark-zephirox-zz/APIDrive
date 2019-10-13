package com.codeworks.apidrive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btnGetJson;
    ProgressBar pbGetJson;
    String strJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pbGetJson = (ProgressBar)findViewById(R.id.pbLoadJson);
        pbGetJson.setVisibility(View.INVISIBLE);
        btnGetJson = (Button)findViewById(R.id.btnGetJson);
        btnGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetJson().execute("https://spreadsheets.google.com/feeds/list/12mfLLs0hYUdUHhgqPt-38aK_nw8lVVesg9KK23hx3iI/od6/public/values?alt=json");
            }
        });
    }
    private class GetJson extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            btnGetJson.setText("Cargando Json...");
            btnGetJson.setEnabled(false);
            pbGetJson.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null){
                    buffer.append(line+"\n");
                    Log.d("Response: ","> " +line);
                }
                return buffer.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){
                    connection.disconnect();
                }
                try{
                    if (reader != null){
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pbGetJson.setVisibility(View.INVISIBLE);
            btnGetJson.setText("Consultar");
            btnGetJson.setEnabled(true);
            strJson = result;
            if (strJson != null) {
                try {
                    JSONObject jsonObj = new JSONObject(strJson);
                    Integer totJsonValues = jsonObj.getJSONObject("feed").getJSONObject("openSearch$totalResults").getInt("$t");
                    JSONArray jsonRealData = jsonObj.getJSONObject("feed").getJSONArray("entry");
                    for (int i = 0; i < totJsonValues; i++) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}