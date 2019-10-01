package com.addison.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.addison.bakingapp.BuildConfig;
import com.addison.bakingapp.MainActivity;
import com.addison.bakingapp.R;
import com.addison.bakingapp.RecipeActivity;

public class BakingWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private static void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        SharedPreferences sharedPreferences = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE);
        String recipeName = sharedPreferences.getString(RecipeActivity.RECIPE_NAME_PREFERENCE_KEY, "");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recipe);
        remoteViews.setOnClickPendingIntent(R.id.tv_widget_recipe_name, pendingIntent);
        if (!recipeName.isEmpty()) {
            remoteViews.setTextViewText(R.id.tv_widget_recipe_name, recipeName);
        }

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }
}
