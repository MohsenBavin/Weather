package com.bavin.mohsen.weather;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class DleleteCityFragment extends Fragment {
    int cityPos;

    TextView textCityDl;
    Button buttonYes,buttonNo;
    public static DleleteCityFragment newdeleteCityFragment(int position) {
        DleleteCityFragment deleteCityFragmentIntc=new DleleteCityFragment();
        Bundle args=new Bundle();
        args.putInt("psition",position);
        deleteCityFragmentIntc.setArguments(args);
        return deleteCityFragmentIntc;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityPos=getArguments().getInt("psition");
        //if(cityName==null) cityName="Tehran";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_dlelete_city, container, false );
        textCityDl=view.findViewById( R.id.txtcitydl );
        buttonYes=view.findViewById( R.id.btnYesDelete );
        buttonNo=view.findViewById( R.id.btnNoDelete );


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        List<String> cityNames= new ArrayList<>(  );
        SQLcity db = new SQLcity(getContext(), "city_db", null, 1);
        cityNames = db.getCityArray();
        textCityDl.setText( cityNames.get( cityPos ));

        buttonYes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> cityNames= new ArrayList<>(  );
                SQLcity db = new SQLcity(getContext(), "city_db", null, 1);
                cityNames = db.getCityArray();
                db.deleteCity( cityNames.get( cityPos ) );
               // startActivity( new Intent( getActivity(),MainActivity.class ) );
                getActivity().finish();

            }
        } );

        buttonNo.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity( new Intent( getActivity(),MainActivity.class ) );
                getActivity().finish();

            }
        } );


    }
}
