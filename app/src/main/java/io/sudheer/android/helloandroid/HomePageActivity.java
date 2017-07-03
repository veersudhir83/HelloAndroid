package io.sudheer.android.helloandroid;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton emailButton = (FloatingActionButton) findViewById(R.id.email_button);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches) {
                    if (info.activityInfo.packageName.toLowerCase().endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail")) {
                        best = info;
                        if (best != null)
                            intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    } else {
                        Toast.makeText(HomePageActivity.this, "Gmail app is not installed !!", Toast.LENGTH_LONG).show();
                        /*Snackbar.make(view, "Gmail app is not installed !!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();*/
                    }
                }
                startActivity(intent);

            }
        });

        FloatingActionButton dialerButon = (FloatingActionButton) findViewById(R.id.dialer_button);
        dialerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasTelephony = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
                if(hasTelephony) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "")));
                    //Snackbar.make(view, "Has Calling Feature", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    /*Snackbar.make(view, "No Calling Feature", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    Toast.makeText(HomePageActivity.this, "Telephone feature is not supported !!", Toast.LENGTH_LONG).show();
                }
            }
        });

        final FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.map_button);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isGoogleMapsInstalled()
    {
        try
        {
            ApplicationInfo info = getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0 );
            return true;
        }
        catch(PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    public DialogInterface.OnClickListener getGoogleMapsListener()
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
}
