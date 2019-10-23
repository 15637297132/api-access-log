package com.edu.api.log.v2;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

    public static void main(String[] args) throws Exception {

        test("2019-10-16 2:50:00");
        test(10,50);
    }

    public static Date getOrderDate() {
        return new Date();
    }

    public static void test(int a,int b){
        Date now = new Date();

        Calendar $1 = Calendar.getInstance();
        $1.setTime(now);
        $1.set(Calendar.HOUR_OF_DAY, a);
        $1.set(Calendar.MINUTE, b);
        $1.set(Calendar.SECOND, 0);

        Calendar start = Calendar.getInstance();
        start.setTime(now);
        start.set(Calendar.HOUR_OF_DAY, 11);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(now);
        end.add(Calendar.DATE, 1);
        end.set(Calendar.HOUR_OF_DAY, 13);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        if (start.before($1) && end.after($1)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

    }

    public static void test(String date) throws Exception {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date now = format.parse(date);

        Calendar start = Calendar.getInstance();
        start.setTime(now);
        start.set(Calendar.HOUR_OF_DAY, 18);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        Calendar end = Calendar.getInstance();
        end.setTime(now);
        end.add(Calendar.DATE, 1);
        end.set(Calendar.HOUR_OF_DAY, 8);
        end.set(Calendar.MINUTE, 0);
        end.set(Calendar.SECOND, 0);
        System.out.println(now);
        System.out.println(start.getTime());
        System.out.println(end.getTime());
        if (start.getTime().before(now) && end.getTime().after(now)) {
            System.out.println(true);
        } else {
            System.out.println(false);
        }

//        if (start.before(now) && end.after(now)) {
//            System.out.println(true);
//        } else {
//            System.out.println(false);
//        }
    }
}
