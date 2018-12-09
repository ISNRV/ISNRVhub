package com.isnrv;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import com.isnrv.helper.PrayerAlarm;

/**
 * This class shows prayer times in a table. It also highleights the next prayer.
 */
public class Prayer {
	private static final int NUM_OF_FIELDS = 10;
	private final Context context;
	private final PrayerAlarm prayerAlarm;

	public Prayer(Context context) {
		this.context = context;
		this.prayerAlarm = new PrayerAlarm();
	}

	//set the GUI elements and show them
	public void setGUI(View view) {
		final String[] prayerList = prayerAlarm.getTodayPrayersArray(context.getAssets());
		if (prayerList.length == NUM_OF_FIELDS) {
			populateTable(view, prayerList);
		}
	}

	private void populateTable(View view, String[] prayerTimes) {
		final int[] prayerTableCells = {R.id.textViewFajrA, R.id.textViewFajrI,
				R.id.textViewDuhrA, R.id.textViewDuhrI,
				R.id.textViewAsrA, R.id.textViewAsrI,
				R.id.textViewMagribA, R.id.textViewMagribI,
				R.id.textViewIshaA, R.id.textViewIshaI};

		//populate 
		for (int i = 0; i < NUM_OF_FIELDS; i++) {
			((TextView) view.findViewById(prayerTableCells[i])).setText(prayerTimes[i]);
		}
		//set next prayer text bold
		int nextPrayerIndex = prayerAlarm.findNextPrayer(prayerTimes);
		if (nextPrayerIndex != -1) {
			((TextView) view.findViewById(prayerTableCells[nextPrayerIndex])).setTypeface(null, Typeface.BOLD);
			((TextView) view.findViewById(prayerTableCells[nextPrayerIndex + 1])).setTypeface(null, Typeface.BOLD);
		}
	}
}