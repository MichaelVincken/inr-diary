package be.webfactor.inrdiary.domain;

import com.j256.ormlite.field.DatabaseField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DailyDose {

	public static final SimpleDateFormat DB_FORMAT = new SimpleDateFormat("yyyyMMdd");

	@DatabaseField(generatedId = true)
	private int id;

	@DatabaseField(canBeNull = false, uniqueIndex = true)
	private String date;

	@DatabaseField(canBeNull = false)
	private DoseType dose;

	@DatabaseField
	private boolean confirmed;

	@DatabaseField
	private Date confirmationDate;

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

	public DoseType getDose() {
		return dose;
	}

	public void setDose(DoseType dose) {
		this.dose = dose;
	}

	public boolean isConfirmed() {
		return confirmed;
	}

	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	public Date getConfirmationDate() {
		return confirmationDate;
	}

	public void setConfirmationDate(Date confirmationDate) {
		this.confirmationDate = confirmationDate;
	}

}
