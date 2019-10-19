package com.example.yexin.menu6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class twoFragment extends Fragment {


    public twoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    public static twoFragment newInstance() {

        Bundle args = new Bundle();

        twoFragment fragment = new twoFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
