package com.bar.android.my_proj;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //private Spinner spinner1;
    private Spinner spinner2;
    private Button button1;
    private EditText text1;
    private UserGrpDataSource usrgrpdatasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemsOnSpinner2();
        Log.v("Hello", getTrack("Hello"));
        Log.v("Parpom",getTrack("Pap"));
    }

    public String getTrack(String value)
    {

        String str = value;  // or anything else

        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray())
            sb.append((int)c);

        String s = sb.toString();
        Log.v("Key",s);

        return s;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        usrgrpdatasource = new UserGrpDataSource(this);
        usrgrpdatasource.open();
        //usrgrpdatasource.deleteUsr();
        List<String> list = new ArrayList<String>();
        List<UserGrpTracker> usrvalues = usrgrpdatasource.getAllUsers();
        int i=0;
        while (i<usrvalues.size())
        {
            Log.v("jee", usrvalues.get(i).getUserGroup());
            list.add(usrvalues.get(i).getUserGroup());
            i++;
        }

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    public void myButton1(View view)
    {
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        Toast.makeText(MainActivity.this,
                "Loggin in as  : " + String.valueOf(spinner2.getSelectedItem()),
                Toast.LENGTH_SHORT).show();

        String user = spinner2.getSelectedItem().toString();

        Bundle sendBundle = new Bundle();
        sendBundle.putString("usergrp", user);


        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        i.putExtras(sendBundle);
        startActivity(i);

    }

    public void myButton(View view)
    {
        text1 = (EditText) findViewById(R.id.editText);
        String newuser = text1.getText().toString();

        Toast.makeText(MainActivity.this,
                "Loggin in as  : " + String.valueOf(newuser),
                Toast.LENGTH_SHORT).show();

        Bundle sendBundle = new Bundle();
        sendBundle.putString("usergrp", newuser);
        usrgrpdatasource.createUserGrp(newuser);

        Intent i = new Intent(MainActivity.this, Main2Activity.class);
        i.putExtras(sendBundle);
        startActivity(i);

    }





    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
