package com.dilliwal.akash.MarkKar;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioButton radioButton;
    EditText editText;
    EditText editText2;
    EditText eSal;
    Button button;
    NumberPicker numberPicker;
    ActionBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e8b83d")));

        button = (Button) findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.urname_et);
        editText2 = (EditText)findViewById(R.id.username_et);
        eSal = (EditText) findViewById(R.id.password_et);
        numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(31);
    }

    public void save (View v)
    {

        String n = editText.getText().toString();
        String w = editText2.getText().toString();
        String s = eSal.getText().toString();
        int pick = numberPicker.getValue();
        if(n.trim().equals("") || w.trim().equals("")||s.trim().equals(""))
        {
            Toast.makeText(MainActivity.this, "All fields are required !!", Toast.LENGTH_LONG).show();
        }
        else {
            MemberDbHelper memberDbHelper = new MemberDbHelper(this);
            SQLiteDatabase db = memberDbHelper.getWritableDatabase();

            int  sync1 = 0;
            if(checkNetworkConnection(MainActivity.this)) //service to update data on server
            {


                Intent servIntent = new Intent(this,ServiceAdd.class);
                servIntent.putExtra("check","add");
                servIntent.putExtra("NAME",n);
                servIntent.putExtra("WORK",w);
                servIntent.putExtra("DATE",pick);
                servIntent.putExtra("SALARY",s);
                startService(servIntent);
                sync1 = 1;

            }

            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", n);
            contentValues.put("WORK", w);
            contentValues.put("DATE", pick);
            contentValues.put("SALARY", s);
            contentValues.put("SYNC1",sync1);
            db.insert("HOPEISALL", null, contentValues);
            db.close();
            Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,ShowTime.class);
            startActivity(intent);
            finish();
        }

    }
    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

}
