package be.webfactor.inrdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InrMeasurementAdapter extends ArrayAdapter<InrMeasurement> {

	private static final DateFormat DAY_OF_WEEK_FORMAT = new SimpleDateFormat("EEE");
	private static final DateFormat DAY_FORMAT = new SimpleDateFormat("d");
	private static final DateFormat MONTH_FORMAT = new SimpleDateFormat("MMM");

	public InrMeasurementAdapter(Context context, List<InrMeasurement> objects) {
		super(context, R.layout.manage_inr_item, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.manage_inr_item, parent, false);

		InrMeasurement inrMeasurement = getItem(position);

		TextView dayOfWeekTextView = (TextView) rowView.findViewById(R.id.inr_item_date_day_of_week);
		TextView dayTextView = (TextView) rowView.findViewById(R.id.inr_item_date_day);
		TextView monthTextView = (TextView) rowView.findViewById(R.id.inr_item_date_month);

		Date date = inrMeasurement.getDateObj();

		dayOfWeekTextView.setText(DAY_OF_WEEK_FORMAT.format(date).toUpperCase());
		dayTextView.setText(DAY_FORMAT.format(date));
		monthTextView.setText(MONTH_FORMAT.format(date).toUpperCase().substring(0, 3));

		TextView inrValue = (TextView) rowView.findViewById(R.id.inr_item_value);
		inrValue.setText(String.valueOf(inrMeasurement.getInrValue()));

		return rowView;
	}

}
