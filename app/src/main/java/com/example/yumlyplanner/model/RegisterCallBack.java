package com.example.yumlyplanner.model;

public interface RegisterCallBack {
    void showOnRegisterSuccess(String successMessage);
    void showOnRegisterError(String errorMessage);
}
