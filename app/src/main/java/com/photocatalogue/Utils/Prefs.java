package com.photocatalogue.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.photocatalogue.Utils.AppConstant.DESTINATION_PATH;
import static com.photocatalogue.Utils.AppConstant.SHAREDPREF;

/**
 * Created by rujul on 3/2/18.
 */

public class Prefs {


    private static SharedPreferences sp;

    public static Prefs newInstance(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SHAREDPREF, Context.MODE_PRIVATE);
        }
        return new Prefs();
    }

    public void saveDesPath(String path) {
        if (sp != null) {
            sp.edit().putString(DESTINATION_PATH, path).apply();
        }
    }


    public String getDesPath() {
        if (sp != null) {
            return sp.getString(DESTINATION_PATH, "");
        } else {
            return "";
        }
    }

}
