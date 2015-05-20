package be.webfactor.inrdiary.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import be.webfactor.inrdiary.alarm.AlarmScheduler;
import be.webfactor.inrdiary.util.TimeUtil;

public class NotificationTimePreference extends DialogPreference {

	private static final int DEFAULT_HOUR = 21;
	private static final int DEFAULT_MINUTE = 0;

	private int hour;
	private int minute;
	private TimePicker picker = null;

	public NotificationTimePreference(final Context context, AttributeSet attrs) {
		super(context, attrs);

		setPositiveButtonText("Set");
		setNegativeButtonText("Cancel");

		setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(newValue.toString());
				AlarmScheduler.getInstance().scheduleAlarm(context);
				return true;
			}
		});
	}

	@Override
	protected View onCreateDialogView() {
		picker = new TimePicker(getContext());
		picker.setIs24HourView(true);

		return picker;
	}

	@Override
	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);

		picker.setCurrentHour(hour);
		picker.setCurrentMinute(minute);
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		super.onDialogClosed(positiveResult);

		if (positiveResult) {
			hour = picker.getCurrentHour();
			minute = picker.getCurrentMinute();

			String time = TimeUtil.getTimeString(hour, minute);

			if (callChangeListener(time)) {
				persistString(time);
			}
		}
	}

	@Override
	protected Object onGetDefaultValue(TypedArray a, int index) {
		return (a.getString(index));
	}

	@Override
	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		String time;

		if (restoreValue) {
			time = getPersistedString(TimeUtil.getTimeString(DEFAULT_HOUR, DEFAULT_MINUTE));
			setSummary(time);
		} else {
			time = defaultValue.toString();
		}

		hour = TimeUtil.getHourFromString(time);
		minute = TimeUtil.getMinuteFromString(time);
	}

}
