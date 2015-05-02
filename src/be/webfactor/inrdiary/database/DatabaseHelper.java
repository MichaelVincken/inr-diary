package be.webfactor.inrdiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.InrMeasurement;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "inrdiary";
	private static final int DATABASE_VERSION = 9;

	private RuntimeExceptionDao<InrMeasurement, Integer> inrMeasurementDao;
	private RuntimeExceptionDao<DailyDose, Integer> dailyDoseDao;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, DailyDose.class);
			TableUtils.createTable(connectionSource, InrMeasurement.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, DailyDose.class, true);
			TableUtils.dropTable(connectionSource, InrMeasurement.class, true);
			TableUtils.createTable(connectionSource, DailyDose.class);
			TableUtils.createTable(connectionSource, InrMeasurement.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<DailyDose, Integer> getDailyDoseDao() {
		if (dailyDoseDao == null) {
			dailyDoseDao = getRuntimeExceptionDao(DailyDose.class);
		}
		return dailyDoseDao;
	}

	public RuntimeExceptionDao<InrMeasurement, Integer> getInrMeasurementDao() {
		if (inrMeasurementDao == null) {
			inrMeasurementDao = getRuntimeExceptionDao(InrMeasurement.class);
		}
		return inrMeasurementDao;
	}

	public void close() {
		super.close();
		dailyDoseDao = null;
	}

}
