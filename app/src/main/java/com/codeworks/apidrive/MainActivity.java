package com.codeworks.apidrive;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnGetJson;
    ProgressBar pbGetJson;
    String strJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetJson = (Button)findViewById(R.id.btnGetJson);
        btnGetJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JsonTask().execute("http://gsx2json.com/api?id=12mfLLs0hYUdUHhgqPt-38aK_nw8lVVesg9KK23hx3iI");
            }
        });
    }
    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            btnGetJson.setText("Cargando...");
            btnGetJson.setEnabled(false);
            pbGetJson.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

}

