package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

public class InrMeasurement {

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, uniqueIndex = true)
	private String date;

	@DatabaseField(canBeNull = false)
	private float inrValue;

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
