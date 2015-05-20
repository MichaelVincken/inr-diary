package be.webfactor.inrdiary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtil {

	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("H:mm");

	public static int getHourFromString(String time) {
		return getCalendarFromString(time).get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinuteFromString(String time) {
		return getCalendarFromString(time).get(Calendar.MINUTE);
	}

	private static Calendar getCalendarFromString(String time) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(TIME_FORMAT.parse(time));
		} catch (ParseException e) {
		}
		return calendar;
	}

	public static String getTimeString(int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		return TIME_FORMAT.format(calendar.getTime());
	}

}
