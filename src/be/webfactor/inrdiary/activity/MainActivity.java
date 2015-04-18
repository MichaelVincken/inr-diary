package be.webfactor.inrdiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;
import be.webfactor.inrdiary.service.DailyDoseService;

public class MainActivity extends DailyDoseService {

	private LinearLayout layoutWithValue;
	private LinearLayout layoutWithoutValue;
	private TextView todaysDoseAmountTextView;
	private TextView todaysDoseContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		todaysDoseAmountTextView = (TextView) findViewById(R.id.todays_dose_amount_text_view);
		todaysDoseContext = (TextView) findViewById(R.id.todays_dose_context);
		layoutWithValue = (LinearLayout) findViewById(R.id.layout_with_value);
		layoutWithValue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				toggleTodaysDoseConfirmation();
				buildLayout();
			}
		});
		layoutWithoutValue = (LinearLayout) findViewById(R.id.layout_without_value);
		layoutWithoutValue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), ManageDosesActivity.class);
				startActivity(i);
			}
		});
	}

	protected void onResume() {
		super.onResume();

		buildLayout();
	}

	private void buildLayout() {
		DailyDose todaysDose = getTodaysDose();
		layoutWithoutValue.setVisibility(View.GONE);
		layoutWithValue.setVisibility(View.GONE);
		if (todaysDose != null) {
			layoutWithValue.setVisibility(View.VISIBLE);
			if (todaysDose.isConfirmed()) {
				layoutWithValue.setBackgroundColor(getResources().getColor(R.color.green));
				todaysDoseAmountTextView.setText(getResources().getString(R.string.ok));
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_undo));
			} else {
				layoutWithValue.setBackgroundColor(getResources().getColor(R.color.orange));
				todaysDoseAmountTextView.setText(DoseType.getLabelForAmount(getTodaysDose().getDose()));
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_confirm));
			}
		} else {
			layoutWithoutValue.setVisibility(View.VISIBLE);
		}
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
