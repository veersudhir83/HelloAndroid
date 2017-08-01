/*
 * Copyright (c) 2017. Sudheer Veeravalli <veersudhir83@gmail.com>
 * All Rights Reserved
 *
 * This product(or document) is protected by copyright and distributed under
 * licenses restricting copying, distribution and decompilation.
 */

package io.sudheer.android.helloandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private boolean toggle = true;

    /**
     * Default action to render the components with necessary listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.face);
        }

        /**
         * To Open Gmail and send messages
         */
        FloatingActionButton emailButton = (FloatingActionButton) findViewById(R.id.email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                        intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                    } else {
                        Snackbar.make(view, "Gmail app is not installed !!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                startActivity(intent);

            }
        });

        /**
         * To Open dialer
         */
        FloatingActionButton dialerButton = (FloatingActionButton) findViewById(R.id.dialer_button);
        dialerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasTelephony = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
                if(hasTelephony) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "")));
                    //Snackbar.make(view, "Has Calling Feature", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    Snackbar.make(view, "No Calling Feature", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        /**
         * To show restaurants near me
         */
        final FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.restaurants_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGoogleMapsInstalled()) {
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=restaurants");

                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        // Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Please install Google Maps");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Install", getGoogleMapsListener());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        /**
         * To show attractions near me
         */
        final FloatingActionButton nearMeButton = (FloatingActionButton) findViewById(R.id.map_button);
        nearMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isGoogleMapsInstalled()) {
                    // Create a Uri from an intent string. Use the result to create an Intent.
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=attractions");

                    // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        // Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Please install Google Maps");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Install", getGoogleMapsListener());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        /**
         * To open colornote app
         */
        final FloatingActionButton openNotes = (FloatingActionButton) findViewById(R.id.notes_button);
        openNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                Log.d("HomePageActivity", "Here111");
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().endsWith(".note") || info.activityInfo.name.toLowerCase().contains("note")) {
                        Snackbar.make(view, "Notes app is installed !!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        intent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
                        startActivity(intent);
                    } else {
                        Snackbar.make(view, "Notes app is not installed !!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });
    }

    /**
     * Default action to render the components of the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    /**
     * Function to invoke necessary actions on selection of menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_menu_settings_id:
                showAppSettings();
                return true;
            case R.id.home_menu_exit_id:
                this.finishAndRemoveTask();
                return true;
            case R.id.home_menu_full_screen_id:
                showViewInFullScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Checks if google maps is installed
     */
    private boolean isGoogleMapsInstalled()
    {
        try
        {
            getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    /**
     * Opens the google play store if maps is not installed on the device
     */
    private DialogInterface.OnClickListener getGoogleMapsListener()
    {
        return new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.maps"));
                startActivity(intent);

                //Finish the activity so they can't circumvent the check
                finish();
            }
        };
    }

    /**
     * Shows the app in full screen mode
     */
    private void showViewInFullScreen() {
        View decorView = getWindow().getDecorView();
        if (toggle) {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            toggle = false;
        } else {
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            toggle = true;
        }
    }

    /**
     * Shows the app settings
     */
    private void showAppSettings() {
        Toast.makeText(getApplicationContext(), "Show App Settings Here !!", Toast.LENGTH_LONG).show();
    }
}
