package be.webfactor.inrdiary.activity;

import android.app.Activity;
import android.os.Bundle;
import be.webfactor.inrdiary.fragment.SettingsFragment;

public class SettingsActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment())
				.commit();
	}

}
