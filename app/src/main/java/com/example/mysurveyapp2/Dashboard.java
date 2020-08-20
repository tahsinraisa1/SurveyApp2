package com.example.mysurveyapp2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    DatabaseHelper myDB;
    TextView dashr, dasht;
    ListView lst;
    Cursor c;
    ArrayList<String> idp = new ArrayList<>();
    ArrayList<String> tsp = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        lst = findViewById(R.id.dashlist);
        customAdapter ca = new customAdapter();

        myDB = new DatabaseHelper(Dashboard.this);
        c = myDB.getTrackAll();
        if(c.getCount()==0)
        {
            Toast toast = Toast.makeText(this, "Nothing found", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            while (c.moveToNext()){
                idp.add(c.getString(0));
                tsp.add(c.getString(1));

            }

        }
        lst.setAdapter(ca);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Dashboard.this, ResponseDetail.class);
                intent.putExtra("id", idp.get(i));
                intent.putExtra("tims", tsp.get(i));
                startActivity(intent);
            }
        });




    }

    class customAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return c.getCount();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.dash_row,null);
            dashr = view.findViewById(R.id.id_dash);
            dashr.setText(idp.get(i));
            dasht = view.findViewById(R.id.ts_dash);
            dasht.setText(tsp.get(i));

            return view;
        }
    }

}