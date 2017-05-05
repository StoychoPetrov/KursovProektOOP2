package com.example.lubomir.kursovproektoop2.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.interfaces.BaseActivityListener;

public class BaseActivity extends AppCompatActivity implements BaseActivityListener {

    //Pick airplane type type dialog attributes
    AlertDialog mPickTypeDialog;
    Button mLogout;
    Button mExit;

    //Display elements
    Display display;
    Point size;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_activity);
    }

    /**
     * Method which clear all fragments from back stack.
     */
    void clearBackStack() {
        FragmentManager manager = getFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    /**
     * Method which shows log out dialog. User can select log out or exit from app.
     * @param activity
     */
    void showLogoutDialog(final Activity activity) {
        LayoutInflater inflater = getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.logout_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewBox);

        mLogout = (Button) viewBox.findViewById(R.id.btnLogout);
        mExit = (Button) viewBox.findViewById(R.id.btnExit);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,LoginActivity.class);
                startActivity(intent);
                activity.finish();
            }
        });

        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPickTypeDialog = builder.create();
        mPickTypeDialog.show();
        mPickTypeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, getDisplayHeight() / 3);

    }

    /**
     * Method which return the height of device's display
     *
     * @return
     */
    @Override
    public int getDisplayHeight() {
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }
}
