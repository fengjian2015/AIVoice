package com.translation.androidlib.observer;

/**
 * 消息实体
 * Created by Darren on 2018/12/17.
 */
public class EventMsg {

    public static final int REGISTER_FINISH = 0;



    private int key;
    private Object data;
    private Object extraData;

    public EventMsg() {
    }

    public EventMsg(int key) {
        this.key = key;
    }

    public EventMsg(int key, Object data) {
        this.key = key;
        this.data = data;
    }

    public EventMsg(int key, Object data, Object extraData) {
        this.key = key;
        this.data = data;
        this.extraData = extraData;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMsg{" +
                "key=" + key +
                ", data=" + data +
                ", extraData=" + extraData +
                '}';
    }

}
