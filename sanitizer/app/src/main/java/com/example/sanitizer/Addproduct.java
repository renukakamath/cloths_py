package com.example.sanitizer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Addproduct extends AppCompatActivity implements JsonResponse, AdapterView.OnItemSelectedListener {
    EditText e1,e2,e3;
    Spinner s1;
    String status;
    public static String aid;
    String[] fname,agent_id,value;
    SharedPreferences sh;
    Button b1;

    ImageButton i1;

    String request, details;
    final int CAMERA_PIC_REQUEST = 0, GALLERY_CODE = 201;
    public static String encodedImage = "", path = "";
    private Uri mImageCaptureUri;
    byte[] byteArray = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addproduct);

        s1 = findViewById(R.id.spinner);
        s1.setOnItemSelectedListener(this);
        b1 = findViewById(R.id.buy);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        i1 = (ImageButton) findViewById(R.id.imageButton);

        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageOption();
            }
        });

        e1 = (EditText) findViewById(R.id.request);
        e2 = (EditText) findViewById(R.id.details);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                request = e1.getText().toString();
                details = e2.getText().toString();



                sendAttach();
            }
        });

        JsonReq JR = new JsonReq();
        JR.json_response = (JsonResponse) Addproduct.this;
        String q = "/viewagentspinner";
        q = q.replace(" ", "%20");
        JR.execute(q);
    }

    private void sendAttach() {

        try {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            String q = "http://" + Ipsettings.text + "/api/Addproduct";

            Toast.makeText(getApplicationContext(), "Byte Array:" + byteArray.length, Toast.LENGTH_LONG).show();


            Map<String, byte[]> aa = new HashMap<>();

            aa.put("image", byteArray);
            aa.put("aid", aid.getBytes());
            aa.put("request", request.getBytes());
                aa.put("lid",sh.getString("log_id","").getBytes());
            aa.put("details", details.getBytes());
//            aa.put("total", total.getBytes());

//                aa.put("log_id",sh.getString("log_id","").getBytes());
//            aa.put("house",house.getBytes());

            FileUploadAsync fua = new FileUploadAsync(q);
            fua.json_response = (JsonResponse) Addproduct.this;
            fua.execute(aa);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Exception upload : " + e, Toast.LENGTH_SHORT).show();
        }


    }


    private void selectImageOption() {
        /*Android 10+ gallery code
        android:requestLegacyExternalStorage="true"*/

        final CharSequence[] items = {"Capture Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Addproduct.this);
        builder.setTitle("Take Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                    startActivityForResult(intent, CAMERA_PIC_REQUEST);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method = jo.getString("method");
            Log.d("pearl", method);

            if (method.equalsIgnoreCase("viewagentspinner")) {
                status = jo.getString("status");
                Log.d("pearlssssss", status);


                if (status.equalsIgnoreCase("success")) {

                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");

                    fname = new String[ja1.length()];
                    agent_id = new String[ja1.length()];

                    value = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {

                        fname[i] = ja1.getJSONObject(i).getString("fname");
                        agent_id[i] = ja1.getJSONObject(i).getString("agent_id");
                        value[i] = fname[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext, value);

                    s1.setAdapter(ar);


                } else {
                    Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
                    s1.setVisibility(View.GONE);
                }
            }

            if (method.equalsIgnoreCase("Addproduct")) {
                status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {

                    Toast.makeText(getApplicationContext(), " Product Added Successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), Userhome.class));

                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    s1.setVisibility(View.GONE);
                }
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();

        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        aid=agent_id[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : " + mImageCaptureUri);
            //   CropingIMG();

            Uri uri = data.getData();
            Log.d("File Uri", "File Uri: " + uri.toString());
            // Get the path
            //String path = null;
            try {
//                path = FileUtils.getPath(this, uri);
                path=FileUtils.getPath(this,uri);
                Toast.makeText(getApplicationContext(), "path : " + path, Toast.LENGTH_LONG).show();

                File fl = new File(path);
                int ln = (int) fl.length();

                InputStream inputStream = new FileInputStream(fl);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[ln];
                int bytesRead = 0;

                while ((bytesRead = inputStream.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                inputStream.close();
                byteArray = bos.toByteArray();

                Bitmap bit = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                i1.setImageBitmap(bit);

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == Activity.RESULT_OK) {

            try {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                i1.setImageBitmap(thumbnail);
                byteArray = baos.toByteArray();

                String str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encodedImage = str;
//                sendAttach1();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Userhome.class);
        startActivity(b);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}