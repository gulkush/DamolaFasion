package com.pathwaymedia.valisimofashions.people_mvvm.adapter;

import com.jakewharton.rxrelay.PublishRelay;
import com.jakewharton.rxrelay.Relay;

import rx.Observable;

/**
 * Created by gulkush on 10/14/2016.
 */

public class RxBus {

    private static RxBus _rxBus = null;

    // This is better done with a DI Library like Dagger
    public static RxBus getSingleton() {
        if (_rxBus == null) {
            _rxBus = new RxBus();
        }

        return _rxBus;
    }


    private final Relay<Object, Object> _bus = PublishRelay.create().toSerialized();

    public void send(Object o) {
        _bus.call(o);
    }

    public Observable<Object> asObservable() {
        return _bus.asObservable();
    }

    public boolean hasObservers() {
        return _bus.hasObservers();
    }
}
