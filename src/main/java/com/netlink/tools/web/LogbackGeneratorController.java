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
import javax.validation.constraints.NotNull;

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

    @GetMapping("/downloadXml")
    public void genLogbackXmlWithoutChain(@NotNull @RequestParam("source") String source,
                                          @NotNull @RequestParam("appName") String appName,
                                          @NotNull @RequestParam("hasBizLog") Boolean hasBizLog,
                                          HttpServletResponse response){
        log.info("generate logback xml without chain, source={}, appName={}, logType={}", source, appName);

        String appNameShort = appName.replaceAll("-", "").replaceAll("_", "").toLowerCase();
        String infoFileName = "${log.dir}/${appName}/logs/${HOSTNAME}_${appName}-ss_" + source + "_app_" + appNameShort + "_lt_info.log";
        String infoRollFileName = "${log.dir}/${appName}/logs/${HOSTNAME}_${appName}-ss_" + source + "_app_" + appNameShort + "_lt_info_%d{yyyy-MM-dd}.log";

        String errorFileName = "${log.dir}/${appName}/logs/${HOSTNAME}_${appName}-ss_" + source + "_app_" + appNameShort + "_lt_error.log";
        String errorRollFileName = "${log.dir}/${appName}/logs/${HOSTNAME}_${appName}-ss_" + source + "_app_" + appNameShort + "_lt_error_%d{yyyy-MM-dd}.log";

        String bizFileName = "${log.dir}/${appName}/logs/monitor-ss_" + source + "_app_" + appNameShort + "_lt_biz.log";
        String bizRollFileName = "${log.dir}/${appName}/logs/monitor-ss_" + source + "_app_" + appNameShort + "_lt_biz_%d{yyyy-MM-dd}.log";

        String logPattern = "%d [%thread] %-5p [%c] [%F:%L] - %msg%n";
        if (MICRO_SERVICE.equals(source)){
            logPattern = "%d [%thread] %-5p [%c] [%F:%L] [trace=%X{X-Trace-Id:-},span=%X{X-Span-Id:-},parent=%X{X-Parent-Id:-},name=%X{X-Span-Name:-},app=%X{appname:-},begintime=%X{begintime:-},endtime=%X{fin-X1-time:-}] - %msg%n";
        }

        try {
            Document document = DocumentHelper.createDocument();
            document.addComment("只需配置好 log.dir 和 appName 属性");
            Element rootElement = DocumentHelper.createElement("configuration").addAttribute("debug", "false");
            document.add(rootElement);

            rootElement.addElement("property").addAttribute("name", "log.dir").addAttribute("value", LOG_DIR);
            rootElement.addElement("property").addAttribute("name", "appName").addAttribute("value", appName);

            // info
            Element infoAppender = rootElement.addElement("appender").addAttribute("name", "infoAppender").addAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");
            infoAppender.addElement("file").addText(infoFileName);

            Element infoFilter = infoAppender.addElement("filter").addAttribute("class", "ch.qos.logback.classic.filter.ThresholdFilter");
            infoFilter.addElement("level").addText("INFO");

            Element infoRollPolicy = infoAppender.addElement("rollingPolicy").addAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
            infoRollPolicy.addElement("fileNamePattern").addText(infoRollFileName);
            infoRollPolicy.addElement("maxHistory").addText("30");

            Element infoEncoder = infoAppender.addElement("encoder").addAttribute("charset", "UTF-8").addAttribute("class", "ch.qos.logback.classic.encoder.PatternLayoutEncoder");
            infoEncoder.addElement("pattern").addText(logPattern);

            // error
            Element errorAppender = rootElement.addElement("appender").addAttribute("name", "errorAppender").addAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");
            errorAppender.addElement("file").addText(errorFileName);

            Element errorFilter = errorAppender.addElement("filter").addAttribute("class", "ch.qos.logback.classic.filter.ThresholdFilter");
            errorFilter.addElement("level").addText("ERROR");

            Element errorRollPolicy = errorAppender.addElement("rollingPolicy").addAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
            errorRollPolicy.addElement("fileNamePattern").addText(errorRollFileName);
            errorRollPolicy.addElement("maxHistory").addText("30");

            Element errorEncoder = errorAppender.addElement("encoder").addAttribute("charset", "UTF-8").addAttribute("class", "ch.qos.logback.classic.encoder.PatternLayoutEncoder");
            errorEncoder.addElement("pattern").addText(logPattern);

            if (hasBizLog) {
                // biz
                Element bizAppender = rootElement.addElement("appender").addAttribute("name", "bizAppender").addAttribute("class", "ch.qos.logback.core.rolling.RollingFileAppender");
                bizAppender.addElement("file").addText(bizFileName);

                Element bizFilter = bizAppender.addElement("filter").addAttribute("class", "com.alibaba.citrus.logconfig.logback.LevelRangeFilter");
                bizFilter.addElement("levelMax").addText("INFO");

                Element bizRollPolicy = bizAppender.addElement("rollingPolicy").addAttribute("class", "ch.qos.logback.core.rolling.TimeBasedRollingPolicy");
                bizRollPolicy.addElement("fileNamePattern").addText(bizRollFileName);
                bizRollPolicy.addElement("maxHistory").addText("30");

                Element bizEncoder = bizAppender.addElement("encoder").addAttribute("charset", "UTF-8").addAttribute("class", "ch.qos.logback.classic.encoder.PatternLayoutEncoder");
                bizEncoder.addElement("pattern").addText("%msg%n");
            }

            // logger
            Element apacheLogger = rootElement.addElement("logger").addAttribute("name", "org.apache");
            apacheLogger.addElement("level").addAttribute("value", "INFO");
            apacheLogger.addElement("appender-ref").addAttribute("ref", "errorAppender");
            apacheLogger.addElement("appender-ref").addAttribute("ref", "infoAppender");

            Element springLogger = rootElement.addElement("logger").addAttribute("name", "org.springframework");
            springLogger.addElement("level").addAttribute("value", "INFO");
            springLogger.addElement("appender-ref").addAttribute("ref", "errorAppender");
            springLogger.addElement("appender-ref").addAttribute("ref", "infoAppender");

            Element netlinkLogger = rootElement.addElement("logger").addAttribute("name", "com.netlink");
            netlinkLogger.addElement("level").addAttribute("value", "INFO");
            netlinkLogger.addElement("appender-ref").addAttribute("ref", "errorAppender");
            netlinkLogger.addElement("appender-ref").addAttribute("ref", "infoAppender");

            Element netflixLogger = rootElement.addElement("logger").addAttribute("name", "com.netflix");
            netflixLogger.addElement("level").addAttribute("value", "INFO");
            netflixLogger.addElement("appender-ref").addAttribute("ref", "errorAppender");
            netflixLogger.addElement("appender-ref").addAttribute("ref", "infoAppender");

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