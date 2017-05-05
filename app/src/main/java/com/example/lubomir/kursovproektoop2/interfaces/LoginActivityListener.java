package com.example.lubomir.kursovproektoop2.interfaces;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface LoginActivityListener {

    void loginButtonClicked(String nickname, String password) throws GeneralSecurityException, IOException;
    void loadRegisterFragment();
    void loadHelpFragment();
    void registerUser(String nickname, String password, String email, String fullname, int age,
                      String country, String city, String telephone, String type);
}
