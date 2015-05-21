package be.webfactor.inrdiary.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.alarm.AlarmScheduler;
import be.webfactor.inrdiary.util.TimeUtil;

public class NotificationTimePreference extends DialogPreference {

	private int hour;
	private int minute;
	private TimePicker picker = null;

	public NotificationTimePreference(final Context context, AttributeSet attrs) {
		super(context, attrs);

		setPositiveButtonText(context.getResources().getString(R.string.save));
		setNegativeButtonText(context.getResources().getString(R.string.cancel));

		setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				String time = newValue.toString();

				preference.setSummary(time);

				int hour = TimeUtil.getHourFromString(time);
				int minute = TimeUtil.getMinuteFromString(time);
				AlarmScheduler.getInstance().scheduleAlarm(context, hour, minute);

				return true;
			}
		});
	}

	protected View onCreateDialogView() {
		picker = new TimePicker(getContext());
		picker.setIs24HourView(true);

		return picker;
	}

	protected void onBindDialogView(View v) {
		super.onBindDialogView(v);

		String time = getPersistedString(null);

		if (time == null) {
			picker.setCurrentHour(21);
			picker.setCurrentMinute(0);
		} else {
			picker.setCurrentHour(hour);
			picker.setCurrentMinute(minute);
		}
	}

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

	protected Object onGetDefaultValue(TypedArray a, int index) {
		return (a.getString(index));
	}

	protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
		String time = null;

		if (restoreValue) {
			time = getPersistedString(null);
		}

		if (time != null) {
			setSummary(time);
			hour = TimeUtil.getHourFromString(time);
			minute = TimeUtil.getMinuteFromString(time);
		}

	}

}
