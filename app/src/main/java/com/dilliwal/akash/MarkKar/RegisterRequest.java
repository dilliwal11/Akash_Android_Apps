package com.dilliwal.akash.MarkKar;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 30/3/17.
 */
public class RegisterRequest extends StringRequest
{
    private static final String REGISTER_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/Registermarkkar.php";
    private Map<String,String> params;
    public RegisterRequest(String username,String pass, Response.Listener<String> listener)
    {
        super(Method.POST,REGISTER_REQUEST_URL,listener,null);
        params = new HashMap<>();
        params.put("USERNAME",username);
        params.put("PASSWORD",pass);

    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}
