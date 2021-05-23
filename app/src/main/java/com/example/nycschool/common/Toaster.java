package com.example.nycschool.common;

import android.content.Context;
import android.widget.Toast;

public class Toaster implements IToaster {
    private Context context;

    public Toaster(Context context) {
        this.context = context;
    }

    public void show(String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
