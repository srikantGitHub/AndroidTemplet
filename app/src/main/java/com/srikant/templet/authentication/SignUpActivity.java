package com.srikant.templet.authentication;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.srikant.templet.R;
import com.srikant.templet.provider.Contract;

import org.json.JSONObject;

import static com.srikant.templet.authentication.AccountGeneral.USERDATA_USER_OBJ_ID;
import static com.srikant.templet.authentication.AccountGeneral.sServerAuthenticate;
import static com.srikant.templet.authentication.MBPAuthenticatorActivity.ARG_ACCOUNT_TYPE;
import static com.srikant.templet.authentication.MBPAuthenticatorActivity.KEY_ERROR_MESSAGE;
import static com.srikant.templet.authentication.MBPAuthenticatorActivity.PARAM_USER_PASS;

/**
 * In charge of the Sign up process. Since it's not an AuthenticatorActivity decendent,
 * it returns the result back to the calling activity, which is an AuthenticatorActivity,
 * and it return the result back to the Authenticator
 *
 * User: udinic
 */
public class SignUpActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private String mAccountType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountType = getIntent().getStringExtra(ARG_ACCOUNT_TYPE);
        setContentView(R.layout.act_register);
        findViewById(R.id.alreadyMember).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        new AsyncTask<String, Void, Intent>() {
            String name = ((TextView) findViewById(R.id.name)).getText().toString().trim();
            String email = ((TextView) findViewById(R.id.email)).getText().toString().trim();
            String mobileNo = ((TextView) findViewById(R.id.mobileNo)).getText().toString().trim();
            Bundle data = new Bundle();
            @Override
            protected Intent doInBackground(String... params) {
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("name",name);
                    jsonObject.put("email",email);
                    jsonObject.put("mobile_no",mobileNo);
                    String authtoken = null;
                    User user = sServerAuthenticate.userSignUp(jsonObject);
                    user.setUuid("");
                    if (user != null) {
                        authtoken = user.getAccess_token();
                        getContentResolver().insert(Contract.LocationTable.CONTENT_URI, user.getContentValues());
                    }
                    data.putString(AccountManager.KEY_ACCOUNT_NAME, name);
                    data.putString(AccountManager.KEY_ACCOUNT_TYPE, mAccountType);
                    data.putString(AccountManager.KEY_AUTHTOKEN, authtoken);
                    // We keep the user's object id as an extra data on the account.
                    // It's used later for determine ACL for the data we send to the Parse.com service
                    Bundle userData = new Bundle();
                    userData.putString(USERDATA_USER_OBJ_ID,new Gson().toJson(user));
                    data.putBundle(AccountManager.KEY_USERDATA, userData);
                    data.putString(PARAM_USER_PASS, user.getUuid());
                } catch (Exception e) {
                    data.putString(KEY_ERROR_MESSAGE, e.getMessage());
                }
                final Intent res = new Intent();
                res.putExtras(data);
                return res;
            }

            @Override
            protected void onPostExecute(Intent intent) {
                if (intent.hasExtra(KEY_ERROR_MESSAGE)) {
                    Toast.makeText(getBaseContext(), intent.getStringExtra(KEY_ERROR_MESSAGE), Toast.LENGTH_SHORT).show();
                } else {
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
