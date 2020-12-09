package local.impactlife.cubetalk.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import local.impactlife.cubetalk.models.Country;

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    public  static  boolean IsFromChat = false;
    public  static  String MesiboAddress="";
    private static HashMap<Integer, Country> mCountriesMap = new HashMap<Integer, Country>() {
        {
            put(99, new Country(99, "India", "+91", ""));
            put(226, new Country(226, "United States (US)", "+1", ""));
            put(192, new Country(192, "Singapore", "+65", ""));
            put(38, new Country(38, "Canada", "+1", ""));
            put(225, new Country(225, "United Kingdom (UK)", "+44", ""));
            put(80, new Country(80, "Germany", "+49", ""));
            put(13, new Country(13, "Australia", "+61", ""));

        }
    };

    private static Dictionary<Integer, Country> mCountriesDictionary = new Hashtable<Integer, Country>() {
        {
            put(13, new Country(13, "Australia", "+61", ""));
            put(38, new Country(38, "Canada", "+1", ""));
            put(80, new Country(80, "Germany", "+49", ""));
            put(99, new Country(99, "India", "+91", ""));
            put(192, new Country(192, "Singapore", "+65", ""));
            put(225, new Country(225, "United Kingdom (UK)", "+44", ""));
            put(226, new Country(226, "United States (US)", "+1", ""));
        }
    };

    public static List<String> getGenders() {
        List<String> genders = new ArrayList<>();
        genders.add("Male");
        genders.add("Female");
        return genders;
    }

    public static List<Integer> getYearsOfExperiences() {
        List<Integer> yearsOfExperience = new ArrayList<>();
        for (int i = 5; i <= 25; ++i) {
            yearsOfExperience.add(i);
        }
        return yearsOfExperience;
    }

    public static List<Country> getCountries() {
        return new ArrayList<>(mCountriesMap.values());
    }

    public static String getCountryNameById(int countryId) {

        return mCountriesMap.get(countryId) == null ? "" : mCountriesMap.get(countryId).getName();
    }

    public static String convert24HoursTo12Hours(String hoursIn24HoursFormat) {
        int hour = Integer.parseInt(hoursIn24HoursFormat);

        if (hour < 13) {
            hoursIn24HoursFormat += " AM";
        } else {
            hoursIn24HoursFormat = (hour - 12) + " PM";
        }

        return hoursIn24HoursFormat;
    }

    public static String convertSlotDate(String slotDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(slotDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotDate: e.get");
        }

        return "";
    }

    public static String convertSlotDateCreDitshell(String slotDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(slotDate);
            SimpleDateFormat dateformat = new SimpleDateFormat("d" ,Locale.getDefault());
            String day = dateformat.format(date);
            if(day.equalsIgnoreCase("1") || day.equalsIgnoreCase("21") ||
                    day.equalsIgnoreCase("31")){
                outputFormat = new SimpleDateFormat(" d'st' MMM, HH:mm a",Locale.getDefault());
            }else if(day.equalsIgnoreCase("2") || day.equalsIgnoreCase("22")){
                outputFormat = new SimpleDateFormat(" d'nd' MMM, HH:mm a",Locale.getDefault());
            }else if(day.equalsIgnoreCase("3") || day.equalsIgnoreCase("23")){
                outputFormat = new SimpleDateFormat(" d'rd' MMM, HH:mm a",Locale.getDefault());
            }else {
                outputFormat = new SimpleDateFormat(" d'th' MMM, HH:mm a",Locale.getDefault());
            }
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotDate: e.get");
        }

        return "";
    }

    public static String convertSlotDateforearningstatics(String slotDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(slotDate);
            SimpleDateFormat dateformat = new SimpleDateFormat("d" ,Locale.getDefault());
            String day = dateformat.format(date);
            if(day.equalsIgnoreCase("1") || day.equalsIgnoreCase("21") ||
                    day.equalsIgnoreCase("31")){
                outputFormat = new SimpleDateFormat(" d'st', MMM",Locale.getDefault());
            }else if(day.equalsIgnoreCase("2") || day.equalsIgnoreCase("22")){
                outputFormat = new SimpleDateFormat(" d'nd', MMM",Locale.getDefault());
            }else if(day.equalsIgnoreCase("3") || day.equalsIgnoreCase("23")){
                outputFormat = new SimpleDateFormat(" d'rd', MMM",Locale.getDefault());
            }else {
                outputFormat = new SimpleDateFormat(" d'th', MMM",Locale.getDefault());
            }
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotDate: e.get");
        }

        return "";
    }
    public static String convertSlotDatetoMonthname(String slotDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = inputFormat.parse(slotDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotDate: e.get");
        }

        return "";
    }
    public static String convertSlotTime(String slotTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("H.mm", Locale.ENGLISH);
        Date date;
        try {
            date = sdf.parse(slotTime);
            return new SimpleDateFormat("h:mm a", Locale.ENGLISH).format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotTime: e.getMessage(): ");
        }
        return "";
    }

    public static String convertSlotType(String slotType) {
        switch (slotType) {
            case "duration_12":
                return "12 min Slot";

            case "duration_25":
                return "25 min Slot";

            case "duration_50":
                return "50 min Slot";
        }
        return "";
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static String convertdtatomonthname(String slotDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM", Locale.getDefault());
            Date date = inputFormat.parse(slotDate);
            return outputFormat.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "convertSlotDate: e.get");
        }

        return "";
    }
}
