package com.gmail.nikcody92.android.rulerfactory.touchdispatcher;


import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.gmail.nikcody92.android.rulerfactory.touchdispatcher.observer.TouchObservable;
import com.gmail.nikcody92.android.rulerfactory.touchdispatcher.observer.TouchObserver;

import java.util.ArrayList;

public class TouchDispatcher implements View.OnTouchListener, TouchObservable {
    private ArrayList<TouchObserver> observers;
    private GestureDetector detector;
    private ScaleGestureDetector scaleDetector;

    public TouchDispatcher(Context context) {
        this.observers = new ArrayList<>();
        this.detector = new GestureDetector(context, new TouchGestureListener());
        this.scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public void registerObserver(TouchObserver obs) {
        observers.add(obs);
    }

    @Override
    public void unregisterObserver(TouchObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyDown(int x, int y) {
        boolean touched = false;
        for (TouchObserver obs: observers) {
            obs.handleDown(touched);
            touched = touched || obs.isTouched();
        }
    }

    @Override
    public void notifyScroll(int distanceX, int distanceY) {
        for (TouchObserver obs: observers)
            obs.doScroll(distanceX, distanceY);
    }

    @Override
    public void notifyLongPress(int x, int y) {
        for (TouchObserver obs: observers)
            obs.doLongPress(x, y);
    }

    @Override
    public void notifyFling(float velX, float velY) {
        for (TouchObserver obs: observers)
            obs.doFling(velX, velY);
    }

    @Override
    public void notifyScale(float factor) {
        for (TouchObserver obs : observers)
            obs.doScale(factor);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean touch;
        touch = scaleDetector.onTouchEvent(event) && scaleDetector.isInProgress();
        touch = touch || detector.onTouchEvent(event);
        return touch;
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            notifyScale(detector.getScaleFactor());
            return true;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

        }
    }

    private class TouchGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            notifyDown(Math.round(e.getX()), Math.round(e.getY()));
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            notifyScroll(Math.round(distanceX), Math.round(distanceY));
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            notifyLongPress(Math.round(e.getX()), Math.round(e.getY()));
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            notifyFling(velocityX, velocityY);
            return true;
        }

    }
}
