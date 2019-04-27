package com.dilliwal.akash.MarkKar;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by akash on 3/4/17.
 */
public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = "https://akashdilliwal.000webhostapp.com/loginmarkkar.php";
    private Map<String,String> params;
    public LoginRequest(String username, String password, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL, listener,null);

        params = new HashMap<>();
        params.put("username",username);
        params.put("password",password);


    }

    @Override
    public Map<String,String> getParams(){
        return params;
    }
}

