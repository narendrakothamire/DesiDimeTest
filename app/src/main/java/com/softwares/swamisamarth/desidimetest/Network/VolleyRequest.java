package com.softwares.swamisamarth.desidimetest.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by narendra on 10/9/15.
 */
public class VolleyRequest extends JsonArrayRequest {

    private static final int DEFAULT_MAX_RETRIES = 0;
    private static final float DEFAULT_BACKOFF_MULT = 1f;
    private static final int DEFAULT_TIME_OUT = (int) TimeUnit.SECONDS.toMillis(20);
    private HashMap<String, String> params;

    public VolleyRequest(int method, String url, HashMap<String, String> params, JSONObject requestBody, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(this.DEFAULT_TIME_OUT, this.DEFAULT_MAX_RETRIES, this.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        headers.put("Accept", "text/javascript");
        headers.put("X-Desidime-Client", "0c50c23d1ac0ec18eedee20ea0cdce91ea68a20e9503b2ad77f44dab982034b0");
        return headers;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("per_page", String.valueOf(10));
        params.put("page", String.valueOf(2));
        return params;
    }
}
