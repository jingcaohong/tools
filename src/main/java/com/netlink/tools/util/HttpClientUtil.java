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
package com.netlink.tools.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * HttpClientUtil
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-21 20:15 fubencheng
 */
@Slf4j
public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager connManager;
    private static SocketConfig socketConfig;

    static {
        try{
            SSLContextBuilder sslContextBuilder = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            });
            // 构建 Socket 连接工厂
            SSLConnectionSocketFactory sslcsf = new SSLConnectionSocketFactory(sslContextBuilder.build(), new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"},
                    null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            ConnectionSocketFactory csf = PlainConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", csf)
                    .register("https", sslcsf).build();

            // 配置连接管理器参数
            connManager = new PoolingHttpClientConnectionManager(registry);
            // 设置最大连接数
            connManager.setMaxTotal(256);
            // 设置每个路由最大连接数
            connManager.setDefaultMaxPerRoute(32);

            socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(false)
                    .setSoTimeout(6000).build();
        } catch (Exception e){
            log.error("initialize httpclient failed!!! ", e);
        }

    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setDefaultSocketConfig(socketConfig)
                .setConnectionManager(connManager)
                .setConnectionManagerShared(true)
                .build();
    }

    /**
     * 用法：HttpClientUtil.doRequest("http://www.163.com", null, null, null);
     * 
     * @param url 请求的资源URL
     * @param postData POST请求时form表单封装的数据, 没有时传null
     * @param plainText 请求参数 json 类型适用, 没有时传null
     * @param header request请求时附带的头信息(header), 没有时传null
     * @return 请求返回的文本数据
     * @throws Exception 异常
     */
    public static String doRequest(String url, Map<String, Object> postData, String plainText, Map<String, Object> header) throws Exception {
        String responseString;

        if (postData != null || plainText != null) {
            // post方式请求
            responseString = executePost(url, postData, plainText, header);
        } else {
            // get方式请求
            responseString = executeGet(url, header);
        }

        return responseString;
    }

    /**
     * get方式请求
     */
    private static String executeGet(String url, Map<String, Object> header) throws Exception {
        String responseString;
        HttpGet httpGet = new HttpGet(url.trim());

        // 设置头信息
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        try(CloseableHttpClient httpClient = getHttpClient()) {
            log.debug("before execute get, url={}", url);

            HttpResponse response = httpClient.execute(httpGet);
            responseString = readHttpResponse(response);

            log.debug("after execute post, url={}, respString={}", url, responseString);
        } catch (Exception e) {
            log.error("executeGet failed!!!, e = ", e);
            throw e;
        }

        return responseString;
    }

    /**
     * post方式请求
     */
    private static String executePost(String url, Map<String, Object> postData, String plainText, Map<String, Object> header) throws Exception {
        String responseString;
        HttpPost httpPost = new HttpPost(url.trim());

        // 设置头信息
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 设置请求参数
        if (postData != null && !postData.isEmpty()) {
            List<NameValuePair> formParams = new ArrayList<>();
            for (Map.Entry<String, Object> entry : postData.entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            urlEncodedFormEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(urlEncodedFormEntity);
        }

        if (plainText != null){
            // 参数格式json
            StringEntity stringEntity = new StringEntity(plainText, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }

        try(CloseableHttpClient httpClient = getHttpClient()){
            log.debug("before execute post, url={}, postData={}", url, JSONObject.toJSONString(postData));

            HttpResponse response = httpClient.execute(httpPost);
            responseString = readHttpResponse(response);

            log.debug("after execute post, url={}, respString={}", url, responseString);
        } catch (Exception e) {
            log.error("executePost failed!!!, e = ", e);
            throw e;
        }

        return responseString;
    }

    private static String readHttpResponse(HttpResponse response) throws Exception {
        JSONObject result = new JSONObject();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            result.put("success", Boolean.TRUE);
            result.put("code", HttpStatus.SC_OK);
            result.put("content", EntityUtils.toString(response.getEntity()));
        } else {
            result.put("success", Boolean.FALSE);
            result.put("code", response.getStatusLine().getStatusCode());

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result.put("content", EntityUtils.toString(entity));
            }
        }

        return result.toJSONString();
    }

}
