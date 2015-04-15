package be.webfactor.inrdiary.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.fragment.AddDoseDialogFragment;

public class ManageDosesActivity extends DailyDoseServiceActivity implements AddDoseDialogFragment.AddDoseDialogListener {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_doses);
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

	public void onAddDose(DailyDose dose) {
		createDose(dose);
	}

}
