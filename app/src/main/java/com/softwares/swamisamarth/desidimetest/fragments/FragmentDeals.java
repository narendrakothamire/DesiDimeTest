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
import com.softwares.swamisamarth.desidimetest.Models.Footer;
import com.softwares.swamisamarth.desidimetest.Models.Item;
import com.softwares.swamisamarth.desidimetest.Network.VolleyRequest;
import com.softwares.swamisamarth.desidimetest.R;
import com.softwares.swamisamarth.desidimetest.adapters.AdapterDeals;
import com.softwares.swamisamarth.desidimetest.applications.DesiDimeTestApplication;
import com.softwares.swamisamarth.desidimetest.constants.Constants;
import com.softwares.swamisamarth.desidimetest.custom.EndlessRecyclerView;
import com.softwares.swamisamarth.desidimetest.custom.PaginationRecyclerView;
import com.softwares.swamisamarth.desidimetest.utilities.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeals extends Fragment {


    private static final String TAG = FragmentDeals.class.getSimpleName();
    private EndlessRecyclerView recyclerView;
    private AdapterDeals adapter;
    private ArrayList<Item> deals = new ArrayList<>();
    private String URL = "http://rails4.desidime.com/v1/deals/top.json";
    private int currentPage = 1;
    private boolean hasMore;
    private boolean fromLoadMore;
    private LinearLayoutManager linearLayoutManager;

    public FragmentDeals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deals, container, false);

        recyclerView = (EndlessRecyclerView)view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

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


//        recyclerView.setOnLoadMoreListener(new EndlessRecyclerView.LoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                deals.add(new Footer());
//                recyclerView.getAdapter().notifyItemInserted(deals.size() - 1);
//                getDeals(URL);
//            }
//
//
//        });
        hasMore = true;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!hasFooter()) {
                    //position starts at 0
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == linearLayoutManager.getItemCount() - 2) {
                        deals.add(new Footer());
                        recyclerView.getAdapter().notifyItemInserted(deals.size() - 1);
                        fromLoadMore = true;
                        getDeals(URL);
                    }
                }
            }
        });

        if(getArguments().get(Constants.ARGS_DEALS_CAT).equals("TOP")){
            URL = "http://rails4.desidime.com/v1/deals/top.json";
        }else if(getArguments().get(Constants.ARGS_DEALS_CAT).equals("POPULAR")){
            URL = "http://rails4.desidime.com/v1/deals/popular.json";
        }else if(getArguments().get(Constants.ARGS_DEALS_CAT).equals("FEATURED")){
            URL = "http://rails4.desidime.com/v1/deals/featured.json";
        }

        getDeals(URL);

        return view;
    }

    private boolean hasFooter() {

        return deals.get(deals.size() - 1) instanceof Footer;
    }

    private void getDeals(String url) {

        HashMap<String, String> params = new HashMap<>();
        params.put("per_page", String.valueOf(10));
        params.put("page", String.valueOf(currentPage));

        String tempURL = Utility.changeURLForGetRequest(url, params);

        VolleyRequest volleyRequest = new VolleyRequest(Request.Method.GET, tempURL, null, successListener, errorListener);

        DesiDimeTestApplication.getInstance().addToRequestQueue(volleyRequest);
    }


    private Response.Listener<JSONArray> successListener = new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            ArrayList<Deal> tempDeals = new ArrayList<>();
            try {
                Gson gson = new Gson();
                for(int i=0; i<response.length(); i++){
                    Deal deal = gson.fromJson(response.get(i).toString(), Deal.class);
                    tempDeals.add(deal);
                }

                Log.d("Narendra", response.toString());
                if(fromLoadMore){

                    int size = deals.size();
                    deals.remove(size - 1);//removes footer
                    deals.addAll(tempDeals);
                    recyclerView.getAdapter().notifyItemRangeChanged(size - 1, deals.size() - size);
                    fromLoadMore = false;
                }else {
                    deals.addAll(tempDeals);
                    adapter.notifyDataSetChanged();
                }
                currentPage++;
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
