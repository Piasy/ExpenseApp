package com.piasy.expense.model;

import android.support.annotation.Nullable;
import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import java.util.HashMap;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/10.
 */
@AutoValue
public abstract class ExchangeResult {

    public static TypeAdapter<ExchangeResult> typeAdapter(final Gson gson) {
        return new AutoValue_ExchangeResult.GsonTypeAdapter(gson);
    }

    public abstract boolean success();

    @Nullable
    public abstract String source();

    @Nullable
    public abstract HashMap<String, Double> quotes();
}
