package com.example.andik1212.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.andik1212.ActivityDetail;
import com.example.andik1212.R;
import com.example.andik1212.StartActivity;
import com.example.andik1212.database.DBHelperAdapter;
import com.example.andik1212.helper.Article;
import com.example.andik1212.helper.ArticleCollection;
import com.example.andik1212.helper.CustomArrayAdapter;
import com.example.andik1212.helper.GetNews;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: Andik
 * Date: 21.01.13
 * Time: 10:17
 * To change this template use File | Settings | File Templates.
 */
public class FragmenListLike extends SherlockFragment {

    public static class FragmentListLike extends SherlockFragment {
        private static FragmentListLike Instance;
        protected View loading;

        static String[] valuesl; //{ "new1", "new2", "new3", "new4", "new5", "new6"  };
        static String[] datel;
        static String[] contentl;
        static String[] jIdl;
        //временно
//        String[] content = new String[0];


        private static class Self {
            public View view;
            public boolean restore;
            public FragmentActivity activity;
//            public ArticleCollection articles;
            public ArticleCollection articles_db;
        }

        static Self _self = new Self();

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            _self.activity = getActivity();
            Instance = this;
        }

        private View view;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.fragment_list, container, false);
            setHasOptionsMenu(true);
//            loading = View.inflate(_self.activity, R.layout.loading, new LinearLayout(_self.activity));

            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            if (_self.restore && _self.articles_db.size() > 0){
                updateUi();
                _self.restore = false;
            } else{
                if (StartActivity.INTERNET_STATUS == "com.example.andik1212.UP"){
                    loadData();
                }
                else{
                    showLoadingError();
                }
            }
        }
        public void onResume(){
            super.onResume();
            loadData();
        }

        @Override
        public void onPause() {
            super.onPause();

            _self.restore = true;
        }

        public void loadData() {


            new Thread(new Runnable() {
                @Override
                public void run() {
                    //To change body of implemented methods use File | Settings | File Templates.
                    try {
                        _self.articles_db = new ArticleCollection(DBHelperAdapter.GetHelper().getArticleDao().queryForAll());
                    } catch (SQLException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                    Article art;
            valuesl = new String[_self.articles_db.size()];
            datel = new String[_self.articles_db.size()];
            contentl = new String[_self.articles_db.size()];
            jIdl = new String[_self.articles_db.size()];
            for (int i = 0; i < _self.articles_db.size(); i++){
                art = (Article)_self.articles_db.elementAt(i);
                valuesl[i] = art.getTitle();
                datel[i] = art.getDate();
                datel[i] = datel[i].substring(0, 10);
                contentl[i] = art.getContent();
                jIdl[i] = art.getId();
                updateUi();
            }

                }
            }).start();

        }


        private void showLoadingError() {
            _self.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //To change body of implemented methods use File | Settings | File Templates.
                    Toast.makeText(_self.activity, "DB Error ;(", Toast.LENGTH_LONG).show();

                }
            });
        }

        private void updateUi() {
            _self.activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //To change body of implemented methods use File | Settings | File Templates.
                    if (_self.articles_db.size() > 0){
                        ListView list = (ListView) view.findViewById(R.id.list);

//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_2,
//                        android.R.id.text1, values);
                        CustomArrayAdapter adapter = new CustomArrayAdapter(getActivity(), valuesl, datel);

                        list.setAdapter(adapter);

                        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                String text = (String) adapterView.getItemAtPosition(position);
                                String[] extra = new String[4];
                                extra[0] = jIdl[position];
                                extra[1] = valuesl[position];
                                extra[2] = datel[position];
                                extra[3] = contentl[position];

                                Intent intent = new Intent(getActivity(), ActivityDetail.class);
                                intent.putExtra(FragmentDetail.EXTRA_TEXT, extra);
                                startActivity(intent);
                            }
                        });
                    }

                }
            });
        }




        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
        {
            menu.add(0, StartActivity.OPT_BUTTON_ALLLIKES,0,"Return").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        public boolean onOptionsItemSelected(MenuItem item) throws SQLException, IOException {
            if(item.getItemId() == StartActivity.OPT_BUTTON_ALLLIKES)
            {
//            toDo on pressed
                _self.activity.getSupportFragmentManager().beginTransaction().replace(R.id.list_frag, new FragmentList()).addToBackStack(null).commit();

            }

            return super.onOptionsItemSelected(item);
        }






    }
}
