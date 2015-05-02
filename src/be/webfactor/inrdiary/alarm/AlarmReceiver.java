package be.webfactor.inrdiary.alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import be.webfactor.inrdiary.R;
import be.webfactor.inrdiary.activity.MainActivity;
import be.webfactor.inrdiary.database.DailyDoseRepository;

public class AlarmReceiver extends BroadcastReceiver {

	public void onReceive(Context context, Intent intent) {
		DailyDoseRepository dailyDoseRepository = DailyDoseRepository.getInstance(context);

		if(!dailyDoseRepository.getTodaysDose().isConfirmed()) {
			PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(context, MainActivity.class), 0);

			Notification doseReminder = new Notification.Builder(context)
					.setContentTitle(context.getResources().getString(R.string.forgot_to_take_todays_dose))
					.setContentText(context.getResources().getString(R.string.tap_to_confirm))
					.setSmallIcon(R.drawable.ic_alert_pill)
					.setContentIntent(contentIntent)
					.setAutoCancel(true)
					.build();

			((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).notify(0, doseReminder);
		}
	}

}