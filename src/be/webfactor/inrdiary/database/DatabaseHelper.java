package be.webfactor.inrdiary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import be.webfactor.inrdiary.domain.DailyDose;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "inrdiary";
	private static final int DATABASE_VERSION = 2;

	private RuntimeExceptionDao<DailyDose, Integer> dao = null;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, DailyDose.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, DailyDose.class, true);
			TableUtils.createTable(connectionSource, DailyDose.class);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<DailyDose, Integer> getDao() {
		if (dao == null) {
			dao = getRuntimeExceptionDao(DailyDose.class);
		}
		return dao;
	}

	public void close() {
		super.close();
		dao = null;
	}

}
