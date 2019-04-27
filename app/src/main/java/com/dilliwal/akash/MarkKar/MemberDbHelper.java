package com.dilliwal.akash.MarkKar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by akash on 20/2/17.
 */
public class MemberDbHelper extends SQLiteOpenHelper {
    private static final int version=20;
    private static final String name= "hopeisall.db";
     private Context context;
    public MemberDbHelper(Context context)
    {
        super(context, name, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE HOPEISALL ( _id integer PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(10),WORK VARCHAR(10)," +
                " DATE INT,SALARY INT,ATTENDANCEDATE TEXT DEFAULT '' ,RESETVALUE INTEGER DEFAULT '0',ABSENTDATE TEXT DEFAULT '',ABSENT INTEGER DEFAULT '0',PRESENT INTEGER DEFAULT '0',SYNC1 INTEGER DEFAULT '-1',SYNC2 INTEGER DEFAULT '-1')";


        try {
            sqLiteDatabase.execSQL(createTable);


        }
        catch (SQLException e)
        {

            Message.message(context , ""+ e);

        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        try {

            sqLiteDatabase.execSQL("drop table if exists HOPEISALL");
            onCreate(sqLiteDatabase);
        }
        catch (SQLException e){
            Message.message(context , ""+ e);


        }




    }
}
