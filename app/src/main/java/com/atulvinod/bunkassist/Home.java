package com.atulvinod.bunkassist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



import smartdevelop.ir.eram.showcaseviewlib.GuideView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Fragment preFrag = null;
    Fragment setFrag = null;
    Fragment bunkFrag =null;
    Fragment aboutFragment = null;


    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
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
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        Class predict = PredictAttendance.class;
        Class setting = SettingsFragment.class;
        Class bunk = BunkPredict.class;
        Class about = AboutFragment.class;

        try {
            preFrag = (Fragment) predict.newInstance();
            setFrag = (Fragment) setting.newInstance();
            bunkFrag = (Fragment) bunk.newInstance();
            aboutFragment = (Fragment) about.newInstance();

        } catch (Exception e) {

        }
        Button predictAction = (Button) v.findViewById(R.id.homePredict);
        predictAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, preFrag).commit();
            }
        });
        Button settings = (Button) v.findViewById(R.id.config);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, setFrag).commit();
            }
        });
        Button bunkwise = (Button) v.findViewById(R.id.bunkwisePredict);
        bunkwise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, bunkFrag).commit();
            }
        });

        Button aboutButton = (Button) v.findViewById(R.id.about);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame, aboutFragment).commit();

            }
        });
        SharedPreferences pref = getActivity().getSharedPreferences(MainActivity.CONFIG, 0);
        SharedPreferences.Editor prefEdit = pref.edit();


        if (pref.getBoolean("Target", true) == true) {


            prefEdit.putBoolean("Target", false);
            prefEdit.commit();
            showTargets(v);



        }
        return v;
    }
    public void showTargets(View v){
        final View x = v;
        new GuideView.Builder(getContext()).setGuideListener(new GuideView.GuideListener() { //Guide for Predict attendance
            @Override
            public void onDismiss(View view) {
                new GuideView.Builder(getContext()).setGuideListener(new GuideView.GuideListener() { //Guide for config app
                    @Override
                    public void onDismiss(View view) {
                        new GuideView.Builder(getContext()).setGuideListener(new GuideView.GuideListener() { //GuideView for bunkPredict
                            @Override
                            public void onDismiss(View view) { // GuideView for About
                                new GuideView.Builder(getContext()).setGuideListener(new GuideView.GuideListener() {
                                    @Override
                                    public void onDismiss(View view) {
                                        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

                                        drawer.openDrawer(Gravity.START);
                                    }
                                }).setTitle("Attendance Assist").setTargetView(x.findViewById(R.id.about)).setContentText("Finally, Welcome to Attendance Assist, for additional info").setDismissType(GuideView.DismissType.anywhere).setGravity(GuideView.Gravity.auto).build().show();
                            }
                        }).setContentText("Predict attendance based on how may classes you'll bunk").setDismissType(GuideView.DismissType.anywhere).setGravity(GuideView.Gravity.auto).setTargetView(x.findViewById(R.id.bunkwisePredict)).build().show();
                    }
                }).setTargetView(x.findViewById(R.id.config)).setContentText("Configure App from here").setGravity(GuideView.Gravity.auto).setDismissType(GuideView.DismissType.anywhere).build().show();
            }
        }).setContentText("Use this when you want to calculate you attendance based upon current one").setGravity(GuideView.Gravity.auto).setTargetView(x.findViewById(R.id.homePredict)).setDismissType(GuideView.DismissType.anywhere).build().show();



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
}
