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
import com.netlink.tools.dao.LogFileDAO;
import com.netlink.tools.model.LogFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * LogFileService
 *
 * @author fubencheng
 * @version 0.0.1 2018-03-22 13:10 fubencheng
 */
@Service
public class LogFileService {

    private LogFileDAO logFileDAO;

    @Autowired
    public LogFileService(LogFileDAO logFileDAO){
        this.logFileDAO = logFileDAO;
    }

    /**
     * 保存日志文件
     * @param logFile
     */
    public void save(LogFile logFile){
        logFileDAO.save(logFile);
    }

}