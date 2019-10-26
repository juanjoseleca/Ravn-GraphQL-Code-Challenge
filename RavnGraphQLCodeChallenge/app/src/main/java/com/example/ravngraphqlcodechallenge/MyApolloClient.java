package com.example.ravngraphqlcodechallenge;
import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
public class MyApolloClient {
    private static final String BASE_URL = "https://api.github.com/graphql";
    private static ApolloClient apolloClient;
    public static ApolloClient getMyApolloClient()

    {
        //authHeader es la clave que utilizamos para el api de Github
        String authHeader="bcad06ac618925fe455800f1b885cbd328cf56a8";
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization","bearer "+authHeader);
                    return chain.proceed(builder.build());
                }).build();

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;

    }

}