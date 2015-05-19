package be.webfactor.inrdiary.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationTimePreference extends DialogPreference {

	private static final DateFormat TIME_FORMAT = new SimpleDateFormat("H:mm");
	private static final int DEFAULT_HOUR = 21;
	private static final int DEFAULT_MINUTE = 0;

	private int hour;
	private int minute;
	private TimePicker picker = null;

	public NotificationTimePreference(Context context, AttributeSet attrs) {
		super(context, attrs);

		setPositiveButtonText("Set");
		setNegativeButtonText("Cancel");

		setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				preference.setSummary(newValue.toString());
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

			String time = getCurrentTimeString();

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
			time = getPersistedString(getDefaultTimeString());
			setSummary(time);
		} else {
			time = defaultValue.toString();
		}

		hour = getHourFromString(time);
		minute = getMinuteFromString(time);
	}

	private int getHourFromString(String time) {
		return getCalendarFromString(time).get(Calendar.HOUR_OF_DAY);
	}

	private int getMinuteFromString(String time) {
		return getCalendarFromString(time).get(Calendar.MINUTE);
	}

	private Calendar getCalendarFromString(String time) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(TIME_FORMAT.parse(time));
		} catch (ParseException e) {
		}
		return calendar;
	}

	private String getCurrentTimeString() {
		return getTimeString(hour, minute);
	}

	private String getDefaultTimeString() {
		return getTimeString(DEFAULT_HOUR, DEFAULT_MINUTE);
	}

	private String getTimeString(int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		return TIME_FORMAT.format(calendar.getTime());
	}

}
