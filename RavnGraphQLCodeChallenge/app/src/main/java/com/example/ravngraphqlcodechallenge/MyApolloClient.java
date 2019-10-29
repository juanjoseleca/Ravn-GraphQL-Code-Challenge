package com.example.ravngraphqlcodechallenge;
import com.apollographql.apollo.ApolloClient;
import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;
import com.apollographql.apollo.cache.http.ApolloHttpCache;

public class MyApolloClient {
    private static final String BASE_URL = "https://api.github.com/graphql";
    private static ApolloClient apolloClient;
    public static ApolloClient getMyApolloClient(Context contexto)
    {
        //authHeader es la clave que utilizamos para el api de Github
        String authHeader;
        String mi_clave= obtener_clave(contexto);
        if(mi_clave.length()<40)
        {
            authHeader="322b701a696ccd81d9051eaac51807f86489a003";
        }
        else
        {
            Log.d("ACCION","se cambio la clave");
            authHeader=mi_clave;
            Log.d("VALOR:",authHeader);
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization","bearer "+authHeader);
                    return chain.proceed(builder.build());
                }).build();
        File file = new File(contexto.getFilesDir(), "apolloCache");
        int size = 1024*1024;
        DiskLruHttpCacheStore cacheStore = new DiskLruHttpCacheStore(file, size);

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .httpCache(new ApolloHttpCache(cacheStore))
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;

    }
    public static String obtener_clave(Context contexto)
    {
        String ret = "";
        try {
            InputStream inputStream = contexto.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        Log.d("RESULTADO->:",ret);
        return ret;
    }

}