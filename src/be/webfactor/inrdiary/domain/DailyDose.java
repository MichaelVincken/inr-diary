package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyDose {

	public static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat LIST_VIEW_FORMAT = new SimpleDateFormat("EEE d/M");

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, uniqueIndex = true)
	private String date;

	@DatabaseField(canBeNull = false)
	private float dose;

	@DatabaseField
	private boolean confirmed;

	public String getReadableDate() {
		try {
			Date dateObject = DB_FORMAT.parse(date);
			return LIST_VIEW_FORMAT.format(dateObject);
		} catch (ParseException e) {
			return "";
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getDose() {
		return dose;
	}

	public void setDose(float dose) {
		this.dose = dose;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

}
