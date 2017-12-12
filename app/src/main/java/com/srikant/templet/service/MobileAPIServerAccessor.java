package com.srikant.templet.service;
import com.srikant.templet.provider.LocationBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Srikant on 7/1/2017.
 */
public class MobileAPIServerAccessor {
    public List<LocationBean> getLocaton(String auth) throws Exception {
        return new ArrayList<LocationBean>();
    }
    public void putLocation(String authtoken, String userId, LocationBean locationToAdd) throws Exception {

    }
    private class Locations implements Serializable {
        List<LocationBean> results;
    }
}
