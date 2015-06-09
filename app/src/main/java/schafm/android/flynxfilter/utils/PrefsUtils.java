package schafm.android.flynxfilter.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.widget.Toast;

import schafm.android.flynxfilter.R;

/**
 * Utility class for preferences calls of this app.
 *
 * @author thecael
 */
public class PrefsUtils {
    public static final String KEY_BROWSER_ACTIVITY_NAME = "activity_name";
    public static final String KEY_BROWSER_PACKAGE_NAME = "package_name";

    private Context mContext;
    SharedPreferences mPrefs;


    public PrefsUtils(Context context) {
        this.mContext = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    private BrowserInfo setDefaultBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"));
        ActivityInfo activityInfo = mContext.getPackageManager().resolveActivity(
                intent, PackageManager.MATCH_DEFAULT_ONLY).activityInfo;
        BrowserInfo browserInfo = new BrowserInfo(activityInfo.packageName, activityInfo.name);
        saveSelectedBrowser(browserInfo);
        return browserInfo;
    }

    public void saveSelectedBrowser(BrowserInfo browserInfo) {
        mPrefs.edit().putString(KEY_BROWSER_ACTIVITY_NAME, browserInfo.getActivityName()).commit();
        mPrefs.edit().putString(KEY_BROWSER_PACKAGE_NAME, browserInfo.getPackageName()).commit();
        Toast.makeText(mContext, mContext.getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();
    }

    public BrowserInfo loadSelectedBrowser() {
        String packageName = mPrefs.getString(KEY_BROWSER_PACKAGE_NAME, "");
        String activityName = mPrefs.getString(KEY_BROWSER_ACTIVITY_NAME, "");
        if (packageName.isEmpty()) {
            return setDefaultBrowser();
        }
        return new BrowserInfo(packageName, activityName);
    }
}
