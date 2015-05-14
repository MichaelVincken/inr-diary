package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.adapter.DailyDoseAdapter;
import be.webfactor.inrdiary.database.DailyDoseRepository;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;
import be.webfactor.inrdiary.fragment.DoseDialogFragment;

import java.util.Date;
import java.util.List;

public class ManageDosesActivity extends Activity implements DoseDialogFragment.DoseDialogListener {

	private static final int MENU_ITEM_EDIT = 1;
	private static final int MENU_ITEM_DELETE = 2;

	private DailyDoseRepository dailyDoseRepository;
	private DailyDoseAdapter dailyDoseAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_doses);

		dailyDoseRepository = DailyDoseRepository.getInstance(this);
		populateDoseOverview();
	}

	public void onCreateDose(DailyDose dose) {
		onUpdateDose(dose);
		openCreateDoseDialog();
	}

	public void onUpdateDose(DailyDose dose) {
		dailyDoseRepository.saveDose(dose);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_saved), Toast.LENGTH_SHORT).show();
		populateDoseOverview();
	}

	private void populateDoseOverview() {
		ListView resultList = (ListView) findViewById(R.id.doses_listview);
		final List<DailyDose> doses = dailyDoseRepository.getDoses();
		dailyDoseAdapter = new DailyDoseAdapter(getApplicationContext(), doses);
		resultList.setAdapter(dailyDoseAdapter);
		registerForContextMenu(resultList);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle(getResources().getString(R.string.dose));
		menu.add(Menu.NONE, MENU_ITEM_EDIT, Menu.NONE, getResources().getString(R.string.edit));
		menu.add(Menu.NONE, MENU_ITEM_DELETE, Menu.NONE, getResources().getString(R.string.delete));
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		DailyDose dose = dailyDoseAdapter.getItem(info.position);

		switch (item.getItemId()) {
			case MENU_ITEM_EDIT:
				openDoseDialog(dose.getDateObj(), dose.getDose(), true);
				return true;
			case MENU_ITEM_DELETE:
				deleteDose(dose);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	private void deleteDose(DailyDose selectedDose) {
		dailyDoseRepository.deleteDose(selectedDose);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_deleted), Toast.LENGTH_SHORT).show();
		populateDoseOverview();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.manage_doses_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_dose:
				openCreateDoseDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openCreateDoseDialog() {
		Date initialDate = dailyDoseRepository.getNearestDateWithoutDose();
		DoseType dose = dailyDoseRepository.getLastKnownDose();
		openDoseDialog(initialDate, dose, false);
	}

	private void openDoseDialog(Date initialDate, DoseType initialDose, boolean update) {
		DoseDialogFragment.newInstance(initialDate, initialDose, update).show(getFragmentManager(), null);
	}

}