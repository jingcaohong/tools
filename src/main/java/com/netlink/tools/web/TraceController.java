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

import com.alibaba.fastjson.JSONObject;
import com.netlink.tools.dto.TraceDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TraceController.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-05-12 09:46 fubencheng.
 */
@RestController
@RequestMapping("/zipkin")
public class TraceController {

    @RequestMapping("/trace")
    public String getTrace(Model model){
        String traceString = "[[{\"traceId\": \"3478021b1d9e4fbc\",\"id\": \"c56e1a0289b4d513\",\"name\": \"http:/paymenttrace/selectpaytrace\",\"parentId\": \"3478021b1d9e4fbc\",\"timestamp\": 1526024380976000,\"duration\": 23000,\"annotations\": [{\"timestamp\": 1526024380976000,\"value\": \"cs\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"timestamp\": 1526024380989000,\"value\": \"sr\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1526024380996000,\"value\": \"ss\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1526024380999000,\"value\": \"cr\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}],\"binaryAnnotations\": [{\"key\": \"http.host\",\"value\": \"za-fin-payment-mics\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.method\",\"value\": \"POST\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.path\",\"value\": \"/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.url\",\"value\": \"http://za-fin-payment-mics/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"mvc.controller.class\",\"value\": \"PaymentTraceServiceController\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"mvc.controller.method\",\"value\": \"selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}]}],[{\"traceId\": \"0408ffe483da57b9\",\"id\": \"22e6cf3d3d46b297\",\"name\": \"http:/paymenttrace/selectpaytrace\",\"parentId\": \"0408ffe483da57b9\",\"timestamp\": 1525955577624000,\"duration\": 23000,\"annotations\": [{\"timestamp\": 1525955577624000,\"value\": \"cs\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"timestamp\": 1525955577638000,\"value\": \"sr\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1525955577645000,\"value\": \"ss\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1525955577647000,\"value\": \"cr\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}],\"binaryAnnotations\": [{\"key\": \"http.host\",\"value\": \"za-fin-payment-mics\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.method\",\"value\": \"POST\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.path\",\"value\": \"/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.url\",\"value\": \"http://za-fin-payment-mics/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"mvc.controller.class\",\"value\": \"PaymentTraceServiceController\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"mvc.controller.method\",\"value\": \"selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}]}]]";
        TraceDTO traceDTO = JSONObject.parseObject(traceString, TraceDTO.class);
        return "trace";
    }

    public String getTraceView(Model model){
        String traceViewString = "[{\"traceId\": \"3406cd5a7c768c03\",\"id\": \"e74af74d3712ce73\",\"name\": \"http:/paymenttrace/selectpaytrace\",\"parentId\": \"3406cd5a7c768c03\",\"timestamp\": 1525961047293000,\"duration\": 81000,\"annotations\": [{\"timestamp\": 1525961047293000,\"value\": \"cs\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"timestamp\": 1525961047298000,\"value\": \"sr\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1525961047372000,\"value\": \"ss\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"timestamp\": 1525961047374000,\"value\": \"cr\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}],\"binaryAnnotations\": [{\"key\": \"http.host\",\"value\": \"za-fin-payment-mics\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.method\",\"value\": \"POST\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.path\",\"value\": \"/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"http.url\",\"value\": \"http:za-fin-payment-mics/paymentTrace/selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}},{\"key\": \"mvc.controller.class\",\"value\": \"PaymentTraceServiceController\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"mvc.controller.method\",\"value\": \"selectPayTrace\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payment-mics-16145-1525135554-1:za-fin-payment-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payment-mics\",\"ipv4\": \"10.253.172.111\",\"port\": 8080}},{\"key\": \"spring.instance_id\",\"value\": \"za-fin-payacq-mics-16144-1525133581-1:za-fin-payacq-mics:8080\",\"endpoint\": {\"serviceName\": \"za-fin-payacq-mics\",\"ipv4\": \"10.253.163.88\",\"port\": 8080}}]}]";
        TraceDTO traceDTO = JSONObject.parseObject(traceViewString, TraceDTO.class);
        return "traceViewer";
    }

}
