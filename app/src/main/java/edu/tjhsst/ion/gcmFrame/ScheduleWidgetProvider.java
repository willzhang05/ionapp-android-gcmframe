package edu.tjhsst.ion.gcmFrame;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by William Zhang on 8/14/2016.
 */
public class ScheduleWidgetProvider extends AppWidgetProvider {
    /*private String url = "https://ion.tjhsst.edu/schedule/embed/?date=2016-09-12";
    @Override
    public void onEnabled(Context context) {

    }*/
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // We can't trust the appWidgetIds param here, as we're using
        // ACTION_APPWIDGET_UPDATE to trigger our own updates, and
        // Widgets might've been removed/added since the alarm was last set.
        final int[] currentIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(context,ScheduleWidgetProvider.class));

        if (currentIds.length < 1) {
            return;
        }

        // We attach the current Widget IDs to the alarm Intent to ensure its
        // broadcast is correctly routed to onUpdate() when our AppWidgetProvider
        // next receives it.
        Intent iWidget = new Intent(context, ScheduleWidgetProvider.class)
                .setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, currentIds);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, iWidget, 0);

        ((AlarmManager) context.getSystemService(Context.ALARM_SERVICE))
                .setExact(AlarmManager.RTC, System.currentTimeMillis() + 30000, pi);

        Intent iService = new Intent(context, WebShotService.class);
        context.startService(iService);
    }
}
