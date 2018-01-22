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
    private ArrayList<Subscription> subCount = new ArrayList<Subscription>();
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
                        subCount.add(mySub);
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
        subscriptionAdapter = new SubscriptionAdapter(this, subCount);
        // Attach the adapter to a ListView
        listView.setAdapter(subscriptionAdapter);

    }
}
