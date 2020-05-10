package com.piasy.expense.stats;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.piasy.expense.MainActionsHandler;
import com.piasy.expense.R;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class StatsFragment extends Fragment implements MainActionsHandler {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
            @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onAdd() {
    }
}
