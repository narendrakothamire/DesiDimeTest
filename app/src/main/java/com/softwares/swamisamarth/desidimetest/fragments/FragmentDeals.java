package com.softwares.swamisamarth.desidimetest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.softwares.swamisamarth.desidimetest.Models.Deal;
import com.softwares.swamisamarth.desidimetest.Network.VolleyRequest;
import com.softwares.swamisamarth.desidimetest.R;
import com.softwares.swamisamarth.desidimetest.adapters.AdapterDeals;
import com.softwares.swamisamarth.desidimetest.applications.DesiDimeTestApplication;
import com.softwares.swamisamarth.desidimetest.constants.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeals extends Fragment {


    private static final String TAG = FragmentDeals.class.getSimpleName();
    private RecyclerView recyclerView;
    private AdapterDeals adapter;
    private ArrayList<Deal> deals = new ArrayList<>();
    private String URL = "http://rails4.desidime.com/v1/deals/featured.json";
    private int currentPage = 1;

    public FragmentDeals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AdapterDeals(getActivity(), deals);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterDeals.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
//                Intent intent = new Intent(getActivity(), ActivityDetails.class);
//                intent.putExtra(Constants.EXTRA_MOVIE_DATA, movies.get(position));
//                startActivity(intent);
            }
        });

        if(getArguments().get(Constants.ARGS_DEALS_CAT).equals("TOP")){
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("per_page", 10);
                jsonObject.put("page", currentPage);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, URL, jsonObject, successListener, errorListener);
            DesiDimeTestApplication.getInstance().addToRequestQueue(volleyRequest);
        }
        return view;
    }

    private Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {

            try {
                Gson gson = new Gson();
                for(int i=0; i<response.length(); i++){
                    Deal deal = gson.fromJson(response.get(i).toString(), Deal.class);
                    deals.add(deal);
                }
                Log.d("Narendra", response.toString());
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, response.toString());
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, error.getMessage());
        }
    };


}
