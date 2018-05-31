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
import com.netlink.tools.model.Metric;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * MonitorServiceTest
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-09 18:32 fubencheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorServiceTest {

    @Resource
    private MonitorService monitorService;

    @Test
    public void testFetchMetricHis(){
        List<Metric> result = monitorService.fetchMetricHis(1525226400L, null,
                "10.253.115.162","cpu.busy", "max");
        for (Metric metric : result) {
            System.out.println(metric.getMetric());
            Map<String, BigDecimal> map = JSONObject.parseObject(metric.getDps(), HashMap.class);
            Map<String, BigDecimal> finalMap = new LinkedHashMap<>();
            map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(e -> finalMap.put(e.getKey(), e.getValue()));
            for (Map.Entry<String, BigDecimal> entry : finalMap.entrySet()){
                System.out.println(entry.getKey() + "--->" + entry.getValue());
            }
        }
    }

}