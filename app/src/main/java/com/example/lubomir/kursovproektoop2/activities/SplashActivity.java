package com.example.lubomir.kursovproektoop2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lubomir.kursovproektoop2.R;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {

    //Students parameters
    ListView mStudentsList;
    ArrayList<String> students;
    ArrayAdapter<String> mStudentsAdapter;

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 10000;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash_activity);
        mStudentsList = (ListView) findViewById(R.id.lvStudents);
        students = new ArrayList<>();
        students.add("Любомир Бабев , СИТ курс:III група:2а , 61362118");
        students.add("Кристиан Филев , СИТ курс:III група:2а , 61362135");
        students.add("Боян Русев , СИТ      курс:II група:3 , 61462196");
        mStudentsAdapter = new ArrayAdapter<String>(this, R.layout.students_list_item, R.id.tvStudent, students);
        mStudentsList.setAdapter(mStudentsAdapter);
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}