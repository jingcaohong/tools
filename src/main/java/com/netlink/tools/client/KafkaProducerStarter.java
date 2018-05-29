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
package com.netlink.tools.client;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * KafkaProducerStarter.
 *
 * @author fubencheng.
 * @version 0.0.1 2018-05-27 10:43 fubencheng.
 */
@Component
public class KafkaProducerStarter {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void send(String topic, String message){
        ProducerRecord record = new ProducerRecord<>(topic, message);
        kafkaTemplate.send(record);
    }

}
