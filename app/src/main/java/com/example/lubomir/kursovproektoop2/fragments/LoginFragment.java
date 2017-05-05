package com.example.lubomir.kursovproektoop2.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lubomir.kursovproektoop2.R;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class LoginFragment extends BaseFragment {

    //Layout elements
    EditText mNickname, mPassword;
    Button mLogin, mRegister;
    TextView mHelp;

    //Parameter elements
    String mNicknameStr, mPasswordStr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements(view);

        setOnClickListeners();
    }

    /**
     * Method which initialize fragment elements
     * @param view
     */
    void initializeLayoutElements(View view) {
        mNickname = (EditText) view.findViewById(R.id.etNickname);
        mPassword = (EditText) view.findViewById(R.id.etPassword);
        mLogin = (Button) view.findViewById(R.id.btnLogin);
        mRegister= (Button) view.findViewById(R.id.btnRegister);
        mHelp = (TextView) view.findViewById(R.id.tvHelp);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNicknameStr = mNickname.getText().toString();
                mPasswordStr = mPassword.getText().toString();
                if(mNicknameStr.isEmpty() || mNicknameStr == null || mNicknameStr.equals("")) {
                    Toast.makeText(getActivity(), "Моля въведете потребител", Toast.LENGTH_SHORT).show();
                } else if (mPasswordStr.isEmpty() || mPasswordStr == null || mPasswordStr.equals("")) {
                    Toast.makeText(getActivity(), "Моля въведете парола", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        loginCallback.loginButtonClicked(mNicknameStr, mPasswordStr);
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCallback.loadRegisterFragment();
            }
        });

        mHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCallback.loadHelpFragment();
            }
        });
    }
}
