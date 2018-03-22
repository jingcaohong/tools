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
package com.netlink.tools.annotation;

import com.netlink.tools.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * AutoIncrementProcessor
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-21 21:44 fubencheng
 */
@Component
public class AutoIncrementProcessor extends AbstractMongoEventListener<Object> {

    private MongoTemplate mongoTemplate;

    @Autowired
    public AutoIncrementProcessor(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        super.onBeforeConvert(event);
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(), (final Field field) -> {
            ReflectionUtils.makeAccessible(field);
            // 如果字段添加了自定义的AutoIncrement注解
            if (field.isAnnotationPresent(AutoIncrement.class)) {
                // 设置自增ID
                field.set(source, getNextId(source.getClass().getSimpleName()));
            }
        });
    }

    private Long getNextId(String collectionName){
        Query query = new Query(Criteria.where("collection_name").is(collectionName));
        Update update = new Update();
        update.inc("sequence", 1);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(true);
        options.returnNew(true);
        Sequence sequence = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        return sequence.getSequence();
    }

}