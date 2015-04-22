package be.webfactor.inrdiary.service;

import be.webfactor.inrdiary.database.DatabaseHelper;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class DailyDoseService extends OrmLiteBaseActivity<DatabaseHelper> {

	private static final String DATE_FIELD = "date";

	protected void saveDose(DailyDose dose) {
			DailyDose existingDose = getDoseByDate(dose.getDate());
			if (existingDose == null) {
				dao().create(dose);
			} else {
				existingDose.setDose(dose.getDose());
				dao().update(existingDose);
			}
	}

	protected List<DailyDose> getDoses() {
		try {
			return dao().queryBuilder().orderBy(DATE_FIELD, false).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected DailyDose getMostRecentDose() {
		try {
			return dao().queryBuilder().orderBy(DATE_FIELD, false).queryForFirst();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	protected DailyDose getTodaysDose() {
		return getDoseByDate(DailyDose.DB_FORMAT.format(new Date()));
	}

	protected void deleteDose(DailyDose dose) {
		dao().delete(dose);
	}

	protected void toggleTodaysDoseConfirmation() {
		DailyDose dose = getTodaysDose();
		dose.setConfirmationDate(dose.isConfirmed() ? null : new Date());
		dose.setConfirmed(!dose.isConfirmed());
		dao().update(dose);
	}

	protected DoseType getLastKnownDose() {
		DailyDose dose = getMostRecentDose();
		if (dose == null) {
			return DoseType.DEFAULT;
		}
		return dose.getDose();
	}

	protected Date getNearestDateWithoutDose() {
		Date date = new Date();
		while (hasDose(date)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, 1);
			date = calendar.getTime();
		}
		return date;
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
		return getHelper().getDao();
	}

}
