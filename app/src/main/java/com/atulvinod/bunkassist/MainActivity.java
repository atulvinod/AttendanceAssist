package com.atulvinod.bunkassist;


import android.app.DialogFragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,BunkPredict.OnFragmentInteractionListener,Home.OnFragmentInteractionListener,SettingsFragment.OnFragmentInteractionListener,PredictAttendance.OnFragmentInteractionListener {

    public static final String MONDAY_KEY = "mon";
    public static final String TUE_KEY = "tue";
    public static final String WED_KEY = "wed";
    public static final String THU_KEY = "thu";
    public static final String FRI_KEY = "fri";
    public static final String CONFIG = "config";
    int back=1;
    Class setting,predict,home,bunk;
    Fragment settingFragment,predictFragment,homeFragment,bunkPredict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences pref = getSharedPreferences(CONFIG, 0);
        SharedPreferences.Editor prefEdit = pref.edit();

        boolean init = pref.getBoolean("FirstTime",true);
         setting = SettingsFragment.class;
         predict = PredictAttendance.class;
         bunk = BunkPredict.class;
         home = Home.class;
        try {
            settingFragment= (Fragment) setting.newInstance();
            predictFragment = (Fragment)predict.newInstance();
            homeFragment = (Fragment) home.newInstance();
            bunkPredict = (Fragment) bunk.newInstance();

        } catch (Exception e) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
        }





        if(init == true){
            prefEdit.putBoolean("FirstTime",false);
            prefEdit.commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,settingFragment).commit();
            }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();






        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
          if(back%2==0){
              super.onBackPressed();
          }else{
              back++;
              Toast.makeText(this,"Press back again to exit",Toast.LENGTH_SHORT).show();
          }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Class setting = SettingsFragment.class;
            Fragment f = null;
            try{
                f = (Fragment)setting.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,f).commit();
            }catch(Exception e){
                e.printStackTrace();
            }
            getSupportActionBar().setTitle("AttendanceAssist Configuration");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.configureApp:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,settingFragment).commit();
                getSupportActionBar().setTitle("AttendanceAssist Configuration");
                break;
            case R.id.predictFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,predictFragment).commit();
                getSupportActionBar().setTitle("Predict Attendance");
                break;
            case R.id.bunkWise:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame,bunkPredict).commit();
                getSupportActionBar().setTitle("Bunkwise predict Attendance");
                break;
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}



