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
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * TraceLogService
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-15 17:12 fubencheng
 */
@Slf4j
@Service
public class TraceLogService {

    private static final String SERVICE_SPAN = "servicespan";

    @Autowired
    private JestClient jestClient;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private IndicesAdminClient indicesAdminClient;

    public List<TraceLog> searchByTraceId(List<String> indices, String type, String traceId) throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("trace", traceId));
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indices).addType(type).build();
        List<SearchResult.Hit<TraceLog, Void>> hits = jestClient.execute(search).getHits(TraceLog.class);
        List<TraceLog> result = new ArrayList<>();
        for (SearchResult.Hit<TraceLog, Void> hit : hits) {
            result.add(hit.source);
        }
        return result;
    }

    public List<TraceLog> search(List<String> indices, String type, Map<String, Object> params) throws Exception {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        Search search = new Search.Builder(searchSourceBuilder.toString()).addIndex(indices).addType(type).build();
        List<SearchResult.Hit<TraceLog, Void>> hits = jestClient.execute(search).getHits(TraceLog.class);
        List<TraceLog> result = new ArrayList<>();
        for (SearchResult.Hit<TraceLog, Void> hit : hits) {
            result.add(hit.source);
        }
        return result;
    }

    public List<TraceLog> queryByTraceId(String traceId, String type, String... indices) {
        IndicesExistsResponse checkResp = indicesAdminClient.exists(new  IndicesExistsRequest(indices)).actionGet();
        if (!checkResp.isExists()) {
            throw new IllegalArgumentException("indices not found!");
        }
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("trace", traceId);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(indices)
                .withTypes(type)
                .withQuery(queryBuilder)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, TraceLog.class);
    }

    public List<TraceLog> query(Map<String, Object> params, String type, String... indices) {
        IndicesExistsResponse checkResp = indicesAdminClient.exists(new  IndicesExistsRequest(indices)).actionGet();
        if (!checkResp.isExists()) {
            throw new IllegalArgumentException("indices not found!");
        }
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Map.Entry<String, Object> entry : params.entrySet()){
            boolQueryBuilder.must(QueryBuilders.matchQuery(entry.getKey(), entry.getValue()));
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices(indices)
                .withTypes(type)
                .withQuery(boolQueryBuilder)
                .build();
        return elasticsearchTemplate.queryForList(searchQuery, TraceLog.class);
    }

    public Set<String> queryServiceName(String... indices) {
        IndicesExistsResponse checkResp = indicesAdminClient.exists(new  IndicesExistsRequest(indices)).actionGet();
        if (!checkResp.isExists()) {
            throw new IllegalArgumentException("indices not found!");
        }
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withIndices(indices)
                .withTypes(SERVICE_SPAN)
                .addAggregation(AggregationBuilders.terms("serviceName").field("serviceName").size(0))
                .build();
        return elasticsearchTemplate.query(searchQuery, resultExtractor->{
            SearchHit[] hits = resultExtractor.getHits().getHits();
            Set<String> serviceNameSet = new HashSet<>(16);
            for (SearchHit hit : hits){
                serviceNameSet.add(hit.getSource().get("serviceName").toString());
            }
            return serviceNameSet;
        });
    }

    public Set<String> scanServiceName(String... indices){
        IndicesExistsResponse checkResp = indicesAdminClient.exists(new  IndicesExistsRequest(indices)).actionGet();
        if (!checkResp.isExists()) {
            throw new IllegalArgumentException("indices not found!");
        }
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withIndices(indices)
                .withTypes(SERVICE_SPAN)
                .build();
        String scrollId = elasticsearchTemplate.scan(searchQuery,10000L,false);
        Page<String> aggregatedPage = elasticsearchTemplate.scroll(scrollId, 15000L, new SearchResultMapper(){
            @Override
            public <String> AggregatedPage<String> mapResults(SearchResponse searchResponse, Class<String> clazz, Pageable pageable) {
                SearchHit[] hits = searchResponse.getHits().getHits();
                List<String> serviceNameList = new ArrayList<>();
                for (SearchHit hit : hits){
                    serviceNameList.add((String)hit.getSource().get("serviceName"));
                }
                return new AggregatedPageImpl<>(serviceNameList);
            }
        });
        return new HashSet<String>(aggregatedPage.getContent());
    }

}