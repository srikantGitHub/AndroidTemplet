package com.srikant.templet.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.srikant.templet.Myapplication;
import com.srikant.templet.R;
import com.srikant.templet.authentication.AccountGeneral;

public class Home extends AppCompatActivity {
    private AccountManager mAccountManager;
    private String authToken = null;
    private Account mConnectedAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAccountManager = AccountManager.get(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getTokenForAccountCreateIfNeeded(AccountGeneral.ACCOUNT_TYPE, AccountGeneral.AUTHTOKEN_TYPE_FULL_ACCESS);
            }
        }, 3000);
    }
    private void getTokenForAccountCreateIfNeeded(String accountType, String authTokenType) {
        final AccountManagerFuture<Bundle> future = mAccountManager.getAuthTokenByFeatures(accountType, authTokenType, null, this, null, null,
                new AccountManagerCallback<Bundle>() {
                    @Override
                    public void run(AccountManagerFuture<Bundle> future) {
                        Bundle bnd = null;
                        try {
                            bnd = future.getResult();
                            authToken = bnd.getString(AccountManager.KEY_AUTHTOKEN);
                            if (authToken != null) {
                                String accountName = bnd.getString(AccountManager.KEY_ACCOUNT_NAME);
                                mConnectedAccount = new Account(accountName, AccountGeneral.ACCOUNT_TYPE);
                                Myapplication.mConnectedAccount=mConnectedAccount;
                                Intent i = new Intent(Home.this, Home.class);
                                startActivity(i);
                                finish();
                            }
                            showMessage(((authToken != null) ? "SUCCESS!\ntoken: " + authToken : "FAIL"));
                            Log.d("udinic", "GetTokenForAccount Bundle is " + bnd);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showMessage(e.getMessage());
                        }
                    }
                }
                , null);
    } private void showMessage(final String msg) {
        if (msg == null || msg.trim().equals(""))
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
