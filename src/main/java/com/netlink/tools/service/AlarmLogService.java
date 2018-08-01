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
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author fubencheng
 */
@Slf4j
@Service
public class TraceLogService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private IndicesAdminClient indicesAdminClient;

    public List<JSONObject> query(Map<String, Object> params, String type, String... indices) {
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
        return elasticsearchTemplate.queryForList(searchQuery, JSONObject.class);
    }

}