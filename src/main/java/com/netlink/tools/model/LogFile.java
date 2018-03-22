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
package com.netlink.tools.model;

import com.netlink.tools.annotation.AutoIncrement;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * LogFile
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-22 11:47 fubencheng
 */
@Data
@ToString
@Document(collection = "log_file")
public class LogFile {

    @Id
    @Field
    @AutoIncrement
    private Long id;

    @Field("file_path")
    private String filePath;

    @Field
    private String ip;

    @Field("agent_type")
    private String agentType;

    @Field("app_name")
    private String appName;

    @Field
    private String source;

    @Field("project_name")
    private String projectName;

    @Field("gmt_created")
    private Date gmtCreated;

    @Field("gmt_modified")
    private Date gmtModified;
}