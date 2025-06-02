package com.example.weathertrack.network;

import okhttp3.OkHttpClient;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.ResponseBody;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class ApiClient {
    private static Retrofit retrofit = null;

    public static MockWeatherApiService getMockService() {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            // Return static JSON regardless of URL
                            String mockJson = "{"temperature": 25.5, "humidity": 60, "condition": "Sunny"}";
                            return new Response.Builder()
                                    .code(200)
                                    .message(mockJson)
                                    .request(request)
                                    .protocol(okhttp3.Protocol.HTTP_1_1)
                                    .body(ResponseBody.create(mockJson, MediaType.parse("application/json")))
                                    .build();
                        }
                    })
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://mock.api.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MockWeatherApiService.class);
    }
}
