package com.srikant.templet.Myobserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srikant on 1/19/2017.
 */
public class Subject {
    private List<Myobserver> observers = new ArrayList<Myobserver>();
    private String data;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyAllObservers();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void register(Myobserver observer){
       if(!observers.contains(observer)) {
            observers.add(observer);
        }
    }
    public void unregister(Myobserver observer){
            observers.remove(observer);
    }

    public void notifyAllObservers(){
        for (Myobserver observer : observers) {
            observer.update(data);
        }
    }
}
