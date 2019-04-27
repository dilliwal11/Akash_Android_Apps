package com.dilliwal.akash.MarkKar;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 30/3/17.
 */
public class AddServiceRequest extends StringRequest {
    private static final String FIRSTADD_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/firstAdd.php";
   // private static final String MARK_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/mark.php";
    private Map<String,String> params;
    public AddServiceRequest(String usern,String name, String work, int date, String salary, Response.Listener<String> listener) {
        super(Method.POST,FIRSTADD_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("NAME",name);
        params.put("WORK",work);
        params.put("DATE", String.valueOf(date));
        params.put("SALARY",salary);
        params.put("USERNAME",usern);

    }




    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
