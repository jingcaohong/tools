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

import com.netlink.tools.model.TraceLog;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TraceLogService
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-15 17:12 fubencheng
 */
@Slf4j
@Service
public class TraceLogService {

    @Autowired
    private JestClient jestClient;

    public List<TraceLog> searchByTraceId(List<String> indexs, String type, String traceId) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("trace", traceId));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexs).addType(type).build();
        List<SearchResult.Hit<TraceLog, Void>> hits = jestClient.execute(search).getHits(TraceLog.class);
        List<TraceLog> result = new ArrayList<>();
        for (SearchResult.Hit<TraceLog, Void> hit : hits) {
            result.add(hit.source);
        }
        return result;
    }

    public List<TraceLog> search(List<String> indexs, String type, Map<String, Object> params) throws Exception {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indexs).addType(type).build();
        List<SearchResult.Hit<TraceLog, Void>> hits = jestClient.execute(search).getHits(TraceLog.class);
        List<TraceLog> result = new ArrayList<>();
        for (SearchResult.Hit<TraceLog, Void> hit : hits) {
            result.add(hit.source);
        }
        return result;
    }

}