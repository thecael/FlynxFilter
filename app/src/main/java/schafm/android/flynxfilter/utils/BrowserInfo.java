package schafm.android.flynxfilter.utils;

/**
 * A browser info container class.
 *
 * @author thecael
 */
public class BrowserInfo {
    private String mPackageName;
    private String mActivityName;

    public BrowserInfo(String packageName, String activityName) {
        this.mPackageName = packageName;
        this.mActivityName = activityName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public String getActivityName() {
        return mActivityName;
    }

    public boolean isEmpty() {
        return mPackageName.isEmpty();
    }
}
