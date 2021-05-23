package com.example.nycschool.common

import com.example.nycschool.web.RetrofitFactory

abstract class Repository(
    protected val retrofitFactory: RetrofitFactory
)