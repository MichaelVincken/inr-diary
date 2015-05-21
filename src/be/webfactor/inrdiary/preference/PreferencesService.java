package be.webfactor.inrdiary.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import be.webfactor.inrdiary.fragment.SettingsFragment;
import be.webfactor.inrdiary.util.TimeUtil;

public class PreferencesService {

	private Context context;

	public static PreferencesService getInstance(Context context) {
		return new PreferencesService(context);
	}

	private PreferencesService(Context context) {
		this.context = context;
	}

	public boolean isNotificationEnabled() {
		return getPreferences().getBoolean(SettingsFragment.NOTIFICATION_ENABLED_PREF_KEY, false) && getNotificationTime() != null;
	}

	public int getNotificationHour() {
		return TimeUtil.getHourFromString(getNotificationTime());
	}

	public int getNotificationMinute() {
		return TimeUtil.getMinuteFromString(getNotificationTime());
	}

	private String getNotificationTime() {
		return getPreferences().getString(SettingsFragment.NOTIFICATION_TIME_PREF_KEY, null);
	}

	private SharedPreferences getPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

}
