package com.dilliwal.akash.MarkKar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 25/4/17.
 */
public class MarkPtoAServiceReq extends StringRequest {
    private static final String MARK_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/markPtoA.php";
    private Map<String,String> params;
    public MarkPtoAServiceReq (int present,String stringid,String usern, Response.Listener<String> listener){
        super(Request.Method.POST, MARK_REQUEST_URL,listener,null);
        params = new HashMap<>();

        params.put("PRESENT",String.valueOf(present));
        params.put("ID",stringid);
        params.put("username",usern);
    }
    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
