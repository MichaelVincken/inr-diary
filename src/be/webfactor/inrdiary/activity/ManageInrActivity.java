package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.adapter.InrMeasurementAdapter;
import be.webfactor.inrdiary.database.InrMeasurementRepository;
import be.webfactor.inrdiary.domain.InrMeasurement;
import be.webfactor.inrdiary.fragment.AddInrDialogFragment;

import java.util.List;

public class ManageInrActivity extends Activity implements AddInrDialogFragment.AddInrDialogListener {

	private InrMeasurementRepository inrMeasurementRepository;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_inrs);

		inrMeasurementRepository = InrMeasurementRepository.getInstance(this);
		populateInrOverview();
	}

	private void populateInrOverview() {
		ListView resultList = (ListView) findViewById(R.id.inrs_listview);
		final List<InrMeasurement> inrMeasurements = inrMeasurementRepository.getInrMeasurements();
		resultList.setAdapter(new InrMeasurementAdapter(getApplicationContext(), inrMeasurements));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.manage_inrs_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_inr:
				openAddInrDialog();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openAddInrDialog() {
		DialogFragment fragment = new AddInrDialogFragment();
		fragment.show(getFragmentManager(), null);
	}

	protected void onDestroy() {
		super.onDestroy();
		inrMeasurementRepository.release();
	}

	public void onAddInr(InrMeasurement inrMeasurement) {
		inrMeasurementRepository.saveInrMeasurement(inrMeasurement);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.inr_value_was_successfully_added), Toast.LENGTH_SHORT).show();
		populateInrOverview();
	}

}
