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
import android.widget.Toast;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.Dose;

public class AddDoseDialogFragment extends DialogFragment {

	public void onAttach(Activity activity) {
		super.onAttach(activity);
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
						int day = datePicker.getDayOfMonth();
						int month = datePicker.getMonth() + 1;
						int year = datePicker.getYear();
						Dose dose = Dose.values()[numberPicker.getValue()];

						Toast.makeText(getActivity().getApplicationContext(), String.format("Dose %s added for date %s/%s/%s", dose, day, month, year), Toast.LENGTH_LONG).show();
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
		numberPicker.setDisplayedValues(Dose.DISPLAY_VALUES);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(Dose.DISPLAY_VALUES.length - 1);
		numberPicker.setValue(Dose.DEFAULT.ordinal());
		numberPicker.setWrapSelectorWheel(false);
	}

}
