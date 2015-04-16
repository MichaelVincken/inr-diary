package be.webfactor.inrdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DoseType;
import be.webfactor.inrdiary.service.DailyDoseService;

public class MainActivity extends DailyDoseService {

	private TextView todaysDoseAmountTextView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		todaysDoseAmountTextView = (TextView) findViewById(R.id.todays_dose_amount_text_view);

	}

	protected void onResume() {
		super.onResume();

		todaysDoseAmountTextView.setText(DoseType.getLabelForAmount(getTodaysDose().getDose()));
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_manage_doses:
				startActivity(new Intent(this, ManageDosesActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

}
