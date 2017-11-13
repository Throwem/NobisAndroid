package com.throwem.nobis;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Splash extends AppCompatActivity {
    Button postButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        postButton = (Button) findViewById(R.id.mainButton);
        final PostInfo postInfo = new PostInfo();
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postInfo.execute("Android");
            }
        });

    }
    private class PostInfo extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... data) {
            try{
                URL url = new URL("http://192.168.0.14/nobis/api.php"); //in the real code, there is an ip and a port
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept","application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.connect();

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("data", data[0]);
                /*jsonParam.put("message", params[0].getMessage());
                jsonParam.put("latitude", "0");
                jsonParam.put("longitude", "0");
                jsonParam.put("id", "1");*/


                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("SENT MESSAGE",jsonParam.toString());

                Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                Log.i("MSG" , conn.getResponseMessage());

                conn.disconnect();
            }catch (Exception e){
                Log.d("Error",e.getMessage());
            }

            return null;
        }
    }
}
