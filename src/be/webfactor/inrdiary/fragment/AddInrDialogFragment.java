package be.webfactor.inrdiary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.domain.InrMeasurement;

import java.util.Calendar;
import java.util.Date;

public class AddInrDialogFragment extends DialogFragment {

	private AddInrDialogListener listener;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (AddInrDialogListener) activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = getActivity().getLayoutInflater().inflate(R.layout.add_inr_dialog, null);

		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.add_inr_datepicker);
		final EditText inrValueEditText = (EditText) view.findViewById(R.id.add_inr_value_edittext);

		builder.setTitle(R.string.add_inr_value)
				.setView(view)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						InrMeasurement inrMeasurement = new InrMeasurement();

						inrMeasurement.setDate(getDateString(datePicker));
						inrMeasurement.setInrValue(Float.parseFloat(inrValueEditText.getText().toString()));

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
