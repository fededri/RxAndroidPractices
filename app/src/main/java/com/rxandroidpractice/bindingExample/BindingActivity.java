package com.rxandroidpractice.bindingExample;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.rxandroidpractice.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/*Example of multiple EditText Validations combining several Observables which emits Boolean values whether or not a field was correctly completed*/

public class BindingActivity extends RxAppCompatActivity {


    private TextView tvName, tvEmail, tvPassword;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);

        initViews();


        rx.Observable<Boolean> nameStream = RxTextView.textChanges(tvName).subscribeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .compose(bindToLifecycle())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        Boolean flag = charSequence.length() > 4;
                        if (flag) Log.i("Name validation", "true");
                        else Log.i("Name validation", "false");
                        return flag;
                    }
                })
               /* .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) Log.i("Binding", "true");
                        else Log.i("Binding", "false");
                    }
                })*/;


        rx.Observable<Boolean> mailStream = RxTextView.textChanges(tvEmail).subscribeOn(AndroidSchedulers.mainThread())
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.newThread())
                .compose(bindToLifecycle())
                .map(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        Boolean aBoolean = Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
                        if (aBoolean) Log.i("Mail validation", "true");
                        else Log.i("Mail validation", "false");
                        return aBoolean;
                    }
                })
               /* .subscribe(new Action1<Boolean>() {
                               @Override
                               public void call(Boolean aBoolean) {
                                   if (aBoolean) Log.i("Binding Mail", "true");
                                   else Log.i("Binding Mail", "false");
                               }
                           }
                )*/;

       /* rx.Observable.zip(nameStream, mailStream, new Func2<Boolean, Boolean, Boolean>() {

            public Boolean call(Boolean o1, Boolean o2) {
                return o1 && o2;
            }
        }).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            btRegister.setEnabled(true);
                        }
                    }
                });*/

        Observable.combineLatest(nameStream, mailStream, new Func2<Boolean, Boolean, Boolean>() {

            public Boolean call(Boolean o1, Boolean o2) {
                return o1 && o2;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                if (aBoolean) {
                    btRegister.setEnabled(true);
                }
            }
        });


    }


    private void initViews() {
        tvName = (TextView) findViewById(R.id.nombre);
        tvEmail = (TextView) findViewById(R.id.email);
        tvPassword = (TextView) findViewById(R.id.password);
        btRegister = (Button) findViewById(R.id.bt_register);


        btRegister.setEnabled(false);
    }
}
