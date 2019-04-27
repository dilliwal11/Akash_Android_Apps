package com.dilliwal.akash.MarkKar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 4/5/17.
 */
public class DeleteServiceReq extends StringRequest {
    private static final String DELETE_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/delete.php";
    private Map<String,String> params;
    public DeleteServiceReq(String usern,long id, Response.Listener<String> listener){
        super(Request.Method.POST,DELETE_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("username",usern);

        params.put("ID",String.valueOf(id));



    }
    @Override
    public Map<String, String> getParams(){
        return params;
    }
}
