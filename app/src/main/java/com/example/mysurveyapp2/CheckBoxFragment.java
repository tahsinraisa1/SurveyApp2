package com.example.mysurveyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CheckBoxFragment extends AppCompatActivity {

    public static TextView dd1, qno1;
    String dat="";
    int index;
    String Question="";
    String Type = "";
    String Required="";
    String[] Optionss;
    JSONArray jsonArray;
    DatabaseHelper myDB;

    String checkAns="";

    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_box_fragment);

        LinearLayout linearLayout = findViewById(R.id.lay_options1);
        final ArrayList<String> checkSelect = new ArrayList<>();

        dd1 = findViewById(R.id.cbtext);
        qno1 = findViewById(R.id.cbno);
        next = findViewById(R.id.cbbutton);
        myDB = new DatabaseHelper(CheckBoxFragment.this);

        final Intent intent = getIntent();
        dat = intent.getStringExtra("data");
        index = intent.getIntExtra("index", 0);

        qno1.setText("Question: "+String.valueOf(index+1));


        try {
            jsonArray = new JSONArray(dat);
            JSONObject jsonObject = (JSONObject) jsonArray.get(index);
            Question = Question+jsonObject.get("question");
            Type = Type+jsonObject.get("type");
            Required = Required+jsonObject.get("required");
            Optionss = String.valueOf(jsonObject.get("options")).split(", ");
            dd1.setText(Question);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0;i<Optionss.length;i++)
        {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setId(i);
            checkBox.setText(Optionss[i]);

            linearLayout.addView(checkBox);

            final int finalI = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b)
                    {
                        checkSelect.add(compoundButton.getText().toString());
                    }
                    else
                    {
                        checkSelect.remove(checkSelect.indexOf(compoundButton.getText().toString()));
                    }
                }
            });
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Required.equals("true") && checkSelect.size()!=0) || Required.equals("false"))
                {
                    try {
                        JSONObject jsonObject1 = (JSONObject) jsonArray.get(index+1);
                        Type = (String) jsonObject1.get("type");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    myDB.insertSData(Integer.valueOf(MainActivity.keyid), index+1, Question, checkSelect.toString());
                    if(index<jsonArray.length()-1)
                    {

                        if(Type.equals("text"))
                        {
                            Intent intent1 = new Intent(CheckBoxFragment.this, TextFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("Checkbox"))
                        {
                            Intent intent1 = new Intent(CheckBoxFragment.this, CheckBoxFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("multiple choice"))
                        {
                            Intent intent1 = new Intent(CheckBoxFragment.this, MCQFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("number"))
                        {
                            Intent intent1 = new Intent(CheckBoxFragment.this, NumberFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                        else if(Type.equals("dropdown"))
                        {
                            Intent intent1 = new Intent(CheckBoxFragment.this,DropdownFragment.class);
                            intent1.putExtra("data",dat);
                            intent1.putExtra("index",index+1);
                            startActivity(intent1);
                        }
                    }
                    else {
                        Toast.makeText(CheckBoxFragment.this, "Your response has been saved!", Toast.LENGTH_LONG).show();
                        Intent intent1 = new Intent(CheckBoxFragment.this, MainActivity.class);
                        startActivity(intent1);
                    }
                }
                else
                {
                    Toast.makeText(CheckBoxFragment.this, "This field is required!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}