package com.example.nantes.mines.myChat.utils;

/**
 * Store the data of the application
 */
public class Data {
    /** The login used by the user */
    private static String login = "";
    /** The password used by the user */
    private static String pass = "";
    /** The URL used to test if a user is known */
    private final static String CONNECT_URL = "http://training.loicortola.com/parlez-vous-android/connect/";
    /** The URL used to register an user */
    private final static String REGISTER_URL = "http://training.loicortola.com/parlez-vous-android/register/";
    /** The URL used to get all the message */
    private final static String GET_MESSAGE_URL = "http://training.loicortola.com/parlez-vous-android/message/";
    /** The URL used to send a message */
    private final static String SEND_MESSAGE_URL = "http://training.loicortola.com/parlez-vous-android/message/";
    /** The name of the sharedPreferences */
    private final static String PREFERENCES = "MyPrefs";
    /** The sharedPreferences name for a login */
    private final static String PREF_LOGIN = "Login";
    /** The sharedPreferences name for a password */
    private final static String PREF_PASS = "Pass";
    /** The name used to save the status of the error text in the login activity */
    private final static String NAME_STATE_LOGIN_ERROR = "errorState";
    /** The name used to save the position of the listview in the main activity */
    private final static String NAME_STATE_LIST = "listState";
    /** The message to send to open the easter egg (drawActivity */
    private final static String NAME_EASTER_EGG_DRAW = "#picasso";

    public static String getLogin() {
        return login;
    }

    public static String getPass() {
        return pass;
    }

    public static String getConnectUrl() {
        return CONNECT_URL;
    }

    public static String getRegisterUrl() {
        return REGISTER_URL;
    }

    public static String getGetMessageUrl() {
        return GET_MESSAGE_URL;
    }

    public static String getSendMessageUrl() {
        return SEND_MESSAGE_URL;
    }

    public static String getPreferences() {
        return PREFERENCES;
    }

    public static String getPrefLogin() {
        return PREF_LOGIN;
    }

    public static String getPrefPass() {
        return PREF_PASS;
    }

    public static String getNameStateLoginError() {
        return NAME_STATE_LOGIN_ERROR;
    }

    public static String getNameStateList() {
        return NAME_STATE_LIST;
    }

    public static String getNameEasterEggDraw() {
        return NAME_EASTER_EGG_DRAW;
    }

    public static void setLogin(String login) {
        Data.login = login;
    }

    public static void setPass(String pass) {
        Data.pass = pass;
    }



}
