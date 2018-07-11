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
package com.netlink.tools.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netlink.tools.client.HttpSender;
import com.netlink.tools.model.Metric;
import com.netlink.tools.model.MetricHis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * MonitorService
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-09 18:10 fubencheng
 */
@Slf4j
@Service
public class MonitorService {

    private static final String DB_URL = "http://tech-other-za-xquery-service.prd.za-tech.net/query/za-product/za-product-opentsdb";

    @Autowired
    private HttpSender httpSender;

    public List<Metric> fetchMetricHis(Long startTime, Long endTime, String endpoint, String metric, String aggregator) throws Exception {
        JSONObject params = new JSONObject();
        if (!Objects.isNull(startTime)){
            params.put("start", startTime);
        }
        if (!Objects.isNull(endTime)){
            params.put("end", endTime);
        }
        JSONObject tags = new JSONObject();
        tags.put("endpoint", endpoint);

        JSONObject queryObject = new JSONObject();
        queryObject.put("tags", tags);
        queryObject.put("metric", metric);
        queryObject.put("aggregator", aggregator);

        JSONArray queries = new JSONArray();
        queries.add(queryObject);
        params.put("queries", queries);
        String metricHisData = httpSender.postJsonData(DB_URL, params.toJSONString(), null);

        List<Metric> result = new ArrayList<>();
        if (!StringUtils.isEmpty(metric)) {
            MetricHis metricHis = JSONObject.parseObject(metricHisData, MetricHis.class);
            if (metricHis.getSuccess()) {
                result = metricHis.getHitsData();
            }
        }
        return result;
    }

}