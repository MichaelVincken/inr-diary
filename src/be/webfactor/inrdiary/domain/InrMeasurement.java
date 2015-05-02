package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InrMeasurement {

	public static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd");

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, uniqueIndex = true)
	private String date;

	@DatabaseField(canBeNull = false)
	private float inrValue;

	public Date getDateObj() {
		try {
			return DB_FORMAT.parse(date);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public float getInrValue() {
		return inrValue;
	}

	public void setInrValue(float inrValue) {
		this.inrValue = inrValue;
	}

}
