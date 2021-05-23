package com.example.nycschool.common;

import android.content.Context;

public interface IToaster {
    void show(Context context, String text, int duration);
}
