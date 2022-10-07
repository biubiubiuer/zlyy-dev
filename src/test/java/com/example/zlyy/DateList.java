package com.example.zlyy;

import org.apache.commons.lang.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateList {
    public static void main(String[] args) {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
        for (int i = 7; i >= 1; i--) {
            Date date = DateUtils.addDays(new Date(), -i);
            String formatDate = sdf1.format(date);
            dateList.add(formatDate);
        }
        for (String str : dateList) {
            System.out.println(str);
        }
    }
}
