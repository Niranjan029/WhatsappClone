package com.example.whatsapplite.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsapplite.R;
import com.example.whatsapplite.databinding.FragmentStatusBinding;


public class StatusFragment extends Fragment {



    public StatusFragment() {
        // Required empty public constructor
    }

   FragmentStatusBinding binding ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStatusBinding.inflate(inflater,container,false);


        return binding.getRoot();
    }
}