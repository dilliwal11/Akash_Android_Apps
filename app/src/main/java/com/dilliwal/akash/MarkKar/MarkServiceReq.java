package com.dilliwal.akash.MarkKar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 24/4/17.
 */
public class MarkServiceReq extends StringRequest {
    private static final String MARK_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/mark.php";
    private Map<String,String> params;
    public MarkServiceReq (int absent,String attendanceDate,int rv,String stringid,String usern, Response.Listener<String> listener){
        super(Request.Method.POST, MARK_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("ABSENT",String.valueOf(absent));
        params.put("ATTENDANCEDATE",attendanceDate);
        params.put("RESETVALUE",String.valueOf(rv));
        params.put("ID",stringid);
        params.put("username",usern);


    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}

