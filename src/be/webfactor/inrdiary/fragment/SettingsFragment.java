package be.webfactor.inrdiary.fragment;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.alarm.AlarmScheduler;

public class SettingsFragment extends PreferenceFragment {

	public static final String NOTIFICATION_TIME_PREF_KEY = "notification_time";
	public static final String NOTIFICATION_ENABLED_PREF_KEY = "notification_enabled";

	private AlarmScheduler alarmScheduler = AlarmScheduler.getInstance();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);

		Preference notificationEnabledPreference = findPreference(NOTIFICATION_ENABLED_PREF_KEY);
		final Preference notificationTimePreference = findPreference(NOTIFICATION_TIME_PREF_KEY);

		notificationEnabledPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				if (!(Boolean)newValue) {
					getPreferenceScreen().removePreference(notificationTimePreference);
					alarmScheduler.unscheduleAlarm(getActivity());
				} else {
					getPreferenceScreen().addPreference(notificationTimePreference);
					AlarmScheduler.getInstance().scheduleAlarm(getActivity());
				}
				return true;
			}
		});
	}

}
