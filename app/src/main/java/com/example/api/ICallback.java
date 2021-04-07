package com.example.api;

public interface ICallback {
    void onFinish(Sensor response);
    void onError(Exception error);
}