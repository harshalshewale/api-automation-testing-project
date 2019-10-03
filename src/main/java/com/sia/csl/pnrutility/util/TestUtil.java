package com.sia.csl.pnrutility.util;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class TestUtil {
	
	static Logger logger = Logger.getLogger(TestUtil.class);


	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Calendar cal = Calendar.getInstance();
		Date todayesDate = cal.getTime();
		dateFormat.format(todayesDate);
		String datetime = (dateFormat.format(cal.getTime())).toString().trim();
		return datetime;
	}

	public static String getFutureDate(int futureDays) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		Date todayesDate = cal.getTime();
		cal.add(Calendar.DATE, futureDays);
		String futureDate = (dateFormat.format(cal.getTime())).toString().trim();
		return futureDate;
	}

	public static String readPropertyValues(String proprtyFileName, String key)  {
		logger.info("\n*********** Reading Property Values *************\n");
		String keyValue = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(proprtyFileName);
			Properties prop = new Properties();
			prop.load(is);
			keyValue = prop.getProperty(key);
			
			logger.info("Key- "+key +"\nValue-"+keyValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return keyValue;

	}



}
