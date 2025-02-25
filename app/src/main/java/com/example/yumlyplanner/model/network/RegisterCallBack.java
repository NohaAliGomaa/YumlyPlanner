package com.example.yumlyplanner.model.network;

public interface RegisterCallBack {
    void showOnRegisterSuccess(String successMessage);
    void showOnRegisterError(String errorMessage);
}
