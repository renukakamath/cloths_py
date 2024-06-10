package com.example.sanitizer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewwallet extends AppCompatActivity implements JsonResponse {
    ListView l1;
    SharedPreferences sh;
    String[] amount,date,value;
    public static String sid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewwallet);


        l1=(ListView) findViewById(R.id.list);
//        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewwallet.this;
        String q = "/Viewwallet?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                amount = new String[ja1.length()];
                date = new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    amount[i] = ja1.getJSONObject(i).getString("amount");

                    date[i] = ja1.getJSONObject(i).getString("date");





                    value[i] = "amount: " + amount[i]  + "\ndate: " + date[i]  ;

                }
                ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext, value);

                l1.setAdapter(ar);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }
    }
