package com.piasy.expense.model;

import android.text.TextUtils;
import io.reactivex.Observable;
import javax.inject.Inject;

/**
 * Created by Piasy{github.com/Piasy} on 2020/5/9.
 */
public class ExchangeRepo {
    private static final String API_KEY = "f2d441737e45e702e87c8ffff287e78f";
    private static final Double DEFAULT_EXCHANGE_USD_NZD = 1.628293D;

    private final ExchangeService mExchangeService;

    @Inject
    public ExchangeRepo(final ExchangeService exchangeService) {
        mExchangeService = exchangeService;
    }

    public Observable<Double> exchangeUsdNzd() {
        return Observable.concat(Observable.just(DEFAULT_EXCHANGE_USD_NZD),
                mExchangeService.live(API_KEY)
                        .map(exchangeResult -> {
                            if (!exchangeResult.success()
                                || !TextUtils.equals("USD", exchangeResult.source())
                                || exchangeResult.quotes() == null
                                || exchangeResult.quotes().isEmpty()) {
                                return DEFAULT_EXCHANGE_USD_NZD;
                            }
                            Double exchange = exchangeResult.quotes().get("USDNZD");
                            if (exchange == null) {
                                return DEFAULT_EXCHANGE_USD_NZD;
                            }
                            return exchange;
                        }));
    }
}
