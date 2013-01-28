package com.example.andik1212.share.facebook;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 21.01.13
 * Time: 22:44
 * To change this template use File | Settings | File Templates.
 */
public class FacebookHelper {
    private final Activity activity;
    private Session session;
    private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
    private boolean pendingPublishReauthorization = false;
    public String TAG = "FacebookHelper";
    private String title;
    private String message;


    public FacebookHelper(final Activity activity, String title, String message){
        this.activity=activity;
        this.title=title;
        this.message=message;

        session = new Session(activity);

        Session.setActiveSession(session);

        Session.openActiveSession(activity, true, new Session.StatusCallback() {
            // callback when session changes state
            @Override
            public void call(Session session, SessionState state, Exception exception) {
                if (session.isOpened()) {
                    // make request to the /me API
                    Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
                        // callback after Graph API response with user object
                        @Override
                        public void onCompleted(GraphUser user, Response response) {
                            if (user != null) {
                                Toast.makeText(activity, "Hello " + user.getName() + "!" + user.getId(), Toast.LENGTH_LONG).show();
                                postMessageViaFB();
                            }
                        }
                    });
                }
            }
        });

    }

    private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public void postMessageViaFB() {
        Session session = Session.getActiveSession();

        if (session != null){

            // Check for publish permissions
            List<String> permissions = session.getPermissions();
            if (!isSubsetOf(PERMISSIONS, permissions)) {
                pendingPublishReauthorization = true;
                Session.NewPermissionsRequest newPermissionsRequest = new Session
                        .NewPermissionsRequest(activity, PERMISSIONS);
                session.requestNewPublishPermissions(newPermissionsRequest);
                return;
            }

            Bundle postParams = new Bundle();
            postParams.putString("name", title);
            postParams.putString("message", message);
//            postParams.putString("caption", "Build great social apps and get more installs.");
//            postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
            postParams.putString("link", "http://android-developers.blogspot.com/");
//            postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

            Request.Callback callback= new Request.Callback() {
                public void onCompleted(Response response) {
                    JSONObject graphResponse = response
                            .getGraphObject()
                            .getInnerJSONObject();
                    String postId = null;
                    try {
                        postId = graphResponse.getString("id");
                    } catch (JSONException e) {
                        Log.i(TAG,
                                "JSON error " + e.getMessage());
                    }
                    FacebookRequestError error = response.getError();
                    if (error != null) {
                        Toast.makeText(activity
                                .getApplicationContext(),
                                error.getErrorMessage(),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity
                                .getApplicationContext(),
                                postId,
                                Toast.LENGTH_LONG).show();
                    }
                }
            };

            Request request = new Request(session, "me/feed", postParams,
                    HttpMethod.POST, callback);

            RequestAsyncTask task = new RequestAsyncTask(request);
            task.execute();
        }



    }

}
