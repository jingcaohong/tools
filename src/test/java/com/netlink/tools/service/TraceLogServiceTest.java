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

import com.alibaba.fastjson.JSONObject;
import com.netlink.tools.model.TraceLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * TraceLogServiceTest
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-15 18:09 fubencheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TraceLogServiceTest {

    @Resource
    private TraceLogService traceLogService;

    @Test
    public void testSearchByTraceId() throws Exception {
        List<String> indexs = new ArrayList<>();
        indexs.add("microlog-20180515");
        List<TraceLog> traceLogList = traceLogService.searchByTraceId(indexs, "microlog", "be5163de0bcf2e3d");
        System.out.println(JSONObject.toJSONString(traceLogList));
    }

    @Test
    public void testSearch() throws Exception {
        List<String> indexs = new ArrayList<>();
        indexs.add("microlog-20180515");
        Map<String, Object> params = new HashMap<>(4);
        params.put("trace", "be5163de0bcf2e3d");
        params.put("span", "46ce88bcb828c13b");
        List<TraceLog> traceLogList = traceLogService.search(indexs, "microlog", params);
        System.out.println(JSONObject.toJSONString(traceLogList));
    }

    @Test
    public void testQueryByTraceId() {
        List<TraceLog> traceLogList = traceLogService.queryByTraceId("be5163de0bcf2e3d", "microlog", "microlog-20180515");
        System.out.println(JSONObject.toJSONString(traceLogList));
    }

    @Test
    public void testQuery() {
        Map<String, Object> params = new HashMap<>(4);
        params.put("trace", "be5163de0bcf2e3d");
        params.put("span", "46ce88bcb828c13c");
        List<TraceLog> traceLogList = traceLogService.query(params, "microlog", "microlog-20180515");
        System.out.println(JSONObject.toJSONString(traceLogList));
    }

    @Test
    public void testQueryServiceName(){
        String startTime = "2018-05-17";
        String endTime = "2018-05-18";

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        if (!StringUtils.isEmpty(startTime)) {
            // 格式 yyyy-MM-dd
            startDate = LocalDate.parse(startTime);
        }
        if (!StringUtils.isEmpty(endTime)) {
            // 格式 yyyy-MM-dd
            endDate = LocalDate.parse(endTime);
        }
        List<String> dateRangeString = Stream.iterate(startDate, localDate -> localDate
                .plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                .map(LocalDate::toString)
                .collect(Collectors.toList());
        String[] indices = new String[dateRangeString.size()];
        for(int i = 0; i < dateRangeString.size(); i++){
            indices[i] = "zipkin-" + dateRangeString.get(i);
        }

        Set<String> serviceNames = traceLogService.queryServiceName(indices);
        System.out.println(JSONObject.toJSONString(serviceNames));
    }

    @Test
    public void testScanServiceName(){
        String[] indices = new String[]{"zipkin-2018-05-17", "zipkin-2018-05-18"};
        Set<String> serviceNames = traceLogService.scanServiceName(indices);
        System.out.println(JSONObject.toJSONString(serviceNames));
    }

}