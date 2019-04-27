package com.dilliwal.akash.MarkKar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class DetailList extends AppCompatActivity {
    TextView textView,textViewDate,textViewSal,textViewTd,textViewW,textViewInv;
    Button Sv,buttonRes;
    MemberDbHelper memberDbHelper;SQLiteDatabase db;
    RadioGroup radioGroup;
    int absent,present, Nd = 0,MonthSal=0,sal;
    int t,a,Wdate,restvalue;
    String str,str2,stringID;
    String precaution="",precaution2 ="";
    String attendace = "",Atd,detials;
    boolean flag = false;
    int sync2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_list);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e8b83d")));;
        Sv = (Button)findViewById(R.id.buttonSave);
        textViewTd=(TextView) findViewById(R.id.textViewTd);
        textView = (TextView) findViewById(R.id.textView);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewSal = (TextView) findViewById(R.id.textViewSal);
        textViewW = (TextView) findViewById(R.id.textViewWork);
        textViewInv = (TextView)findViewById(R.id.InvisibleText);
        textViewInv.setVisibility(View.GONE);
        radioGroup = (RadioGroup)findViewById(R.id.rgp);
        Intent intent1 = getIntent();
        a = intent1.getIntExtra("listNo",0); // value for the id column
        // present date
        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("dd");
        SimpleDateFormat ft2 = new SimpleDateFormat("dd.MM.yyyy");
        str2 = ft2.format(dNow); //Date to be shown at top
        textViewTd.setText(str2);
        str = ft.format(dNow);
        memberDbHelper = new MemberDbHelper(this);
        db = memberDbHelper.getReadableDatabase();
        String [] col = {"_id","NAME","WORK","DATE","SALARY","ABSENT","ATTENDANCEDATE","PRESENT"}; //select for all columns
        Cursor c = db.query("HOPEISALL",col,null,null,null,null,null); // cursor created
        c.moveToPosition(a); //cursor positioned
        int id = c.getInt(0);
        stringID = String.valueOf(id);
        String name = c.getString(1);
        textView.setText("Name: "+name); // name fetched
        String work = c.getString(2);
        textViewW.setText("Work: "+work);
        Wdate = c.getInt(3); // work cycle date
        textViewDate.setText("Starting Date: "+Wdate);
        sal = c.getInt(4);
        absent= c.getInt(5);
        present = c.getInt(7);
        Atd=c.getString(6);


        if(Atd.equals(str2))
        {
            buttonRes = (Button)findViewById(R.id.buttonRes);
            textViewInv.setVisibility(View.VISIBLE);
            textViewInv.setText("Press RESET to modify !");
            radioGroup.setVisibility(View.INVISIBLE);
            Sv.setVisibility(View.INVISIBLE);
            buttonRes.setVisibility(View.VISIBLE);

        }
        db.close();
    }
    public  void details (View v)
    {
         memberDbHelper = new MemberDbHelper(this);
         db = memberDbHelper.getReadableDatabase();
        String [] col = {"_id","ABSENT","PRESENT"}; //select for all columns
        Cursor c = db.query("HOPEISALL",col,null,null,null,null,null); // cursor created
        c.moveToPosition(a); //cursor positioned
        int absent1 = c.getInt(1);
        int present1 = c.getInt(2);

        t = Integer.parseInt(str); //today date changed to integer for calculating the cycle days
        Nd = 0;
        MonthSal=0;
        if(Wdate>t)    //calculation for salary according to cycle date and present date
        {
            Nd = Wdate-t;
            Nd = 31-Nd;
        }
        else
        {
            Nd = t - Wdate;
        }
        int p = Nd-absent1;
        MonthSal=sal/31;
        MonthSal = MonthSal*p;
        Nd=Nd+1;
        if (absent1 < 0)
            absent1 = 0;

        detials = "Month Salary is "+sal+", Amount to pay till today is "+MonthSal + ", days passed  " + Nd + ", No. of days not came "+absent1 +" and No. of days presence marked "+ present1+ ".";

        textViewSal.setText(detials);
        db.close();

    }



    public void reset (View v){
        textViewInv.setVisibility(View.INVISIBLE);
        radioGroup.setVisibility(View.VISIBLE);
        Sv.setVisibility(View.VISIBLE);
        buttonRes.setVisibility(View.GONE);
         memberDbHelper = new MemberDbHelper(this);
         db = memberDbHelper.getReadableDatabase();
        String [] col = {"ATTENDANCEDATE","RESETVALUE"}; //select for all columns
        Cursor c = db.query("HOPEISALL",col,null,null,null,null,null); // cursor created
        c.moveToPosition(a); //cursor positioned
        Atd = c.getString(0);
        db.execSQL("UPDATE HOPEISALL SET ATTENDANCEDATE = REPLACE(ATTENDANCEDATE," + "'"+ Atd+ " "+"'"+",'') WHERE _id = "+stringID);

        restvalue = c.getInt(1);
        if(restvalue == 0){
            absent = absent - 1;
            db.execSQL("UPDATE HOPEISALL SET ABSENT = " + absent + " WHERE _id = " + stringID);
            db.execSQL("UPDATE HOPEISALL SET ABSENTDATE = REPLACE(ABSENTDATE," + "'"+ str2+ " "+"'"+",'') WHERE _id = "+stringID);
            if(checkNetworkConnection(DetailList.this)){
                Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                intent.putExtra("check","reset0");
                intent.putExtra("ABSENTDATE",str2);
                intent.putExtra("ATTENDANCEDATE",Atd);
                intent.putExtra("ABSENT",absent);
                intent.putExtra("STRINGID",stringID);
                startService(intent);
            }

        }
        else if (restvalue == 1)
        {
            present = present-1;
            db.execSQL("UPDATE HOPEISALL SET PRESENT = " + present + " WHERE _id = " + stringID);
            if(checkNetworkConnection(DetailList.this)){
                Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                intent.putExtra("check","reset1");
                intent.putExtra("PRESENT",present);
                intent.putExtra("STRINGID",stringID);
                intent.putExtra("ATTENDANCEDATE",Atd);
                startService(intent);
            }
        }

            db.execSQL("UPDATE HOPEISALL SET SYNC2 = -1");
            db.close();

    }

    public void listAbs (View v){
        memberDbHelper = new MemberDbHelper(this);
         db = memberDbHelper.getReadableDatabase();
        String [] col = {"_id","ABSENTDATE"}; //select for all columns
        Cursor c = db.query("HOPEISALL",col,null,null,null,null,null); // cursor created
        c.moveToPosition(a); //cursor positioned
        String absDates = c.getString(1);
        String [] values = absDates.split(" ");
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailList.this);
        LayoutInflater inflater = getLayoutInflater();
        View viewL = (View) inflater.inflate(R.layout.absent_list, null);
        builder.setView(viewL);
        builder.setTitle("Absent List");
        builder.setPositiveButton("ok",null).setNegativeButton("cancel",null);
        ListView listView2 = (ListView)viewL.findViewById(R.id.listView2);
        //custom listing
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Arrays.asList(values));
        listView2.setAdapter(arrayAdapter);
        builder.show();
        /* AlertDialog alertDialog = builder.create();
        alertDialog.show();*/
        db.close();
    }



    public void take(View v) { //mark it button clicked
        int i = radioGroup.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) radioGroup.findViewById(i);
        attendace = rb.getText().toString(); //present or absent radio button
        if (attendace.equals("Absent") && precaution.equals(""))
        {
            absent =absent+1;
            restvalue = 0; // reset value is kept and for absent it is zero.
            if(precaution2.equals(str2))
            {                                               // updation from present to absent
                present = present -1;

                if(checkNetworkConnection(DetailList.this)){   //markPtoA details saved on server
                    Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                    intent.putExtra("check","markPtoA");
                    intent.putExtra("PRESENT",present);
                    intent.putExtra("STRINGID",stringID);
                    startService(intent);
                    flag = true;

                }
                if(flag){
                    flag = false;
                    sync2 = 1;
                }
                else{
                    sync2 = 0;
                }
                memberDbHelper = new MemberDbHelper(this);
                 db = memberDbHelper.getWritableDatabase();
                db.execSQL("UPDATE HOPEISALL SET PRESENT = "+present +",SYNC2 = "+sync2+" WHERE _id = "+ stringID);
                Toast.makeText(DetailList.this, "updation from present to absent", Toast.LENGTH_SHORT).show();



            }

            if(checkNetworkConnection(DetailList.this)){   //mark it absent details saved on server
                flag = true;
                Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                intent.putExtra("check","mark");
                intent.putExtra("ABSENT",absent);
                intent.putExtra("ATTENDANCEDATE",str2);
                intent.putExtra("RESETVALUE",restvalue);
                intent.putExtra("STRINGID",stringID);
                startService(intent);


            }
            if(flag){
                flag = false;
                sync2 = 1;
            }
            else{
                sync2 = 0;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put("ABSENT",absent);
            contentValues.put("ATTENDANCEDATE",str2);
            contentValues.put("RESETVALUE",restvalue);
            contentValues.put("SYNC2",sync2);
            precaution = str2;
            String [] whereargs = {stringID};
            memberDbHelper = new MemberDbHelper(this);
            db = memberDbHelper.getWritableDatabase();
            db.update("HOPEISALL",contentValues,"_id = ?",whereargs); //updation of present date, absent and reset value in database
            db.execSQL("UPDATE HOPEISALL SET ABSENTDATE = ABSENTDATE || "+ "'"+ str2+ " "+"'"+ " WHERE _id = "+ stringID);


            Toast.makeText(DetailList.this, "Absence saved", Toast.LENGTH_SHORT).show();
            db.close();

        }
        else if (attendace.equals("Present") && precaution2.equals(""))
        {
            present = present + 1;
            restvalue = 1;

            if(precaution.equals(str2)){ //updation from absent to present

                absent = absent - 1;

                if(checkNetworkConnection(DetailList.this)){   //markAtoP details saved on server
                    Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                    intent.putExtra("check","markAtoP");
                    intent.putExtra("ABSENTDATE",str2);
                    intent.putExtra("ABSENT",absent);
                    intent.putExtra("STRINGID",stringID);
                    startService(intent);
                   flag = true;

                }
                if(flag){
                    flag = false;
                    sync2 = 1;
                }
                else{
                    sync2 = 0;
                }
                 memberDbHelper = new MemberDbHelper(this);
                 db = memberDbHelper.getWritableDatabase();
                db.execSQL("UPDATE HOPEISALL SET ABSENT = "+absent +",SYNC2 = "+sync2+" WHERE _id = "+ stringID);
                db.execSQL("UPDATE HOPEISALL SET ABSENTDATE = REPLACE(ABSENTDATE," + "'"+ str2+ " "+"'"+",'') WHERE _id = "+stringID);
                Toast.makeText(DetailList.this, "updation from absent to present", Toast.LENGTH_SHORT).show();


            }

            if(checkNetworkConnection(DetailList.this)){ //mark present on server
                Intent intent = new Intent(DetailList.this,ServiceAdd.class);
                intent.putExtra("check","markP");
                intent.putExtra("PRESENT",present);
                intent.putExtra("ATTENDANCEDATE",str2);
                intent.putExtra("RESETVALUE",restvalue);
                intent.putExtra("STRINGID",stringID);
                startService(intent);
                flag = true;
            }
            if(flag){
                flag = false;
                sync2 = 1;
            }
            else{
                sync2 = 0;
            }
            precaution2 = str2;
            ContentValues contentValues = new ContentValues();
            contentValues.put("PRESENT", present);
            contentValues.put("ATTENDANCEDATE", str2);
            contentValues.put("RESETVALUE",restvalue);
            contentValues.put("SYNC2",sync2);
            String[] whereargs = {stringID};
            memberDbHelper = new MemberDbHelper(this);
             db = memberDbHelper.getWritableDatabase();
            db.update("HOPEISALL", contentValues, "_id = ?", whereargs); //updation of present attendance date,present and reset value in database
            Toast.makeText(DetailList.this, "Present taken", Toast.LENGTH_SHORT).show();

        }
        else if (attendace.equals(""))
        {
            Toast.makeText(DetailList.this, "select please", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(DetailList.this, "Same decision", Toast.LENGTH_SHORT).show();
        }
        details(v);
    }
    public boolean checkNetworkConnection(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
SharedPreferences sp;
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(checkNetworkConnection(this)) {

            int id = item.getItemId();
            switch (id) {
                case R.id.menuLayout:                                   //on Logout
                    Intent i = new Intent(DetailList.this, Login.class);
                    sp = getSharedPreferences("Myfile", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("isloggedin", false);
                    editor.commit();
                    startActivity(i);
                    memberDbHelper.onUpgrade(db, 0, 0);
                    finish();

                    break;
            }
        }
        else {
            Toast.makeText(this,"Check Network Connection First",Toast.LENGTH_LONG).show();
        }
        return true;
    }

    public void sendSms(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.putExtra("sms_body",detials);
        intent.setType("vnd.android-dir/mms-sms");
        // intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
        startActivity(intent);
    }
}
