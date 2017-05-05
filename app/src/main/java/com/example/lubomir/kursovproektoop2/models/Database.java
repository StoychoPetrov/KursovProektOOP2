package com.example.lubomir.kursovproektoop2.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class Database extends SQLiteOpenHelper {

    //Initialization of database;
    private static Database database;

    //LOG TAG
    private static final String LOG = Database.class.getName();

    //The name of database
    private static final String DATABASE_NAME = "airplanesManager";

    //The version of database
    private static final int DATABASE_VERSION = 1;

    //Table for users
    private static final String TABLE_USERS = "users";

    //Table for airports
    private static final String TABLE_AIRPORTS = "airports";

    //Table for airplanes
    private static final String TABLE_AIRPLANES = "airplanes";

    //Table for flights
    private static final String TABLE_FLIGHTS = "flights";

    //Table for tickets
    private static final String TABLE_TICKETS = "tickets";

    //User username
    private static final String KEY_USER = "user";
    //User password
    private static final String KEY_PASSWORD = "password";
    //User email
    private static final String KEY_EMAIL = "email";
    //User name
    private static final String KEY_USER_NAME = "user_name";
    //User age
    private static final String KEY_AGE = "age";
    //User country
    private static final String KEY_COUNTRY = "country";
    //User city
    private static final String KEY_CITY = "city";
    //User telephone
    private static final String KEY_TELEPHONE = "telephone";
    //User type
    private static final String KEY_USER_TYPE = "user_type";

    //Airport latitude
    private static final String KEY_LATITUDE = "latitude";
    //Airport longitude
    private static final String KEY_LONGITUDE = "longitude";
    //Airport runway
    private static final String KEY_RUNWAY = "runway";
    //Airport berths
    private static final String KEY_BERTHS = "berths";
    //Airport name
    private static final String KEY_AIRPORT_NAME = "airport_name";
    //Airport places count
    private static final String KEY_PLACES = "places";

    //Airplane weight
    private static final String KEY_WEIGHT = "weight";
    //Airplane maximum flight time
    private static final String KEY_MAX_FLIGHT_TIME = "flight_time";
    //Airplane time for service
    private static final String KEY_TIME_FOR_SERVICE = "service_time";
    //Airplane name
    private static final String KEY_AIRPLANE_NAME = "airplane_name";
    //Airplane type
    private static final String KEY_AIRPLANE_TYPE = "airplane_type";
    //Airplane maximum weight
    private static final String KEY_MAX_WEIGHT = "max_weight";
    //Airplane maximum places
    private static final String KEY_MAX_PLACES = "max_places";

    //Flight from airport
    private static final String KEY_FLIGHT_FROM_AIRPORT = "flight_from_airport";
    //Flight to airport
    private static final String KEY_FLIGHT_TO_AIRPORT = "flight_to_airport";
    //Flight airplane
    private static final String KEY_FLIGHT_AIRPLANE = "flight_airplane";
    //Flight date
    private static final String KEY_FLIGHT_DATE = "flight_date";
    //Flight time
    private static final String KEY_FLIGHT_TIME = "flight_time";
    //Flight max time
    private static final String KEY_FLIGHT_MAX_TIME = "flight_max_time";

    //Ticket from airport
    private static final String KEY_TICKET_FROM_AIRPORT = "ticket_from_airport";
    //Ticket to airport
    private static final String KEY_TICKET_TO_AIRPORT = "ticket_to_airport";
    //Ticket airplane
    private static final String KEY_TICKET_AIRPLANE = "ticket_airplane";
    //Ticket date
    private static final String KEY_TICKET_DATE = "ticket_date";
    //Ticket time
    private static final String KEY_TICKET_TIME = "ticket_time";
    //Ticket max flight time
    private static final String KEY_TICKET_MAX_FLIGHT_TIME = "ticket_max_flight_time";
    //Ticket price
    private static final String KEY_TICKET_PRICE = "ticket_price";

    //Creating users table
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + KEY_USER + " TEXT," + KEY_PASSWORD + " TEXT," + KEY_EMAIL + " TEXT,"
            + KEY_USER_NAME + " TEXT," + KEY_AGE + " INTEGER," + KEY_COUNTRY + " TEXT,"
            + KEY_CITY + " TEXT," + KEY_TELEPHONE + " TEXT," + KEY_USER_TYPE + " TEXT" + ")";

    //Creating airports table
    private static final String CREATE_AIRPORTS_TABLE = "CREATE TABLE " + TABLE_AIRPORTS + "("
            + KEY_LATITUDE + " REAL," + KEY_LONGITUDE + " REAL," + KEY_RUNWAY + " INTEGER,"
            + KEY_BERTHS + " INTEGER," + KEY_AIRPORT_NAME + " TEXT," + KEY_PLACES + " INTEGER"
            + KEY_USER_TYPE + " TEXT" + ")";

    //Creating airplanes table
    private static final String CREATE_AIRPLANES_TABLE = "CREATE TABLE " + TABLE_AIRPLANES + "("
            + KEY_WEIGHT + " INTEGER," + KEY_MAX_FLIGHT_TIME + " INTEGER," + KEY_TIME_FOR_SERVICE
            + " INTEGER," + KEY_AIRPLANE_NAME + " TEXT," + KEY_AIRPLANE_TYPE + " TEXT," + KEY_MAX_WEIGHT
            + " INTEGER," + KEY_MAX_PLACES + " INTEGER" + ")";

    //Creating flights table
    private static final String CREATE_FLIGHTS_TABLE = "CREATE TABLE " + TABLE_FLIGHTS + "("
            + KEY_FLIGHT_FROM_AIRPORT + " TEXT," + KEY_FLIGHT_TO_AIRPORT + " TEXT," + KEY_FLIGHT_AIRPLANE
            + " TEXT," + KEY_FLIGHT_DATE + " TEXT," + KEY_FLIGHT_TIME + " TEXT," + KEY_FLIGHT_MAX_TIME
            + " INTEGER" + ")";

    //Creating tickets table
    private static final String CREATE_TICKETS_TABLE = "CREATE TABLE " + TABLE_TICKETS + "("
            + KEY_TICKET_FROM_AIRPORT + " TEXT," + KEY_TICKET_TO_AIRPORT + " TEXT," + KEY_TICKET_AIRPLANE
            + " TEXT," + KEY_TICKET_DATE + " TEXT," + KEY_TICKET_TIME + " TEXT," + KEY_TICKET_MAX_FLIGHT_TIME
            + " INTEGER," + KEY_TICKET_PRICE + " INTEGER" + ")";

    //Define the database;
    SQLiteDatabase db;

    //Define the values;
    ContentValues values;

    //Define the cursor;
    Cursor cursor;

    //Crypt elements
    private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
    private static final byte[] SALT = {
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
            (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    //Singleton pattern
    public static Database getInstance(Context context) {
        if (database == null) {
            database = new Database(context);
        }
        return database;
    }

    //DatabaseHelper class constructor
    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Creating all tables in the app
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_AIRPORTS_TABLE);
        db.execSQL(CREATE_AIRPLANES_TABLE);
        db.execSQL(CREATE_FLIGHTS_TABLE);
        db.execSQL(CREATE_TICKETS_TABLE);
    }

    //Upgrading the tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRPORTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AIRPLANES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        onCreate(db);
    }

    /**
     * Method which encrypt current String.
     *
     * @param property
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    public static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Encoder().encode(bytes);
    }

    /**
     * Method which decrypt current String.
     *
     * @param property
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    public static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Decoder().decodeBuffer(property);
    }

    /**
     * Method which add new user to database
     *
     * @param user
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void addUser(User user) throws GeneralSecurityException, UnsupportedEncodingException {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_USER, user.getNickname());
        values.put(KEY_PASSWORD, encrypt(user.getPassword()));
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_USER_NAME, user.getName());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_COUNTRY, user.getCountry());
        values.put(KEY_CITY, user.getCity());
        values.put(KEY_TELEPHONE, user.getTelephone());
        values.put(KEY_USER_TYPE, user.getType());
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    /**
     * Method which check if the user exist in the database
     *
     * @param nickname
     * @return
     */
    public boolean isUserExisting(String nickname) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USER + " = ? ";
        cursor = db.rawQuery(selectQuery, new String[]{nickname});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method which check if the email exist in the database
     *
     * @param email
     * @return
     */
    public boolean emailExist(String email) {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_EMAIL + " = ? ";
        cursor = db.rawQuery(selectQuery, new String[]{email});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method which check if the password compare with current user password
     *
     * @param nickname
     * @param password
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public boolean searchPass(String nickname, String password) throws GeneralSecurityException, IOException {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM "
                + TABLE_USERS + " WHERE "
                + KEY_USER + " = ? AND "
                + KEY_PASSWORD + " = ? ";
        cursor = db.rawQuery(selectQuery, new String[]{nickname, password});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method which get current user by id.
     *
     * @param nickname
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public User getUser(String nickname) throws GeneralSecurityException, IOException {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " WHERE " + KEY_USER + " = ? ";
        cursor = db.rawQuery(selectQuery, new String[]{nickname});
        if (cursor != null) {
            cursor.moveToFirst();
        }
        User user = new User();
        user.setNickname(cursor.getString((cursor.getColumnIndex(KEY_USER))));
        user.setPassword(decrypt(cursor.getString((cursor.getColumnIndex(KEY_PASSWORD)))));
        user.setEmail(cursor.getString((cursor.getColumnIndex(KEY_EMAIL))));
        user.setName(cursor.getString((cursor.getColumnIndex(KEY_USER_NAME))));
        user.setAge(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_AGE)))));
        user.setCountry(cursor.getString((cursor.getColumnIndex(KEY_COUNTRY))));
        user.setCity(cursor.getString((cursor.getColumnIndex(KEY_CITY))));
        user.setTelephone(cursor.getString((cursor.getColumnIndex(KEY_TELEPHONE))));
        user.setType(cursor.getString((cursor.getColumnIndex(KEY_USER_TYPE))));
        return user;
    }

    /**
     * Method which add new user to database
     *
     * @param airport
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void addAirport(Airport airport) throws GeneralSecurityException, UnsupportedEncodingException {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_LATITUDE, airport.getLatitude());
        values.put(KEY_LONGITUDE, airport.getLongitude());
        values.put(KEY_RUNWAY, airport.getRunway());
        values.put(KEY_BERTHS, airport.getBerths());
        values.put(KEY_AIRPORT_NAME, airport.getName());
        values.put(KEY_PLACES, airport.getPlaces());
        db.insert(TABLE_AIRPORTS, null, values);
        db.close();
    }

    /**
     * Method which get current user by id.
     *
     * @param airportName
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Airport getAirport(String airportName) throws GeneralSecurityException, IOException {
        db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_AIRPORTS + " WHERE " + KEY_AIRPORT_NAME + " = " + airportName;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Airport airport = new Airport();
        airport.setLatitude(Double.parseDouble(cursor.getString((cursor.getColumnIndex(KEY_LATITUDE)))));
        airport.setLongitude(Double.parseDouble(cursor.getString((cursor.getColumnIndex(KEY_LONGITUDE)))));
        airport.setRunway(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_RUNWAY)))));
        airport.setBerths(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_BERTHS)))));
        airport.setName(cursor.getString((cursor.getColumnIndex(KEY_AIRPORT_NAME))));
        airport.setPlaces(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_PLACES)))));
        return airport;
    }

    /**
     * Method which get list of all airports.
     *
     * @return
     */
    public ArrayList<Airport> getAllAirports() {
        ArrayList<Airport> airports = new ArrayList<Airport>();
        String selectQuery = "SELECT  * FROM " + TABLE_AIRPORTS;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Airport airport = new Airport();
                airport.setLatitude(Double.parseDouble(cursor.getString((cursor.getColumnIndex(KEY_LATITUDE)))));
                airport.setLongitude(Double.parseDouble(cursor.getString((cursor.getColumnIndex(KEY_LONGITUDE)))));
                airport.setRunway(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_RUNWAY)))));
                airport.setBerths(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_BERTHS)))));
                airport.setName(cursor.getString((cursor.getColumnIndex(KEY_AIRPORT_NAME))));
                airport.setPlaces(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_PLACES)))));
                airports.add(airport);
            } while (cursor.moveToNext());
        }
        return airports;
    }

    /**
     * Method which add new user to database
     *
     * @param airplane
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public void addAirplane(Airplane airplane) throws GeneralSecurityException, UnsupportedEncodingException {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_WEIGHT, airplane.getWeight());
        values.put(KEY_MAX_FLIGHT_TIME, airplane.getMaxFlightTime());
        values.put(KEY_TIME_FOR_SERVICE, airplane.getTimeForService());
        values.put(KEY_AIRPLANE_NAME, airplane.getName());
        values.put(KEY_AIRPLANE_TYPE, airplane.getType());
        values.put(KEY_MAX_WEIGHT, airplane.getMaxWeight());
        values.put(KEY_MAX_PLACES, airplane.getMaxPlaces());
        db.insert(TABLE_AIRPLANES, null, values);
        db.close();
    }

    /**
     * Method which get current user by id.
     *
     * @param airplaneName
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public Airplane getAirplane(String airplaneName) throws GeneralSecurityException, IOException {
        db = this.getReadableDatabase();
        String selectQuery =
                "SELECT  * FROM " + TABLE_AIRPLANES + " WHERE " + KEY_AIRPLANE_NAME + " = " + airplaneName;
        cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Airplane airplane = new Airplane(cursor.getString((cursor.getColumnIndex(KEY_AIRPLANE_NAME))));
        airplane.setWeight(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_WEIGHT)))));
        airplane.setMaxFlightTime(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_FLIGHT_TIME)))));
        airplane.setTimeForService(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_TIME_FOR_SERVICE)))));
        airplane.setType(cursor.getString((cursor.getColumnIndex(KEY_AIRPLANE_TYPE))));
        airplane.setMaxWeight(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_WEIGHT)))));
        airplane.setMaxPlaces(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_PLACES)))));
        return airplane;
    }

    /**
     * Method which get list of all airplanes.
     *
     * @return
     */
    public ArrayList<Airplane> getAllAirplanes() {
        ArrayList<Airplane> airplanes = new ArrayList<Airplane>();
        String selectQuery = "SELECT  * FROM " + TABLE_AIRPLANES;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Airplane airplane = new Airplane(cursor.getString((cursor.getColumnIndex(KEY_AIRPLANE_NAME))));
                airplane.setWeight(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_WEIGHT)))));
                airplane.setMaxFlightTime(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_FLIGHT_TIME)))));
                airplane.setTimeForService(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_TIME_FOR_SERVICE)))));
                airplane.setType(cursor.getString((cursor.getColumnIndex(KEY_AIRPLANE_TYPE))));
                airplane.setMaxWeight(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_WEIGHT)))));
                airplane.setMaxPlaces(Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_MAX_PLACES)))));
                airplanes.add(airplane);
            } while (cursor.moveToNext());
        }
        return airplanes;
    }

    /**
     * Method which add new flight into database
     *
     * @param flight
     */
    public void addFlight(Flight flight) throws GeneralSecurityException, UnsupportedEncodingException {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_FLIGHT_FROM_AIRPORT, flight.getFromAirport());
        values.put(KEY_FLIGHT_TO_AIRPORT, flight.getToAirport());
        values.put(KEY_FLIGHT_AIRPLANE, flight.getAirplane());
        values.put(KEY_FLIGHT_DATE, flight.getDate());
        values.put(KEY_FLIGHT_TIME, flight.getTime());
        values.put(KEY_FLIGHT_MAX_TIME, flight.getFlightTime());
        db.insert(TABLE_FLIGHTS, null, values);
        db.close();
    }

    /**
     * Method which get list of all airplanes.
     *
     * @return
     */
    public ArrayList<Flight> getAllFlights() {
        ArrayList<Flight> flights = new ArrayList<Flight>();
        String selectQuery = "SELECT  * FROM " + TABLE_FLIGHTS;
        db = this.getReadableDatabase();
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Flight flight = new Flight
                        (cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_FROM_AIRPORT))),
                                cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_TO_AIRPORT))),
                                cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_AIRPLANE))),
                                cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_DATE))),
                                cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_TIME))),
                                Integer.parseInt(cursor.getString((cursor.getColumnIndex(KEY_FLIGHT_MAX_TIME)))));
                flights.add(flight);
            } while (cursor.moveToNext());
        }
        return flights;
    }

    /**
     * Method which add new ticket into database
     *
     * @param ticket
     */
    public void addTicket(Ticket ticket) throws GeneralSecurityException, UnsupportedEncodingException {
        db = this.getWritableDatabase();
        values = new ContentValues();
        values.put(KEY_TICKET_FROM_AIRPORT, ticket.getFromAirport());
        values.put(KEY_TICKET_TO_AIRPORT, ticket.getToAirport());
        values.put(KEY_TICKET_AIRPLANE, ticket.getAirplane());
        values.put(KEY_TICKET_DATE, ticket.getDate());
        values.put(KEY_TICKET_TIME, ticket.getTime());
        values.put(KEY_TICKET_MAX_FLIGHT_TIME, ticket.getMaxFlightTime());
        values.put(KEY_TICKET_PRICE, ticket.getPrice());
        db.insert(TABLE_TICKETS, null, values);
        db.close();
    }
}
