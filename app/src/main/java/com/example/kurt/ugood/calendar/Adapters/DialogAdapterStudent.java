package com.example.kurt.ugood.calendar.Adapters;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kurt.ugood.R;
import com.example.kurt.ugood.calendar.Dialogpojo;

import java.util.ArrayList;

class DialogAdaptorStudent extends BaseAdapter {
    Activity activity;

    private Activity context;
    private ArrayList<Dialogpojo> alCustom;
    private String sturl;


    public DialogAdaptorStudent(Activity context, ArrayList<Dialogpojo> alCustom) {
        this.context = context;
        this.alCustom = alCustom;

    }

    @Override
    public int getCount() {
        return alCustom.size();

    }

    @Override
    public Object getItem(int i) {
        return alCustom.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.calendar_row_addapt, null, true);

        TextView tvTitle=(TextView)listViewItem.findViewById(R.id.tv_name);
        TextView tvSubject=(TextView)listViewItem.findViewById(R.id.tv_type);
        TextView tvDate=(TextView)listViewItem.findViewById(R.id.tv_date);
        TextView tvDescription=(TextView)listViewItem.findViewById(R.id.tv_class);


        tvTitle.setText("Title : "+alCustom.get(position).getTitles());
        tvSubject.setText("Mood : "+alCustom.get(position).getSubjects());
        tvDate.setText("Date : "+alCustom.get(position).getDuedates());
        tvDescription.setText("Description : "+alCustom.get(position).getDescripts());

        return  listViewItem;
    }

}