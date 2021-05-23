package com.example.nycschool.common;

import io.reactivex.Scheduler;

public interface ISchedulerProvider {
    Scheduler io();
    Scheduler ui();
}
