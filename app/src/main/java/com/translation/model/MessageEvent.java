package com.translation.model;


/**
 * Created by GA on 2017/11/22.
 */

public class MessageEvent {

    public static final int REGISTER_FINISH = 0;


    private int id;
    private Object data;
    private Object extraData;


    public MessageEvent(int id) {
        this.id = id;
    }

    public MessageEvent(int id, Object data) {
        this.id = id;
        this.data = data;
    }

    public MessageEvent(int id, Object data, Object extraData) {
        this.id = id;
        this.data = data;
        this.extraData = extraData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    @Override
    public String toString() {
        return "MessageEvent{" +
                "id=" + id +
                ", data=" + data +
                ", extraData=" + extraData +
                '}';
    }
}
