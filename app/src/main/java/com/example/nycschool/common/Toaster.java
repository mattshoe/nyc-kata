package com.example.nycschool.common;

import android.content.Context;
import android.widget.Toast;

public class Toaster {
    public void show(Context context, String text, int duration) {
        Toast.makeText(context, text, duration).show();
    }
}
