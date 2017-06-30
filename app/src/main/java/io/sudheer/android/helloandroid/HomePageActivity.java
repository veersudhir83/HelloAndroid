package io.sudheer.android.helloandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
                Snackbar.make(view, "Replace with your own email actions", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton dialerButon = (FloatingActionButton) findViewById(R.id.dialer_button);
        dialerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean hasTelephony = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
                if(hasTelephony) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9966377798")));
                    //Snackbar.make(view, "Has Calling Feature", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    /*Snackbar.make(view, "No Calling Feature", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/
                    Toast.makeText(HomePageActivity.this, "Hello.. This is a toast", Toast.LENGTH_LONG).show();
                }
            }
        });

        FloatingActionButton mapButton = (FloatingActionButton) findViewById(R.id.map_button);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own map action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
}
