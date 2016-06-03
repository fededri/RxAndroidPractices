package com.rxandroidpractice.lyfeCycleExample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rxandroidpractice.R;
import com.rxandroidpractice.connections.ApiClient;
import com.rxandroidpractice.connections.Pathology;
import com.trello.rxlifecycle.components.RxFragment;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Retainedfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Retainedfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Retainedfragment extends RxFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Observable<List<Pathology>> pathologiesStream;
    CallBacks callBacks;



    public Retainedfragment() {
        // Required empty public constructor
    }


    public static Retainedfragment newInstance(String param1, String param2) {
        Retainedfragment fragment = new Retainedfragment();
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
        setRetainInstance(true);


           callBacks = (CallBacks) getActivity();


        pathologiesStream = ApiClient.getServiceClient().getPathologiesRx();

        pathologiesStream.compose(bindToLifecycle()).observeOn(Schedulers.newThread())
                        .delay(2000, TimeUnit.MILLISECONDS)
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(callBacks::onResponseObtained);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_retainedfragment, container, false);
    }


    public interface CallBacks{
        public void onResponseObtained(List<Pathology> pathologies);
    }

    public void getData(){
        pathologiesStream = ApiClient.getServiceClient().getPathologiesRx();

        pathologiesStream.compose(bindToLifecycle()).observeOn(Schedulers.newThread())
                .delay(2000,TimeUnit.MILLISECONDS) // I set delay to see clearly that im receiving the response requested previously on activity rotation
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(callBacks::onResponseObtained);
    }


}
