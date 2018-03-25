package com.gmail.nikcody92.android.rulerfactory.touchdispatcher.observer;

public interface TouchObserver {
    boolean isTouched();
    void handleDown(boolean obsTouched);
    void doScroll(int distX, int distY);
    void doLongPress(int x, int y);
    void doFling(float velX, float velY);

    void doScale(float factor);
}
