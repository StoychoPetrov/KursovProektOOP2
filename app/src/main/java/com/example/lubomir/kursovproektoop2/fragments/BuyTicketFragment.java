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

public class BuyTicketFragment extends BaseFragment {

    //Layout elements
    TextView mFlightTimeText, mPriceText;
    Button mFromAirportBtn, mToAirportBtn, mAirplaneBtn, mDateBtn, mTimeBtn, mBuyTicket;

    //Parameter elements
    String mFromAirport, mToAirport, mAirplane, mDate, mTime;
    int mFlightTime, mPrice;

    //Pick type dialog attributes
    AlertDialog mPickAirplaneDialog;
    ListView mAirplaneListView;
    ArrayList<Airplane> mAirplaneList;
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
        return inflater.inflate(R.layout.buy_ticket_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeLayoutElements(view);

        setOnClickListeners();

        getPassedData();

        sumFlightTimeAndPrice();
    }

    /**
     * Method which initialize fragment elements
     * @param view
     */
    private void initializeLayoutElements(View view) {
        mFromAirportBtn = (Button) view.findViewById(R.id.btnFromAirport);
        mToAirportBtn = (Button) view.findViewById(R.id.btnToAirport);
        mFlightTimeText = (TextView) view.findViewById(R.id.tvMaxFlightTime);
        mPriceText = (TextView) view.findViewById(R.id.tvPrice);
        mAirplaneBtn = (Button) view.findViewById(R.id.btnChooseAirplane);
        mDateBtn = (Button) view.findViewById(R.id.btnChooseDate);
        mTimeBtn = (Button) view.findViewById(R.id.btnChooseTime);
        mBuyTicket = (Button) view.findViewById(R.id.btnBuyTicket);
    }

    /**
     * Method which set fragment views click listeners
     */
    void setOnClickListeners() {
        mFromAirportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyerCallback.loadMapFragment("fromAirport", null);
            }
        });

        mToAirportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFromAirportBtn.getHint().toString().equals("От летище")) {
                    Toast.makeText(getActivity(), "Първо изберете летище от което искате да летите", Toast.LENGTH_LONG).show();
                } else {
                    buyerCallback.loadMapFragment("toAirport", mFromAirport);
                }
            }
        });

        mAirplaneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickAirplaneDialog();
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

        mBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFromAirport = mFromAirportBtn.getHint().toString();
                mToAirport = mToAirportBtn.getHint().toString();
                mAirplane = mAirplaneBtn.getHint().toString();
                mDate = mDateBtn.getHint().toString();
                mTime = mTimeBtn.getHint().toString();
                mFlightTime = 6;
                mPrice = 5;
                if(mFromAirportBtn.getHint().toString().equals("От летище")) {
                    Toast.makeText(getActivity(), "Моля изберете летище от което искате да летите", Toast.LENGTH_LONG).show();
                } else if(mToAirportBtn.getHint().toString().equals("До летище")) {
                    Toast.makeText(getActivity(), "Моля изберете летище до което искате да летите", Toast.LENGTH_LONG).show();
                } else if(mAirplane.equals("Изберете самолет")){
                    Toast.makeText(getActivity(), "Моля изберете самолет с който искате да летите", Toast.LENGTH_LONG).show();
                } else if(mDate.equals("Изберете дата")) {
                    Toast.makeText(getActivity(), "Моля изберете дата", Toast.LENGTH_LONG).show();
                } else if(mTime.equals("Изберете час")) {
                    Toast.makeText(getActivity(), "Моля изберете час", Toast.LENGTH_LONG).show();
                } else {
                    buyerCallback.buyTicket(mFromAirport, mToAirport, mAirplane, mDate, mTime, mFlightTime, mPrice);
                }
            }
        });
    }

    /**
     * Method which get passed data to the fragment
     */
    void getPassedData() {
        Bundle bundle = getArguments();
        if(bundle != null) {
            mFromAirport = bundle.getString("fromAirport");
            mToAirport = bundle.getString("toAirport");
            if(mFromAirport != null) {
                mFromAirportBtn.setHint(mFromAirport);
                if(mToAirport != null) {
                    mToAirportBtn.setHint(mToAirport);
                }
            }
        }
    }

    /**
     * Method which show pick airplane dialog. User can select with which airplane wants to fly
     */
    void showPickAirplaneDialog() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View viewBox = inflater.inflate(R.layout.pick_airplane_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(viewBox);

        mAirplaneListView = (ListView) viewBox.findViewById(R.id.lvAirplanes);
        loadAirplanes();
        mAirplanesAdapter = new AirplanesAdapter(getActivity(), mAirplaneList);
        mAirplaneListView.setAdapter(mAirplanesAdapter);
        mAirplaneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAirplaneBtn.setHint(mAirplaneList.get(position).getName());
                mPickAirplaneDialog.cancel();
            }
        });
        mPickAirplaneDialog = builder.create();
        mPickAirplaneDialog.show();
        mPickAirplaneDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    /**
     * Method which create list with airplanes
     */
    void loadAirplanes() {
        mAirplaneList = new ArrayList<Airplane>();
        mAirplaneList.add(new Airplane("Airplane 1"));
        mAirplaneList.add(new Airplane("Airplane 2"));
        mAirplaneList.add(new Airplane("Airplane 3"));
        mAirplaneList.add(new Airplane("Airplane 4"));
        mAirplaneList.add(new Airplane("Airplane 5"));
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
     * Method which shows time picker and set the time that user pick
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
     * Method which count the flight time and the price of the flight
     */
    private void sumFlightTimeAndPrice() {
        if(mFromAirport != null && mToAirport !=null) {
            if (mFromAirport.equals("Летище София")) {
                if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 8;
                    mPrice = 120;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 8;
                    mPrice = 120;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                }
            } else if (mFromAirport.equals("Летище Варна")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 8;
                    mPrice = 120;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 4;
                    mPrice = 80;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                }
            } else if (mFromAirport.equals("Летище Пловдив")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Бургас")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                }
            } else if (mFromAirport.equals("Летище Бургас")) {
                if (mToAirport.equals("Летище София")) {
                    mFlightTime = 8;
                    mPrice = 120;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Варна")) {
                    mFlightTime = 4;
                    mPrice = 80;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                } else if (mToAirport.equals("Летище Пловдив")) {
                    mFlightTime = 6;
                    mPrice = 100;
                    setFlightTimeAndPriceText(mFlightTime, mPrice);
                }
            }
        }
    }

    /**
     * Method which set flight time and price into their TextViews
     * @param time
     * @param price
     */
    private void setFlightTimeAndPriceText(int time, int price) {
        mFlightTimeText.setText("Време на полет : " + time + " часа");
        mPriceText.setText("Цена : " + price + " $");
    }

}
