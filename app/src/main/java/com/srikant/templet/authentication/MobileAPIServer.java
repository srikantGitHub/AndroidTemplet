package com.srikant.templet.authentication;

import android.util.Base64;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MobileAPIServer implements ServerAuthenticate {
    User user;
    @Override
    public User userSignUp(JSONObject mbpuser) throws Exception {
        try {
            Gson gson=new Gson();
            URL url = new URL("http://mobileapi.jhpolice.gov.in/MBP/rest/mbpregister/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(30000);
            OutputStreamWriter request = new OutputStreamWriter(connection.getOutputStream());
            request.write("mbpuserdata="+mbpuser.toString()+"&access_token=de87385a-e3df-40c7-8dbe-e7d4ae250787");
            request.flush();
            request.close();
            String line;
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null){sb.append(line + "\n");}
            user=gson.fromJson(String.valueOf(sb),User.class);
            isr.close();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
       return user;
    }
    @Override
    public User userSignIn(String uuid) throws Exception {
        try {
            Gson gson=new Gson();
            URL url = new URL("http://mobileapi.jhpolice.gov.in/MBP/rest/mbpregister/login");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setReadTimeout(30000);
            OutputStreamWriter request = new OutputStreamWriter(connection.getOutputStream());
            request.write("field=name&value="+uuid+"&access_token=de87385a-e3df-40c7-8dbe-e7d4ae250787");
            request.flush();
            request.close();
            String line;
            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null){sb.append(line + "\n");}
            user=gson.fromJson(String.valueOf(sb),User.class);
            isr.close();
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
