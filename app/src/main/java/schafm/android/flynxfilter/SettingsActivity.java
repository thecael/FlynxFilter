package schafm.android.flynxfilter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import schafm.android.flynxfilter.adapter.AppAdapter;
import schafm.android.flynxfilter.utils.BrowserInfo;
import schafm.android.flynxfilter.utils.PrefsUtils;

/**
 * An activity to configure the behaviour of this filter app.
 *
 * @author thecael
 */
public class SettingsActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private Spinner mSelectionSpinner;
    private PrefsUtils mPrefsUtils;
    private List<ResolveInfo> mBrowsersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.title_activity_settings));
        setContentView(R.layout.activity_settings);
        mSelectionSpinner = (Spinner) findViewById(R.id.selectBrowserSpinner);
        mPrefsUtils = new PrefsUtils(this);
        mBrowsersList = getBrowserAppsAsList();
        AppAdapter adapter = new AppAdapter(this, getPackageManager(), mBrowsersList);
        mSelectionSpinner.setAdapter(adapter);
        mSelectionSpinner.post(new Runnable() {
            public void run() {
                mSelectionSpinner.setOnItemSelectedListener(SettingsActivity.this);
            }
        });

        loadPrefs();
    }

    private void loadPrefs() {
        BrowserInfo browserInfo = mPrefsUtils.loadSelectedBrowser();
        for (int i = 0; i < mBrowsersList.size(); i++) {
            String packageName = mBrowsersList.get(i).activityInfo.packageName;
            String activityName = mBrowsersList.get(i).activityInfo.name;

            if (packageName.equals(browserInfo.getPackageName()) && activityName.equals(
                    browserInfo.getActivityName())) {
                mSelectionSpinner.setSelection(i);
            }
        }
    }

    public List<ResolveInfo> getBrowserAppsAsList() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"));
        List<ResolveInfo> browsersList = getPackageManager().queryIntentActivities(
                intent, PackageManager.MATCH_DEFAULT_ONLY);
        Collections.sort(browsersList, new ResolveInfo.DisplayNameComparator(getPackageManager()));
        return browsersList;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        ResolveInfo selectedBrowser = (ResolveInfo) mSelectionSpinner.getSelectedItem();
        String packageName = selectedBrowser.activityInfo.packageName;
        String activityName = selectedBrowser.activityInfo.name;
        BrowserInfo browserInfo = new BrowserInfo(packageName, activityName);
        mPrefsUtils.saveSelectedBrowser(browserInfo);
        Toast.makeText(this, getString(R.string.settings_saved), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
