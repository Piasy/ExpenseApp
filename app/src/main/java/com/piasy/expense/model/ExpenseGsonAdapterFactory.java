package com.piasy.expense.model;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@GsonTypeAdapterFactory
public abstract class ExpenseGsonAdapterFactory implements TypeAdapterFactory {

    // Static factory method to access the package
    // private generated implementation
    public static TypeAdapterFactory create() {
        return new AutoValueGson_ExpenseGsonAdapterFactory();
    }
}
