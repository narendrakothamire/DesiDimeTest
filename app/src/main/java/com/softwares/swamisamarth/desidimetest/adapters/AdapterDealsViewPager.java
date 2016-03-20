package com.softwares.swamisamarth.desidimetest.adapters;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.softwares.swamisamarth.desidimetest.constants.Constants;
import com.softwares.swamisamarth.desidimetest.fragments.FragmentDeals;


/**
 * Created by narendra on 10/8/15.
 */
public class AdapterDealsViewPager extends FragmentStatePagerAdapter {

    private String[] dealsCat = {"TOP", "POPULAR", "FEATURED"};

    public AdapterDealsViewPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return dealsCat[position];
    }

    @Override
    public Fragment getItem(int position) {

        FragmentDeals fragmentDeals = new FragmentDeals();
        Bundle args = new Bundle();
        args.putString(Constants.ARGS_DEALS_CAT, dealsCat[position]);
        fragmentDeals.setArguments(args);
        return fragmentDeals;
    }

    @Override
    public int getCount() {
        return dealsCat.length;
    }
}
