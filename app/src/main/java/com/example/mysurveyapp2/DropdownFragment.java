package com.example.mysurveyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DropdownFragment extends AppCompatActivity {

    TextView dd, qno;
    String dat="";
    int index;
    String Question="";
    String Type = "";
    String Required="";
    String[] Optionss;

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dropdown_fragment);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        dd = findViewById(R.id.ddtext);
        qno = findViewById(R.id.ddno);
        next = findViewById(R.id.ddbutton);

        final Intent intent = getIntent();
        dat = intent.getStringExtra("data");
        index = intent.getIntExtra("index", 0);

        qno.setText("Question: "+String.valueOf(index+1));


        try {
            JSONArray jsonArray = new JSONArray(dat);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            Question = Question+jsonObject.get("question");
            Type = Type+jsonObject.get("type");
            Required = Required+jsonObject.get("required");
            Optionss = String.valueOf(jsonObject.get("options")).split(", ");
            dd.setText(Question);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, Optionss);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast toast = Toast.makeText(DropdownFragment.this, "You cannot leave this field blank!", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type.equals("text"))
                {
                    Intent intent1 = new Intent(DropdownFragment.this, TextFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("Checkbox"))
                {
                    Intent intent1 = new Intent(DropdownFragment.this, CheckBoxFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("multiple choice"))
                {
                    Intent intent1 = new Intent(DropdownFragment.this, MCQFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("number"))
                {
                    Intent intent1 = new Intent(DropdownFragment.this, NumberFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("dropdown"))
                {
                    Intent intent1 = new Intent(DropdownFragment.this,DropdownFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
            }
        });

    }
}