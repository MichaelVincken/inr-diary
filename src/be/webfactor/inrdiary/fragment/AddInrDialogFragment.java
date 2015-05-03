package be.webfactor.inrdiary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddInrDialogFragment extends DialogFragment {

	private static final List<Float> INR_VALUES;
	private static final List<String> INR_DISPLAY_VALUES_LIST;
	private static final String[] INR_DISPLAY_VALUES;

	private static final float INR_VALUE_FROM = 1.0f;
	private static final float INR_VALUE_TO = 5.0f;
	private static final float INR_VALUE_INCREMENT = 0.1f;
	private static final float DEFAULT_INR = 2.0f;

	static {
		INR_DISPLAY_VALUES_LIST = new ArrayList<>();
		INR_VALUES = new ArrayList<>();
		for (float i = INR_VALUE_FROM; i <= INR_VALUE_TO; i += INR_VALUE_INCREMENT) {
			INR_VALUES.add(i);
			INR_DISPLAY_VALUES_LIST.add(InrMeasurement.INR_VALUE_NUMBER_FORMAT.format(i));
		}
		INR_DISPLAY_VALUES = INR_DISPLAY_VALUES_LIST.toArray(new String[INR_DISPLAY_VALUES_LIST.size()]);
	}

	private AddInrDialogListener listener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (AddInrDialogListener) activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = getActivity().getLayoutInflater().inflate(R.layout.add_inr_dialog, null);

		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.add_inr_datepicker);
		final NumberPicker inrValueNumberPicker = (NumberPicker) view.findViewById(R.id.add_inr_numberpicker);

		setupNumberPicker(inrValueNumberPicker);

		builder.setTitle(R.string.add_inr_value)
				.setView(view)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						InrMeasurement inrMeasurement = new InrMeasurement();

						inrMeasurement.setDate(getDateString(datePicker));
						inrMeasurement.setInrValue(INR_VALUES.get(inrValueNumberPicker.getValue()));

						listener.onAddInr(inrMeasurement);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		return builder.create();
	}

	private void setupNumberPicker(NumberPicker numberPicker) {
		numberPicker.setDisplayedValues(INR_DISPLAY_VALUES);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(INR_VALUES.size() - 1);
		numberPicker.setWrapSelectorWheel(false);
		numberPicker.setValue(INR_DISPLAY_VALUES_LIST.indexOf(InrMeasurement.INR_VALUE_NUMBER_FORMAT.format(DEFAULT_INR)));
	}

	private String getDateString(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year =  datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		Date date = calendar.getTime();

		return InrMeasurement.DB_FORMAT.format(date);
	}

	public interface AddInrDialogListener {

		void onAddInr(InrMeasurement inrMeasurement);

	}

}
