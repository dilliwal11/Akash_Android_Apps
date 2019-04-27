package com.dilliwal.akash.MarkKar;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    EditText editUser;
    EditText editPass;
    SharedPreferences sp;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
        b = (Button)findViewById(R.id.bLogin);


        boolean check = sp.getBoolean("isloggedin",false);
        if(check)
        {
                                                            //login check if it was already then this activity skips.
            Intent i = new Intent(this,ShowTime.class);
            startActivity(i);
            this.finish();

        }
        setContentView(R.layout.activity_login);
        editUser = (EditText)findViewById(R.id.edtUsername);
        editPass = (EditText)findViewById(R.id.edtpassword);
    }

    public  void login (final View v) {
        if (checkNetworkConnection(this)) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("wait for it...");
            progressDialog.show();
             progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);

            String pass = editPass.getText().toString();  //after clicking login pass and user are sent to server.
            final String user = editUser.getText().toString();
            if (pass.trim().equals("") || user.trim().equals("")) {
                Toast.makeText(Login.this, "All fields are required !!", Toast.LENGTH_LONG).show();
            } else {


                if (v.isEnabled()) {
                    v.setEnabled(false);        // when login buton clicked it becomes disabled.
                }


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            int flag = jsonObject.getInt("flag");
                            if (success) { //if login success
                                progressDialog.dismiss();
                                if (flag == 1) {
                                    Intent i = new Intent(Login.this, ShowTime.class);
                                    startActivity(i);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username", user);
                                    editor.putBoolean("isloggedin", true);
                                    editor.commit();


                                } else {
                                    MemberDbHelper memberDbHelper = new MemberDbHelper(Login.this);
                                    SQLiteDatabase db = memberDbHelper.getWritableDatabase();
                                    memberDbHelper.onUpgrade(db, 0, 0);
                                    JSONArray n = jsonObject.getJSONArray("name");
                                    JSONArray w = jsonObject.getJSONArray("work");
                                    JSONArray d = jsonObject.getJSONArray("date");
                                    JSONArray sal = jsonObject.getJSONArray("salary");
                                    JSONArray attendancedate = jsonObject.getJSONArray("attendancedate");
                                    JSONArray resetvalue = jsonObject.getJSONArray("resetvalue");
                                    JSONArray absentdate = jsonObject.getJSONArray("absentdate");
                                    JSONArray absent = jsonObject.getJSONArray("absent");
                                    JSONArray present = jsonObject.getJSONArray("present");
                                    JSONObject value = null;
                                    ContentValues contentValues = new ContentValues();
                                    for (int i = 0; i < n.length(); i++) {
                                        value = n.getJSONObject(i);
                                        String naam = value.getString("name");
                                        String kaam = value.getString("work");
                                        String tarik = value.getString("date");
                                        String salString = value.getString("salary");
                                        String ad = value.getString("attendancedate");
                                        String rv = value.getString("resetvalue");
                                        String absd = value.getString("absentdate");
                                        String abs = value.getString("absent");
                                        String pres = value.getString("present");
                                        contentValues.put("NAME", naam);
                                        contentValues.put("WORK", kaam);
                                        contentValues.put("DATE", tarik);
                                        contentValues.put("SALARY", salString);
                                        contentValues.put("ATTENDANCEDATE", ad);
                                        contentValues.put("RESETVALUE", rv);
                                        contentValues.put("ABSENTDATE", absd);
                                        contentValues.put("ABSENT", abs);
                                        contentValues.put("PRESENT", pres);
                                        db.insert("HOPEISALL", null, contentValues);

                                      //  Log.d("aaya kya", " " + value + " ");
                                    }

                                    Intent i = new Intent(Login.this, ShowTime.class);
                                    startActivity(i);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("username", user);
                                    editor.putBoolean("isloggedin", true);
                                    editor.commit();
                                    Login.this.finish();


                                }
                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                                Toast.makeText(Login.this, "nope", Toast.LENGTH_SHORT);
                                builder.setTitle("Username/Password").setNegativeButton("Retry", null).setMessage("please try again").create().show();
                                progressDialog.dismiss();
                                v.setEnabled(true);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(user, pass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(loginRequest);

            }
        }
        else {
            Toast.makeText(Login.this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void notRegis(View v) {
        Intent i = new Intent(this, Registration_Page.class);
        startActivity(i);
        Login.this.finish();

    }
    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("EXIT").setMessage("Do you want close MarkKar ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
            }
        }).setNegativeButton("No", null).setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }



}