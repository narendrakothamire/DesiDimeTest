package com.softwares.swamisamarth.desidimetest.utilities;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by Narendra Kothamire on 3/22/2016.
 */
public class Utility {

    public static String changeURLForGetRequest(String url, HashMap<String, String> params) {
        url = url+"?";
        for(String key: params.keySet()) {
            url += "&"+key+"="+params.get(key);
        }
        Log.d("URL", url);
        return url;
    }
}
