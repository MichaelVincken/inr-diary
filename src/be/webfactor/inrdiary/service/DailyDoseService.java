package be.webfactor.inrdiary.service;

import be.webfactor.inrdiary.database.DatabaseHelper;
import be.webfactor.inrdiary.domain.DailyDose;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
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

	protected DailyDose getTodaysDose() {
		return getDoseByDate(DailyDose.DB_FORMAT.format(new Date()));
	}

	protected void deleteDose(DailyDose dose) {
		dao().delete(dose);
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
