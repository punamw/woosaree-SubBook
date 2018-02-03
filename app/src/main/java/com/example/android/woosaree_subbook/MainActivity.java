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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // Filename
    private static final String FILENAME = "file.sav";

    // ArrayList for Subscriptions
    private ArrayList<Subscription> subscriptionCounters = new ArrayList<Subscription>();

    //Create Adapter
    private SubscriptionAdapter subscriptionAdapter;

    //Create the listview to be populated
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListView_Counter);

        //Executes when user presses add button to add subscription
        FloatingActionButton floatingButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //inflate the edit_view to gather information
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

                alertDialogBuilder.setPositiveButton("Add",  new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){

                        //execute if name,date and charge are non empty fields
                        if (!(editName.getText().toString().equals("")) && !(editDate.getText().toString().equals("")) && !(editCharge.getText().toString().equals("")) ){

                            String name = editName.getText().toString();
                            String date = editDate.getText().toString();
                            String charge = editCharge.getText().toString();
                            String comment = editComment.getText().toString();

                            Subscription mySub = new Subscription(name, date, charge, comment);
                            subscriptionCounters.add(mySub);
                            subscriptionAdapter.notifyDataSetChanged();

                            //used to calculate the total charge of all the subscriptions
                            float subscriptionTotal = 0;
                            for (int i = 0; i < subscriptionCounters.size(); i ++){
                                float charge1 = Float.parseFloat(subscriptionCounters.get(i).getSubCharge());
                                subscriptionTotal = subscriptionTotal + charge1;
                            }
                            //set the total charge of all subscriptions
                            TextView chargeTextView = (TextView)findViewById(R.id.totalCounters);
                            String subTotal = "Total Subscriptions: $" + Float.toString(subscriptionTotal);
                            chargeTextView.setText(subTotal);

                            //save to file and exit dialog
                            saveInFile();
                            dialog.dismiss();

                        }
                        else {
                            //execute if either name,date or charge are empty fields
                            Toast.makeText(getApplicationContext(), "Make sure Name and Count are not blank", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //set up a cancel button
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
        loadFromFile();

        // Create the adapter to convert the array to views
        subscriptionAdapter = new SubscriptionAdapter(this, subscriptionCounters);
        // Attach the adapter to a ListView
        listView.setAdapter(subscriptionAdapter);

        //find the total charge of subscriptions
        float subscriptionTotal = 0;
        for (int i = 0; i < subscriptionCounters.size(); i ++){
            float charge1 = Float.parseFloat(subscriptionCounters.get(i).getSubCharge());
            subscriptionTotal = subscriptionTotal + charge1;
        }
        //set the total charge of all subscriptions
        final TextView chargeTextView = (TextView)findViewById(R.id.totalCounters);
        String subTotal = "Total Subscriptions: $" + Float.toString(subscriptionTotal);
        chargeTextView.setText(subTotal);


        //create listener for each item in list view
        //execute if clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                // Find the current subscription that was clicked on
                final Subscription currentSubscription = subscriptionAdapter.getItem(position);

                //inflate the edit_item_view to allow user to edit information
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
                        subscriptionCounters.remove(pos);   //remove current Subscription from list of subscriptions
                        saveInFile();
                        subscriptionAdapter.notifyDataSetChanged();
                        Intent eventIntent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(eventIntent);
                    }
                });

                //create editable text views
                editName.setText(currentSubscription.getSubName().toString(), TextView.BufferType.EDITABLE);
                editDate.setText(currentSubscription.getSubDate().toString(), TextView.BufferType.EDITABLE);
                editCharge.setText(currentSubscription.getSubCharge().toString(), TextView.BufferType.EDITABLE);
                editComment.setText(currentSubscription.getSubComment().toString(), TextView.BufferType.EDITABLE);

                //create dialog to allow user to edit subscription
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Edit Subscription");
                builder.setView(v);

                builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //executes if name, charge and date are not empty fields
                        if (!(editName.getText().toString().equals("")) && !(editDate.getText().toString().equals("")) && !(editCharge.getText().toString().equals(""))) {
                            //set name,date,charge and comment to the new values
                            String Name = editName.getText().toString();
                            String Date = editDate.getText().toString();
                            String Charge = editCharge.getText().toString();
                            String Comment = editComment.getText().toString();

                            //update the current subscription to the new values
                            currentSubscription.setSubName(Name);
                            currentSubscription.setSubDate(Date);
                            currentSubscription.setSubCharge(Charge);
                            currentSubscription.setSubComment(Comment);

                            subscriptionAdapter.notifyDataSetChanged();

                            //find the total charge of subscriptions
                            float subscriptionTotal = 0;
                            for (int i = 0; i < subscriptionCounters.size(); i ++){
                                float charge1 = Float.parseFloat(subscriptionCounters.get(i).getSubCharge());
                                subscriptionTotal = subscriptionTotal + charge1;
                            }
                            // set the total charge of all subscriptions
                            TextView chargeTextView = (TextView)findViewById(R.id.totalCounters);
                            String subTotal = "Total Subscriptions: $" + Float.toString(subscriptionTotal);
                            chargeTextView.setText(subTotal);

                            saveInFile();
                            subscriptionAdapter.notifyDataSetChanged();
                        }
                        else {
                            //execute if either the name, date or charge is left blank
                            Toast.makeText(getApplicationContext(), "Make sure Name, Date and Charge are not blank", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                //Sets up cancel option for user
                builder.setNegativeButton("Cancel", null);
                builder.setCancelable(false);

                AlertDialog alert = builder.create();
                alert.show();
            }});
    }


    // Save data when activity is paused
    @Override
    public void onPause() {
        super.onPause();
        saveInFile();
    }

    /**
     * Load data from file
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Subscription>>() {}.getType();
            subscriptionCounters = gson.fromJson(in, listType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            subscriptionCounters = new ArrayList<Subscription>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    /**
     * Save the data in file
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(subscriptionCounters, writer);
            writer.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }
}