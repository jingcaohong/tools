package com.netlink.tools;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@SpringBootApplication
public class ToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsApplication.class, args);
	}

	@Value("${elasticsearch.cluster-name}")
	private String clusterName;

	@Value("${elasticsearch.url}")
	private String clusterUrl;

	@Bean
	public Client transportClient() {
		Settings settings = Settings.settingsBuilder().put("cluster.name",clusterName).build();

		//https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
		TransportClient transportClient = TransportClient.builder().settings(settings).build();
		///TransportClient transportClient = new PreBuiltTransportClient(settings);
		String[] urlList = clusterUrl.split(",");
		for(String url : urlList){
			try {
				String[] ips = url.split(":");
				transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ips[0]), Integer.parseInt(ips[1])));
			} catch (UnknownHostException e) {
				log.error("unknown host : {}", url, e);
			}
		}
		return transportClient;
	}

	@Bean
	public ElasticsearchTemplate elasticsearchTemplate(){
		return new ElasticsearchTemplate(transportClient());
	}

	@Bean
	public IndicesAdminClient indicesAdminClient(){
		return transportClient().admin().indices();
	}
}
