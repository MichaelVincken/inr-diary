package be.webfactor.inrdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DailyDose;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DailyDoseAdapter extends ArrayAdapter<DailyDose> {

	private static final DateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat("EEE");
	private static final DateFormat DAY_FORMAT = new SimpleDateFormat("d");
	private static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MMM");

	public DailyDoseAdapter(Context context, List<DailyDose> objects) {
		super(context, R.layout.manage_doses_item, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.manage_doses_item, parent, false);

		DailyDose dailyDose = getItem(position);

		TextView dayOfWeekTextView = (TextView) rowView.findViewById(R.id.dose_item_date_day_of_week);
		TextView dayTextView = (TextView) rowView.findViewById(R.id.dose_item_date_day);
		TextView monthTextView = (TextView) rowView.findViewById(R.id.dose_item_date_month);

		Date date = dailyDose.getDateObj();

		dayOfWeekTextView.setText(DAY_OF_WEEK_FORMAT.format(date).toUpperCase());
		dayTextView.setText(DAY_FORMAT.format(date));
		monthTextView.setText(MONTH_FORMAT.format(date).toUpperCase().substring(0, 3));

		TextView doseAmount = (TextView) rowView.findViewById(R.id.dose_amount);
		doseAmount.setText(dailyDose.getDose().getLabel());

		TextView confirmationTime = (TextView) rowView.findViewById(R.id.list_item_confirmation_date);
		confirmationTime.setText(dailyDose.getFormattedConfirmationTime());

		if (!dailyDose.isConfirmed()) {
			confirmationTime.setVisibility(View.GONE);
			TextView confirmationLabel = (TextView) rowView.findViewById(R.id.list_item_confirmation_label);
			if (dailyDose.getDateObj().before(new Date())) {
				confirmationLabel.setText(parent.getResources().getString(R.string.not_confirmed));
				confirmationLabel.setTextColor(parent.getResources().getColor(R.color.red));
			} else {
				confirmationLabel.setVisibility(View.GONE);
			}
		}

		return rowView;
	}

}
