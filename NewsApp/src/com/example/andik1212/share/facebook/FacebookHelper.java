package com.example.andik1212.share.facebook;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.model.GraphUser;


import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 21.01.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class FacebookHelper {
    public static final String APP_ID = "557912684236608";
    private final Activity activity;
    private Facebook facebookClient;
    private Session session;
    private Request.Callback callback;


    public FacebookHelper(final Activity activity){
        this.activity=activity;
        session = new Session(activity);
        Session.setActiveSession(session);




    }

    public void postMessageViaFB(String message) {
        // API access object
        facebookClient = new Facebook(APP_ID);

// request parameters building
        Bundle params = new Bundle();
        params.putString("message", message);
        // sending request
        facebookClient.dialog(activity, "feed", params, new Facebook.DialogListener() {
            public void onComplete(Bundle values) {/*do something on complete*/}
            public void onFacebookError(FacebookError e) {/*do something on FB error*/}
            public void onError(DialogError e) {/*do something on dialog error*/}
            public void onCancel() {/*do something on user cancel*/}
        });


        try {
            facebookClient.request("me/feed", params, "POST");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        Request request = new Request(session, "me/feed", params,
//                HttpMethod.POST, callback);
//        callback= new Request.Callback() {
//            public void onCompleted(Response response) {
//
//
//                FacebookRequestError error = response.getError();
//                if (error != null) {
//                    Toast.makeText(activity
//                            .getApplicationContext(),
//                            error.getErrorMessage(),
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(activity
//                            .getApplicationContext(),
//                            "Thank you!",
//                            Toast.LENGTH_LONG).show();
//                }
//            }
//        };
//
//        RequestAsyncTask task = new RequestAsyncTask(request);
//        task.execute();
//
//// sending request
//        facebookClient.dialog(activity, "me/feed", params, new Facebook.DialogListener() {
//            public void onComplete(Bundle values) {/*do something on complete*/}
//
//            public void onFacebookError(FacebookError e) {/*do something on FB error*/}
//
//            public void onError(DialogError e) {/*do something on dialog error*/}
//
//            public void onCancel() {/*do something on user cancel*/}
//        });


    }

}
