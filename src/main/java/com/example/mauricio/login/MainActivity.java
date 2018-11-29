package com.example.mauricio.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText txtCor, txtPas;
    Button btnIngresar;

    private String user;

//    public static final String MyPREFERENCES = "MyPrefs";
//    public static final String Name = "nameKey";
//    public static final String Email = "emailKey";
//    public static final String Password = "passwordKey";
//    public static final String Image = "imageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCor = (EditText)findViewById(R.id.txtCor);
        txtPas = (EditText)findViewById(R.id.txtPas);

        btnIngresar = (Button)findViewById(R.id.btnIngresar);

        //SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread tr = new Thread(){
                    @Override
                    public void run() {
                        final String res = enviarPost(txtCor.getText().toString(), txtPas.getText().toString());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                int r = objJSON(res);

                                String u = getResultData(res);

                                if (r > 0){

                                    Intent i = new Intent(getApplicationContext(), Principal.class);
                                    i.putExtra("data", res);
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getApplicationContext(),"Usuario o Password Incorrectos", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                };

                tr.start();

            }
        });


    }

    public String enviarPost(String cor, String pas){

        String parametros = "cor="+cor+"&pas="+pas;

        HttpURLConnection conection = null;

        String respuesta = "";

        try{
            URL url = new URL("http://10.0.0.6/webservicesappmovil/valida.php");
            conection=(HttpURLConnection)url.openConnection();
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Length", ""+Integer.toString(parametros.getBytes().length));

            conection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(conection.getOutputStream());

            wr.writeBytes(parametros);

            wr.close();

            Scanner inStream = new Scanner(conection.getInputStream());

            while (inStream.hasNextLine())
                respuesta+=(inStream.nextLine());


        }catch (Exception e){}

        return respuesta.toString();

    }

    public int objJSON(String rspta){

        int res = 0;

        try{
            JSONArray json = new JSONArray(rspta);

            if (json.length() > 0)
                res=1;

        }catch (Exception e){}

        return res;
    }

    public String getResultData(String resp){

        try{
            JSONArray arr = new JSONArray(resp);
            JSONObject jObj = arr.getJSONObject(0);
            user = jObj.getString("nombre");


        }catch (Exception e){}

        return user;
    }
}
