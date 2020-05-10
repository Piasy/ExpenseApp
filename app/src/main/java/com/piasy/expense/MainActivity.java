package com.piasy.expense;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import com.github.piasy.yamvp.dagger2.YaMvpDiActivity;
import com.piasy.expense.di.DaggerExpenseComponent;
import com.piasy.expense.di.ExpenseComponent;
import com.piasy.expense.di.ProviderModule;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class MainActivity extends YaMvpDiActivity<ExpenseComponent>
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ExpenseComponent mComponent;

    private ViewPager mViewPager;
    private BottomNavigationView mNavi;
    private FloatingActionButton mButtonAdd;

    private ScreenPagerAdapter mScreenPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.pager);
        mNavi = findViewById(R.id.navi);
        mButtonAdd = findViewById(R.id.add);

        mScreenPagerAdapter = new ScreenPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mScreenPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset,
                    final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                selectBottomNavigationViewMenuItem(
                        mScreenPagerAdapter.getScreens().get(position).menuId());
                if (mScreenPagerAdapter.getScreens().get(position) == MainScreen.STATS) {
                    mButtonAdd.hide();
                } else {
                    mButtonAdd.show();
                }
            }

            @Override
            public void onPageScrollStateChanged(final int state) {
            }
        });

        mButtonAdd.setOnClickListener(v -> {
            // TODO: this kind of UI logic should also be extracted out
            Fragment fragment = mScreenPagerAdapter.getItem(mViewPager.getCurrentItem());
            if (fragment instanceof MainActionsHandler) {
                ((MainActionsHandler) fragment).onAdd();
            }
        });

        scrollToScreen(MainScreen.TRANSACTIONS);
    }

    @Override
    protected void initializeDi() {
        mComponent = DaggerExpenseComponent.builder()
                .providerModule(new ProviderModule(getApplication()))
                .build();
    }

    private void scrollToScreen(MainScreen screen) {
        int position = mScreenPagerAdapter.getScreens().indexOf(screen);
        if (position != mViewPager.getCurrentItem()) {
            mViewPager.setCurrentItem(position);
        }
    }

    private void selectBottomNavigationViewMenuItem(@IdRes int menuId) {
        mNavi.setOnNavigationItemSelectedListener(null);
        mNavi.setSelectedItemId(menuId);
        mNavi.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public ExpenseComponent getComponent() {
        return mComponent;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
        MainScreen screen = MainScreen.getMainScreenForMenuItem(menuItem.getItemId());
        if (screen != null) {
            scrollToScreen(screen);
            return true;
        }

        return false;
    }
}
