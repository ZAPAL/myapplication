package com.example.zapal.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class ListRecept extends ActionBarActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recept);
    }*/

    //ListView listView ;
    final String LOG_TAG = "myLogs";

    //@Override
    /*protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_recept);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Автобус № 1",
                "Автобус № 2",
                "Автобус № 3",
                "Автобус № 4",
                "Автобус № 5",
                "Автобус № 6",
                "Автобус № 7",
                "Автобус № 8",
                "Автобус № 10",
                "Автобус № 11",
                "Автобус № 12",
                "Автобус № 13",
                "Автобус № 14",
                "Автобус № 15",
                "Автобус № 16",
                "Автобус № 17",
                "Автобус № 18",
                "Автобус № 19",
                "Автобус № 20",
                "Автобус № 21",
                "Автобус № 22",
                "Автобус № 23",
                "Автобус № 24",
                "Автобус № 25",
                "Автобус № 26",

        };

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                Intent myIntent = new Intent(ListRecept.this, DetailActivity.class);
                myIntent.putExtra("BUSN", itemValue);
                startActivity(myIntent);

               *//* // Show Alert
                Toast toast = new Toast(getApplicationContext());
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();*//*

            }

        });
    }*/


    private static final String DB_NAME = "bus.sql";
    //Хорошей практикой является задание имен полей БД константами
    private static final String TABLE_NAME = "bus";
    private static final String NUMBER = "NUMBER";
    private static final String ROUTE = "ROUTE";

    private SQLiteDatabase myDataBase;
    private ListView listView;
    private ArrayList bus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //Наш ключевой хелпер
        //ExternalDbOpenHelper dbOpenHelper = new ExternalDbOpenHelper(this);
       // database = dbOpenHelper.openDataBase();

        ExternalDbOpenHelper myDbHelper = new ExternalDbOpenHelper(this);
        myDbHelper = new ExternalDbOpenHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        }catch(SQLException sqle){

            throw sqle;

        }
        //Все, база открыта!
        fillFreinds();

        setUpList();
    }

    private void setUpList() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, bus);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        //Подарим себе тост — для души
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view,
                                    int position,long id) {
                Toast.makeText(getApplicationContext(),
                        ((TextView) view).getText() +
                                " could be iDev's friend",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Извлечение элементов из базы данных
    private void fillFreinds() {
        bus = new ArrayList<String>();
        Log.v(LOG_TAG, "test");
        Cursor friendCursor = myDataBase.query("bus", null,null, null,null,null,null);
        friendCursor.moveToFirst();
        if(!friendCursor.isAfterLast()) {
            do {
                String number = friendCursor.getString(1);
                Log.v(LOG_TAG, number);
                bus.add(number);
            } while (friendCursor.moveToNext());
        }

        friendCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_recept, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean result = super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.action_main).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(ListRecept.this, MainActivity.class));
                return true;
            }
        });
        return result;
    }


}
