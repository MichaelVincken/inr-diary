package be.webfactor.inrdiary.activity;

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
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.fragment.AddDoseDialogFragment;
import be.webfactor.inrdiary.service.DailyDoseService;

import java.util.List;

public class ManageDosesActivity extends DailyDoseService implements AddDoseDialogFragment.AddDoseDialogListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_doses);

		populateDoseOverview();
	}

	public void onAddDose(DailyDose dose) {
		saveDose(dose);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.dose_was_successfully_added), Toast.LENGTH_SHORT).show();
		populateDoseOverview();
	}

	private void populateDoseOverview() {
		ListView resultList = (ListView) findViewById(R.id.doses_listview);
		final List<DailyDose> doses = getDoses();
		final DailyDoseAdapter dailyDoseAdapter = new DailyDoseAdapter(getApplicationContext(), getDoses());
		resultList.setAdapter(dailyDoseAdapter);
		resultList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				deleteDose(doses.get(position));
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
				DialogFragment fragment = new AddDoseDialogFragment();
				fragment.show(getFragmentManager(), null);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
