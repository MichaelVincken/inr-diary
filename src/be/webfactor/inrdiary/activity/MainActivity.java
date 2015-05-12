package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.alarm.AlarmScheduler;
import be.webfactor.inrdiary.database.DailyDoseRepository;
import be.webfactor.inrdiary.database.InrMeasurementRepository;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

	private static final DateFormat TODAY_DATE_FORMAT = new SimpleDateFormat("EEEE d MMMM");

	private LinearLayout layoutWithValue;
	private LinearLayout layoutWithoutValue;
	private TextView todaysDoseTitle;
	private TextView todaysDoseAmountTextView;
	private TextView todaysDoseContext;
	private ImageView todaysDoseIcon;

	private TextView tomorrowsDoseTextView;
	private ImageView tomorrowsDoseImageView;

	private DailyDoseRepository dailyDoseRepository;
	private InrMeasurementRepository inrMeasurementRepository;

	private TextView mostRecentInrValueTextView;
	private TextView mostRecentInrDateTextView;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		dailyDoseRepository = DailyDoseRepository.getInstance(this);
		inrMeasurementRepository = InrMeasurementRepository.getInstance(this);

		todaysDoseTitle = (TextView) findViewById(R.id.todays_dose_title);
		todaysDoseAmountTextView = (TextView) findViewById(R.id.todays_dose_amount_text_view);
		todaysDoseContext = (TextView) findViewById(R.id.todays_dose_context);
		todaysDoseIcon = (ImageView) findViewById(R.id.todays_dose_pill_icon);
		layoutWithValue = (LinearLayout) findViewById(R.id.layout_with_value);
		layoutWithValue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				dailyDoseRepository.toggleTodaysDoseConfirmation();
				buildLayout();
			}
		});
		layoutWithoutValue = (LinearLayout) findViewById(R.id.layout_without_value);
		layoutWithoutValue.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ManageDosesActivity.class));
			}
		});

		tomorrowsDoseTextView = (TextView) findViewById(R.id.tomorrows_dose_textview);
		tomorrowsDoseImageView = (ImageView) findViewById(R.id.tomorrows_dose_imageview);

		mostRecentInrValueTextView = (TextView) findViewById(R.id.most_recent_inr_value_textview);
		mostRecentInrDateTextView = (TextView) findViewById(R.id.most_recent_inr_date_textview);
		findViewById(R.id.most_recent_inr_layout).setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ManageInrActivity.class));
			}
		});

		AlarmScheduler.getInstance().scheduleAlarm(this);
	}

	protected void onResume() {
		super.onResume();

		buildLayout();
	}

	private void buildLayout() {
		DailyDose todaysDose = dailyDoseRepository.getTodaysDose();
		layoutWithoutValue.setVisibility(View.GONE);
		layoutWithValue.setVisibility(View.GONE);
		if (todaysDose != null) {
			layoutWithValue.setVisibility(View.VISIBLE);
			if (todaysDose.isConfirmed()) {
				layoutWithValue.setBackground(getResources().getDrawable(R.drawable.background_green));
				todaysDoseTitle.setText(getResources().getString(R.string.confirmed_at_x, todaysDose.getFormattedConfirmationTime()));
				todaysDoseAmountTextView.setVisibility(View.GONE);
				todaysDoseIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_undo));
			} else {
				layoutWithValue.setBackground(getResources().getDrawable(R.drawable.background_orange));
				String todayString = TODAY_DATE_FORMAT.format(new Date());
				todayString = Character.toUpperCase(todayString.charAt(0)) + todayString.substring(1);
				todaysDoseTitle.setText(todayString);
				todaysDoseAmountTextView.setVisibility(View.VISIBLE);
				todaysDoseAmountTextView.setText(todaysDose.getDose().getLabel());
				todaysDoseIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill_white));
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_confirm));
			}
		} else {
			layoutWithoutValue.setVisibility(View.VISIBLE);
		}

		DailyDose tomorrowsDose = dailyDoseRepository.getTomorrowsDose();
		if (tomorrowsDose != null) {
			tomorrowsDoseImageView.setVisibility(View.VISIBLE);
			tomorrowsDoseTextView.setText(tomorrowsDose.getDose().getLabel());
		} else {
			tomorrowsDoseImageView.setVisibility(View.GONE);
			tomorrowsDoseTextView.setText("?");
		}

		InrMeasurement inrMeasurement = inrMeasurementRepository.getMostRecentMeasurement();
		if (inrMeasurement != null) {
			mostRecentInrValueTextView.setText(inrMeasurement.getFormattedInrValue());
			String dateString = TODAY_DATE_FORMAT.format(inrMeasurement.getDateObj());
			dateString = Character.toUpperCase(dateString.charAt(0)) + dateString.substring(1);
			mostRecentInrDateTextView.setText(dateString);
		} else {
			mostRecentInrValueTextView.setText(getResources().getString(R.string.not_available));
			mostRecentInrDateTextView.setText(getResources().getString(R.string.tap_to_configure_inrs));
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
			case R.id.action_manage_inr:
				startActivity(new Intent(this, ManageInrActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	protected void onDestroy() {
		super.onDestroy();
		dailyDoseRepository.release();
	}

}
