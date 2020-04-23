package minhaj.msm.selfpurification.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

import minhaj.msm.selfpurification.FragmentHistory;
import minhaj.msm.selfpurification.FragmentNew;
import minhaj.msm.selfpurification.R;
import minhaj.msm.selfpurification.models.DailyReport;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String HISTORY_DATA = "history_data";

    public static List<DailyReport> history;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_new, R.string.tab_text_history};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:{
                frag=new FragmentNew();
                break;
            }
            case 1:{
                frag=new FragmentHistory();
                break;
            }
        }
        return frag;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}