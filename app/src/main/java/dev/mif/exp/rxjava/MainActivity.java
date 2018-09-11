package dev.mif.exp.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    /**
     * Basic Observable, Observer, Subscriber example
     * Observable emits list of animal names
     */

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // #nb Oservable: penyedia data
        Observable<String> animaslsObservable = getAnimalsObservable();

        // #nb Observer: penerima data
        Observer<String> animalsObserver = getAnimalsObserver();

        animaslsObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);
                // #nb observeOn(AndroidSchedulers.mainThread()): This tells the Observer to receive the data on android UI thread so that you can take any UI related actions.
                // #nb subscribeOn(Schedulers.io()): This tell the Observable to run the task on a background thread.

    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }

    private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {

            //onSubscribe(): Method will be called when an Observer subscribes to Observable.
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            //onNext(): This method will be called when Observable starts emitting the data.
            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext - Name: " + s);
            }

            //onError(): In case of any error, onError() method will be called.
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            //onComplete(): When an Observable completes the emission of all the items, onComplete() will be called.
            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete - All items are emitted!");
            }
        };
    }
}
