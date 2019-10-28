package com.example.ravngraphqlcodechallenge;
import com.apollographql.apollo.ApolloClient;
import android.content.Context;
import com.apollographql.apollo.api.cache.http.HttpCacheStore;
import okhttp3.internal.cache.CacheInterceptor;

import java.io.IOException;
import java.io.File;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.cache.DiskLruCache;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;
import com.apollographql.apollo.cache.http.ApolloHttpCache;
import okhttp3.internal.cache.InternalCache;

public class MyApolloClient {
    private static final String BASE_URL = "https://api.github.com/graphql";
    private static ApolloClient apolloClient;
    public static ApolloClient getMyApolloClient(Context contexto)
    {
        //authHeader es la clave que utilizamos para el api de Github
        String authHeader="211b701a696ccd81d9051eaac51807f86489a003";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization","bearer "+authHeader);
                    return chain.proceed(builder.build());
                }).build();
        File file = new File(contexto.getFilesDir(), "apolloCache");

// Size in bytes of the cache
        int size = 1024*1024;

// Create the http response cache store
        DiskLruHttpCacheStore cacheStore = new DiskLruHttpCacheStore(file, size);

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .httpCache(new ApolloHttpCache(cacheStore))
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;

    }

}