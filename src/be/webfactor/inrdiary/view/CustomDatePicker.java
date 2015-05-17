package be.webfactor.inrdiary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.webfactor.inrdiary.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomDatePicker extends LinearLayout {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE d MMM");

	private TextView currentDateTextView;
	private Calendar currentDate;

	public CustomDatePicker(Context context) {
		super(context);

		loadViews();
	}

	public CustomDatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);

		LayoutInflater inflater = LayoutInflater.from(context);
		inflater.inflate(R.layout.custom_date_picker, this);

		loadViews();
	}

	private void loadViews() {
		currentDate = Calendar.getInstance();

		Button previousButton = (Button) findViewById(R.id.date_prev_button);
		previousButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentDate.add(Calendar.DATE, -1);
				updateTextView();
			}
		});

		Button nextButton = (Button) findViewById(R.id.date_next_button);
		nextButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentDate.add(Calendar.DATE, 1);
				updateTextView();
			}
		});

		currentDateTextView = (TextView) findViewById(R.id.date_current_textview);
		updateTextView();
	}

	public void setDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		currentDate = calendar;
		updateTextView();
	}

	public Date getDate() {
		return currentDate.getTime();
	}

	private void updateTextView() {
		String dateString = DATE_FORMAT.format(currentDate.getTime());
		currentDateTextView.setText(dateString);
	}

}
