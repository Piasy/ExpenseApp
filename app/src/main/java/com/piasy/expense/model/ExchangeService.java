package com.piasy.expense.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public interface ExchangeService {
    @GET("live")
    Observable<ExchangeResult> live(@Query("access_key") String key);
}
