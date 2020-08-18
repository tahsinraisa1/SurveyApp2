package com.example.mysurveyapp2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RadioAdapter extends ArrayAdapter {
    List list = new ArrayList();
    public RadioAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(@Nullable String object) {
        super.add(object);
        list.add(object);
    }


}
