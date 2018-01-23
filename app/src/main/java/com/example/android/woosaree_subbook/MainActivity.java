package com.example.android.woosaree_subbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // ArrayList for Subscriptions
    private ArrayList<Subscription> subscriptionCounters = new ArrayList<Subscription>();
    private SubscriptionAdapter subscriptionAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListView_Counter);

        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.event_view, null);

                final EditText editName = (EditText) view.findViewById(R.id.edit_sub_name);
                final EditText editDate = (EditText) view.findViewById(R.id.edit_sub_date);
                final EditText editCharge = (EditText) view.findViewById(R.id.edit_sub_charge);
                final EditText editComment = (EditText) view.findViewById(R.id.edit_sub_comment);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                // set prompts.xml to alertDialog builder
                alertDialogBuilder.setView(view);

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                //alertDialog.show();

                alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editName.getText().toString();
                        String date = editDate.getText().toString();
                        String charge = editCharge.getText().toString();
                        String comment = editComment.getText().toString();

                        Subscription mySub = new Subscription(name, date, charge, comment);
                        subscriptionCounters.add(mySub);
                        subscriptionAdapter.notifyDataSetChanged();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialogBuilder.show();

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        // Create the adapter to convert the array to views
        subscriptionAdapter = new SubscriptionAdapter(this, subscriptionCounters);
        // Attach the adapter to a ListView
        listView.setAdapter(subscriptionAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current subscription that was clicked on
                final Subscription currentSubscription = subscriptionAdapter.getItem(position);

                View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.edit_item_view, null);

                final EditText editName = (EditText) v.findViewById(R.id.edit_sub_name);
                final EditText editDate = (EditText) v.findViewById(R.id.edit_sub_date);
                final EditText editCharge = (EditText) v.findViewById(R.id.edit_sub_charge);
                final EditText editComment = (EditText) v.findViewById(R.id.edit_sub_comment);
                final int pos = position;

                // Set Delete Button
                final Button deleteButton = (Button) v.findViewById(R.id.edit_delete);
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscriptionCounters.remove(pos);
                        Intent eventIntent = new Intent(MainActivity.this, MainActivity.class);
                        subscriptionAdapter.notifyDataSetChanged();
                        startActivity(eventIntent);

                    }
                });

                editName.setText(currentSubscription.getSubName().toString(), TextView.BufferType.EDITABLE);
                editDate.setText(currentSubscription.getSubDate().toString(), TextView.BufferType.EDITABLE);
                editCharge.setText(currentSubscription.getSubCharge().toString(), TextView.BufferType.EDITABLE);
                editComment.setText(currentSubscription.getSubComment().toString(), TextView.BufferType.EDITABLE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Edit Subscription");
                builder.setView(v);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Subscription = editName.getText().toString();
                        String Date = editDate.getText().toString();
                        String Charge = editCharge.getText().toString();
                        String Comment = editComment.getText().toString();

                        currentSubscription.setSubName(Subscription);
                        currentSubscription.setSubDate(Subscription);
                        currentSubscription.setSubCharge(Subscription);
                        currentSubscription.setSubComment(Subscription);

                        subscriptionAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }});
    }}