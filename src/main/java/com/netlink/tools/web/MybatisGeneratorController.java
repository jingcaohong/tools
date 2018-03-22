/*  
 * Licensed under the Apache License, Version 2.0 (the "License");  
 *  you may not use this file except in compliance with the License.  
 *  You may obtain a copy of the License at  
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0  
 *  
 *  Unless required by applicable law or agreed to in writing, software  
 *  distributed under the License is distributed on an "AS IS" BASIS,  
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  
 *  See the License for the specific language governing permissions and  
 *  limitations under the License.  
 */
package com.netlink.tools.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mybatis映射生成工具控制器.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-03-04 09:37 fubencheng.
 */
@Slf4j
@RestController
@RequestMapping("/mybatis")
public class MybatisGeneratorController {

    @GetMapping("/generator")
    public String mybatisGenerator(){
        log.info("start mybatis generate ... ");

        List<String> warnings = new ArrayList<>();

        try(InputStream xmlInStream = Resources.getResourceAsStream("mybatis-generator.xml")){

            ConfigurationParser parser = new ConfigurationParser(warnings);
            Configuration config = parser.parseConfiguration(xmlInStream);

            DefaultShellCallback callback = new DefaultShellCallback(Boolean.TRUE);
            MyBatisGenerator generator = new MyBatisGenerator(config, callback, warnings);
            generator.generate(null);

            log.info("warnings : " + warnings);
        } catch (Exception e) {
            log.error("mybatis generator running failed! ", e);
            return "FAIL";
        }

        log.info("finish mybatis generate !!! ");
        return "SUCCESS";
    }

}
