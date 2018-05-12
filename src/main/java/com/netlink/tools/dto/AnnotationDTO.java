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
package com.netlink.tools.dto;

import lombok.*;

import java.io.Serializable;

/**
 * AnnotationDTO.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-05-12 10:07 fubencheng.
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnnotationDTO implements Serializable {
    private static final long serialVersionUID = 8441267043485982646L;

    private Long timestamp;
    private String value;
    private EndpointDTO endpoint;

}
