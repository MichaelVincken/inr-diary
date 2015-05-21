package be.webfactor.inrdiary.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import be.webfactor.inrdiary.R;

public class SettingsFragment extends PreferenceFragment {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}

}
