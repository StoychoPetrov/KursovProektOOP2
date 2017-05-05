package com.example.lubomir.kursovproektoop2.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.fragments.HelpFragment;
import com.example.lubomir.kursovproektoop2.fragments.LoginFragment;
import com.example.lubomir.kursovproektoop2.fragments.RegisterFragment;
import com.example.lubomir.kursovproektoop2.interfaces.LoginActivityListener;
import com.example.lubomir.kursovproektoop2.models.Database;
import com.example.lubomir.kursovproektoop2.models.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public class LoginActivity extends BaseActivity implements LoginActivityListener {

    //Database instance
    Database database = Database.getInstance(this);

    //Fragment elements
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //Toolbar elements
    Toolbar mToolbar;
    TextView mToolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        initializeLayoutElements();

        loadLoginFragment();
    }

    /**
     * Method which initialize activities elements
     */
    void initializeLayoutElements() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
    }

    /**
     * Method which set function when this intent is created
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        loadLoginFragment();
    }

    /**
     * Method which load LoginFragment and set toolbar title
     */
    void loadLoginFragment() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, new LoginFragment(), "LoginFragment");
        fragmentTransaction.addToBackStack("LoginFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("LoginFragment");
    }

    /**
     * Method which check if current user and his password compare in the database and if they are
     * check what is the type of the user and create intent to new activity
     *
     * @param nickname
     * @param password
     * @throws GeneralSecurityException
     * @throws IOException
     */
    @Override
    public void loginButtonClicked(String nickname, String password) throws GeneralSecurityException, IOException {
        String passwordCrypt = database.encrypt(password);
        if (database.searchPass(nickname, passwordCrypt)) {
            Intent intent;
            if(database.getUser(nickname).getType().equals("Купувач")) {
                intent = new Intent(this, BuyerActivity.class);
                intent.putExtra("nickname", database.getUser(nickname).getNickname());
                startActivity(intent);
            } else if(database.getUser(nickname).getType().equals("Диспечър")) {
                intent = new Intent(this, DispatcherActivity.class);
                intent.putExtra("nickname", database.getUser(nickname).getNickname());
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(this, "Потребителят или паролата са грешни", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method which load RegisterFragment and set toolbar title
     */
    @Override
    public void loadRegisterFragment() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, new RegisterFragment(), "RegisterFragment");
        fragmentTransaction.addToBackStack("RegisterFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("RegisterFragment");
    }

    /**
     * Method which load HelpFragment and set toolbar title
     */
    @Override
    public void loadHelpFragment() {
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragments, new HelpFragment(), "HelpFragment");
        fragmentTransaction.addToBackStack("HelpFragment");
        fragmentTransaction.commit();
        setToolbarTitleText("HelpFragment");
    }

    /**
     * Method which create new user, save it into database,clear all fragments from back stack
     * and load LoginFragment
     *
     * @param nickname
     * @param password
     * @param email
     * @param fullname
     * @param age
     * @param country
     * @param city
     * @param telephone
     * @param type
     */
    @Override
    public void registerUser(String nickname, String password, String email, String fullname, int age,
                             String country, String city, String telephone, String type) {
        if (database.isUserExisting(nickname)) {
            Toast.makeText(this, "Потребителят вече съществува, опитайте пак", Toast.LENGTH_LONG).show();
        } else if (database.emailExist(email)) {
            Toast.makeText(this, "Този email вече съществува, опитайте пак", Toast.LENGTH_LONG).show();
        } else {
            User user = new User();
            user.setNickname(nickname);
            user.setPassword(password);
            user.setEmail(email);
            user.setName(fullname);
            user.setAge(age);
            user.setCountry(country);
            user.setCity(city);
            user.setTelephone(telephone);
            user.setType(type);
            try {
                database.addUser(user);
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            clearBackStack();
            loadLoginFragment();
            Toast.makeText(this, "Регистрацията беше успешна", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Method that set current toolbar title by passed fragment name
     * @param fragmentName
     */
    void setToolbarTitleText(String fragmentName) {
        switch (fragmentName) {
            case "LoginFragment":
                mToolbarTitle.setText("Вход");
                break;
            case "RegisterFragment":
                mToolbarTitle.setText("Регистрация");
                break;
            case "HelpFragment":
                mToolbarTitle.setText("Помощ");
        }
    }

    /**
     * Method which set function to back button
     */
    @Override
    public void onBackPressed() {
        fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
            setToolbarTitleText(fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 2).getName());
        } else {
            super.onBackPressed();
        }
    }

}
