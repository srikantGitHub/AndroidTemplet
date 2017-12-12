package com.srikant.templet.Myobserver;

/**
 * Created by Srikant on 3/16/2017.
 */

public interface Myobserver {
   Subject subject=new Subject();
    void update(String data);
}
