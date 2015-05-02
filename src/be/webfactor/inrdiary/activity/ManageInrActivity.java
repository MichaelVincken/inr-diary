package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.adapter.InrMeasurementAdapter;
import be.webfactor.inrdiary.database.InrMeasurementRepository;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.util.List;

public class ManageInrActivity extends Activity {

	private InrMeasurementRepository inrMeasurementRepository;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_inrs);

		inrMeasurementRepository = InrMeasurementRepository.getInstance(this);
		populateDoseOverview();
	}

	private void populateDoseOverview() {
		ListView resultList = (ListView) findViewById(R.id.inrs_listview);
		final List<InrMeasurement> inrMeasurements = inrMeasurementRepository.getInrMeasurements();
		resultList.setAdapter(new InrMeasurementAdapter(getApplicationContext(), inrMeasurements));
	}

	protected void onDestroy() {
		super.onDestroy();
		inrMeasurementRepository.release();
	}

}
