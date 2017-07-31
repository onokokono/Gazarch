package com.example.marceline.gazarch.Login.ServiceRequests;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONObject;
import org.json.JSONException;
import com.squareup.picasso.Request;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    private final static String REGISTER_REQUEST_URL = "http://gazarch.herokuapp.com/sign-up/";
    private Map<String, String> params = new HashMap<>();
    private JSONObject jsonObject = new JSONObject();
    private JSONObject js = new JSONObject();
    final String mRequestBody = js.toString();

    public RegisterRequest(String username, String eMail, String first_name, String last_name, String password, String password_repeat,
                           String gender, String birth_date,String phone_number,String picture , Response.Listener<String> responseListener , Response.ErrorListener errorListener) throws JSONException {
        super(Method.POST ,REGISTER_REQUEST_URL, responseListener, errorListener);

        jsonObject.put("username",username);
        jsonObject.put("email",eMail);
        jsonObject.put("first_name",first_name);
        jsonObject.put("last_name",last_name);
        jsonObject.put("password",password);
        jsonObject.put("password_repeat",password_repeat);
        jsonObject.put("gender",gender);
        jsonObject.put("birth_date",birth_date);
        jsonObject.put("phone_number",phone_number);
        jsonObject.put("picture",picture);

        js.put("Data",jsonObject.toString());

    }

    @Override
    public String getBodyContentType() {
        return "application/json; charset=utf-8";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json; charset=utf-8");
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
            return null;
        }
    }
}
