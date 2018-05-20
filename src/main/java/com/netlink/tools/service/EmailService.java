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

import com.netlink.tools.dto.EmailDTO;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * EmailService
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-04 17:08 fubencheng
 */
@Slf4j
@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String mailUser;

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void sendTextEmail(EmailDTO emailDTO){
        log.info("send email, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUser);
            message.setTo(emailDTO.getReceiverAddr().toArray(new String[emailDTO.getReceiverAddr().size()]));
            message.setSubject(emailDTO.getSubject());
            message.setText(emailDTO.getContent());
            mailSender.send(message);
        } catch (Exception e) {
            log.error("send email failed, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject(), e);
        }
    }

    public void sendHtmlEmail(EmailDTO emailDTO){
        log.info("send email, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(mailUser);
            mimeMessageHelper.setTo(emailDTO.getReceiverAddr().toArray(new String[emailDTO.getReceiverAddr().size()]));
            mimeMessageHelper.setSubject(emailDTO.getSubject());
            mimeMessageHelper.setText(emailDTO.getContent(), true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("send email failed, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject(), e);
        }
    }

    public void sendHtmlEmailWithAttachment(EmailDTO emailDTO, List<File> attachmentList){
        log.info("send email, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(mailUser);
            mimeMessageHelper.setTo(emailDTO.getReceiverAddr().toArray(new String[emailDTO.getReceiverAddr().size()]));
            mimeMessageHelper.setSubject(emailDTO.getSubject());
            mimeMessageHelper.setText(emailDTO.getContent(), true);
            for (File attachment : attachmentList) {
                mimeMessageHelper.addAttachment(attachment.getName(), attachment);
            }
            mailSender.send(message);
        } catch (Exception e) {
            log.error("send email failed, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject(), e);
        }
    }

    public void sendHtmlEmailByTemplate(EmailDTO emailDTO, Map<String, Object> templateParams){
        log.info("send email, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setFrom(mailUser);
            mimeMessageHelper.setTo(emailDTO.getReceiverAddr().toArray(new String[emailDTO.getReceiverAddr().size()]));
            mimeMessageHelper.setSubject(emailDTO.getSubject());

            freeMarkerConfigurer.setTemplateLoaderPath("classpath:templates");
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mail.html");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateParams);
            mimeMessageHelper.setText(html, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("send email failed, receiverAddr={}, subject={}", emailDTO.getReceiverAddr(), emailDTO.getSubject(), e);
        }
    }

}