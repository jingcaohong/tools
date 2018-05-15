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

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * TraceLog
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-15 17:38 fubencheng
 */
@Getter
@Setter
@ToString
public class TraceLog implements Serializable {
    private static final long serialVersionUID = -8049213257053968739L;

    private String hostName;
    private String level;
    private String appName;
    private String ip;
    private String source;
    private String message;
    private String trace;
    private String time;
    private String span;
}