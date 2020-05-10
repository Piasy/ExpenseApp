package com.piasy.expense.di;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.piasy.expense.model.DbOpenHelper;
import com.piasy.expense.model.ExchangeService;
import com.piasy.expense.model.ExpenseGsonAdapterFactory;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.threeten.bp.ZoneId;
import org.threeten.bp.chrono.IsoChronology;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.ResolverStyle;
import org.threeten.bp.temporal.ChronoField;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
@Module
public class ProviderModule {
    private static final String TIME_FORMAT_ISO_8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    private final Application mApplication;

    public ProviderModule(Application application) {
        mApplication = application;
    }

    @Singleton
    @Provides
    DateTimeFormatter provideDateTimeFormatter() {
        return new DateTimeFormatterBuilder().parseCaseInsensitive()
                .append(DateTimeFormatter.ISO_LOCAL_DATE)
                .appendLiteral('T')
                .appendValue(ChronoField.HOUR_OF_DAY, 2)
                .appendLiteral(':')
                .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
                .optionalStart()
                .appendLiteral(':')
                .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
                .appendLiteral('Z')
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT)
                .withChronology(IsoChronology.INSTANCE)
                .withZone(ZoneId.systemDefault());
    }

    @Singleton
    @Provides
    Context provideContext() {
        return mApplication;
    }

    @Singleton
    @Provides
    BriteDatabase provideBriteDatabase(DbOpenHelper dbOpenHelper) {
        final SqlBrite sqlBrite = new SqlBrite.Builder()
                .logger(message -> Log.d("SqlBrite", message))
                .build();
        final BriteDatabase briteDb = sqlBrite
                .wrapDatabaseHelper(dbOpenHelper, rx.schedulers.Schedulers.io());
        // TODO: only for debugging
        briteDb.setLoggingEnabled(true);
        return briteDb;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(ExpenseGsonAdapterFactory.create())
                .create();
        return new Retrofit.Builder()
                .baseUrl("http://api.currencylayer.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    ExchangeService provideExchangeService(Retrofit retrofit) {
        return retrofit.create(ExchangeService.class);
    }
}
