package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

public class DailyDose {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false)
	private String date;

	@DatabaseField(canBeNull = false)
	private float dose;

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

}
