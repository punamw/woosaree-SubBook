
/**SubscriptionAdapter
 *
 * Copyright (c) Feb 2, 2018 Punam Woosaree- All Rights Reserved
 */

package com.example.android.woosaree_subbook;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by PunamWoosaree on 2018-01-22.
 */

public class SubscriptionAdapter extends ArrayAdapter<Subscription> {

    private static final String LOG_TAG = SubscriptionAdapter.class.getSimpleName();

    public SubscriptionAdapter(Activity context, ArrayList<Subscription> subscriptions) {
        super(context, 0, subscriptions);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_view, parent, false);
        }

        /**
         * Get the subscription we are currently on
         */
        final Subscription currentSub = getItem(position);

        //Get the subscription name and set the subscription name
        TextView nameTextView = (TextView) listItemView.findViewById(R.id.sub_name);
        nameTextView.setText(currentSub.getSubName());

        //Get the subscription date and set the subscription date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.sub_date);
        dateTextView.setText(currentSub.getSubDate());

        //Get the subscription charge and set the subscription charge
        TextView chargeTextView = (TextView) listItemView.findViewById(R.id.sub_charge);
        chargeTextView.setText(String.valueOf(currentSub.getSubCharge()));

        //Get the subscription comment and set the subscription comment
        TextView commentTextView = (TextView) listItemView.findViewById(R.id.sub_comment);
        commentTextView.setText(currentSub.getSubComment());

        return listItemView;
    }

}
