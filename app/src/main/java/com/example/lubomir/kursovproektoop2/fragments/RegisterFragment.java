package com.example.lubomir.kursovproektoop2.fragments;

import android.app.AlertDialog;
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

public class RegisterFragment extends BaseFragment {

    //Layout elements
    EditText mNickname, mPassword, mRepeatPassowrd, mEmail, mFullname, mAge, mCountry, mCity, mTelephone;
    TextView mPickType;
    Button mRegister;

    //Parameter elements
    String mNicknameStr, mPasswordStr, mEmailStr, mFullnameStr, mCountryStr, mCityStr, mTelephoneStr, mTypeStr;
    int mAgeInt = 0;

    //Pick type dialog attributes
    AlertDialog mPickTypeDialog;
    Button mDispatcher;
    Button mBuyer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_fragment, container, false);
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
        mRepeatPassowrd = (EditText) view.findViewById(R.id.etRepeatPassword);
        mEmail = (EditText) view.findViewById(R.id.etEmail);
        mFullname = (EditText) view.findViewById(R.id.etFullname);
        mAge = (EditText) view.findViewById(R.id.etAge);
        mCountry = (EditText) view.findViewById(R.id.etCountry);
        mCity = (EditText) view.findViewById(R.id.etCity);
        mTelephone = (EditText) view.findViewById(R.id.etTelephone);
        mPickType = (TextView) view.findViewById(R.id.tvPickType);
        mRegister = (Button) view.findViewById(R.id.btnRegister);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mPickType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickTypeDialog();
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNicknameStr = mNickname.getText().toString();
                mPasswordStr = mPassword.getText().toString();
                mEmailStr = mEmail.getText().toString();
                mFullnameStr = mFullname.getText().toString();
                if (!mAge.getText().toString().equals("")) {
                    mAgeInt = Integer.parseInt(mAge.getText().toString());
                }
                mCountryStr = mCountry.getText().toString();
                mCityStr = mCity.getText().toString();
                mTelephoneStr = mTelephone.getText().toString();
                mTypeStr = mPickType.getText().toString();
                if (mNicknameStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете потребител", Toast.LENGTH_LONG)
                            .show();
                } else if (mPasswordStr.length() < 6) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете парола с поне 6 символа", Toast.LENGTH_LONG)
                            .show();
                } else if (!mPasswordStr.equals(mRepeatPassowrd.getText().toString())) {
                    Toast.makeText(getActivity().getApplicationContext(), "Парола и повторена парола не съвпадат", Toast.LENGTH_LONG)
                            .show();
                } else if (mEmailStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете email", Toast.LENGTH_LONG)
                            .show();
                } else if (mFullnameStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете пълно име", Toast.LENGTH_LONG)
                            .show();
                } else if (mAgeInt == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете години", Toast.LENGTH_LONG)
                            .show();
                } else if (mCountryStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете държава", Toast.LENGTH_LONG)
                            .show();
                } else if (mCityStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете град", Toast.LENGTH_LONG)
                            .show();
                } else if (mTelephoneStr.equals("")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля попълнете телефон", Toast.LENGTH_LONG)
                            .show();
                } else if (!(mTypeStr.equals("Диспечър") || mTypeStr.equals("Купувач"))) {
                    Toast.makeText(getActivity().getApplicationContext(), "Моля изберете вид на потребителя", Toast.LENGTH_LONG)
                            .show();
                } else {
                    loginCallback.registerUser(mNicknameStr, mPasswordStr, mEmailStr, mFullnameStr,
                            mAgeInt, mCountryStr, mCityStr, mTelephoneStr, mTypeStr);
                }
            }
        });
    }

    /**
     * Method which show pick type dialog. User can choose dispatcher or buyer
     */
    void showPickTypeDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.user_type_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewBox);

        mDispatcher = (Button) viewBox.findViewById(R.id.btnDispatcher);
        mBuyer = (Button) viewBox.findViewById(R.id.btnBuyer);

        mDispatcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickType.setText("Диспечър");
                mPickTypeDialog.cancel();
            }
        });

        mBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickType.setText("Купувач");
                mPickTypeDialog.cancel();
            }
        });

        mPickTypeDialog = builder.create();
        mPickTypeDialog.show();
        mPickTypeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, baseCallback.getDisplayHeight() / 3);

    }
}
