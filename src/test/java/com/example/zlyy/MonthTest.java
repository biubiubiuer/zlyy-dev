package com.example.zlyy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MonthTest {
    public static void main(String[] args) {
        List<String> resultList = new ArrayList<String>();
        Calendar cal = Calendar.getInstance();
        //近六个月
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去
        for(int i=0; i<12; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            resultList.add(String.valueOf(cal.get(Calendar.YEAR))
                    +"-"+ (cal.get(Calendar.MONTH)+1 < 10 ? "0" +
                    (cal.get(Calendar.MONTH)+1) : (cal.get(Calendar.MONTH)+1)));
        }
        for (int i = 0; i < resultList.size(); i++) {
            System.out.print(resultList.get(i) + ' ');
        }
    }
}
