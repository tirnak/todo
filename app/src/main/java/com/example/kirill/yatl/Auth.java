package com.example.kirill.yatl;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.GooglePlayServicesUtil;

import java.io.IOException;

/**
 * Created by kirill on 04.08.14.
 */
public class Auth {

    private Context context;

    public Auth(Context newContext) {
        context = newContext;
    }

    public String asdf() {
        AccountManager mAccountManager = AccountManager.get(context);
        Account[] accounts = mAccountManager.getAccountsByType(
                GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        for (String name : names) {
            System.out.println(name);
        }

        String token = null;
        try {
            token = GoogleAuthUtil.getToken(context, names[0], "oauth2:https://accounts.google.com/o/oauth2/auth https://www.googleapis.com/auth/tasks https://mail.google.com/");
        } catch (GooglePlayServicesAvailabilityException playEx) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(
                    playEx.getConnectionStatusCode(),
                    (Activity) context,
                    54);
            // Use the dialog to present to the user.
        } catch (UserRecoverableAuthException recoverableException) {
            Intent recoveryIntent = recoverableException.getIntent();
            // Use the intent in a custom dialog or just startActivityForResult.
            //this.startActivityForResult(recoveryIntent, 23);
        } catch (GoogleAuthException authEx) {
            return "";
        } catch (IOException ioEx) {
            return "";
        }
        if (token != null) {
            return ("token is" + token);
        } else {
            return "";
        }

    }

}
