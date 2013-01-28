package com.example.andik1212.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.andik1212.ActivityDetail;
import com.example.andik1212.R;
import com.example.andik1212.database.DBHelperAdapter;
import com.example.andik1212.helper.Article;
import com.example.andik1212.share.facebook.FacebookHelper;
import com.example.andik1212.share.twitter.PrepareRequestTokenActivity;
import com.example.andik1212.share.twitter.TwitterUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class FragmentDetail extends SherlockFragment {

    public static final String EXTRA_TEXT = "extra_text";

    private static final int REQUEST_NUMBER = 1;

    private View view;
    private String[] text;
    Article article = new Article();
    String buttonLabel = "like";
    Menu menuLockal;
    FacebookHelper facebookHelper;
    private SharedPreferences prefs;


    public static FragmentDetail newInstance(String[] text) {
        FragmentDetail f = new FragmentDetail();
        Bundle args = new Bundle();
        args.putStringArray(EXTRA_TEXT, text);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            text = args.getStringArray(EXTRA_TEXT);
            article.setId(text[0]);
            article.setTitle(text[1]);
            article.setDate(text[2]);
            article.setContent(text[3]);
            this.prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            text = savedInstanceState.getStringArray("text");
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) view.findViewById(R.id.text);
                tv.setText(text[1]);
                WebView wv = (WebView) view.findViewById(R.id.web);
                wv.loadData(text[3], "text/html", null);

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("text", text);
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) throws SQLException {
        List articleList = null;
        articleList = DBHelperAdapter.GetHelper().getArticleDao().queryForMatchingArgs(article);
        Boolean existIn = false;
        if (!(articleList == null || articleList.size()==0)) existIn = true;
        if (existIn) {buttonLabel = "disLike";} else {buttonLabel = "like";}

        menu.add(0, ActivityDetail.OPT_BUTTON_FACEBOOOK, 0, "").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, ActivityDetail.OPT_BUTTON_TWEETTER, 0, "").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, ActivityDetail.OPT_BUTTON_LIKE, 0, "").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.findItem(ActivityDetail.OPT_BUTTON_FACEBOOOK).setIcon(R.drawable.fb);
        menu.findItem(ActivityDetail.OPT_BUTTON_TWEETTER).setIcon(R.drawable.twitter);
        if (buttonLabel=="like"){
            menu.findItem(ActivityDetail.OPT_BUTTON_LIKE).setIcon(R.drawable.like);
        } else{
            menu.findItem(ActivityDetail.OPT_BUTTON_LIKE).setIcon(R.drawable.dislike);
        }

        this.menuLockal=menu;
    }

    public boolean onOptionsItemSelected(MenuItem item) throws SQLException, IOException {
        if(item.getItemId() == ActivityDetail.OPT_BUTTON_LIKE)
        {   int rows=-1;
//            toDo on pressed
            if (buttonLabel == "like"){

            try{
                rows = DBHelperAdapter.GetHelper().getArticleDao().create(article);
                Toast.makeText(getActivity(),rows+" rows updated",Toast.LENGTH_SHORT).show();
//                ArticleCollection articles_db = new ArticleCollection(DBHelperAdapter.GetHelper().getArticleDao().queryForAll());
//                Toast.makeText(getActivity(),rows+" rows updated"+articles_db.size()+" entries in bd",Toast.LENGTH_LONG).show();
                buttonLabel = "disLike";
//                menuLockal.findItem(ActivityDetail.OPT_BUTTON_LIKE).setTitle(buttonLabel);
                menuLockal.findItem(ActivityDetail.OPT_BUTTON_LIKE).setIcon(R.drawable.dislike);

            } catch (SQLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            }else{
                rows = DBHelperAdapter.GetHelper().getArticleDao().delete(article);
                Toast.makeText(getActivity(),rows+" rows deleted",Toast.LENGTH_SHORT).show();
                buttonLabel = "like";
//                menuLockal.findItem(ActivityDetail.OPT_BUTTON_LIKE).setTitle(buttonLabel);
                menuLockal.findItem(ActivityDetail.OPT_BUTTON_LIKE).setIcon(R.drawable.like);
            }
        }
        if (item.getItemId() == ActivityDetail.OPT_BUTTON_FACEBOOOK){
            facebookHelper = new FacebookHelper(getSherlockActivity(),text[1],"Post on android-developers.blogspot.com posted at "+text[2]);

        }
        if (item.getItemId() == ActivityDetail.OPT_BUTTON_TWEETTER){
            if (TwitterUtils.isAuthenticated(prefs)) {
                sendTweet();
            } else {
                Intent i = new Intent(getActivity(), PrepareRequestTokenActivity.class);
                i.putExtra("tweet_msg",getTweetMsg());
                startActivity(i);
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private String getTweetMsg() {
        return text[1]+"\n Post on android-developers.blogspot.com posted at "+text[2];
    }

    public void sendTweet() {
        Thread t = new Thread() {
            public void run() {

                try {
                    TwitterUtils.sendTweet(prefs, getTweetMsg());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        };
        t.start();
    }

}
