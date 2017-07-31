package com.example.marceline.gazarch.Login.RegionSpinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marceline.gazarch.R;

import java.util.ArrayList;

/**
 * Created by Marceline on 7/25/17.
 */

public class RegionSpinnerAdapter extends ArrayAdapter<RegionSpinnerItem> {
    int groupid;
    Activity context;
    ArrayList<RegionSpinnerItem> list;
    LayoutInflater inflater;

    public RegionSpinnerAdapter(Activity context, int groupid, int id, ArrayList<RegionSpinnerItem>
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);

        ImageView imageView=(ImageView)itemView.findViewById(R.id.RegionSpinnerImage);
        imageView.setImageResource(list.get(position).getImageId());

        TextView textView=(TextView)itemView.findViewById(R.id.RegionSpinnerText);
        textView.setText(list.get(position).getText());

        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }
}