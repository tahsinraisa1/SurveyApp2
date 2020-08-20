package com.example.mysurveyapp2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mysurveyapp2.TextFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchQuestion extends AsyncTask<Void, Void, Void> {


    String data="";
    static int i=0;
    String Type="";

    private Context context;

    public FetchQuestion(Context context) {
        this.context = context;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://example-response.herokuapp.com/getSurvey");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line!=null)
            {
                line = bufferedReader.readLine();
                data = data+line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        try {
            JSONArray jsonArray = new JSONArray(data);
            JSONObject jsonObject = (JSONObject) jsonArray.get(i);
            Type = Type+jsonObject.get("type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Type.equals("text"))
        {
            Intent intent1 = new Intent(context, TextFragment.class);
            intent1.putExtra("data",data);
            intent1.putExtra("index",i);
            context.startActivity(intent1);
        }
        else if(Type.equals("Checkbox"))
        {
            Intent intent1 = new Intent(context, CheckBoxFragment.class);
            intent1.putExtra("data",data);
            intent1.putExtra("index",i);
            context.startActivity(intent1);
        }
        else if(Type.equals("multiple choice"))
        {
            Intent intent1 = new Intent(context, MCQFragment.class);
            intent1.putExtra("data",data);
            intent1.putExtra("index",i);
            context.startActivity(intent1);
        }
        else if(Type.equals("number"))
        {
            Intent intent1 = new Intent(context, NumberFragment.class);
            intent1.putExtra("data",data);
            intent1.putExtra("index",i);
            context.startActivity(intent1);
        }
        else if(Type.equals("dropdown"))
        {
            Intent intent1 = new Intent(context,DropdownFragment.class);
            intent1.putExtra("data",data);
            intent1.putExtra("index",i);
            context.startActivity(intent1);
        }


    }

}
