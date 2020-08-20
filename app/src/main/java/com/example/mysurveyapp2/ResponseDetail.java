package com.example.mysurveyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResponseDetail extends AppCompatActivity {
    TextView detail;
    DatabaseHelper myDB;
    String iid, timest;
    Cursor c;
    ListView lst;

    ArrayList<String> ques = new ArrayList<>();
    ArrayList<String> ans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response_detail);
        Intent intent = getIntent();
        iid = intent.getStringExtra("id");
        timest = intent.getStringExtra("tims");
        lst = findViewById(R.id.det1);
        customAdapter ca = new customAdapter();

        myDB = new DatabaseHelper(ResponseDetail.this);
        c = myDB.getAllData(Integer.valueOf(iid));
        if(c.getCount()==0)
        {
            Toast toast = Toast.makeText(this, "Nothing found", Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            while (c.moveToNext()){
                ques.add(c.getString(2));
                ans.add(c.getString(3));
            }
        }
        lst.setAdapter(ca);

    }
    class customAdapter extends BaseAdapter {

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
            view = getLayoutInflater().inflate(R.layout.detail_row,null);
            detail = view.findViewById(R.id.dv1);
            detail.setText("Response No.: "+iid+"\nTimestamp: "+timest+"\n\nQuestion: "+ques.get(i)+"\nYour answer: "+ans.get(i));

            return view;
        }
    }
}