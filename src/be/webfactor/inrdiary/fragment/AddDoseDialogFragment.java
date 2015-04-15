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

	public interface AddDoseDialogListener {

		void onAddDose(DailyDose dose);

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
		setupNumberPicker(numberPicker);

		builder.setTitle(R.string.add_dose)
				.setView(view)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						DailyDose dailyDose = new DailyDose();

						dailyDose.setDate(getDateString(datePicker));
						dailyDose.setDose(DoseType.getDoseForIndex(numberPicker.getValue()));

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

	private String getDateString(DatePicker datePicker) {
		int day = datePicker.getDayOfMonth();
		int month = datePicker.getMonth();
		int year =  datePicker.getYear();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);

		Date date = calendar.getTime();

		return DailyDose.DB_FORMAT.format(date);
	}

	private void setupNumberPicker(NumberPicker numberPicker) {
		numberPicker.setDisplayedValues(DoseType.DISPLAY_VALUES);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(DoseType.DISPLAY_VALUES.length - 1);
		numberPicker.setValue(DoseType.DEFAULT.ordinal());
		numberPicker.setWrapSelectorWheel(false);
	}

}
