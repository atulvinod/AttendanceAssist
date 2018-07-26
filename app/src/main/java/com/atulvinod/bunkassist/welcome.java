package com.atulvinod.bunkassist;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcome extends AppCompatActivity implements SettingsFragment.OnFragmentInteractionListener{

    Fragment frag = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button b = (Button) findViewById(R.id.button);
        Class setting = SettingsFragment.class;

        try{
            frag = (Fragment)setting.newInstance();

        }catch(Exception e){

        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
