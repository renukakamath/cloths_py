package com.example.sanitizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewproduct extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] amount,product,image,value,fname,product_id;
    public static String pid,amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewproduct);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewproduct.this;
        String q = "/Viewproduct?log_id=" +sh.getString("log_id", "");
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
                product = new String[ja1.length()];
                image = new String[ja1.length()];

                fname = new String[ja1.length()];
                product_id = new String[ja1.length()];


                value = new String[ja1.length()];



                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    amount[i] = ja1.getJSONObject(i).getString("amount");

                    product[i] = ja1.getJSONObject(i).getString("product");

                    image[i] = ja1.getJSONObject(i).getString("image");
                    fname[i] = ja1.getJSONObject(i).getString("fname");

                    product_id[i] = ja1.getJSONObject(i).getString("product_id");




                    value[i] = "product:" + product[i] + "\nagent name: " + fname[i]  + "\namount: " + amount[i]     ;

                }
                Custimage a=new Custimage(this,product,fname,amount,image);
                l1.setAdapter(a);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        pid=product_id[i];
        amt=amount[i];
        final CharSequence[] items = {"Book Now", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewproduct.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Book Now")) {

                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewproduct.this;
                    String q = "/Buynow?log_id=" +sh.getString("log_id", "")+"&pid="+pid +"&amt="+amt;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                    Toast.makeText(getApplicationContext(), "Booking Successfully:" , Toast.LENGTH_LONG).show();




                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
    }
