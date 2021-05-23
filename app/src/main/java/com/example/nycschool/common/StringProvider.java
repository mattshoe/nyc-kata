package com.example.nycschool.common;

import android.content.Context;
import androidx.annotation.StringRes;

public class StringProvider implements IStringProvider {
    private Context context;

    public StringProvider(Context context) {
        this.context = context;
    }

    @Override
    public String getStringResource(@StringRes int id) {
        return context.getResources().getString(id);
    }
}
