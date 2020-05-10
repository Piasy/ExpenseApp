package com.piasy.expense;

import android.app.Application;
import com.jakewharton.threetenabp.AndroidThreeTen;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class ExpenseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // TODO: we could create a splash screen to reduce launch time white screen later
        AndroidThreeTen.init(this);
    }
}
