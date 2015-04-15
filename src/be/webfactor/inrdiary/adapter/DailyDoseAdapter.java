package be.webfactor.inrdiary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;

import java.util.List;

public class DailyDoseAdapter extends ArrayAdapter<DailyDose> {

	public DailyDoseAdapter(Context context, List<DailyDose> objects) {
		super(context, R.layout.manage_doses_item, objects);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.manage_doses_item, parent, false);

		DailyDose dailyDose = getItem(position);

		TextView doseDate = (TextView) rowView.findViewById(R.id.dose_date);
		doseDate.setText(dailyDose.getReadableDate());

		TextView doseAmount = (TextView) rowView.findViewById(R.id.dose_amount);
		doseAmount.setText(DoseType.getLabelForAmount(dailyDose.getDose()));

		return rowView;
	}

}
