package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.adapter.InrMeasurementAdapter;
import be.webfactor.inrdiary.database.InrMeasurementRepository;
import be.webfactor.inrdiary.domain.InrMeasurement;
import be.webfactor.inrdiary.fragment.InrDialogFragment;

import java.util.Date;
import java.util.List;

public class ManageInrActivity extends Activity implements InrDialogFragment.AddInrDialogListener {

	private static final int MENU_ITEM_EDIT = 1;

	private InrMeasurementRepository inrMeasurementRepository;
	private InrMeasurementAdapter inrMeasurementAdapter;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_inrs);

		inrMeasurementRepository = InrMeasurementRepository.getInstance(this);
		populateInrOverview();
	}

	private void populateInrOverview() {
		ListView resultList = (ListView) findViewById(R.id.inrs_listview);
		final List<InrMeasurement> inrMeasurements = inrMeasurementRepository.getInrMeasurements();
		inrMeasurementAdapter = new InrMeasurementAdapter(getApplicationContext(), inrMeasurements);
		resultList.setAdapter(inrMeasurementAdapter);
		registerForContextMenu(resultList);
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);

		menu.setHeaderTitle(getResources().getString(R.string.inr_value));
		menu.add(Menu.NONE, MENU_ITEM_EDIT, Menu.NONE, getResources().getString(R.string.edit));
	}

	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		InrMeasurement inr = inrMeasurementAdapter.getItem(info.position);

		switch (item.getItemId()) {
			case MENU_ITEM_EDIT:
				openInrDialog(inr.getDateObj(), inr.getInrValue(), true);
				return true;
			default:
				return super.onContextItemSelected(item);
		}
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.manage_inrs_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.add_inr:
				openInrDialog(new Date(), 2.0f, false);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	private void openInrDialog(Date initialDate, float initialInr, boolean update) {
		InrDialogFragment.newInstance(initialDate, initialInr, update).show(getFragmentManager(), null);
	}

	public void onSaveInr(InrMeasurement inrMeasurement) {
		inrMeasurementRepository.saveInrMeasurement(inrMeasurement);
		Toast.makeText(getApplicationContext(), getResources().getText(R.string.inr_value_was_successfully_saved), Toast.LENGTH_SHORT).show();
		populateInrOverview();
	}

}
