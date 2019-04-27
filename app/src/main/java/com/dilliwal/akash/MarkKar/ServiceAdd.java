package com.dilliwal.akash.MarkKar;

import android.app.AlertDialog;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.dilliwal.akash.MarkKar.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akash on 1/4/17.
 */
public class ServiceAdd extends IntentService {
    SharedPreferences sp;
    public ServiceAdd()
    {
        super("mark_thread");
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        String trick = intent.getStringExtra("check");
        switch (trick) {
            case "add": {
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                 String usern = sp.getString("username","");
                 String name = intent.getStringExtra("NAME");
                 String work = intent.getStringExtra("WORK");
                 int date = intent.getIntExtra("DATE", 1);
                 String salary = intent.getStringExtra("SALARY");


                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated Add");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddServiceRequest addServiceRequest = new AddServiceRequest(usern, name, work, date, salary, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(addServiceRequest);
                break;
            }
            case "delete":
            {
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");

                long longId = intent.getLongExtra("ID",0);
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated delete");
                            } else {
                                Log.d("delete", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                DeleteServiceReq deleteServiceRequest = new DeleteServiceReq( usern,longId,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(deleteServiceRequest);

                break;
            }
            case "mark" :

            {
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");

                int absent = intent.getIntExtra("ABSENT",0);
                String attendanceDate = intent.getStringExtra("ATTENDANCEDATE");
                int resetv = intent.getIntExtra("RESETVALUE",0);
                String stringId = intent.getStringExtra("STRINGID");

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated mark");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MarkServiceReq markServiceRequest = new MarkServiceReq(absent, attendanceDate, resetv, stringId,usern, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(markServiceRequest);
                break;
            }
            case "markPtoA":{
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");

                int present = intent.getIntExtra("PRESENT",0);
                String stringId = intent.getStringExtra("STRINGID");
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated markPtoA");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MarkPtoAServiceReq markPtoAServiceRequest = new MarkPtoAServiceReq(present, stringId,usern, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(markPtoAServiceRequest);
                break;
            }

            case "markAtoP":{
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");
                int absent = intent.getIntExtra("ABSENT",0);
                String absentDate = intent.getStringExtra("ABSENTDATE");
                String stringId = intent.getStringExtra("STRINGID");
                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated markAtoP");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MarkAtoPServiceReq markAtoPServiceRequest = new MarkAtoPServiceReq(absent,absentDate, stringId,usern, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(markAtoPServiceRequest);
                break;
            }
            case"markP":{
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                 String usern = sp.getString("username", "");

                int present = intent.getIntExtra("PRESENT",0);
                String attendanceDate = intent.getStringExtra("ATTENDANCEDATE");
                int resetv = intent.getIntExtra("RESETVALUE",0);
                String stringId = intent.getStringExtra("STRINGID");

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated markP");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                MarkPServiceReq markPServiceRequest = new MarkPServiceReq(present, attendanceDate, resetv, stringId,usern, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(markPServiceRequest);


                break;
            }
            case "reset":
            {
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");
                String stringId = intent.getStringExtra("STRINGID");
                String attendanceDate = intent.getStringExtra("ATTENDANCEDATE");

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                    ResetServiceRequest resetServiceRequest = new ResetServiceRequest(usern,stringId,attendanceDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(resetServiceRequest);

                break;
            }
            case "reset0":{
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                final String usern = sp.getString("username", "");
                String stringId = intent.getStringExtra("STRINGID");
                String absentDate = intent.getStringExtra("ABSENTDATE");
                String attendanceDate = intent.getStringExtra("ATTENDANCEDATE");
                int absent = intent.getIntExtra("ABSENT",0);

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated reset0");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ResetZeroServiceRequest resetZeroServiceRequest = new ResetZeroServiceRequest(usern,stringId,absentDate,attendanceDate,absent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(resetZeroServiceRequest);

                break;
            }
            case "reset1":{
                sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                 String usern = sp.getString("username", "");
                String stringId = intent.getStringExtra("STRINGID");
                int present = intent.getIntExtra("PRESENT",0);
                String attendanceDate = intent.getStringExtra("ATTENDANCEDATE");

                final Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if (success) {
                                Log.d("a", "updated reset1");
                            } else {
                                Log.d("x", "sorry");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ResetOneServiceRequest resetOneServiceRequest = new ResetOneServiceRequest(usern,stringId,present,attendanceDate, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ServiceAdd.this);
                queue.add(resetOneServiceRequest);

                break;
            }




        }
    }
    }




