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

import com.netlink.tools.dto.EmployeeDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EmployeeController
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-11 11:53 fubencheng
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @RequestMapping("/list")
    public String handleRequest(Model model)  {

        Date hireDate1 = new Date();
        EmployeeDTO e1 = new EmployeeDTO(1L, "E01", "Tom", hireDate1);

        Date hireDate2 = new Date();
        EmployeeDTO e2 = new EmployeeDTO(2L, "E02", "Jerry", hireDate2);

        List<EmployeeDTO> employees = new ArrayList<>();
        employees.add(e1);
        employees.add(e2);
        model.addAttribute("employees", employees);

        return "employee";
    }


}