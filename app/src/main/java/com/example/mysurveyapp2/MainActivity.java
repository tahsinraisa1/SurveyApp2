package com.example.mysurveyapp2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static String keyid;
    Button start;
    TextView dash;
    DatabaseHelper myDB;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(MainActivity.this);
        Date date = new Date();
        final Timestamp ts=new Timestamp(date.getTime());

        dash = findViewById(R.id.dashlink);

        start = findViewById(R.id.button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInsertedT = myDB.insertTData(ts);
                if(isInsertedT==true)
                {
                    c = myDB.getTrack();
                    while (c.moveToNext())
                    {
                        keyid = c.getString(0);
                    }
                    //Toast toast = Toast.makeText(MainActivity.this, "Successfully trackID inserted", Toast.LENGTH_LONG);
                    //toast.show();
                    FetchQuestion fq = new FetchQuestion(MainActivity.this);
                    fq.execute();
                }
                else
                {
                    Toast toast = Toast.makeText(MainActivity.this, "Not inserted trackID", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

        dash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}