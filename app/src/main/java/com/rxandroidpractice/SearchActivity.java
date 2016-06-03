package com.rxandroidpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class SearchActivity extends RxAppCompatActivity {

    Subscription mTextWatchSubscription;
    EditText mSearchInput;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        mSearchInput = (EditText) findViewById(R.id.et_search);
        linearLayout = (LinearLayout) findViewById(R.id.text_linear);

        final PublishSubject mSearchResultsSubject = PublishSubject.create();


        //debounce allows me to only emit the last value when the user didnt change the text
        //for 400 miliseconds

        final mRestClient restClient = new mRestClient();

        mTextWatchSubscription = mSearchResultsSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Func1<String, List<String>>() {

                    @Override
                    public List<String> call(String s) {
                        return restClient.searchForCity(s);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> cities) {
                        handleSearchResults(cities);
                    }
                });


        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchResultsSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }




    private void handleSearchResults(List<String> results) {


        for (String city : results) {
            TextView textView = new TextView(this);
            textView.setOnClickListener(view-> Toast.makeText(SearchActivity.this, "Click", Toast.LENGTH_SHORT).show());
            LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(params);
            textView.setText(city);
            linearLayout.addView(textView);
        }


    }


    /*this class represents  retrofit call, its funcionality doesnt matter for this case*/
    private class mRestClient {

        private List<String> cities = new ArrayList<>();

        public mRestClient() {
            cities.add("Delhi");
            cities.add("Buenos Aires");
            cities.add("Agripa");
            cities.add("Cordoba");
            cities.add("Juanez");
        }

        public List<String> searchForCity(String input) {
            List<String> results = new ArrayList<>();
            for (String city : cities) {
                if (city.contains(input)) {
                    results.add(city);
                }
            }

            return results;
        }
    }
}
