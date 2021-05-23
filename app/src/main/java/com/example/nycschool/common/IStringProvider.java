package com.example.nycschool.common;

import androidx.annotation.StringRes;

public interface IStringProvider {
    String getStringResource(@StringRes int id);
}
