package com.srikant.templet.authentication;

import org.json.JSONObject;

/**
 * User: udinic
 * Date: 3/27/13
 * Time: 2:35 AM
 */
public interface ServerAuthenticate {
    public User userSignUp(JSONObject mbpuser) throws Exception;
    public User userSignIn(final String uuid) throws Exception;
}
