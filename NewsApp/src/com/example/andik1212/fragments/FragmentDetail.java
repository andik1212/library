package com.example.andik1212.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.andik1212.ActivityDetail;
import com.example.andik1212.R;
import com.example.andik1212.database.DBAdapter;


public class FragmentDetail extends SherlockFragment {

    public static final String EXTRA_TEXT = "extra_text";

    private static final int REQUEST_NUMBER = 1;

    private View view;
    private String[] text;
    DBAdapter db = new DBAdapter(getActivity());

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

//        Button replace = (Button) view.findViewById(R.id.details_btn);
//        replace.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment2_2 numberFragment = Fragment2_2.newInstance(text);
//                numberFragment.setTargetFragment(Fragment2_1.this, REQUEST_NUMBER);
//
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.frag_container, numberFragment);
//                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                ft.addToBackStack(null);
//                ft.commit();
//            }
//        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("text", text);
    }



    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.add(0, ActivityDetail.OPT_BUTTON_LIKE, 0, "like").setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == ActivityDetail.OPT_BUTTON_LIKE)
        {
//            toDo on pressed
            db.open();
            Toast.makeText(getActivity(), db.putDB(text)+"", Toast.LENGTH_SHORT).show();
            db.close();
        }

        return super.onOptionsItemSelected(item);
    }




//    public void setText(final String number, final int requestCode) {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                switch (requestCode) {
//                    case 1:
//                        text = text + " " + number;
//                        break;
//                }
//            }
//        });
//    }
}
