package be.webfactor.inrdiary.activity;

import be.webfactor.inrdiary.database.DatabaseHelper;
import be.webfactor.inrdiary.domain.DailyDose;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

public abstract class DailyDoseServiceActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	public void save(DailyDose dose) {
		RuntimeExceptionDao<DailyDose, Integer> dao = getHelper().getDao();

		List<DailyDose> existingDoses = dao.queryForEq("date", dose.getDate());
		if (existingDoses.isEmpty()) {
			dao.create(dose);
		} else {
			DailyDose existingDose = existingDoses.get(0);
			existingDose.setDose(dose.getDose());
			dao.update(existingDose);
		}
	}

}
