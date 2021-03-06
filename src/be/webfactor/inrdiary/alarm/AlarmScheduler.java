package be.webfactor.inrdiary.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import be.webfactor.inrdiary.preference.PreferencesService;

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
		PreferencesService preferencesService = PreferencesService.getInstance(context);

		if (preferencesService.isNotificationEnabled()) {
			int hour = preferencesService.getNotificationHour();
			int minute = preferencesService.getNotificationMinute();

			scheduleAlarm(context, hour, minute);
		}
	}

	public void scheduleAlarm(Context context, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();

		long currentTime = calendar.getTimeInMillis();

		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);

		long intendedTime = calendar.getTimeInMillis();

		if (currentTime > intendedTime) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			intendedTime = calendar.getTimeInMillis();
		}

		getAlarmManager(context).setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, AlarmManager.INTERVAL_DAY, getPendingIntent(context));
	}

	public void unscheduleAlarm(Context context) {
		getAlarmManager(context).cancel(getPendingIntent(context));
	}

	private AlarmManager getAlarmManager(Context context) {
		return (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	}

	private PendingIntent getPendingIntent(Context context) {
		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
		return PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
	}

}
