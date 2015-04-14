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

import java.util.Arrays;
import java.util.List;

public class AddDoseDialogFragment extends DialogFragment {

	private static final List<String> DOSES = Arrays.asList("0", "0.25", "0.5", "0.75", "1", "1.25", "1.5", "1.75", "2");
	private static final String DEFAULT_DOSE = "1";

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
						float dose = Float.valueOf(DOSES.get(numberPicker.getValue()));

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
		numberPicker.setDisplayedValues(DOSES.toArray(new String[DOSES.size()]));
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(DOSES.size() - 1);
		numberPicker.setValue(DOSES.indexOf(DEFAULT_DOSE));
		numberPicker.setWrapSelectorWheel(false);
	}

}
