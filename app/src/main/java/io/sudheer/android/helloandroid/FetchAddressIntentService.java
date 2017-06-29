package io.sudheer.android.helloandroid;

import android.app.IntentService;
import android.content.Intent;
import java.util.Locale;
import android.location.Geocoder;

/**
 * Created by Sudheer Veeravalli on 6/29/2017.
 */

public class FetchAddressIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchAddressIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // ...
    }

}
