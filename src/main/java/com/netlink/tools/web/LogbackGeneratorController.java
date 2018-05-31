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
package com.netlink.tools.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * LogbackGeneratorController
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-28 17:10 fubencheng
 */
@Slf4j
@RestController
@RequestMapping("/logback")
public class LogbackGeneratorController {

    private static final String LOG_DIR = "/alidata1/admin/";

    private static final String MICRO_SERVICE = "micro";

    private static final String FILE_NAME_PATTERN = "${log.dir}/${appName}/logs/${HOSTNAME}_${appName}-ss_%s_app_%s_lt_%s.log";

    private static final String LOG_PATTERN = "%d [%thread] %-5p [%c] [%F:%L] - %msg%n";

    private static final String MICRO_LOG_PATTERN = "%d [%thread] %-5p [%c] [%F:%L] [trace=%X{X-Trace-Id:-},span=%X{X-Span-Id:-},parent=%X{X-Parent-Id:-},name=%X{X-Span-Name:-},app=%X{appname:-},begintime=%X{begintime:-},endtime=%X{fin-X1-time:-}] - %msg%n";

    @GetMapping("/downloadXml")
    public void genLogbackXml(@RequestParam("source") String source,
                              @RequestParam("appName") String appName,
                              @RequestParam("logType") String logType,
                              HttpServletResponse response){
        log.info("generate logback xml, source={}, appName={}, logType={}", source, appName, logType);

        String appNameShort = appName.replaceAll("-", "").replaceAll("_", "").toLowerCase();
        String[] logTypeArray = logType.split("\\|");

        Document document = DocumentHelper.createDocument();
        document.addComment("只需配置好 log.dir 和 appName 属性");
        Element rootElement = DocumentHelper.createElement("configuration").addAttribute("debug", "false");
        document.add(rootElement);

        rootElement.addElement("property").addAttribute("name", "log.dir").addAttribute("value", LOG_DIR);
        rootElement.addElement("property").addAttribute("name", "appName").addAttribute("value", appName);

        for (String type : logTypeArray){
            String lowerCaseLogType = type.toLowerCase();
            String appenderName = lowerCaseLogType + "Appender";
            Element appender = rootElement.addElement("appender").addAttribute("name", appenderName).addAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");
            if (!"biz".equals(lowerCaseLogType)){
                appender.addElement("file").addText(String.format(FILE_NAME_PATTERN, source, appNameShort, lowerCaseLogType));
                Element filter = appender.addElement("filter").addAttribute("class", "ch.qos.logback.classic.filter.ThresholdFilter");
                filter.addElement("level").addText(type);
                Element rollPolicy = appender.addElement("rollingPolicy").addAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
                rollPolicy.addElement("fileNamePattern").addText(String.format(FILE_NAME_PATTERN, source, appNameShort, lowerCaseLogType).replace(".log", "_%d{yyyy-MM-dd}.log"));
                rollPolicy.addElement("maxHistory").addText("30");
                Element encoder = appender.addElement("encoder").addAttribute("charset", "UTF-8").addAttribute("class", "ch.qos.logback.classic.encoder.PatternLayoutEncoder");
                if (MICRO_SERVICE.equals(source)) {
                    encoder.addElement("pattern").addText(MICRO_LOG_PATTERN);
                } else {
                    encoder.addElement("pattern").addText(LOG_PATTERN);
                }
            } else {
                appender.addElement("file").addText("${log.dir}/${appName}/logs/monitor-ss_" + source + "_app_" + appNameShort + "_lt_biz.log");
                Element bizFilter = appender.addElement("filter").addAttribute("class", "com.alibaba.citrus.logconfig.logback.LevelRangeFilter");
                bizFilter.addElement("levelMax").addText("INFO");
                Element bizRollPolicy = appender.addElement("rollingPolicy").addAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
                bizRollPolicy.addElement("fileNamePattern").addText("${log.dir}/${appName}/logs/monitor-ss_" + source + "_app_" + appNameShort + "_lt_biz_%d{yyyy-MM-dd}.log");
                bizRollPolicy.addElement("maxHistory").addText("30");
                Element bizEncoder = appender.addElement("encoder").addAttribute("charset", "UTF-8").addAttribute("class", "ch.qos.logback.classic.encoder.PatternLayoutEncoder");
                bizEncoder.addElement("pattern").addText("%msg%n");
            }
        }

        // logger
        String[] loggers = new String[]{"org.apache", "org.springframework", "com.netflix", "com.netlink"};
        for (String logger : loggers){
            Element loggerElement = rootElement.addElement("logger").addAttribute("name", logger);
            loggerElement.addElement("level").addAttribute("value", "INFO");
            for (String type : logTypeArray){
                String lowerCaseLogType = type.toLowerCase();
                if (!"biz".equals(lowerCaseLogType)) {
                    loggerElement.addElement("appender-ref").addAttribute("ref", lowerCaseLogType + "Appender");
                }
            }
        }

        // root
        Element rootLogger = rootElement.addElement("root");
        rootLogger.addElement("level").addAttribute("value", "ERROR");
        rootLogger.addElement("appender-ref").addAttribute("ref", "errorAppender");

        // 排版缩进的格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        // 设置编码
        format.setEncoding("UTF-8");
        // 设置是否缩进
        format.setIndent(true);

        try {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            // inline在浏览器中直接显示，不提示用户下载；attachment弹出对话框，提示用户进行下载保存本地；默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=logback.xml");
            // XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File("D:\\logback1.xml")), "UTF-8"), format);
            XMLWriter writer = new XMLWriter(response.getOutputStream(), format);
            writer.setEscapeText(false);
            writer.write(document);
            writer.close();
        } catch (Exception e) {
            log.error("generate logback xml without chain failed!!!", e);
        }
    }

}