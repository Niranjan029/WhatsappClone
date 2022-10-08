package com.example.whatsapplite.Adapters;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapplite.Fragments.CallingFragment;
import com.example.whatsapplite.Fragments.StatusFragment;
import com.example.whatsapplite.Fragments.chatFragment;

public class FragmentsAdapter extends FragmentPagerAdapter {

    public FragmentsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0 : return new chatFragment();
            case 1 : return new StatusFragment();
            case 2 : return new CallingFragment();
            default:return new chatFragment() ;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null ;
        if(position==0)
        {
            title = "CHATS" ;
        }
        if(position==1)
        {
            title = "STATUS" ;
        }
        if(position==2)
        {
            title = "CALLS" ;
        }
        return title ;
    }
}
