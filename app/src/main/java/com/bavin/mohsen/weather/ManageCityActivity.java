package com.bavin.mohsen.weather;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ManageCityActivity extends AppCompatActivity {

    boolean isMatch=false;
    String search="search";
    String delete="delete";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_manage_city );

        Intent intent=getIntent();
        String mode=intent.getStringExtra( "mode" );
        int po=intent.getIntExtra( "mode2",-1 );
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fTransaction = fragmentManager.beginTransaction();
        if ((isMatch = search.equals(mode))==true) {

            fTransaction.replace( R.id.fragSearch, new SearchCityFragment() );
        }
        else if((isMatch = delete.equals(mode))==true){
            fTransaction.replace( R.id.fragSearch, DleleteCityFragment.newdeleteCityFragment( po ));

        }
        fTransaction.commit();
    }
}
