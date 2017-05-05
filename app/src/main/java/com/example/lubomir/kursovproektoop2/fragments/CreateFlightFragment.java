package com.example.lubomir.kursovproektoop2.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lubomir.kursovproektoop2.R;
import com.example.lubomir.kursovproektoop2.adapters.AirplanesAdapter;
import com.example.lubomir.kursovproektoop2.models.Airplane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CreateFlightFragment extends BaseFragment {

    //Layout elements
    TextView mFlightTimeText;
    Button mFromAirportBtn, mToAirportBtn, mAirplaneTypeBtn, mAirplaneBtn, mDateBtn, mTimeBtn, mCreateFlight;

    //Parameter elements
    String mFromAirport, mAirplaneType, mToAirport, mAirplane, mDate, mTime;
    int mFlightTime;

    //Pick airplane type type dialog attributes
    AlertDialog mPickTypeDialog;
    Button mPassenger;
    Button mFreight;

    //Pick airplane dialog attributes
    AlertDialog mPickAirplaneDialog;
    ListView mAirplaneListView;
    ArrayList<Airplane> mPassengerAirplaneList;
    ArrayList<Airplane> mFreightAirplaneList;
    AirplanesAdapter mAirplanesAdapter;

    //Date strings
    String mDay;
    String mMonth;
    String mYear;
    String mMinutes;
    String mHours;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_flight_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements(view);

        setOnClickListeners();

        getPassedData();

        sumFlightTime();
    }

    /**
     * Method which initialize fragment elements
     * @param view
     */
    private void initializeLayoutElements(View view) {
        mFromAirportBtn = (Button) view.findViewById(R.id.btnFromAirport);
        mToAirportBtn = (Button) view.findViewById(R.id.btnToAirport);
        mFlightTimeText = (TextView) view.findViewById(R.id.tvMaxFlightTime);
        mAirplaneTypeBtn = (Button) view.findViewById(R.id.btnChooseAirplaneType);
        mAirplaneBtn = (Button) view.findViewById(R.id.btnChooseAirplane);
        mDateBtn = (Button) view.findViewById(R.id.btnChooseDate);
        mTimeBtn = (Button) view.findViewById(R.id.btnChooseTime);
        mCreateFlight = (Button) view.findViewById(R.id.btnCreateFlight);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mFromAirportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatcherCallback.loadMapFragment("fromAirport", null);
            }
        });

        mToAirportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFromAirportBtn.getHint().toString().equals("От летище")) {
                    Toast.makeText(getActivity(), "Първо изберете летище от което желаете да е полетът", Toast.LENGTH_LONG).show();
                } else {
                    dispatcherCallback.loadMapFragment("toAirport", mFromAirport);
                }
            }
        });

        mAirplaneTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickAirplaneTypeDialog();
            }
        });

        mAirplaneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mAirplaneTypeBtn.getHint().toString().equals("Изберете вид на самолета")) {
                    showPickAirplaneDialog();
                } else {
                    Toast.makeText(getActivity(), "Първо изберете вид на самолета", Toast.LENGTH_LONG).show();
                }
            }
        });

        mDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        mTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        mCreateFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFromAirport = mFromAirportBtn.getHint().toString();
                mToAirport = mToAirportBtn.getHint().toString();
                mAirplaneType = mAirplaneTypeBtn.getHint().toString();
                mAirplane = mAirplaneBtn.getHint().toString();
                mDate = mDateBtn.getHint().toString();
                mTime = mTimeBtn.getHint().toString();
                if (mFromAirport.equals("От летище")) {
                    Toast.makeText(getActivity(), "Моля изберете от кое летище желаете да е полетът", Toast.LENGTH_LONG).show();
                } else if (mToAirport.equals("До летище")) {
                    Toast.makeText(getActivity(), "Моля изберете до кое летище желаете да е полетът", Toast.LENGTH_LONG).show();
                } else if (mAirplaneType.equals("Изберете вид на самолета")) {
                    Toast.makeText(getActivity(), "Моля изберете вид на самолета", Toast.LENGTH_LONG).show();
                } else if (mAirplane.equals("Изберете самолет")) {
                    Toast.makeText(getActivity(), "Моля изберете самолет", Toast.LENGTH_LONG).show();
                } else if (mDate.equals("Изберете дата")) {
                    Toast.makeText(getActivity(), "Моля изберете дата", Toast.LENGTH_LONG).show();
                } else if (mTime.equals("Изберете час")) {
                    Toast.makeText(getActivity(), "Моля изберете час", Toast.LENGTH_LONG).show();
                } else {
                    dispatcherCallback.createFlight(mFromAirport, mToAirport, mAirplane, mDate, mTime, mFlightTime);
                }
            }
        });
    }

    /**
     * Method which get passed data to the fragment
     */
    void getPassedData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mFromAirport = bundle.getString("fromAirport");
            mToAirport = bundle.getString("toAirport");
            if (mFromAirport != null) {
                mFromAirportBtn.setHint(mFromAirport);
                if (mToAirport != null) {
                    mToAirportBtn.setHint(mToAirport);
                }
            }
        }
    }

    /**
     * Method which show pick airplane dialog. User can select current airplane
     */
    void showPickAirplaneDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.pick_airplane_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewBox);

        mAirplaneListView = (ListView) viewBox.findViewById(R.id.lvAirplanes);
        if(mAirplaneTypeBtn.getHint().equals("Пътнически")) {
            loadPassengerAirplanes();
            mAirplanesAdapter = new AirplanesAdapter(getActivity(), mPassengerAirplaneList);
            mAirplaneListView.setAdapter(mAirplanesAdapter);
            mAirplaneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mAirplaneBtn.setHint(mPassengerAirplaneList.get(position).getName());
                    mPickAirplaneDialog.cancel();
                }
            });
        } else if(mAirplaneTypeBtn.getHint().equals("Товарен")) {
            loadFreightAirplanes();
            mAirplanesAdapter = new AirplanesAdapter(getActivity(), mFreightAirplaneList);
            mAirplaneListView.setAdapter(mAirplanesAdapter);
            mAirplaneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mAirplaneBtn.setHint(mFreightAirplaneList.get(position).getName());
                    mPickAirplaneDialog.cancel();
                }
            });
        }
        mPickAirplaneDialog = builder.create();
        mPickAirplaneDialog.show();
        mPickAirplaneDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    /**
     * Method which load passenger airplanes
     */
    void loadPassengerAirplanes() {
        mPassengerAirplaneList = new ArrayList<Airplane>();
        for (int i = 1; i <= 5; i++) {
            Airplane airplane = new Airplane();
            airplane.setWeight(3000);
            airplane.setMaxFlightTime(16);
            airplane.setTimeForService(8);
            airplane.setName("Пътнически самолет N:" + i);
            airplane.setType("passenger");
            airplane.setMaxPlaces(120);
            mPassengerAirplaneList.add(airplane);
        }
    }

    /**
     * Method which load freight airplanes
     */
    void loadFreightAirplanes() {
        mFreightAirplaneList = new ArrayList<Airplane>();
        for (int i = 1; i <= 5; i++) {
            Airplane airplane = new Airplane();
            airplane.setWeight(3000);
            airplane.setMaxFlightTime(16);
            airplane.setTimeForService(8);
            airplane.setName("Товарен самолет N:" + i);
            airplane.setType("freight");
            airplane.setMaxWeight(5000);
            mFreightAirplaneList.add(airplane);
        }
    }

    /**
     * Method which shows date picker and set the date that user pick.
     */
    private void showDatePicker() {
        int MILLIS_IN_SECOND = 1000;
        int SECONDS_IN_MINUTE = 60;
        int MINUTES_IN_HOUR = 60;
        int HOURS_IN_DAY = 24;
        long MILLISECONDS_IN_DAY = (long) MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY;

        DatePickerDialog.OnDateSetListener myDatePickerCallback = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mDay = getZeroPaddedNum(dayOfMonth);
                mMonth = getZeroPaddedNum(monthOfYear + 1);
                mYear = Integer.toString(year);
                mDateBtn.setHint(mDay + "-" + mMonth + "-" + mYear);
            }
        };

        DatePickerDialog mDatePickerDialog = new DatePickerDialog(getActivity(),
                myDatePickerCallback,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        mDatePickerDialog.getDatePicker().setMinDate(new Date().getTime() + MILLISECONDS_IN_DAY);
        mDatePickerDialog.show();
    }

    /**
     * Method for taking the correct value of date picker
     */
    private String getZeroPaddedNum(int num) {
        String str;
        if (num < 10 && num >= 0) {
            str = "0" + num;
        } else {
            str = String.valueOf(num);
        }
        return str;
    }

    /**
     * Method which shows time picker and set the time that user pick.
     */
    private void showTimePicker() {
        TimePickerDialog.OnTimeSetListener myTimePickerCallback = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mMinutes = getZeroPaddedNum(minute);
                mHours = getZeroPaddedNum(hourOfDay);
                mTimeBtn.setHint(mHours + ":" + mMinutes);
            }
        };

        TimePickerDialog mTimePickerDialog = new TimePickerDialog(getActivity(),
                myTimePickerCallback,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE), true);
        mTimePickerDialog.show();
    }

    /**
     * Method which count the flight time
     */
    private void sumFlightTime() {
        if (mFromAirport != null && mToAirport != null) {
            if (mFromAirport.equals("Летище София")) {
                if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 8;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 8;
                    setFlightTimeAndPriceText(mFlightTime);
                }
            } else if (mFromAirport.equals("Летище Варна")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 8;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 4;
                    setFlightTimeAndPriceText(mFlightTime);
                }
            } else if (mFromAirport.equals("Летище Пловдив")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                }
            } else if (mFromAirport.equals("Летище Бургас")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 8;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 4;
                    setFlightTimeAndPriceText(mFlightTime);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    setFlightTimeAndPriceText(mFlightTime);
                }
            }
        }
    }

    /**
     * Method which set flight time
     * @param time
     */
    private void setFlightTimeAndPriceText(int time) {
        mFlightTimeText.setText("Време на полет : " + time + " часа");
    }

    /**
     * Method which shows pick airplane type dialog. User can select passenger or freight.
     */
    void showPickAirplaneTypeDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.airplane_type_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewBox);

        mPassenger = (Button) viewBox.findViewById(R.id.btnPassenger);
        mFreight = (Button) viewBox.findViewById(R.id.btnFreight);

        mPassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAirplaneTypeBtn.setHint("Пътнически");
                mPickTypeDialog.cancel();
            }
        });

        mFreight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAirplaneTypeBtn.setHint("Товарен");
                mPickTypeDialog.cancel();
            }
        });

        mPickTypeDialog = builder.create();
        mPickTypeDialog.show();
        mPickTypeDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, baseCallback.getDisplayHeight() / 3);

    }
}
