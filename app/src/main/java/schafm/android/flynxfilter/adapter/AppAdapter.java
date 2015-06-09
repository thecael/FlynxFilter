package schafm.android.flynxfilter.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import schafm.android.flynxfilter.R;

/**
 * Custom adapter to display the available web browser apps.
 *
 * @author thecael
 */
public class AppAdapter extends BaseAdapter {
    private PackageManager mPackageManager;
    private List<ResolveInfo> mBrowserApps;
    private Context mContext;

    public AppAdapter(Context context, PackageManager packageManager, List<ResolveInfo> apps) {
        this.mContext = context;
        this.mPackageManager = packageManager;
        this.mBrowserApps = apps;
    }

    @Override
    public int getCount() {
        return mBrowserApps.size();
    }

    @Override
    public ResolveInfo getItem(int i) {
        return mBrowserApps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ResolveInfo browser = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.app_adapter_row, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.browserName);
            viewHolder.icon = (ImageView) convertView.findViewById(R.id.browserIcon);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(browser.loadLabel(mPackageManager));
        viewHolder.icon.setImageDrawable(browser.loadIcon(mPackageManager));
        return convertView;
    }

    private class ViewHolder {
        TextView name;
        ImageView icon;
    }
}