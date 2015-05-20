package be.webfactor.inrdiary.preference;

import android.content.Context;
import android.preference.PreferenceManager;
import be.webfactor.inrdiary.util.TimeUtil;

public class PreferencesService {

	private static final String NOTIFICATION_TIME = "notification_time";
	
	private Context context;

	public static PreferencesService getInstance(Context context) {
		return new PreferencesService(context);
	}

	private PreferencesService(Context context) {
		this.context = context;
	}

	public int getNotificationHour() {
		return TimeUtil.getHourFromString(getNotificationTime());
	}

	public int getNotificationMinute() {
		return TimeUtil.getMinuteFromString(getNotificationTime());
	}

	private String getNotificationTime() {
		return PreferenceManager.getDefaultSharedPreferences(context).getString(NOTIFICATION_TIME, null);
	}

}
