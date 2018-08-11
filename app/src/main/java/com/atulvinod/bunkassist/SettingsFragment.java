package com.atulvinod.bunkassist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences pref;
    private SharedPreferences.Editor prefEdit;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Class home = Home.class;

        // Inflate the layout for this fragment
        Log.d("frag","Added Fragment");
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        pref = getActivity().getSharedPreferences(MainActivity.CONFIG,0);
        prefEdit = pref.edit();

        Button b = (Button)view.findViewById(R.id.saveButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Settings saved",Toast.LENGTH_SHORT).show();
                SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.CONFIG,0);

                if(pref.getString(MainActivity.START,"start")=="start"){
                    Fragment homeFrag = null;
                    try{
                        homeFrag = (Fragment)home.newInstance();
                    }catch(Exception e){

                    }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame,homeFrag).commit();
                }
            }
        });

        Spinner mon = (Spinner) view.findViewById(R.id.mon_val);
        Spinner tue = (Spinner) view.findViewById(R.id.tue_val);
        Spinner wed = (Spinner) view.findViewById(R.id.wed_val);
        Spinner thu = (Spinner) view.findViewById(R.id.thurs_val);
        Spinner fri = (Spinner) view.findViewById(R.id.fri_val);

        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(getActivity().getApplicationContext(),R.array.classVal,R.layout.spinner_item) ;
        ArrayAdapter<CharSequence> adap = new ArrayAdapter<>(getActivity().getApplicationContext(),R.layout.spinner_item);
        adap.insert("1",0);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        mon.setAdapter(adapter);
        tue.setAdapter(adapter);
        wed.setAdapter(adapter);
        thu.setAdapter(adapter);
        fri.setAdapter(adapter);

        int mondayVal = pref.getInt(MainActivity.MONDAY_KEY,1);
        int tueVal = pref.getInt(MainActivity.TUE_KEY,1);
        int wedVal = pref.getInt(MainActivity.WED_KEY,1);
        int thuVal = pref.getInt(MainActivity.THU_KEY,1);
        int friVal = pref.getInt(MainActivity.FRI_KEY,1);

        mon.setSelection(mondayVal-1);
        tue.setSelection(tueVal-1);
        wed.setSelection(wedVal-1);
        thu.setSelection(thuVal-1);
        fri.setSelection(friVal-1);



        mon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int value = Integer.parseInt((String)parent.getItemAtPosition(position));
                prefEdit.putInt(MainActivity.MONDAY_KEY,value);
                prefEdit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int value = Integer.parseInt((String)parent.getItemAtPosition(position));
                prefEdit.putInt(MainActivity.TUE_KEY,value);
                prefEdit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        wed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int value = Integer.parseInt((String)parent.getItemAtPosition(position));
                prefEdit.putInt(MainActivity.WED_KEY,value);
                prefEdit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        thu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int value = Integer.parseInt((String)parent.getItemAtPosition(position));
                prefEdit.putInt(MainActivity.THU_KEY,value);
                prefEdit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        fri.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int value = Integer.parseInt((String)parent.getItemAtPosition(position));
                prefEdit.putInt(MainActivity.FRI_KEY,value);
                prefEdit.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    class Adapter implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
