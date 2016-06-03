package com.rxandroidpractice.lyfeCycleExample;

import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import com.rxandroidpractice.R;
import com.rxandroidpractice.connections.Pathology;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.List;


/*
* This Example shows how to get a request called before a Context change using RetainedFragment Pattern and and Observable object which emits the response from the API using Retrofit
* This avoid excess of requests made by -for example- orientation changes
*Notice the fact that Activity extends RxAppCompatActivity  which handles leak problems
*
* */



public class LifeCycleActivity extends RxAppCompatActivity implements Retainedfragment.CallBacks {


    private Retainedfragment retainedfragment;
    private String FRAGMENT_TAG = "tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_subject);


       FragmentManager fm = getFragmentManager();

        retainedfragment = (Retainedfragment) fm.findFragmentByTag(FRAGMENT_TAG);

        if(retainedfragment == null){
            retainedfragment = Retainedfragment.newInstance("asd","asd");
            fm.beginTransaction().add(retainedfragment,FRAGMENT_TAG).commit();
        } else{
            retainedfragment.getData();
        }




    }


    @Override
    public void onResponseObtained(List<Pathology> pathologies){
        Log.i("Lifecycle","obtained list of pathologies");
        //retainedfragment.getData();
    }



}
