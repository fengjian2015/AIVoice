package com.translation.androidlib.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者管理类
 * Created by darren on 2017/1/13
 */

public class EventManager {

    private static EventManager manager;
    private List<OnEventMsgListener> listenerList;

    private EventManager() {
        listenerList = new ArrayList<>();
    }

    public static EventManager getInstance() {
        if (manager == null) {
            synchronized (EventManager.class) {
                if (manager == null) {
                    manager = new EventManager();
                }
            }
        }
        return manager;
    }

    public static void post(EventMsg eventMsg){
        getInstance().postEvent(eventMsg);
    }

    public void register(OnEventMsgListener listener) {
        synchronized (EventManager.class) {
            if (!listenerList.contains(listener)) {
                listenerList.add(listener);
            }
        }
    }

    public void unRegister(OnEventMsgListener listener) {
        if (listener != null) {
            synchronized (EventManager.class) {
                listenerList.remove(listener);
            }
        }
    }

    private void postEvent(EventMsg msg){
        synchronized (EventManager.class) {
            for (OnEventMsgListener listener : listenerList) {
                listener.onEventMsg(msg);
            }
        }
    }

}
