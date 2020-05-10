package com.piasy.expense;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class ScreenPagerAdapter extends FragmentStatePagerAdapter {
    private final List<MainScreen> mScreens = Arrays.asList(MainScreen.STATS,
            MainScreen.TRANSACTIONS, MainScreen.CATEGORIES);

    public ScreenPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        return mScreens.get(position).fragment();
    }

    @Override
    public int getCount() {
        return mScreens.size();
    }

    public List<MainScreen> getScreens() {
        return mScreens;
    }
}
