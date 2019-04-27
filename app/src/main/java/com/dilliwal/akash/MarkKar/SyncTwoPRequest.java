package com.dilliwal.akash.MarkKar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 29/4/17.
 */
public class SyncTwoPRequest extends StringRequest {
    private static final String MARK_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/synctwoP.php";
    private Map<String,String> params;
    public SyncTwoPRequest (int id,String attendanceDate,int rv,String absentDate,int absent,int present,String usern, Response.Listener<String> listener){
        super(Request.Method.POST, MARK_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("username",usern);
        params.put("ID",String.valueOf(id));
        params.put("ATTENDANCEDATE",attendanceDate);
        params.put("RESETVALUE",String.valueOf(rv));
        params.put("ABSENTDATE",absentDate);
        params.put("PRESENT",String.valueOf(present));
        params.put("ABSENT",String.valueOf(absent));



    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}

