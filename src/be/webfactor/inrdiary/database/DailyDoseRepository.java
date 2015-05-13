package be.webfactor.inrdiary.database;

import android.content.Context;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyDoseRepository {

	private static final String DATE_FIELD = "date";

	private static DailyDoseRepository instance;
	private DatabaseHelper databaseHelper;

	public void release() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			instance = null;
			databaseHelper = null;
		}
	}

	public static DailyDoseRepository getInstance(Context context) {
		if (instance == null) {
			instance = new DailyDoseRepository(context);
		}
		return instance;
	}

	private DailyDoseRepository(Context context) {
		databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}

	public void saveDose(DailyDose dose) {
		DailyDose existingDose = getDoseByDate(dose.getDate());
		if (existingDose == null) {
			dao().create(dose);
		} else {
			existingDose.setDose(dose.getDose());
			dao().update(existingDose);
		}
	}

	public List<DailyDose> getDoses() {
		try {
			return dao().queryBuilder().orderBy(DATE_FIELD, false).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DailyDose getMostRecentDose() {
		try {
			return dao().queryBuilder().orderBy(DATE_FIELD, false).queryForFirst();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public DailyDose getTodaysDose() {
		return getDoseByDate(DailyDose.DB_FORMAT.format(new Date()));
	}

	public DailyDose getTomorrowsDose() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		Date tomorrow = calendar.getTime();
		return getDoseByDate(DailyDose.DB_FORMAT.format(tomorrow));
	}

	public void deleteDose(DailyDose dose) {
		dao().delete(dose);
	}

	public void toggleTodaysDoseConfirmation() {
		DailyDose dose = getTodaysDose();
		dose.setConfirmationDate(dose.isConfirmed() ? null : new Date());
		dose.setConfirmed(!dose.isConfirmed());
		dao().update(dose);
	}

	public DoseType getLastKnownDose() {
		DailyDose dose = getMostRecentDose();
		if (dose == null) {
			return DoseType.DEFAULT;
		}
		return dose.getDose();
	}

	public int getDaysUntilNoDose() {
		int result = 0;
		Date date = new Date();
		while (hasDose(date)) {
			date = addOneDay(date);
			result ++;
		}
		return result;
	}

	public Date getNearestDateWithoutDose() {
		Date date = new Date();
		while (hasDose(date)) {
			date = addOneDay(date);
		}
		return date;
	}

	private Date addOneDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	private boolean hasDose(Date date) {
		String dateString = DailyDose.DB_FORMAT.format(date);
		return getDoseByDate(dateString) != null;
	}

	private DailyDose getDoseByDate(String date) {
		try {
			return dao().queryBuilder().where().eq(DATE_FIELD, date).queryForFirst();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private RuntimeExceptionDao<DailyDose, Integer> dao() {
		return databaseHelper.getDailyDoseDao();
	}

}
