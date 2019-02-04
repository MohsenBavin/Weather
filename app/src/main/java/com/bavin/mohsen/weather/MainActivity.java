package com.bavin.mohsen.weather;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;


   // RelativeLayout relText;
    ViewPager viewPager;
    String cityName="Tehran";
    //TextView textTab;
    ViewTabAdapter pagerAdapter;
    List<String> cityNamesArray= new ArrayList<>(  );
    List<String> cityLonsArray= new ArrayList<>(  );
    List<String> cityLatsArray= new ArrayList<>(  );



    boolean isMatch=false;
    TextView textStatus2;
    Switch switchButton2;

    String mode="not";
    private static final int PERMISSION_REQ_CODE = 1234;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        //set toolbar
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textStatus2=findViewById( R.id.switchStatus );
        switchButton2=findViewById( R.id.btnSwitch );
        switchManager();
        String stateSwitch =
                PreferenceManager.getDefaultSharedPreferences(this)
                        .getString("location","not") ;
        switch (stateSwitch){
            case "not":
                switchButton2.setChecked(true);
                PreferenceManager.getDefaultSharedPreferences( MainActivity.this ).edit().putString( "location", "enable" ).apply();
                checkPermissions();
                break;
            case "enable":
                switchButton2.setChecked(true);
                checkPermissions();
                break;
            case "disable":
                switchButton2.setChecked(false);
                break;
        }
        switchButton2.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textStatus2.setText( "Current location shown" );
                    PreferenceManager.getDefaultSharedPreferences( MainActivity.this ).edit().putString( "location", "enable" ).apply();
                    checkPermissions();
                    setViewPager();
                    //Toast.makeText( MainActivity.this,"Location Enable",Toast.LENGTH_LONG ).show();
                    Toasty.info(MainActivity.this, "Current location shown", Toast.LENGTH_SHORT, true).show();

                }else {
                    textStatus2.setText( "Current location disregarded" );
                    PreferenceManager.getDefaultSharedPreferences( MainActivity.this).edit().putString( "location", "disable" ).apply();
                    Toasty.info(MainActivity.this, "Disable to find your current position", Toast.LENGTH_SHORT, true).show();

                    // Toast.makeText( MainActivity.this,"Location disable",Toast.LENGTH_LONG ).show();
                    setViewPager();
                }

            }
        } );



        //Drawer
        drawerLayout=findViewById( R.id.drawerId );

        DrawerFragment drawerFragment = (DrawerFragment) getSupportFragmentManager().
                findFragmentById(R.id.drawer);

        drawerFragment.install((DrawerLayout)findViewById(R.id.drawerId),toolbar);

        setViewPager();

        checkPermissions();




    }


    @Override
    protected void onStart() {
        super.onStart();
        //checkPermissions();
        setViewPager();


    }

    @Override
    protected void onResume() {
        super.onResume();
        int getRun =
                PreferenceManager.getDefaultSharedPreferences(this).getInt("firstRun",-1) ;
        if (getRun==-1){
            Intent intentStart=new Intent( MainActivity.this,ManageCityActivity.class );
            intentStart.putExtra( "mode","search" );
            startActivity( intentStart );
        }

    }

    @Override
    public void onBackPressed() {


        if (drawerLayout.isDrawerOpen( GravityCompat.START )){
            drawerLayout.closeDrawer( GravityCompat.START );
        }
        else{
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        }
    }
   public void switchManager(){
       String stateSwitch =
               PreferenceManager.getDefaultSharedPreferences(this)
                       .getString("location","not") ;
     switch (stateSwitch){
        case "not":
            switchButton2.setChecked(true);
            PreferenceManager.getDefaultSharedPreferences( MainActivity.this ).edit().putString( "location", "enable" ).apply();
            checkPermissions();
            break;
        case "enable":
            switchButton2.setChecked(true);
            checkPermissions();
            break;
        case "disable":
            switchButton2.setChecked(false);
            break;
    }
   }

    private void setViewPager(){
        boolean bTrue=true;
        List<Fragment> viewFragments=new ArrayList<>(  );
      String enLocation=PreferenceManager.getDefaultSharedPreferences(this).getString("location","disable") ;
        isMatch=enLocation.equals("enable");

        viewFragments.add( setSelectCity() );
        if(isMatch==bTrue)viewFragments.add( setCurrentCity());
        pagerAdapter=new ViewTabAdapter( getSupportFragmentManager(),viewFragments );
        viewPager.setAdapter( pagerAdapter );

    }

    private Fragment setCurrentCity(){
        Fragment fragmentCurrent;
        GpsTracker gpstracker =  new GpsTracker(this);
            double lat = gpstracker.getLatitude();
            double lon = gpstracker.getLongitude();
            String latSt=""+lat;
            String lonSt=""+lon;
            fragmentCurrent=NowFragment.newNowFragment(lonSt,latSt,1);
        return fragmentCurrent;
    }


   private Fragment setSelectCity(){
       Fragment fragmentSelect;
       Intent intent=getIntent();
       int po=intent.getIntExtra( "mode2",-1 );

       if (po != -1) {
           PreferenceManager.getDefaultSharedPreferences( this ).edit().putInt( "position",po ).apply();
           SQLcity db = new SQLcity( this, "city_db", null, 1 );
           cityNamesArray = db.getCityArray();
           cityName = cityNamesArray.get( po );

           cityLonsArray=db.getLonCityArray();
           String lon=cityLonsArray.get( po );

           cityLatsArray=db.getLatCityArray();
           String lat=cityLatsArray.get( po );

           PreferenceManager.getDefaultSharedPreferences( this ).edit().putString( "cityLonPref",lon ).apply();
           PreferenceManager.getDefaultSharedPreferences( this ).edit().putString( "cityLatPref",lat ).apply();



           fragmentSelect=NowFragment.newNowFragment(lon,lat,0);
       }
       else{

           String getLonCityPref =
                   PreferenceManager.getDefaultSharedPreferences(this).getString("cityLonPref","0") ;

           String getLatCityPref =
                   PreferenceManager.getDefaultSharedPreferences(this).getString("cityLatPref","0") ;

           fragmentSelect=NowFragment.newNowFragment(getLonCityPref,getLatCityPref,0);


           }


       return fragmentSelect;
   }


    private void checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQ_CODE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_REQ_CODE){
            try {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                    Toasty.info( MainActivity.this, "Permission granted", Toast.LENGTH_SHORT, true ).show();


                    PreferenceManager.getDefaultSharedPreferences( MainActivity.this ).edit().putString( "location", "enable" ).apply();

                    setViewPager();


                } else {
                    //Toast.makeText(this, "Permission denied to access location", Toast.LENGTH_SHORT).show();
                    Toasty.info( MainActivity.this, "Permission denied to access location", Toast.LENGTH_SHORT, true ).show();

                    PreferenceManager.getDefaultSharedPreferences( MainActivity.this ).edit().putString( "location", "disable" ).apply();

                    switchManager();
                    setViewPager();

                }
            }
            catch (Exception e){
            }

            }
        }

    }

