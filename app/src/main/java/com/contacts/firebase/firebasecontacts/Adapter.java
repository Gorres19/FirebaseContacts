package com.contacts.firebase.firebasecontacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by joshua on 8/6/2016.
 */
public class Adapter extends ArrayAdapter<Contacts> {
    private Context context;
    private int resource;
    private ArrayList<Contacts> objects;

    public Adapter(Context con, int text, ArrayList<Contacts> hahaha) {
        super(con, text, hahaha);

        this.context = con;
        this.resource = text;
        this.objects = hahaha;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contacts contacts = getItem(position);

        if(convertView == null){

            convertView = LayoutInflater.from(context).inflate(resource, parent, false);
            TextView mName= (TextView) convertView.findViewById(R.id.txtname);
            TextView mContactNumber = (TextView) convertView.findViewById(R.id.txtcontactNumber);

            mName.setText(contacts.getFriendsName());
            mContactNumber.setText(contacts.getContactNumber());
        }

        return convertView;
    }
}
