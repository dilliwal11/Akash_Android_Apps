package com.dilliwal.akash.MarkKar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 28/4/17.
 */
public class SyncOneRequest extends StringRequest {
    private static final String MARK_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/syncone.php";
    private Map<String,String> params;
    public SyncOneRequest (String name,String work,int date,int salary,String usern, Response.Listener<String> listener){
        super(Request.Method.POST, MARK_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("SALARY",String.valueOf(salary));
        params.put("NAME",name);
        params.put("DATE",String.valueOf(date));
        params.put("WORK",work);
        params.put("username",usern);


    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
