package com.srikant.templet.service;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.srikant.templet.Myapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient extends AsyncTask<Context, Void, String> {

	Context context=null;       String decesion;         String intentFilter;
	ProgressDialog progress; 	String filter="";        String dialogmessage="";
	String apppendURI="";       String responseJson="";  String Sms;
	String otp;
	public RestClient(Context context, String decesion, String intentFilter, String filter, String dialogmessage, String apppendURI){
		this.context=context;
		this.decesion=decesion;
		this.intentFilter=intentFilter;
		this.filter=filter;
		this.dialogmessage=dialogmessage;
		this.apppendURI=apppendURI;
		}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progress=new ProgressDialog(context);
		progress.setMessage(dialogmessage);
		progress.show();
		progress.setCancelable(false);
	}
@Override
protected String doInBackground(Context... params) {
	 String uri="http://mobileapi.jhpolice.gov.in/MobileAPI/mobile";
	switch(decesion){
		case "login": {
			try {
				URL url = new URL(uri+apppendURI);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				String userCredentials = "srikant:pari";
				String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(),0));
				connection.setRequestProperty ("Authorization", basicAuth);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setReadTimeout(30000);
				OutputStreamWriter request = new OutputStreamWriter(connection.getOutputStream());
				request.write(filter);
				request.flush();
				request.close();
				String line;
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				BufferedReader reader = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null){sb.append(line + "\n");}
				responseJson = String.valueOf(sb);
				isr.close();
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		case "cctnsstatedb": {
			try {
				URL url = new URL(uri+apppendURI);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				String userCredentials = "srikant:pari";
				String basicAuth = "Basic " + new String(Base64.encode(userCredentials.getBytes(),0));
				connection.setRequestProperty ("Authorization", basicAuth);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestMethod("GET");
				connection.setReadTimeout(30000);
				String line;
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				BufferedReader reader = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null){sb.append(line + "\n");}
				responseJson = String.valueOf(sb);
				isr.close();
				reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
		case "SMS": {
			try {
				String parameters = "username=uidjharsms-jhpol&password=indiagov@834004&smsservicetype=singlemsg&content="+getOtp()+"&mobileno="+filter+"&senderid=JHRPOL";
				URL url = new URL("http://msdgweb.mgov.gov.in/esms/sendsmsrequest");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				connection.setRequestMethod("POST");
				connection.setReadTimeout(5000);
				OutputStreamWriter request = new OutputStreamWriter(connection.getOutputStream());
				request.write(parameters);
				request.flush();
				request.close();
				String line;
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				BufferedReader reader = new BufferedReader(isr);
				StringBuilder sb = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				responseJson =sb.toString();
				isr.close();
				reader.close();
			}catch(Exception e){
			e.printStackTrace();
			}
		}
	}
  return responseJson;
 }
@Override
protected void onPostExecute(String data) {
	if(data!=null) {
		Myapplication.registrationSubject.setData(data.toString());
	}
	else{
		Myapplication.registrationSubject.setData("NOT");
	}
	Myapplication.registrationSubject.setState(intentFilter);
	progress.dismiss();
	super.onPostExecute(data);
	}
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
}