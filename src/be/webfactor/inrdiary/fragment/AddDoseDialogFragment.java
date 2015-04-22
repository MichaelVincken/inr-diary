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
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;

import java.util.Calendar;
import java.util.Date;

public class AddDoseDialogFragment extends DialogFragment {

	private static final String DATE_KEY = "date";
	private static final String DOSE_KEY = "dose";

	public static AddDoseDialogFragment newInstance(Date initialDate, DoseType initialDose) {
		AddDoseDialogFragment fragment = new AddDoseDialogFragment();

		Bundle args = new Bundle();
		args.putSerializable(DATE_KEY, initialDate);
		args.putSerializable(DOSE_KEY, initialDose);
		fragment.setArguments(args);

		return fragment;
	}

	private AddDoseDialogListener listener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (AddDoseDialogListener) activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = getActivity().getLayoutInflater().inflate(R.layout.add_dose_dialog, null);

		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.add_dose_datepicker);
		final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.add_dose_numberpicker);

		setupDatePicker(datePicker, (Date) getArguments().getSerializable(DATE_KEY));
		setupNumberPicker(numberPicker, (DoseType) getArguments().getSerializable(DOSE_KEY));

		builder.setTitle(R.string.add_dose)
				.setView(view)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						DailyDose dailyDose = new DailyDose();

						dailyDose.setDate(getDateString(datePicker));
						dailyDose.setDose(DoseType.values()[numberPicker.getValue()]);

						listener.onAddDose(dailyDose);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		return builder.create();
	}

	private void setupDatePicker(DatePicker datePicker, Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		datePicker.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
	}

	private String getDateString(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year =  datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		Date date = calendar.getTime();

		return DailyDose.DB_FORMAT.format(date);
	}

	private void setupNumberPicker(NumberPicker numberPicker, DoseType dose) {
		numberPicker.setDisplayedValues(DoseType.DISPLAY_VALUES);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(DoseType.DISPLAY_VALUES.length - 1);
		numberPicker.setValue(dose.ordinal());
		numberPicker.setWrapSelectorWheel(false);
	}

	public interface AddDoseDialogListener {

		void onAddDose(DailyDose dose);

	}

}
