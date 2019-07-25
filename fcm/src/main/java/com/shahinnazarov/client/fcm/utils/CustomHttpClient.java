package com.shahinnazarov.client.fcm.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

public class CustomHttpClient {
    private CustomHttpClient() {
    }

    private static final int MILLI_UNIT = 1000;
    private static final int TIMEOUT = 1000;

    public static HttpClient getHttpClient(final int timeout) {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * MILLI_UNIT)
                .setConnectionRequestTimeout(timeout * MILLI_UNIT)
                .setSocketTimeout(timeout * MILLI_UNIT).build();

        return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static HttpClient getHttpClient() {
        return getHttpClient(TIMEOUT);
    }
}
