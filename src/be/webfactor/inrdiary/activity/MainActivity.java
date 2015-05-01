package be.webfactor.inrdiary.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
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
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.service.DailyDoseService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends DailyDoseService {

	private static final DateFormat TODAY_DATE_FORMAT = new SimpleDateFormat("EEEE d MMMM");

	private LinearLayout layoutWithValue;
	private LinearLayout layoutWithoutValue;
	private TextView todaysDoseTitle;
	private TextView todaysDoseAmountTextView;
	private TextView todaysDoseContext;
	private ImageView todaysDoseIcon;
	private NotificationManager notificationManager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		todaysDoseTitle = (TextView) findViewById(R.id.todays_dose_title);
		todaysDoseAmountTextView = (TextView) findViewById(R.id.todays_dose_amount_text_view);
		todaysDoseContext = (TextView) findViewById(R.id.todays_dose_context);
		todaysDoseIcon = (ImageView) findViewById(R.id.todays_dose_pill_icon);
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

		Notification doseReminder = new Notification.Builder(getApplicationContext())
				.setContentTitle("Forgot to take your pill?")
				.setContentText("Did you forget to take today's dose? Do it now!")
				.setSmallIcon(R.drawable.ic_alert_pill)
				.build();
		notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
		notificationManager.notify(0, doseReminder);
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
				todaysDoseTitle.setText(getResources().getString(R.string.confirmed_at_x, todaysDose.getFormattedConfirmationTime()));
				todaysDoseAmountTextView.setText(getResources().getString(R.string.ok));
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_undo));
				todaysDoseIcon.setVisibility(View.GONE);
			} else {
				layoutWithValue.setBackgroundColor(getResources().getColor(R.color.orange));
				String todayString = TODAY_DATE_FORMAT.format(new Date());
				todayString = Character.toUpperCase(todayString.charAt(0)) + todayString.substring(1);
				todaysDoseTitle.setText(todayString);
				todaysDoseAmountTextView.setText(getTodaysDose().getDose().getLabel());
				todaysDoseContext.setText(getResources().getString(R.string.tap_to_confirm));
				todaysDoseIcon.setVisibility(View.VISIBLE);
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
