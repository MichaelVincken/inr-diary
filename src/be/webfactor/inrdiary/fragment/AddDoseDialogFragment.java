package be.webfactor.inrdiary.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import be.webfactor.inrdiary.R;

public class AddDoseDialogFragment extends DialogFragment {

	private AddDoseDialogListener listener;

	public interface AddDoseDialogListener {

		void onSave(DialogFragment dialog);

	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (AddDoseDialogListener) activity;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(R.string.add_dose)
				.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						listener.onSave(AddDoseDialogFragment.this);
					}
				})
				.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		return builder.create();
	}

}
