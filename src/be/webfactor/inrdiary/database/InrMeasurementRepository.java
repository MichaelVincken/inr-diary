package be.webfactor.inrdiary.database;

import android.content.Context;
import be.webfactor.inrdiary.domain.InrMeasurement;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

public class InrMeasurementRepository {

	private static final String DATE_FIELD = "date";

	private static InrMeasurementRepository instance;
	private DatabaseHelper databaseHelper;

	public void release() {
		if (databaseHelper != null) {
			OpenHelperManager.releaseHelper();
			instance = null;
			databaseHelper = null;
		}
	}

	public static InrMeasurementRepository getInstance(Context context) {
		if (instance == null) {
			instance = new InrMeasurementRepository(context);
		}
		return instance;
	}

	public List<InrMeasurement> getInrMeasurements() {
		try {
			return dao().queryBuilder().orderBy(DATE_FIELD, false).query();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private InrMeasurementRepository(Context context) {
		databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
	}

	private RuntimeExceptionDao<InrMeasurement, Integer> dao() {
		return databaseHelper.getInrMeasurementDao();
	}

}
