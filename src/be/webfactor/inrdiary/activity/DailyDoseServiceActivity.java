package be.webfactor.inrdiary.activity;

import be.webfactor.inrdiary.database.DatabaseHelper;
import be.webfactor.inrdiary.domain.DailyDose;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public abstract class DailyDoseServiceActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	public void createDose(DailyDose dose) {
		getHelper().getDao().create(dose);
	}

}
