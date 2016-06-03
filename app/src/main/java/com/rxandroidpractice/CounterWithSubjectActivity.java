package com.rxandroidpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import rx.Observer;
import rx.subjects.PublishSubject;

public class CounterWithSubjectActivity extends AppCompatActivity {

 /*Example of a counter on RX using PublishSubject class which  works as a pipeline*/


    private TextView mCounterDisplay;
    private Button mIncrementButton;
    private int mCounter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);



        mCounterDisplay  = (TextView) findViewById(R.id.counter_display);
        mIncrementButton = (Button) findViewById(R.id.bt_counter);


       final PublishSubject mCounterEmitter = PublishSubject.create();

        mCounterEmitter.subscribe(new Observer<Integer>(){

            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onNext(Integer integer) {
                mCounterDisplay.setText(String.valueOf(integer));
            }
        });




        mIncrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCounter ++;
                mCounterEmitter.onNext(mCounter);
            }
        });

    }
}
