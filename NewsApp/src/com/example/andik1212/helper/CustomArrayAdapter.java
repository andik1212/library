package com.example.andik1212.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.andik1212.R;


/**
 * Created with IntelliJ IDEA.
 * User: Vadim
 * Date: 25.11.12
 * Time: 22:27
 * To change this template use File | Settings | File Templates.
 */
public class CustomArrayAdapter extends ArrayAdapter {
    private Context context;
    private String[] text1;
    private String[] text2;
    public CustomArrayAdapter(Context context, String[] text1, String[] text2){
        super(context, R.layout.row, text1);
        this.context=context;
        this.text1=text1;
        this.text2=text2;
    }



    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

// TODO use recycling here to avoid inflating view every time
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView title = (TextView) rowView.findViewById(R.id.title);
        TextView published = (TextView) rowView.findViewById(R.id.published);
//        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        title.setText(text1[position]);
        published.setText(text2[position]);

        return rowView;
    }




}
