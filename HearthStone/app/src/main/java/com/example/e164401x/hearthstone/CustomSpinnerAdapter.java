package com.example.e164401x.hearthstone;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E164401X on 03/04/18.
 */

public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    private final Context activity;
    private List<String> asr;

    public CustomSpinnerAdapter(Context context, List<String> asr) {
        this.asr=asr;
        activity = context;
    }



    public int getCount()
    {
        return asr.size();
    }

    public Object getItem(int i)
    {
        return asr.get(i);
    }

    public long getItemId(int i)
    {
        return (long)i;
    }



    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView txt = new TextView(this.activity);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(18);
        txt.setBackgroundColor(getResource().getColor(R.color.mySpinnerBackground));
        txt.setGravity(Gravity.CENTER_VERTICAL);
        txt.setText(asr.get(position));
        txt.setTextColor(Color.parseColor("#856226"));
        return  txt;
    }

    public View getView(int i, View view, ViewGroup viewgroup) {
        TextView txt = new TextView(this.activity);
        txt.setGravity(Gravity.CENTER);
        txt.setPadding(16, 16, 16, 16);
        txt.setTextSize(16);
        txt.setText(asr.get(i));
        txt.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_down, 0);
        txt.setTextColor(Color.parseColor("#856226"));
        return  txt;
    }

}