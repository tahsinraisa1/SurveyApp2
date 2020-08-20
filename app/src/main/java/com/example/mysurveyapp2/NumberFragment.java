package com.example.mysurveyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NumberFragment extends AppCompatActivity {

    TextView dd, qno;
    EditText txt;
    String dat="";
    int index;
    String Question="";
    String Type = "";
    String Required="";
    JSONArray jsonArray;
    DatabaseHelper myDB;

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_fragment);

        LinearLayout linearLayout = findViewById(R.id.lay_optionsnum);
        myDB = new DatabaseHelper(NumberFragment.this);

        dd = findViewById(R.id.numtext);
        qno = findViewById(R.id.numno);
        next = findViewById(R.id.numbutton);
        txt = findViewById(R.id.editTextPhone);

        final Intent intent = getIntent();
        dat = intent.getStringExtra("data");
        index = intent.getIntExtra("index", 0);

        qno.setText("Question: "+String.valueOf(index+1));


        try {
            jsonArray = new JSONArray(dat);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            Question = Question+jsonObject.get("question");
            Type = Type+jsonObject.get("type");
            Required = Required+jsonObject.get("required");
            dd.setText(Question);
            
        } catch (JSONException e) {
            e.printStackTrace();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Required.equals("true") && !txt.getText().toString().isEmpty()) || Required.equals("false"))
                {
                    try {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(index+1);
                        Type = (String) jsonObject1.get("type");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myDB.insertSData(Integer.valueOf(MainActivity.keyid), index+1, Question, txt.getText().toString());
                    if(index<jsonArray.length()-1)
                    {
                        if(Type.equals("text"))
                        {
                            Intent intent1 = new Intent(NumberFragment.this, TextFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("Checkbox"))
                        {
                            Intent intent1 = new Intent(NumberFragment.this, CheckBoxFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("multiple choice"))
                        {
                            Intent intent1 = new Intent(NumberFragment.this, MCQFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("number"))
                        {
                            Intent intent1 = new Intent(NumberFragment.this, NumberFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("dropdown"))
                        {
                            Intent intent1 = new Intent(NumberFragment.this, DropdownFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                    }
                    else {
                        Toast.makeText(NumberFragment.this, "Your response has been saved!", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(NumberFragment.this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                else
                {
                    Toast.makeText(NumberFragment.this, "This field is required!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}