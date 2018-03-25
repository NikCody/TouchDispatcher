package com.gmail.nikcody92.android.rulerfactory.touchdispatcher.observer;

public interface TouchObservable {
    void registerObserver(TouchObserver obs);
    void unregisterObserver(TouchObserver obs);
    void notifyDown(int x, int y);
    void notifyScroll(int distanceX, int distanceY);
    void notifyLongPress(int x, int y);
    void notifyFling(float velX, float velY);

    void notifyScale(float factor);
}
