package com.bavin.mohsen.weather;


import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DrawerFragment extends Fragment {

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;
    RecyclerView recyclerViewCountry;
    CardView cardView;
    Switch switchButton;
    TextView textStatus;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_drawer, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated( view, savedInstanceState );
        cardView=view.findViewById( R.id.btnAdd );
        switchButton=view.findViewById( R.id.btnSwitch );
        textStatus=view.findViewById( R.id.switchStatus );
        recyclerViewCountry=view.findViewById(R.id.recycleCityDraw);

        cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),ManageCityActivity.class  );
                intent.putExtra( "mode","search" );
                startActivity( intent);
            }
        } );
        /*

        switchButton.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textStatus.setText( "Enable to find your current position" );
                    PreferenceManager.getDefaultSharedPreferences( getContext() ).edit().putString( "Location", "enable" ).apply();

                }else {
                    textStatus.setText( "Disable to find your current position" );
                    PreferenceManager.getDefaultSharedPreferences( getContext() ).edit().putString( "Location", "disable" ).apply();
                }

            }
        } );

*/

    }

    @Override
    public void onResume() {
        super.onResume();
        List<String> cityNames= new ArrayList<>(  );
        SQLcity db = new SQLcity(getContext(), "city_db", null, 1);
        cityNames = db.getCityArray();
        CityListRecyclerAdapter adapter =new CityListRecyclerAdapter(cityNames);
        recyclerViewCountry.setAdapter(adapter);
        recyclerViewCountry.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

    }

    public void install(DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerLayout=drawerLayout;
        mActionBarDrawerToggle=new ActionBarDrawerToggle
                (getActivity(),drawerLayout,toolbar,
                     R.string.open,R.string.close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                }

        };


        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

    }



}
