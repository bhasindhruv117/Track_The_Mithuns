package com.akshit.sih;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SRKC on 30-03-2018.
 */



    public class UtilClipCodes {

        final static String FileName="MyFileName";

        public static String readSharedSetting(Context ctx, String settingName, String defaultValue)
        {
            SharedPreferences sharedPref=ctx.getSharedPreferences(FileName,Context.MODE_PRIVATE);
            return sharedPref.getString(settingName,defaultValue);
        }
        public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
            SharedPreferences sharedPref = ctx.getSharedPreferences(FileName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(settingName, settingValue);
            editor.apply();
        }
        public static void SharedPrefesSAVE(Context ctx,String Name){
            SharedPreferences prefs = ctx.getSharedPreferences("NAME", 0);
            SharedPreferences.Editor prefEDIT = prefs.edit();
            prefEDIT.putString("Name", Name);
            prefEDIT.commit();
        }

}
