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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * EmailServiceTest
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-07 20:04 fubencheng
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Resource
    private EmailService emailService;

    @Test
    public void testSendTextEmail(){
        EmailDTO emailDTO = createEmailDTO();
        emailDTO.setContent("测试");
        emailService.sendTextEmail(emailDTO);
    }

    @Test
    public void testSendHtmlEmail(){
        EmailDTO emailDTO = createEmailDTO();
        emailDTO.setContent("<h1 style='text-align:center'>测试</h1><p style='color:#F00; text-align:center'>测试</p><p style='text-align:center'>测试</p>");
        emailService.sendHtmlEmail(emailDTO);
    }

    private EmailDTO createEmailDTO(){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject("测试邮件");
        emailDTO.setReceiverAddr(Arrays.asList("fubencheng@netlink.com"));
        return emailDTO;
    }

}