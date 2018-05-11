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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * EmployeeController
 *
 * @author fubencheng
 * @version 0.0.1 2018-05-11 11:53 fubencheng
 */
@Controller
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


    //
//    [
//            [
//    {
//        "traceId": "3478021b1d9e4fbc",
//            "id": "c56e1a0289b4d513",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "3478021b1d9e4fbc",
//            "timestamp": 1526024380976000,
//            "duration": 23000,
//            "annotations": [
//        {
//            "timestamp": 1526024380976000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1526024380989000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1526024380996000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1526024380999000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "e450be414bfb3d3f",
//            "id": "b503da78650b4d4b",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "e450be414bfb3d3f",
//            "timestamp": 1525967302535000,
//            "duration": 14000,
//            "annotations": [
//        {
//            "timestamp": 1525967302535000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525967302540000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525967302547000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525967302549000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "3406cd5a7c768c03",
//            "id": "e74af74d3712ce73",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "3406cd5a7c768c03",
//            "timestamp": 1525961047293000,
//            "duration": 81000,
//            "annotations": [
//        {
//            "timestamp": 1525961047293000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047298000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047372000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047374000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "230afd99d7419da3",
//            "id": "3747398c273a4345",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "230afd99d7419da3",
//            "timestamp": 1525957969090000,
//            "duration": 22000,
//            "annotations": [
//        {
//            "timestamp": 1525957969090000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957969103000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957969111000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957969112000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "9b7ad4a3d298dc9e",
//            "id": "c0de9a25ae07f7ad",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "9b7ad4a3d298dc9e",
//            "timestamp": 1525957289948000,
//            "duration": 24000,
//            "annotations": [
//        {
//            "timestamp": 1525957289948000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957289962000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957289970000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525957289972000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "fa06df7492488fa6",
//            "id": "d0811cea299753b7",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "fa06df7492488fa6",
//            "timestamp": 1525956939635000,
//            "duration": 23000,
//            "annotations": [
//        {
//            "timestamp": 1525956939635000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956939649000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956939656000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956939658000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "bad246844befa919",
//            "id": "10d116d93db27bc7",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "bad246844befa919",
//            "timestamp": 1525956190911000,
//            "duration": 22000,
//            "annotations": [
//        {
//            "timestamp": 1525956190911000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956190924000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956190932000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525956190933000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "11f0d770a1f533f1",
//            "id": "b52b2b02a108a35d",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "11f0d770a1f533f1",
//            "timestamp": 1525955809436000,
//            "duration": 24000,
//            "annotations": [
//        {
//            "timestamp": 1525955809436000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955809449000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955809457000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955809460000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "9ed7dc2f7c416484",
//            "id": "1c35d87c3f9589af",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "9ed7dc2f7c416484",
//            "timestamp": 1525955782878000,
//            "duration": 13000,
//            "annotations": [
//        {
//            "timestamp": 1525955782878000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955782882000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955782889000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955782891000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ],
//          [
//    {
//        "traceId": "0408ffe483da57b9",
//            "id": "22e6cf3d3d46b297",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "0408ffe483da57b9",
//            "timestamp": 1525955577624000,
//            "duration": 23000,
//            "annotations": [
//        {
//            "timestamp": 1525955577624000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955577638000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955577645000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525955577647000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//      ]
//    }
//  ]
//          ]


//[
//    {
//        "traceId": "3406cd5a7c768c03",
//            "id": "e74af74d3712ce73",
//            "name": "http:/paymenttrace/selectpaytrace",
//            "parentId": "3406cd5a7c768c03",
//            "timestamp": 1525961047293000,
//            "duration": 81000,
//            "annotations": [
//        {
//            "timestamp": 1525961047293000,
//                "value": "cs",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047298000,
//                "value": "sr",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047372000,
//                "value": "ss",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "timestamp": 1525961047374000,
//                "value": "cr",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//    ],
//        "binaryAnnotations": [
//        {
//            "key": "http.host",
//                "value": "za-fin-payment-mics",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.method",
//                "value": "POST",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.path",
//                "value": "/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "http.url",
//                "value": "http://za-fin-payment-mics/paymentTrace/selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.class",
//                "value": "PaymentTraceServiceController",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "mvc.controller.method",
//                "value": "selectPayTrace",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payment-mics",
//                    "ipv4": "10.253.172.111",
//                    "port": 8080
//        }
//        },
//        {
//            "key": "spring.instance_id",
//                "value": "za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080",
//                "endpoint": {
//            "serviceName": "za-fin-payacq-mics",
//                    "ipv4": "10.253.163.88",
//                    "port": 8080
//        }
//        }
//    ]
//    }
//]


}