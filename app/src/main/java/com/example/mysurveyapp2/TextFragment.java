package com.example.mysurveyapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.BreakIterator;

public class TextFragment extends AppCompatActivity {

    public static TextView dd, qno;
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
        setContentView(R.layout.activity_text_fragment);

        dd = findViewById(R.id.textView7);
        qno = findViewById(R.id.textView8);
        next = findViewById(R.id.button2);

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
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Type.equals("text"))
                {
                    Intent intent1 = new Intent(TextFragment.this, TextFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("Checkbox"))
                {
                    Intent intent1 = new Intent(TextFragment.this, CheckBoxFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("multiple choice"))
                {
                    Intent intent1 = new Intent(TextFragment.this, MCQFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("number"))
                {
                    Intent intent1 = new Intent(TextFragment.this, NumberFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
                else if(Type.equals("dropdown"))
                {
                    Intent intent1 = new Intent(TextFragment.this,DropdownFragment.class);
                    intent1.putExtra("data",dat);
                    intent1.putExtra("index",index+1);
                    startActivity(intent1);
                }
            }
        });

    }
}