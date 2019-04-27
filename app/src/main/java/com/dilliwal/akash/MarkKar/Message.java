package com.dilliwal.akash.MarkKar;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by akash on 20/2/17.
 */
public class Message  {

    public static void message (Context context,String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
