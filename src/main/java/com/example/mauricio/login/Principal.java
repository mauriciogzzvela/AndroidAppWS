package com.example.mauricio.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        setTitle("Actividad Principal");


        try {

            Intent intent = getIntent();
            String message = intent.getStringExtra("data");

            JSONArray arr = new JSONArray(message);
            JSONObject jObj = arr.getJSONObject(0);
            String name = jObj.getString("nombre");
            String email = jObj.getString("correo");
            String image = jObj.getString("imagen");

            TextView nameView = (TextView)findViewById(R.id.textNameRequest);
            TextView emailView = (TextView)findViewById(R.id.textEmailRequest);
            TextView imageView = (TextView)findViewById(R.id.textImageRequest);

            nameView.setText(name);
            emailView.setText(email);
            imageView.setText(image);



        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}
