package be.webfactor.inrdiary.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmScheduler {

	private static AlarmScheduler INSTANCE;

	public static AlarmScheduler getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AlarmScheduler();
		}
		return INSTANCE;
	}

	public void scheduleAlarm(Context context) {
		Calendar calendar = Calendar.getInstance();

		long currentTime = calendar.getTimeInMillis();

		calendar.set(Calendar.HOUR_OF_DAY, 21);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		long intendedTime = calendar.getTimeInMillis();

		if (currentTime > intendedTime) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			intendedTime = calendar.getTimeInMillis();
		}

		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, pendingIntent);
	}

}
