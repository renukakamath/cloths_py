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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewbookings extends AppCompatActivity implements JsonResponse, AdapterView.OnItemClickListener {
    ListView l1;
    SharedPreferences sh;
    String[] amount,date,statu,name,fname,value,solution_id,image;
    public static String amt,bid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewbookings);

        l1=(ListView) findViewById(R.id.list);
        l1.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Viewbookings.this;
        String q = "/viewbookings?log_id=" +sh.getString("log_id", "");
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    @Override
    public void response(JSONObject jo) {
        try {


            String method=jo.getString("method");
            if(method.equalsIgnoreCase("Makewallet")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Viewbookings.class));

                }

                else {

                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("viewbookings"))
            {

            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                amount = new String[ja1.length()];
                date = new String[ja1.length()];
                statu = new String[ja1.length()];
                name = new String[ja1.length()];

                solution_id = new String[ja1.length()];
                image = new String[ja1.length()];

                value = new String[ja1.length()];


                String[] value = new String[ja1.length()];

                for (int i = 0; i < ja1.length(); i++) {
                    amount[i] = ja1.getJSONObject(i).getString("amount");

                    date[i] = ja1.getJSONObject(i).getString("date");
                    statu[i] = ja1.getJSONObject(i).getString("status");
                    name[i] = ja1.getJSONObject(i).getString("product");

                    solution_id[i] = ja1.getJSONObject(i).getString("booking_id");
                    image[i] = ja1.getJSONObject(i).getString("image");


                    value[i] = "name: " + name[i] + "\namount: " + amount[i] + "\ndate: " + date[i] + "\nstatu: " + statu[i];

                }
                Custimage1 a = new Custimage1(this, name, amount, date, statu, image);
                l1.setAdapter(a);
            }

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    amt=amount[i];
    bid=solution_id[i];

        final CharSequence[] items = {"make Payment","Make Payment wallet","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Viewbookings.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("make Payment")) {

                    startActivity(new Intent(getApplicationContext(),makepayment.class));




                }  else if (items[item].equals("Make Payment wallet")) {


                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Viewbookings.this;
                    String q = "/Makewallet?login_id=" + sh.getString("log_id", "")+"&bid="+bid+ "&amt="+amt ;
                    q = q.replace(" ", "%20");
                    JR.execute(q);





                }


                else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }

        });
        builder.show();

    }
}