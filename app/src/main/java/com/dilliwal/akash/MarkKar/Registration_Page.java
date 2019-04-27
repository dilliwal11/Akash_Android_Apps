package com.dilliwal.akash.MarkKar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;

public class Registration_Page extends AppCompatActivity {
    EditText usern,pass;
    String un,p;SharedPreferences sp ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_registration__page);
        usern = (EditText) findViewById(R.id.editTuserNameR);
        pass = (EditText) findViewById(R.id.editTextPassP);

    }



    public void register (final View v) {
        if (checkNetworkConnection(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("wait for it...");
            progressDialog.show();
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);



            un = usern.getText().toString();
            p = pass.getText().toString();
            if (p.trim().equals("") || un.trim().equals("")) {
                Toast.makeText(Registration_Page.this, "All fields are required !!", Toast.LENGTH_LONG).show();
            } else {

                if (v.isEnabled()) {
                    v.setEnabled(false);  // when Register buton clicked it becomes disabled.
                }


                final Response.Listener<String> response = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                progressDialog.dismiss();
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("username", un);  //username is taken here when registration done and put into sharedpreference
                                editor.commit();
                                Intent i = new Intent(Registration_Page.this, ShowTime.class);
                                startActivity(i);
                                Registration_Page.this.finish();


                            } else {
                                v.setEnabled(true);
                                AlertDialog.Builder builder = new AlertDialog.Builder(Registration_Page.this);
                                builder.setTitle("Username Already Exists").setNegativeButton("Retry", null).create().show();
                                progressDialog.dismiss();
                                v.setEnabled(true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(un, p, response);
                RequestQueue queue = Volley.newRequestQueue(Registration_Page.this);
                queue.add(registerRequest);
            }
        }
        else{
            Toast.makeText(Registration_Page.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void login(View v){

        Intent i = new Intent(this,Login.class);
        startActivity(i);
        Registration_Page.this.finish();

    }
    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

}