package com.zubin.ewriter.utils;

import android.content.Context;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zubin on 15/6/3.
 */
public class DateHelper {

    private static final SimpleDateFormat sWriterShowDateFormat = new SimpleDateFormat("yyyy-M-d  h:mm");

    public static String getWriterDate(Context context) {
        Date time = new Date();
        return sWriterShowDateFormat.format(time);
    }
}
