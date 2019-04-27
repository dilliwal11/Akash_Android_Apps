package com.dilliwal.akash.MarkKar;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTime extends AppCompatActivity {
    ListView listView;
    SimpleCursorAdapter simpleCursorAdapter;
    MemberDbHelper memberDbHelper;
    SQLiteDatabase db;SharedPreferences sp;
    TextView textViewWel;
    TextView textViewPep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_time);
        memberDbHelper = new MemberDbHelper(ShowTime.this);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e8b83d")));
        int a;


        listView = (ListView) findViewById(R.id.listView);
        display(); // call to display method for showing the content

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(ShowTime.this,MainActivity.class);
                startActivity(intent);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ShowTime.this, DetailList.class);
                intent.putExtra("listNo", i);
                startActivity(intent);

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, final View view, int i, final long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowTime.this);
                builder.setTitle("Delete").setMessage("Say bye to this person ?").setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override

                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(checkNetworkConnection(ShowTime.this)) {
                            SQLiteDatabase db = memberDbHelper.getReadableDatabase();
                            db.execSQL("DELETE FROM HOPEISALL WHERE _id = " + l);
                            display();
                            simpleCursorAdapter.notifyDataSetChanged();
                            Intent servIntent = new Intent(ShowTime.this, ServiceAdd.class);
                            servIntent.putExtra("check", "delete");
                            servIntent.putExtra("ID",l);
                            startService(servIntent);
                        }
                        else
                        {
                            Toast.makeText(ShowTime.this,"No Network !!",Toast.LENGTH_LONG).show();
                        }


                    }
                }).setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

                return true;
            }

        });


    }

    public void display(){


        db = memberDbHelper.getReadableDatabase();
        String[] col = { "_id","NAME", "WORK", "DATE", "SALARY"};
        int[] boundTo = { R.id.textViewNum ,R.id.textViewName, R.id.textViewWork, R.id.textViewDate, R.id.textViewSal};
        Cursor c = db.query("HOPEISALL", col, null, null, null, null, null);
        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.memberlist, c, col, boundTo, 0);
        listView.setAdapter(simpleCursorAdapter);
        int a = simpleCursorAdapter.getCount();

        if(a==0)
        {
            textViewPep = (TextView)findViewById(R.id.textViewpeople);
            textViewWel = (TextView)findViewById(R.id.textViewWel);
            textViewPep.setVisibility(View.VISIBLE);
            textViewWel.setVisibility(View.VISIBLE);


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(checkNetworkConnection(this)) {

            int id = item.getItemId();
            switch (id) {
                case R.id.menuLayout:                                   //on Logout
                    sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isloggedin", false);
                    editor.commit();
                    Intent in = new Intent(ShowTime.this, Login.class);
                    startActivity(in);
                    memberDbHelper.onUpgrade(db, 0, 0);
                    ShowTime.this.finish();

                    break;
            }
        }
        else {
            Toast.makeText(this,"Check your network connection",Toast.LENGTH_LONG).show();
        }
            return true;
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowTime.this);
        builder.setTitle("EXIT").setMessage("Do you want close MarkKar ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                moveTaskToBack(true);
            }
        }).setNegativeButton("No", null).setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }


}