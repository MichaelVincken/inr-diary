package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

public class DailyDose {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private int day;

	@DatabaseField(canBeNull = false)
	private int month;

	@DatabaseField(canBeNull = false)
	private int year;

	@DatabaseField(canBeNull = false)
	private DoseType dose;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public DoseType getDose() {
		return dose;
	}

	public void setDose(DoseType dose) {
		this.dose = dose;
	}

}
