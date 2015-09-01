package org.sdrc.evm.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTest {
	
	 public static void main(String[] args) throws ParseException {
//		    java.util.Date today = new java.util.Date();
//		    java.sql.Timestamp ts1 = new java.sql.Timestamp(today.getTime());
//		    java.sql.Timestamp ts2 = java.sql.Timestamp.valueOf("2005-04-06 09:01:10");
//
//		    long tsTime1 = ts1.getTime();
//		    long tsTime2 = ts2.getTime();
//		    
//		    System.out.println(tsTime1);
//		    System.out.println(tsTime2);
//		    
		 Timestamp date1 = new Timestamp(new Date().getTime());
		 Timestamp date2 = new Timestamp(new Date().getTime());
		    
//		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        	Date date1 = sdf.parse("2009-12-31");
//        	Date date2 = sdf.parse("2010-01-31");
 
//        	System.out.println(sdf.format(date1));
//        	System.out.println(sdf.format(date2));
		 
		 System.out.println(date1);
     	 System.out.println(date2);
 
        	if(date1.after(date2)){
        		System.out.println("Date1 is after Date2");
        	}
 
        	if(date1.before(date2)){
        		System.out.println("Date1 is before Date2");
        	}
 
        	if(date1.equals(date2)){
        		System.out.println("Date1 is equal Date2");
        	}
        	

        	Date date = new Date(date1.getTime());
//        	 String simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy").format(date);
//        	 System.out.println(simpleDateFormat);
//        	 
//        	 
//        	 
//        	 String a = simpleDateFormat.substring(2);
//        	 String b = "27".concat(a);
//        	 System.out.println(b+"---1");
        	
 
        	 
//        	 Calendar cal = Calendar.getInstance();
//        	 cal.add(Calendar.MONTH, -1);
//        	 Date result = cal.getTime();
//        	 System.out.println(result+"---2");
//        	 String result1 = new SimpleDateFormat("dd-MM-yyyy").format(result);
//        	 System.out.println(result1+"---3");
//        	 
        	Calendar calendar=Calendar.getInstance();
    		calendar.add(Calendar.DATE,27-calendar.get(Calendar.DATE));
    		Date result = calendar.getTime();
    		String simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd").format(result);
    		System.out.println(simpleDateFormat+"---calendar");
		  }
}
