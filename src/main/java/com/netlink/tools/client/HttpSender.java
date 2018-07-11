package com.netlink.tools.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fubencheng
 * @date 2018-06-20
 *
 * HTTP请求发送类
 */
@Slf4j
@Component
public class HttpSender {

    private PoolingHttpClientConnectionManager connectionManager;

    @PostConstruct
    private void init(){
        connectionManager = new PoolingHttpClientConnectionManager();
        // 设置最大连接数
        connectionManager.setMaxTotal(256);
        // 设置每个路由最大连接数
        connectionManager.setDefaultMaxPerRoute(32);
    }

    private CloseableHttpClient getHttpClient(){
        return HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    /**
     * POST 提交表单数据
     * @param url 请求URL
     * @param postData POST form表单数据
     * @param header 请求头信息(header), 没有时传null
     * @return 应答文本数据
     * @throws Exception 异常
     */
    public String postFormData(String url, Map<String, Object> postData, Map<String, Object> header) throws Exception {
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

        try{
            log.debug("before execute post, url={}, postData={}", url, JSONObject.toJSONString(postData));

            HttpResponse response = getHttpClient().execute(httpPost);
            responseString = readHttpResponse(response);

            log.debug("after execute post, url={}, respString={}", url, responseString);
        } catch (Exception e) {
            log.error("executePost failed!!!, e = ", e);
            throw e;
        }

        return responseString;
    }

    /**
     * POST 提交JSON数据
     * @param url 请求URL
     * @param json POST JSON数据
     * @param header 请求头信息(header), 没有时传null
     * @return 应答文本数据
     * @throws Exception 异常
     */
    public String postJsonData(String url, String json, Map<String, Object> header) throws Exception {
        String responseString;
        HttpPost httpPost = new HttpPost(url.trim());

        // 设置头信息
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpPost.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        // 参数格式json
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        httpPost.setEntity(stringEntity);

        try{
            log.debug("before execute post, url={}, json={}", url, json);

            HttpResponse response = getHttpClient().execute(httpPost);
            responseString = readHttpResponse(response);

            log.debug("after execute post, url={}, respString={}", url, responseString);
        } catch (Exception e) {
            log.error("executePost failed!!!, e = ", e);
            throw e;
        }

        return responseString;
    }

    /**
     * GET 请求数据
     * @param url 请求URL
     * @param header 请求头信息(header), 没有时传null
     * @return 应答文本数据
     * @throws Exception 异常
     */
    public String get(String url, Map<String, Object> header) throws Exception {
        String responseString;
        HttpGet httpGet = new HttpGet(url.trim());

        // 设置头信息
        if (header != null && !header.isEmpty()) {
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                httpGet.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }

        try {
            log.debug("before execute get, url={}", url);

            HttpResponse response = getHttpClient().execute(httpGet);
            responseString = readHttpResponse(response);

            log.debug("after execute post, url={}, respString={}", url, responseString);
        } catch (Exception e) {
            log.error("executeGet failed!!!, e = ", e);
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
