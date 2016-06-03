package com.rxandroidpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

/**/

public class MapActivity extends AppCompatActivity {


    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        display = (TextView) findViewById(R.id.textview);


        Single.just(4).map(number-> String.valueOf(number)).subscribe(new SingleSubscriber<String>() {

            @Override
            public void onSuccess(String value) {
                display.setText(value);
            }

            @Override
            public void onError(Throwable error) {
            }
        });

    }
}
