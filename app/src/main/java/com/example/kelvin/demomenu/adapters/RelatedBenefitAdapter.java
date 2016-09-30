package com.example.kelvin.demomenu.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by HP on 05/09/2016.
 */
public class RelatedBenefitAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public RelatedBenefitAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public float getPageWidth(int position) {

        if(getCount() == 1)
            return 1f;
        else
            return 0.7f;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
