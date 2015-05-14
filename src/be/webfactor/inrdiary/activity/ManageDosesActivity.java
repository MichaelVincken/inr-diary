package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

	private DailyDoseRepository dailyDoseRepository;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_doses);

		dailyDoseRepository = DailyDoseRepository.getInstance(this);
		populateDoseOverview();
	}

	public void onCreate(DailyDose dose) {
		dailyDoseRepository.saveDose(dose);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_added), Toast.LENGTH_SHORT).show();
		populateDoseOverview();

		openCreateDoseDialog();
	}

	public void onUpdate(DailyDose dose) {
		dailyDoseRepository.saveDose(dose);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_updated), Toast.LENGTH_SHORT).show();
		populateDoseOverview();
	}

	private void populateDoseOverview() {
		ListView resultList = (ListView) findViewById(R.id.doses_listview);
		final List<DailyDose> doses = dailyDoseRepository.getDoses();
		final DailyDoseAdapter dailyDoseAdapter = new DailyDoseAdapter(getApplicationContext(), doses);
		resultList.setAdapter(dailyDoseAdapter);
		resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				DailyDose dose = doses.get(position);
				DialogFragment fragment = DoseDialogFragment.newInstance(dose.getDateObj(), dose.getDose(), true);
				fragment.show(getFragmentManager(), null);
			}
		});
		resultList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				dailyDoseRepository.deleteDose(doses.get(position));
				Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_deleted), Toast.LENGTH_SHORT).show();
				populateDoseOverview();
				return false;
			}
		});
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
		DoseType initialDose = dailyDoseRepository.getLastKnownDose();
		DialogFragment fragment = DoseDialogFragment.newInstance(initialDate, initialDose, false);
		fragment.show(getFragmentManager(), null);
	}

}
