package be.webfactor.inrdiary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.DailyDose;
import be.webfactor.inrdiary.domain.DoseType;
import be.webfactor.inrdiary.view.CustomDatePicker;

import java.util.Date;

public class DoseDialogFragment extends DialogFragment {

	private static final String DATE_KEY = "date";
	private static final String DOSE_KEY = "dose";
	private static final String UPDATE_KEY = "update";

	public static DoseDialogFragment newInstance(Date initialDate, DoseType initialDose, boolean update) {
		DoseDialogFragment fragment = new DoseDialogFragment();

		Bundle args = new Bundle();
		args.putSerializable(DATE_KEY, initialDate);
		args.putSerializable(DOSE_KEY, initialDose);
		args.putBoolean(UPDATE_KEY, update);
		fragment.setArguments(args);

		return fragment;
	}

	private DoseDialogListener listener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (DoseDialogListener) activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = getActivity().getLayoutInflater().inflate(R.layout.add_dose_dialog, null);

		final CustomDatePicker datePicker = (CustomDatePicker) view.findViewById(R.id.add_dose_datepicker);
		final NumberPicker numberPicker = (NumberPicker) view.findViewById(R.id.add_dose_numberpicker);

		datePicker.setDate((Date) getArguments().getSerializable(DATE_KEY));
		setupNumberPicker(numberPicker, (DoseType) getArguments().getSerializable(DOSE_KEY));
		final boolean update = getArguments().getBoolean(UPDATE_KEY);

		builder.setTitle(update ? R.string.update_dose : R.string.add_dose)
				.setView(view)
				.setNeutralButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setPositiveButton(update ? R.string.save : R.string.save_and_continue, createSaveListener(datePicker, numberPicker, update));

		return builder.create();
	}

	private DialogInterface.OnClickListener createSaveListener(final CustomDatePicker datePicker, final NumberPicker numberPicker, final boolean update) {
		return new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				DailyDose dailyDose = new DailyDose();

				dailyDose.setDate(DailyDose.DB_FORMAT.format(datePicker.getDate()));
				dailyDose.setDose(DoseType.values()[numberPicker.getValue()]);

				if (update) {
					listener.onUpdateDose(dailyDose);
				} else {
					listener.onCreateDose(dailyDose);
				}
			}
		};
	}

	private void setupNumberPicker(NumberPicker numberPicker, DoseType dose) {
		numberPicker.setDisplayedValues(DoseType.DISPLAY_VALUES);
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(DoseType.DISPLAY_VALUES.length - 1);
		numberPicker.setValue(dose.ordinal());
		numberPicker.setWrapSelectorWheel(false);
	}

	public interface DoseDialogListener {

		void onCreateDose(DailyDose dose);

		void onUpdateDose(DailyDose dose);

	}

}
