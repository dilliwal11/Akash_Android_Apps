package com.dilliwal.akash.MarkKar;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by akash on 2/4/17.
 */
public class NetworkMonitor extends BroadcastReceiver {

    int sync2;int sync1;SharedPreferences sp;int count = 0;

    Cursor c;
    @Override
    public void onReceive(Context context, Intent intent) {

        if (checkNetworkConnection(context)) {

            MemberDbHelper memberDbHelper = new MemberDbHelper(context);
            final SQLiteDatabase db = memberDbHelper.getWritableDatabase();
            String[] col = {"_id","NAME", "WORK","DATE","SALARY",
                    "ATTENDANCEDATE","RESETVALUE","ABSENTDATE","ABSENT","PRESENT","SYNC1","SYNC2"};
            c = db.query("HOPEISALL", col, null, null, null, null, null);
          //  Log.d("start", "onReceive:"+c.getPosition());

            while (c.moveToNext()) {
                sync1 = c.getInt(10);
                sync2 = c.getInt(11);
             //   Log.d("in while loop",""+ c.getPosition());
                if (sync1 == 0) {
                    count++;
                    sp = context.getSharedPreferences("Myfile",Context.MODE_PRIVATE);
                    String usern = sp.getString("username", "");
                    final int id1 = c.getInt(0);
                    String name = c.getString(1);
                    String work = c.getString(2);
                    int date = c.getInt(3);
                    int salary = c.getInt(4);

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    Log.d("success","sycnone "+count);
                                    db.execSQL("update HOPEISALL set SYNC1 = 1 where _id = "+id1);

                                } else {
                                    Log.d("x", "sorry");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                   SyncOneRequest syncOneRequest = new SyncOneRequest(name,work,date,salary,usern, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(syncOneRequest);
                }
                if (sync2 == 0)
                {
                    sp = context.getSharedPreferences("Myfile",Context.MODE_PRIVATE);
                    String usern = sp.getString("username", "");
                   final int id2 = c.getInt(0);
                    String attendanceDate = c.getString(5);
                    final int rv = c.getInt(6);
                    String absentDate = c.getString(7);
                    int absent = c.getInt(8);
                    int present = c.getInt(9);


                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");

                                if (success) {
                                    if(rv==0){
                                    Log.d("a","synctwoA");
                                    }
                                    else if (rv==1){
                                        Log.d("a","synctwoP");
                                    }
                                    db.execSQL("update HOPEISALL set SYNC2 = 1 where _id = "+id2);

                                }
                                else
                                {
                                    Log.d("x", "sorry");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    if(rv == 0)
                    {

                            SyncTwoARequest syncTwoARequest = new SyncTwoARequest(id2, attendanceDate, rv, absentDate, absent, present, usern, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(syncTwoARequest);


                    }
                    else if (rv==1){

                        SyncTwoPRequest syncTwoPRequest = new SyncTwoPRequest(id2, attendanceDate, rv, absentDate, absent, present, usern, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(context);
                        queue.add(syncTwoPRequest);
                    }
                }

            }

        }
    }



    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
}
