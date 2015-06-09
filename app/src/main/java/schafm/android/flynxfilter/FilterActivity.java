package schafm.android.flynxfilter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import schafm.android.flynxfilter.utils.BrowserInfo;
import schafm.android.flynxfilter.utils.PrefsUtils;

/**
 * Main and invisible activity to do the filtering.
 * An activity is used because the call cannot be received by a service.
 *
 * @author thecael
 */
public class FilterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Uri data = getIntent().getData();
        PrefsUtils prefsUtils = new PrefsUtils(this);
        BrowserInfo browserInfo = prefsUtils.loadSelectedBrowser();
        boolean result = startBrowserActivity(data, browserInfo);
        if (!result) {
            showSettingsActivity();
            return;
        }

        finishFilterActivity();
    }

    private void finishFilterActivity() {
        setResult(0);
        finish();
    }

    private void showSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        finishFilterActivity();
    }

    private boolean startBrowserActivity(Uri uri, BrowserInfo browserInfo) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);
        try {
            browserIntent.setClassName(browserInfo.getPackageName(), browserInfo.getActivityName());
            browserIntent.setData(uri);
            startActivity(browserIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getBaseContext(), getString(R.string.browser_cannot_be_opened),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}

