/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netlink.tools.client;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * HttpClientHelper
 *
 * @author fubencheng
 * @version 0.0.1 2018-04-28 16:24 fubencheng
 */
@Slf4j
public final class HttpClientHelper {

    private static final int DEFAULT_WRITE_TIMEOUT = 10;
    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 30;

    private static MediaType mediaType = MediaType.parse("application/json");

    private static OkHttpClient okHttpClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient = builder.build();
    }

    private HttpClientHelper(){

    }

//    private static class OkHttpHelperHolder{
//        private static HttpClientHelper helperInstance = new HttpClientHelper();
//    }
//
//    public static HttpClientHelper getHttpClientHelper(){
//        return OkHttpHelperHolder.helperInstance;
//    }

    public static String get(String url, Map<String, Object> params){
        String result = null;
        try {
            Response response = okHttpClient.newCall(createGetRequest(url, params)).execute();
            if (!response.isSuccessful()){
                log.error("unexpected http response code {}", response.code());
            } else {
                result = response.body().string();
            }
        } catch (IOException e) {
            log.error("okhttp call execute failed!!!", e);
        }
        return result;
    }

    public static void asyncGet(String url, Map<String, Object> params, CallbackHandler handler){
        okHttpClient.newCall(createGetRequest(url, params)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    handler.onSuccess(response.body().string());
                } else {
                    handler.onSuccess(String.valueOf(response.code()));
                }
            }
        });
    }

    public static String postFormData(String url, Map<String, String> params){
        String result = null;
        try {
            Response response = okHttpClient.newCall(createPostRequest(url, params)).execute();
            if (!response.isSuccessful()){
                log.error("unexpected http response code {}", response.code());
            } else {
                result = response.body().string();
            }
        } catch (IOException e) {
            log.error("okhttp call execute failed!!!", e);
        }
        return result;
    }

    public static String postJsonString(String url, String paramsString){
        String result = null;
        try {
            RequestBody requestBody = RequestBody.create(mediaType, paramsString);
            Request request = new Request.Builder().url(url).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()){
                log.error("unexpected http response code {}", response.code());
            } else {
                result = response.body().string();
            }
        } catch (IOException e) {
            log.error("okhttp call execute failed!!!", e);
        }
        return result;
    }

    public static void asyncPostFormData(String url, Map<String, String> params, CallbackHandler handler){
        okHttpClient.newCall(createPostRequest(url, params)).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    handler.onSuccess(response.body().string());
                } else {
                    handler.onSuccess(String.valueOf(response.code()));
                }
            }
        });
    }

    private static Request createGetRequest(String url, Map<String, Object> params){
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if (Objects.nonNull(params)){
            for (Map.Entry<String, Object> entry : params.entrySet()){
                urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        return new Request.Builder().url(urlBuilder.substring(0, urlBuilder.length() -1)).build();
    }

    private static Request createPostRequest(String url, Map<String, String> params){
        FormBody.Builder formBodyBuild = new FormBody.Builder();
        if (Objects.nonNull(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formBodyBuild.add(entry.getKey(), entry.getValue());
            }
        }
        return new Request.Builder().url(url).post(formBodyBuild.build()).build();
    }

}